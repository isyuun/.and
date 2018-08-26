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
 * filename	:	Main2XXX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main2XXX.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 메인메뉴 고도화
 *  메인분리작업
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015-12-17
 */
class Main2XXX extends Main2XX {
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

	@Override
	protected void ShowMenu(String method) {
		super.ShowMenu(method);
		showBody();
	}

	@Deprecated
	private void showBody() {
		if (bg_main != null) {
			bg_main.setVisibility(View.VISIBLE);
		}
		if (layout_body != null) {
			layout_body.setVisibility(View.VISIBLE);
		}
	}

	@Deprecated
	private void hideBody() {
		if (bg_main != null) {
			bg_main.setVisibility(View.INVISIBLE);
		}
		if (layout_body != null) {
			layout_body.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#setBaseLayout()
	 */
	@Override
	protected void setBaseLayout() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		super.setBaseLayout();

		btn_home_main_01 = (Button) findViewById(R.id.btn_home_main_01);
		btn_home_main_01.setBackgroundResource(R.drawable.main_icon_01_on);
		btn_home_main_02 = (Button) findViewById(R.id.btn_home_main_02);
		btn_home_main_03 = (Button) findViewById(R.id.btn_home_main_03);
		btn_home_main_04 = (Button) findViewById(R.id.btn_home_main_04);
		btn_home_main_05 = (Button) findViewById(R.id.btn_home_main_05);

		btn_home_event_01 = findViewById(R.id.btn_home_event_01);
		bal_sing = findViewById(R.id.bal_sing);
		btn_home_event_02 = findViewById(R.id.btn_home_event_02);
		bal_listen = findViewById(R.id.bal_listen);
		btn_home_event_03 = findViewById(R.id.btn_home_event_03);
		btn_home_event_04 = findViewById(R.id.btn_home_event_04);
		btn_home_event_05 = findViewById(R.id.btn_home_event_05);
		layout_home_notice_menu = findViewById(R.id.layout_home_notice_menu);

		txt_home_sing_title = (TextView) btn_home_event_01.findViewById(R.id.txt_home_sing_title);
		txt_home_sing_artist = (TextView) btn_home_event_01.findViewById(R.id.txt_home_sing_artist);
		txt_home_listen_title = (TextView) btn_home_event_02.findViewById(R.id.txt_home_listen_title);
		txt_home_listen_artist = (TextView) btn_home_event_02.findViewById(R.id.txt_home_listen_artist);

		bal_sing.setVisibility(View.INVISIBLE);
		bal_listen.setVisibility(View.INVISIBLE);

		btn_main_sing = (Button) findViewById(R.id.btn_main_sing);
		btn_main_listen = (Button) findViewById(R.id.btn_main_listen);
		btn_main_my = (Button) findViewById(R.id.btn_main_my);
		btn_main_shop = (Button) findViewById(R.id.btn_main_shop);
		btn_main_customer = (Button) findViewById(R.id.btn_main_customer);

		bg_main = findViewById(R.id.bg_main);
		layout_body = findViewById(R.id.layout_body);

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	int resSing = 0;

	@Override
	public void displayMenuHome(int keyID) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		super.displayMenuHome(keyID);


		if (keyID == REMOTE_INIT) {
			remote.m_iState = STATE_HOME_MENU;
			remote.m_iMenuHomeFocus = 1;
			remote.m_iMenuHomeFocusY = 1;
			if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState());
		}

		if (resSing == 0) {
			btn_home_main_01.setBackgroundResource(R.drawable.main_icon_01);
			resSing = R.drawable.main_icon_01;
		}

		if (m_imgMainQuickBtn02 != null && util_mainQuickBtnOff02 != null) {
			m_imgMainQuickBtn02.setImageBitmap(util_mainQuickBtnOff02.m_bitMap);
		}

		resetHomeMenu();

		switch (remote.m_iMenuHomeFocus) {
			//노래 부르기
			case 1:
				btn_home_main_01.setLayoutParams(lpHomeOn);
				btn_home_main_01.setSelected(true);
				break;

			case 2:
				btn_home_main_02.setLayoutParams(lpHomeOn);
				btn_home_main_02.setSelected(true);
				break;
			case 3:
				btn_home_main_03.setLayoutParams(lpHomeOn);
				btn_home_main_03.setSelected(true);
				break;
			case 4:
				btn_home_main_04.setLayoutParams(lpHomeOn);
				btn_home_main_04.setSelected(true);
				break;
			case 5:
				btn_home_main_05.setLayoutParams(lpHomeOn);
				btn_home_main_05.setSelected(true);
				break;
			case 6:
				btn_home_event_01.setSelected(true);
				WidgetUtils.setTextViewMarquee(txt_home_sing_title, true);
				WidgetUtils.setTextViewMarquee(txt_home_sing_artist, true);
				bal_sing.setVisibility(View.VISIBLE);
				break;
			case 7:
				btn_home_event_02.setSelected(true);
				WidgetUtils.setTextViewMarquee(txt_home_listen_title, true);
				WidgetUtils.setTextViewMarquee(txt_home_listen_artist, true);
				bal_listen.setVisibility(View.VISIBLE);
				break;
			case 8:
				btn_home_event_03.setSelected(true);
				break;
			case 9:
				btn_home_event_04.setSelected(true);
				break;
			case 10:
				btn_home_event_05.setSelected(true);
				break;
			case 11:
				layout_home_notice_menu.setSelected(true);
				break;
		}

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * //컨텐츠 초기화 및 변경 및 조회
	 * //KP(keyID);
	 * //setListBackground(getMethodName());
	 */
	@Override
	public void displayMenuMain(int keyID) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.displayMenuMain(keyID);

		if (m_iMainMenuFocusSave == remote.m_iMenuMainFocus) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[OK]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + REMOTE_STATE.get(keyID));
			m_bListAlreadyReflashed = true;
		}

		m_iMainMenuFocusSave = remote.m_iMenuMainFocus;

		if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[NO]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + REMOTE_STATE.get(keyID));
			remote.m_iMenuHomeFocus = remote.m_iMenuMainFocus;
			m_bListAlreadyReflashed = false;
		}

		if (keyID == REMOTE_INIT) {
			m_bListAlreadyReflashed = false;
		}

		if (keyID == REMOTE_DOWN || keyID == REMOTE_ENTER) {
			clickMenu(remote.m_iMenuMainFocus);
			return;
		}

		if (keyID == REMOTE_UP) {
			return;
		}

		resetMainMenu();

		switch (remote.m_iMenuMainFocus) {
			// 노래부르기
			case 1:
				if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
					remote.m_iMenuSingFocus = 1;
				}

				m_iSongItemCount = 0;
				m_iCurrentSongListPage = 1;
				m_iCurrentViewSongListPage = 1;
				m_iTotalSongListPage = 1;
				m_iRequestPage = 1;

				btn_main_sing.setLayoutParams(lpMainOn);
				btn_main_sing.setBackgroundResource(R.drawable.sub_icon_01_on);

				// 서브메뉴 초기화 및 노래부르기 서브메뉴로 변경
				addViewSingMenu();

				break;
			// 녹음곡감상
			case 2:
				if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
					remote.m_iMenuListenFocus = 1;
				}

				// m_iSetListenItemCount = 0;
				m_iListenItemCount = 0;
				m_iListenItemCount = 0;
				m_iCurrentListenListPage = 1;
				m_iCurrentViewListenListPage = 1;
				m_iTotalListenListPage = 1;
				m_iRequestPage = 1;

				btn_main_listen.setLayoutParams(lpMainOn);
				btn_main_listen.setBackgroundResource(R.drawable.sub_icon_02_on);

				// 서브메뉴 초기화 및 녹음곡 감상 서브메뉴로 변경
				addViewListenMenu();

				break;
			// 마이노래방
			case 3:
				if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
					remote.m_iMenuMyFocus = 1;
				}

				m_iSongItemCount = 0;
				m_iCurrentSongListPage = 1;
				m_iCurrentViewSongListPage = 1;
				m_iTotalSongListPage = 1;
				m_iRequestPage = 1;

				btn_main_my.setLayoutParams(lpMainOn);
				btn_main_my.setBackgroundResource(R.drawable.sub_icon_03_on);

				// 서브메뉴 초기화 및 마이 노래방 서브메뉴로 변경
				addViewMyMenu();

				break;
			// 노래방샵
			case 4:
				if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
					remote.m_iMenuShopFocus = 1;
				}

				btn_main_shop.setLayoutParams(lpMainOn);
				btn_main_shop.setBackgroundResource(R.drawable.sub_icon_04_on);

				// 서브메뉴 초기화 및 노래방샵 서브메뉴로 변경
				addViewShopMenu();

				Button btnCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
				btnCertify.setVisibility(View.VISIBLE);

				break;
			// 고객센터
			case 5:
				if (m_iEnterCustomerMenu == CUSTOMER_ENTER_EVENT) {
					remote.m_iMenuCustomerFocus = 1;
				} else if (m_iEnterCustomerMenu == CUSTOMER_ENTER_NOTICE) {
					remote.m_iMenuCustomerFocus = 2;
				}

				m_iCustomerListItemCount = 0;
				m_iCurrentViewCustomerListPage = 1;
				m_iTotalCustomerListPage = 1;
				m_iCustomerListDetailTotalPage = 1;
				m_iCurrentCustomerListDetailPage = 1;

				m_strRequestCustomerDetailID = "";
				m_iCurrentCustomerListPage = 1;

				btn_main_customer.setLayoutParams(lpMainOn);
				btn_main_customer.setBackgroundResource(R.drawable.sub_icon_05_on);

				// 서브메뉴 초기화 및 고객센터 서브메뉴로 변경
				addViewCustomerMenu();

				break;
		}

		// 노래부르기->인기장르
		if (remote.m_iMenuMainFocus == 1 && remote.m_iMenuSingFocus == 3) {
			ShowGenre();
		} else {
			HideGenre();
		}

		if (layout_content.getChildCount() == 0) {
			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[NO]" + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + REMOTE_STATE.get(keyID));
			m_bListAlreadyReflashed = false;
		}

		this.keyID = keyID;

		if (!m_bListAlreadyReflashed) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + REMOTE_STATE.get(keyID));
			HideGenre();
			removeAllViewsContent();
			hideBody();
			postDelayed(displayMainBody, 500);
		}

		if (keyID == REMOTE_NONE) {
			showBody();
		}

		setListBackground(getMethodName());

		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	private int keyID = REMOTE_NONE;

	final private Runnable displayMainBody = new Runnable() {
		@Override
		public void run() {
			displayMainBody();
		}
	};

	private void displayMainBody() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		showBody();

		switch (remote.m_iMenuMainFocus) {
			// 노래부르기
			case 1:
				// 컨텐츠 초기화 및 노래부르기 컨텐츠로 변경
				addViewSingList();
				break;
			// 녹음곡감상
			case 2:
				addViewListenList();
				break;
			// 마이노래방
			case 3:
				// 컨텐츠 초기화 및 마이 노래방 컨텐츠로 변경
				break;
			// 노래방샵
			case 4:
				break;
			// 고객센터
			case 5:
				// 컨텐츠 초기화 및 고객센터 컨텐츠로 변경
				displayMenuCustomer(keyID);
				break;
		}

		KP(keyID);

		if (m_iMainMenuFocusSave == remote.m_iMenuMainFocus) {
			if (IKaraokeTV.DEBUG) Log.e(_toString(), "[OK]" + getMethodName() + "m_bListAlreadyReflashed:" + m_bListAlreadyReflashed + ":" + REMOTE_STATE.get(keyID));
			m_bListAlreadyReflashed = true;
		}

		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void KP(int keyID) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString() + "[KP]", getMethodName() + "[ST]" + REQUEST_KP.get(KP_REQUEST) + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());

		switch (remote.m_iMenuMainFocus) {
			// 노래부르기
			case 1:
				if (layout_content.findViewById(R.id.sing_list) != null && layout_content.findViewById(R.id.sing_list).getVisibility() != View.VISIBLE) {
					layout_content.findViewById(R.id.sing_list).setVisibility(View.VISIBLE);
				}
				KP(REQUEST_SONG_LIST, KP_1000, M1_MENU_SING, M2_SING_HOT);
				break;
			// 녹음곡감상
			case 2:
				if (layout_content.findViewById(R.id.listen_list) != null && layout_content.findViewById(R.id.listen_list).getVisibility() != View.VISIBLE) {
					layout_content.findViewById(R.id.listen_list).setVisibility(View.VISIBLE);
				}
				KP(REQUEST_LISTEN_LIST, KP_2100, M1_MENU_LISTEN, M2_LISTEN_TIMELINE);
				break;
			// 마이노래방
			case 3:
				if (layout_content.findViewById(R.id.sing_list) != null && layout_content.findViewById(R.id.sing_list).getVisibility() != View.VISIBLE) {
					layout_content.findViewById(R.id.sing_list).setVisibility(View.VISIBLE);
				}
				KP(REQUEST_MY_SUB_MENU, KP_1500, M2_MENU, M1_MENU_MYLIST);
				break;
			// 노래방샵
			case 4:
				if (layout_content.findViewById(R.id.shop_ticket) != null && layout_content.findViewById(R.id.shop_ticket).getVisibility() != View.VISIBLE) {
					layout_content.findViewById(R.id.shop_ticket).setVisibility(View.VISIBLE);
				}
				switch (remote.m_iMenuShopFocus) {
					case 1:
						KP(REQUEST_TICKET_SALES_STATE, "", "", "");
						break;
					case 2:
						KP(REQUEST_CERTIFY_STATE, KP_9000, M1_MAIN, M2_MENU);
						break;
					default:
						break;
				}
				break;
			// 고객센터
			case 5:
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + "m_bDisplayingCustomerDetail:" + m_bDisplayingCustomerDetail);
				if (m_iEnterCustomerMenu == CUSTOMER_ENTER_KEY) {
					switch (remote.m_iMenuCustomerFocus) {
						case 1:
							KP(REQUEST_EVENT_LIST, KP_0010, M1_MENU_HELP, M2_HELP_EVENT);
							break;
						case 2:
							KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_NOTICE);
							break;
						case 3:
							KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_USEINFO);
							break;
					}
				}
				break;
			default:
				break;

		}
		if (IKaraokeTV.DEBUG) Log.wtf(_toString() + "[KP]", getMethodName() + "[ED]" + REQUEST_KP.get(KP_REQUEST) + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

}
