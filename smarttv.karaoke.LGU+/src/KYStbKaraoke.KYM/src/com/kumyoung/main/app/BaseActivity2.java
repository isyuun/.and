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
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	KYStbKaraoke
 * filename	:	BaseActivity2.java
 * author	:	isyoon
 *
 * <pre>
 * com.kumyoung.main
 *    |_ BaseActivity2.java
 * </pre>
 * 
 */

package com.kumyoung.main.app;

import kr.kymedia.karaoke.util.gtv.BuildUtils;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import kr.kumyoung.gtvkaraoke.BuildConfig;
import kr.kumyoung.gtvkaraoke.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 * Audio Focus처리
 * </pre>
 *
 * <a href="http://developer.android.com/training/managing-audio/audio-focus.html">Managing Audio Focus</a>
 *
 * @author isyoon
 * @since 2015. 4. 29.
 * @version 1.0
 */
public class BaseActivity2 extends BaseActivity {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public String toString() {
		super.toString();
		return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	// /**
	// * <a href="http://developer.android.com/training/managing-audio/audio-focus.html#HandleFocusLoss">Managing Audio Focus#Handle the Loss of Audio Focus</a>
	// */
	// OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {
	// public void onAudioFocusChange(int focusChange) {
	// if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
	// // Pause playback
	// } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
	// // Resume playback
	// } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
	// am.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
	// am.abandonAudioFocus(afChangeListener);
	// // Stop playback
	// }
	//
	// if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
	// // Lower the volume
	// } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
	// // Raise it back to normal
	// }
	// }
	// };
	//
	// /**
	// * <a href="http://developer.android.com/training/managing-audio/audio-focus.html#DUCK">Managing Audio Focus#Duck!</a>
	// */
	// OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {
	// public void onAudioFocusChange(int focusChange) {
	// if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
	// // Lower the volume
	// } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
	// // Raise it back to normal
	// }
	// }
	// };

	AudioManager am;

	/**
	 * <pre>
	 * [GAIN]
	 * 	AUDIOFOCUS_GAIN
	 * 	AUDIOFOCUS_GAIN_TRANSIENT
	 * 	AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
	 * [LOSS]
	 * 	AUDIOFOCUS_LOSS
	 * 	AUDIOFOCUS_LOSS_TRANSIENT
	 * 	AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK
	 * </pre>
	 * 
	 * <a href="http://egloos.zum.com/shadowxx/v/10987471">[Android] Audio Focus (오디오 포커스).</a>
	 */
	OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {

		@Override
		public void onAudioFocusChange(int focusChange) {
			switch (focusChange) {
			case AudioManager.AUDIOFOCUS_GAIN:
			case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
			case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
				Log.e(__CLASSNAME__, getMethodName() + "[GAIN]" + focusChange);
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				Log.e(__CLASSNAME__, getMethodName() + "[LOSS]" + focusChange);
				break;
			default:
				Log.e(__CLASSNAME__, getMethodName() + "[UNKNOWN]" + focusChange);
				break;
			}
		}

	};

	/**
	 * <pre>
	 * </pre>
	 * 
	 * <a href="http://developer.android.com/training/managing-audio/audio-focus.html#RequestFocus">Managing Audio Focus#RequestFocus</a>
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e(__CLASSNAME__, getMethodName());
		super.onCreate(savedInstanceState);

		am = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

		// Request audio focus for playback
		int result = am.requestAudioFocus(afChangeListener,
				// Use the music stream.
				AudioManager.STREAM_MUSIC,
				// Request permanent focus.
				AudioManager.AUDIOFOCUS_GAIN);

		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			// am.registerMediaButtonEventReceiver(RemoteControlReceiver);
			// Start playback.
		}
	}

	@Override
	protected void onPause() {
		Log.e(__CLASSNAME__, getMethodName());
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.e(__CLASSNAME__, getMethodName());
		super.onResume();
	}

	private final Handler handler = new Handler();

	protected void removeCallbacks(Runnable r) {
		if (handler != null) {
			handler.removeCallbacks(r);
		}
	}

	protected void post(Runnable r) {
		if (handler != null) {
			handler.post(r);
		}
	}

	protected void postDelayed(Runnable r, long delayMillis) {
		if (handler != null) {
			handler.postDelayed(r, delayMillis);
		}
	}

	/**
	 * 버전정보
	 */
	protected void startVersion() {
		// if (IKaraokeTV.DEBUG) Log.d(__CLASSNAME__, getMethodName());

		try {
			postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						// showVersion()

						PackageInfo pkgInfo = BuildUtils.getPackageInfo(getApplicationContext());
						if (pkgInfo != null) {
							int versionNumber = pkgInfo.versionCode;
							String versionName = pkgInfo.versionName;
							String version = "";
							if (BuildConfig.DEBUG) {
								version += ":" + "DEBUG";
							}
							// install datetime
							// String installDate = DateUtils.getDate("yyyy/MM/dd hh:mm:ss", pkgInfo.lastUpdateTime);
							// build datetime
							// version += ":" + getString(R.string.category_setting_version);
							// version += ":" + versionName + "(" + versionNumber + ")";
							version += ":" + versionName + "(" + versionNumber + ")";
							// version += "(" + "p_ver:" + P_VER + ":p_apiversion:" + P_APIVER + ")";
							// String installDate = DateUtils.getDate("yyyy/MM/dd hh:mm:ss", pkgInfo.lastUpdateTime);
							//String buildDate = BuildUtils.getDate("yyyy/MM/dd HH:mm", BuildUtils.getBuildDate(getApplicationContext()));
							String buildDate = (new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)).format(new Date(BuildConfig.TIMESTAMP/* + TimeZone.getTimeZone("Asia/Seoul").getRawOffset()*/));
							version += "-" + buildDate;
							TextView tv = (TextView) findViewById(R.id.textViewStatus);
							if (tv != null) {
								tv.setText("LGU+ tvG 금영노래방 : " + version);
								tv.setSingleLine();
								tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
								// WidgetUtils.setTextViewMarquee(tv);
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
