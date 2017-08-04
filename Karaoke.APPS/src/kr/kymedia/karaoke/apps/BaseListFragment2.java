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
 * filename	:	BaseListFragment2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseListFragment2.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;

/**
 * <pre>
 * 안드로이드 6.0 마시멜롱 런타임 권한
 * <a href="https://developer.android.com/intl/ko/training/permissions/index.html">Working with System Permissions</a>
 * <a href="https://www.google.com/design/spec/patterns/permissions.html">Permissions</a>
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-20
 */
class BaseListFragment2 extends BaseListFragment {
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

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment2#onOptionsItemSelected(int, String, boolean)
	 */
	@Override
	public Intent onOptionsItemSelected(int id, String menu_name, boolean open) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[PERMISSION]" + getResourceEntryName(id) + ":" + menu_name + " - " + open);
		return super.onOptionsItemSelected(id, menu_name, open);
	}

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment2#onContextItemSelected(Context, int, KPItem, KPItem, boolean)
	 */
	@Override
	protected Intent onContextItemSelected(Context context, int id, KPItem info, KPItem list, boolean open) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[PERMISSION]" + getResourceEntryName(context, id) + " - " + open);
		return super.onContextItemSelected(context, id, info, list, open);
	}
}
