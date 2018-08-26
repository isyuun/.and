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
 * filename	:	NoticeFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ NoticeFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._WebViewFragment;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 16.
 * @version 1.0
 */
class NoticeFragment extends _WebViewFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public static Fragment newInstance(Bundle extras) {

		return null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KP_nnnn() {
		// super.KP_nnnn();


		KPItem info = getInfo();
		KPItem list = getList();

		String id = "";
		if (info != null) {
			id = info.getValue("id") == null ? id : info.getValue("id");
		}

		if (list != null) {
			id = list.getValue("id") == null ? id : list.getValue("id");
		}

		KP_nnnn.KP_0011(getApp().p_mid, p_m1, p_m2, id);

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);


		KPItem info = KP_nnnn.getInfo();

		if (info != null) {
			String notice_title = info.getValue("title") == null ? "" : info.getValue("title");

			String notice_date = getString(R.string.title_register_date) + ":"
					+ (info.getValue("reg_date") == null ? "" : info.getValue("reg_date"));

			String contents = info.getValue("contents") == null ? "" : info.getValue("contents");

			String url_contents = info.getValue("url_contents");

			String notice_url = info.getValue("notice_url") == null ? "" : info.getValue("notice_url");

			String notice_image = info.getValue("notice_image") == null ? "" : info
					.getValue("notice_image");

			String notice_html = info.getValue("notice_html") == null ? "" : info
					.getValue("notice_html");

			View v = null;

			if (URLUtil.isNetworkUrl(notice_url)) {
				mWebView.setVisibility(View.VISIBLE);
				mURL = notice_url;
				mMethod = "POST";
				openWebView();
			} else {
				mWebView.setVisibility(View.GONE);
			}

			if (URLUtil.isNetworkUrl(notice_image)) {
				url_contents = notice_image;
			}

			if (URLUtil.isNetworkUrl(url_contents)) {
				// mWebView.setVisibility(View.GONE);
				// putURLImage(mContext, (ImageView) findViewById(R.id.img_notice_content), url_contents, true, R.drawable.bg_trans);
				mWebView.setVisibility(View.VISIBLE);
				mURL = url_contents;
				mMethod = "IMAGE";
				openWebView();
			} else {
				mWebView.setVisibility(View.GONE);
			}

			v = findViewById(R.id.txt_notice_content);
			if (v != null) {
				putValue(v, contents);
			}

			if (!TextUtil.isEmpty(notice_html)) {
				mWebView.setVisibility(View.VISIBLE);
				mWebView.loadData(notice_html, "text/html", null);
			}

			v = findViewById(R.id.txt_notice_title);
			putValue(v, notice_title);
			WidgetUtils.setTextViewMarquee((TextView) v);

			v = findViewById(R.id.txt_notice_date);
			putValue(v, notice_date);
		}

		super.KPnnnn();
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		// if (v.getId() == R.id.btn_notice_list) {
		// onOptionsItemSelected(R.id.menu_notice, true);
		// } else {
		// }
	}

	@Override
	public void openWebView() {

		super.openWebView();

		mWebView.getSettings().setBuiltInZoomControls(false);
		mWebView.getSettings().setSupportZoom(false);
		mWebView.getSettings().setLoadWithOverviewMode(false);
		mWebView.getSettings().setUseWideViewPort(false);
	}

}
