package com.cuuuurzel.gollivewallpaper;

public class GameOfLife {

	public Cell[][] grid;
	
	public GameOfLife( int rows, int cols ) {
		grid = new Cell[rows][cols];
		this.initGrid( rows, cols );
	}

	public void initGrid( int rows, int cols ) {
		for ( int r=0; r<rows; r++ ) {
			Cell[] row = new Cell[cols];
			for ( int c=0; c<cols; c++ ) {
				row[c] = new Cell();
			}
			grid[r] = row;
		}
		for ( int r=0; r<rows; r++ ) {
			for ( int c=0; c<cols; c++ ) {
				grid[r][c].setNeighboors( r, c, grid );
			}
		}

		grid[5][5].isAlive = true;
		grid[6][5].isAlive = true;
		grid[7][5].isAlive = true;
	} 
	
	public void update() {
		for ( int r=0; r<grid.length; r++ ) {
			for ( int c=0; c<grid[0].length; c++ ) {
				grid[r][c].prepare();
			}
		}
		for ( int r=0; r<grid.length; r++ ) {
			for ( int c=0; c<grid[0].length; c++ ) {
				grid[r][c].update();
			}
		}
	}
	
	public String toString() {
		String s = "GAME OF LIFE STATE : \n";
		String sr;
		for ( int r=0; r<grid.length; r++ ) {
			sr = "| ";
			for ( int c=0; c<grid[0].length; c++ ) {
				sr += grid[r][c] + " | ";
			}
			s += sr + "\n";
		}
		return s;
	}
}