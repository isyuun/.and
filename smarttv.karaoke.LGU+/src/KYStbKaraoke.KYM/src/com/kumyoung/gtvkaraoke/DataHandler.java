package com.kumyoung.gtvkaraoke;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.kumyoung.stbcomm.SIMClientHandlerLGU;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import isyoon.com.devscott.karaengine.Global;
import kr.kumyoung.gtvkaraoke.BuildConfig;

//import com.lgdacom.monitor.*;
//import android.widget.Toast;

/**
 * singleton
 */
public class DataHandler extends Activity {

	private static final String TAG = DataHandler.class.getSimpleName();
	// public static SharedPreferences pref;
	private static DataHandler instance;
	public static int ticket_type = 0;
	public static String ticket_name = "unknown product";

	// public static SettingInteropService SIS;
	public static String serverKYIP = "http://211.236.190.103:8080/";
	public static String serverKYURL = serverKYIP + "/common2/";
	public static String mp3Path = "";
	public static String videoPath = "http://kumyoung.hscdn.com/GTV_Video/aaa.mp4";
	// / public static String videoPath = "rtmp://rtmp01.hddn.com/play";
	public static String retString = "";

	// public static Boolean SkipPing = false;
	public static String pingServer = "http://www.google.com";

	public static String serverUpdateURL = "";
	// public static boolean _have_userinfo = false;
	public static boolean isReachable = true;
	public static String telephone = "고객센터(국번없이 101)로 문의바랍니다";

	public int IDLE = 0;
	public int MOVIE = 1;
	public int SETUP = 2;
	public int running_status = IDLE;
	// final private static int DMC_ID = 12; // for ky
	// final private static String SO_ID = "GTV";

	// info
	public static String login_message = "가입이 필요합니다";
	public static String register_date = null;
	public static String AD_image;
	public static String AD_full_image;

	public static boolean ADPOPUP_use = false;
	public static String ADPOPUP_image;
	// public static boolean AD_useDetail = false;

	public static String theme1_text;
	public static String theme2_text;
	public static String theme1_image;		// 테마 리스트
	public static String theme2_image;		// 테마 리스트 2

	public static boolean have_month_ticket = false;
	public static int reg_return = 0;		// 이것이 없으면 프로그램 실행 중

	// respone
	public static int result = 0;
	public static String[] resultMessage = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
	public static String resultDate = null;

	public static String temp;

	private DataHandler()
	{
	}

	public static DataHandler getInstance()
	{
		if (instance == null)
		{
			instance = new DataHandler();
			// read xml
		}
		return instance;
	}

	public static void setExternalPath(String s)
	{
		temp = s;

		File path = new File("/mnt/sdcard/KY/");

		if (!path.isDirectory()) {
			path.mkdirs();
		}

	}

	public static String getExternalPath()
	{
		return "/mnt/sdcard/KY/";
	}

	/**
	 * 
	 * @return
	 */
	// public static String getSTBID()
	// {
	// return stb_id;
	// }

	/**
	 * <words>
	 * <word value="lorem">
	 * <word value="ipsum">
	 * </word>
	 * </word>
	 * </words>
	 */
	public static void ReadConfigureation()
	{
		try
		{
			/*
			 * XmlPullParser xpp = getResources().getXml(R.xml.words);
			 * while ( xpp.getEventType() != XmlPullParser.END_DOCUMENT ) // 마지막 문서까지
			 * {
			 * if ( xpp.getEventType() == XmlPullParser.START_TAG ) // START_EVENT 받음.
			 * {
			 * if ( xpp.getName().equals("word") ) // 엘리먼트 이름이 word라면
			 * {
			 * // xml파일이 정확한 형태로 만들어져 있기때문에 getAttributeValue 사용
			 * // 원래 속성의 갯수(getAttributeCount()), 이름(getAttributeName) 사용하는게 좋음.
			 * items.add(xpp.getAttributeValue(0));
			 * }
			 * }
			 * xpp.next();
			 * }
			 */
		} catch (Throwable t)
		{
			// Toast.makeText(this, "실패 : " + t.toString(), 2000).show();
		}
	}

