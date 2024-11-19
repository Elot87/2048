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
	private String color;	// tiles of different values are different colors
	
	public void Tile(int val) {
		this.val = val;
		
		// determine color of tile based on val
		switch(val) {
		case 2:
			// color for tiles of val 2
		case 4:
			// color for tiles of val 4
		case 8:
			// color for tiles of val 8
		case 16:
			// color for tiles of val 16
		case 32:
			// color for tiles of val 32
		case 64:
			// color for tiles of val 64
		case 128:
			// color for tiles of val 128
		case 256:
			// color for tiles of val 256
		case 512:
			// color for tiles of val 512
		case 1024:
			// color for tiles of val 1024
		case 2048:
			// color for tiles of val 2048
		}	
	}
	
	/*
	 * Getter method for the val of a tile.
	 * 
	 * @return The value of a tile.
	 */
	public int getVal() {
		return val;
	}
	
	/*
	 * Getter method for the color of a tile.
	 * 
	 * @return The color of a tile.
	 */
	public String getColor() {
		return color;
	}
}
