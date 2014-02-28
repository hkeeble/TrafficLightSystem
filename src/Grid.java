/*
 * Represents a grid of crossroads
 */
public class Grid {
	int width, height;
	
	Crossroad[][] crossroads;
	
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		
	crossroads = new Crossroad[width][height];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				crossroads[x][y] = new Crossroad();
			}
		}
	}
	
	/**
	 * Returns the crossroad at the given location.
	 * @param x The x coordinate of the crossroad.
	 * @param y The y coordinate of the crossroad.
	 * @return The desired crossroad.
	 */
	public Crossroad Get(int x, int y) {
		return crossroads[x][y];
	}
	
	/**
	 * Returns the width of the grid.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the grid.
	 */
	public int getHeight() {
		return height;
	}
}
