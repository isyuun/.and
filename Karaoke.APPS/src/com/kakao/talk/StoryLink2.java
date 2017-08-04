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
 * filename	:	StoryLink2.java
 * author	:	isyoon
 *
 * <pre>
 * com.kakao.talk
 *    |_ StoryLink2.java
 * </pre>
 * 
 */

package com.kakao.talk;

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
 * @see StoryLink2.java
 */
public class StoryLink2 extends StoryLink {
	private static StoryLink2 stroyLink = null;

	protected StoryLink2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static StoryLink2 getLink(Context context) {
		if (stroyLink != null)
			return stroyLink;

		return new StoryLink2(context);
	}

	/**
	 * @see com.kakao.talk.StoryLink#openKakaoLink(android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void openKakaoLink(Activity activity, String post, String appId, String appVer,
			String appName, String encoding, Map<String, Object> urlInfoAndroid) {

		if (TextUtils.isEmpty(post) || TextUtils.isEmpty(appId) || TextUtils.isEmpty(appVer) || TextUtils.isEmpty(appName) || TextUtils.isEmpty(encoding)) {
			String error = "";
			if (TextUtils.isEmpty(post)) {
				error = "param error : post is empty";
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
			super.openKakaoLink(activity, post, appId, appVer, appName, encoding, urlInfoAndroid);
		}
	}

	/**
	 * @see com.kakao.talk.StoryLink#openStoryLinkImageApp(android.app.Activity, java.lang.String)
	 */
	@Override
	public void openStoryLinkImageApp(Activity activity, String path) {

		super.openStoryLinkImageApp(activity, path);
	}

}
