package bdd;

import java.util.ArrayList;
import java.io.*;

public class Catalog {
    //Singleton
    private static Catalog catalog;
    private Catalog(){}

    public static Catalog getInstance(){
        if (catalog == null){
            catalog = new Catalog();
        }
        return catalog;
    }

    // Membres classes
    public ArrayList<RelationInfo> listeRelationInfo;
    public int compteurRelation;

    /**
     * methode init()
     * initialise une instance
     * @throws IOException
     */
    public void Init() throws IOException{

        // initialisation instance
        DiskManager dm = DiskManager.getInstance();
        BufferManager bf = BufferManager.getInstance();
        listeRelationInfo = new ArrayList<>();
        compteurRelation = 0;

        
        // Regarde si fichier Catalog.def existe
        File temp = new File(DBParams.DBPath + "Catalog.def");
        boolean catalogExist = temp.exists();

        if(catalogExist){
            // si le fichier exist
            // lecture du fichier
            try {
                FileInputStream fileCatalogIn = new FileInputStream(DBParams.DBPath + "Catalog.def");
                ObjectInputStream ObjectIn = new ObjectInputStream(fileCatalogIn);
                
                Object obj = ObjectIn.readObject();

//                System.out.println("Le fichier Catalog.def a ÈtÈ lu.");
                ObjectIn.close();
                fileCatalogIn.close();

//                System.out.println("contenu objet :\n" + obj);

            }catch (IOException ioe)
            {
                ioe.printStackTrace();
                return;
            }catch (ClassNotFoundException c)
            {
                System.out.println("Class not found");
                c.printStackTrace();
                return;
            }
        }// fin if
    }

    /**
     * methode finish
     * s'occupe du "menage"
     */
    public void Finish () throws IOException{

        try {
        // Cr√©ation fichier Catalog.def
        FileOutputStream fichierCatalogOut = new FileOutputStream(DBParams.DBPath + "Catalog.def");
        
        // ecriture dans le fichier
        ObjectOutputStream out = new ObjectOutputStream(fichierCatalogOut);
        out.writeObject(listeRelationInfo);
        out.close();
        fichierCatalogOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * m√©thode addRelation: Ajoute une relation au catalogue
     * @param rInfo Une relation 
     */
    public void AddRelation(RelationInfo rInfo){
        listeRelationInfo.add(rInfo);
        compteurRelation++;
    }

        public void Reset() {
        listeRelationInfo.clear();
        compteurRelation = 0;
    }
}