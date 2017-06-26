package model;

import java.util.Random;

public class Hindernis extends SpielObjekt {
	private int asciiAuswahl;
	private int yStart = 0;
	private String[][] asciiImages = new String[][] {		//Hindernisse müssen eine rechteckige Form (String Array) haben !!!
		{
			"|#|",
			"|#|",
			"|#|"			
		},
		{
			" _____ ",
			"|+++++|",
			"|+++++|"	
		},
		{
			" /Ö Ö\\ ",
			"(  @  )",
			" \\-~-/ "				
		},
		{
			"|<<<|>>>|",
			"|<<<|>>>|",
			"|<<<|>>>|"
		},
		{
			" (_) /_) ",
			" (o . o) ",
			"('')_('')"
		},
		{
			" _\\_/_ ",
			"xXxXxXx",
			" XxXxX ",
			"  xXx  ",
		},
		{
			"/\\,,/\\ ",
			"(=';'=)",
			"/ *** \\",
			"(||.||)"
		}
	};
	public boolean hindernisLöschen = false;
	
	public Hindernis(Position position) {
		super(position);
		Random rand = new Random();
		asciiAuswahl = rand.nextInt(asciiImages.length);  // Zufällige Auswahl des Hindernisses
		ausgabe = asciiImages[asciiAuswahl];
	}
	
	
	
	@Override
	public void bewegen() {			
		StringBuilder sb;
		String[] asciiImage = asciiImages[asciiAuswahl];
		if (this.position.y != 0) {
			this.position.y--;			
		}
		if (yStart > 0) {											// wenn Hindernis linken Rand erreicht hat, wird immer weniger davon gezeichnet
			for (int x = 0; x < asciiImage.length; x++) {   		 
				sb = new StringBuilder();							
				for (int y = yStart; y < asciiImage[x].length(); y++) { 
					sb.append(asciiImage[x].charAt(y));
				}		
				asciiImage[x] = sb.toString();
			}
		}		
		if (this.position.y == 0) {
			yStart++;
			if (yStart > asciiImages[asciiAuswahl][0].length()) {
				hindernisLöschen = true;
			}
		}
	}

	@Override
	public char[][] stringToChar() {
		String[] s = ausgabe;
		char[][] c = new char[s.length][s[0].length()];
		for (int x = 0; x < s.length; x++) {
			for (int y = 0; y < s[x].length(); y++) {
				c[x][y] = s[x].charAt(y);
			}
		}
		return c;
	}
}
