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
 * 2012 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	WebViewFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ WebViewFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 7. 12.
 * @version 1.0
 */

class WebViewFragment extends _BaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected WebView mWebView = null;
	protected String mURL = "";
	protected String mMethod = "";
	protected boolean isCanGoBack = true;

	ProgressBar mProgressBar;

	public WebViewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_webview, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		// mWebContainer = (FrameLayout) getBaseActivity().findViewById(R.id.fragment1);
		mWebView = new WebView(getBaseActivity());
		mRootView.addView(mWebView);

		ViewGroup.LayoutParams params = mWebView.getLayoutParams();
		params.width = ViewGroup.LayoutParams.MATCH_PARENT;
		params.height = ViewGroup.LayoutParams.MATCH_PARENT;
		mWebView.setLayoutParams(params);

		mWebView.getSettings().setSavePassword(false);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		mWebView.getSettings().setAppCacheEnabled(false);

		mWebView.getSettings().setJavaScriptEnabled(true); // 웹뷰에서 자바스크립트실행가능
		// Bridge 인스턴스 등록
		// [출처] 안드로이드 하이브리드 앱 - WebView과 App(NDK)간 통신하기 |작성자 구피
		mWebView.addJavascriptInterface(new JavaScriptInterface(), "KpopHolicApi");

		// 페이지리사이즈관련
		// mWebView.getSettings().setDisplayZoomControls(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		//
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mWebView.setScrollbarFadingEnabled(true);

		// mProgressBar = (ProgressBar) this.findViewById(R.id.pro);

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "onProgressChanged() " + progress);

				super.onProgressChanged(view, progress);
				if (mProgressBar != null) {
					mProgressBar.setVisibility(View.VISIBLE);
					mProgressBar.setProgress(progress);
				}
			}
		});

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onPageStarted() ");
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, url);

				super.onPageStarted(view, url, favicon);
				// startLoading(__CLASSNAME__, getMethodName());
				// stopLoading(__CLASSNAME__, getMethodName());
				if (mURL != null && !mURL.equalsIgnoreCase(url)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url);
				}
				mURL = url;
				mWebView.clearHistory();
				if (mProgressBar != null) {
					mProgressBar.setVisibility(View.VISIBLE);
				}
				setRefreshComplete();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onPageFinished() ");
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, url);

				super.onPageFinished(view, url);
				// startLoading(__CLASSNAME__, getMethodName());
				stopLoading(__CLASSNAME__, getMethodName());
				if (mURL != null && !mURL.equalsIgnoreCase(url)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url);
				}
				mURL = url;
				mWebView.clearHistory();
				if (mProgressBar != null) {
					mProgressBar.setVisibility(View.GONE);
				}
				setRefreshComplete();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onReceivedError() ");
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, Integer.toString(errorCode));
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, description);
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, failingUrl);

				super.onReceivedError(view, errorCode, description, failingUrl);
				stopLoading(__CLASSNAME__, getMethodName());
				Toast.makeText(getBaseActivity(), description, Toast.LENGTH_SHORT).show();
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				mWebView.clearHistory();
				if (mProgressBar != null) {
					mProgressBar.setVisibility(View.GONE);
				}
				setRefreshComplete();
			}

		});

		// LinearLayout.LayoutParams params = (LayoutParams) mWebView.getLayoutParams();
		// params.height = LayoutParams.MATCH_PARENT;
		// params.width = LayoutParams.MATCH_PARENT;
		// mWebView.setLayoutParams(params);
	}

	@Override
	public void onRefresh() {

		getWebURL();
		super.onRefresh();
	}

	@Override
	protected void start() {

		super.start();

		getWebURL();

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		try {
			mRootView.removeAllViews();
			mWebView.destroy();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public String getWebURL() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String URL = "";
		Bundle extras = getExtras();

		if (extras != null) {
			mMethod = extras.getString("method");
			// 최초조회시
			URL = extras.getString("url");
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, mURL);
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, URL);
			if (URL != null && !URL.equalsIgnoreCase(mURL)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, mURL);
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, URL);
				mURL = URL;
			}
		}

		return URL;
	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		openWebView();
	}

	/**
	 * 진저브레드이하(2.3.x) HTML인풋테크 키보드보기 오류 (소액결제, 쪽지보내기,...)
	 * <a href="http://stackoverflow.com/questions/9262092/webview-html-input-form-not-showing-allowing-keyboard">Webview html input form not showing/allowing keyboard</a>
	 */
	public void openWebView() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mWebView);

		try {

			if (mWebView == null) {
				return;
			}

			startLoading(__CLASSNAME__, getMethodName());

			if (("IMAGE").equalsIgnoreCase(mMethod)) {
				mWebView.getSettings().setBuiltInZoomControls(true);
				mWebView.getSettings().setSupportZoom(true);
				mWebView.getSettings().setLoadWithOverviewMode(true);
				mWebView.getSettings().setUseWideViewPort(true);
			} else {
				mWebView.getSettings().setBuiltInZoomControls(false);
				mWebView.getSettings().setSupportZoom(false);
				mWebView.getSettings().setLoadWithOverviewMode(false);
				mWebView.getSettings().setUseWideViewPort(false);
			}

			if (("POST").equalsIgnoreCase(mMethod)) {
				// POST
				postData(mURL);
			} else {
				// GET/IMAGE
				loadUrl(mURL);
			}

			// Webview html input form not showing/allowing keyboard
			mWebView.requestFocus(View.FOCUS_DOWN);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void loadUrl(String url) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mWebView);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "url:" + url);

		if (mWebView == null) {
			return;
		}

		if (("IMAGE").equalsIgnoreCase(mMethod)) {
			loadImg(url);
		} else {
			mWebView.loadUrl(url);
		}
	}

	@SuppressLint("NewApi")
	protected void loadImg(String url) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mWebView);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "url:" + url);

		if (mWebView == null) {
			return;
		}

		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

		try {
			int width = mWebView.getWidth();
			int height = mWebView.getHeight();

			Display display = getBaseActivity().getWindowManager().getDefaultDisplay();
			if (_IKaraoke.IS_ABOVE_ICE_CREAM_SANDWICH) {
				Rect rect = new Rect();
				display.getRectSize(rect);
				width = rect.width();
				height = rect.height();
			} else {
				width = display.getWidth();
				height = display.getHeight();
			}

			String data = "<html><head><title></title>"
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; \">"
					// + "<meta name=\"viewport\"\"content=\"width=100%, initial-scale=1.00 \" />" + "</head>"
					+ "<meta name=\"viewport\"\"content=\"width=" + width + ", initial-scale=1.00 \" />"
					+ "<meta name=\"viewport\"\"content=\"height=" + height + ", initial-scale=1.00 \" />"
					+ "</head>"
					+ "<body style=\"margin:0; padding:0\">"
					+ "<center><img width=\"100%\" height: auto; src=\"" + url + "\" /></center></body></html>";

			String base64 = android.util.Base64.encodeToString(data.getBytes("UTF-8"),
					android.util.Base64.DEFAULT);

			mWebView.loadData(base64, "text/html", "base64");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void postData(String url) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, url);

		if (mWebView == null) {
			return;
		}

		// RequestParams params = RequestParams.getParams(url);

		final String query[] = TextUtils.split(url, "\\?");
		if (query.length < 2) {
			loadUrl(url);
			return;
		}

		try {
			StringBuffer postDataBuilder = new StringBuffer();

			postDataBuilder.append(query[1]);

			byte[] postData = postDataBuilder.toString().getBytes("UTF8");
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, postData.toString());

			mWebView.postUrl(query[0], postData);

			mURL = query[0];

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, mURL);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 백버튼처리
	 */
	@Override
	public boolean onBackPressed() {
		Log2.e(__CLASSNAME__, getMethodName());
		if (isCanGoBack && mWebView.canGoBack()) {
			mWebView.goBack();
			return false;
		} else {
			return super.onBackPressed();
		}
	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, mURL);

		KP_init();

		// Bundle extras = getExtras();
		// if (extras != null) {
		// mMethod = extras.getString("method");
		// //최초조회시
		// String URL = extras.getString("url");
		// URL += "&getApp().p_mid="+getApp().p_mid;
		// extras.putString("url", URL);
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, mURL);
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, URL);
		// }
		getWebURL();

		super.refresh();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		refresh();
	}

}
