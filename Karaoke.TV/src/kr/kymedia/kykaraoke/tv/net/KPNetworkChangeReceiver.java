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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	KPNetworkChangeReceiver.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.net
 *    |_ KPNetworkChangeReceiver.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.net;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.R;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-04-22
 */
public class KPNetworkChangeReceiver extends kr.kymedia.karaoke.net.NetworkChangeReceiver {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();

		String message = context.getString(R.string.message_error_network_timeout);

		if (null != ni) {
			message += "\n" + "type:" + ni.getType();
			Log.e("[NetworkInfo]" + _toString(), "[NetworkInfo]" + ni.getTypeName() + ", type:" + ni.getType() + ", isAvailable:" + ni.isAvailable() + ", isConnected:" + ni.isConnected() + ", isConnectedOrConnecting:" + ni.isConnectedOrConnecting());
			Log.e("[NetworkInfo]" + _toString(), "[NetworkInfo]" + ni.getState());
			Log.e("[NetworkInfo]" + _toString(), "[NetworkInfo]" + ni.getDetailedState());
			Log.e("[NetworkInfo]" + _toString(), "[NetworkInfo]" + ni.getSubtypeName());
			Log.e("[NetworkInfo]" + _toString(), "[NetworkInfo]" + ni.getExtraInfo());
			Log.e("[NetworkInfo]" + _toString(), "[NetworkInfo]" + ni.getReason());
		}

		if (ni == null || !ni.isConnected() || !ni.isConnectedOrConnecting()) {
			// display error
			Log.wtf(_toString(), getMethodName() + "[NG]" + message);
			//sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			return;
		}
	}
}
