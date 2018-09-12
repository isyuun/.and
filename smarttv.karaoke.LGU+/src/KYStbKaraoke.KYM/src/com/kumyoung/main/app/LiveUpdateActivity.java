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
 * project	:	KYStbKaraoke.KYM
 * filename	:	BaseUpdateActivity.java
 * author	:	isyoon
 *
 * <pre>
 * com.kumyoung.main.app
 *    |_ BaseUpdateActivity.java
 * </pre>
 * 
 */

package com.kumyoung.main.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;

import com.kumyoung.gtvkaraoke.LiveUpdateBroadcastReceiver;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 6. 8.
 * @version 1.0
 */
public class LiveUpdateActivity extends BaseActivity3 {
	// private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public String toString() {
		super.toString();
		return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	private LiveUpdateBroadcastReceiver mReceiver = null;

	protected void registerLiveUpdateBroadcastReceiver() {
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());

		if (mReceiver != null)
			return;

		final IntentFilter theFilter = new IntentFilter();
		theFilter.addAction("android.lgt.appstore.LIVE_UPDATE_AUTO");
		mReceiver = new LiveUpdateBroadcastReceiver();
		registerReceiver(mReceiver, theFilter);
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[registerReceiver]" + mReceiver + ":" + theFilter);

		// update service start

		/**/
		// DataHandler.getInstance().UpdateService(); // start service
		// int pid = android.os.Process.myPid();

		PackageManager pm = this.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = pm.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		// NEW LGUPlus Store 14.8.11
		String version = packageInfo.versionName; // 버전 네임
		int versionCode = packageInfo.versionCode; // 버전 코드
		String checkUri = String.format("ozstore://LIVEUPDATE_MANUAL/Q13011202819/%s/%d/com.kumyoung.gtvkaraoke", version, versionCode);
		// test
		// String checkUri = String.format("ozstore://LIVEUPDATE_MANUAL/Q13011202819/%s/%d/com.kumyoung.gtvkaraoke", "1.0.0", 100);
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + checkUri);

		String pkgName = "com.lguplus.iptv3.updatecheck";
		String clsName = pkgName + ".UpdateService";

		Intent intentUpdate = new Intent();
		intentUpdate.setAction("ozstore.external.linked");
		intentUpdate.setComponent(new ComponentName(pkgName, clsName));
		intentUpdate.setData(Uri.parse(checkUri));
		ComponentName componentName = startService(intentUpdate);
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[startService]" + componentName + ":" + intentUpdate + ":" + intentUpdate.getData());
	}

	protected void unregisterLiveUpdateBroadcastReceiver() {
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());

		if (mReceiver != null) {
			if (mReceiver.isOrderedBroadcast()) {
				this.unregisterReceiver(mReceiver);
			}
		}
	}

	@Override
	protected void onResume() {
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());
		super.onResume();

		registerLiveUpdateBroadcastReceiver();
	}

	@Override
	protected void onPause() {
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());
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
		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName());

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

		Log.e(toString() + "[LiveUpdateBroadcastReceiver]", getMethodName() + "[startService]" + componentName + ":" + intentUpdate + ":" + intentUpdate.getData());
	}
}
