package bdd;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
    DBParams.DBPath = "C:\\Users\\chris\\Desktop\\PROJET_BDDA_LI_LIN_HASNI_TIAN\\DB\\";
    //   DBParams.DBPath = "C:\\Users\\chris\\Desktop\\L3_2021-2022\\L3S5_BDDA_projet\\10112021\\DB\\";
//    	DBParams.DBPath = "/users/licence/ik05965/eclipse-workspace/PROJET_BDDA/BD/";
        // DBParams.pageSize = 10;


        DBManager dbm = new DBManager();
        
        dbm.Init();
        Scanner scanner = new Scanner(System.in);
        String commande;
        while(true) {
        	 System.out.println("Entre une commande:");
        	 commande = scanner.nextLine();
        	 
        	 if(commande.toUpperCase().equals("EXIT")) {
        		 dbm.Finish();
        		 break;
        	 } else {
        		 dbm.ProcessCommand(commande);
        	 }        	 
        }
        scanner.close();
    } // fin main
} // fin class