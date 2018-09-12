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
 * 2014 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	BaseApplication2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app
 *    |_ BaseApplication2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Map;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener.onKPnnnnError;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener.onKPnnnnFail;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener.onKPnnnnSuccess;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.impl.IBaseDialog;
import kr.kymedia.karaoke.kpop.webview;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * <pre>
 * </pre>
 * 
 * @author isyoon
 * @since 2014. 12. 5.
 * @version 1.0
 */
class BaseApplication2 extends BaseApplication {
	final protected String __CLASSNAME__ = "[Iab]" + (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * 기본KPnnnn 절대정적할당하지 않는다~<br>
	 */
	private KPnnnn KP_0000 = null;
	/**
	 * 로그기록:KP_4012 마켓플레이스 광고 진입 버튼(이미지) 선택 시 사용자
	 */
	private KPnnnn KP_4012 = null;
	/**
	 * <pre>
	 * 기존:KP_4011 holic 상품권 구매(홀릭차감)<br>
	 * 신규:KP_4003(이용권 구매:KP_4002 -> KP_4003 연동)<br>
	 * 절대정적할당하지 않는다~
	 * </pre>
	 */
	private KPnnnn KP_4003 = null;
	/**
	 * <pre>
	 * 기존:KP_4010 상품권 구매 (In-App Billing) <br> 
	 * 신규:KP_4005(충전 상품 구매:KP_4004 -> KP_4005 연동)
	 * 절대정적할당하지 않는다~
	 * </pre>
	 */
	private KPnnnn KP_4005 = null;
	// /**
	// * 로그기록:KP_5022 카카오톡 진입 버튼(이미지) 선택 시 사용자
	// */
	// private KPnnnn KP_5022 = null;
	/**
	 * <pre>
	 * KP_3011 녹음곡 공개설정/삭제
	 * 절대정적할당하지 않는다~
	 * </pre>
	 */
	private KPnnnn KP_3011 = null;
	/**
	 * KP_6032 오디션 개최권 구매
	 */
	@Deprecated
	private KPnnnn KP_6032;
	/**
	 * 로그기록:재생/녹음/업로드-오류보고
	 */
	private KPnnnn KP_9999 = null;

	/**
	 * 재생/녹음/업로드-오류보고 여부확인<br>
	 * 기본값:true
	 */
	boolean kp_error_report = true;

	/**
	 * UI처리용핸들러 Need handler for callbacks to the UI thread
	 */
	// final protected Handler mHandlerUI = new Handler();
	protected HandlerUI mHandlerUI;

	public static class HandlerUI extends Handler {
		WeakReference<BaseApplication2> m_HandlerObj;

		HandlerUI(BaseApplication2 handlerobj) {
			m_HandlerObj = new WeakReference<BaseApplication2>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseApplication2 handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			super.handleMessage(msg);
		}
	}

	public void post(Runnable r) {
		if (mHandlerUI != null) {
			mHandlerUI.post(r);
		}

	}

	public void postDelayed(Runnable r, long delayMillis) {
		if (mHandlerUI != null) {
			mHandlerUI.postDelayed(r, delayMillis);
		}

	}

	protected HandlerQuery mHandlerQuery;

	static class HandlerQuery extends Handler {
		WeakReference<BaseApplication2> m_HandlerObj;

		HandlerQuery(BaseApplication2 handlerobj) {
			m_HandlerObj = new WeakReference<BaseApplication2>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseApplication2 handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			handlerobj.onKPnnnnResult(msg);
		}
	}

	private void onKPnnnnResult(Message msg) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + msg);


		int what = msg.what;
		String r_opcode = msg.getData().getString("opcode");
		String r_code = msg.getData().getString("result_code");
		String r_message = msg.getData().getString("result_message");
		String r_info = msg.getData().getString("result_info");

