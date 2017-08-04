package kr.kymedia.kykaraoke.tv.api.btv;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.api._VASS;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 * VASS:BTV전문
 * </pre>
 *
 * @author isyoon
 */
class VASS extends Thread implements _VASS {
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

	protected final Handler m_handler;

	protected String m_strSTBID = "";
	protected String m_strMACAddress = "";

	//private String end_date = "";
	//
	//@Override
	//public String getTicketEndDate() {
	//	return end_date;
	//}
	//
	//@Override
	//public void setTicketEndDate(String end_date) {
	//	this.end_date = end_date;
	//}

	@Override
	public void putTicketItems(LinkedHashMap<String, TicketItem> items) {
	}

	@Override
	public LinkedHashMap<String, TicketItem> getTicketItems() {
		return null;
	}

	@Override
	public void setTicketKey(String key) {
	}

	@Override
	public TicketItem getTicketItem() {
		return null;
	}

	REQUEST_VASS request = REQUEST_VASS.REQUEST_VASS_NONE;

	@Override
	public void setRequest(REQUEST_VASS request) {
		this.request = request;
	}

	///**
	// * 빙신아이건머야
	// */
	//@Deprecated
	//private String m_strProduct = "";
	//
	///**
	// * 빙신아이건머야
	// */
	//@Deprecated
	//@Override
	//public void setProduct(String product) {
	//	m_strProduct = product;
	//}

	//@Deprecated
	//private /*static */ InputStream en;

