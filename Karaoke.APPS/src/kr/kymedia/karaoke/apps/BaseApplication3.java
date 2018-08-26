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
 * 2014 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	BaseApplication3.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ BaseApplication3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.impl.IBaseDialog;
import kr.kymedia.karaoke.billing.v3.IabHelper;
import kr.kymedia.karaoke.billing.v3.IabResult;
import kr.kymedia.karaoke.billing.v3.Inventory;
import kr.kymedia.karaoke.billing.v3.Purchase;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * <pre>
 * In - app - Billing.V3
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 12. 8.
 * @version 1.0
 */
class BaseApplication3 extends BaseApplication2 implements IabHelper.OnIabSetupFinishedListener, IabHelper.QueryInventoryFinishedListener,
		IabHelper.OnIabPurchaseFinishedListener, IabHelper.OnConsumeFinishedListener, IabHelper.OnConsumeMultiFinishedListener {
	final protected String __CLASSNAME__ = "[Iab]" + (new Exception()).getStackTrace()[0].getFileName();

	IabHelper mHelper;

	static final int RC_REQUEST = 10001;

	@Override
	public void onCreate() {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, getMethodName());


		super.onCreate();

		/*
		 * base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY (that you got from the Google
		 * Play developer console). This is not your developer public key, it's the *app-specific*
		 * public key.
		 * 
		 * Instead of just storing the entire literal string here embedded in the program, construct the
		 * key at runtime from pieces or use bit manipulation (for example, XOR with some other string)
		 * to hide the actual key. The key itself is not secret information, but we don't want to make
		 * it easy for an attacker to replace the public key with one of their own and then fake
		 * messages from the server.
		 */
		// String base64EncodedPublicKey = "CONSTRUCT_YOUR_KEY_AND_PLACE_IT_HERE";
		String base64EncodedPublicKey = _IKaraoke.GOOGLE_API.INAPP_PUBLIC_KEY;

		if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
			base64EncodedPublicKey = _IKaraoke.GOOGLE_API.INAPP_PUBLIC_KEY_JPOP;
		} else if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
			base64EncodedPublicKey = _IKaraoke.GOOGLE_API.INAPP_PUBLIC_KEY_KPOP;
		}

		// Some sanity checks to see if the developer (that's you!) really followed the
		// instructions to run this sample (don't put these checks on your app!)
		if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
			throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
		}
		if (getPackageName().startsWith("com.example")) {
			throw new RuntimeException("Please change the sample's package name! See README.");
		}

		// Create the helper, passing it our context and the public key to verify signatures with
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "Creating IAB helper.");
		mHelper = new IabHelper(this, base64EncodedPublicKey);

		// enable debug logging (for a production application, you should set this to false).
		mHelper.enableDebugLogging(_IKaraoke.DEBUG);
		// test
		// mHelper.enableDebugLogging(true);

	}

	@Override
	public void KP_param() {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName());

		super.KP_param();

		post(new Runnable() {

			@Override
			public void run() {

				startSetUpIabHelper();
			}
		});
	}

	@Override
	public void onTerminate() {

		super.onTerminate();
	}

	@Override
	public void release() {

		super.release();

		// if (mHelper != null) {
		// mHelper.dispose();
		// isStarted = false;
		// }
	}

	private boolean checkResult(IabResult result, Purchase purchase) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, getMethodName());

		boolean ret = false;

		if (result.getResponse() == IabHelper.IABHELPER_USER_CANCELLED) {
			return ret;
		}

		// if we were disposed of in the meantime, quit.
		if (mHelper == null) {
			alert(IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, null);
			return ret;
		}

		if (result.isFailure()) {
			// complain("Error purchasing: " + result);
			alert(IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, result.getMessage());
			return ret;
		}

		if (!verifyDeveloperPayload(purchase)) {
			// complain("Error purchasing. Authenticity verification failed.");
			alert(IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, null);
			return ret;
		}

		if (purchase == null) {
			// complain("Error purchasing. Authenticity verification failed.");
			alert(IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, null);
			return ret;
		}

		ret = true;

		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + ret);

		return ret;
	}

	boolean isStarted = false;

	protected void startSetUpIabHelper() {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "isLoginUser:" + isLoginUser() + ", isStarted:" + isStarted);

		if (!isLoginUser()) {
			return;
		}

		// Start setup. This is asynchronous and the specified listener
		// will be called once setup completes.
		try {
			if (!isStarted) {
				mHelper.startSetup(this);
			} else {
				queryInventoryAsync();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	boolean isQuerying = false;

	protected void queryInventoryAsync() {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, getMethodName() + "isLoginUser:" + isLoginUser());

		if (!isLoginUser()) {
			return;
		}

		try {
			if (!isQuerying) {
				mHelper.queryInventoryAsync(this);
				isQuerying = true;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onIabSetupFinished(IabResult result) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, getMethodName() + result);


		if (!result.isSuccess()) {
			// Oh noes, there was a problem.
			// complain("Problem setting up in-app billing: " + result);
			alert(IBaseDialog.DIALOG_ALERT_BILLING_NOT_STARTED, null);
			return;
		}

		// Have we been disposed of in the meantime? If so, quit.
		if (mHelper == null) {
			alert(IBaseDialog.DIALOG_ALERT_BILLING_NOT_STARTED, null);
			return;
		}

		// IAB is fully set up. Now, let's get an inventory of stuff we own.
		// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, "Setup successful. Querying inventory.");
		queryInventoryAsync();

		isStarted = true;
	}

	// Listener that's called when we finish querying the items and subscriptions we own
	@Override
	public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, getMethodName() + result);
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + inventory);
		isQuerying = false;

		// Have we been disposed of in the meantime? If so, quit.
		if (mHelper == null) {
			alert(IBaseDialog.DIALOG_ALERT_BILLING_NOT_SUPPORTED, null);
			return;
		}

		// Is it a failure?
		if (result.isFailure()) {
			// complain("Failed to query inventory: " + result);
			alert(IBaseDialog.DIALOG_ALERT_BILLING_NOT_SUPPORTED, result.getMessage());
			return;
		}

		/*
		 * Check for items we own. Notice that for each purchase, we check the developer payload to see
		 * if it's correct! See verifyDeveloperPayload().
		 */
		try {
			if (inventory != null) {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, "[" + IabHelper.ITEM_TYPE_INAPP + "]" + inventory.getAllPurchases(IabHelper.ITEM_TYPE_INAPP));
				if (inventory.getAllPurchases(IabHelper.ITEM_TYPE_INAPP) != null && inventory.getAllPurchases(IabHelper.ITEM_TYPE_INAPP).size() > 0) {
					for (Purchase purchase : inventory.getAllPurchases(IabHelper.ITEM_TYPE_INAPP)) {
						if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase);
						// if (IabHelper.ITEM_TYPE_INAPP.equalsIgnoreCase(purchase.getItemType())) {
						// mHelper.consumeAsync(purchase, BaseApplication3.this);
						// }
					}
					mHelper.consumeAsync(inventory.getAllPurchases(IabHelper.ITEM_TYPE_INAPP), this);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// Callback for when a purchase is finished
	@Override
	public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, getMethodName() + result);
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase);

		// test
		// KP_4005("", "", "", "MARKET", "I", "");

		if (checkResult(result, purchase)) {
			// if (_IKaraoke.DEBUG)Log2.d(__CLASSNAME__, "Purchase successful.");

			try {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase.getSku());
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase.getOrderId());
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase.getDeveloperPayload());

				setWaitScreen(true);
				mHelper.consumeAsync(purchase, this);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	// Called when consumption is complete
	@Override
	public void onConsumeFinished(Purchase purchase, IabResult result) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, getMethodName() + result);
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase);

		if (checkResult(result, purchase)) {
			// if (_IKaraoke.DEBUG)Log2.d(__CLASSNAME__, "End consumption flow.");

			try {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase.getSku());
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase.getOrderId());
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchase.getDeveloperPayload());

				String goodscode = purchase.getSku();
				String tid = purchase.getOrderId();
				String is_pay = "Y";

				String list = purchase.getDeveloperPayload();

				setWaitScreen(true);
				KP_4005(goodscode, tid, is_pay, "MARKET", "I", list);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

	}

	@Override
	public void onConsumeMultiFinished(List<Purchase> purchases, List<IabResult> results) {

		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, getMethodName() + results);
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchases);

		try {
			if (purchases != null) {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "" + purchases.size());
			}
			for (Purchase purchase : purchases) {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.w(__CLASSNAME__, "" + purchase);
				int index = purchases.indexOf(purchase);
				onConsumeFinished(purchase, results.get(index));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + requestCode + "," + resultCode + "," + data);
		_BaseFragment fragment = getCurrentFragment();
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG)
			Log.e(__CLASSNAME__, getMethodName() + fragment + "," + (fragment instanceof IabHelper.OnIabPurchaseFinishedListener) + ","
					+ (fragment instanceof IabHelper.OnConsumeFinishedListener));

		if (mHelper == null) {
			alert(IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, null);
			return;
		}

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[APP]");
		} else {
			// if (_IKaraoke.DEBUG)Log2.d(__CLASSNAME__, "onActivityResult handled by IABUtil.");
			if (data != null && data.getExtras() != null) {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[IAB]" + "\n" + data.getExtras());
			} else {
				if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[IAB]");
			}
		}
	}

	/**
	 * 안드로이드마켓-인앱결제
	 */
	public void requestGoogleINAPP(KPItem info, KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "isLoginUser:" + isLoginUser());

		if (!isLoginUser()) {
			return;
		}

		if (info == null || list == null) {

			if (info == null) {
				showDataError(__CLASSNAME__, getMethodName(), "info", info);
				return;
			}

			if (list == null) {
				showDataError(__CLASSNAME__, getMethodName(), "list", list);
				return;
			}
			// KPItem info = KP_nnnn.getInfo();
			// KPItem list = KP_nnnn.getList(KP_index);
		}

		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (info != null) {
			p_passtype = info.getValue(LOGIN.KEY_PASSTYPE) == null ? p_passtype : info.getValue(LOGIN.KEY_PASSTYPE);
			if ("Y".equalsIgnoreCase(info.getValue(LOGIN.KEY_PASSTYPE))) {
				Bundle args = new Bundle();
				args.putString("message", getString(R.string.errmsg_iap_msg_buy_duplicate));
				getBaseActivity().showDialog2(IBaseDialog.DIALOG_ALERT_MESSAGE_CONFIRM, args);
				return;
			}
		}

		try {
			if (getBaseActivity() instanceof PurchaseActivity && list != null) {
				String productId = list.getValue(LOGIN.KEY_GOODSCODE);
				String itemType = list.getValue("goodstype");

				if (!TextUtil.isEmpty(itemType)) {
					if (IabHelper.ITEM_TYPE_INAPP.equalsIgnoreCase(itemType)) {
						requestGoogleINAPP(productId, IabHelper.ITEM_TYPE_INAPP, list.toString());
					} else if (IabHelper.ITEM_TYPE_SUBS.equalsIgnoreCase(itemType)) {
						requestGoogleINAPP(productId, IabHelper.ITEM_TYPE_SUBS, list.toString());
					} else {
						requestGoogleINAPP(productId, itemType, list.toString());
					}
				} else {
					requestGoogleINAPP(productId, IabHelper.ITEM_TYPE_INAPP, list.toString());
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void requestGoogleINAPP(String productId, String itemType, String list) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "isLoginUser:" + isLoginUser() + "," + productId + "," + itemType);


		if (!isLoginUser()) {
			return;
		}

		if (!IabHelper.ITEM_TYPE_INAPP.equalsIgnoreCase(itemType) && !IabHelper.ITEM_TYPE_SUBS.equalsIgnoreCase(itemType)) {
			alert(IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED, null);
			return;
		}

		try {
			setWaitScreen(true);
			mHelper.launchPurchaseFlow(getBaseActivity(), productId, itemType, RC_REQUEST, this, list);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/** Verifies the developer payload of a purchase. */
	boolean verifyDeveloperPayload(Purchase p) {
		String payload = p.getDeveloperPayload();
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + payload);

		/*
		 * TODO: verify that the developer payload of the purchase is correct. It will be the same one
		 * that you sent when initiating the purchase.
		 * 
		 * WARNING: Locally generating a random string when starting a purchase and verifying it here
		 * might seem like a good approach, but this will fail in the case where the user purchases an
		 * item on one device and then uses your app on a different device, because on the other device
		 * you will not have access to the random string you originally generated.
		 * 
		 * So a good developer payload has these characteristics:
		 * 
		 * 1. If two different users purchase an item, the payload is different between them, so that
		 * one user's purchase can't be replayed to another user.
		 * 
		 * 2. The payload must be such that you can verify it even when the app wasn't the one who
		 * initiated the purchase flow (so that items purchased by the user on one device work on other
		 * devices owned by the user).
		 * 
		 * Using your own server to store and verify developer payloads across app installations is
		 * recommended.
		 */

		return true;
	}

	// Enables or disables the "please wait" screen.
	void setWaitScreen(final boolean set) {
		post(new Runnable() {

			@Override
			public void run() {

				if (getBaseActivity() != null) {
					if (set) {
						// startLoading(__CLASSNAME__, getMethodName());
						getBaseActivity().startLoadingDialog(getString(R.string.msg_text_waiting));
					} else {
						// stopLoading(__CLASSNAME__, getMethodName());
						getBaseActivity().stopLoadingDialog(null);
					}
				}
			}
		});
	}

	// private void complain(String message) {
	// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, "**** TrivialDrive Error: " + message);
	// alert("Error: " + message);
	// }
	//
	// private void alert(String message) {
	// AlertDialog.Builder bld = new AlertDialog.Builder(this);
	// bld.setMessage(message);
	// bld.setNeutralButton("OK", null);
	// if (_IKaraoke.DEBUG)Log2.d(__CLASSNAME__, "Showing alert dialog: " + message);
	// bld.create().show();
	// }
	public void alert(int id, String message) {
		String title = "";
		switch (id) {
		case IBaseDialog.DIALOG_ALERT_BILLING_NOT_STARTED:
			title = getString(R.string.iap_msg_cannot_connect_title);
			if (TextUtil.isEmpty(message)) {
				message = getString(R.string.iap_msg_cannot_connect_message);
			}

			break;
		case IBaseDialog.DIALOG_ALERT_BILLING_NOT_SUPPORTED:
			title = getString(R.string.iap_msg_billing_not_supported_title);
			if (TextUtil.isEmpty(message)) {
				message = getString(R.string.iap_msg_billing_not_supported_message);
			}
			break;
		case IBaseDialog.DIALOG_ALERT_PURCHASE_NOT_SUPPORTED:
			title = getString(R.string.iap_msg_purchase_not_supported_title);
			if (TextUtil.isEmpty(message)) {
				message = getString(R.string.iap_msg_purchase_not_supported_message);
			}
			break;
		default:
			break;
		}

		setWaitScreen(false);

		//AlertDialog.Builder bld = new AlertDialog.Builder(getBaseActivity());
		//bld.setTitle(title);
		//bld.setMessage(message);
		//bld.setNeutralButton("OK", null);
		//bld.create().show();
		getBaseActivity().showAlertDialog(
				//아이콘
				android.R.attr.alertDialogIcon,
				//타이틀
				title,
				//메시지
				message,
				//확인
				getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				},
				//거부
				null, null,
				//취소
				true, null);

		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, "Showing alert dialog: " + message);
	}

}
