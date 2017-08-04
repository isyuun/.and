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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	BaseActivity3.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseActivity3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPparam;
import kr.kymedia.karaoke.api.Log2;

/**
 * TODO<br>
 * 
 * <pre>
 * 전문비동기처리
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 12. 2.
 * @version 1.0
 */
class BaseActivity3 extends BaseActivity2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * 푸쉬처리된인텐트를 받은후 화면이동을한다.<br>
	 * 어풀이비실행중인경우홈화면에서처리될것이당~~~
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]");


		super.onPostCreate(savedInstanceState);

		KP_start();

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[END]");
	}

	/**
	 * <pre>
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * </pre>
	 * 
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingIdInfo()
	 */
	private void KP_param() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		//try {
		//	if (getApp().KPparam == null || isACTIONMAIN()) {
		//		getApp().KP_param();
		//	}
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
	}

	private boolean started = false;

	/**
	 * <pre>
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * </pre>
	 * 
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingIdInfo()
	 */
	protected void start() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		KP_init();

		KP_nnnn();

		initGCM();

		started = true;
	}

	void KP_start() {
		(new KP_start()).execute();
	}

	/**
	 * <pre>
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * </pre>
	 * 
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingIdInfo()
	 */
	private class KP_start extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {

			KP_param();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			start();
		}

	}

	/**
	 * <pre>
	 * 전문제조회
	 * 전문초기화
	 * {@link BaseFragment2#refresh()} 호출
	 * </pre>
	 */
	protected void refresh() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		dismissAlertDialogsAll();

		getIntentData();

		KP_init();

		KP_nnnn();

		initGCM();

		if (getCurrentFragment() != null) {
			getCurrentFragment().refresh();
		}
	}

	/**
	 * 로그인/로그아웃시 리플레쉬여부확인
	 */
	// boolean isRefresh = false;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().isRefresh + ", requestCode:" + requestCode + ", resultCode:" + resultCode + ", data:" + data);


		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onNewIntent(Intent newIntent) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.onNewIntent(newIntent);

		newIntent = getIntentData(newIntent);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[ST]");
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "" + getIntent());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + getIntent().getExtras());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "" + newIntent);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + newIntent.getExtras());

		if (newIntent.getExtras() == null) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][NULL]");
			return;
		}

		// if (getIntent().getExtras() != null && newIntent.getExtras().toString().equals(getIntent().getExtras().toString())) {
		if (equalBundles(newIntent.getExtras(), getIntent().getExtras())) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][SAME]");
			return;
		}

		setIntent(newIntent);

		getIntentData();

		if (!getApp().isRefresh) {
			refresh();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[OK]");
	}

	@Override
	protected void onResume() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.onResume();

		if (started) {
			if (getApp().isRefresh) {
				refresh();
			}
		}

		if (isACTIONMAIN()) {
			getApp().isRefresh = false;
		}

		started = true;
	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

}
