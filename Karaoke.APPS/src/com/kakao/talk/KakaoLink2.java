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
 * filename	:	KakaoLink2.java
 * author	:	isyoon
 *
 * <pre>
 * com.kakao.talk
 *    |_ KakaoLink2.java
 * </pre>
 * 
 */

package com.kakao.talk;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 4. 30.
 * @version 1.0
 * @see KakaoLink2.java
 */
public class KakaoLink2 extends KakaoLink {
	private static KakaoLink2 kakaoLink = null;

	public static KakaoLink2 getLink(Context context) {
		if (kakaoLink != null)
			return kakaoLink;

		return new KakaoLink2(context);
	}

	protected KakaoLink2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see com.kakao.talk.KakaoLink#openKakaoLink(android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void openKakaoLink(Activity activity, String url, String message, String appId,
			String appVer, String appName, String encoding) {

		if (TextUtils.isEmpty(url) || TextUtils.isEmpty(message) || TextUtils.isEmpty(appId) || TextUtils.isEmpty(appVer) || TextUtils.isEmpty(appName) || TextUtils.isEmpty(encoding)) {
			String error = "";
			if (TextUtils.isEmpty(url)) {
				error = "param error : url is empty";
			} else if (TextUtils.isEmpty(message)) {
				error = "param error : message is empty";
			} else if (TextUtils.isEmpty(appId)) {
				error = "param error : appId is empty";
			} else if (TextUtils.isEmpty(appVer)) {
				error = "param error : appVer is empty";
			} else if (TextUtils.isEmpty(appName)) {
				error = "param error : appName is empty";
			} else if (TextUtils.isEmpty(encoding)) {
				error = "param error : encoding is empty";
			}
			throw new IllegalArgumentException(error);
		} else {
			super.openKakaoLink(activity, url, message, appId, appVer, appName, encoding);
		}
	}

	/**
	 * @see com.kakao.talk.KakaoLink#openKakaoAppLink(android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList)
	 */
	@Override
	public void openKakaoAppLink(Activity activity, String url, String message, String appId,
			String appVer, String appName, String encoding, ArrayList<Map<String, String>> metaInfoArray) {

		if (TextUtils.isEmpty(url) || TextUtils.isEmpty(message) || TextUtils.isEmpty(appId) || TextUtils.isEmpty(appVer) || TextUtils.isEmpty(appName) || TextUtils.isEmpty(encoding)) {
			String error = "";
			if (TextUtils.isEmpty(url)) {
				error = "param error : url is empty";
			} else if (TextUtils.isEmpty(message)) {
				error = "param error : message is empty";
			} else if (TextUtils.isEmpty(appId)) {
				error = "param error : appId is empty";
			} else if (TextUtils.isEmpty(appVer)) {
				error = "param error : appVer is empty";
			} else if (TextUtils.isEmpty(appName)) {
				error = "param error : appName is empty";
			} else if (TextUtils.isEmpty(encoding)) {
				error = "param error : encoding is empty";
			}
			throw new IllegalArgumentException(error);
		} else {
			super.openKakaoAppLink(activity, url, message, appId, appVer, appName, encoding, metaInfoArray);
		}
	}

}
