package com.kumyoung.stbcomm;

import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kr.kymedia.kykaraoke.api.IKaraokeTV;

/**
 * singleton
 */
class ISUHandler /* extends Activity */{

	private static final String TAG = ISUHandler.class.getSimpleName();

	// public static SharedPreferences pref;
	private static ISUHandler instance = null;
	// public Calendar OnTime;
	// public Calendar OffTime;

	// TestBED
	// String serverURL = "http://iptvisutb.lgdacom.net:8900/servlets/";

	// LIVE
	// String serverURL = "http://iptvisu.lgdacom.net:443/servlets/";
	// String serverURL = "http://iptvisu.lgdacom.net:8900/servlets/";
	// String serverURL = "http://iptvisu.lgdacom.net:8900/servlets/";

	String strAPPID = "90";

	// public static String isu_hostname = "123.140.16.88"; // from XML
	public static String isu_hostname = "123.140.17.223"; // test BED
	public static int isu_port = 8900;
	// ISU server
	public static String hosturl = "/servlets/";

	// respone
	public static int result = 0;
	public static String[] resultCode = { "", "", "", "", "", "", "", "", "",
			"", "", "", "", "", "", "", "", "",
			"", "", "", "", "", "", "", "", "" };

	public static String retString = "......";
	public static String resultDate = null;

	public ISUHandler()
	{
		// localIP = getLocalIpAddress();
		// OnTime = Calendar.getInstance();
		// OffTime = Calendar.getInstance();
	}

	public static ISUHandler getInstance()
	{
		if (instance == null)
		{
			instance = new ISUHandler();
			// read xml
		}
		return instance;
	}

	public String GetMessageFromCode(String strCode)
	{

		if (strCode.equals("B0000"))
			return "정상적으로 처리하였습니다.";

		return "서비스 오류 입니다. ";
	}

