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
 * filename	:	Main3XXXXX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main3XXXXX.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv;

import android.graphics.Paint;
import android.os.Build;
import android.os.Message;
import android.support.v4.widget.Space;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *  이용권기능 대~~~개조작전
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-07-12
 */
public class Main3XXXXX extends Main3XXXX {
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
	public void KP(int request, String OP, String M1, String M2) {
		super.KP(request, OP, M1, M2);

		switch (request) {
			case REQUEST_TICKET_SALES_STATE:
				remote.setIndex(0);
				break;
			case REQUEST_COUPON_REGIST:
				break;
			default:
				break;
		}
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
		super.KP(msg);

		//isyoon:홈화면에선 처리 안한다.
		if (PANE_STATE.PANE_MAIN == PANE_STATE.get(m_iPaneState)) {
			switch (COMPLETE_KP.get(msg.getData().getInt("state"))) {
				case COMPLETE_TICKET_SALES_STATE:
					/**
					 * 이건remote에정의하고
					 */
					remote.m_iShopTicketFocusY = 0;
					remote.m_iShopTicketFocusX = 1;
					// 이건아니고?그지?
					//m_iCouponFocus = 1;
					addViewShopList();
					addViewShopTabs();
					displayShopTicket(REMOTE_INIT);
					break;
				case COMPLETE_COUPON_REGIST:
					/**
					 * 이건remote에정의하고
					 */
					remote.m_iShopTicketFocusY = 0;
					remote.m_iShopTicketFocusX = 1;
					// 이건아니고?그지?
					//m_iCouponFocus = 1;
					//addViewShopTabs();
					displayShopTicket(REMOTE_INIT);
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected void addViewShopList() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + remote.key());
		super.addViewShopList();
	}

	private void addViewShopTabs() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ST]" + remote.key());

		boolean add = false;

		for (String key : remote.getTicketItems().keySet()) {
			if (null == remote.getTicketItems().get(key).button) {
				add = true;
				break;
			}
		}

		//만들까마까
		if (!add) {
			if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[OK]" + remote.key());
			return;
		}

		LinearLayout ll = (LinearLayout) findViewById(R.id.layout_shop_type);
		ll.removeAllViews();

