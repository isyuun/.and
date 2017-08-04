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
 * filename	:	SettingFragment2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ SettingFragment2.java
 * </pre>
 */
package kr.kymedia.karaoke.kpop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.kymedia.karaoke._IKaraoke;;
import kr.kymedia.karaoke.apps.BuildConfig;

/**
 * <pre>
 * 안드로이드 6.0 마시멜롱 런타임 권한
 * <a href="https://developer.android.com/intl/ko/training/permissions/index.html">Working with System Permissions</a>
 * <a href="https://www.google.com/design/spec/patterns/permissions.html">Permissions</a>
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-23
 */
class SettingFragment2 extends SettingFragment {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment5#onActivityCreated(Bundle)
	 */
	@Override
	protected void onActivityCreated() {
		super.onActivityCreated();
	}

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment5#onActivityResult(int, int, Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//if (IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "requestCode:" + requestCode + "-" + _IKaraoke.KARAOKE_REQUEST.get(requestCode) + ", resultCode:" + resultCode + "-" + _IKaraoke.KARAOKE_RESULT.get(resultCode) + " - " + data);
		super.onActivityResult(requestCode, resultCode, data);

		if (null != data && null != data.getExtras() && requestCode == _IKaraoke.KARAOKE_INTENT_ACTION_GOOGLE_ACCOUNT_PICKER && resultCode == _IKaraoke.KARAOKE_RESULT_OK) {
			//((EditText) findViewById(R.id.edt_login_email)).setText(getApp().KPparam.getGmail());
		}
	}
}
