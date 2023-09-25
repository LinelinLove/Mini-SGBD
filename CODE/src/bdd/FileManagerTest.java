
package bdd;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;


public class FileManagerTest {
    public static void main(String[] args) throws IOException {
        //  declaration et initialisation
        DBParams.DBPath = "DB";
        DBParams.maxPagesPerFile = 4;
        DBParams.pageSize = 4096;

        BufferManager bm = BufferManager.getInstance();
        FileManager fm = FileManager.getInstance();

        
        //Test D1 et D2
        System.out.println("Test D1 et D2");
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putInt(0, -1);
        buffer.putInt(4, -1);
        buffer.putInt(8, -1);
        buffer.putInt(12, -1);
        byte[] buff = buffer.array();
        System.out.println(fm.readPageIdFromPageBuffer(buff, true));
        System.out.println(fm.readPageIdFromPageBuffer(buff, false));
        System.out.println("Expected: (-1,-1) and (-1,-1)");
        PageId p1 = new PageId(1,1);
        PageId p2 = new PageId(2,2);
        System.out.println("\nAprès");
        fm.writePageIdToBuffer(p1, buff, true);
        fm.writePageIdToBuffer(p2, buff, false);
        System.out.println(fm.readPageIdFromPageBuffer(buff, true));
        System.out.println(fm.readPageIdFromPageBuffer(buff, false));
        System.out.println("Expected: (1,1) and (2,2)");
        PageId p3 = new PageId(5,5);
        PageId p4 = new PageId(8,8);
        fm.writePageIdToBuffer(p3, buff, true);
        fm.writePageIdToBuffer(p3, buff, false);
        System.out.println(fm.readPageIdFromPageBuffer(buff, true));
        System.out.println(fm.readPageIdFromPageBuffer(buff, false));


        

        /*
        //Test D3
        System.out.println("\n\nTest D3");
        PageId headId = fm.createHeaderPage();
        System.out.println(headId + " is the headID");
        byte[] headBuffer = bm.GetPage(headId);
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, false));
        System.out.println("Expected output: (-1,0) and (-1,0)");
        

        
        //Test D4
        System.out.println("\n\nTest D4");

        String nomRelation = "etudiant";
        int nbColonnes = 4;
        String [] nomColonnes =  {"numero", "prenom", "prenom2", "nom2"};
        ArrayList<String> values = new ArrayList<String>();
        values.add("123");
        values.add("random");        
        values.add("test");
        values.add("ab1");
        String [] typeColonnes = {"int", "string6", "string4", "string3"};

        RelationInfo relInfo = new RelationInfo(nomRelation, nbColonnes, nomColonnes, typeColonnes, headId);

        PageId relId = fm.addDataPage(relInfo);
        byte[] relBuffer = bm.GetPage(relId);
        System.out.println("\nTest Chainage Relation");
        System.out.println(fm.readPageIdFromPageBuffer(relBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(relBuffer, false));
        System.out.println("\nExpected output: (-1,0) and (-1,0)");

        System.out.println("\nTest ByteMap");
        ByteBuffer relBB = ByteBuffer.wrap(relBuffer);
        relBB.position(16);
        System.out.println();
        System.out.println("RecordSize" + relInfo.getRecordSize());
        //System.out.println("Il y'a " + count + " dans la bytemap");
        System.out.println("Expected: " + relInfo.getSlotCount());
        
        System.out.println("\nTest Chainage HeaderPage");
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, false));
        System.out.println("Expected output: (0,0) and (-1,0)");
        

        System.out.println("\nTest2");
        PageId relId2 = fm.addDataPage(relInfo);
        byte[] relBuffer2 = bm.GetPage(relId2);
        System.out.println("\nTest Chainage Relation");
        System.out.println(fm.readPageIdFromPageBuffer(relBuffer2, true));
        System.out.println(fm.readPageIdFromPageBuffer(relBuffer2, false));
        System.out.println("\nExpected output: (-1,0) and (0,0)");


        
        //Test D5
        System.out.println("\n\nTest D5");
        System.out.println(fm.getFreeDataPageId(relInfo));
        System.out.println("Expected: (0,0)");
        PageId relTestId = fm.createHeaderPage();
        RelationInfo relTest = new RelationInfo(nomRelation, nbColonnes, nomColonnes, typeColonnes, relTestId);
        System.out.println(fm.getFreeDataPageId(relTest));


        
        //Test D6
        System.out.println("\n\nTest D6");
        Record rec = new Record(relInfo, values);
        System.out.println(rec.valuesToString());
        System.out.println("Test RID");

        //System.out.println(fm.writeRecordToDataPage(relInfo, rec, relId));
        System.out.println("Test Mettre Record");

        for(int j=0; j< relInfo.getSlotCount(); j++){
            fm.writeRecordToDataPage(relInfo, rec, relId);
        }

        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, false));
        System.out.println(fm.readPageIdFromPageBuffer(relBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(relBuffer, false));
        System.out.println("Expected: (0,0) and (0,0)");

        

        //Test D7
        System.out.println("\n\nTest D7");
        List<Record> records = new ArrayList<>(fm.getRecordsInDataPage(relInfo, relId));
        System.out.println("Taille est de : " + records.size() );
        System.out.println(records);
        bm.FreePage(relId, false);
        bm.FreePage(headId, false);*/
    

 

    

    


    }

}
