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
import java.util.Observable;
import java.util.Random;

public class Spielfeld extends Observable {
	public char[][] feld;
	private SpielObjekt[] spielObjekte;
	private Berge berge;
	private Duene duene;
	private MoonRacer racer;
	private Boden boden;	
	private Ufo ufo;	
	private ArrayList<Hindernis> hindernisse = new ArrayList<Hindernis>();
	/* schwierigkeitHindernisse: Stellt ein, mit welchem Abstand zueinander Hindernisse auftreten können; 
	 * Zahl muss größer als eins sein; Umso größer die Zahl umso, schwieriger wird das Spiel	 
	 */
	private double schwierigkeitHindernisse = 1.2;
	/* wahrscheinlichkeitHindernisse: Stellt ein, mit welcher Wahrscheinlichkeit Hindernisse auftreten können;
	 * Zahl muss größer als eins sein; Umso kleiner die Zahl, umso schwieriger wird das Spiel
	 * 
	 */
	private int wahrscheinlichkeitHindernisse = 5; 
	
	public Spielfeld(int x, int y) {
		berge = new Berge(new Position(5, 0));
		duene = new Duene(new Position(8, 0));
		boden = new Boden(new Position(0, 0));    
		feld = new char[x][y];
		spielObjekte = new SpielObjekt[2];
		racer = new MoonRacer(new Position(x - 7, 0), y);				//positioniert racer 
		ufo = new Ufo(new Position(1, 3), y);							//positioniert ufo
		spielObjekte[0] = racer;
		spielObjekte[1] = ufo;
	}
	
	private void detectCollisions() { // Kollision mit Geschossen oder Hindernissen werden überprüft		
		for (Geschoss g : racer.geschosse) {
			if (detectUfoTreffer(ufo, g)) {			
				spielObjekte = new SpielObjekt[] { racer };
			}
		}
		for (Geschoss g : ufo.geschosse) {
			if (detectRacerTreffer(g)) {
				spielObjekte = new SpielObjekt[] { ufo };
			}
		}		
		for (Hindernis h : hindernisse) {
			if (detectCollisionHindernis(h)) {
				spielObjekte = new SpielObjekt[] { ufo };
			}
		}
	}
	
