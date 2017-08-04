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
 * filename	:	PurchaseObserver.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.billing
 *    |_ PurchaseObserver.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing.r2.app;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.billing.BillingService.RestoreTransactions;
import kr.kymedia.karaoke.billing.Consts.PurchaseState;
import kr.kymedia.karaoke.billing.Consts.ResponseCode;
import kr.kymedia.karaoke.billing.RequestPurchase;
import android.app.Activity;
import android.os.Handler;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2013. 4. 19.
 * @version 1.0
 */
@Deprecated
class PurchaseObserver extends kr.kymedia.karaoke.billing.PurchaseObserver {
	final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private PurchaseActivity mPurchaseActivity = null;

	public PurchaseObserver(Activity activity, Handler handler) {
		super(activity, handler);
		// TODO Auto-generated constructor stub
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "PurchaseObserver(...)");

		mPurchaseActivity = (PurchaseActivity) activity;
	}

	@Override
	public void onBillingSupported(boolean supported) {

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "onBillingSupported(...)" + supported);

		if (mPurchaseActivity != null) {
			mPurchaseActivity.onBillingSupported(supported);
		}
	}

	@Override
	public void onBillingSupported(boolean supported, String type) {


	}

	@Override
	public void onPurchaseStateChange(PurchaseState purchaseState, String itemId, int quantity,
			long purchaseTime, String developerPayload) {

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "onPurchaseStateChange(...)" + purchaseState.toString() + "," + itemId
				+ "," + quantity + "," + purchaseTime + "," + developerPayload);

		if (mPurchaseActivity != null) {
			mPurchaseActivity.onPurchaseStateChange(purchaseState, itemId, quantity, purchaseTime,
					developerPayload);
		}
	}

	@Override
	public void onRequestPurchaseResponse(RequestPurchase request, ResponseCode responseCode) {

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__,
				"onRequestPurchaseResponse(...)" + request.toString() + "," + responseCode.toString());

		if (mPurchaseActivity != null) {
			mPurchaseActivity.onRequestPurchaseResponse(request, responseCode);
		}
	}

	@Override
	public void onRestoreTransactionsResponse(RestoreTransactions request, ResponseCode responseCode) {

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "onRestoreTransactionsResponse(...)" + request.toString() + ","
				+ responseCode.toString());

		if (mPurchaseActivity != null) {
			mPurchaseActivity.onRestoreTransactionsResponse(request, responseCode);
		}
	}

}
