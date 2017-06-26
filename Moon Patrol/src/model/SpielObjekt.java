/**
 * Datum: 17.05.2017
 * Technische Hochschule Deggendorf
 * 
 * @author Florian Kasberger
 * @version 1.0
 */

package model;

public class SpielObjekt implements StringToChar {
	public Position position;
	protected String[] ausgabe;
	
	public SpielObjekt(Position position) {
		this.position = position;		
	}
	
	public void bewegen() {	
		
	}
	
	public String[] getAusgabe() {
		String[] s = new String[0];
		return s;
	}

	@Override
	public char[][] stringToChar() {	
		char[][] c = new char[ausgabe.length][400];
		for (int i = 0; i < ausgabe.length; i++) {
			for (int z = 0; z < ausgabe[i].length(); z++) {
				c[i][z] = ausgabe[i].charAt(z);
			}
		}
		return c;
	}

}
