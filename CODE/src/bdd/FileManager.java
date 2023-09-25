package bdd;

import java.io.IOException;
import java.util.ArrayList;
import java.nio.*;
import java.util.*;

public class FileManager{
    //Singleton 
    private static FileManager fileManager;
    
    FileManager() {
    }

    public static FileManager getInstance() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    } 

    DiskManager dm = DiskManager.getInstance(); 
    BufferManager bm = BufferManager.getInstance();

    /**
     * Lit les pageID au début d'une page (premier ou second)
     * @param buffer un buffer 
     * @param first un booléen indiquant si c'est la première pageID
     * @return pid le PID lu
     * @throws IOException 
     */
    public PageId readPageIdFromPageBuffer(byte[] buffer, boolean first) throws IOException{
        PageId pid; 
        if(first){
            // si first, lecture de la premiere page de donnée
            ByteBuffer wrapped = ByteBuffer.wrap(buffer);
            wrapped.rewind();
            int fileIdx = wrapped.getInt();
            int pageIdx = wrapped.getInt();
            pid = new PageId(fileIdx, pageIdx);

        }
        else{
            // lecture d'une page de donnée
            ByteBuffer wrapped = ByteBuffer.wrap(buffer);
            wrapped.rewind();
            wrapped.position(8);
            int fileIdx = wrapped.getInt();
            int pageIdx = wrapped.getInt();
            pid = new PageId(fileIdx, pageIdx);

        }
        
        return pid;
    }

    /**
     * Ecrit les pageID au début d'une page (premier ou second)
     * @param pid la PageId à écrire
     * @param buffer un buffer 
     * @param first un booléen indiquant si c'est la première pageID
     * @throws IOException 
     */
    public void writePageIdToBuffer(PageId pid, byte[] buff, boolean first){
        ByteBuffer wrapped = ByteBuffer.wrap(buff);
        if(first){
            // si first, ecriture dans la premiere page de donnée
            wrapped.position(0);
            wrapped.putInt(pid.getFileIdx());
            wrapped.putInt(pid.getPageIdx());
        }
        else{
            // ecriture dans une page de donnée
             
            wrapped.position(Integer.BYTES*2);
            wrapped.putInt(pid.getFileIdx());
            wrapped.putInt(pid.getPageIdx());

        }
    }
    
    /**
     * Créer une headerPage 
     * @throws IOException 
     */
    public PageId createHeaderPage() throws IOException {
        PageId headerPagePid = dm.AllocPage(); 
        byte[] buffer = bm.GetPage(headerPagePid); 
        PageId fakeId = new PageId(-1,0);
        writePageIdToBuffer(fakeId, buffer, true); // headerPage
        writePageIdToBuffer(fakeId, buffer, false); // 2eme page
        bm.FreePage(headerPagePid, true); 
        // pour headerPage valdirty true, on decrement p_count et libere la page
        return headerPagePid;
    }
    

    /**
     * Cette méthode devra rajouter une page de données « vide » au 
     * Heap File correspondant à la relation 
     * identifiée par relInfo, et retourner le PageId de cette page
     * @param relInfo une relation
     * @return PageId le pid de la nouvelle page
     */
    public PageId addDataPage(RelationInfo relInfo) throws IOException{
        PageId relPid = dm.AllocPage();
        byte[] buffer = bm.GetPage(relPid); 
        byte[] headerbuff = bm.GetPage(relInfo.getHeaderPageId());
        PageId headerID = readPageIdFromPageBuffer(headerbuff, true);

        //Ajout de la bytemap
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        wrapped.position(16);
        for(int i=0; i<relInfo.getSlotCount(); i++){
            //wrapped.putInt(0);
            wrapped.put((byte)0b0);
        }
        
        //Chainage
        writePageIdToBuffer(relPid, headerbuff, true);
        writePageIdToBuffer(new PageId(-1, 0), buffer, true);
        writePageIdToBuffer(headerID, buffer, false);
        bm.FreePage(relInfo.getHeaderPageId(), true);
        bm.FreePage(relPid, true);
        return relPid; 
    }


    /**
     * Trouve une page libre pour une relation
     * @param relInfo une relation
     * @return pid le PID d'une page libre
     * @throws IOException 
     */
    public PageId getFreeDataPageId(RelationInfo relInfo) throws IOException{
        byte[] headerBuffer = bm.GetPage(relInfo.getHeaderPageId());
        PageId headId = readPageIdFromPageBuffer(headerBuffer, true);
        //S'il n'y a pas de pages vides
        if(headId.getFileIdx() == -1){
            //System.out.println("Créer");
            PageId relPid = addDataPage(relInfo); //Créer une nouvelle page
            return relPid;
        }
        bm.FreePage(headId, false);
        bm.FreePage(relInfo.getHeaderPageId(), false);
        
        return headId;
    }


    /**
     * Ecriture du contenu d'un record dans une page
     * @param relInfo une relation
     * @param record un record à écrire
     * @param pid le pid de la page 
     * @return Rid le rid du record 
     * @throws IOException 
     */
    public Rid writeRecordToDataPage(RelationInfo relInfo, Record record, PageId pid) throws IOException{
        byte[] buffer = bm.GetPage(pid); 
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);
        int occupe = 0;
        int countLibre = relInfo.getSlotCount();
        wrapped.position(16);

        for(int i=0; i<relInfo.getSlotCount(); i++){
            //int bytemap = wrapped.getInt();
            byte bit = wrapped.get();
            if(bit == 0b1){
                occupe++;
            }
        }
        
        wrapped.position(0);
        countLibre = countLibre - occupe;
        int pos = 16 + (relInfo.getSlotCount()) + (occupe*relInfo.getRecordSize());

        record.writeToBuffer(wrapped, pos);
        Rid rid = new Rid(pid, occupe+1);
        
        wrapped.put(16+occupe, (byte)0b1);
        countLibre--; //On vient de prendre un slot, on décrementer le nb de slot libre

        //Si la page est devenue pleine
        if(countLibre == 0){
//            System.out.println("Pleine");
            byte[] headBuffer = bm.GetPage(relInfo.getHeaderPageId());
            PageId headID = readPageIdFromPageBuffer(headBuffer, false);
            PageId prevID = readPageIdFromPageBuffer(buffer, false);
            writePageIdToBuffer(pid, headBuffer, false);
            writePageIdToBuffer(headID, buffer, false);
            writePageIdToBuffer(new PageId(-1, 0), buffer, true);
            writePageIdToBuffer(prevID, headBuffer, true);
            bm.FreePage(relInfo.getHeaderPageId(), true);
        }
        
        bm.FreePage(pid, true);
        return rid;

    }


    /**
     * Enumère les records d'une page de données
     * @param relInfo une relation
     * @param pid le pid de la page
     * @return une liste de records
     * @throws IOException 
     */
    public ArrayList<Record> getRecordsInDataPage(RelationInfo relInfo, PageId pid) throws IOException{
        byte[] buffer = bm.GetPage(pid);
        ByteBuffer wrapped = ByteBuffer.wrap(buffer);

        ArrayList<Record> records = new ArrayList<>();
        List<Byte> bytemap = new ArrayList<>();
        int occupe = 0;

        wrapped.position(16);
        for(int i=0; i<relInfo.getSlotCount(); i++){
            bytemap.add(wrapped.get());
        }
        wrapped.rewind();
        for(byte b: bytemap){
            int pos = 16 + (relInfo.getSlotCount()) + (occupe*relInfo.getRecordSize());
            Record rec = new Record(relInfo, new ArrayList<String>());     
            if(b==0b1){   
                rec.readFromBuffer(wrapped, pos);
                records.add(rec);   
                occupe++; 
            }
        }
        bm.FreePage(pid, false);
        return records;
    }

    
    //API 
    /**
     * Insère une record dans une relation
     * @param relInfo une relation
     * @param record un record/tuple
     * @return Rid le RID sur record inséré
     * @throws IOException 
     */
    public Rid InsertRecordIntoRelation(RelationInfo relInfo, Record record) throws IOException{
        return writeRecordToDataPage(relInfo, record, getFreeDataPageId(relInfo));
    }
    
    /**
     * Enumère tout les records d'une relation
     * @param relInfo une relation
     * @return ArrayList<Record> une liste de record
     * @throws IOException 
     */
    public ArrayList<Record> GetAllRecords(RelationInfo relInfo) throws IOException{
//        ArrayList<Record> records = new ArrayList<>();
//
//        byte[] headBuffer = bm.GetPage(relInfo.getHeaderPageId());
//        //Lire Les Pages Occupés 
//        PageId pidOccupe = readPageIdFromPageBuffer(headBuffer, false);
//        while(!(pidOccupe.getFileIdx() == -1)){
//            ArrayList<Record> tempOccupe = new ArrayList<>();
//            tempOccupe = getRecordsInDataPage(relInfo, pidOccupe);
//            for(Record rOccupe: tempOccupe){
//                records.add(rOccupe);
//            }
//            byte[] nextOccupe = bm.GetPage(pidOccupe);
//            PageId temp = readPageIdFromPageBuffer(nextOccupe, false);
//            bm.FreePage(pidOccupe, false);
//            pidOccupe = temp;
//        }
//
//        PageId pidLibre = readPageIdFromPageBuffer(headBuffer, true);
//        //Lire les Pages Libres
//        while(!(pidLibre.getFileIdx() == -1)){
//            ArrayList<Record> tempLibre = new ArrayList<>();
//            if(pidLibre.getFileIdx() == -1){
//                break;
//            }
//            tempLibre = getRecordsInDataPage(relInfo, pidLibre);
//            for(Record rLibre: tempLibre){
//                records.add(rLibre);
//            }
//            byte[] nextLibre = bm.GetPage(pidLibre);
//            PageId temp = readPageIdFromPageBuffer(nextLibre, true);
//            bm.FreePage(pidLibre, false);
//            pidLibre = temp;
//        }
//        bm.FreePage(relInfo.getHeaderPageId(), false);
//        return records;
        return getRecordsInDataPage(relInfo, getFreeDataPageId(relInfo));
    }

} // fin class