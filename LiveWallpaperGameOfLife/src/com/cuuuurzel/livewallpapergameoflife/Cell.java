package com.cuuuurzel.livewallpapergameoflife;

import java.util.ArrayList;

public class Cell {

	boolean isAlive = false;
	boolean nextState = false;
	ArrayList<Cell> neighboors;
	
	public Cell( int r, int c, Cell[][] grid ) {
		neighboors = new ArrayList<Cell>();
		for ( int ro=-1; ro<2; ro++ ) {
			for ( int co=-1; co<2; co++ ) { 
				try {
					neighboors.add( grid[r+ro][c+co] );
				} catch ( IndexOutOfBoundsException e ) {}
			}
		}
		neighboors.remove( grid[r][c] );
	}
	 
	public void prepare() {
		int n = 0;
		for ( int i=0; i<neighboors.size(); i++ ) {
			if ( neighboors.get(i).isAlive ) n++;
		}		
		
		if ( this.isAlive ) {
			if ( n == 2 || n == 3 ) { 
				nextState = true;
			} else { 
				nextState = false;
			} 
		} else {
			if ( n == 3 ) nextState = true;
		}
	}
	
	public void update() {
		isAlive = nextState; 		
	}
}
