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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	playListenFragment3.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ playListenFragment3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.karaoke.widget.SeekBarVertical;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * TODO<br>
 * 
 * <pre>
 * 	자막위치
 * 자막싱크
 * </pre>
 *
 * @author isyoon
 * @since 2013. 11. 6.
 * @version 1.0
 * @see
 */
class playListenFragment3 extends playListenFragment2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private static float MAX_ALPHA = 1.0f;
	private static float MIN_ALPHA = 0.5f;

	/**
	 * 동기화최대치 = MAX_SYNC/2
	 */
	private static int MAX_SYNC = 10000;
	private int max_sync = 0;
	private int min_sync = 0;

	/**
	 * 자막동기화시간(msec)
	 */
	private int sync_time = 0;

	private View mSyncContainer = null;
	private SeekBarVertical mSyncSeekBar = null;
	private TextView mSyncTracker = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_listen4, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void player() {

		super.player();

		// 자막싱크
		mSyncContainer = findViewById(R.id.include_sync);
		setSyncCtrlTransfer(true);

		// mSyncTracker = new TrackerTime(getBaseActivity(), getResources().getColor(R.color.solid_white));
		mSyncTracker = (TextView) findViewById(R.id.sync_tracker);
		mSyncTracker.setVisibility(View.GONE);

		mSyncSeekBar = (SeekBarVertical) findViewById(R.id.sync_bar);
		mSyncSeekBar.setMax(MAX_SYNC);

		max_sync = mSyncSeekBar.getMax() / 2;
		min_sync = mSyncSeekBar.getMax() / 2 * -1;

		((TextView) findViewById(R.id.sync_max)).setText(TextUtil.getTimeTrackerString("", max_sync, true));
		((TextView) findViewById(R.id.sync_min)).setText(TextUtil.getTimeTrackerString("", min_sync, true));

		mSyncSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

				showPopupTime(true);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

				showPopupTime(false);

				setLyricSync(sync_time);

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				if (fromUser) {
					// sync = progress;
					sync_time = (progress - seekBar.getMax() / 2);

					updatePopupTime(getString(R.string.hint_lyric_sync_format), sync_time, true);
				}

			}
		});

		showPopupTime(false);

		// 자막싱크설정
		sync_time = getApp().getSharedPreferences().getInt("sync_time", 0);
		setLyricSync(sync_time);

		WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_sync),
				new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {

						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							setSyncCtrlTransfer(false);
							break;
						case MotionEvent.ACTION_MOVE:
							break;
						case MotionEvent.ACTION_UP:
							setSyncCtrlTransfer(true);
							break;
						default:
							break;
						}
						return false;
					}
				});

		WidgetUtils.setOnClickListener(mContext, (ViewGroup) findViewById(R.id.include_sync), new OnClickListener() {

			@Override
			public void onClick(View v) {

				// setSyncCtrlTransfer(true);

			}
		}, true);
	}

	@SuppressLint("NewApi")
	private void setSyncCtrlTransfer(boolean transfer) {
		float alpha = MAX_ALPHA;
		int background = R.drawable.btn_counter;

		if (transfer) {
			alpha = MIN_ALPHA;
			background = android.R.color.transparent;
		}

		try {
			if (mSyncContainer != null) {
				mSyncContainer.setBackgroundResource(background);
				if (_IKaraoke.IS_ABOVE_HONEYCOMB) {
					mSyncContainer.setAlpha(alpha);
				} else {
					mSyncContainer.getBackground().setAlpha((int) (alpha * 255));
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void showPopupTime(boolean show) {
		setSyncCtrlTransfer(!show);
		View parent = findViewById(R.id.sync_zero);
		if (mSyncTracker != null && parent != null) {
			// mSyncTracker.showPopupTime(parent, show);
			if (show) {
				mSyncTracker.setVisibility(View.VISIBLE);
			} else {
				mSyncTracker.setVisibility(View.INVISIBLE);
			}
		}
	}

	private void updatePopupTime(String format, int msec, boolean isHmsec) {
		if (mSyncTracker != null) {
			// mSyncTracker.updatePopupTime(getResources().getString(R.string.hint_lyric_sync_format), msec, isHmsec);
			// mSyncTracker.updatePopupTime("", sync, isHmsec);
			String pos = TextUtil.getTimeTrackerString(format, msec, isHmsec);
			mSyncTracker.setText(pos);
		}
	}

	@Override
	public void showPlayer() {

		super.showPlayer();

		// if (mSyncContainer != null) {
		// int margin = getView().getHeight()/10;
		// LayoutParams params = (LayoutParams) mSyncContainer.getLayoutParams();
		// params.topMargin = margin;
		// params.bottomMargin = margin;
		// mSyncContainer.setLayoutParams(params);
		// }
	}

	@Override
	public void showPlayerBar(boolean show) {

		super.showPlayerBar(show);

		if (mSyncContainer != null) {
			if (show) {
				mSyncContainer.setVisibility(View.VISIBLE);
			} else {
				mSyncContainer.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		if (v.getId() == R.id.sync_refresh) {
			setLyricSync(0);
		}

	}

	/**
	 * 
	 * <pre>
	 * 음양조화(프로그래스와까꾸로!!!)
	 * - : 빠르게
	 * + : 느리게
	 * </pre>
	 * 
	 * @param sync
	 */
	private void setLyricSync(int sync) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + sync);

		mSyncSeekBar.setProgress(sync + mSyncSeekBar.getMax() / 2);
		getPlayer().setLyricSync(sync * -1);

		this.sync_time = sync;

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mSyncSeekBar.getProgress());

		if (_IKaraoke.DEBUG) {
			getApp().popupToast(getPlayInfo(), Toast.LENGTH_LONG);
		}
	}

	@Override
	public void onDestroy() {

		SharedPreferences.Editor edit = getApp().getSharedPreferences().edit();

		edit.putInt("sync_time", sync_time);

		edit.commit();

		super.onDestroy();
	}

	@Override
	protected String getPlayInfo() {

		String ret = super.getPlayInfo();
		ret += "\n\tsync_time:" + sync_time;
		return ret;
	}

}
