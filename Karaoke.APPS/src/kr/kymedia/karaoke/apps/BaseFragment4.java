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
 * project	:	Karaoke.KPOP.APP
 * filename	:	HomeFragment4.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ HomeFragment4.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

/**
 * <pre>
 * SwipeRefreshLayout
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 10. 17.
 * @version 1.0
 * @see This sample shows how to use SwipeRefreshLayout with manually creating (and attaching) a {@link SwipeRefreshLayout} to the view.
 */
class BaseFragment4 extends BaseFragment3 implements OnRefreshListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private SwipeRefreshLayout mSwipeRefreshLayout;

	public SwipeRefreshLayout getSwipeRefreshLayout() {
		return mSwipeRefreshLayout;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		if (mRootView instanceof SwipeRefreshLayout) {
			mSwipeRefreshLayout = (SwipeRefreshLayout) mRootView;
		} else {
			mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
		}

		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setProgressBackgroundColor(R.color.solid_orange);
			mSwipeRefreshLayout.setColorSchemeResources(R.color.solid_white_choco);
			mSwipeRefreshLayout.setOnRefreshListener(this);
		}
	}

	public void setRefreshComplete() {
		try {
			setRefreshing(false);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void setRefreshing(boolean refreshing) {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(refreshing);
		}
	}

	@Override
	public void onRefresh() {

		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());


		post(new Runnable() {

			@Override
			public void run() {

				setRefreshing(true);
				refresh();
			}
		});

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());


		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (what == _IKaraoke.STATE_DATA_QUERY_START) {
			return;
		}

		setRefreshComplete();
	}

	@Override
	protected void KPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + p_opcode + "[RESULT_CODE]" + r_code);


		super.KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (what == _IKaraoke.STATE_DATA_QUERY_START) {
			return;
		}

		setRefreshComplete();
	}

	// @Override
	// final protected void startLoading(String name, String method) {
	//
	// super.startLoading(name, method);
	// setRefreshing(true);
	// }
	//
	// @Override
	// final protected void stopLoading(String name, String method) {
	//
	// super.stopLoading(name, method);
	// setRefreshing(false);
	// }
	//
	// @Override
	// final protected void startLoadingDialog(String msg) {
	//
	// super.startLoadingDialog(msg);
	// setRefreshing(true);
	// }
	//
	// @Override
	// final protected void stopLoadingDialog(String msg) {
	//
	// super.stopLoadingDialog(msg);
	// setRefreshing(false);
	// }

}
