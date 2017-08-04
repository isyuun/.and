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
 * filename	:	PurchaseActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ PurchaseActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing.r2.app;

import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.BaseAdActivity3;
import kr.kymedia.karaoke.billing.BillingService;
import kr.kymedia.karaoke.billing.IPurchaseObserver;
import kr.kymedia.karaoke.billing.RequestPurchase;
import kr.kymedia.karaoke.billing.ResponseHandler;
import kr.kymedia.karaoke.billing.Security;
import kr.kymedia.karaoke.billing.BillingService.RestoreTransactions;
import kr.kymedia.karaoke.billing.Consts.PurchaseState;
import kr.kymedia.karaoke.billing.Consts.ResponseCode;
import kr.kymedia.karaoke.apps.R;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 19.
 * @version 2.0
 */
@Deprecated
class PurchaseActivity extends BaseAdActivity3 {

	// private Handler mHandler;
	private PurchaseObserver mPurchaseObserver;

	private BillingService mBillingService;

	/**
	 * The developer payload that is sent with subsequent purchase requests.
	 */
	private String mPayloadContents = "";

	// static final int DIALOG_CANNOT_CONNECT_ID = 22;
	// static final int DIALOG_BILLING_NOT_SUPPORTED_ID = 23;
	// static final int DIALOG_PURCHASE_NOT_SUPPORTED_ID = 24;

	/**
	 * Each product in the catalog is either MANAGED or UNMANAGED. MANAGED means that the product can
	 * be purchased only once per user (such as a new level in a game). The purchase is remembered by
	 * Android Market and can be restored if this application is uninstalled and then re-installed.
	 * UNMANAGED is used for products that can be used up and purchased multiple times (such as poker
	 * chips). It is up to the application to keep track of UNMANAGED products for the user.
	 */
	// private enum Managed {
	// MANAGED, UNMANAGED
	// }

