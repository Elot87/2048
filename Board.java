/*
 * File: Board.java
 * Authors: Elliott Cepin, Yashi Gupta, Aarush Parvataneni, Alex Salgado
 * 
 * This class represents the board for our 2048. It has a default size 4x4.
 */

import java.util.Random;


public class Board {
	private final int SIZE = 4;
	private final int TILES = SIZE * SIZE;
	private final int[] RANDOM_DISTRIBUTION = {2,2,2,4}; // 75% chance to be a 2
	private Tile[][] board;
	
	// This will not be perminant, it is just the easiest way for me to test as I go
	public static void main(String args[]){
		System.out.println("-----TEST 1-----");
		testAddRandomTile();

		System.out.println("-----TEST 2-----");
		testEmptyAt();


	}

	private static void testAddRandomTile(){
		Board testBoard = new Board();

		for (int i=0; i<testBoard.TILES -1; i++){
			if (!testBoard.addRandomTile()) System.out.println("Couldn't add tile to board that shouldn't be full");
		}

		if (testBoard.addRandomTile()) System.out.println("Couldn't add tile to board that should be full");
	}

	private static void testEmptyAt(){
		Board testBoard = new Board();
		if (!testBoard.emptyAt(7)) System.out.println("Claimed empty board spot (7) to be full--it is not");
		testBoard.addTile(new Tile(2), 7);
		if (testBoard.emptyAt(7)) System.out.println("Claimed full board spot (7) to be empty--it is not");
	}

	public Board(){
		board = new Tile[SIZE][SIZE];
		
		// Adds the first two tiles
		addRandomTile();
		addRandomTile();
		
	}
	
	// checks if board is empty at position in row major order
	// @pre 0 <= num < 16
	private boolean emptyAt(int num){
		return board[num / SIZE][num % SIZE] == null;
	}

	private boolean emptyAt(int x, int y){
		return board[x][y] == null;
	}

	private void addTile(Tile tile, int position){
		this.board[position / SIZE][position % SIZE] = tile;
	}
	// adds a random 2 or 4 tile
	// returns false if board is full
	// work in progress
	private boolean addRandomTile(){
		Random random = new Random();
		
		int randint = random.nextInt(10000000) % TILES;
		int selector = random.nextInt(10000000) % RANDOM_DISTRIBUTION.length;
		for (int i=0; i<TILES; i++){
			if (this.emptyAt((randint + i) % TILES)){
				this.addTile(new Tile(RANDOM_DISTRIBUTION[selector]), (randint + i) % TILES);
				return true;
			}
		}


		return false;
	}
}
