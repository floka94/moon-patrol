package model;

public class Geschoss extends SpielObjekt{
	protected int speed = 0;
	
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
