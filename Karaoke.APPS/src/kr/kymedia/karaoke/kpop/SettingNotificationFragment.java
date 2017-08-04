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
 * filename	:	SettingNotificationFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ SettingNotificationFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.NotificationListAdapter;
import kr.kymedia.karaoke.apps.R;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 11. 15.
 * @version 1.0
 */

public class SettingNotificationFragment extends _BaseListFragment implements OnClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	NotificationListAdapter mNotificationListAdapter = null;

	Map<String, String> types = new HashMap<String, String>();

	/**
	 * KP_9003 사용자푸시정보등록
	 */
	// KPnnnn KP_9003 = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting_notification, container,
				false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mNotificationListAdapter == null) {
			mNotificationListAdapter = new NotificationListAdapter(getBaseActivity(),
					R.layout.item_notification, KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		setListAdapter(mNotificationListAdapter);

		if (mHeaderView.findViewById(R.id.view1) != null) {
			mHeaderView.findViewById(R.id.view1).setVisibility(View.GONE);
		}

		CheckBox chk_notificaiont_sound = (CheckBox) findViewById(R.id.chk_notification_sound);
		if (chk_notificaiont_sound != null) {
			chk_notificaiont_sound.setChecked(getApp().notification_sound);
		}
		CheckBox chk_notification_vibration = (CheckBox) findViewById(R.id.chk_notification_vibration);
		if (chk_notification_vibration != null) {
			chk_notification_vibration.setChecked(getApp().notification_vibration);
		}
	}

	@Override
	public void KP_init() {

		super.KP_init();

		// KP_9003 = KP_init(mContext, KP_9003);

	}

	/**
	 * @see BaseListFragment#KP_list(int)
	 */
	@Override
	public void KP_list(int page) {

		mTotalCount = 0;
		this.page = page;

		KP_nnnn.KP_9004(getApp().p_mid);

		super.KP_list(page);
	}

	/**
	 * @see BaseListFragment#onKPnnnnResult(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		if (p_opcode.equalsIgnoreCase("KP_9004")) {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				KP_9004();
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START) {
			}
		} else if (p_opcode.equalsIgnoreCase("KP_9003")) {

		}
	}

	void KP_9004() {
		ArrayList<KPItem> lists = KP_nnnn.getLists();

		if (lists == null) {
			return;
		}

		types.clear();
		for (int i = 0; i < lists.size(); i++) {
			KPItem list = lists.get(i);
			if (list != null) {
				String key = list.getValue("type");
				String value = list.getValue("is_on");
				types.put(key, value);
			}
		}

		String type = "";
		for (Map.Entry<String, ?> entry : types.entrySet()) {
			try {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, entry.getKey() + ": " + entry.getValue().toString());
				type += entry.getKey() + "|";
				type += entry.getValue().toString() + ":";
			} catch (Exception e) {

				// e.printStackTrace();
			}
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + " - " + type);

	}

	/**
	 * @see BaseListFragment#onItemClick(android.view.View)
	 */
	@Override
	public void onListItemClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onListItemClick(v);

		boolean checked = ((CheckBox) v).isChecked();

		int index = KP_index;
		ArrayList<KPItem> lists = KP_nnnn.getLists();
		KPItem list = lists.get(index);

		String key = list.getValue("type");
		String value = Boolean.toString(checked);

		types.put(key, value);

		KP_9003();
	}

	/**
	 * KP_9003 사용자푸시정보등록
	 */
	void KP_9003() {
		String type = "";
		for (Map.Entry<String, ?> entry : types.entrySet()) {
			try {
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, entry.getKey() + ": " + entry.getValue().toString());
				type += entry.getKey() + "|";
				type += entry.getValue().toString() + ":";
			} catch (Exception e) {

				// e.printStackTrace();
			}
		}

		if (getApp().KP_9003 != null) {
			getApp().KP_9003.KP_9003(getApp().p_mid, "", getApp().getGCM().REGID, "", "", type);
		}
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + " - " + type);
	}

	/**
	 * @see BaseListFragment#onPause()
	 */
	@Override
	public void onPause() {

		super.onPause();
		KP_9003();
	}

	@Override
	public void onClick(View v) {

		super.onClick(v);

		boolean checked = false;

		if (v.getId() == R.id.chk_notification_sound) {
			checked = ((CheckBox) v).isChecked();
			getApp().notification_sound = checked;
			putLogin(null);
		} else if (v.getId() == R.id.chk_notification_vibration) {
			checked = ((CheckBox) v).isChecked();
			getApp().notification_vibration = checked;
			putLogin(null);
		} else {
		}
	}

}
