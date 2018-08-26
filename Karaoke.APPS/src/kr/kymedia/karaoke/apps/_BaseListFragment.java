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
 * 2014 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	_BaseListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ _BaseListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.widget.AbsListView;

import kr.kymedia.karaoke.apps.adpt._BaseListAdapter;

/**
 *
 * <pre></pre>
 *
 * @author isyoon
 * @since 2014. 4. 28.
 * @version 1.0
 */
public class _BaseListFragment extends BaseListFragment2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public _BaseListFragment() {
		super();
		__CLASSNAME__ = this.getClass().getSimpleName();

	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();
	}

	@Override
	public void KP_list(int page) {

		super.KP_list(page);
	}

	@Override
	public void refresh() {

		super.refresh();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();

	}

	@Override
	public void release() {

		try {
			super.release();

			if (getListView() != null) {
				getListView().setOnScrollListener(null);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void setListAdapter(_BaseListAdapter listAdapter) {

		super.setListAdapter(listAdapter);

		// if (getImageDownLoader() instanceof ImageDownLoader3) {
		// boolean pauseOnScroll = false; // or true
		// boolean pauseOnFling = true; // or false
		// PauseOnScrollListener listener = new PauseOnScrollListener(
		// ((ImageDownLoader3) getImageDownLoader()).getImageLoader(), pauseOnScroll, pauseOnFling,
		// this);
		// if (getListView() != null) {
		// getListView().setOnScrollListener(listener);
		// }
		// }

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {

		super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

	}

}
