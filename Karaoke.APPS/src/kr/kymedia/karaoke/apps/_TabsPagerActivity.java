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
 * filename	:	_TabsPagerBaseActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ _TabsPagerBaseActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.os.Bundle;
import android.util.Log;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.util.EnvironmentUtils;

/**
 *
 * <pre>
 * </pre>
 *
 * @author isyoon
 * @since 2013. 8. 2.
 * @version 1.0
 * @see
 */
public class _TabsPagerActivity extends TabsPagerActivity2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public _TabsPagerActivity() {
		super();
	}

	@Override
	public void onPageSelected(int position) {

		super.onPageSelected(position);

		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());

		if (isUseDrawaerLaytout) {

		}
	}

	@Override
	public void addTabs() {

		super.addTabs();

		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	@Override
	public void addTab(String tag, String name, Class<?> cls, Bundle args) {

		super.addTab(tag, name, cls, args);

		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

}
