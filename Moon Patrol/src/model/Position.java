/**
 * Datum: 17.05.2017
 * Technische Hochschule Deggendorf
 * 
 * @author Florian Kasberger
 * @version 1.0
 */

package model;

public class Position {
	public int zeile;
	public int spalte;

	public Position(int zeile, int spalte) {
		this.zeile = zeile;
		this.spalte = spalte;
	}

	@Override
	public boolean equals(Object o) { // Erlaubt einen Positionsvergleich und gibt bei gleichheit boolschen wert zurück
		Position that = (Position) o;
		return (this.zeile == that.zeile && this.spalte == this.zeile);
	}
}
