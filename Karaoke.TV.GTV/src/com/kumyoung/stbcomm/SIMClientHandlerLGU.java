package com.kumyoung.stbcomm;

import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import ac.rs.com.model.RequestData;
import ac.rs.com.model.ResponseData;
import ac.rs.lgd.svc.sim.RSRequestClient;

//import android.widget.Toast;

/*
 1. 어플 로딩시
 가. 사용자 인증
 나. 월정액 구매여부 확인

 2. 결제시
 가. 일정액 이용권 결제
 나. 월정액 이용권 결제

 과금 모듈과 이렇게 4번의 연동이 필요합니다.
 테스트는 C&M에서만 가능합니다.
 */

class SIMClientHandlerLGU /* extends Activity */{

	private /*static */final String TAG = SIMClientHandlerLGU.class.getSimpleName();
	private /*static */SIMClientHandlerLGU instance;

	public String version = "ver 0.0.2.0";
	public /*static */int dmc_id = 12;	   // from 13.3.19 // from ch lee( 12/3/8 )
	public /*static */String so_id = "GTV";   // from 13.3.19 // from ch lee
	// public /*static */String app_id = "59"; // UPlusTV STB app guide 5.9
	public /*static */String app_id = "90";    // from junoci;/
	public /*static */String cont_no = ""; 	// 가입계약번호 : 얻어오는 것
	public /*static */String stb_mac_addr = "";
	public /*static */Boolean register = false;
	public /*static */Boolean happy_call = false;
	public /*static */String return_itemcode = "";

	public /*static */String session_id = "20111123003205943";

	// detail
	// private /*static */String item_kind_cd = "1";
	// private /*static */String user_id = "";

	public /*static */String item_id_d = "40102";  // 일일권 상품코드
	public /*static */String item_id_m = "40103";  // 월정액 상품코드
	public /*static */String item_id_mR = "40104";  // 월정액 상품코드 (약정)
	// private /*static */String req_code = "20|21"; // "21|26|27|29";


	//// info
	//public /*static */String login_message = "가입이 필요합니다.";
	//public /*static */String ad_image;
	//public /*static */String ad_full_image;
	//public /*static */String theme1_image; // 테마 리스트
	//public /*static */String theme2_image; // 테마 리스트 2
	//public /*static */int have_ticket;

	// private /*static */String dbs_hostname = "123.140.16.78";
	public /*static */String dbs_hostname = "123.140.17.223";        // from TEST BED prof
	public /*static */int dbs_port = 8000;

	// private /*static */String hostname = "192.168.61.131"; // from ch lee
	// public /*static */int port = 80; // from ch lee
	public /*static */String hosturl = "/LGDacomIPTV/SIMListener";

	// (this.hostname, this.port, this.hosturl);

	// respone
	public /*static */int return_code = 0;
	public /*static */String return_message;
	public /*static */String[] resultMessage = { null, null, null, null, null };
	private /*static */Class clz = null;

	public /*static */int st_type = 3;

	public /*static */String svc_kumyoung = "고객센터 : 02-6390-2221";

	// public /*static */String svc_lgu = "고객센터 : 국번없이 101 번";

	public SIMClientHandlerLGU()
	{
		// localIP = getLocalIpAddress();
		// clientID = "unknown";
		// clientPassword = "??";
		// dealerID = "unknown";
		// serverTAG = "nonhing";
	}

	public /*static */SIMClientHandlerLGU getInstance()
	{
		if (instance == null)
		{
			instance = new SIMClientHandlerLGU();
			// read xml
			/*
			 * try {
			 * clz = Class.forName("com.lge.iptv.IptvPlayer");
			 * } catch (ClassNotFoundException e) {
			 *
			 * e.printStackTrace();
			 * }
			 */
			/*
			 * Intent intent = new Intent( ILgIptvShmRemote.class.getName() );
			 * // ILgIptvShmRemote a = new ILgIptvShmRemote();
			 * try {
			 * ILgIptvShmRemote.getSubscriberNo();
			 * } catch (RemoteException e) {
			 *
			 * e.printStackTrace();
			 * }
			 */
		}
		return instance;
	}

	public /*static */String getSessionID()
	{
		return session_id;
	}

