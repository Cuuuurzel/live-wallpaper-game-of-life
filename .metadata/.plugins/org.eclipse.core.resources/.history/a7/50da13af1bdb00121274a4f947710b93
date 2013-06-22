package com.cuuuurzel.gollivewallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DummyWidget {

	float x, y, w, h;
	
	public DummyWidget( float x, float y, float w, float h ) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean collide( float px, float py ) {
		if ( x < px && px < x+w ) {
			if ( y < py && py < y+h ) {
				return true;
			}
		}		
		return false;
	}
	
	public void draw( Canvas c, Paint p ) {
		float sw = p.getStrokeWidth();
		p.setStrokeWidth( 5 );
		c.drawLine( x+w/3,   y, x+w/3,   y+h, p );
		c.drawLine( x+2*w/3, y, x+2*w/3, y+h, p );
		c.drawRect( 10, 10, 100, 100, p );
		p.setStrokeWidth( sw );
	}
}
