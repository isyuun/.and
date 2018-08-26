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
 * filename	:	Main.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ Main.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.atv.st.com;

import kr.kymedia.kykaraoke.tv._Main;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 2. 26.
 * @version 1.0
 */
class Main extends _Main {
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

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#setIsKeySound(boolean)
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsKeySound(true);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#getTypeface()
	 */
	@Override
	public Typeface getTypeface() {
		return null;
	}

	@Override
	protected void init() {
		super.init();
	}

	/**
	 * <pre>
	 * 강제디버그경고표시
	 * 릴리스전반드시푼다
	 * p_debug:"DEBUG"
	 * </pre>
	 *
	 */
	@Override
	protected void showCBT(boolean show) {
		p_debug = "DEBUG";
		super.showCBT(show);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3X#init()
	 */
	@Override
	protected void getVender() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + getIntent() + getIntent().getExtras());
		super.getVender();

		p_appname = P_APPNAME_STC;
		p_apikey = P_APIKEY_STC;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + intent + intent.getExtras());
		super.onNewIntent(intent);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + getIntent() + getIntent().getExtras());
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#onStart()
	 */
	@Override
	protected void onStart() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onStart();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#onStop()
	 */
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
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#VASS(REQUEST_VASS)
	 */
	@Override
	public void VASS(REQUEST_VASS request) {
		if (IKaraokeTV.DEBUG) Log.e("[VASS]" + _toString(), getMethodName() + request + ":" + VASS);
		super.VASS(request);
	}

	@Override
	public void VASS(Message msg) {
		super.VASS(msg);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 
	 * <pre>
	 * "url_lyric": "http://cyms.chorus.co.kr/cykara_dl2.asp?song_id=47203"
	 * "url_skym": "http://211.236.190.103:8080/svc_media/mmp3/47203.mp3"
	 * "video_url": "http://kumyoung.hscdn.com/MPEG2_TS_Video/47203.mpg"
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Main3XX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#down()
	 */
	@Override
	protected void KP(Message msg) {
		int state = msg.getData().getInt("state");

		// 반주곡스트리밍처리
		switch (state) {
		case COMPLETE_SONG_PLAY: // 반주곡 파일 다운로드
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
			if (_KP_1016() != null) {
				_KP_1016().url_lyric = "http://cyms.chorus.co.kr/cykara_dl2.asp?song_id=47203";
				_KP_1016().url_skym = "http://211.236.190.103:8080/svc_media/mmp3/47203.mp3";
				_KP_1016().video_url = "http://kumyoung.hscdn.com/kymedia/btv_test/47203.mpg";
			}
			break;
		}

		super.KP(msg);
	}
}
