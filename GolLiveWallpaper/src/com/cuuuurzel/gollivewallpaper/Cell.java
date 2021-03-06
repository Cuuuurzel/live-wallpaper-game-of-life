package com.cuuuurzel.gollivewallpaper;

import java.util.ArrayList;

public class Cell {

	boolean isAlive = false;
	boolean nextState = false;
	ArrayList<Cell> neighboors;
	int r, c;
	
	public void setNeighboors( int r, int c, Cell[][] grid ) {
		int tr, tc;
		neighboors = new ArrayList<Cell>();
		for (int ro = -1; ro < 2; ro++) {
			for (int co = -1; co < 2; co++) {
				try {
					neighboors.add( grid[r+ro][c+co] );
				} catch ( IndexOutOfBoundsException e ) {
					tr = r + ro;
					tc = c + co;
					if ( r+ro == -1 ) tr = grid.length-1;
					if ( c+co == -1 ) tc = grid[0].length-1;
					if ( r+ro == grid.length ) tr = 0;
					if ( c+co == grid[0].length ) tc = 0;
					neighboors.add( grid[tr][tc] );					
				}
			}
		}
		neighboors.remove( grid[r][c] );
		this.r = r;
		this.c = c;
	}
	

	public void prepare() {
		int n = 0;
		for (int i = 0; i < neighboors.size(); i++) {
			if ( neighboors.get(i).isAlive ) n++;
		}

		nextState = false;
		if ( isAlive ) {
			if ( n == 2 || n == 3 ) nextState = true;
		} else {
			if ( n == 3 ) nextState = true; 
		}
	}
	public void update() {
		isAlive = nextState;
	}
	
	public String toString() {
		if ( isAlive ) return "X"; 
		else return "_";
	}
}