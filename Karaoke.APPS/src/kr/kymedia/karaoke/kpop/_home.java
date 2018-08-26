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
 * 2012 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	home.java	
 * author		:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop 
 *    |_ home.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;

import kr.kymedia.karaoke.apps._BaseActivity;
import kr.kymedia.karaoke.apps.R;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 2. 16.
 * @version 1.0
 */
public class _home extends _BaseActivity {

	@Override
	protected void onNewIntent(Intent newIntent) {

		newIntent.setAction(Intent.ACTION_MAIN);
		newIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		super.onNewIntent(newIntent);
	}

	@Override
	public Intent getIntent() {

		Intent ret = super.getIntent();

		ret.setAction(Intent.ACTION_MAIN);
		ret.addCategory(Intent.CATEGORY_LAUNCHER);
		ret.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

		return ret;
	}

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addFragment(R.id.fragment1, new _HomeFragment(), "fragment1");
		// test
		// FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.add(R.id.fragment1, new BaseFragment2(), "fragment1");
		// ft.commit();

	}

	/**
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);
		// 공지사항
		setActionMenuItemVisible(R.id.menu_notice, true);
	}

	DialogInterface.OnClickListener mWarnMobilePListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {

		}
	};

	DialogInterface.OnClickListener mWarnMobileNListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			close();
		}
	};

	OnCancelListener mWarnMobileCListener = new OnCancelListener() {
		public void onCancel(DialogInterface dialog) {
			close();
		}
	};

	DialogInterface.OnClickListener mNoNetworkPListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			close();
		}
	};

	DialogInterface.OnClickListener mNoNetworkNListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			close();
		}
	};

	OnCancelListener mNoNetworkCListener = new OnCancelListener() {
		public void onCancel(DialogInterface dialog) {
			close();
		}
	};

}
