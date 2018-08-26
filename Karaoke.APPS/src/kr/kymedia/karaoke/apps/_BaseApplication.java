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
 * 2014 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	_Application.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ _Application.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import android.util.Log;

/**
 * <pre>
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 4. 30.
 * @version 1.0
 */
public class _BaseApplication extends BaseApplication5 {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
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
	 * 화면복귀시 리플레쉬여부확인
	 */
	public boolean isRefresh = false;

	@Override
	public void onCreate() {

		super.onCreate();

		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", _toString() + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApplicationContext(), _toString() + ":" + getMethodName());
	}

	@Override
	public void onTerminate() {

		super.onTerminate();

		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", _toString() + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApplicationContext(), _toString() + ":" + getMethodName());
	}

}
