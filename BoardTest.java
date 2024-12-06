import org.junit.Test;
import static org.junit.Assert.*;
public class BoardTest {
	
	@Test
	public void testBoardInitialization() {
	    Board board = new Board();
	    int nonEmptyTiles = board.tileCount();
	    assertEquals("board should initialize with exactly two tiles", 2, nonEmptyTiles);
	}

	
	
	@Test
	public void testIsFull() {
	    Board board = new Board();
	    // Fill the board
	    for (int i = 0; i < 16; i++) {
	        board.addTile(new Tile(2), i); 
	    }
	    assertTrue("Board should be full", board.isFull());
	}

	@Test
    public void testAddRandomTile() {
        Board testBoard = new Board();

        // Keep adding tiles until the board is full
        while (!testBoard.isFull()) {
            assertTrue("Should be able to add a tile to a non-full board", testBoard.addRandomTile());
        }

        // Ensure no tiles can be added once the board is full
        assertFalse("Should not be able to add a tile to a full board", testBoard.addRandomTile());
    }

    @Test
    public void testEmptyAt() {
        Board testBoard = new Board();

        // Check that position 7 is initially empty
        assertTrue("Position 7 should be empty initially", testBoard.emptyAt(7));

        // Add a tile at position 7 and verify
        testBoard.addTile(new Tile(2), 7);
        assertFalse("Position 7 should not be empty after adding a tile", testBoard.emptyAt(7));
    }

 

    @Test
    public void testUpdate() {
        Board board = new Board();
        board.addTile(new Tile(2), 0); // Add tile to (0, 0)
        board.addTile(new Tile(2), 1); // Add tile to (0, 1)

        // Perform update to the right
        assertTrue("Update should return true for successful movement", board.update("right"));
        assertEquals("Tile at (0, 3) should be 4 after merging", 4, board.valAt(0, 3));
    }

  

    @Test
    public void testShiftLeft() {
        Board board = new Board();
        board.addTile(new Tile(2), 0); // (0, 0)
        board.addTile(new Tile(2), 1); // (0, 1)
        assertTrue("Shift left should merge tiles", board.update("left"));
        assertEquals("Tile at (0, 0) should be 4 after merging", 4, board.valAt(0, 0));
    }

    @Test
    public void testRemove() {
        Board board = new Board();
        board.addTile(new Tile(2), 0); // Add tile at (0, 0)
        board.remove(0, 0); // Remove it
        assertTrue("Tile at (0, 0) should be removed", board.emptyAt(0, 0));
    }
   



	


}
