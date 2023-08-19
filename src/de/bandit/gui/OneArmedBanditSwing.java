package de.bandit.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Diese Klasse implementiert einen einfachen Einarmigen Banditen mit einer
 * grafischen Benutzeroberfläche. Der Spieler kann durch Betätigen des
 * "DREHEN"-Buttons versuchen, Geld zu gewinnen.
 */
@SuppressWarnings("serial")
public class OneArmedBanditSwing extends JFrame {

	// Layouts
	JPanel col1, col2, col3;
	JPanel row1, row2;

	// GUI-Komponenten
	int totalAmt = 100; // Startguthaben: 100€
	int spinCount = 0;
	JLabel lbTotalAmt; // Label für die Anzeige des aktuellen Guthabens
	JLabel lbWonSpin, lbWonTotal;
	JLabel imageView1, imageView2, imageView3;
	private Random rand = new Random(); // Instanzvariable für den Random-Generator

	/**
	 * Konstruktor für die OneArmedBanditSwing-Klasse. Initialisiert die
	 * Benutzeroberfläche.
	 */
	public OneArmedBanditSwing() {
		setTitle("Einarmiger Bandit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridLayout(2, 1));
		rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
		rootPanel.setPreferredSize(new Dimension(600, 220));

		row1 = new JPanel();
		row2 = new JPanel();
		rootPanel.add(row1);
		rootPanel.add(row2);

		lbTotalAmt = new JLabel("Startguthaben: €" + totalAmt);
		row1.add(lbTotalAmt);

		col1 = new JPanel();
		col2 = new JPanel();
		col3 = new JPanel();
		row2.setLayout(new GridLayout(1, 3));
		row2.add(col1);
		row2.add(col2);
		row2.add(col3);

		JLabel lbAmt = new JLabel("Aktueller Spieleinsatz: €10");
		JLabel lbSpins = new JLabel("Anzahl der Spiele: 0");
		lbWonSpin = new JLabel("Gewinn : €");
		lbWonTotal = new JLabel("Total Gewinn : €");

		JLabel lbMsg = new JLabel("Bitte Betrag eingeben !");
		lbMsg.setVisible(false);

		col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
		col1.add(lbAmt);
		col1.add(lbSpins);

		JButton btn = new JButton("DREHEN");
		col2.add(btn);

		col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
		col3.add(lbWonSpin);
		col3.add(lbWonTotal);

		add(rootPanel);
		pack();
		setVisible(true);

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lbMsg.setVisible(false);
				int spinAmt = 10; // Jedes Spiel kostet 10€
				if (totalAmt >= spinAmt) {
					totalAmt -= spinAmt;
					lbTotalAmt.setText("Aktuelles Guthaben: €" + totalAmt);
					lbAmt.setText("Aktueller Spieleinsatz: €" + spinAmt); // Anzeigen des aktuellen Spieleinsatzes
					int random1 = getRandomInteger(6, 1);
					int random2 = getRandomInteger(6, 1);
					int random3 = getRandomInteger(6, 1);
					DisplayCalculate(random1, random2, random3, spinAmt);
					lbSpins.setText("Anzahl der Spiele: " + (++spinCount)); // Zähler für die Anzahl der Spiele erhöhen
				} else {
					lbMsg.setText("Kein Guthaben mehr");
					lbMsg.setVisible(true);
				}
			}
		});
	}

	/**
	 * Zeigt die berechneten Symbole auf der Benutzeroberfläche an.
	 *
	 * @param random1 Zufällig generiertes Symbol 1
	 * @param random2 Zufällig generiertes Symbol 2
	 * @param random3 Zufällig generiertes Symbol 3
	 * @param spinAmt Spieleinsatz für den aktuellen Spin
	 */
	public void DisplayCalculate(int random1, int random2, int random3, int spinAmt) {
		row1.removeAll();

		ImageIcon image1 = new ImageIcon(getClass().getResource("/images/" + random1 + ".png"));
		imageView1 = new JLabel(image1);

		ImageIcon image2 = new ImageIcon(getClass().getResource("/images/" + random2 + ".png"));
		imageView2 = new JLabel(image2);

		ImageIcon image3 = new ImageIcon(getClass().getResource("/images/" + random3 + ".png"));
		imageView3 = new JLabel(image3);

		int maxImageWidth = Math.max(image1.getIconWidth(), Math.max(image2.getIconWidth(), image3.getIconWidth()));
		int maxImageHeight = Math.max(image1.getIconHeight(), Math.max(image2.getIconHeight(), image3.getIconHeight()));

		imageView1.setPreferredSize(new Dimension(maxImageWidth, maxImageHeight));
		imageView2.setPreferredSize(new Dimension(maxImageWidth, maxImageHeight));
		imageView3.setPreferredSize(new Dimension(maxImageWidth, maxImageHeight));

		row1.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		row1.add(imageView1);
		row1.add(imageView2);
		row1.add(imageView3);

		spinAmt = 0; // Setzen Sie den Gewinn auf 0

		if (random1 == random2 && random1 == random3) {
			spinAmt = 100; // 100€ Gewinn bei drei identischen Würfeln
		} else if (random1 == random2 || random1 == random3 || random2 == random3) {
			spinAmt = 20; // 20€ Gewinn bei zwei identischen Würfeln
		}

		totalAmt += spinAmt; // Gewinn zum Guthaben hinzufügen
		lbWonSpin.setText("Gewinn : € " + spinAmt);
		lbWonTotal.setText("Total Gewinn : € " + totalAmt);

		revalidate();
		repaint();
	}

	/**
	 * Generiert eine zufällige Ganzzahl zwischen minimum und maximum.
	 *
	 * @param maximum Das obere Limit für die zufällige Ganzzahl
	 * @param minimum Das untere Limit für die zufällige Ganzzahl
	 * @return Eine zufällige Ganzzahl zwischen minimum und maximum
	 */
	public int getRandomInteger(int maximum, int minimum) {
		return rand.nextInt((maximum - minimum) + 1) + minimum;
	}

	/**
	 * Hauptmethode zum Starten der Anwendung.
	 *
	 * @param args Kommandozeilenargumente (nicht verwendet)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new OneArmedBanditSwing();
			}
		});
	}
}
