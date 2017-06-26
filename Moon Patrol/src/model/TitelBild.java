package model;

public class TitelBild extends SpielObjekt {
	private String[] asciiImage = new String[] {
			"______  ___                        ________       _____             ______",
			"___   |/  /__________________      ___  __ \\_____ __  /________________  /",
			"__  /|_/ /_  __ \\  __ \\_  __ \\     __  /_/ /  __ `/  __/_  ___/  __ \\_  / ",
			"_  /  / / / /_/ / /_/ /  / / /     _  ____// /_/ // /_ _  /   / /_/ /  /  ",
			"/_/  /_/  \\____/\\____//_/ /_/      /_/     \\__,_/ \\__/ /_/    \\____//_/   " };

	public TitelBild(Position position) {
		super(position);
		this.ausgabe = asciiImage;
	}

	@Override
	public String[] getAusgabe() {
		return asciiImage;
	}
	
}
