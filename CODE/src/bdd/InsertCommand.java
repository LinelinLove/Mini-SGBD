package bdd;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class InsertCommand {
    RelationInfo rel;
	String nomRel;
	Record rec;
	ArrayList<String> valeurs = new ArrayList<>();
    public InsertCommand(String commande) {
		String[] commandeSplit = commande.replaceAll("[()]", "").split("\\s+");
		
		nomRel = commandeSplit[2];
		
		String[] valeursTemp = commandeSplit[4].split(",");
		for (int i = 0; i < valeursTemp.length; i++) {
			valeurs.add(valeursTemp[i]);
		}
		
		for(RelationInfo relInfo : Catalog.getInstance().listeRelationInfo) {
			if(relInfo.getNomRelation().equals(nomRel)) {
				rel = relInfo;
			}
		}
		
		rec = new Record(rel, valeurs);
	}
	
	public void Execute() throws IOException {
		FileManager fm = FileManager.getInstance();
		fm.InsertRecordIntoRelation(rel,rec);
		
	}
}
