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
 * filename	:	BillingRequest.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.billing
 *    |_ BillingRequest.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing;

import kr.kymedia.karaoke.billing.Consts.ResponseCode;
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
 * The base class for all requests that use the MarketBillingService.
 * Each derived class overrides the run() method to call the appropriate
 * service interface. If we are already connected to the MarketBillingService,
 * then we call the run() method directly. Otherwise, we bind
 * to the service and save the request on a queue to be run later when
 * the service is connected.
 */
abstract class BillingRequest {
	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 */
	private final BillingService mBillingService;
	private final int mStartId;
	protected long mRequestId;

	public BillingRequest(BillingService billingService, int startId) {
		mBillingService = billingService;
		mStartId = startId;
	}

	public int getStartId() {
		return mStartId;
	}

	/**
	 * Run the request, starting the connection if necessary.
	 * 
	 * @return true if the request was executed or queued; false if there
	 *         was an error starting the connection
	 */
	public boolean runRequest() {
		if (runIfConnected()) {
			return true;
		}

		if (mBillingService.bindToMarketBillingService()) {
			// Add a pending request to run when the service is connected.
			BillingService.mPendingRequests.add(this);
			return true;
		}
		return false;
	}

	/**
	 * Try running the request directly if the service is already connected.
	 * 
	 * @return true if the request ran successfully; false if the service
	 *         is not connected or there was an error when trying to use it
	 */
	public boolean runIfConnected() {
		if (Consts.DEBUG) {
			Log.d(BillingService.TAG, getClass().getSimpleName());
		}
		if (BillingService.mService != null) {
			try {
				mRequestId = run();
				if (Consts.DEBUG) {
					Log.d(BillingService.TAG, "request id: " + mRequestId);
				}
				if (mRequestId >= 0) {
					BillingService.mSentRequests.put(mRequestId, this);
				}
				return true;
			} catch (RemoteException e) {
				onRemoteException(e);
			}
		}
		return false;
	}

	/**
	 * Called when a remote exception occurs while trying to execute the {@link #run()} method. The derived class can override this to
	 * execute exception-handling code.
	 * 
	 * @param e
	 *          the exception
	 */
	protected void onRemoteException(RemoteException e) {
		Log.w(BillingService.TAG, "remote billing service crashed");
		BillingService.mService = null;
	}

	/**
	 * The derived class must implement this method.
	 * 
	 * @throws RemoteException
	 */
	abstract protected long run() throws RemoteException;

	/**
	 * This is called when Android Market sends a response code for this
	 * request.
	 * 
	 * @param responseCode
	 *          the response code
	 */
	protected void responseCodeReceived(ResponseCode responseCode) {
	}

	protected Bundle makeRequestBundle(String method) {
		Bundle request = new Bundle();
		request.putString(Consts.BILLING_REQUEST_METHOD, method);
		request.putInt(Consts.BILLING_REQUEST_API_VERSION, 2);
		request.putString(Consts.BILLING_REQUEST_PACKAGE_NAME, mBillingService.getPackageName());
		return request;
	}

	protected void logResponseCode(String method, Bundle response) {
		ResponseCode responseCode = ResponseCode.valueOf(
				response.getInt(Consts.BILLING_RESPONSE_RESPONSE_CODE));
		if (Consts.DEBUG) {
			Log.e(BillingService.TAG, method + " received " + responseCode.toString());
		}
	}
}
