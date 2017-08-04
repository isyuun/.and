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
 * project	:	Karaoke.KPOP.OLD
 * filename	:	WaringFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ WaringFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._WebViewFragment;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 8. 7.
 * @version 1.0
 */

class PopupFragment2 extends _WebViewFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	// -- "닫기" 버튼 선택 시는 팝업 이미지만 닫음.
	// * KP_0000 프로토콜의 "info" 에 팝업 이미지 출력 정보 추가.
	/**
	 * <br>
	 * 1. "is_pop" - "Y" / "N" <br>
	 * - "Y" : 메인에 팝업 이미지 노출 <br>
	 * - "N" : 메인에 팝업 이미지 미노출
	 */
	String is_pop;
	/**
	 * <pre>
	 * 2. "btn_type" - "-2", "-1", "0", "1", "2"
	 * - "-2" : "확인", "닫기" 버튼 숨김
	 * - "-1" : "강제종료" 버튼 하단에 노출
	 * - "0" : "확인" 버튼 하단에 노출
	 * - "1" : "닫기" 버튼 하단에 노출
	 * - "2" : "확인", "닫기" 버튼 하단에 노출
	 * </pre>
	 */
	int btn_type;
	/**
	 * 3. "url_img_pop" : 메인 팝업 이미지 URL
	 */
	String url_img_pop;
	String url_htm_pop;
	/**
	 * 4. "url_pop" : 메인 팝업 이미지의 "확인" 버튼 선택 시 링크 주소
	 */
	String url_pop;
	String result_message;

	@Override
	public void onCreateOptionsMenu(Menu menu1, MenuInflater menuinflater) {

		// super.onCreateOptionsMenu(menu1, menuinflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_popup2, container, false);

		onCreateView();

		return mRootView;
	}

	LinearLayout mContentView;

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		mContentView = (LinearLayout) findViewById(R.id.con_popup);
		// mContentView.setVisibility(View.INVISIBLE);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mContentView);
		if (mContentView != null) {
			// mContentView.removeAllViews();
			mRootView.removeView(mWebView);
			mContentView.removeView(mWebView);
			LinearLayout.LayoutParams params = (LayoutParams) mWebView.getLayoutParams();
			params.width = LinearLayout.LayoutParams.MATCH_PARENT;
			params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
			// params.weight = 1.0f;
			mWebView.setLayoutParams(params);
			mContentView.addView(mWebView);
			mContentView.setVisibility(View.VISIBLE);
			mWebView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void openWebView() {

		// test
		// mURL = "http://thechive.com";
		// mMethod = "";

		// findViewById(R.id.img_popup).setVisibility(View.GONE);
		// mWebView.setVisibility(View.VISIBLE);
		// super.openWebView();
		if (("IMAGE").equalsIgnoreCase(mMethod)) {
			mWebView.setVisibility(View.GONE);
			putURLImage(mContext, (ImageView) findViewById(R.id.img_popup), url_img_pop, true, R.drawable.bg_trans);
		} else {
			findViewById(R.id.img_popup).setVisibility(View.GONE);
			super.openWebView();
		}

		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.getSettings().setSupportZoom(false);
		mWebView.getSettings().setLoadWithOverviewMode(false);
		mWebView.getSettings().setUseWideViewPort(false);
	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		try {
			if (mContentView != null) {
				mContentView.removeAllViews();
				if (mWebView != null) {
					mWebView.destroy();
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void start() {

		super.start();

		KPItem info = getInfo();

		is_pop = info.getValue("is_pop");
		btn_type = TextUtil.parseInt(info.getValue("btn_type"));
		url_img_pop = info.getValue("url_img_pop");
		url_htm_pop = info.getValue("url_htm_pop");
		url_pop = info.getValue("url_pop");
		result_message = info.getValue("result_message");

		// test
		// url_img_pop = "http://thechive.com/2013/12/18/if-you-like-tattoos-get-in-here-45-photos/";

		mURL = url_img_pop;
		mMethod = "POST";
		isCanGoBack = false;

		String type = TextUtil.getMimeTypeFromUrl(mURL);
		boolean isImage = false;

		if (!TextUtil.isEmpty(type)) {
			isImage = type.toLowerCase().trim().contains("image");
		}

		if (isImage) {
			mMethod = "IMAGE";
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		View v = null;
		// v = findViewById(R.id.img_popup);
		// if (v != null) {
		// if (URLUtil.isNetworkUrl(url_img_pop)) {
		// putURLImage(mContext, (ImageView) v, url_img_pop, true, R.drawable.bg_trans);
		// v.setVisibility(View.VISIBLE);
		// } else {
		// v.setVisibility(View.GONE);
		// }
		// }
		//
		// v = findViewById(R.id.txt_popup);
		// if (v != null) {
		// putValue(v, txt_pop);
		// if (TextUtil.isEmpty(txt_pop)) {
		// v.setVisibility(View.GONE);
		// }
		// }

		///**
		// * 2. "btn_type" - "-1", "0", "1", "2"
		// * - "-1" : "강제확인" 버튼 하단에 노출
		// * - "0" : "확인" 버튼 하단에 노출
		// * - "1" : "닫기" 버튼 하단에 노출
		// * - "2" : "확인", "닫기" 버튼 하단에 노출
		// */
		//v = findViewById(R.id.btn_confirm);
		//if (v != null) {
		//	v.setVisibility(View.GONE);
		//}
		//v = findViewById(R.id.btn_close);
		//if (v != null) {
		//	v.setVisibility(View.GONE);
		//}
		//switch (btn_type) {
		//case -1:
		//	v = findViewById(R.id.btn_confirm);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//case 0:
		//	v = findViewById(R.id.btn_confirm);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//case 1:
		//	v = findViewById(R.id.btn_close);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//case 2:
		//	v = findViewById(R.id.btn_confirm);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	v = findViewById(R.id.btn_close);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//default:
		//	v = findViewById(R.id.btn_close);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		if (v.getId() == R.id.img_popup || v.getId() == R.id.btn_confirm) {
			if (btn_type == -1) {
				setResult(_IKaraoke.KARAOKE_RESULT_FINISH, null);
				// onOptionsItemSelected(R.id.base, "", true);
			}
			close();
			openWebView(webview.class, "", "", "", url_pop, "POST", false);
		} else if (v.getId() == R.id.con_popup && ("IMAGE").equalsIgnoreCase(mMethod)) {
			close();
			openWebView(webview.class, "", "", "", url_pop, "POST", false);
		} else if (v.getId() == R.id.btn_close) {
			close();
		} else {
		}
	}

	/**
	 * 백버튼처리
	 */
	@Override
	public boolean onBackPressed() {
		if (btn_type == -1) {
			setResult(_IKaraoke.KARAOKE_RESULT_FINISH, null);
		}
		return super.onBackPressed();
	}
}
