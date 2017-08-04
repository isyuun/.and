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

package kr.kymedia.karaoke.kpop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.PlayActivity;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseFragment;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 12.
 * @version 1.0
 * @see singlist.java
 */

public class play extends PlayActivity {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		isUseDrawaerLaytout = false;

		super.onCreate(savedInstanceState);

		lockDrawerLayout();

	}

	@Override
	public void setActionMenuVisible(Menu menu) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		try {
			super.setActionMenuVisible(menu);

			setActionMenuItemVisible(R.id.menu_search, false);
			setActionMenuItemVisible(R.id.menu_refresh, true);

			String m1 = getList().getValue("m1");
			String m2 = getList().getValue("m2");

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "m1:" + m1 + " m2:" + m2);

			if (m1.indexOf("SING") > -1 || m2.indexOf("SING") > -1) {
				setActionMenuItemVisible(R.id.menu_comment, false);
			} else if (m1.indexOf("LISTEN") > -1 || m2.indexOf("LISTEN") > -1) {
				setActionMenuItemVisible(R.id.menu_comment, getApp().isLoginUser());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.KP_nnnn();
		if (KP_nnnn == null) {
			return;
		}


		try {
			getIntentData();

			String type = getList().getValue("type");
			String m1 = getList().getValue("m1");
			String m2 = getList().getValue("m2");

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + type + ", " + m1 + ", " + m2);

			if (getCurrentFragment() == null) {
				_BaseFragment fragment = null;
				if (m1.indexOf("SING") > -1 || m2.indexOf("SING") > -1) {
					// 반주곡재생
					// You can then add a fragment using the add() method,
					// specifying the fragment to add and the view in which to insert it. For example:
					fragment = new _playSingFragment();
					setTitle(R.string.menu_sing);
				} else if (m1.indexOf("LISTEN") > -1 || m2.indexOf("LISTEN") > -1) {
					// 녹음곡재생
					// You can then add a fragment using the add() method,
					// specifying the fragment to add and the view in which to insert it. For example:
					fragment = new _playListenFragment();
					setTitle(R.string.menu_listen);
				}

				replaceFragment(R.id.fragment1, fragment, "fragment1");

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void onNewIntent(Intent newIntent) {
		super.onNewIntent(newIntent);

		// try {
		// Bundle extras1 = getIntent().getExtras();
		// Bundle extras2 = newIntent.getExtras();
		// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, getMethodName() + "\n" + extras1);
		// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, getMethodName() + "\n" + extras2);
		//
		//
		//
		// if (extras1 == null || (extras2 != null && !extras1.equals(extras2))) {
		// KP_nnnn();
		// }
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
	}

}
