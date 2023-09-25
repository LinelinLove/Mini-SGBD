package bdd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class BatchInsertCommand {

	String nomRel;
	String fichier;
    public BatchInsertCommand(String commande) {
		String[] commandeSplit = commande.replaceAll("[()]", "").split("\\s+");
		nomRel = commandeSplit[2];
		fichier = commandeSplit[5];

	}
	
	public void Execute() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fichier))){
		String ligneRecord;
		while( (ligneRecord = br.readLine() ) != null){
			String insertCMD = ("INSERT INTO "+ nomRel + " RECORD ("+ ligneRecord +")");
//			System.out.println(insertCMD);
			InsertCommand insertRecord = new InsertCommand(insertCMD);
			insertRecord.Execute();
		}
		br.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e)  {
			System.err.println("Batchinsert erreur : problème lors de la lecture fichier");
			e.printStackTrace();
		}
		
	}// fin Execute
	
} // fin class