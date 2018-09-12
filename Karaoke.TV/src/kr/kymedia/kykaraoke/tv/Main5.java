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
 * filename	:	Main5.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main5.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv;

import kr.kymedia.kykaraoke.api.IKaraokeTV;
import android.util.Log;

/**
 * <pre>
 * 배경영상재생유지
 * </pre>
 *
 * @see {@link Video5}
 * @author isyoon
 * @since 2015. 4. 6.
 * @version 1.0
 */
class Main5 extends Main4XXX {
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

	@Override
	protected void start() {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + video_url);
		super.start();
		showBackBoard();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + video_url);
	}

	@Override
	protected void stop(int engage) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + video_url);
		super.stop(engage);
		hideBackBoard();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + video_url);
	}

	@Override
	public void hideBackBoard() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + video + ":" + video_url);
		// super.hideBackBoard();
		if (video != null) {
			if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OK]" + video.isPlaying() + ":" + video_url);
			if (video.isPlaying()) {
				super.hideBackBoard();
			} else {
				super.showBackBoard();
			}
		} else {
			super.hideBackBoard();
		}
	}

	@Override
	public void showBackBoard() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + video + ":" + video_url);
		// super.showBackBoard();
		if (video != null) {
			if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OK]" + video.isPlaying() + ":" + video_url);
			if (!video.isPlaying()) {
				super.showBackBoard();
			} else {
				super.hideBackBoard();
			}
		} else {
			super.showBackBoard();
		}
	}

}
