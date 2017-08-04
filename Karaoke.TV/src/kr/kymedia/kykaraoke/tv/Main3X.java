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
 * project	:	Karaoke.TV
 * filename	:	Main21.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main21.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import kr.kymedia.karaoke.api.KPparam;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 조회데이터관리
 * KP / VASS
 * 조회순서변경
 * 기존:VASS이용권조회->KP_0000
 * 변경:KP_4000->VASS이용권조회->KP_0000
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 4. 2.
 */
class Main3X extends Main3 {
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
	protected void onNewIntent(Intent intent) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + intent + intent.getExtras());
		super.onNewIntent(intent);
		setIntent(intent);
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + getIntent() + getIntent().getExtras());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + savedInstanceState);
		super.onCreate(savedInstanceState);

		startInit();
	}

	protected void startInit() {
		(new init()).execute();
	}

	/**
	 * <pre>
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * </pre>
	 *
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingID()
	 */
	private class init extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {

			KP_param(false);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			init();
		}

	}

	protected KPparam KPparam = null;

	/**
	 * KP_Params
	 *
	 * @see Main3X#init()
	 */
	protected void KP_param(boolean adsID) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		// PackageInfo pkgInfo = BuildUtils.getPackageInfo(getApplicationContext());
		// int versionNumber = pkgInfo.versionCode;
		// String versionName = pkgInfo.versionName;

		KPparam = new KPparam(getApplicationContext(), adsID);

		// KP Params
		// p_domain = P_DOMAIN_BTV;
		if (IKaraokeTV.DEBUG) {
			p_debug = "debug";
		}
		p_market = P_MARKET;
		p_ver = P_VER;
		p_mid = "";
		p_stbid = "";
		p_account = "";
		p_ncode = KPparam.getNcode();
		p_lcode = KPparam.getLcode();
		p_model = "";
		p_mac = "";
		p_os = P_OS;
		p_osversion = Build.VERSION.RELEASE.toString();
		p_passcnt = "";

	}

	/**
	 * <pre>
	 * Called by AsyncThread
	 * </pre>
	 */
	protected void init() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		getVender();

		initVender();

		initVASS();

		KP(REQUEST_TICKET_SALES_STATE, "", "", "");

		setPlayer();

		setVideo();

		setListen();
	}

	/**
	 * 네트워크오류로 이용권확인이 안된 경우 처리
	 */
	@Override
	public void goHome() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + isPassUser());
		super.goHome();

		if (!isPassUser()) {
			startInit();
		}
	}

	protected void clearVender() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		m_strSTBVender = "";
		m_orgSTBID = "";
		p_stbid = "";
		p_readmac = "";
		p_mac = "";
		p_model = "";
		p_appname = "";
		p_apikey = "";
	}

	/**
	 * <pre>
	 * 업체기기별 초기화처리
	 * </pre>
	 *
	 * @see Main3X#init()
	 */
	protected void getVender() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + getIntent() + getIntent().getExtras());

		clearVender();

		p_apkname = getPackageName();

		m_strSTBVender = P_APPNAME_DEMO;
		p_appname = P_APPNAME_DEMO;
		p_apikey = P_APIKEY_DEMO;

		m_orgSTBID = KPparam.getAdvertisingID();
		p_stbid = Base64.encodeToString(m_orgSTBID.getBytes(), Base64.NO_WRAP);
		p_readmac = KPparam.getMacAdress();
		p_mac = KPparam.getMacAdress();
		p_model = KPparam.getModel();

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + m_strSTBVender);
	}

	/**
	 * @see Main3X#init()
	 */
	private void initVender() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + m_strSTBVender);

		remote.m_strSTBVender = m_strSTBVender;

		sdPath = getApplicationContext().getExternalFilesDir(null) + "";
	}

	/**
	 * @see Main3X#init()
	 */
	protected void initVASS() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + m_strSTBVender);

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + m_strSTBVender);
	}

	/**
	 * @see Main2#KP(int, String, String, String)
	 * @see Main2X#KP(int, String, String, String)
	 * @see Main3X#KP(int, String, String, String)
	 * @see Main3XXX#KP(int, String, String, String)
	 * @see Main3XXXXX#VASSPPX(COMPLETE_VASS)
	 */
	@Override
	public void KP(int request, String OP, String M1, String M2) {
		this.KP_REQUEST = request;
		this.OP = OP;
		this.M1 = M1;
		this.M2 = M2;
		_clearData(getMethodName(), REQUEST_KP.get(request));
		postDelayed(super_KP, 500);
	}

	private  void super_KP() {
		super.KP(KP_REQUEST, OP, M1, M2);
	}

	private Runnable super_KP = new Runnable() {
		@Override
		public void run() {
			super_KP();
		}
	};

	/**
	 * <pre>
	 * 전문조회실패
	 *  응답오류
	 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *  전문결과표시
	 *    결과없음("result_code": "00901")
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	@Override
	protected void KP(Message msg) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":" + msg);

		super.KP(msg);

		boolean clear = true;

		/**
		 * <pre>
		 * 전문조회실패
		 *  응답오류
		 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
		 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
		 *  전문결과표시
		 *    결과없음("result_code": "00901")
		 * </pre>
		 */
		String op = KP.p_op;
		String code = KP.result_code;
		String message = KP.result_message;
		message += "\n";
		message += "(" + op + ":" + code + ")";
		/**
		 *    결과없음("result_code": "00901")
		 */
		if (("00901").equalsIgnoreCase(code)) {
			message = KP.result_message;
		}

		/**
		 * 전문스타토!!!
		 */
		switch (KP_REQUEST) {
			case REQUEST_SONG_PLAYED_TIME:
			case REQUEST_LISTEN_PLAYED_TIME:
				clear = false;
				op = _KP_1012.p_op;
				code = _KP_1012.result_code;
				message = _KP_1012.result_message;
				break;
			default:
				break;
		}

		COMPLETE_KP state = COMPLETE_KP.get(msg.getData().getInt("state"));

		/**
		 * 전문결과
		 */
		if (("00000").equalsIgnoreCase(code)) {
			Log.wtf("[KP]" + _toString(), "[KP]" + "[OK]" + REQUEST_KP.get(KP_REQUEST) + ":" + state + ":" + this.OP + ", " + this.M1 + ", " + this.M2 + "-" + op + ":" + code + ":" + msg);
		} else {
			/**
			 * 전문실패
			 */
			switch (state) {
				case COMPLETE_ERROR_REQUEST_NOT_RESPONSE:
				case COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE:
					Log.wtf("[KP]" + _toString(), "[KP]" + "[ER]" + REQUEST_KP.get(KP_REQUEST) + ":" + state + ":" + this.OP + ", " + this.M1 + ", " + this.M2 + "-" + op + ":" + code + ":" + msg);
					break;
				default:
					Log.wtf("[KP]" + _toString(), "[KP]" + "[NG]" + REQUEST_KP.get(KP_REQUEST) + ":" + state + ":" + this.OP + ", " + this.M1 + ", " + this.M2 + "-" + op + ":" + code + ":" + msg);
					break;
			}


			if (clear) {
				_clearData(getMethodName(), state);
			}

			/**
			 * 전문처리
			 */
			if (TextUtil.isEmpty(message)) {
				message = getString(R.string.message_error_network_timeout);
			}

			setBottomProductText(message);
		}

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":" + msg);
	}

	int m_iSaveCurrentSongListPage = 0;
	int m_iSaveCurrentListenListPage = 0;
	int m_iSaveCurrentCustomerListPage = 0;

	@Override
	protected void displayListSong(int keyID) {
		m_iSaveCurrentSongListPage = m_iCurrentSongListPage;
		super.displayListSong(keyID);
	}

	@Override
	public void displayListListen(int keyID) {
		m_iSaveCurrentListenListPage = m_iCurrentListenListPage;
		super.displayListListen(keyID);
	}

	@Override
	public void displayListCustomer(int keyID) {
		m_iSaveCurrentCustomerListPage = m_iCurrentCustomerListPage;
		super.displayListCustomer(keyID);
	}

	/**
	 * <pre>
	 * 전문조회실패
	 *  응답오류
	 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *  전문결과표시
	 *    결과없음("result_code": "00901")
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	private void _clearDataAll(String method, COMPLETE_KP complete) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NG]" + method + ":" + REQUEST_KP.get(KP_REQUEST) + ":" + /*COMPLETE_KP.get*/(complete) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		//전체데이터를 지우는건 좀그래...
		//오류시 강제 증가된 페이지를 줄인다.
		switch (KP_REQUEST) {
			case REQUEST_SONG_LIST:
				if (m_iCurrentSongListPage != 1) {
					m_iCurrentSongListPage = m_iSaveCurrentSongListPage;
				}
				setPageTextSongList();
				break;
			case REQUEST_LISTEN_LIST:
				if (m_iCurrentListenListPage != 1) {
					m_iCurrentListenListPage = m_iSaveCurrentListenListPage;
				}
				setPageTextListenList();
				break;
			case REQUEST_LISTEN_OTHER:
				break;
			case REQUEST_CUSTOMER_LIST:
				if (m_iCurrentCustomerListPage != 1) {
					m_iCurrentCustomerListPage = m_iSaveCurrentCustomerListPage;
				}
				setPageTextCustomerList();
				break;
		}
	}

	/**
	 * <pre>
	 * 전문조회실패
	 *  응답오류
	 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *  전문결과표시
	 *    결과없음("result_code": "00901")
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	private void _clearData(String method, COMPLETE_KP complete) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NG]" + method + ":" + /*COMPLETE_KP.get*/(complete) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		switch (complete) {
			case COMPLETE_ERROR_REQUEST_NOT_RESPONSE:
			case COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE:
				_clearDataAll(getMethodName(), complete);
				break;
			case COMPLETE_SONG_LIST:
				clearSongItems();
				clearSongPages();
				setPageTextSongList();
				break;
			case COMPLETE_LISTEN_LIST:
				clearListenItems();
				clearListenPages();
				setPageTextListenList();
				break;
			case COMPLETE_LISTEN_OTHER:
				clearListenOtherItems();
				clearListenOtherPages();
				break;
			case COMPLETE_CUSTOMER_LIST:
				clearCustomerItems();
				clearCustomerPages();
				setPageTextCustomerList();
				break;
		}
	}

	/**
	 * <pre>
	 * 전문조회전처리
	 * </pre>
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(int, String, String, String)
	 */
	private void _clearData(String method, REQUEST_KP request) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO]" + method + ":" + /*REQUEST_KP.get*/(request) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		switch (request) {
			case REQUEST_SONG_LIST:
				clearSongItems();
				break;
			case REQUEST_LISTEN_LIST:
				clearListenItems();
				break;
			case REQUEST_LISTEN_OTHER:
				clearListenOtherItems();
				break;
			case REQUEST_CUSTOMER_LIST:
				clearCustomerItems();
				break;
		}
	}

	private void clearSongItems() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		mSongItems.clear();
	}

	private void clearSongPages() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		m_iCurrentSongListPage = 0;
		m_iTotalSongListPage = 0;
	}

	private void clearListenItems() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		mListenItems.clear();
	}

	private void clearListenPages() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		m_iCurrentListenListPage = 0;
		m_iTotalListenListPage = 0;
	}

	private void clearListenOtherItems() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		mListenOtherItems.clear();
	}

	private void clearListenOtherPages() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		m_iCurrentListenOtherListPage = 0;
		m_iTotalListenOtherListPage = 0;
	}

	private void clearCustomerItems() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		mCustomerItems.clear();
	}

	private void clearCustomerPages() {
		m_iCurrentCustomerListPage = 0;
		m_iTotalCustomerListPage = 0;
	}

	@Override
	protected void displayGUI(int keyID) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.displayGUI(keyID);
	}

	@Override
	public void clickMenuSing() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (null == mSongItems || mSongItems.size() == 0) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mSongItems);
			return;
		}
		super.clickMenuSing();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void displayMenuSing(int keyID) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (keyID == REMOTE_DOWN) {
			if (null == mSongItems || mSongItems.size() == 0) {
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mSongItems);
				return;
			}
		}
		super.displayMenuSing(keyID);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void clickMenuListen() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (null == mListenItems || mListenItems.size() == 0) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mListenItems);
			return;
		}
		super.clickMenuListen();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void displayMenuListen(int keyID) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (keyID == REMOTE_DOWN) {
			if (null == mListenItems || mListenItems.size() == 0) {
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mListenItems);
				return;
			}
		}
		super.displayMenuListen(keyID);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 보류
	 */
	@Override
	public void displayListenOther(int keyID) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (null == mListenOtherItems || mListenOtherItems.size() == 0) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mListenOtherItems);
			return;
		}
		super.displayListenOther(keyID);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void clickMenuMy() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + "remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (remote.m_iMenuMyFocus != 3) {
			if (null == mSongItems || mSongItems.size() == 0) {
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mSongItems);
				return;
			}
		} else {
			if (!TextUtil.isEmpty(KP.auth_date) && (null == mListenItems || mListenItems.size() == 0)) {
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mListenItems);
				return;
			}
		}
		super.clickMenuMy();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + "remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void displayMenuMy(int keyID) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + "remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (keyID == REMOTE_DOWN) {
			if (remote.m_iMenuMyFocus != 3) {
				if (null == mSongItems || mSongItems.size() == 0) {
					if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mSongItems);
					return;
				}
			} else {
				if (!TextUtil.isEmpty(KP.auth_date) && (null == mListenItems || mListenItems.size() == 0)) {
					if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mListenItems);
					return;
				}
			}
		}
		super.displayMenuMy(keyID);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + "remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 2.고객센터 > 이용안내 > 목록의 항목 선택 진입 > 포커스 이용안내로 이동 후 리모콘 OK 선택 > 이용안내 목록 표시되지 않음
	 */
	@Override
	public void clickMenuCustomer() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (m_iEnterCustomerMenu == CUSTOMER_ENTER_KEY && (null == mCustomerItems || mCustomerItems.size() == 0)) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mCustomerItems);
			return;
		}
		super.clickMenuCustomer();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 븅신 좆나게만들어서 젖같이 쓰고있네.
	 * 2.고객센터 > 이용안내 > 목록의 항목 선택 진입 > 포커스 이용안내로 이동 후 리모콘 OK 선택 > 이용안내 목록 표시되지 않음
	 * </pre>
	 */
	@Override
	protected void displayMenuCustomer(int keyID) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (keyID == REMOTE_DOWN && (null == mCustomerItems || mCustomerItems.size() == 0)) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mCustomerItems);
			return;
		}

		if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[CL][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mCustomerItems);
			_clearData(getMethodName(), REQUEST_KP.REQUEST_CUSTOMER_LIST);
		}
		super.displayMenuCustomer(keyID);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

}
