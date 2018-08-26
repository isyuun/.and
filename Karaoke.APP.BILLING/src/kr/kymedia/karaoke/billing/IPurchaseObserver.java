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
 * filename	:	IPurchaseObserver.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.billing
 *    |_ IPurchaseObserver.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing;

import kr.kymedia.karaoke.billing.Consts.PurchaseState;
import kr.kymedia.karaoke.billing.Consts.ResponseCode;

/**
 * 
 * TODO<br>
 * 
 * <pre></pre>
 * 
 * @author isyoon
 * @since 2014. 11. 11.
 * @version 1.0
 */
public interface IPurchaseObserver {

	// @Deprecated
	// public void onBillingSupported(boolean supported);
	//
	// @Deprecated
	// public void onRestoreTransactionsResponse(RestoreTransactions request, ResponseCode responseCode);

	public void onRequestPurchaseResponse(RequestPurchase request, ResponseCode responseCode);

	public void onPurchaseStateChange(PurchaseState purchaseState, String itemId, int quantity, long purchaseTime, String developerPayload);

}
