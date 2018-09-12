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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	_PlayActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.app
 *    |_ _PlayActivity.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.app;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 4. 3.
 * @version 1.0
 */
public class _PlayActivity extends _Activity {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	private PowerManager.WakeLock m_wakeLock = null;

	public void wakeLockAquire() {
		try {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			// m_wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "SongPlayer");
			m_wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, toString());
			m_wakeLock.acquire();
		} catch (Exception e) {

			Log.e(_toString(), "wakeLockAquire()" + "[NG]");
			e.printStackTrace();
		}
	}

	public void wakeLockRelease() {
		try {
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			if (m_wakeLock != null) {
				m_wakeLock.release();
				m_wakeLock = null;
			}
		} catch (Exception e) {

			Log.e(_toString(), "wakeLockRelease()" + "[NG]");
			e.printStackTrace();
		}
	}

	AudioManager mAudioManager;

	/**
	 * 필요에따라막는다.
	 */
	protected void volumeUp(int keyCode, KeyEvent event) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + keyCode + ", " + event);
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	}

	/**
	 * 필요에따라막는다.
	 */
	protected void volumeDown(int keyCode, KeyEvent event) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + keyCode + ", " + event);
		mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	}

}
