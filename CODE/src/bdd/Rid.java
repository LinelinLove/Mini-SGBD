package bdd;

public class Rid{

    // attribut
    private PageId pid; 
    private int slotIdx; 

    // constructeur
    public Rid(PageId pid, int slotIdx){
        this.pid = pid; 
        this.slotIdx= slotIdx;
    }

    // getters et setters
    public PageId getPid() {
        return this.pid;
    }

    public void setPid(PageId pid) {
        this.pid = pid;
    }

    public int getSlotIdx() {
        return this.slotIdx;
    }

    public void setSlotIdx(int slotIdx) {
        this.slotIdx = slotIdx;
    }
    
    @Override
    public String toString(){
        return "("+ this.pid + "," + this.slotIdx + ")";
    }

}