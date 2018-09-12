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
 * filename	:	LoginFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ LoginFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;

/**
 * 
 * 로그인/회원등록<br>
 * 
 * @author isyoon
 * @since 2012. 7. 4.
 * @version 1.0
 * @version 1.1
 * 
 *          <pre>
 * 회원정보생성시 국가선택기능
 * </pre>
 */

class LoginFragment extends _BaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	String article_id = "1";
	String using_id = "2";

	public Spinner mProfileCountry = null;

	LoginFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
		//onCreateView();
		//return mRootView;
		return null;
	}

	protected void getGoogleAccount() {
		super.getGoogleAccount();

		if (null != getApp().KPparam && TextUtil.isEmpty(((EditText) findViewById(R.id.edt_login_email)).getText())) {
			((EditText) findViewById(R.id.edt_login_email)).setText(getApp().KPparam.getGmail());
		}
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		showRegisterButton(View.GONE);
		showLoginButton(View.VISIBLE);

		//if (!isLoginUser())
		{
			getGoogleAccount();
		}

		mProfileCountry = (Spinner) findViewById(R.id.spn_login_country);
		getApp().mFlagListAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		mProfileCountry.setAdapter(getApp().mFlagListAdapter);

		mProfileCountry.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {


			}
		});

		mProfileCountry.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				return false;
			}
		});

		String ncode = getApp().mLocale.getCountry().toUpperCase();
		String lcode = getApp().mLocale.getLanguage().toUpperCase();

		int position = getApp().mFlagListAdapter.getFlagsIndex(ncode);
		if (mProfileCountry != null) {
			mProfileCountry.setSelection(position);
		}

		int res = getResource("img_flag_" + ncode.toLowerCase(), "drawable");
		((_ImageView) findViewById(R.id.img_profile_country)).setImageResource(res);

		Locale locale = new Locale(lcode, ncode);
		String name = locale.getDisplayCountry();
		((TextView) findViewById(R.id.txt_login_country)).setText(name);
	}

	@Override
	protected void start() {

		super.start();
	}

	@Override
	public void refresh() {

		super.refresh();
		stopLoading(__CLASSNAME__, getMethodName());
	}

	@Override
	public void onResume() {

		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onResume();
		stopLoading(__CLASSNAME__, getMethodName());

		if (isLoginUser()) {
			setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			close();
		}
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.chk_pwd_check) {
			showPwd(((CheckBox) v).isChecked());
		} else if (v.getId() == R.id.btn_login_register) {
			getApp().hideSoftInput((EditText) findViewById(R.id.edt_login_pwd));
			getBaseActivity().startLoadingDialog(null);
			post(new Runnable() {

				@Override
				public void run() {

					sendRegister(false);
				}
			});
		} else if (v.getId() == R.id.btn_login_login) {
			getApp().hideSoftInput((EditText) findViewById(R.id.edt_login_pwd));
			// //startLoading(__CLASSNAME__, getMethodName());
			getBaseActivity().startLoadingDialog(null);
			post(new Runnable() {

				@Override
				public void run() {

					sendLogIn();
				}
			});
		} else if (v.getId() == R.id.btn_change_register) {
			showRegisterButton(View.VISIBLE);
			showLoginButton(View.GONE);
		} else if (v.getId() == R.id.btn_change_login) {
			showRegisterButton(View.GONE);
			showLoginButton(View.VISIBLE);
		} else if (v.getId() == R.id.txt_login_lost_pwd) {
			String url = KP_nnnn.getHostPath() + "search/sreq.php?" + KP_nnnn.getParams();
			openWebView(webview.class, getString(R.string.M1_LOGIN), getString(R.string.M1_LOGIN), getString(R.string.menu_login), url, "POST", false);
		} else {
		}

		super.onClick(v);

	}

	public void showLoginButton(int visibility) {
		View v = null;

		v = findViewById(R.id.layout_chane_login);
		if (v != null) {
			if (visibility == View.VISIBLE) {
				v.setVisibility(View.GONE);
			} else {
				v.setVisibility(View.VISIBLE);
			}
		}

		v = findViewById(R.id.btn_login_login);
		if (v != null) {
			v.setVisibility(visibility);
		}

		if (visibility == View.VISIBLE) {
			v = findViewById(R.id.edt_login_email);
			if (v != null) {
				v.requestFocus();
			}
		}

		v = findViewById(R.id.edt_login_pwd);
		if (v != null) {
			if (visibility == View.VISIBLE) {
				((EditText) v).setImeOptions(EditorInfo.IME_ACTION_DONE);
			}
		}

	}

	public void showRegisterButton(int visibility) {
		View v = null;

		v = findViewById(R.id.layout_chane_register);
		if (v != null) {
			if (visibility == View.VISIBLE) {
				v.setVisibility(View.GONE);
			} else {
				v.setVisibility(View.VISIBLE);
			}
		}

		v = findViewById(R.id.layout_login_nickname);
		if (v != null) {
			v.setVisibility(visibility);
		}

		v = findViewById(R.id.layout_login_country);
		if (v != null) {
			v.setVisibility(visibility);
		}

		v = findViewById(R.id.btn_login_register);
		if (v != null) {
			v.setVisibility(visibility);
		}

		v = findViewById(R.id.layout_login_recommend);
		if (getApp().is_recommend) {
			if (v != null) {
				v.setVisibility(visibility);
			}
		} else {
			v.setVisibility(View.GONE);
		}

		if (visibility == View.VISIBLE) {
			v = findViewById(R.id.edt_login_nickname);
			if (v != null) {
				v.requestFocus();
			}
		}

		v = findViewById(R.id.edt_login_pwd);
		if (v != null) {
			if (visibility == View.VISIBLE && getApp().is_recommend) {
				if (_IKaraoke.IS_ABOVE_HONEYCOMB) {
					((EditText) v).setImeOptions(EditorInfo.IME_FLAG_NAVIGATE_NEXT);
				} else {
					((EditText) v).setImeOptions(EditorInfo.IME_ACTION_NEXT);
				}
			} else {
				((EditText) v).setImeOptions(EditorInfo.IME_ACTION_DONE);
			}
		}

	}

	@Override
	public void onKPnnnnResult(final int what, final String p_opcode, final String r_code, final String r_message, final String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (("KP_9002").equalsIgnoreCase(p_opcode)) {
			// KP_9002 로그인결과반영
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				putLogin(KP_nnnn.getInfo());
				getApp().popupToast(r_message, Toast.LENGTH_SHORT);
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				// onOptionsItemSelected(R.id.menu_home, "", true);
				close();
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
				putLogout();
			}
		} else if (("KP_5020").equalsIgnoreCase(p_opcode)) {
			// KP_5020 회원가입
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				putLogin(KP_nnnn.getInfo());
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				// 회원등록/수정
				getApp().popupToast(r_message, Toast.LENGTH_SHORT);
				if (getApp().KP_9003 != null && getApp().getGCM() != null) {
					getApp().KP_9003.KP_9003(getApp().p_mid, "", getApp().getGCM().REGID, "", "", "");
				}
				// 신규가입시
				if (!p_type.equalsIgnoreCase("change")) {
					// popupToast(R.string.messge_setting_login_register, Toast.LENGTH_LONG);
					// onOptionsItemSelected(R.id.menu_profile);
					getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							// onOptionsItemSelected(R.id.menu_ticket, true);
							if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(mContext.getPackageName())) {
							} else {
								onOptionsItemSelected(R.id.menu_shop_ticket, "", true);
							}
							close();
						}
					}, null, null, true, null);
				} else {
					p_type = "change";
					// changeRegister();
					// //코멘트수정
					// KP_3012();
					//
					// //이미지수정
					// KP_1011();
					// onOptionsItemSelected(R.id.menu_home, "", true);
					close();
				}
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
				if (r_code.equalsIgnoreCase("00900")) {
					getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							getApp().popupToast(r_message, Toast.LENGTH_LONG);
							// Intent mailClient = new Intent(Intent.ACTION_VIEW);
							// mailClient.setClassName("com.google.android.gm",
							// "com.google.android.gm.ConversationListActivity");
							// startActivity(mailClient);
							Intent intent = mContext.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
							startActivity(intent);
							close();
						}

					}, null, null, true, null);
				}
			}
		} else if (("KP_5011").equalsIgnoreCase(p_opcode)) {
			// 이메일확인
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				if (p_type.equalsIgnoreCase("register")) {
					sendRegister(false);
				}
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
			}
		}
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

	public void sendLogIn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		getBaseActivity().startLoadingDialog(null);

		putLogout();

		EditText et = null;

		getApp().p_email = ((EditText) findViewById(R.id.edt_login_email)).getText().toString();
		getApp().p_pwd = ((EditText) findViewById(R.id.edt_login_pwd)).getText().toString();

		Bundle args = new Bundle();

		// 이메일형식확인
		if (TextUtil.isEmpty(getApp().p_email) || !TextUtil.checkParameter(LOGIN.KEY_EMAIL, getApp().p_email)) {
			et = (EditText) findViewById(R.id.edt_login_email);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_email_check));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return;
		}

		// 패스워드확인
		if (TextUtil.isEmpty(getApp().p_pwd) || !TextUtil.checkPwd(getApp().p_pwd)) {
			et = (EditText) findViewById(R.id.edt_login_pwd);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_pwd_not_valid, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return;
		}

		// pwd = TextUtil.getMD5Hash(pwd);
		KP_nnnn.KP_9002(getApp().p_mid, getApp().p_email, getApp().p_pwd);

		getApp().hideSoftInput((EditText) findViewById(R.id.edt_login_pwd));
	}

	public boolean checkRegister() {
		boolean ret = false;
		Bundle args = new Bundle();
		EditText et = null;

		// 닉네임확인
		if (TextUtil.isEmpty(getApp().p_nickname)) {
			et = (EditText) findViewById(R.id.edt_login_nickname);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_nick_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_nick_check));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return ret;
		}

		// 사용자(이메일)형식확인
		if (TextUtil.isEmpty(getApp().p_email) || !TextUtil.checkParameter(LOGIN.KEY_EMAIL, getApp().p_email)) {
			et = (EditText) findViewById(R.id.edt_login_email);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_email_check));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return ret;
		}

		// // 추천인(영숫자)형식확인
		// if (!TextUtil.isEmpty(recommend_id) && !TextUtil.checkParameter("eng_num", recommend_id)) {
		// et = (EditText) findViewById(R.id.edt_recommend_id);
		// et.requestFocus();
		// getApp().showSoftInput(et);
		// // popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
		// args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
		// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
		// stopLoading(__CLASSNAME__, getMethodName());
		// return ret;
		// }

		// 패스워드확인
		if (TextUtil.isEmpty(getApp().p_pwd) || !TextUtil.checkPwd(getApp().p_pwd)) {
			et = (EditText) findViewById(R.id.edt_login_pwd);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_pwd_not_valid, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			stopLoading(__CLASSNAME__, getMethodName());
			return ret;
		}

		ret = true;
		return ret;
	}

	public void sendRegister(boolean emailcheck) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + emailcheck);

		getBaseActivity().startLoadingDialog(null);

		putLogout();

		//getApp().p_cardno = ((EditText) findViewById(R.id.edt_login_cardno)).getText().toString().trim();
		getApp().p_nickname = ((EditText) findViewById(R.id.edt_login_nickname)).getText().toString().trim();
		getApp().p_email = ((EditText) findViewById(R.id.edt_login_email)).getText().toString().trim();
		getApp().p_pwd = ((EditText) findViewById(R.id.edt_login_pwd)).getText().toString().trim();
		String recommend_id = ((EditText) findViewById(R.id.edt_recommend_id)).getText().toString().trim();

		if (!checkRegister()) {
			return;
		}

		// startLoading(__CLASSNAME__, getMethodName());

		// if (((RadioGroup) findViewById(R.id.sex)).getCheckedRadioButtonId() == R.id.female) {
		// sex = "F";
		// } else {
		// sex = "M";
		// }
		getApp().p_sex = "F";

		// birthday = ((TextView) findViewById(R.id.edt_login_birthday)).getText().toString();
		getApp().p_birthday = (String) DateFormat.format("dd.MM.yyyy", new Date());

		KPItem info = getInfo();

		if (info != null && !TextUtil.isEmpty(info.getValue("article_id"))) {
			article_id = info.getValue("article_id");
		}

		if (info != null && !TextUtil.isEmpty(info.getValue("using_id"))) {
			using_id = info.getValue("using_id");
		}

		// 수정여부확인
		if (p_type.equalsIgnoreCase("change")) {
			emailcheck = false;
		}

		int index = mProfileCountry.getSelectedItemPosition();
		String ncode = getApp().mFlagListAdapter.getFlagsNcode(index);

		if (emailcheck) {
			KP_nnnn.KP_5011(getApp().p_mid, p_m1, p_m2, getApp().p_email);
		} else {
			KP_nnnn.KP_5020(getApp().p_mid, p_m1, p_m2, getApp().p_nickname, getApp().p_email, getApp().p_pwd, getApp().p_sex, getApp().p_birthday, ncode, article_id, using_id,
					recommend_id, getApp().p_cardno);
		}

		getApp().hideSoftInput((EditText) findViewById(R.id.edt_login_pwd));
	}

	@Override
	public boolean onBackPressed() {

		// if (getBaseActivity() instanceof BaseActivity5) {
		// ((BaseActivity5)getBaseActivity()).setRefresh(true);
		// }
		// return super.onBackPressed();
		// onOptionsItemSelected(R.id.menu_home, "", true);
		close();
		return false;
	}

}
