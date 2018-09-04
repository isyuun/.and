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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV.LGT
 * filename	:	Main2X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.gtv
 *    |_ Main2X.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.gtv;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;
import com.kumyoung.gtvkaraoke.LiveUpdateBroadcastReceiver;

import kr.kymedia.kykaraoke.api.IKaraokeTV;

/**
 * <pre>
 * 라이브업데이트기능
 * 어플종료기능
 * </pre>
 *
 * @author isyoon
 * @since 2015. 4. 15.
 * @version 1.0
 */
class Main1 extends Main {
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

	public static String BROADCASTACTION = "others.menu.start";

	// bgkim APP KILL 이벤트 받으면 실제로 죽이는 리시버
	private static final String ACTION_DO_SUSPEND = "com.marvell.AppKiller.DO_SUSPEND";

	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// closeThisMenu();
		}
	};

	protected BroadcastReceiver receiverAppKill = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + savedInstanceState);
		super.onCreate(savedInstanceState);

		// commonBtnSetting();

		IntentFilter filter = new IntentFilter();
		filter.addAction(BROADCASTACTION);
		registerReceiver(receiver, filter);

		IntentFilter filterAppKill = new IntentFilter();
		filterAppKill.addAction(ACTION_DO_SUSPEND);
		registerReceiver(receiverAppKill, filterAppKill);

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + savedInstanceState);
	}

	// UI처리나 스케줄링을 위한 액티비티 마다있는 핸들러
	protected Handler mainHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			recieveMessage(msg);
		}
	};

	protected void setTitle(String titleStr) {
		/*
		 * TextView titleTextView = (TextView) findViewById(R.id.common_main_song_list_title);
		 * if(titleTextView != null) {
		 * titleTextView.setText(titleStr);
		 * }
		 */
	}

	// 메인핸들러 메시지를 받아주는 메소드
	protected void recieveMessage(Message msg) {
	}

	@Override
	public void startActivity(Intent intent) {
		startActivityForResult(intent, -99999);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(0, 0);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
		if (receiver != null) {
			unregisterReceiver(receiver);
		}

		if (receiverAppKill != null) {
			unregisterReceiver(receiverAppKill);
		}
	}

	private LiveUpdateBroadcastReceiver mReceiver = null;

	protected void registerLiveUpdateBroadcastReceiver() {
		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());

		if (mReceiver != null) return;

		final IntentFilter theFilter = new IntentFilter();
		theFilter.addAction("android.lgt.appstore.LIVE_UPDATE_AUTO");
		mReceiver = new LiveUpdateBroadcastReceiver();
		registerReceiver(mReceiver, theFilter);
		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[registerReceiver]" + mReceiver + ":" + theFilter);

		// update service start

		/**/
		// DataHandler.getInstance().UpdateService(); // start service
		// int pid = android.os.Process.myPid();

		PackageManager pm = this.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = pm.getPackageInfo(this.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		// NEW LGUPlus Store 14.8.11
		String version = packageInfo.versionName; // 버전 네임
		int versionCode = packageInfo.versionCode; // 버전 코드
		String checkUri = String.format("ozstore://LIVEUPDATE_MANUAL/Q13011202819/%s/%d/com.kumyoung.gtvkaraoke", version, versionCode);
		// test
		// String checkUri = String.format("ozstore://LIVEUPDATE_MANUAL/Q13011202819/%s/%d/com.kumyoung.gtvkaraoke", "1.0.0", 100);
		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + checkUri);

		String pkgName = "com.lguplus.iptv3.updatecheck";
		String clsName = pkgName + ".UpdateService";

		Intent intentUpdate = new Intent();
		intentUpdate.setAction("ozstore.external.linked");
		intentUpdate.setComponent(new ComponentName(pkgName, clsName));
		intentUpdate.setData(Uri.parse(checkUri));
		ComponentName componentName = startService(intentUpdate);
		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[startService]" + componentName + ":" + intentUpdate + ":" + intentUpdate.getData());
	}

	protected void unregisterLiveUpdateBroadcastReceiver() {
		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());

		if (mReceiver != null) {
			if (mReceiver.isOrderedBroadcast()) {
				this.unregisterReceiver(mReceiver);
			}
		}
	}

	@Override
	protected void onResume() {

		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());
		super.onResume();

		registerLiveUpdateBroadcastReceiver();
	}

	@Override
	protected void onPause() {

		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());
		super.onPause();

		unregisterLiveUpdateBroadcastReceiver();
	}

	/**
	 * <pre>
	 * startService(...) 액티비티에서처리하는게....
	 * </pre>
	 *
	 */
	public void startLiveUpdateService() {
		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());

		// update 존재
		String pkgName = "com.lguplus.iptv3.updatecheck";
		String clsName = pkgName + ".UpdateService";

		Intent intentUpdate = new Intent();
		intentUpdate.setAction("ozstore.external.linked");
		intentUpdate.setComponent(new ComponentName(pkgName, clsName));
		intentUpdate.setData(Uri.parse("ozstore://UPDATE/Q13011202819"));
		ComponentName componentName = startService(intentUpdate);

		postDelayed(new Runnable() {

			@Override
			public void run() {

				// Toast.makeText(context, "어플리케이션 업데이트를 위해 다시 시작됩니다.", Toast.LENGTH_SHORT).show();
				doAlert(99998, "최신 버전으로 자동 업데이트 중입니다.", "어플리케이션을 다시 실행해주세요.");
			}
		}, 500);

		Log.e(_toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[startService]" + componentName + ":" + intentUpdate + ":" + intentUpdate.getData());
	}

}
