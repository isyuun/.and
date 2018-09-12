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
 * filename	:	PlayListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ PlayListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.Toast;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO<br>
 * NOTE:<br>
 * SingListFragment/ListenListFragment 부모클래스<br>
 * 공통기능관리<br>
 * 1. 애창곡기능<br>
 * <br>
 * 
 * @author isyoon
 * @since 2013. 2. 8.
 * @version 1.0
 */

public class playListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * // KP_6050 애창곡등록 <br>
	 */
	KPnnnn KP_6050;

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6050 = KP_init(mContext, KP_6050);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

		KPItem list = KP_nnnn.getList(KP_index);
		if (list == null) {
			return;
		}

		MenuItem item = null;

		// 애창곡(등록/삭제)
		String mark_mysing = list.getValue("mark_mysing");
		if (("Y").equalsIgnoreCase(mark_mysing)) {
			item = menu.findItem(R.id.context_mysing_register);
			setContextMenuItemVisible(item, false);
			item = menu.findItem(R.id.context_mysing_delete);
			setContextMenuItemVisible(item, true);
		} else {
			item = menu.findItem(R.id.context_mysing_register);
			setContextMenuItemVisible(item, true);
			item = menu.findItem(R.id.context_mysing_delete);
			setContextMenuItemVisible(item, false);
		}

		// 반주곡확인
		String song_id = list.getValue("song_id");
		if (!TextUtil.isNumeric(song_id)) {
			// SING
			item = menu.findItem(R.id.context_play_sing);
			setContextMenuItemVisible(item, false);
			// SONG's (이 노래의 녹음곡)
			item = menu.findItem(R.id.context_song_record);
			setContextMenuItemVisible(item, false);
			// 애창곡(등록)
			item = menu.findItem(R.id.context_mysing_register);
			setContextMenuItemVisible(item, false);
			// 애창곡(삭제)
			item = menu.findItem(R.id.context_mysing_delete);
			setContextMenuItemVisible(item, false);
			// AUDITION 개최
			item = menu.findItem(R.id.context_audition_open);
			setContextMenuItemVisible(item, false);
		}

		// 녹음곡확인
		String record_id = list.getValue("record_id");
		if (TextUtil.isNull(record_id) || TextUtil.isEmpty(record_id) || (new String(record_id)).length() == 15) {
			// LISTEN
			item = menu.findItem(R.id.context_play_listen);
			setContextMenuItemVisible(item, false);
		}

		// 가수확인
		String artist_id = list.getValue("artist_id");
		if (!TextUtil.isNumeric(artist_id)) {
			// ARTIST's (이 가수의 다른 곡)
			item = menu.findItem(R.id.context_artist_song);
			setContextMenuItemVisible(item, false);
		}

		// 사용자확인
		String uid = list.getValue("uid");
		if (TextUtil.isNull(uid) || TextUtil.isEmpty(uid)) {
			// MY BEST(이회원의인기녹음곡)
			item = menu.findItem(R.id.context_my_best);
			setContextMenuItemVisible(item, false);
		}

		if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(getBaseActivity().getPackageName())) {
			item = menu.findItem(R.id.context_audition_open);
			setContextMenuItemVisible(item, false);
		}

		super.onCreateContextMenu(menu, v, menuInfo);

	}

	/**
	 * 애창곡등록추가
	 */
	@Override
	protected Intent onContextItemSelected(Context context, int id, KPItem info, KPItem list,
			boolean open) {

		Intent intent = super.onContextItemSelected(context, id, info, list, open);

		String song_id = list.getValue("song_id");

		if (id == R.id.context_mysing_delete || id == R.id.context_mysing_register) {
			KP_6050.KP_6050(getApp().p_mid, p_m1, p_m2, song_id);
		} else {
		}

		return intent;
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		if (("KP_6050").equalsIgnoreCase(p_opcode)) {
			// KP_6050 애창곡등록
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				KPItem list = KP_6050.getList(0);
				KPItem item = getBaseListAdapter().getItem(KP_index);

				String mark_mysing = list.getValue("mark_mysing");
				item.putValue("mark_mysing", mark_mysing);

				// 애창곡목록메뉴인경우삭제
				if (getString(R.string.M1_MENU_SING).equalsIgnoreCase(p_m1)
						&& getString(R.string.M2_SING_MYSING).equalsIgnoreCase(p_m2)) {
					if (("N").equalsIgnoreCase(mark_mysing)) {
						getBaseListAdapter().remove(item);
					}
				}

				getBaseListAdapter().notifyDataSetChanged();

				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);

				getApp().popupToast(r_message, Toast.LENGTH_SHORT);

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "item - " + item.toString(2));
			}
		}
	}

}
