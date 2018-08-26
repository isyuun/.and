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
 * filename	:	FollowListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FollowListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.UserListAdapter;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * <pre>
 * 팔로워목록
 * 팔로잉목록
 * </pre>
 * 
 * @author isyoon
 * @since 2012. 7. 20.
 * @version 1.0
 */

class UserListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	UserListAdapter mUserListAdapter = null;

	KPnnnn KP_6010;

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6010 = KP_init(mContext, KP_6010);
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mUserListAdapter == null) {
			mUserListAdapter = new UserListAdapter(getBaseActivity(), R.layout.item_follow,
					KP_nnnn.getLists(), new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());

		}

		mUserListAdapter.setNotifyOnChange(false);
		setListAdapter(mUserListAdapter);
		// getListView().setDivider(null);
	}

	@Override
	public void KP_list(int page) {

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
		play_id = list.getValue("uid");

		if (("FEEL").equalsIgnoreCase(p_type)) {
			play_id = list.getValue("feel_id");
		}

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (("FEEL").equalsIgnoreCase(p_type)) {
			KP_nnnn.KP_6004(getApp().p_mid, p_m1, p_m2, "L", play_id, page);
		} else {
			KP_nnnn.KP_6011(getApp().p_mid, p_m1, p_m2, p_type, play_id, page);
		}

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		if (("KP_6010").equalsIgnoreCase(p_opcode)) {
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				// KP_6010 팔로우삭제
				// KP_list(1);
				KPItem item = getBaseListAdapter().getItem(KP_index);
				getBaseListAdapter().remove(item);
				getBaseListAdapter().notifyDataSetChanged();
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			} else {
				// getListAdapter().notifyDataSetChanged();
			}
		} else {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		}
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

		if (v.getId() == R.id.btn_del) {
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete),
					getString(R.string.btn_title_delete), new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							deleteFollow();
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);
		super.onItemClick(parent, v, position, id);

		try {
			KPItem list = KP_nnnn.getList(KP_index);
			if (list == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
				return;
			}

			String uid = list.getValue("uid");
			if (!TextUtil.isEmpty(uid)) {
				onContextItemSelected(R.id.context_go_holic, KP_nnnn, KP_index, true);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void deleteFollow() {
		KPItem list = KP_nnnn.getList(KP_index);

		String seq = list.getValue("seq");
		String uid = list.getValue("uid");

		KP_6010.KP_6010(getApp().p_mid, p_m1, p_m2, uid, seq);
	}
}
