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
 * project	:	Karaoke.KPOP.APP
 * filename	:	FeelPostFragment2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FeelPostFragment2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPItemShareData;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.app.html.HtmlBubble;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * <pre>
 * 1.이미지업로드기능제거
 * 2.녹음곡공유기능추가
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 10. 7.
 * @version 1.0
 * @see
 */
public class FeelPostFragment2 extends FeelPostFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		if (findViewById(R.id.btn_image_camera) != null) {
			findViewById(R.id.btn_image_camera).setVisibility(View.GONE);
		}

		if (findViewById(R.id.btn_image_pick) != null) {
			findViewById(R.id.btn_image_pick).setVisibility(View.GONE);
		}

		if (findViewById(R.id.btn_image_crop) != null) {
			findViewById(R.id.btn_image_crop).setVisibility(View.GONE);
		}
	}

	@Override
	protected HtmlBubble parseHtml(String source, String url, String type) {

		HtmlBubble ret = super.parseHtml(source, url, type);

		if (ret != null) {
			View v = findViewById(R.id.feel_text);
			String text = "";
			if (null != v) {
				text = ((TextView)v).getText().toString();
			}
			if (TextUtil.isEmpty(text)) {
				text = ret.title;
			}
			if (!TextUtil.isEmpty(text)) {
				putValue(v, text);
			}
		}

		return ret;
	}

	@Override
	public void putFeelPost() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		KPItem info = getInfo();
		KPItem list = getList();

		KPItemShareData shareData = new KPItemShareData(info, list);

		// String uid = shareData.uid;
		// String song_id = shareData.song_id;
		String record_id = shareData.record_id;
		// String title = shareData.title;
		// String artist = shareData.artist;
		// String name = shareData.name;
		String url = shareData.url;

		if (!TextUtil.isEmpty(record_id)) {

			// String text = "";
			// String comment = "";
			// //comment = "[" + hello + "]";
			//
			// if (!TextUtil.isEmpty(name)) {
			// comment += String.format(getString(R.string.context_format_share_record_name), name);
			// }
			// if (!TextUtil.isEmpty(title)) {
			// text += title;
			// }
			// if (!TextUtil.isEmpty(artist)) {
			// text += " - " + artist;
			// }
			// if (!TextUtil.isEmpty(text)) {
			// comment += " " + String.format(getString(R.string.context_format_share_record_song), text);
			// }
			// list.putString("comment", comment);

			if (TextUtil.isNetworkUrl(url)) {
				list.putValue("furl", url);
			}
		}


		super.putFeelPost();
	}

	@Override
	protected void KP_6001() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

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

		// if (("K").equalsIgnoreCase(feel_type)) {
		// //반주곡
		// csid = list.getString("song_id");
		// } else if (("R").equalsIgnoreCase(feel_type)) {
		// //녹음곡
		// csid = list.getString("record_id");
		// }

		// 반주곡
		// String song_id = list.getString("song_id");
		// if (!TextUtil.isEmpty(song_id)) {
		// feel_type = "K";
		// csid = song_id;
		// }

		// 녹음곡
		String record_id = list.getValue("record_id");
		if (!TextUtil.isEmpty(record_id)) {
			feel_type = "R";
			csid = record_id;
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

			// try {
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + furl);
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isAboutUrl()" + URLUtil.isAboutUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isAssetUrl()" + URLUtil.isAssetUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isContentUrl()" + URLUtil.isContentUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isDataUrl()" + URLUtil.isDataUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isFileUrl()" + URLUtil.isFileUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isHttpsUrl()" + URLUtil.isHttpsUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isHttpUrl()" + URLUtil.isHttpUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isJavaScriptUrl()" + URLUtil.isJavaScriptUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isNetworkUrl()" + URLUtil.isNetworkUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isValidUrl()" + URLUtil.isValidUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.guessUrl()" + URLUtil.guessUrl(furl));
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.stripAnchor()" + URLUtil.stripAnchor(furl));
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }

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

		KP_nnnn.KP_6001(getApp().p_mid, p_m1, p_m2, type, feel_id, mode, title, comment, feel_type, csid, furl, fhtm, url_comment, path, order);

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + feel_type + "[WHAT]" + what + "[OPCODE]" + p_opcode + "[RESULT_CODE]" + r_code);


		// super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (what != _IKaraoke.STATE_DATA_QUERY_START && ("00000").equalsIgnoreCase(r_code)) {
			if (("T").equalsIgnoreCase(feel_type)) {
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			}
			getApp().popupToast(r_message, Toast.LENGTH_LONG);
			close();
		}
	}

}
