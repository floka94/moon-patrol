/**
 * Datum: 17.05.2017
 * Technische Hochschule Deggendorf
 * 
 * @author Florian Kasberger
 * @version 1.0
 */

package model;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class Spielfeld extends Observable {
	public char[][] feld;
	private SpielObjekt[] spielObjekte;
	private Berge berge;
	private Duene duene;
	private MoonRacer racer;
	private Boden boden;
	private Ufo ufo;
	
	public Spielfeld(int zeilen, int spalten) {
		berge = new Berge(new Position(5, 0));
		duene = new Duene(new Position(8, 0));
		boden = new Boden(new Position(15, 0));
		feld = new char[zeilen][spalten];
		spielObjekte = new SpielObjekt[2];
		racer = new MoonRacer(new Position(18, 0), spalten);				//positioniert racer 
		ufo = new Ufo(new Position(1, 3));									//positioniert ufo
		spielObjekte[0] = racer;
		spielObjekte[1] = ufo;
	}
	
	public void initSpielfeld() {						//spielfeld mit leerzeichen initialisieren
		for (int z = 0; z < feld.length; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				feld[z][s] = ' ';			
			}
		}
	}
	
	public void initLandschaft() {					// spielfeld mit der landschaft initialisieren: berge, duene und boden ...
		char[][] c = berge.stringToChar();
		for (int z = 0; z < c.length; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				feld[z+4][s] = c[z][s];				
			}
		}
		c = duene.stringToChar();
		for (int z = 0; z < c.length; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				if (c[z][s] != '*') {  			// zeichnet die sternchen von der d�ne nicht!
					feld[z+8][s] = c[z][s];	
				}
			}
		}		
		c = boden.stringToChar();		// zeichnet den boden
		for (int z = 0; z < c.length; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				feld[z+23][s] = c[z][s];				
			}
		}
	}
	
	public void setKeyEvent(KeyEvent e) {
		racer.keyEvent = e;
	}
	
	public void start() {		
		while (true) {
			initSpielfeld();			
			initLandschaft();			
			duene.bewegen();
			berge.bewegen();
			for (int i = 0; i < spielObjekte.length; i++) {					// zeichnet alle Figuren im array spielfiguren[] ins char[][] array deshalb drei for schleifen
				spielObjekte[i].bewegen();									// jedes spielobjekt bewegen			
				char[][] c = spielObjekte[i].stringToChar();
				for (int z = 0; z < spielObjekte[i].stringToChar().length; z++) {
					for (int s = 0; s < spielObjekte[i].getAusgabe()[z].length(); s++) {
						feld[spielObjekte[i].position.zeile + z][spielObjekte[i].position.spalte + s] = c[z][s];
					}
				}
//				if (spielObjekte[i] instanceof Schie�en) {
//					
//				}
				racer.schie�en();
				if (!racer.geschosse.isEmpty()) {
					for (Geschoss g : racer.geschosse) {
						feld[g.position.zeile][g.position.spalte] = g.getChar();
						if (g.position.zeile == 0) {
							racer.geschosse.remove(g);
							break;
						}
					}
				}
			}
			setChanged();
			notifyObservers();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}
}
