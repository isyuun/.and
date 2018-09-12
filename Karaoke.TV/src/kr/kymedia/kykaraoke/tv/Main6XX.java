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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Main6XX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.widget
 *    |_ Main6XX.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.ui._SoundManager;

/**
 * <pre>
 * 키입력 사운드
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-01-06
 */
class Main6XX extends Main6X {
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

	/**
	 * 키입력 사운드 설정
	 */
	private boolean mIsKeySound = false;

	/**
	 * 키입력 사운드 설정
	 */
	public void setIsKeySound(boolean mIsKeySound) {
		this.mIsKeySound = mIsKeySound;
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#setIsKeySound(boolean)
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsKeySound(false);
	}

	@Override
	protected void init() {
		super.init();

		if (mIsKeySound) {
			// sound effect initial
			_SoundManager.getInstance().initSounds(this);
			_SoundManager.getInstance().addSound(1, R.raw.key);
			_SoundManager.getInstance().addSound(2, R.raw.select);
		}
	}

	/**
	 * 키입력소리재생
	 *
	 * @see Main2#onKeyDown(int, android.view.KeyEvent)
	 * @see Main2XXXXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main3XXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6X#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6XX#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event == null) {
			return super.onKeyDown(keyCode, event);
		}

		boolean ret = super.onKeyDown(keyCode, event);

		onKeySound(keyCode, event);

		return ret;
	}

	private boolean onKeySound(int keyCode, KeyEvent event) {
		if (mIsKeySound && !isPlaying() && !isListening()) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_MAIN, getMethodName() + keyCode + ", " + event);
			switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
				case KeyEvent.KEYCODE_DPAD_DOWN:
				case KeyEvent.KEYCODE_DPAD_LEFT:
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					_SoundManager.getInstance().playSound(1);
					break;
				case KeyEvent.KEYCODE_DPAD_CENTER:
				case KeyEvent.KEYCODE_ENTER:
				case KeyEvent.KEYCODE_MENU:
					_SoundManager.getInstance().playSound(2);
					break;
			}
		}

		return true;
	}
}
