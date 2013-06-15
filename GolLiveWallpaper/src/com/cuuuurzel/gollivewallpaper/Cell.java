package com.cuuuurzel.gollivewallpaper;

import java.util.ArrayList;

public class Cell {

	boolean isAlive = false;
	boolean nextState = false;
	ArrayList<int[]> neighboors;
	
	public void setNeighboors( int r, int c, Cell[][] grid ) {
		neighboors = new ArrayList<int[]>();
		for (int ro = -1; ro < 2; ro++) {
			for (int co = -1; co < 2; co++) {
				//TODO check index!!
				//if ( r+ro >= 0 && r+ro)
				neighboors.add( new int[]{ r+ro, c+co } );
			}
		}
		neighboors.remove(grid[r][c]);
	}
	

	public void prepare( Cell[][] grid ) {
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
		else return "O";
	}
}