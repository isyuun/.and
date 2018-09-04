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
 * filename	:	Video5.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Video5.java
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
 * @author isyoon
 * @since 2015. 4. 6.
 * @version 1.0
 */
class Video5 extends Video4 {
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
	protected void startVideo(String url, int playtype) {

		stop();
		super.startVideo(url, playtype);
	}

	@Override
	protected void reset() {
		if (mMusicVideo != null) {
			mMusicVideo.setOnPreparedListener(null);
			mMusicVideo.setOnCompletionListener(null);
			mMusicVideo.setOnErrorListener(null);
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
				mMusicVideo.setOnInfoListener(null);
			}
		}
	}

	@Override
	public boolean stop() {

		// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + "[ST]");
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		boolean ret = super.stop();
		reset();
		if (mMusicVideo != null) {
			mMusicVideo.stopPlayback();
		}
		// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + "[ED]");
		return ret;
	}

	/**
	 * <pre>
	 * 배경영상재생유지
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Video3#startBlankVideo(java.lang.String, int)
	 */
	@Override
	public void startBlankVideo(String url, int next) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + url);
		isMusicVideo = false;
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + url);
	}

	/**
	 * <pre>
	 * 배경영상재생유지
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Video3#stopMusicVideo(java.lang.String, int)
	 */
	@Override
	public void stopMusicVideo(String url, int engage) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + isMusicVideo + ":" + PLAY_ENGAGE.get(engage) + url);
		if (engage == PLAY_NEXT) {
			isMusicVideo = true;
		} else {
			isMusicVideo = false;
		}
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + isMusicVideo + ":" + PLAY_ENGAGE.get(engage) + url);
	}

	/**
	 * 
	 * <pre>
	 * 배경영상재생유지
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Video3#stopBlankVideo()
	 */
	@Override
	public void stopBlankVideo() {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		isMusicVideo = false;
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

}
