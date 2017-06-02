/**
 * Datum: 17.05.2017
 * Technische Hochschule Deggendorf
 * 
 * @author Florian Kasberger
 * @version 1.0
 */

package controller;

import java.util.Observable;
import java.util.Observer;

import model.Menue;
import model.Spielfeld;
import view.EinUndAusgabe;

public class Programm implements Observer{
	static int zeilen = 25;
	static int spalten = 120;
	private Spielfeld spielfeld;
	private EinUndAusgabe einUndAusgabe;
	private Menue menue;
	
	public Programm() {
		spielfeld = new Spielfeld(zeilen, spalten);
		spielfeld.addObserver(this);
		einUndAusgabe = new EinUndAusgabe("Moon Patrol", 16, zeilen, spalten);			
		einUndAusgabe.addObserver(this);
		menue = new Menue(zeilen, spalten);
		menue.addObserver(this);
		menue.startMenue();
		spielfeld.start();
		einUndAusgabe.printToFrame(menue.feld);
	}
	
	public static void main(String[] args) {
		new Programm();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o.equals(menue)) {
			einUndAusgabe.printToFrame(menue.feld);
			menue.setKeyEvent(einUndAusgabe.getKeyEvent());
		}else if (o.equals(spielfeld)) {
			einUndAusgabe.printToFrame(spielfeld.feld);
			spielfeld.setKeyEvent(einUndAusgabe.getKeyEvent());
		}	
	}

}
