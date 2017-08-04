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
 * filename	:	SingListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ SingListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps.adpt.FeelListAdapter;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 *          //
 */
class FeelListFragment extends playListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public static Fragment newInstance(Bundle extras) {

		return null;
	}

	/**
	 * KP_6001 FEEL관리(파일업로드) KP_1011 대체
	 */
	public KPnnnn KP_6001 = null;
	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	KPnnnn KP_6003;

	FeelListAdapter mFeelListAdapter = null;
	String mode = "";

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6001 = KP_init(mContext, KP_6001);
		KP_6003 = KP_init(mContext, KP_6003);
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mFeelListAdapter == null) {
			mFeelListAdapter = new FeelListAdapter(getBaseActivity(), R.layout.item_feel,
					KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {
							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mFeelListAdapter.setNotifyOnChange(false);
		setListAdapter(mFeelListAdapter);

	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.refresh();
	}

	@Override
	public void KP_list(int page) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + page);

		play_id = getApp().p_mid;

		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		// list.merge(info);

		if (!TextUtil.isEmpty(list.getValue("id"))) {
			play_id = list.getValue("id");
		}

		p_type = !TextUtil.isEmpty(list.getValue("type")) ? list.getValue("type") : "ALL";
		if (("ALL").equalsIgnoreCase(p_type)) {
			play_id = getApp().p_mid;
		} else if (("MY").equalsIgnoreCase(p_type)) {
			play_id = getApp().p_mid;
		} else if (("OT").equalsIgnoreCase(p_type)) {
			play_id = list.getValue("uid");
		}
		play_id = list.getValue("uid");
		// play_id = getApp().p_mid;

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "type - " + p_type);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "play_id - " + play_id);

		// if (!("ALL").equalsIgnoreCase(p_type) && TextUtil.isEmpty(play_id)) {
		// getApp().popupToast(R.string.errmsg_dataerror, Toast.LENGTH_LONG);
		// return;
		// }

		// // Get the intent, verify the action and get the query
		// if (Intent.ACTION_SEARCH.equals(getBaseActivity().getIntent().getAction())) {
		// String query = getBaseActivity().getIntent().getStringExtra(SearchManager.QUERY);
		// //String word = URLDecoder.decode(query);
		// String word = "";
		// try {
		// word = URLDecoder.decode(query, "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		//
		// //e.printStackTrace();
		// }
		// boolean hangul = TextUtil.isNotAlphaNumeric(word);
		// // 한글
		// if ((hangul && word.length() > 1) || (!hangul && word.length() > 2)) {
		// KP_nnnn.KP_2020(getApp().p_mid, p_m1, p_m2, "SONG", query, page);
		// String title = String.format(getString(R.string.context_format_search_result), query);
		// getBaseActivity().setTitle(title);
		// } else {
		// stopLoading(__CLASSNAME__, getMethodName());
		// popupToast(R.string.errmsg_search_short, Toast.LENGTH_SHORT);
		// getBaseActivity().onSearchRequested();
		// //close();
		// }
		// } else {
		// KP_nnnn.KP_6000(getApp().p_mid, p_m1, p_m2, play_id, order, page);
		// }
		KP_nnnn.KP_6000(getApp().p_mid, p_m1, p_m2, play_id, p_type, page);

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message,
			String r_info) {


		if (("KP_6001").equalsIgnoreCase(p_opcode)) {
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				// KP_6001 필삭제
				// KP_list(1);
				KPItem item = getBaseListAdapter().getItem(KP_index);
				getBaseListAdapter().remove(item);
				getBaseListAdapter().notifyDataSetChanged();
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			} else {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				stopLoading(__CLASSNAME__, getMethodName());
			}
		} else if (("KP_6003").equalsIgnoreCase(p_opcode)) {
			// KP_6003 좋아요(하트) 등록/수정요청
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP6003();
				}
			}
		} else {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		}

	}

	@Override
	public void KPlist() {

		KPlist(KP_nnnn.getInfo(), KP_nnnn.getLists());
	}

	@Override
	public void onListItemClick(View v) {
		if (getListView() == null) {
			return;
		}

		String nm = getResourceEntryName(v.getId());
		int position = getPositionForView((View) v.getParent());
		if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + v.getId());

		if (position == AdapterView.INVALID_POSITION) {
			return;
		}

		super.onListItemClick(v);

		// KPItem info = KP_nnnn.getInfo();
		KPItem list = KP_nnnn.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		if (v.getId() == R.id.btn_feel_del) {
			// deleteFeelPost();
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete),
					getString(R.string.btn_title_delete), new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							KP_6001();
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_feel_edit) {
			openFeelPost("UPDATE", KP_nnnn, KP_index);
		} else if (v.getId() == R.id.btn_siren) {
			// onContextItemSelected(mContext, R.id.context_siren_open, info, list, true);
			onContextItemSelected(R.id.context_siren_open, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.feel_image) {
			onContextItemSelected(R.id.context_play_feel, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.chk_heart) {
			KP_6003();
		} else if (v.getId() == R.id.btn_link) {
			String furl = list.getValue("furl");
			openWebView(webview.class, getString(R.string.M1_MENU_FEEL),
					getString(R.string.M2_FEEL_INFO), getString(R.string.menu_feel), furl, "POST", false);
		} else if (v.getId() == R.id.btn_sing) {
			onContextItemSelected(R.id.context_play_sing, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_listen) {
			onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		} else {
		}

	}

	protected void KP_6001() {
		KPItem list = KP_nnnn.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		String feel_id = "";
		String feel_type = "";

		if (list != null) {
			feel_id = list.getValue("feel_id");
			feel_type = list.getValue("feel_type");
		}

		if (!TextUtil.isEmpty(feel_id)) {
			KP_6001.KP_6001(getApp().p_mid, p_m1, p_m2, "POST", feel_id, "DEL", "", "", feel_type, "", "", "", "", "", "");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		//String nm = getResourceEntryName(v.getId());
		//if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);
		super.onItemClick(parent, v, position, id);

		KPItem list = KP_nnnn.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		String record_id = list.getValue("record_id");

		// 자신인경우
		// String my_contents = list.getString("my_contents");
		// if (("Y").equalsIgnoreCase(my_contents)) {
		// onContextItemSelected(R.id.context_play_feel, KP_nnnn, KP_index, true);
		// } else {
		// if (!TextUtil.isEmpty(record_id)) {
		// onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		// } else {
		// onContextItemSelected(R.id.context_play_feel, KP_nnnn, KP_index, true);
		// }
		// }
		if (!TextUtil.isEmpty(record_id)) {
			onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		} else {
			onContextItemSelected(R.id.context_play_feel, KP_nnnn, KP_index, true);
		}

	}

	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	protected void KP_6003() {
		KPItem list = KP_nnnn.getList(KP_index);
		String feel_id = list.getValue("feel_id");
		if (!TextUtil.isEmpty(feel_id)) {
			KP_6003.KP_6003(getApp().p_mid, p_m1, p_m2, "FEEL", "", feel_id);
		}
	}

	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	protected void KP6003() {
		ArrayList<KPItem> lists = KP_nnnn.getLists();

		KPItem list = KP_6003.getList(0);

		if (list != null) {
			// String feel_id = list.getString("feel_id");
			// String record_id = list.getString("record_id");
			String is_heart = list.getValue("is_heart");
			String heart = list.getValue("heart");

			int position = KP_index;

			KPItem item = lists.get(position);

			item.putValue("is_heart", is_heart);
			item.putValue("heart", heart);

			try {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "item - " + item.toString(2));
			} catch (Exception e) {

				// e.printStackTrace();
			}

			// for (int i = 0; i < lists.size(); i++) {
			// item = lists.get(i);
			// if (feel_id.equalsIgnoreCase(item.getString("feel_id"))) {
			// position = i;
			// break;
			// }
			// }
			// item = lists.get(position);
			// item.putString("is_heart", is_heart);

			// for (int i = 0; i < lists.size(); i++) {
			// item = lists.get(i);
			// if (record_id.equalsIgnoreCase(item.getString("record_id"))) {
			// position = i;
			// break;
			// }
			// }
			// item = lists.get(position);
			// item.putString("is_heart", is_heart);

			// try {
			// CheckBox c = (CheckBox) findViewById(R.id.chk_heart);
			// if (("Y").equalsIgnoreCase(is_heart)) {
			// c.setChecked(true);
			// } else {
			// c.setChecked(false);
			// }
			// TextView v = (TextView) findViewById(R.id.heart);
			// v.setText(heart);
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }
			getBaseListAdapter().notifyDataSetChanged();

		}
	}

}
