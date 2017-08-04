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
 * 2014 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP.KAR
 * filename	:	home.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.kar
 *    |_ home.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop.kar;

import android.content.Intent;
import android.os.Bundle;

//import com.twwpc.duzea106950.AdListener;
//import com.twwpc.duzea106950.Universal;

/**
 * 
 * TODO<br>
 * 
 * <pre></pre>
 * 
 * @author isyoon
 * @since 2014. 3. 27.
 * @version 1.0
 */
public class home extends kr.kymedia.karaoke.kpop._home {

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		// new Runnable() {
		// public void run() {
		// if (getApp().isLoginUser()) {
		// initAirPush();
		// }
		// }
		// }.run();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		// new Runnable() {
		// public void run() {
		// if (getApp().isLoginUser()) {
		// initAirPush();
		// }
		// }
		// }.run();
	}

	// Universal airsdk; //Declare SDK here
	//
	// void initAirPush() {
	// try {
	// if (_IKaraoke.DEBUG)Log.w("Ads - airsdk", __CLASSNAME__ + ":initAds:" + getMethodName() + Float.parseFloat(_IKaraoke.P_VER));
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// return;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// }
	//
	// //if (Float.parseFloat(_IKaraoke.P_VER) <= 2.42f) {
	// // return;
	// //}
	//
	// if (_IKaraoke.DEBUG)Log.e("Ads - airsdk", __CLASSNAME__ + ":initAds:" + getMethodName() + airsdk + getApp().isPassUser());
	//
	// if (airsdk != null) {
	// android.util.Log.d("Ads - airsdk - NOTI", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + ":" + getApp().isPassUser());
	// airsdk.startNotificationAd(false);
	// return;
	// }
	//
	// //if (!isACTIONMAIN()) {
	// // return;
	// //}
	//
	// if (getApp().isPassUser()) {
	// Universal.enableSDK(getApp(), false);
	// return;
	// }
	//
	// android.util.Log.d("Ads - airsdk - PUSH", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + ":" + getApp().isPassUser());
	//
	// //if (airsdk == null)
	// airsdk = new Universal(getApp(), new AdListener() {
	//
	// @Override
	// public void onSmartWallAdShowing() {
	//
	// android.util.Log.i("Ads - airsdk", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onSmartWallAdClosed() {
	//
	// android.util.Log.i("Ads - airsdk", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onSDKIntegrationError(String arg0) {
	//
	// android.util.Log.i("Ads - airsdk", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdError(String arg0) {
	//
	// android.util.Log.i("Ads - airsdk", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "- " + arg0);
	//
	// }
	//
	// @Override
	// public void onAdCached(AdType arg0) {
	//
	// android.util.Log.i("Ads - airsdk", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "- " + arg0);
	//
	// }
	//
	// @Override
	// public void noAdAvailableListener() {
	//
	// android.util.Log.i("Ads - airsdk", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// }, true);
	//
	// android.util.Log.d("Ads - airsdk - NOTI", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + ":" + getApp().isPassUser());
	// airsdk.startNotificationAd(false);
	// //test
	// //airsdk.startNotificationAd(_IKaraoke.DEBUG);
	//
	// if (_IKaraoke.DEBUG) {
	// //airsdk.startIconAd();
	// //airsdk.callSmartWallAd();
	// ////To start Overlay Dialog Ad:
	// //airsdk.callOverlayAd();
	// ////To start AppWall Ad:
	// //airsdk.callAppWall();
	// ////To start Landing Page Ad:
	// //airsdk.callLandingPageAd();
	// ////To start Rich Media Interstitial Ad:
	// //airsdk.displayRichMediaInterstitialAd();
	// ////To start Video Ad:
	// //airsdk.callVideoAd();
	// }
	// } catch (Exception e) {
	//
	// //e.printStackTrace();
	// android.util.Log.e("Ads - airsdk",
	// "isPassUser:" + getApp().isPassUser() + Log.getStackTraceString(e));
	//
	// }
	//
	// }

}
