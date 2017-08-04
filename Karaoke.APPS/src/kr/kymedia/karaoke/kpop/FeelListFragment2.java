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
 * filename	:	FeelListFragment2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FeelListFragment2.java
 * </pre>
 */
package kr.kymedia.karaoke.kpop;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

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
 * @since 2016-05-19
 */
class FeelListFragment2 extends FeelListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (BuildConfig.DEBUG) Log.d(__CLASSNAME__, getMethodName() + nm + "," + position + "," + v.getId());
		super.onItemClick(parent, v, position, id);
	}

	@Override
	public void onListItemClick(View v) {
		String nm = getResourceEntryName(v.getId());
		if (BuildConfig.DEBUG) Log.d(__CLASSNAME__, getMethodName() + nm + "," + getPositionForView((View) v.getParent()) + "," + v.getId());
		super.onListItemClick(v);
	}
}
