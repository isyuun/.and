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
 * filename	:	BaseFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.kakao.talk.KakaoLink2;
import com.kakao.talk.StoryLink2;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import is.yuun.yanzm.products.quickaction.lib.QuickAction2;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPItemShareData;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.impl.IBaseFragment;
import kr.kymedia.karaoke.kpop.FeelPostFragment;
import kr.kymedia.karaoke.kpop.ListenPostFragment;
import kr.kymedia.karaoke.kpop.ListenPostFragment2;
import kr.kymedia.karaoke.kpop._FeelListFragment;
import kr.kymedia.karaoke.kpop._myholictab;
import kr.kymedia.karaoke.kpop._popup;
import kr.kymedia.karaoke.kpop.auditionlist;
import kr.kymedia.karaoke.kpop.auditiontab;
import kr.kymedia.karaoke.kpop.auditionview;
import kr.kymedia.karaoke.kpop.feel;
import kr.kymedia.karaoke.kpop.feelpost;
import kr.kymedia.karaoke.kpop.feeltab;
import kr.kymedia.karaoke.kpop.listenlist;
import kr.kymedia.karaoke.kpop.listenpost;
import kr.kymedia.karaoke.kpop.listenpost2;
import kr.kymedia.karaoke.kpop.listentab;
import kr.kymedia.karaoke.kpop.login;
import kr.kymedia.karaoke.kpop.myholic;
import kr.kymedia.karaoke.kpop.notice;
import kr.kymedia.karaoke.kpop.noticelist;
import kr.kymedia.karaoke.kpop.profile;
import kr.kymedia.karaoke.kpop.setting;
import kr.kymedia.karaoke.kpop.settingnotification;
import kr.kymedia.karaoke.kpop.shop;
import kr.kymedia.karaoke.kpop.singlist;
import kr.kymedia.karaoke.kpop.singtab;
import kr.kymedia.karaoke.kpop.userlist;
import kr.kymedia.karaoke.kpop.webinipay;
import kr.kymedia.karaoke.kpop.webview;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.util.TextUtil;
import yanzm.products.quickaction.lib.ActionItem;

/**
 * TODO NOTE:<br>
 * 
 * <pre>
 * 전문(KPnnnn) 추가 후 기본프래그먼트
 * 마켓리워드오픈기능추가이동 {@link #openMarketNReward(KPItem, KPItem)}
 * </pre>
 * 
 * @author isyoon
 * @since 2012. 2. 24.
 * @version 1.0
 */

class BaseFragment2 extends BaseFragment implements IBaseFragment {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	/**
	 * 기본KPnnnn 절대정적할당하지 않는다~<br>
	 */
	protected KPnnnn KP_nnnn = null;

	/**
	 * @see BaseApplication2#KP_9999(String, String, String, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.TYPE, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.LEVEL, String, String, String, int, int, String, String, KPItem,
	 *      KPItem, String)
	 * 
	 */
	public void KP_9999(String mid, String m1, String m2, MEDIAERROR.TYPE type, MEDIAERROR.LEVEL level, String song_id, String record_id, String where, int what, int extra,
			String wname, String ename, KPItem info, KPItem list, String message) {
		getApp().KP_9999(mid, m1, m2, type, level, song_id, record_id, where, what, extra, wname, ename, info, list, message);
	}

	/**
	 * KPnnnn 리스트 인덱스 BaseFragment에서는 무조건 0 이겠지 아니면?
	 */
	protected int KP_index = 0;
	// 화면정보(화면간고유정보)
	/**
	 * 1차메뉴코드 KPOPApp가 요청하는 1차메뉴 코드 (3.4 메뉴코드 정의 참고)
	 */
	public String p_m1 = "";
	/**
	 * 2차메뉴코드 KPOPApp가 요청하는 2차메뉴 코드 (3.4 메뉴코드 정의 참고)
	 */
	public String p_m2 = "";
	/**
	 * 전달데이터의 type결정
	 */
	public String p_type = "";
	/**
	 * 전달받은 메뉴에 해당하는 id값이 존재하거나 list일 경우 다음 메뉴가 곡목록임
	 */
	public String play_id = "";
	/**
	 * 엑티비티이동간 전달 info 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달되 info정보
	 */
	KPItem mInfo = new KPItem();

	/**
	 * 엑티비티이동간 전달 info 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달되 info정보
	 */
	public KPItem getInfo() {
		return mInfo;
	}

	// /**
	// * 엑티비티이동간 전달 list배열 조회후 정보로 사용하지 않는다.
	// */
	// @Deprecated
	// private ArrayList<KPItem> mLists = new ArrayList<KPItem>();
	//
	// /**
	// */
	// public void setLists(ArrayList<KPItem> lists) {
	// this.mLists = lists;
	// }

	/**
	 * 엑티비티이동간 전달 list 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달된 list정보
	 */
	KPItem mList = new KPItem();

	/**
	 * 엑티비티이동간 전달 list 조회후 정보로 사용하지 않는다. 한마디로 이전화면에서 전달된 list정보
	 */
	public KPItem getList() {
		return mList;
	}

	// /**
	// * 엑티비티이동간 전달 list 조회후 정보로 사용하지 않는다.
	// */
	// public void setList(KPItem list) {
	// this.mList = list;
	// }

	/**
	 * 번들확인 탭페이지 프래그먼트인 경우 확인
	 */
	protected Bundle mExtras = null;

	public Bundle getExtras() {
		return mExtras;
	}

	/**
	 * 다음화면으로 현재화면의 조회정보를 전달한다. <br>
	 * 다수의 프로토콜을 사용하는 경우 잘전달한다.~
	 */
	final protected Intent putIntentData(Class<?> cls, KPnnnn KP_xxxx, int index) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\n" + cls.getSimpleName());

		Intent intent = null;

		try {
			if (index < 0) {
				return null;
			}

			if (KP_xxxx == null) {
				KP_xxxx = KP_nnnn;
			}

			KPItem info = KP_xxxx.getInfo();
			KPItem list = getList();
			ArrayList<KPItem> lists = KP_xxxx.getLists();

			// if (lists != null && mListIndex > -1 && mListIndex < lists.size()) {
			// list = lists.get(mListIndex);
			// }
			if (lists != null && index > -1 && index < lists.size()) {
				KPItem item = lists.get(index);
				list = item;
				list.merge(item);
			}

			intent = putIntentData(mContext, cls, info, list);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return intent;

	}

