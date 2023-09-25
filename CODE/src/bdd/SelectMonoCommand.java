package bdd;
import java.io.IOException;
import java.util.ArrayList;

import java.util.ArrayList;

public class SelectMonoCommand {
    public String nomRel;
    public RelationInfo rel;
    public ArrayList<Record> records;
    public int nbCritere = 0;

    public SelectMonoCommand(String commande){
        String[] commandeSplit = commande.replaceAll("[()]", "").split("\\s+");
        nomRel = commandeSplit[3];
        for(RelationInfo relInfo : Catalog.getInstance().listeRelationInfo) {
			if(relInfo.getNomRelation().equals(nomRel)) {
				rel = relInfo;
			}
		}

        if((commandeSplit.length - 4) > 0 ){
            for(String s : commandeSplit){
                if(s.equals("AND")){
                    nbCritere++;
                }
            }
        }
        

    }
    public void Execute() throws IOException {
		FileManager fm = FileManager.getInstance();

        records = fm.GetAllRecords(rel);
		for(Record r: records){
            System.out.println(r);
        }
        System.out.println("Total records=" + records.size() +",");
		
	}

}
