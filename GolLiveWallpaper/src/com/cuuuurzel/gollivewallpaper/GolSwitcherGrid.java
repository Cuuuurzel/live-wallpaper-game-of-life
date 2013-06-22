package com.cuuuurzel.gollivewallpaper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class GolSwitcherGrid extends View {

	private GameOfLife gameState;
	private Paint mPaint;
	
	public GolSwitcherGrid( Context context ) {
		this( context, null );
	}

	public GolSwitcherGrid( Context context, AttributeSet attrs ) {
		this( context, attrs, 0 );
	}

	public GolSwitcherGrid( Context context, AttributeSet attrs, int defStyle ) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
		mPaint.setStyle( Style.FILL_AND_STROKE );
		mPaint.setColor(0xFFFFFFFF);
		this.gameState = new GameOfLife( 5, 5 );
	}
	
	public void setSize( int rows, int cols ) {
		Cell[][] lastConfig = this.gameState.grid;		
		this.gameState = new GameOfLife( rows, cols );		
		
		for ( int r=0; r < Math.min( lastConfig.length, rows ); r++ ) {
			for ( int c=0; c < Math.min( lastConfig[0].length, cols ); c++ ) {
				this.gameState.grid[r][c].isAlive = lastConfig[r][c].isAlive;
			} 
		}
	}
	
	public int rows() {
		return this.gameState.grid.length;		
	}
	
	public int cols() {
		return this.gameState.grid[0].length;		
	}

	@Override
	public boolean onTouchEvent( MotionEvent m ) {
		System.out.println( "DIO CAN!" );
		int r = (int) ( ( m.getY() / this.getHeight() ) * this.rows() );
		int c = (int) ( ( m.getX() / this.getWidth() ) * this.cols() );
		this.gameState.grid[r][c].isAlive = !this.gameState.grid[r][c].isAlive;
		this.invalidate();
		return super.onTouchEvent( m );
	}
	
	@Override
	public void draw( Canvas cnv ) {			
		float w = cnv.getWidth();
		float h = cnv.getHeight();			
		
		float d = Math.min( h/gameState.grid.length, w/gameState.grid[0].length );

		cnv.drawColor( Color.BLACK );
		
		this.drawGrid( d, cnv );
		
		for (int r = 0; r < gameState.grid.length; r++) {
			for (int c = 0; c < gameState.grid[0].length; c++) {
				if (gameState.grid[r][c].isAlive) {
					cnv.drawRect( d*c, d*r, d*(c+1), d*(r+1), mPaint);
				}
			}
		}
		cnv.restore();
		super.draw( cnv );
	}
	
	private void drawGrid( float d, Canvas cnv ) {
		for (int r=0; r<gameState.grid.length+2; r++) {
			cnv.drawLine( 0, d*r, d*(gameState.grid[0].length+1), d*r, mPaint );
		}

		for (int c=0; c<gameState.grid[0].length+2; c++) {
			cnv.drawLine( d*c, 0, d*c, d*(gameState.grid.length+1), mPaint );
		}
	}
	
	public void clear() {
		this.gameState.clear();		
	}
	
}
