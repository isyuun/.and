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
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Video2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.gtv
 *    |_ Video2.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.gtv;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;
import com.kumyoung.gtvkaraoke.XmlFinder;
import com.kumyoung.stbcomm._ISUHandler;
import com.kumyoung.stbcomm.SetopHandler;
import com.kumyoung.stbcomm._SIMClientHandlerLGU;
import com.lguplus.iptv3.base.settingmanager._GetILgIptvShmRemote;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-06-28
 */
class Video2 extends Video {
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
	 * @see kr.kymedia.kykaraoke.tv.gtv.Main#getVender()
	 * @see kr.kymedia.kykaraoke.tv.Video2#start()
	 */
	@Override
	public void start() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");
		if (remoteLG != null && !TextUtil.isEmpty(remoteLG.getMacAddress())) {
			startMainActivity(null);
		} else {
			initRemoteLG();
		}
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void onResume() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onResume();
	}

	@Override
	protected void onStop() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");
		super.onDestroy();
		// _LOG.d(TAG, "onDestroy");

		try {
			// bgkim 150122 앱 종료될 때 마이크도 Disable
			SetopHandler.getInstance().setKaraokeMode(false);

			// // bgkim 150122 앱 종료될 때 mp3 플레이어 처리
			// // Global.Inst().streamMp3player.exitMp3Player();
			//
			// // bgkim 150122 앱 종료될 때 배경영상 처리
			// // mVideoView.doStop();
			//
			// // bgkim 150122 APP KILL 리시버 제거
			// //if (receiverAppKill != null) {
			// // unregisterReceiver(receiverAppKill);
			// //}

		} catch (Exception e) {

			e.printStackTrace();
		}

		// app 종료유도
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		am.killBackgroundProcesses(getPackageName());
		System.exit(0);

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	public static boolean isReachable(Context context) {
		// First, check we have any sort of connectivity
		final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			// Some sort of connection is open, check if server is reachable
			try {
				URL url = new URL("http://www.google.com");
				HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
				urlc.setRequestProperty("User-Agent", "Android Application");
				urlc.setRequestProperty("Connection", "close");
				urlc.setConnectTimeout(3 * 1000); // Thirty seconds timeout in milliseconds
				urlc.connect();
				if (urlc.getResponseCode() == 200)  // Good response
					return true;
				else
					// Anything else is unwanted
					return false;

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else return false;
	}

	private _GetILgIptvShmRemote remoteLG = null;

	public _GetILgIptvShmRemote getRemoteLG() {
		return remoteLG;
	}

	private void initRemoteLG() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		try {
			if (remoteLG == null) {
				remoteLG = new _GetILgIptvShmRemote(this.getApplicationContext(), mHandler);
				remoteLG.onBindIptvRemote();       // IPTV 정보를 연결한다.
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	_SIMClientHandlerLGU _SIMClientHandlerLGU;

	private void putRemoteLG() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		if (IKaraokeTV.DEBUG) {
			Log.e(_toString(), getMethodName() + "mac " + remoteLG.mac);
			Log.e(_toString(), getMethodName() + "subScriberNO " + remoteLG.subScriberNo);
			Log.e(_toString(), getMethodName() + "secret num : " + SetopHandler.getInstance().getSecretNum());
		}

		_SIMClientHandlerLGU = new _SIMClientHandlerLGU(this);

		_SIMClientHandlerLGU.cont_no = remoteLG.subScriberNo;   // KY Ref. 208092830113
		_SIMClientHandlerLGU.stb_mac_addr = remoteLG.mac;       // KY Ref. 0002.141c.eae7

		if (remoteLG.configXml != null) {
			if (IKaraokeTV.DEBUG) Log.i(_toString(), "remoteLG.configXml");
			if (IKaraokeTV.DEBUG) Log.d(_toString(), remoteLG.configXml);
			XmlFinder xf = new XmlFinder();
			// xf.setXml( remoteLG.configXml);
			SharedPreferences pref = getSharedPreferences("Setting", 0);
			SharedPreferences.Editor edit = pref.edit();
			if (xf.find(remoteLG.configXml, "dbs")) {
				// _SIMClientHandlerLGU.dbs_hostname = xf.getAttribute("address");
				// _SIMClientHandlerLGU.dbs_port = Integer.parseInt(xf.getAttribute("port"));
				edit.putString("dbs_hostname", xf.getAttribute("address"));
				edit.putInt("dbs_port", Integer.parseInt(xf.getAttribute("port")));
			}
			if (xf.find(remoteLG.configXml, "isu")) {
				// ISUHandler.isu_hostname = xf.getAttribute("address");
				// ISUHandler.isu_port = Integer.parseInt(xf.getAttribute("port"));
				edit.putString("isu_hostname", xf.getAttribute("address"));
				edit.putInt("isu_port", Integer.parseInt(xf.getAttribute("port")));
			}
			edit.commit();
		}
		// if ( false == _SIMClientHandlerLGU.sendRequestRSi200n21 () ) // 가입주문내역조회 200.21 .
		// DataHandler.GetUserInfoFromKY( _SIMClientHandlerLGU.return_itemcode ); // 정액 가입유무, 유저 정보에 대한 환경파일을 금영서버에서 갖어온다.

		// LGU 독산동 문제점
		/*
		 * for ( int i = 0; i < 10; i++)
		 * {
		 * if ( DataHandler.isReachable == false )
		 * {
		 * try {
		 * Thread.sleep(1000);
		 * } catch (InterruptedException e) {
		 * e.printStackTrace();
		 * }
		 * }
		 * }
		 */
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	String telephone;

	private boolean getVenderLG() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]");

		if (IKaraokeTV.DEBUG) {
			Log.wtf(_toString(), getMethodName() + "mac " + remoteLG.mac);
			Log.wtf(_toString(), getMethodName() + "subScriberNO " + remoteLG.subScriberNo);
			Log.wtf(_toString(), getMethodName() + "secret num : " + SetopHandler.getInstance().getSecretNum());
		}

		int sbc_type = SetopHandler.getInstance().getSBC_TYPE();
		if (sbc_type == 0) this.telephone = "고객센터(국번없이 101)로 문의바랍니다";
		else this.telephone = "고객센터(1544-0001)로 문의바랍니다";

		// API 불안정으로 인한 임시 막음.
		Log.wtf("service", "setKaraokeMode : " + SetopHandler.getInstance().setKaraokeMode(true));

		// if ( Global.Inst().isTestBed )
		// {
		// ISUHandler.isu_hostname = "123.140.17.223" ; //test BED
		// ISUHandler.isu_port = 8900;
		// _SIMClientHandlerLGU.dbs_hostname = "123.140.17.223";
		// _SIMClientHandlerLGU.dbs_port = 8000;
		// }
		// else
		{
			SharedPreferences pref = getSharedPreferences("Setting", 0);
			_ISUHandler.isu_hostname = pref.getString("isu_hostname", "");
			_ISUHandler.isu_port = pref.getInt("isu_port", 0);
			_SIMClientHandlerLGU.dbs_hostname = pref.getString("dbs_hostname", "");
			_SIMClientHandlerLGU.dbs_port = pref.getInt("dbs_port", 0);
		}

		_SIMClientHandlerLGU.cont_no = remoteLG.subScriberNo;   // KY Ref. 208092830113
		_SIMClientHandlerLGU.stb_mac_addr = remoteLG.mac;       // KY Ref. 0002.141c.eae7
		//Boolean bCatchError = false;

		// if (Global.Inst().isRelease)
		{

			////test
			//if (true) {
			if (remoteLG.subScriberNo == null || remoteLG.subScriberNo.length() < 5) {
				//bCatchError = true;
				//doAlertExit(" 가입자 정보를 얻을수 없습니다.\n  더 이상 진행 할수 없습니다. \n" + remoteLG.subScriberNo, true);
				error_title = "확 인";
				error_message = "가입자 정보를 얻을수 없습니다.\n더 이상 진행 할수 없습니다. \n" + remoteLG.subScriberNo;
				error_exit = true;
				post(doMessage);
				return false;
			}

			/**
			 * 상품리스트
			 */
			_ISUHandler.getInstance();
			_ISUHandler.getInstance().getProductList(_SIMClientHandlerLGU.cont_no, _SIMClientHandlerLGU.stb_mac_addr);

			// 월정액에 가입되어있습니다. 혹은 노래방이용권을 구입해주세요
			// SIMClientHandler.sendRequestRSi200n21(); 1.1 가입 여부를 알아낸다.

			//test
			//if (true) {
			if (false == _SIMClientHandlerLGU.sendRequestRSi200n21()) {       // 가입주문내역조회 200.21 .
				//doAlertExit(_SIMClientHandlerLGU.return_code, _SIMClientHandlerLGU.return_message, this.telephone);
				post(doAlertExit);
				return false;
			}

			if (_SIMClientHandlerLGU.register) {
				// 서비스 사용자 인증 요청 100,11
				////test
				//if (true) {
				if (_SIMClientHandlerLGU.sendRequestRSi100n11() == false) {
					//bCatchError = true;
					//doAlertExit(_SIMClientHandlerLGU.return_code, _SIMClientHandlerLGU.return_message, Video2.this.telephone);
					post(doAlertExit);
					return false;
				}

				//if (bCatchError == false) {
				//// feedback
				//DataHandler.GetUserInfoFromKY(_SIMClientHandlerLGU.return_itemcode); // 정액 가입유무, 유저 정보에 대한 환경파일을 금영서버에서 갖어온다.
				//}
				//mVideoView.startProc();
				//
				//if (bCatchError == false && DataHandler.getInstance().IsServiceDate())
				//doAlertExit("\n\n무료 이용기간 입니다.\n금영노래방을 마음껏 이용해보세요.\n", false);
				//
				//if (bCatchError == false)
				//DataHandler.PollWatch(_SIMClientHandlerLGU.stb_mac_addr); // 시청률 폴.
				//
				//if (bCatchError == false)
				//{
				//// if ( DataHandler.getInstance().NeedUpdateVersionA( ATVKaraokeActivity.this ) == true )
				//// OMA doSelectMessage( "새로운 버전이 발견 \n 지금 업데이트 할까요? " );
				//// else
				//{
				//if (DataHandler.ADPOPUP_use)
				//doPopupAD();
				//}
				//}
			} else // _SIMClientHandlerLGU.register == false
			{
				//doAlertExit(_SIMClientHandlerLGU.return_code, _SIMClientHandlerLGU.return_message, Video2.this.telephone);
				post(doAlertExit);
				return false;
			} // _SIMClientHandlerLGU.register

		} // Global.Inst().isRelease

		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]");

		return true;
	}

	private String error_title;
	private String error_message;
	private boolean error_exit;
	private Runnable doMessage = new Runnable() {
		@Override
		public void run() {
			doAlert(error_title, error_message, error_exit);
		}
	};

	private Runnable doAlertExit = new Runnable() {
		@Override
		public void run() {
			doAlert(_SIMClientHandlerLGU.return_code, _SIMClientHandlerLGU.return_message, Video2.this.telephone);
		}
	};

	private class initAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

			if (getVenderLG()) {
				startMainActivity(null);
				//isReachable = isReachable(getApplicationContext());
			}

			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
			return null;
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + msg);

			if (msg.what == 0) {
			}

			putRemoteLG();
			new initAsyncTask().execute();

			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + msg);
		}
	}; // Handler()

}
