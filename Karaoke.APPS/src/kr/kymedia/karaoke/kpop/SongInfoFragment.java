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
 * filename	:	SongInfoFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ SongInfoFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.apps.impl.IBaseFragment;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 9.
 * @version 1.0
 */
@Deprecated
public class SongInfoFragment extends _BaseFragment implements IBaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	String msg_kr = "";
	String msg_us = "";
	String msg_jp = "";
	String msg_cn = "";

	final static int BTN_SONG_INFO[] = { R.id.btn_song_info_kr, R.id.btn_song_info_us,
			R.id.btn_song_info_jp, R.id.btn_song_info_cn, };

	public SongInfoFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_song_info, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		KPItem info = getInfo();
		KPItem list = getList();

		p_type = "";
		play_id = "";

		if (info != null) {
			p_type = info.getValue("type");
			play_id = list.getValue("song_id");
		}

		if (list != null) {
			play_id = list.getValue("song_id");
		}

		KP_nnnn.KP_1030(getApp().p_mid, p_m1, p_m2, p_type, play_id);

	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);

		KPItem info = KP_nnnn.getInfo();

		View v = null;
		String value = "";

		// "title":"하루하루",
		// "artist":"빅뱅(Bigbang)",
		value = info.getValue("title") + "-" + info.getValue("artist");
		v = findViewById(R.id.txt_song_title);
		if (value != null) {
			putValue(v, value);
		}
		WidgetUtils.setTextViewMarquee((TextView) v);

		// "msg_kr":"N",
		msg_kr = info.getValue("msg_kr");
		v = findViewById(R.id.btn_song_info_kr);
		if (msg_kr == null || TextUtil.isEmpty(msg_kr) || ("N").equalsIgnoreCase(msg_kr)) {
			v.setVisibility(View.GONE);
		}
		// "msg_us":"N",
		msg_us = info.getValue("msg_us");
		v = findViewById(R.id.btn_song_info_us);
		if (msg_us == null || TextUtil.isEmpty(msg_us) || ("N").equalsIgnoreCase(msg_us)) {
			v.setVisibility(View.GONE);
		}
		// "msg_jp":"N",
		msg_jp = info.getValue("msg_jp");
		v = findViewById(R.id.btn_song_info_jp);
		if (msg_jp == null || TextUtil.isEmpty(msg_jp) || ("N").equalsIgnoreCase(msg_jp)) {
			v.setVisibility(View.GONE);
		}
		// "msg_cn":""
		msg_cn = info.getValue("msg_cn");
		v = findViewById(R.id.btn_song_info_cn);
		if (msg_cn == null || TextUtil.isEmpty(msg_cn) || ("N").equalsIgnoreCase(msg_cn)) {
			v.setVisibility(View.GONE);
		}

		String msg = msg_kr;

		int res = R.id.btn_song_info_kr;

		v = findViewById(R.id.txt_song_content);
		value = info.getValue("msg_" + getApp().mLocale.getCountry().toUpperCase());
		if (!TextUtil.isEmpty(value) && !("N").equalsIgnoreCase(value)) {
			res = getResource("btn_song_info_" + getApp().mLocale.getCountry().toLowerCase(), "id");
			msg = value;
		}

		if (msg != null && !TextUtil.isEmpty(msg) && !msg.equalsIgnoreCase("N")) {
			((TextView) v).setText(msg);
		}

		setSongInfoButton(res);

		if (p_type.equalsIgnoreCase("GASA")) {
			((TextView) v).setGravity(Gravity.CENTER);
			((TextView) v).setTextAppearance(mContext, R.style.TextXLargeWhite);
		} else {
			((TextView) v).setGravity(Gravity.LEFT | Gravity.TOP);
			((TextView) v).setTextAppearance(mContext, R.style.TextXLargeWhite);
		}

		super.KPnnnn();
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		TextView content = (TextView) findViewById(R.id.txt_song_content);

		if (v.getId() == R.id.btn_song_info_kr) {
			setSongInfoButton(v.getId());
			content.setText(msg_kr);
		} else if (v.getId() == R.id.btn_song_info_us) {
			setSongInfoButton(v.getId());
			content.setText(msg_us);
		} else if (v.getId() == R.id.btn_song_info_jp) {
			setSongInfoButton(v.getId());
			content.setText(msg_jp);
		} else if (v.getId() == R.id.btn_song_info_cn) {
			setSongInfoButton(v.getId());
			content.setText(msg_cn);
		} else {
		}
		super.onClick(v);
	}

	public void setSongInfoButton(int id) {
		for (int i = 0; i < BTN_SONG_INFO.length; i++) {
			if (id == BTN_SONG_INFO[i]) {
				findViewById(BTN_SONG_INFO[i]).setBackgroundResource(R.drawable.btn_normal_counter_01);
			} else {
				findViewById(BTN_SONG_INFO[i]).setBackgroundResource(R.drawable.btn_normal_01);
			}
		}
	}
}
