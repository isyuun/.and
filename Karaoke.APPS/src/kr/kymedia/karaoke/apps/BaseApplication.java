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
 * filename	:	_Application.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ _Application.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPparam;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.app.MultiDexApplication;
import kr.kymedia.karaoke.apps.adpt.FlagListAdapter;
import kr.kymedia.karaoke.apps.gcm.GCM2;
import kr.kymedia.karaoke.util.Base64;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.ImageDownLoader3;

/**
 * <pre>
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 4. 30.
 * @version 1.0
 */
class BaseApplication extends MultiDexApplication {
	String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	private Activity mActivity;

	final public Activity getActivity() {
		return mActivity;
	}

	final public void setActivity(Activity mActivity) {
		this.mActivity = mActivity;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 */
	public BaseApplication() {
		// TODO Auto-generated constructor stub
		__CLASSNAME__ = this.getClass().getSimpleName();
	}

	@Override
	public void onCreate() {
		if (_IKaraoke.DEBUG) Log.d(__CLASSNAME__, getMethodName());

		super.onCreate();

		// KP_param();

		getCountry();

		initImageDownLoader();
	}

	/**
	 * 로케일
	 */
	public Locale mLocale;
	/**
	 * 국가정보코드
	 */
	public FlagListAdapter mFlagListAdapter;

	public void getCountry() {
		mLocale = getResources().getConfiguration().locale;

		// String ncode = mLocale.getCountry().toUpperCase();
		String lcode = mLocale.getLanguage().toUpperCase();

		// 국기정보스트링
		ArrayList<String> ncodes = new ArrayList<String>();
		Collections.addAll(ncodes, getResources().getStringArray(R.array.array_flags));
		ArrayList<String> nnames = TextUtil.getFlagsNames(lcode, ncodes);

		HashMap<String, String> country = new HashMap<String, String>();

		int index = 0;
		for (String ncode : ncodes) {
			country.put(nnames.get(index), ncode);
			index++;
		}

		// 국기정보어댑터
		if (mFlagListAdapter == null) {
			mFlagListAdapter = new FlagListAdapter(this, R.layout.simple_spinner_item, nnames, country);
			mFlagListAdapter.sort(new Comparator<String>() {
				@Override
				public int compare(String object1, String object2) {
					return object1.compareToIgnoreCase(object2);
				};
			});
		}
	}

	@Override
	public void onTerminate() {
		if (_IKaraoke.DEBUG) Log.d(__CLASSNAME__, getMethodName());

		super.onTerminate();

		release();
	}

	public void release() {
		try {
			if (mImageDownLoader == null) {
				mImageDownLoader.release();
			}
			mImageDownLoader = null;
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private IImageDownLoader mImageDownLoader;

	public void initImageDownLoader() {
		if (mImageDownLoader == null) {
			mImageDownLoader = new ImageDownLoader3(this);
		}
	}

	public IImageDownLoader getImageDownLoader() {
		return mImageDownLoader;
	}

	// 회원정보(화면간공유정보)
	/**
	 * <pre>
	 * 전문기본파람정보
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * </pre>
	 */
	public KPparam KPparam = null;

	private void KPparam() {
		mLocale = getResources().getConfiguration().locale;

		// String ncode = mLocale.getCountry().toUpperCase();
		String lcode = mLocale.getLanguage().toUpperCase();

		// 기기정보
		if (KPparam == null) {
			KPparam = new KPparam(this, true);
		} else {
			KPparam.init(this, _IKaraoke.DEBUG);
		}

		if (KPparam != null) {
			if (!TextUtil.isEmpty(lcode)) {
				KPparam.setLcode(lcode);
			}
		}
	}

	public void KP_param() {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		if (_IKaraoke.DEBUG) {
			KPparam();
		} else {
			try {
				KPparam();
			} catch (Exception e) {
				Log2.e(__CLASSNAME__, getMethodName() + Log2.getStackTraceString(e));
			}
		}
	}

	/**
	 * User id 로그인한 user id:p_mid
	 */
	public String p_mid = "";
	/**
	 * //사용자 ID //기본적으로, 위에 언급한 snuid는 UDID (또는 iOS의 Mac Address)를 리턴합니다. //그러나, 어플리케이션이 사용자 id를 사용하고
	 * 있을 시 UDID 대신에 사용자 id를 통해 트래킹하려면 다음 메서드를 호출할 수 있습니다. //안드로이드 이용 안내
	 */
	protected String p_uid = "";

	/**
	 * 광고어플콜백영 유저아이디확인
	 */
	void checkUID() {
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName());


		String mid = "";

		try {
			mid = new String(Base64.decode(p_mid));
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (KPparam != null) {
			p_uid = Base64.encode((mid + ":" + KPparam.getStbid()).getBytes());
		}
		// if (_IKaraoke.DEBUG)Log.i("[USERINFO]", getMethodName() + "[p_uid]" + p_uid);
		// if (_IKaraoke.DEBUG)Log.i("[USERINFO]", getMethodName() + "[mid]" + getApp().p_mid);
		// if (_IKaraoke.DEBUG)Log.i("[USERINFO]", getMethodName() + "[mid]" + mid);
		// if (_IKaraoke.DEBUG)Log.i("[USERINFO]", getMethodName() + "[stbid]" + KP_param.getStbid());
	}

	/**
	 * 이용권유형:p_passtype - N:무료사용자 Y:기간제이용권 E/C:이벤트사용자
	 */
	public String p_passtype = "";
	/*
	 * 
	 */
	public String p_expire_date = "";

	/**
	 * 이용권유형 무료사용자 :N 기간제이용권:Y 이벤트사용자:E/C
	 * 
	 * @see #p_passtype
	 * @see #isLoginUser()
	 */
	public boolean isPassUser() {
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName());
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_mid);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_email);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_pwd);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_passtype);

		// if (!isLoginUser()) {
		// return false;
		// }

		if (!TextUtil.isEmpty(p_passtype) && !("N").equalsIgnoreCase(p_passtype)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 잔여갯수 곡단위이용권 사용자일경우 잔여갯수:p_passcnt
	 */
	public String p_passcnt = "";
	/**
	 * 무료곡 가능갯수:p_free - 0 인 경우 1일무료곡 있음 곡번호인 경우 1일무료곡 없음(이미 사용했음)
	 */
	public int p_free = 0;
	/**
	 * 자동로그인
	 */
	public boolean login_check = true;
	/**
	 * 온스팟카드번호
	 */
	public String p_cardno = "";
	/**
	 * 로그인아이디
	 */
	public String p_email = "";
	/**
	 * 로그인비밀번호
	 */
	public String p_pwd = "";
	/**
	 * 로그인닉네임
	 */
	public String p_nickname = "";
	/**
	 * 로그인생년월일
	 */
	public String p_birthday = "";
	/**
	 * 로그인성별
	 */
	public String p_sex = "";
	/**
	 * 상품명
	 */
	public String p_goodsname = "";
	/**
	 * 상품코드
	 */
	public String p_goodscode = "";
	/**
	 * 소리 알림
	 */
	public boolean notification_sound = true;
	/**
	 * 진동 알림
	 */
	public boolean notification_vibration = true;

	/**
	 * 모바일데이터 사용허용
	 */
	public boolean network_usemobile = false;
	/**
	 * 회원 가입 시 추천인 INPUT BOX 출력 여부 설정 Response 변수값 추가
	 */
	public boolean is_recommend = false;
	/**
	 * 폰환경설정(사용자정보)
	 */
	protected SharedPreferences mSharedPreferences;

	/**
	 * 어플리케이션 사용자정보<br>
	 * XML저장데이터 부하발생 주의
	 */
	public SharedPreferences getSharedPreferences() {
		if (mSharedPreferences != null) {
			return mSharedPreferences;
		}

		String name = getPackageName();

		if (mSharedPreferences == null) {
			mSharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE);
		}

		showSharedPreferenceFile();

		return mSharedPreferences;
	}

	public void showSharedPreferenceFile() {

		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		if (!_IKaraoke.DEBUG) {
			return;
		}

		// 설정값xml확인하기
		String path = EnvironmentUtils.getSharePrefPath(this);

		if (path == null) {
			return;
		}

		try {
			FileReader in = new FileReader(new File(path));
			BufferedReader br = new BufferedReader(in);
			String line = "";
			while ((line = br.readLine()) != null) {
				if (_IKaraoke.DEBUG) Log.i("[SharedPreferenceFile]", line);
			}
			br.close();
		} catch (Exception e) {

			// e.printStackTrace();
		}
	}

	// @Deprecated
	// public void showSharedPreferenceValue() {
	// // 설정값map확인하기
	// Map<String, ?> keys = mSharedPreferences.getAll();
	// for (Map.Entry<String, ?> entry : keys.entrySet()) {
	// try {
	// if (_IKaraoke.DEBUG)Log.e("[SharedPreference]", entry.getKey() + ": " + entry.getValue().toString());
	// } catch (Exception e) {
	//
	// //e.printStackTrace();
	// }
	// }
	// }

	/**
	 * <p>
	 * 로그인여부확인
	 * </p>
	 * <p>
	 * 패스워드와 이메일확인으로 로그인확인
	 * </p>
	 * 
	 * @return
	 */
	public boolean isLoginUser() {
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName());
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, p_mid);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, p_email);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, p_pwd);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, p_passtype);

		boolean ret = false;

		// 패스워드와 이메일확인으로 로그인확인
		// if (!TextUtil.isEmpty(p_mid) && TextUtil.checkParameter(LOGIN.KEY_EMAIL, p_email)
		// && !TextUtil.isEmpty(p_pwd)) {
		if (!TextUtil.isEmpty(p_mid)) {
			ret = true;
		} else {
			ret = false;
		}

		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName() + ret);

		checkUID();

		return ret;
	}

	// /**
	// * 저장된 로그인정보를 삭제한다.
	// * <p>
	// * 로그인화면으로이동
	// * </p>
	// */
	// public void putLogout() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + p_mid);
	//
	// boolean ok = delLogin();
	//
	// if (ok) {
	// putLogin(null);
	// getLogin();
	// }
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + p_mid);
	// }

	/**
	 * 해당사용자의 로그인프로파일 정보삭제
	 */
	protected boolean delLoginProfile() {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		// p_passtype = "";
		// p_free = 0;
		// p_nickname = "";
		// p_birthday = "";
		// p_sex = "";
		// p_goodsname = "";
		// p_goodscode = "";

		return true;
	}

	/**
	 * 해당사용자의 로그인정보 전체삭제
	 */
	protected boolean delLogin() {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		SharedPreferences.Editor edit = mSharedPreferences.edit();
		edit.clear();
		boolean ok = edit.commit();
		getSharedPreferences();

		// 로그인정보삭제
		p_mid = "";
		p_email = "";
		p_pwd = "";
		p_passtype = "";
		p_free = 0;
		p_nickname = "";
		p_birthday = "";
		p_sex = "";
		p_goodsname = "";
		p_goodscode = "";

		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + ok);

		return ok;
	}

	/**
	 * 로그인결과반영<br>
	 * <br>
	 * info == null - 전문 "info"태그를 저장한다. -> 한마디로 로그인/회원정보변경등 전문연동시<br>
	 * info != null - 현제 인스턴스변수를 저장한다.-> 한마디로 로그아웃/재로그인등 로컬정보삭제시<br>
	 * <br>
	 */
	int putLogin(KPItem info) {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());
		if (info != null) {
			if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, "info - " + info.toString(2));
		} else {
			if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, "info - " + info);
		}


		getSharedPreferences();

		if (info != null && !TextUtil.isEmpty(info.getValue(LOGIN.KEY_MID))) {
			p_mid = TextUtil.isEmpty(info.getValue(LOGIN.KEY_MID)) ? p_mid : info.getValue(LOGIN.KEY_MID).trim();
			String email = TextUtil.isEmpty(info.getValue(LOGIN.KEY_EMAIL)) ? p_email : info.getValue(LOGIN.KEY_EMAIL).trim();
			if (TextUtil.checkParameter(LOGIN.KEY_EMAIL, email)) {
				p_email = email;
			} else {
				try {
					p_email = new String(Base64.decode(email.getBytes()));
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			p_passtype = TextUtil.isEmpty(info.getValue(LOGIN.KEY_PASSTYPE)) ? p_passtype : info.getValue(LOGIN.KEY_PASSTYPE).trim();
			p_free = TextUtil.parseInt(TextUtil.isEmpty(info.getValue(LOGIN.KEY_FREE)) ? "0" : info.getValue(LOGIN.KEY_FREE).trim());
			p_nickname = TextUtil.isEmpty(info.getValue(LOGIN.KEY_NICKNAME)) ? p_nickname : info.getValue(LOGIN.KEY_NICKNAME).trim();
			p_birthday = TextUtil.isEmpty(info.getValue(LOGIN.KEY_BIRTHDAY)) ? p_birthday : info.getValue(LOGIN.KEY_BIRTHDAY).trim();
			p_sex = TextUtil.isEmpty(info.getValue(LOGIN.KEY_SEX)) ? p_sex : info.getValue(LOGIN.KEY_SEX).trim();
			p_pwd = TextUtil.isEmpty(info.getValue(LOGIN.KEY_PWD)) ? p_pwd : info.getValue(LOGIN.KEY_PWD).trim();
			p_pwd = TextUtil.replacePwd(p_pwd).trim();
			p_goodsname = TextUtil.isEmpty(info.getValue(LOGIN.KEY_GOODSNAME)) ? p_goodsname : info.getValue(LOGIN.KEY_GOODSNAME);
			p_goodscode = TextUtil.isEmpty(info.getValue(LOGIN.KEY_GOODSCODE)) ? p_goodscode : info.getValue(LOGIN.KEY_GOODSCODE);
		}

		SharedPreferences.Editor edit = mSharedPreferences.edit();

		edit.putBoolean(LOGIN.KEY_LOGIN_CHECK, login_check);
		edit.putBoolean(LOGIN.KEY_NETWORK_USEMOBILE, network_usemobile);
		edit.putString(LOGIN.KEY_MID, TextUtil.isEmpty(p_mid) ? "" : p_mid);
		edit.putString(LOGIN.KEY_EMAIL, TextUtil.isEmpty(p_email) ? "" : Base64.encode(p_email.getBytes()));
		edit.putString(LOGIN.KEY_PASSTYPE, TextUtil.isEmpty(p_passtype) ? "" : p_passtype);
		edit.putInt(LOGIN.KEY_FREE, p_free);
		edit.putString(LOGIN.KEY_NICKNAME, TextUtil.isEmpty(p_nickname) ? "" : p_nickname);
		edit.putString(LOGIN.KEY_BIRTHDAY, TextUtil.isEmpty(p_birthday) ? "" : p_birthday);
		edit.putString(LOGIN.KEY_SEX, TextUtil.isEmpty(p_sex) ? "" : p_sex);
		edit.putString(LOGIN.KEY_PWD, TextUtil.isEmpty(p_pwd) ? "" : p_pwd);
		edit.putString(LOGIN.KEY_GOODSNAME, TextUtil.isEmpty(p_goodsname) ? "" : p_goodsname);
		edit.putString(LOGIN.KEY_GOODSCODE, TextUtil.isEmpty(p_goodscode) ? "" : p_goodscode);
		edit.putBoolean(LOGIN.KEY_NOTIFICATION_SOUND, notification_sound);
		edit.putBoolean(LOGIN.KEY_NOTIFICATION_VIBRATION, notification_vibration);

		boolean ok = edit.commit();

		if (ok) {
			// mSpref = mSharedPreferences;
			//
			// if (mSpref != null) {
			// login_check = mSpref.getBoolean(LOGIN.KEY_LOGIN_CHECK, true);
			// network_usemobile = mSpref.getBoolean(LOGIN.KEY_NETWORK_USEMOBILE, false);
			// //kr이 아닌 곳은 3g설정 기본 체크로
			// if (!("KR").equalsIgnoreCase(mPhoneInfo.getNcode())) {
			// network_usemobile = mSpref.getBoolean(LOGIN.KEY_NETWORK_USEMOBILE, true);
			// }
			// p_mid = mSpref.getString(LOGIN.KEY_MID, "");
			// p_email = mSpref.getString(LOGIN.KEY_EMAIL, "");
			// p_passtype = mSpref.getString(LOGIN.KEY_PASSTYPE, "");
			// p_free = mSpref.getInt(LOGIN.KEY_FREE, 0);
			// p_nickname = mSpref.getString(LOGIN.KEY_NICKNAME, "");
			// p_birthday = mSpref.getString(LOGIN.KEY_BIRTHDAY, "");
			// p_sex = mSpref.getString(LOGIN.KEY_SEX, "");
			// p_pwd = mSpref.getString(LOGIN.KEY_PWD, "");
			// p_goodsname = mSpref.getString(LOGIN.KEY_GOODSNAME, "");
			// p_goodscode = mSpref.getString(LOGIN.KEY_GOODSCODE, "");
			// }
		}

		return 1;
	}

	/**
	 * 로그인확인:비로그인시로그인처리한다.
	 */
	protected boolean checkLogin(boolean login) {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		if (!isLoginUser()) {
			if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, "[LOGININFO]\np_mid:" + p_mid + "\np_email:" + p_email + "\np_pwd:" + p_pwd + "\np_passtype:" + p_passtype);
			getLogin();
		}

		return isLoginUser();
	}

	/**
	 * 저장된 로그인정보를 가져온다. 주로 onCreateView()에서 호출된다.
	 */
	protected boolean getLogin() {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		getSharedPreferences();

		if (mSharedPreferences != null) {
			login_check = mSharedPreferences.getBoolean(LOGIN.KEY_LOGIN_CHECK, true);
			network_usemobile = mSharedPreferences.getBoolean(LOGIN.KEY_NETWORK_USEMOBILE, false);
			// kr이 아닌 곳은 3g설정 기본 체크로
			if (!("KR").equalsIgnoreCase(mLocale.getCountry())) {
				network_usemobile = mSharedPreferences.getBoolean(LOGIN.KEY_NETWORK_USEMOBILE, true);
			}
			p_mid = mSharedPreferences.getString(LOGIN.KEY_MID, "").trim();
			String email = mSharedPreferences.getString(LOGIN.KEY_EMAIL, "").trim();
			if (TextUtil.checkParameter(LOGIN.KEY_EMAIL, email)) {
				p_email = email;
			} else {
				try {
					p_email = new String(Base64.decode(email.getBytes()));
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			p_passtype = mSharedPreferences.getString(LOGIN.KEY_PASSTYPE, "").trim();
			p_free = mSharedPreferences.getInt(LOGIN.KEY_FREE, 99999);
			p_nickname = mSharedPreferences.getString(LOGIN.KEY_NICKNAME, "").trim();
			p_birthday = mSharedPreferences.getString(LOGIN.KEY_BIRTHDAY, "").trim();
			p_sex = mSharedPreferences.getString(LOGIN.KEY_SEX, "").trim();
			p_pwd = mSharedPreferences.getString(LOGIN.KEY_PWD, "").trim();
			p_goodsname = mSharedPreferences.getString(LOGIN.KEY_GOODSNAME, "");
			p_goodscode = mSharedPreferences.getString(LOGIN.KEY_GOODSCODE, "");
			notification_sound = mSharedPreferences.getBoolean(LOGIN.KEY_NOTIFICATION_SOUND, true);
			notification_vibration = mSharedPreferences.getBoolean(LOGIN.KEY_NOTIFICATION_VIBRATION, true);
		}

		return isLoginUser();
	}

	/**
	 * KP_9003 사용자푸시정보등록
	 */
	public KPnnnn KP_9003 = null;

	/**
	 * KP_0004 메뉴조회
	 */
	KPnnnn KP_menu = null;

	/**
	 * 전달된 푸시인텐트가 이전과 동일한지 확인한다.
	 * 
	 * @see <a href="http://stackoverflow.com/questions/5719113/how-to-properly-clear-intent-data-from-singletop-activity">stackoverFlow - How to properly clear intent data from singleTop Activity?</a>
	 * @see <a href="https://groups.google.com/forum/#!topic/android-developers/vrLdM5mKeoY">google groups - How to properly clear intent data from singleTop Activity?</a>
	 */
	public Intent intentGCM;

	Toast mToast = null;

	public void popupToast(CharSequence text, int dur) {
		try {
			if (mToast != null) {
				mToast.cancel();
			}
			mToast = Toast.makeText(this, text, dur);
			mToast.show();
		} catch (Exception e) {

			Log.e(__CLASSNAME__, Log.getStackTraceString(e));
		}
	}

	public void popupToast(String text, int dur) {
		try {
			if (mToast != null) {
				mToast.cancel();
			}
			mToast = Toast.makeText(this, text, dur);
			mToast.show();
		} catch (Exception e) {

			Log.e(__CLASSNAME__, Log.getStackTraceString(e));
		}
	}

	public void popupToast(int resId, int dur) {
		try {
			if (mToast != null) {
				mToast.cancel();
			}
			mToast = Toast.makeText(this, resId, dur);
			mToast.show();
		} catch (Exception e) {

			Log.e(__CLASSNAME__, Log.getStackTraceString(e));
		}
	}

	EditText mLastEditText;

	/**
	 * 1. 키보드 감추기
	 */
	public void hideSoftInput(EditText et) {
		try {
			mLastEditText = et;
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 2. 키보드 보여주기
	 */
	public void showSoftInput(EditText et) {
		try {
			mLastEditText = et;
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void hideSoftInput() {
		if (mLastEditText != null) {
			hideSoftInput(mLastEditText);
		}
	}

	private int getNotificationIcon() {
		boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
		return useWhiteIcon ? R.drawable.ic_launcher_push : R.drawable.ic_launcher;
	}

	/**
	 * 푸쉬서비스용<br>
	 * 알림창열기<br>
	 */
	public void openNotification(PendingIntent pIntent, int id, long when, CharSequence ticker, CharSequence title, CharSequence message, boolean ongoing, boolean sound,
			boolean vibration) {
		if (_IKaraoke.DEBUG) Log.e("BaseActivity1", "openPushNotification() " + id + "," + ticker + "," + title + "," + message);

		try {
			// Get Notification Service
			NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
			Bitmap bm = ((BitmapDrawable) d).getBitmap();

			NotificationCompat.Builder nb = new NotificationCompat.Builder(this);

			nb.setSmallIcon(getNotificationIcon())
					.setLargeIcon(bm)
					.setTicker(ticker)
					.setWhen(when)
					.setContentTitle(title)
					.setContentText(message)
					.setContentIntent(pIntent)
					.setOngoing(ongoing).setOnlyAlertOnce(true).setAutoCancel(true);

			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
				nb.setColor(0xff123456);
			}

			Notification nf = nb.build();
			if (sound) {
				nf.defaults |= Notification.DEFAULT_SOUND;
			}
			if (vibration) {
				nf.defaults |= Notification.DEFAULT_VIBRATE;
			}

			nm.notify(id, nf);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * 알림창닫기
	 */
	public void cancelNotification(int id) {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(id);
	}

	/**
	 * 알림창닫기
	 */
	@Deprecated
	public void closeNotificationAll() {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();
	}

	/**
	 * 푸시(GCM)
	 */
	private GCM2 mGCM = null;

	public GCM2 getGCM() {
		return mGCM;
	}

	/**
	 * 푸시(GCM)리시버
	 */
	// private GCM2Receiver mGCMReceiver = null;

	/**
	 * 푸시 REGID등록(KP_9003)만처리<br>
	 */
	protected void initGCM(Activity activity) {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__ + "[GCM]", getMethodName() + isLoginUser() + " : " + p_mid);

		try {
			// 푸시클래스(GCM)
			if (mGCM == null) {
				mGCM = new GCM2(this);
			}

			if (mGCM != null) {

				if (!mGCM.checkPlayServices(activity)) {
					return;
				}

				// 푸시스타트(GCM)
				if (mGCM != null) {
					try {
						mGCM.startGCM();
					} catch (Exception e) {

						e.printStackTrace();
					}
				}

			}

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "REGID=" + mGCM.REGID);

			// 푸시아이디등록
			if (KP_9003 != null && isLoginUser() && !TextUtil.isEmpty(mGCM.REGID)) {
				KP_9003.KP_9003(p_mid, "", mGCM.REGID, "", "", "");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void testPushGCM() {
		if (mGCM != null) {
			mGCM.testPushGCM();
		}
	}

	final protected void refresh() {

		if (getBaseActivity() != null) {
			getBaseActivity().refresh();
		}
	}

	final protected _BaseActivity getBaseActivity() {

		return (_BaseActivity) getActivity();
	}

	final protected _BaseFragment getCurrentFragment() {
		if (getBaseActivity() != null) {
			return getBaseActivity().getCurrentFragment();
		} else {
			return null;
		}
	}

	final public String showDataError(String name, String method, String tag, KPItem item) {
		String message = getString(R.string.errmsg_dataerror);

		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) {
			message += "\n" + name + "::" + method + "\n";
			popupToast(message, Toast.LENGTH_LONG);
		}

		message += tag + " - " + item;

		if (_IKaraoke.DEBUG) {
			getBaseActivity().showAlertDialog(getString(R.string.errmsg_data), message, getString(R.string.btn_title_confirm), null, null, null, true, null);
		}

		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e("[DATAERROR]", message);

		return message;
	}

}
