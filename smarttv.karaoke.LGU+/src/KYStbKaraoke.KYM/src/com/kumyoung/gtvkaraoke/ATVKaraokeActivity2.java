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
 * project	:	KYStbKaraoke
 * filename	:	ATVKaraokeActivity2.java
 * author	:	isyoon
 *
 * <pre>
 * com.kumyoung.gtvkaraoke
 *    |_ ATVKaraokeActivity2.java
 * </pre>
 * 
 */

package com.kumyoung.gtvkaraoke;

import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KPlay;
import kr.kymedia.karaoke.util.gtv.Util;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * <pre>
 * 키처리관련기능추가
 * 게임패드기능추가
 * </pre>
 *
 * @author isyoon
 * @since 2015. 4. 22.
 * @version 1.0
 */
public class ATVKaraokeActivity2 extends ATVKaraokeActivity {
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

	private Context getContext() {
		return getApplicationContext();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mVideoView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String text = getMethodName() + "[RECT]";
				text += "\n[RECT][PX](" + event.getX() + "," + event.getY() + ")->";
				text += "\n[RECT][DP:NG](" + Global.DPFromPixel((int) event.getX()) + "," + Global.DPFromPixel((int) event.getY()) + ")";
				text += "\n[RECT][DP:OK](" + Util.px2dp(getContext(), event.getX()) + "," + Util.px2dp(getContext(), event.getY()) + ")";
				// text += event;
				Log.i("mVideoView", text);
				return false;
			}

		});
	}

	/**
	 * <pre>
	 * 재생키관련오류
	 * </pre>
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MEDIA_PLAY:
			KPlay.Inst().play_send(KMsg.MSG_KBD_START);
			break;

		default:
			break;
		}

		// isyoon_20150507
		// * 일반키보드동작
		switch (keyCode) {
		case KeyEvent.KEYCODE_NUMPAD_0:
			keyCode = KeyEvent.KEYCODE_0;
			break;
		case KeyEvent.KEYCODE_NUMPAD_1:
			keyCode = KeyEvent.KEYCODE_1;
			break;
		case KeyEvent.KEYCODE_NUMPAD_2:
			keyCode = KeyEvent.KEYCODE_2;
			break;
		case KeyEvent.KEYCODE_NUMPAD_3:
			keyCode = KeyEvent.KEYCODE_3;
			break;
		case KeyEvent.KEYCODE_NUMPAD_4:
			keyCode = KeyEvent.KEYCODE_4;
			break;
		case KeyEvent.KEYCODE_NUMPAD_5:
			keyCode = KeyEvent.KEYCODE_5;
			break;
		case KeyEvent.KEYCODE_NUMPAD_6:
			keyCode = KeyEvent.KEYCODE_6;
			break;
		case KeyEvent.KEYCODE_NUMPAD_7:
			keyCode = KeyEvent.KEYCODE_7;
			break;
		case KeyEvent.KEYCODE_NUMPAD_8:
			keyCode = KeyEvent.KEYCODE_8;
			break;
		case KeyEvent.KEYCODE_NUMPAD_9:
			keyCode = KeyEvent.KEYCODE_9;
			break;
		case KeyEvent.KEYCODE_NUMPAD_ENTER:
		case KeyEvent.KEYCODE_BUTTON_START:
			keyCode = KeyEvent.KEYCODE_ENTER;
			break;
		case KeyEvent.KEYCODE_DEL:
			// if (!isShowSoftKeyboard()) {
			// keyCode = KeyEvent.KEYCODE_BACK;
			// }
			break;
		case KeyEvent.KEYCODE_BUTTON_SELECT:
			keyCode = KeyEvent.KEYCODE_BACK;
			break;
		case KeyEvent.KEYCODE_F1:
		case KeyEvent.KEYCODE_BUTTON_B:
			keyCode = KeyEvent.KEYCODE_PROG_RED;
			break;
		case KeyEvent.KEYCODE_F2:
		case KeyEvent.KEYCODE_BUTTON_A:
			keyCode = KeyEvent.KEYCODE_PROG_GREEN;
			break;
		case KeyEvent.KEYCODE_F3:
		case KeyEvent.KEYCODE_BUTTON_Y:
			keyCode = KeyEvent.KEYCODE_PROG_YELLOW;
			break;
		case KeyEvent.KEYCODE_F4:
		case KeyEvent.KEYCODE_BUTTON_X:
		case KeyEvent.KEYCODE_SEARCH:
			keyCode = KeyEvent.KEYCODE_PROG_BLUE;
			break;
		case KeyEvent.KEYCODE_F5:
			keyCode = KeyEvent.KEYCODE_MENU;
			break;
		case KeyEvent.KEYCODE_F9:
			keyCode = KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
			break;
		case KeyEvent.KEYCODE_F10:
			keyCode = KeyEvent.KEYCODE_MEDIA_STOP;
			break;
		case KeyEvent.KEYCODE_F11:
			keyCode = KeyEvent.KEYCODE_MEDIA_REWIND;
			break;
		case KeyEvent.KEYCODE_F12:
			keyCode = KeyEvent.KEYCODE_MEDIA_FAST_FORWARD;
			break;
		case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
		case KeyEvent.KEYCODE_COMMA:
			// |◀◀
			// previous();
			// ret = true;
			break;
		case KeyEvent.KEYCODE_MEDIA_NEXT:
		case KeyEvent.KEYCODE_PERIOD:
			// // ▶▶|
			// next();
			// ret = true;
			break;
		default:
			break;
		}

		return super.onKeyDown(keyCode, event);

	}

}
