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
 * filename	:	singlist.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ singlist.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.kpop._SingListFragment;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 */

class SearchResultActivity extends _BaseAdActivity {

	@Override
	public void setActionMenuVisible(Menu menu) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		try {
			super.setActionMenuVisible(menu);

			setActionMenuItemVisible(R.id.menu_comment, false);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		isUseDrawaerLaytout = false;
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		saveQueryString(intent);

		Bundle extras = null;

		if (intent != null) {
			extras = intent.getBundleExtra(SearchManager.APP_DATA);
		}

		if (extras == null) {
			return;
		}

		// FragmentManager fragmentManager = getSupportFragmentManager();
		// FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		//
		// String type = extras.getString("type");
		// String m1 = extras.getString("m1");
		// String m2 = extras.getString("m2");
		//
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + type + ", " + m1 + ", " + m2);
		//
		// if (p_m1.indexOf("LISTEN") > -1 || p_m2.indexOf("LISTEN") > -1) {
		// // 녹음곡검색처리
		// //You can then add a fragment using the add() method, specifying the fragment to add and the view in which to insert it. For example:
		// ListenListFragment fragment = new ListenListFragment();
		// //fragmentTransaction.add(fragment, "fragment1");
		// fragmentTransaction.add(R.id.fragment1, fragment, "fragment1");
		// } else {
		// // 반주곡검색처리
		// //You can then add a fragment using the add() method, specifying the fragment to add and the view in which to insert it. For example:
		// SingListFragment fragment = new SingListFragment();
		// //fragmentTransaction.add(fragment, "fragment1");
		// fragmentTransaction.add(R.id.fragment1, fragment, "fragment1");
		// }

		// 반주곡검색처리
		// You can then add a fragment using the add() method, specifying the fragment to add and the view in which to insert it. For example:
		_SingListFragment fragment = new _SingListFragment();
		addFragment(R.id.fragment1, fragment, "fragment1");

	}

	@Override
	protected void onNewIntent(Intent intent) {
		String oldQuery = "";
		String newQuery = "";

		if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
			oldQuery = getIntent().getStringExtra(SearchManager.QUERY);
			newQuery = intent.getStringExtra(SearchManager.QUERY);

			if (oldQuery.equalsIgnoreCase(newQuery)) {
				return;
			}
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + oldQuery + "->" + newQuery);

		saveQueryString(intent);


		super.onNewIntent(intent);

	}
}
