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
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	Karaoke.TV
 * filename	:	KPRequest3.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api
 *    |_ KPRequest3.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.api;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.data.CustomerItem;
import kr.kymedia.kykaraoke.tv.data.ListenItem;
import kr.kymedia.kykaraoke.tv.data.SongItem;
import kr.kymedia.kykaraoke.tv.data.SubMenuItem;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 * 네트워크상태확인
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 9. 25.
 */
class KPRequest3 extends KPRequest2 {
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

	Context context;

	public KPRequest3(Context context, Handler handler) {
		super(handler);
		this.context = context;
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	@Override
	public void run() {
		super.run();
		try {
			send();
		} catch (Exception e) {
		}
	}

	@Override
	public void interrupt() {

		try {
			super.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.handler = null;
	}

	protected void send() throws Exception {
		Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[" + REQUEST_KP.get(request) + "]");
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[" + m_strUrl + "]");

		this.result_code = "";
		this.result_message = "";

		InputStream is = null;

		try {
			URL url = new URL(m_strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_REQUEST_READ);
			conn.setConnectTimeout(TIMEOUT_REQUEST_CONNECT);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.wtf(_toString(), "[SEND]" + "[OP]" + this.p_op + "[CODE]" + response);
			is = conn.getInputStream();
		} catch (Exception e) {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.p_op + "\n" + Log.getStackTraceString(e));
			sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
			e.printStackTrace();
			throw (e);
		}

		//if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[" + m_strUrl + "]");

		try {
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line;
			String result = "";
			int iReadCount = 0;

			while ((line = bufReader.readLine()) != null) {
				// if (IKaraokeTV.DEBUG) _LOG.i("JSON", line);
				if (iReadCount > 0) {
					result += "\r\n";
				}

				result += line;
				iReadCount++;
			}

			JSONObject response = new JSONObject(result);
			if (IKaraokeTV.DEBUG) Log.d(_toString(), "[JSON]" + response.toString(2));

			JSONObject record = response.getJSONObject("record");

			JSONArray infos;
			if (record.has("info")) {
				infos = record.getJSONArray("info");
			} else {
				result_code = "55555";
				result_message = "[NO][INFO]";
				Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.p_op + "[" + REQUEST_KP.get(request) + "]" + result_code + ":" + result_message);
				sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
				return;
			}

			JSONObject info;
			if (null != infos) {
				info = infos.getJSONObject(0);
			} else {
				result_code = "66666";
				result_message = "[NO][INFO]";
				Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.p_op + "[" + REQUEST_KP.get(request) + "]" + result_code + ":" + result_message);
				sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
				return;
			}

			if (null != info) {
				if (IKaraokeTV.DEBUG) Log.w(_toString(), "info - " + info.toString(2));
			} else {
				result_code = "77777";
				result_message = "[NO][INFO]";
				Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[SEND]" + "[NG]" + this.p_op + "[" + REQUEST_KP.get(request) + "]" + result_code + ":" + result_message);
				sendMessage(COMPLETE_ERROR_REQUEST_NOT_RESPONSE);
				return;
			}

			result_code = info.getString("result_code");
			result_message = info.getString("result_message");

			Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[" + REQUEST_KP.get(request) + "]" + result_code + ":" + result_message);

			/**
			 * 없을수도.있지롱
			 */
			JSONArray lists = null;
			if (record.has("list")) {
				lists = record.getJSONArray("list");
			} else {
				Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[" + REQUEST_KP.get(request) + "]" + "[NO][LIST]");
			}

			if (IKaraokeTV.DEBUG && lists != null) {
				Log.wtf(_toString(), "[RECV]" + "[OP]" + this.p_op + "[COUNT]" + lists.length());
			}

			switch (request) {
				case REQUEST_MAIN:
					parseMain(info, lists);
					break;
				case REQUEST_MY_SUB_MENU:
					parseMySubMenu(info, lists);
					break;
				case REQUEST_SHOP_SUB_MENU:
					parseShopSubMenu(info, lists);
					break;
				case REQUEST_SONG_LIST:
					parseSongList(info, lists);
					break;
				case REQUEST_FAVOR:
					parseFavor(info, lists);
					break;
				case REQUEST_CUSTOMER_LIST:
				case REQUEST_EVENT_LIST:
					parseCustomerList(info, lists);
					break;
				case REQUEST_CUSTOMER_LIST_DETAIL:
				case REQUEST_EVENT_LIST_DETAIL:
					parseCustomerListDetail(info, lists);
					break;
				case REQUEST_SEARCH_LIST:
					parseSearchList(info, lists);
					break;
				case REQUEST_SONG_PLAY:
					parsePlaySong(info, lists);
					break;
				case REQUEST_LISTEN_LIST:
					parseListenList(info, lists);
					break;
				case REQUEST_LISTEN_SONG:
				case REQUEST_LISTEN_SONG_OTHER:
					parseListenSong(info, lists);
					break;
				case REQUEST_LISTEN_OTHER:
					parseListenList(info, lists);
					break;
				case REQUEST_NUMBER_SEARCH:
					parseNumberSearch(info, lists);
					break;
				case REQUEST_SONG_PLAYED_TIME:
				case REQUEST_LISTEN_PLAYED_TIME:
					parseSongPlayedTime(info, lists);
					break;
				case REQUEST_AUTH_NUMBER:
					parseAuthNumber(info, lists);
					break;
				case REQUEST_CERTIFY_STATE:
					parseCertifyState(info, lists);
					break;
				case REQUEST_AUTH_NUMBER_CORRECT:
					parseAuthNumberCorrect(info, lists);
					break;
				case REQUEST_MY_RECORD_LIST:
					parseMyRecordList(info, lists);
					break;
				case REQUEST_TICKET_SALES_STATE:
					parseTicketSalesState(info, lists);
					break;
				case REQUEST_TICKET_PURCHASE_COMPLETE:
					parsePurchaseComplete(info, lists);
					break;
				case REQUEST_EVENT_APPLY:
					parseEventApply(info, lists);
					break;
				case REQUEST_EVENT_HP:
					parseEventHP(info, lists);
					break;
				case REQUEST_COUPON_REGIST:
					parseCouponRegist(info, lists);
					break;
			}
		} catch (Exception e) {
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
			if (is != null) {
				is.close();
			}
			Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[RECV]" + "[NG]" + this.p_op + "\n" + Log.getStackTraceString(e));
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
	}

