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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Main2XX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main2XX.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 븅신들땜에정말케삽질한다.
 * 홈메뉴/메인메뉴 이동처리 오류개선
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015-12-23
 */
class Main2XX extends Main2X {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

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

	//@Override
	//protected boolean isLoading() {
	//	return (loading_time != LOADING_TIME.LOADING_NONE || super.isLoading());
	//}

	/**
	 * <pre>
	 *   강제중지
	 * </pre>
	 *
	 * @see Main3XXX#onKeyDown(int, KeyEvent)
	 */
	@Override
	protected void startLoading(String method, int time) {
		super.startLoading(method, time);

		//잠시만 기다려 주십시요.\n무응답시 이전/취소 키를 5초정도 누르십시요.
		switch (this.loading_time) {
			case LOADING_NONE:
				break;
			case LOADING_SHORT:
			case LOADING_LONG:
			case LOADING_SING:
			case LOADING_LISTEN:
			default:
				setBottomProductText(getString(R.string.message_load_exit_app));
				break;

		}
	}

	/**
	 * <pre>
	 *   강제중지
	 * </pre>
	 *
	 * @see Main3XXX#onKeyDown(int, KeyEvent)
	 */
	@Override
	protected void stopLoading(String method) {
		super.stopLoading(method);

		switch (this.loading_time) {
			case LOADING_NONE:
				break;
			case LOADING_SHORT:
				break;
			case LOADING_LONG:
				break;
			case LOADING_SING:
				break;
			case LOADING_LISTEN:
				break;
			default:
				break;
		}

		this.loading_time = LOADING_TIME.LOADING_NONE;
	}

	LinearLayout.LayoutParams lpHomeOn;
	LinearLayout.LayoutParams lpHomeOff;
	LinearLayout.LayoutParams lpMainOn;
	LinearLayout.LayoutParams lpMainOff;

	@Override
	protected void setBaseLayout() {
		super.setBaseLayout();

		lpHomeOn = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		lpHomeOn.setMargins(0, 0, 0, PixelFromDP(10));
		lpHomeOn.weight = 5;

		lpHomeOff = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		lpHomeOff.setMargins(0, PixelFromDP(15), 0, PixelFromDP(25));
		lpHomeOff.weight = 3;

		lpMainOn = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		lpMainOn.setMargins(0, 0, 0, 0);
		lpMainOn.weight = 5;

		lpMainOff = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		lpMainOff.setMargins(0, PixelFromDP(12), 0, PixelFromDP(12));
		lpMainOff.weight = 3;
	}

	@Override
	protected void resetMainMenu() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		btn_main_sing.setBackgroundResource(R.drawable.sub_icon_01_off);
		btn_main_listen.setBackgroundResource(R.drawable.sub_icon_02_off);
		btn_main_my.setBackgroundResource(R.drawable.sub_icon_03_off);
		btn_main_shop.setBackgroundResource(R.drawable.sub_icon_04_off);
		btn_main_customer.setBackgroundResource(R.drawable.sub_icon_05_off);

		btn_main_sing.setLayoutParams(lpMainOff);
		btn_main_listen.setLayoutParams(lpMainOff);
		btn_main_my.setLayoutParams(lpMainOff);
		btn_main_shop.setLayoutParams(lpMainOff);
		btn_main_customer.setLayoutParams(lpMainOff);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void resetHomeMenu() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		btn_home_main_01.setLayoutParams(lpHomeOff);
		btn_home_main_01.setSelected(false);

		btn_home_main_02.setLayoutParams(lpHomeOff);
		btn_home_main_02.setSelected(false);

		btn_home_main_03.setLayoutParams(lpHomeOff);
		btn_home_main_03.setSelected(false);

		btn_home_main_04.setLayoutParams(lpHomeOff);
		btn_home_main_04.setSelected(false);

		btn_home_main_05.setLayoutParams(lpHomeOff);
		btn_home_main_05.setSelected(false);

