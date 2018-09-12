/*
// * Copyright 2011 The Android Open Source Project
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
 * filename	:	GCM.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ GCM.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

//import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseApplication;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * TODO NOTE:<br>
 * 푸시클래스(GCM)<br>
 * 전달푸시를처리한다.<br>
 * https://code.google.com/p/gcm/
 * 
 * @author isyoon
 * @since 2013. 12. 3.
 * @version 1.0
 */

public class GCM2 {
	final private static String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();
	String TAG = __CLASSNAME__;

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	private Context context = null;

	public GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();

	// private static final String EXTRA_MESSAGE = "message";
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	// private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	public String REGID = "";

	/**
	 * 
	 */
	public GCM2(Context context) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		this.context = context;

		if (gcm == null) {
			gcm = GoogleCloudMessaging.getInstance(context);
		}

		checkGCM();

	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	public boolean checkPlayServices(Activity activity) {
		return WidgetUtils.checkPlayServices(activity);
	}

	/**
	 * Stores the registration ID and the app versionCode in the application's {@code SharedPreferences}.
	 *
	 * @param context
	 *          application's context.
	 * @param regId
	 *          registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGcmPreferences(context);
		int appVersion = getAppVersion(context);
		Log2.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Gets the current registration ID for application on GCM service, if there is one.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGcmPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log2.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log2.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration ID and the app versionCode in the application's shared preferences.
	 */
	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				String GCM_SENDER_ID = _IKaraoke.GOOGLE_API.GCM_SENDER_ID_KPOP;

				if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(context.getPackageName())) {
					GCM_SENDER_ID = _IKaraoke.GOOGLE_API.GCM_SENDER_ID_JPOP;
				}

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "registerInBackground() " + getMethodName() + context.getPackageName() + ":" + GCM_SENDER_ID);

				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					REGID = gcm.register(GCM_SENDER_ID);
					msg = "Device registered, registration ID=" + REGID;

					// Persist the regID - no need to register again.
					storeRegistrationId(context, REGID);

					// You should send the registration ID to your server over HTTP, so it
					// can use GCM/HTTP or CCS to send messages to your app.
					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the device will send
					// upstream messages to a server that echo back the message using the
					// 'from' address in the message.

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, msg);
				}

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, REGID);
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
			}

		}.execute(null, null, null);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGcmPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences, but
		// how you store the regID in your app is up to you.
		return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
	 * messages to your app. Not needed for this demo since the device sends upstream messages
	 * to a server that echoes back the message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, REGID);

		// Your implementation here.
		handleGCMRegistration(context, REGID);
		// displayMessage(context, getString(R.string.gcm_registered));
		// ServerUtilities.register(context, registrationId);
		Intent intent = new Intent(_IKaraoke.GOOGLE_API.GCM_INTENT_FROM_GCM_REGISTRATION_CALLBACK);
		intent.putExtra("registration_id", REGID);
		context.sendBroadcast(intent);

		// 푸시아이디등록
		if (((_BaseApplication) context).KP_9003 != null && ((_BaseApplication) context).isLoginUser()
				&& !TextUtil.isEmpty(REGID)) {
			((_BaseApplication) context).KP_9003.KP_9003(((_BaseApplication) context).p_mid, "", REGID, "", "",
					"");
		}

	}

	public boolean checkGCM() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		REGID = getRegistrationId(context);

		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "SID=" + SID);
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "LSID=" + LSID);
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "Auth=" + Auth);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "REGID=" + REGID);

		boolean ret = false;

		if (!TextUtil.isEmpty(REGID)) {
			ret = true;
		}

		return ret;
	}

	public void handleGCMRegistration(Context context, String registrationId) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "handleGCMRegistration()");

		// 서버에서 넘어오는 메시지의 내용에 key이름 "registration_id"에는 이 기기에만 사용하는 인증키값이 담겨서 넘어온다.
		if (registrationId != null) {
			// TEST
			REGID = registrationId;
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "\nREGID=" + REGID);
		}
	}

	/**
	 * 절대로 UI에 접근하지 않는다.
	 * 
	 * @author isyoon
	 * 
	 */
	public void startGCM() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (!checkGCM()) {
			registerInBackground();
		}
	}

	public void testPushGCM() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					String GCM_SENDER_ID = _IKaraoke.GOOGLE_API.GCM_SENDER_ID_KPOP;

					if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(context.getPackageName())) {
						GCM_SENDER_ID = _IKaraoke.GOOGLE_API.GCM_SENDER_ID_JPOP;
					}

					Bundle data = new Bundle();
					data.putString("my_message", "Hello Push GCM!!!");
					data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");

					String id = Integer.toString(msgId.incrementAndGet());

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "testPushGCM() " + context.getPackageName() + ":" + GCM_SENDER_ID);

					gcm.send(GCM_SENDER_ID + "@gcm.googleapis.com", id, data);
					msg = "Sent message";
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
			}
		}.execute(null, null, null);
	}

	public void destroy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	}
}