	public /*static */String getTodayFull()
	{
		GregorianCalendar today = new GregorianCalendar();

		int year = today.get(GregorianCalendar.YEAR);
		int month = today.get(GregorianCalendar.MONTH) + 1;
		int day = today.get(GregorianCalendar.DATE);
		int hour = today.get(GregorianCalendar.HOUR);
		int min = today.get(GregorianCalendar.MINUTE);
		int sec = today.get(GregorianCalendar.SECOND);
		String str = String.format("%04d%02d%02d%02d%02d%02d", year, month, day, hour, min, sec);
		return str;
	}

	/**
	 * 서비스 사용자 인증
	 * 4.4.1.1  서비스 인증 (biz_code: 100)
	 *
	 * isyoon:sendRequestServiceUserData()
	 */
	/*static */public boolean sendRequestRSi100n11()
	{
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "100";
			requestdata.req_code = "11";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			requestdata.fields.put("cont_no", cont_no);
			requestdata.fields.put("app_id", app_id);
			// requestdata.fields.put("user_id", "testid");
			// requestdata.fields.put("item_cd", "70001");

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);

			System.out.println("100.11 status start");

			System.out.println("return_code = " + responsedata.return_code);
			System.out.println("return_message = " + responsedata.return_message);
			System.out.println("item_code = " + responsedata.fields.get("item_code"));
			System.out.println("start_date = " + responsedata.fields.get("start_date"));
			System.out.println("end_date = " + responsedata.fields.get("end_date"));
			if (BuildConfig.DEBUG)
			{
				System.out.println("name = " + responsedata.fields.get("name"));
			}

			String itmcod = (String) responsedata.fields.get("item_code");
			if (itmcod != null)
				return_itemcode = new String(itmcod);
			else
				return_itemcode = "";

			Log.d(TAG, "Return_itemcode:" + return_itemcode);

			return_code = Integer.parseInt(responsedata.return_code);
			resultMessage[0] = responsedata.return_message;

