/*
 * File: Board.java
 * Authors: Elliott Cepin, Yashi Gupta, Aarush Parvataneni, Alex Salgado
 * 
 * This class represents the board for our 2048. Size of the board is set to
 * 4x4 by default, but is flexible enough to create boards up to 8x8.
 */

import java.util.Random;


public class Board {
	private final int SIZE;
	private final int TILES;
	private final int[] RANDOM_DISTRIBUTION = {2,2,2,4}; // 75% chance to be a 2
	private Tile[][] board;
	
	/**
	 * Constructs a new Board object with a default size 4x4 and initializes it
	 * with 2 randomly placed tiles
	 */
	public Board(){
		SIZE = 4;
		TILES = SIZE*SIZE; 
		board = new Tile[SIZE][SIZE];
		
		// Adds the first two tiles
		addRandomTile();
		addRandomTile();	
	}

	/**
	 * Constructs a new Board object of a given size and initializes it with 2
	 * randomly placed tiles
	 * 
	 * @param size: int; dimensions of the board (between 4-8)
	 */
	public Board(int size){
		SIZE = size;
		TILES = size*size;
		board = new Tile[SIZE][SIZE];
		
		// Adds the first two tiles
		addRandomTile();
		addRandomTile();
	}
	
	/**
	 * Updates the board based on a given direction to merge like tiles
	 * 
	 * @pre direction must be one of four options {"right", "left", "up", "down"}
	 * @param direction: String; indicates the direction to shift the tiles
	 * @return boolean; whether the board could be updated
	 */
	public boolean update(String direction){
		boolean shifted = true;
		switch (direction){
			case "right":
				shifted = shiftRight();
				break;
			case "left":
				shifted = shiftLeft();
				break;
			case "up":
				shifted = shiftUp();
				break;
			case "down":
				shifted = shiftDown();
				break;
			default:
				//this should not occur
				System.out.println("Error: wasn't able to update!");
				return false;
		}
			
		if(!shifted) {
			return true; 
		}
		return addRandomTile();
	}
	
	/**
	 * Obtains the dimensions of the board
	 * 
	 * @return int; represents the number of tiles per row/column
	 */
	public int getSize() {
		return SIZE;
	}
	
