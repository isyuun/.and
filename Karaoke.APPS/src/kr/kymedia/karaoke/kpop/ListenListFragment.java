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

import android.app.SearchManager;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps.adpt.ListenListAdapter2;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 * @see SingListFragment.java
 */

class ListenListFragment extends playListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * KP_3011 녹음곡 공개설정/삭제
	 */
	KPnnnn KP_3011;
	ListenListAdapter2 mListenListAdapter = null;

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mListenListAdapter == null) {
			mListenListAdapter = new ListenListAdapter2(getBaseActivity(), R.layout.item_listen3,
					KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mListenListAdapter.setNotifyOnChange(false);
		setListAdapter(mListenListAdapter);
		// getListView().setDivider(null);
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_3011 = KP_init(mContext, KP_3011);
	}

	@Override
	public void KP_list(int page) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + page);

		String order = "UPDATE";

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

		// Get the intent, verify the action and get the query
		if (Intent.ACTION_SEARCH.equals(getBaseActivity().getIntent().getAction())) {
			String query = getBaseActivity().getIntent().getStringExtra(SearchManager.QUERY);
			// String word = URLDecoder.decode(query);
			String word = "";
			try {
				word = URLDecoder.decode(query, "UTF-8");
			} catch (UnsupportedEncodingException e) {

				// e.printStackTrace();
			}
			boolean hangul = TextUtil.isNotAlphaNumeric(word);
			// 한글
			if ((hangul && word.length() > 1) || (!hangul && word.length() > 2)) {
				KP_nnnn.KP_2020(getApp().p_mid, p_m1, p_m2, "SONG", query, page);
				String title = String.format(getString(R.string.context_format_search_result), query);
				getBaseActivity().setTitle(title);
			} else {
				stopLoading(__CLASSNAME__, getMethodName());
				getApp().popupToast(R.string.errmsg_search_short, Toast.LENGTH_SHORT);
				getBaseActivity().onSearchRequested();
				// close();
			}
		} else {
			if (p_m1.equalsIgnoreCase(getString(R.string.M1_MENU_MYHOLIC))
					&& p_m2.equalsIgnoreCase(getString(R.string.M2_MY_REC))) {
				// m1 = getString(R.string.M1_MENU_MYHOLIC);
				// m2 = getString(R.string.M2_MY_REC);
				// KP_nnnn.KP_3010(getApp().p_mid, p_m1, p_m2, play_id, order, page);
				KP_nnnn.KP_3013(getApp().p_mid, p_m1, p_m2, play_id, order, page);
			} else {
				// p_m1 = getString(R.string.M1_MENU_LISTEN);
				// p_m2 = "LISTEN_TOP100";
				// p_m2 = getString(R.string.M2_LISTEN_TIMELINE);
				if (p_type != null && ("SONG").equalsIgnoreCase(p_type)) {
					KP_nnnn.KP_2001(getApp().p_mid, p_m1, p_m2, play_id, page);
				} else {
					// KP_nnnn.KP_2000(getApp().p_mid, p_m1, p_m2, page);
					KP_nnnn.KP_2100(getApp().p_mid, p_m1, p_m2, page);
				}
			}
		}

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message,
			String r_info) {


		if (("KP_3011").equalsIgnoreCase(p_opcode)) {
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				// KP_3011 녹음곡삭제
				if (mode.equalsIgnoreCase("DEL")) {
					// KP_list(1);
					KPItem item = getBaseListAdapter().getItem(KP_index);
					getBaseListAdapter().remove(item);
					getBaseListAdapter().notifyDataSetChanged();
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				}
			} else {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
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
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		String vn = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_index + ", " + vn);



		KPItem info = KP_nnnn.getInfo();
		KPItem list = getList();
		ArrayList<KPItem> lists = KP_nnnn.getLists();

		if (lists != null && KP_index > -1 && KP_index < lists.size()) {
			KPItem item = lists.get(KP_index);
			list = item;
			list.merge(item);
		}

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		p_type = "REORD";
		p_type = !TextUtil.isEmpty(list.getValue("type")) ? list.getValue("type") : p_type;

		play_id = list.getValue("record_id");

		if (!TextUtil.isEmpty(list.getValue("id"))) {
			play_id = list.getValue("id");
		}

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

		// listen.xml 에서 메뉴 불러오기
		MenuInflater inflater = getBaseActivity().getMenuInflater();
		inflater.inflate(R.menu.context_listen, menu);

		String title = getString(R.string.context_title_listen);
		if (!TextUtil.isEmpty(list.getValue("title"))) {
			title = list.getValue("title");
		}
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.setHeaderTitle(title);

		// LayoutInflater layout = getBaseActivity().getLayoutInflater();
		// View view = layout.inflate( R.layout.context_view_for_button, null );
		// menu.setHeaderView( view );

		// 내녹음곡듣기시
		MenuItem item = null;
		if (p_m1.equalsIgnoreCase(getString(R.string.M1_MENU_MYHOLIC))
				&& p_m2.equalsIgnoreCase(getString(R.string.M2_MY_REC))) {
			// USER's (사용자의 전체 녹음곡)
			item = menu.findItem(R.id.context_user_record);
			setContextMenuItemVisible(item, false);
			// 다른사용자녹음곡리스트인경우
			if (("UID").equalsIgnoreCase(p_type) && !getApp().p_mid.equalsIgnoreCase(play_id)) {
				// 녹음곡삭제
				item = menu.findItem(R.id.context_delete_listen);
				setContextMenuItemVisible(item, false);
			}
		} else {
			// 녹음곡삭제
			item = menu.findItem(R.id.context_delete_listen);
			setContextMenuItemVisible(item, false);
		}

		// MV(유튜브영상) 유부확인
		String url_vod = list.getValue("url_vod");
		if (!URLUtil.isNetworkUrl(url_vod)) {
			item = menu.findItem(R.id.context_play_movie);
			setContextMenuItemVisible(item, false);
		}

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onListItemClick(View v) {
		if (getListView() == null) {
			return;
		}

		String nm = getResourceEntryName(v.getId());
		int position = getPositionForView((View) v.getParent());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position);
		if (position == AdapterView.INVALID_POSITION) {
			return;
		}


		super.onListItemClick(v);

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

		String record_id = list.getValue("record_id");

		if (v.getId() == R.id.lock) {
			// 공개여부확인
			String is_on = list.getValue("is_on");
			if (("Y").equalsIgnoreCase(is_on)) {
				mode = "OFF";
				list.putValue("is_on", "N");
			} else {
				mode = "ON";
				list.putValue("is_on", "Y");
			}
			int res = 0;
			if (("ON").equalsIgnoreCase(mode)) {
				// res = getResource("btn_list_lock_off", "drawable");
				res = R.drawable.ic_unlock;
			} else {
				// res = getResource("btn_list_lock_on", "drawable");
				res = R.drawable.ic_lock;
			}
			((ImageButton) v).setImageResource(res);
			KP_3011.KP_3011(getApp().p_mid, p_m1, p_m2, mode, record_id);
		} else if (v.getId() == R.id.btn_del) {
			onContextItemSelected(R.id.context_delete_listen, KP_nnnn, KP_index, false);
		} else if (v.getId() == R.id.btn_sing) {
			onContextItemSelected(R.id.context_play_sing, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_play || v.getId() == R.id.btn_listen) {
			onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		} else {
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);


		super.onItemClick(parent, v, position, id);

		// mListenListAdapter.setSelectedPosition(KP_index);

		onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);

	}

	/**
	 * @see BaseListFragment#onListViewItemLongClick(android .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		String name = getResourceEntryName(view.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "," + position + "," + id);


		boolean ret = super.onItemLongClick(parent, view, position, id);

		showContextMenu(view, true, false);

		return ret;
	}

}
