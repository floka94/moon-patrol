package model;

public class Geschoss extends SpielObjekt{
	
	public Geschoss(Position position) {
		super(position);
	}
	
	@Override
	public void bewegen() {
		// TODO Auto-generated method stub
		super.bewegen();
	}	

	public char getChar() {
		return '|';
	}

}
