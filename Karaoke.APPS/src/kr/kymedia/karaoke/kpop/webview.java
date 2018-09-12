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
 * filename	:	eula.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ eula.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.PurchaseActivity;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._WebViewFragment;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 11.
 * @version 1.0
 */

public class webview extends PurchaseActivity {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onCreate(savedInstanceState);

		addFragment(R.id.fragment1, new _WebViewFragment(), "fragment1");
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onStart() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onStart();
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onStop() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onStop();
	}

}
