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
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	Util2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke
 *    |_ Util2.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.data;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.app._Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 9. 1.
 * @version 1.0
 */
class Util2 extends Util {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	_Activity activity;

	public Util2(_Activity activity, Handler h) {
		super(h);
		this.activity = activity;
	}

	ImageView v;

	public void setImageView(int id) {
		try {
			this.v = (ImageView) activity.findViewById(id);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void setImageView(View v) {
		try {
			this.v = (ImageView) v;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	String url;

	@Override
	public void setImageUrl(String url) {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + url);
		super.setImageUrl(url);
		this.url = url;
	}

	@Override
	public void LoadImageFromWeb() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REQUEST_UTIL.get(m_iUtilType) + ":" + m_bitMap);

		// switch (m_iUtilType)
		// {
		// case REQUEST_UTIL_MAIN_EVENT_IMAGE:
		// break;
		// case REQUEST_UTIL_CUSTOMER_DETAIL_IMAGE:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_1:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_2:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_3:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_4:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_5:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_6:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_7:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_8:
		// break;
		// case REQUEST_UTIL_PROFILE_IMAGE_HOME:
		// break;
		// case REQUEST_UTIL_CERTIFY_PROFILE_IMAGE:
		// break;
		// case REQUEST_UTIL_MY_RECORD_PROFILE_IMAGE:
		// break;
		// case REQUEST_UTIL_MAIN_QUICK_IMAGE_01_ON:
		// break;
		// case REQUEST_UTIL_MAIN_QUICK_IMAGE_01_OFF:
		// break;
		// case REQUEST_UTIL_MAIN_QUICK_IMAGE_02_ON:
		// break;
		// case REQUEST_UTIL_MAIN_QUICK_IMAGE_02_OFF:
		// break;
		// case REQUEST_UTIL_EVENT_DETAIL_ON:
		// break;
		// case REQUEST_UTIL_EVENT_DETAIL_OFF:
		// break;
		// case REQUEST_UTIL_SHOP_ITEM_01:
		// break;
		// case REQUEST_UTIL_SHOP_ITEM_02:
		// break;
		// case REQUEST_UTIL_KY_LOGO:
		// break;
		// case REQUEST_UTIL_MIC:
		// break;
		// }

		if (this.activity != null && this.v != null && TextUtil.isNetworkUrl(this.url)) {
			putURLImage(this.v, this.url, false, 0);
			return;
		}

		super.LoadImageFromWeb();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REQUEST_UTIL.get(m_iUtilType) + ":" + m_bitMap);
	}

	private String getResourceEntryName(View v) {
		try {
			if (activity != null) {
				return activity.getResourceEntryName(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	private String getResourceEntryName(int resId) {
		try {
			if (activity != null) {
				return activity.getResourceEntryName(resId);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public void putURLImage(final ImageView v, final String url, final boolean resize, final int imageRes) {
		try {
			if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(v) + "," + url + "," + resize + "," + getResourceEntryName(imageRes));
			if (this.activity != null && v != null && TextUtil.isNetworkUrl(url)) {
				this.activity.putURLImage(v, url, false);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
