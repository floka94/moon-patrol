package model;

public class Boden extends SpielObjekt{
	private int i = 0;
	private String[] asciiImage = new String[]
			{
					"hallo veränderung",
			};
	
	public Boden(Position position) {
		super(position);
		this.ausgabe = asciiImage;
	}
	
	@Override
	public void bewegen() {
		i++;
		if (i == 2) {
			for (int i = 0; i < asciiImage.length; i++) {
				StringBuilder s = new StringBuilder();
				char c = ' ';
				for (int z = 1; z < asciiImage[i].length(); z++) {
					c = asciiImage[i].charAt(0);
					s.append(asciiImage[i].charAt(z));
				}
				s.append(c);
				asciiImage[i] = s.toString();
			}
			i = 0;
		}
	}
	@Override
	public String[] getAusgabe() {
		return ausgabe;
	}
}
