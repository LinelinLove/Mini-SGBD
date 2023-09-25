package bdd;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import javax.swing.text.Position;

public class test {
    public static void main(String[] args) throws IOException {
        DBParams.DBPath = "DB";
        DBParams.maxPagesPerFile = 4;
        DBParams.pageSize = 4096;

        BufferManager bm = BufferManager.getInstance();
        FileManager fm = FileManager.getInstance();

        PageId p1 = new PageId(0,1);
        String nomRelation = "etudiant";
        int nbColonnes = 4;
        String [] nomColonnes =  {"numero", "prenom", "prenom2", "nom2"};
        ArrayList<String> values = new ArrayList<String>();
        values.add("123");
        values.add("prenom");        
        values.add("1");
        values.add("9999");
        String [] typeColonnes = {"int", "string6", "int", "int"};

        RelationInfo relInfo = new RelationInfo(nomRelation, nbColonnes, nomColonnes, typeColonnes, p1);
        
        Record rec = new Record(relInfo, values);
        Record testRec = new Record(relInfo, new ArrayList<String>());

        ByteBuffer test = ByteBuffer.allocate(200);
        test.position(0);
        test.putInt(-1);
        test.putInt(-1);
        test.putInt(-1);
        test.putInt(-1);

        rec.writeToBuffer(test, 16);
        testRec.readFromBuffer(test, 16);
        
        System.out.println(rec.valuesToString());
        System.out.println(testRec.valuesToString());



    }
}
