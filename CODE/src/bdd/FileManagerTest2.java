package bdd;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class FileManagerTest2 {
    public static void main(String[] args) throws IOException{
        DBParams.DBPath = "DB\\";
        DBParams.maxPagesPerFile = 4;
        DBParams.pageSize = 4096;
        FileManager fm = FileManager.getInstance();
        BufferManager bm = BufferManager.getInstance();
        

        String [] nomColonnes =  {"num1", "string1", "num2"};
        String [] typeColonnes = {"int", "string3", "int"};
        PageId headerID = fm.createHeaderPage();
        System.out.println("The header page is:" + headerID);
        RelationInfo r = new RelationInfo("R", 3, nomColonnes, typeColonnes,headerID);
        ArrayList<String> values = new ArrayList<String>();
        values.add("1");
        values.add("aab");
        values.add("2");
        ArrayList<String> values2 = new ArrayList<String>();
        values2.add("2");
        values2.add("abc");
        values2.add("2");
        ArrayList<String> values3 = new ArrayList<String>();
        values3.add("1");
        values3.add("agh");
        values3.add("1");

        Record r1 = new Record(r, values);
        Record r2 = new Record(r, values2);
        Record r3 = new Record(r, values3);

        PageId relId = fm.getFreeDataPageId(r);

        byte[] headBuffer = bm.GetPage(headerID);
        byte[] currBuffer = bm.GetPage(relId);

        System.out.println(headerID + "is the HeadID)");
        System.out.println( "current");
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, false));
        System.out.println(relId + "is the relId)");
        System.out.println( "current");
        System.out.println(fm.readPageIdFromPageBuffer(currBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(currBuffer, false));
        //System.out.println(fm.writeRecordToDataPage(r, r1, relId));
        //System.out.println(fm.writeRecordToDataPage(r, r2, relId));
        //System.out.println(fm.writeRecordToDataPage(r, r3, relId));
        System.out.println("The slot Count is: " + r.getSlotCount());
        for(int i=0; i< 10;i++){
            System.out.println(fm.InsertRecordIntoRelation(r, r1));
        }
        System.out.println(r.getSlotCount());
        System.out.println(headerID + "is the HeadID)");
        System.out.println( "after");
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(headBuffer, false));
        System.out.println(relId + "is the relId)");
        System.out.println( "after");
        System.out.println(fm.readPageIdFromPageBuffer(currBuffer, true));
        System.out.println(fm.readPageIdFromPageBuffer(currBuffer, false));
        
        

        
       /* System.out.println(fm.InsertRecordIntoRelation(r, r1));
        System.out.println(fm.InsertRecordIntoRelation(r, r2));
        System.out.println(fm.InsertRecordIntoRelation(r, r3));*/
        List<Record> records = new ArrayList<>(fm.GetAllRecords(r));
        System.out.println("Taille est de : " + records.size() );
        //System.out.println(records); 

        bm.FreePage(headerID, false);
        bm.FreePage(relId, false);


    }
}
