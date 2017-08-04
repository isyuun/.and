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
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	.prj
 * filename	:	BaseApplication5.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseApplication5.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-26
 */
public class BaseApplication5 extends BaseApplication4 {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	//Snackbar mSnackbar = null;
	//
	//public void popupSnackbar(CharSequence text, int dur) {
	//	try {
	//		if (mSnackbar != null) {
	//			mSnackbar.cancel();
	//		}
	//		mSnackbar = Snackbar.makeText(this, text, dur);
	//		mSnackbar.show();
	//	} catch (Exception e) {
	//		Log.e(__CLASSNAME__, Log.getStackTraceString(e));
	//	}
	//}
	//
	//public void popupSnackbar(String text, int dur) {
	//	try {
	//		if (mSnackbar != null) {
	//			mSnackbar.cancel();
	//		}
	//		mSnackbar = Snackbar.makeText(this, text, dur);
	//		mSnackbar.show();
	//	} catch (Exception e) {
	//		Log.e(__CLASSNAME__, Log.getStackTraceString(e));
	//	}
	//}
	//
	//public void popupSnackbar(int resId, int dur) {
	//	try {
	//		if (mSnackbar != null) {
	//			mSnackbar.cancel();
	//		}
	//		mSnackbar = Snackbar.makeText(this, resId, dur);
	//		mSnackbar.show();
	//	} catch (Exception e) {
	//		Log.e(__CLASSNAME__, Log.getStackTraceString(e));
	//	}
	//}
}
