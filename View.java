/*
 * File: View.java
 * Authors: Elliott Cepin, Yashi Gupta, Aarush Parvataneni, Alex Salgado
 * 
 * This class represents the graphical user interface that presents information
 * to and accepts it from the user. It displays the board of tiles as well as
 * a point tracker, time tracker, and other features.
 */


import javax.swing.*;

public class View extends JFrame {
	private Board board;
	
	/*
	 * Constructs the GUI for the current 2048 game.
	 */
	public View() {
		board = new Board();
		
		// configure main frame
		setTitle("2048 Game");
		setSize(600, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// display board and tiles
		
		// arrow key logic
		
	}
	
	/*
	 * 
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			View gui = new View();
			gui.setVisible(true);
		});
	}
}
