/**
 * Datum: 17.05.2017
 * Technische Hochschule Deggendorf
 * 
 * @author Florian Kasberger
 * @version 1.0
 */

package model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MoonRacer extends SpielObjekt implements Schießen {	
	protected KeyEvent keyEvent;
	private String[] asciiImage1 = new String[] {
			" _________",
			"/######\\__\\___",
			"\\#############\\",	
			"/__\\ /\\ \\  / /\\",
			"\\__/ \\_\\/  \\/_/"};
	private String[] asciiImage2 = new String[] {
			" _________",
			"/######\\__\\___",
			"\\#############\\",
			"/\\ \\ / /\\  /__\\",
			"\\_\\/ \\/_/  \\__/"};
	private String[] asciiImage3 = new String[] {
			" _________",
			"/######\\__\\___",
			"\\#############\\",
			"/ /\\ /__\\  /\\ \\",
			"\\/_/ \\__/  \\_\\/"};	
	private int ausgabeAuswahl = 1;		// für switch case anweisung
	private int spielfeldRandRechts;	
	private int springen = 0;			// für switch case racer springt
	private int x = 0;					// für methode racerSpringt()
	private boolean sollSchießen = false;
	private boolean darfSchießen = true;	
	public ArrayList<Geschoss> geschosse = new ArrayList<Geschoss>();
	
	public MoonRacer(Position position, int spielfeldRandRechts) {
		super(position);	
		this.spielfeldRandRechts = spielfeldRandRechts;
		this.ausgabe = asciiImage1;
	}
	
	private void racerSpringt() { // racer springt 
		if (x > -1 && x < 10) {
			position.zeile = position.zeile - 1;
			x++;
		} else if (x > 9 && x < 20) {
			position.zeile = position.zeile + 1;
			x++;
		} else if (x == 20) {
			x = 0;
			springen = -1;
		}
	}
	
	@Override
	public String[] getAusgabe() {
		return asciiImage1;
	}
	
	@Override
	public void bewegen() {
		if (keyEvent != null) {				
			switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_UP:						// racer soll springen	
				springen = 1;
				break;
			case KeyEvent.VK_LEFT:
				if (position.spalte > 0) {						// fährt nicht über den linken rand
					position.spalte = position.spalte - 1;
				}				
				break;
			case KeyEvent.VK_RIGHT:
				if (position.spalte < spielfeldRandRechts - 15)			// fährt nicht über den rechten rand
				position.spalte = position.spalte + 1;
				break;
			case KeyEvent.VK_SPACE:		
				if (darfSchießen) {
					sollSchießen = true;	
				}																
			default:
				break;
			}
		}
		switch(springen) {	// racer springt 1/2 der geschwindigkeit
		case 1:
			racerSpringt();
			springen++;
			break;
		case 2:
			springen = 1;
			break;
		default:
			break;
		}
		keyEvent = null;
	}	
	
	@Override
	public char[][] stringToChar() {
		String[] s = asciiImage1;
		switch (ausgabeAuswahl) {		//erzeugt ein bewegtes bild der reifen
		case 1:	
			ausgabeAuswahl++;
			break;
		case 2:
			s = asciiImage2;
			ausgabeAuswahl++;
			break;
		case 3:
			s = asciiImage3;
			ausgabeAuswahl = 1;
			break;
		default:
			break;
		}
		char[][] c = new char[s.length][20];
		for (int i = 0; i < s.length; i++) {
			for (int z = 0; z < s[i].length(); z++) {
				c[i][z] = s[i].charAt(z);
			}
		}
		return c;
	}
	
	@Override
	public void schießen() {					//wird immer aufgerufen aber nur wenn space gedrückt wird, wird ein neues
		int abstandGeschosse = 2;
		int speedGeschosse = 3;							// umso kleiner der wert umso schneller
		if (sollSchießen) {						// geschoss erzeugt mit der aktuellen Position des Racers
			geschosse.add(new Geschoss(new Position(this.position.zeile + 1, this.position.spalte + 5)));	
			sollSchießen = false;
			darfSchießen = false;
		}
		if (!geschosse.isEmpty()) {
			if (geschosse.get(geschosse.size() - 1).position.zeile < this.position.zeile - abstandGeschosse) { 	// geschosse kommen mit gewissen abstand
				darfSchießen = true;   			// darf erst schießen wenn letztes geschoss einen gewissen abstand zum auto besitzt
			}
			for (Geschoss g : geschosse) {   	
				if (g.speed == 0) {				// Geschwindigkeit der Geschosse
					g.position.zeile--;
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
