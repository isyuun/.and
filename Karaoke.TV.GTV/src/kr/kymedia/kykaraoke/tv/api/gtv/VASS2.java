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
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	VASS2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api.gtv
 *    |_ VASS2.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.api.gtv;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kumyoung.stbcomm.SetopHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import ac.rs.com.model.RequestData;
import ac.rs.com.model.ResponseData;
import ac.rs.lgd.svc.sim.RSRequestClient;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-06-28
 */
public class VASS2 extends VASS {
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

	private Handler m_handler;

	public VASS2(Handler h) {
		super();
		m_handler = h;
	}

	@Override
	public void sendMessage(int state) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + request + "->" + COMPLETE_VASS.get(state));
		Bundle b = new Bundle();
		b.putInt("state", state);

		Message msg = m_handler.obtainMessage();
		msg.setData(b);
		m_handler.sendMessage(msg);
	}

	String SBCCONTNO;
	String STBMAC;

	@Override
	public void setVASSParam(String SBCCONTNO, String STBMAC) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "SBCCONTNO=" + SBCCONTNO + "|" + "SBCCONTNO=" + STBMAC);
		this.SBCCONTNO = SBCCONTNO;
		this.STBMAC = STBMAC;
		__VASS.cont_no = SBCCONTNO;
		__VASS.stb_mac_addr = STBMAC;
	}

	REQUEST_VASS request = REQUEST_VASS.REQUEST_VASS_NONE;

	@Override
	public void setRequest(REQUEST_VASS request) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + request);
		this.request = request;

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_CHECK:
				break;
			case REQUEST_VASS_PPX_CHECK_PLAY:
				break;
			case REQUEST_VASS_PPX_PASSWORD:
				break;
			case REQUEST_VASS_PPX_PURCHASE:
				break;
		}
	}

	protected String url = "";
	String m;

	/**
	 * <pre>
	 *   KP_4000에서 전문조회한다.
	 *   빙신...머하잔겨...누가네멋대로여다갖다처박으래
	 *    1. 1일 이용권 구매 purchasePpvProduct
	 *    2. 1일 이용권 조회 checkPurchasePpvProduct
	 *    3. 월/년 이용권 구매 purchasePpxProduct
	 *    4. 월/년 이용권 조회 checkPurchasePpmProduct
	 *    5. promise 년 이용권 구매 시에만 10으로 입력
	 * </pre>
	 */
	String password;
	@Override
	public void setVASSUrl(String password) {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + request);

		this.password = password;

		this.url = __VASS.VASS_REQUEST_PAGE;
		this.password = password;

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_CHECK:
			case REQUEST_VASS_PPX_CHECK_PLAY:
				this.m = "getSubService";
				break;
			case REQUEST_VASS_PPX_PASSWORD:
			case REQUEST_VASS_PPX_PURCHASE:
				this.m = "subService";
				break;
		}

		this.url += "CMD=" + m;
		this.url += "&PARAM=";
		this.url += "SBCCONTNO=" + SBCCONTNO;
		this.url += "|PRODUCTCODE=" + getTicketItem().id_product;
		this.url += "|STBMAC=" + STBMAC;
		this.url += "|SVCID=";
		this.url += "|APPID=" + "90";
		this.url += "|CHNO=|";
	}

	private boolean confirmAuth() throws Exception {
		boolean ret = false;

		Log.i(_toString(), "[SEND]" + "confirmAuth()"/*getMethodName()*/ + ret + "[M]" + this.m);
		//if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + "confirmAuth()"/*getMethodName()*/ + ret + "[M]" + this.m + "[" + this.url + "]"/* + getVASSParams()*/);

		this.url = __VASS.VASS_REQUEST_PAGE;
		this.url += String.format("CMD=confirmAuth&PARAM=SBCCONTNO=%s|STBMAC=%s|SEARCHTYPE=%d|PERSONALNO=%s|", SBCCONTNO, STBMAC, 3, SBCCONTNO);

		parseVASSResultISU(sendRequestISU());

		ret = ("Y").equalsIgnoreCase(isSuccess);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "[RECV]" + "confirmAuth()"/*getMethodName()*/ + ret + "[M]" + this.m);
		//if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "confirmAuth()"/*getMethodName()*/ + ret + "[M]" + this.m + "[" + this.url + "]"/* + getVASSParams()*/);

		return ret;
	}

	private String checkPurchasePpxProduct() throws Exception {
		String ret = null;

		for (String key : items.keySet()) {
			this.key = key;
			TicketItem item = items.get(key);

			if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());

			switch (item.product_type) {
				case NONE:
					break;
				case PPM:
					break;
				case PPV:
					break;
				case CPN:
					continue;
			}

			sendRequestRSi100n11(item.id_product);
			if (("Y").equalsIgnoreCase(isSuccess)) {
				break;
			}
		}

		return ret;
	}

	@Override
	public void sendRequest() throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		String response = null;

		switch (request) {
			case REQUEST_VASS_NONE:
				break;
			case REQUEST_VASS_PPX_CHECK:
			case REQUEST_VASS_PPX_CHECK_PLAY:
				checkPurchasePpxProduct();
				break;
			case REQUEST_VASS_PPX_PASSWORD:
				break;
			case REQUEST_VASS_PPX_PURCHASE:
				TicketItem item = getTicketItem();
				switch (item.product_type) {
					case NONE:
						break;
					case PPM:
						if (confirmAuth()) {
							setVASSUrl(this.password);
							response = (sendRequestISU());
						}
						sendRequestRSi100n11(item.id_product);
						parseVASSResultISU(response);
						break;
					case PPV:
						sendRequestRSi300n31(item.id_product, item.price, "00000002400");
						sendRequestRSi100n11(item.id_product);
						break;
					case CPN:
						break;
				}
				break;
		}

		parseVASSResult(response);
	}

	private String sendRequestISU() throws Exception {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + getMethodName() + "[M]" + this.m + "[" + this.url + "]"/* + getVASSParams()*/);

		String ret = null;

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
			Log.wtf(_toString(), "[SEND]" + getMethodName() + "[M]" + this.m + "[CODE]" + response);
			is = conn.getInputStream();
		} catch (Exception e) {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + getMethodName() + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
			throw (e);
		}

		try {
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line = null;
			String result = "";
			int iReadCount = 0;

			while ((line = bufReader.readLine()) != null) {
				if (iReadCount > 0) {
					result += "\r\n";
				}

				result += line;
				iReadCount++;
			}

			Log.wtf(_toString(), "[RECV]" + getMethodName() + "[M]" + this.m + "[" + result + "]");

			ret = result;

		} catch (Exception e) {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[RECV]" + getMethodName() + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
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

	RequestData requestdata = new RequestData();
	ResponseData responsedata/* = new ResponseData()*/;

	/**
	 * 요청메시지 전송 후 응답 메시시 수신 후 출력
	 */
	private void sendRequestRSi(String item_code) throws Exception {
		//if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName()/* + "[" + this.url + "]"*/);
		Log.wtf(_toString(), "[SEND]" + getMethodName() + "[M]" + this.m + "[CODE]" + requestdata.biz_code + "," + requestdata.req_code + ":" + item_code);
		if (IKaraokeTV.DEBUG) Log.d(_toString(), "[SEND]" + getMethodName() + "[REQUESTDATA]" + requestdata);

		RSRequestClient datatrans = new RSRequestClient(__VASS.hostname, __VASS.port, __VASS.hosturl);
		responsedata = datatrans.sendData(requestdata);

		Log.i(_toString(), "return_code = " + responsedata.return_code);
		Log.i(_toString(), "return_message = " + responsedata.return_message);
		for (Object key : responsedata.fields.keySet()) {
			Log.wtf(_toString(), key + " = " + responsedata.fields.get(key));
		}
		Log.d(_toString(), "name = " + responsedata.fields.get("name"));
		Log.d(_toString(), "item_code = " + responsedata.fields.get("item_code"));
		Log.d(_toString(), "start_date = " + responsedata.fields.get("start_date") + ":" + TextUtil.getToday("yyyyMMddHHmmss", "yyyy년MM월dd일HH시 부터", TextUtil.trim("" + responsedata.fields.get("start_date"))));
		Log.d(_toString(), "end_date = " + responsedata.fields.get("end_date") + ":" + TextUtil.getToday("yyyyMMddHHmmss", "yyyy년MM월dd일HH시 까지", TextUtil.trim("" + responsedata.fields.get("end_date"))));

		Log.wtf(_toString(), "[RECV]" + getMethodName() + "[ITEM_CODE]" + item_code + ":" + (!TextUtil.isEmpty(item_code) ? item_code.equals(responsedata.fields.get("item_code")) : false) + "[RETURN_CODE]" + (responsedata.return_code) + "[RETURN_MESSGE]" + responsedata.return_message + "-" + getVASSErrorMsg(responsedata.return_code));

		setVASSErrorCode(responsedata.return_code + ":" + responsedata.return_message);

		if (responsedata.return_code != null && ("00000").equals(responsedata.return_code)) {
			isSuccess = ("Y");
			//있슴한다.
			if (!TextUtil.isEmpty("" + responsedata.fields.get("item_code"))) {
				if (!(!TextUtil.isEmpty(item_code) ? item_code.equals(responsedata.fields.get("item_code")) : false)) {
					Log.wtf(_toString(), "[RECV]" + getMethodName() + "[NG]" + responsedata.fields.get("item_code") + ":" + item_code);
					isSuccess = ("N");
				}
			}
		} else {
			isSuccess = ("N");
		}

		TicketItem item = getTicketItem();

		//정상이믄한다.
		if (("Y").equalsIgnoreCase(isSuccess)) {
			if (!TextUtil.isEmpty("" + responsedata.fields.get("start_date"))) {
				item.start_date = TextUtil.trim("" + responsedata.fields.get("start_date"));
			}
			if (!TextUtil.isEmpty("" + responsedata.fields.get("end_date"))) {
				item.end_date = TextUtil.trim("" + responsedata.fields.get("end_date"));
			}
		}

		/**
		 * RETURN_DATA=상품코드^(성공/실패)^셋탑아이디^구매번호(TID)^이용권만료일자
		 */
		/**
		 * (성공/실패)
		 */
		RETURN_DATA = isSuccess + "^";
		/**
		 * 셋탑아이디
		 */
		RETURN_DATA += __VASS.cont_no + "^";
		/**
		 * 구매번호(TID) - 서비스 가입 시작 일시
		 */
		RETURN_DATA += item.start_date + "^";
		/**
		 * 이용권만료일자 - 서비스 가입 종료 일시
		 */
		RETURN_DATA += item.end_date;

		Log.wtf(_toString(), "[RECV]" + getMethodName() + "[ITEM_CODE]" + item_code + "[isSuccess]" + isSuccess + "[RETURN_DATA]" + RETURN_DATA);
	}

	/**
	 * 4.4.1.1  서비스 인증 (biz_code: 100)
	 */
	private void sendRequestRSi100n11(String item_cd) throws Exception {
		this.url = "http://" + __VASS.hostname + ":" + __VASS.port + __VASS.hosturl;
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + getMethodName() + item_cd + "[" + this.url + "]");

		try {
			//요청데이타 생성
			requestdata.biz_code = "100";
			requestdata.req_code = "11";
			//requestdata.req_code = "12";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("stb_mac_addr", STBMAC);
			requestdata.fields.put("cont_no", __VASS.cont_no);
			requestdata.fields.put("app_id", __VASS.app_id);
			//requestdata.fields.put("user_id", "testid");
			requestdata.fields.put("item_cd", item_cd);
			//요청메시지 전송 후 응답 메시시 수신 후 출력
			sendRequestRSi(item_cd);
		} catch (Exception e) {
			//e.printStackTrace();
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
			throw (e);
		}
	}

	/**
	 * 4.4.1.3  종량형 상품 구매 정보 (biz_code: 300)
	 */
	private void sendRequestRSi300n31(String item_id, int price, String end_date) throws Exception {
		this.url = "http://" + __VASS.hostname + ":" + __VASS.port + __VASS.hosturl;
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + getMethodName() + item_id + "," + price + "," + end_date + "[M]" + this.m + "[" + "http://" + this.url + "]");

		try {
			//요청데이타 생성
			requestdata.biz_code = "300";
			requestdata.req_code = "31";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("stb_mac_addr", __VASS.stb_mac_addr);
			requestdata.fields.put("cont_no", __VASS.cont_no);
			requestdata.fields.put("app_id", __VASS.app_id);
			requestdata.fields.put("item_code", item_id);
			requestdata.fields.put("pay_kind_cd", "1");
			requestdata.fields.put("price", "" + price);
			requestdata.fields.put("quantity", "1");
			requestdata.fields.put("discount", "0");
			requestdata.fields.put("order_date", TextUtil.getTodayFull());    // YYYYMMDDXXXXXX
			//requestdata.fields.put("end_date", "00000002400"); // pppp
			//requestdata.fields.put("end_date", "00000000005");    // junoci fix 4/3
			requestdata.fields.put("end_date", end_date);
			//requestdata.fields.put("mobile_no", "01012341234");
			//requestdata.fields.put("approval_no", "1234567890");
			//requestdata.fields.put("prepay_kind_cd", "2");
			//requestdata.fields.put("telecom_cd", "3");
			//requestdata.fields.put("confirm", "1");
			//requestdata.fields.put("user_id", "testid");
			//요청메시지 전송 후 응답 메시시 수신 후 출력
			sendRequestRSi(item_id);
		} catch (Exception e) {
			//e.printStackTrace();
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.m + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
			throw (e);
		}
	}

	public static enum ERROR_MSG_VASS {

		B0000(__VASS.B0000),
		B0001(__VASS.B0001),
		B0002(__VASS.B0002),
		B0003(__VASS.B0003),
		B0004(__VASS.B0004),
		B0005(__VASS.B0005),
		B0006(__VASS.B0006),
		B0007(__VASS.B0007),
		B0008(__VASS.B0008),
		B0009(__VASS.B0009),
		B0010(__VASS.B0010),
		B0011(__VASS.B0011),
		B0012(__VASS.B0012),
		B0013(__VASS.B0013),
		C9999(__VASS.C9999),
		B0014(__VASS.B0014),
		B0015(__VASS.B0015),
		B0016(__VASS.B0016),
		B0017(__VASS.B0017),
		B0018(__VASS.B0018),
		B0019(__VASS.B0019),
		B0020(__VASS.B0020),
		B0021(__VASS.B0021),
		B0022(__VASS.B0022),
		B0023(__VASS.B0023),
		_00000(__VASS._00000),
		_10000(__VASS._10000),
		_10001(__VASS._10001),
		_10002(__VASS._10002),
		_10003(__VASS._10003),
		_10004(__VASS._10004),
		_20000(__VASS._20000),
		_20001(__VASS._20001),
		_20002(__VASS._20002),
		_20003(__VASS._20003),
		_20004(__VASS._20004),
		_20005(__VASS._20005),
		_30000(__VASS._30000),
		_30001(__VASS._30001),
		_30002(__VASS._30002),
		_40000(__VASS._40000),
		_40001(__VASS._40001),
		_40002(__VASS._40002),
		_40003(__VASS._40003),
		_50000(__VASS._50000),
		_50001(__VASS._50001),
		_50002(__VASS._50002),
		_50003(__VASS._50003),
		_50004(__VASS._50004),
		_50005(__VASS._50005),
		_50006(__VASS._50006),
		_60000(__VASS._60000),
		_60001(__VASS._60001),
		_70000(__VASS._70000),
		_70001(__VASS._70001),
		_99999(__VASS._99999);

		private final String value;

		ERROR_MSG_VASS(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}

		public static String get(String value) {
			String ret = _99999.value();
			for (ERROR_MSG_VASS state : ERROR_MSG_VASS.values()) {
				if (null != state.name() && (state.name().equals(value) || state.name().substring(1).equals(value))) {
					//if (IKaraokeTV.DEBUG) Log.i("ERROR_MSG_VASS", (state.name().equals(value) || state.name().substring(1).equals(value)) + "-" + value + ":" + state.name() + ":" + state.value());
					ret = state.value();
				}
			}
			return ret;
		}
	}

	/**
	 * <pre>
	 * ISU - 결과 사유 응답 코드
	 *  B0000	정상등록완료
	 *  B0001	가입자 없음
	 *  B0002	가입자 일시정지
	 *  B0003	가입자 이용중지
	 *  B0004	가입자 변경 처리중
	 *  B0005	가입자 해지 처리중
	 *  B0006	XCION 상품코드 매핑 오류
	 *  B0007	IPTV 서비스 미사용
	 *  B0008	요청상품 기신청
	 *  B0009	요청상품 기사용
	 *  B0010	시스템 점검중
	 *  B0011	가입계약번호 오류
	 *  B0012	MAC Address 오류
	 *  B0013	중복채널 존재
	 *  C9999	기타사유
	 *  B0014	가입된 서비스/상품 없슴
	 *  B0015	IPTV 서비스 미 가입
	 *  B0016	IPTV 서비스 미 등록
	 *  B0017	등록 확인 오류
	 *  B0018	주민번호 또는 사업자번호 또는 가입자번호가 다릅니다.
	 *  B0019	월 1회 가입 제한
	 *  B0020	해당 상품 없음(NO Data)
	 *  B0021	Xcion 연동 Time Out
	 *  B0022	상품가입불가
	 *  B0023	부가서비스 해지 실패
	 * RSi(TV) - 결과코드
	 *  00000	정상처리
	 *  10000	메시지 오류(알 수 없는 오류)
	 *  10001	정의 되지 않은 Biz Code.
	 *  10002	정의되지 않은 Req Code.
	 *  10003	필수 항목 누락.
	 *  10004	데이터 타입 오류.
	 *  20000	번대 DB 관련 오류
	 *  20000	검색 중 오류가 발생하였습니다. (알 수 없는 DB 오류)
	 *  20001	DB Connection 에러
	 *  20002	중복 등록 오류
	 *  20003	수정, 삭제 실패 (미등록 정보에 대한 수정, 삭제 시도)
	 *  20004	검색 결과가 없음
	 *  20005	데이터 타입 오류
	 *  30000	번대 타 시스템과 연동 중 오류
	 *  30000	알 수 없는 오류가 발생하였습니다.
	 *  30001	PVS 시스템과 연동 중 오류가 발생하였습니다.
	 *  30002	PVS DB 와 연동 중 오류가 발생하였습니다.
	 *  40000	네트워크 연결 중 오류가 발생하였습니다.
	 *  40001	메시지 수신 중 오류가 발생하였습니다.(메시지를 모두 수신하지 못 함)  일시적인 네트워크 오류이므로 3 회까지 동일 메시지 자동으로 재 요청 함(biz_code=300 제외)
	 *  40002	해당 프로세스 접근 중 오류가 발생하였습니다.
	 *  40003	객체를 수신하지 못하였습니다.
	 *  50000	사용자 인증 처리 중 알 수 없는 오류가 발생하였습니다.
	 *  50001	서비스에 가입하지 않은 사용자 입니다.
	 *  50002	서비스 가입 처리 중 입니다.
	 *  50003	서비스 가입 처리에 실패하였습니다. 고객 센터에 문의하세요.
	 *  50004	존재하지 않는 상품에 인증요청을 하셨습니다.
	 *  50005	서비스 정지 사용자 입니다.
	 *  50006	서비스 해지 사용자 입니다
	 *  60000	종량형 상품 구매 처리 중 알 수 없는 오류가 발생하였습니다.
	 *  60001	존재하지 않는 상품을 구매 요청 하셨습니다.
	 *  70000	가입형 서비스 가입 이력 처리 중 알 수 없는 오류가 발생하였습니다.
	 *  70001	존재하지 않는 상품 이력을 요청 하셨습니다.
	 *  99999	알 수 없는 오류
	 * </pre>
	 */
	private String getVASSErrorMsg(String code) {
		return ERROR_MSG_VASS.get(code).toString();
	}

	private boolean checkPassword() {
		return (!TextUtil.isEmpty(password) && password.equals(SetopHandler.getInstance().getSecretNum()));
	}

	private void parseVASSResultISU(String response) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + request + ":" + response);

		String[] results = response.split("\\|");
		if (null != results) {

			for (String line : results) {
				if (IKaraokeTV.DEBUG) Log.d(_toString(), "[RESULT]" + line);
			}

			String[] codes = results[results.length - 1].split("\n");

			for (String line : codes) {
				if (IKaraokeTV.DEBUG) Log.d(_toString(), "[RETURN_CODE]" + line);
			}

			Log.wtf(_toString(), "[RETURN_CODE]" + (codes[0]) + "[RETURN_MESSGE]" + getVASSErrorMsg(codes[0]));

			setVASSErrorCode(codes[0] + ":" + getVASSErrorMsg(codes[0]));

			if (codes[0] != null && ("B0000").equals(codes[0])) {
				isSuccess = ("Y");
			} else {
				isSuccess = ("N");
			}
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + request + ":" + response);
	}

	private String isSuccess;

	@Override
	public String isSuccess() {
		return isSuccess;
	}

	/**
	 * 결과메시지만전달하자
	 */
	@Override
	public void parseVASSResult(String response) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + request + ":" + response);

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
				if (checkPassword()) {
					isSuccess = ("Y");
				}
				sendMessage(COMPLETE_VASS_PPX_PASSWORD);
				break;
			case REQUEST_VASS_PPX_PURCHASE:
				sendMessage(COMPLETE_VASS_PPX_PURCHASE);
				break;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + request + ":" + response);
	}

	private LinkedHashMap<String, TicketItem> items = new LinkedHashMap<>();

	public void putTicketItems(LinkedHashMap<String, TicketItem> items) {
		if (IKaraokeTV.DEBUG) kr.kymedia.karaoke.api.Log.d("[VASS]" + _toString(), getMethodName() + "" + items);
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
