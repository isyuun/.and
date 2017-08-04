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

package kr.kymedia.kykaraoke.tv.btv;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.BuildConfig;
import kr.kymedia.kykaraoke.tv.R;
import kr.kymedia.kykaraoke.tv._Main;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.util.Log;

import com.dasan.micapi.Miclib;
import com.dasan.product.SystemInfo;

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
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#setIsKeySound(boolean)
	 * @see kr.kymedia.kykaraoke.tv.Main6XX#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setIsKeySound(false);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#getTypeface()
	 */
	@Override
	public Typeface getTypeface() {

		return super.getTypeface();
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
		//p_debug = "DEBUG";
		super.showCBT(show);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3X#init()
	 */
	@Override
	protected void getVender() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + getIntent() + getIntent().getExtras());
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getIntent().getStringExtra("STBID"));
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getIntent().getStringExtra("MAC"));
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getIntent().getStringExtra("STBIDOrigin"));
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getIntent().getStringExtra("MODELNAME"));
		super.getVender();

		clearVender();
		/*
		 * STBID, MAC을 조작하고 싶을 때 사용한다
		 * DAE7F8FA-EE64-11DF-A530-FF677D27FE51 0:21:4C:DA:FE:B2
		 * 2405403C-885F-11E1-B168-1197FB8290F3 0:8:B9:2B:97:FD
		 * 8F71FD70-50E2-11E3-9C8F-07238ADCC295 0:8:B9:CD:D:9A
		 */
		Bundle bundle = getIntent().getExtras();
		// 번들데이터확인
		if (bundle != null) {
			try {
				// SMART_STB
				p_stbid = bundle.getString("STBID");
				p_readmac = bundle.getString("MAC");

				if (p_readmac.substring(0, 1).equals("0")) {
					p_mac = p_readmac.substring(1, p_readmac.length());
				} else {
					p_mac = p_readmac;
				}

				p_mac = p_mac.replace(":0", ":");
				m_orgSTBID = bundle.getString("STBIDOrigin");
				p_model = bundle.getString("MODELNAME");
				m_strSTBVER = bundle.getString("STBVER");
				// 셋탑종류확인
				m_strSTBVender = P_APPNAME_SKB_STB;
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			// SMART_BOX
			String STBID = null;
			try {
				if ((new SystemInfo()) != null) {
					STBID = SystemInfo.GetSystemProperty("STB_ID");
					p_readmac = SystemInfo.GetSystemProperty("ETHERNET_MAC");
					p_model = SystemInfo.GetSystemProperty("MODEL_NAME");
					m_strSTBVER = SystemInfo.GetSystemProperty("FIRMWARE_VERSION");
				}
				if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[STBID:OK]" + STBID);
				int i = 0;
				while (i < STBID.length()) {
					if (!STBID.substring(i, i + 1).equals("{") && !STBID.substring(i, i + 1).equals("}")) {
						m_orgSTBID = m_orgSTBID + STBID.substring(i, i + 1);
					}

					i++;
				}

				p_stbid = Base64.encodeToString(m_orgSTBID.getBytes(), Base64.NO_WRAP);

				if (p_readmac.substring(0, 1).equals("0")) {
					p_mac = p_readmac.substring(1, p_readmac.length());
				} else {
					p_mac = p_readmac;
				}

				p_mac = p_mac.replace(":0", ":");

				try {
					System.loadLibrary("mic");
					m_bLoadMICLibrary = true;
				} catch (Throwable e) {
					if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[MIC:NG]");
					e.printStackTrace();
					m_bLoadMICLibrary = false;
				}

				if (m_bLoadMICLibrary) {
					ShowMessageCommon(CLOSE_AUTO_MID_BOTTOMRIGHT, getString(R.string.common_info), "체감 상 반주곡 음량이 작게 들릴 수 있습니다.\r\n노래방 실행 및 종료 시에 볼륨을 조절해주세요.");
				} else {
					ShowMessageCommon(CLOSE_AUTO_MID_BOTTOMRIGHT, getString(R.string.common_info), "현재 USB 마이크를 사용할 수 없습니다.\r\nB box 설정 메뉴에서 소프트웨어 업데이트 해주세요.");
				}
				// 셋탑종류확인
				m_strSTBVender = P_APPNAME_SKT_BOX;
			} catch (UnsatisfiedLinkError e) {

				if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[STBID:NG1]" + STBID);
				e.printStackTrace();
			} catch (Exception e) {

				if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[STBID:NG2]" + STBID);
				e.printStackTrace();
			}

		}

		// 셋탑종류확인
		if (TextUtil.isEmpty(m_strSTBVender)) {
			HideMessageCommon();
			ShowMessageOk(POPUP_EXIT, getString(R.string.common_info), getString(R.string.message_error_stb_info) + "\n" + m_strSTBVender);
			return;
		}

		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			p_appname = P_APPNAME_SKT_BOX;
			p_apikey = P_APIKEY_SKT_BOX;
		} else {
			p_appname = P_APPNAME_SKB_STB;
			p_apikey = P_APIKEY_SKB_STB;
		}

		/*
		 * STB로 시작했는데 모델명을 취득해보니 UHD더라
		 */
		// if (m_strSTBVender == P_APPNAME_SKB_STBUHD)
		{
			if (("BHX-UH200").equals(p_model)) {
				p_appname = P_APPNAME_SKB_UHD;
				p_apikey = P_APIKEY_SKB_UHD;
			} else if (("BHX-S300").equals(p_model) || ("BKO-S300").equals(p_model)) {
				p_appname = P_APPNAME_BTV_1;
				p_apikey = P_APIKEY_BTV_1;
			} else {
			}
		}

		// 세탑이면 홈 프로필 이미지 위치 보정
		// if (m_strSTBVender == P_APPNAME_SKB_SMART_STB/* || m_strSTBVender == SMART_UHD */) {
		// LinearLayout layoutTopBlank = (LinearLayout) findViewById(R.id.layout_main_listen_top_blank);
		// LinearLayout layoutBottomBlank = (LinearLayout) findViewById(R.id.layout_main_listen_bottom_blank);
		// LinearLayout layoutLeftBlank = (LinearLayout) findViewById(R.id.layout_main_listen_bottom_left);
		// LinearLayout layoutRightBlank = (LinearLayout) findViewById(R.id.layout_main_listen_bottom_right);
		//
		// LinearLayout.LayoutParams lpTop = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		// lpTop.setMargins(0, 0, 0, 0);
		// lpTop.weight = 243; // 241
		// layoutTopBlank.setLayoutParams(lpTop);
		//
		// LinearLayout.LayoutParams lpBottom = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		// lpBottom.setMargins(0, 0, 0, 0);
		// lpBottom.weight = 32; // 34
		// layoutBottomBlank.setLayoutParams(lpBottom);
		//
		// LinearLayout.LayoutParams lpLeft = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
		// lpLeft.setMargins(0, 0, 0, 0);
		// lpLeft.weight = 103; // 101
		// layoutLeftBlank.setLayoutParams(lpLeft);
		//
		// LinearLayout.LayoutParams lpRight = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
		// lpRight.setMargins(0, 0, 0, 0);
		// lpRight.weight = 86; // 88
		// layoutRightBlank.setLayoutParams(lpRight);
		// }

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + m_strSTBVender);
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
		// if (P_DEVICE == SMART_BOX) {
		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			// for BOX
			if (m_bLoadMICLibrary) {
				Miclib.openMic();
			}
		}

		super.onStart();
	}

	@Override
	protected void onResume() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + getIntent().getExtras());
		super.onResume();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#onStop()
	 */
	@Override
	protected void onStop() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		// if (P_DEVICE == SMART_BOX) {
		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			// for BOX
			if (m_bLoadMICLibrary) {
				Miclib.closeMic();
			}
		}

		super.onStop();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + savedInstanceState);
		super.onPostCreate(savedInstanceState);

		wakeLockAquire();
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + savedInstanceState + ", " + persistentState);
		super.onPostCreate(savedInstanceState, persistentState);

		wakeLockAquire();
	}

	@Override
	protected void onDestroy() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onDestroy();

		wakeLockRelease();
	}

	@Override
	protected void play() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.play();
		wakeLockAquire();
	}

	@Override
	protected void stop(int engage) {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.stop(engage);
		wakeLockAquire();
	}

}