	private boolean detectUfoTreffer(Ufo ufo, Geschoss g) {       // Gibt true zurück, wenn ein Geschoss ein Ufo getroffen hat	
		int x = ufo.ausgabe.length;
		int y = ufo.ausgabe[0].length();
		if (g.position.x <= ufo.position.x + x && g.position.y >= ufo.position.y && g.position.y <= ufo.position.y + y) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean detectRacerTreffer(Geschoss g) {  // Gibt true zurück, wenn ein Geschoss den Racer getroffen hat
		MoonRacer r = racer;
		int y = racer.ausgabe[4].length();
		if (g.position.x >= r.position.x && g.position.y >= r.position.y && g.position.y <= r.position.y + y) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean detectCollisionHindernis(Hindernis h) {	// Ermittelt ob der Racer mit einem Hinderniss kollidiert ist
		MoonRacer r = racer;
		char[][] test = h.stringToChar();
		int hY = test[0].length;
		int rX = racer.ausgabe.length;
		int rY = racer.ausgabe[4].length();
		if (r.position.y + rY >= h.position.y && r.position.y < h.position.y + hY - 2 && r.position.x + rX > h.position.x) {
			return true;
		}
		return false;
	}
	
	private void initSpielfeld() {						//spielfeld mit leerzeichen initialisieren
		for (int x = 0; x < feld.length; x++) {
			for (int y = 0; y < feld[0].length; y++) {
				feld[x][y] = ' ';			
			}
		}
	}
	
	private void initLandschaft() {					// Spielfeld mit der Landschaft initialisieren: Berge, Duene, Boden, Hindernisse
		char[][] c = berge.stringToChar();
		for (int x = 0; x < c.length; x++) {
			for (int y = 0; y < feld[0].length; y++) {
				feld[x+4][y] = c[x][y];				
			}
		}
		c = duene.stringToChar();
		for (int x = 0; x < c.length; x++) {
			for (int y = 0; y < feld[0].length; y++) {
				if (c[x][y] != '*') {  						// zeichnet die sternchen von der Duene nicht!
					feld[x+8][y] = c[x][y];	
				}
			}
		}		
		c = boden.stringToChar();						// zeichnet den Boden
		for (int x = 0; x < c.length; x++) {
			for (int y = 0; y < feld[0].length; y++) {
				feld[feld.length - c.length + x][y] = c[x][y];				
			}
		}
		
		if (!hindernisse.isEmpty()) {	
			for (Hindernis hindernis : hindernisse) {
				hindernis.bewegen();	
				int yGrenze = feld[0].length - hindernis.position.y - 1;
				c = hindernis.stringToChar();
				if (yGrenze < c[0].length) {    							 // Hindernis wird vom rechts kommend gezeichnet
					yGrenze++;
				} else if (yGrenze > c[0].length) {   						 // Sobald das Hindernis am linken Rand ankommt, wird immer weniger davon gezeichnet; c[0].length wird 
					yGrenze = c[0].length;									// durch die Methoden bewegen() immer kleiner
				}
				for (int x = 0; x < c.length; x++) {
					for (int y = 0; y < yGrenze; y++) {
						feld[hindernis.position.x + x][hindernis.position.y + y] = c[x][y];				
					}
				}				
			}
		}
	}
	
	private void hindernisseLoeschen() {				// Hindernisse die am linken Rand vorbei sind, werden wieder gelöscht	
		if (!hindernisse.isEmpty()) {
			for (Hindernis hindernis : hindernisse) {				
				if (hindernis.hindernisLöschen) {
					hindernisse.remove(hindernis);
					break;
				}
			}
		}
	}
	
	private void geschosseZeichnen() {
		if (!racer.geschosse.isEmpty()) {
			for (Geschoss g : racer.geschosse) {
				feld[g.position.x][g.position.y] = g.getChar();  // zeichne die geschosse ins feld
				if (g.position.x == 0) {					// alle geschosse die den oberen rand erreichen werden gelöscht
					racer.geschosse.remove(g);
					break;
				}
			}				
		}
		if (!ufo.geschosse.isEmpty()) {
			for (Geschoss g : ufo.geschosse) {
				feld[g.position.x][g.position.y] = g.getChar();     // zeichne die geschosse ins feld
				if (g.position.x == feld.length - boden.ausgabe.length) { // alle Geschosse die den Boden erreichen werden gelöscht
					ufo.geschosse.remove(g);
					break;
				}	
			}
		}
		
	}
	
	private void hindernisseZufallErzeugen() {     // Es werden nach Zufallsprinzip Hindernisse in den Weg gelegt
		Random rand = new Random();
		int zufallszahl = rand.nextInt(wahrscheinlichkeitHindernisse);
		if (hindernisse.isEmpty()) {
			Hindernis h = new Hindernis(new Position(0, 0));
			h.position.x = feld.length-boden.ausgabe.length - h.ausgabe.length +1;
			h.position.y = feld[0].length;
			hindernisse.add(h);			
		} else if (zufallszahl == 1 && hindernisse.get(hindernisse.size() - 1).position.y < feld[0].length - feld[0].length / schwierigkeitHindernisse) {			// maximal 3 Hindernisse auf dem Spielfeld
			//hindernisse.add(new Hindernis(new Position(feld.length-boden.ausgabe.length - 2, feld[0].length)));
			Hindernis h = new Hindernis(new Position(0, 0));
			h.position.x = feld.length-boden.ausgabe.length - h.ausgabe.length + 1;
			h.position.y = feld[0].length;
			hindernisse.add(h);
		}
	}
	
	public void setKeyEvent(KeyEvent e) {
		racer.keyEvent = e;
	}
	
	public void start() {		
		while (true) {			
			hindernisseLoeschen();
			hindernisseZufallErzeugen();
			initSpielfeld();			
			initLandschaft();			
			duene.bewegen();
			berge.bewegen();		
			for (SpielObjekt sp : spielObjekte) {				// zeichnet alle Figuren im array spielfiguren[] ins char[][] array deshalb drei for schleifen
				sp.bewegen();									// jedes spielobjekt bewegen			
				char[][] c = sp.stringToChar();
				for (int x = 0; x < sp.stringToChar().length; x++) {
					for (int y = 0; y < sp.getAusgabe()[x].length(); y++) {
						feld[sp.position.x + x][sp.position.y + y] = c[x][y];
					}
				}
			}
			racer.schießen();
			ufo.schießen();
			geschosseZeichnen();
			detectCollisions();
			setChanged();
			notifyObservers();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}
}
