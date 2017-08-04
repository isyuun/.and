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
 * filename	:	HomeFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ HomeFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO NOTE:<br>
 * 가변메뉴로변경
 * 
 * @author isyoon
 * @since 2012. 2. 28.
 * @version 1.0
 */
class HomeFragment extends _BaseFragment implements OnClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	HomeFragment() {
		super();
	}

	@Override
	protected void onActivityCreated() {
		p_m1 = getString(R.string.M1_MAIN);
		p_m2 = getString(R.string.M2_MENU);


		super.onActivityCreated();

	}

	@Override
	protected void start() {

		super.start();

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.KP_nnnn();

		// KP_nnnn.KP_0002(getApp().p_mid, p_m1, p_m2, getApp().p_email);
		KP_nnnn.KP_0003(getApp().p_mid, p_m1, p_m2, getApp().p_email);
	}

	@Override
	public void refresh() {

		super.refresh();
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message,
			String r_info) {


		KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (what != _IKaraoke.STATE_DATA_QUERY_START) {
			// KP_0002 동적메인메뉴 요청
			if (("KP_0002").equalsIgnoreCase(p_opcode)) {

				KPItem info = KP_nnnn.getInfo();

				putLogin(info);

				if (info == null) {
					info = new KPItem();
					KP_nnnn.setInfo(info);
				}

				KPnnnn();

				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				}
			}
		}

	}

	@Override
	public void onClick(View v) {

		String vn = getResourceEntryName(v.getId());
		KP_index = getMenuTilesIndex(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + KP_index + " " + vn + ", " + cn);

		if (getBaseActivity().isLoading()) {
			return;
		}

		KPItem info = KP_nnnn.getInfo();
		KPItem list = KP_nnnn.getList(KP_index);
		ArrayList<KPItem> lists = KP_nnnn.getLists();

		if (info == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "info", info);
			return;
		}

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		// if (lists == null) {
		// getApp().showDataError(__CLASSNAME__, getMethodName(), "lists", lists);
		// return;
		// }

		String menu_id = list.getValue("menu_id");
		String menu_name = list.getValue("menu_name");

		if (("true").equalsIgnoreCase(list.getValue("refresh"))) {
			getApp().isRefresh = true;
		} else {
			getApp().isRefresh = false;
		}

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		if (!TextUtil.isEmpty(menu_id)) {
			if (menu_id.contains("menu_")) {
				onOptionsItemSelected(getResource(menu_id, "id"), menu_name, true);
			} else if (menu_id.contains("context_")) {
				onContextItemSelected(getResource(menu_id, "id"), KP_nnnn, KP_index, true);
			}
		}

		super.onClick(v);
	}

	public int getMenuTilesIndex(int id) {
		return 0;
	}

	@Override
	public Intent onOptionsItemSelected(int id, String menu_name, boolean open) {


		if (id == android.R.id.home || id == R.id.menu_home) {
			return null;
		}

		return super.onOptionsItemSelected(id, menu_name, open);
	}

}
