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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Toast;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.adpt.SingListAdapter;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 * @see SingListFragment.java
 */

class SingListFragment extends playListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	SingListAdapter mSingListAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mSingListAdapter == null) {
			mSingListAdapter = new SingListAdapter(getBaseActivity(), R.layout.item_sing3, KP_nnnn.getLists(),
					new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mSingListAdapter.setNotifyOnChange(false);
		setListAdapter(mSingListAdapter);
		// getListView().setDivider(null);
	}

	@Override
	public void KP_list(int page) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + page);

		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

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
				// KP_nnnn.KP_0020(getApp().p_mid, p_m1, p_m2, "SONG", query,
				// page);
				// KP_nnnn.KP_0020(getApp().p_mid, p_m1, p_m2, "ARTIST", query,
				// page);
				// KP_nnnn.KP_0020(getApp().p_mid, p_m1, p_m2, "LYRIC", query,
				// page);
				// KP_nnnn.KP_0020(getApp().p_mid, p_m1, p_m2, "NUMBER", query,
				// page);
				Bundle extras = getBaseActivity().getIntent().getExtras();
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "extras - " + extras);
				KP_nnnn.KP_0020(getApp().p_mid, p_m1, p_m2, "SONG", query, page);
				String title = String.format(getString(R.string.context_format_search_result), query);
				getBaseActivity().setTitle(title);
			} else {
				stopLoading(__CLASSNAME__, getMethodName());
				getApp().popupToast(R.string.errmsg_search_short, Toast.LENGTH_SHORT);
				getBaseActivity().onSearchRequested();
				// close();
			}
		} else {
			if (p_type != null && ("ARTIST").equalsIgnoreCase(p_type)) {
				if (TextUtil.isEmpty(play_id)) {
					getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
					return;
				}
				KP_nnnn.KP_1040(getApp().p_mid, p_m1, p_m2, play_id, page);
			} else {
				KP_nnnn.KP_1000(getApp().p_mid, p_m1, p_m2, play_id, page);
			}
		}

		super.KP_list(page);
	}

	@Override
	public void KPlist() {

		KPlist(KP_nnnn.getInfo(), KP_nnnn.getLists());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_index + ", " + getResourceEntryName(v.getId()));

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
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		String uid = null;
		String song_id = null;

		if (list != null) {
			// 무료곡표시여부확인
			song_id = list.getValue("song_id");
			// BEST HOLIC 유무확인
			uid = list.getValue("uid");
		}

		// context_sing.xml 에서 메뉴 불러오기
		MenuInflater inflater = getBaseActivity().getMenuInflater();
		inflater.inflate(R.menu.context_sing, menu);

		String title = getString(R.string.context_title_sing);
		if (!TextUtil.isEmpty(list.getValue("title"))) {
			title = list.getValue("title");
		}
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.setHeaderTitle(title);

		MenuItem item = null;

		// LayoutInflater layout = getBaseActivity().getLayoutInflater();
		// View view = layout.inflate( R.layout.context_view_for_button, null );
		// menu.setHeaderView( view );

		// 무료곡
		if (getApp().p_mid == null || TextUtil.isEmpty(getApp().p_mid)) {
			// 비로그인 사용자
			item = menu.findItem(R.id.context_free_song);
			setContextMenuItemVisible(item, false);
		} else {
			// 무료곡 사용가능확인
			// 이용권이 유무확인 getApp().p_passtype : N
			// 무료이용 곡번호가 없고 free : 0
			// 무료이동 곡번호가 일치하지 한는경우
			if (isPassUser() || (getApp().p_free != 0 && getApp().p_free != TextUtil.parseInt(song_id))) {
				item = menu.findItem(R.id.context_free_song);
				setContextMenuItemVisible(item, false);
			}
		}

		// BEST HOLIC 유무확인
		if (TextUtil.isEmpty(uid)) {
			item = menu.findItem(R.id.context_best_holic);
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

	public void playItem() {
		if ((getString(R.string.M1_MENU_YOUTUBE)).equalsIgnoreCase(p_m1)) {
			openYouTube(KP_nnnn, KP_index, true, true);
		} else {
			onContextItemSelected(R.id.context_play_sing, KP_nnnn, KP_index, true);
		}
	}

	/**
	 * @see BaseListFragment#onItemClick(android.view.View)
	 */
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

		if (v.getId() == R.id.mic) {
			openYouTube(KP_nnnn, KP_index, true, true);
		} else if (v.getId() == R.id.btn_play || v.getId() == R.id.btn_sing) {
			onContextItemSelected(R.id.context_play_sing, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_listen) {
			onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		} else {
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);


		super.onItemClick(parent, v, position, id);

		playItem();

	}

	/**
	 * @see BaseListFragment#onListViewItemLongClick(android .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		String name = getResourceEntryName(view.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "," + position + "," + id);


		boolean ret = super.onItemLongClick(parent, view, position, id);

		if ((getString(R.string.M1_MENU_YOUTUBE)).equalsIgnoreCase(p_m1)) {
		} else {
			showContextMenu(view, true, false);
		}

		return ret;
	}

}
