package bdd;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Record {

	// attributs
	private RelationInfo relInfo;
	private ArrayList<String> values;
	
	// constructeurs
	public Record(RelationInfo relInfo, ArrayList<String> values) {
		this.relInfo = relInfo;
		this.values = values;
	}
	
	public Record(RelationInfo relInfo) {
		this.relInfo = relInfo;
		values = new ArrayList<String>();
	}
	
	// getters et setters
	public RelationInfo getRelInfo() {
		return relInfo;
	}

	public void setRelInfo(RelationInfo relInfo) {
		this.relInfo = relInfo;
	}
	
	/**
	 * cette methode ecrit les valeurs du record dans le buffer, 
	 * l'une apres l'autre, a partir de la position
	 * @param buff
	 * @param position
	 */
	public void writeToBuffer(ByteBuffer buff, int position) {

		buff.position(position); // position du buffer
		
		for(int i = 0; i < relInfo.getNbColonnes(); i++) {
			if(relInfo.getTypeColonnes()[i].equals("int")) {
				buff.putInt(Integer.valueOf(values.get(i)));
				//buff.putInt(Integer.parseInt(values.get(i)));
			
			}
			else if(relInfo.getTypeColonnes()[i].equals("char")) {
				buff.putChar(values.get(i).charAt(0));
				
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("byte")) {
				buff.put(Byte.parseByte(values.get(i)));
				
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("short")) {
				buff.putShort(Short.parseShort(values.get(i)));
				
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("float")) {
				buff.putFloat(Float.valueOf(values.get(i)));
				
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("double")) {
				buff.putDouble(Double.parseDouble(values.get(i)));
				
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("long")) {
				buff.putLong(Long.parseLong(values.get(i)));
				
				
			}
			else if(relInfo.getTypeColonnes()[i].startsWith("string")) {
				int taille = Integer.parseInt(relInfo.getTypeColonnes()[i].substring(6));
//				char[] lettres = new char[values.get(i).length()];
				for(int j = 0; j < taille; j++) {
//					lettres[j] = values.get(i).charAt(j);
					buff.putChar(values.get(i).charAt(j));
				}
				
			}
			
		}
	} // fin write
	

	/**
	 * cette methode devra lire les valeurs du record depuis le buffer,
	 * l'un apres l'autre, a partir de la position
	 * @param buff
	 * @param position
	 */
	public void readFromBuffer(ByteBuffer buff, int position) {
		buff.position(position);
		for(int i = 0; i < relInfo.getNbColonnes(); i++) {
			if(relInfo.getTypeColonnes()[i].equals("int")) {
				int b = buff.getInt();
				this.values.add(String.valueOf(b));
			}
			
			else if(relInfo.getTypeColonnes()[i].equals("char")) {
				char c = buff.getChar();
				this.values.add(String.valueOf(c));
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("byte")) {
				byte bit = buff.get();
				this.values.add(String.valueOf(bit));
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("short")) {
				short s = buff.getShort();
				this.values.add(String.valueOf(s));
				

			}
			else if(relInfo.getTypeColonnes()[i].equals("float")) {
				float f = buff.getFloat();
				this.values.add(String.valueOf(f));
				
			}
			
			else if(relInfo.getTypeColonnes()[i].equals("double")) {
				double d = buff.getDouble();
				this.values.add(String.valueOf(d));
				
			}
			else if(relInfo.getTypeColonnes()[i].equals("long")) {
				long l = buff.getLong();
				this.values.add(String.valueOf(l));
				
			}
			else if(relInfo.getTypeColonnes()[i].startsWith("string")) {

				int taille = Integer.parseInt(relInfo.getTypeColonnes()[i].substring(6));
				String s = "";
				for(int j = 0; j <taille; j++) {
					s += buff.getChar();
				}
				this.values.add(s);
				
			}
			
		} // fin for

	} // fin read
	
	
	public String valuesToString() {
		
		StringBuilder valeurs = new StringBuilder("(");
		
		for(int i = 0; i < values.size();i++) {
			// si dernier element on affiche pas le " ; "
			if(i == (values.size() - 1) )
				valeurs.append(values.get(i));
			else
				valeurs.append(values.get(i) + " ; ");
		}
		
		valeurs.append(").");
		return valeurs.toString();
	}
	

	public ArrayList<String> getValues() {
		return values;
	}

	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	@Override
    public String toString() {
        return valuesToString();
    }

} // fin class Record