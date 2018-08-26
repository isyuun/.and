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
 * filename	:	NotificationActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ NotificationActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseActivity;
import kr.kymedia.karaoke.apps._BaseFragment;

/**
 *
 * <pre>
 * 푸시처리액티비티
 * 어플실행시에만생성한다.
 * </pre>
 *
 * @author isyoon
 * @since 2014. 11. 24.
 * @version 1.0
 * @see GCM2IntentService#handleGCMMessage(Context, Intent)
 */
public class GCM2Activity extends _BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		isUseDrawaerLaytout = false;
		super.onCreate(savedInstanceState);

		addFragment(R.id.fragment1, new _BaseFragment(), "fragment1");

		lockDrawerLayout();
	}

	@Override
	protected void onResume() {

		super.onResume();
		close();
	}

}
