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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Main6X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main6X.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 *  가사처리슬립추가
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015-10-14
 */
class Main6X extends Main6 {
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

	protected void hideThreadSleepTime() {
		long time = getPlayer().getLyricsPlay().getThreadSleepTime();
		TextView info = (TextView) findViewById(R.id.txt_lyric_sleep_info);
		if (info != null) {
			info.setVisibility(View.INVISIBLE);
			info.setText("" + time);
		}
		//_LOG.e(_toString() + TAG_MAIN, "hideThreadSleepTime() " + isRCBelow() + ":" + loading_time);
	}

	protected void showThreadSleepTime() {
		long time = getPlayer().getLyricsPlay().getThreadSleepTime();
		TextView info = (TextView) findViewById(R.id.txt_lyric_sleep_info);
		if (info != null) {
			if (isCBTBelow()) {
				info.setVisibility(View.VISIBLE);
			} else {
				info.setVisibility(View.INVISIBLE);
			}
			info.setText("" + time);
		}
		//_LOG.e(_toString() + TAG_MAIN, "showThreadSleepTime() " + isRCBelow() + ":" + loading_time);
	}

	/**
	 * 가사처리슬립추가
	 */
	private void setThreadSleepTime(long time) {
		try {
			getPlayer().getLyricsPlay().setThreadSleepTime(time);
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	/**
	 * 가사처리슬립추가
	 */
	protected void setThreadSleepTimeUP() {
		long time = getPlayer().getLyricsPlay().getThreadSleepTime();
		try {
			setThreadSleepTime(++time);
			showThreadSleepTime();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	/**
	 * 가사처리슬립추가
	 */
	protected void setThreadSleepTimeDN() {
		long time = getPlayer().getLyricsPlay().getThreadSleepTime();
		try {
			setThreadSleepTime(--time);
			showThreadSleepTime();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
		// _LOG.e(_toString() + TAG_MAIN, "setThreadSleepTimeDN()" + loading_time);
	}

	/**
	 * <pre>
	 * 가사처리슬립추가:강제할당
	 * 	15fps인 경우는, 1000/15 = 66.7 ms의 딜레이를
	 * 	30fps 인 경우는, 1000/30 = 33.3 ms 의 딜레이를 설정하면 되는데,
	 * 	윤인식 차장님 자리에서 테스트 했을때,
	 * 	제 의견은 HUMAX/ST박스의 경우는 15fps가 적당할 것으로 보입니다.
	 * </pre>
	 */
	public static long SLEEP_TIME = 0L;

	/**
	 * 가사처리슬립추가:강제할당:초기기본값설정
	 */
	@Override
	protected void startCBT() {
		//_LOG.wtf(_toString() + TAG_MAIN, "startCBT()");
		setThreadSleepTime(SLEEP_TIME);
		super.startCBT();
		showThreadSleepTime();
	}

	/**
	 * 가사처리슬립추가:강제할당:초기기본값설정
	 */
	@Override
	protected void play() {
		//_LOG.wtf(_toString() + TAG_MAIN, "play()");
		super.play();
		setThreadSleepTime(SLEEP_TIME);
		showThreadSleepTime();
	}

	/**
	 * <pre>
	 *  가사처리슬립추가
	 * </pre>
	 * @see Main2#onKeyDown(int, android.view.KeyEvent)
	 * @see Main2XXXXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main3XXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main4X#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6X#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 가사처리슬립추가
		if (isCBTBelow() && event.isShiftPressed() && event.isCtrlPressed()) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_UP:
					setThreadSleepTimeUP();
					return true;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					setThreadSleepTimeDN();
					return true;
				default:
					break;
			}
		}

		return super.onKeyDown(keyCode, event);
	}
}