		try {
			onKPnnnnResult(what, r_opcode, r_code, r_message, new KPItem(r_info));
		} catch (Exception e) {

			e.printStackTrace();
		}
		// }

	}

	/**
	 * <pre>
	 * 재생관련전문목록
	 * @see KPnnnn#KP_1016(String, String, String, String, String)
	 * @see KPnnnn#KP_1016(String, String, String, String, String, String)
	 * @see KPnnnn#KP_2016(String, String, String, String, String)
	 * </pre>
	 */
	final public static String KP_PLAY[] = { "KP_1016", "KP_2016", };

	/**
	 * <pre>
	 * 로딩처리제외목록
	 * @see BaseFragment2#KPnnnnResult(int, String, String, String, String)
	 * @see BaseActivity2#startLoading(String, String)
	 * @see BaseActivity2#getBaseActivity().stopLoading(String, String)
	 * @see KPnnnn#KP_1012(String, String, String, String, String, String, String)
	 * @see KPnnnn#KP_4013(String, String, String, String)
	 * @see KPnnnn#KP_4014(String, String, String)
	 * @see KPnnnn#KP_9003(String, String, String, String, String, String)
	 * @see KPnnnn#KP_9999(String, String, String, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.TYPE, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.LEVEL, String, String, String, int, int, String, String, KPItem, KPItem, String)
	 * </pre>
	 */
	final public static String KP_HIDE_LOADING_STOP[] = { "KP_1012", "KP_4013", "KP_4014", "KP_9003", "KP_9999", };

	/**
	 * 조회결과오류표시 기능: 1. 로딩화면중지 2. 오류내용팝업 "99999"(Unknown Error) 발생시 처리추가
	 * 
	 * <pre>
	 * 44444 : UnknownHostException - 네트워크(?) 오류관련
	 * 55555 : UnknownServiceException - 웹(?)서비스 오류관련
	 * 66666 : SocketException - 소켓(?) 오류관련 
	 * 77777 : HttpResponseException - HTTP 오류관련
	 * 88888 : DataParsingErorr - JSON데이터 오류관련
	 * 99999 : UnknownDataError - 그외 오류관련
	 * 
	 *  00000	성공	토스트 메시지 또는 무시	공통	Success
	 *  		컨펌 팝업	KP_1011 - 녹음곡 다운로드	녹음곡 업로드를 완료하였습니다.\n녹음곡은 MY HOLIC 메뉴에서 확인해주세요.
	 *  		컨펌 팝업	KP_1011 - 녹음곡 다운로드	오디션 참여가 완료되었습니다.\n오디션 화면으로 이동하시겠습니까?
	 *  		토스트 메시지	KP_6050 - 애창곡 등록/삭제	애창곡이 등록되었습니다.
	 *  		토스트 메시지	KP_6050 - 애창곡 등록/삭제	애창곡이 삭제되었습니다.
	 *  		팝업	KP_3011 - 녹음곡 공개/비공개 설정	삭제 권한이 없습니다.
	 *  		토스트 메시지	KP_1013 - 반주곡 재생	1분 미리듣기만 가능합니다.
	 *  		팝업	KP_5020 - 회원 가입	공짜 이용권 받아가세요!\n선물받은 Holic으로 이용권을 공짜로 받아가세요!
	 *  		팝업	KP_5020 - 회원 가입	이미 가입된 기기입니다. Holic은 무료충전소에서 충전가능합니다
	 *  00900	팝업 메시지			등록된 회원이 아닙니다. 확인바랍니다.
	 *  				해당 회원의 정보가 존재하지 않습니다.
	 *  				비밀번호 수정에 실패하였습니다.
	 *  				이미 등록된 디바이스 입니다.
	 *  				해당 이메일 계정으로 임시 비밀번호가 발송되었습니다.
	 *  				회원전용 메뉴입니다.
	 *  				로그인해주세요!
	 *  				상품 결제에 실패하였습니다.
	 *  				이용권 사용자는 기간 만료 후 구매하실 수 있습니다.
	 *  				Holic이 부족합니다.\nHolic을 충전해서 다시 시도해주세요.
	 *  				이미 사용중인 이용권이 존재합니다.
	 *  				2개 이상의 계정 생성을 위해서는\n인증 절차를 완료하셔야 합니다.\n등록하신 이메일 주소로 인증메일을\n보내드렸습니다.
	 *  				오디션 참여곡은 삭제가 불가능합니다.
	 *  				잘못된 조회 명령입니다.
	 *  00901	토스트 메시지			아이디가 일치하지 않습니다.
	 *  				비밀번호가 일치하지 않습니다.
	 *  				등록하신 닉네임은 사용중입니다.
	 *  				검색 결과가 존재하지 않습니다.
	 *  				이미 다른 무료곡을 등록했습니다.
	 *  				해당 회원의 최고 하트 보유 녹음곡이 없습니다.
	 *  				해당 곡의 최대 추천수 보유 녹음곡이 없습니다.
	 *  				해당 곡이 만료되었거나 비공개 처리되었습니다.
	 *  				자신의 녹음곡에는 heart를 줄 수 없습니다.
	 *  				공개 설정 변경은 개최자만 가능합니다.
	 *  				공개로 전환되었습니다.
	 *  				비공개로 전환되었습니다.
	 *  				오디션 개최자만 변경이 가능합니다.
	 *  				참가자가 최소 1명은 존재하여야 합니다.
	 *  				[NICKNAME]님이 탈락되었습니다.
	 *  				진행중인 오디션이 아닙니다. 변경 하실 수 없습니다.
	 *  				해당 오디션은 이미 1위곡이 선정되었습니다.
	 *  				오디션이 마감되었습니다.
	 *  				비공개 오디션입니다.
	 *  				오디션 참여곡은 삭제가 불가능합니다.
	 *  				본인 전용 메뉴입니다. 접근이 제한됩니다
	 *  				쪽지가 발송되었습니다
	 *  				수신 차단이 해제 되었습니다.
	 *  				자신은 수신차단 할 수 없습니다.
	 *  				상품 목록이 없습니다.
	 *  				이용권 구매정보가 없습니다.
	 *  				해당 이메일은 이미 존재합니다.
	 *  				이미 사용하고 있는 E-Mail 입니다.
	 *  				이미 사용하고 있는 닉네임 입니다.
	 *  				잘못된 비밀번호 입니다.
	 *  				본인만 삭제가 가능합니다.
	 *  				오디션 참여곡은 변경이 불가능합니다.
	 *  				녹음곡 등록에 실패하였습니다.[ERROR:1]
	 *  				녹음곡 등록에 실패하였습니다.[ERROR:2]
	 *  				녹음곡 아이디 생성에 실패하였습니다.
	 *  				삭제 권한이 없습니다.
	 *  				게시물의 내용을 등록하세요.
	 *  				수신차단된 회원입니다.
	 *  				금지어는 등록 할 수 없습니다.
	 *  				사용자가 많습니다. 다시 시도해 주세요.
	 *  				결과가 존재하지 않습니다.
	 *  99980	팝업 메시지	컨펌 팝업	KP_1013 - 반주곡 재생	1분 미리듣기만 가능합니다.\n보유홀릭 : 10Holic\n 10Holic으로 1시간 이용권이 가능합니다.\nHolic을 이용하시겠습니까?
	 *  99981	팝업 메시지	컨펌 팝업	KP_1013 - 반주곡 재생	보유홀릭 : 1Holic\n1분 미리듣기만 가능합니다\nHolic을 무료충전하시겠습니까?
	 *  99994	메인 팝업 이미지	메인 팝업(이벤트/알림) 이미지 오픈	KP_0001 - 메인 정보	
	 *  99996	팝업 메시지	팝업 - 마켓 리뷰로 이동 기능		[이벤트]마켓에 리뷰 작성하기
	 *  99997	팝업 메시지	컨펌 팝업		최신 버전이 존재합니다. 업데이트하시겠습니까?
	 *  99998	팝업 메시지	팝업 - 강제 업데이트 처리		KPOP을 이용하기 위해서는 업데이트해야 합니다
	 *  2.1. Holic으로 상품구매가 가능할 경우
	 *  - r_code    : "99980"
	 *  - r_message    : "보유홀릭 : 100Holic\n100Holic으로 7일 이용이 가능합니다.\nHolic을 이용하시겠습니까?"
	 *  - 버튼 : "Yes"/"No"
	 *      : "Yes" -> KP_4011 프로토콜 호출
	 *      : "No" -> 팝업 닫힘
	 *  2.2. Holic으로 상품구매가 불가능할 경우 
	 *  - r_code    : "99981"
	 *  - r_message    : "보유홀릭 : 1Holic\n1분 미리듣기만 가능합니다\nHolic을 무료충전하시겠습니까?"
	 *  - 버튼 : "Yes"/"No"
	 *      : "Yes" -> Ticket 목록 이동(KP_4001)
	 *      : "No" -> 팝업 닫힘                
	 *  KPOP HOLIC 메인 팝업 이미지 노출 건    * KP_0000 프로토콜의 "info" 에 팝업 이미지 출력 정보 추가. 
	 *  - r_code    : "99994"
	 *  1. "is_pop" - "Y" / "N" 
	 *  		- "Y" : 메인에 팝업 이미지 노출 
	 *  		- "N" : 메인에 팝업 이미지 미노출 
	 *  
	 *  2. "btn_type" - "0", "1", "2" 
	 *  		- "0" : "확인" 버튼 하단에 노출 
	 *  		- "1" : "닫기" 버튼 하단에 노출 
	 *  		- "2" : "확인", "닫기" 버튼 하단에 노출 
	 *  		
	 *  3. "url_img_pop" : 메인 팝업 이미지 URL 
	 *  
	 *  4. "url_pop" : 메인 팝업 이미지의 "확인" 버튼 선택 시 링크 주소 
	 *  
	 *  
	 *  -- "닫기" 버튼 선택 시는 팝업 이미지만 닫음.  	 *
	 * </pre>
	 */
	protected void KPnnnnResult(final int what, final String r_opcode, final String r_code, final String r_message, final String r_info) {

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + p_opcode
		// + "[RESULT_CODE]" + r_code);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[WHAT]" + what);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[OPCODE]" + r_opcode);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[RESULT_CODE]" + r_code);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "[RESULT_MESSAGE]" + r_message);

		if (what == _IKaraoke.STATE_DATA_QUERY_START) {
			return;
		}

		try {
			if (!TextUtil.isEmpty(r_info)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[RESULT_INFO][OK]\n" + (new KPItem(r_info)).toString(2));
			} else {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[RESULT_INFO][NG]" + r_info);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		boolean stopLoading = true;

		for (String s : KP_HIDE_LOADING_STOP) {
			if (s.equalsIgnoreCase(r_opcode)) {
				stopLoading = false;
			}
		}

		if (stopLoading) {
			getBaseActivity().stopLoading(__CLASSNAME__, getMethodName() + r_opcode + ":" + r_code);
		}

		if (KP_0000 == null) {
			return;
		}

		String title = getString(R.string.alert_title_error);
		String message = getString(R.string.errmsg_network_data);

		KPItem info = null;
		try {
			info = new KPItem(r_info);
		} catch (Exception e) {

			e.printStackTrace();

			message += "\n\n(" + r_opcode + ":" + _IKaraoke.ERROR_CODE_JSONDATAPARSINGERORR + ")";
			message += "\n" + Log2.getStackTraceString(e);

			if (_IKaraoke.DEBUG) {
				message += "\n\n[디버그정보]\n" + Log2.getStackTraceString(e);
			}

			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_retry), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					refresh();
				}
			}, null, null, true, null);
		}

		String value = "";
		if (info != null) {
			value = info.getValue("result_url");
		}
		final String result_url = value;

		if (info != null) {
			value = info.getValue(LOGIN.KEY_GOODSCODE);
		}
		final String goodscode = value;

		// if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "\n" + (info != null ? info.toString(2) : info));

		if (("00000").equalsIgnoreCase(r_code)) {
		} else if (("00900").equalsIgnoreCase(r_code)) {
			// 링크연결팝업 (UCC)
			showURLLinkDialog(r_message, result_url);
		} else if (("00901").equalsIgnoreCase(r_code)) {
			// 단순공지팝업 (UCC)
			popupToast(r_message, Toast.LENGTH_LONG);
		} else if (("00902").equalsIgnoreCase(r_code)) {
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), r_message, result_url, "POST", false);
		} else if (("00903").equalsIgnoreCase(r_code)) {
			openWebView(webview.class, getString(R.string.M1_MENU_INFO), getString(R.string.M2_MENU_INFO), r_message, result_url, "POST", true);
		} else if (("50001").equalsIgnoreCase(r_code)) {
		} else if (("40050").equalsIgnoreCase(r_code)) {
			// Holic으로 상품구매 오류발생시
			title = getString(R.string.alert_title_error);
			message = r_message;
			message += "\n(" + r_opcode + ":" + r_code + ")";
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
		} else if (("99980").equalsIgnoreCase(r_code)) {
			// 2.1. Holic으로 상품구매가 가능할 경우
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					getBaseActivity().startLoading(__CLASSNAME__, getMethodName());
					KP_4003(goodscode, "", "", "HOLIC", "I");
				}
			}, getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					if (getCurrentFragment() instanceof PlayFragment) {
						((_PlayFragment) getCurrentFragment()).play();
					}
				}
			}, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					if (getCurrentFragment() instanceof PlayFragment) {
						((_PlayFragment) getCurrentFragment()).play();
					}
				}
			});
		} else if (("99981").equalsIgnoreCase(r_code)) {
			// 2.2. Holic으로 상품구매가 불가능할 경우
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.btn_title_yes), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					onOptionsItemSelected(R.id.menu_shop_charge, "", true);
				}
			}, getString(R.string.btn_title_no), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					if (getCurrentFragment() instanceof PlayFragment) {
						((_PlayFragment) getCurrentFragment()).play();
					}
				}
			}, true, new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {

					if (getCurrentFragment() instanceof PlayFragment) {
						((_PlayFragment) getCurrentFragment()).play();
					}
				}
			});
		} else if (("99900").equalsIgnoreCase(r_code)) {
			// 로그인확인
			Bundle args = new Bundle();
			// args.putString("message", getString(R.string.warning_nologin_preview));
			args.putString("message", r_message);

			if (!isLoginUser()) {
				getBaseActivity().showDialog2(IBaseDialog.DIALOG_ALERT_NOLOGIN_YESNO, args);
			}
		} else if (("99994").equalsIgnoreCase(r_code)) {
			// //프래그먼트활성화시...
			// if (isVisible() && isRestricted()) {
			// //KPOP HOLIC 메인 팝업 이미지 노출 건
			// onContextItemSelected(R.id.context_popup, KP_nnnn, KP_index, true);
			// }
		} else if (("99995").equalsIgnoreCase(r_code)) {
			// 단순공지사항
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), null, null, null, true, null);
			// } else if (("99996").equalsIgnoreCase(r_code)) {
			// //리플입력유도
			// showDialogAlert(getString("alert_title_confirm"), r_message,
			// getString("alert_title_confirm"), mUpdatePListener, null, null, true, null);
			// } else if (("99997").equalsIgnoreCase(r_code)) {
			// //업데이트확인
			// showUpdateDialog(r_message);
		} else if (("99998").equalsIgnoreCase(r_code)) {
			// 강제업데이트
			popupToast(r_message, Toast.LENGTH_LONG);
			openUpdate(result_url);
		} else {

			String e_message = _IKaraoke.ERROR_MESSAGE_UNKOWNDATAERROR;
			if ((_IKaraoke.ERROR_CODE_IOEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_IOEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_UNKNOWNHOSTEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_UNKNOWNHOSTEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_UNKNOWNSERVICEEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_UNKNOWNSERVICEEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_TIMEOUTEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_TIMEOUTEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_SOCKETEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_SOCKETEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_HTTPRESPONSEEXCEPTION).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_HTTPRESPONSEEXCEPTION;
			} else if ((_IKaraoke.ERROR_CODE_JSONDATAPARSINGERORR).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_JSONDATAPARSINGERORR;
			} else if ((_IKaraoke.ERROR_CODE_UNKOWNDATAERROR).equalsIgnoreCase(r_code)) {
				e_message = _IKaraoke.ERROR_MESSAGE_UNKOWNDATAERROR;
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "\n[ERROR_MESSAGE]" + e_message);

			message += "\n\n(" + r_opcode + ":" + r_code + ")";
			message += "\n" + e_message;

			if (info != null) {
				if (TextUtil.isEmpty(info.getValue("HttpStatusCode"))) {
					message += "\n\n(" + info.getValue("HttpStatusCode") + ")";
				}
				if (TextUtil.isEmpty(info.getValue("HttpStatusMessage"))) {
					message += "\n" + info.getValue("HttpStatusMessage");
				}
			}

			if (_IKaraoke.DEBUG) {
				message += "\n\n[디버그정보]\n" + r_message;
			}

			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_retry), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					refresh();
				}
			}, null, null, true, null);
		}

	}

	private void openUpdate(final String url) {

		if (getCurrentFragment() != null) {
			getCurrentFragment().openUpdate(url);
		}

	}

	protected void onOptionsItemSelected(int id, String menu_name, boolean open) {

		if (getCurrentFragment() != null) {
			getCurrentFragment().onOptionsItemSelected(id, menu_name, open);
		}
	}

	private void openWebView(Class<?> cls, String m1, String m2, String menu_name, String url, String method, boolean browser) {

		if (getCurrentFragment() != null) {
			getCurrentFragment().openWebView(cls, m1, m2, menu_name, url, method, browser);
		}

	}

	private void showURLLinkDialog(String r_message, String result_url) {

		if (getCurrentFragment() != null) {
			getCurrentFragment().showURLLinkDialog(r_message, result_url);
		}
	}

	private void onKPnnnnResult(final int what, final String r_opcode, final String r_code, final String r_message, final KPItem r_info) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.i(__CLASSNAME__, getMethodName() + getCurrentFragment());


		if (getCurrentFragment() != null) {
			getCurrentFragment().onKPnnnnResult(what, r_opcode, r_code, r_message, r_info.toString());
		} else {
			KPnnnnResult(what, r_opcode, r_code, r_message, r_info.toString());
		}
	}

	@Override
	public void onCreate() {

		super.onCreate();
		mHandlerUI = new HandlerUI(this);
		mHandlerQuery = new HandlerQuery(this);
	}

	/**
	 * 
	 * <pre>
	 * </pre>
	 * 
	 * @see BaseActivity3#KP_param()
	 * @see BaseFragment3#KP_param()
	 */
	@Override
	public void KP_param() {

		super.KP_param();

		KP_init();

		KP_hand();
	}

	public KPnnnn KP_init(Context context, KPnnnn KP_xxxx) {

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + KP_xxxx);
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "" + context);
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "" + KP_xxxx);

		if (KP_xxxx == null) {
			KP_xxxx = new KPnnnn(context, mHandlerQuery, KPparam, p_mid, p_passtype, p_passcnt);
		} else {
			KP_xxxx.KP_nnnn(KPparam, p_mid, p_passtype, p_passcnt, "", "");
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_xxxx);

		return KP_xxxx;
	}

	public void KP_init() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		KP_0000 = KP_init(this, KP_0000);

		KP_4003 = KP_init(this, KP_4003);

		KP_4005 = KP_init(this, KP_4005);

		KP_4012 = KP_init(this, KP_4012);

		KP_3011 = KP_init(this, KP_3011);

		KP_9999 = KP_init(this, KP_9999);
	}

	public void KP_hand() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (KP_4003 != null) {
			KP_4003.setHandler(mHandlerQuery);

			KP_4003.setOnSuccessListener(new onKPnnnnSuccess() {

				@Override
				public void onSuccess(int what, String r_opcode, String r_code, String r_message, KPItem r_info) {
					if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + r_opcode + "[RESULT_CODE]" + r_code);

					KP4003();
					onKPnnnnResult(what, r_opcode, r_code, r_message, r_info);
				}
			});

			KP_4003.setOnErrorListener(new onKPnnnnError() {

				@Override
				public void onError(int what, String r_opcode, String r_code, String r_message, KPItem r_info) {
					if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + r_opcode + "[RESULT_CODE]" + r_code);

					onKPnnnnResult(what, r_opcode, r_code, r_message, r_info);
				}
			});

			KP_4003.setOnFailListener(new onKPnnnnFail() {

				@Override
				public void onFail(int what, String r_opcode, String r_code, String r_message, KPItem r_info) {
					if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + r_opcode + "[RESULT_CODE]" + r_code);

					onKPnnnnResult(what, r_opcode, r_code, r_message, r_info);
				}
			});
		}

		if (KP_4005 != null) {
			KP_4005.setHandler(mHandlerQuery);

			KP_4005.setOnSuccessListener(new onKPnnnnSuccess() {

				@Override
				public void onSuccess(int what, String r_opcode, String r_code, String r_message, KPItem r_info) {
					if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + r_opcode + "[RESULT_CODE]" + r_code);

					onKPnnnnResult(what, r_opcode, r_code, r_message, r_info);
				}
			});

			KP_4005.setOnErrorListener(new onKPnnnnError() {

				@Override
				public void onError(int what, String r_opcode, String r_code, String r_message, KPItem r_info) {
					if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + r_opcode + "[RESULT_CODE]" + r_code);

					onKPnnnnResult(what, r_opcode, r_code, r_message, r_info);
				}
			});

			KP_4005.setOnFailListener(new onKPnnnnFail() {

				@Override
				public void onFail(int what, String r_opcode, String r_code, String r_message, KPItem r_info) {
					if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "[WHAT]" + what + "[OPCODE]" + r_opcode + "[RESULT_CODE]" + r_code);

					onKPnnnnResult(what, r_opcode, r_code, r_message, r_info);
				}
			});
		}

		if (KP_4012 != null) {
			KP_4012.setHandler(mHandlerQuery);
		}

		if (KP_3011 != null) {
			KP_3011.setHandler(mHandlerQuery);
		}

	}

	/**
	 * @see kr.kymedia.karaoke.api.KPnnnn#KP_0000(String, String, String, String, boolean, Map)
	 * 
	 */
	protected void KP_0000(String mid, String m1, String m2, String opcode, boolean clear, Map<String, String> params) {
		if (KP_0000 != null) {
			KP_0000.KP_0000(mid, m1, m2, opcode, clear, params);
		}
	}

	/**
	 * <pre>
	 * 기존:KP_4011 holic 상품권 구매(홀릭차감)<br>
	 * 신규:KP_4003(이용권 구매:KP_4002 -> KP_4003 연동)<br>
	 * 절대정적할당하지 않는다~
	 * </pre>
	 * 
	 * @see kr.kymedia.karaoke.api.KPnnnn#KP_4003(String, String, String, String, String, String, String, String)
	 */
	protected void KP_4003(String goodscode, String tid, String is_pay, String pay_type, String iu_type) {
		if (KP_4003 != null) {
			// KP_4003.KP_4003(p_mid, p_m1, p_m2, goodscode, "", "", "HOLIC", "I");
			KP_4003.KP_4003(p_mid, getString(R.string.M1_MENU_SHOP), getString(R.string.M2_SHOP_TICKET), goodscode, tid, is_pay, pay_type, iu_type);
		}
	}

	void KP4003() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (KP_4003 == null) {
			return;
		}

		KPItem info = KP_4003.getInfo();

		if (info == null) {
			return;
		}

		// p_passtype = info.getString(LOGIN.KEY_PASSTYPE);
		p_passtype = info.getValue(LOGIN.KEY_PASSTYPE) == null ? p_passtype : info.getValue(LOGIN.KEY_PASSTYPE);
		p_expire_date = info.getValue("expire_date") == null ? p_expire_date : info.getValue("expire_date");

	}

	/**
	 * <pre>
	 * 기존:KP_4010 상품권 구매 (In-App Billing) <br> 
	 * 신규:KP_4005(충전 상품 구매:KP_4004 -> KP_4005 연동)
	 * 절대정적할당하지 않는다~
	 * </pre>
	 * 
	 * @see kr.kymedia.karaoke.api.KPnnnn#KP_40050(String, String, String, String, String, String, String, String, String)
	 */
	protected void KP_4005(String goodscode, String tid, String is_pay, String pay_type, String iu_type, String list) {
		if (KP_4005 != null) {
			// KP_4005.KP_4010(p_mid, p_m1, p_m2, goodescode, tid, is_pay, "MARKET", "U");
			// KP_4005.KP_4010(p_mid, p_m1, p_m2, goodescode, tid, is_pay, "MARKET", "I");
			KP_4005.KP_40050(p_mid, getString(R.string.M1_MENU_SHOP), getString(R.string.M2_SHOP_CHARGE), goodscode, tid, is_pay, pay_type, iu_type, list);
		}
	}

	/**
	 * @see kr.kymedia.karaoke.api.KPnnnn#KP_4012(String, String, String, String)
	 * 
	 */
	protected void KP_4012(String mid, String m1, String m2, String type) {
		if (KP_4012 != null) {
			KP_4012.KP_4012(mid, m1, m2, type);
		}
	}

	protected void KP_3011(String mid, String m1, String m2, String mode, String record_id) {
		if (KP_3011 != null) {
			KP_3011.KP_3011(mid, m1, m2, mode, record_id);
		}
	}

	/**
	 * 재생/녹음/업로드-오류보고
	 * 
	 * @see #kp_error_report
	 * @see #KP_9999
	 * @see kr.kymedia.karaoke.api.KPnnnn#KP_9999(String, String, String, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.TYPE, kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.LEVEL, String, String, String, int, int, String, String, KPItem, KPItem, String)
	 */
	protected void KP_9999(String mid, String m1, String m2, MEDIAERROR.TYPE type, MEDIAERROR.LEVEL level, String song_id, String record_id, String where, int what, int extra,
			String wname, String ename, KPItem info, KPItem list, String message) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + kp_error_report);

		if (!kp_error_report) {
			return;
		}

		KP_9999.KP_9999(mid, m1, m2, type, level, song_id, record_id, where, what, extra, wname, ename, info, list, message);
	}
}
