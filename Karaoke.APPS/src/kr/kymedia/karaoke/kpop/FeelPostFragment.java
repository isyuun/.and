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
 * filename	:	FeelPostFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FeelPostFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.app.html.HtmlBubble;
import kr.kymedia.karaoke.apps.BaseImageFragment;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 7. 5.
 * @version 1.0
 */
public class FeelPostFragment extends BaseImageFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	CheckBox isOrder;
	/**
	 * type PHOTO:프로필 사진 POST:게시물 RECORD:녹음곡
	 * 
	 */
	String type = "POST";
	/**
	 * feel_id 녹음곡 또는 게시물 id
	 */
	String feel_id = "";
	/**
	 * mode ADD:등록 UPDATE:수정 DEL:삭제
	 */
	String mode = "ADD";
	/**
	 * <pre>
	 * FEEL 등록인 경우 FEEL등록 
	 * 화면 설정 값
	 * K : 반주곡화면에서 등록
	 * R : 녹음곡화면에서 등록
	 * T : 일반화면에서 등록
	 * 화면을 제외한 화면에서 등록
	 * A : 오디션 화면에서 등록
	 * </pre>
	 */
	String feel_type = "T";
	/**
	 * csid 반주곡/녹음곡 ID
	 * 
	 */
	String csid = "";

	String seq = "";
	String siren_code = "";

	String title = "";
	/**
	 * comment 멘트(FEEL / 녹음곡소개글)
	 */
	String comment = "";
	/**
	 * furl URL 정보
	 */
	String furl = "";
	/**
	 * furl HTM 정보
	 */
	String fhtm = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		checkLogin(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_feel_post, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		getBaseActivity().setTitle(getString(R.string.menu_feel));
		getBaseActivity().setActionMenuItemVisible(R.id.menu_comment, false);
	}

	@Override
	protected void start() {

		super.start();

		putFeelPost();
	}

	@Override
	protected void putText(TextView v, CharSequence value) {

		super.putText(v, value);

		// if (!TextUtil.isEmpty(value)) {
		// //v.setText(value);
		// String text = value.toString();
		// if (TextUtil.retrieveLinks(text).size() > 0) {
		// text = TextUtil.retrieveHtmls(value.toString());
		// ((TextView) v).setMovementMethod(LinkMovementMethod.getInstance());
		// ((TextView) v).setText(Html.fromHtml(text));
		// } else {
		// v.setText(text);
		// }
		// v.setVisibility(View.VISIBLE);
		// } else {
		// v.setVisibility(View.GONE);
		// }
	}

	@Override
	protected HtmlBubble parseHtml(String source, String url, String type) {

		HtmlBubble html = super.parseHtml(source, url, type);

		try {
			ViewGroup vg = (ViewGroup) findViewById(R.id.include_feel_html);

			if (vg == null) {
				return null;
			}

			if (TextUtil.isNetworkUrl(url)) {
				vg.setVisibility(View.VISIBLE);
			} else {
				vg.setVisibility(View.GONE);
				return null;
			}

			// if (vg.findViewById(R.id.html_title) != null) {
			// ((TextView) vg.findViewById(R.id.html_title)).setText(html.title);
			// vg.findViewById(R.id.html_title).setVisibility(View.GONE);
			// }
			//
			// if (vg.findViewById(R.id.html_text) != null) {
			// ((TextView) vg.findViewById(R.id.html_text)).setText(html.description);
			// vg.findViewById(R.id.html_text).setVisibility(View.GONE);
			// }

			fhtm = html.body.replace("\"", "'");
			if (vg.findViewById(R.id.html_html) != null) {
				((TextView) vg.findViewById(R.id.html_html)).setMovementMethod(LinkMovementMethod.getInstance());
				((TextView) vg.findViewById(R.id.html_html)).setText(Html.fromHtml(html.body));
				((TextView) vg.findViewById(R.id.html_html)).setSelected(false);
				vg.findViewById(R.id.html_html).setVisibility(View.VISIBLE);
			}

			if (vg.findViewById(R.id.html_image) != null) {
				putURLImage(mContext, (ImageView) vg.findViewById(R.id.html_image), html.image, false, R.drawable.bg_trans);
			}

			if (vg.findViewById(R.id.html_progress) != null) {
				vg.findViewById(R.id.html_progress).setVisibility(View.GONE);
			}

			if (vg.findViewById(R.id.html_del) != null) {
				vg.findViewById(R.id.html_del).setVisibility(View.GONE);
			}

			if (html != null) {
				getList().putValue("url_comment", html.image);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return html;
	}

	public void putFeelPost() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (getBaseActivity() == null) {
			return;
		}

		View v = null;
		String value = "";

		KPItem list = getList();
		// if (list != null) {
		// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, "list - " + list.toString(2));
		// }

		ShareCompat.IntentReader intentReader = ShareCompat.IntentReader.from(getBaseActivity());

		if (list != null && intentReader != null) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intentReader);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intentReader.getSubject());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intentReader.getText());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intentReader.getHtmlText());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intentReader.getStreamCount());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intentReader.getStream());

			String text = "";

			if (!TextUtil.isEmpty(intentReader.getSubject())) {
				text += intentReader.getSubject() + " ";
			}

			if (!TextUtil.isEmpty(intentReader.getText())) {

				if (TextUtil.isEmpty(text)) {
					text += intentReader.getText().toString();
				}

				ArrayList<String> furls = TextUtil.retrieveLinks(intentReader.getText().toString());
				if (furls.size() > 0) {
					furl = furls.get(0);
					list.putValue("furl", furl);
				}
			}

			if (!TextUtil.isEmpty(text)) {
				list.putValue("comment", text);
			}
		}

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

		type = !TextUtil.isEmpty(list.getValue("type")) ? list.getValue("type") : type;
		feel_id = !TextUtil.isEmpty(list.getValue("feel_id")) ? list.getValue("feel_id") : feel_id;
		mode = !TextUtil.isEmpty(list.getValue("mode")) ? list.getValue("mode") : mode;
		feel_type = !TextUtil.isEmpty(list.getValue("feel_type")) ? list.getValue("feel_type") : feel_type;
		csid = "";

		seq = !TextUtil.isEmpty(list.getValue("seq")) ? list.getValue("seq") : seq;
		siren_code = !TextUtil.isEmpty(list.getValue("siren_code")) ? list.getValue("siren_code") : siren_code;

		v = findViewById(R.id.feel_image);
		setImageView((_ImageView) v);

		// if (!("ADD").equalsIgnoreCase(mode))
		{
			value = list.getValue("title");
			v = findViewById(R.id.feel_title);
			if (!TextUtil.isEmpty(value)) {
				putValue(v, value);
			}
			if (v != null) {
				v.setVisibility(View.GONE);
			}

			value = list.getValue("comment");
			v = findViewById(R.id.feel_text);
			if (!TextUtil.isEmpty(value)) {
				putValue(v, value);
			}

			value = list.getValue("furl");
			v = findViewById(R.id.feel_url);
			if (URLUtil.isNetworkUrl(value)) {
				putValue(v, value);
				v.setVisibility(View.GONE);
				v = findViewById(R.id.include_feel_html);
				if (v != null) {
					v.setVisibility(View.VISIBLE);
				}

				// startLoading(__CLASSNAME__, getMethodName());
				(new parseHtml()).execute(value);
			}

			value = list.getValue("url_comment");
			v = findViewById(R.id.feel_image);
			if (URLUtil.isNetworkUrl(value)) {
				findViewById(R.id.feel_image).setVisibility(View.VISIBLE);
				putURLImage(mContext, (ImageView) v, value, true, R.drawable.ic_menu_01);
				setImagePath(value);
			}
		}

		isOrder = (CheckBox) findViewById(R.id.feel_open);

	}

	@Override
	public void onClick(View v) {

		super.onClick(v);

		// EditText et = (EditText) findViewById(R.id.feel_text);
		// et.requestFocus();
		// getApp().hideSoftInput(et);

		if (v.getId() == R.id.btn_image_pick) {
			openImagePicker();
		} else if (v.getId() == R.id.btn_image_camera) {
			openCameraPicker();
		} else if (v.getId() == R.id.btn_image_crop) {
			openImageCroper();
		} else if (v.getId() == R.id.feel_save) {
			KP_6001();
		} else if (v.getId() == R.id.feel_open) {
			if (((CheckBox) v).isChecked()) {
				getApp().popupToast(R.string.warning_open_public, Toast.LENGTH_LONG);
				((CheckBox) v).setText(R.string.btn_title_open_public);
			} else {
				getApp().popupToast(R.string.warning_open_friend, Toast.LENGTH_LONG);
				((CheckBox) v).setText(R.string.btn_title_open_friend);
			}
		} else if (v.getId() == R.id.feel_image) {
			openWebView(webview.class, getString(R.string.M1_MENU_FEEL), getString(R.string.M2_FEEL_INFO),
					getString(R.string.menu_feel), getImagePath(), "IMAGE", false);
		} else if (v.getId() == R.id.feel_text) {
			getApp().showSoftInput((EditText) v);
		} else {
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_IMAGE:
		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_CAMERA:
			return;
		default:
			break;
		}

		try {
			if ((new File(getImageUri().getPath())).exists()) {
				findViewById(R.id.feel_image).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.feel_image).setVisibility(View.GONE);
				String msg = getString(R.string.alert_title_upload_error) + " : " + getImagePath();
				getApp().popupToast(msg, Toast.LENGTH_SHORT);
			}
		} catch (Exception e) {

			e.printStackTrace();
			String msg = getString(R.string.alert_title_upload_error) + " : " + getImagePath();
			getApp().popupToast(msg, Toast.LENGTH_SHORT);
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + requestCode + "," + resultCode + "," + getImagePath());

	}

	/**
	 * KP_6002 FEEL관리(파일업로드) KP_1011 대체
	 */
	protected void KP_6001() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		KPItem list = getList();

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		View v = null;

		if (list != null && !TextUtil.isEmpty(list.getValue("type"))) {
			type = list.getValue("type");
		}
		type = "POST";

		if (list != null && !TextUtil.isEmpty(list.getValue("feel_id"))) {
			feel_id = list.getValue("feel_id");
		}

		if (list != null && !TextUtil.isEmpty(list.getValue("mode"))) {
			mode = list.getValue("mode");
		}

		if (list != null && !TextUtil.isEmpty(list.getValue("feel_type"))) {
			feel_type = list.getValue("feel_type");
		}

		if (("K").equalsIgnoreCase(feel_type)) {
			// 반주곡
			csid = list.getValue("song_id");
		} else if (("R").equalsIgnoreCase(feel_type)) {
			// 녹음곡
			csid = list.getValue("record_id");
		}

		if (list != null && !TextUtil.isEmpty(list.getValue("seq"))) {
			seq = list.getValue("seq");
		}

		if (list != null && !TextUtil.isEmpty(list.getValue("siren_code"))) {
			siren_code = list.getValue("siren_code");
		}

		v = findViewById(R.id.feel_title);
		if (v != null) {
			title = ((EditText) v).getText().toString();
		}

		v = findViewById(R.id.feel_text);
		if (v != null) {
			comment = ((EditText) v).getText().toString();
		}

		if (TextUtil.isEmpty(comment)) {
			getApp().popupToast(R.string.hint_feel_text, Toast.LENGTH_LONG);
			return;
		}

		v = findViewById(R.id.feel_url);
		if (v != null) {
			furl = ((TextView) v).getText().toString();

			try {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + furl);
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isAboutUrl()" + URLUtil.isAboutUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isAssetUrl()" + URLUtil.isAssetUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isContentUrl()" + URLUtil.isContentUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isDataUrl()" + URLUtil.isDataUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isFileUrl()" + URLUtil.isFileUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isHttpsUrl()" + URLUtil.isHttpsUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isHttpUrl()" + URLUtil.isHttpUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isJavaScriptUrl()" + URLUtil.isJavaScriptUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isNetworkUrl()" + URLUtil.isNetworkUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.isValidUrl()" + URLUtil.isValidUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.guessUrl()" + URLUtil.guessUrl(furl));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "URLUtil.stripAnchor()" + URLUtil.stripAnchor(furl));
			} catch (Exception e) {

				e.printStackTrace();
			}

			if (!TextUtil.isEmpty(furl) && !URLUtil.isNetworkUrl(furl)) {
				// if (!furl.contains("://")) {
				// furl = "http://" + furl;
				// }
				if (URLUtil.isNetworkUrl(URLUtil.guessUrl(furl))) {
					furl = URLUtil.guessUrl(furl);
				}
			}
		}

		// startLoading(__CLASSNAME__, getMethodName());

		// if (("REPLY").equalsIgnoreCase(type)) {
		// KP_nnnn.KP_2018(getApp().p_mid, p_m1, p_m2, feel_id, mode, seq, siren_code, comment);
		// } else {
		// KP_nnnn.KP_6001(getApp().p_mid, p_m1, p_m2, type, feel_id, mode, title, comment, feel_type, csid,
		// furl, getImagePath());
		// }
		String path = getImagePath();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + path);
		if (URLUtil.isNetworkUrl(path)) {
			path = "";
		}

		String order = "Y";
		if (!isOrder.isChecked()) {
			order = "N";
		}

		try {
			// comment = TextUtil.retrieveHtmls(comment);
			ArrayList<String> urls = TextUtil.retrieveLinks(comment);
			// 코멘트에서furl생성
			if (!TextUtil.isNetworkUrl(furl) && urls.size() > 0) {
				furl = urls.get(0);
			}
			// URL링크는 코멘트에서 뺀다?
			if (TextUtil.isNetworkUrl(furl)) {
				// comment = comment.replace(furl, "").trim();
				// comment = comment.replace(furl, "<a href='"+furl+"'>"+furl + "</a>");
				for (String url : urls) {
					comment = comment.replace(furl, "<a href='" + url + "'>" + url + "</a>");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		String url_comment = list.getValue("url_comment");
		fhtm = fhtm.replace("\"", "'");

		KP_nnnn.KP_6001(getApp().p_mid, p_m1, p_m2, type, feel_id, mode, title, comment, feel_type, csid, furl,
				fhtm, url_comment, path, order);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		if (what != _IKaraoke.STATE_DATA_QUERY_START && ("00000").equalsIgnoreCase(r_code)) {
			setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			getApp().popupToast(r_message, Toast.LENGTH_LONG);
			close();
		}
	}
}
