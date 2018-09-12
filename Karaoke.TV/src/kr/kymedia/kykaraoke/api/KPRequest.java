package kr.kymedia.kykaraoke.api;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.data.CustomerItem;
import kr.kymedia.kykaraoke.tv.data.ListenItem;
import kr.kymedia.kykaraoke.tv.data.SongItem;
import kr.kymedia.kykaraoke.tv.data.SubMenuItem;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

class KPRequest extends Thread implements _Const {
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

	Handler handler;

	// Common
	public int request;
	public int total_page;
	public String m_strUrl;
	public String result_code;
	public String result_message;

	public String p_op;

	// Base
	public String p_domain = "";
	private String p_apkname;
	public String p_appname = "";
	public String p_debug = "";
	public String p_market = "";
	public String p_ver = "";
	public String p_mid = "";
	public String p_stbid = "";
	public String p_account = "";
	public String p_ncode = "";
	public String p_lcode = "";
	public String p_model = "";
	public String p_mac = "";
	public String p_readmac = "";
	public String p_os = "";
	public String p_osversion = "";
	public int p_apkversioncode = 0;
	public String p_apkversionname = "";
	public String p_passtype = "";
	public String p_passcnt = "";
	public String p_apikey = "";

	// 곡번호 검색
	public String m_strNumberSearchResult = "";

	// 홈
	public String song_title = "";
	public String song_artist = "";
	public String song_id = "";
	public String url_img = "";
	public String listen_title = "";
	public String listen_nickname = "";
	public String listen_id = "";
	public String event_url_img = "";
	public String notice_title = "";
	public String notice_id = "";
	public String event_id = "";
	public String video_url = "";
	public String m_strMainQuickBtnOnUrl01 = "";
	public String m_strMainQuickBtnOffUrl01 = "";
	public String m_strMainQuickBtnOnUrl02 = "";
	public String m_strMainQuickBtnOffUrl02 = "";
	public String video_url_back = "";
	public String url_kylogo = "";
	public SubMenuItem subMainQuickBtn01 = new SubMenuItem();
	public SubMenuItem subMainQuickBtn02 = new SubMenuItem();

	// 애창곡 등록or삭제
	public String result_message_favor = "";

	// 공지사항or이용안내 내용
	public String m_strCustomerDetailTitle = "";
	public String m_strCustomerDetailDate = "";

	// 이벤트 상세 내용
	public String b_type = "";
	public String term_date = "";
	public String e_stats = "";

	// 이벤트 응모
	public String event_popup_msg1 = "";
	public String event_popup_msg2 = "";
	public String event_popup_msg3 = "";

	// 재생 반주곡 번호
	public String m_strRequestPlaySongID = "";

	// 재생 반주곡 정보
	public String url_skym = "";
	public String url_lyric = "";
	public String type = "";

	// 재생 녹음곡 정보
	public String url_record = "";
	// public String m_strListeningSongID = "";

	// 인증 상태
	public String auth_date = "";
	public String auth_mark_auth_idx = "";
	public String auth_modify_idx = "";
	public String auth_btn_type = "";
	public String auth_url_profile = "";
	public String auth_phoneno = "";

	// (마이)녹음곡 프로필 상태
	public String my_auth_url_profile = "";
	public String my_auth_nickname = "";
	public String my_auth_hit = "";
	public String my_auth_heart = "";
	//public String my_auth_auth_date = "";
	public String my_auth_mark_auth_idx = "";
	public String my_auth_modify_idx = "";
	public String my_auth_btn_type = "";

	// 휴대폰 인증번호
	public String auth_num = "";

	// 쿠폰 등록 결과 : 적용 기간
	public String cpn_term = "";
	public String is_cpn = "";

	// 판매 이용권 정보
	private LinkedHashMap<String, TicketItem> items = new LinkedHashMap<>();

	protected void putTicketItems(String key, TicketItem item) {
		//if (null != item)
		{
			items.put(key, item);
		}
	}

	public LinkedHashMap<String, TicketItem> getTicketItems() {
		return items;
	}

	//public boolean m_bShowCouponMenu = false;

	public ArrayList<SongItem> arraySongItem = new ArrayList<SongItem>();
	public ArrayList<CustomerItem> arrayCustomerItem = new ArrayList<CustomerItem>();
	public ArrayList<String> url_imgs = new ArrayList<String>();
	public ArrayList<ListenItem> arrayListenItem = new ArrayList<ListenItem>();
	public ArrayList<SubMenuItem> arraySubMenuItem = new ArrayList<SubMenuItem>();
	public ListenItem listenItem = new ListenItem();

	@Deprecated
	private /*static */InputStream en;

	public KPRequest(Handler handler) {
		this.handler = handler;
		this.en = null;
		this.request = 0;
		this.total_page = 0;
		this.m_strUrl = "";
		this.result_code = "";
		this.result_message = "";
	}

