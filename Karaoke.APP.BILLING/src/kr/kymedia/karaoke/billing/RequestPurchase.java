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
 * 2014 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	RequestPurchase.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.billing
 *    |_ RequestPurchase.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing;

import kr.kymedia.karaoke.billing.Consts.ResponseCode;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

/**
 *
 * TODO<br>
 * <pre></pre>
 *
 * @author	isyoon
 * @since	2014. 11. 11.
 * @version 1.0
 */
/**
 * Wrapper class that requests a purchase.
 */
public class RequestPurchase extends BillingRequest {
	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 */
	private BillingService mBillingService;
	public final String mProductId;
	public final String mDeveloperPayload;
	public final String mProductType;

	/**
	 * Legacy constructor
	 *
	 * @param itemId
	 *          The ID of the item to be purchased. Will be assumed to be a one-time
	 *          purchase.
	 * @param billingService
	 *          TODO
	 */
	@Deprecated
	public RequestPurchase(BillingService billingService, String itemId) {
		this(billingService, itemId, null, null);
		mBillingService = billingService;
	}

	/**
	 * Legacy constructor
	 *
	 * @param itemId
	 *          The ID of the item to be purchased. Will be assumed to be a one-time
	 *          purchase.
	 * @param developerPayload
	 *          Optional data.
	 * @param billingService
	 *          TODO
	 */
	@Deprecated
	public RequestPurchase(BillingService billingService, String itemId, String developerPayload) {
		this(billingService, itemId, null, developerPayload);
		mBillingService = billingService;
	}

	/**
	 * Constructor
	 *
	 * @param itemId
	 *          The ID of the item to be purchased. Will be assumed to be a one-time
	 *          purchase.
	 * @param itemType
	 *          Either Consts.ITEM_TYPE_INAPP or Consts.ITEM_TYPE_SUBSCRIPTION,
	 *          indicating the type of item type support is being checked for.
	 * @param developerPayload
	 *          Optional data.
	 * @param billingService
	 *          TODO
	 */
	public RequestPurchase(BillingService billingService, String itemId, String itemType, String developerPayload) {
		// This object is never created as a side effect of starting this
		// service so we pass -1 as the startId to indicate that we should
		// not stop this service after executing this request.
		super(billingService, -1);
		mBillingService = billingService;
		mProductId = itemId;
		mDeveloperPayload = developerPayload;
		mProductType = itemType;
	}

	@Override
	protected long run() throws RemoteException {
		Bundle request = makeRequestBundle("REQUEST_PURCHASE");
		request.putString(Consts.BILLING_REQUEST_ITEM_ID, mProductId);
		request.putString(Consts.BILLING_REQUEST_ITEM_TYPE, mProductType);
		// Note that the developer payload is optional.
		if (mDeveloperPayload != null) {
			request.putString(Consts.BILLING_REQUEST_DEVELOPER_PAYLOAD, mDeveloperPayload);
		}
		Bundle response = BillingService.mService.sendBillingRequest(request);
		PendingIntent pendingIntent = response.getParcelable(Consts.BILLING_RESPONSE_PURCHASE_INTENT);
		if (pendingIntent == null) {
			Log.e(BillingService.TAG, "Error with requestPurchase");
			return Consts.BILLING_RESPONSE_INVALID_REQUEST_ID;
		}

		Intent intent = new Intent();
		ResponseHandler.iap_msg_buyPageIntentResponse(pendingIntent, intent);
		return response.getLong(Consts.BILLING_RESPONSE_REQUEST_ID,
				Consts.BILLING_RESPONSE_INVALID_REQUEST_ID);
	}

	@Override
	protected void responseCodeReceived(ResponseCode responseCode) {
		ResponseHandler.responseCodeReceived(mBillingService, this, responseCode);
	}
}
