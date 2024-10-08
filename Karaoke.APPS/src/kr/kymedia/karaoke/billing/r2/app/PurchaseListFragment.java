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
 * filename	:	PurchaseFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.billing
 *    |_ PurchaseFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing.r2.app;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.billing.BillingService.RestoreTransactions;
import kr.kymedia.karaoke.billing.Consts.PurchaseState;
import kr.kymedia.karaoke.billing.Consts.ResponseCode;
import kr.kymedia.karaoke.billing.IPurchaseObserver;
import kr.kymedia.karaoke.billing.RequestPurchase;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 19.
 * @version 3.0
 */
public class PurchaseListFragment extends _BaseListFragment implements IPurchaseObserver {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			updateResultsInUi();
		}
	};

	private void updateResultsInUi() {
	}

	public void onBillingSupported(boolean supported) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onBillingSupported(...)" + supported);

	}

	public void onPurchaseStateChange(PurchaseState purchaseState, String itemId, int quantity,
			long purchaseTime, String developerPayload) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onPurchaseStateChange(...)" + purchaseState.toString() + "," + itemId
				+ "," + quantity + "," + purchaseTime + "," + developerPayload);

	}

	public void onRequestPurchaseResponse(RequestPurchase request, ResponseCode responseCode) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__,
				"onRequestPurchaseResponse(...)" + request.toString() + "," + responseCode.toString());

	}

	public void onRestoreTransactionsResponse(RestoreTransactions request, ResponseCode responseCode) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "onRestoreTransactionsResponse(...)" + request.toString() + ","
				+ responseCode.toString());

	}

}
