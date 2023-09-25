package bdd;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RecordTests {

    public static void main(String[] args){

        PageId p1 = new PageId(0,1);

        // initialisation
        int position = 0;
        int capacity = 200;
        ByteBuffer buff =  ByteBuffer.allocate(capacity);

        String nomRelation = "etudiant";
        int nbColonnes = 4;
        String [] nomColonnes =  {"numero", "prenom", "prenom2", "nom2"};
        ArrayList<String> values = new ArrayList<String>();
        values.add("123");
        values.add("random");        
        values.add("test");
        values.add("ab1");
        String [] typeColonnes = {"int", "string6", "string4", "string3"};

        RelationInfo relInfo = new RelationInfo(nomRelation, nbColonnes, nomColonnes, typeColonnes, p1);
        
        Record rec = new Record(relInfo, values);

        //test de write
        System.out.println("Write s'exécute");
        rec.writeToBuffer(buff, position);
        System.out.println("\nRead s'exécute");
        //rec.readFromBuffer(buff, position);
        Record test = new Record(relInfo);
        test.readFromBuffer(buff, position);
        //System.out.println("Contenu buff : " + new String (buff.array()) ) ;
        // contenu de values
        System.out.println("Valeurs : ");
        System.out.println(rec.valuesToString());
        System.out.println(test.valuesToString());

       
        
    


        System.out.println("\nFin de record tests");

        
        
        

    }
	

} // fin class
