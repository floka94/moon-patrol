package model;

public class Geschoss extends SpielObjekt{
	protected int speed = 0;
	private char geschossChar;
	
	public Geschoss(Position position, char c) {
		super(position);
		geschossChar = c;
	}
	
	@Override
	public void bewegen() {	
		super.bewegen();
	}	

	public char getChar() {
		return geschossChar;
	}

}
