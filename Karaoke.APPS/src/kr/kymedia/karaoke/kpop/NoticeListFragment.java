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
 * filename	:	NoticeListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ NoticeListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.view.View;
import android.widget.AdapterView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.NoticeListAdapter;
import kr.kymedia.karaoke.apps.R;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 16.
 * @version 1.0
 * @see NoticeListFragment.java
 */

public class NoticeListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	NoticeListAdapter mNoticeListAdapter = null;

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mNoticeListAdapter == null) {
			mNoticeListAdapter = new NoticeListAdapter(getBaseActivity(), R.layout.item_notice2,
					KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mNoticeListAdapter.setNotifyOnChange(false);
		setListAdapter(mNoticeListAdapter);
		// getListView().setDivider(null);
	}

	/**
	 * @see BaseListFragment#KP_list(int)
	 */
	@Override
	public void KP_list(int page) {
		KP_nnnn.KP_0010(getApp().p_mid, p_m1, p_m2);

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code,
			String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);


		super.onItemClick(parent, v, position, id);

		onContextItemSelected(R.id.context_notice, KP_nnnn, KP_index, true);

	}
}
