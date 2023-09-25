package bdd;

import java.io.IOException;

public class BufferManager {
	
	/**
     * Singleton
     */
    private static BufferManager buffermanager;
    BufferManager() {
        initBufferPool();
    }

    public static BufferManager getInstance() {
        if (buffermanager == null) {
            buffermanager = new BufferManager();
        }
        return buffermanager;
    }

    public BufferManager getBufferManager(){
        return buffermanager;
    }

    //attributs
    private DiskManager dm = DiskManager.getInstance();
    private Frame[] framePool = new Frame[DBParams.frameCount];

    private int nbFreeFrame = 2; 

    private void initBufferPool()
	{
		for (int i = 0; i < framePool.length; i++)
			framePool[i] = new Frame();
	}

    //methodes

    /**
     * Fonction GetPage
     * repondre a une demande de page venant de couche plus haute
     * retourner un des buffer associes a une frame
     * @param pid
     * @return un tableau de byte (correspond au buffer)
     * @throws IOException
     */
    public byte[] GetPage(PageId pid) throws IOException {
    	

        Frame frameAdd = null;

        //Verifie si le PageID est pas deje charge dans une frame
        for(int i = 0; i < framePool.length; i++){
                if (framePool[i].getPid().equals(pid) ) {
                    return framePool[i].getBuffer();
            }
        }
    
        
        //S'il y'a des frames libres 
        if( nbFreeFrame > 0 ){
        	
            //creer une nouvelle frame
            frameAdd = new Frame();
            frameAdd.incrementPinCount(); 
            frameAdd.setPid(pid);
            
            // met le buffer 
            dm.ReadPage(pid, frameAdd.getBuffer());

           // si case 0 est libre
           	if(framePool[0].getPid().getFileIdx() == -1) {
           		framePool[0] = frameAdd;
           	}

           	else if(framePool[0].getPid().getFileIdx() != -1 && framePool[1].getPid().getFileIdx() == -1) {
           		framePool[1] = frameAdd;
           	}     
            nbFreeFrame--;
            
            return frameAdd.getBuffer();
        }
        
        //Sinon, MRU 
        else{
            int frameRemplacement = 0; 
            long timeRemplacement = framePool[0].getLastUse(); 

            for(int k = 0; k < framePool.length; k++){
                //On cherche tout les pin count = 0 
                if(framePool[k].getPin_count() == 0){

                    //On cherche le frame avec le temps le plus recent 
                    if(framePool[k].getLastUse() >= timeRemplacement){
                        frameRemplacement = k;
                        timeRemplacement = framePool[k].getLastUse();
                        
                        //si Dirty 
                        if(framePool[k].isFlag_dirty() == true){
                            dm.WritePage(framePool[k].getPid(),framePool[k].getBuffer());
                        }
                    }
                }
            }
            
            //creation nouvelle page 
            frameAdd = new Frame(); 
            frameAdd.incrementPinCount(); 
            frameAdd.setPid(pid);
            dm.ReadPage(pid, frameAdd.getBuffer());

            for(int i = 0; i < framePool.length; i++){
                if(i == frameRemplacement)
                framePool[i] = frameAdd;
                }
   
       return frameAdd.getBuffer();

        }//fin MRU


    }// fin getPage
    
    // affichage contenue du framepool
    public void afficheFramePool() {
    	System.out.println("\nContenu FramePool : ");
        for(int i = 0; i < framePool.length; i++) {
        	System.out.print("\tframePool[" + i + "] : ");
//        	System.out.print("F" + framePool[i].getPid().getFileIdx() + " ");
        	System.out.println("P" + framePool[i].getPid().getPageIdx());
        }
    }
    
    
	/**
	 *  Methode FreePage
	 *  decremente le p_count et actualiser le flagdirty de la page
	 *  potentiellement actualiser les info du MRU
	 * @param pid
	 * @param valdirty
	 * @throws IOException
	 */
    public void FreePage(PageId pid, boolean valdirty) throws IOException{

        for(int i = 0; i < DBParams.frameCount; i++) {
            if(pid.getFileIdx() == framePool[i].getPid().getFileIdx() && pid.getPageIdx() == framePool[i].getPid().getPageIdx()) {

                framePool[i].decrementPinCount();
                framePool[i].setFlag_dirty(valdirty);
                break;
            }
        }
    }
    
    
    /**
     * Methode FlushAll
     * ecriture de toutes les pages dont le flagdirty = 1
     * remise a 0 de tous les flags/info et contenu de buffer
     * @throws IOException
     */
    public void FlushAll() throws IOException {
        for(int i = 0; i < DBParams.frameCount; i++) {
            if(framePool[i].isFlag_dirty() == true){
                dm.WritePage(framePool[i].getPid(), framePool[i].getBuffer());                
            }
            framePool[i].reset();
        }
    }

    /**
     * Methode reset
     * met le bufferpool a zero
     */
    public void reset() {
		nbFreeFrame = 2;
		for(int i = 0; i< framePool.length;i++) {
			framePool[i].reset();
		}
	}

}//fin class