	public String getLocalIpAddress() {

		/*
		 * String str = null;
		 * try {
		 * Socket socket = new Socket("www.droidnova.com", 80);
		 * Log.i("", socket.getLocalAddress().toString());
		 * str =socket.getLocalAddress().toString();
		 * } catch (Exception e) {
		 * Log.i("", e.getMessage());
		 * }
		 */
		try
		{
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
			{
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
				{
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress())
						return inetAddress.getHostAddress().toString();
				}
			}
		} catch (SocketException exception)
		{
		}
		return "0.0.0.0";
	}

	/*
	 * public void run()
	 * {
	 * Log.v("ExtHandler", "run");
	 * Thread background = new Thread(new Runnable()
	 * {
	 * public void run()
	 * {
	 * try
	 * {
	 * // Log.e( "tag", "in aMidiSetup()");
	 * // nativeLib.aMidiSetup();
	 * 
	 * }
	 * catch (Throwable t )
	 * {
	 * // LOGi("dectection err");
	 * }
	 * }
	 * });
	 * background.start();
	 * 
	 * }
	 * 
	 * public void stop()
	 * {
	 * // if ( queue != null )
	 * // queue.stop();
	 * }
	 */

	/**
	 * 종량제 가입
	 */
	static public boolean BuyTicket(String user_id,
			int dmc_id,
			String so_id,
			String session_id,
			int register /* 0 */, String product /* PRS0001 */)
	{

		// left
		boolean ret = false;

		if (register == 0)
		{
			ret = SIMClientHandlerLGU.pay_product_sendRequestData(); 			// 300, 31
			if (ret == false)
			{
				return ret;
			}
		}
		// else
		// ret = SIMClientHandlerLGU.subscription_product_sendRequestData(); // 400, 41

		//
		// KYSERVER notify
		//
		try {

			String sASP = String.format("PayTicket_lgutv.asp?");
			String sUser = String.format("USER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", user_id, dmc_id, so_id, session_id);
			// , SIMClientHandlerLGU.cont_no
			// , SIMClientHandlerLGU.dmc_id
			// , SIMClientHandlerLGU.so_id
			// , SIMClientHandlerLGU.getSessionID() );
			String sOption = String.format("&ticket_code=%s&pay_yn=%s", product, "Y");

			Log.d(TAG, serverKYURL + sASP + sUser + sOption);

			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser + sOption);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, i + 1 + ":" + array[i]);
				switch (i)
				{
				case 0:
					result = Integer.parseInt(array[i]);
					break;
				case 1:
					resultMessage = array[i].split("\n");
					break; // array[i].split("\\"); break;
				case 12:
					resultMessage = array[i].split("\n");
					resultDate = new String(resultMessage[0]);
					break; // array[i].split("\\"); break;
				case 13:
					break; // message
				default:
					break;
				}

			}
		} catch (MalformedURLException e)
		{
			//
		} catch (IOException e) {
			e.printStackTrace();
		} // try

		return true;
	}

	// static public void get_song_info

	/**
	 * 곡 정보 취득
	 */
	static public int GetSongInfoFromKY(int sno)
	{
		/*
		 * if ( isDemo == true ) {
		 * mp3Path = String.format("svc_media/mmp3/%05d.mp3", sno );
		 * return 0;
		 * }
		 */

		String need_popup = null;
		String success = null;
		String iserror = null;

		try {

			// String sASP = String.format( "GetSongInfo_cnm.asp?");
			String sASP = String.format("GetSongInfo_lgugtv.asp?");
			String sUser = String.format("USER_ID=%s&DMC_ID=%d&SO_ID=%s&SONG_ID=%d", SIMClientHandlerLGU.cont_no
					, SIMClientHandlerLGU.dmc_id
					, SIMClientHandlerLGU.so_id
					, sno);
			// String sOption = String.format( "&ticket_code=%d&pay_yn=%s", 1, "Y");
			Log.d(TAG, "GetSongInfo URL: " + serverKYURL + sASP + sUser);

			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser);       // URL 설정
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();   // 접속

			Log.d("ke", "check point 1");

			// 전송 모드 설정 - 기본적인 설정이다
			httpUrlConnection.setDoInput(true);                         // 서버에서 읽기 모드 지정
			httpUrlConnection.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			httpUrlConnection.setDefaultUseCaches(false);
			httpUrlConnection.setRequestMethod("GET");         // 전송 방식은 POST
			httpUrlConnection.setConnectTimeout(3000);
			httpUrlConnection.setReadTimeout(3000);

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			httpUrlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = httpUrlConnection.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			String myResult = "";
			Global.Inst().app.doMenu(1);		// remove score

			try
			{
				// 서버에서 전송받기
				InputStreamReader tmp = new InputStreamReader(httpUrlConnection.getInputStream(), "EUC-KR");
				BufferedReader reader = new BufferedReader(tmp);
				StringBuilder builder = new StringBuilder();
				String str;
				while ((str = reader.readLine()) != null)        // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				{
					builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
				}
				reader.close();
				myResult = builder.toString();                       // 전송결과를 전역 변수에 저장

			} catch (IOException e)
			{
				Global.Inst().app.doMenu(0);		// wait
				System.out.println(e);
				return -1;
			}
			httpUrlConnection.disconnect();

			/**
			 * isyoon:로그확인
			 */
			if (BuildConfig.DEBUG) Log.wtf("[KP]" + TAG, myResult);

			Global.Inst().app.doMenu(0);		// wait
			String[] array = myResult.split("ð");

			/**
			 * isyoon:로그확인
			 */
			if (BuildConfig.DEBUG) Log.d("[KP]" + TAG, "" + Arrays.toString(array));

			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, i + 1 + ":" + array[i]);
				switch (i)
				{
				case 0:
					iserror = array[i];
					break;
				case 1:
					success = array[i];
					break;
				case 2:
					need_popup = array[i];
					break;
				case 4:
					mp3Path = array[i];
					break;
				case 20:
					videoPath = "http://kumyoung.hscdn.com/" + array[i];
					Log.d("ke", "videoPath" + videoPath);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} // try

		try {
			if (Integer.parseInt(iserror) == 0)
				return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			return Integer.parseInt(need_popup);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -2;
	}

	/**
	 * 사용자 정보 취득 /SIM
	 * 
	 */
	static public void GetUserInfoFromKY(String item_code) {

		have_month_ticket = true;
		if (item_code == null || item_code.length() <= 0)
			have_month_ticket = false;

		String month_ticket = new String("N");

		if (have_month_ticket)
		{
			if (item_code.equals(SIMClientHandlerLGU.item_id_m) ||
					item_code.equals(SIMClientHandlerLGU.item_id_mR))
			{
				month_ticket = new String("Y");
			}
		}

		try {
			String sASP = String.format("GetUserInfo_lgugtv.asp?");
			String sUser = String.format("USER_ID=%s&DMC_ID=%d&SO_ID=%s&JOIN_YN=%s&VERSION=1", SIMClientHandlerLGU.cont_no
					, SIMClientHandlerLGU.dmc_id
					, SIMClientHandlerLGU.so_id,
					month_ticket/* 월정액만 가입된 상태 */);

			/*
			 * if ( Global.Inst().isDemo)
			 * sUser = String.format( "USER_ID=1112233&dmc_id=123&so_id=123&join_YN=Y&version=1");
			 */

			// String sOption = String.format( "&ticket_code=%d&pay_yn=%s", 1, "Y");

			Log.d(TAG, "req :" + sASP + sUser);
			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("POST");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str/* + "\n" */);                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, "USERINFO " + (i + 1) + ":" + array[i]);
				switch (i)
				{
				case 1:
					login_message = array[i];
					break;
				case 2:
					register_date = array[i];
					break;
				case 3:
					AD_image = array[i];
					break;
				case 5:
					AD_full_image = array[i];
					break;
				case 11: {
					String[] arr = array[i].split("º");
					theme1_text = arr[0];		// 나는 가수다.
					theme2_text = arr[1];		// 위탄 노래 다시듣기
				}
					break;
				case 12: {
					String[] arr = array[i].split("º");
					theme1_image = arr[0];		// 나는 가수다.
					theme2_image = arr[1];		// 위탄 노래 다시듣기
				}
					break;
				case 21:	// 가지고 있는 이용
					reg_return = Integer.parseInt(array[i]);
					break;

				case 25:
					login_message = array[i];
					login_message.trim();
					break;
				case 26:
					videoPath = "http://kumyoung.hscdn.com/" + array[i];
					break;
				default:
					break;
				}

			}
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {
			e.printStackTrace();
		} // try

		// _have_userinfo = true;
	}

	/**
	 * 이미지 정보만 갖고 오게 하려고 만들 내용
	 */
	static public void GetBannerInfoFromKY()
	{

		try {
			String sASP = String.format("GetBannerInfo_lgutv.asp");
			Log.d(TAG, "req :" + sASP);
			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("POST");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str/* + "\n" */);                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, "USERINFO " + (i + 1) + ":" + array[i]);
				switch (i)
				{
				// case 1: login_message = array[i]; break;
				// case 2: register_date = array[i]; break;
				case 3:
					AD_image = array[i];
					break;
				case 5:
					AD_full_image = array[i];
					break;

				case 7:
					ADPOPUP_use = (array[i]).equals("1");
					break;
				case 8:     // AD_useDetail = (array[i]).equals("1"); break; // no use
				case 9:
					ADPOPUP_image = array[i];
					break;   // POPUP image
				case 12: {
					String[] arr = array[i].split("º");
					theme1_image = arr[0];		// 나는 가수다.
					theme2_image = arr[1];		// 위탄 노래 다시듣기
				}
					break;
				case 21:	// 가지고 있는 이용
					// reg_return = Integer.parseInt( array[i] );
					break;
				case 25:
					// login_message = array[i];
					// login_message.trim();
					break;
				case 26:
					videoPath = "http://kumyoung.hscdn.com/" + array[i];
					break;
				default:
					break;
				}

			}
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} // try

		// _have_userinfo = true;
	}

	/**
	 * 주/월 간 20 랭킹 리스트를 얻어온다.
	 */
	static public void Ranking20_HttpGetData()
	{
		// / SocketPermission("www.company.com:7000-", "connect,accept");
		try {

			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + "show_ranking_list.asp");       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			String gb_val = "w";		// w : 주간, m : 월간

			StringBuffer buffer = new StringBuffer();
			buffer.append("Gb").append("=").append(gb_val);

			/*
			 * buffer.append("id").append("=").append(myId).append("&"); // php 변수에 값 대입
			 * buffer.append("pword").append("=").append(myPWord).append("&"); // php 변수 앞에 '$' 붙이지 않는다
			 * buffer.append("title").append("=").append(myTitle).append("&"); // 변수 구분은 '&' 사용
			 * buffer.append("subject").append("=").append(mySubject);
			 */

			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");
			for (int i = 0; i < array.length; i++)
				Log.d(TAG, array[i]);
			// ((TextView)(findViewById(R.id.text_result))).setText(myResult);
			// Toast.makeText(MainActivity.this, "전송 후 결과 받음", 0).show();
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {

			e.printStackTrace();

		} // try
	} // HttpPostData

	static public void HttpPostData()
	{

		// / SocketPermission("www.company.com:7000-", "connect,accept");
		try {

			// URL 설정하고 접속하기
			// URL url = new URL("http://korea-com.org/foxmann/lesson01.php"); // URL 설정
			URL url = new URL(serverKYURL);       // URL 설정

			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정

			http.setDefaultUseCaches(false);
			http.setRequestMethod("POST");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			String myId = "id";
			String myPWord = "pw";
			String myTitle = "title";
			String mySubject = "subject";

			StringBuffer buffer = new StringBuffer();
			buffer.append("id").append("=").append(myId).append("&");                 // php 변수에 값 대입
			buffer.append("pword").append("=").append(myPWord).append("&");   // php 변수 앞에 '$' 붙이지 않는다
			buffer.append("title").append("=").append(myTitle).append("&");           // 변수 구분은 '&' 사용
			buffer.append("subject").append("=").append(mySubject);

			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");

			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			}
			reader.close();
			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장

			Log.d("ke", myResult);
			// ((TextView)(findViewById(R.id.text_result))).setText(myResult);
			// Toast.makeText(MainActivity.this, "전송 후 결과 받음", 0).show();
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {

			e.printStackTrace();

		} // try
	} // HttpPostData

	public static int getVersionCode(Context context)
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}

	// public void RegistService()
	// {
	// LiveUpdateBroadcastReceiver receiver = new LiveUpdateBroadcastReceiver();
	// IntentFilter filter = new IntentFilter();
	// filter.addAction("android.lgt.appstore.LIVE_UPDATE_AUTO");
	// registerReceiver(receiver, filter);
	// }
	//
	// public void UpdateService()
	// {
	// /*
	// int pid = android.os.Process.myPid();
	// PackageManager pm = this.getPackageManager();
	// PackageInfo packageInfo = null;
	// try {
	// packageInfo = pm.getPackageInfo(this.getPackageName(), 0);
	// } catch (NameNotFoundException e) {
	// e.printStackTrace();
	// }
	// //String version = packageInfo.versionName; //버전 네임
	// int versionCode = packageInfo.versionCode; //버전 코드
	//
	// // NEW LGUPlus Store 14.8.11
	// String checkUri = String.format( "update://UPDATECHECK/%d/com.kumyoung.gtvkaraoke/%d", pid, versionCode) ;
	// Intent intentService = new Intent();
	// intentService.setAction("com.lguplus.iptv3.updatecheck.updateservice");
	// intentService.setData(Uri.parse(checkUri));
	// startService(intentService);
	// */
	// }

	// 만료.
	public boolean NeedUpdateVersionA(Context context)
	{
		int versionCode = getVersionCode(context);

		serverUpdateURL = new String("");
		// http://211.236.190.103:8080/common2/GetAppVerInfo_lgutv.asp?ver=1.0.32
		try {
			String sASP = String.format("GetAppVerInfo_lgutv.asp?");
			String sUser = String.format("ver=%d", versionCode);
			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");

			int count = 0;
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, i + 1 + ":" + array[i]);
				switch (i) {
				case 0:
					result = Integer.parseInt(array[i]);
					break;
				case 1:
					if (result != 0) {
						resultMessage = array[i].split("\n");
						serverUpdateURL = new String(resultMessage[0]);
					}
					break;
				default:
					break;
				}
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		} // try
		return (result == 1);
	}

	static public int PollWatch(String strMacAddress)
	{
		try
		{
			String sASP = "http://stat.mylgtv.com/stat.tiff?gbn=23&seq=kumyoung_tvg&sid=";
			// URL 설정하고 접속하기
			URL url = new URL(sASP + strMacAddress);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			// no respones
			/*
			 * String myResult = builder.toString(); // 전송결과를 전역 변수에 저장
			 * String[] array = myResult.split("ð");
			 * for ( int i = 0; i < array.length; i++)
			 * {
			 * Log.d(TAG, i+1 + ":" + array[i] );
			 * switch ( i )
			 * {
			 * case 0: result = Integer.parseInt( array[i]);break;
			 * case 1: resultMessage = array[i].split("\n"); break; //array[i].split("\\"); break;
			 * }
			 * 
			 * }
			 */
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {
			e.printStackTrace();
		} // try

		return result;
	}

	/**
	 * 
	 * @param user_id
	 * @param sno
	 * @return
	 */
	static public int SetRegistFavoriteSong(String user_id, int sno)
	{
		try
		{
			String sASP = String.format("Favor_reg.asp?");
			String sUser = String.format("USER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", user_id
					, SIMClientHandlerLGU.dmc_id
					, SIMClientHandlerLGU.so_id
					, SIMClientHandlerLGU.getSessionID()
					);
			String sOption = String.format("&SONG_ID=%d", sno);

			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser + sOption);       // URL 설정
			// Log.d("ke", serverURL + sASP + sUser + sOption );

			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, i + 1 + ":" + array[i]);
				switch (i)
				{
				case 0:
					result = Integer.parseInt(array[i]);
					break;
				case 1:
					resultMessage = array[i].split("\n");
					break; // array[i].split("\\"); break;
				}

			}
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {
			e.printStackTrace();
		} // try

		return result;
	}

	/**
	 * user_id에 해당하는 가족 애창곡을 얻어옴.
	 * 
	 * @param user_id
	 * @return
	 */
	static public int GetRegistFavoriteSong(String user_id, ArrayList<Integer> p)
	{
		try {
			String sASP = String.format("Favor_list.asp?");
			String sUser = String.format("USER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", user_id
					, SIMClientHandlerLGU.dmc_id
					, SIMClientHandlerLGU.so_id
					, SIMClientHandlerLGU.getSessionID());
			String sOption = String.format("&tot_cnt=%d", 0/* 전체조회 */);

			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser + sOption);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");

			int count = 0;
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, i + 1 + ":" + array[i]);
				switch (i)
				{
				case 0:
					result = Integer.parseInt(array[i]);
					break;
				case 1:
					resultMessage = array[i].split("\n");
					break; // array[i].split("\\"); break;

				case 2:
					count = Integer.parseInt(array[i]);
					break;

				case 3:
					String[] arr = array[i].split("º");
					for (int in = 0; in < count; in++)
					{
						int sno = Integer.parseInt(arr[in]);
						p.add(sno);
					}
					break;
				}

			}
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {
			e.printStackTrace();
		} // try

		return result;
	}

	/**
	 * user_id에 해당하는 가족 애창곡을 지움
	 * 
	 * @param user_id
	 * @return
	 */
	static public int UnRegistFavoriteSong(String user_id, int song_id) // , ArrayList<Integer> p)
	{
		try {
			String sASP = String.format("Favor_del.asp?");
			String sUser = String.format("USER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", user_id
					, SIMClientHandlerLGU.dmc_id
					, SIMClientHandlerLGU.so_id
					, SIMClientHandlerLGU.getSessionID());

			String sOption = String.format("&song_id=%d", song_id);

			// URL 설정하고 접속하기
			URL url = new URL(serverKYURL + sASP + sUser + sOption);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(3000);

			// 서버로 값 전송
			StringBuffer buffer = new StringBuffer();
			/*
			 * buffer.append("USER_ID").append("=").append( user_id ).append("&");
			 * buffer.append("DMC_ID").append("=").append(dmc_id).append("&");
			 * buffer.append("SO_ID").append("=").append("").append("&");
			 * buffer.append("STB_MODEL").append("=").append("lge").append("&");
			 * buffer.append("JOIN_YN").append("=").append("Y");
			 */
			OutputStream os = http.getOutputStream();
			OutputStreamWriter outStream = new OutputStreamWriter(os, "EUC-KR");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
			String[] array = myResult.split("ð");

			int count = 0;
			for (int i = 0; i < array.length; i++)
			{
				Log.d(TAG, i + 1 + ":" + array[i]);
				switch (i)
				{
				case 0:
					result = Integer.parseInt(array[i]);
					break;
				case 1:
					resultMessage = array[i].split("\n");
					break; // array[i].split("\\"); break;

				case 2:
					count = Integer.parseInt(array[i]);
					break;

				case 3:
					String[] arr = array[i].split("º");
					// for ( int in = 0; in < count; in++)
					// {
					// int sno = Integer.parseInt(arr[in]);
					// p.add( sno );
					// }
					break;
				}

			}
		} catch (MalformedURLException e) {
			//
		} catch (IOException e) {
			e.printStackTrace();
		} // try

		return result;
	}

	// 한달간 서비스를 준다.
	public Boolean IsServiceDate()
	{

		/*
		 * GregorianCalendar today = new GregorianCalendar();
		 * int year = today.get(Calendar.YEAR);
		 * int month = today.get(Calendar.MONTH);
		 * int day = today.get(Calendar.DATE);
		 * Calendar endDate = Calendar.getInstance();
		 * endDate.set(year, month, day);
		 * 
		 * if ( have_userinfo )
		 * {
		 * Calendar openDate = Calendar.getInstance();
		 * year = Integer.parseInt( register_date.substring(0,3) );
		 * month = Integer.parseInt( register_date.substring(4,5) );
		 * day = Integer.parseInt( register_date.substring(6,7) );
		 * 
		 * openDate.set( year, month-1, day);
		 * 
		 * long rawResult = ( endDate.getTimeInMillis() - openDate.getTimeInMillis() ) / 1000;
		 * Log.i(TAG, "날짜 차이 : " + result);
		 * return ( result < 30 );
		 * }
		 */
		return false;
	}

	public static void UpdateSongList()
	{
		// WebUpdater.getInstance();
		if (DataHandler.isReachable)
		{
			try {
				// stbhb_new.html
				// stbhd_version
				// http://211.236.190.103:8080/common2/songs_utf8.list
				String urlLive = String.format(DataHandler.serverKYIP + "common2/songs.list");

				URL url = new URL(urlLive);
				HttpURLConnection http = (HttpURLConnection) url.openConnection();
				http.setConnectTimeout(20 * 1000); // Thirty seconds timeout in milliseconds

				if (http.getResponseCode() == HttpURLConnection.HTTP_OK)
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "euc-kr"));
					StringBuilder builder = new StringBuilder();
					String str;
					while ((str = in.readLine()) != null)
					{
						String strNo = str.substring(0, str.indexOf('\t'));
						int sno = Integer.parseInt(strNo);
						int firstTab = str.indexOf('\t') + 1;
						String title_and_singer = str.substring(firstTab, str.length());
						Database.Inst().insertHashData(sno, title_and_singer);
						// Log.d("ke", sno + " " + title_and_singer);
					}
					Log.d(TAG, "update songlist file");
					// Toast.makeText(this,"Can't open database connection.",Toast.LENGTH_LONG).show();
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
				// return;
			} catch (IOException e)
			{
				e.printStackTrace();
				// return;
			}
		}

	}

}