	public VASS(Handler h) {
		m_handler = h;
		//en = null;
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

	@Override
	public void setVASSParam(String stbid, String mac) {
		m_strSTBID = stbid;
		m_strMACAddress = mac;
	}

	///**
	// * <pre>
	// *   KP_4000에서 전문조회한다.
	// *   빙신...머하잔겨...누가네멋대로여다갖다처박으래
	// *    1. 1일 이용권 구매 purchasePpvProduct
	// *    2. 1일 이용권 조회 checkPurchasePpvProduct
	// *    3. 월/년 이용권 구매 purchasePpxProduct
	// *    4. 월/년 이용권 조회 checkPurchasePpmProduct
	// *    5. promise 년 이용권 구매 시에만 10으로 입력
	// * </pre>
	// */
	//protected void setVASSUrl(String m, String password) {
	//	//this.m = m;
	//	//this.url = __VASS.VASS_REQUEST_PAGE;
	//	//this.url = this.url + "m=" + m;
	//	//this.url = this.url + "&stb_id=" + m_strSTBID;
	//	//
	//	////if (request == REQUEST_VASS_PASSWORD_FOR_DAY || request == REQUEST_VASS_PASSWORD_FOR_MONTH) {
	//	//if (request == REQUEST_VASS_PPX_PASSWORD) {
	//	//	this.url = this.url + "&purchase_passwd=" + password;
	//	//	return;
	//	//}
	//	//
	//	//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + this.url);
	//	//
	//	//switch (request) {
	//	//	case REQUEST_VASS_MONTH_PURCHASE:
	//	//	case REQUEST_VASS_MONTH_CHECK:
	//	//	case REQUEST_VASS_MONTH_CHECK_PLAY:
	//	//		// 빙신...머하잔겨...누가네멋대로여다갖다처박으래
	//	//		// if (m_strProduct == P_APPNAME_SKB_STB_UHD) {
	//	//		// this.url = this.url + "&id_product=" + VASS_PRODUCT_ID_MONTH_SKB_STBUHD;
	//	//		// } else {
	//	//		// this.url = this.url + "&id_product=" + VASS_PRODUCT_ID_MONTH_SKT_BOX;
	//	//		// }
	//	//		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "mTicketItemMonth:" + mTicketItemMonth);
	//	//		if (mTicketItemMonth != null) {
	//	//			this.url = this.url + "&id_product=" + mTicketItemMonth.id_product;
	//	//		}
	//	//		break;
	//	//	case REQUEST_VASS_DAY_PURCHASE:
	//	//	case REQUEST_VASS_DAY_CHECK:
	//	//	case REQUEST_VASS_DAY_CHECK_PLAY:
	//	//		// 빙신...머하잔겨...누가네멋대로여다갖다처박으래
	//	//		// if (m_strProduct == P_APPNAME_SKB_STB_UHD) {
	//	//		// this.url = this.url + "&id_product=" + VASS_PRODUCT_ID_DAY_SKB_STBUHD;
	//	//		// } else {
	//	//		// this.url = this.url + "&id_product=" + VASS_PRODUCT_ID_DAY_SKT_BOX;
	//	//		// }
	//	//		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "mTicketItemDay:" + mTicketItemDay);
	//	//		if (mTicketItemDay != null) {
	//	//			this.url = this.url + "&id_product=" + mTicketItemDay.id_product;
	//	//		}
	//	//		break;
	//	//}
	//	//
	//	//this.url = this.url + "&mac_address=" + m_strMACAddress;
	//}

	@Override
	public void setVASSUrl(String password) {
	}

	/**
	 * <pre>
	 *   org.apache.http 대체
	 * </pre>
	 *
	 * @see VASS2#sendRequest()
	 */
	@Override
	public void sendRequest() throws Exception {
		//if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		//
		//try {
		//	DefaultHttpClient client = new DefaultHttpClient();
		//
		//	// String this.url = URLEncoder.encode(this.url, "UTF-8");
		//	HttpPost post = new HttpPost(this.url);
		//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + "[M]" + this.m + "[" + this.this.url + "]");
		//	/*
		//	 * HttpParams param = client.getParams(); HttpConnectionParams.setConnectionTimeout(param, 5000); HttpConnectionParams.setSoTimeout(param, 5000);
		//	 */
		//	HttpResponse response = client.execute(post);
		//
		//	HttpEntity httpEntity = response.getEntity();
		//	en = httpEntity.getContent();
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//} catch (ClientProtocolException e) {
		//	e.printStackTrace();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		//
		//try {
		//	BufferedReader bufReader = new BufferedReader(new InputStreamReader(en, "UTF-8"));
		//
		//	String line = null;
		//	String result = "";
		//	int iReadCount = 0;
		//
		//	while ((line = bufReader.readLine()) != null) {
		//		if (iReadCount > 0) {
		//			result += "\r\n";
		//		}
		//
		//		result += line;
		//		iReadCount++;
		//	}
		//
		//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[M]" + this.m + "[" + result + "]");
		//
		//	parseVASSResult(result);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
	}

	ArrayList<String> results = new ArrayList<String>();

	@Override
	public void parseVASSResult(String response) {
		//// if (IKaraokeTV.DEBUG) _LOG.i(CLASS, "[VASS Response] " + response);
		//// isyoon
		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + request + ":" + response);
		//
		//isSuccess = "";
		//results.clear();
		//
		//// String result = response.replace("RETURN_DATA=", "");
		//String responses[] = response.split("=");
		//Collections.addAll(results, responses[1].split("\\^"));
		//
		//Log.wtf(_toString(), "[RETURN_DATA]" + results.size() + ":" + results + "[" + responses[1] + "]");
		//
		//isSuccess = results.get(0);
		//
		//if (isSuccess.equals("Y") || isSuccess.equals("N")) {
		//	switch (request) {
		//		case REQUEST_VASS_DAY_PURCHASE:
		//			m_strPurchaseResult = responses[1];
		//			sendMessage(COMPLETE_VASS_DAY_PURCHASE);
		//			break;
		//		case REQUEST_VASS_DAY_CHECK:
		//			if (isSuccess.equals("Y")) {
		//				String temp = response.substring(response.length() - 14, response.length());
		//				end_date = "(" + temp.substring(0, 4) + "년" + temp.substring(4, 6) + "월" + temp.substring(6, 8) + "일 " + temp.substring(8, 10) + ":" + temp.substring(10, 12) + "까지)";
		//				if (IKaraokeTV.DEBUG) Log.i(_toString(), "Available Date is " + end_date);
		//			}
		//			sendMessage(COMPLETE_VASS_DAY_CHECK);
		//			break;
		//		case REQUEST_VASS_MONTH_PURCHASE:
		//			m_strPurchaseResult = responses[1];
		//			sendMessage(COMPLETE_VASS_MONTH_PURCHASE);
		//			break;
		//		case REQUEST_VASS_MONTH_CHECK:
		//			sendMessage(COMPLETE_VASS_MONTH_CHECK);
		//			break;
		//		case REQUEST_VASS_YEAR_PURCHASE:
		//			sendMessage(COMPLETE_VASS_YEAR_PURCHASE);
		//			break;
		//		case REQUEST_VASS_YEAR_CHECK:
		//			sendMessage(COMPLETE_VASS_YEAR_CHECK);
		//			break;
		//		case REQUEST_VASS_PASSWORD_FOR_DAY:
		//			sendMessage(COMPLETE_VASS_PASSWORD_FOR_DAY);
		//			break;
		//		case REQUEST_VASS_PASSWORD_FOR_MONTH:
		//			sendMessage(COMPLETE_VASS_PASSWORD_FOR_MONTH);
		//			break;
		//		case REQUEST_VASS_DAY_CHECK_PLAY:
		//			if (isSuccess.equals("Y")) {
		//				String temp = response.substring(response.length() - 14, response.length());
		//				end_date = "(" + temp.substring(0, 4) + "년" + temp.substring(4, 6) + "월" + temp.substring(6, 8) + "일 " + temp.substring(8, 10) + ":" + temp.substring(10, 12) + "까지)";
		//				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[OK]" + "Available Date is " + end_date);
		//			}
		//			sendMessage(COMPLETE_VASS_DAY_CHECK_PLAY);
		//			break;
		//		case REQUEST_VASS_MONTH_CHECK_PLAY:
		//			sendMessage(COMPLETE_VASS_MONTH_CHECK_PLAY);
		//			break;
		//	}
		//}
		//
		//String strVASSErrorCode = "[NG]";
		//
		//try {
		//	strVASSErrorCode = m_strPurchaseResult.substring(m_strPurchaseResult.length() - 4, m_strPurchaseResult.length());
		//} catch (Exception e) {
		//
		//	// e.printStackTrace();
		//}
		//
		//m_strVASSErrorCode = results.get(results.size() - 1);
		//
		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + request + ":" + strVASSErrorCode + ":" + m_strVASSErrorCode);
	}

	@Override
	public String isSuccess() {
		return null;
	}

	@Override
	public String getVASSErrorCode() {
		return null;
	}

	@Override
	public String RETURN_DATA() {
		return null;
	}
}
