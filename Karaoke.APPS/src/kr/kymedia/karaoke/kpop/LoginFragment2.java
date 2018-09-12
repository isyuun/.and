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
 * filename	:	LoginFragment2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ LoginFragment2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * TODO<br>
 * 
 * <pre>
 * 온스탑금영노래방로그인
 * </pre>
 *
 * @author isyoon
 * @since 2014. 6. 11.
 * @version 1.0
 */
class LoginFragment2 extends LoginFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_login2, container, false);
		//onCreateView();
		//return mRootView;
		return null;
	}

	//@Override
	//public void showLoginButton(int visibility) {
	//	super.showLoginButton(visibility);
	//	//if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(mContext.getPackageName())) {
	//	//	View v = findViewById(R.id.layout_login_cardno);
	//	//	if (v != null) {
	//	//	} else {
	//	//		v.setVisibility(View.GONE);
	//	//	}
	//	//}
	//}

	//@Override
	//public void showRegisterButton(int visibility) {
	//	super.showRegisterButton(visibility);
	//	//if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(mContext.getPackageName())) {
	//	//	View v = findViewById(R.id.layout_login_cardno);
	//	//	if (v != null) {
	//	//		v.setVisibility(visibility);
	//	//		if (visibility == View.VISIBLE) {
	//	//			v.requestFocus();
	//	//		}
	//	//	} else {
	//	//		v.setVisibility(View.GONE);
	//	//	}
	//	//}
	//}

	//@Override
	//public boolean checkRegister() {
	//	//boolean ret = false;
	//	//Bundle args = new Bundle();
	//	//EditText et = null;
	//	//
	//	//if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(mContext.getPackageName())) {
	//	//	// 카드번호확인
	//	//	if (TextUtil.isEmpty(getApp().p_cardno) || !TextUtils.isDigitsOnly(getApp().p_cardno) || getApp().p_cardno.length() != 8) {
	//	//		et = (EditText) findViewById(R.id.edt_login_cardno);
	//	//		et.requestFocus();
	//	//		getApp().showSoftInput(et);
	//	//		// popupToast(R.string.summary_setting_cardno_check, Toast.LENGTH_SHORT);
	//	//		args.putString("message", getString(R.string.summary_setting_cardno_check));
	//	//		getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
	//	//		stopLoading(__CLASSNAME__, getMethodName());
	//	//		return ret;
	//	//	}
	//	//}
	//
	//	return super.checkRegister();
	//}

}