	/**
	 * Obtains the current arrangement of tiles on the board
	 * 
	 * @return Tile[][]; a copy of the board
	 */
	public Tile[][] getBoardState(){
		Tile[][] boardState = new Tile[SIZE][SIZE];

		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				boardState[i][j] = new Tile(valAt(i,j));
			}
		}
		return boardState;
	}
	
	/**
	 * Prints a text representation of the board
	 */
	public void printBoard(){
		int size = 5;
		String spaces;
		String tile;
		for (int y = 0; y<SIZE; y++){
			System.out.print("|");
			for (int x=0; x<SIZE; x++){
				spaces = "";
				tile = "" + valAt(y, x);
				for (int i=0; i<size; i++) spaces += " ";
				System.out.print(" " + tile + spaces + " |");
			}
			System.out.print("\n");
		}
	}
	

	/**
	 * Determines if the game has concluded; the user has won by achieving a 2048
	 * tile, or the board has filled with no possible moves
	 * 
	 * @return 1 if there is a 2048 tile, 0 if the tiles can be shifted and the
	 * game can continue, -1 if the board has filled and there are no possible
	 * merges
	 */
	public int gameOverCode() {	
		for (int i = 0; i < TILES; i++) {
			if (valAt(i/SIZE, i%SIZE) == 2048) {
				return 1;
			}
			
			if (exists(i/SIZE - 1, i%SIZE) && valAt(i/SIZE, i%SIZE) == valAt(i/SIZE -1, i%SIZE)) { 
				return 0;
			}
			else if (exists(i/SIZE, i%SIZE - 1) && valAt(i/SIZE, i%SIZE) == valAt(i/SIZE , i%SIZE - 1)) {
				return 0;
			}
			else if (valAt(i/SIZE, i%SIZE) == 0) {
				return 0;
			}
		}
		return -1;
	}
	

	/**
	 * Obtains the score of the current round
	 * 
	 * @return int; represents current player's score
	 */
	public int getScore() {
		int score = 0;
		for (int i = 0; i < TILES; i++) {
			if (board[i/SIZE][i%SIZE] != null) {
				score += board[i/SIZE][i%SIZE].getVal();
			}
		}
		return score;
	}
		
		

	/* PRIVATE HELPER METHODS */
	
	
	// checks if board is empty at position in row major order
	// @pre 0 <= num < 16
	protected boolean emptyAt(int num){
		return board[num / SIZE][num % SIZE] == null;
	}
	
	// determines if a particular tile position is empty
	protected boolean emptyAt(int y, int x){
		return board[y][x] == null;
	}
	
	// determines if the board is full
	protected boolean isFull(){
		for (int i=0; i<TILES; i++){
			if (emptyAt(i)) return false;
		}

		return true;
	}
		
	// adds a tile to a specified position on the board
	protected void add(Tile tile, int y, int x){
		board[y][x] = tile;
	}
	
	// adds 2 or 4 tile to a random position on the board
	protected boolean addRandomTile(){
		Random random = new Random();
		
		int randint = random.nextInt(10000000) % TILES;
		int selector = random.nextInt(10000000) % RANDOM_DISTRIBUTION.length;
		for (int i=0; i<TILES; i++){
			if (this.emptyAt((randint + i) % TILES)){
				this.add(new Tile(RANDOM_DISTRIBUTION[selector]), ((randint + i) % TILES) / SIZE, ((randint + i) % TILES) % SIZE);
				return true;
			}
		}
		return false;
	}
	
	// removes the tile at a specified position on the board
	protected void remove(int y, int x){
		board[y][x] = null;
	}

	// obtains the number of tiles currently at play on the board
	protected int tileCount(){
		int count = 0;
		for (int i=0; i<TILES; i++){
			if (!emptyAt(i)) count++;
		}
		return count;
	}

	// shifts all current tiles on the board to the right, if possible, and 
	// merging when necessary
	private boolean shiftRight(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;
		boolean shifted = false;

		// let x be the index of the current column
		for (int x=SIZE-2; x>=0; x--){
			// let y be the index of the current row
			for (int y=0; y<SIZE; y++){

				// is there an Tile at the current coordinate?
				if (!emptyAt(y, x)){

					// until there is nolonger somewhere to move the tile
					for (cur = x; exists(y,cur+1);cur++){
						// check if we can move it
						if (emptyAt(y, cur+1)){
							shifted = true;
							tile = new Tile(valAt(y,cur));
							remove(y,cur); // moving consists of deleting
							add(tile, y, cur+1); // and placing (original object dies, but that is ok)	
						} else {
							// there is a tile in the way...
							// can we merge?
							if ((valAt(y, cur) == valAt(y, cur+1)) && !(mergeBoard[y][cur+1] || mergeBoard[y][cur])){
								shifted = true;
								mergeBoard[y][cur+1] = true; // we can no longer merge again on this square during this shift
								tile = new Tile(valAt(y, cur) + valAt(y, cur+1)); // new tile, twice as big
								remove(y, cur); // merging consists of deleting the old tiles
								add(tile, y, cur+1); // and replacing the right one with the doubled tile
							}	
						}
					}
				}
			}
		}
		return shifted;
	}

	// shifts all current tiles on the board to the left, if possible, and 
	// merging when necessary
	private boolean shiftLeft(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;
		boolean shifted = false;
		// let x be the index of the current column
		for (int x=1; x<SIZE; x++){
			// let y be the index of the current row
			for (int y=0; y<SIZE; y++){
				
				// is there a Tile at the current coordinate?
				if (!emptyAt(y, x)){
					
					// until there is nolonger somewhere to move the tile
					for (cur = x; exists(y, cur-1); cur--){
						//check if we can move it
						if (emptyAt(y, cur-1)){
							shifted = true;
							tile = new Tile(valAt(y,cur));
							remove(y,cur); // moving consists of deleting
							add(tile, y, cur-1); // and placing (original object dies, but that is ok)
						} else {
							if ((valAt(y, cur) == valAt(y, cur-1)) && !(mergeBoard[y][cur-1] || mergeBoard[y][cur])){
								shifted = true;
								mergeBoard[y][cur-1] = true; // we can no longer merge again on this square during this shift
								tile = new Tile(valAt(y, cur) + valAt(y, cur-1)); // new tile, twice as big
								remove(y, cur); // merging consists of deleting the old tiles
								add(tile, y, cur-1); // and replacing the right one with the doubled tile.
							}
						}
					}
				}
			}
		}
		return shifted;
	}

	// shifts all current tiles on the board upwards, if possible, and
	// merging when necessary
	private boolean shiftUp(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;

		boolean shifted = false;

		// let x be the index of the current column
		for (int x=0; x<SIZE; x++){
			// let y be the index of the current row
			for (int y=1; y<SIZE; y++){
			
				// is there a Tile at the current coordinate?
				if (!emptyAt(y, x)){
					
					// until there is nolonger somewhere to move the tile
					for (cur = y; exists(cur-1, x); cur--){
						// check if we can move it
						if (emptyAt(cur-1, x)){
							shifted = true;
							tile = new Tile(valAt(cur, x));
							remove(cur, x); // moving consists of deleting
							add(tile, cur-1, x); // and placing (original object dies, but that is ok)
						} else { 
							if ((valAt(cur,x) == valAt(cur-1, x)) && !(mergeBoard[cur-1][x] || mergeBoard[cur][x])){
								shifted = true;
								mergeBoard[cur-1][x] = true; // we can nolonger merge again on this square during this shift
								tile = new Tile(valAt(cur, x) + valAt(cur-1, x)); // new tile, twice as big
								remove(cur, x); // merging consists of deleting the old tiles
								add(tile, cur-1, x); // and replacing the right one with the doubled tile
							}
						}
					}
				}
			}
		}
		return shifted;
	}

	// shifts all current tiles on the board downwards, if possible, and
	// merging when necessary
	private boolean shiftDown(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;
		boolean shifted = false;

		// let x be the index of the current column
		for (int x=0; x<SIZE; x++){
			// let y be the index of the current row
			for (int y=SIZE-2; y>=0; y--){
				
				// is there a Tile at the current coordinate?
				if (!emptyAt(y, x)){
					
					// until there is nolonger somewher to move the tile
					for (cur = y; exists(cur+1, x); cur++){
						if (emptyAt(cur+1, x)){
							shifted = true;
							tile = new Tile(valAt(cur, x));
							remove(cur, x); // moving consists of deleting
							add(tile, cur+1, x); // moving consists of deleting
						} else {
							if ((valAt(cur, x) == valAt(cur+1, x)) && !(mergeBoard[cur+1][x] || mergeBoard[cur][x])){
								shifted = true;
								mergeBoard[cur+1][x] = true; // we can nolonger merge again on this square during this shift
								tile = new Tile(valAt(cur, x) + valAt(cur+1, x)); // new tile, twice as big
								remove(cur, x); // merging consists of deleting the old tiles
								add(tile, cur+1, x); // and replacing the right one with the doubled tile
							}
						}
					}
				}
			}
		}
		return shifted;
	}
	
	// determines if a specified position is available with the current board's
	// dimensions
	private boolean exists(int y, int x){
		return (y < SIZE && y >= 0 && x < SIZE && x >= 0);
	}

	// obtains the value of the tile at a specified position on the board
	protected int valAt(int y, int x){
		if (emptyAt(y,x)) {
			return 0;
		}
		return board[y][x].getVal();
	}
	
	// calculates the sum of all current tiles on the board
	protected int boardValue(){
		int sum =0;
		for (int i=0; i<TILES; i++){
			sum += valAt(i / SIZE, i % SIZE);
		}
		return sum;
	}
	
}
