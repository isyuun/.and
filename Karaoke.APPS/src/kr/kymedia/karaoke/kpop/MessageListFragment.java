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
 * filename	:	ReplyListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ReplyListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.MessageListAdapter;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * <pre>
 * 쪽지함목록
 * </pre>
 * 
 * @author isyoon
 * @since 2012. 7. 20.
 * @version 1.0
 * @see ReplyListFragment.java
 */

class MessageListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * KP_6043 쪽지삭제/차단
	 */
	KPnnnn KP_6043;

	MessageListAdapter mMessageListAdapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_message2, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6043 = KP_init(mContext, KP_6043);

	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mMessageListAdapter == null) {
			int res[] = { R.layout.item_message_right, R.layout.item_message_left };
			mMessageListAdapter = new MessageListAdapter(getActivity(), res, KP_nnnn.getLists(), new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					onListItemClick(view);
				}
			}, getApp().getImageDownLoader());
		}

		mMessageListAdapter.setNotifyOnChange(false);
		setListAdapter(mMessageListAdapter);
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

		KP_nnnn.KP_6041(getApp().p_mid, p_m1, p_m2, play_id, page);

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {


		if (("KP_6043").equalsIgnoreCase(p_opcode)) {
			// KP_6043 쪽지삭제/차단
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP_list(1);
				}
			}
		} else {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		}
	}

	@Override
	public void onClick(View v) {

		KPItem info = KP_nnnn.getInfo();

		if (info == null) {
			return;
		}

		String url_block = info.getValue("url_block");
		String url_contents = info.getValue("url_contents");

		super.onClick(v);

		if (v.getId() == R.id.btn_message_send) {
			openWebView(webview.class, getString(R.string.M1_MENU_MESSAGE), getString(R.string.M2_MESSAGE_SEND), getString(R.string.btn_title_message_send), url_contents, "POST", false);
		} else if (v.getId() == R.id.btn_message_setting) {
			openWebView(webview.class, getString(R.string.M1_MENU_MESSAGE), getString(R.string.M2_MESSAGE_SETTING), getString(R.string.btn_title_message_send), url_block, "POST", false);
		} else {
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		//if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);
		if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);

		super.onItemClick(parent, v, position, id);

		showContextMenu(v, true, false);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		boolean ret = super.onItemLongClick(parent, view, position, id);

		showContextMenu(view, true, false);

		return ret;
	}

	@Override
	public void onListItemClick(View v) {
		if (getListView() == null) {
			return;
		}

		String nm = getResourceEntryName(v.getId());
		int position = getPositionForView((View) v.getParent());
		//if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + v.getId());
		if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + v.getId());
		if (position == AdapterView.INVALID_POSITION) {
			return;
		}

		super.onListItemClick(v);

		final KPItem info = KP_nnnn.getInfo();
		final KPItem list = KP_nnnn.getList(KP_index);

		if (v.getId() == R.id.btn_del) {
			onContextItemSelected(getApp(), R.id.context_message_delete, info, list, false);
		} else {
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {


		// context_sing.xml 에서 메뉴 불러오기
		MenuInflater inflater = getBaseActivity().getMenuInflater();
		inflater.inflate(R.menu.context_message, menu);

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	/**
	 * 쪽지 차단/삭제/답장
	 */
	@Override
	public Intent onContextItemSelected(Context context, int id, final KPItem info, final KPItem list, boolean open) {

		String nm = getResourceEntryName(context, id);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + nm);

		Intent ret = super.onContextItemSelected(context, id, info, list, open);
		getBaseActivity().stopLoadingDialog(null);

		if (id == R.id.context_message_block) {
			// 쪽지차단
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_message_block), getString(R.string.btn_title_delete),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							KP_6043(list, "B");
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (id == R.id.context_message_reply) {
			// 쪽지답장
			if (list != null) {
				String url_message = list.getValue("url_message");
				openWebView(webview.class, getString(R.string.M1_MENU_MESSAGE), getString(R.string.M2_MESSAGE_LIST), getString(R.string.btn_title_message_send), url_message, "POST", false);
			}
		} else if (id == R.id.context_message_delete) {
			// 쪽지삭제
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_message_delete), getString(R.string.btn_title_delete),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							KP_6043(list, "D");
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else {
		}

		return ret;
	}

	/**
	 * KP_6043 쪽지삭제/차단<br>
	 * <br>
	 * 
	 */
	protected void KP_6043(KPItem list, String type) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

		if (list != null) {
			String id = "";
			id = list.getValue("id");
			if (!TextUtil.isEmpty(id) && !TextUtil.isEmpty(type)) {
				KP_6043.KP_6043(getApp().p_mid, p_m1, p_m2, play_id, id, type);
			}
		}

	}

}
