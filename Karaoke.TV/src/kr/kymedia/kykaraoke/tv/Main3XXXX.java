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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Main3XXXX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main3XXXX.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.api._KPRequest;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *   상품서비스구매처리
 *   전문확인:로딩중단
 *   네트워크오류종료팝업표시
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-04-28
 */
class Main3XXXX extends Main3XXX {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (m_bShowMessageOK) {
			if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_BACK) {
				if (m_bIsExit) {
					Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[종료][팝업]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
					finish();
				}
				HideMessageOk();
			}
			return true;
		}

		if (isFinishing()) {
			Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[종료][중]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish() {
		Log.wtf("[KP]" + _toString(), getMethodName() + "[종료]" + "[KP]" + REQUEST_KP.get(KP_REQUEST) + ":" + OP + ", " + M1 + ", " + M2);
		super.finish();
		if (video != null) {
			video.finish();
		}
	}

	/**
	 * <pre>
	 * 네트워크오류종료팝업표시
	 * 네트워크 오류로 앱을 종료합니다.
	 * </pre>
	 */
	private void ShowMessageExitNetwork() {
		Log.wtf("[KP]" + _toString(), getMethodName() + "[KP]" + REQUEST_KP.get(KP_REQUEST) + ":" + OP + ", " + M1 + ", " + M2);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		String message = getString(R.string.message_error_network_timeout);
		message += "\n" + getString(R.string.message_error_network_timeout_exit_app);
		ShowMessageOk(POPUP_EXIT, getString(R.string.common_exit), message);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 홈화면에서오류발생시종료처리
	 */
	private void checkShowMessageExitNetwork() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		switch (remote.getState()) {
			case STATE_HOME_MENU:
			case STATE_MAIN_MENU:
				ShowMessageExitNetwork();
				break;
			default:
				break;
		}
	}

	@Override
	protected _KPRequest KP(_KPRequest KP) {
		try {
			if (KP != null) {
				KP.interrupt();
				KP.join();
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

		KP = new _KPRequest(this, handlerKP);

		KP.setKPParam(                            // KP.setKPParam(
				p_domain,                             // p_domain,
				p_apkname,                            // p_apkname,
				p_appname,                            // p_appname,
				p_debug,                              // p_debug,
				p_market,                            // p_market,
				p_ver,                                // p_ver,
				p_mid,                                // p_mid,
				p_stbid,                              // p_stbid,
				p_account,                            // p_account,
				p_ncode,                              // p_ncode,
				p_lcode,                              // p_lcode,
				p_model,                              // p_model,
				p_mac,                                // p_mac,
				p_readmac,                            // p_readmac,
				p_os,                                // p_os,
				p_osversion,                          // p_osversion,
				p_apkversioncode,                    // p_apkversioncode,
				p_apkversionname,                    // p_apkversionname,
				p_passtype,                          // p_passtype,
				p_passcnt,                            // p_passcnt,
				p_apikey);                            // p_apikey);

		return KP;
	}

	/**
	 * @see Main2#KP(int, String, String, String)
	 * @see Main2X#KP(int, String, String, String)
	 * @see Main3X#KP(int, String, String, String)
	 * @see Main3XXX#KP(int, String, String, String)
	 */
	@Override
	public void KP(int request, String OP, String M1, String M2) {
		Log.wtf("[KP]" + _toString(), "[KP]" + REQUEST_KP.get(request) + ":" + OP + ", " + M1 + ", " + M2 + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.KP(request, OP, M1, M2);
	}

	private _KPRequest _KP_4002;

	/**
	 * <pre>
	 * 전문확인:로딩중단
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	@Override
	protected void KP(Message msg) {
		super.KP(msg);

		if (null == COMPLETE_KP.get(msg.getData().getInt("state"))) {
			return;
		}

		switch (COMPLETE_KP.get(msg.getData().getInt("state"))) {
			case COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE:
			case COMPLETE_ERROR_REQUEST_NOT_RESPONSE:
				checkShowMessageExitNetwork();
				break;
			case COMPLETE_TICKET_SALES_STATE:
				if (("00000").equalsIgnoreCase(KP.result_code)) {
					_KP_4002 = KP;
					//Log.d("[KP]" + _toString(), "[KP]" + getMethodName() + "[TICKET]" + _KP_4002);
					/**
					 * 이건멀~~~까
					 */
					remote.putTicketItems(_KP_4002.getTicketItems());
					for (String key : remote.getTicketItems().keySet()) {
						TicketItem item = remote.getTicketItems().get(key);
						Log.wtf("[VASS]" + _toString(), "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());
					}
					/**
					 * KP_4000->VASS이용권조회
					 */
					VASS(REQUEST_VASS.REQUEST_VASS_PPX_CHECK);
				}
				break;
			case COMPLETE_CERTIFY_STATE:
				stopLoading(getMethodName());
				break;
			case COMPLETE_UTIL_SHOP_ITEM_01:
				break;
			case COMPLETE_UTIL_SHOP_ITEM_02:
				break;
			default:
				break;
		}
	}

	REQUEST_VASS VASS_REQUEST = REQUEST_VASS.REQUEST_VASS_NONE;
	/**
	 * <pre>
	 *   각사이트프로젝트별로구현하도록한다
	 *   여기서프로세스구현하지않는다고...
	 *   전문취소 처리로 해야하나???
	 * </pre>
	 */
	public void VASS(final REQUEST_VASS request) {
		Log.wtf("[VASS]" + _toString(), "[VASS]" + request);
		this.VASS_REQUEST = request;

		VASS.putTicketItems(remote.getTicketItems());
		VASS.setTicketKey(remote.key());

		VASS.setVASSParam(m_orgSTBID, p_mac);
		VASS.setRequest(request);
		VASS.setVASSUrl(m_strVASSPassword);

		boolean vass = false;

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_CHECK:
				vass = true;
				break;
			case REQUEST_VASS_PPX_CHECK_PLAY:
				vass = true;
				break;
			case REQUEST_VASS_PPX_PASSWORD:
				vass = true;
				break;
			case REQUEST_VASS_PPX_PURCHASE:
				vass = true;
				break;
			default:
				break;
		}

		if (vass) {
			if (_IKaraoke.DEBUG) Log.wtf("[VASS]" + _toString(), "[VASS]" + "[RQ]" + request);
			//isOpening = true;
			showCancel("VASS");
		}

		postDelayed(VASSstart, 100);
	}

	Runnable VASSstart = new Runnable() {

		@Override
		public void run() {
			if (null != VASS) {
				VASS.start();
			}
		}
	};

	/**
	 * 이용권확인
	 */
	private String p_passtype = "";

	protected boolean isPassUser() {
		Log.wtf("[VASS]" + _toString(), getMethodName() + p_passtype + ":" + m_bCouponUser);
		return (!TextUtil.isEmpty(p_passtype) || m_bCouponUser);
	}
	protected boolean isPassUserPPM() {
		Log.wtf("[VASS]" + _toString(), getMethodName() + p_passtype + ":" + m_bCouponUser);
		TicketItem item = VASS.getTicketItems().get(p_passtype);
		return (null != item && item.product_type == PRODUCT_TYPE.PPM);
	}

	@Override
	protected void KP_param(boolean adsID) {
		super.KP_param(adsID);
		p_passtype = "";
	}

	private _KPRequest _KP_4003;
	/**
	 * <pre>
	 * 1년약정
	 * 월이용권
	 * 일이용권
	 * </pre>
	 */
	private void VASSPPX(COMPLETE_VASS state) {
		Log.d("[VASS]" + "[KP]" + _toString(),"[VASS]" + getMethodName() + "[ST]" + VASS_REQUEST + ":" + state + ":" + VASS.isSuccess() + ":" + VASS);

		TicketItem item = VASS.getTicketItem();

		/**
		 * 오류처리
		 */
		if (!("Y").equalsIgnoreCase(VASS.isSuccess())) {
			/**
			 * 이용권표시
			 */
			p_passtype = "";
			setBottomTicketText();

			String code = item.id_product + ":" + VASS.getVASSErrorCode();
			String text = getString(R.string.message_error_ticket_buy) + "(" + code + ")";

			Log.wtf("[VASS]" + "[KP]" + _toString(),"[VASS]" + getMethodName() + "[NG]" + VASS_REQUEST + ":" + state + ":" + VASS.isSuccess() + ":" + text);

			TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);

			if (null != txtPassResult) {
				txtPassResult.setText(text);
				WidgetUtils.setTextViewMarquee(txtPassResult, true);
			}
			return;
		}

		/**
		 * 이용권표시
		 */
		p_passtype = item.p_passtype;
		setBottomTicketText();

		/**
		 * 구매결과표시
		 */
		switch (state) {
			case COMPLETE_VASS_PPX_PURCHASE:
				exitPPV();
				showMessagePPX();
				remote.m_iState = STATE_MESSAGE_PPX_SUCCESS;
				try {
					LinearLayout layoutOKCancel = (LinearLayout) findViewById(R.id.layout_ppv_okcancel);
					layoutOKCancel.setVisibility(View.GONE);

					LinearLayout layoutComment = (LinearLayout) findViewById(R.id.layout_ppv_comment);
					layoutComment.setVisibility(View.VISIBLE);

					TextView txtRed = (TextView) findViewById(R.id.txt_message_ticket_day_bill_red);
					TextView txtBlack = (TextView) findViewById(R.id.txt_message_ticket_day_bill_black);

					txtRed.setVisibility(View.VISIBLE);
					txtBlack.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}

		/**
		 * 구매결과처리
		 */
		m_bIsGoToCertifyMessage = false;

		/**
		 * KP_4003:REUTRN_DATA
		 */
		RETURN_DATA = item.id_product;
		RETURN_DATA += "|" + VASS.RETURN_DATA().replace("^", "|");

		Log.i("[VASS]" + "[KP]" + _toString(), "[VASS]" + getMethodName() + "[PASSTYPE]" + item.p_passtype + "-" + p_passtype + "[RETURN_DATA]" + RETURN_DATA);

		/**
		 * KP_4003 호출
		 * @see kr.kymedia.kykaraoke.tv.api.KPRequest#setTicketPurchaseCompleteUrl(String, String)
		 */
		//KP(REQUEST_TICKET_PURCHASE_COMPLETE, KP_4003, M2_MENU_SHOP, M2_SHOP_TICKET);
		_KP_4003 = KP(_KP_4003);
		_KP_4003.setTicketPurchaseCompleteUrl(KP_4003, RETURN_DATA);
		_KP_4003.setRequestType(REQUEST_TICKET_PURCHASE_COMPLETE);
		_KP_4003.start();

		/**
		 * 이용기간(VASS결제후정보)
		 */
		String date = TextUtil.getToday(getString(R.string.message_info_ticket_end_date_format), item.end_date);
		Log.wtf("[VASS]" + "[KP]" + _toString(),"[VASS]" + getMethodName() + "[확인]" + VASS_REQUEST + ":" + date + ":" + item.end_date);
		TextView txtDate = (TextView) findViewById(R.id.txt_end_date);
		if (null != txtDate && !TextUtil.isEmpty(date)) {
			// 날짜박기
			String header = "";
			txtDate.setText(header + " " + date);
		}

		Log.d("[VASS]" + "[KP]" + _toString(),"[VASS]" + getMethodName() + "[ED]" + VASS_REQUEST + ":" + state + ":" + VASS.isSuccess());
	}

	/**
	 * @see Main3XXXXX#showMessagePPX()
	 */
	protected void showMessagePPX() {
	}

	/**
	 * @see Main2XX#startLoading(String, int)
	 */
	@Override
	protected void startLoading(String method, int time) {
		super.startLoading(method, time);
	}

	/**
	 * @see Main2XX#stopLoading(String)
	 */
	@Override
	protected void stopLoading(String method) {
		/*if (BuildConfig.DEBUG) */
		Log.d("[VASS]" + _toString(), "[VASS]" + "stopLoading() " + method + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.stopLoading(method);

		setBottomTicketText();
	}

	@Override
	protected String setBottomTicketText() {
		Log.wtf("[VASS]" + _toString(), "[VASS]" + getMethodName() + p_passtype + ":" + VASS);
		String text = getString(R.string.message_error_ticket_no);

		try {
			TicketItem item = null;
			if (BuildConfig.DEBUG) Log.wtf("[VASS]" + _toString(), "[VASS]" + getMethodName() + item);
			if (null != VASS && null != VASS.getTicketItems()) {
				item = VASS.getTicketItems().get(p_passtype);
			}
			if (null != item) {
				text = String.format(getString(R.string.message_info_ticket_have_ppx), item.product_name);
				if (!TextUtil.isEmpty(item.end_date)) {
					text += " " + TextUtil.getToday("yyyyMMddHHmmss", getString(R.string.message_info_ticket_end_date_format), item.end_date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (m_bCouponUser) {
			text = (m_strCouponTerm);
		}

		setBottomProductText(text);
		return text;
	}

	@Override
	public void VASS(Message msg) {
		Log.wtf("[VASS]" + _toString(), "[VASS]" + VASS_REQUEST + ":" + COMPLETE_VASS.get(msg.getData().getInt("state")) + ":" + msg + ":" + VASS.isSuccess() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.VASS(msg);

		if (null == COMPLETE_VASS.get(msg.getData().getInt("state"))) {
			return;
		}

		switch (COMPLETE_VASS.get(msg.getData().getInt("state"))) {
			case COMPLETE_VASS_PPX_CHECK:
			case COMPLETE_VASS_PPX_CHECK_PLAY:
			case COMPLETE_VASS_PPX_PURCHASE:
				VASSPPX(COMPLETE_VASS.get(msg.getData().getInt("state")));
				break;
		}

		switch (COMPLETE_VASS.get(msg.getData().getInt("state"))) {
			case COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE:
			case COMPLETE_ERROR_REQUEST_NOT_RESPONSE:
				checkShowMessageExitNetwork();
				break;
			case COMPLETE_VASS_PPX_PASSWORD:
				if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
					VASS(REQUEST_VASS.REQUEST_VASS_PPX_PURCHASE);
				} else {
					stopLoading(getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")));
					TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);
					txtPassResult.setText(getString(R.string.ticket_popup_pass_error));
				}
				break;
			case COMPLETE_VASS_PPX_PURCHASE:
				/**
				 * <pre>
				 * 1년약정
				 * 월이용권
				 * 일이용권
				 * </pre>
				 */
				stopLoading(getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")));
				//if (TextUtil.isEmpty(item.end_date)) {
				//	item.end_date = TextUtil.getToday("yyyyMMddHHmmss", item.product_term);
				//}
				break;
			case COMPLETE_VASS_PPX_CHECK:
				/**
				 * isyoon:홈화면에만 처리한다.
				 */
				if (PANE_STATE.PANE_HOME == PANE_STATE.get(m_iPaneState)) {
					KP(REQUEST_MAIN, KP_0000, M1_MAIN, M2_MENU);
				} else {
					stopLoading(getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")));
				}
				break;
			case COMPLETE_VASS_PPX_CHECK_PLAY:
				TryPlaySong();
				break;
		}

	}

}
