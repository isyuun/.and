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
 * 2012 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	singlist.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ singlist.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.content.CallReceiver;
import kr.kymedia.karaoke.kpop.singlist;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 * @see singlist.java
 */

public class PlayActivity extends _BaseAdActivity {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	BaseActivity2 mActivity = null;

	CallReceiver mCallReceiver = new CallReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + intent.getAction());
			// try {
			// PlayFragment fragment = (PlayFragment) getCurFragment();
			// fragment.isPauseOnPause = true;
			// if ((mActivity) instanceof sing) {
			// fragment.isCloseOnPause = true;
			// }
			// fragment.pause();
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onCreate(savedInstanceState);

		// 화면켜짐설정 - 갤스왜꺼지고 지랄이야 !!!
		super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mActivity = this;

		IntentFilter mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("android.intent.action.PHONE_STATE");
		mIntentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
		registerReceiver(mCallReceiver, mIntentFilter);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(new PhoneStateListener() {
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE: // 전화 종료
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK: // 전화 받기
					break;
				case TelephonyManager.CALL_STATE_RINGING: // 전화 옴
					break;
				}

				if (state != TelephonyManager.CALL_STATE_IDLE) {
					try {
						_PlayFragment fragment = (_PlayFragment) getCurrentFragment();
						if (fragment != null) {
							// fragment.isPauseOnPause = true;
							// fragment.isCloseOnPause = true;
							fragment.pause();
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);

	}

	/**
	 * 재생종료시전면팝업처리
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStop() {

		super.onStop();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		unregisterReceiver(mCallReceiver);
	}

}
