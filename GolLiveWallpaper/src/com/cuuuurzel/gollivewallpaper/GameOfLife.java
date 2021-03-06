package com.cuuuurzel.gollivewallpaper;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameOfLife {

	public Cell[][] grid;
	
	public GameOfLife( int rows, int cols ) {
		this.initGrid( rows, cols );
	}
	
	public void initGrid( int rows, int cols ) {
		grid = new Cell[rows][cols];
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
	
	public void clear() {
		for ( int r=0; r<grid.length; r++ ) {
			for ( int c=0; c<grid[0].length; c++ ) {
				grid[r][c].isAlive = false;
				grid[r][c].nextState = false;
			}	
		}
	}
	
	/**
	 * Min grid size is 3x3!  
	 */
	public void setSize( int rows, int cols ) {
		if ( rows < 3 ) rows = 3;
		if ( cols < 3 ) cols = 3;
		Cell[][] lastConfig = this.grid;		
		this.initGrid( rows, cols );
		
		for ( int r=0; r < Math.min( lastConfig.length, rows ); r++ ) {
			for ( int c=0; c < Math.min( lastConfig[0].length, cols ); c++ ) {
				this.grid[r][c].isAlive = lastConfig[r][c].isAlive;
			} 
		}
	}
	
	/**
	 * Load config from the indicated file.
	 * File format :
	 * n of rows, n of columns[, rowN, colM, rowX, colY, ...]
	 * Where the cells indicated are active.
	 */
	public int setup( String path ) {
		File inf = new File( path );
		try {
			ObjectInputStream in = new ObjectInputStream( new FileInputStream( inf ) );
			int fps = in.readInt();
			int rows = in.readInt();
			int cols = in.readInt();
			this.setSize( rows, cols );

			for ( int r=0; r<rows; r++ ) {
				for ( int c=0; c<cols; c++ ) {
					this.grid[r][c].isAlive = false;
				}				
			}
			try {
				int r, c;
				while ( true ) {
					r = in.readInt();
					c = in.readInt();
					this.grid[r][c].isAlive = true;
				}	
			} catch ( EOFException e ) {}
			in.close();	
			return fps;
		} catch ( IOException e ) {
			e.printStackTrace();
			this.setSize( 10, 6 );
			return 2;
		}	
	}
	
	public String toString() {
		String s = "GAME STATE : \n";
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

	public boolean isAlive(int r, int c) {
		return this.grid[r][c].isAlive;
		
	}
}