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
 * filename	:	NaviFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ NaviFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.NaviListAdapter;
import kr.kymedia.karaoke.apps.R;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 7.
 * @version 1.0
 * @see NaviListFragment.java
 */

public class NaviListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public static Fragment newInstance(Bundle extras) {

		return null;
	}

	NaviListAdapter mNaviListAdapter = null;

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mNaviListAdapter == null) {
			mNaviListAdapter = new NaviListAdapter(getBaseActivity(), R.layout.item_navi2,
					KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mNaviListAdapter.setNotifyOnChange(false);
		setListAdapter(mNaviListAdapter);
		// getListView().setDivider(null);

	}

	@Override
	public void KP_list(int page) {
		KP_nnnn.KP_1000(getApp().p_mid, p_m1, p_m2, "", page);

		super.KP_list(page);
	}

	@Override
	public void KPlist() {

		KPlist(KP_nnnn.getInfo(), KP_nnnn.getLists());
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

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);


		super.onItemClick(parent, v, position, id);

		try {
			Class<?> cls = navilist.class;
			KPItem list = KP_nnnn.getList(KP_index);

			try {
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
			} catch (Exception e) {

				// e.printStackTrace();
			}

			p_type = list.getValue("type");
			String m1 = list.getValue("m1");

			if (p_type != null && p_type.equalsIgnoreCase("list")) {
				if (m1.indexOf("SING") > -1) {
					cls = singlist.class;
				} else if (m1.indexOf("LISTEN") > -1) {
					cls = listenlist.class;
				} else {
					cls = singlist.class;
				}
			}

			startActivity(putIntentData(cls, null, KP_index));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