	@Override
	public synchronized void start() {

		try {
			super.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(int state) {
		if (handler == null) {
			return;
		}

		switch (state) {
			case COMPLETE_ERROR_REQUEST_NOT_RESPONSE:
			case COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE:
				this.result_code = "";
				this.result_message = "";
				break;
		}

		Bundle b = new Bundle();
		b.putInt("state", state);

		Message msg = handler.obtainMessage();
		msg.setData(b);
		handler.sendMessage(msg);
	}

	public void setKPParam(String domain, String apkname, String appname, String debug, String market, String version, String mid, String stbid, String account, String ncode, String lcode, String model, String mac, String macori, String os, String osversion, int versioncode, String versionname, String passtype, String passcount, String apikey) {
		p_domain = domain;
		p_apkname = apkname;
		p_appname = appname;
		p_debug = debug;
		p_market = market;
		p_ver = version;
		p_mid = mid;
		p_stbid = stbid;
		p_account = account;
		p_ncode = ncode;
		p_lcode = lcode;
		p_model = model;
		p_mac = mac;
		p_readmac = macori;
		p_os = os;
		p_osversion = osversion;
		p_apkversioncode = versioncode;
		p_apkversionname = versionname;
		p_passtype = passtype;
		p_passcnt = passcount;
		p_apikey = apikey;
	}

	public String setKPBase() {
		String strUrl = "";
		strUrl = p_domain;
		strUrl += "?p_apkname=" + p_apkname;
		strUrl += "?p_appname=" + p_appname;
		strUrl += "&p_debug=" + p_debug;
		strUrl += "&market=" + p_market;
		strUrl += "&p_ver=" + p_ver;
		strUrl += "&p_mid=" + p_mid;
		strUrl += "&p_stbid=" + p_stbid;
		strUrl += "&p_account=" + p_account;
		strUrl += "&p_ncode=" + p_ncode;
		strUrl += "&p_lcode=" + p_lcode;
		strUrl += "&p_model=" + p_model;
		strUrl += "&p_mac=" + p_mac;
		strUrl += "&p_readmac=" + p_readmac;
		strUrl += "&p_os=" + p_os;
		strUrl += "&p_osversion=" + p_osversion;
		strUrl += "&p_apkversioncode=" + p_apkversioncode;
		try {
			strUrl = strUrl + "&p_apkversionname=" + URLEncoder.encode(p_apkversionname, "UTF-8");
		} catch (Exception e) {

			e.printStackTrace();
		}
		strUrl = strUrl + "&p_passtype=" + p_passtype;
		strUrl = strUrl + "&p_passcnt=" + p_passcnt;
		strUrl = strUrl + "&p_apikey=" + p_apikey;

		return strUrl;
	}

	/*
	 * 마이노래방 메뉴 리스트
	 */
	public void setMyMenuUrl() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + p_op + ",MENU,MENU_MYLIST");
		this.p_op = "KP_1500";
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=KP_1500";
		m_strUrl = m_strUrl + "&p_m1=MENU";
		m_strUrl = m_strUrl + "&p_m2=MENU_MYLIST";
	}

