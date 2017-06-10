package model;

public class Ufo extends SpielObjekt implements Schießen {
	private String[][] asciiImages = new String[][] {
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(___<_>___)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",   
		"(___<__>__)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(__<___>__)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(__<____>_)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(_<_____>_)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(_<______>)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(<_______>)"
		},
		{
		"   .-^-.   ",
		" _/__~0_\\_ ",    
		"(_________)"
		}
		};
	private int spielfeldRandRechts;
	private int i = 0;
	private int counter = 0;
	private boolean nachRechts = true;
	
	public Ufo(Position position, int spielfeldRandRechts) {
		super(position);
		this.ausgabe = asciiImages[1];
		this.spielfeldRandRechts = spielfeldRandRechts;
	}
	
	@Override
	public String[] getAusgabe() {
		return asciiImages[1];
	}
	
	@Override
	public char[][] stringToChar() {
		counter++;
		if (counter == 5) {				//zeichnet das ufo mit langsamer geschwindigkeit der bewegten zeichen
			counter = 0;
			i++;			
		}		
		String[] s = asciiImages[i];
		char[][] c = new char[s.length][20];
		for (int i = 0; i < s.length; i++) {
			for (int z = 0; z < s[i].length(); z++) {
				c[i][z] = s[i].charAt(z);
			}
		}
		if (i == 7) {
			i = 0;
		} 
		return c;		
	}
	
	@Override
	public void bewegen() {		
		if (position.spalte < spielfeldRandRechts - 11 && nachRechts) { // fährt nicht über den linken rand
			position.spalte++;
		} else {
			nachRechts = false;
		}
		if (position.spalte > 0 && !nachRechts) { // fährt nicht über den rechten rand
			position.spalte--;												
		} else {
			nachRechts = true;
		}
	}

	@Override
	public void schießen() {
		// TODO Auto-generated method stub
		
	}
}
