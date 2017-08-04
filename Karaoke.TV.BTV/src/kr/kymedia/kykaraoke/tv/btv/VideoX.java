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
 * project	:	Karaoke.TV.BTV
 * filename	:	VideoX.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ VideoX.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.btv;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.dasan.product.SystemInfo;
import com.tvstorm.tv.ITVService;
import com.tvstorm.tv.TVContextManager;
import com.tvstorm.tv.framework.TVSystem;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.BuildConfig;
import kr.kymedia.kykaraoke.tv.R;

/**
 * <a href="http://pms.skdevice.net/redmine/issues/3593">3593 com.tvstorm.tv.framework.TVSystem...</a><br>
 * 
 * <pre>
 * DASAN:
 * 	셋탑STB_ID확인
 * 		STBID = SystemInfo.GetSystemProperty("STB_ID");
 * TVSTORM:
 * 	셋탑STBID확인
 * 		STBID = tvSystem.getSystem().getProperty("STBID");
 * 	셋탑시스템연결
 * 	셋탑마이크오픈
 * </pre>
 *
 * @author isyoon
 * @since 2015. 8. 21.
 * @version 1.0
 * @see com.dasan.product.SystemInfo
 * @see com.tvstorm.tv.ITVService
 * @see com.tvstorm.tv.TVContextManager
 * @see com.tvstorm.tv.framework.TVSystem
 */
class VideoX extends Video {
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

	// for STB
	protected String m_strSTBVender = P_APPNAME_SKT_BOX;
	protected String m_orgSTBID = "";
	protected String m_strSTBID = "";
	protected String m_strSTBMac = "";
	protected String m_strSTBModel = "";
	protected String m_strSTBVER = "";

	// 티비스톰
	boolean m_bSTBServiceSystemConnect = false;
	String STBID = "";
	TVContextManager tvContextManager = null;
	TVSystem tvSystem = null;

