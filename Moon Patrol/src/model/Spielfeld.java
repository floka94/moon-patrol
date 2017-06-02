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
	
	public Spielfeld(int zeilen, int spalten) {
		berge = new Berge(new Position(5, 0));
		duene = new Duene(new Position(8, 0));
		boden = new Boden(new Position(15, 0));
		feld = new char[zeilen][spalten];
		spielObjekte = new SpielObjekt[1];
		racer = new MoonRacer(new Position(17, 0), spalten);				//positioniert racer 
		spielObjekte[0] = racer;
	}
	
	public void initSpielfeld() {						//spielfeld mit leerzeichen initialisieren
		for (int z = 0; z < feld.length; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				feld[z][s] = ' ';			
			}
		}
	}
	
	public void initLandschaft() {					// spielfeld mit der landschaft initialisieren: berge, d�ne ...
		char[][] c = berge.stringToChar();
		for (int z = 0; z < 10; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				feld[z+3][s] = c[z][s];				
			}
		}
		c = duene.stringToChar();
		for (int z = 0; z < 15; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				if (c[z][s] != '*') {  			// zeichnet die sternchen von der d�ne nicht!
					feld[z+7][s] = c[z][s];	
				}
			}
		}
		
//		c = boden.stringToChar();
//		for (int z = 0; z < 10; z++) {
//			for (int s = 0; s < feld[0].length; s++) {
//				feld[z+3][s] = c[z][s];				
//			}
//		}
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
				spielObjekte[i].bewegen();									// jede spielfigur bewegen				
				char[][] c = spielObjekte[i].stringToChar();
				for (int z = 0; z < spielObjekte[i].stringToChar().length; z++) {
					for (int s = 0; s < spielObjekte[i].getAusgabe()[z].length(); s++) {
						feld[spielObjekte[i].position.zeile + z][spielObjekte[i].position.spalte + s] = c[z][s];
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
