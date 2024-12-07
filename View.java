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

	/*
	 * This is the constructor method that initializes the board and boardSize variables to the 
	 * default values required for the Main menu
	 * */
	public View() {
		board = new Board();
		boardSize = 400;
		setTitle("2048 Game");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// This is the method that calls the Board.java (the model) update method to update the 
	// the board and refresh the game board. It also calls the methods to play the appropriate sounds
	// based on the move and game situation.
	// @param: an int that is essentially an enum showing the input key hit
	private void handleKeyPress(int keyCode) {
		
	    int gameOver;
	    switch (keyCode) {
	        case KeyEvent.VK_LEFT:
	            if (board.update("left")) { // valid move
	            playSound("sounds/valid move.wav");
	        } else {
	            playSound("sounds/error.wav"); // invalid move
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
	}
	
	// Check whether the game is over 
	gameOver = board.gameOverCode();
	// gameOver is 0 if we still can play
	if (gameOver != 0) { 
		// code is 1 if we win
	    if (gameOver == 1) { 
	        playSound("sounds/game won.wav");
	        setUsernameEntry(true);
	    // code is -1 if we loose
	    } else if (gameOver == -1) {
	        playSound("sounds/game over.wav");
	        setUsernameEntry(false);
	    }
	 // switch to the game over panel if game is over
	    switchPanels(2); 
	}
	
	refreshBoard(); // Update the UI after a move
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

	// Method to play the sound based on the soundFile
	// @param: A String that is the name of the file containing the sound
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
	
	
	// creates the bottom bar Panel consisting the Main Menu and Restart buttons. 
	// Then it saves this bar to the panelList array
	private void setBottomBar() {
		
		JPanel bottomBar = new JPanel();
		bottomBar.setBounds(0, boardSize, boardSize, 100);
		bottomBar.setBackground(Color.GREEN);
		bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// buttton that takes you to the main menu
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBounds(boardSize/3 - 50, boardSize + 25, 100, 50);
		mainMenuButton.addActionListener(click -> {
			boardSize = 400; // need to reset boardsize so window can resize appropriately
			switchPanels(0); 
			});
		bottomBar.add(mainMenuButton);
		
		// button that restarts the game with same board size
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
	
	// Sets up the JPanel that contains the actual game board for the gameplay
	// Then we add it to the panelList so it can easily be added to the Frame
	private void setGameBoard() {
		
		JPanel gameBoard = new JPanel();
		JLabel tile; 
		int currLogVal;
		gameBoard.setLayout(new GridLayout(getBoardSize(), getBoardSize()));
		gameBoard.setSize(getBoardSize()*100, getBoardSize()*100);
		tileList = new JLabel[getBoardSize()*getBoardSize()];
		Tile[][] boardPos = board.getBoardState();
		
		for (int i = 0; i < getBoardSize()*getBoardSize(); i++) {
			
			 tile = new JLabel(Integer.toString(boardPos[i/getBoardSize()][i%getBoardSize()].getVal()));
			 tile.setSize(100, 100);
			 tile.setFont(new Font("Impact", Font.PLAIN, 24));
			 // since we can't take log of 0, we just set it to 0
			 if (boardPos[i/getBoardSize()][i%getBoardSize()].getVal() == 0) {currLogVal = 0; tile.setText("");}
			 // otherwise we take the log of the tile value so we can create various different colours for the gui tiles
			 else {currLogVal = (int) (Math.log(boardPos[i/getBoardSize()][i%getBoardSize()].getVal())/Math.log(2));}
			 
			 tile.setBackground(new Color(250 - 10*currLogVal, 230 - 10*currLogVal, 210 - 10*currLogVal));
			 tile.setOpaque(true);
			 tile.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			 tile.setHorizontalAlignment(SwingConstants.CENTER);
			 gameBoard.add(tile);
			 tileList[i] = tile;
		}
		
		gameBoard.setFocusable(true);
		// the method needed to listen for the arrow key hits
		gameBoard.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            // Handle key press
	            handleKeyPress(e.getKeyCode());
	        }
	    });
		panelList[1] = gameBoard;
		
	}
	
	
	// Sets up the JPanel that contains the Main menu where the player can choose to see
	// the leader board, set board size, or hit play.
	// Then we add it to the panelList so it can easily be added to the Frame
	private void setMainMenu() {
		
		JPanel mainMenu = new JPanel();
		mainMenu.setBackground(new Color(255, 167, 99));
		mainMenu.setLayout(null);
		mainMenu.setSize(400, 400);		
		
		// button to play the game
		JButton play = new JButton("Play Game");
		play.setName("toPlay");
		play.setBounds(boardSize/2 - 50, boardSize/5 , 100, 50);
		
		JLabel sizeLab = new JLabel("Board Size (Enter Integer between 4 & 8)");
		sizeLab.setBounds(boardSize/2 - 125, boardSize/2 - 50, 250, 50);
		
		// board size is 4 by default
		JTextField sizeInput = new JTextField("4");
		sizeInput.setName("sizeInput");
		sizeInput.setBounds(boardSize/2 - 50, boardSize/2, 100, 50);
		
		// button to take you to the leaderboard
		JButton leaderboard = new JButton("Click here to see Leaderboard");
		leaderboard.setName("leaderboard");
		leaderboard.setBounds(boardSize/2 - 110, boardSize - 125, 220, 50);
		
		
		mainMenu.add(leaderboard);
		mainMenu.add(sizeInput);
		mainMenu.add(sizeLab);
		mainMenu.add(play);
		panelList[0] = mainMenu;
		
		// method to hit play
		play.addActionListener(click -> {
			try {
	            int newSize = Integer.parseInt(sizeInput.getText());
	            // check to make sure that the user input size is between 4-8
	            if (newSize > 8 || newSize < 4) {throw new NumberFormatException();}
	            board = new Board(newSize);
	            boardSize = newSize*100;
	            setBottomBar();
	            setGameBoard();
	            switchPanels(1);
	        } catch (NumberFormatException e) {
	        	System.out.println("Enter Integer between 4 & 8");
	        }	
	   });
	   
		// method that makes us go to the leaderboard when button is clicked
		leaderboard.addActionListener(click -> {
			setBottomBar();
			setLeaderBoard();	
			switchPanels(3);
		});
	}
	
	// method to set the leaderboard containing top 10 scores
	private void setLeaderBoard() {
		
		JPanel leaders = new JPanel(new GridLayout(11, 2)); // creates a table of sorts
		leaders.setBackground(Color.CYAN);
		leaders.setSize(boardSize, boardSize);
		
		// reading the leaderboard.txt file to obtain the top 10 scores
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
		name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); // headers have to be in a bigger, bolder font
		name.setFont(new Font("Impact", Font.PLAIN, 24));
		score.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		score.setFont(new Font("Impact", Font.PLAIN, 24));
		leaders.add(name);
		leaders.add(score);
		
		int i = 0;
		// assuming the file is formatted "username, score"
		while (i < 10) {
			if (leaderFile.hasNextLine()) {
				currLine = leaderFile.nextLine();
				splitLine = currLine.split(",");
				name = new JLabel(splitLine[0]);
				score = new JLabel(splitLine[1]);
			} else {
				// when we have less than 10 entries in the file
				name = new JLabel();
				score = new JLabel();
			}
			
			// creates the borders between different entries in the table
			name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			score.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
			leaders.add(name);
			leaders.add(score);
			i++;
		}
		panelList[3] = leaders;
		leaderFile.close();
	}
	
	// Method to set the panel where the player can enter their username for the leaderboard
	// @param: boolean won which indicates if the player got the 2048 or not
	private void setUsernameEntry(boolean won) {
		
		board.printBoard();
		int score = board.getScore();
		// gets the position on the leaderboard based on score, it is -1 if not on leaderboard
		int pos = leaderboardPos(score); 
		
		JPanel username = new JPanel();
		username.setLayout(null);
		username.setBackground(Color.ORANGE);
		username.setSize(boardSize, boardSize);
		
		JLabel go = new JLabel("Game Over");
		go.setBounds(0, 25, boardSize, 100);
		go.setHorizontalAlignment(SwingConstants.CENTER);
		// if the player won, the text should be different
		if (won) {go.setText("Congrats! You Win!");}
		username.add(go);
		
		// label to indicate player's score
		JLabel scoreLabel = new JLabel("Your score is " + score);
		scoreLabel.setBounds(0, 3*boardSize/8, boardSize, 50);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		username.add(scoreLabel);
		
		// we only allow the player to input their username if they are in the highscores
		if (pos != -1) {
			
			JLabel congrats = new JLabel("Congratulations! You have the " + pos + " highest score!");
			JLabel enter = new JLabel("Please enter your username to save it in leaderboard");
			// field to enter username
			JTextField entryField = new JTextField();
			
			congrats.setBounds(0, 5*boardSize/8, boardSize, 25);
			congrats.setHorizontalAlignment(SwingConstants.CENTER);
			enter.setBounds(0, 5*boardSize/8 + 25, boardSize, 25);
			enter.setHorizontalAlignment(SwingConstants.CENTER);
			
			entryField.setBounds(boardSize/2 - 75, 6*boardSize/8, 150, 30);
			
			JButton submit = new JButton("Submit");
			submit.setBounds(boardSize/2 - 50, 7*boardSize/8, 100, 25);
			
			// hitting submit adds your username to the leaderboard only if your username isn't empty
			// and goes back to the mainpage
			submit.addActionListener(click -> {
				if (!(entryField.getText() == null || entryField.getText().length() == 0)) {
					addToLeaderboard(entryField.getText() + "," + score, pos);
					boardSize = 400;
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
	
	
	// Method to switch between different panels of our UI
	// @param: panelNo is an int indicating the index of the panelList whose panel we want to switch to
	private void switchPanels(int panelNo) {
		getContentPane().removeAll();
		setLayout(null);
		
		// main menu we don't add bottomBar
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
			// we need to request focus on gameBoard so it listens to keystrokes
			panelList[panelNo].requestFocusInWindow();
		}
		setVisible(true);
	}
	
	
	// Method to find the leaderboard position based on score
	// @param: an integer indicating the score of the player in the game
	// @return: returns an integer indicating the positon on leaderboard
	private int leaderboardPos(int score) {
		Scanner leaderFile;
		try {
			// making a scanner of the leaderboard file
			leaderFile = new Scanner(new File("leaderboard.txt"));
		} catch (FileNotFoundException e) {
			return -1; // shouldn't happen if leaderboard.txt isn't moved from the directory
		}
		
		String currLine;
		int pos = 1;
		while (leaderFile.hasNextLine()) {
			// if the position is over 10 we don't display so we return -1 to indicate that
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
		if (pos > 10) { // if our position is over 10 return -1
			return -1;
		}
		return pos;
	}

	
	// Method to add an entry to the leaderboard file
	// @param: String that is the line in format "username, score" to be added to the leaderboard file
	// @param: int that is the position at which it needs to be added
	private void addToLeaderboard(String toAdd, int pos) {
		
		Scanner leaderFile;
		try {
			leaderFile = new Scanner(new File("leaderboard.txt"));
			int i = 1;
			boolean added = false;
			StringBuilder fileContent = new StringBuilder();
			while (leaderFile.hasNextLine() && i <= 10) {
				// append the new entry to the stringbuilder if position and i are equal
				if (pos == i) {
					fileContent.append(toAdd);
					// set added to true so we don't accidentally add at the end when we have less than 10 entries
					added = true; 
				}
				// append the pre-existing records if not
				else {
					fileContent.append(leaderFile.nextLine());
				}
				fileContent.append(System.lineSeparator());
				i++;
			}
			
			if (i <= 10 && !added) {
				fileContent.append(toAdd + System.lineSeparator());
			}
			leaderFile.close();
			System.out.println(pos + " " + toAdd);
			System.out.println(fileContent.toString());
			// use FileWriter to overwrite leaderboard.txt with the new string
			FileWriter toLeaderboard = new FileWriter("leaderboard.txt");
			toLeaderboard.write(fileContent.toString());
			toLeaderboard.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// getter method to obtain the panelList
	private JPanel[] getPanelList() {
		return panelList;
	}
	
	// getter method to obtain the size of the board
	private int getBoardSize() {
		return board.getSize();
	}
	
	// running this will run the App
	public static void main(String[] args) {
		View gui = new View();
		gui.setMainMenu();
		gui.add(gui.getPanelList()[0]);
		gui.setVisible(true);
		
		
	}

	
}
