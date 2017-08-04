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
 * 2014 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	BaseActivity5.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ BaseActivity5.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * <pre>
 * SwipeRefreshLayout
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 11. 28.
 * @version 1.0
 * @see This sample shows how to use SwipeRefreshLayout with manually creating (and attaching) a {@link SwipeRefreshLayout} to the view.
 */
class BaseActivity5 extends BaseActivity4X implements OnRefreshListener {

	private SwipeRefreshLayout mSwipeRefreshLayout;

	public SwipeRefreshLayout getSwipeRefreshLayout() {
		return mSwipeRefreshLayout;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void KP_menu_init() {

		super.KP_menu_init();

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.menu_swipe);
		if (mSwipeRefreshLayout != null)
		{
			mSwipeRefreshLayout.setProgressBackgroundColor(R.color.solid_orange);
			mSwipeRefreshLayout.setColorSchemeResources(R.color.solid_white_choco);
			mSwipeRefreshLayout.setOnRefreshListener(this);

			if (findViewById(R.id.refresh) != null) {
				// findViewById(R.id.refresh).setVisibility(View.GONE);
				findViewById(R.id.refresh).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						refresh();
					}
				});
			}
		}
	}

	@Override
	protected void KP_menu_update() {

		super.KP_menu_update();
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	@Override
	protected void KP_menu_clear(String name, String method) {

		super.KP_menu_clear(name, method);
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}

	@Override
	public void onRefresh() {

		post(new Runnable() {

			@Override
			public void run() {

				KP_menu_refresh(__CLASSNAME__, getMethodName());
			}
		});
	}
}
