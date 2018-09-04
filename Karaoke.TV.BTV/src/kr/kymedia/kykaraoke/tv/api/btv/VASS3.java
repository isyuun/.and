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
 * filename	:	VASS3.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api.btv
 *    |_ VASS3.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.api.btv;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-07-20
 */
class VASS3 extends VASS2 {
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

	public VASS3(Handler h) {
		super(h);
	}

	protected String getVASSParams() {
		String ret = "";
		//ret += "\n[PARAMS]" + __VASS.VASS_REQUEST_PAGE;
		ret += "\n[PARAMS]" + request;
		ret += "\n[PARAMS]" + m;
		ret += "\n[PARAMS]" + m_handler;
		ret += "\n[PARAMS]" + m_strSTBID;
		ret += "\n[PARAMS]" + m_strMACAddress;
		ret += "\n[PARAMS]" + getTicketItem().id_product;
		//ret += "\n[PARAMS]" + this.url;
		//ret += "\n[RESULT]" + results;
		//ret += "\n[RESULT]" + isSuccess;
		//ret += "\n[RESULT]" + m_strPurchaseResult;
		//ret += "\n[RESULT]" + end_date;
		return ret;
	}

	@Override
	public void run() {
		try {
			sendRequest();
		} catch (Exception e) {
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
		}
	}

	protected String url = "";
	String m;
	String password;

	@Override
	public void setVASSUrl(String password) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + request);

		TicketItem item = getTicketItem();

		String m = null;

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_PURCHASE:
				switch (item.product_type) {
					case NONE:
						break;
					case PPM:
						m = __VASS.M_PPM_PURCHASE;
						break;
					case PPV:
						m = __VASS.M_PPV_PURCHASE;
						break;
					case CPN:
						break;
				}
				break;
			case REQUEST_VASS_PPX_CHECK:
			case REQUEST_VASS_PPX_CHECK_PLAY:
				break;
			case REQUEST_VASS_PPX_PASSWORD:
				m = __VASS.M_PASSWORD_CHECK;
				break;
		}

		setVASSUrl(m, password);
	}

	private void setVASSUrl(String m, String password) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + m + ", " + password);

		this.password = password;
		this.m = m;

		this.url = __VASS.VASS_REQUEST_PAGE;
		this.url = this.url + "m=" + m;
		this.url = this.url + "&stb_id=" + m_strSTBID;

		if (request == REQUEST_VASS.REQUEST_VASS_PPX_PASSWORD) {
			this.url = this.url + "&purchase_passwd=" + password;
			return;
		}

		this.url = this.url + "&id_product=" + items.get(key).id_product;

		this.url = this.url + "&mac_address=" + m_strMACAddress;
	}

	private String send() throws Exception {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[SEND]" + "[M]" + this.m + "[" + this.url + "]" + getVASSParams());

		String ret = "";

		InputStream is = null;

		try {
			URL url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_REQUEST_READ);
			conn.setConnectTimeout(TIMEOUT_REQUEST_CONNECT);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(_toString(), "[SEND]" + "[M]" + this.m + "[CODE]" + response);
			is = conn.getInputStream();
		} catch (Exception e) {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
			throw (e);
		}

		try {
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line;
			int iReadCount = 0;

			while ((line = bufReader.readLine()) != null) {
				if (iReadCount > 0) {
					ret += "\r\n";
				}

				ret += line;
				iReadCount++;
			}

			Log.wtf(_toString(), "[RECV]" + "[M]" + this.m + "[" + ret + "]");

		} catch (Exception e) {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[RECV]" + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
			throw (e);
		} finally {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
		}

		return ret;
	}

	private String checkPurchasePpxProduct() throws Exception {
		String ret = null;

		for (String key : items.keySet()) {
			this.key = key;
			TicketItem item = items.get(key);
			String m = null;

			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());

			switch (item.product_type) {
				case NONE:
					break;
				case PPM:
					m = __VASS.M_PPM_CHECK;
					break;
				case PPV:
					m = __VASS.M_PPV_CHECK;
					break;
				case CPN:
					continue;
			}

			if (!TextUtil.isEmpty(m)) {
				setVASSUrl(m, this.password);
				ret = send();
				if (("Y").equalsIgnoreCase(parseVASSResponse(ret).get(0))) {
					break;
				}
			}
		}

		return ret;
	}

	@Override
	public void sendRequest() throws Exception {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + request + "[M]" + this.m);

		String result = null;

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_CHECK:
			case REQUEST_VASS_PPX_CHECK_PLAY:
				result = checkPurchasePpxProduct();
				break;
			case REQUEST_VASS_PPX_PASSWORD:
			case REQUEST_VASS_PPX_PURCHASE:
				result = send();
				break;
		}

		parseVASSResult(result);
	}

	private ArrayList<String> parseVASSResponse(String response) {
		if (TextUtil.isEmpty(response)) {
			return results;
		}

		results.clear();

		String ret[] = response.split("=");
		RETURN_DATA = ret[1];
		Collections.addAll(results, ret[1].split("\\^"));

		Log.d(_toString(), getMethodName() + "[RETURN_DATA]" + results.size() + ":" + results);

		return results;
	}

	@Override
	public void parseVASSResult(String response) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + request + ":" + response);

		TicketItem item = getTicketItem();

		parseVASSResponse(response);

		try {
			isSuccess = results.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			item.end_date = TextUtil.getToday("yyyyMMddHHmmss", results.get(results.size() -1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			m_strVASSErrorCode = results.get(results.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[RT]" + request + ":" + results + ":" + item);

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_CHECK:
				sendMessage(COMPLETE_VASS_PPX_CHECK);
				break;
			case REQUEST_VASS_PPX_CHECK_PLAY:
				sendMessage(COMPLETE_VASS_PPX_CHECK_PLAY);
				break;
			case REQUEST_VASS_PPX_PASSWORD:
				sendMessage(COMPLETE_VASS_PPX_PASSWORD);
				break;
			case REQUEST_VASS_PPX_PURCHASE:
				sendMessage(COMPLETE_VASS_PPX_PURCHASE);
				break;
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + request + ":" + m_strVASSErrorCode);
	}

	private String isSuccess = "";

	@Override
	public String isSuccess() {
		return isSuccess;
	}

	private String m_strVASSErrorCode = "";

	@Override
	public String getVASSErrorCode() {
		return m_strVASSErrorCode;
	}

	private LinkedHashMap<String, TicketItem> items = new LinkedHashMap<>();

	public void putTicketItems(LinkedHashMap<String, TicketItem> items) {
		if (BuildConfig.DEBUG) kr.kymedia.karaoke.api.Log.d("[VASS]" + _toString(), getMethodName() + "" + items);
		this.items = items;
	}

	public LinkedHashMap<String, TicketItem> getTicketItems() {
		return items;
	}

	String key;

	@Override
	public void setTicketKey(String key) {
		this.key = key;
	}


	public TicketItem getTicketItem() {
		return items.get(key);
	}

	/**
	 * RETURN_DATA=상품코드^(성공/실패)^셋탑아이디^구매번호(TID)^이용권만료일자
	 */
	String RETURN_DATA = "";

	@Override
	public String RETURN_DATA() {
		return this.RETURN_DATA;
	}
}
