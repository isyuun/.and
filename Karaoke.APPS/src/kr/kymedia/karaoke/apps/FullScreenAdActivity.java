/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 2014 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.PLAY4.APP
 * filename	:	FullScreenActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.play4.app
 *    |_ FullScreenActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

import kr.kymedia.karaoke.api.Log;

/**
 * <pre>
 * 풀스크린기능
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 10. 2.
 * @version 1.0
 */
public class FullScreenAdActivity extends _BaseAdActivity implements OnTouchListener, GestureDetector.OnDoubleTapListener {

	static int TIMER_SYSTEM_UI_REFRESH = 5000;
	private int visibility = 0;
	private int fullVisibility = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			visibility = getDecorView().getSystemUiVisibility();
		}

	}

	@SuppressLint("InlinedApi")
	void setTranslucentSystemUI(boolean enabled) {
		// Log.i(toString(), getMethodName() + enabled);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (enabled) {
				Window w = getWindow(); // in Activity's onCreate() for instance
				w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			} else {
				final WindowManager.LayoutParams attrs = getWindow().getAttributes();
				attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				getWindow().setAttributes(attrs);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			}
		}
	}

	private View getDecorView() {
		return getWindow().getDecorView();
	}

	private boolean isShowSystemUI = true;

	public boolean isShowSystemUI() {
		return isShowSystemUI;
	}

	private boolean enabledFullScreen = false;

	@SuppressLint("InlinedApi")
	private void enableFullScreen(final boolean enabled) {
		try {
			this.enabledFullScreen = enabled;

			fullVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
			fullVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
			fullVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
			fullVisibility |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

			enableFullScreenImmersive(enabled);
			// enableFullScreenLeanback(enabled);
			// enableFullScreenImmersiveSticky(enabled);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void onSystemUiVisibilityChange(int visibility) {

	}

	private Runnable enableFullScreenImmersive = new Runnable() {

		@Override
		public void run() {

			enableFullScreenImmersive(true);
		}
	};

	@SuppressLint({ "InlinedApi", "NewApi" })
	private void enableFullScreenImmersive(boolean enabled) {
		// Log.i(toString(), getMethodName() + enabled);

		this.enabledFullScreen = enabled;

		int newVisibility = this.visibility;

		getDecorView().setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {

			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				isShowSystemUI = ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0);
				Log.e(toString(), getMethodName() + visibility + ", isShowSystemUI:" + isShowSystemUI);


				if (enabledFullScreen) {
					if (isShowSystemUI) {
						// in show
						showActionBar();
						resetHideTimer(enableFullScreenImmersive);
					} else {
						// in hide
						hideActionBar();
					}
				}

				FullScreenAdActivity.this.onSystemUiVisibilityChange(visibility);
			}
		});

		if (enabled) {
			newVisibility |= fullVisibility;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				newVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE;
			}

		}

		getDecorView().setSystemUiVisibility(newVisibility);
	}

	private Runnable enableFullScreenImmersiveSticky = new Runnable() {

		@Override
		public void run() {

			enableFullScreenImmersiveSticky(true);
		}
	};

	@Deprecated
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void enableFullScreenImmersiveSticky(boolean enabled) {
		// Log.i(toString(), getMethodName() + enabled);

		this.enabledFullScreen = enabled;

		int newVisibility = this.visibility;

		getDecorView().setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {

			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				isShowSystemUI = ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0);
				Log.e(toString(), getMethodName() + visibility + ":" + isShowSystemUI);


				if (enabledFullScreen) {
					if (isShowSystemUI) {
						// in show
						showActionBar();
						resetHideTimer(enableFullScreenImmersiveSticky);
					} else {
						// in hide
						hideActionBar();
					}
				}

				FullScreenAdActivity.this.onSystemUiVisibilityChange(visibility);
			}
		});

		if (enabled) {
			newVisibility |= fullVisibility;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				newVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			}
		}

		getDecorView().setSystemUiVisibility(newVisibility);
	}

	private void resetHideTimer(Runnable r) {
		// First cancel any queued events - i.e. resetting the countdown clock
		removeCallbacks(r);
		// And fire the event in 3s time
		if (enabledFullScreen) {
			postDelayed(r, TIMER_SYSTEM_UI_REFRESH);
		}
	}

	private final Runnable enableFullScreenLeanback = new Runnable() {

		@Override
		public void run() {

			enableFullScreenLeanback(true);

		}
	};

	@Deprecated
	@SuppressLint({ "InlinedApi", "NewApi" })
	private void enableFullScreenLeanback(boolean enabled) {
		// Log.i(toString(), getMethodName() + enabled);

		this.enabledFullScreen = enabled;

		int newVisibility = this.visibility;

		getDecorView().setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {

			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				isShowSystemUI = ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0);
				Log.e(toString(), getMethodName() + visibility + ":" + isShowSystemUI);


				if (enabledFullScreen) {
					if (isShowSystemUI) {
						// in show
						showActionBar();
						resetHideTimer(enableFullScreenLeanback);
					} else {
						// in hide
						hideActionBar();
					}
				}

				FullScreenAdActivity.this.onSystemUiVisibilityChange(visibility);
			}
		});

		if (enabled) {
			newVisibility |= fullVisibility;
		}

		// Set the visibility
		getDecorView().setSystemUiVisibility(newVisibility);
	}

	private boolean isFullScreen = false;

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public void setFullScreen(boolean enabled) {
		// Log.i(toString(), getMethodName());

		isFullScreen = enabled;
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// enableFullScreen(enabled);
		// } else {
		// if (enabled) {
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// } else {
		// getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// }
		// }

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			enableFullScreen(enabled);
		} else {
			if (enabled) {
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			} else {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
		}
	}

	private boolean isNoTitle = false;

	@SuppressLint("NewApi")
	protected void setNoTitle() {
		// Log.i(toString(), getMethodName());

		isNoTitle = true;
		try {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
				requestWindowFeature(Window.FEATURE_NO_TITLE);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private boolean isShowActionBar = true;

	public boolean isShowActionBar() {
		return isShowActionBar;
	}

	Runnable hideActionBar = new Runnable() {

		@Override
		public void run() {

			getSupportActionBar().hide();

		}
	};

	@SuppressLint("NewApi")
	protected void hideActionBar() {
		// Log.i(toString(), getMethodName());

		isShowActionBar = false;
		try {
			// removeCallbacks(hideActionBar);
			// postDelayed(hideActionBar, 100);
			getSupportActionBar().hide();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	Runnable showActionBar = new Runnable() {

		@Override
		public void run() {

			getSupportActionBar().show();
		}
	};

	@SuppressLint("NewApi")
	protected void showActionBar() {
		// Log.i(toString(), getMethodName());

		isShowActionBar = true;
		try {
			// removeCallbacks(showActionBar);
			// postDelayed(showActionBar, 100);
			getSupportActionBar().show();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private boolean isActionBarOverlay = false;

	@SuppressLint("InlinedApi")
	protected void setActionBarOverlay(boolean enabled) {
		// Log.i(toString(), getMethodName() + enabled);

		isActionBarOverlay = enabled;

		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		// if (enabled) {
		// getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		// } else {
		// getWindow().clearFlags(Window.FEATURE_ACTION_BAR_OVERLAY);
		// }
		// } else {
		// getWindow().requestFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
		// }
		if (enabled) {
			getWindow().requestFeature(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
		} else {
			getWindow().clearFlags(WindowCompat.FEATURE_ACTION_BAR_OVERLAY);
		}

	}

	@Override
	public void setContentView(int layoutResID) {

		super.setContentView(layoutResID);

		// if (isActionBarOverlay) {
		// getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00212121")));
		// }
	}

	void setResume() {
		if (isFullScreen) {
			setFullScreen(isFullScreen);
		}

		if (isNoTitle) {

		}

		if (isShowActionBar) {
			showActionBar();
		} else {
			hideActionBar();
		}
	}

	@Override
	protected void onResume() {

		super.onResume();

		setResume();
	}

	private GestureDetector gestureDetector;

	@Override
	protected void onStart() {
		// Log.e(toString(), getMethodName());

		super.onStart();

		// ViewGroup g = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
		// g = (ViewGroup) getWindow().getDecorView().getRootView();
		// //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
		// // g = (ViewGroup) getWindow().getDecorView().getRootView();
		// //}
		// g.setOnTouchListener(this);
		// WidgetUtils.setOnTouchListener(getApplicationContext(), g, this);
		if (getCurrentFragment() != null) {
			if (getCurrentFragment().getRootView() != null) {
				getCurrentFragment().getRootView().setOnTouchListener(this);
			} else {
				getCurrentFragment().getRootView().setOnTouchListener(this);
			}
		}

		gestureDetector = new GestureDetector(this, new GestureListener());
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// Log.e(toString(), getMethodName() + v + event);


		// if (v == mRootView && event.getAction() == MotionEvent.ACTION_DOWN) {
		// ((_Activity) getActivity()).enableFullScreen();
		// }

		return gestureDetector.onTouchEvent(event);
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDoubleTap(MotionEvent e) {

			FullScreenAdActivity.this.onDoubleTap(e);
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {

			FullScreenAdActivity.this.onSingleTapConfirmed(e);
			return super.onSingleTapConfirmed(e);
		}

	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {

		// Log.e(toString(), getMethodName() + isShowSystemUI());
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {

		// Log.e(toString(), getMethodName() + isShowSystemUI());
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {

		return false;
	}

}
