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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.AuditionListAdapter;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 */

class AuditionListFragment extends playListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public static Fragment newInstance(Bundle extras) {

		return null;
	}

	KPnnnn KP_6035;
	AuditionListAdapter mAuditionListAdapter = null;

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mAuditionListAdapter == null) {
			mAuditionListAdapter = new AuditionListAdapter(getBaseActivity(), R.layout.item_audition2,
					KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mAuditionListAdapter.setNotifyOnChange(false);

		setListAdapter(mAuditionListAdapter);
		// getListView().setDivider(null);

	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6035 = KP_init(mContext, KP_6035);
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
		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		if (!TextUtil.isEmpty(list.getValue("id"))) {
			play_id = list.getValue("id");
		}

		p_type = list.getValue("type");
		// if (("REORD").equalsIgnoreCase(p_type)) {
		// if (!TextUtil.isEmpty(list.getString("record_id"))) {
		// play_id = list.getString("record_id");
		// }
		// } else if (("UID").equalsIgnoreCase(p_type)) {
		// if (!TextUtil.isEmpty(list.getString("uid"))) {
		// play_id = list.getString("uid");
		// }
		// } else if (("SONG").equalsIgnoreCase(p_type)) {
		// if (!TextUtil.isEmpty(list.getString("song_id"))) {
		// play_id = list.getString("song_id");
		// }
		// } else if (("ARTIST").equalsIgnoreCase(p_type)) {
		// if (!TextUtil.isEmpty(list.getString("artist_id"))) {
		// play_id = list.getString("artist_id");
		// }
		// }
		if (!TextUtil.isEmpty(list.getValue("uid"))) {
			play_id = list.getValue("uid");
		}

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
			KP_nnnn.KP_6033(getApp().p_mid, p_m1, p_m2, play_id, page);
		}

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		if (("KP_6035").equalsIgnoreCase(p_opcode)) {

			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				getApp().popupToast(r_message, Toast.LENGTH_LONG);
			}

			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				if (mode.equalsIgnoreCase("DEL")) {
					// KP_list(1);
					KPItem item = getBaseListAdapter().getItem(KP_index);
					getBaseListAdapter().remove(item);
					getBaseListAdapter().notifyDataSetChanged();
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				} else {
					// getListAdapter().notifyDataSetChanged();
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
		inflater.inflate(R.menu.context_audition, menu);

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

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);


		super.onItemClick(parent, v, position, id);

		onContextItemSelected(R.id.context_audition, KP_nnnn, KP_index, true);

	}

	/**
	 * @see _BaseListFragment#onListViewItemLongClick(android .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		String name = getResourceEntryName(view.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "," + position + "," + id);


		boolean ret = super.onItemLongClick(parent, view, position, id);

		// showContextMenu(view);

		return ret;
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

		String audition_id = list.getValue("audition_id");
		if (TextUtil.isEmpty(audition_id)) {
			audition_id = getList().getValue("id");
		}
		String uid = list.getValue("uid");

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
			KP_6035.KP_6035(getApp().p_mid, p_m1, p_m2, uid, mode, audition_id);
		} else if (v.getId() == R.id.btn_del) {
			onContextItemSelected(R.id.context_delete_listen, KP_nnnn, KP_index, false);
		} else if (v.getId() == R.id.btn_sing) {
			onContextItemSelected(R.id.context_play_sing, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_listen) {
			onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		} else {
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		super.onActivityResult(requestCode, resultCode, data);
	}

}
