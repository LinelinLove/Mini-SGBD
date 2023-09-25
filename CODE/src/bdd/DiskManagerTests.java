package bdd;

import java.io.IOException;

public class DiskManagerTests {

    public static void main(String[] args) throws IOException {
    	DBParams.DBPath = "DB";
//        DBParams.DBPath = "D:\\helen\\Documents\\Study\\DEVOIR\\PROJET_BDDA_LI_LIN_HASNI_TIAN\\DB";
        DBParams.maxPagesPerFile = 4;
        DBParams.pageSize = 4096;
        
        DiskManager dm = DiskManager.getInstance();
        
        PageId [] pages = new PageId [10];
        byte [] buff = new String("BONJOUR").getBytes();

        System.out.println("Test alloc : " );
        for (int i = 0; i < pages.length; i++) {
            pages[i] = dm.AllocPage();
            System.out.println("F" + pages[i].getFileIdx() + " " + pages[i].getPageIdx());
        }

        System.out.println("Write page execute" );
		for (int i = 0; i < pages.length; i++)
		{
			dm.WritePage(pages[i], buff);
		}

        System.out.println("Read page : " );
        for (int i = 0; i < pages.length; i++)
		{
			dm.ReadPage(pages[i], buff);
            System.out.println("Contenu" + " : " + new String (buff));
		}
        for(PageId pid: pages){
            System.out.println(pid);
        }
    }
}