	private void disconnectTVSYSTEM() {
		Log.w(_toString() + TAG_VIDEO, "disconnectTVSYSTEM()" + "[ST]" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
		try {
			if (tvSystem != null) {
				Log.i(_toString() + TAG_VIDEO, "disconnectTVSYSTEM()" + "[CK]" + ":tvSystem.disconnect():" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
				tvSystem.disconnect();
			}
		} catch (Exception e) {

			Log.e(_toString() + TAG_VIDEO, "disconnectTVSYSTEM()" + "[NG]" + ":tvSystem.disconnect():" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
			e.printStackTrace();
		}
		Log.w(_toString() + TAG_VIDEO, "disconnectTVSYSTEM()" + "[ED]" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
	}

	private void connectTVSYSTEM() {
		Log.w(_toString() + TAG_VIDEO, "connectTVSYSTEM()" + "[ST]" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
		try {
			if (tvSystem != null) {
				Log.i(_toString() + TAG_VIDEO, "connectTVSYSTEM()" + "[CK]" + ":tvSystem.connect():" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
				tvSystem.connect(tvSystemListener);
			}
		} catch (Exception e) {

			Log.e(_toString() + TAG_VIDEO, "connectTVSYSTEM()" + "[NG]" + ":tvSystem.connect():" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
			//if (get_Application().getMainActivity() == null)
			{
				startMainActivity(null);
			}
			e.printStackTrace();
			ShowMessageOk(POPUP_OK, getString(R.string.common_info), getString(R.string.message_error_stb_info) + "\n" + "[NG]" + ":tvSystem.connect():" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
			disconnectTVSYSTEM();
		}
		Log.w(_toString() + TAG_VIDEO, "connectTVSYSTEM()" + "[ED]" + ":tvSystem.isConnected():" + (tvSystem != null ? tvSystem.isConnected() : tvSystem));
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.btv.Video#start()
	 */
	@Override
	public void start() {

		Log.e(_toString() + TAG_VIDEO, "start() " + "[ST]" + m_strSTBVender);
		super.start();

		// SMART_BOX확인
		try {
			if ((new SystemInfo()) != null) {
				STBID = SystemInfo.GetSystemProperty("STB_ID");
			}
			Log.i(_toString() + TAG_VIDEO, "start() " + "[BTV:BOX:STB_ID]" + "[OK]" + STBID);
		} catch (UnsatisfiedLinkError e) {

			Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:BOX:STB_ID]" + "[NG]" + STBID/* + "\n" + _LOG.getStackTraceString(e) */);
			//e.printStackTrace();
		} catch (Exception e) {

			Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:BOX:STB_ID]" + "[NG]" + STBID/* + "\n" + _LOG.getStackTraceString(e) */);
			//e.printStackTrace();
		}

		if (!TextUtil.isEmpty(STBID)) {
			// SMART_BOX
			try {
				m_strSTBVender = P_APPNAME_SKT_BOX;
				startMainActivity(null);
				Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:BOX]" + "[OK]" + m_strSTBVender);
			} catch (Exception e) {

				Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:BOX]" + "[NG]" + m_strSTBVender/* + "\n" + _LOG.getStackTraceString(e) */);
				e.printStackTrace();
				startMainActivity(null);
			}
		} else {
			// SMART_STB
			try {
				m_strSTBVender = P_APPNAME_SKB_STB;
				Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:STB]" + "TVContextManager.getInstance()" + m_strSTBVender);
				tvContextManager = TVContextManager.getInstance(getApplicationContext());
				Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:STB]" + "tvContextManager.getService()" + m_strSTBVender);
				tvSystem = (TVSystem) tvContextManager.getService(ITVService.TVSystem);
				// _LOG.e(_toString() + TAG_VIDEO, "start() " + "[BTV:STB]" + "tvSystem.connect()" + m_strSTBVender);
			 connectTVSYSTEM();
				Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:STB]" + "[OK]" + m_strSTBVender);
			} catch (Exception e) {

				Log.e(_toString() + TAG_VIDEO, "start() " + "[BTV:STB]" + "[NG]" + m_strSTBVender/* + "\n" + _LOG.getStackTraceString(e) */);
				e.printStackTrace();
				startMainActivity(null);
			}
		}

		Log.e(_toString() + TAG_VIDEO, "start() " + "[ED]" + m_strSTBVender);
	}

	TVSystem.ConnectionListener tvSystemListener = new TVSystem.ConnectionListener()
	{

		/**
		 * 
		 * <pre>
		 * isyoon
		 * setHDMIAudioMode(int)
		 * 	기존:HDMI_AUDIO_MODE_PCM->돌비설정오류발생
		 * </pre>
		 * 
		 * @see com.tvstorm.tv.systemframework.settings.ITVSettings#setHDMIAudioMode(int)
		 */
		@Override
		public void onTVServiceConnected() {

			Log.e(_toString() + TAG_VIDEO, "onTVServiceConnected() " + "[BTV:STB]" + tvSystem.isConnected() + ":" + tvSystem);

			if (tvSystem == null) {
				Log.e(_toString() + TAG_VIDEO, "onTVServiceConnected() " + "[BTV:STB]" + "[NG]" + tvSystem);
				return;
			}

			if (BuildConfig.DEBUG) {
				tvSystem.getSystem().setMousePointerVisible(true);
			} else {
				tvSystem.getSystem().setMousePointerVisible(false);
			}

			setMicOn();

			startTVSYSTEM();

		}

		@Override
		public void onTVServiceDisconnected() {

			Log.e(_toString() + TAG_VIDEO, "onTVServiceConnected() " + "[BTV:STB]" + tvSystem.isConnected() + ":" + tvSystem);
			setMicOff();
		}

	};

	/**
	 * <pre>
	 * onTVServiceConnected()시
	 * </pre>
	 * 
	 * @see #tvSystemListener
	 */
	private void startTVSYSTEM() {
		Log.e(_toString() + TAG_VIDEO, "startTVSYSTEM()");

		if (!m_bSTBServiceSystemConnect) {
			m_bSTBServiceSystemConnect = true;

			STBID = tvSystem.getSystem().getProperty("STBID");

			int i = 0;
			while (i < STBID.length()) {
				if (!STBID.substring(i, i + 1).equals("{") && !STBID.substring(i, i + 1).equals("}")) {
					m_orgSTBID = m_orgSTBID + STBID.substring(i, i + 1);
				}

				i++;
			}

			m_strSTBID = Base64.encodeToString(m_orgSTBID.getBytes(), Base64.NO_WRAP);
			m_strSTBMac = tvSystem.getEthernet().getMacAddress();
			m_strSTBModel = tvSystem.getSystem().getModelName();
			m_strSTBVER = tvSystem.getSystem().getFirmwareVersion();

			Bundle bundle = new Bundle();
			bundle.putString("STBIDOrigin", m_orgSTBID);
			bundle.putString("STBID", m_strSTBID);
			bundle.putString("MAC", m_strSTBMac);
			bundle.putString("MODELNAME", m_strSTBModel);
			bundle.putString("STBVER", m_strSTBVER);

			startMainActivity(bundle);
		}
	}

	/**
	 * <pre>
	 * onStop()시
	 * </pre>
	 * 
	 * @see #onStop()
	 */
	private void stopTVSYSTEM() {
		Log.e(_toString() + TAG_VIDEO, "stopTVSYSTEM()");
		setMicOff();
		disconnectTVSYSTEM();
	}

	/**
	 * <pre>
	 * onDestroy()시
	 * </pre>
	 * 
	 * @see #onDestroy()
	 */
	private void releaseTVSYSTEM() {
		Log.w(_toString() + TAG_VIDEO, "releaseTVSYSTEM()");
		stopTVSYSTEM();
		try {
			if (tvSystem != null) {
				tvSystem = null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * onPause()시
	 * </pre>
	 * 
	 * @see #onPause()
	 */
	void pauseTVSYSTEM() {
		Log.w(_toString() + TAG_VIDEO, "pauseTVSYSTEM()");
		setMicOff();
		disconnectTVSYSTEM();
	}

	/**
	 * <pre>
	 * 마이크켜기
	 * </pre>
	 *
	 */
	protected void setMicOn() {
		Log.e(_toString() + TAG_VIDEO, "setMicOn()");
		try {
			if (tvSystem != null && tvSystem.getSettings() != null) {
				// tvSystem.getSettings().setHDMIAudioMode(ITVSettings.HDMI_AUDIO_MODE_PCM);
				tvSystem.getSettings().setMicEchoLevel(1);
				tvSystem.getSettings().setMicOnOff(true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 마이크끄기
	 * </pre>
	 *
	 */
	protected void setMicOff() {
		Log.e(_toString() + TAG_VIDEO, "setMicOff()");
		try {
			if (tvSystem != null && tvSystem.getSettings() != null) {
				tvSystem.getSettings().setMicEchoLevel(0);
				tvSystem.getSettings().setMicOnOff(false);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e(_toString() + TAG_VIDEO, "onCreate()" + savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Video2#onResume()
	 * @see #start()
	 */
	@Override
	protected void onResume() {
		Log.e(_toString() + TAG_VIDEO, "onResume()" + " :isFinishing:" + isFinishing + ":isFinishing()" + isFinishing());
		Log.i(_toString() + TAG_VIDEO, "onResume()" + " :tvSystem.isConnected()" + (null != tvSystem ? tvSystem.isConnected() : tvSystem));
		super.onResume();
	}

	@Override
	protected void onStop() {
		Log.e(_toString() + TAG_VIDEO, "onStop()");
		stopTVSYSTEM();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.e(_toString() + TAG_VIDEO, "onDestroy()");
		stopTVSYSTEM();
		releaseTVSYSTEM();
		super.onDestroy();
	}

	@Override
	public void finish() {
		Log.e(_toString() + TAG_VIDEO, "finish()");
		// stopTVSYSTEM();
		// releaseTVSYSTEM();
		super.finish();
	}

}
