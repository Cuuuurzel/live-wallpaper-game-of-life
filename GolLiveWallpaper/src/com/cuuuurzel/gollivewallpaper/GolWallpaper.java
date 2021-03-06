package com.cuuuurzel.gollivewallpaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Environment;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

public class GolWallpaper extends WallpaperService {

	private final Handler mHandler = new Handler();
	int sw, sh;

	@Override
	public Engine onCreateEngine() {
		return new GolEngine();
	}

	class GolEngine extends Engine {

		private final Paint mPaint;
		private GameOfLife game;
		int fps = 2;

		private final Runnable mSampleDraw = new Runnable() {
			public void run() {
				drawFrame();
			}
		};

		private boolean mVisible;

		public GolEngine() {
			mPaint = new Paint();
			mPaint.setStyle(Style.FILL_AND_STROKE);
			mPaint.setColor(0xFFFFFFFF);
			DisplayMetrics metrics = getBaseContext().getResources()
					.getDisplayMetrics();
			float r = metrics.heightPixels / Float.valueOf(metrics.widthPixels);
			game = new GameOfLife((int) (12 * r), 12);
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
			// By default we don't get touch events, so enable them.
			setTouchEventsEnabled(true);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mSampleDraw);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mSampleDraw);
			}
			fps = game.setup(Environment.getExternalStorageDirectory() + "/"
					+ GolSettingsGrid.path);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			drawFrame();
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mSampleDraw);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			drawFrame();
		}

		/*
		 * Draw one frame of the animation. This method gets called repeatedly
		 * by posting a delayed Runnable. You can do any drawing you want in
		 * here.
		 */
		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					realDraw(c);
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			// Reschedule the next redraw
			mHandler.removeCallbacks(mSampleDraw);
			if (mVisible) {
				try {
					mHandler.postDelayed(mSampleDraw, 1000 / fps);
				} catch (ArithmeticException e) {
					mHandler.postDelayed(mSampleDraw, 60000);
				}
			}
		}

		void realDraw(Canvas cnv) {
			float w = cnv.getWidth();
			float h = cnv.getHeight();

			cnv.save();
			if (h < w) {
				cnv.rotate(90);
				cnv.translate(0, -w);
				float t = w;
				w = h;
				h = t;
			}

			float d = Math.max(h / game.grid.length, w / game.grid[0].length);

			cnv.drawColor(Color.BLACK);

			for (int r = 0; r < game.grid.length; r++) {
				for (int c = 0; c < game.grid[0].length; c++) {
					if (game.grid[r][c].isAlive) {
						cnv.drawRect(d * c, d * r, d * (c + 1), d * (r + 1),
								mPaint);
					}
				}
			}
			cnv.restore();
			game.update();
		}
	}
}
