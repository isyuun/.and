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
 * filename	:	setting.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ setting.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseActivity;
import android.os.Bundle;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 2.
 * @version 1.0
 */

public class setting extends _BaseActivity {

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		isUseDrawaerLaytout = false;
		super.onCreate(savedInstanceState);

		addFragment(R.id.fragment1, new _SettingFragment(), "fragment1");

		lockDrawerLayout();
	}

	@Override
	protected void onResume() {

		super.onResume();

		stopLoading(__CLASSNAME__, getMethodName());
	}

}
