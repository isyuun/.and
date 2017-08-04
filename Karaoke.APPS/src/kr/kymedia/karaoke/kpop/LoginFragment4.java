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
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	LoginFragment4.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ LoginFragment4.java
 * </pre>
 */
package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;

/**
 * <pre>
 *   [한국인터넷진흥원] - 위치정보보호법 및 정보통신망법 개선필요 사항에 대한 안내
 *    개인정보 취급 방침 [읽음]
 *    개인정보 수집 이용 동의 [필수]
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-07-06
 */
class LoginFragment4 extends LoginFragment3 {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_login3, container, false);
		onCreateView();
		return mRootView;
	}

	/**
	 * <pre>
	 *   [한국인터넷진흥원] - 위치정보보호법 및 정보통신망법 개선필요 사항에 대한 안내
	 *    개인정보 취급 방침 [읽음]
	 *    <a href='http://kpop.kymedia.kr/ucc/protocol/web/KP_5004.php?market=G&p_ver=2.60&p_mid=MTMwMjIwTUw1WTFMMzAy&p_stbid=MWRmYjU5NzJjODRkNjc0NjM1NDE2NDA2Mjg5NTE2MA==&p_appname=kr.kymedia.karaoke.kpop.kar&p_appversionname=1.17&p_appversioncode=17&p_account=a2lta3NzNzEyMUBnbWFpbC5jb20=&p_phoneno=01056345416&p_ncode=KR&p_lcode=KO&model=LG-F460S&p_model=LG-F460S&p_manufacturer=LGE&p_device=tiger6&p_board=APQ8084&p_bootloader=unknown&p_brand=lge&p_mac=02:00:00:00:00:00&p_os=A&p_osversion=6.0&p_apiversion=23&p_passtype=N&p_passcnt=&p_opcode=&p_m1=&p_m2=&p_apikey=61G3M106HGRWMRPVS7O5'>개인정보 취급 방침 [읽음]</a>
	 *    개인정보 수집 이용 동의 [필수]
	 *    <a href='http://kpop.kymedia.kr/ucc/protocol/web/KP_5001.php?market=G&p_ver=2.60&p_mid=MTMwMjIwTUw1WTFMMzAy&p_stbid=MWRmYjU5NzJjODRkNjc0NjM1NDE2NDA2Mjg5NTE2MA==&p_appname=kr.kymedia.karaoke.kpop.kar&p_appversionname=1.17&p_appversioncode=17&p_account=a2lta3NzNzEyMUBnbWFpbC5jb20=&p_phoneno=01056345416&p_ncode=KR&p_lcode=KO&model=LG-F460S&p_model=LG-F460S&p_manufacturer=LGE&p_device=tiger6&p_board=APQ8084&p_bootloader=unknown&p_brand=lge&p_mac=02:00:00:00:00:00&p_os=A&p_osversion=6.0&p_apiversion=23&p_passtype=N&p_passcnt=&p_opcode=&p_m1=&p_m2=&p_apikey=61G3M106HGRWMRPVS7O5'>개인정보 수집 이용 동의 [필수]</a>
	 * </pre>
	 */
	@Override
	protected void onActivityCreated() {
		super.onActivityCreated();
		///**
		// *  개인정보 취급 방침 [읽음]
		// */
		//{
		//	//CheckBox c = (CheckBox) findViewById(R.id.chk_setting_eula3);
		//	TextView t = (TextView) findViewById(R.id.txt_setting_eula3);
		//	//SpannableString content = new SpannableString(t.getText());
		//	//content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		//	//t.setText(content);
		//	t.setOnClickListener(new View.OnClickListener() {
		//		@Override
		//		public void onClick(View v) {
		//			Log.d(_toString(), getMethodName() + v);
		//			LoginFragment4.this.onClick(v);
		//			///**
		//			// * 개인정보 취급 방침 [읽음]
		//			// */
		//			//String url = KP_nnnn.getHostPath() + "protocol/web/KP_5004.php?";
		//			//String param = KP_nnnn.getParams();
		//			//openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_eula3), url + param, "POST", false);
		//		}
		//	});
		//}
		///**
		// *  개인정보 수집 이용 동의 [필수]
		// */
		//{
		//	//CheckBox c = (CheckBox) findViewById(R.id.chk_setting_eula4);
		//	TextView t = (TextView) findViewById(R.id.txt_setting_eula4);
		//	//SpannableString content = new SpannableString(t.getText());
		//	//content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		//	//t.setText(content);
		//	t.setOnClickListener(new View.OnClickListener() {
		//		@Override
		//		public void onClick(View v) {
		//			Log.d(_toString(), getMethodName() + v);
		//			//LoginFragment4.this.onClick(v);
		//			/**
		//			 * 개인정보 수집 이용 동의 [필수]
		//			 */
		//			String url = KP_nnnn.getHostPath() + "protocol/web/KP_5001.php?";
		//			String param = KP_nnnn.getParams();
		//			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_eula4), url + param, "POST", false);
		//		}
		//	});
		//}
	}

	/**
	 * @see BaseFragment2#onClick(View)
	 */
	@Override
	public void onClick(View v) {
		Log.d(_toString(), getMethodName() + v);
		super.onClick(v);
	}

	@Override
	public void openWebView(Class<?> cls, String m1, String m2, String menu_name, String url, String method, boolean browser) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + cls);
		if (BuildConfig.DEBUG) Log.d(_toString(), "m1:" + m1 + ", m2:" + m2);
		if (BuildConfig.DEBUG) Log.d(_toString(), url);
		super.openWebView(cls, m1, m2, menu_name, url, method, browser);
	}

	/**
	 * @see LoginFragment#showLoginButton(int)
	 */
	@Override
	public void showLoginButton(int visibility) {
		super.showLoginButton(visibility);
		View v;

		//국가설정
		v = findViewById(R.id.layout_login_country);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		//추천인
		v = findViewById(R.id.layout_login_recommend);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

	}

	/**
	 * @see LoginFragment#showRegisterButton(int)
	 */
	@Override
	public void showRegisterButton(int visibility) {
		super.showRegisterButton(visibility);
		View v;

		//국가설정
		v = findViewById(R.id.layout_login_country);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		//추천인
		v = findViewById(R.id.layout_login_recommend);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		//개인정보
		v = findViewById(R.id.layout_eula);
		Log.wtf(_toString(), getMethodName() + v);
		if (v != null) {
			v.setVisibility(visibility);
		}
	}

	@Override
	public boolean checkRegister() {
		boolean ret = false;
		Bundle args = new Bundle();
		CheckBox c;

		/**
		 *  개인정보 취급 방침 [읽음]
		 */
		c = (CheckBox) findViewById(R.id.chk_setting_eula3);
		if (null != c && !c.isChecked()) {
			c.requestFocus();
			// popupToast(R.string.summary_setting_eula3, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_eula3));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return ret;
		}

		/**
		 *  개인정보 수집 이용 동의 [필수]
		 */
		c = (CheckBox) findViewById(R.id.chk_setting_eula4);
		if (null != c && !c.isChecked()) {
			c.requestFocus();
			// popupToast(R.string.summary_setting_eula4, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_eula4));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return ret;
		}

		ret = true;
		return (ret && super.checkRegister());
	}
}
