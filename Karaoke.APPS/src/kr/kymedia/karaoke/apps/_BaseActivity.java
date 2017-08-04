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
 * project	:	Karaoke.KPOP
 * filename	:	BaseActivity0.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseActivity0.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

/**
 * 
 * TODO<br>
 * NOTE:<br>
 * 베이스루트액티비티
 * 
 * @author isyoon
 * @since 2013. 8. 1.
 * @version 1.0
 * @see
 */
public class _BaseActivity extends BaseActivity7 {

	/** Standard activity result: operation canceled. */
	public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
	/** Standard activity result: operation succeeded. */
	public static final int RESULT_OK = Activity.RESULT_OK;
	/** Start of user-defined activity results. */
	public static final int RESULT_FIRST_USER = Activity.RESULT_FIRST_USER;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// test
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG){
		// // If the Android version is lower than Jellybean, use this call to hide
		// // the status bar.
		// //if (Build.VERSION.SDK_INT < 16) {
		// // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// // WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// //} else {
		// // View decorView = getWindow().getDecorView();
		// // // Hide the status bar.
		// // int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		// // decorView.setSystemUiVisibility(uiOptions);
		// // // Remember that you should never show the action bar if the
		// // // status bar is hidden, so hide that too if necessary.
		// // //ActionBar actionBar = getSupportActionBar();
		// // //actionBar.hide();
		// //}
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// }

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	protected void onResume() {

		super.onResume();

		getApp().setActivity(this);
	}

	@Override
	public void setActionMenuVisible(Menu menu) {
		super.setActionMenuVisible(menu);

		// if (_IKaraoke.DEBUG) {
		// setActionMenuItemVisible(R.id.menu_refresh, true);
		// }
	}


	@Override
	public void refresh() {
		super.refresh();
	}

}
