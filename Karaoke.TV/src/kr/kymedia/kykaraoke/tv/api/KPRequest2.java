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
 * filename	:	KPRequest2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api
 *    |_ KPRequest2.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.api;

import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.kymedia.kykaraoke.tv.BuildConfig;

/**
 * <pre>
 *   org.apache.http 대체
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-04-25
 */
class KPRequest2 extends KPRequest {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	public KPRequest2(Handler handler) {
		super(handler);
	}

	///**
	// * <pre>
	// *   org.apache.http 대체
	// * </pre>
	// */
	//protected void send() throws Exception {
	//	Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[" + REQUEST_KP.get(request) + "]");
	//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[" + m_strUrl + "]");
	//
	//	this.result_code = "";
	//	this.result_message = "";
	//
	//	InputStream is = null;
	//
	//	try {
	//		URL url = new URL(m_strUrl);
	//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	//		conn.setReadTimeout(TIMEOUT_REQUEST_READ);
	//		conn.setConnectTimeout(TIMEOUT_REQUEST_CONNECT);
	//		conn.setRequestMethod("POST");
	//		conn.setDoInput(true);
	//		// Starts the query
	//		conn.connect();
	//		int response = conn.getResponseCode();
	//		Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[CODE]" + response);
	//		is = conn.getInputStream();
	//	} catch (Exception e) {
	//		// Makes sure that the InputStream is closed after the app is
	//		// finished using it.
	//		if (is != null) {
	//			is.close();
	//		}
	//		Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.p_op + "\n" + Log.getStackTraceString(e));
	//		sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
	//		e.printStackTrace();
	//		throw (e);
	//	}
	//
	//	//if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[" + m_strUrl + "]");
	//
	//	try {
	//		BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	//
	//		String line = null;
	//		String result = "";
	//		int iReadCount = 0;
	//
	//		while ((line = bufReader.readLine()) != null) {
	//			// if (IKaraokeTV.DEBUG) _LOG.i("JSON", line);
	//			if (iReadCount > 0) {
	//				result += "\r\n";
	//			}
	//
	//			result += line;
	//			iReadCount++;
	//		}
	//
	//		// isyoon
	//		if (IKaraokeTV.DEBUG) {
	//			JSONObject json = new JSONObject(result);
	//			Log.d(_toString(), "[JSON]" + json.toString(2));
	//		}
	//
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[" + REQUEST_KP.get(request) + "]");
	//
	//		switch (request) {
	//			case REQUEST_MAIN:
	//				parseMain(result);
	//				break;
	//			case REQUEST_MY_SUB_MENU:
	//				parseMySubMenu(result);
	//				break;
	//			case REQUEST_SHOP_SUB_MENU:
	//				parseShopSubMenu(result);
	//				break;
	//			case REQUEST_SONG_LIST:
	//				parseSongList(result);
	//				break;
	//			case REQUEST_FAVOR:
	//				parseFavor(result);
	//				break;
	//			case REQUEST_CUSTOMER_LIST:
	//			case REQUEST_EVENT_LIST:
	//				parseCustomerList(result);
	//				break;
	//			case REQUEST_CUSTOMER_LIST_DETAIL:
	//			case REQUEST_EVENT_LIST_DETAIL:
	//				parseCustomerListDetail(result);
	//				break;
	//			case REQUEST_SEARCH_LIST:
	//				parseSearchList(result);
	//				break;
	//			case REQUEST_SONG_PLAY:
	//				parsePlaySong(result);
	//				break;
	//			case REQUEST_LISTEN_LIST:
	//				parseListenList(result);
	//				break;
	//			case REQUEST_LISTEN_SONG:
	//			case REQUEST_LISTEN_SONG_OTHER:
	//				parseListenSong(result);
	//				break;
	//			case REQUEST_LISTEN_OTHER:
	//				parseListenList(result);
	//				break;
	//			case REQUEST_NUMBER_SEARCH:
	//				parseNumberSearch(result);
	//				break;
	//			case REQUEST_SONG_PLAYED_TIME:
	//			case REQUEST_LISTEN_PLAYED_TIME:
	//				parseSongPlayedTime(result);
	//				break;
	//			case REQUEST_AUTH_NUMBER:
	//				parseAuthNumber(result);
	//				break;
	//			case REQUEST_CERTIFY_STATE:
	//				parseCertifyState(result);
	//				break;
	//			case REQUEST_AUTH_NUMBER_CORRECT:
	//				parseAuthNumberCorrect(result);
	//				break;
	//			case REQUEST_MY_RECORD_LIST:
	//				parseMyRecordList(result);
	//				break;
	//			case REQUEST_TICKET_SALES_STATE:
	//				parseTicketSalesState(result);
	//				break;
	//			case REQUEST_TICKET_PURCHASE_COMPLETE:
	//				parsePurchaseComplete(result);
	//				break;
	//			case REQUEST_EVENT_APPLY:
	//				parseEventApply(result);
	//				break;
	//			case REQUEST_EVENT_HP:
	//				parseEventHP(result);
	//				break;
	//			case REQUEST_COUPON_REGIST:
	//				parseCouponRegist(result);
	//				break;
	//		}
	//	} catch (Exception e) {
	//		// Makes sure that the InputStream is closed after the app is
	//		// finished using it.
	//		if (is != null) {
	//			is.close();
	//		}
	//		Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[RECV]" + "[NG]" + this.p_op + "\n" + Log.getStackTraceString(e));
	//		sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
	//		e.printStackTrace();
	//		throw (e);
	//	} finally {
	//		// Makes sure that the InputStream is closed after the app is
	//		// finished using it.
	//		if (is != null) {
	//			is.close();
	//		}
	//	}
	//}
}
