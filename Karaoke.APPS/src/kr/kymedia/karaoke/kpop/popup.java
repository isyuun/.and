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
 * filename	:	notice.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ notice.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.view.Menu;

import kr.kymedia.karaoke.apps._BaseActivity;
import kr.kymedia.karaoke.apps.R;

/**
 *
 * TODO NOTE:<br>
 *
 * @author isyoon
 * @since 2012. 4. 16.
 * @version 1.0
 */

class popup extends _BaseActivity {

	/**
	 * @see kr.kymedia.karaoke.apps.BaseActivity4#onCreate(Bundle)
	 * @see kr.kymedia.karaoke.apps.BaseActivity2#setActionMenuVisible(Menu)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		isUseDrawaerLaytout = false;
		super.onCreate(savedInstanceState);


		try {
			/**
			 * 이게먼...개지랄이야...구글...이븅신들아...
			 */
			getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		getSupportActionBar().hide();

		addFragment(R.id.fragment1, new _PopupFragment(), "fragment1");

		lockDrawerLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}
