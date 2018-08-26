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
 * filename	:	__VASS.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api
 *    |_ __VASS.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.api.gtv;

import android.os.Handler;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-04-26
 */
public class __VASS extends VASS2 {
	/*
	 * VASS
	 */
	///*final */static public String VASS_REQUEST_PAGE = "http://iptvisu.lgdacom.net:8900/servlets/CommSvl?";
	///*final */static public String VASS_REQUEST_PAGE = "http://iptvisutb.lgdacom.net:8900/servlets/CommSvl?";
	// ISUHandler.isu_hostname = "123.140.17.223" ; //test BED
	// ISUHandler.isu_port = 8900;
	/**
	 * ISU 호스트정보
	 */
	/*final */static public String VASS_REQUEST_PAGE = "http://123.140.17.223:8900/servlets/CommSvl?";
	///*final */static public String M_PPV_PURCHASE = "purchasePpvProduct";
	///*final */static public String M_PPV_CHECK = "checkPurchasePpvProduct";
	///*final */static public String M_PPM_PURCHASE = "purchasePpxProduct";
	///*final */static public String M_PPM_CHECK = "checkPurchasePpmProduct";
	///*final */static public String VASS_PPM_PROMISE = "10";
	///*final */static public String M_PASSWORD_CHECK = "checkPurchasePw";

	//테스트베드 장비 123.140.17.223 8000
	//시범서비스 이후 정합용 상용장비 123.140.16.78 8000 상용(시범)서비스
	// private static String hostname = "192.168.61.131"; // from ch lee
	// public static int port = 80; // from ch lee
	// _SIMClientHandlerLGU.dbs_hostname = "123.140.17.223";
	// _SIMClientHandlerLGU.dbs_port = 8000;
	/**
	 * RSi 호스트정보
	 */
	static public String hostname = "123.140.17.223";        // from TEST BED prof
	static public int port = 8000;
	static public String hosturl = "/LGDacomIPTV/SIMListener";

	static String app_id = "90";    // from junoci;/
	static String cont_no = "";  // 가입계약번호 : 얻어오는 것
	static String stb_mac_addr = "";

	static String B0000 = "정상등록완료";
	static String B0001 = "가입자 없음";
	static String B0002 = "가입자 일시정지";
	static String B0003 = "가입자 이용중지";
	static String B0004 = "가입자 변경 처리중";
	static String B0005 = "가입자 해지 처리중";
	static String B0006 = "XCION 상품코드 매핑 오류";
	static String B0007 = "IPTV 서비스 미사용";
	static String B0008 = "요청상품 기신청";
	static String B0009 = "요청상품 기사용";
	static String B0010 = "시스템 점검중";
	static String B0011 = "가입계약번호 오류";
	static String B0012 = "MAC Address 오류";
	static String B0013 = "중복채널 존재";
	static String C9999 = "기타사유";
	static String B0014 = "가입된 서비스/상품 없슴";
	static String B0015 = "IPTV 서비스 미 가입";
	static String B0016 = "IPTV 서비스 미 등록";
	static String B0017 = "등록 확인 오류";
	static String B0018 = "주민번호 또는 사업자번호 또는 가입자번호가 다릅니다.";
	static String B0019 = "월 1회 가입 제한 ";
	static String B0020 = "해당 상품 없음(NO Data)";
	static String B0021 = "Xcion 연동 Time Out";
	static String B0022 = "상품가입불가";
	static String B0023 = "부가서비스 해지 실패";
	static String _00000 = "정상처리";
	static String _10000 = "메시지 오류(알 수 없는 오류)";
	static String _10001 = "정의 되지 않은 Biz Code.";
	static String _10002 = "정의되지 않은 Req Code.";
	static String _10003 = "필수 항목 누락.";
	static String _10004 = "데이터 타입 오류.";
	static String _20000 = "검색 중 오류가 발생하였습니다. (알 수 없는 DB 오류)";
	static String _20001 = "DB Connection 에러";
	static String _20002 = "중복 등록 오류";
	static String _20003 = "수정, 삭제 실패 (미등록 정보에 대한 수정, 삭제 시도)";
	static String _20004 = "검색 결과가 없음";
	static String _20005 = "데이터 타입 오류";
	static String _30000 = "알 수 없는 오류가 발생하였습니다.";
	static String _30001 = "PVS 시스템과 연동 중 오류가 발생하였습니다.";
	static String _30002 = "PVS DB 와 연동 중 오류가 발생하였습니다.";
	static String _40000 = "네트워크 연결 중 오류가 발생하였습니다. ";
	static String _40001 = "메시지 수신 중 오류가 발생하였습니다.";
	static String _40002 = "해당 프로세스 접근 중 오류가 발생하였습니다.";
	static String _40003 = "객체를 수신하지 못하였습니다.";
	static String _50000 = "사용자 인증 처리 중 알 수 없는 오류가 발생하였습니다.";
	static String _50001 = "서비스에 가입하지 않은 사용자 입니다.";
	static String _50002 = "서비스 가입 처리 중 입니다.";
	static String _50003 = "서비스 가입 처리에 실패하였습니다. 고객 센터에 문의하세요.";
	static String _50004 = "존재하지 않는 상품에 인증요청을 하셨습니다.";
	static String _50005 = "서비스 정지 사용자 입니다.";
	static String _50006 = "서비스 해지 사용자 입니다";
	static String _60000 = "종량형 상품 구매 처리 중 알 수 없는 오류가 발생하였습니다.";
	static String _60001 = "존재하지 않는 상품을 구매 요청 하셨습니다.";
	static String _70000 = "가입형 서비스 가입 이력 처리 중 알 수 없는 오류가 발생하였습니다.";
	static String _70001 = "존재하지 않는 상품 이력을 요청 하셨습니다.";
	static String _99999 = "알 수 없는 오류";

	public __VASS(Handler h) {
		super(h);
	}
}