		btn_home_event_01.setSelected(false);
		bal_sing.setVisibility(View.INVISIBLE);
		WidgetUtils.setTextViewMarquee(txt_home_sing_title, false);
		WidgetUtils.setTextViewMarquee(txt_home_sing_artist, false);

		btn_home_event_02.setSelected(false);
		bal_listen.setVisibility(View.INVISIBLE);
		WidgetUtils.setTextViewMarquee(txt_home_listen_title, false);
		WidgetUtils.setTextViewMarquee(txt_home_listen_artist, false);


		btn_home_event_03.setSelected(false);
		btn_home_event_04.setSelected(false);
		btn_home_event_05.setSelected(false);

		layout_home_notice_menu.setSelected(false);

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnHomeSing(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goSingList(true);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnHomeListen(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goListenList(true);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnHomeMy(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goMyList(true);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnHomeShop(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goShop(true, 1);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnHomeCustomer(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goCustomer(true);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnMainSing(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goSingList(false);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnMainListen(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goListenList(false);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnMainMy(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goMyList(false);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnMainShop(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goShop(false, 1);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void onBtnMainCustomer(View view) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		goCustomer(false);
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void clickMenu(int index) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + index + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		//if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + ":" + m_bListAlreadyReflashed);

		ShowMenu(getMethodName());

		if (m_iMainMenuFocusSave != index) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[NO]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + "m_iMainMenuFocusSave:" + m_iMainMenuFocusSave);
			m_bListAlreadyReflashed = false;
		}

		switch (index) {
			// 노래부르기
			case 1:
				goSingList(true);
				break;
			// 녹음곡감상
			case 2:
				goListenList(true);
				break;
			// 마이노래방
			case 3:
				goMyList(true);
				break;
			// 노래방샵
			case 4:
				goShop(true);
				break;
			// 고객센터
			case 5:
				goCustomer(true);
				break;
			// 부르기
			case 6:
				goSing();
				break;
			// 듣기
			case 7:
				goListen();
				break;
			// 앱 설치 안내
			case 8:
				goCustomer(false, 4);
				break;
			// 구매 안내
			case 9:
				if (submenuQuickBtn02.m2.equals(M2_SHOP_TICKET)) {
					// 이용권
					goShop(false, 1);
				} else if (submenuQuickBtn02.m2.equals(M2_HELP_MIKE)) {
					// 마이크
					goCustomer(false, 5);
				}
				break;
			// 이벤트
			case 10:
				goEvent(true);
				break;
			//공지사항
			case 11:
				goNotice(false);
				break;
		}
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + index + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void openHomeMenu() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		clickMenu(remote.m_iMenuHomeFocus);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void openMainMenu() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (m_iMainMenuFocusSave == remote.m_iMenuMainFocus) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[OK]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);
			m_bListAlreadyReflashed = true;
		}
		m_iMainMenuFocusSave = remote.m_iMenuMainFocus;
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		clickMenu(remote.m_iMenuMainFocus);
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	protected void resetMenus(String method) {
		super.resetMenus(method);
		m_iMainMenuFocusSave = 0;
	}

	@Override
	public void goSearch() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.goSearch();
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 *   홈키처리
	 * </pre>
	 */
	@Override
	public void goHome() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.goHome();

		/**
		 * 노래 재생 > 곡찾기 키 실행 > 홈키 > 노래부르기 메뉴 접근 > UI 오류
		 */
		boolean exitSearch = false;
		switch (remote.m_iState) {
			case STATE_SEARCH_MENU:
			case STATE_SEARCH_SELF:
			case STATE_SEARCH_LETTER_KOR:
			case STATE_SEARCH_LETTER_ENG:
			case STATE_SEARCH_LETTER_NUM:
			case STATE_SEARCH_LIST:
			case STATE_SEARCH_LIST_DETAIL:
				exitSearch = true;
				break;
			default:
				break;
		}

		remote.m_iState = STATE_HOME_MENU;

		if (!isShowMenu()) {
			ShowMenu(getMethodName());
		}

		//반주곡중지
		stopPlay(PLAY_STOP);

		//녹음곡중지
		stopListen();

		// hideBottomGuide01();

		removeListening();

		// if (m_layoutListenListFocus != null) {
		// ((ViewManager) m_layoutListenListFocus.getParent()).removeView(m_layoutListenListFocus);
		// m_layoutListenListFocus = null;
		// }
		removeView(m_layoutListenListFocus);
		m_layoutListenListFocus = null;

		setContentViewKaraoke(m_layoutHome);

		displayMenuHome(REMOTE_NONE);

		if (m_iMainMenuFocusSave == remote.m_iMenuMainFocus) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[OK]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);
			m_bListAlreadyReflashed = true;
		}

		m_iMainMenuFocusSave = remote.m_iMenuMainFocus;

		if (exitSearch) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[검색]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);
			m_bListAlreadyReflashed = false;
		}

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void exitMenuSearch() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.exitMenuSearch();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void goListen() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		if (isPlaying()) {
			// 재생 중지
			stopPlay(PLAY_STOP);
		}

		KP(REQUEST_LISTEN_SONG, KP_2016, M1_MENU_LISTEN, M2_LISTEN_TIMELINE);
	}

