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
			"\\__/ \\_\\/  \\/_/"
			};
	private String[] asciiImage2 = new String[] {
			" _________",
			"/######\\__\\___",
			"\\#############\\",
			"/\\ \\ / /\\  /__\\",
			"\\_\\/ \\/_/  \\__/"
			};
	private String[] asciiImage3 = new String[] {
			" _________",
			"/######\\__\\___",
			"\\#############\\",
			"/ /\\ /__\\  /\\ \\",
			"\\/_/ \\__/  \\_\\/"
			};	
	private int ausgabeAuswahl = 1;		         // für switch case anweisung
	private int spielfeldRandRechts;	
	private int springen = 0;			        //  Hilfsvariable für switch case racer springt
	private int springGeschwindigkeit = 4;      //  wie schnell soll racer springen -> einstellbar
	private int sprungHoeheAktuell = 0;			// Hilfsvariable für methode racerSpringt()
	private int sprungHoeheSoll = 7;		    // Sprunghoehe -> einstellbar
	private boolean sollSchießen = false;
	private boolean darfSchießen = true;	
	public ArrayList<Geschoss> geschosse = new ArrayList<Geschoss>();
	private char geschossChar = '|';
	
	public MoonRacer(Position position, int spielfeldRandRechts) {
		super(position);	
		this.spielfeldRandRechts = spielfeldRandRechts;
		this.ausgabe = asciiImage1;
	}
	
	private void racerSpringt() {            // racer springt 
		if (sprungHoeheAktuell > -1 && sprungHoeheAktuell < sprungHoeheSoll) {
			position.x = position.x - 1;
			sprungHoeheAktuell++;
		} else if (sprungHoeheAktuell > sprungHoeheSoll - 1 && sprungHoeheAktuell < sprungHoeheSoll * 2) {
			position.x = position.x + 1;
			sprungHoeheAktuell++;
		} else if (sprungHoeheAktuell == sprungHoeheSoll * 2) {
			sprungHoeheAktuell = 0;
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
				if (position.y > 0) {						// fährt nicht über den linken rand
					position.y = position.y - 1;
				}				
				break;
			case KeyEvent.VK_RIGHT:
				if (position.y < spielfeldRandRechts - 15)			// fährt nicht über den rechten rand
				position.y = position.y + 1;
				break;
			case KeyEvent.VK_SPACE:		
				if (darfSchießen) {
					sollSchießen = true;	
				}																
			default:
				break;
			}
		}
		if (springen == springGeschwindigkeit) {			// racer springt mit geringerer geschwindigkeit
			springen = 1;			
		} else if (springen > 1 && springen < springGeschwindigkeit) {
			springen++;
		} else if (springen == 1) {
			racerSpringt();
			springen++;
		}
		keyEvent = null;
	}	
	
	@Override
	public char[][] stringToChar() {
		String[] s = asciiImage1;
		switch (ausgabeAuswahl) {		// erzeugt ein bewegtes bild der reifen
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
		for (int x = 0; x < s.length; x++) {
			for (int y = 0; y < s[x].length(); y++) {
				c[x][y] = s[x].charAt(y);
			}
		}
		return c;
	}
	
	@Override
	public void schießen() {					//wird immer aufgerufen, aber nur wenn Space gedrückt wird, wird ein neues Geschoss erzeugt!
		int abstandGeschosse = 2;
		int speedGeschosse = 3;							// umso kleiner der wert umso schneller die Geschosse
		if (sollSchießen) {						// Geschoss wird erzeugt mit der aktuellen Position des Racers
			geschosse.add(new Geschoss(new Position(this.position.x + 1, this.position.y + 5), geschossChar));	
			sollSchießen = false;
			darfSchießen = false;
		}
		if (!geschosse.isEmpty()) {
			if (geschosse.get(geschosse.size() - 1).position.x < this.position.x - abstandGeschosse) { 	// geschosse kommen mit gewissen abstand
				darfSchießen = true;   			     // Spieler darf erst schießen wenn letztes geschoss einen gewissen abstand zum auto besitzt
			}
			for (Geschoss g : geschosse) {   	
				if (g.speed == 0) {				// Regelung der Geschwindigkeit der Geschosse 
					g.position.x--;
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
