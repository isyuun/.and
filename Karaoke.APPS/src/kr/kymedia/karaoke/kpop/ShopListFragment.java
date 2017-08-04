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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	NoticeListFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ NoticeListFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.ShopListAdapter;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 16.
 * @version 1.0
 */

class ShopListFragment extends _BaseListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public static Fragment newInstance(Bundle extras) {

		return null;
	}

	ShopListAdapter mShopListAdapter = null;

	/**
	 * KP_4020 내 이용권정보
	 */
	KPnnnn KP_4020 = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_shop, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void start() {

		super.start();
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mShopListAdapter == null) {
			mShopListAdapter = new ShopListAdapter(getBaseActivity(), R.layout.item_shop, KP_nnnn.getLists(), new View.OnClickListener() {

				@Override
				public void onClick(View view) {

					onListItemClick(view);
				}
			}, getApp().getImageDownLoader());
		}

		mShopListAdapter.setNotifyOnChange(false);
		setListAdapter(mShopListAdapter);
		ColorDrawable divider = new ColorDrawable(this.getResources().getColor(R.color.solid_gray));
		getListView().setDivider(divider);
		getListView().setDividerHeight(1);

		showTicketInfo(false);

	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_4020 = KP_init(mContext, KP_4020);

	}

	protected void KP_4020() {

		KP_4020.KP_4020(getApp().p_mid, p_m1, p_m2, "TERM");
	}

	/**
	 * <pre>
	 * TODO
	 * KP_4001 상품권 목록
	 * KP_4002(이용권 목록)
	 * KP_4004(충전 목록)
	 * </pre>
	 * 
	 */
	@Override
	public void KP_nnnn(KPItem info, KPItem list, boolean clear) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.KP_nnnn(info, list, clear);

		Map<String, String> params = KP_0000(info, list);

		clear();

		if (getString(R.string.M2_SHOP_TICKET).equalsIgnoreCase(p_m2)) {
			KP_nnnn.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_4002", clear, params);
		} else if (getString(R.string.M2_SHOP_CHARGE).equalsIgnoreCase(p_m2)) {
			KP_nnnn.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_4004", clear, params);
		} else {
			KP_nnnn.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_4001", clear, params);
		}

	}

	@Override
	public void KP_list(final int page) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.KP_list(page);

		boolean clear = true;
		// if (page == 1) {
		// clear = true;
		// startLoading(__CLASSNAME__, getMethodName());
		// }

		KP_nnnn(getInfo(), getList(), clear);
	}

	@Override
	public void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + isQueryOnManual + "," + isQueryOnActivityCreate);

		super.onResume();
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);

		if (what != _IKaraoke.STATE_DATA_QUERY_START) {
			stopLoading(__CLASSNAME__, getMethodName());
		}

		KPItem info = null;
		String value = "";
		View v = null;


		if (("KP_4000").equalsIgnoreCase(p_opcode) || ("KP_4001").equalsIgnoreCase(p_opcode) || ("KP_4002").equalsIgnoreCase(p_opcode) || ("KP_4004").equalsIgnoreCase(p_opcode)) {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
			// 상품권 목록
			if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				info = KP_nnnn.getInfo();
				if (info != null) {
					value = info.getValue("title");
					v = findViewById(R.id.point_title);
					if (v != null && !TextUtil.isEmpty(value)) {
						((TextView) v).setText(value);
					}
					value = info.getValue("point_info");
					v = findViewById(R.id.point_info);
					if (v != null && !TextUtil.isEmpty(value)) {
						((TextView) v).setText(value);
					}
					value = info.getValue("url_point");
					v = findViewById(R.id.point_img);
					if (v != null && !URLUtil.isNetworkUrl(value)) {
						putURLImage(mContext, (ImageView) v, value, true, R.drawable.bg_trans);
					}
				}
			} else if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
			}
		} else if (("KP_4005").equalsIgnoreCase(p_opcode) || ("KP_4010").equalsIgnoreCase(p_opcode) || ("KP_4003").equalsIgnoreCase(p_opcode) || ("KP_4011").equalsIgnoreCase(p_opcode)) {
			// KP_4010/KP_4011 상품권 구매
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				getBaseActivity().refresh();
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					if (("KP_4003").equalsIgnoreCase(p_opcode) || ("KP_4011").equalsIgnoreCase(p_opcode)) {
						getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), null, null, null, true, null);
					}
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				} else if (what != _IKaraoke.STATE_DATA_QUERY_START) {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				}
			}
		} else if (("KP_4020").equalsIgnoreCase(p_opcode)) {
			// 내 이용권 정보
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KP4020();
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				} else {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				}
			}
		}

	}

	void KP4020() {
		KPItem info = KP_4020.getInfo();

		View v = null;
		String value = "";
		if (info != null) {
			// 나의 이용 정보
			v = getView().findViewById(R.id.goodsname);
			value = info.getValue(LOGIN.KEY_GOODSNAME);
			if (!TextUtil.isEmpty(value)) {
				putValue(v, value);
			}
			// 이용권정보
			v = getView().findViewById(R.id.txt_ticket_info);
			value = info.getValue("using_info");
			if (!TextUtil.isEmpty(value)) {
				putValue(v, value);
			}
			// 이용권구매일시
			v = getView().findViewById(R.id.txt_ticket_pay_date);
			value = info.getValue("pay_date");
			if (!TextUtil.isEmpty(value)) {
				putValue(v, value);
			}
			// 이용권만료일시
			v = getView().findViewById(R.id.txt_ticket_valid_date);
			value = info.getValue("valid_date");
			if (!TextUtil.isEmpty(value)) {
				putValue(v, value);
			}
			// 이용권재구매처리
			// value = info.getString("repay_yn");
			// if ("Y".equalsIgnoreCase(value)) {
			// v.setVisibility(View.VISIBLE);
			// } else {
			// v = getView().findViewById(R.id.btn_ticket_repay);
			// v.setVisibility(View.GONE);
			// }

			value = info.getValue("result_code");
			if (("00000").equalsIgnoreCase(value)) {
				putTicketInfo(true);
			} else {
				putTicketInfo(false);
				showTicketInfo(true);
			}
		}
	}

	public void showTicketInfo(boolean visible) {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName());

		View v = getView().findViewById(R.id.include_ticket_info);
		if (v == null) {
			return;
		}

		if (visible) {
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}

		v = getView().findViewById(R.id.goodsname);
		Drawable ds[] = ((TextView) v).getCompoundDrawables();
		if (ds.length == 4) {
			if (visible) {
				ds[2] = WidgetUtils.getDrawable(getApp(), R.drawable.btn_list_arrow_04);
			} else {
				ds[2] = WidgetUtils.getDrawable(getApp(), R.drawable.btn_list_arrow_01);
			}
			((TextView) v).setCompoundDrawables(ds[0], ds[1], ds[2], ds[3]);
		}
	}

	public void showTicketInfo(String url_contents) {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName());

		if (URLUtil.isNetworkUrl(url_contents)) {
			openWebView(webview.class, getString(R.string.M1_MENU_TICKET), getString(R.string.M2_TICKET_HISTORY), getString(R.string.category_ticket_product), url_contents, "POST",
					false);
			return;
		}

		View v = getView().findViewById(R.id.include_ticket_info);
		if (v == null) {
			return;
		}

		// boolean visible = true;
		// if (v.getVisibility() == View.VISIBLE) {
		// visible = false;
		// } else {
		// visible = true;
		// }

		showTicketInfo(false);
	}

	public void putTicketInfo(boolean success) {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + success);

		if (success) {
			getView().findViewById(R.id.ticket_info).setVisibility(View.VISIBLE);
			getView().findViewById(R.id.ticket_no_info).setVisibility(View.GONE);
		} else {
			getView().findViewById(R.id.ticket_info).setVisibility(View.GONE);
			getView().findViewById(R.id.ticket_no_info).setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);

		super.KPnnnn();

		KP_4020();

		KPItem info = KP_nnnn.getInfo();

		if (info == null) {
			return;
		}

		final String url_contents = info.getValue("url_contents");
		final View txt_ticket_detail = findViewById(R.id.txt_ticket_detail);

		if (txt_ticket_detail != null) {
			txt_ticket_detail.setClickable(true);
			txt_ticket_detail.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// startActivity(putIntentData(ticketinfo.class));
					showTicketInfo(url_contents);
				}
			});

			OnTouchListener listener = new OnTouchListener() {

				boolean cancel = false;

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						txt_ticket_detail.setPressed(true);
						cancel = false;
						break;

					case MotionEvent.ACTION_MOVE:
						break;

					case MotionEvent.ACTION_UP:
						if (!cancel) {
							txt_ticket_detail.playSoundEffect(SoundEffectConstants.CLICK);
							txt_ticket_detail.setPressed(false);
							showTicketInfo(url_contents);
						}
						break;

					default:
						txt_ticket_detail.setPressed(false);
						cancel = true;
						break;
					}
					return true;
				}

			};

			final View ticketname = findViewById(R.id.ticketname);
			if (ticketname != null) {
				ticketname.setClickable(true);
				ticketname.setOnTouchListener(listener);
			}

			final View goodsname = findViewById(R.id.goodsname);
			if (goodsname != null) {
				goodsname.setClickable(true);
				goodsname.setOnTouchListener(listener);
			}
		}

		final String url_point = info.getValue("url_point");
		final View txt_ticket_point = findViewById(R.id.txt_ticket_point);

		if (txt_ticket_point != null) {
			txt_ticket_point.setClickable(true);
			txt_ticket_point.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// startActivity(putIntentData(ticketinfo.class));
					openWebView(webview.class, getString(R.string.M1_MENU_TICKET), getString(R.string.M2_TICKET_PRODUCT), getString(R.string.category_ticket_product), url_point, "POST",
							false);
				}
			});

			OnTouchListener listener = new OnTouchListener() {

				boolean cancel = false;

				@Override
				public boolean onTouch(View v, MotionEvent event) {

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						txt_ticket_point.setPressed(true);
						cancel = false;
						break;

					case MotionEvent.ACTION_MOVE:
						break;

					case MotionEvent.ACTION_UP:
						if (!cancel) {
							txt_ticket_point.playSoundEffect(SoundEffectConstants.CLICK);
							txt_ticket_point.setPressed(false);
							openWebView(webview.class, getString(R.string.M1_MENU_TICKET), getString(R.string.M2_TICKET_PRODUCT), getString(R.string.category_ticket_product), url_point, "POST",
									false);
						}
						break;

					default:
						txt_ticket_point.setPressed(false);
						cancel = true;
						break;
					}
					return true;
				}

			};

			final View point_title = findViewById(R.id.point_title);
			if (point_title != null) {
				point_title.setClickable(true);
				point_title.setOnTouchListener(listener);
			}

			final View point_img = findViewById(R.id.point_img);
			if (point_img != null) {
				point_img.setClickable(true);
				point_img.setOnTouchListener(listener);
			}

			final View point_info = findViewById(R.id.point_info);
			if (point_info != null) {
				point_info.setClickable(true);
				point_info.setOnTouchListener(listener);
			}
		}

	}

	/**
	 * 상품권 목록 온픈<br>
	 * 1. 안드로이드마켓오픈<br>
	 * 2. 홀릭이용권화면오픈<br>
	 * <br>
	 */
	@Override
	public void openMarketNReward(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.openMarketNReward(info, list);
	}

	@Override
	public void requestGoogleINAPP(KPItem info, KPItem list) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.requestGoogleINAPP(info, list);
	}

	@Override
	public void requestSamsungINAPP(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.requestSamsungINAPP(info, list);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		super.onItemClick(parent, v, position, id);

		String nm = getResources().getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + "(" + position + "," + KP_index + ")" + nm + ":" + id);

		try {
			if (KP_nnnn.getLists() == null || KP_nnnn.getListCount() == 0) {
				return;
			}

			KPItem info = KP_nnnn.getInfo();
			KPItem list = KP_nnnn.getList(KP_index);

			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

			openMarketNReward(info, list);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see BaseListFragment#onListViewItemClick(android.view.View)
	 */
	@Override
	public void onListItemClick(View v) {
		if (getListView() == null) {
			return;
		}

		String nm = getResourceEntryName(v.getId());
		int position = getPositionForView((View) v.getParent());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position);
		if (position == AdapterView.INVALID_POSITION) {
			return;
		}


		super.onListItemClick(v);

		KPItem info = KP_nnnn.getInfo();
		KPItem list = KP_nnnn.getList(KP_index);

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			// e.printStackTrace();
		}

		openMarketNReward(info, list);

	}

	// @Deprecated
	// @Override
	// public void onRequestPurchaseResponse(RequestPurchase request, ResponseCode responseCode) {
	//
	// super.onRequestPurchaseResponse(request, responseCode);
	// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, getMethodName() + request + "," + responseCode);
	//
	// String goodscode = request.mProductId;
	// String tid = "";
	// String is_pay = "N";
	//
	// if (responseCode == ResponseCode.RESULT_OK) {
	// is_pay = "Y";
	// } else if (responseCode == ResponseCode.RESULT_USER_CANCELED) {
	// is_pay = "D";
	// }
	//
	// KP_4005(goodscode, tid, is_pay, "MARKET", "I");
	// }

	// @Deprecated
	// @Override
	// public void onPurchaseStateChange(PurchaseState purchaseState, String itemId, int quantity,
	// long purchaseTime, String developerPayload) {
	//
	// super.onPurchaseStateChange(purchaseState, itemId, quantity, purchaseTime, developerPayload);
	// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, getMethodName() + purchaseState.toString() + "," + itemId
	// + "," + quantity + "," + purchaseTime + "," + developerPayload);
	//
	// String goodscode = itemId;
	// String tid = developerPayload;
	// String is_pay = "Y";
	//
	// KP_4005(goodscode, tid, is_pay, "MARKET", "U");
	// }

	View txt_ticket_detail;
	View txt_ticket_point;

	@Override
	public void onClick(View v) {
		// try {
		// String vn = getResourceEntryName(v.getId());
		// String cn = v.getClass().getSimpleName();
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + vn + ", " + cn);
		//
		//
		// final View txt_ticket_detail = findViewById(R.id.txt_ticket_detail);
		// final View txt_ticket_point = findViewById(R.id.txt_ticket_point);
		//
		// switch (v.getId()) {
		// case R.id.ticketname:
		// case R.id.goodsname:
		// txt_ticket_detail.performClick();
		// break;
		//
		// case R.id.point_title:
		// case R.id.point_info:
		// case R.id.point_img:
		// txt_ticket_point.performClick();
		// break;
		//
		// default:
		// break;
		// }
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }

		super.onClick(v);
	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());


		super.refresh();
	}

	@Override
	public void onRefresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onRefresh();

	}
}