	/**
	 * 다음화면으로 현재화면의 조회정보를 전달한다.<br>
	 * 다수의 프로토콜을 사용하는 경우 잘전달한다.<br>
	 */
	@Override
	final public Intent putIntentData(Context context, Class<?> cls, KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\n" + cls.getSimpleName());

		// 인텐트를 생성한다.
		// 컨텍스트로 현재 액티비티를, 생성할 액티비티로 ItemClickExampleNextActivity 를 지정한다.
		Intent intent = new Intent(context, cls);

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		Bundle extras = new Bundle();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return null;
		}

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			extras.putParcelable(_IKaraoke.KEY_KPOP_INFOITEM, info);
			// extras.putParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS, lists);
			extras.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);

			// 위에서 만든 Bundle을 인텐트에 넣는다.
			intent.putExtras(extras);

		} catch (Exception e) {

			// e.printStackTrace();
		}

		return intent;
	}

	/**
	 * <pre>
	 * !!!!중요!!!!
	 * 이전화면에서의 주요정보를 전달받는다.
	 * </pre>
	 * 
	 * @see BaseFragment3#start()
	 */
	final protected Intent getIntentKPItem() {

		// 인텐트를 생성한다.
		// 컨텍스트로 현재 액티비티를, 생성할 액티비티로 ItemClickExampleNextActivity 를 지정한다.
		Bundle extras = getArguments();
		Intent intent = null;

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]");
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + extras);

		KPItem item = null;
		try {
			// 번들이 있는경우 액티비티의 인텐트 번들을 변경한다.
			if (extras != null) {
				// 탭페이지(BaseTabsPagerActivity)에서 호출한 경우
				intent = new Intent();
				intent.putExtras(extras);
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[EXTRAS]");
			} else {
				// 기본액티비티(BaseActivity1)에서 호출한 경우
				intent = getBaseActivity().getIntent();
				extras = intent.getExtras();
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[INTENT]");
			}

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "" + intent);
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + extras);

			mExtras = extras;

			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "\n" + intent + "\n" + extras);

			// 번들데이터생성
			if (extras != null) {
				item = extras.getParcelable(_IKaraoke.KEY_KPOP_INFOITEM);
				if (item != null) {
					// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, "item - " + item.toString(2));
					mInfo = item;
				}
				item = extras.getParcelable(_IKaraoke.KEY_KPOP_LISTITEM);
				if (item != null) {
					// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, "item - " + item.toString(2));
					mList = item;
				}
				// mLists = extras.getParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS);
			}

			// 검색 액티비티에서 넘어온 Bundle 데이터를 읽는다.
			extras = intent.getBundleExtra(SearchManager.APP_DATA);
			if (extras != null) {
				item = extras.getParcelable(_IKaraoke.KEY_KPOP_INFOITEM);
				if (item != null) {
					// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, "item - " + item.toString(2));
					mInfo = item;
				}
				item = extras.getParcelable(_IKaraoke.KEY_KPOP_LISTITEM);
				if (item != null) {
					// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, "item - " + item.toString(2));
					mList = item;
				}
				// mLists = extras.getParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS);
			}

			// 위에서 만든 Bundle을 인텐트에 넣는다.
			// intent.putExtras(extras);

		} catch (Exception e) {

			// e.printStackTrace();
		}

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "\n" + intent.getExtras() + "\n" + mExtras);

		return intent;
	}

	/**
	 * <pre>
	 * 전문제조회
	 * 전문초기화
	 * {@link BaseActivity3#refresh()}에서 호출
	 * </pre>
	 */
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		post(new Runnable() {

			@Override
			public void run() {

				getIntentKPItem();

				KP_init();

				KP_nnnn();

			}
		});

		if (BaseFragment2.this == getBaseActivity().getCurrentFragment()) {
			getApp().startSetUpIabHelper();
		}
	}

	public KPnnnn KP_init(Context context, KPnnnn KP_xxxx) {

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + KP_xxxx);
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "" + context);
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "" + KP_xxxx);

		try {
			if (KP_xxxx == null) {
				KP_xxxx = new KPnnnn(context, mHandlerQuery, getApp().KPparam, getApp().p_mid, getApp().p_passtype, getApp().p_passcnt);
			} else {
				KP_xxxx.KP_nnnn(getApp().KPparam, getApp().p_mid, getApp().p_passtype, getApp().p_passcnt, p_m1, p_m2);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_xxxx);

		return KP_xxxx;
	}

	/**
	 * KP_nnnn 초기화 딴데서 하지마라!<br>
	 * KP_xxxx 초기화 딴데서 하지마라!<br>
	 * 
	 */
	@Override
	public void KP_init() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String value = "";

		try {
			if (mInfo != null) {
				if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "mInfo - " + mInfo.toString(2));
			}

			if (mList != null) {
				if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "mList - " + mList.toString(2));

				value = mList.getValue("m1");
				if (TextUtil.isEmpty(value)) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][m1]" + mList.getValue("m1"));
				}
				p_m1 = value;

				value = mList.getValue("m2");
				if (TextUtil.isEmpty(value)) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG][m2]" + mList.getValue("m2"));
				}
				p_m2 = value;

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "p_m1:\t" + p_m1 + ", p_m2:\t" + p_m2);

		KP_nnnn = KP_init(mContext, KP_nnnn);

	}

	/**
	 * <pre>
	 * static으로 정의된 전문의 핸들을 연결한다.
	 * </pre>
	 * 
	 */
	public void KP_hand() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (KP_nnnn != null) {
			KP_nnnn.setHandler(mHandlerQuery);
		}

	}

	/**
	 * KP_nnnn 조회시작
	 * 
	 */
	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);


		KP_hand();

		if (this instanceof PlayFragment) {
			startLoading(__CLASSNAME__, getMethodName() + "[" + KP_nnnn + "]");
		} else {
			if (KP_nnnn == null || (KP_nnnn != null && TextUtil.isEmpty(KP_nnnn.p_opcode))) {
				stopLoading(__CLASSNAME__, getMethodName() + "[" + KP_nnnn + "]");
			} else {
				startLoading(__CLASSNAME__, getMethodName() + "[" + KP_nnnn + "]");
			}
		}

	}

	/**
	 * 조회결과반영 기능: 1. 회원기본정보저장 2. 로그인여부확인 (email유무확인)
	 */
	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);


		KPItem info = KP_nnnn.getInfo();

		if (info != null) {

			boolean save = false;

			if (!TextUtil.isEmpty(info.getValue("is_recommend"))) {
				if (("true").equalsIgnoreCase(info.getValue("is_recommend"))) {
					getApp().is_recommend = true;
				} else {
					getApp().is_recommend = false;
				}
				save = true;
			}

			if (!TextUtil.isEmpty(info.getValue("getApp().kp_error_report"))) {
				if (("true").equalsIgnoreCase(info.getValue("getApp().kp_error_report"))) {
					getApp().kp_error_report = true;
				} else {
					getApp().kp_error_report = false;
				}
				save = true;
			}

			if (!TextUtil.isEmpty(info.getValue(LOGIN.KEY_MID))) {
				getApp().p_mid = info.getValue(LOGIN.KEY_MID);
				save = true;
			}

			if (!TextUtil.isEmpty(info.getValue(LOGIN.KEY_EMAIL))) {
				getApp().p_email = info.getValue(LOGIN.KEY_EMAIL);
				save = true;
			}

			if (!TextUtil.isEmpty(info.getValue(LOGIN.KEY_PASSTYPE))) {
				getApp().p_passtype = info.getValue(LOGIN.KEY_PASSTYPE);

				// 회원아이디확인
				if (TextUtil.isEmpty(getApp().p_mid)) {
					getApp().p_passtype = "N";
				}

				save = true;
			}

			if (!TextUtil.isEmpty(info.getValue("expire_date"))) {
				getApp().p_expire_date = info.getValue("expire_date");

				// 회원아이디확인
				// if (TextUtil.isEmpty(getApp().p_mid)) {
				// getApp().p_expire_date = "N";
				// }

				save = true;
			}

			if (save) {
				// putLogin();
			}
		}

		try {
			if (getBaseActivity() instanceof BaseAdActivity) {
				((BaseAdActivity) getBaseActivity()).queryAd(p_m1, p_m2);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * 액티비티 백버튼차단시 오류메시지 무시
	 */
	boolean mBackPressed = false;

	@Override
	public boolean onBackPressed() {

		mBackPressed = true;
		return super.onBackPressed();
	}

	protected void KPnnnnResult(int what, String p_opcode, String r_code, String r_message, KPItem r_info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + p_opcode + "[RESULT_CODE]" + r_code);

		KPnnnnResult(what, p_opcode, r_code, r_message, (r_info != null ? r_info.toString() : ""));
	}

	/**
	 * <pre>
	 * 재생관련전문목록
	 * @see KPnnnn#KP_1016(String, String, String, String, String)
	 * @see KPnnnn#KP_1016(String, String, String, String, String, String)
	 * @see KPnnnn#KP_2016(String, String, String, String, String)
	 * </pre>
	 */
	final public static String KP_PLAY[] = { "KP_1016", "KP_2016", };

	/**
	 * <pre>
	 * 로딩처리제외목록
	 * @see BaseFragment2#KPnnnnResult(int, String, String, String, String)
	 * @see BaseActivity2#startLoading(String, String)
	 * @see BaseActivity2#stopLoading(String, String)
	 * @see KPnnnn#KP_1012(String, String, String, String, String, String, String)
	 * @see KPnnnn#KP_4013(String, String, String, String)
	 * @see KPnnnn#KP_4014(String, String, String)
	 * @see KPnnnn#KP_9003(String, String, String, String, String, String)
	 * @see KPnnnn#KP_9999(String, String, String, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.TYPE, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.LEVEL, String, String, String, int, int, String, String, KPItem, KPItem, String)
	 * </pre>
	 */
	final public static String KP_HIDE_LOADING_STOP[] = { "KP_1012", "KP_4013", "KP_4014", "KP_9003", "KP_9999", };

	/**
	 * 조회결과오류표시 기능: 1. 로딩화면중지 2. 오류내용팝업 "99999"(Unknown Error) 발생시 처리추가
	 * 
	 * <pre>
	 * 44444 : UnknownHostException - 네트워크(?) 오류관련
	 * 55555 : UnknownServiceException - 웹(?)서비스 오류관련
	 * 66666 : SocketException - 소켓(?) 오류관련 
	 * 77777 : HttpResponseException - HTTP 오류관련
	 * 88888 : DataParsingErorr - JSON데이터 오류관련
	 * 99999 : UnknownDataError - 그외 오류관련
	 * 
	 *  00000	성공	토스트 메시지 또는 무시	공통	Success
	 *  		컨펌 팝업	KP_1011 - 녹음곡 다운로드	녹음곡 업로드를 완료하였습니다.\n녹음곡은 MY HOLIC 메뉴에서 확인해주세요.
	 *  		컨펌 팝업	KP_1011 - 녹음곡 다운로드	오디션 참여가 완료되었습니다.\n오디션 화면으로 이동하시겠습니까?
	 *  		토스트 메시지	KP_6050 - 애창곡 등록/삭제	애창곡이 등록되었습니다.
	 *  		토스트 메시지	KP_6050 - 애창곡 등록/삭제	애창곡이 삭제되었습니다.
	 *  		팝업	KP_3011 - 녹음곡 공개/비공개 설정	삭제 권한이 없습니다.
	 *  		토스트 메시지	KP_1013 - 반주곡 재생	1분 미리듣기만 가능합니다.
	 *  		팝업	KP_5020 - 회원 가입	공짜 이용권 받아가세요!\n선물받은 Holic으로 이용권을 공짜로 받아가세요!
	 *  		팝업	KP_5020 - 회원 가입	이미 가입된 기기입니다. Holic은 무료충전소에서 충전가능합니다
	 *  00900	팝업 메시지			등록된 회원이 아닙니다. 확인바랍니다.
	 *  				해당 회원의 정보가 존재하지 않습니다.
	 *  				비밀번호 수정에 실패하였습니다.
	 *  				이미 등록된 디바이스 입니다.
	 *  				해당 이메일 계정으로 임시 비밀번호가 발송되었습니다.
	 *  				회원전용 메뉴입니다.
	 *  				로그인해주세요!
	 *  				상품 결제에 실패하였습니다.
	 *  				이용권 사용자는 기간 만료 후 구매하실 수 있습니다.
	 *  				Holic이 부족합니다.\nHolic을 충전해서 다시 시도해주세요.
	 *  				이미 사용중인 이용권이 존재합니다.
	 *  				2개 이상의 계정 생성을 위해서는\n인증 절차를 완료하셔야 합니다.\n등록하신 이메일 주소로 인증메일을\n보내드렸습니다.
	 *  				오디션 참여곡은 삭제가 불가능합니다.
	 *  				잘못된 조회 명령입니다.
	 *  00901	토스트 메시지			아이디가 일치하지 않습니다.
	 *  				비밀번호가 일치하지 않습니다.
	 *  				등록하신 닉네임은 사용중입니다.
	 *  				검색 결과가 존재하지 않습니다.
	 *  				이미 다른 무료곡을 등록했습니다.
	 *  				해당 회원의 최고 하트 보유 녹음곡이 없습니다.
	 *  				해당 곡의 최대 추천수 보유 녹음곡이 없습니다.
	 *  				해당 곡이 만료되었거나 비공개 처리되었습니다.
	 *  				자신의 녹음곡에는 heart를 줄 수 없습니다.
	 *  				공개 설정 변경은 개최자만 가능합니다.
	 *  				공개로 전환되었습니다.
	 *  				비공개로 전환되었습니다.
	 *  				오디션 개최자만 변경이 가능합니다.
	 *  				참가자가 최소 1명은 존재하여야 합니다.
	 *  				[NICKNAME]님이 탈락되었습니다.
	 *  				진행중인 오디션이 아닙니다. 변경 하실 수 없습니다.
	 *  				해당 오디션은 이미 1위곡이 선정되었습니다.
	 *  				오디션이 마감되었습니다.
	 *  				비공개 오디션입니다.
	 *  				오디션 참여곡은 삭제가 불가능합니다.
	 *  				본인 전용 메뉴입니다. 접근이 제한됩니다
	 *  				쪽지가 발송되었습니다
	 *  				수신 차단이 해제 되었습니다.
	 *  				자신은 수신차단 할 수 없습니다.
	 *  				상품 목록이 없습니다.
	 *  				이용권 구매정보가 없습니다.
	 *  				해당 이메일은 이미 존재합니다.
	 *  				이미 사용하고 있는 E-Mail 입니다.
	 *  				이미 사용하고 있는 닉네임 입니다.
	 *  				잘못된 비밀번호 입니다.
	 *  				본인만 삭제가 가능합니다.
	 *  				오디션 참여곡은 변경이 불가능합니다.
	 *  				녹음곡 등록에 실패하였습니다.[ERROR:1]
	 *  				녹음곡 등록에 실패하였습니다.[ERROR:2]
	 *  				녹음곡 아이디 생성에 실패하였습니다.
	 *  				삭제 권한이 없습니다.
	 *  				게시물의 내용을 등록하세요.
	 *  				수신차단된 회원입니다.
	 *  				금지어는 등록 할 수 없습니다.
	 *  				사용자가 많습니다. 다시 시도해 주세요.
	 *  				결과가 존재하지 않습니다.
	 *  99980	팝업 메시지	컨펌 팝업	KP_1013 - 반주곡 재생	1분 미리듣기만 가능합니다.\n보유홀릭 : 10Holic\n 10Holic으로 1시간 이용권이 가능합니다.\nHolic을 이용하시겠습니까?
	 *  99981	팝업 메시지	컨펌 팝업	KP_1013 - 반주곡 재생	보유홀릭 : 1Holic\n1분 미리듣기만 가능합니다\nHolic을 무료충전하시겠습니까?
	 *  99994	메인 팝업 이미지	메인 팝업(이벤트/알림) 이미지 오픈	KP_0001 - 메인 정보	
	 *  99996	팝업 메시지	팝업 - 마켓 리뷰로 이동 기능		[이벤트]마켓에 리뷰 작성하기
	 *  99997	팝업 메시지	컨펌 팝업		최신 버전이 존재합니다. 업데이트하시겠습니까?
	 *  99998	팝업 메시지	팝업 - 강제 업데이트 처리		KPOP을 이용하기 위해서는 업데이트해야 합니다
	 *  2.1. Holic으로 상품구매가 가능할 경우
	 *  - r_code    : "99980"
	 *  - r_message    : "보유홀릭 : 100Holic\n100Holic으로 7일 이용이 가능합니다.\nHolic을 이용하시겠습니까?"
	 *  - 버튼 : "Yes"/"No"
	 *      : "Yes" -> KP_4011 프로토콜 호출
	 *      : "No" -> 팝업 닫힘
	 *  2.2. Holic으로 상품구매가 불가능할 경우 
	 *  - r_code    : "99981"
	 *  - r_message    : "보유홀릭 : 1Holic\n1분 미리듣기만 가능합니다\nHolic을 무료충전하시겠습니까?"
	 *  - 버튼 : "Yes"/"No"
	 *      : "Yes" -> Ticket 목록 이동(KP_4001)
	 *      : "No" -> 팝업 닫힘                
	 *  KPOP HOLIC 메인 팝업 이미지 노출 건    * KP_0000 프로토콜의 "info" 에 팝업 이미지 출력 정보 추가. 
	 *  - r_code    : "99994"
	 *  1. "is_pop" - "Y" / "N" 
	 *  		- "Y" : 메인에 팝업 이미지 노출 
	 *  		- "N" : 메인에 팝업 이미지 미노출 
	 *  
	 *  2. "btn_type" - "0", "1", "2" 
	 *  		- "0" : "확인" 버튼 하단에 노출 
	 *  		- "1" : "닫기" 버튼 하단에 노출 
	 *  		- "2" : "확인", "닫기" 버튼 하단에 노출 
	 *  		
	 *  3. "url_img_pop" : 메인 팝업 이미지 URL 
	 *  
	 *  4. "url_pop" : 메인 팝업 이미지의 "확인" 버튼 선택 시 링크 주소 
	 *  
	 *  
	 *  -- "닫기" 버튼 선택 시는 팝업 이미지만 닫음.  	 *
	 * </pre>
	 */
	protected void KPnnnnResult(final int what, final String p_opcode, final String r_code, final String r_message, final String r_info) {

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + p_opcode
		// + "[RESULT_CODE]" + r_code);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[WHAT]" + what);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[OPCODE]" + p_opcode);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[RESULT_CODE]" + r_code);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[RESULT_MESSAGE]" + r_message);

		if (what == _IKaraoke.STATE_DATA_QUERY_START) {
			return;
		}

		try {
			if (!TextUtil.isEmpty(r_info)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[RESULT_INFO][OK]\n" + (new KPItem(r_info)).toString(2));
			} else {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[RESULT_INFO][NG]" + r_info);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		boolean stopLoading = true;

		for (String s : KP_HIDE_LOADING_STOP) {
			if (s.equalsIgnoreCase(p_opcode)) {
				stopLoading = false;
			}
		}

		if (stopLoading) {
			stopLoading(__CLASSNAME__, getMethodName() + p_opcode + ":" + r_code);
		}

		if (KP_nnnn == null) {
			return;
		}

		String title = getString(R.string.alert_title_error);
		String message = getString(R.string.errmsg_network_data);

		KPItem info = null;
		try {
			info = new KPItem(r_info);
		} catch (Exception e) {

			e.printStackTrace();

			message += "\n\n(" + p_opcode + ":" + _IKaraoke.ERROR_CODE_JSONDATAPARSINGERORR + ")";
			message += "\n" + Log2.getStackTraceString(e);

			if (_IKaraoke.DEBUG) {
				message += "\n\n[디버그정보]\n" + Log2.getStackTraceString(e);
			}

			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_retry), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					refresh();
				}
			}, null, null, true, null);
		}

		String value = "";
		if (info != null) {
			value = info.getValue("result_url");
		}
		final String result_url = value;

		if (info != null) {
			value = info.getValue(LOGIN.KEY_GOODSCODE);
		}
		final String goodscode = value;

		// if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "\n" + (info != null ? info.toString(2) : info));

		if (("00000").equalsIgnoreCase(r_code)) {
		} else if (("00900").equalsIgnoreCase(r_code)) {
			// 링크연결팝업 (UCC)
			showURLLinkDialog(r_message, result_url);
		} else if (("00901").equalsIgnoreCase(r_code)) {
			// 단순공지팝업 (UCC)
			getApp().popupToast(r_message, Toast.LENGTH_LONG);
		} else if (("00902").equalsIgnoreCase(r_code)) {
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), r_message, result_url, "POST", false);
		} else if (("00903").equalsIgnoreCase(r_code)) {
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), r_message, result_url, "POST", true);
		} else if (("50001").equalsIgnoreCase(r_code)) {
		} else if (("40050").equalsIgnoreCase(r_code)) {
			// Holic으로 상품구매 오류발생시
			title = getString(R.string.alert_title_error);
			message = r_message;
			message += "\n(" + p_opcode + ":" + r_code + ")";
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
		} else if (("99980").equalsIgnoreCase(r_code)) {
			// 2.1. Holic으로 상품구매가 가능할 경우
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					startLoading(__CLASSNAME__, getMethodName());
					// KP_4003(goodscode, "", "", "HOLIC", "I");
					getApp().KP_4003(goodscode, "", "", "HOLIC", "I");
				}
			}, getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					if (BaseFragment2.this instanceof PlayFragment) {
						((_PlayFragment) BaseFragment2.this).play();
					}
				}
			}, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					if (BaseFragment2.this instanceof PlayFragment) {
						((_PlayFragment) BaseFragment2.this).play();
					}
				}
			});
		} else if (("99981").equalsIgnoreCase(r_code)) {
			// 2.2. Holic으로 상품구매가 불가능할 경우
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					onOptionsItemSelected(R.id.menu_shop_charge, "", true);
				}
			}, getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					if (BaseFragment2.this instanceof PlayFragment) {
						((_PlayFragment) BaseFragment2.this).play();
					}
				}
			}, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					if (BaseFragment2.this instanceof PlayFragment) {
						((_PlayFragment) BaseFragment2.this).play();
					}
				}
			});
		} else if (("99900").equalsIgnoreCase(r_code)) {
			// 로그인확인
			Bundle args = new Bundle();
			// args.putString("message", getString(R.string.warning_nologin_preview));
			args.putString("message", r_message);

			if (!isLoginUser()) {
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, args);
			}
		} else if (("99994").equalsIgnoreCase(r_code)) {
			// 프래그먼트활성화시...
			if (isVisible() && isResumed()) {
				// KPOP HOLIC 메인 팝업 이미지 노출 건
				onContextItemSelected(R.id.context_popup, KP_nnnn, KP_index, true);
			}
		} else if (("99995").equalsIgnoreCase(r_code)) {
			// 단순공지사항
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), null, null, null, true, null);
			// } else if (("99996").equalsIgnoreCase(r_code)) {
			// //리플입력유도
			// showDialogAlert(getString("alert_title_confirm"), r_message,
			// getString("alert_title_confirm"), mUpdatePListener, null, null, true, null);
			// } else if (("99997").equalsIgnoreCase(r_code)) {
			// //업데이트확인
			// showUpdateDialog(r_message);
		} else if (("99998").equalsIgnoreCase(r_code)) {
			// 강제업데이트
			getApp().popupToast(r_message, Toast.LENGTH_LONG);
			openUpdate(result_url);
		} else {

			String e_message = _IKaraoke.ERROR_MESSAGE_UNKOWNDATAERROR;
			if ((_IKaraoke.ERROR_CODE_IOEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_IOEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_UNKNOWNHOSTEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_UNKNOWNHOSTEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_UNKNOWNSERVICEEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_UNKNOWNSERVICEEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_TIMEOUTEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_TIMEOUTEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_SOCKETEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_SOCKETEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_HTTPRESPONSEEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_HTTPRESPONSEEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_JSONDATAPARSINGERORR).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_JSONDATAPARSINGERORR;
			} else if ((_IKaraoke.ERROR_CODE_UNKOWNDATAERROR).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_UNKOWNDATAERROR;
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "\n[ERROR_MESSAGE]" + e_message);

			message += "\n\n(" + p_opcode + ":" + r_code + ")";
			message += "\n" + e_message;

			if (info != null) {
				if (TextUtil.isEmpty(info.getValue("HttpStatusCode"))) {
					message += "\n\n(" + info.getValue("HttpStatusCode") + ")";
				}
				if (TextUtil.isEmpty(info.getValue("HttpStatusMessage"))) {
					message += "\n" + info.getValue("HttpStatusMessage");
				}
			}

			if (_IKaraoke.DEBUG) {
				message += "\n\n[디버그정보]\n" + r_message;
			}

			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_retry), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					refresh();
				}
			}, null, null, true, null);
		}

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + p_opcode + "[RESULT_CODE]" + r_code);

		KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		switch (what) {
		case _IKaraoke.STATE_DATA_QUERY_START:
			break;

		case _IKaraoke.STATE_DATA_QUERY_SUCCESS:
			KPnnnn();
			break;

		case _IKaraoke.STATE_DATA_QUERY_FAIL:
			break;

		case _IKaraoke.STATE_DATA_QUERY_ERROR:
			break;

		case _IKaraoke.STATE_DATA_QUERY_CANCEL:
			break;

		default:
			break;
		}

	}

	// @Override
	// public void onKPnnnnResult(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnStart(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnSuccess(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnFail(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnError(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnCancel(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnFinish(int what, String p_opcode, String r_code,
	// String r_message, KPItem r_info) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "info - " + (r_info == null ? r_info : r_info.toString(2)));
	//
	// }
	//
	// @Override
	// public void onKPnnnnProgress(long size, long total) {
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "size:" + size + ", total:" + total);
	//
	// }

	protected void sendMessage(Handler handler, int what, String opcode, String r_code, String r_message, String r_info) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.getData().putString("opcode", opcode);
		msg.getData().putString("result_code", r_code);
		msg.getData().putString("result_message", r_message);
		handler.sendMessage(msg);
	}

	/**
	 * UI처리용핸들러 Need handler for callbacks to the UI thread
	 */
	// final protected Handler mHandlerUI = new Handler();
	protected HandlerUI mHandlerUI;

	public static class HandlerUI extends Handler {
		WeakReference<BaseFragment2> m_HandlerObj;

		HandlerUI(BaseFragment2 handlerobj) {
			m_HandlerObj = new WeakReference<BaseFragment2>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseFragment2 handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			super.handleMessage(msg);
		}
	}

	public void post(Runnable r) {
		if (mHandlerUI != null) {
			mHandlerUI.post(r);
		}

	}

	public void postDelayed(Runnable r, long delayMillis) {
		if (mHandlerUI != null) {
			mHandlerUI.postDelayed(r, delayMillis);
		}

	}

	protected HandlerQuery mHandlerQuery;

	static class HandlerQuery extends Handler {
		WeakReference<BaseFragment2> m_HandlerObj;

		HandlerQuery(BaseFragment2 handlerobj) {
			m_HandlerObj = new WeakReference<BaseFragment2>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseFragment2 handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			handlerobj.onKPnnnnResult(msg);
		}
	}

	private void onKPnnnnResult(Message msg) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + msg);


		int what = msg.what;
		String opcode = msg.getData().getString("opcode");
		String r_code = msg.getData().getString("result_code");
		String r_message = msg.getData().getString("result_message");
		String r_info = msg.getData().getString("result_info");

		// 액티비티 연결여부확인
		if (isAttached()) {
			onKPnnnnResult(what, opcode, r_code, r_message, r_info);
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mHandlerUI = new HandlerUI(this);
		mHandlerQuery = new HandlerQuery(this);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + newConfig);

		super.onConfigurationChanged(newConfig);
		if (mQuickAction != null) {
			mQuickAction.dismiss();
		}
	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onPause();
	}

	@Override
	public void onResume() {

		super.onResume();

	}

	/**
	 * 데이터가 변경되어 이전화면을 업데이트해야 하는경우<br>
	 * 예) 이전화면(예:녹음곡)이 업데이트해야 할경우 호출해야해~~~<br>
	 * 
	 * @see IKaraoke3#KARAOKE_RESULT_CANCEL
	 * @see IKaraoke3#KARAOKE_RESULT_OK
	 * @see IKaraoke3#KARAOKE_RESULT_DEFAULT
	 * @see IKaraoke3#KARAOKE_RESULT_REFRESH
	 * @see IKaraoke3#KARAOKE_RESULT_PLAYING
	 * @see IKaraoke3#KARAOKE_RESULT_INFORM
	 * @see IKaraoke3#KARAOKE_RESULT_FINISH
	 */
	public final void setResult(int resultCode, Intent intent) {
		getBaseActivity().setResult(resultCode, intent);
	}

	/**
	 * <pre>
	 * 머지랄이래
	 * <a href="http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment">onActivityResult not being called in Fragment - Stack Overflow</a>
	 * <a href="http://stackoverflow.com/questions/20038880/onactivityresult-for-fragment">onActivityResult For Fragment - Stack Overflow</a>
	 * </pre>
	 * 
	 * 데이터가 변경되어 이전화면을 업데이트해야 하는경우<br>
	 * 예) 이전화면(예:녹음곡)이 업데이트해야 할경우 호출해야해~~~<br>
	 * 
	 * @see IKaraoke3#KARAOKE_RESULT_CANCEL
	 * @see IKaraoke3#KARAOKE_RESULT_OK
	 * @see IKaraoke3#KARAOKE_RESULT_DEFAULT
	 * @see IKaraoke3#KARAOKE_RESULT_REFRESH
	 * @see IKaraoke3#KARAOKE_RESULT_PLAYING
	 * @see IKaraoke3#KARAOKE_RESULT_INFORM
	 * @see IKaraoke3#KARAOKE_RESULT_FINISH
	 * @see #setResult(int resultCode, Intent intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * <pre>
	 * 머지랄이래
	 * <a href="http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment">onActivityResult not being called in Fragment - Stack Overflow</a>
	 * <a href="http://stackoverflow.com/questions/20038880/onactivityresult-for-fragment">onActivityResult For Fragment - Stack Overflow</a>
	 * </pre>
	 */
	@Override
	public void startActivity(final Intent intent) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + intent);

		// post(new Runnable() {
		// public void run() {
		// BaseFragment2.super.startActivity(intent);
		// }
		// });
		super.startActivity(intent);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[END]" + intent);
	}

	/**
	 * <pre>
	 * 머지랄이래
	 * <a href="http://stackoverflow.com/questions/6147884/onactivityresult-not-being-called-in-fragment">onActivityResult not being called in Fragment - Stack Overflow</a>
	 * <a href="http://stackoverflow.com/questions/20038880/onactivityresult-for-fragment">onActivityResult For Fragment - Stack Overflow</a>
	 * </pre>
	 */
	@Override
	public void startActivityForResult(final Intent intent, final int requestCode) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + intent + requestCode);
		super.startActivityForResult(intent, requestCode);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[END]" + intent + requestCode);
	}

	public Intent openYouTube(KPnnnn KP_xxxx, int index, boolean skym, boolean open) {

		Intent intent = null;

		try {
			if (KP_xxxx == null) {
				KP_xxxx = KP_nnnn;
			}

			KPItem list = KP_xxxx.getList(index);

			intent = openYouTube(list, skym, open);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return intent;
	}

	private Intent openYouTube(KPItem list, boolean skym, boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		Intent intent = null;

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		String url_vod = list.getValue("url_vod");
		if (skym) {
			url_vod = list.getValue("url_vod_skym");
		}
		String videoId = "";
		if (url_vod != null) {

			// 유튜브영상 아이디확인
			try {
				videoId = Uri.parse(url_vod).getQueryParameter("v");
			} catch (Exception e) {

				e.printStackTrace();
			}

			if (URLUtil.isNetworkUrl(url_vod)) {
				try {
					if (!TextUtil.isEmpty(videoId)) {
						intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
						intent.putExtra("VIDEO_ID", videoId);
					} else {
						intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_vod));
					}
					if (open) {
						startActivity(intent);
					}
				} catch (Exception e) {

					e.printStackTrace();
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_vod));
					if (open) {
						startActivity(intent);
					}
				}
			} else {
				getApp().popupToast(R.string.errmsg_url_vod, Toast.LENGTH_SHORT);
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\n[id]" + videoId + "\n[url]" + url_vod + "\n[uri]" + Uri.parse(url_vod));
		}

		return intent;
	}

	/**
	 * 카카오인텐트
	 */
	@Deprecated
	public Intent openKAKAOLINK(KPItem info, KPItem list, boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String title = getString(R.string.alert_title_error);
		String message = "KakaoTalk Error";
		String error = "\n\n[디버그정보]\n";

		// Recommended: Use application context for parameter.
		KakaoLink2 kakaoLink = KakaoLink2.getLink(getApp());

		// check, intent is available.
		if (!kakaoLink.isAvailableIntent()) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
			// alert("Not installed KakaoTalk.");
			getBaseActivity().showAlertDialog(title, "Not installed KakaoTalk.", getString(R.string.btn_title_confirm), null, null, null, true, null);
			return null;
		}

		Intent intent = openACTIONSHARE(info, list, false, null, null);

		// KakaoTalk으로 바로 보내시려면 아래 코드를 추가합니다.
		intent.setPackage("com.kakao.talk");

		try {
			if (open) {
				startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_SHARE);
			}

			getApp().KP_0000(getApp().p_mid, p_m1, p_m2, "KP_5022", true, new HashMap<String, String>());

		} catch (Exception e) {

			e.printStackTrace();
			error += Log2.getStackTraceString(e);
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);

		}

		return intent;
	}

	/**
	 * 카카오링크API
	 */
	public KakaoLink2 openKAKAOLINK(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String title = getString(R.string.alert_title_error);
		String message = "KakaoTalk Error";
		String error = "\n\n[디버그정보]\n";

		if (info == null) {
			error += "info - " + info;
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "info - " + info);
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
			return null;
		}

		if (list == null) {
			error += "list - " + list;
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "list - " + list);
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
			return null;
		}

		try {
			KPItem item = new KPItem(list.getValue("kakao_metainfo"));

			// item = new KPItem(item.getString("metainfo"));

			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - \n" + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - \n" + list.toString(2));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_metainfo - \n" + item.toString(2));

			ArrayList<Map<String, String>> metaInfoArray = new ArrayList<Map<String, String>>();

			// If application is support Android platform.
			Map<String, String> metaInfoAndroid = new Hashtable<String, String>(1);
			metaInfoAndroid.put("os", item.getValue("os"));
			metaInfoAndroid.put("devicetype", item.getValue("devicetype"));
			metaInfoAndroid.put("installurl", item.getValue("installurl"));
			metaInfoAndroid.put("executeurl", item.getValue("executeurl"));

			// If application is support ios platform.
			// Map<String, String> metaInfoIOS = new Hashtable<String, String>(1);
			// metaInfoIOS.put("os", "ios");
			// metaInfoIOS.put("devicetype", "phone");
			// metaInfoIOS.put("installurl", "your iOS app install url");
			// metaInfoIOS.put("executeurl", "kakaoLinkTest://starActivity");

			// add to array
			metaInfoArray.add(metaInfoAndroid);
			// metaInfoArray.add(metaInfoIOS);

			// Recommended: Use application context for parameter.
			KakaoLink2 kakaoLink = KakaoLink2.getLink(getApp());

			// check, intent is available.
			if (!kakaoLink.isAvailableIntent()) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
				// alert("Not installed KakaoTalk.");
				getBaseActivity().showAlertDialog(title, "Not installed KakaoTalk.", getString(R.string.btn_title_confirm), null, null, null, true, null);
				return null;
			}

			String kakao_appname = list.getValue("kakao_appname");

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_type:" + list.getValue("kakao_type"));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_url:" + list.getValue("kakao_url"));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_msg:" + list.getValue("kakao_msg"));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "appId:" + getPackageName());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "appVer:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "appName:" + kakao_appname);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "encoding:" + "UTF-8");

			if (("app").equalsIgnoreCase(list.getValue("kakao_type"))) {
				kakaoLink.openKakaoAppLink(getBaseActivity(), list.getValue("kakao_url"), list.getValue("kakao_msg"), getPackageName(),
						getPackageManager().getPackageInfo(getPackageName(), 0).versionName, kakao_appname, "UTF-8", metaInfoArray);
			} else {
				kakaoLink.openKakaoLink(getBaseActivity(), list.getValue("kakao_url"), list.getValue("kakao_msg"), getPackageName(), getPackageManager()
						.getPackageInfo(getPackageName(), 0).versionName, kakao_appname, "UTF-8");
			}

			getApp().KP_0000(getApp().p_mid, p_m1, p_m2, "KP_5022", true, new HashMap<String, String>());

			return kakaoLink;
		} catch (Exception e) {

			e.printStackTrace();
			error += Log2.getStackTraceString(e);
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);

			return null;
		}

	}

	/**
	 * 카카오스토리인텐트
	 */
	public Intent openKAKAOSTORY(KPItem info, KPItem list, boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String title = getString(R.string.alert_title_error);
		String message = "KakaoStory Data Error";
		String error = "\n\n[디버그정보]\n";

		// Recommended: Use application context for parameter.
		StoryLink2 storyLink = StoryLink2.getLink(getApp());

		// check, intent is available.
		if (!storyLink.isAvailableIntent()) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
			// alert("Not installed KakaoStory.");
			getBaseActivity().showAlertDialog(title, "Not installed KakaoStory.", getString(R.string.btn_title_confirm), null, null, null, true, null);
			return null;
		}

		Intent intent = openACTIONSHARE(info, list, false, null, null);

		// Kakao Story로 바로 보내시려면 아래 코드를 추가합니다.
		intent.setPackage("com.kakao.story");

		try {
			if (open) {
				startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_SHARE);
			}

			getApp().KP_0000(getApp().p_mid, p_m1, p_m2, "KP_5022", true, new HashMap<String, String>());

		} catch (Exception e) {

			e.printStackTrace();
			error += Log2.getStackTraceString(e);
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);

		}

		return intent;
	}

	/**
	 * 카카오스토리API
	 */
	@SuppressWarnings("unused")
	public StoryLink2 openKAKAOSTORY(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		String title = getString(R.string.alert_title_error);
		String message = "KakaoStory Data Error";
		String error = "\n\n[디버그정보]\n";

		if (info == null) {
			error += "info - " + info;
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "info - " + info);
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
			return null;
		}

		if (list == null) {
			error += "list - " + list;
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "list - " + list);
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
			return null;
		}

		try {
			KPItem item = new KPItem(list.getValue("kakao_metainfo"));

			if (item == null) {
				error += "kakao_metainfo - " + item;
				if (_IKaraoke.DEBUG) {
					message += error;
				}
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_metainfo - " + item);
				getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
				return null;
			}

			// item = new KPItem(item.getString("metainfo"));

			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - \n" + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - \n" + list.toString(2));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_metainfo - \n" + item.toString(2));

			Map<String, Object> urlInfoAndroid = new Hashtable<String, Object>(1);
			urlInfoAndroid.put("title", item.getValue("title"));
			urlInfoAndroid.put("desc", item.getValue("desc"));
			// urlInfoAndroid.put("imageurl", new String[] {"http://m1.daumcdn.net/photo-media/201209/27/ohmynews/R_430x0_20120927141307222.jpg"});
			urlInfoAndroid.put("imageurl", new String[] { item.getValue("imageurl") });
			urlInfoAndroid.put("type", item.getValue("type"));

			// Recommended: Use application context for parameter.
			StoryLink2 storyLink = StoryLink2.getLink(getApp());

			// check, intent is available.
			if (!storyLink.isAvailableIntent()) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
				// alert("Not installed KakaoStory.");
				getBaseActivity().showAlertDialog(title, "Not installed KakaoStory.", getString(R.string.btn_title_confirm), null, null, null, true, null);
				return null;
			}

			String kakao_appname = list.getValue("kakao_appname");

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_type:" + list.getValue("kakao_type"));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_url:" + list.getValue("kakao_url"));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "kakao_msg:" + list.getValue("kakao_msg"));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "appId:" + getPackageName());
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "appVer:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "appName:" + kakao_appname);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "encoding:" + "UTF-8");

			if (("image").equalsIgnoreCase(list.getValue("kakao_type"))) {
				storyLink.openStoryLinkImageApp(getBaseActivity(), list.getValue("kakao_url"));
			} else {
				storyLink.openKakaoLink(getBaseActivity(), list.getValue("kakao_msg"), getPackageName(), getPackageManager().getPackageInfo(getPackageName(), 0).versionName,
						kakao_appname, "UTF-8", urlInfoAndroid);
			}

			getApp().KP_0000(getApp().p_mid, p_m1, p_m2, "KP_5022", true, new HashMap<String, String>());

			return storyLink;
		} catch (Exception e) {

			e.printStackTrace();
			error += Log2.getStackTraceString(e);
			if (_IKaraoke.DEBUG) {
				message += error;
			}
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);

			return null;
		}
	}

	/**
	 * 공유하기 SNS/메일 호출 : {@link PlayFragment.onActivityResult(int requestCode, int resultCode, Intent
	 * data)}
	 * 
	 * @param list2
	 *          조회된 결과 데이터사용시
	 */
	public Intent openACTIONSHARE(KPItem info, KPItem list, boolean open, String packageName, String className) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + packageName + className);

		KPItemShareData shareData = new KPItemShareData(info, list);

		String uid = shareData.uid;
		String song_id = shareData.song_id;
		String record_id = shareData.record_id;
		String title = shareData.title;
		String artist = shareData.artist;
		String name = shareData.name;
		String url = shareData.url;

		if (TextUtil.isEmpty(url)) {
			// url = "market://details?id=" + getBaseActivity().getPackageName();
			url = "https://play.google.com/store/apps/details?id=" + getBaseActivity().getPackageName();
		}

		if (TextUtil.isEmpty(name)) {
			name = getApp().p_nickname;
		}

		Bundle args = new Bundle();

		// 국가별SNS기능차단
		if (("CN").equalsIgnoreCase(getApp().mLocale.getCountry())) {
			args.putString("message", getString(R.string.errmsg_noservice_country));
			getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
			return null;
		}

		// 공유 SNS연계
		String text = "";
		String comment = "";
		// comment = "[" + hello + "]";

		if (!TextUtil.isEmpty(name)) {
			comment += String.format(getString(R.string.context_format_share_record_name), name);
		}
		if (!TextUtil.isEmpty(title)) {
			text += title;
		}
		if (!TextUtil.isEmpty(artist)) {
			text += " - " + artist;
		}
		if (!TextUtil.isEmpty(text)) {
			comment += " " + String.format(getString(R.string.context_format_share_record_song), text);
		}

		Intent intent = null;
		intent = new Intent(Intent.ACTION_SEND);

		// 필공유시...만...
		if (!TextUtil.isEmpty(packageName) && !TextUtil.isEmpty(className)) {
			intent.setClassName(packageName, className);
			Bundle extras = new Bundle();
			extras.putParcelable(_IKaraoke.KEY_KPOP_INFOITEM, info);
			extras.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);
			intent.putExtras(extras);
		}

		// 일반공유데이터...
		intent.setAction(Intent.ACTION_SEND);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(Intent.EXTRA_SUBJECT, comment);
		intent.putExtra(Intent.EXTRA_TITLE, comment);
		if (!TextUtil.isEmpty(url)) {
			url.replace("\\", "");
			comment += " - " + url.trim();
		}
		intent.putExtra(Intent.EXTRA_TEXT, comment);
		intent.setType("text/plain");

		if (open) {
			startActivityForResult(Intent.createChooser(intent, "Share/SNS/Mail/ETC"), _IKaraoke.KARAOKE_INTENT_ACTION_SHARE);
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + comment + uid + song_id + record_id);

		return intent;

	}

	protected String getMenuName() {
		String menu_name = "";

		try {
			if (mList != null) {
				menu_name = mList.getValue("menu_name");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + menu_name);
		return menu_name;
	}

	public boolean onSearchRequested() {
		stopLoading(__CLASSNAME__, getMethodName());
		return getBaseActivity().onSearchRequested();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getResourceEntryName(item.getItemId()) + ":" + item);

		// popupToast(text, Toast.LENGTH_SHORT);

		Intent intent = null;

		if (item != null) {
			intent = onOptionsItemSelected(item.getItemId(), "", true);
		}

		if (intent == null) {
			return super.onOptionsItemSelected(item);
		} else {
			return true;
		}
	}

	/**
	 * <pre>
	 * 옵션메뉴선택시 다음화면에 전달될 인텐트생성 후 화면이동
	 * <a href="http://stackoverflow.com/questions/18843118/launch-android-application-from-library-project">Launch Android application from Library project - Stack Overflow</a>
	 * <a href="http://stackoverflow.com/questions/6468319/onactivityresult-onresume">android - onActivityResult() & onResume() - Stack Overflow</a>
	 * </pre>
	 */
	protected Intent onOptionsItemSelected(int id, String menu_name, boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + getResourceEntryName(id) + ":" + menu_name);

		// 온스팟상품내용숨김
		if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(getBaseActivity().getPackageName())) {
			if (id == R.id.menu_shop_ticket) {
				return null;
			}
		}

		if (getActivity() == null) {
			return null;
		}

		if (getBaseActivity().isLoadingDialog) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[EXIT]" + getResourceEntryName(id) + ":" + menu_name);
			return null;
		}

		if (open) {
			getBaseActivity().startLoadingDialog(null);
		}

		String m1 = p_m1;
		String m2 = p_m2;
		String title = "";

		Class<?> cls = null;

		int requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_DEFAULT;

		// 비로그인사용자화면이동시로그인처리요청
		if (!isLoginUser()) {
			requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_LOGIN;
		}

		KPItem info = new KPItem();
		KPItem list = new KPItem();

		Bundle extras = new Bundle();

		int tabIndex = 0;

		if (id == R.id.menu_refresh) {
			// 리플레쉬
			getBaseActivity().refresh();
			return null;
		} else if (id == android.R.id.home || id == R.id.menu_home) {
			// [출처] Activity 관리 : 어플내 홈버튼 클릭시 어플 메인으로 이동 |작성자 아즈라엘
			// m1 = getString(R.string.M1_MAIN);
			// m2 = getString(R.string.M2_MENU);
			// menu_name = getString(R.string.menu_home);
			// cls = home.class;
			Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
			if (open) {
				// startActivityForResult(launchIntent, requestCode);
				startActivityForResult(launchIntent, _IKaraoke.KARAOKE_INTENT_ACTION_DEFAULT);
			}
			return launchIntent;
		} else if (id == R.id.menu_notice) {
			// 공지사항
			m1 = getString(R.string.M1_MENU_INFO);
			m2 = getString(R.string.M2_MENU_NOTICE);
			title = getString(R.string.menu_notice);
			cls = noticelist.class;
		} else if (id == R.id.menu_search) {
			// 검색
			m1 = getString(R.string.M1_MENU_INFO);
			m2 = getString(R.string.M2_MENU_NOTICE);
			title = "";
			onSearchRequested();
			return null;
		} else if (id == R.id.menu_feel) {
			// FEEL
			m1 = getString(R.string.M1_MENU_FEEL);
			m2 = getString(R.string.M2_FEEL_LIST);
			title = getString(R.string.menu_feel);
			cls = feeltab.class;
			// cls = playtab.class;
		} else if (id == R.id.menu_comment) {
			// FEEL작성
			if (isLoginUser()) {
				openFeelPost("ADD", KP_nnnn, KP_index);
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
			return null;
		} else if (id == R.id.menu_share) {
			// 공유
			openACTIONSHARE(KP_nnnn.getInfo(), getList(), true, null, null);
			return null;
		} else if (id == R.id.menu_sing) {
			// SING
			m1 = getString(R.string.M1_MENU_SING);
			m2 = getString(R.string.M1_MENU_SING);
			title = getString(R.string.menu_sing);
			cls = singtab.class;
			// cls = playtab.class;
		} else if (id == R.id.menu_listen) {
			// LISTEN
			m1 = getString(R.string.M1_MENU_LISTEN);
			m2 = getString(R.string.M1_MENU_LISTEN);
			title = getString(R.string.menu_listen);
			cls = listentab.class;
			// cls = playtab.class;
		} else if (id == R.id.menu_audition) {
			// AUDITION
			m1 = getString(R.string.M1_MENU);
			m2 = getString(R.string.M2_MENU_AUDITION);
			title = getString(R.string.menu_audition);
			cls = auditiontab.class;
			// cls = playtab.class;
		} else if (id == R.id.menu_audition_open) {
			m1 = getString(R.string.M1_MENU);
			m2 = getString(R.string.M2_MENU_AUDITION);
			title = getString(R.string.menu_audition_open);
			// AUDITION개최
			if (isLoginUser()) {
				String url = KP_nnnn.getHostPath() + "protocol/web/KP_6030.php?";
				String param = KP_nnnn.getParams();
				openWebView(webview.class, getString(R.string.M1_MENU_AUDITION), getString(R.string.M2_AUDITION_PARTICIPATI), getString(R.string.menu_audition_open), url + param, "POST",
						false);
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_webview_open) {
			title = list.getValue("menu_name");
			String url_contents = list.getValue("url_contents");
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_NOTICE), title, url_contents, "POST", false);
			return null;
		} else if (id == R.id.menu_myholic) {
			// MY HOLIC
			m1 = getString(R.string.M1_MENU_MYHOLIC);
			m2 = getString(R.string.M1_MENU_MYHOLIC);
			title = getString(R.string.menu_myholic);
			// list.putString("id", null);
			if (isLoginUser()) {
				cls = myholic.class;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
			// } else if (id == R.id.menu_ticket) {
			// // TICKET
			// m1 = getString(R.string.M1_MENU_TICKET);
			// m2 = getString(R.string.M2_MENU_TICKET);
			// menu_name = getString(R.string.menu_ticket);
			// if (isLoginUser()) {
			// cls = ticketlist.class;
			// } else {
			// extras.putString("message", getString(R.string.errmsg_login));
			// getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			// }
		} else if (id == R.id.menu_shop || id == R.id.menu_shop_ticket || id == R.id.menu_shop_charge) {
			// SHOP
			m1 = getString(R.string.M1_MENU_SHOP);
			m2 = getString(R.string.M2_MENU_SHOP);
			title = getString(R.string.menu_shop_ticket);
			if (isLoginUser()) {
				cls = shop.class;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
			if (id == R.id.menu_shop_ticket) {
				// SHOP(이용권)
				tabIndex = 0;
			} else if (id == R.id.menu_shop_charge) {
				// SHOP(충전)
				tabIndex = 1;
			}
		} else if (id == R.id.menu_setting) {
			// 설정
			m1 = getString(R.string.M2_MENU_SETTING);
			m2 = getString(R.string.M2_MENU_SETTING);
			title = getString(R.string.menu_setting);
			if (isLoginUser()) {
				cls = setting.class;
				requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_setting_notification) {
			// 설정
			if (isLoginUser()) {
				cls = settingnotification.class;
				requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_profile) {
			// 설정
			if (isLoginUser()) {
				cls = profile.class;
				requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_profile_edit) {
			// 설정
			if (isLoginUser()) {
				showProfileEdit(open);
				return null;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_reserve) {
		} else if (id == R.id.menu_mysing) {
			// 애창곡 목록
			if (isLoginUser()) {
				cls = singlist.class;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_record) {
			// 나의 녹음곡
			m1 = getString(R.string.M1_MENU_MYHOLIC);
			m2 = getString(R.string.M2_MY_REC);
			title = getString(R.string.menu_mylisten);
			if (isLoginUser()) {
				cls = listenlist.class;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_iap_msg_buying) {
			// 구매곡
			if (isLoginUser()) {
				cls = listenlist.class;
			} else {
				extras.putString("message", getString(R.string.errmsg_login));
				getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, extras);
			}
		} else if (id == R.id.menu_login) {
			// 로그인
			m1 = getString(R.string.M1_LOGIN);
			m2 = getString(R.string.M2_LOGIN);
			title = getString(R.string.menu_login);
			cls = login.class;
			requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_LOGIN;
		} else if (id == R.id.menu_logout) {
			// 로그아웃
			m1 = getString(R.string.M1_LOGOUT);
			m2 = getString(R.string.M2_LOGOUT);
			title = getString(R.string.menu_logout);
			putLogout();
		} else if (id == R.id.menu_add) {
		} else {
		}

		if (TextUtil.isEmpty(menu_name)) {
			menu_name = title;
		}

		info.putValue(LOGIN.KEY_MID, getApp().p_mid);
		list.putValue("m1", m1);
		list.putValue("m2", m2);
		list.putValue("menu_name", menu_name);
		list.putValue("uid", getApp().p_mid);

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		extras.putParcelable(_IKaraoke.KEY_KPOP_INFOITEM, info);
		extras.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);

		// 위에서 만든 Bundle을 인텐트에 넣는다.
		Intent nIntent = null;

		if (cls != null) {
			nIntent = new Intent(mContext, cls);
			nIntent.putExtras(extras);
			nIntent.putExtra("tabIndex", tabIndex);
		}

		if (nIntent != null) {

			// 동일메뉴여부확인
			if (getBaseActivity().getClass() == cls) {
				// if (p_m1.equalsIgnoreCase(m1)) {
				// refresh();
				// return null;
				// } else {
				// close();
				// }
				// refresh();
			}

			// 메인인경우 - 액티비티순서변경
			if (Intent.ACTION_MAIN.equalsIgnoreCase(nIntent.getAction())) {
				// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				nIntent.setAction(Intent.ACTION_MAIN);
			}

			// 옵션메뉴호출화면하나이기때문에...
			// //로그인중복호출방지
			// if (this instanceof LoginFragment) {
			// stopLoading(__CLASSNAME__, getMethodName());
			// return null;
			// }
			nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

			if (open) {
				startActivityForResult(nIntent, requestCode);
			}
		}

		return nIntent;
	}

	protected void openListenPost(KPnnnn KP_xxxx, int index) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		// FEEL작성중복막기
		if (this instanceof ListenPostFragment) {
			return;
		}

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		Intent intent = putIntentData(listenpost.class, KP_xxxx, index);

		if (intent != null) {
			// 액티비티
			startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH);
		}

	}

	protected void openListenPost2(KPnnnn KP_xxxx, int index) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		// FEEL작성중복막기
		if (this instanceof ListenPostFragment2) {
			return;
		}

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		Intent intent = putIntentData(listenpost2.class, KP_xxxx, index);

		if (intent != null) {
			// 액티비티
			startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH);
		}

	}

	/**
	 * <pre>
	 * FEEL신규작성
	 * 녹음곡리스트/공유필리스트에서 녹음곡 강제공유오류
	 * 컨텍스트 메뉴가 아닌경우 반주곡/녹음곡 재생화면외 곡공유불가.
	 * </pre>
	 */
	protected void openFeelPost(String mode, KPnnnn KP_xxxx, int index) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mode);

		// FEEL작성중복막기
		if (this instanceof FeelPostFragment) {
			stopLoading(__CLASSNAME__, getMethodName());
			return;
		}

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		Intent intent = putIntentData(feelpost.class, KP_xxxx, index);

		if (intent == null) {
			stopLoading(__CLASSNAME__, getMethodName());
			return;
		}

		Bundle extras = intent.getExtras();

		KPItem info = extras.getParcelable(_IKaraoke.KEY_KPOP_INFOITEM);
		// ArrayList<KPItem> lists = extras.getParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS);
		KPItem list = extras.getParcelable(_IKaraoke.KEY_KPOP_LISTITEM);

		String feel_type = list.getValue("feel_type");
		if (TextUtil.isEmpty(feel_type)) {
			list.putValue("feel_type", "T");
		}

		// 필리스트화면작성시
		if (this instanceof _FeelListFragment) {
			list.putValue("feel_type", "T");
		}

		list.putValue("mode", mode);

		// 신규작성
		if (("ADD").equalsIgnoreCase(mode)) {
			list.putValue("feel_id", "");
			list.putValue("comment", "");
			list.putValue("url_comment", "");
			list.putValue("furl", "");
			// 컨텍스트 메뉴가 아닌경우 반주곡/녹음곡 재생화면외 곡공유불가.
			if (/* !context && */!(this instanceof PlayFragment)) {
				list.putValue("record_id", "");
				list.putValue("song_id", "");
			}
		}

		list.putValue("m1", getString(R.string.M1_MENU_FEEL));
		list.putValue("m2", getString(R.string.M2_MENU_FEEL));
		list.putValue("menu_name", getString(R.string.menu_feel));

		if (list != null) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "list - " + list.toString(2));
		}

		extras.putParcelable(_IKaraoke.KEY_KPOP_INFOITEM, info);
		// extras.putParcelableArrayList(_IKaraoke.KEY_KPOP_LISTITEMS, lists);
		extras.putParcelable(_IKaraoke.KEY_KPOP_LISTITEM, list);

		intent.putExtras(extras);

		// 다이얼로그
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		// android.support.v4.app.FragmentTransaction ft = getBaseActivity().getSupportFragmentManager()
		// .beginTransaction();
		// android.support.v4.app.Fragment prev = getBaseActivity().getSupportFragmentManager()
		// .findFragmentByTag("dialog");
		// if (prev != null) {
		// ft.remove(prev);
		// }
		// ft.addToBackStack(null);
		// Create and show the dialog.
		// BaseFragment fragment = null;
		// fragment = FeelPostFragment.newInstance(extras);
		// fragment.show(ft, "dialog");

		if (intent != null) {
			// 액티비티
			startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH);
		}

	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onOptionsMenuClosed(android.view.Menu)
	 */
	@Override
	public void onOptionsMenuClosed(Menu menu1) {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onOptionsMenuClosed(menu1);
	}

	protected MenuItem setContextMenuItemVisible(MenuItem item, boolean visible) {
		if (item != null) {
			item.setVisible(visible);
			item.setEnabled(visible);

			// String text = getResourceEntryName(item.getItemId());
			// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + text + ":" +
			// item.isVisible() + "," + visible);

			// if (android.os.Build.VERSION.SDK_INT >= 11) {
			// if (visible) {
			// MenuItemCompat.setShowAsAction(item, MenuItem.SHOW_AS_ACTION_ALWAYS);
			// } else {
			// MenuItemCompat.setShowAsAction(item, MenuItem.SHOW_AS_ACTION_NEVER);
			// }
			// }
		}
		return item;
	}

	public void openMarketNReward(final String info, final String list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	}

	/**
	 * 안드로이드마켓오픈<br>
	 * 무료충전소오픈
	 */
	public void openMarketNReward(final KPItem info, final KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {

			if (list == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
				return;
			}

			final String menu_name = !TextUtil.isEmpty(list.getValue("menu_name")) ? list.getValue("menu_name") : getMenuName();
			final String url_contents = list.getValue("url_contents");
			final String type = list.getValue("type");
			final String goodscode = list.getValue(LOGIN.KEY_GOODSCODE);

			String message = String.format(getString(R.string.warning_iap_msg_buy_holic_confirm), list.getValue(LOGIN.KEY_GOODSNAME));
			if (!TextUtil.isEmpty(list.getValue("btn_message"))) {
				message = list.getValue("btn_message");
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + type + ":" + url_contents);

			getApp().isRefresh = true;

			if (URLUtil.isNetworkUrl(url_contents)) {
				// 소액결재/쿠폰결재
				openWebView(webinipay.class, p_m1, p_m2, menu_name, url_contents, "POST", false);
			} else {
				if (("MARKET").equalsIgnoreCase(type)) {
					getApp().isRefresh = false;
					requestGoogleINAPP(info, list);
				} else if (("GOOGLE").equalsIgnoreCase(type)) {
					getApp().isRefresh = false;
					requestGoogleINAPP(info, list);
				} else if (("SAMSUNG").equalsIgnoreCase(type)) {
					requestSamsungINAPP(info, list);
				} else if (("TABJOY").equalsIgnoreCase(type) || ("TAPJOY").equalsIgnoreCase(type)) {
					((BaseAdActivity) getBaseActivity()).initAdTapjoyReward();
					((BaseAdActivity) getBaseActivity()).showAdListTapjoy();
				} else if (("ADDPOPCORN").equalsIgnoreCase(type)) {
					// getApp().showAdPopListAdPopCorn();
				} else if (("TNKFACTORY").equalsIgnoreCase(type)) {
					((BaseAdActivity) getBaseActivity()).initAdTNK();
					((BaseAdActivity) getBaseActivity()).showAdPopListTNK(list);
				} else if (("METAPS").equalsIgnoreCase(type)) {
					((BaseAdActivity) getBaseActivity()).initAdMetaps();
					((BaseAdActivity) getBaseActivity()).showAdPopListMeTaps();
				} else if (("TAPAD").equalsIgnoreCase(type)) {
					// getApp().showAdPopListTapAd();
				} else if (("FLURRY").equalsIgnoreCase(type)) {
					// getApp().showAdPopListFlurry();
				} else if (("W3I").equalsIgnoreCase(type)) {
					// getApp().showAdPopListW3i();
				} else if (("SPONSORPAY").equalsIgnoreCase(type)) {
					// getApp().showAdPopListSponsorPay();
				} else if (("KAKAOTALK").equalsIgnoreCase(type)) {
					onContextItemSelected(mContext, R.id.context_kakaotalk_link, info, list, true);
				} else if (("HOLIC").equalsIgnoreCase(type) || ("COIN").equalsIgnoreCase(type)) {
					getBaseActivity().showAlertDialog(
							//아이콘
							android.R.attr.alertDialogIcon,
							//타이틀
							getString(R.string.alert_title_confirm),
							//메시지
							message,
							//확인
							getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									getApp().KP_4003(goodscode, "", "", type, "I");
								}
							},
							//거부
							getString(R.string.btn_title_no), null,
							//취소
							true, null);
				} else {
					throw new Exception("type - error:" + type);
				}
			}

			getApp().KP_4012(getApp().p_mid, p_m1, p_m2, type);

		} catch (Exception e) {

			Log2.e(__CLASSNAME__, getMethodName() + Log2.getStackTraceString(e));
			e.printStackTrace();
			String message = getString(R.string.iap_msg_cannot_connect_message);
			if (_IKaraoke.DEBUG) {
				message += "\n\n[디버그정보]\n" + Log2.getStackTraceString(e);
			}

			getBaseActivity().showAlertDialog(
					//아이콘
					android.R.attr.alertDialogIcon,
					//타이틀
					getString(R.string.iap_msg_cannot_connect_title),
					//메시지
					message,
					//확인
					getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							openMarketNReward(info, list);
						}
					},
					//거부
					getString(R.string.btn_title_cancel), null,
					//취소
					true, null);
		}

	}

	/**
	 * 안드로이드마켓-인앱결제
	 */
	public void requestGoogleINAPP(String info, String list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			requestGoogleINAPP(new KPItem(info), new KPItem(list));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 안드로이드마켓-인앱결제
	 */
	public void requestGoogleINAPP(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			getApp().requestGoogleINAPP(info, list);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	/**
	 * 삭제:삼성인앱마켓-인앱결제
	 */
	@Deprecated
	public void requestSamsungINAPP(String info, String list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			requestSamsungINAPP(new KPItem(info), new KPItem(list));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 삭제:삼성인앱마켓-인앱결제
	 */
	@Deprecated
	public void requestSamsungINAPP(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	}

	/**
	 * DialogFragment스택수확인용
	 */
	int mStackLevel = 0;

	/**
	 * 
	 * <pre>
	 * 프로필편집이동
	 * </pre>
	 * 
	 */
	//void showProfileEdit(int index) {
	//	if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	//
	//	mStackLevel++;
	//
	//	// DialogFragment.show() will take care of adding the fragment
	//	// in a transaction. We also want to remove any currently showing
	//	// dialog, so make our own transaction and take care of that here.
	//	android.support.v4.app.FragmentTransaction ft = getBaseActivity().getSupportFragmentManager().beginTransaction();
	//	android.support.v4.app.Fragment prev = getBaseActivity().getSupportFragmentManager().findFragmentByTag("dialog");
	//	if (prev != null) {
	//		ft.remove(prev);
	//	}
	//	ft.addToBackStack(null);
	//
	//	// 액티비티
	//	KPItem info = KP_nnnn.getInfo();
	//	info.putValue("type", "change");
	//	startActivityForResult(putIntentData(profile.class, null, KP_index), _IKaraoke.KARAOKE_RESULT_REFRESH);
	//
	//}
	protected Intent showProfileEdit(boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		mStackLevel++;

		Intent intent = null;

		try {

			intent = putIntentData(profile.class, null, 0);

			// DialogFragment.show() will take care of adding the fragment
			// in a transaction. We also want to remove any currently showing
			// dialog, so make our own transaction and take care of that here.
			android.support.v4.app.FragmentTransaction ft = getBaseActivity().getSupportFragmentManager().beginTransaction();
			android.support.v4.app.Fragment prev = getBaseActivity().getSupportFragmentManager().findFragmentByTag("dialog");

			if (prev != null) {
				ft.remove(prev);
			}

			ft.addToBackStack(null);

			// 액티비티
			KPItem info = KP_nnnn.getInfo();
			info.putValue("type", "change");

			if (open) {
				startActivityForResult(intent, _IKaraoke.KARAOKE_RESULT_REFRESH);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return intent;
	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onContextItemSelected(android.view.MenuItem )
	 * 
	 *      This hook is called whenever an item in a context menu is selected. The default
	 *      implementation simply returns false to have the normal processing happen (calling the
	 *      item's Runnable or sending a message to its Handler as appropriate). You can use this
	 *      method for any items for which you would like to do processing without those other
	 *      facilities. Use getMenuInfo() to get extra information set by the View that added this
	 *      menu item. Derived classes should call through to the base class for it to perform the
	 *      default menu handling.
	 * 
	 *      Parameters item The context menu item that was selected.
	 * 
	 *      Returns boolean Return false to allow normal context menu processing to proceed, true to
	 *      consume it here.
	 */
	@Override
	final public boolean onContextItemSelected(MenuItem item) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + KP_index + ", " + getResourceEntryName(item.getItemId()));


		boolean ret = true;

		// Return false to allow normal context menu processing to proceed, true to consume it here.
		// 동일 엑티비티에서 다수의 프레그먼트가 존재하면 이벤트가 프레그먼트 배열 순서대로 전달된다. 시~발~
		if (this == getBaseActivity().mContextFragment) {
			onContextItemSelected(item.getItemId(), KP_nnnn, KP_index, true);
		} else {
			ret = false;
		}

		return ret;
	}

	/**
	 * 컨텍스트메뉴이동시처리 - 상속
	 */
	protected Intent onContextItemSelected(Context context, int id, final KPItem info, final KPItem list, final boolean open) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[START]" + getResourceEntryName(context, id));

		if (getActivity() == null) {
			return null;
		}

		if (getBaseActivity().isLoadingDialog) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[EXIT]" + getResourceEntryName(id));
			return null;
		}

		if (open) {
			getBaseActivity().startLoadingDialog(null);
		}

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (info == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "info", info);
			return null;
		}

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return null;
		}

		String menu_name = list.getValue("menu_name");

		KPItemShareData shareData = new KPItemShareData(info, list);

		String uid = shareData.uid;
		String song_id = shareData.song_id;
		String title = shareData.title;
		String artist = shareData.artist;
		String name = shareData.name;

		Class<?> cls = null;
		Intent nIntent = null;
		int requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH;

		// 비로그인사용자화면이동시로그인처리요청
		if (!isLoginUser()) {
			requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_LOGIN;
		}

		String url_contents = "";

		// if (id == R.id.context_home) {
		// //list.putString("m1", getString(R.string.M1_MAIN));
		// //list.putString("m2", getString(R.string.M2_MENU));
		// //cls = home.class;
		// Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
		// if (open) {
		// startActivityForResult(launchIntent, requestCode);
		// }
		// return launchIntent;
		// }

		if (id == R.id.context_freezone) {
			if (getBaseActivity() instanceof BaseAdActivity) {
				((BaseAdActivity) getBaseActivity()).isQueryAd = true;
				((BaseAdActivity) getBaseActivity()).queryAd(p_m1, p_m2);
				((BaseAdActivity) getBaseActivity()).showAdFreezone();
			}
		} else if (id == R.id.context_kakaotalk) {
			openKAKAOLINK(info, list);
		} else if (id == R.id.context_kakaotalk_link) {
			openKAKAOLINK(info, list);
		} else if (id == R.id.context_kakaotalk_story) {
			openKAKAOSTORY(info, list);
		} else if (id == R.id.context_audition) {
			// menu_name = getString(R.string.menu_audition);
			// url_contents = list.getString("url_contents");
			// openWebView(getString(R.string.M1_MENU_AUDITION),
			// getString(R.string.M2_AUDITION_PARTICIPATI), menu_name, url_contents, "POST", false);
			info.putValue("type", "AUDITION");
			list.putValue("type", "AUDITION");
			if (TextUtil.isEmpty(menu_name)) {
				menu_name = getString(R.string.menu_audition);
				info.putValue("menu_name", menu_name);
				list.putValue("menu_name", menu_name);
			}
			cls = auditionview.class;
		} else if (id == R.id.context_audition_open) {
			menu_name = getString(R.string.menu_audition_open);
			url_contents = list.getValue("url_contents");
			openWebView(webview.class, getString(R.string.M1_MENU_AUDITION), getString(R.string.M2_AUDITION_HELD), menu_name, url_contents, "POST", false);
		} else if (id == R.id.context_webview_open) {
			menu_name = list.getValue("menu_name");
			url_contents = list.getValue("url_contents");
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_NOTICE), menu_name, url_contents, "POST", false);
		} else if (id == R.id.context_webrowser_open) {
			menu_name = list.getValue("menu_name");
			url_contents = list.getValue("url_contents");
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_NOTICE), menu_name, url_contents, "POST", true);
		} else if (id == R.id.context_siren_open) {
			menu_name = list.getValue("menu_name");
			url_contents = list.getValue("url_siren");
			url_contents += "&getApp().p_mid=" + getApp().p_mid;
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_SIREN), getString(R.string.menu_siren), url_contents, "POST", false);
		} else if (id == R.id.context_android_market_open || id == R.id.context_market_reward_open) {
			openMarketNReward(info, list);
		} else if (id == R.id.context_list_audition) {
			menu_name = getString(R.string.menu_audition);
			info.putValue("type", "SONG");
			list.putValue("type", "SONG");
			cls = auditionlist.class;
		} else if (id == R.id.context_list_sing) {
			menu_name = getString(R.string.menu_sing);
			info.putValue("type", "SONG");
			list.putValue("type", "SONG");
			cls = singlist.class;
		} else if (id == R.id.context_list_listen) {
			menu_name = getString(R.string.menu_listen);
			info.putValue("type", "RECORD");
			list.putValue("type", "RECORD");
			cls = listenlist.class;
		} else if (id == R.id.context_list_popsong) {
			menu_name = getString(R.string.menu_popsong);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			cls = singlist.class;
			// cls = playtab.class;
		} else if (id == R.id.context_tab_audition) {
			menu_name = getString(R.string.menu_audition);
			info.putValue("type", "SONG");
			list.putValue("type", "SONG");
			cls = auditiontab.class;
			// cls = playtab.class;
		} else if (id == R.id.context_tab_sing) {
			menu_name = getString(R.string.menu_popsong);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			cls = singtab.class;
			// cls = playtab.class;
		} else if (id == R.id.context_tab_listen) {
			menu_name = getString(R.string.menu_popsong);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			cls = listentab.class;
			// cls = playtab.class;
		} else if (id == R.id.context_tab_popsong) {
			menu_name = getString(R.string.menu_popsong);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			cls = singtab.class;
			// cls = playtab.class;
		} else if (id == R.id.context_play_feel) {
			// FEEL재생(?)
			// KP_1010
			menu_name = getString(R.string.menu_feel);
			info.putValue("type", "FEEL");
			list.putValue("type", "FEEL");
			list.putValue("menu_name", menu_name);
			cls = feel.class;
		} else if (id == R.id.context_feel_view) {
			// FEEL재생(?)
			// KP_1010
			menu_name = getString(R.string.menu_feel);
			info.putValue("type", "FEEL");
			list.putValue("type", "FEEL");
			list.putValue("menu_name", menu_name);
			cls = feel.class;
		} else if (id == R.id.context_feel_hearlist) {
			// FEEL추천(?)
			// KP_6004
			menu_name = getString(R.string.menu_feel);
			info.putValue("type", "FEEL");
			list.putValue("type", "FEEL");
			list.putValue("menu_name", menu_name);
			cls = userlist.class;
		} else if (id == R.id.context_feel_alllist) {
		} else if (id == R.id.context_feel_ourlist) {
		} else if (id == R.id.context_play_sing) {
			// 반주곡재생
			// KP_1010
			menu_name = getString(R.string.context_play_sing);
			info.putValue("type", "SONG");
			list.putValue("type", "SONG");
			list.putValue("m1", getString(R.string.M1_MENU_SING));
			list.putValue("m2", getString(R.string.M2_SING_PLAY));
			// cls = kr.kymedia.karaoke.kpop.play.class;
			cls = kr.kymedia.karaoke.kpop.sing.class;
		} else if (id == R.id.context_play_listen) {
			// 녹음곡재생
			// KP_1010
			menu_name = getString(R.string.context_play_listen);
			info.putValue("type", "RECORD");
			list.putValue("type", "RECORD");
			// list.putString("menu_name", menu_name);
			list.putValue("m1", getString(R.string.M1_MENU_LISTEN));
			list.putValue("m2", getString(R.string.M2_LISTEN_PLAY));
			// cls = kr.kymedia.karaoke.kpop.play.class;
			cls = kr.kymedia.karaoke.kpop.listen.class;
		} else if (id == R.id.context_play_movie) {
			openYouTube(list, false, open);
		} else if (id == R.id.context_sing_a_song) {
			// Sing a SONG
			// KP_1010
			menu_name = getString(R.string.context_sing_a_song);
			info.putValue("type", "RECORD");
			list.putValue("type", "RECORD");
			// list.putString("menu_name", menu_name);
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			list.putValue("m1", getString(R.string.M1_MENU_SING));
			list.putValue("m2", getString(R.string.M2_SING_PLAY));
			// cls = kr.kymedia.karaoke.kpop.play.class;
			cls = kr.kymedia.karaoke.kpop.sing.class;
		} else if (id == R.id.context_free_song) {
			if (getApp().p_mid == null || TextUtil.isEmpty(getApp().p_mid)) {
				popupToast(context, R.string.warning_nologin_menu, Toast.LENGTH_SHORT);
				return null;
			}
			// 무료이용
			// KP_1010
			menu_name = String.format(getString(R.string.context_format_free_song), song_id + " - " + title);
			// 무료이용
			// type
			// L:1분미리듣기
			// F:일일무료곡
			// P:유료재생
			String type = "F";
			info.putValue("type", type);
			list.putValue("type", type);
			// list.putString("menu_name", menu_name);
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			list.putValue("m1", getString(R.string.M1_MENU_SING));
			list.putValue("m2", getString(R.string.M2_SING_PLAY));
			// cls = kr.kymedia.karaoke.kpop.play.class;
			cls = kr.kymedia.karaoke.kpop.sing.class;
		} else if (id == R.id.context_lylic_info) {
			// 가사보기
			// KP_1030
			menu_name = String.format(getString(R.string.context_format_lylic_info), title);
			info.putValue("type", "GASA");
			list.putValue("type", "GASA");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			// cls = songinfo.class;
		} else if (id == R.id.context_artist_song) {
			// ARTIST's (이 가수의 다른 곡)
			// KP_1040
			menu_name = String.format(getString(R.string.context_format_artist_song), artist);
			info.putValue("type", "ARTIST");
			list.putValue("type", "ARTIST");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", getString(R.string.M1_MENU_SING));
			list.putValue("m2", getString(R.string.M2_SING_HOT));
			cls = singlist.class;
		} else if (id == R.id.context_song_record) {
			// SONG's (이 노래의 녹음곡)
			// KP_2001
			menu_name = String.format(getString(R.string.context_format_song_record), title);
			info.putValue("type", "SONG");
			list.putValue("type", "SONG");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", getString(R.string.M1_MENU_LISTEN));
			list.putValue("m2", getString(R.string.M2_LISTEN_TIMELINE));
			cls = listenlist.class;
		} else if (id == R.id.context_user_record) {
			// USER's (사용자의 전체 녹음곡)
			// KP_3010
			if (getApp().p_mid.equalsIgnoreCase(uid)) {
				menu_name = getString(R.string.menu_record);
			} else {
				menu_name = String.format(getString(R.string.context_format_user_record), name);
			}
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", getString(R.string.M1_MENU_MYHOLIC));
			list.putValue("m2", getString(R.string.M2_MY_REC));
			cls = listenlist.class;
		} else if (id == R.id.context_my_record) {
			// USER's (사용자의 전체 녹음곡)
			// KP_3010
			menu_name = getString(R.string.menu_record);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", getString(R.string.M1_MENU_MYHOLIC));
			list.putValue("m2", getString(R.string.M2_MY_REC));
			cls = listenlist.class;
		} else if (id == R.id.context_go_holic) {
			// GO HOLIC
			// KP_2010
			list.putValue("menu_name", getString(R.string.context_go_holic));
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = myholic.class;
		} else if (id == R.id.context_best_holic) {
			// BEST HOLIC
			// KP_2010
			list.putValue("menu_name", getString(R.string.context_best_holic));
			info.putValue("type", "SONG");
			list.putValue("type", "SONG");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = myholic.class;
		} else if (id == R.id.context_my_best) {
			// MY BEST
			// KP_2010
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			// list.putString("menu_name", getString(R.string.context_my_best));
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			list.putValue("m1", getString(R.string.M1_MENU_LISTEN));
			list.putValue("m2", getString(R.string.M2_LISTEN_PLAY));
			// cls = kr.kymedia.karaoke.kpop.play.class;
			cls = kr.kymedia.karaoke.kpop.listen.class;
		} else if (id == R.id.context_song_info) {
			// 곡 소개
			// KP_1030
			menu_name = String.format(getString(R.string.context_format_song_info), title);
			info.putValue("type", "INFO");
			list.putValue("type", "INFO");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			// cls = songinfo.class;
		} else if (id == R.id.context_user_all_record) {
			// All Recording SONGs
			// KP_3010
			if (getApp().p_mid.equalsIgnoreCase(uid)) {
				menu_name = getString(R.string.menu_record);
			} else {
				menu_name = String.format(getString(R.string.context_format_user_record), name);
			}
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("menu_name", menu_name);
			list.putValue("m1", getString(R.string.M1_MENU_MYHOLIC));
			list.putValue("m2", getString(R.string.M2_MY_REC));
			cls = listenlist.class;
			// } else if (id == R.id.context_comment) {
			// String packageName = "kr.kymedia.karaoke.kpop";
			// String className = "kr.kymedia.karaoke.kpop.feelpost";
			// openACTIONSHARE(info, list, open, packageName, className);
		} else if (id == R.id.context_share) {
			// 공유
			nIntent = openACTIONSHARE(info, list, true, null, null);
			return nIntent;
		} else if (id == R.id.context_delete_listen) {
			// 녹음곡삭제
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_record_delete), getString(R.string.btn_title_delete),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							deleteListen();
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (id == R.id.context_popup) {
			// 팝업노출
			cls = _popup.class;
			requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH;
			// 2. "btn_type" - "-1", "0", "1", "2"
			// - "-1" : "강제확인" 버튼 하단에 노출
			// - "0" : "확인" 버튼 하단에 노출
			// - "1" : "닫기" 버튼 하단에 노출
			// - "2" : "확인", "닫기" 버튼 하단에 노출
			//int btn_type = TextUtil.parseInt(info.getValue("btn_type"));
			//if (btn_type == -1) {
			if (TextUtil.parseInt(info.getValue("btn_type"), 0) == -1) {
				getBaseActivity().finish();
			}
		} else if (id == R.id.context_notice) {
			// 공지사항
			list.putValue("m1", getString(R.string.M1_MENU_INFO));
			list.putValue("m2", getString(R.string.M2_MENU_NOTICE));
			list.putValue("menu_name", list.getValue("title"));
			nIntent = putIntentData(context, notice.class, info, list);
			cls = notice.class;
		} else if (id == R.id.context_record || id == R.id.txt_record) {
			// 마이홀릭.녹음곡
			nIntent = putIntentData(context, _myholictab.class, info, list);
			nIntent.putExtra("tabIndex", 0);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else if (id == R.id.context_feel_mylist) {
			// 마이홀릭.필목록
			nIntent = putIntentData(context, _myholictab.class, info, list);
			nIntent.putExtra("tabIndex", 1);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else if (id == R.id.context_follower || id == R.id.txt_follower) {
			// 마이홀릭.팔로워
			nIntent = putIntentData(context, _myholictab.class, info, list);
			nIntent.putExtra("tabIndex", 2);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else if (id == R.id.context_following || id == R.id.txt_following) {
			// 마이홀릭.팔로잉
			nIntent = putIntentData(context, _myholictab.class, info, list);
			nIntent.putExtra("tabIndex", 3);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else if (id == R.id.context_guestbook || id == R.id.txt_guestbook) {
			// 마이홀릭.방명록
			nIntent = putIntentData(context, _myholictab.class, info, list);
			nIntent.putExtra("tabIndex", 4);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else if (id == R.id.context_message || id == R.id.txt_message) {
			// 마이홀릭.쪽지함
			nIntent = putIntentData(context, _myholictab.class, info, list);
			nIntent.putExtra("tabIndex", 5);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else if (id == R.id.context_mysing) {
			// 마이홀릭.애창곡
			nIntent = putIntentData(context, _myholictab.class, info, list);
			int tabIndex = 6;
			// 사용자별탭차단 - 쪽지함이없음
			if (!TextUtil.isEmpty(uid) && !getApp().p_mid.equalsIgnoreCase(uid)) {
				tabIndex = 5;
			}
			nIntent.putExtra("tabIndex", tabIndex);
			info.putValue("type", "UID");
			list.putValue("type", "UID");
			list.putValue("m1", p_m1);
			list.putValue("m2", p_m2);
			cls = _myholictab.class;
		} else {
		}

		// startActivityForResult할 액티비티클래스와 인텐트체크
		if (cls != null && nIntent == null) {
			nIntent = putIntentData(context, cls, info, list);
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[CHECK]" + nIntent);

		if (nIntent != null) {

			// 메인인경우 - 액티비티순서변경
			if (Intent.ACTION_MAIN.equalsIgnoreCase(nIntent.getAction())) {
				nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				nIntent.setAction(Intent.ACTION_MAIN);
			}

			if (cls == myholic.class) {
				// 마이홀릭(회원내용정보) - 여러개오픈한다.
			} else if (cls == _myholictab.class) {
				// 마이홀릭(회원내용정보) - 여러개오픈한다.
			} else {
				nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			}

			if (open) {
				startActivityForResult(nIntent, requestCode);
			}
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[END]" + cls);

		return nIntent;
	}

	/**
	 * 컨텍스트메뉴이동시처리 - 호출
	 */
	protected Intent onContextItemSelected(int id, KPnnnn KP_xxxx, int index, boolean open) {

		Intent intent = null;

		try {
			if (KP_xxxx == null) {
				KP_xxxx = KP_nnnn;
			}

			KPItem info = KP_xxxx.getInfo();
			KPItem list = getList();
			ArrayList<KPItem> lists = KP_xxxx.getLists();

			if (lists != null && index > -1 && index < lists.size()) {
				KPItem item = lists.get(index);
				list = item;
			}

			intent = onContextItemSelected(mContext, id, info, list, open);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return intent;
	}

	protected String mode = "";

	public void deleteListen() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		KPItem list = KP_nnnn.getList(KP_index);

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		String record_id = "";

		if (list != null) {
			record_id = list.getValue("record_id");
		}

		mode = "DEL";
		getApp().KP_3011(getApp().p_mid, p_m1, p_m2, mode, record_id);
	}

	/**
	 * 퀵액션메뉴<br>
	 * <br>
	 * 
	 * @see #onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	QuickAction2 mQuickAction = null;
	/**
	 * 퀵액션아이템<br>
	 * <br>
	 */
	ArrayList<ActionItem> mActionItems = new ArrayList<ActionItem>();

	protected boolean showContextMenu(View v, boolean isShowCenter, boolean isShowArrow) {
		if (v == null) {
			return false;
		}
		// ContextMenu를 onCreateContextMenu에서 QuickActionMenu로 변환후
		boolean ret = v.showContextMenu();
		// QuickActionMenu를 보인다.
		popupQuickActionContextMenu(v, isShowCenter, isShowArrow);
		return ret;
	}

	/**
	 * 퀵액션메뉴팝업
	 */
	protected void popupQuickActionContextMenu(View v, boolean isShowCenter, boolean isShowArrow) {

		if (v == null) {
			return;
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + v.toString() + isShowArrow);

		int orientation = getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mQuickAction = new QuickAction2(v, R.layout.popup_horizontal, QuickAction2.STYLE_BUTTON, R.layout.quickaction_item_button);
		} else {
			mQuickAction = new QuickAction2(v, R.layout.popup_vertical2, QuickAction2.STYLE_LIST, R.layout.quickaction_item_list);
		}

		for (int i = 0; i < mActionItems.size(); i++) {
			mQuickAction.addActionItem(mActionItems.get(i));
		}

		mQuickAction.setShowCenter(true);
		mQuickAction.setShowArrow(isShowArrow);
		mQuickAction.show();
	}

	/**
	 * 퀵액션메뉴생성<br>
	 * <br>
	 */
	protected int makeQuickActionContextMenu(Menu menu) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + menu.toString());

		// WindowManager wm = (WindowManager) getBaseActivity().getSystemService(Context.WINDOW_SERVICE);
		// Display display = wm.getDefaultDisplay();
		// int rotation = display.getRotation();

		int ret = 0;
		mActionItems.clear();
		MenuItem item = null;
		for (int i = 0; i < menu.size(); i++) {
			item = menu.getItem(i);
			if (item.isVisible() && item.isEnabled()) {
				onCreateQuickActionContextMenu(item);
				ret++;
			}
		}

		return ret;
	}

	/**
	 * 퀵액션메뉴생성<br>
	 * <br>
	 * 
	 * @see BaseFragment2#makeQuickActionContextMenu(Menu)
	 */
	protected void onCreateQuickActionContextMenu(final MenuItem item) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + item.toString());

		String title = item.getTitle().toString();
		Drawable icon = item.getIcon();
		ActionItem action = new ActionItem();
		// action.setActionId(item.getItemId());
		action.setIcon(icon);
		action.setTitle(title);
		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onContextItemSelected(item.getItemId(), KP_nnnn, KP_index, true);
				mQuickAction.dismiss();
			}
		});

		mActionItems.add(action);
	}

	/**
	 * 퀵액션매뉴 생성시<br>
	 * 조회데이터별 메뉴 보이기/숨기기 처리후 !!!반드시 상속함수 마지막에 호출해라!!!<br>
	 * 안그르문 설날퇴근 몬한다...T_T<br>
	 * <br>
	 * 
	 * @see BaseFragment2#mQuickAction
	 * @see BaseFragment2#makeQuickActionContextMenu(Menu)
	 * @see BaseFragment2#onCreateQuickActionContextMenu(MenuItem)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_index + ", " + getResourceEntryName(v.getId()));


		super.onCreateContextMenu(menu, v, menuInfo);

		getBaseActivity().mContextFragment = this;

		// QuickActionMenu생성후
		if (makeQuickActionContextMenu(menu) > 0) {
			// ContextMenu메뉴클리어
			menu.clear();
		}
	}

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
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_mid);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_email);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_pwd);
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getApp().p_passtype);

		return getApp().isLoginUser();
	}

	/**
	 * 저장된 로그인정보를 삭제한다.
	 * <p>
	 * 로그인화면으로이동
	 * </p>
	 */
	public void putLogout() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().p_mid);

		boolean ok = delLogin();

		if (ok) {
			putLogin(null);
			getLogin();
		}

		// close();
		refresh();

		getBaseActivity().stopLoadingDialog(null);

		onOptionsItemSelected(R.id.menu_login, "", true);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().p_mid);
	}

	/**
	 * 해당사용자의 로그인프로파일 정보삭제
	 */
	protected boolean delLoginProfile() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// getApp().p_passtype = "";
		// p_free = 0;
		// getApp().p_nickname = "";
		// getApp().p_birthday = "";
		// getApp().p_sex = "";
		// p_goodsname = "";
		// p_goodscode = "";

		return true;
	}

	/**
	 * 해당사용자의 로그인정보 전체삭제
	 */
	private boolean delLogin() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		getApp().delLogin();

		return true;
	}

	/**
	 * 로그인결과반영<br>
	 * <br>
	 * info == null - 전문 "info"태그를 저장한다. -> 한마디로 로그인/회원정보변경등 전문연동시<br>
	 * info != null - 현제 인스턴스변수를 저장한다.-> 한마디로 로그아웃/재로그인등 로컬정보삭제시<br>
	 * <br>
	 */
	public int putLogin(KPItem info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		getApp().putLogin(info);

		setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);

		return 1;
	}

	/**
	 * 로그인확인:비로그인시로그인처리한다.
	 */
	protected boolean checkLogin(boolean login) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + login);

		if (getBaseActivity().isACTIONMAIN()) {
			login = true;
		}

		if (!isLoginUser()) {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[LOGININFO]\ngetApp().p_mid:" + getApp().p_mid + "\ngetApp().p_email:" + getApp().p_email + "\ngetApp().p_pwd:" + getApp().p_pwd + "\ngetApp().p_passtype:" + getApp().p_passtype);
			getLogin();
		}

		// 로그인/회원가입
		if (!isLoginUser() && login) {
			// 비로그인메인화면인경우로그인화면이동
			onOptionsItemSelected(R.id.menu_login, "", true);
		}

		return isLoginUser();
	}

	/**
	 * 저장된 로그인정보를 가져온다. 주로 onCreateView()에서 호출된다.
	 */
	protected boolean getLogin() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		getApp().getLogin();

		return isLoginUser();
	}

	public void deleteUserId() {
		KP_nnnn.KP_5013(getApp().p_mid, p_m1, p_m2, getApp().p_email);
	}

	/**
	 * 이용권유형 무료사용자 :N 기간제이용권:Y 이벤트사용자:E/C
	 * 
	 * @see #getApp().p_passtype
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

		return getApp().isPassUser();
	}

	private boolean isDestroyed = false;

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		// 메인화면에서 로그아웃확인
		if (getBaseActivity().isACTIONMAIN()) {
			if (!getApp().login_check) {
				putLogout();
			}
		}

		super.onDestroy();

		isDestroyed = true;
	}

	/**
	 * 웹뷰를 오픈한다. 로컬파일 오픈지원 WebView POST오픈은 안된단다.~젖된단다.
	 * 
	 * @param url
	 *          알지?
	 * @param method
	 *          "POST" "GET" "IMAGE"
	 * @param browser
	 *          머로오픈할까 true: ACTION_VIEW사용(외부어플오픈)
	 * 
	 */
	public void openWebView(Class<?> cls, String m1, String m2, String menu_name, String url, String method, boolean browser) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + cls);
		if (BuildConfig.DEBUG) Log.e(_toString(), "m1:" + m1 + ", m2:" + m2);
		if (BuildConfig.DEBUG) Log.e(_toString(), url);

		try {
			File file = null;
			try {
				// 로컬파일확인
				if (!TextUtil.isNetworkUrl(url) && !TextUtil.isMarketUrl(url)) {
					file = new File(url);
					if (file != null && file.exists()) {
						url = "file://" + url;
						browser = true;
					} else {
						return;
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			// 마켓URI처리
			if (TextUtil.isMarketUrl(url)) {
				browser = true;
			}

			Uri uri = Uri.parse(url);
			String type = "";
			try {
				if (file != null && file.exists()) {
					type = EnvironmentUtils.getMimeTypeFromPath(file);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			Intent intent = null;
			int requestCode = _IKaraoke.KARAOKE_INTENT_ACTION_DEFAULT;

			if (browser) {
				intent = new Intent(Intent.ACTION_VIEW);
				if (file != null && file.exists()) {
					// 로컬파일오픈
					if (method.equalsIgnoreCase("image")) {
						type = "image/*";
					}
					intent.setDataAndType(uri, type);
				} else {
					// 브라우저오픈(POST)(??)
					intent.setData(uri);
				}

				startActivityForResult(intent, requestCode);
			} else {
				// 어플내에오픈
				// intent = putIntentData(webview.class, KP_nnnn, KP_index);
				KPItem info = new KPItem();
				KPItem list = new KPItem();

				list.putValue("m1", m1);
				list.putValue("m2", m2);
				list.putValue("menu_name", menu_name);

				intent = putIntentData(mContext, cls, info, list);
				intent.putExtra("method", method);
				intent.putExtra("url", url);
				intent.putExtra("uri", uri);

				startActivityForResult(intent, requestCode);
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, url + "\n" + menu_name + "\n" + intent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Override
	// public void openWebView(String m1, String m2, String menu_name, String url, String method,
	// boolean action) {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + url + " - " + "m1:" + m1 + ", m2:" + m2);
	// openWebView(webview.class, m1, m2, menu_name, url, method, action);
	// }

	protected void showURLLinkDialog(String r_message, final String result_url) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm),
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						openWebView(webview.class, "", "", "UPDATE", result_url, "POST", true);

					}

				}, null, null, true, null);

	}

	/**
	 * 나의 녹음곡 화면 열기확인
	 */
	public void openMyRecord(KPItem info) {
		final String url_contents = info.getValue("url_contents");
		final String r_message = info.getValue("result_message");
		// 녹음곡 업로드를 완료 하였습니다.\n녹음곡은 MY HOLIC 메뉴에서 확인해주십시요.
		getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.btn_title_ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				close();
				if (URLUtil.isNetworkUrl(url_contents)) {
					openWebView(webview.class, getString(R.string.M1_MENU_AUDITION), getString(R.string.M2_AUDITION_PARTICIPATI), getString(R.string.menu_audition), url_contents, "POST",
							false);
				} else {
					onOptionsItemSelected(R.id.menu_myholic, "", true);
				}
			}
		}, getString(R.string.btn_title_cancel), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				close();
			}
		}, true, new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

				close();
			}
		});
	}

	protected Map<String, String> KP_0000(KPItem info, KPItem list) {

		Map<String, String> params = new HashMap<String, String>();

		return params;
	}

	@Override
	public void KP_nnnn(KPItem info, KPItem list, boolean clear) {


	}

	/**
	 * <pre>
	 *  서비스 이용약관
	 *  개인정보 수집 및 이용 안내
	 *  개인정보 취급 방침 [읽음]
	 *  개인정보 수집 이용 동의 [필수]
	 * </pre>
	 */
	@Override
	public void onClick(View v) {

		super.onClick(v);

		if (v.getId() == R.id.close) {
			close();
		}

		String url;
		String param;

		if (v.getId() == R.id.txt_setting_eula1) {
			/**
			 * 서비스 이용약관
			 */
			url = KP_nnnn.getHostPath() + "protocol/web/KP_5000.php?";
			param = KP_nnnn.getParams();
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_eula1), url + param, "POST", false);
		} else if (v.getId() == R.id.txt_setting_eula2) {
			/**
			 * 개인정보 수집 및 이용 안내
			 */
			url = KP_nnnn.getHostPath() + "protocol/web/KP_5001.php?";
			param = KP_nnnn.getParams();
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_eula2), url + param, "POST", false);
		} else if (v.getId() == R.id.txt_setting_eula3) {
			/**
			 * 개인정보 취급 방침 [읽음]
			 */
			url = KP_nnnn.getHostPath() + "protocol/web/KP_5004.php?";
			param = KP_nnnn.getParams();
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_eula3), url + param, "POST", false);
		} else if (v.getId() == R.id.txt_setting_eula4) {
			/**
			 * 개인정보 수집 이용 동의 [필수]
			 */
			url = KP_nnnn.getHostPath() + "protocol/web/KP_5001.php?";
			param = KP_nnnn.getParams();
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_eula4), url + param, "POST", false);
		} else if (v.getId() == R.id.txt_setting_open_source_licences) {
			/**
			 * 오픈소스 라이센스
			 */
			url = KP_nnnn.getHostPath() + "protocol/web/KP_5003.php?";
			param = KP_nnnn.getParams();
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), getString(R.string.category_setting_open_source_licenses), url + param,
					"POST", false);
		}
	}

}
