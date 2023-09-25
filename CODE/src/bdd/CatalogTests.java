package bdd;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;


public class CatalogTests implements Serializable {
    private static final long serialVersionUID = 1L;
    public static void main(String[] args) throws IOException{
        

        PageId p1 = new PageId(0,1);

        // initialisation relinfo1
        String nomRelation = "etudiant";
        int nbColonnes = 3;
        String [] nomColonnes =  {"numero", "prenom", "prenom2"};
        ArrayList<String> values = new ArrayList<String>();
        values.add("123");
        values.add("random");        
        values.add("test");
        String [] typeColonnes = {"int", "string6", "string4"};

        RelationInfo relInfo1 = new RelationInfo(nomRelation, nbColonnes, nomColonnes, typeColonnes, p1);

        // initialisation relinfo2
        String nomRelation2 = "employe";
        int nbColonnes2 = 3;
        String [] nomColonnes2 =  {"numero", "prenom", "prenom2"};
        ArrayList<String> values2 = new ArrayList<String>();
        values2.add("456");
        values2.add("azerty");        
        values2.add("pouet");
        String [] typeColonnes2 = {"int", "string6", "string5"};

        RelationInfo relInfo2 = new RelationInfo(nomRelation2, nbColonnes2, nomColonnes2, typeColonnes2, p1);


        Catalog catalog = Catalog.getInstance();

        System.out.println("Debut Catalog");
        catalog.Init();
        catalog.AddRelation(relInfo1);
        catalog.AddRelation(relInfo2);
        catalog.Finish();
        catalog.Init();

        System.out.println("\nrelInfo1 record size  attendu : 4 + 12 + 8 = 24 \n\tresultat : " + relInfo1.calculRecordSize());
        System.out.println("relInfo2 record size  attendu : 4 + 12 + 10 = 26 \n\tresultat : " + relInfo2.calculRecordSize());

        System.out.println("\nrelInfo1 slot count : " + relInfo1.calculSlotcount());
        System.out.println("relInfo2 slot count : " + relInfo2.calculSlotcount());

        System.out.println("Fin CatalogTests");
    }
}