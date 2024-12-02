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
	// No output means the test went well
	public static void main(String args[]){
		System.out.println("-----TEST 1-----");
		testAddRandomTile();

		System.out.println("-----TEST 2-----");
		testEmptyAt();
		
		System.out.println("-----TEST 3-----");
		testShiftRight();

		System.out.println("-----Test 4-----");
		testShiftLeft();

		System.out.println("-----Test 5-----");
		testShiftUp();

		System.out.println("-----Test 6-----");
		testShiftDown();
	}

	private static void testAddRandomTile(){
		Board testBoard = new Board();

		while (!testBoard.isFull()){
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

	private static void testShiftRight(){
		Board testBoard = new Board();
		// regular merging
		testBoard.add(new Tile(2), 0, 0);
		testBoard.add(new Tile(2), 0, 1);
		

		// testing the "no multiple merges on the same spot" rule
		testBoard.add(new Tile(2), 1, 3);
		testBoard.add(new Tile(2), 1, 2);
		testBoard.add(new Tile(4), 1, 1);
	
		// regular not-merging
		testBoard.add(new Tile(2), 2, 3);
		testBoard.add(new Tile(4), 2, 2);
		
		int currentSum = testBoard.boardValue();
		testBoard.update("right");
		// this test will become invalid once the update function is finished
		if (currentSum != testBoard.boardValue()) System.out.println("The sum of values on the board changed post-update");
		
		if (testBoard.tileCount() != 5) System.out.println("After shifting right, the tile count was not 5");
		

		testBoard.printBoard();
	}

	private static void testShiftLeft(){
		Board testBoard = new Board();
		// regular merging
		testBoard.add(new Tile(2), 0, 0);
		testBoard.add(new Tile(2), 0, 1);

		// testing the "no multiple merges on the same spot" rule
		testBoard.add(new Tile(4), 1, 3);
		testBoard.add(new Tile(2), 1, 2);
		testBoard.add(new Tile(2), 1, 1);
		
		// regular not-merging
		testBoard.add(new Tile(2), 2, 3);
		testBoard.add(new Tile(4), 2, 2);

		int currentSum = testBoard.boardValue();
		testBoard.update("left");
		// this test will become invalid once the update function is finished
		if (currentSum != testBoard.boardValue()) System.out.println("The sum of values on the board chaned post-update"); // because random tile addition
		if (testBoard.tileCount() != 5) System.out.println("After shifting right, the tile count was not 5");
		
		testBoard.printBoard();
	}

	private static void testShiftUp(){
		Board testBoard = new Board();
		// regular merging
		testBoard.add(new Tile(2), 0, 0);
		testBoard.add(new Tile(2), 1, 0);

		// testing the "no multiple merges on the same spot" rule
		testBoard.add(new Tile(4), 3, 1);
		testBoard.add(new Tile(2), 2, 1);
		testBoard.add(new Tile(2), 1, 1);

		// regular not-merging
		testBoard.add(new Tile(2), 3, 2);
		testBoard.add(new Tile(4), 2, 2);

		int currentSum = testBoard.boardValue();
		testBoard.update("up");
		// this test will become invalid once the update function is finished
		if (currentSum != testBoard.boardValue()) System.out.println("The sum of values on the board changed post-update"); // because random tile addition
		if (testBoard.tileCount() != 5) System.out.println("After shifting right, the tile count was not 5");
		testBoard.printBoard();
	}

	private static void testShiftDown(){
		Board testBoard = new Board();
		// regular merging
		testBoard.add(new Tile(2), 0, 0);
		testBoard.add(new Tile(2), 1, 0);

		// testing the 'no multiple merges on the same spot" rule
		testBoard.add(new Tile(4), 3, 1);
		testBoard.add(new Tile(2), 2, 1);
		testBoard.add(new Tile(2), 1, 1);

		// regular not-merging
		testBoard.add(new Tile(2), 3, 2);
		testBoard.add(new Tile(4), 2, 2);

		int currentSum = testBoard.boardValue();
		// this test will become invalid once the update function is finished
		if (currentSum != testBoard.boardValue()) System.out.println("The sum of values on the board changed post-update");
		if (testBoard.tileCount() !=5) System.out.println("After shifting right, the tile count was not 5");
		testBoard.printBoard();
	}

	public Board(){
		board = new Tile[SIZE][SIZE];
		
		// Adds the first two tiles
		addRandomTile();
		addRandomTile();
		
	}

	// method for when user wants to control size of board
	public Board(int size){
		SIZE = size;
		TILES = size*size;
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

	private int boardValue(){
		int sum =0;
		for (int i=0; i<TILES; i++){
			sum += valAt(i / SIZE, i % SIZE);
		}
		
		return sum;
	}

	private int tileCount(){
		int count = 0;
		for (int i=0; i<TILES; i++){
			if (!emptyAt(i)) count++;
		}

		return count;
	}

	private boolean emptyAt(int y, int x){
		return board[y][x] == null;
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

	private boolean isEmpty(){
		for (int i=0; i<TILES; i++){
			if (!emptyAt(i)) return false;
		}

		return true;
	}

	private boolean isFull(){
		for (int i=0; i<TILES; i++){
			if (emptyAt(i)) return false;
		}

		return true;
	}

	// for the sake of encapsulation, we should make sure that there is no way to "set" the values of a tile
	// The original plan was to replace with a new tile on merge, and I am still going with this plan, but I haven't looked at Tile.java
	public Tile[][] getBoardState(){
		Tile[][] boardState = new Tile[SIZE][SIZE];

		for (int i=0; i<SIZE; i++){
			for (int j=0; j<SIZE; j++){
				boardState[i][j] = new Tile(valAt(i,j));
			}
		}

		return boardState;
	}
	// precondition: direction in {"right", "left", "up", "down"}
	public void update(String direction){
		switch (direction){
			case "right":
				shiftRight();
				break;
			case "left":
				shiftLeft();
				break;
			case "up":
				shiftUp();
				break;
			case "down":
				shiftDown();
				break;
			default:
				//this should not occur
				return;

		}
	}
	
	// for future reference. please never use x and y. use rows and columns. x and y gets real confusing real fast
	private void shiftRight(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;

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
							tile = new Tile(valAt(y,cur));
							remove(y,cur); // moving consists of deleting
							add(tile, y, cur+1); // and placing (original object dies, but that is ok)	
						} else {
							// there is a tile in the way...
							// can we merge?
							if ((valAt(y, cur) == valAt(y, cur+1)) && !mergeBoard[y][cur+1]){
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
	}

	private void shiftLeft(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;

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
							tile = new Tile(valAt(y,cur));
							remove(y,cur); // moving consists of deleting
							add(tile, y, cur-1); // and placing (original object dies, but that is ok)
						} else {
							if ((valAt(y, cur) == valAt(y, cur-1)) && !mergeBoard[y][cur-1]){
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
	}

	private void shiftUp(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;

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
							tile = new Tile(valAt(cur, x));
							remove(cur, x); // moving consists of deleting
							add(tile, cur-1, x); // and placing (original object dies, but that is ok)
						} else { 
							if ((valAt(cur,x) == valAt(cur-1, x)) && !mergeBoard[cur-1][x]){
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
	}

	private void shiftDown(){
		boolean[][] mergeBoard = new boolean[SIZE][SIZE];
		int cur;
		Tile tile;

		// let x be the index of the current column
		for (int x=0; x<SIZE; x++){
			// let y be the index of the current row
			for (int y=SIZE-2; y>=0; y--){
				
				// is there a Tile at the current coordinate?
				if (!emptyAt(y, x)){
					
					// until there is nolonger somewher to move the tile
					for (cur = y; exists(cur+1, x); cur++){
						if (emptyAt(cur+1, x)){
							tile = new Tile(valAt(cur, x));
							remove(cur, x); // moving consists of deleting
							add(tile, cur+1, x); // moving consists of deleting
						} else {
							if ((valAt(cur, x) == valAt(cur+1, x)) && !mergeBoard[cur+1][x]){
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
	}
	private void remove(int y, int x){
		board[y][x] = null;
	}

	private void add(Tile tile, int y, int x){
		board[y][x] = tile;
	}

	private int valAt(int y, int x){
		if (emptyAt(y,x)) return 0;
		return board[y][x].getVal();
	}

	private boolean exists(int y, int x){
		return (y < SIZE && y >= 0 && x < SIZE && x >= 0);
	}

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


}
