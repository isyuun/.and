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

package kr.kymedia.kykaraoke.tv.atv.st.com;

import kr.kymedia.kykaraoke.tv._Video;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 3. 23.
 * @version 1.0
 */
class Video extends _Video {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {

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

	@Override
	public void startMainActivity(Bundle bundle) {

		// super.startMainActivity(bundle);
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + bundle);
		Intent intent = new Intent(getApplicationContext(), __Main.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + intent + bundle);
		startActivity(intent);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.atv.st.com.Main#getVender()
	 * @see kr.kymedia.kykaraoke.tv.Video2#start()
	 */
	@Override
	public void start() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");
		super.start();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void onResume() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onResume();
	}

	@Override
	protected void onStop() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onStop();
	}

	@Override
	protected void onPause() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onPause();
	}

	/**
	 * 테스트영상강제할당
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Video3#startVideo(java.lang.String, int)
	 */
	@Override
	protected void startVideo(String url, int playtype) {

		super.startVideo("http://kumyoung.hscdn.com/kymedia/btv_test/47203.mpg", playtype);
	}

}
