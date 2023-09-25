package bdd;

import java.io.*; //class File
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManager {
	
	/**
     * Singleton
     */
    private static DiskManager diskmanager;
    
    DiskManager() {
    }

    public static DiskManager getInstance() {
        if (diskmanager == null) {
            diskmanager = new DiskManager();
        }
        return diskmanager;
    }

    /**
     * Rempli le contenu de l'arg buff avec le contenu du disque de la pageId
     * @param pageId
     * @param buff
     * @throws IOException
     */
    public void ReadPage(PageId pageId, byte[] buff) throws IOException {

        try {
            String filename;
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.contains("win")) {
                filename = DBParams.DBPath + "\\F" + pageId.getFileIdx() + ".df";
            } else {
                filename = DBParams.DBPath + "/F" + pageId.getFileIdx() + ".df";
            }
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            file.seek(pageId.getPageIdx()*DBParams.pageSize);
            file.read(buff);
            file.close();

        } catch (IOException e) {
            e.getStackTrace();
        }
    }//fin readPage

    /**
     * Ecrit le contenu de l'arg buff dans le fichier et a la position pageId indiquee
     * @param pageId
     * @param buff
     * @throws IOException
     */
    public void WritePage(PageId pageId, byte[] buff) throws IOException {

        try {
            String filename;
            String OS = System.getProperty("os.name").toLowerCase();
            if (OS.contains("win")) {
                filename = DBParams.DBPath + "\\F" + pageId.getFileIdx() + ".df";
            } else {
                filename = DBParams.DBPath + "/F" + pageId.getFileIdx() + ".df";
            }
            RandomAccessFile file = new RandomAccessFile(filename, "rw");
            //recupere la longueur du fichier file
            //chercher la position
            file.seek(pageId.getPageIdx()*DBParams.pageSize);
            //ecrit le string s au fichier file
            file.write(buff);
            file.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }//fin WritePage

    /**
     * Allocation d'une page si le fichier n'a pas atteint sa taille max
     * @return
     * @throws IOException
     */
    public PageId AllocPage() throws IOException {

        //File fff = new File(DBParams.DBPath + ".DS_Store");
        //if (fff.exists())
        //    fff.delete();
        File f = new File(DBParams.DBPath);
        File[] files = f.listFiles();
        boolean findOnePage = false;
        PageId pid = null;

        if (files.length == 0) {

            File f0 = new File(DBParams.DBPath + "F0.df");
            f0.createNewFile();
            RandomAccessFile raf = new RandomAccessFile(DBParams.DBPath + "F0.df", "rw");
            ByteBuffer buff = ByteBuffer.allocate(DBParams.pageSize);

            raf.seek(raf.length());
            raf.write(buff.array());
            raf.close();

            pid = new PageId(0, 0);

        } 
        else{

            for (int i = 0; i < files.length; i++) {

                RandomAccessFile thisFile = new RandomAccessFile(DBParams.DBPath + "F" + i + ".df", "rw");

                int countPage = (int) thisFile.length() / DBParams.pageSize;
                //System.out.println("***" + countPage + "***");

                if (countPage < 4) {

                    ByteBuffer buff = ByteBuffer.allocate(DBParams.pageSize);

                    thisFile.seek(thisFile.length());
                    thisFile.write(buff.array());
                    thisFile.close();

                    pid = new PageId(i, countPage);

                    findOnePage = true;

                    break;

                } else {

                    thisFile.close();
                }
            }

            if (!findOnePage) {

                File fn = new File(DBParams.DBPath, "F" + files.length + ".df");
                fn.createNewFile();

                RandomAccessFile raf = new RandomAccessFile(DBParams.DBPath + "F" + files.length + ".df", "rw");

                ByteBuffer buff = ByteBuffer.allocate(DBParams.pageSize);

                raf.seek(raf.length());
                raf.write(buff.array());
                raf.close();

                pid = new PageId(files.length, 0);

            } // fin if !findOnePage
        }
        return pid;
        
    } // fin allocPage
    
} // fin class