	/*
	 * 노래방샵 메뉴 리스트
	 */
	public void setShopMenuUrl() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + p_op + ",MENU,MENU_SHOP");
		this.p_op = "KP_1500";
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=KP_1500";
		m_strUrl = m_strUrl + "&p_m1=MENU";
		m_strUrl = m_strUrl + "&p_m2=MENU_SHOP";
	}

	/*
	 * 메인 KP_0000
	 */
	public void setMainUrl(String op, String m1, String m2) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MAIN";
		m_strUrl = m_strUrl + "&p_m2=MENU";
		if (BuildConfig.DEBUG) Log.i("HAWAII", m_strUrl);
	}

	/*
	 * 반주곡 목록 KP_1000(노래부르기), KP_3010(마이노래방)
	 */
	public void setSongListUrl(String op, String m1, String m2, int page) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&page=" + String.valueOf(page);
	}

	/*
	 * 반주곡 재생 KP_1016
	 */
	public void setSongPlayUrl(String op, String m1, String m2, String id) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + id);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&song_id=" + id;
	}

	/*
	 * 반주곡 재생 시간 로그 KP_1012
	 */
	public void setSongPlayedTimeUrl(String op, String m1, String m2, String id, String type) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + id + ", " + type);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&play_id=" + id;
		m_strUrl = m_strUrl + "&type=" + type;
	}

	/*
	 * 애창곡 등록/삭제 KP_3010
	 */
	public void setFavorUrl(String op, String m1, String m2, String id) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + id);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&song_id=" + id;
	}

	/*
	 * 녹음곡 목록 KP_2100 MENU_LISTEN LISTEN_TIMELINE
	 */
	public void setListenListUrl(String op, String m1, String m2, int page) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + page);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&page=" + String.valueOf(page);
	}

	/*
	 * 공지사항or이용안내 목록 KP_0010
	 */
	public void setCustomerListUrl(String op, String m1, String m2, int page) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + page);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&page=" + String.valueOf(page);
	}

	/*
	 * 공지사항or이용안내 상세내용 KP_0011
	 */
	public void setCustomerListDetailUrl(String op, String m1, String m2, String id) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + id);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&id=" + id;
	}

	/*
	 * 검색 KP_0020 SEARCH_1 : 제목명 SEARCH_2 : 가수명 SEARCH_3 : 곡번호 SEARCH_4 : 색인
	 */
	public void setSearchListUrl(String op, String m1, String m2, String keyword, int page) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + page);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&search_word=" + keyword;
		m_strUrl = m_strUrl + "&page=" + String.valueOf(page);
	}

	/*
	 * 검색 KP_0020 SEARCH_1 : 제목명 SEARCH_2 : 가수명 SEARCH_3 : 곡번호 SEARCH_4 : 색인
	 */
	public void setNumberSearchUrl(String op, String m1, String m2, String num) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + num);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&search_word=" + num;
		m_strUrl = m_strUrl + "&page=1";
	}

	/*
	 * 녹음곡 재생 KP_2016 MENU_LISTEN LISTEN_TIMELINE LISTEN_WEEK LISTEN_TOP100
	 */
	public void setListenSongUrl(String op, String m1, String m2, String record_id, String uid) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + m1 + ", " + m2 + ", " + uid);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=" + m1;
		m_strUrl = m_strUrl + "&p_m2=" + m2;
		m_strUrl = m_strUrl + "&record_id=" + record_id;
		if (!uid.equals("")) {
			m_strUrl = m_strUrl + "&uid=" + p_mid;
		}
	}

	/*
	 * 이 녹음곡의 다른 사람 녹음곡 KP_2001
	 */

	public void setListenOtherUrl(String op, String id, int page) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + page);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MENU_LISTEN";
		m_strUrl = m_strUrl + "&p_m2=LISTEN_TIMELINE";
		m_strUrl = m_strUrl + "&song_id=" + id;
		m_strUrl = m_strUrl + "&page=" + String.valueOf(page);
	}

	/*
	 * 휴대폰 인증번호 요청 KP_9001
	 */
	public void setAuthNumberUrl(String op, String phoneno, String authno) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + phoneno + ", " + authno);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MAIN";
		m_strUrl = m_strUrl + "&p_m2=MENU";
		m_strUrl = m_strUrl + "&p_phoneno=" + phoneno;
		m_strUrl = m_strUrl + "&auth_num=" + authno;
	}

	/*
	 * 인증 상태 요청 KP_9000
	 */
	public void setCertifyStateUrl(String op) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MAIN";
		m_strUrl = m_strUrl + "&p_m2=MENU";
	}

	/*
	 * 휴대폰 인증번호 일치 요청 KP_9000
	 */
	public void setAuthNumberCorrectUrl(String op, String phoneno, String authno) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + phoneno + ", " + authno);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MAIN";
		m_strUrl = m_strUrl + "&p_m2=MENU";
		m_strUrl = m_strUrl + "&p_phoneno=" + phoneno;
		m_strUrl = m_strUrl + "&auth_num=" + authno;
	}

	/*
	 * 판매 이용권 정보 KP_4000 MENU_SHOP SHOP_TICKET
	 */
	public void setTicketSalesStateUrl(String op) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MENU_SHOP";
		m_strUrl = m_strUrl + "&p_m2=SHOP_TICKET";
	}

	/*
	 * 1년약정 구매 결과 KP_4001
	 * 월정액 구매 결과 KP_4001
	 * 1일이용권 구매 결과 KP_4001
	 */
	// ==================================================tamashii
	public void setTicketPurchaseCompleteUrl(String op, String RETURN_DATA) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + RETURN_DATA);
		this.p_op = op;
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=" + op;
		m_strUrl = m_strUrl + "&p_m1=MENU_SHOP";
		m_strUrl = m_strUrl + "&p_m2=SHOP_TICKET";
		//m_strUrl = m_strUrl + "&RETURN_DATA=" + Base64.encodeToString(RETURN_DATA.getBytes(), Base64.NO_WRAP);
		m_strUrl = m_strUrl + "&RETURN_DATA=" + RETURN_DATA;
	}

	///*
	// * 1일 이용권 구매 결과 KP_4001
	// */
	//public void setDayPurchaseCompleteUrl(String op, String result) {
	//	if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + op + ", " + result);
	//	this.p_op = op;
	//	m_strUrl = setKPBase();
	//	m_strUrl = m_strUrl + "&p_opcode=" + op;
	//	m_strUrl = m_strUrl + "&p_m1=MENU_SHOP";
	//	m_strUrl = m_strUrl + "&p_m2=SHOP_TICKET";
	//	m_strUrl = m_strUrl + "&RETURN_DATA=" + result;
	//}

	public void setEventApply(String id) {
		this.p_op = "KP_0012";
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + p_op + ",MENU_HELP,HELP_EVENT," + id);
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=KP_0012";
		m_strUrl = m_strUrl + "&p_m1=MENU_HELP";
		m_strUrl = m_strUrl + "&p_m2=HELP_EVENT";
		m_strUrl = m_strUrl + "&id=" + id;
	}

	public void setEventHP(String id, String hp) {
		this.p_op = "KP_0013";
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + p_op + ",MENU_HELP,HELP_EVENT," + id + ", " + hp);
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=KP_0013";
		m_strUrl = m_strUrl + "&p_m1=MENU_HELP";
		m_strUrl = m_strUrl + "&p_m2=HELP_EVENT";
		m_strUrl = m_strUrl + "&id=" + id;
		m_strUrl = m_strUrl + "&p_phoneno=" + hp;
	}

	public void setCouponRegistUrl(String cpn) {
		this.p_op = "KP_0014";
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[OP]" + p_op + ",MENU,MENU_SHOP," + cpn);
		m_strUrl = setKPBase();
		m_strUrl = m_strUrl + "&p_opcode=KP_0014";
		m_strUrl = m_strUrl + "&p_m1=MENU";
		m_strUrl = m_strUrl + "&p_m2=MENU_SHOP";
		m_strUrl = m_strUrl + "&cpn_id=" + cpn;
	}

	public void setRequestType(int request) {
		this.request = request;
	}

	///**
	// * <pre>
	// *   org.apache.http 대체
	// * </pre>
	// */
	//public void sendRequest() throws Exception {
	//	if (BuildConfig.DEBUG) Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[" + m_strUrl + "]");
	//
	//	this.result_code = "";
	//	this.result_message = "";
	//
	//	try {
	//		DefaultHttpClient client = new DefaultHttpClient();
	//
	//		// m_strUrl = URLEncoder.encode(m_strUrl, "UTF-8");
	//		// HttpPost post = new HttpPost(tempUrl);
	//		HttpPost post = new HttpPost(m_strUrl);
	//
	//		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "[" + m_strUrl + "]");
	//
	//		/*
	//		 * HttpParams param = client.getParams(); HttpConnectionParams.setConnectionTimeout(param, 5000); HttpConnectionParams.setSoTimeout(param, 5000);
	//		 */
	//		HttpResponse response = client.execute(post);
	//
	//		HttpEntity httpEntity = response.getEntity();
	//		en = httpEntity.getContent();
	//		//} catch (UnsupportedEncodingException e) {
	//		//	e.printStackTrace();
	//		//} catch (ClientProtocolException e) {
	//		//	e.printStackTrace();
	//		//} catch (IOException e) {
	//		//	e.printStackTrace();
	//	} catch (Exception e) {
	//		Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.p_op + "\n" + Log.getStackTraceString(e));
	//		sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
	//		e.printStackTrace();
	//		return;
	//	}
	//
	//	if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[" + m_strUrl + "]");
	//	try {
	//		BufferedReader bufReader = new BufferedReader(new InputStreamReader(en, "UTF-8"));
	//
	//		String line = null;
	//		String result = "";
	//		int iReadCount = 0;
	//
	//		while ((line = bufReader.readLine()) != null) {
	//			// if (BuildConfig.DEBUG) _LOG.i("JSON", line);
	//			if (iReadCount > 0) {
	//				result += "\r\n";
	//			}
	//
	//			result += line;
	//			iReadCount++;
	//		}
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			try {
	//				JSONObject json = new JSONObject(result);
	//				// if (json != null) {
	//				// if (BuildConfig.DEBUG) _LOG.i("JSON", json.toString(2));
	//				// }
	//			} catch (Exception e) {
	//
	//				e.printStackTrace();
	//			}
	//		}
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
	//			case REQUEST_MONTH_PURCHASE_COMPLETE:
	//			case REQUEST_DAY_PURCHASE_COMPLETE:
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
	//		Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[RECV]" + "[NG]" + this.p_op + "\n" + Log.getStackTraceString(e));
	//		sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
	//		e.printStackTrace();
	//		return;
	//	}
	//}
	//
	//private void parsePurchaseComplete(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName());
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(0);
	//			return;
	//		}
	//
	//		result_message_favor = info.getString("result_message");
	//	}
	//
	//	sendMessage(0);
	//
	//	if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName());
	//}
	//
	//private void parseMain(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseMain >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//		video_url_back = info.getString("video_url");
	//
	//		if (result_code.equals("00000")) {
	//			p_mid = info.getString("mid");
	//			is_cpn = info.getString("is_cpn");
	//			cpn_term = info.getString("cpn_term");
	//			url_kylogo = info.getString("url_kylogo");
	//		} else {
	//			sendMessage(COMPLETE_MAIN);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	JSONObject list1 = jArrList.getJSONObject(5); // 6. 부르기
	//	song_id = list1.getString("song_id");
	//	song_title = list1.getString("title");
	//	song_artist = list1.getString("artist");
	//
	//	JSONObject list2 = jArrList.getJSONObject(6); // 7. 듣기
	//	url_img = list2.getString("url_img");
	//	listen_id = list2.getString("record_id");
	//	listen_title = list2.getString("title");
	//	listen_nickname = list2.getString("nickname");
	//
	//	// JSONObject list5 = jArrList.getJSONObject(6);
	//	// // 8. 퀵메뉴1
	//	// subMainQuickBtn01.op = list5.getString("go_opcode");
	//	// subMainQuickBtn01.m1 = list5.getString("m1");
	//	// subMainQuickBtn01.m2 =
	//	// list5.getString("m2");
	//	// m_strMainQuickBtnOnUrl01 = list5.getString("url_img_on");
	//	// m_strMainQuickBtnOffUrl01 = list5.getString("url_img_off");
	//
	//	JSONObject list6 = jArrList.getJSONObject(8); // 9. 퀵메뉴2
	//	subMainQuickBtn02.go_opcode = list6.getString("go_opcode");
	//	subMainQuickBtn02.m1 = list6.getString("m1");
	//	subMainQuickBtn02.m2 = list6.getString("m2");
	//	m_strMainQuickBtnOnUrl02 = list6.getString("url_img_on");
	//	m_strMainQuickBtnOffUrl02 = list6.getString("url_img_off");
	//
	//	JSONObject list3 = jArrList.getJSONObject(9); // 10. 이벤트
	//	event_url_img = list3.getString("url_img");
	//	event_id = list3.getString("id");
	//
	//	JSONObject list4 = jArrList.getJSONObject(10); // 11. 공지사항
	//	notice_title = list4.getString("title");
	//	notice_id = list4.getString("id");
	//
	//	sendMessage(COMPLETE_MAIN);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseMain <");
	//}
	//
	//private void parseSongList(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseSongList >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_SONG_LIST);
	//			return;
	//		}
	//
	//		this.total_page = Integer.parseInt(info.getString("total_page"));
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		SongItem item = new SongItem();
	//
	//		item.song_id = getString(list, "song_id");
	//		item.title = getString(list, "title");
	//		item.artist = getString(list, "artist");
	//		item.mark_favorite = getString(list, "mark_favorite");
	//
	//		arraySongItem.add(item);
	//	}
	//
	//	sendMessage(COMPLETE_SONG_LIST);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseSongList <" + arraySongItem.size());
	//}
	//
	//private void parsePlaySong(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parsePlaySong >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		this.url_skym = getString(list, "url_skym");
	//		this.url_lyric = getString(list, "url_lyric");
	//		this.type = getString(list, "type");
	//		this.video_url = getString(list, "video_url");
	//	}
	//
	//	sendMessage(COMPLETE_SONG_PLAY);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parsePlaySong <");
	//}
	//
	//private void parseFavor(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseFavor >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_FAVOR);
	//			return;
	//		}
	//
	//		result_message_favor = info.getString("result_message");
	//	}
	//
	//	sendMessage(COMPLETE_FAVOR);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseFavor <");
	//}
	//
	//private void parseCustomerList(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCustomerList >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_CUSTOMER_LIST);
	//			return;
	//		}
	//
	//		this.total_page = Integer.parseInt(info.getString("total_page"));
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		CustomerItem item = new CustomerItem();
	//
	//		item.id = getString(list, "id");
	//		item.title = getString(list, "title");
	//		item.reg_date = getString(list, "reg_date");
	//
	//		if (request == REQUEST_EVENT_LIST) {
	//			item.term_date = getString(list, "term_date");
	//			item.e_stats = getString(list, "e_stats");
	//		}
	//
	//		arrayCustomerItem.add(item);
	//	}
	//
	//	sendMessage(COMPLETE_CUSTOMER_LIST);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCustomerList <");
	//}
	//
	//private void parseCustomerListDetail(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCustomerListDetail >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_CUSTOMER_LIST_DETAIL);
	//			return;
	//		}
	//
	//		m_strCustomerDetailTitle = info.getString("title");
	//		m_strCustomerDetailDate = info.getString("reg_date");
	//
	//		if (request == REQUEST_EVENT_LIST_DETAIL) {
	//			b_type = info.getString("b_type");
	//			term_date = info.getString("term_date");
	//			e_stats = info.getString("e_stats");
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		String temp = getString(list, "url_img");
	//		url_imgs.add(temp);
	//	}
	//
	//	sendMessage(COMPLETE_CUSTOMER_LIST_DETAIL);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCustomerListDetail <");
	//}
	//
	//private void parseSearchList(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseSearchList >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_SEARCH_LIST);
	//			return;
	//		}
	//
	//		this.total_page = Integer.parseInt(info.getString("total_page"));
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		SongItem item = new SongItem();
	//
	//		item.song_id = getString(list, "song_id");
	//		item.title = getString(list, "title");
	//		item.artist = getString(list, "artist");
	//		item.mark_favorite = getString(list, "mark_favorite");
	//
	//		arraySongItem.add(item);
	//	}
	//
	//	sendMessage(COMPLETE_SEARCH_LIST);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseSearchList <");
	//}
	//
	//private void parseListenList(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseListenList >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			switch (request) {
	//				case REQUEST_LISTEN_LIST:
	//					sendMessage(COMPLETE_LISTEN_LIST);
	//					break;
	//				case REQUEST_LISTEN_OTHER:
	//					sendMessage(COMPLETE_LISTEN_OTHER);
	//					break;
	//			}
	//			return;
	//		}
	//
	//		this.total_page = Integer.parseInt(info.getString("total_page"));
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		ListenItem item = new ListenItem();
	//
	//		item.url_profile = getString(list, "url_profile");
	//		item.record_id = getString(list, "record_id");
	//		item.title = getString(list, "title");
	//		item.artist = getString(list, "artist");
	//		item.nickname = getString(list, "nickname");
	//		item.heart = getString(list, "heart");
	//		item.hit = getString(list, "hit");
	//		item.reg_date = getString(list, "reg_date");
	//
	//		arrayListenItem.add(item);
	//	}
	//
	//	switch (request) {
	//		case REQUEST_LISTEN_LIST:
	//			sendMessage(COMPLETE_LISTEN_LIST);
	//			break;
	//		case REQUEST_LISTEN_OTHER:
	//			sendMessage(COMPLETE_LISTEN_OTHER);
	//			break;
	//	}
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseListenList <");
	//}
	//
	//private void parseListenOther(String response) {
	//	try {
	//		if (BuildConfig.DEBUG) Log.i(_toString(), "parseListenOther >");
	//
	//		JSONObject json = new JSONObject(response);
	//		JSONObject record = json.getJSONObject("record");
	//
	//		// Parse Info
	//		JSONArray jArrInfo = record.getJSONArray("info");
	//
	//		for (int i = 0; i < jArrInfo.length(); i++) {
	//			JSONObject info = jArrInfo.getJSONObject(i);
	//			// isyoon
	//			if (BuildConfig.DEBUG) {
	//				if (info != null) {
	//					Log.w(_toString(), "info - " + info.toString(2));
	//				}
	//			}
	//
	//			result_code = info.getString("result_code");
	//			result_message = info.getString("result_message");
	//
	//			if (!result_code.equals("00000")) {
	//				sendMessage(COMPLETE_LISTEN_LIST);
	//				return;
	//			}
	//
	//			this.total_page = Integer.parseInt(info.getString("total_page"));
	//		}
	//
	//		// Parse List
	//		JSONArray jArrList = record.getJSONArray("list");
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG && jArrList != null) {
	//			Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//		}
	//
	//		for (int i = 0; i < jArrList.length(); i++) {
	//			JSONObject list = jArrList.getJSONObject(i);
	//
	//			// isyoon
	//			if (BuildConfig.DEBUG) {
	//				if (list != null) {
	//					Log.d(_toString(), "list - " + list.toString(2));
	//				}
	//			}
	//
	//			ListenItem item = new ListenItem();
	//
	//			item.url_profile = getString(list, "url_profile");
	//			item.record_id = getString(list, "record_id");
	//			item.song_id = getString(list, "song_id");
	//			item.title = getString(list, "title");
	//			item.artist = getString(list, "artist");
	//			item.nickname = getString(list, "nickname");
	//			item.heart = getString(list, "heart");
	//			item.hit = getString(list, "hit");
	//			item.reg_date = getString(list, "reg_date");
	//
	//			arrayListenItem.add(item);
	//		}
	//
	//		sendMessage(COMPLETE_LISTEN_LIST);
	//
	//		if (BuildConfig.DEBUG) Log.i(_toString(), "parseListenOther <");
	//	} catch (JSONException e) {
	//		e.printStackTrace();
	//	}
	//}
	//
	//private void parseListenSong(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseListenSong >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			if (request == REQUEST_LISTEN_SONG) {
	//				sendMessage(COMPLETE_LISTEN_SONG);
	//			} else {
	//				sendMessage(COMPLETE_LISTEN_OTHER_SONG);
	//			}
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		url_record = getString(list, "url_record");
	//		listenItem.url_profile = getString(list, "url_profile");
	//		listenItem.record_id = getString(list, "record_id");
	//		listenItem.title = getString(list, "title");
	//		listenItem.artist = getString(list, "artist");
	//		listenItem.nickname = getString(list, "nickname");
	//		listenItem.heart = getString(list, "heart");
	//		listenItem.hit = getString(list, "hit");
	//		// listenItem.reg_date = getString(list, "reg_date");
	//		song_id = getString(list, "song_id");
	//	}
	//
	//	if (request == REQUEST_LISTEN_SONG) {
	//		sendMessage(COMPLETE_LISTEN_SONG);
	//	} else {
	//		sendMessage(COMPLETE_LISTEN_OTHER_SONG);
	//	}
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseListenSong <");
	//}
	//
	//private void parseNumberSearch(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseNumberSearch >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_NUMBER_SEARCH);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		m_strNumberSearchResult = getString(list, "title");
	//		m_strNumberSearchResult = m_strNumberSearchResult + "-" + getString(list, "artist");
	//	}
	//
	//	sendMessage(COMPLETE_NUMBER_SEARCH);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseNumberSearch <");
	//}
	//
	//private void parseSongPlayedTime(String response) {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseSongPlayedTime >");
	//
	//	sendMessage(COMPLETE_SONG_PLAYED_TIME);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseSongPlayedTime <");
	//}
	//
	//private void parseAuthNumber(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseAuthNumber >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000") && !result_code.equals("00901")) {
	//			sendMessage(COMPLETE_AUTH_NUMBER);
	//			return;
	//		}
	//
	//		try {
	//			auth_num = info.getString("auth_num");
	//		} catch (JSONException e) {
	//			auth_num = "";
	//			sendMessage(COMPLETE_AUTH_NUMBER);
	//			return;
	//		}
	//	}
	//
	//	sendMessage(COMPLETE_AUTH_NUMBER);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseAuthNumber <");
	//}
	//
	//private void parseCertifyState(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCertifyState >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_CERTIFY_STATE);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		auth_date = getString(list, "auth_date");
	//		auth_mark_auth_idx = getString(list, "mark_auth_idx");
	//		auth_modify_idx = getString(list, "modify_idx");
	//		auth_btn_type = getString(list, "btn_type");
	//		auth_url_profile = getString(list, "url_profile");
	//		auth_phoneno = getString(list, "phoneno");
	//	}
	//
	//	sendMessage(COMPLETE_CERTIFY_STATE);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCertifyState <");
	//}
	//
	//private void parseAuthNumberCorrect(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseAuthNumberCorrect >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_AUTH_NUMBER_CORRECT);
	//			return;
	//		}
	//	}
	//
	//	sendMessage(COMPLETE_AUTH_NUMBER_CORRECT);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseAuthNumberCorrect <");
	//}
	//
	//private void parseMyRecordList(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseMyRecordList >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		/*
	//		 * if (!m_strResultCode.equals("00000")) { sendMessage(COMPLETE_MY_RECORD_LIST); return; }
	//		 */
	//
	//		my_auth_url_profile = info.getString("url_profile");
	//		my_auth_nickname = info.getString("my_nickname");
	//		my_auth_hit = info.getString("my_hit");
	//		my_auth_heart = info.getString("my_heart");
	//		auth_date = info.getString("auth_date");
	//		my_auth_mark_auth_idx = info.getString("mark_auth_idx");
	//		my_auth_modify_idx = info.getString("modify_idx");
	//		my_auth_btn_type = info.getString("btn_type");
	//		auth_phoneno = info.getString("phoneno");
	//
	//		this.total_page = Integer.parseInt(info.getString("total_page"));
	//	}
	//
	//	if (total_page == 0) {
	//		sendMessage(COMPLETE_MY_RECORD_LIST);
	//		return;
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		ListenItem item = new ListenItem();
	//
	//		item.record_id = getString(list, "record_id");
	//		item.title = getString(list, "title");
	//		item.artist = getString(list, "artist");
	//		item.heart = getString(list, "heart");
	//		item.reg_date = getString(list, "reg_date");
	//
	//		arrayListenItem.add(item);
	//	}
	//
	//	sendMessage(COMPLETE_MY_RECORD_LIST);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseMyRecordList <");
	//}
	//
	//private void parseMySubMenu(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseMySubMenu >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_MY_SUB_MENU);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		SubMenuItem item = new SubMenuItem();
	//
	//		item.go_opcode = getString(list, "go_opcode");
	//		item.m1 = getString(list, "m1");
	//		item.m2 = getString(list, "m2");
	//		item.name = getString(list, "menu_name");
	//
	//		arraySubMenuItem.add(item);
	//	}
	//
	//	sendMessage(COMPLETE_MY_SUB_MENU);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseMySubMenu <");
	//}
	//
	//private void parseShopSubMenu(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseShopSubMenu >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_SHOP_SUB_MENU);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		SubMenuItem item = new SubMenuItem();
	//
	//		item.go_opcode = getString(list, "go_opcode");
	//		item.m1 = getString(list, "m1");
	//		item.m2 = getString(list, "m2");
	//		item.name = getString(list, "menu_name");
	//
	//		arraySubMenuItem.add(item);
	//	}
	//
	//	sendMessage(COMPLETE_SHOP_SUB_MENU);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseShopSubMenu <");
	//}
	//
	//private String getString(JSONObject json, String name) {
	//	try {
	//		return json.getString(name);
	//	} catch (JSONException e) {
	//		e.printStackTrace();
	//		return null;
	//	}
	//}
	//
	//private Boolean getBoolean(JSONObject json, String name) {
	//	try {
	//		return Boolean.parseBoolean(json.getString(name));
	//	} catch (JSONException e) {
	//		e.printStackTrace();
	//		return false;
	//	}
	//}
	//
	//private void parseTicketSalesState(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseTicketSalesState >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_TICKET_SALES_STATE);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	if (jArrList.length() > 1) {
	//
	//		for (int i = 0; i < jArrList.length(); i++) {
	//			JSONObject list = jArrList.getJSONObject(i);
	//
	//			// isyoon
	//			if (BuildConfig.DEBUG) {
	//				if (list != null) {
	//					Log.d(_toString(), "list - " + list.toString(2));
	//				}
	//			}
	//
	//			TicketItem item = new TicketItem();
	//
	//			item.p_passtype = "";
	//			item.p_passtype = getString(list, "p_passtype");
	//			item.id_product = getString(list, "id_product");
	//			item.product_name = getString(list, "product_name");
	//			item.product_type = PRODUCT_TYPE.get(TextUtil.trim(getString(list, "product_type")));
	//			item.product_term = (TextUtil.isEmpty(getString(list, "product_term")) ? 0 : Integer.parseInt(getString(list, "product_term")));
	//			item.product_desc = getString(list, "product_desc");
	//			item.product_info = getString(list, "product_info");
	//			item.product_note_01 = getString(list, "product_note_01");
	//			item.product_note_02 = getString(list, "product_note_02");
	//			item.product_image = getString(list, "product_image");
	//			item.service_item_id = getString(list, "service_item_id");
	//			item.price = (TextUtil.isEmpty(getString(list, "price")) ? 0 : Integer.parseInt(getString(list, "price")));
	//			item.real_price = (TextUtil.isEmpty(getString(list, "real_price")) ? 0 : Integer.parseInt(getString(list, "real_price")));
	//			item.start_date = getString(list, "start_date");
	//			item.end_date = getString(list, "end_date");
	//
	//			///**
	//			// * 작업전이야 - 시발
	//			// */
	//			////if (TextUtil.isEmpty(item.p_passtype))
	//			//{
	//			//	if (("1일이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
	//			//		item.p_passtype = PASS_TYPE_DAY;
	//			//		if (null != item) {
	//			//			if (item.product_type == PRODUCT_TYPE.NONE) {
	//			//				item.product_type = PRODUCT_TYPE.PPV;
	//			//			}
	//			//			if (item.product_term == 0) {
	//			//				item.product_term = 1;
	//			//			}
	//			//		}
	//			//	} else if (("월이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
	//			//		item.p_passtype = PASS_TYPE_MONTH;
	//			//		if (null != item) {
	//			//			if (item.product_type == PRODUCT_TYPE.NONE) {
	//			//				item.product_type = PRODUCT_TYPE.PPM;
	//			//			}
	//			//			if (item.product_term == 0) {
	//			//				item.product_term = 0;
	//			//			}
	//			//		}
	//			//	} else if (("1년약정").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
	//			//		item.p_passtype = PASS_TYPE_YEAR;
	//			//		if (null != item) {
	//			//			if (item.product_type == PRODUCT_TYPE.NONE) {
	//			//				item.product_type = PRODUCT_TYPE.PPM;
	//			//			}
	//			//			if (item.product_term == 0) {
	//			//				item.product_term = 365;
	//			//			}
	//			//		}
	//			//	} else if (("무료쿠폰등록").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
	//			//		item.p_passtype = PASS_TYPE_COUPON;
	//			//		if (null != item) {
	//			//			if (item.product_type == PRODUCT_TYPE.NONE) {
	//			//				item.product_type = PRODUCT_TYPE.CPN;
	//			//			}
	//			//			if (item.product_term == 0) {
	//			//				item.product_term = 0;
	//			//			}
	//			//		}
	//			//	} else {
	//			//		item.p_passtype = PASS_TYPE_NONE;
	//			//	}
	//			//}
	//			///**
	//			// * 작업전이야 - 종발
	//			// */
	//
	//			if (!TextUtil.isEmpty(item.p_passtype)) {
	//				item.p_passtype = item.p_passtype.toUpperCase();
	//			}
	//
	//			String key = item.p_passtype;
	//			putTicketItems(key, item);
	//
	//			//if (BuildConfig.DEBUG) Log.wtf("[VASS]" + _toString(), "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + items.get(key).product_name);
	//		}
	//
	//	}
	//
	//	///**
	//	// * 테스트야 - 시발
	//	// */
	//	//TicketItem ticketItemDay = items.get(PASS_TYPE_DAY);
	//	//TicketItem ticketItemMonth = items.get(PASS_TYPE_MONTH);
	//	//TicketItem ticketItemYear = items.get(PASS_TYPE_YEAR);
	//	//TicketItem ticketItemCoupon = items.get(PASS_TYPE_COUPON);
	//	///**
	//	// * 이용권목록 - test
	//	// */
	//	//items.clear();
	//	///**
	//	// * 1일이용권 - test
	//	// */
	//	//try {
	//	//	if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemDay.p_passtype + "[ITEM]" + ticketItemDay);
	//	//	putTicketItems(ticketItemDay.p_passtype, ticketItemDay);
	//	//} catch (Exception e) {
	//	//	e.printStackTrace();
	//	//}
	//	///**
	//	// * 월이용권 - test
	//	// */
	//	//try {
	//	//	if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemMonth.p_passtype + "[ITEM]" + ticketItemMonth);
	//	//	putTicketItems(ticketItemMonth.p_passtype, ticketItemMonth);
	//	//} catch (Exception e) {
	//	//	e.printStackTrace();
	//	//}
	//	///**
	//	// * 1년약정 - test
	//	// */
	//	//ticketItemYear = new TicketItem();
	//	//ticketItemYear.p_passtype = PASS_TYPE_YEAR;
	//	//ticketItemYear.id_product = ticketItemMonth.id_product;
	//	//ticketItemYear.product_name = PASS_TYPE_YEAR;
	//	//ticketItemYear.product_desc = ticketItemMonth.product_desc;
	//	//ticketItemYear.service_item_id = ticketItemMonth.service_item_id;
	//	//ticketItemYear.price = ticketItemMonth.price;
	//	//ticketItemYear.real_price = ticketItemMonth.real_price;
	//	//ticketItemYear.start_date = ticketItemMonth.start_date;
	//	//ticketItemYear.end_date = ticketItemMonth.end_date;
	//	//ticketItemYear.product_type = PRODUCT_TYPE.PPM;
	//	//ticketItemYear.product_term = 365;
	//	//try {
	//	//	if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemYear.p_passtype + "[ITEM]" + ticketItemYear);
	//	//	putTicketItems(ticketItemYear.p_passtype, ticketItemYear);
	//	//} catch (Exception e) {
	//	//	e.printStackTrace();
	//	//}
	//	///**
	//	// * 무료구폰등록 - test
	//	// */
	//	//try {
	//	//	if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemCoupon.p_passtype + "[ITEM]" + ticketItemCoupon);
	//	//	putTicketItems(ticketItemCoupon.p_passtype, ticketItemCoupon);
	//	//} catch (Exception e) {
	//	//	e.printStackTrace();
	//	//}
	//	///**
	//	// * 테스트야 - 종발
	//	// */
	//
	//	for (String key : items.keySet()) {
	//		TicketItem item = items.get(key);
	//		Log.d("[VASS]" + _toString(), "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());
	//	}
	//
	//	if (items.size() == 0) {
	//		Log.wtf(_toString(), "[RECV]" + "[NG]" + items);
	//		result_code = "XXXXX";
	//		result_message = "상품없쎠!!!!상품없쎠!!!!상품없쎠!!!!" + "(" + items.size() + ")";
	//	}
	//
	//	sendMessage(COMPLETE_TICKET_SALES_STATE);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseTicketSalesState <");
	//}
	//
	//private void parseEventApply(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseEventApply >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_EVENT_APPLY);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		event_popup_msg1 = getString(list, "msg1");
	//		event_popup_msg2 = getString(list, "msg2");
	//		event_popup_msg3 = getString(list, "msg3");
	//	}
	//
	//	sendMessage(COMPLETE_EVENT_APPLY);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseEventApply <");
	//}
	//
	//private void parseEventHP(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseEventHP >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//	}
	//
	//	sendMessage(COMPLETE_EVENT_HP);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseEventHP <");
	//}
	//
	//private void parseCouponRegist(String response) throws Exception {
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCouponRegist >");
	//
	//	JSONObject json = new JSONObject(response);
	//	JSONObject record = json.getJSONObject("record");
	//
	//	// Parse Info
	//	JSONArray jArrInfo = record.getJSONArray("info");
	//
	//	for (int i = 0; i < jArrInfo.length(); i++) {
	//		JSONObject info = jArrInfo.getJSONObject(i);
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (info != null) {
	//				Log.w(_toString(), "info - " + info.toString(2));
	//			}
	//		}
	//
	//		result_code = info.getString("result_code");
	//		result_message = info.getString("result_message");
	//
	//		if (!result_code.equals("00000")) {
	//			sendMessage(COMPLETE_COUPON_REGIST);
	//			return;
	//		}
	//	}
	//
	//	// Parse List
	//	JSONArray jArrList = record.getJSONArray("list");
	//
	//	// isyoon
	//	if (BuildConfig.DEBUG && jArrList != null) {
	//		Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + jArrList.length());
	//	}
	//
	//	for (int i = 0; i < jArrList.length(); i++) {
	//		JSONObject list = jArrList.getJSONObject(i);
	//
	//		// isyoon
	//		if (BuildConfig.DEBUG) {
	//			if (list != null) {
	//				Log.d(_toString(), "list - " + list.toString(2));
	//			}
	//		}
	//
	//		cpn_term = getString(list, "cpn_term");
	//	}
	//
	//	sendMessage(COMPLETE_COUPON_REGIST);
	//
	//	if (BuildConfig.DEBUG) Log.i(_toString(), "parseCouponRegist <");
	//}
}