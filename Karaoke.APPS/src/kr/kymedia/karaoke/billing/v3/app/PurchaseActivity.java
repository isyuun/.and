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
 * filename	:	PurchaseActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app.billing.v3
 *    |_ PurchaseActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing.v3.app;

import android.content.Intent;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.apps._BaseApplication;
import kr.kymedia.karaoke.billing.v3.IabHelper;

import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.apps.BaseAdActivity3;
import kr.kymedia.karaoke.apps._BaseFragment;

/**
 * <pre>
 * In - app - Billing.V3
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 11. 10.
 * @version 1.0
 */
public class PurchaseActivity extends BaseAdActivity3 {
	final protected String __CLASSNAME__ = "[Iab]" + (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + requestCode + "," + resultCode + "," + data);
		_BaseFragment fragment = getCurrentFragment();
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + fragment + "," + (fragment instanceof IabHelper.OnIabPurchaseFinishedListener) + "," + (fragment instanceof IabHelper.OnConsumeFinishedListener));

		super.onActivityResult(requestCode, resultCode, data);

		if (getApp() instanceof _BaseApplication) {
			getApp().onActivityResult(requestCode, resultCode, data);
		}
	}

}