	protected void goSing() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		m_strRequestPlaySongID = m_strMainSingID;
		startSing(m_strMainSingID);
		// 이븅신새끼하는짓좀보소
		// if (isPlaying()) {
		// // 재생 중지
		// stopPlay(PLAY_STOP);
		//
		// // 140422 예약된 반주곡을 자동 시작할 때는 중지 처리가 완전히 끝난 뒤에 새 요청 시작 (메인 > 부르기)
		// m_timerStartCurrentSong = new Timer();
		// TaskStartCurrentSong startTask = new TaskStartCurrentSong();
		//
		// StartLoading(LOADING_LONG);
		// m_timerStartCurrentSong.schedule(startTask, 10000);
		// } else {
		// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
		// }
	}

	/**
	 * 고객센터->메뉴이동
	 */
	protected void goCustomer(boolean home, int focus) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "home:" + home + ":focus:" + focus);
		m_bListAlreadyReflashed = false;
		remote.m_iMenuCustomerFocus = focus;
		goCustomer(home);
	}

	/**
	 * 고객센터
	 */
	private void goCustomer(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus);

		remote.m_iState = STATE_CUSTOMER_MENU;
		remote.m_iMenuMainFocus = 5;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		m_iEnterCustomerMenu = CUSTOMER_ENTER_KEY;

		setContentViewKaraoke(m_layoutMain);

		if (!m_bListAlreadyReflashed) {
			displayMenuMain(REMOTE_INIT);
		}

		displayMenuCustomer(REMOTE_NONE);

		btn_main_customer.setBackgroundResource(R.drawable.sub_icon_05_focus);
	}

	protected void goNotice(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		remote.m_iState = STATE_CUSTOMER_MENU;
		remote.m_iMenuMainFocus = 5;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		setContentViewKaraoke(m_layoutMain);

		addViewCustomerMenu();

		addViewCustomerList();

		m_iEnterCustomerMenu = CUSTOMER_ENTER_NOTICE;
		displayMenuMain(REMOTE_NONE);

		m_iCustomerListItemCount = 0;
		m_iCurrentViewCustomerListPage = 1;
		m_iTotalCustomerListPage = 1;
		m_iCustomerListDetailTotalPage = 1;
		m_iCurrentCustomerListDetailPage = 1;
		remote.m_iCustomerListFocus = 1;

		m_strRequestCustomerDetailID = m_strMainCustomerID;

		KP(REQUEST_CUSTOMER_LIST_DETAIL, KP_0011, M1_MENU_HELP, M2_HELP_NOTICE);

		resetMainMenu();
		btn_main_customer.setBackgroundResource(R.drawable.sub_icon_05_focus);
		btn_main_customer.setLayoutParams(lpMainOn);
	}

	protected void goEvent(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		remote.m_iState = STATE_CUSTOMER_MENU;
		remote.m_iMenuMainFocus = 5;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		setContentViewKaraoke(m_layoutMain);

		addViewCustomerMenu();

		addViewCustomerListEvent();

		m_iEnterCustomerMenu = CUSTOMER_ENTER_EVENT;
		displayMenuMain(REMOTE_NONE);

		m_iCustomerListItemCount = 0;
		m_iCurrentViewCustomerListPage = 1;
		m_iTotalCustomerListPage = 1;
		m_iCustomerListDetailTotalPage = 1;
		m_iCurrentCustomerListDetailPage = 1;
		remote.m_iCustomerListFocus = 1;

		m_strRequestCustomerDetailID = m_strMainEventID;

		KP(REQUEST_EVENT_LIST_DETAIL, KP_0011, M1_MENU_HELP, M2_HELP_EVENT);

		resetMainMenu();
		btn_main_customer.setBackgroundResource(R.drawable.sub_icon_05_focus);
		btn_main_customer.setLayoutParams(lpMainOn);
	}

	@Override
	public void exitDetailCustomer() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus);

		if (m_iEnterCustomerMenu != CUSTOMER_ENTER_KEY) {
			//홈화면이동시
			remote.m_iState = STATE_CUSTOMER_MENU;
		} else {
			//리스트이동시
			if (remote.m_iMenuCustomerFocus != 1) {
				remote.m_iState = STATE_CUSTOMER_LIST;
			} else {
				remote.m_iState = STATE_CUSTOMER_LIST_EVENT;
			}
		}

		int keyID = REMOTE_NONE;

		if (mCustomerItems.size() == 0) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO][DATA]" + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + KP.result_code + ":" + "\n" + mCustomerItems);
			keyID = REMOTE_INIT;
		}

		m_bDisplayingCustomerDetail = true;

		displayGUI(keyID);

		if (m_iEnterCustomerMenu != CUSTOMER_ENTER_KEY) {
			//홈화면이동시
			clickMenuCustomer();
		} else {
			//리스트이동시
			moveCustomerListPage();
		}

		m_iEnterCustomerMenu = CUSTOMER_ENTER_KEY;

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 노래방샵->메뉴이동
	 */
	protected void goShop(boolean home, int focus) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "home:" + home + ":focus:" + focus);
		m_bListAlreadyReflashed = false;
		remote.m_iMenuShopFocus = focus;
		goShop(home);
	}

	/**
	 * 노래방샵
	 */
	private void goShop(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":remote.m_iMenuShopFocus:" + remote.m_iMenuShopFocus);

		remote.m_iState = STATE_SHOP_MENU;
		remote.m_iMenuMainFocus = 4;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		setContentViewKaraoke(m_layoutMain);

		if (!m_bListAlreadyReflashed) {
			displayMenuMain(REMOTE_INIT);
		}

		displayMenuShop(REMOTE_NONE);

		btn_main_shop.setBackgroundResource(R.drawable.sub_icon_04_focus);
	}

	/**
	 * 마이노래방
	 */
	protected void goMyList(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		remote.m_iState = STATE_MY_MENU;
		remote.m_iMenuMainFocus = 3;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		setContentViewKaraoke(m_layoutMain);

		if (!m_bListAlreadyReflashed) {
			displayMenuMain(REMOTE_INIT);
		}

		displayMenuMy(REMOTE_NONE);

		btn_main_my.setBackgroundResource(R.drawable.sub_icon_03_focus);
	}

	/**
	 * 녹음곡감상
	 */
	protected void goListenList(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		remote.m_iState = STATE_LISTEN_MENU;
		remote.m_iMenuMainFocus = 2;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		setContentViewKaraoke(m_layoutMain);

		if (!m_bListAlreadyReflashed) {
			displayMenuMain(REMOTE_INIT);
		}

		displayMenuListen(REMOTE_NONE);

		btn_main_listen.setBackgroundResource(R.drawable.sub_icon_02_focus);
	}

	/**
	 * 노래부르기
	 */
	protected void goSingList(boolean home) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);

		remote.m_iState = STATE_SING_MENU;
		remote.m_iMenuMainFocus = 1;

		//메인메뉴->홈메뉴
		if (home) {
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			remote.m_iMenuHomeFocusY = 1;
		}

		setContentViewKaraoke(m_layoutMain);

		if (!m_bListAlreadyReflashed) {
			displayMenuMain(REMOTE_INIT);
		}

		displayMenuSing(REMOTE_NONE);

		btn_main_sing.setBackgroundResource(R.drawable.sub_icon_01_focus);

		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed);
	}

	/**
	 * 메뉴(메인화면) - 저장
	 */
	protected int m_iMainMenuFocusSave = 0;

	protected String _checkKaraokeMenuFoci() {
		String ret = "";
		ret += "\n:m_iMainMenuFocusSave:" + m_iMainMenuFocusSave;
		ret += "\n:remote.m_iMenuHomeFocus:" + remote.m_iMenuHomeFocus;
		ret += "\n:remote.m_iMenuMainFocus:" + remote.m_iMenuMainFocus;
		ret += "\n:remote.m_iMenuSingFocus:" + remote.m_iMenuSingFocus;
		ret += "\n:remote.m_iMenuSingGenreFocus:" + remote.m_iMenuSingGenreFocus;
		ret += "\n:remote.m_iMenuListenFocus:" + remote.m_iMenuListenFocus;
		ret += "\n:remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus;
		ret += "\n:remote.m_iMenuShopFocus:" + remote.m_iMenuShopFocus;
		ret += "\n:remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus;
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), ret);
		return "";
	}

	/**
	 * 븅신들땜에정말케삽질한다.
	 * <pre>
	 * 절대~~~예외처리하지 않습니다.
	 * 서브메뉴 종료도 몬하고 좃됩니다.
	 * </pre>
	 */
	@Override
	protected void clickGUI() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		//상단메뉴이동시만
		if (remote.m_iMenuHomeFocus < 6 && m_iMainMenuFocusSave != remote.m_iMenuHomeFocus) {
			resetMenuSub(getMethodName());
		}

		if (IKaraokeTV.DEBUG) {
			super.clickGUI();
		} else {
			try {
				super.clickGUI();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}

		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 븅신들땜에정말케삽질한다.
	 * <pre>
	 * 절대~~~예외처리하지 않습니다.
	 * 서브메뉴 종료도 몬하고 좃됩니다.
	 * </pre>
	 */
	@Override
	protected void displayGUI(int keyID) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		//상단메뉴이동시만
		if (remote.m_iMenuMainFocus < 6 && m_iMainMenuFocusSave != remote.m_iMenuMainFocus) {
			resetMenuSub(getMethodName());
		}

		if (IKaraokeTV.DEBUG) {
			super.displayGUI(keyID);
		} else {
			try {
				super.displayGUI(keyID);
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}

		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState());
	}

	/**
	 * <pre>
	 * 녹음곡 음정/템포
	 *  기존:REMOTE_DOWN
	 *  신규:REMOTE_MENU
	 * </pre>
	 */
	@Override
	public void displayListening(int keyID) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState());
		if (keyID == REMOTE_MENU && isListening() && remote.m_iState == STATE_LISTENING) {
			super.displayListening(keyID);
		} else {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState());
			exitListening();
		}
	}

	@Override
	public void exitListening() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + remote.getState());
		super.exitListening();
	}

	/**
	 * 븅신들땜에정말케삽질한다.
	 * <pre>
	 * 절대~~~예외처리하지 않습니다.
	 * 서브메뉴 종료도 몬하고 좃됩니다.
	 * </pre>
	 */
	@Override
	protected void exitGUI() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.exitGUI();
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	@Override
	protected void KP(Message msg) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) {
			super.KP(msg);
		} else {
			try {
				super.KP(msg);
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

}
