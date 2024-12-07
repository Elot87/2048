import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
	private Board testBoard;
	private Board emptyBoard;
	
	
	@Test
	public void testBoardInitialization() {
		testBoard = new Board();

	    int nonEmptyTiles = testBoard.tileCount();
	    assertEquals("board should initialize with exactly two tiles", 2, nonEmptyTiles);
	}
	
	@Test 
	public void testInitializeBoard7by7() {
		testBoard = new Board(7);
		
		int nonEmptyTiles = testBoard.tileCount();
		assertEquals("7x7 board should initialize with exactly two tiles", 2, nonEmptyTiles);
	}
	
	@Test
	public void testIsFull() {
		testBoard = new Board();

	    // Fill the board
	    for (int i = 0; i < 16; i++) {
	    	testBoard.add(new Tile(2), i/4, i%4); 
	    }

	    assertTrue("Board should be full", testBoard.isFull());
	}

	@Test
    public void testAddRandomTile() {
		testBoard = new Board();

        // Keep adding tiles until the board is full
        while (!testBoard.isFull()) {
            assertTrue("Should be able to add a tile to a non-full board", testBoard.addRandomTile());
        }

        // Ensure no tiles can be added once the board is full
        assertFalse("Should not be able to add a tile to a full board", testBoard.addRandomTile());
    }

    @Test
    public void testEmptyAt() {
    	// initialize an empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}		
		// Check that position 7 is initially empty
        assertTrue("Position 7 should be empty initially", testBoard.emptyAt(7));

        // Add a tile at position 7 and verify
        testBoard.add(new Tile(2), 1, 3);

        assertFalse("Position 7 should not be empty after adding a tile", testBoard.emptyAt(7));
    }

    @Test
    public void testUpdate() {
    	// initialize an empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(2), 0, 0); // Add tile to (0, 0)
		testBoard.add(new Tile(2), 0, 1); // Add tile to (0, 1)

        // Perform update to the right
        assertTrue("Update should return true for successful movement", testBoard.update("right"));
        assertEquals("Tile at (0, 3) should be 4 after merging", 4, testBoard.valAt(0, 3));
    }
    
    @Test
    public void testShiftRight() {
    	// initialize an empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		// regular merging
		testBoard.add(new Tile(2), 0, 0);
		testBoard.add(new Tile(2), 0, 1); 
		
		// no multiple merges on the same spot
		testBoard.add(new Tile(2), 1, 3);
		testBoard.add(new Tile(2), 1, 2);
		testBoard.add(new Tile(4), 1, 1);
		
		// regular not-merging
		testBoard.add(new Tile(2), 2, 3);
		testBoard.add(new Tile(4), 2, 2);
    	
    	
        assertTrue("Shift rght should merge tiles", testBoard.update("right"));
        assertEquals("Tile at (0, 3) should be 4 after merging", 4, testBoard.valAt(0, 3));
        assertEquals("Tile at (1, 3) should be 4 after merging", 4, testBoard.valAt(1, 3));
        assertEquals("Tile at (1, 2) should be 4 after merging", 4, testBoard.valAt(1, 2));
        assertEquals("Tile at (2, 3) should be 2 after merging", 2, testBoard.valAt(2, 3));
        assertEquals("Tile at (2, 3) should be 4 after merging", 4, testBoard.valAt(2, 2));
    }

    @Test
    public void testShiftLeft() {
    	// initialize empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(2), 0, 0); // (0, 0)
		testBoard.add(new Tile(2), 0, 1); // (0, 1)
    	
        assertTrue("Shift left should merge tiles", testBoard.update("left"));
        assertEquals("Tile at (0, 0) should be 4 after merging", 4, testBoard.valAt(0, 0));
    }
    
    @Test
    public void testShiftUp() {
    	// initialize empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(2), 0, 0); // (0, 0)
		testBoard.add(new Tile(2), 1, 0); // (1, 0)
    	
        assertTrue("Shift up should merge tiles", testBoard.update("up"));
        assertEquals("Tile at (0, 0) should be 4 after merging", 4, testBoard.valAt(0, 0));
    }
    
    @Test
    public void testShiftDown() {
    	// initialize empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(2), 0, 0); // (0, 0)
		testBoard.add(new Tile(2), 1, 0); // (1, 0)
    	
        assertTrue("Shift down should merge tiles", testBoard.update("down"));
        assertEquals("Tile at (3, 0) should be 4 after merging", 4, testBoard.valAt(3, 0));
    }

    @Test
    public void testRemove() {
    	// initialize empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(2), 0, 0); // Add tile at (0, 0)
		testBoard.remove(0, 0); // Remove it
        assertTrue("Tile at (0, 0) should be removed", testBoard.emptyAt(0, 0));
    }
    
    @Test
    public void testBoardValue() {
    	// initialize empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(8), 0, 0);
		testBoard.add(new Tile(32), 0, 1);
		testBoard.add(new Tile(16), 0, 2);
		testBoard.add(new Tile(128), 0, 3);
		testBoard.add(new Tile(2), 1, 0);
		testBoard.add(new Tile(4), 1, 1);
		testBoard.add(new Tile(64), 1, 2);
		testBoard.add(new Tile(16), 1, 3);
		testBoard.add(new Tile(16), 2, 0);
		testBoard.add(new Tile(8), 2, 1);
		testBoard.add(new Tile(16), 2, 2);
		testBoard.add(new Tile(32), 2, 3);
		testBoard.add(new Tile(4), 3, 0);
		testBoard.add(new Tile(2), 3, 1);
		testBoard.add(new Tile(4), 3, 2);
		testBoard.add(new Tile(2), 3, 3);
		
		assertTrue(354 == testBoard.boardValue());
    }
    
    /*
    @Test
    public void testWinGame() {
    	// initialize empty board
    	testBoard = new Board();
		for (int row = 0; row < testBoard.getSize(); row++) {
			for (int col = 0; col < testBoard.getSize(); col++) {
				if (!testBoard.emptyAt(col, row)) {
					testBoard.remove(col, row);
				}
			}
		}
		
		testBoard.add(new Tile(2), 0, 0); // (0, 0)
    }
    */

}
