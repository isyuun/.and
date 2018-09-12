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
 * filename	:	listentab2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ listentab2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Intent;
import android.os.Bundle;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._TabsPagerActivity;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 5. 24.
 * @version 1.0
 */
public class myholictab extends _TabsPagerActivity {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		// super.KP_nnnn();

		post(new Runnable() {

			@Override
			public void run() {

				addTabs();
			}
		});
	}

	@Override
	public void addTabs() {
		if (getCount() > 0) {
			return;
		}

		String menu_name = "";

		// 녹음곡목록
		menu_name = getString(R.string.context_record);
		addTab(getString(R.string.M1_MENU_MYHOLIC), getString(R.string.M2_MY_REC),
				getString(R.string.menu_mylisten), "UID", menu_name, _ListenListFragment.class);

		// FEEL목록
		menu_name = getString(R.string.context_feel);
		addTab(getString(R.string.M1_MENU_FEEL), getString(R.string.M2_FEEL_LIST),
				getString(R.string.menu_feel_list), "OT", menu_name, _FeelListFragment.class);

		// 팔로워목록
		menu_name = getString(R.string.context_follower);
		addTab(getString(R.string.M1_MENU_FOLLOW), getString(R.string.M2_FOLLOWER_LIST),
				getString(R.string.menu_follow_list), "FOLLOWER", menu_name, _UserListFragment.class);

		// 팔로잉목록
		menu_name = getString(R.string.context_following);
		addTab(getString(R.string.M1_MENU_FOLLOW), getString(R.string.M2_FOLLOWING_LIST),
				getString(R.string.menu_follow_list), "FOLLOWING", menu_name, _UserListFragment.class);

		// 방명록목록
		menu_name = getString(R.string.context_guestbook);
		addTab(getString(R.string.M1_MENU_VISITOR), getString(R.string.M2_VISITOR_LIST),
				getString(R.string.menu_visitor_list), "UID", menu_name, _ReplyListFragment.class);

		// 쪽지목록
		menu_name = getString(R.string.context_message);
		addTab(getString(R.string.M1_MENU_MESSAGE), getString(R.string.M2_MESSAGE_LIST),
				getString(R.string.menu_message_list), "UID", menu_name, _MessageListFragment.class);

		// 애창곡
		menu_name = getString(R.string.context_mysing);
		addTab(getString(R.string.M1_MENU_SING), getString(R.string.M2_SING_MYSING),
				getString(R.string.menu_message_list), "UID", menu_name, _SingListFragment.class);

		notifyDataSetChanged();

	}

	void addTab(String m1, String m2, String tag, String type, String name, Class<?> cls) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + " - m1:" + m1 + ", m2:" + m2 + ", tag:" + tag);

		Bundle args = new Bundle();

		KPItem info = new KPItem();
		KPItem list = new KPItem();

		KPItem item = null;
		String uid = "";

		try {
			item = getList();
			uid = item.getValue("uid");
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "item - " + item.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		// 사용자별탭차단 - 쪽지함이없어짐
		if (!TextUtil.isEmpty(uid) && !getApp().p_mid.equalsIgnoreCase(uid)) {
			// 쪽지함차단
			if (getString(R.string.M1_MENU_MESSAGE).equalsIgnoreCase(m1)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + " - m1:" + m1 + ", m2:" + m2 + ", tag:" + tag);
				return;
			}
		}

		list.putValue("uid", uid);
		info.putValue("uid", uid);

		list.putValue("type", type);
		info.putValue("type", type);

		list.putValue("menu_name", name);
		list.putValue("m1", m1);
		list.putValue("m2", m2);

		args.putParcelable(_IKaraoke.KEY_KPOP_INFOITEM, info);
		args.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);

		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

		addTab(tag, name, cls, args);
	}

	/**
	 */
	@Override
	protected void onNewIntent(Intent newIntent) {

		super.onNewIntent(newIntent);

		refresh();
	}

}
