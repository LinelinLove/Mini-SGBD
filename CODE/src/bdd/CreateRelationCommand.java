package bdd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateRelationCommand {

	String nomRelation;
	int nbColonnes;
    ArrayList<String> nomColonnes;
    ArrayList<String> typeColonnes;
    
    public CreateRelationCommand(String commande) {

    	String[] commandeSplit = commande.replaceAll("[()]", "").split("\\s+");
    	
    	nomRelation = commandeSplit[2];
    	String[] infoColonnes = commandeSplit[3].split(",");

    	nomColonnes = new ArrayList<>();
    	typeColonnes = new ArrayList<>();

    	for(int i = 0; i < infoColonnes.length ; i++) {
    		String[] temp = infoColonnes[i].split(":");
			nomColonnes.add(temp[0]);
    		typeColonnes.add(temp[1]);
    	}
        
    nbColonnes = nomColonnes.size();
	}
    
    void Execute() throws IOException {
    	
    	String[] nColonnes = new String[nbColonnes];
    	String[] tColonnes = new String[nbColonnes];
    	PageId headerPageId = FileManager.getInstance().createHeaderPage();
    	
    	for (int i = 0; i < nbColonnes; i++) {
    		nColonnes[i] =  nomColonnes.get(i);
    		tColonnes[i] =  typeColonnes.get(i);
    	}
    	
    	RelationInfo relation = new RelationInfo(nomRelation, nbColonnes, nColonnes, tColonnes, headerPageId);
    	Catalog.getInstance().AddRelation(relation);
    }
}
