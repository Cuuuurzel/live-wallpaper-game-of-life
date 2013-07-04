package com.cuuuurzel.gollivewallpaper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class GolSettings extends Activity {

	int fps, rows, cols;
	int[] gridState; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		File inf = new File(Environment.getExternalStorageDirectory(),
				GolSettingsGrid.path);
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					inf));
			this.fps = in.readInt();
			this.rows = in.readInt();
			this.cols = in.readInt();
			in.close();
		} catch (IOException e) {
			rows = 10;
			cols = 6;
			fps = 2;
		}
		setupForm(false);
	}

	public void incRows(View v) {
		this.rows++;
		setupForm(true);
	}

	public void incCols(View v) {
		this.cols++;
		setupForm(true);
	}

	public void decRows(View v) {
		if (rows > 10) {
			this.rows--;
		}
		setupForm(true);
	}

	public void decCols(View v) {
		if (cols > 6) {
			this.cols--;
		}
		setupForm(true);
	}

	public void setupForm(boolean justLabel) {
		TextView l = (TextView) findViewById(R.id.txvGridSize);
		l.setText(rows + " x " + cols);
		if (!justLabel) {
			SeekBar skb = (SeekBar) findViewById(R.id.seekBar1);
			skb.setProgress(this.fps - 1);
		}
	}

	public void showGrid(View v) {
		SeekBar skb = (SeekBar) findViewById(R.id.seekBar1);
		fps = skb.getProgress() + 1;
		Intent intentMain = new Intent(GolSettings.this, GolSettingsGrid.class);
		intentMain.putExtra("fps", fps);
		intentMain.putExtra("rows", rows);
		intentMain.putExtra("cols", cols);
		GolSettings.this.startActivityForResult(intentMain, 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			this.gridState = data.getIntArrayExtra( "alives" );
		}
	}

	public void saveAndExit(View v) {
		try {
			File outf = new File(Environment.getExternalStorageDirectory(),
					GolSettingsGrid.path );
			outf.createNewFile();
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(outf));

			out.writeInt( this.fps );
			out.writeInt( this.rows );
			out.writeInt( this.cols );
			try {
				for (int x=0; x<this.gridState.length; x++ ) {
					out.writeInt( gridState[x] );
				}
			} catch ( NullPointerException e ) {}
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		finish();
	}
}