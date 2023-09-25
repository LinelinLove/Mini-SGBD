package bdd;

import java.io.Serializable;
import java.util.Objects;

public class RelationInfo implements Serializable {

    // attributs
    private static final long serialVersionUID = 1L;
    private String nomRelation;
    private int nbColonnes;
    private String[] nomColonnes;
    private String[] typeColonnes;
    private PageId headerPageId; 
    private int recordSize; 
    private int slotCount;

    // constructeur
    public RelationInfo(String nomRelation, int nbColonnes, String[] nomColonnes, String[] typeColonnes, PageId headerPageId) {
        super();
        this.nomRelation = nomRelation;
        this.nbColonnes = nbColonnes;
        this.nomColonnes = nomColonnes;
        this.typeColonnes = typeColonnes;
        this.headerPageId = headerPageId;
        this.recordSize = calculRecordSize();
        this.slotCount = calculSlotcount();
    }

    // methodes
    public int calculRecordSize(){
        recordSize = 0;
        for(String type: this.typeColonnes){
            if(type.equals("int")){
                recordSize += 4;
            }
            if(type.equals("float")){
                recordSize += 4;
            }
            if(type.startsWith("string")){
                // pour le type commencant par string
                // on recupere la taille du string avec un split
                int taille = Integer.parseInt(type.split("string")[1]);
//                System.out.println("La taille du string est de:"+ taille);
                recordSize += (taille * 2);
            }
        }
        return this.recordSize;
    }

    public int calculSlotcount(){

        // nbCases = (taillePage - taille) / (tailleRecord + 1) ( 1 étant le bitmap)

        // 2 PagesId = 2*2 * 4 = 16 octets 
        // Recordsize + 1 (bitmap)
        // Slot count = Pagesize - 16 / Recordsize +1

        //slotCount = (DBParams.pageSize - 16)/(getRecordSize() + 4);
        slotCount = (DBParams.pageSize - 16)/(getRecordSize() +1);
        return slotCount;

    }

    @Override
    public String toString() {
        return "Relation : Nom = " + getNomRelation() + ", nbColonnes = " + nbColonnes + ", nomColonnes = " + getNomCol() + ", typeColonnes = " + getTypeCol();
    }

    // getters et setters
    public String getNomRelation() {
        return nomRelation;
    }

    public void setNomRelation(String nomRelation) {
        this.nomRelation = nomRelation;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public void setNbColonnes(int nbColonnes) {
        this.nbColonnes = nbColonnes;
    }

    public String[] getNomColonnes() {
        return nomColonnes;
    }

    public void setNomColonnes(String[] nomColonnes) {
        this.nomColonnes = nomColonnes;
    }

    public String[] getTypeColonnes() {
        return typeColonnes;
    }

    public void setTypeColonnes(String[] typeColonnes) {
        this.typeColonnes = typeColonnes;
    }


    public String getNomCol(){
		
		StringBuilder builder = new StringBuilder("( ");
		
		for(int i = 0; i < nbColonnes;i++) {
			// si dernier element on affiche pas le " ; "
			if(i == (nbColonnes - 1) )
            builder.append(nomColonnes[i]);
			else
            builder.append(nomColonnes[i] + " ; ");
		}
		
		builder.append(" )");
		return builder.toString();
    }
    

    public String getTypeCol(){
		
		StringBuilder builder = new StringBuilder("( ");
		
		for(int i = 0; i < nbColonnes ;i++) {
			// si dernier element on affiche pas le " ; "
			if(i == (nbColonnes - 1) )
            builder.append(typeColonnes[i]);
			else
            builder.append(typeColonnes[i] + " ; ");
		}
		
		builder.append(" )");
		return builder.toString();
    }
    
    public PageId getHeaderPageId() {
        return this.headerPageId;
    }

    public void setHeaderPageId(PageId headerPageId) {
        this.headerPageId = headerPageId;
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    public int getSlotCount() {
        return this.slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }


}