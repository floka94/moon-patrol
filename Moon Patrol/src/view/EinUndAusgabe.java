package view;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * @author Andreas Berl
 * @version 2.0
 * 
 *          Diese Klasse erm�glicht die Ausgabe einer Text-Basierten Grafik und
 *          gibt Tastendr�cke zur�ck.
 * 
 *          Die automatische Tastenwiederholung bei gedr�ckt gehaltener Taste
 *          ist standardm��ig ausgeschaltet. Wird sie mit der Methode
 *          <code> typematic(boolean on) </code>eingeschaltet, dann liegt die
 *          Anschlagsverz�gerung bei 20 ms. D.h. eine gedr�ckt gehaltene Taste
 *          liefert alle 20 ms einen Anschlag.
 * 
 *
 */
public class EinUndAusgabe extends Observable {

	private JFrame frame;
	private JTextArea area;
	private LinkedList<KeyEvent> keyEvents;
	private LinkedList<KeyEvent> keyPressed;
	private boolean typematic;

	/**
	 * Konstruktor der Klasse.
	 * 
	 * @param fensterName
	 *            Name der oben im Fenster angezeigt wird.
	 * @param fontGroesse
	 *            Groesse der anzuzeigenden Schriftart.
	 * @param hoehe
	 *            Die hoehe des Fensters in Zeilen.
	 * @param breite
	 *            Die breite des Fensters in Spalten.
	 */
	public EinUndAusgabe(String fensterName, int fontGroesse, int zeilen, int spalten) {
		keyEvents = new LinkedList<>();
		keyPressed = new LinkedList<>();
		typematic = true;
		area = new JTextArea(zeilen, spalten);
		area.setFont(new Font("Monospaced", Font.BOLD, fontGroesse));
		area.setEditable(false);

		frame = new JFrame(fensterName + " (Schriftgröße: " + fontGroesse + ", Höhe: " + zeilen + " Zeichen, Breite: "
				+ spalten + " Zeichen)");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(area);
		frame.setVisible(true);
		frame.setSize(frame.getPreferredSize());
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		area.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				synchronized (keyPressed) {
					KeyEvent k = getKeyEventFromKeyPressed(e.getKeyCode());
					if (k != null) {
						keyPressed.remove(k);
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				synchronized (keyPressed) {

					if (getKeyEventFromKeyPressed(e.getKeyCode()) == null) {
						keyPressed.add(e);
						addKeyEvent(e);
						if (typematic) {
							Runnable run = new Runnable() {

								@Override
								public void run() {
									boolean first = true;
									while (getKeyEventFromKeyPressed(e.getKeyCode()) != null) {
										if (!first) {
											addKeyEvent(e);
										}
										first = false;
										try {
											Thread.sleep(20);
										} catch (InterruptedException e1) {
										}
									}
								}
							};
							new Thread(run).start();
						}
					}
				}
			}
		});
	}

	/**
	 * Diese Methode schaltet die Tastenwiederholung ein oder aus. Die
	 * automatische Tastenwiederholung bei gedr�ckt gehaltener Taste ist
	 * standardm��ig ausgeschaltet. eingeschaltet, dann liegt die
	 * Anschlagsverz�gerung bei 20 ms. D.h. eine gedr�ckt gehaltene Taste
	 * liefert alle 20 ms einen Anschlag.
	 * 
	 * @param on
	 *            schaltet die Tastenwiederholung ein oder aus.
	 */
	public void typematic(boolean on) {
		typematic = on;
	}

	/**
	 * Gibt ein java.awt.event.KeyEvent (gedr�ckte Taste) des Benutzers zur�ck, in der
	 * Reihenfolge in der sie auftetreten sind. Diese Methode kann so oft
	 * aufgerufen werden bis sie als Ergebnis null liefert. Dann sind keine
	 * KeyEvents mehr gespeichert. <br>
	 * <br>
	 * Beispiel zur Erkennung eines A:
	 * 
	 * <pre>
	 * <code>
	 * KeyEvent keyEvent = einUndAusgabe.getKeyEvent(); 
	 * if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
	 * 	System.out.println("A wurde gedr�ckt"); 
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @return Tastendruck als Key-Event in der Reihenfolge in der sie erzeugt
	 *         wurden. null, falls keine weitere Taste gedr�ckt wurde.
	 */
	public KeyEvent getKeyEvent() {
		KeyEvent key = null;
		synchronized (keyEvents) {
			if (!keyEvents.isEmpty()) {
				try {
					key = keyEvents.removeFirst();
				} catch (Exception e) {
					// no need to do something here.
				}
			}
		}
		return key;
	}

	private void addKeyEvent(KeyEvent e) {
		keyEvents.add(e);
		setChanged();
		notifyObservers();
	}

	/**
	 * Gibt den �bergebenen String im Fenster aus. Diese Methode sollte
	 * h�chstens alle 20 ms aufgerufen werden (60 Frames pro Sekunde). Alles
	 * dar�ber hinaus verlangsamt das Programm und bringt keinen Vorteil.
	 * 
	 * @param ausgabe
	 *            Der anzuzeigende String.
	 */
	public void printToFrame(String ausgabe) {
		area.setText(ausgabe);
	}

	/**
	 * Gibt das �bergebene Char-Array im Fenster aus. Diese Methode sollte
	 * h�chstens alle 20 ms aufgerufen werden (60 Frames pro Sekunde). Alles
	 * dar�ber hinaus verlangsamt das Programm und bringt keinen Vorteil.
	 * 
	 * @param ausgabe
	 *            Das anzuzeigende Char-Array.
	 */
	public void printToFrame(char[][] ausgabe) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ausgabe.length; i++) {
			for (int j = 0; j < ausgabe[i].length; j++) {
				sb.append(ausgabe[i][j]);
			}
			sb.append("\n");
		}
		printToFrame(sb.toString());
	}

	private synchronized KeyEvent getKeyEventFromKeyPressed(int keyCode) {
		synchronized (keyPressed) {

			for (KeyEvent keyEvent : keyPressed) {
				if (keyEvent.getKeyCode() == keyCode) {
					return keyEvent;
				}
			}
			return null;
		}
	}

}