	private void parsePurchaseComplete(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i("[VASS]" + _toString(), getMethodName());

		if (!result_code.equals("00000")) {
			sendMessage(0);
			return;
		}

		result_message_favor = info.getString("result_message");

		sendMessage(0);

		if (IKaraokeTV.DEBUG) Log.i("[VASS]" + _toString(), getMethodName());
	}

	private void parseMain(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseMain >");

		video_url_back = info.getString("video_url");

		if (result_code.equals("00000")) {
			p_mid = info.getString("mid");
			is_cpn = info.getString("is_cpn");
			cpn_term = info.getString("cpn_term");
			url_kylogo = info.getString("url_kylogo");
		} else {
			sendMessage(COMPLETE_MAIN);
			return;
		}

		JSONObject list1 = lists.getJSONObject(5); // 6. 부르기
		song_id = list1.getString("song_id");
		song_title = list1.getString("title");
		song_artist = list1.getString("artist");

		JSONObject list2 = lists.getJSONObject(6); // 7. 듣기
		url_img = list2.getString("url_img");
		listen_id = list2.getString("record_id");
		listen_title = list2.getString("title");
		listen_nickname = list2.getString("nickname");

		// JSONObject list5 = lists.getJSONObject(6);
		// // 8. 퀵메뉴1
		// subMainQuickBtn01.op = list5.getString("go_opcode");
		// subMainQuickBtn01.m1 = list5.getString("m1");
		// subMainQuickBtn01.m2 =
		// list5.getString("m2");
		// m_strMainQuickBtnOnUrl01 = list5.getString("url_img_on");
		// m_strMainQuickBtnOffUrl01 = list5.getString("url_img_off");

		JSONObject list6 = lists.getJSONObject(8); // 9. 퀵메뉴2
		subMainQuickBtn02.go_opcode = list6.getString("go_opcode");
		subMainQuickBtn02.m1 = list6.getString("m1");
		subMainQuickBtn02.m2 = list6.getString("m2");
		m_strMainQuickBtnOnUrl02 = list6.getString("url_img_on");
		m_strMainQuickBtnOffUrl02 = list6.getString("url_img_off");

		JSONObject list3 = lists.getJSONObject(9); // 10. 이벤트
		event_url_img = list3.getString("url_img");
		event_id = list3.getString("id");

		JSONObject list4 = lists.getJSONObject(10); // 11. 공지사항
		notice_title = list4.getString("title");
		notice_id = list4.getString("id");

		sendMessage(COMPLETE_MAIN);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseMain <");
	}

