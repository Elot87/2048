/*
 * File: Board.java
 * Authors: Elliott Cepin, Yashi Gupta, Aarush Parvataneni, Alex Salgado
 * 
 * This class represents the board for our 2048. It has a default size 4x4.
 */

import java.util.Random;


public class Board {
	private final int SIZE = 4;
	
	private Tile[][] board;
	
	// This will not be perminant, it is just the easiest way for me to test as I go
	public static void main(String args[]){
		
	}

	public Board(){
		board = new Tile[SIZE][SIZE];
		
		// Adds the first two tiles
		addRandomTile();
		addRandomTile();
		
	}
	// adds a random 2 or 4 tile
	// returns false if board is full
	private static boolean addRandomTile(){
		return true;
	}
}
