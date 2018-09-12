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
 * filename	:	SettingFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ SettingFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.accounts.Account;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.util.BuildUtils;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.OnComplexTouchListener;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 2.
 * @version 1.0
 */

class SettingFragment extends _BaseFragment implements OnClickListener, OnTouchListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	SettingFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		setLogin();

		OnComplexTouchListener l;

		l = new OnComplexTouchListener();
		l.add(findViewById(R.id.chk_login_check2));
		l.add(findViewById(R.id.txt_login_nickname));
		l.setOnTouchListener(this);

		l = new OnComplexTouchListener();
		l.add(findViewById(R.id.chk_network_usemobile));
		l.add(findViewById(R.id.txt_network_usemobile));
		l.setOnTouchListener(this);

		l = new OnComplexTouchListener();
		l.add(findViewById(R.id.chk_player_sync_type));
		l.add(findViewById(R.id.txt_player_sync_type));
		l.setOnTouchListener(this);

		boolean sync_type = getApp().getSharedPreferences().getBoolean("sync_type", false);
		((CheckBox) findViewById(R.id.chk_player_sync_type)).setChecked(sync_type);

		((TextView) findViewById(R.id.txt_setting_app_name)).setText(getString(R.string.app_name));
		if (_IKaraoke.DEBUG) {
			findViewById(R.id.txt_setting_app_name).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.txt_setting_app_name).setVisibility(View.GONE);
		}

		post(new Runnable() {

			@Override
			public void run() {

				try {
					PackageInfo pkgInfo = BuildUtils.getPackageInfo(mContext);
					if (pkgInfo != null) {
						int versionCode = pkgInfo.versionCode;
						String versionName = pkgInfo.versionName;
						String version = getString(R.string.category_setting_version);
						if (_IKaraoke.DEBUG || _IKaraoke.DEBUG || BuildConfig.DEBUG) {
							version += ":" + "DEBUG";
						}
						// String installDate = DateUtils.getDate("yyyy/MM/dd hh:mm:ss", pkgInfo.lastUpdateTime);
						//String buildDate = BuildUtils.getDate("yyyy/MM/dd HH:mm", BuildUtils.getBuildDate(getApplicationContext()));
						String buildDate = (new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)).format(new Date(BuildConfig.TIMESTAMP/* + TimeZone.getTimeZone("Asia/Seoul").getRawOffset()*/));
						version += " : " + versionName;
						version += "(" + versionCode + "-" + _IKaraoke.P_VER + ")";
						version += " - " + buildDate;
						TextView tv = (TextView) findViewById(R.id.txt_setting_version);
						if (tv != null) {
							tv.setText(version);
							tv.setSingleLine();
							tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
							WidgetUtils.setTextViewMarquee(tv);
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

		if (findViewById(R.id.btn_notification_testings) != null) {
			if (_IKaraoke.DEBUG) {
				findViewById(R.id.btn_notification_testings).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.btn_notification_testings).setVisibility(View.GONE);
			}
		}
		findViewById(R.id.btn_notification_testings).setVisibility(View.GONE);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		String vn = v.getContext().getResources().getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + cn + "\n" + event);

		return super.onTouch(v, event);
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);


		KPItem info = KP_nnnn.getInfo();
		if (info == null) {
			info = new KPItem();
			KP_nnnn.setInfo(info);
		}

		getApp().login_check = ((CheckBox) findViewById(R.id.chk_login_check1)).isChecked();

		Editor edit = getApp().getSharedPreferences().edit();
		boolean checked = false;

		// KPOP UCC FAQ/QNA URL 정보 - URL 정보 : http://dev.mobileon.co.kr/kpop/ucc/board/list.php
		// - 파라미터
		// 1. "type" : "f"(FAQ), "q"(QNA)
		// 2. "p_ncode" : 국가코드
		// 3. LOGIN.KEY_EMAIL : 회원 이메일 계정
		String url = KP_nnnn.getHostPath() + "board/list.php?";
		String param = KP_nnnn.getParams();
		String str = "";

		if (v.getId() == R.id.chk_login_check1) {
			checked = ((CheckBox) v).isChecked();
			((CheckBox) findViewById(R.id.chk_login_check2)).setChecked(checked);
			edit.putBoolean(LOGIN.KEY_LOGIN_CHECK, checked);
			getApp().login_check = checked;
		} else if (v.getId() == R.id.txt_login_nickname) {
			// ((CheckBox) findViewById(R.id.chk_login_check2)).performClick();
			checked = !((CheckBox) findViewById(R.id.chk_login_check2)).isChecked();
			((CheckBox) findViewById(R.id.chk_login_check2)).setChecked(checked);
			((CheckBox) findViewById(R.id.chk_login_check1)).setChecked(checked);
			edit.putBoolean(LOGIN.KEY_LOGIN_CHECK, checked);
			getApp().login_check = checked;
		} else if (v.getId() == R.id.chk_login_check2) {
			checked = ((CheckBox) v).isChecked();
			((CheckBox) findViewById(R.id.chk_login_check1)).setChecked(checked);
			edit.putBoolean(LOGIN.KEY_LOGIN_CHECK, checked);
			getApp().login_check = checked;
		} else if (v.getId() == R.id.btn_login_register) {
			info.putValue("type", "register");
			startActivity(putIntentData(eula.class, null, KP_index));
		} else if (v.getId() == R.id.btn_login_change) {
			info.putValue("type", "change");
			startActivity(putIntentData(profile.class, null, KP_index));
		} else if (v.getId() == R.id.btn_login_findid) {
			showFindLogin();
		} else if (v.getId() == R.id.btn_login_findpwd) {
			showFindLogin();
		} else if (v.getId() == R.id.btn_login_pwd) {
			changePwd(true);
		} else if (v.getId() == R.id.btn_login_pwd_confirm) {
			sendChangPwd();
		} else if (v.getId() == R.id.btn_login_pwd_cancel) {
			changePwd(false);
		} else if (v.getId() == R.id.btn_login_delete) {
			// deleteUserId();
			getBaseActivity().showDialog2(DIALOG_ALERT_LOGIN_DELETE_YESNO, null);
		} else if (v.getId() == R.id.btn_history_clear) {
			getBaseActivity().clearQueryString();
		} else if (v.getId() == R.id.txt_network_usemobile) {
			checked = !((CheckBox) findViewById(R.id.chk_network_usemobile)).isChecked();
			((CheckBox) findViewById(R.id.chk_network_usemobile)).setChecked(checked);
			edit.putBoolean(LOGIN.KEY_NETWORK_USEMOBILE, checked);
			getApp().network_usemobile = checked;
		} else if (v.getId() == R.id.chk_network_usemobile) {
			checked = ((CheckBox) v).isChecked();
			edit.putBoolean(LOGIN.KEY_NETWORK_USEMOBILE, checked);
			getApp().network_usemobile = checked;
		} else if (v.getId() == R.id.txt_player_sync_type) {
			checked = !((CheckBox) findViewById(R.id.chk_player_sync_type)).isChecked();
			((CheckBox) findViewById(R.id.chk_player_sync_type)).setChecked(checked);
			edit.putBoolean("sync_type", checked);
		} else if (v.getId() == R.id.chk_player_sync_type) {
			checked = ((CheckBox) v).isChecked();
			edit.putBoolean("sync_type", checked);
		} else if (v.getId() == R.id.chk_pwd_check) {
			checked = ((CheckBox) v).isChecked();
			showPwd(checked);
		} else if (v.getId() == R.id.chk_pwd_check1) {
			checked = ((CheckBox) v).isChecked();
			showPwd1(checked);
		} else if (v.getId() == R.id.btn_customer_information) {
			// 공지사항
			onOptionsItemSelected(R.id.menu_notice, "", true);
		} else if (v.getId() == R.id.btn_customer_facebook) {
			openWebView(webview.class, "MENU_FACEBOOK", "MENU_FACEBOOK", getString(R.string.title_setting_customer_facebook), "http://www.facebook.com/pages/Kpop-Holic/234311606670461",
					"post", false);
		} else if (v.getId() == R.id.btn_customer_faq) {
			str = url + "type=f&" + param;
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO_FAQ), getString(R.string.title_setting_customer_faq), str, "post", false);
		} else if (v.getId() == R.id.btn_customer_qna) {
			str = url + "type=q&" + param;
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO_QNA), getString(R.string.title_setting_customer_faq), str, "post", false);
		} else if (v.getId() == R.id.txt_login_goodsname || v.getId() == R.id.txt_ticket_product_detail) {
			// onOptionsItemSelected(R.id.menu_ticket, true);
			onOptionsItemSelected(R.id.menu_shop_ticket, "", true);
		} else if (v.getId() == R.id.btn_notification_settings) {
			onOptionsItemSelected(R.id.menu_setting_notification, "", true);
		} else if (v.getId() == R.id.btn_notification_testings) {
			getApp().testPushGCM();
		} else if (v.getId() == R.id.txt_setting_version) {
			// 버전정보
			// url = KP_nnnn.getHostPath() + "protocol/web/KP_5002.php?";
			// param = KP_nnnn.getParams();
			// openWebView(webview.class, getString(R.string.M1_MENU_INFO),
			// getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_version), url
			// + param, "POST", false);
		}

		edit.commit();

		super.onClick(v);

	}

	public boolean showPwd(boolean checked) {
		EditText org_pwd = ((EditText) findViewById(R.id.edt_login_pwd));

		int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
		if (checked) {
			type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
		}

		org_pwd.setInputType(type);
		org_pwd.setSelection(org_pwd.getText().length());

		return checked;
	}

	public boolean showPwd1(boolean checked) {
		EditText new_pwd = ((EditText) findViewById(R.id.edt_login_pwd1));
		EditText con_pwd = ((EditText) findViewById(R.id.edt_login_pwd2));

		int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
		if (checked) {
			type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
		}

		new_pwd.setInputType(type);
		new_pwd.setSelection(new_pwd.getText().length());
		con_pwd.setInputType(type);
		con_pwd.setSelection(con_pwd.getText().length());

		return checked;
	}

	public void setLogin() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		try {
			// super.getLogin();


			((CheckBox) findViewById(R.id.chk_login_check1)).setChecked(getApp().login_check);
			((CheckBox) findViewById(R.id.chk_login_check2)).setChecked(getApp().login_check);
			((EditText) findViewById(R.id.edt_login_email)).setText(getApp().p_email);
			((EditText) findViewById(R.id.edt_login_pwd)).setText("");

			((TextView) findViewById(R.id.txt_login_nickname)).setText(getApp().p_nickname);

			if (!TextUtil.isEmpty(getApp().p_expire_date)) {
				// String text = getString(R.string.category_ticket_product) + getApp().p_expire_date;
				((TextView) findViewById(R.id.txt_login_goodsname)).setText(getApp().p_expire_date);
				WidgetUtils.setTextViewMarquee(((TextView) findViewById(R.id.txt_login_goodsname)));
			}

			if (isLoginUser()) {
				findViewById(R.id.include_setting_login).setVisibility(View.GONE);
				findViewById(R.id.include_setting_userinfo).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.include_setting_login).setVisibility(View.VISIBLE);
				Account accounts[] = EnvironmentUtils.getGoogleAccount(getBaseActivity());
				String email = "";
				if (accounts.length > 0) {
					email = accounts[0].name;
				}
				((EditText) findViewById(R.id.edt_login_email)).setText(email);
				findViewById(R.id.include_setting_userinfo).setVisibility(View.GONE);
			}
			findViewById(R.id.include_setting_pwdchange).setVisibility(View.GONE);

			((CheckBox) findViewById(R.id.chk_network_usemobile)).setChecked(getApp().network_usemobile);

			if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(getBaseActivity().getPackageName())) {
				((TextView) findViewById(R.id.txt_login_delete)).setText(getString(R.string.summary_setting_login_delete_onspot));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Deprecated
	public void sendLogIn() {
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName());
		//
		// delLogin();
		//
		// InputMethodManager imm = (InputMethodManager) getBaseActivity().getSystemService(
		// Context.INPUT_METHOD_SERVICE);
		// EditText et = null;
		//
		// getApp().p_email = ((EditText) findViewById(R.id.edt_login_email)).getText().toString();
		// getApp().p_pwd = ((EditText) findViewById(R.id.edt_login_pwd)).getText().toString();
		//
		// Bundle args = new Bundle();
		//
		// // 이메일형식확인
		// if (TextUtil.isEmpty(getApp().p_email) || !TextUtil.checkParameter(LOGIN.KEY_EMAIL, getApp().p_email)) {
		// et = (EditText) findViewById(R.id.edt_login_email);
		// et.requestFocus();
		// getApp().showSoftInput(et);
		// // popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
		// args.putString("message", getString(R.string.summary_setting_email_check));
		// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
		// return;
		// }
		//
		// // 패스워드확인
		// if (TextUtil.isEmpty(getApp().p_pwd) || !TextUtil.checkPwd(getApp().p_pwd)) {
		// et = (EditText) findViewById(R.id.edt_login_pwd);
		// et.requestFocus();
		// getApp().showSoftInput(et);
		// // popupToast(R.string.summary_setting_pwd_not_valid, Toast.LENGTH_SHORT);
		// args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
		// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
		// return;
		// }
		//
		// KP_nnnn.KP_9002(getApp().p_mid, getApp().p_email, getApp().p_pwd);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (("KP_9001").equalsIgnoreCase(p_opcode)) {
			// 로그인결과반영
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				getApp().popupToast(R.string.messge_setting_login_login, Toast.LENGTH_SHORT);
				putLogin(KP_nnnn.getInfo());
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
			}
		} else if (("KP_5013").equalsIgnoreCase(p_opcode)) {
			// 회원탈퇴결과반영
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				getApp().popupToast(r_message, Toast.LENGTH_SHORT);
				putLogout();
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
			}
		} else if (("KP_5014").equalsIgnoreCase(p_opcode)) {
			// 비밀번호변경결과반영
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				getApp().popupToast(r_message, Toast.LENGTH_SHORT);
				changePwd(false);
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
			}
		} else if (("KP_5015").equalsIgnoreCase(p_opcode)) {
			// 아이디/비밀번호찾기
			// 비밀번호초기화결과확인
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				// mFindLoginDialog.KPnnnn();
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
				if (r_code.equalsIgnoreCase("00020")) {
					Bundle args = new Bundle();
					args.putString("message", r_message);
					getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
				}
			}
		}
	}

	/**
	 * 로그인경과반영
	 */
	@Override
	public int putLogin(KPItem info) {
		int ret = super.putLogin(info);

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		getApp().login_check = ((CheckBox) findViewById(R.id.chk_login_check1)).isChecked();
		getApp().network_usemobile = ((CheckBox) findViewById(R.id.chk_network_usemobile)).isChecked();

		SharedPreferences.Editor edit = getApp().getSharedPreferences().edit();

		edit.putBoolean(LOGIN.KEY_LOGIN_CHECK, getApp().login_check);
		edit.putBoolean(LOGIN.KEY_NETWORK_USEMOBILE, getApp().network_usemobile);

		edit.commit();

		return ret;
	}

	public void changePwd(boolean enable) {
		if (enable) {
			findViewById(R.id.include_setting_pwdchange).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.include_setting_pwdchange).setVisibility(View.GONE);
		}
	}

	public void sendChangPwd() {
		String new_pwd = ((EditText) findViewById(R.id.edt_login_pwd1)).getText().toString();
		String con_pwd = ((EditText) findViewById(R.id.edt_login_pwd2)).getText().toString();

		EditText et = null;

		Bundle args = new Bundle();

		if (!new_pwd.equalsIgnoreCase(con_pwd)) {
			et = (EditText) findViewById(R.id.edt_login_pwd1);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_pwd_different, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_pwd_different));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// 패스워드확인
		if (TextUtil.isEmpty(new_pwd) || !TextUtil.checkPwd(new_pwd)) {
			et = (EditText) findViewById(R.id.edt_login_pwd1);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_pwd_not_valid, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// 패스워드확인
		if (TextUtil.isEmpty(con_pwd) || !TextUtil.checkPwd(con_pwd)) {
			et = (EditText) findViewById(R.id.edt_login_pwd2);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_pwd_not_valid, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		KP_5014(new_pwd, con_pwd);
	}

	int mStackLevel = 0;

	void showFindLogin() {
		mStackLevel++;

		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getBaseActivity().getSupportFragmentManager().beginTransaction();
		Fragment prev = getBaseActivity().getSupportFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		// mFindLoginDialog = _FindLoginDialog.newInstance(mStackLevel);
		// mFindLoginDialog.KP_nnnn = this.KP_nnnn;
		// mFindLoginDialog.show(ft, "dialog");
	}

	// 비밀번호변경
	public void KP_5014(String new_pwd, String con_pwd) {
		KP_nnnn.KP_5014(getApp().p_mid, p_m1, p_m2, getApp().p_email, new_pwd, con_pwd);

	}

	// 아이디/비밀번호 찾기
	public void KP_5015(String m1, String m2, String email, String nickname, String birthday) {
		// //startLoading(__CLASSNAME__, getMethodName());
		getBaseActivity().startLoadingDialog(null);

		KP_nnnn.KP_5015(getApp().p_mid, m1, m2, email, nickname, birthday);
	}

}
