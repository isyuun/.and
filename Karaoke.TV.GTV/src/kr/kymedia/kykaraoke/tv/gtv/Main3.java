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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV.LGT
 * filename	:	Main.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.gtv
 *    |_ Main.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.gtv;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.kumyoung.gtvkaraoke.BuildConfig;

/**
 * <pre>
 * 메시지박스
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 4. 16.
 */
class Main3 extends Main2XX {
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
	 * Karaoke.TV로 이동
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		//// sound effect initial
		//SoundManager.getInstance().initSounds(this);
		//SoundManager.getInstance().addSound(1, R.raw.key);
		//SoundManager.getInstance().addSound(2, R.raw.select);
	}

	/**
	 * Karaoke.TV로 이동
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#onKeyDown(int, KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (event == null) {
			return false;
		}

		// isyoon.20150414
		// 종료키처리
		try {
			//if (keyCode == KeyEvent.KEYCODE_TV || keyCode == 170) {
			if (keyCode == KeyEvent.KEYCODE_TV || keyCode == KeyEvent.KEYCODE_HOME) {
				Log.e(_toString() + TAG_MAIN, "onKeyDown()" + "[종료]" + keyCode + ", " + event);
				finish();
				return true;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return super.onKeyDown(keyCode, event);
	}

}
