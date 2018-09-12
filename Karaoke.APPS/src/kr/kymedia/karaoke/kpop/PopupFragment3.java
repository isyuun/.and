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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	PopupFragment3.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ PopupFragment3.java
 * </pre>
 */
package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._WebViewFragment;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-07-15
 */
class PopupFragment3 extends PopupFragment2 {
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * @see _WebViewFragment#onActivityCreated()
	 */
	@Override
	protected void onActivityCreated() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.onActivityCreated();
	}

	@Override
	public void openWebView() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + mURL);
		super.openWebView();
	}

	/**
	 * <pre>
	 * 풀스크린가리기"is_full" - true/false
	 *  true :
	 * </pre>
	 */
	boolean is_full;

	@Override
	protected void start() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.start();

		KPItem info = getInfo();

		String popup_title = info.getValue("popup_title");
		if (TextUtil.isEmpty(popup_title)) {
			popup_title = getString(R.string.menu_notice);
		}

		is_full = TextUtil.parseBoolean(info.getValue("is_full"), true);
		btn_type = TextUtil.parseInt(info.getValue("btn_type"), 0);
		getBaseActivity().setTitle(popup_title);

		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "is_full:" + is_full);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "is_pop:" + is_pop);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "btn_type:" + btn_type);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "url_img_pop:" + url_img_pop);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "url_htm_pop:" + url_htm_pop);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "url_pop:" + url_pop);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[INFO]" + "result_message:" + result_message);

		if (!is_full) {
			getBaseActivity().getSupportActionBar().show();
		} else {
			getBaseActivity().getSupportActionBar().hide();
		}

		if (!TextUtil.isEmpty(url_htm_pop)) {
			mURL = url_htm_pop;
		}
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + mURL);

		isCanGoBack = false;
		mMethod = "POST";

		String type = TextUtil.getMimeTypeFromUrl(mURL);
		boolean isImage = false;

		if (!TextUtil.isEmpty(type)) {
			isImage = type.toLowerCase().trim().contains("image");
		}

		if (isImage) {
			mMethod = "IMAGE";
		}

		View btn_confirm = findViewById(R.id.btn_confirm);
		if (btn_confirm != null) {
			btn_confirm.setVisibility(View.GONE);
		}

		View btn_close = findViewById(R.id.btn_close);
		if (btn_close != null) {
			btn_close.setVisibility(View.GONE);
		}

		/**
		 * <pre>
		 * 2. "btn_type" - "-2", "-1", "0", "1", "2"
		 * - "-2" : "확인", "닫기" 버튼 숨김
		 * - "-1" : "강제확인" 버튼 하단에 노출
		 * - "0" : "확인" 버튼 하단에 노출
		 * - "1" : "닫기" 버튼 하단에 노출
		 * - "2" : "확인", "닫기" 버튼 하단에 노출
		 * </pre>
		 */
		switch (btn_type) {
			case -2:
				break;

			case -1:
				if (btn_confirm != null) {
					btn_confirm.setVisibility(View.VISIBLE);
				}
				break;

			case 0:
				if (btn_confirm != null) {
					btn_confirm.setVisibility(View.VISIBLE);
				}
				break;

			case 1:
				if (btn_close != null) {
					btn_close.setVisibility(View.VISIBLE);
				}
				break;

			case 2:
				if (btn_confirm != null) {
					btn_confirm.setVisibility(View.VISIBLE);
				}
				if (btn_close != null) {
					btn_close.setVisibility(View.VISIBLE);
				}
				break;

			default:
				if (btn_close != null) {
					btn_close.setVisibility(View.VISIBLE);
				}
				break;
		}
	}

	/**
	 * 백버튼처리
	 */
	@Override
	public boolean onBackPressed() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + btn_type);
		/**
		 * 웹뷰로오픈시리플레시문제
		 //if (btn_type == -1) {
		 //	setResult(_IKaraoke.KARAOKE_RESULT_FINISH, null);
		 //}
		 */
		setResult(_IKaraoke.KARAOKE_RESULT_OK, null);
		return super.onBackPressed();
	}
}
