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
 * filename	:	FindLoginFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FindLoginFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 13.
 * @version 1.0
 */
@Deprecated
public class _FindLoginDialog extends _BaseFragment {

	_SettingFragment mSettingFragment;
	String nickname = "";
	String birthday = "";
	String email_id = "";

	public _FindLoginDialog() {
		super();
	}

	// 인스턴스생성은 여다한다...
	/**
	 * Create a new instance of BaseDialogFragment, providing "num" as an argument.
	 */
	public static _FindLoginDialog newInstance(int num) {
		_FindLoginDialog f = new _FindLoginDialog();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("num", num);
		f.setArguments(args);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_find_login, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		mSettingFragment = (_SettingFragment) getBaseActivity().getCurrentFragment();
	}

	DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String date_selected = String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear + 1)
					+ "." + String.valueOf(year);
			((TextView) findViewById(R.id.edt_login_birthday)).setText(date_selected);
		}

	};

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + vn + ", " + cn);


		if (v.getId() == R.id.edt_login_birthday || v.getId() == R.id.btn_login_birthday) {
			getBaseActivity().showDatePickerDialog(mDateSetListener, birthday);
		} else if (v.getId() == R.id.btn_login_findid) {
			findID();
		} else if (v.getId() == R.id.btn_login_findpwd) {
			findPWD();
		} else {
		}
		super.onClick(v);
	}

	public void findID() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		EditText et = null;

		nickname = ((EditText) findViewById(R.id.edt_login_nickname)).getText().toString();
		email_id = ((EditText) findViewById(R.id.edt_login_email)).getText().toString();
		birthday = ((TextView) findViewById(R.id.edt_login_birthday)).getText().toString();

		Bundle args = new Bundle();

		// // 이메일형식확인
		// if (TextUtil.isEmpty(email_id) || !TextUtil.checkParameter(LOGIN.KEY_EMAIL, email_id)) {
		// et = (EditText) findViewById(R.id.edt_login_email);
		// et.requestFocus();
		// getApp().showSoftInput(et);
		// // popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
		// args.putString("message", getString(R.string.summary_setting_email_check));
		// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
		// return;
		// }

		// 닉네임확인
		if (TextUtil.isEmpty(nickname)) {
			et = (EditText) findViewById(R.id.edt_login_nickname);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_nick_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_find_id_nick));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// 생년월일확인
		if (TextUtil.isEmpty(birthday) || !TextUtil.checkParameter("date", birthday)) {
			// et = (EditText) findViewById(R.id.edt_login_birthday);
			// et.requestFocus();
			// getApp().showSoftInput(et);
			getApp().popupToast(R.string.summary_setting_find_id_birthday,
					Toast.LENGTH_SHORT);
			// args.putString("message", getString(R.string.summary_setting_birthday_check));
			// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			getBaseActivity().showDatePickerDialog(mDateSetListener, birthday);
			return;
		}

		mSettingFragment.KP_5015(getString(R.string.M1_MENU_INFO), getString(R.string.M2_MY_ID_SEARCH),
				email_id, nickname, birthday);

	}

	public void findPWD() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		EditText et = null;

		nickname = ((EditText) findViewById(R.id.edt_login_nickname)).getText().toString();
		email_id = ((EditText) findViewById(R.id.edt_login_email)).getText().toString();
		birthday = ((TextView) findViewById(R.id.edt_login_birthday)).getText().toString();

		Bundle args = new Bundle();

		// 이메일형식확인
		if (TextUtil.isEmpty(email_id) || !TextUtil.checkParameter(LOGIN.KEY_EMAIL, email_id)) {
			et = (EditText) findViewById(R.id.edt_login_email);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_find_pwd_email));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// 닉네임확인
		if (TextUtil.isEmpty(nickname)) {
			et = (EditText) findViewById(R.id.edt_login_nickname);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_nick_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_find_pwd_nick));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// 생년월일확인
		if (TextUtil.isEmpty(birthday) || !TextUtil.checkParameter("date", birthday)) {
			// et = (EditText) findViewById(R.id.edt_login_birthday);
			// et.requestFocus();
			// getApp().showSoftInput(et);
			getApp().popupToast(R.string.summary_setting_find_pwd_birthday,
					Toast.LENGTH_SHORT);
			// args.putString("message", getString(R.string.summary_setting_birthday_check));
			// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			getBaseActivity().showDatePickerDialog(mDateSetListener, birthday);
			return;
		}

		mSettingFragment.KP_5015(getString(R.string.M1_MENU_INFO), getString(R.string.M2_MY_PW_SEARCH), email_id, nickname, birthday);

	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);

		KPItem info = KP_nnnn.getInfo();

		String email_id = "";
		View v = null;

		if (info != null) {
			email_id = info.getValue("id") == null ? "" : info.getValue("id");
			v = findViewById(R.id.edt_login_email);
			putValue(v, email_id);
		}

		super.KPnnnn();
	}
}
