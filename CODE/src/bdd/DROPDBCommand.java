package bdd;

import java.io.File;
import java.io.IOException;

public class DROPDBCommand {
	
	/**
	 * supprime les fichiers dans le dossier BD
	 * remise à zero du buffermanager et catalog
	 * @throws IOException
	 */
	void Execute() throws IOException {
		// Remise à zéro du BufferManager
		// ecriture de toutes les pages dont le flagdirty = 1
		// remise a 0 de tous les flags et contenu de buffer
		BufferManager bm = BufferManager.getInstance();
		bm.FlushAll();

		// bufferpool à zéro
		bm.reset();
		
		//Remise à zéro du Catalog
		Catalog cat = Catalog.getInstance();
		cat.Reset();
		
		//Suppression des fichiers du dossier DB
		File DBFolder = new File(DBParams.DBPath);
		deleteDBFolderContent(DBFolder);
	}
	
//	supprimer les fichiers dans le dossier DB
	void deleteDBFolderContent(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	        	f.delete();
	        }
	    }
	}
	
} // fin class


