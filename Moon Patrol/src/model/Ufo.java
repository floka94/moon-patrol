package model;

import java.util.ArrayList;
import java.util.Random;

public class Ufo extends SpielObjekt implements Schieﬂen {
	private String[][] asciiImages = new String[][] {
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(___<_>___)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",   
		"(___<__>__)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(__<___>__)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(__<____>_)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(_<_____>_)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(_<______>)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(<_______>)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(_________)"
		}
		};
	private int spielfeldRandRechts;
	private int i = 0;
	private int counter = 0;
	private boolean nachRechts = true;
	public ArrayList<Geschoss> geschosse = new ArrayList<Geschoss>();
	private char geschossChar = 'U';
	private boolean sollSchieﬂen = false;
	private boolean darfSchieﬂen = true;
	
	public Ufo(Position position, int spielfeldRandRechts) {
		super(position);
		this.ausgabe = asciiImages[1];
		this.spielfeldRandRechts = spielfeldRandRechts;
	}
	
	@Override
	public String[] getAusgabe() {
		return asciiImages[1];
	}
	
	@Override
	public char[][] stringToChar() {
		counter++;
		if (counter == 5) {				//zeichnet das ufo mit langsamer geschwindigkeit der bewegten zeichen
			counter = 0;
			i++;			
		}		
		String[] s = asciiImages[i];
		char[][] c = new char[s.length][20];
		for (int x = 0; x < s.length; x++) {
			for (int y = 0; y < s[x].length(); y++) {
				c[x][y] = s[x].charAt(y);
			}
		}
		if (i == 7) {
			i = 0;
		} 
		return c;		
	}
	
	@Override
	public void bewegen() {		
		if (position.y < spielfeldRandRechts - 11 && nachRechts) { // f‰hrt nicht ¸ber den linken rand
			position.y++;
		} else {
			nachRechts = false;
		}
		if (position.y > 0 && !nachRechts) { // f‰hrt nicht ¸ber den rechten rand
			position.y--;												
		} else {
			nachRechts = true;
		}
	}
	
	@Override
	public void schieﬂen() {
		int geschosseWahrscheinlichkeit = 40;
		int abstandGeschosse = 8;
		int speedGeschosse = 7;	
		Random rand = new Random();
		if (rand.nextInt(geschosseWahrscheinlichkeit) == 1) {
			sollSchieﬂen = true;			
		}
		if (sollSchieﬂen && darfSchieﬂen) {						// Geschoss wird erzeugt mit der aktuellen Position des Racers
			geschosse.add(new Geschoss(new Position(this.position.x + 2, this.position.y + 6), geschossChar));	
			sollSchieﬂen = false;
			darfSchieﬂen = false;
		}
		/* Geschosse kommen mit gewissen Abstand, und Ufo darf erst wieder schieﬂen
		 * wenn weniger als 3 geschosse auf dem Spielfeld sind und der Abstand zwischen den
		 * Geschossen passt
		 */
		if (!geschosse.isEmpty()) {
			if (geschosse.get(geschosse.size() - 1).position.x > this.position.x - abstandGeschosse && geschosse.size() < 3) { 	
				darfSchieﬂen = true;   			     
			}
			for (Geschoss g : geschosse) {   	
				if (g.speed == 0) {				// Regelung der Geschwindigkeit der Geschosse 
					g.position.x++;
					g.speed++;
				} else if (g.speed == speedGeschosse) {
					g.speed = 0;
				} else {
					g.speed++;
				}
			}			
		}
	}	
}