	private void parseSongList(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseSongList >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_SONG_LIST);
			return;
		}

		this.total_page = Integer.parseInt(info.getString("total_page"));

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			SongItem item = new SongItem();

			item.song_id = getString(list, "song_id");
			item.title = getString(list, "title");
			item.artist = getString(list, "artist");
			item.mark_favorite = getString(list, "mark_favorite");

			arraySongItem.add(item);
		}

		sendMessage(COMPLETE_SONG_LIST);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseSongList <" + arraySongItem.size());
	}

	private void parsePlaySong(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parsePlaySong >");

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			this.url_skym = getString(list, "url_skym");
			this.url_lyric = getString(list, "url_lyric");
			this.type = getString(list, "type");
			this.video_url = getString(list, "video_url");
		}

		sendMessage(COMPLETE_SONG_PLAY);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parsePlaySong <");
	}

	private void parseFavor(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseFavor >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_FAVOR);
			return;
		}

		result_message_favor = info.getString("result_message");

		sendMessage(COMPLETE_FAVOR);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseFavor <");
	}

	private void parseCustomerList(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCustomerList >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_CUSTOMER_LIST);
			return;
		}

		this.total_page = Integer.parseInt(info.getString("total_page"));

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			CustomerItem item = new CustomerItem();

			item.id = getString(list, "id");
			item.title = getString(list, "title");
			item.reg_date = getString(list, "reg_date");

			if (request == REQUEST_EVENT_LIST) {
				item.term_date = getString(list, "term_date");
				item.e_stats = getString(list, "e_stats");
			}

			arrayCustomerItem.add(item);
		}

		sendMessage(COMPLETE_CUSTOMER_LIST);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCustomerList <");
	}

	private void parseCustomerListDetail(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCustomerListDetail >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_CUSTOMER_LIST_DETAIL);
			return;
		}

		m_strCustomerDetailTitle = info.getString("title");
		m_strCustomerDetailDate = info.getString("reg_date");

		if (request == REQUEST_EVENT_LIST_DETAIL) {
			b_type = info.getString("b_type");
			term_date = info.getString("term_date");
			e_stats = info.getString("e_stats");
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			String temp = getString(list, "url_img");
			url_imgs.add(temp);
		}

		sendMessage(COMPLETE_CUSTOMER_LIST_DETAIL);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCustomerListDetail <");
	}

	private void parseSearchList(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseSearchList >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_SEARCH_LIST);
			return;
		}

		this.total_page = Integer.parseInt(info.getString("total_page"));

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			SongItem item = new SongItem();

			item.song_id = getString(list, "song_id");
			item.title = getString(list, "title");
			item.artist = getString(list, "artist");
			item.mark_favorite = getString(list, "mark_favorite");

			arraySongItem.add(item);
		}

		sendMessage(COMPLETE_SEARCH_LIST);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseSearchList <");
	}

	private void parseListenList(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseListenList >");

		if (!result_code.equals("00000")) {
			switch (request) {
				case REQUEST_LISTEN_LIST:
					sendMessage(COMPLETE_LISTEN_LIST);
					break;
				case REQUEST_LISTEN_OTHER:
					sendMessage(COMPLETE_LISTEN_OTHER);
					break;
			}
			return;
		}

		this.total_page = Integer.parseInt(info.getString("total_page"));

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			ListenItem item = new ListenItem();

			item.url_profile = getString(list, "url_profile");
			item.record_id = getString(list, "record_id");
			item.title = getString(list, "title");
			item.artist = getString(list, "artist");
			item.nickname = getString(list, "nickname");
			item.heart = getString(list, "heart");
			item.hit = getString(list, "hit");
			item.reg_date = getString(list, "reg_date");

			arrayListenItem.add(item);
		}

		switch (request) {
			case REQUEST_LISTEN_LIST:
				sendMessage(COMPLETE_LISTEN_LIST);
				break;
			case REQUEST_LISTEN_OTHER:
				sendMessage(COMPLETE_LISTEN_OTHER);
				break;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseListenList <");
	}

	private void parseListenOther(JSONObject info, JSONArray lists) {
		try {
			if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseListenOther >");

			if (!result_code.equals("00000")) {
				sendMessage(COMPLETE_LISTEN_LIST);
				return;
			}

			this.total_page = Integer.parseInt(info.getString("total_page"));

			for (int i = 0; i < lists.length(); i++) {
				JSONObject list = lists.getJSONObject(i);

				// isyoon
				if (IKaraokeTV.DEBUG) {
					if (list != null) {
						Log.d(_toString(), "list - " + list.toString(2));
					}
				}

				ListenItem item = new ListenItem();

				item.url_profile = getString(list, "url_profile");
				item.record_id = getString(list, "record_id");
				item.song_id = getString(list, "song_id");
				item.title = getString(list, "title");
				item.artist = getString(list, "artist");
				item.nickname = getString(list, "nickname");
				item.heart = getString(list, "heart");
				item.hit = getString(list, "hit");
				item.reg_date = getString(list, "reg_date");

				arrayListenItem.add(item);
			}

			sendMessage(COMPLETE_LISTEN_LIST);

			if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseListenOther <");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseListenSong(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseListenSong >");

		if (!result_code.equals("00000")) {
			if (request == REQUEST_LISTEN_SONG) {
				sendMessage(COMPLETE_LISTEN_SONG);
			} else {
				sendMessage(COMPLETE_LISTEN_OTHER_SONG);
			}
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			url_record = getString(list, "url_record");
			listenItem.url_profile = getString(list, "url_profile");
			listenItem.record_id = getString(list, "record_id");
			listenItem.title = getString(list, "title");
			listenItem.artist = getString(list, "artist");
			listenItem.nickname = getString(list, "nickname");
			listenItem.heart = getString(list, "heart");
			listenItem.hit = getString(list, "hit");
			// listenItem.reg_date = getString(list, "reg_date");
			song_id = getString(list, "song_id");
		}

		if (request == REQUEST_LISTEN_SONG) {
			sendMessage(COMPLETE_LISTEN_SONG);
		} else {
			sendMessage(COMPLETE_LISTEN_OTHER_SONG);
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseListenSong <");
	}

	private void parseNumberSearch(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseNumberSearch >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_NUMBER_SEARCH);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			m_strNumberSearchResult = getString(list, "title");
			m_strNumberSearchResult = m_strNumberSearchResult + "-" + getString(list, "artist");
		}

		sendMessage(COMPLETE_NUMBER_SEARCH);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseNumberSearch <");
	}

	private void parseSongPlayedTime(JSONObject info, JSONArray lists) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseSongPlayedTime >");

		sendMessage(COMPLETE_SONG_PLAYED_TIME);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseSongPlayedTime <");
	}

	private void parseAuthNumber(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseAuthNumber >");

		if (!result_code.equals("00000") && !result_code.equals("00901")) {
			sendMessage(COMPLETE_AUTH_NUMBER);
			return;
		}

		try {
			auth_num = info.getString("auth_num");
		} catch (JSONException e) {
			auth_num = "";
			sendMessage(COMPLETE_AUTH_NUMBER);
			return;
		}

		sendMessage(COMPLETE_AUTH_NUMBER);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseAuthNumber <");
	}

	private void parseCertifyState(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCertifyState >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_CERTIFY_STATE);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			auth_date = getString(list, "auth_date");
			auth_mark_auth_idx = getString(list, "mark_auth_idx");
			auth_modify_idx = getString(list, "modify_idx");
			auth_btn_type = getString(list, "btn_type");
			auth_url_profile = getString(list, "url_profile");
			auth_phoneno = getString(list, "phoneno");
		}

		sendMessage(COMPLETE_CERTIFY_STATE);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCertifyState <");
	}

	private void parseAuthNumberCorrect(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseAuthNumberCorrect >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_AUTH_NUMBER_CORRECT);
			return;
		}

		sendMessage(COMPLETE_AUTH_NUMBER_CORRECT);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseAuthNumberCorrect <");
	}

	private void parseMyRecordList(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseMyRecordList >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_MY_RECORD_LIST);
			return;
		}

			/*
			 * if (!m_strResultCode.equals("00000")) { sendMessage(COMPLETE_MY_RECORD_LIST); return; }
			 */

		my_auth_url_profile = info.getString("url_profile");
		my_auth_nickname = info.getString("my_nickname");
		my_auth_hit = info.getString("my_hit");
		my_auth_heart = info.getString("my_heart");
		auth_date = info.getString("auth_date");
		my_auth_mark_auth_idx = info.getString("mark_auth_idx");
		my_auth_modify_idx = info.getString("modify_idx");
		my_auth_btn_type = info.getString("btn_type");
		auth_phoneno = info.getString("phoneno");

		this.total_page = Integer.parseInt(info.getString("total_page"));

		if (total_page == 0) {
			sendMessage(COMPLETE_MY_RECORD_LIST);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			ListenItem item = new ListenItem();

			item.record_id = getString(list, "record_id");
			item.title = getString(list, "title");
			item.artist = getString(list, "artist");
			item.heart = getString(list, "heart");
			item.reg_date = getString(list, "reg_date");

			arrayListenItem.add(item);
		}

		sendMessage(COMPLETE_MY_RECORD_LIST);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseMyRecordList <");
	}

	private void parseMySubMenu(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseMySubMenu >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_MY_SUB_MENU);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			SubMenuItem item = new SubMenuItem();

			item.go_opcode = getString(list, "go_opcode");
			item.m1 = getString(list, "m1");
			item.m2 = getString(list, "m2");
			item.name = getString(list, "menu_name");

			arraySubMenuItem.add(item);
		}

		sendMessage(COMPLETE_MY_SUB_MENU);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseMySubMenu <");
	}

	private void parseShopSubMenu(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseShopSubMenu >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_SHOP_SUB_MENU);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			SubMenuItem item = new SubMenuItem();

			item.go_opcode = getString(list, "go_opcode");
			item.m1 = getString(list, "m1");
			item.m2 = getString(list, "m2");
			item.name = getString(list, "menu_name");

			arraySubMenuItem.add(item);
		}

		sendMessage(COMPLETE_SHOP_SUB_MENU);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseShopSubMenu <");
	}

	private String getString(JSONObject json, String name) {
		try {
			return json.getString(name);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Boolean getBoolean(JSONObject json, String name) {
		try {
			return Boolean.parseBoolean(json.getString(name));
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void parseTicketSalesState(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseTicketSalesState >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_TICKET_SALES_STATE);
			return;
		}

		if (lists.length() > 1) {

			for (int i = 0; i < lists.length(); i++) {
				JSONObject list = lists.getJSONObject(i);

				// isyoon
				if (IKaraokeTV.DEBUG) {
					if (list != null) {
						Log.d(_toString(), "list - " + list.toString(2));
					}
				}

				TicketItem item = new TicketItem();

				item.p_passtype = "";
				item.p_passtype = getString(list, "p_passtype");
				item.id_product = getString(list, "id_product");
				item.product_name = getString(list, "product_name");
				item.product_type = PRODUCT_TYPE.get(TextUtil.trim(getString(list, "product_type")));
				item.product_term = (TextUtil.isEmpty(getString(list, "product_term")) ? 0 : Integer.parseInt(getString(list, "product_term")));
				item.product_desc = getString(list, "product_desc");
				item.product_info = getString(list, "product_info");
				item.product_note_01 = getString(list, "product_note_01");
				item.product_note_02 = getString(list, "product_note_02");
				item.product_image = getString(list, "product_image");
				item.service_item_id = getString(list, "service_item_id");
				item.price = (TextUtil.isEmpty(getString(list, "price")) ? 0 : Integer.parseInt(getString(list, "price")));
				item.real_price = (TextUtil.isEmpty(getString(list, "real_price")) ? 0 : Integer.parseInt(getString(list, "real_price")));
				item.start_date = getString(list, "start_date");
				item.end_date = getString(list, "end_date");

				///**
				// * 작업전이야 - 시발
				// */
				////if (TextUtil.isEmpty(item.p_passtype))
				//{
				//	if (("1일이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
				//		item.p_passtype = PASS_TYPE_DAY;
				//		if (null != item) {
				//			if (item.product_type == PRODUCT_TYPE.NONE) {
				//				item.product_type = PRODUCT_TYPE.PPV;
				//			}
				//			if (item.product_term == 0) {
				//				item.product_term = 1;
				//			}
				//		}
				//	} else if (("월이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
				//		item.p_passtype = PASS_TYPE_MONTH;
				//		if (null != item) {
				//			if (item.product_type == PRODUCT_TYPE.NONE) {
				//				item.product_type = PRODUCT_TYPE.PPM;
				//			}
				//			if (item.product_term == 0) {
				//				item.product_term = 0;
				//			}
				//		}
				//	} else if (("1년약정").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
				//		item.p_passtype = PASS_TYPE_YEAR;
				//		if (null != item) {
				//			if (item.product_type == PRODUCT_TYPE.NONE) {
				//				item.product_type = PRODUCT_TYPE.PPM;
				//			}
				//			if (item.product_term == 0) {
				//				item.product_term = 365;
				//			}
				//		}
				//	} else if (("무료쿠폰등록").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
				//		item.p_passtype = PASS_TYPE_COUPON;
				//		if (null != item) {
				//			if (item.product_type == PRODUCT_TYPE.NONE) {
				//				item.product_type = PRODUCT_TYPE.CPN;
				//			}
				//			if (item.product_term == 0) {
				//				item.product_term = 0;
				//			}
				//		}
				//	} else {
				//		item.p_passtype = PASS_TYPE_NONE;
				//	}
				//}
				///**
				// * 작업전이야 - 종발
				// */

				if (!TextUtil.isEmpty(item.p_passtype)) {
					item.p_passtype = item.p_passtype.toUpperCase();
				}

				String key = item.p_passtype;
				putTicketItems(key, item);

				//if (IKaraokeTV.DEBUG) Log.wtf("[VASS]" + _toString(), "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + items.get(key).product_name);
			}

		}

		///**
		// * 테스트야 - 시발
		// */
		//TicketItem ticketItemDay = items.get(PASS_TYPE_DAY);
		//TicketItem ticketItemMonth = items.get(PASS_TYPE_MONTH);
		//TicketItem ticketItemYear = items.get(PASS_TYPE_YEAR);
		//TicketItem ticketItemCoupon = items.get(PASS_TYPE_COUPON);
		///**
		// * 이용권목록 - test
		// */
		//items.clear();
		///**
		// * 1일이용권 - test
		// */
		//try {
		//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemDay.p_passtype + "[ITEM]" + ticketItemDay);
		//	putTicketItems(ticketItemDay.p_passtype, ticketItemDay);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		///**
		// * 월이용권 - test
		// */
		//try {
		//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemMonth.p_passtype + "[ITEM]" + ticketItemMonth);
		//	putTicketItems(ticketItemMonth.p_passtype, ticketItemMonth);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		///**
		// * 1년약정 - test
		// */
		//ticketItemYear = new TicketItem();
		//ticketItemYear.p_passtype = PASS_TYPE_YEAR;
		//ticketItemYear.id_product = ticketItemMonth.id_product;
		//ticketItemYear.product_name = PASS_TYPE_YEAR;
		//ticketItemYear.product_desc = ticketItemMonth.product_desc;
		//ticketItemYear.service_item_id = ticketItemMonth.service_item_id;
		//ticketItemYear.price = ticketItemMonth.price;
		//ticketItemYear.real_price = ticketItemMonth.real_price;
		//ticketItemYear.start_date = ticketItemMonth.start_date;
		//ticketItemYear.end_date = ticketItemMonth.end_date;
		//ticketItemYear.product_type = PRODUCT_TYPE.PPM;
		//ticketItemYear.product_term = 365;
		//try {
		//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemYear.p_passtype + "[ITEM]" + ticketItemYear);
		//	putTicketItems(ticketItemYear.p_passtype, ticketItemYear);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		///**
		// * 무료구폰등록 - test
		// */
		//try {
		//	if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "[RECV]" + "[KEY]" + ticketItemCoupon.p_passtype + "[ITEM]" + ticketItemCoupon);
		//	putTicketItems(ticketItemCoupon.p_passtype, ticketItemCoupon);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		///**
		// * 테스트야 - 종발
		// */

		for (String key : getTicketItems().keySet()) {
			TicketItem item = getTicketItems().get(key);
			Log.d("[VASS]" + _toString(), "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());
		}

		if (getTicketItems().size() == 0) {
			Log.wtf(_toString(), "[RECV]" + "[NG]" + getTicketItems());
			result_code = "XXXXX";
			result_message = "상품없쎠!!!!상품없쎠!!!!상품없쎠!!!!" + "(" + getTicketItems().size() + ")";
		}

		sendMessage(COMPLETE_TICKET_SALES_STATE);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseTicketSalesState <");
	}

	private void parseEventApply(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseEventApply >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_EVENT_APPLY);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			event_popup_msg1 = getString(list, "msg1");
			event_popup_msg2 = getString(list, "msg2");
			event_popup_msg3 = getString(list, "msg3");
		}

		sendMessage(COMPLETE_EVENT_APPLY);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseEventApply <");
	}

	private void parseEventHP(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseEventHP >");

		sendMessage(COMPLETE_EVENT_HP);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseEventHP <");
	}

	private void parseCouponRegist(JSONObject info, JSONArray lists) throws Exception {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCouponRegist >");

		if (!result_code.equals("00000")) {
			sendMessage(COMPLETE_COUPON_REGIST);
			return;
		}

		for (int i = 0; i < lists.length(); i++) {
			JSONObject list = lists.getJSONObject(i);

			// isyoon
			if (IKaraokeTV.DEBUG) {
				if (list != null) {
					Log.d(_toString(), "list - " + list.toString(2));
				}
			}

			cpn_term = getString(list, "cpn_term");
		}

		sendMessage(COMPLETE_COUPON_REGIST);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "parseCouponRegist <");
	}
}
