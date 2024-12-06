/*
 * File: Tile.java
 * Authors: Elliott Cepin, Yashi Gupta, Aarush Parvataneni, Alex Salgado
 * 
 * This class encapsulates a tile's details. Encapsulation is maintained by
 * keeping all instance variables private and providing getter methods only to
 * access the tile's information. These getter methods ensure controlled access.
 */


public class Tile {
	private int val;
	
	/*
	 * Constructs a new tile with a given value and assigns a color to the tile
	 * based on its value.
	 * 
	 * @param val The value (int) of the tile.
	 */
	public Tile(int val) {
		this.val = val;
	}
	
	/*
	 * Getter method for the val of a tile.
	 * 
	 * @return The value of a tile.
	 */
	public int getVal() {
		return val;
	}
}
