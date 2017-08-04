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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.KPOP
 * filename	:	ReplyListFragment.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ReplyListFragment.java
 * </pre>
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.ReplyListAdapter;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 *
 * <pre>
 * 방명록목록
 * </pre>
 *
 * @author isyoon
 * @since 2012. 7. 20.
 * @version 1.0
 * @see ReplyListFragment.java
 */

class ReplyListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	ReplyListAdapter mReplyListAdapter = null;

	/**
	 * KP_6020 방명록 등록/삭제
	 */
	KPnnnn KP_6020;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_reply2, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6020 = KP_init(mContext, KP_6020);

	}

	@Override
	public void setListAdapter() {
		super.setListAdapter();

		if (mReplyListAdapter == null) {
			int res[] = {R.layout.item_message_right, R.layout.item_message_left};
			mReplyListAdapter = new ReplyListAdapter(mContext, res, KP_nnnn.getLists(), new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					onListItemClick(view);
				}
			}, getApp().getImageDownLoader());
		}

		mReplyListAdapter.setNotifyOnChange(false);
		setListAdapter(mReplyListAdapter);
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

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		KP_nnnn.KP_6021(getApp().p_mid, p_m1, p_m2, play_id, page);

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);

		if (("KP_6020").equalsIgnoreCase(p_opcode)) {
			// 방명록등록/삭제
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP_list(1);
				} else {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				}
			}
		} else {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		}
	}

	@Override
	public void onClick(View v) {

		super.onClick(v);
		if (v.getId() == R.id.btn_reply_save) {
			KP_6020("ADD");
		} else {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);
		super.onItemClick(parent, v, position, id);
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
			// deleteFeelPost();
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete), getString(R.string.btn_title_delete), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					KP_6020("DEL");
				}
			}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_edit) {
		} else {
		}
	}

	protected void KP_6020(String mode) {
		EditText t = (EditText) findViewById(R.id.edt_reply_text);
		String comment = t.getText().toString();

		if (("ADD").equalsIgnoreCase(mode) && TextUtil.isEmpty(comment)) {
			getApp().popupToast(R.string.hint_feel_text, Toast.LENGTH_SHORT);
			return;
		} else {
			t.setText("");
		}
		getApp().hideSoftInput(t);

		String uid = "";
		try {
			uid = getList().getValue("uid");
		} catch (Exception e) {

			e.printStackTrace();
			return;
		}

		KPItem list = KP_nnnn.getList(KP_index);

		if (!("ADD").equalsIgnoreCase(mode) && list == null) {
			return;
		}

		String seq = "";
		if (list != null) {
			seq = list.getValue("seq");
		}

		if (!TextUtil.isEmpty(uid)) {
			KP_6020.KP_6020(getApp().p_mid, p_m1, p_m2, mode, uid, seq, comment);
		}
	}

}
