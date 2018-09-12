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
 * filename	:	RegisterFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ RegisterFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.accounts.Account;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BaseImageFragment;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;

/**
 * 
 * 프로필편집<br>
 * 
 * @author isyoon
 * @since 2012. 3. 6.
 * @version 1.0
 * @version 1.1
 * 
 *          <pre>
 * 회원정보생성시 국가선택기능
 * </pre>
 */

class ProfileFragment extends BaseImageFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	String article_id = "";
	String using_id = "";

	String uid = "";

	public _ImageView mProfileImgView = null;
	public EditText mProfileComment = null;

	public Spinner mProfileCountry = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		mProfileComment = (EditText) findViewById(R.id.edt_profile_comment);

		mProfileImgView = (_ImageView) findViewById(R.id.img_profile_image);
		setImageView(mProfileImgView);

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
	}

	@Override
	public void onResume() {

		super.onResume();
		// 수정여부확인
		KPItem info = getInfo();
		if (info != null && !TextUtil.isEmpty(info.getValue("type"))) {
			p_type = info.getValue("type");
		}
		p_type = "change";
		getLogin();
		changeRegister();
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);


		try {
			getApp().p_email = ((EditText) findViewById(R.id.edt_login_email)).getText().toString();
			getApp().p_pwd = ((EditText) findViewById(R.id.edt_login_pwd)).getText().toString();
			getApp().p_birthday = ((TextView) findViewById(R.id.edt_login_birthday)).getText().toString();

			boolean checked = false;

			if (v.getId() == R.id.btn_login_register) {
				sendRegister(false);
			} else if (v.getId() == R.id.edt_login_birthday || v.getId() == R.id.btn_login_birthday) {
				getBaseActivity().showDatePickerDialog(mDateSetListener, getApp().p_birthday);
			} else if (v.getId() == R.id.btn_email_check) {
			} else if (v.getId() == R.id.btn_pwd_check) {
			} else if (v.getId() == R.id.chk_pwd_check) {
				checked = ((CheckBox) v).isChecked();
				showPwd(checked);
			} else if (v.getId() == R.id.btn_image_pick) {
				openImagePicker();
			} else if (v.getId() == R.id.btn_image_camera) {
				openCameraPicker();
			} else if (v.getId() == R.id.btn_image_crop) {
				openImageCroper();
			} else if (v.getId() == R.id.img_profile_image) {
				String url = getImagePath();
				openWebView(webview.class, getString(R.string.M1_MAIN), getString(R.string.M2_MENU_MYINFO), getString(R.string.category_setting_profile), url, "IMAGE", false);
			} else {
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

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

	DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String date_selected = String.valueOf(dayOfMonth) + "." + String.valueOf(monthOfYear + 1) + "." + String.valueOf(year);
			((TextView) findViewById(R.id.edt_login_birthday)).setText(date_selected);
		}

	};

	@Override
	public boolean getLogin() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		boolean ret = true;

		try {
			ret = super.getLogin();

			((EditText) findViewById(R.id.edt_login_nickname)).setText(getApp().p_nickname);
			((EditText) findViewById(R.id.edt_login_email)).setText(getApp().p_email);
			// ((EditText) findViewById(R.id.edt_login_pwd)).setText("");

			if (getApp().p_sex.equalsIgnoreCase("F")) {
				((RadioGroup) findViewById(R.id.sex)).check(R.id.female);
			} else {
				((RadioGroup) findViewById(R.id.sex)).check(R.id.male);
			}
			((TextView) findViewById(R.id.edt_login_birthday)).setText(getApp().p_birthday);

			// int position = getApp().mFlagListAdapter.getFlagsIndex(mPhoneInfo.getNcode());
			// if (mProfileCountry != null) {
			// mProfileCountry.setSelection(position);
			// }
			//
			// int res = getResource("img_flag_" + mPhoneInfo.getNcode().toLowerCase(), "drawable");
			// ((ImageView) mRootView.findViewById(R.id.img_profile_country)).setImageResource(res);
			//
			// Locale locale = new Locale(mPhoneInfo.getLcode(), mPhoneInfo.getNcode());
			// String name = locale.getDisplayCountry();
			// ((TextView) mRootView.findViewById(R.id.txt_login_country)).setText(name);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return ret;
	}

	public void changeRegister() {
		String email = "";

		if (p_type.equalsIgnoreCase("change")) {
			// 회원정보수정시
			((Button) findViewById(R.id.btn_login_register)).setText(R.string.title_setting_login_change);
		} else {
			// 회원신규가입시
			((Button) findViewById(R.id.btn_login_register)).setText(R.string.title_setting_login_register);
			Account accounts[] = EnvironmentUtils.getGoogleAccount(getBaseActivity());
			if (accounts.length > 0) {
				email = accounts[0].name;
			}
			EditText et = (EditText) findViewById(R.id.edt_login_email);
			et.setText(email);
			email = et.getText().toString();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "changeRegister()->email:" + email + ", " + p_type);
	}

	public void sendRegister(boolean emailcheck) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + emailcheck);

		EditText et = null;

		getApp().p_nickname = ((EditText) findViewById(R.id.edt_login_nickname)).getText().toString().trim();
		getApp().p_email = ((EditText) findViewById(R.id.edt_login_email)).getText().toString().trim();
		getApp().p_pwd = ((EditText) findViewById(R.id.edt_login_pwd)).getText().toString().trim();
		getApp().p_birthday = ((TextView) findViewById(R.id.edt_login_birthday)).getText().toString().trim();

		Bundle args = new Bundle();

		// 이메일형식확인
		if (TextUtil.isEmpty(getApp().p_email) || !TextUtil.checkParameter(LOGIN.KEY_EMAIL, getApp().p_email)) {
			et = (EditText) findViewById(R.id.edt_login_email);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_email_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_email_check));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// // 패스워드확인
		// if (TextUtil.isEmpty(pwd) || !TextUtil.checkPwd(pwd)) {
		// et = (EditText) findViewById(R.id.edt_login_pwd);
		// et.requestFocus();
		// getApp().showSoftInput(et);
		// // popupToast(R.string.summary_setting_pwd_not_valid, Toast.LENGTH_SHORT);
		// args.putString("message", getString(R.string.summary_setting_pwd_not_valid));
		// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
		//
		// return;
		// }

		// 닉네임확인
		if (TextUtil.isEmpty(getApp().p_nickname)) {
			et = (EditText) findViewById(R.id.edt_login_nickname);
			et.requestFocus();
			getApp().showSoftInput(et);
			// popupToast(R.string.summary_setting_nick_check, Toast.LENGTH_SHORT);
			args.putString("message", getString(R.string.summary_setting_nick_check));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return;
		}

		// 생년월일확인
		if (TextUtil.isEmpty(getApp().p_birthday) || !TextUtil.checkParameter("date", getApp().p_birthday)) {
			// et = (EditText) findViewById(R.id.edt_login_birthday);
			// et.requestFocus();
			// getApp().showSoftInput(et);
			getApp().popupToast(R.string.summary_setting_birthday_check, Toast.LENGTH_SHORT);
			// args.putString("message",
			// getString(R.string.summary_setting_birthday_check));
			// getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			getBaseActivity().showDatePickerDialog(mDateSetListener, getApp().p_birthday);
			return;
		}

		delLoginProfile();

		// startLoading(__CLASSNAME__, getMethodName());

		if (((RadioGroup) findViewById(R.id.sex)).getCheckedRadioButtonId() == R.id.female) {
			getApp().p_sex = "F";
		} else {
			getApp().p_sex = "M";
		}

		getApp().p_birthday = ((TextView) findViewById(R.id.edt_login_birthday)).getText().toString();

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

		// if (emailcheck) {
		// KP_nnnn.KP_5011(getApp().p_mid, p_m1, p_m2, getApp().p_email);
		// } else {
		// KP_nnnn.KP_5021(getApp().p_mid, p_m1, p_m2, getApp().p_nickname, getApp().p_email, getApp().p_pwd, getApp().p_sex, getApp().p_birthday,
		// mPhoneInfo.getNcode(), article_id, using_id);
		// }
		int index = mProfileCountry.getSelectedItemPosition();
		String ncode = getApp().mFlagListAdapter.getFlagsNcode(index);

		// KP_nnnn.KP_5021(getApp().p_mid, p_m1, p_m2, getApp().p_nickname, getApp().p_email, getApp().p_pwd, getApp().p_sex, getApp().p_birthday,
		// mPhoneInfo.getNcode(), article_id, using_id);
		KP_nnnn.KP_5021(getApp().p_mid, p_m1, p_m2, getApp().p_nickname, getApp().p_email, getApp().p_pwd, getApp().p_sex, getApp().p_birthday, ncode, article_id, using_id);

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {


		if (("KP_5011").equalsIgnoreCase(p_opcode)) {
			// KP_5011 EMAIL중복체크
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				if (p_type.equalsIgnoreCase("register")) {
					sendRegister(false);
				}
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
			}
		} else if (("KP_5021").equalsIgnoreCase(p_opcode)) {
			// KP_5021 회원정보수정
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				// 회원정보수정
				setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				getApp().popupToast(r_message, Toast.LENGTH_SHORT);
				putLogin(KP_nnnn.getInfo());
				// 신규가입시
				if (!p_type.equalsIgnoreCase("change")) {
					// popupToast(R.string.messge_setting_login_register, Toast.LENGTH_LONG);
					// onOptionsItemSelected(R.id.menu_myholic);
					// close();
					getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							// onOptionsItemSelected(R.id.menu_ticket, true);
							onOptionsItemSelected(R.id.menu_shop_ticket, "", true);
							close();
						}
					}, null, null, true, null);
				}
				p_type = "change";
				changeRegister();

				// 코멘트수정
				KP_3012();

				// 이미지수정
				KP_1011();

			} else if (what != _IKaraoke.STATE_DATA_QUERY_START && !("00000").equalsIgnoreCase(r_code)) {
			}

		}

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		play_id = getApp().p_mid;

		KPItem list = getList();

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "type - " + p_type);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "play_id - " + play_id);

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		// if (p_type != null && ("SONG").equalsIgnoreCase(p_type)) {
		// KP_nnnn.KP_1050(getApp().p_mid, p_m1, p_m2, play_id);
		// } else {
		// KP_nnnn.KP_3000(getApp().p_mid, p_m1, p_m2, play_id);
		// }
		KP_nnnn.KP_3001(getApp().p_mid, p_m1, p_m2, play_id);

	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);


		KPItem list = KP_nnnn.getList(0);

		// ViewGroup include_profile_my = (ViewGroup) findViewById(R.id.include_profile_my);
		// ViewGroup include_profile_people = (ViewGroup) findViewById(
		// R.id.include_profile_people);
		// ViewGroup include_profile_time = (ViewGroup) findViewById(R.id.include_profile_time);

		View v = null;
		String value = "";

		// if (mProfileImgView == null) {
		// mProfileImgView = (ImageView) include_profile_people.findViewById(R.id.img_profile_image);
		// }
		//
		// if (mProfileComment == null) {
		// mProfileComment = (TextView) include_profile_people.findViewById(R.id.txt_profile_comment);
		// }

		try {
			// // "my_name"
			// value = list.getString("my_nickname");
			// v = include_profile_my.findViewById(R.id.txt_profile_nickname);
			// if (value != null) {
			// putValue(v, value);
			// }
			//
			// // "ncode":"KR",
			// value = list.getString("ncode");
			// v = include_profile_my.findViewById(R.id.img_profile_country);
			// if (value != null) {
			// res = getResource("img_flag_" + value.toLowerCase(), "drawable");
			// try {
			// ((ImageView) v).setImageResource(res);
			// } catch (Exception e) {
			//
			// //e.printStackTrace();
			// }
			// } else {
			// v.setVisibility(View.GONE);
			// }
			//
			// // "my_hit":0,
			// value = list.getString("my_hit");
			// v = include_profile_my.findViewById(R.id.txt_profile_hit);
			// if (value != null) {
			// value = TextUtil.numberFormat(value);
			// putValue(v, value);
			// }
			//
			// // "my_heart":0,
			// value = list.getString("my_heart");
			// v = include_profile_my.findViewById(R.id.txt_profile_heart);
			// if (value != null) {
			// value = TextUtil.numberFormat(value);
			// putValue(v, value);
			// }
			//
			// // "name":"prettyMan1",
			// value = list.getString(LOGIN.KEY_NICKNAME);
			// v = include_profile_people.findViewById(R.id.txt_profile_nickname);
			// if (value != null) {
			// putValue(v, value);
			// }
			//
			// // "ncode":"JP",
			// value = list.getString("ncode");
			// v = include_profile_people.findViewById(R.id.img_profile_country);
			// if (value != null) {
			// res = getResource("img_flag_" + value.toLowerCase(), "drawable");
			// try {
			// ((ImageView) v).setImageResource(res);
			// } catch (Exception e) {
			//
			// //e.printStackTrace();
			// }
			// } else {
			// v.setVisibility(View.GONE);
			// }
			//
			// Locale locale = new Locale(mPhoneInfo.getNcode(), value);
			// value = locale.getDisplayCountry();
			// v = include_profile_people.findViewById(R.id.txt_profile_country);
			// if (value != null) {
			// putValue(v, value);
			// }
			//
			// // "hit":6,
			// value = list.getString("hit");
			// v = include_profile_people.findViewById(R.id.txt_profile_hit);
			// if (value != null) {
			// value = TextUtil.numberFormat(value);
			// putValue(v, value);
			// }
			//
			// // "heart":0,
			// value = list.getString("heart");
			// v = include_profile_people.findViewById(R.id.txt_profile_heart);
			// if (value != null) {
			// value = TextUtil.numberFormat(value);
			// putValue(v, value);
			// }
			//
			// // "url_profile":"",
			// value = list.getString("url_profile");
			// v = include_profile_people.findViewById(R.id.img_profile_image);
			// if (value != null) {
			// BitmapUtils2.putQueryURLImage(getBaseActivity().getBaseContext(), (ImageView) v, value);
			// mProfileImgPath = value;
			// }
			//
			// // LOGIN.KEY_BIRTHDAY:"20120301",
			// value = list.getString(LOGIN.KEY_BIRTHDAY);
			// v = include_profile_people.findViewById(R.id.txt_profile_birthday);
			// if (value != null) {
			// putValue(v, value);
			// }
			//
			// // LOGIN.KEY_SEX:"M",
			// value = list.getString(LOGIN.KEY_SEX);
			// v = include_profile_people.findViewById(R.id.txt_profile_birthday);
			// if (value != null) {
			// Drawable d = null;
			// if (value == "F") {
			// d = getBaseActivity().getResources().getDrawable(R.drawable.img_top_people_woman);
			// } else {
			// d = getBaseActivity().getResources().getDrawable(R.drawable.img_top_people_man);
			// }
			// if (d != null) {
			// d.setBounds(new Rect(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()));
			// ((TextView) v).setCompoundDrawables(d, null, null, null);
			// }
			//
			// }
			//
			// // "comment":""
			// value = list.getString("comment");
			// v = include_profile_people.findViewById(R.id.txt_profile_comment);
			// if (value != null) {
			// putValue(v, value);
			// ((TextView) v).setSingleLine(true);
			// ((TextView) v).setSelected(true);
			// }
			//
			// // "uid":"3",
			// value = list.getString("uid");
			// v = include_profile_people.findViewById(R.id.btn_profile_edit);
			// if (value != null) {
			// if (getApp().p_mid.equalsIgnoreCase(value)) {
			// v.setVisibility(View.VISIBLE);
			// } else {
			// v.setVisibility(View.INVISIBLE);
			// }
			// uid = value;
			// } else {
			// v.setVisibility(View.INVISIBLE);
			// }
			//
			// // "holic_time": "10mins 18secs",
			// value = list.getString("play_time");
			// v = include_profile_time.findViewById(R.id.txt_profile_time);
			// if (value != null) {
			// putValue(v, value);
			// }

			// "ncode":"KR",
			value = list.getValue("ncode");
			int position = getApp().mFlagListAdapter.getFlagsIndex(value);
			if (mProfileCountry != null) {
				mProfileCountry.setSelection(position);
			}
			v = findViewById(R.id.img_profile_country);
			if (value != null) {
				try {
					((_ImageView) v).setImageResource(getResource("img_flag_" + value.toLowerCase(), "drawable"));
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				v.setVisibility(View.GONE);
			}

			// "url_profile":"",
			value = list.getValue("url_profile");
			v = findViewById(R.id.img_profile_image);
			if (URLUtil.isNetworkUrl(value)) {
				setImagePath(value);
				putURLImage(mContext, (ImageView) v, value, true, R.drawable.ic_menu_01);
			}

			// "comment":""
			value = list.getValue("comment");
			v = findViewById(R.id.edt_profile_comment);
			if (v != null) {
				if (!TextUtil.isEmpty(value)) {
					putValue(v, value);
				}
				v.setVisibility(View.GONE);
			}

		} catch (Exception e) {

			// e.printStackTrace();
		}

		super.KPnnnn();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_IMAGE:
		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_CAMERA:
			return;
		default:
			break;
		}

		try {
			if (!(new File(getImageUri().getPath())).exists()) {
				String msg = getString(R.string.alert_title_upload_error) + " : " + getImagePath();
				getApp().popupToast(msg, Toast.LENGTH_SHORT);
			}
		} catch (Exception e) {

			e.printStackTrace();
			String msg = getString(R.string.alert_title_upload_error) + " : " + getImagePath();
			getApp().popupToast(msg, Toast.LENGTH_SHORT);
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + requestCode + "," + resultCode + "," + getImagePath());

	}

	/**
	 * 프로파일 코멘트 수정 (KP_3012)
	 */
	//
	public void KP_3012() {
		// 코멘트수정
		// if (!TextUtil.isEmpty(mProfileComment.getText()))
		{
			// startLoading(__CLASSNAME__, getMethodName());
			KP_nnnn.KP_3012(getApp().p_mid, p_m1, p_m2, mProfileComment.getText().toString());
		}
	}

	/**
	 * 프로파일 이미지 수정 (KP_1011)
	 */
	public void KP_1011() {
		String path = getImagePath();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + path);
		if (URLUtil.isNetworkUrl(path)) {
			path = "";
		}
		if (!TextUtil.isEmpty(path)) {
			// startLoading(__CLASSNAME__, getMethodName());
			// (new UploadImageAsyncTask()).execute();
			try {
				KP_nnnn.KP_1011(getApp().p_mid, p_m1, p_m2, "", "PHOTO", "", getImagePath());
			} catch (Exception e) {

				e.printStackTrace();
				getApp().popupToast(R.string.alert_title_upload_error, Toast.LENGTH_SHORT);
			}
		}
	}

}
