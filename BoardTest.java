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
        // Check that position 7 is initially empty
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}		
        assertTrue("Position 7 should be empty initially", emptyBoard.emptyAt(7));

        // Add a tile at position 7 and verify
        emptyBoard.add(new Tile(2), 1, 3);

        assertFalse("Position 7 should not be empty after adding a tile", emptyBoard.emptyAt(7));
    }

    @Test
    public void testUpdate() {
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}
		
    	emptyBoard.add(new Tile(2), 0, 0); // Add tile to (0, 0)
    	emptyBoard.add(new Tile(2), 0, 1); // Add tile to (0, 1)

        // Perform update to the right
        assertTrue("Update should return true for successful movement", emptyBoard.update("right"));
        assertEquals("Tile at (0, 3) should be 4 after merging", 4, emptyBoard.valAt(0, 3));
    }
    
    @Test
    public void testShiftRight() {
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}
		
		emptyBoard.add(new Tile(2), 0, 0); // (0, 0)
    	emptyBoard.add(new Tile(2), 0, 1); // (0, 1)
    	
        assertTrue("Shift rght should merge tiles", emptyBoard.update("right"));
        assertEquals("Tile at (0, 3) should be 4 after merging", 4, emptyBoard.valAt(0, 3));
    }

    @Test
    public void testShiftLeft() {
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}
		
    	emptyBoard.add(new Tile(2), 0, 0); // (0, 0)
    	emptyBoard.add(new Tile(2), 0, 1); // (0, 1)
    	
        assertTrue("Shift left should merge tiles", emptyBoard.update("left"));
        assertEquals("Tile at (0, 0) should be 4 after merging", 4, emptyBoard.valAt(0, 0));
    }
    
    @Test
    public void testShiftUp() {
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}
		
    	emptyBoard.add(new Tile(2), 0, 0); // (0, 0)
    	emptyBoard.add(new Tile(2), 1, 0); // (1, 0)
    	
        assertTrue("Shift up should merge tiles", emptyBoard.update("up"));
        assertEquals("Tile at (0, 0) should be 4 after merging", 4, emptyBoard.valAt(0, 0));
    }
    
    @Test
    public void testShiftDown() {
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}
		
    	emptyBoard.add(new Tile(2), 0, 0); // (0, 0)
    	emptyBoard.add(new Tile(2), 1, 0); // (1, 0)
    	
        assertTrue("Shift down should merge tiles", emptyBoard.update("down"));
        assertEquals("Tile at (3, 0) should be 4 after merging", 4, emptyBoard.valAt(3, 0));
    }

    @Test
    public void testRemove() {
    	emptyBoard = new Board();
		for (int row = 0; row < emptyBoard.getSize(); row++) {
			for (int col = 0; col < emptyBoard.getSize(); col++) {
				if (!emptyBoard.emptyAt(col, row)) {
					emptyBoard.remove(col, row);
				}
			}
		}
		
    	emptyBoard.add(new Tile(2), 0, 0); // Add tile at (0, 0)
    	emptyBoard.remove(0, 0); // Remove it
        assertTrue("Tile at (0, 0) should be removed", emptyBoard.emptyAt(0, 0));
    }

}
