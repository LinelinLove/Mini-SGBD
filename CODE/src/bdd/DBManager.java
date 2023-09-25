package bdd;

import java.io.IOException;
import java.util.StringTokenizer;

public class DBManager {

	/**
     * Singleton
     */
    private static DBManager dbmanager;
    
    DBManager() {
    }

    public static DBManager getInstance() {
        if (dbmanager == null) {
            dbmanager = new DBManager();
        }
        return dbmanager;
    }
    
    public void Init() throws IOException {
    	Catalog.getInstance().Init();
    }
	
    public void Finish() throws IOException {
    	Catalog.getInstance().Finish();
    	BufferManager.getInstance().FlushAll();    	
    }
    
    public void ProcessCommand(String commande) throws IOException {
    	String[] commandeSplit = commande.split("\\s+");
    	
    	switch(commandeSplit[0]) {
    		case "CREATE":
    			if (commandeSplit[1].equals("RELATION")){
    				CreateRelationCommand crc = new CreateRelationCommand(commande);
    				crc.Execute();
    			}
    			break;
    		case "DROPDB":
    			DROPDBCommand ddbc = new DROPDBCommand();
    			ddbc.Execute();
    			break;
    		case "INSERT":
    			if (commandeSplit[1].equals("INTO") && commandeSplit[3].equals("RECORD")){
        			InsertCommand ic = new InsertCommand(commande);
        			ic.Execute();
    			}
    			break;
    			
    		case "BATCHINSERT" :
    			if (commandeSplit[1].equals("INTO") && commandeSplit[3].equals("FROM") && commandeSplit[4].equals("FILE")){
        			BatchInsertCommand bic = new BatchInsertCommand(commande);
        			bic.Execute();
    			}
    			break;
    		case "SELECTMONO":
				if(commandeSplit[1].equals("*") && commandeSplit[2].equals("FROM")){
					SelectMonoCommand smc = new SelectMonoCommand(commande);
					smc.Execute();
				}
				break;

			default:
				System.out.println("Commande incorrecte");
    	} // fin switch
    	
    } // fin ProcessCommand()
    
}
