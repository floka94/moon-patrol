package model;

import java.awt.event.KeyEvent;
import java.util.Observable;

public class Menue extends Observable {
	private TitelBild titelBild;
	private MoonRacer racer;
	private String start = "Press [ENTER] to start the game";
	protected KeyEvent keyEvent;
	public char[][] feld;
	
	public Menue(int zeilen, int spalten) {
		feld = new char[zeilen][spalten];
		titelBild = new TitelBild(new Position(3, spalten/2 - 37));		
		racer = new MoonRacer(new Position(18, 0), spalten);				//positioniert racer 
	}
	
	private void initFeld() {						
		for (int z = 0; z < feld.length; z++) {
			for (int s = 0; s < feld[0].length; s++) {
				feld[z][s] = ' ';			
			}
		}
	}
	
	private void initMenue() { // menue zeichnen
		char[][] c = titelBild.stringToChar();
		for (int z = 0; z < c.length; z++) {
			for (int s = 0; s < titelBild.getAusgabe()[z].length(); s++) {
				feld[titelBild.position.zeile + z][titelBild.position.spalte + s] = c[z][s];
			}
		}
	}
	
	private void initRacer(int count) {	
		while (count < (feld[0].length / 2 - 6)) {
			initFeld();
			initMenue();
			char[][] c = racer.stringToChar();
			for (int z = 0; z < c.length; z++) {
				for (int s = 0; s < racer.getAusgabe()[z].length(); s++) {
					feld[racer.position.zeile + z][racer.position.spalte + s] = c[z][s];
				}
			}
			count++;
			racer.position.spalte++;
			setChanged();
			notifyObservers();
			if (racer.position.spalte > feld[0].length - 15) {
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}		
	}
	
	public void setKeyEvent(KeyEvent e) {
		this.keyEvent = e;
	}
	
	public void startMenue() {
		boolean bool = true;
		int x = 1;
		int count = 0;
		String s = start;
		initRacer(count);
		while (bool) {
			if (keyEvent != null) {				
				if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					bool = false;
				}
			}
			switch (x) {
			case 1: 
				s = start;
				x = 0;
				break;
			case 0:			
				s = start.replaceAll(".", " ");
				x = 1;
				break;
			default:
				break;				
			}
			for (int i = 0; i < start.length(); i++) {
				feld[13][feld[0].length / 2 - start.length() / 2 + i] = s.charAt(i);
			}	
			setChanged();
			notifyObservers();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
		}
		initRacer(count);
	}		
}
