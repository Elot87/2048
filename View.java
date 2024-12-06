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
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;
import java.awt.FlowLayout;
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
	private JPanel[] panelList = new JPanel[5];
	private int boardSize;
	
	public static void main(String[] args) {
		View gui = new View();
		gui.setMainMenu();
		gui.add(gui.getPanelList()[0]);
		gui.setVisible(true);
	}
	
	/*
	 * Constructs the GUI for the current 2048 game.
	 */
	public View() {
		board = new Board();
		boardSize = 400;
		setTitle("2048 Game");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// method to handle key presses
<<<<<<< Updated upstream
	private void handleKeyPress(int keyCode) {
		int gameOver;
		switch (keyCode) {
			case KeyEvent.VK_LEFT:
				if (board.update("left")) {
					playSound("sounds/valid move.wav");
				} else {
					playSound("sounds/error.wav");
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (board.update("right")) {
					playSound("sounds/valid move.wav");
				} else {
					playSound("sounds/error.wav");
				}
				break;
			case KeyEvent.VK_UP:
				if (board.update("up")) {
            	playSound("sounds/valid move.wav");
				} else {
					playSound("sounds/error.wav");
				}
				break;
			case KeyEvent.VK_DOWN:
				if (board.update("down")) {
					playSound("sounds/valid move.wav");
				} else {
					playSound("sounds/error.wav");
				}
				break;
			default:
				playSound("sounds/error.wav"); // Error sound if wrong key is pressed
=======
	/*
	 * 
	 */
	private void handleKeyPress(int keyCode) {
		int gameOver;
		switch (keyCode) {
        	case KeyEvent.VK_LEFT:
        		if (board.update("left")) {
        			playSound("sounds/valid move.wav");
        		} else {
        			playSound("sounds/error.wav");
        		}
        		break;
        	case KeyEvent.VK_RIGHT:
        		if (board.update("right")) {
        			playSound("sounds/valid move.wav");
        		} else {
        			playSound("sounds/error.wav");
        		}
        		break;
        	case KeyEvent.VK_UP:
        		if (board.update("up")) {
        			playSound("sounds/valid move.wav");
        		} else {
        			playSound("sounds/error.wav");
        		}
        		break;
        	case KeyEvent.VK_DOWN:
        		if (board.update("down")) {
        			playSound("sounds/valid move.wav");
        		} else {
        			playSound("sounds/error.wav");
        		}
        		break;
        	default:
        		playSound("sounds/error.wav"); // Error sound if wrong key is pressed
>>>>>>> Stashed changes
		}
    
		// Check game over condition
		gameOver = board.gameOverCode();
		if (gameOver != 0) {
			if (gameOver == 1) {
				playSound("sounds/game won.wav");
				setUsernameEntry(true);
			} else if (gameOver == -1) {
				playSound("sounds/game over.wav");
				setUsernameEntry(false);
			}
			switchPanels(2);
		}
    
		refreshBoard(); // Update the UI after a move
	}

	// Method to refresh the board display
	/*
	 * 
	 */
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

	/*
	 * 
	 */
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
	
	/*
	 * 
	 */
	private void setBottomBar() {
		
		JPanel bottomBar = new JPanel();
		bottomBar.setBounds(0, boardSize, boardSize, 100);
		bottomBar.setBackground(Color.GREEN);
		bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds(boardSize/3 - 50, boardSize + 25, 100, 50);
		mainMenuButton.addActionListener(click -> switchPanels(0));
		bottomBar.add(mainMenuButton);
		
		JButton restartButton = new JButton("Restart");
		restartButton.setBounds(2*boardSize/3 - 50, boardSize + 25, 100, 50);
		restartButton.addActionListener(click -> {
			board = new Board(boardSize/100);
			setGameBoard();
			switchPanels(1);
		});
		bottomBar.add(restartButton);
		
		panelList[4] = bottomBar;
	}
	
	// need to add space for instruction on how to play
	/*
	 * 
	 */
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
			if (boardPos[i/getBoardSize()][i%getBoardSize()].getVal() == 0) {
				currLogVal = 0; tile.setText("");
			}
			else {currLogVal = (int) (Math.log(boardPos[i/getBoardSize()][i%getBoardSize()].getVal())/Math.log(2));}
			 
			tile.setBackground(new Color(250 - 10*currLogVal, 230 - 10*currLogVal, 210 - 10*currLogVal));
			tile.setOpaque(true);
			tile.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			tile.setHorizontalAlignment(SwingConstants.CENTER);
			gameBoard.add(tile);
			tileList[i] = tile;
		}
		
		gameBoard.setFocusable(true);
		gameBoard.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            // Handle key press
	            handleKeyPress(e.getKeyCode());
	        }
	    });
		
		panelList[1] = gameBoard;
		
	}
	
	/*
	 * 
	 */
	private void setMainMenu() {
		JPanel mainMenu = new JPanel();
		mainMenu.setBackground(new Color(255, 167, 99));
		mainMenu.setLayout(null);
		mainMenu.setSize(400, 400);		
		
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
		
		play.addActionListener(click -> {
			try {
	            int newSize = Integer.parseInt(sizeInput.getText());
	            if (newSize > 8 || newSize < 4) {
	            	throw new NumberFormatException();
	            }
	            board = new Board(newSize);
	            boardSize = newSize*100;
	            setGameBoard();
	            setBottomBar();
	            switchPanels(1);
	        } catch (NumberFormatException e) {
	        	System.out.println("Enter Integer between 4 & 8");
	        }	
	   });
	   
		leaderboard.addActionListener(click -> {
			setLeaderBoard();	
			setBottomBar();
			switchPanels(3);
		});
	}
	
	/*
	 * 
	 */
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
		leaderFile.close();
	}
	
	/*
	 * 
	 */
	private void setUsernameEntry(boolean won) {
		board.printBoard();
		int score = board.getScore();
		int pos = leaderboardPos(score);
		
		JPanel username = new JPanel();
		username.setLayout(null);
		username.setBackground(Color.ORANGE);
		
		JLabel go = new JLabel("GAME OVER");
		go.setBounds(0, 25, boardSize, 100);
		go.setHorizontalAlignment(SwingConstants.CENTER);
		if (won) {
			go.setText("Congrats! You Win!");
		}
		username.add(go);
		
		JLabel scoreLabel = new JLabel("Your score is " + score);
		scoreLabel.setBounds(0, 3*boardSize/8, boardSize, 50);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		username.add(scoreLabel);
		
		if (pos != -1) {
			String numSuffix;
			if (pos % 10 == 1) {
				numSuffix = "st";
			}
			else if (pos % 10 == 2) {
				numSuffix = "nd";
			}
			else if (pos % 10 == 3) {
				numSuffix = "rd";
			}
			else {
				numSuffix = "th";
			}
			
<<<<<<< Updated upstream
			JLabel congrats = new JLabel("Congratulations! You have the" + pos + numSuffix + " highest score!");
			JLabel enter = new JLabel("Please enter your username to save it in leaderboard");
=======
			JLabel congrats = new JLabel("Congratulations! You have the " + pos + numSuffix + " highest score!");
			JLabel enter = new JLabel("Please enter your username to save it in the leaderboard");
>>>>>>> Stashed changes
			JTextField entryField = new JTextField();
			
			congrats.setBounds(0, 5*boardSize/8, boardSize, 25);
			congrats.setHorizontalAlignment(SwingConstants.CENTER);
			enter.setBounds(0, 5*boardSize/8 + 25, boardSize, 25);
			enter.setHorizontalAlignment(SwingConstants.CENTER);
			
			entryField.setBounds(boardSize/2 - 75, 6*boardSize/8, 150, 30);
			
			JButton submit = new JButton("Submit");
			submit.setBounds(boardSize/2 - 30, 7*boardSize/8, 60, 25);
			submit.addActionListener(click -> {
				if (!(entryField.getText() == null || entryField.getText().length() == 0)) {
					addToLeaderboard(entryField.getText() + "," + score, pos);
					switchPanels(0);
				}
			});
			
			username.add(entryField);
			username.add(enter);
			username.add(congrats);
			username.add(submit);
		}
		
		panelList[2] = username;
	}
	
	/*
	 * 
	 */
	private void switchPanels(int panelNo) {
		getContentPane().removeAll();
		if (panelNo == 0) {
			setSize(boardSize, boardSize);
		} else {
			setSize(boardSize, boardSize + 100);
			add(panelList[4]);
		}
		add(panelList[panelNo]);
		
		revalidate();
		repaint();
		if (panelNo == 1) {
			panelList[panelNo].requestFocusInWindow();
		}
		setVisible(true);
	}
	
	/*
	 * 
	 */
	private int leaderboardPos(int score) {
		Scanner leaderFile;
		try {
			leaderFile = new Scanner(new File("leaderboard.txt"));
		} catch (FileNotFoundException e) {
			return -1; // shouldn't happen if leaderboard.txt isn't moved from the directory
		}
		
		String currLine;
		int pos = 1;
		while (leaderFile.hasNextLine()) {
			if (pos > 10) {
				leaderFile.close();
				return -1;
			}
			currLine = leaderFile.nextLine();
			if (score > Integer.parseInt(currLine.split(",")[1].strip())) {
				leaderFile.close();
				return pos;
			}
			pos++;
		}
		leaderFile.close();
		if (pos > 10) {
			return -1;
		}
		return pos;
	}

	/*
	 * 
	 */
	private void addToLeaderboard(String toAdd, int pos) {
		
		Scanner leaderFile;
		try {
			leaderFile = new Scanner(new File("leaderboard.txt"));
			int i = 1;
			StringBuilder fileContent = new StringBuilder();
			while (leaderFile.hasNextLine() && i <= 10) {
				if (pos == i) {
					fileContent.append(toAdd);
				}
				else {
					fileContent.append(leaderFile.nextLine());
				}
				fileContent.append(System.lineSeparator());
				i++;
			}
			if (i <= 10) {
				fileContent.append(toAdd + System.lineSeparator());
			}
			leaderFile.close();
			System.out.println(pos + " " + toAdd);
			System.out.println(fileContent.toString());
			FileWriter toLeaderboard = new FileWriter("leaderboard.txt");
			toLeaderboard.write(fileContent.toString());
			toLeaderboard.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	private JPanel[] getPanelList() {
		return panelList;
	}
	
	/*
	 * 
	 */
	private int getBoardSize() {
		return board.getSize();
	}
	
}
