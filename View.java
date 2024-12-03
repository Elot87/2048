/*
 * File: View.java
 * Authors: Elliott Cepin, Yashi Gupta, Aarush Parvataneni, Alex Salgado
 * 
 * This class represents the graphical user interface that presents information
 * to and accepts it from the user. It displays the board of tiles as well as
 * a point tracker, time tracker, and other features.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class View extends JFrame {
	private Board board;
	private JLabel[] tileList;
	private JPanel[] panelList = new JPanel[4];
	private int boardSize;
	
	/*
	 * Constructs the GUI for the current 2048 game.
	 */

	private enum Direction {
		LEFT, UP, RIGHT, DOWN
	}

	public View(int size) {
		// add a way to change the SIZE variable
		
		board = new Board(size);
		boardSize = size*100;
		// configure main frame
		setTitle("2048 Game");
		setSize(size*100, size*100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);

	    // add KeyListener for arrow keys
	    addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            // Handle key press
	            handleKeyPress(e.getKeyCode());
	        }
	    });
	}

	// method to handle key presses
	private void handleKeyPress(int keyCode) {
	    switch (keyCode) {
	        case KeyEvent.VK_LEFT:
	            board.update("left");
	            break;
	        case KeyEvent.VK_RIGHT:
	            board.update("right");
	            break;
	        case KeyEvent.VK_UP:
	            board.update("up");
	            break;
	        case KeyEvent.VK_DOWN:
	            board.update("down");
	            break;
	        default:
	            return; // ignore if some other key is added
	    }
		//need to figure out how to still play music if its not my computer 
	    playSound("/Users/yashi/git/2048/mixkit-epic-orchestra-transition-2290.wav"); // play sound
        refreshBoard(); //update the UI after a move
	}

	// Method to refresh the board display
	private void refreshBoard() {
	    Tile[][] boardPos = board.getBoardState();
	    for (int i = 0; i < tileList.length; i++) {
	        int value = boardPos[i / getBoardSize()][i % getBoardSize()].getVal();
	        tileList[i].setText(value == 0 ? "" : Integer.toString(value));
	        int currLogVal = value == 0 ? 0 : (int) (Math.log(value) / Math.log(2));
	        tileList[i].setBackground(new Color(250 - 10 * currLogVal, 230 - 10 * currLogVal, 210 - 10 * currLogVal));
	    }
	    repaint();
	}

	private void playSound(String soundFile) {
	    try {
	        // Load the sound file
	        File file = new File(soundFile);
	        if (!file.exists()) {
	            System.err.println("Sound file not found: " + soundFile);
	            return;
	        }

	        // Create an AudioInputStream
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file); 

	        // Get a sound clip resource
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioStream);

	        // Play the clip
	        clip.start();
	    } catch (UnsupportedAudioFileException e) {
	        System.err.println("Unsupported audio file: " + soundFile);
	    } catch (IOException e) {
	        System.err.println("Error reading audio file: " + soundFile);
	    } catch (LineUnavailableException e) {
	        System.err.println("Audio line unavailable for: " + soundFile);
	    }
	}
	public static void main(String[] args) {
		View gui = new View(4);
		gui.setGameBoard();
		gui.setMainMenu();
		gui.setLeaderBoard();
		gui.setUsernameEntry();
		gui.add(gui.getPanelList()[3]);
		gui.setVisible(true);
		
	}
	
	// need to add space for instruction on how to play
	private void setGameBoard() {
		JPanel gameBoard = new JPanel();
		JLabel tile; int currLogVal;
		gameBoard.setLayout(new GridLayout(getBoardSize(), getBoardSize()));
		gameBoard.setSize(getBoardSize()*100, getBoardSize()*100);
		tileList = new JLabel[getBoardSize()*getBoardSize()];
		Tile[][] boardPos = board.getBoardState();
		for (int i = 0; i < getBoardSize()*getBoardSize(); i++) {
			 tile = new JLabel(Integer.toString(boardPos[i/getBoardSize()][i%getBoardSize()].getVal()));
			 tile.setSize(100, 100);
			 if (boardPos[i/getBoardSize()][i%getBoardSize()].getVal() == 0) {currLogVal = 0;}
			 else {currLogVal = (int) (Math.log(boardPos[i/getBoardSize()][i%getBoardSize()].getVal())/Math.log(2));}
			 
			 tile.setBackground(new Color(250 - 10*currLogVal, 230 - 10*currLogVal, 210 - 10*currLogVal));
			 tile.setOpaque(true);
			 tile.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			 tile.setHorizontalAlignment(SwingConstants.CENTER);
			 gameBoard.add(tile);
			 tileList[i] = tile;
		}
		panelList[1] = gameBoard;
		
	}
	
	private void setMainMenu() {
		JPanel mainMenu = new JPanel();
		mainMenu.setBackground(new Color(255, 167, 99));
		mainMenu.setLayout(null);
		JButton play = new JButton("Play Game");
		play.setName("toPlay");
		play.setBounds(boardSize/2 - 50, boardSize/5 , 100, 50);
		
		JLabel sizeLab = new JLabel("Board Size (Enter Integer between 4 & 8)");
		sizeLab.setBounds(boardSize/2 - 125, boardSize/2 - 50, 250, 50);
		
		JTextField sizeInput = new JTextField("4");
		sizeInput.setName("sizeInput");
		sizeInput.setBounds(boardSize/2 - 50, boardSize/2, 100, 50);
		
		JButton leaderboard = new JButton("Click here to see Leaderboard");
		leaderboard.setName("leaderboard");
		leaderboard.setBounds(boardSize/2 - 110, boardSize - 125, 220, 50);
		
		mainMenu.add(leaderboard);
		mainMenu.add(sizeInput);
		mainMenu.add(sizeLab);
		mainMenu.add(play);
		panelList[0] = mainMenu;
	}
	
	private void setLeaderBoard() {
		JPanel leaders = new JPanel(new GridLayout(11, 2));
		leaders.setBackground(Color.CYAN);
		Scanner leaderFile;
		try {
			leaderFile = new Scanner(new File("leaderboard.txt"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
			return;
		}
		String currLine; JLabel name, score;
		String[] splitLine;
		
		name = new JLabel("Username");
		score = new JLabel("High Score");
		name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		name.setFont(name.getFont().deriveFont(Font.BOLD));
		score.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		score.setFont(score.getFont().deriveFont(Font.BOLD));
		leaders.add(name);
		leaders.add(score);
		
		int i = 0;
		// assuming the file is formatted username, score
		while (i < 10) {
			if (leaderFile.hasNextLine()) {
				currLine = leaderFile.nextLine();
				splitLine = currLine.split(",");
				name = new JLabel(splitLine[0]);
				score = new JLabel(splitLine[1]);
			} else {
				name = new JLabel();
				score = new JLabel();
			}
			name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			score.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			leaders.add(name);
			leaders.add(score);
			i++;
		}
		panelList[3] = leaders;
	}
	
	private void setUsernameEntry() {
		JPanel username = new JPanel();
		username.setLayout(null);
		username.setBackground(Color.ORANGE);
		JLabel congrats = new JLabel("Congratulations! You are in the top 10 scores!");
		JLabel enter = new JLabel("Please enter your username to save it in leaderboard");
		JTextField entry = new JTextField();
		entry.setName("username");
		congrats.setBounds(boardSize/2 - 140, boardSize/2 - 50, 280, 25);
		enter.setBounds(boardSize/2 - 160, boardSize/2 - 25, 320, 25);
		entry.setBounds(boardSize/2 - 75, boardSize/2, 150, 30);
		username.add(entry);
		username.add(enter);
		username.add(congrats);
		panelList[2] = username;
	}
	
	private JPanel[] getPanelList() {
		return panelList;
	}
	
	private JLabel[] getTileList() {
		return tileList;
	}

	private void shiftTiles(Direction D) {
		
	}

	private boolean check2048() {
		
		return false;
	}
	
	private int getBoardSize() {
		return board.getSize();
	}

	
}