		ll.addView(new Space(this), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3));
		//addView(ll, new Space(this), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3));

		ArrayList<String> keys = new ArrayList<>();
		for (String key : remote.getTicketItems().keySet()) {
			TicketItem item = remote.getTicketItems().get(key);
			keys.add("" + key);

			Button button = new Button(this);
			button.setPadding(0, 0, 0, 0);
			button.setGravity(Gravity.CENTER);
			button.setClickable(true);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				button.setTextColor(getColor(android.R.color.white));
			} else {
				button.setTextColor(getResources().getColor(android.R.color.white));
			}
			button.setBackgroundResource(R.drawable.shop_ticket_tab);

			item.button = button;
			item.button.setText(item.product_name);

			ll.addView(item.button, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 22));
			//addView(ll, item.button, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 22));

			ll.addView(new Space(this), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
			//addView(ll, new Space(this), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

			if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + remote.getTicketItems().get(key));
		}

		ll.addView(new Space(this), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 65));
		//addView(ll, new Space(this), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 65));

		_childViews(ll);

		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ED]" + remote.key());
	}

	private void showTicket() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName());
		try {
			findViewById(R.id.layout_shop_ticket_body).setVisibility(View.VISIBLE);
			findViewById(R.id.layout_shop_ticket_coupon).setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_shop_kp_info).setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showCoupon() {
		//test
		//m_bCouponUser = true;
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + m_bCouponUser);
		try {
			findViewById(R.id.layout_shop_ticket_body).setVisibility(View.INVISIBLE);
			findViewById(R.id.layout_shop_ticket_coupon).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_shop_kp_info).setVisibility(View.INVISIBLE);
			/**
			 * 쿠폰내용표시
			 */
			LinearLayout layout_coupon_registed = (LinearLayout) findViewById(R.id.layout_coupon_registed);
			LinearLayout layout_coupon_not_regist = (LinearLayout) findViewById(R.id.layout_coupon_not_regist);
			if (m_bCouponUser) {
				layout_coupon_registed.setVisibility(View.VISIBLE);
				layout_coupon_not_regist.setVisibility(View.INVISIBLE);
				TextView txtCouponTerm = (TextView) findViewById(R.id.txt_coupon_term);
				txtCouponTerm.setText(m_strCouponTerm);
			} else {
				layout_coupon_registed.setVisibility(View.INVISIBLE);
				layout_coupon_not_regist.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clickMenuShop() {
		if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + remote.key());
		try {
			switch (remote.m_iMenuShopFocus) {
				case 1:
					remote.m_iState = STATE_SHOP_TICKET;
					remote.m_iShopTicketFocusY = 1;
					remote.m_iShopTicketFocusX = 1;

					Button btnTicket = (Button) findViewById(R.id.btn_shop_sub_ticket);
					if (btnTicket != null) {
						btnTicket.setPressed(true);
					}

					Button btnPurchase = (Button) findViewById(R.id.btn_shop_purchase);
					if (btnPurchase != null) {
						btnPurchase.setSelected(true);
					}

					displayShopTicket(REMOTE_INIT);
					break;
				case 2:
					remote.m_iState = STATE_SHOP_CERTIFY;

					Button btnCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
					if (btnCertify != null) {
						//븅신개삽지랄
						btnCertify.setPressed(true);
					}

					if (/*!m_bIsCertifyedUser*/TextUtil.isEmpty(KP.auth_date)) {
						ImageView imgCertify = (ImageView) findViewById(R.id.img_shop_certify);
						if (imgCertify != null) {
							imgCertify.setImageResource(R.drawable.shop_certify_on);
						}
					} else {
						LinearLayout layoutCertify = (LinearLayout) findViewById(R.id.layout_already_certify);
						if (layoutCertify != null) {
							layoutCertify.setBackgroundResource(R.drawable.shop_certify_already_on);
						}
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void clearCouponEdit() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);

		EditText edit_coupon = (EditText) findViewById(R.id.edit_coupon);
		View btn_coupon_regist = findViewById(R.id.btn_coupon_regist);

		btn_coupon_regist.setSelected(false);
		btn_coupon_regist.setPressed(false);

		clearFocus(edit_coupon);
		hideSoftKeyboard(edit_coupon);

	}

	private void requestCouponEdit() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);

		EditText edit_coupon = (EditText) findViewById(R.id.edit_coupon);
		View btn_coupon_regist = findViewById(R.id.btn_coupon_regist);

		btn_coupon_regist.setPressed(false);
		if (remote.m_iShopTicketFocusY == 2 && remote.m_iShopTicketFocusX == 2) {
			btn_coupon_regist.setSelected(true);
		} else {
			btn_coupon_regist.setSelected(false);
		}

		if (remote.m_iShopTicketFocusY == 2 && remote.m_iShopTicketFocusX == 1) {
			requestFocus(edit_coupon);
			setIMELocation();/*showSoftKeyboard(edit_coupon);*/
		} else {
			clearFocus(edit_coupon);
			hideSoftKeyboard(edit_coupon);
		}

	}

	@Override
	public void displayShopTicket(int keyID) {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);

		if (null == findViewById(R.id.shop_ticket)) {
			if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[NG]" + REMOTE_STATE.get(keyID) + ":" + findViewById(R.id.shop_ticket));
			return;
		}

		/**
		 * 이용권확인
		 */
		TicketItem item = remote.getTicketItem();
		if (BuildConfig.DEBUG) Log.wtf("[VASS]" + _toString(), getMethodName() + item);

		int res = R.drawable.img_passtype_none;
		///**
		// * 작업전이야 - 시발
		// */
		//if ((PASS_TYPE_YEAR).equalsIgnoreCase(TextUtil.trim(remote.key()))) {
		//	res = R.drawable.img_passtype_year;
		//} else if ((PASS_TYPE_MONTH).equalsIgnoreCase(TextUtil.trim(remote.key()))) {
		//	res = R.drawable.img_passtype_month;
		//} else if ((PASS_TYPE_DAY).equalsIgnoreCase(TextUtil.trim(remote.key()))) {
		//	res = R.drawable.img_passtype_day;
		//} else if ((PASS_TYPE_COUPON).equalsIgnoreCase(TextUtil.trim(remote.key()))) {
		//	if (m_bCouponUser) {
		//		res = R.drawable.img_passtype_coupon_on;
		//	} else {
		//		res = R.drawable.img_passtype_coupon_off;
		//	}
		//}
		///**
		// * 작업전이야 - 종말
		// */

		//if (null != findViewById(R.id.img_shop_item))
		{
			if (!TextUtil.isEmpty(item.product_image)) {
				putURLImage((ImageView) findViewById(R.id.img_shop_item), item.product_image, false);
			} else {
				((ImageView) findViewById(R.id.img_shop_item)).setImageResource(res);
			}
			if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + "[IMAGE]" + item.product_image);
		}

		//test
		//item.real_price -= 1000;
		/**
		 * 이용금액처리
		 */
		DecimalFormat formatter = new DecimalFormat("#,###,###");
		String price = formatter.format(item.price) + " " + getString(R.string.ticket_popup_content_price);
		((TextView) findViewById(R.id.txt_shop_price)).setText(price);
		String real_price = formatter.format(item.real_price) + " " + getString(R.string.ticket_popup_content_price);
		((TextView) findViewById(R.id.txt_shop_real_price)).setText(real_price);
		/**
		 * 이용금약할인
		 */
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)findViewById(R.id.txt_shop_price).getLayoutParams();
		if (item.real_price < item.price) {
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
			((TextView) findViewById(R.id.txt_shop_price)).setPaintFlags(((TextView) findViewById(R.id.txt_shop_price)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			findViewById(R.id.txt_shop_price).setVisibility(View.VISIBLE);
			findViewById(R.id.txt_shop_real_price).setVisibility(View.VISIBLE);
		} else {
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			((TextView) findViewById(R.id.txt_shop_price)).setPaintFlags(((TextView) findViewById(R.id.txt_shop_price)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
			findViewById(R.id.txt_shop_price).setVisibility(View.VISIBLE);
			findViewById(R.id.txt_shop_real_price).setVisibility(View.GONE);
		}
		findViewById(R.id.txt_shop_price).setLayoutParams(layoutParams);

		/**
		 * 상품정보
		 */
		String product_info = getString(R.string.app_ticket_vat);

		/**
		 * 상품정보/이용권/무료쿠폰등록-보이기/숨기기
		 */
		switch (remote.product_type()) {
			case NONE:
				break;
			case PPM:
			case PPV:
				product_info = getString(R.string.app_ticket_vat);
				showTicket();
				clearCouponEdit();
				break;
			case CPN:
				product_info = getString(R.string.coupon_subscripte);
				showCoupon();
				requestCouponEdit();
				break;
		}

		/**
		 * 상품정보
		 */
		if (null != item && !TextUtil.isEmpty(item.product_info)) {
			product_info = item.product_info;
		}

		((TextView) findViewById(R.id.txt_product_info)).setText(product_info);

		/**
		 * 포커싱 - 확인
		 */
		boolean focus = (remote.m_iShopTicketFocusY == 1 || item.product_type == PRODUCT_TYPE.CPN);
		if (BuildConfig.DEBUG) Log.wtf("[VASS]" + _toString(), getMethodName() + "[FOCUS]" + REMOTE_STATE.get(keyID) + ":" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);

		/**
		 * 포커싱 - 이용권선택
		 */
		if (focus) {
			findViewById(R.id.img_shop_focus).setVisibility(View.VISIBLE);
			findViewById(R.id.btn_shop_purchase).setSelected(true);
		} else {
			findViewById(R.id.img_shop_focus).setVisibility(View.INVISIBLE);
			findViewById(R.id.btn_shop_purchase).setSelected(false);
		}

		/**
		 * 포커싱 - 탭버튼셀렉숀
		 */
		for (String key : remote.getTicketItems().keySet()) {
			if (null != remote.getTicketItems().get(key).button) {
				if (item == remote.getTicketItems().get(key)) {
					if (remote.m_iShopTicketFocusY == 1 && focus) {
						remote.getTicketItems().get(key).button.setSelected(true);
						remote.getTicketItems().get(key).button.setPressed(false);
					} else {
						remote.getTicketItems().get(key).button.setSelected(false);
						remote.getTicketItems().get(key).button.setPressed(true);
					}
				} else {
					remote.getTicketItems().get(key).button.setSelected(false);
					remote.getTicketItems().get(key).button.setPressed(false);
				}
			}
		}

		/**
		 * 포커싱 - 모바일앱설치안내
		 */
		if (remote.m_iShopTicketFocusY == 2) {
			findViewById(R.id.btn_shop_kp_info).setSelected(true);
			findViewById(R.id.btn_shop_kp_info).setPressed(false);
		} else {
			findViewById(R.id.btn_shop_kp_info).setSelected(false);
			findViewById(R.id.btn_shop_kp_info).setPressed(false);
		}

		/**
		 * 포커싱 - 키입력
		 */
		if (keyID == REMOTE_UP) {
			if (remote.m_iShopTicketFocusY == 0) {
				exitTicket();
				return;
			}
		}

		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);
	}

	@Override
	public void exitTicket() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ST]" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);
		remote.m_iState = STATE_SHOP_MENU;

		/**
		 * 이건remote에정의하고
		 */
		remote.m_iShopTicketFocusY = 0;
		remote.m_iShopTicketFocusX = 1;
		// 이건아니고?그지?
		//m_iCouponFocus = 1;

		resetShopSubMenu();

		findViewById(R.id.btn_shop_sub_ticket).setSelected(true);

		//이용권확인
		TicketItem item = remote.getTicketItem();
		if (BuildConfig.DEBUG) Log.wtf("[VASS]" + _toString(), getMethodName() + item);

		//탭버튼셀렉숀
		for (String key : remote.getTicketItems().keySet()) {
			if (null != remote.getTicketItems().get(key).button) {
				if (item == remote.getTicketItems().get(key)) {
					remote.getTicketItems().get(key).button.setPressed(true);
				}
			}
		}

		//이용권포커싱
		findViewById(R.id.img_shop_focus).setVisibility(View.INVISIBLE);

		findViewById(R.id.btn_shop_purchase).setSelected(false);

		findViewById(R.id.btn_shop_kp_info).setSelected(false);

		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ED]" + ":" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);
	}

	protected void showMessagePPX() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);

		TicketItem item = remote.getTicketItem();

		message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_ppx_info);

		if (null != message_ticket) {
			/**
			 * 상품명
			 */
			((TextView) findViewById(R.id.txt_product_name)).setText(getString(R.string.app_title) + " " + item.product_name);


			/**
			 * 상품유형
			 */
			String product_type = "";
			switch (item.product_type) {
				case NONE:
					product_type = getString(R.string.product_type_none);
					break;
				case PPM:
					product_type = getString(R.string.product_type_ppm);
					break;
				case PPV:
					product_type = getString(R.string.product_type_ppv);
					break;
				case CPN:
					product_type = getString(R.string.product_type_cpn);
					break;
			}
			((TextView) findViewById(R.id.txt_product_type)).setText(product_type);

			/**
			 * 이용기간(VASS결제전정보)
			 */
			String date = TextUtil.getToday(getString(R.string.message_info_ticket_end_date_format), item.product_term);
			Log.wtf("[VASS]" + "[KP]" + _toString(),"[VASS]" + getMethodName() + "[확인]" + VASS_REQUEST + ":" + date + ":" + item.end_date);
			TextView txtDate = (TextView) findViewById(R.id.txt_end_date);
			if (null != txtDate && item.product_term > 0 &&  !TextUtil.isEmpty(date)) {
				// 날짜박기
				String header = "";
				txtDate.setText(header + " " + date);
			}

			/**
			 * 이용금액
			 */
			DecimalFormat formatter = new DecimalFormat("#,###,###");
			String price = formatter.format(item.price) + getString(R.string.ticket_popup_content_vat_price);
			((TextView) findViewById(R.id.txt_price)).setText(price);

			remote.m_iState = STATE_MESSAGE_PPX_INFO;
			m_iTicketMessageFocusX = 1;
			m_iTicketMessageFocusY = 1;

			setSelectedPPXInfoOkCancel(REMOTE_NONE);
		}
	}

	private void showMessageCPN() {
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);
		//if (remote.m_iShopTicketFocusY == 2 && remote.m_iShopTicketFocusX == 2)
		{
			EditText edit_coupon = (EditText) findViewById(R.id.edit_coupon);

			if (null != edit_coupon && null != edit_coupon.getText()) {
				m_strCouponSerial = edit_coupon.getText().toString();
			}

			if (null != m_strCouponSerial && !TextUtil.isEmpty(m_strCouponSerial)) {
				m_strCouponSerial.replace(" ", "");
			}

			if (TextUtil.isEmpty(m_strCouponSerial)) {
				ShowMessageOk(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.coupon_not_input_serial));
			} else {
				if (m_strCouponSerial.length() != 12) {
					ShowMessageOk(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.coupon_wrong_input_serial));
					return;
				}

				KP(REQUEST_COUPON_REGIST, KP_0014, M2_MENU, M2_MENU_SHOP);
			}
		}
	}

	@Override
	public void clickShopTicket() {
		if (BuildConfig.DEBUG) Log.wtf("[VASS]" + _toString(), getMethodName() + remote.product_type());
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ST]" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);
		switch (remote.product_type()) {
			case NONE:
				break;
			case PPM:
			case PPV:
				if (remote.m_iShopTicketFocusY == 1) {
					// bgkimt 이미 사용중인 이용권이 있으면 이용권 구매 차단
					// isyoon 멜~~~롱 그래도 할꺼 걸랑!!!
					if (!BuildConfig.DEBUG && isPassUser()) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.ticket_already_use));
					} else {
						showMessagePPX();
					}
				} else if (remote.m_iShopTicketFocusY == 2) {
					//고객센터->모바일설치안내
					goCustomer(true, 4);
				}
				break;
			case CPN:
				showMessageCPN();
				break;
		}
		if (BuildConfig.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "[ED]" + ":" + remote.key() + ":m_iShopTicketFocusY:" + remote.m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + remote.m_iShopTicketFocusX);
	}

	/**
	 * <pre>
	 * 휴대폰 번호 등록 -> 열기
	 * 강우석:이용권사용자확인기능제거 - 디버그버전예외
	 * </pre>
	 * @since : 2016-09-02
	 */
	@Override
	public void clickShopCertify() {
		if (BuildConfig.DEBUG) Log.w("[VASS]" + _toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		// bgkimt [인증번호등록] 버튼 눌렀을 때 인증센터 팝업 띄운다. 월정액 이용권 없으면 튕겨준다
		// bgkimt 인증번호 변경 가능 횟수가 다 했어도 튕겨준다.
		if (BuildConfig.DEBUG || isPassUserPPM()) {
			if (BuildConfig.DEBUG || auth_modify_idx > 0) {
				remote.m_iCertifyHPFocusX = 1;
				remote.m_iCertifyHPFocusY = 1;

				message_hp = (LinearLayout) addPopup(R.layout.message_hp);

				remote.m_iState = STATE_CERTIFY_HP;

				displayCertifyHP(REMOTE_NONE);
			} else {
				ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.message_error_certify_count));
			}
		} else {
			ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.message_error_certify_ticket_month_no));
		}
		///**
		// * 강우석:이용권사용자확인기능제거 - 디버그버전예외
		// */
		//if (BuildConfig.DEBUG || auth_modify_idx > 0) {
		//	remote.m_iCertifyHPFocusX = 1;
		//	remote.m_iCertifyHPFocusY = 1;
		//	message_hp = (LinearLayout) addPopup(R.layout.message_hp);
		//	remote.m_iState = STATE_CERTIFY_HP;
		//	displayCertifyHP(REMOTE_NONE);
		//} else {
		//	ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.message_error_certify_count));
		//}
		if (BuildConfig.DEBUG) Log.w("[VASS]" + _toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void clickPPXInfo() {
		if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + remote.key());
		super.clickPPXInfo();
	}

	@Override
	public void clickPPXNotice() {
		if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + remote.key());
		super.clickPPXNotice();
	}

	@Override
	public void clickPPXPass() {
		if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + remote.key());
		switch (m_iTicketMessageFocusX) {
			case POPUP_OK:
				if (m_iTicketMessageFocusY == 2) {
					if (m_strInputPass[0] == "" || m_strInputPass[1] == "" || m_strInputPass[2] == "" || m_strInputPass[3] == "") {
						TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);
						txtPassResult.setText(getString(R.string.ticket_popup_pass_info_03));
					} else {
						m_strVASSPassword = "";
						m_strVASSPassword = m_strInputPass[0] + m_strInputPass[1] + m_strInputPass[2] + m_strInputPass[3];

						switch(remote.product_type()) {
							case NONE:
								break;
							case PPM:
							case PPV:
								startLoading(getMethodName(), LOADING_LONG);
								VASS(REQUEST_VASS.REQUEST_VASS_PPX_PASSWORD);
								break;
							case CPN:
								break;
						}
					}
				}
				break;
			case POPUP_BACK:
				exitPPV();
				remote.m_iState = STATE_MESSAGE_PPX_INFO;
				m_iTicketMessageFocusX = 1;
				showMessagePPX();
				break;
			case POPUP_CANCEL:
				exitPPV();
				break;
		}
		setSelectedPPXInfoOkCancel(REMOTE_NONE);
	}

}
