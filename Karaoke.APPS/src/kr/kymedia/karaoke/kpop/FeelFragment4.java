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
 * filename	:	FeelFragment3.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FeelFragment3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;

/**
 * <pre>
 * 좋아요사용자이미지표시
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 9. 16.
 * @version 1.0
 */
public class FeelFragment4 extends FeelFragment3 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		if (findViewById(R.id.include_player_people) != null) {
			findViewById(R.id.include_player_people).setVisibility(View.GONE);
		}
	}

	/**
	 * KP_2011 댓글 좌우 스크롤
	 */
	protected KPnnnn KP_6004 = null;

	/**
	 * 댓글 좌우 스크롤
	 * 
	 * @param inc
	 */
	public void KP_6004() {
		// 순차조회필요없음
		KP_6004.clear();
		KP_6004.KP_6004(getApp().p_mid, p_m1, p_m2, "T", play_id, 1);
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6004 = KP_init(mContext, KP_6004);

		KP_6004.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {

			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {

				KP6004(null, 1);

			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			@Deprecated
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {


			}

			@Override
			@Deprecated
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {


			}
		});
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message,
			String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (("KP_6002").equalsIgnoreCase(p_opcode)) {
			// 필내용
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					KP_6004();
				} else {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
					stopLoading(__CLASSNAME__, getMethodName());
				}
			}
		}

	}

	/**
	 * 댓글좌우스크롤처리 <br>
	 * 결과가 없어도처리
	 */
	public void KP6004(KPnnnn KP_xxxx, int page) {
		if (_IKaraoke.DEBUG)
			Log2.i(__CLASSNAME__, getMethodName());

		if (page < 1) {
			return;
		}

		if (KP_xxxx == null) {
			KP_xxxx = KP_6004;
		}

		KPItem info = KP_xxxx.getInfo();
		ArrayList<KPItem> lists = KP_xxxx.getLists();

		try {
			if (info != null) {
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		View v = null;

		// if (info != null) {
		// total_page = TextUtil.parseInt(info.getString("total_page") == null ? "1" : info
		// .getString("total_page"));
		// }

		v = findViewById(R.id.include_player_people);
		if (v != null) {
			if (lists != null && lists.size() > 0) {
				v.setVisibility(View.VISIBLE);
				// WidgetUtils.setVisibility(mContext, (ViewGroup) v, View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
				// WidgetUtils.setVisibility(mContext, (ViewGroup) v, View.INVISIBLE);
			}
		}

		int res = 0;
		String name = "";
		String num = "";
		String url = "";
		String ment = "";

		KPItem list = null;

		for (int i = 0; i < 5; i++) {
			num = String.format("%02d", i + 1);
			url = null;
			ment = null;

			list = null;

			int index = i + (page - 1) * 5;
			if (index < lists.size()) {
				list = lists.get(index);
			}

			if (list != null) {
				name = list.getValue("name");
				url = list.getValue("url_profile");
				ment = "(" + name + ":)\n" + list.getValue("ment");
			}

			if (getView() == null) {
				return;
			}

			res = getResource("btn_player_people_" + num, "id");
			v = findViewById(res);
			if (v != null) {
				// if (URLUtil.isNetworkUrl(url)) {
				// putURLImage(mContext, (ImageView) v, url, false);
				// } else {
				// ((ImageView) v).setImageResource(R.drawable.ic_menu_01);
				// }
				putURLImage(mContext, (ImageView) v, url, false, R.drawable.ic_menu_01);
				if (!URLUtil.isNetworkUrl(url)) {
					v.setVisibility(View.INVISIBLE);
				}
			}

		}
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		// KPItem list = null;
		// if (KP_6002.getListCount() > 0) {
		// list = KP_6002.getList(0);
		// }

		if (v.getId() == R.id.arrow_left) {
			// KP_2011(-1);
			onContextItemSelected(R.id.context_feel_hearlist, KP_6002, 0, true);
		} else if (v.getId() == R.id.arrow_right) {
			// KP_2011(1);
			onContextItemSelected(R.id.context_feel_hearlist, KP_6002, 0, true);
		} else if (v.getId() == R.id.btn_player_people_01) {
			// showComment(0);
			onContextItemSelected(R.id.context_go_holic, KP_6004, 0, true);
		} else if (v.getId() == R.id.btn_player_people_02) {
			// showComment(1);
			onContextItemSelected(R.id.context_go_holic, KP_6004, 1, true);
		} else if (v.getId() == R.id.btn_player_people_03) {
			// showComment(2);
			onContextItemSelected(R.id.context_go_holic, KP_6004, 2, true);
		} else if (v.getId() == R.id.btn_player_people_04) {
			// showComment(3);
			onContextItemSelected(R.id.context_go_holic, KP_6004, 3, true);
		} else if (v.getId() == R.id.btn_player_people_05) {
			// showComment(4);
			onContextItemSelected(R.id.context_go_holic, KP_6004, 4, true);
		}
	}

}
