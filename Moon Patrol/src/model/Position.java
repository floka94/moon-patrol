/**
 * Datum: 17.05.2017
 * Technische Hochschule Deggendorf
 * 
 * @author Florian Kasberger
 * @version 1.0
 */

package model;

public class Position {
	public int x;
	public int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) { // Erlaubt einen Positionsvergleich und gibt bei gleichheit boolschen wert zurück
		Position that = (Position) o;
		return (this.x == that.x && this.y == this.x);
	}
}
