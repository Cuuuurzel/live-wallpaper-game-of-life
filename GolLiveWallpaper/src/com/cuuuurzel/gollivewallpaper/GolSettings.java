package com.cuuuurzel.gollivewallpaper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GolSettings extends Activity {
	
	String path = "sdcard/.gollivewallpaper/config";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView( R.layout.settings );
	}

	private GolSwitcherGrid getGrid() {
		return (GolSwitcherGrid) findViewById( R.id.grid );		
	}
	
	public void incRows( View v ) {
		GolSwitcherGrid grid = getGrid();
		grid.setSize( grid.rows()+1, grid.cols() );
	}
	
	public void incCols( View v ) {
		GolSwitcherGrid grid = getGrid();
		grid.setSize( grid.rows(), grid.cols()+1 );
	}
	
	public void decRows( View v ) {
		GolSwitcherGrid grid = getGrid();
		grid.setSize( grid.rows()-1, grid.cols() );
	}
	
	public void decCols( View v ) {
		GolSwitcherGrid grid = getGrid();
		grid.setSize( grid.rows(), grid.cols()-1 );
	}

	/**
	 * Save a file to the path.
	 * File format :
	 * n of rows, n of columns[, rowN, colM, rowX, colY, ...]
	 * Where the cells indicated are active. 
	 */
	public void saveConfig( View v ) {
		try {
			GolSwitcherGrid grid = getGrid();
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream( path ) );
			out.writeInt( grid.rows() );
			out.writeInt( grid.cols() );
			for ( int r=0; r<grid.rows(); r++ ) {
				for ( int c=0; c<grid.cols(); c++ ) {
					if ( grid.isAlive( r, c ) ) {
						out.writeInt( r );
						out.writeInt( c );
					}
				}
			}
		} catch ( IOException e ) {}
	}
}