	/**
	 * 셋탑에서 시청하고자 하는 상품정보가 없는 경우 요청하는 API
	 */
	public void getProductList(String strContID, String strMacAddr)
	{
		try {
			String sASP = String.format("CommSvl?");
			String sUser = String.format("CMD=getProductList&PARAM=SBCCONTNO=%s|STBMAC=%s|SVCID=|APPID=%s|CHNO=|"
					, strContID, strMacAddr, strAPPID);
			String sOption = String.format("");
			String serverURL = "http://" + isu_hostname + ":" + isu_port + hosturl;
			if (BuildConfig.DEBUG)
			{
				Log.d(TAG, serverURL + sASP + sUser + sOption);
			}

			// URL 설정하고 접속하기
			URL url = new URL(serverURL + sASP + sUser + sOption);       // URL 설정
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
			OutputStreamWriter outStream = new OutputStreamWriter(os, "utf8");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "utf8");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장

			if (BuildConfig.DEBUG)
			{
				// _LOG.d(TAG, "myResult" + myResult);
				Log.i(TAG, myResult);
			}
			String[] array = myResult.split("\\|");
			for (int i = 0; i < array.length; i++)
			{
				if (BuildConfig.DEBUG)
				{
					Log.d(TAG, i + 1 + ":" + array[i]);
				}
				switch (i)
				{

				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					resultCode = array[i].split("\n");
					break; // array[i].split("\\"); break;
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

		// return result;
	}

	// static public void get_song_info

	/**
	 * 부가서비스 신규 가입시 요청하는 API
	 */
	public boolean subServiceReq(String strContID, String strMacAddr, String ProductCode)
	{
		try {
			String sASP = String.format("CommSvl?");
			String sUser = String.format("CMD=subService&PARAM=SBCCONTNO=%s|STBMAC=%s|PRODUCTCODE=%s|APPID=%s|"
					, strContID, strMacAddr, ProductCode, strAPPID);
			String sOption = String.format("");
			String serverURL = "http://" + isu_hostname + ":" + isu_port + hosturl;
			if (BuildConfig.DEBUG)
			{
				Log.d(TAG, serverURL + sASP + sUser + sOption);
			}

			// URL 설정하고 접속하기
			URL url = new URL(serverURL + sASP + sUser + sOption);       // URL 설정
			HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속

			// 전송 모드 설정 - 기본적인 설정이다
			http.setDoInput(true);                         // 서버에서 읽기 모드 지정
			http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
			http.setDefaultUseCaches(false);
			http.setRequestMethod("GET");         // 전송 방식은 POST

			// 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
			http.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			http.setConnectTimeout(3000);
			http.setReadTimeout(10000);

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
			OutputStreamWriter outStream = new OutputStreamWriter(os, "utf8");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "utf8");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                      // 전송결과를 전역 변수에 저장

			if (BuildConfig.DEBUG)
			{
				Log.d(TAG, "myResult : " + myResult);
			}
			String[] array = myResult.split("\\|");
			for (int i = 0; i < array.length; i++)
			{
				if (BuildConfig.DEBUG)
				{
					Log.d(TAG, i + 1 + ":" + array[i]);
				}
				switch (i)
				{
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 6:
					resultCode = array[i].split("\n");
					break; // array[i].split("\\"); break;

				default:
					break;
				}

			}
		} catch (MalformedURLException e)
		{
			retString = new String("Exception error - MalformedURLException ");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			retString = new String("Exception error - IOException");
			return false;
		} // try

		retString = new String(GetMessageFromCode(resultCode[0]));
		return (resultCode[0].equals("B0000"));
	}

	/**
	 * 양방향서비스 또는 실시간 채널 서비스 / 상품 신규 가입시 가입전에 당사자 여부를 확인하기 위해 인증 요청 하는 API
	 */
	public boolean subServiceConfirmAuth(String strContID, String strMacAddr)
	{
		try {
			String sASP = String.format("CommSvl?");
			String sUser = String.format("CMD=confirmAuth&PARAM=SBCCONTNO=%s|STBMAC=%s|SEARCHTYPE=%d|PERSONALNO=%s|"
					, strContID, strMacAddr, 3, strContID);
			String sOption = String.format("");
			String serverURL = "http://" + isu_hostname + ":" + isu_port + hosturl;
			if (BuildConfig.DEBUG)
			{
				Log.d(TAG, serverURL + sASP + sUser + sOption);
			}

			// URL 설정하고 접속하기
			URL url = new URL(serverURL + sASP + sUser + sOption);       // URL 설정
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
			OutputStreamWriter outStream = new OutputStreamWriter(os, "utf8");
			PrintWriter writer = new PrintWriter(outStream);
			writer.write(buffer.toString());
			writer.flush();

			// 서버에서 전송받기
			InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "utf8");
			BufferedReader reader = new BufferedReader(tmp);
			StringBuilder builder = new StringBuilder();

			String str;
			while ((str = reader.readLine()) != null)
				// 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
				builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
			reader.close();

			String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장

			String[] array = myResult.split("\\|");
			for (int i = 0; i < array.length; i++)
			{
				if (BuildConfig.DEBUG)
				{
					Log.d(TAG, i + 1 + ":" + array[i]);
				}
				switch (i)
				{
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					resultCode = array[i].split("\n");
					break; // array[i].split("\\"); break;
				default:
					break;
				}

			}
		} catch (MalformedURLException e)
		{
			retString = new String("Excpetion error 1:" + e.getMessage());
			return false;
		} catch (IOException e) {
			retString = new String("Excpetion error 2:" + e.getMessage());
			e.printStackTrace();
			return false;
		} // try

		retString = new String(GetMessageFromCode(resultCode[0]));
		return (resultCode[0].equals("B0000"));
	}

	/**
	 * 셋탑에서 고객이 이미 가입되어진 서비스 또는 상품리스트를 요청하는 API
	 */
	public void getSubService()
	{

	}

}