	/**
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		super.onCreate(savedInstanceState);

		// mHandler = new Handler();
		mPurchaseObserver = new PurchaseObserver(this, getHandlerUI());
		mBillingService = new BillingService();
		mBillingService.setContext(this);

		// Check if billing is supported.
		ResponseHandler.register(mPurchaseObserver);
		// if (!mBillingService.checkBillingSupported()) {
		// showDialog2(DIALOG_ALERT_BILLING_NOT_STARTED, null);
		// }
	}

	/**
	 * Called when this activity becomes visible.
	 */
	@Override
	protected void onStart() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mPurchaseObserver);
		super.onStart();
		ResponseHandler.register(mPurchaseObserver);
	}

	/**
	 * Called when this activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mPurchaseObserver);
		super.onStop();
		// 젖됬네...
		// ResponseHandler.unregister(mPurchaseObserver);
	}

	@Override
	protected void onDestroy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mPurchaseObserver);
		super.onDestroy();
		ResponseHandler.unregister(mPurchaseObserver);
		mBillingService.unbind();
	}

	/**
	 * Save the context of the log so simple things like rotation will not result in the log being
	 * cleared.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
		super.onSaveInstanceState(outState);
	}

	/**
	 * Restore the contents of the log if it has previously been saved.
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + id);
		switch (id) {
		case DIALOG_ALERT_BILLING_NOT_STARTED:
			return createDialog(R.string.iap_msg_cannot_connect_title, R.string.iap_msg_cannot_connect_message);
		case DIALOG_ALERT_BILLING_NOT_SUPPORTED:
			return createDialog(R.string.iap_msg_billing_not_supported_title,
					R.string.iap_msg_billing_not_supported_message);
		case DIALOG_ALERT_PURCHASE_NOT_SUPPORTED:
			return createDialog(R.string.iap_msg_purchase_not_supported_title,
					R.string.iap_msg_purchase_not_supported_message);
		default:
			return null;
		}
	}

	private Dialog createDialog(int titleId, int messageId) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + titleId + ", " + messageId);

		String helpUrl = replaceLanguageAndRegion(getString(R.string.iap_msg_help_url));
		final Uri helpUri = Uri.parse(helpUrl);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titleId).setIcon(android.R.drawable.stat_sys_warning).setMessage(messageId)
				.setCancelable(false).setPositiveButton(android.R.string.ok, null)
				.setNegativeButton(R.string.iap_msg_learn_more, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Intent.ACTION_VIEW, helpUri);
						startActivity(intent);
					}
				});
		return builder.create();
	}

	/**
	 * Replaces the language and/or country of the device into the given string. The pattern "%lang%"
	 * will be replaced by the device's language code and the pattern "%region%" will be replaced with
	 * the device's country code.
	 * 
	 * @param str
	 *          the string to replace the language/country within
	 * @return a string containing the local language and region codes
	 */
	private String replaceLanguageAndRegion(String str) {
		// Substitute language and or region if present in string
		if (str.contains("%lang%") || str.contains("%region%")) {
			Locale locale = Locale.getDefault();
			str = str.replace("%lang%", locale.getLanguage().toLowerCase());
			str = str.replace("%region%", locale.getCountry().toLowerCase());
		}
		return str;
	}

	public boolean requestPurchase(String productId, String itemType) {

		Security.INAPP_PUBLIC_KEY = _IKaraoke.GOOGLE_API.INAPP_PUBLIC_KEY;

		if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
			Security.INAPP_PUBLIC_KEY = _IKaraoke.GOOGLE_API.INAPP_PUBLIC_KEY_JPOP;
		} else if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
			Security.INAPP_PUBLIC_KEY = _IKaraoke.GOOGLE_API.INAPP_PUBLIC_KEY_KPOP;
		}

		boolean ret = mBillingService.requestPurchase(productId, itemType, mPayloadContents);

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + productId + ", " + mPayloadContents + ", " + ret);

		if (!ret) {
			showDialog2(DIALOG_ALERT_BILLING_NOT_SUPPORTED, null);
		}

		return ret;
	}

	// public boolean requestGoogleINAPP(String productId) {
	// return requestPurchase(productId, Consts.ITEM_TYPE_INAPP);
	// }
	//
	// public boolean requestGoogleSUBSCRIPTION(String productId) {
	// return requestPurchase(productId, Consts.ITEM_TYPE_SUBSCRIPTION);
	// }
	/**
	 * @see kr.kymedia.karaoke.billing.PurchaseObserver#onBillingSupported(boolean)
	 */
	public void onBillingSupported(boolean supported) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onBillingSupported(...)" + supported);

		if (supported) {
		} else {
			showDialog2(DIALOG_ALERT_BILLING_NOT_SUPPORTED, null);
		}

		// try {
		// if (getCurrentFragment() != null && getCurrentFragment() instanceof IPurchaseObserver) {
		// ((IPurchaseObserver) getCurrentFragment()).onBillingSupported(supported);
		// }
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }
	}

	/**
	 * @see kr.kymedia.karaoke.billing.PurchaseObserver#onPurchaseStateChange(kr.kymedia.karaoke.billing.Consts.PurchaseState, java.lang.String, int, long, java.lang.String)
	 */
	public void onPurchaseStateChange(PurchaseState purchaseState, String itemId, int quantity,
			long purchaseTime, String developerPayload) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onPurchaseStateChange(...)" + purchaseState.toString() + "," + itemId
				+ "," + quantity + "," + purchaseTime + "," + developerPayload);

		try {
			if (getCurrentFragment() != null && getCurrentFragment() instanceof IPurchaseObserver) {
				((IPurchaseObserver) getCurrentFragment()).onPurchaseStateChange(purchaseState, itemId, quantity, purchaseTime, developerPayload);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * @see kr.kymedia.karaoke.billing.PurchaseObserver#onRequestPurchaseResponse(kr.kymedia.karaoke.billing.BillingService.RequestPurchase, kr.kymedia.karaoke.billing.Consts.ResponseCode)
	 */
	public void onRequestPurchaseResponse(RequestPurchase request, ResponseCode responseCode) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__,
				"onRequestPurchaseResponse(...)" + request.toString() + "," + responseCode.toString());

		if (responseCode != ResponseCode.RESULT_OK && responseCode != ResponseCode.RESULT_USER_CANCELED) {
			showDialog2(DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, null);
		}

		try {
			if (getCurrentFragment() != null && getCurrentFragment() instanceof IPurchaseObserver) {
				((IPurchaseObserver) getCurrentFragment()).onRequestPurchaseResponse(request, responseCode);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * @see kr.kymedia.karaoke.billing.PurchaseObserver#onRestoreTransactionsResponse(kr.kymedia.karaoke.billing.BillingService.RestoreTransactions, kr.kymedia.karaoke.billing.Consts.ResponseCode)
	 */
	public void onRestoreTransactionsResponse(RestoreTransactions request, ResponseCode responseCode) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onRestoreTransactionsResponse(...)" + request.toString() + ","
				+ responseCode.toString());

		try {
			if (getCurrentFragment() != null && getCurrentFragment() instanceof IPurchaseObserver) {
				((PurchaseFragment) getCurrentFragment()).onRestoreTransactionsResponse(request, responseCode);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
