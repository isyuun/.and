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
 * filename	:	navitab2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ navitab2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.kpop._AuditionListFragment;
import kr.kymedia.karaoke.kpop._FeelListFragment;
import kr.kymedia.karaoke.kpop._ListenListFragment;
import kr.kymedia.karaoke.kpop._NaviListFragment;
import kr.kymedia.karaoke.kpop._NoticeFragment;
import kr.kymedia.karaoke.kpop._ShopListFragment;
import kr.kymedia.karaoke.kpop._SingListFragment;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 5. 29.
 * @version 1.0
 */

public class TabsPagerActivity2 extends TabsPagerActivity {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());


		super.onCreate(savedInstanceState);
	}

	@Override
	public void onPageSelected(int position) {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + position);

		super.onPageSelected(position);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @see BaseAdActivity#KP_init()
	 */
	@Override
	public void KP_init() {

		super.KP_init();
	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.KP_nnnn();

		// if (getCount() > 0) {
		// stopLoading(__CLASSNAME__, getMethodName());
		// return;
		// }

		getIntentData();

		KPItem list = getList();

		String mid = getApp().p_mid;
		String m1 = list.getValue("m1");
		String m2 = list.getValue("m2");
		String type = list.getValue("type");
		String uid = "";
		if (("UID").equalsIgnoreCase(type)) {
			uid = list.getValue("uid");
		}

		KP_nnnn.getLists().clear();
		if (!TextUtil.isEmpty(m1) && !TextUtil.isEmpty(m2)) {
			if (m1.indexOf("SING") > -1 || m2.indexOf("SING") > -1) {
				KP_nnnn.KP_1000(mid, getString(R.string.M1_MENU), m2, "", 1);
			} else {
				KP_nnnn.KP_1500(mid, m1, m2, uid, 1);
			}
		}

		KP_nnnn.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				addTabs();
			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			@Deprecated
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			@Deprecated
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});
	}

	@Override
	public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, String r_info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + opcode + ", " + r_code + ", " + r_message);


		// super.onKPnnnnQueryResult(what, opcode, r_code, r_message, r_info);
		try {
			KPnnnnResult(what, opcode, r_code, r_message, r_info);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void addTabs() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + (KP_nnnn));


		// 중복실행막기
		if (TextUtil.isEmpty(KP_nnnn.p_opcode)) {
			return;
		}

		clear();

		KP_nnnn.p_opcode = "";

		ArrayList<KPItem> lists = KP_nnnn.getLists();

		KPItem item = getList();
		String title = "";

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "item - " + item.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		if (item != null) {
			title = item.getValue("menu_name");
		}

		Class<?> cls = null;
		Bundle args = new Bundle();
		KPItem list = null;
		String tag = "";
		String name = "";

		for (int i = 0; i < lists.size(); i++) {
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__ + ".addTab()", "->START!!!");
			// if (_IKaraoke.APP_API_TEST && i > 0) {
			// continue;
			// }

			list = lists.get(i);

			if (list == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
				continue;
			}

			String type = list.getValue("type");
			String m1 = list.getValue("m1");
			String m2 = list.getValue("m2");
			String id = list.getValue("id");

			try {
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
			} catch (Exception e) {

				// e.printStackTrace();
			}

			// 가변메뉴처리
			if (("list").equalsIgnoreCase(type)) {
				cls = _SingListFragment.class;
				if (m1.equalsIgnoreCase(getString(R.string.M1_MENU_LISTEN)) || m2.equalsIgnoreCase(getString(R.string.M1_MENU_LISTEN))) {
					cls = _ListenListFragment.class;
				} else if (m1.equalsIgnoreCase(getString(R.string.M1_MENU_SING)) || m2.equalsIgnoreCase(getString(R.string.M1_MENU_SING))) {
					cls = _SingListFragment.class;
				} else if (m1.equalsIgnoreCase(getString(R.string.M1_MENU_AUDITION)) || m2.equalsIgnoreCase(getString(R.string.M1_MENU_AUDITION))) {
					cls = _AuditionListFragment.class;
				} else if (m1.equalsIgnoreCase(getString(R.string.M1_MENU_FEEL)) || m2.equalsIgnoreCase(getString(R.string.M1_MENU_FEEL))) {
					cls = _FeelListFragment.class;
				} else if (m1.equalsIgnoreCase(getString(R.string.M1_MENU_SHOP)) || m2.equalsIgnoreCase(getString(R.string.M2_MENU_SHOP))) {
					cls = _ShopListFragment.class;
				}
			} else if (type.equalsIgnoreCase("notice")) {
				// 공지사항
				list.putValue("id", id);
				cls = _NoticeFragment.class;
			} else {
				// 메뉴리스트
				cls = _NaviListFragment.class;
			}

			tag = list.getValue("m2");
			name = list.getValue("menu_name");

			// 상태바 타이틀을 통일한다.
			// 반드시 레벨전달 후에 할것.
			list.putValue("menu_name", title);

			// 번들을 전달한다.
			args = new Bundle();
			args.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);

			addTab(tag, name, cls, args);

			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__ + ".addTab()", "->END!!!");

		}

		notifyDataSetChanged();
	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.refresh();

		for (int i = 0; i < getCount(); i++) {
			if (i == getCurrentPosition() - 1 || i == getCurrentPosition() + 1) {
				_BaseFragment fragment = getAt(i);
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + fragment);
				if (fragment != null) {
					fragment.refresh();
				}
			}
		}

	}

	@Override
	protected void onNewIntent(Intent newIntent) {

		super.onNewIntent(newIntent);

		notifyDataSetChanged();
	}

}
