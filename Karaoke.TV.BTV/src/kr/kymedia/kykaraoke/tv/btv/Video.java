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
 * project	:	Karaoke.TV.APP
 * filename	:	Video.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ Video.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.btv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import kr.kymedia.kykaraoke.BuildConfig;
import kr.kymedia.kykaraoke.tv._Video;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.api._Const;

/**
 * <pre>
 * </pre>
 *
 * @author isyoon
 * @since 2015. 3. 23.
 * @version 1.0
 */
class Video extends _Video {
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
	 * <pre>
	 * 잘불러야된데이...
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.app._Activity#startMainActivity(android.os.Bundle)
	 */
	@Override
	public void startMainActivity(Bundle bundle) {
		Intent intent = new Intent(getApplicationContext(), __Main.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Log.wtf(_toString(), "startMainActivity()" + bundle + ":" + intent);
		startActivity(intent);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.btv.Main#getVender()
	 * @see kr.kymedia.kykaraoke.tv.Video2#start()
	 */
	@Override
	public void start() {

		if (IKaraokeTV.DEBUG) Log.e(_toString() + TAG_VIDEO, "start() " + "[ST]");
		/**
		 * 동영상재시작(시간:60초):많이도준다~~~
		 */
		TIMER_RETRY = 6 * TIMER_MP4_RETRY;
		/**
		 * 동영상재시작(횟수:3회)
		 */
		COUNT_RETRY = _Const.COUNT_MP4_RETRY;

		if (IKaraokeTV.DEBUG) Log.e(_toString() + TAG_VIDEO, "start() " + "[ED]");
	}

}
