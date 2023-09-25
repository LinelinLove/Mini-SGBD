package bdd;

import java.io.IOException;

public class BufferManagerTests {

    public static void main(String[] args) throws IOException {
//    	declaration et initialisation
        DBParams.DBPath = "DB";
    	// DBParams.DBPath = "/users/licence/ik00901/eclipse-workspace/PROJET_BDDA_LI_LIN_HASNI_TIAN_v1/";
        DBParams.maxPagesPerFile = 4;
        DBParams.pageSize = 20;

        BufferManager bm = BufferManager.getInstance();
        DiskManager dm = DiskManager.getInstance();
        
        PageId p0 = new PageId(0,0);
        PageId p1 = new PageId(1,1);
        PageId p2 = new PageId(2,2);
        
		byte [] binText = new String("BONJOUR_1").getBytes();
		dm.WritePage(p0, binText);

		byte [] binText2 = new String("COUCOU_2").getBytes();
		dm.WritePage(p1, binText2);
		
		byte [] binText3 = new String("SALUT_3").getBytes();
		dm.WritePage(p2, binText3);
		
		// affichage
        System.out.println("p0 : " + new String(bm.GetPage(p0)));
        System.out.println("p1 : " + new String(bm.GetPage(p1)));
        bm.GetPage(p0);
        bm.FreePage(p0, true);
        bm.FreePage(p1, true);
        System.out.println("p2 : " + new String(bm.GetPage(p2)));

        System.out.println("\nSerie demandees:");
        System.out.println("\tgetPage(p0)");
        System.out.println("\tgetPage(p1)");
        System.out.println("\tgetPage(p0)");
        System.out.println("\tFreePage(p0)");
        System.out.println("\tFreePage(p1)");
        System.out.println("\tgetPage(p2)");
        
        bm.afficheFramePool();
        
        System.out.println("\nExpect output framePool : P0 P2");
        
    }
}