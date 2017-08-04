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
 * filename	:	BaseFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ BaseFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.apps.impl.IBaseFragment;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 28.
 * @version 1.0
 */

class MyHolicFragment2 extends _BaseFragment implements IBaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	String uid;
	/**
	 * KP_6010 팔로잉/팔로워 등록/수정/삭제
	 */
	KPnnnn KP_6010;
	/**
	 * KP_6000 FEEL목록요청
	 */
	KPnnnn KP_6000;
	/**
	 * KP_3002 GO(MY/BEST) HOLIC 회원의 녹음곡/팔로워 목록 정보
	 */
	KPnnnn KP_30021;
	/**
	 * KP_3002 GO(MY/BEST) HOLIC 회원의 녹음곡/팔로워 목록 정보
	 */
	KPnnnn KP_30022;
	/**
	 * KP_3002 GO(MY/BEST) HOLIC 회원의 녹음곡/팔로워 목록 정보
	 */
	KPnnnn KP_30023;

	MyHolicFragment2() {
		super();
	}

	// private ImageView mProfileImgView = null;
	//
	// public ImageView getProfileImgView() {
	// return mProfileImgView;
	// }
	//
	// public String mProfileImgPath = "";
	// public Uri mProfileImgUri = null;
	//
	// private TextView mProfileComment = null;
	//
	// public TextView getProfileComment() {
	// return mProfileComment;
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_holic2, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		KPItem list = null;
		String song_id = "";

		list = getList();
		if (list != null) {
			song_id = list.getValue("song_id");
		}

		try {
			View v = findViewById(R.id.menu_sing_a_song);
			if (song_id == null || TextUtil.isEmpty(song_id)) {
				v.setVisibility(View.GONE);
			}
		} catch (Exception e) {

			// e.printStackTrace();
		}
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6010 = KP_init(mContext, KP_6010);

		KP_6000 = KP_init(mContext, KP_6000);

		KP_30021 = KP_init(mContext, KP_30021);

		KP_30022 = KP_init(mContext, KP_30022);

		KP_30023 = KP_init(mContext, KP_30023);

	}

	@Override
	public void KP_nnnn() {
		super.KP_nnnn();


		play_id = getApp().p_mid;

		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (!TextUtil.isEmpty(list.getValue("id"))) {
			play_id = list.getValue("id");
		}

		p_type = list.getValue("type");
		if (("REORD").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("record_id"))) {
				play_id = list.getValue("record_id");
			}
		} else if (("UID").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("uid"))) {
				play_id = list.getValue("uid");
			}
		} else if (("SONG").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("song_id"))) {
				play_id = list.getValue("song_id");
			}
		} else if (("ARTIST").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("artist_id"))) {
				play_id = list.getValue("artist_id");
			}
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "type - " + p_type);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "play_id - " + play_id);

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (p_type != null && ("SONG").equalsIgnoreCase(p_type)) {
			KP_nnnn.KP_1051(getApp().p_mid, p_m1, p_m2, play_id);
		} else {
			KP_nnnn.KP_3001(getApp().p_mid, p_m1, p_m2, play_id);
		}

		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + " - mid:" + getApp().p_mid + ", play_id" + play_id);
			View v = findViewById(R.id.context_message);
			if (v != null) {
				if (getApp().p_mid.equalsIgnoreCase(play_id)) {
					v.setVisibility(View.VISIBLE);
				} else {
					v.setVisibility(View.GONE);
				}
			}

			if (getBaseActivity() != null) {
				if (getApp().p_mid.equalsIgnoreCase(play_id)) {
					getBaseActivity().setTitle(R.string.menu_holichome);
				} else {
					getBaseActivity().setTitle(R.string.menu_myholic);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code
				+ ", " + r_message);


		if (("KP_1051").equalsIgnoreCase(p_opcode) || ("KP_3001").equalsIgnoreCase(p_opcode)) {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
			// 검색결과가 존재하지 않는경우 종료
			if (!r_code.equals("00000")) {
				close();
				return;
			}
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					// /KP_6000();
					KP_3002();
				}
			}
		} else if (("KP_6000").equalsIgnoreCase(p_opcode)) {
			// KP_6000 내필목록
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KP6000();
			}
		} else if (("KP_3002").equalsIgnoreCase(p_opcode)) {
			// KP_3002 GO(MY/BEST) HOLIC 회원의 녹음곡/팔로워 목록 정보
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KP3002();
			}
		} else if (("KP_6010").equalsIgnoreCase(p_opcode)) {
			// KP_6010 팔로잉/팔로워 등록/수정/삭제
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					// 팔로잉정보 리플레쉬 불필요.
					// setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP_nnnn();
					KP6010();
				}
			}
		}

	}

	int MY_FEEL_IMAGE[] = { R.id.MY_FEEL_IMAGE_01, R.id.MY_FEEL_IMAGE_02, R.id.MY_FEEL_IMAGE_03,
			R.id.MY_FEEL_IMAGE_04, R.id.MY_FEEL_IMAGE_05, R.id.MY_FEEL_IMAGE_06, R.id.MY_FEEL_IMAGE_07,
			R.id.MY_FEEL_IMAGE_08, R.id.MY_FEEL_IMAGE_09, R.id.MY_FEEL_IMAGE_10,

	};

	int MY_FEEL_TEXT[] = { R.id.MY_FEEL_TEXT_01, R.id.MY_FEEL_TEXT_02, R.id.MY_FEEL_TEXT_03,
			R.id.MY_FEEL_TEXT_04, R.id.MY_FEEL_TEXT_05, R.id.MY_FEEL_TEXT_06, R.id.MY_FEEL_TEXT_07,
			R.id.MY_FEEL_TEXT_08, R.id.MY_FEEL_TEXT_09, R.id.MY_FEEL_TEXT_10,

	};

	/**
	 * // KP_6000 FEEL목록요청
	 */
	public void KP_6000() {
		KP_6000.KP_6000(getApp().p_mid, p_m1, p_m2, uid, "OT", 1);
	}

	/**
	 * // KP_6000 FEEL목록요청
	 */
	public void KP6000() {
		ArrayList<KPItem> lists = KP_6000.getLists();

		String value = "";
		View v = null;

		KPItem list = null;
		for (int i = 0; i < MY_FEEL_IMAGE.length; i++) {

			if (i < lists.size()) {
				list = lists.get(i);
			} else {
				v = findViewById(MY_FEEL_IMAGE[i]);
				v.setVisibility(View.INVISIBLE);
				v = findViewById(MY_FEEL_TEXT[i]);
				v.setVisibility(View.INVISIBLE);
				WidgetUtils.setTextViewMarquee((TextView) v);
				continue;
			}

			value = list.getValue("url_comment");
			v = findViewById(MY_FEEL_IMAGE[i]);
			if (v != null) {
				if (URLUtil.isNetworkUrl(value)) {
					putURLImage(getBaseActivity(), (_ImageView) v, value, false, R.drawable.ic_menu_01);
				} else {
					((_ImageView) v).setImageResource(R.drawable.ic_menu_01);
				}
				v.setVisibility(View.VISIBLE);
			}

			value = list.getValue("comment");
			v = findViewById(MY_FEEL_TEXT[i]);
			if (v != null) {
				putValue(v, value);
			}
		}
	}

	/**
	 * KP_3002 GO(MY/BEST) HOLIC 회원의 녹음곡/팔로워 목록 정보
	 */
	public void KP_3002() {
		KP_30021.KP_3002(getApp().p_mid, p_m1, p_m2, uid, "n");
		KP_30022.KP_3002(getApp().p_mid, p_m1, p_m2, uid, "p");
		KP_30023.KP_3002(getApp().p_mid, p_m1, p_m2, uid, "f");
	}

	/**
	 * 타이틀/마크(아이콘)처리
	 */
	public void KP3002(String head, int index, KPItem list) {
		View v = null;
		String name;
		name = String.format(head + "title_%d", index + 1);
		v = findViewById(getResource(name, "id"));
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + name);
		if (v != null && list != null) {
			String title = list.getValue("title");
			String artist = list.getValue("artist");
			String value = title + " - " + artist;
			putValue(v, value);
		} else {
			putValue(v, null);
		}

		name = String.format(head + "mark_%d", index + 1);
		v = findViewById(getResource(name, "id"));
		if (v != null) {
			View mark = null;
			mark = ((ViewGroup) v).findViewById(R.id.best);
			if (mark != null && list != null) {
				String value = list.getValue("mark_best");
				if (("Y").equalsIgnoreCase(value)) {
					mark.setVisibility(View.VISIBLE);
				} else {
					mark.setVisibility(View.GONE);
				}
			} else {
				mark.setVisibility(View.GONE);
			}
			mark = ((ViewGroup) v).findViewById(R.id.hot);
			if (mark != null && list != null) {
				String value = list.getValue("mark_hot");
				if (("Y").equalsIgnoreCase(value)) {
					mark.setVisibility(View.VISIBLE);
				} else {
					mark.setVisibility(View.GONE);
				}
			} else {
				mark.setVisibility(View.GONE);
			}
			mark = ((ViewGroup) v).findViewById(R.id.audition);
			if (mark != null && list != null) {
				String value = list.getValue("mark_audition");
				if (("Y").equalsIgnoreCase(value)) {
					mark.setVisibility(View.VISIBLE);
				} else {
					mark.setVisibility(View.GONE);
				}
			} else {
				mark.setVisibility(View.GONE);
			}
			mark = ((ViewGroup) v).findViewById(R.id.first);
			if (mark != null && list != null) {
				String value = list.getValue("mark_first");
				if (("Y").equalsIgnoreCase(value)) {
					mark.setVisibility(View.VISIBLE);
				} else {
					mark.setVisibility(View.GONE);
				}
			} else {
				mark.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * KP_3002 GO(MY/BEST) HOLIC 회원의 녹음곡/팔로워 목록 정보
	 */
	public void KP3002() {

		ArrayList<KPItem> lists = null;

		lists = KP_30021.getLists();
		if (lists != null && lists.size() > 0) {
			if (findViewById(R.id.new_song_title_0) != null) {
				findViewById(R.id.new_song_title_0).setVisibility(View.GONE);
			}
			for (int i = 0; i < 2; i++) {
				KPItem list = null;
				if (i < lists.size()) {
					list = lists.get(i);
				}
				KP3002("new_song_", i, list);
			}
		} else {
			if (findViewById(R.id.new_song_title_0) != null) {
				findViewById(R.id.new_song_title_0).setVisibility(View.VISIBLE);
			}
		}

		lists = KP_30022.getLists();
		if (lists != null && lists.size() > 0) {
			if (findViewById(R.id.best_song_title_0) != null) {
				findViewById(R.id.best_song_title_0).setVisibility(View.GONE);
			}
			for (int i = 0; i < 2; i++) {
				KPItem list = null;
				if (i < lists.size()) {
					list = lists.get(i);
				}
				KP3002("best_song_", i, list);
			}
		} else {
			if (findViewById(R.id.best_song_title_0) != null) {
				findViewById(R.id.best_song_title_0).setVisibility(View.VISIBLE);
			}
		}

		lists = KP_30023.getLists();
		if (lists != null && lists.size() > 0) {
			if (findViewById(R.id.new_follower_title_0) != null) {
				findViewById(R.id.new_follower_title_0).setVisibility(View.GONE);
			}
			if (findViewById(R.id.include_player_people) != null) {
				findViewById(R.id.include_player_people).setVisibility(View.VISIBLE);
			}
			for (int i = 0; i < 5; i++) {
				KPItem list = null;
				if (i < lists.size()) {
					list = lists.get(i);
				}
				String name = String.format("btn_player_people_%02d", i + 1);
				View v = findViewById(getResource(name, "id"));
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + name);
				if (v != null) {
					if (list != null) {
						// String title = list.getString("title");
						// String artist = list.getString("artist");
						String value = list.getValue("url_profile");
						putURLImage(mContext, (ImageView) v, value, false, R.drawable.ic_menu_01);
					} else {
						v.setVisibility(View.INVISIBLE);
					}
				}
			}
		} else {
			if (findViewById(R.id.new_follower_title_0) != null) {
				findViewById(R.id.new_follower_title_0).setVisibility(View.VISIBLE);
			}
			if (findViewById(R.id.include_player_people) != null) {
				findViewById(R.id.include_player_people).setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);


		KPItem list = KP_nnnn.getList(0);

		ViewGroup include_profile_my = (ViewGroup) findViewById(R.id.include_profile_my);
		ViewGroup include_profile_people = (ViewGroup) findViewById(R.id.include_profile_people);
		ViewGroup include_profile_time = (ViewGroup) findViewById(R.id.include_profile_time);

		int res = 0;
		View v = null;
		String value = "";

		try {
			// "my_nickname"
			value = list.getValue("my_nickname");
			v = include_profile_my.findViewById(R.id.txt_profile_nickname);
			if (value != null) {
				putValue(v, value);
			}

			// "my_level"
			int my_level = !TextUtil.isEmpty(list.getValue("my_level")) ? TextUtil.parseInt(list
					.getValue("my_level")) : 0;
			int my_color = getResources().getColor(R.color.solid_black);
			if (my_level == 4) {
				my_color = getResources().getColor(R.color.solid_orange);
				((TextView) v).setTextColor(my_color);
			} else if (my_level == 5) {
				my_color = getResources().getColor(R.color.solid_pink);
				((TextView) v).setTextColor(my_color);
			} else {
				my_color = getResources().getColor(R.color.solid_black);
				((TextView) v).setTextColor(my_color);
			}

			// "ncode":"KR",
			value = list.getValue("ncode");
			v = include_profile_my.findViewById(R.id.img_profile_country);
			if (value != null && v != null) {
				try {
					res = getResource("img_flag_" + value.toLowerCase(), "drawable");
					((_ImageView) v).setImageResource(res);
				} catch (Exception e) {

					// e.printStackTrace();
				}
			} else {
				v.setVisibility(View.GONE);
			}

			// "my_hit":0,
			value = list.getValue("my_hit");
			v = include_profile_my.findViewById(R.id.txt_profile_hit);
			if (value != null) {
				value = TextUtil.numberFormat(value);
				putValue(v, value);
			}

			// "my_heart":0,
			value = list.getValue("my_heart");
			v = include_profile_my.findViewById(R.id.txt_profile_heart);
			if (value != null) {
				value = TextUtil.numberFormat(value);
				putValue(v, value);
			}

			// LOGIN.KEY_NICKNAME:"prettyMan1",
			value = list.getValue(LOGIN.KEY_NICKNAME);
			v = include_profile_people.findViewById(R.id.txt_profile_nickname);
			if (value != null) {
				putValue(v, value);
				WidgetUtils.setTextViewMarquee((TextView) v);
			}

			// "my_level"
			// int level = !TextUtil.isEmpty(list.getString("level")) ? TextUtil.parseInt(list.getString("level")) : 0;
			// int color = getResources().getColor(R.color.text_thick);
			// if (level == 4) {
			// color = getResources().getColor(R.color.solid_orange);
			// ((TextView) v).setTextColor(color);
			// } else if (level == 5) {
			// color = getResources().getColor(R.color.solid_pink);
			// ((TextView) v).setTextColor(color);
			// } else {
			// color = getResources().getColor(R.color.text_thick);
			// ((TextView) v).setTextColor(color);
			// }

			// "ncode":"JP",
			value = list.getValue("ncode");
			v = include_profile_people.findViewById(R.id.img_profile_country);
			try {
				if (value != null) {
					res = getResource("img_flag_" + value.toLowerCase(), "drawable");
					((_ImageView) v).setImageResource(res);
				} else {
					v.setVisibility(View.GONE);
				}
			} catch (Exception e) {

				// e.printStackTrace();
			}

			value = getApp().mLocale.getDisplayCountry();
			v = include_profile_people.findViewById(R.id.txt_profile_country);
			if (v != null) {
				if (value != null) {
					putValue(v, value);
				}
				v.setVisibility(View.GONE);
			}

			// "hit":6,
			value = list.getValue("hit");
			v = include_profile_people.findViewById(R.id.txt_profile_hit);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				}
			}

			// "heart":0,
			value = list.getValue("heart");
			v = include_profile_people.findViewById(R.id.txt_profile_heart);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				}
			}

			// "follower_cnt":0,
			value = list.getValue("follower_cnt");
			v = include_profile_people.findViewById(R.id.txt_profile_follow);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				}
			}

			// "url_profile":"",
			value = list.getValue("url_profile");
			v = include_profile_people.findViewById(R.id.img_profile_image);
			if (v != null) {
				if (URLUtil.isNetworkUrl(value)) {
					putURLImage(mContext, (ImageView) v, value, false, R.drawable.ic_menu_01);
				}
			}

			// LOGIN.KEY_BIRTHDAY:"20120301",
			value = list.getValue(LOGIN.KEY_BIRTHDAY);
			v = include_profile_people.findViewById(R.id.txt_profile_birthday);
			if (v != null) {
				if (value != null) {
					putValue(v, value);
				}
				v.setVisibility(View.GONE);
			}

			// LOGIN.KEY_SEX:"M",
			value = list.getValue(LOGIN.KEY_SEX);
			v = include_profile_people.findViewById(R.id.txt_profile_birthday);
			if (v != null) {
				try {
					if (value != null) {
						Drawable d = null;
						if (value == "F") {
							d = getBaseActivity().getResources().getDrawable(R.drawable.img_top_people_woman);
						} else {
							d = getBaseActivity().getResources().getDrawable(R.drawable.img_top_people_man);
						}
						if (d != null) {
							d.setBounds(new Rect(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()));
							((TextView) v).setCompoundDrawables(d, null, null, null);
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			// "comment":""
			value = list.getValue("comment");
			v = include_profile_people.findViewById(R.id.txt_profile_comment);
			if (v != null) {
				try {
					if (value != null) {
						putValue(v, value);
						WidgetUtils.setTextViewMarquee((TextView) v);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			// "uid":"3",
			uid = value = list.getValue("uid");
			try {
				v = include_profile_people.findViewById(R.id.btn_star);
				if (value != null) {
					if (getApp().p_mid.equalsIgnoreCase(value)) {
						if (v != null) {
							v.setVisibility(View.INVISIBLE);
						}
					} else {
						if (v != null) {
							v.setVisibility(View.VISIBLE);
						}
					}
				} else {
					if (v != null) {
						v.setVisibility(View.INVISIBLE);
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			// "is_follow":"Y/N",
			value = list.getValue("is_follow");
			try {
				v = include_profile_people.findViewById(R.id.chk_star);
				if (("Y").equalsIgnoreCase(value)) {
					((CheckBox) v).setChecked(true);
				} else {
					((CheckBox) v).setChecked(false);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			// "holic_time": "10mins 18secs",
			value = list.getValue("play_time");
			v = include_profile_time.findViewById(R.id.txt_profile_time);
			if (v != null) {
				if (value != null) {
					putValue(v, value);
				}
			}

			// "feel_cnt":0,
			value = list.getValue("feel_cnt");
			v = findViewById(R.id.include_profile_activity).findViewById(R.id.btn_feel);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				} else {
					putValue(v, "0");
				}
			}

			// "record_cnt":0,
			value = list.getValue("record_cnt");
			v = findViewById(R.id.include_profile_activity).findViewById(R.id.btn_record);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				} else {
					putValue(v, "0");
				}
			}

			// "follower_cnt":0,
			value = list.getValue("follower_cnt");
			v = findViewById(R.id.include_profile_activity).findViewById(R.id.btn_follower);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				} else {
					putValue(v, "0");
				}
			}

			// "following_cnt":0,
			value = list.getValue("following_cnt");
			v = findViewById(R.id.include_profile_activity).findViewById(R.id.btn_following);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				} else {
					putValue(v, "0");
				}
			}

			// "visit_cnt":0,
			value = list.getValue("visit_cnt");
			v = findViewById(R.id.include_profile_activity).findViewById(R.id.btn_guestbook);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				} else {
					putValue(v, "0");
				}
			}

			// "visit_cnt":0,
			value = list.getValue("message_cnt");
			v = findViewById(R.id.include_profile_activity).findViewById(R.id.btn_message);
			if (v != null) {
				if (value != null) {
					value = TextUtil.numberFormat(value);
					putValue(v, value);
				} else {
					putValue(v, "0");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		super.KPnnnn();
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);


		KPItem list = KP_nnnn.getList(0);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (vn.indexOf("context_") > -1) {
			onContextItemSelected(v.getId(), KP_nnnn, KP_index, true);
			super.onClick(v);
			return;
		}

		if (v.getId() == R.id.txt_profile_comment) {
			String comment = list.getValue("comment");
			getApp().popupToast(comment, Toast.LENGTH_LONG);
		} else if (v.getId() == R.id.img_profile_image) {
			if (getApp().p_mid.equalsIgnoreCase(list.getValue("uid"))) {
				//showProfileEdit(true);
				onOptionsItemSelected(R.id.menu_profile_edit, getString(R.string.menu_holichome), true);
			} else {
				String url = list.getValue("url_org_profile");
				if (!TextUtil.isNetworkUrl(url)) {
					url = list.getValue("url_profile");
				}
				openWebView(webview.class, getString(R.string.M1_MENU_MYHOLIC),
						getString(R.string.M2_MENU_MYHOLIC), getString(R.string.menu_myholic), url, "IMAGE",
						false);
			}
		} else if (v.getId() == R.id.btn_profile_edit) {
			//showProfileEdit(true);
			onOptionsItemSelected(R.id.menu_profile_edit, getString(R.string.menu_holichome), true);
		} else if (v.getId() == R.id.btn_feel) {
			onContextItemSelected(R.id.context_feel_mylist, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_record) {
			onContextItemSelected(R.id.context_record, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_follower) {
			onContextItemSelected(R.id.context_follower, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_following) {
			onContextItemSelected(R.id.context_following, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_guestbook) {
			onContextItemSelected(R.id.context_guestbook, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_message) {
			onContextItemSelected(R.id.context_message, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.menu_mysing) {
			onContextItemSelected(R.id.context_mysing, KP_nnnn, 0, true);
		} else if (v.getId() == R.id.context_feel || v.getId() == R.id.txt_feel) {
			if (findViewById(R.id.btn_feel) != null) {
				findViewById(R.id.btn_feel).performClick();
			}
		} else if (v.getId() == R.id.context_record || v.getId() == R.id.txt_record) {
			if (findViewById(R.id.btn_record) != null) {
				findViewById(R.id.btn_record).performClick();
			}
		} else if (v.getId() == R.id.context_follower || v.getId() == R.id.txt_follower) {
			if (findViewById(R.id.btn_follower) != null) {
				findViewById(R.id.btn_follower).performClick();
			}
		} else if (v.getId() == R.id.context_following || v.getId() == R.id.txt_following) {
			if (findViewById(R.id.btn_following) != null) {
				findViewById(R.id.btn_following).performClick();
			}
		} else if (v.getId() == R.id.context_guestbook || v.getId() == R.id.txt_guestbook) {
			if (findViewById(R.id.btn_guestbook) != null) {
				findViewById(R.id.btn_guestbook).performClick();
			}
		} else if (v.getId() == R.id.context_message || v.getId() == R.id.txt_message) {
			if (findViewById(R.id.btn_message) != null) {
				findViewById(R.id.btn_message).performClick();
			}
		} else if (v.getId() == R.id.btn_star || v.getId() == R.id.chk_star) {
			KP_6010();
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_01 || v.getId() == R.id.MY_FEEL_TEXT_01) {
			openFeel(0);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_02 || v.getId() == R.id.MY_FEEL_TEXT_02) {
			openFeel(1);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_03 || v.getId() == R.id.MY_FEEL_TEXT_03) {
			openFeel(2);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_04 || v.getId() == R.id.MY_FEEL_TEXT_04) {
			openFeel(3);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_05 || v.getId() == R.id.MY_FEEL_TEXT_05) {
			openFeel(4);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_06 || v.getId() == R.id.MY_FEEL_TEXT_06) {
			openFeel(5);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_07 || v.getId() == R.id.MY_FEEL_TEXT_07) {
			openFeel(6);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_08 || v.getId() == R.id.MY_FEEL_TEXT_08) {
			openFeel(7);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_09 || v.getId() == R.id.MY_FEEL_TEXT_09) {
			openFeel(8);
		} else if (v.getId() == R.id.MY_FEEL_IMAGE_10 || v.getId() == R.id.MY_FEEL_TEXT_10) {
			openFeel(9);
		} else if (v.getId() == R.id.menu_audition) {
			onContextItemSelected(R.id.context_tab_audition, KP_nnnn, 0, true);
		} else if (v.getId() == R.id.menu_message_send) {
			String url_contents = list.getValue("url_contents");
			openWebView(webview.class, getString(R.string.M1_MENU_MESSAGE),
					getString(R.string.M2_MESSAGE_SEND), getString(R.string.btn_title_message_send),
					url_contents, "POST", false);
		} else if (v.getId() == R.id.new_song_title_1) {
			onContextItemSelected(R.id.context_play_listen, KP_30021, 0, true);
		} else if (v.getId() == R.id.new_song_title_2) {
			onContextItemSelected(R.id.context_play_listen, KP_30021, 1, true);
		} else if (v.getId() == R.id.best_song_title_1) {
			onContextItemSelected(R.id.context_play_listen, KP_30022, 0, true);
		} else if (v.getId() == R.id.best_song_title_2) {
			onContextItemSelected(R.id.context_play_listen, KP_30022, 1, true);
		} else if (v.getId() == R.id.btn_player_people_01) {
			onContextItemSelected(R.id.context_go_holic, KP_30023, 0, true);
		} else if (v.getId() == R.id.btn_player_people_02) {
			onContextItemSelected(R.id.context_go_holic, KP_30023, 1, true);
		} else if (v.getId() == R.id.btn_player_people_03) {
			onContextItemSelected(R.id.context_go_holic, KP_30023, 2, true);
		} else if (v.getId() == R.id.btn_player_people_04) {
			onContextItemSelected(R.id.context_go_holic, KP_30023, 3, true);
		} else if (v.getId() == R.id.btn_player_people_05) {
			onContextItemSelected(R.id.context_go_holic, KP_30023, 4, true);
		} else {
		}

		super.onClick(v);
	}

	void openFeel(int index) {

		KPItem list = KP_6000.getList(index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		String feel_id = list.getValue("feel_id");
		if (TextUtil.isEmpty(feel_id)) {
			return;
		}

		onContextItemSelected(R.id.context_play_feel, KP_6000, index, true);

	}

	/**
	 * // KP_6010 팔로잉/팔로워 등록/수정/삭제
	 */
	public void KP_6010() {
		KP_6010.KP_6010(getApp().p_mid, p_m1, p_m2, uid, "");

	}

	/**
	 * // KP_6010 팔로잉/팔로워 등록/수정/삭제
	 */
	public void KP6010() {
		KPItem list = KP_6010.getList(0);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		View v = null;

		String is_follow = list.getValue("is_follow");
		try {
			v = findViewById(R.id.chk_star);
			if (v != null) {
				if (("Y").equalsIgnoreCase(is_follow)) {
					((CheckBox) v).setChecked(true);
				} else {
					((CheckBox) v).setChecked(false);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void onItemClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + cn);
	}
}