			/* return code : 4xxxx 대는 프로그램 실행을 하지 않게 한다. */
			if (return_code >= 40000 && return_code < 50000)
				return false;

		} catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public /*static */String getToday()
	{
		GregorianCalendar today = new GregorianCalendar();

		int year = today.get(today.YEAR);
		int month = today.get(today.MONTH) + 1;
		int day = today.get(today.DATE);

		String str = String.format("%04d%02d%02d", year, month, day);
		// / return "20120314";
		return str;
	}

	///**
	// * 호스트 아이디를 얻어온다.
	// *
	// * @param cr
	// */
	//public /*static */void requestHostID( ContentResolver cr)
	//{
	////ContentResolver cr = getContentResolver();
	//Uri nUri = Uri.parse("content://tvguides");
	//nUri = Uri.withAppendedPath(nUri, "sysinfo/hostid" );
	//Cursor nCursor = cr.query(nUri, null, null, null, null);
	//if (nCursor!=null)
	//{
	//while(nCursor.moveToNext())
	//{
	//_LOG.d(TAG, "HOST_ID :" + nCursor.getString(0) ); // hostId
	////host_id = nCursor.getString(0);
	//host_id = nCursor.getString(0).substring(2); // 왼쪽 두개 버림
	//}
	//}
	//}


	/**
	 * 씨앤앰 스마트셋탑박스의 경우 Sim Client만 사용하게 됩니다.
	 * Sim Client는 씨앤앰의 리턴서버를 통해 실제 과금 청구를 하는 서버와 연동되는 API입니다.
	 * 
	 * 상품은 1일 종량(1회만 청구) 상품과 월정액 가입(해지전까지 매월 청구) 상품 2가지가 있습니다.
	 * 
	 * 이에 따라
	 * 
	 * 앱 로딩시 2회 통신하며, (1) 가입자 유효성 확인, (2) 월정액 상품 가입자인지 확인
	 * 과금시에는 1일 종량, 월정액 가입에 맞추어 통신하면 됩니다.
	 * 
	 * 노래방의 상품 코드는 아래와 같습니다.
	 * 월정액 : PD00000024
	 * 1일종량 : IC00000001
	 */

	/*
	 * 이 절에서는 STB의 Application에서 SIM-Client를 이용해 iTV Return System의 SIM-Listener와 통신하기 위한 프로토콜(메시지 구조)과
	 * 활용법에 대해서 알아본다.
	 * 
	 * 
	 * 연동형식
	 * 
	 * - RequestData : STB ---> ITV R/S로 전송하는 요청메시지 객체
	 * A B C
	 * 메시지는 biz_code, req_code, fields 으로 구분
	 * A : biz_code (String) 업무구분 코드
	 * B : req_code (String) 요청하고자 하는 항목 코드
	 * C : filds
	 * 1. 실제 ITV R/S로 전송하고자 하는 항목으로 key = value 형식으로 여러 항목을 전송할 수 있다.
	 * 2. Key는 모두 소문자를 사용
	 * 3. 만일, 동일한 항목이 key = value 로 반복되는 경우 value는 ArrayList를 사용
	 * 
	 * 예) 서비스 사용자 인증 요청
	 * requestdata.biz_code = "200";
	 * requestdata.req_code = "21";
	 * requestdata.fields = new Hashtable();
	 * requestdata.fields.put("stb_host_id ", " 100000000001");
	 * requestdata.fields.put("pay_kind_cd ", "1");
	 * requestdata.fields.put("user_id", "hwpark");
	 * requestdata.fields.put("item_id", "1000001");
	 * 
	 * 단, 회원정보를 요청하는 biz_code = 200에 대해서는 필요한 데이터에 따라서 req_code를 구분자 ‘|’를 사용하여
	 * 여러 값을 동시에 요청할 수 있다. 예를 들어 가입자 기본정보에 SO_ID가 필요한 경우 req_code = “21|28”로 설정하여 통신하면 된다.
	 * 
	 * - ResponseData : ITV R/S ----> STB로 전송하는 응답메시지 객체
	 * A B C
	 * 메시지는 return_code, return_message, fileds 으로 구분
	 * A : return_code(String) 응답 코드로 에러 발생시 에러 코드를 반환한다.
	 * B : return_message(String) 응답메시지로 에러 발생시에는 에러 메시지 반환
	 * C : filds
	 * 1. 실제 ITV R/S로 전송하고자 하는 항목으로 key = value 형식으로 여러 항목을 전송할 수 있다.
	 * 2. Key는 모두 소문자를 사용
	 * 3. 만일, 폴과 같이 동일한 항목이 key = value 로 반복되는 경우 value는 ArrayList를 사용
	 * 
	 * 예) 서비스 사용자 인증 응답
	 * String returnCode = responsedata.return_code ;
	 * String returnMessage = responsedata.return_message ;
	 * Hashtable htCode = new Hashtable();
	 * ArrayList return_list = new ArrayList();
	 * return_list = (ArrayList)responsedata.fields.get("case");
	 * for(int i=0; i< return_list.size(); i++){
	 * htCode = (Hashtable) return_list.get(i);
	 * _LOG.d("sim", "폴 보기 번호 = "+ htCode.get("case_no"));
	 * _LOG.d("sim", "해당 보기의 참여자 수 = "+ htCode.get("case_result"));
	 */

	/*
	 * 3. 코드 정의
	 * 3.1 요청코드 정의
	 * biz_code : 업무 구분을 나타내는 코드로써 내부적으로 Business logic을 구분하는 항목
	 * req_code : 요청 메시지 구분하는 코드로써 각 메시지단위로 생성
	 * 
	 * biz_code biz_code설명 req-code
	 * 200 회원정보 20
	 * 21
	 * 22
	 * 23
	 * 24
	 * 25
	 * 26
	 * 27
	 * 28
	 * 29
	 * 
	 * 300 종량상품과금요청/정산 31
	 * 400 가입형상품 가입요청정보 41
	 * 500 가입형상품 가입/해지요청 51
	 * 600 가입자 주문정보요청 61
	 * 
	 * 3.2 결과 코드 정의
	 * 각 메시지에 대한 결과를 반환 할 때 포함된 결과 코드로써 성공과 실패를 구분하는 코드로써 에러 일 경우는 에러메시지도 함께 전송
	 * 
	 * 코드 코드 설명
	 * 00 정상처리
	 * 1X XML및 HTTP관련오류
	 * 2X DB관련 오류
	 * 4X 전송 규격 관련 오류
	 * 5X 타 시스템과 연동중 오류
	 * 8X 네트워크 관련 오류
	 * 9X 시스템 관련 오류
	 */

	/**
	 * 가입자 기본정보 요청
	 * 
	 * return
	 * st_type 서비스상태( 1:사용, 2:해지, 3:정지 )
	 * void_use_yn 해피콜여부 ( Y/N )
	 * open_date myLGtv 가입일자 yyyymmdd
	 *
	 * isyoon:user_regist_sendRequestData
	 */
	/*static */public boolean sendRequestRSi200n21() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "200";
			requestdata.req_code = "21";		// hard coding.
			requestdata.fields = new Hashtable();
			requestdata.fields.put("app_id", app_id);
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			requestdata.fields.put("cont_no", cont_no);

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			// DataTransmission datatrans = new DataTransmission( hostname, port, hosturl);
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);

			responsedata = datatrans.sendData(requestdata);
			String compare_cont_no = (String) responsedata.fields.get("cont_no");
			String sttype = (String) responsedata.fields.get("st_typ");

			Log.d("sim", "return_code = " + responsedata.return_code);
			Log.d("sim", "return_message = " + responsedata.return_message);
			Log.d("sim", "responseData = " + responsedata.fields);
			Log.d("sim", "void_use_yn = " + responsedata.fields.get("vod_use_yn"));
			Log.d("sim", "open_date = " + responsedata.fields.get("open_date"));

			if (BuildConfig.DEBUG)
			{
				Log.d("sim", "가입계약번호 = " + compare_cont_no); /*   */
				Log.d("sim", "가입자명= " + responsedata.fields.get("name"));
				Log.d("sim", "전화번호1= " + responsedata.fields.get("phone_no1"));
				Log.d("sim", "우편번호= " + responsedata.fields.get("zip_cd"));
				Log.d("sim", "주소= " + responsedata.fields.get("address"));
			}

			Log.d("sim", "st_typ= " + sttype);

			return_code = Integer.parseInt(responsedata.return_code);
			return_message = responsedata.return_message;

			if (sttype != null)
				st_type = Integer.parseInt(sttype);
			else
				st_type = 1;

			if (responsedata.return_code.equals("00000"))
			{
				// cont_no = (String) responsedata.fields.get("cont_no");
				// _LOG.d("sim", "cont_id = " + cont_no );

				String happy = (String) responsedata.fields.get("vod_use_yn");
				Log.d("sim", "vod_use_yn= " + happy);
				Log.d("sim", "open_date= " + responsedata.fields.get("open_date "));

				happy_call = true;
				if (happy.equals("N"))
					happy_call = false;

				// 가입 상태 정보
				// sttype ==1 정상가입
				// ==2 해지
				// ==3 정지

				if (st_type == 1)
				{
					register = true;
				}
				else if (st_type == 2)
				{
					register = false;
					return_message = new String("가입 해지 되었습니다.");
					Log.d("sim", return_message);
				}
				else if (st_type == 3)
				{
					register = false;
					return_message = new String("가입 정지 되었습니다.");
					Log.d("sim", return_message);
				}
			}
			else
			{
				register = false;

				// if ( responsedata.return_code.equals( "40000" ) )

				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return true;
	}

	/*static */public void user_regist_sendRequestData2() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "200";
			requestdata.req_code = "20";		// hard coding.
			requestdata.fields = new Hashtable();

			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			// requestdata.fields.put("item_kind_cd", "1");//item_kind_cd );
			// requestdata.fields.put("item_id ", "PD00000024");//item_id_m );
			requestdata.fields.put("item_kind_cd", "2");// item_kind_cd );
			requestdata.fields.put("item_id ", item_id_m);

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			// DataTransmission datatrans = new DataTransmission( hostname, port, hosturl);
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);

			responsedata = datatrans.sendData(requestdata);
			Log.d("sim", "return_code = " + responsedata.return_code);
			Log.d("sim", "return_message = " + responsedata.return_message);
			if (BuildConfig.DEBUG)
			{
				Log.d("sim", "cont_id = " + responsedata.fields.get("cont_id"));
			}
			Log.d("sim", "svc_reg_date = " + responsedata.fields.get("svc_reg_date"));
			Log.d("sim", "isuse_yn = " + responsedata.fields.get("isuse_yn"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 4.2. 가입자 정보
	 * 4.2.1 데이터요청
	 * RequestData를 통해 iTV R/S로 데이터를 요청한다.
	 * biz_code=200
	 * req_code=요청코드
	 * fields.put(<KEY>,<VALUE>)
	 * 
	 *  회원 정보는 인터페이스 정의서에 명시된 req_code를 통해 원하는 항목을 요청한다
	 *  요청코드가 여러 개인 경우 구분자는 "|"를 사용한다
	 * 
	 * 4.2.2 반환 값
	 * ResponseData객체를 통해 각 항목을 요청한 개수와 순서에 맞추어 반환 정보를 전달한다.
	 * return_code=결과코드
	 * return_message=결과메시지
	 * fields.get(<KEY>)
	 * 
	 *  고객정보가 없는 경우 각 항목은 "null" 을 반환한다.
	 *  결과 코드 값은 3.2결과 코드 정의를 참고한다.
	 * 
	 * APP->리턴 : RequestData를 통해 ITV R/S로 데이터를 요청
	 * biz_code 200
	 * req_code 21|26|27|29
	 * appl_id 어플리케이션 ID
	 * stb_host_id STB Host ID
	 * 
	 * 리터->APP : ResponseData 객체를 통해 요청한 개수와 순서에 맞추어 반환
	 * return_Code 결과코드
	 * return_message 결과메시지
	 * .
	 * .
	 * 
	 * 4.2.3 요청과 반환 값의 예 (biz_code=200)
	 * 
	 * /**
	 * 4.2.3.1. 가입자 기본 정보
	 */
	/*static */public boolean user_base_info_sendRequestData() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "200";
			requestdata.req_code = "21";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			requestdata.fields.put("cont_no", cont_no);
			requestdata.fields.put("app_id", "90");

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			// DataTransmission datatrans = new DataTransmission(hostname, port, hosturl);
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);

			Log.d("sim", "responseData = " + responsedata.return_code);
			if (responsedata.return_code.equals("00000"))
			{
				String compare_cont_no = (String) responsedata.fields.get("cont_no");

				Log.d("sim", "responseData = " + responsedata.fields);
				Log.d("sim", "void_use_yn = " + responsedata.fields.get("vod_use_yn"));
				Log.d("sim", "open_date = " + responsedata.fields.get("open_date"));

				if (BuildConfig.DEBUG)
				{
					Log.d("sim", "가입계약번호 = " + compare_cont_no); /*   */
					Log.d("sim", "가입자명= " + responsedata.fields.get("name"));
					Log.d("sim", "전화번호1= " + responsedata.fields.get("phone_no1"));
					Log.d("sim", "우편번호= " + responsedata.fields.get("zip_cd"));
					Log.d("sim", "주소= " + responsedata.fields.get("address"));
				}

				Log.d("sim", "st_typ= " + responsedata.fields.get("st_typ"));

				register = true;
			}
			else
			{
				register = false;
				return_message = responsedata.return_message;

				return_code = Integer.parseInt(responsedata.return_code);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			register = false;
		}
		return (register);
	}

	/**
	 * 4.2.3.2 가입자 기본정보, 주민등록번호, 이메일주소
	 */
	/*static */public void user_advance_info_sendRequestData() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "200";
			requestdata.req_code = "21|26|27";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			// DataTransmission datatrans = new DataTransmission(hostname, port, hosturl);
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);
			Log.d("sim", "responseData = " + responsedata.return_code);
			Log.d("sim", "responseData = " + responsedata.return_message);
			Log.d("sim", "responseData = " + responsedata.fields);

			if (BuildConfig.DEBUG)
			{
				Log.d("sim", "가입계약번호 = " + responsedata.fields.get("cont_id"));
				Log.d("sim", "가입자명= " + responsedata.fields.get("cust_name"));
				Log.d("sim", "전화번호1= " + responsedata.fields.get("phone_no1"));
				Log.d("sim", "우편번호= " + responsedata.fields.get("zipcode"));
				Log.d("sim", "주소= " + responsedata.fields.get("addr "));
				Log.d("sim", "주민등록번호= " + responsedata.fields.get("resid_no"));
				Log.d("sim", "이메일주소= " + responsedata.fields.get("email"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 4.3 종량상품 과금요청/정산정보
	 * 결제처리 성공시 DP-C&M간 정산시 참고자료로 활요하기 위해 처리내역을 iTV R/S로 전송한다.
	 * 
	 * 4.3.1 데이터요청
	 * RequestData를 통해 ITV R/S로 데이터를 요청한다.
	 * biz_code=300
	 * req_code=31
	 * fields.put(<KEY>,<VALUE>)
	 * 
	 *  인터페이스 정의서에 명시된 req_code를 통해 원하는 항목을 요청한다
	 * 
	 * 4.3.2 반환 값
	 * ResponseData객체를 통해 각 항목을 요청한 개수와 순서에 맞추어 반환 정보를 전달한다.
	 * return_code=결과코드
	 * return_message=결과메시지
	 * fields.get(<KEY>)
	 * 
	 *  정보가 없는 경우 각 항목은 "null" 을 반환한다.
	 *  결과 코드 값은 3.2결과 코드 정의를 참고한다.
	 * 
	 * 4.3.3 데이터 요청 및 응답
	 * APP->리턴 : RequestData를 통해 ITV R/S로 데이터를 요청
	 * biz_code = 300
	 * req_code = 31
	 * .
	 * .
	 * 리터->APP : ResponseData 객체를 통해 요청한 개수와 순서에 맞추어 반환
	 * return_code 결과코드
	 * return_message 결과메시지
	 */

	/*
	 * 
	 * 1회성 과금 요청
	 * 4.3.4 종량상품 과금요청/정산정보 요청과 반환 값의
	 */
	/*static */public boolean pay_product_sendRequestData() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();

		try {
			// 요청데이타 생성
			requestdata.biz_code = "300";
			requestdata.req_code = "31";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			requestdata.fields.put("cont_no", cont_no);
			requestdata.fields.put("app_id", "90"/* app_id */);
			requestdata.fields.put("item_code", item_id_d);
			requestdata.fields.put("pay_kind_cd", "1");		// 과금요청 1
			requestdata.fields.put("price", "1000");
			//requestdata.fields.put("discount", "0");
			requestdata.fields.put("quantity", "1");
			requestdata.fields.put("order_date", getTodayFull());		// YYYYMMDDXXXXXX
			// requestdata.fields.put("end_date", "00000002400"); // pppp
			requestdata.fields.put("end_date", "00000000005");		// junoci fix 4/3

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			// DataTransmission datatrans = new DataTransmission(hostname, port, hosturl);
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);
			if (responsedata.return_code.equals("00000"))
			{
				Log.d("sim", "return_code = " + responsedata.return_code);
				Log.d("sim", "return_message = " + responsedata.return_message);
				return true;
			}
			else
			{
				Log.d("sim", "return_code = " + responsedata.return_code);
				Log.d("sim", "return_message = " + responsedata.return_message);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 4.4 가입형상품 가입 요청 정보
	 * 4.4.1 데이터 요청
	 * RequestData를 통해 iTV R/S로 데이터를 요청한다.
	 * biz_code=400
	 * req_code=41
	 * fields.put(<KEY>,<VALUE>)
	 * 
	 * 인터페이스 정의서에 명시된 req_code를 통해 원하는 항목을 요청
	 * 4.4.2 반환 값
	 * ResponseData 객체를 통해 각 항목을 요청한 개수와 순서에 맞추어 반환 정보를 전달
	 * return_code = 결과코드
	 * return_message=결과메시지
	 * fields.get(<KEY>)
	 * 
	 * 정보가 없는 경우 각 항목은 "null"을 반환
	 * 결과 코드 값은 3.2결과 코드 정의를 참고
	 * 4.4.3 데이터 요청 및 응답
	 * APP->리턴 : RequestData를 통해 iTV R/S로 데이터를 요청
	 * biz_code = 400
	 * req_code = 41
	 * .
	 * .
	 * 리턴->APP : ResponseData 객체를 통해 요청한 개수와 순서에 맞추어 반환
	 * return_code = 결과코드
	 * return_message = 결과메시지
	 */

	/**
	 * 가입형 과금요청
	 * 4.4.4 가입형 상품 가입 요청과 반환 값의 예
	 */
	/*static */public int subscription_product_sendRequestData() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "400";
			requestdata.req_code = "41";
			requestdata.fields = new Hashtable();

			requestdata.fields.put("cont_no", cont_no);
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			// 지금안씀 requestdata.fields.put("user_id", "10020");
			requestdata.fields.put("item_id", item_id_m);  // 가입형 상품(월정액 ID )
			requestdata.fields.put("svc_reg_date", getToday());

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);
			if (responsedata.return_code.equals("00"))
			{
				Log.d("sim", "return_code = " + responsedata.return_code);
				Log.d("sim", "return_message = " + responsedata.return_message);
				return 0;
			}
			else
			{
				return -1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return -1;
	}

	/**
	 * 4.5. 가입형상품 가입/해지 정보
	 * 가입형상품 가입/해지 정보는 C&M Call-Center를 통해 가입형상품 가입/해지에 대한 정보를
	 * DP 시스템에서 필요시 요청하는 정보 항목이며, 정보 요청은 해당일자 기준이다.
	 * 4.5.1 데이터요청
	 * RequestData를 통해 iTV R/S로 데이터를 요청한다.
	 * biz_code=500
	 * req_code=51
	 * fields.put(<KEY>,<VALUE>)
	 *  인터페이스 정의서에 명시된 req_code를 통해 원하는 항목을 요청한다
	 * 
	 * 4.5.2 반환 값
	 * ResponseData객체를 통해 각 항목을 요청한 개수와 순서에 맞추어 반환 정보를 전달한다.
	 * return_code=결과코드
	 * return_message=결과메시지
	 * fields.get(<KEY>)
	 * 
	 *  정보가 없는 경우 각 항목은 "null" 을 반환한다.
	 *  결과 코드 값은 3.2결과 코드 정의를 참고한다.
	 * 
	 * 4.5.3 데이터 요청 및 응답
	 * APP->리턴 : RequestData를 통해 iTV R/S로 데이터를 요청
	 * biz_code = 500
	 * req_code = 51
	 * 
	 * 리턴->APP : ResponseData 객체를 통해 요청한 개수와 순서에 맞추어 반환
	 * return_code = 결과코드
	 * return_message = 결과메시지
	 */

	/*
	 * 
	 * 4.5.4 가입형상품 가입/해지 요청과 반환 값의
	 */
	/*static */public void subscription_register_sendRequestData() {
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "500";
			requestdata.req_code = "51";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("item_id", "0000001");
			requestdata.fields.put("request_date", "20060828");

			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);
			Log.d("sim", "return_code = " + responsedata.return_code);
			Log.d("sim", "return_message = " + responsedata.return_message);
			ArrayList returnList = new ArrayList();
			returnList = (ArrayList) responsedata.fields.get("return_list");
			http: // cafe.naver.com/ArticleRead.nhn?clubid=10050146&page=1&menuid=0&inCafeSearch=true&searchBy=0&query=3gs&includeAll=&exclude=&include=&exact=&searchdate=all&media=0&sortBy=date&articleid=99928772&referrerAllArticles=true
			for (int i = 0; i < returnList.size(); i++)
			{
				Hashtable ht = (Hashtable) returnList.get(i);
				if (BuildConfig.DEBUG)
				{
					Log.d("sim", "cont_id= " + ht.get("cont_id"));
					Log.d("sim", "item_id= " + ht.get("item_id"));
					Log.d("sim", "user_id= " + ht.get("user_id"));
					Log.d("sim", "stb_host_id=" + ht.get("stb_host_id"));
				}
				Log.d("sim", "svc_reg_date= " + ht.get("svc_reg_date"));
				Log.d("sim", "insert_date=" + ht.get("insert_date"));
				Log.d("sim", "update_date=" + ht.get("update_date"));
				Log.d("sim", "isuse_yn=" + ht.get("isuse_yn"));
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * 4.6 가입자 주문내역 정보
	 * 4.6.1 데이터요청
	 * RequestData를 통해 iTV R/S로 데이터를 요청한다.
	 * biz_code=500
	 * req_code=51
	 * fields.put(<KEY>,<VALUE>)
	 *  인터페이스 정의서에 명시된 req_code를 통해 원하는 항목을 요청한다
	 * 
	 * 4.6.2. 반환 값
	 * ResponseData객체를 통해 각 항목을 요청한 개수와 순서에 맞추어 반환 정보를 전달한다.
	 * return_code=결과코드
	 * return_message=결과메시지
	 * fields.get(<KEY>)
	 * 
	 *  정보가 없는 경우 각 항목은 "null" 을 반환한다.
	 *  결과 코드 값은 3.2결과 코드 정의를 참고한다.
	 * 
	 * 4.6.3. 데이터 요청 및 응답
	 * APP->리턴 RequestData를 통해 iTV R/S로 데이터를 요청
	 * biz_code = 600
	 * req_code = 61
	 * 
	 * 리터->APP RqsponseData 객체를 통해 요청한 개수와 순서에 맞추어 반환
	 * return_code = 결과코드
	 * return_message = 결과 메시지
	 */
	/*
	 * 4.6.4 가입자 주문내역 정보 요청과 반환 값의 예
	 */
	/*static */public String orders_info_sendRequestData() {
		String have_product = "N";
		RequestData requestdata = new RequestData();
		ResponseData responsedata = new ResponseData();
		try {
			// 요청데이타 생성
			requestdata.biz_code = "600";
			requestdata.req_code = "61";
			requestdata.fields = new Hashtable();
			requestdata.fields.put("app_id", app_id/* "SV00000044" */);
			requestdata.fields.put("stb_mac_addr", stb_mac_addr);
			requestdata.fields.put("from_date", getToday());
			requestdata.fields.put("to_date", getToday());

			//
			// 요청메시지 전송 후 응답 메시시 수신 후 출력
			//
			RSRequestClient datatrans = new RSRequestClient(dbs_hostname, dbs_port, hosturl);
			responsedata = datatrans.sendData(requestdata);
			Log.d("sim", "return_code = " + responsedata.return_code);
			Log.d("sim", "return_message = " + responsedata.return_message);
			ArrayList jnitemList = new ArrayList();
			ArrayList spitemList = new ArrayList();
			jnitemList = (ArrayList) responsedata.fields.get("jnitem_list");		//
			for (int i = 0; i < jnitemList.size(); i++) {
				Hashtable ht = (Hashtable) jnitemList.get(i);
				if (BuildConfig.DEBUG)
				{
					Log.d("sim", "cont_id= " + ht.get("cont_id"));
					Log.d("sim", "item_id= " + ht.get("item_id"));
					Log.d("sim", "pay_kind_cd= " + ht.get("pay_kind_cd"));
					Log.d("sim", "svc_reg_date= " + ht.get("svc_reg_date"));
				}
				Log.d("sim", "isuse_yn=" + ht.get("isuse_yn"));

				// PD0000024 && isuse_yn =Y
				if (ht.get("item_id").equals(item_id_m))		// 월정기상품권인가?
					have_product = (String) ht.get("isuse_yn");

			}

			/*
			 * 무시
			 * spitemList = (ArrayList) responsedata.fields.get("spitem_list");
			 * for(int i = 0;i< spitemList.size();i++){
			 * Hashtable ht = (Hashtable)spitemList.get(i);
			 * _LOG.d("sim", "cont_id= " +ht.get("cont_id"));
			 * _LOG.d("sim", "item_id= " +ht.get("item_id"));
			 * _LOG.d("sim", "pay_kind_cd= " +ht.get("pay_kind_cd"));
			 * _LOG.d("sim", "isuse_yn=" +ht.get("isuse_yn"));
			 * _LOG.d("sim", "svc_reg_date= " +ht.get("svc_reg_date"));
			 * }
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return have_product;
	}

}
