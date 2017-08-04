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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV.BTV
 * filename	:	MainX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ MainX.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.btv;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <a href="http://pms.skdevice.net/redmine/issues/3430">3430 금영노래방앱에서 노래 실행 시 반주가 끊겨 들리는 현상(v9.0.21)</a><br>
 * <a href="http://pms.skdevice.net/redmine/issues/3479">3479 노래 재생 후, 메뉴에서 RCU ( > )방향키 계속 누르면 노래가 종료되면서 메인으로 가는 현상</a><br>
 * <p/>
 * 기종별예외처리
 * <p/>
 * <pre>
 * AOSP(BHX-S300)/AOSP(BKO-S300)
 * 	키연속입력차단:디버그시제외
 * 	재생시 중지제외한 모든 키차단:디버그시제외
 * </pre>
 * <p/>
 * <pre>
 * UHD(BHX-UH200)/AOSP(BHX-S300)/AOSP(BKO-S300)
 * 	반주곡 재생시 이미지처리
 * UHD(BHX-UH200)
 * AOSP(BHX-S300)
 * <a href="http://pms.skdevice.net/redmine/issues/3482">3482 일부노래 자막 하단이 잘려서 출력되는 현상</a>
 * 	48859 - '씨스타 - Shake It' 노래 부르기 진행 중 일부 가사 하단부분이 잘려서 출력됩니다.
 * AOSP(BKO-S300)
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 6. 16.
 */
class Main3 extends Main2X {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	protected boolean isBHXUHXXX() {
		return isBHXUH200();
	}

	/**
	 * UHD(BHX-UH200)
	 */
	protected boolean isBHXUH200() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ("BHX-UH200").equals(p_model) + ":" + p_model);

		return ("BHX-UH200").equals(p_model);
	}

	protected boolean isBHXSXXX() {
		return isBHXS300();
	}

	/**
	 * AOSP(BHX-S300)
	 */
	protected boolean isBHXS300() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ("BHX-S300").equals(p_model) + ":" + p_model);

		return ("BHX-S300").equals(p_model);
	}

	protected boolean isBKOSXXX() {
		return isBKOS300();
	}

	/**
	 * AOSP(BKO-S300)
	 */
	protected boolean isBKOS300() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ("BKO-S300").equals(p_model) + ":" + p_model);

		return ("BKO-S300").equals(p_model);
	}

	/**
	 * AOSP(BKO-S200)
	 * <pre>
	 *   녹음곡스트리밍시 음정템포 오류발생
	 * </pre>
	 * <a href="http://resource.kymedia.kr/record/kpop/20120712/89/120712BSJM92K89.m4a">Loving U(러빙유)-씨스타<</a>
	 */
	protected boolean isBKOS200() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ("BKO-S200").equals(p_model) + ":" + p_model);

		return ("BKO-S200").equals(p_model);
	}

	/**
	 * AOSP셋탑확인:디버그시제외
	 */
	private boolean isAOSPSTB() {
		if (IKaraokeTV.DEBUG) {
			return false;
		}
		return (isBHXS300() || isBKOS300());
	}

	/**
	 * UHD셋탑확인
	 */
	private boolean isUHD2STB() {
		return (isBHXUH200() || (!TextUtil.isEmpty(p_model) && p_model.toLowerCase().contains("UH")));
	}

	/**
	 * <a href="http://pms.skdevice.net/redmine/issues/3482">3482 일부노래 자막 하단이 잘려서 출력되는 현상</a>
	 * 48859 - '씨스타 - Shake It' 노래 부르기 진행 중 일부 가사 하단부분이 잘려서 출력됩니다.
	 * <p/>
	 * <pre>
	 * AOSP(BHX - S300)
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main3X#setPlayer()
	 */
	@Override
	protected void setPlayer() {
		Log.e(_toString(), getMethodName() + p_model);
		super.setPlayer();
	}

	/**
	 * <pre>
	 * 이미지다운로더기능추가
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.app._Activity#putURLImage(android.widget.ImageView, java.lang.String, boolean, int)
	 */
	@Override
	public void putURLImage(ImageView v, String url, boolean resize, int imageRes) {
		super.putURLImage(v, url, resize, imageRes);
	}

	/**
	 * <a href="http://pms.skdevice.net/redmine/issues/3430">3430 금영노래방앱에서 노래 실행 시 반주가 끊겨 들리는 현상(v9.0.21)</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3479">3479 노래 재생 후, 메뉴에서 RCU ( > )방향키 계속 누르면 노래가 종료되면서 메인으로 가는 현상</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3645">3645 금영노래방 결제창에서 RCU 나가기 버튼 누를시 포커스 에러 및 검색 버튼 누를시 에러 팝업 표출 문제</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3650">3650 금영노래방에서 나가기 버튼 선택시 노래방 곡검색 페이지로 이동되는 문제</a><br>
	 * <p/>
	 * <pre>
	 * AOSP(BHX-S300)/AOSP(BKO-S300)
	 * 	키연속입력차단:디버그시제외
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main4#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i(_toString() + TAG_MAIN, "onKeyDown()" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);

		if (event == null) {
			return super.onKeyDown(keyCode, event);
		}

		// 홈키입력시(종료처리)
		if (keyCode == KeyEvent.KEYCODE_BUTTON_11) {
			Log.e(_toString() + TAG_MAIN, "onKeyDown()" + "[홈키][종료]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			finish();
			return super.onKeyDown(keyCode, event);
		}

		// 볼륨키는 어쩌구 이 븅신들아!!!
		// +-볼륨버튼동작
		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				return super.onKeyDown(keyCode, event);
			default:
				break;
		}

		// KEYCODE_F12 -> KEYCODE_BACK : '나가기'버튼 -> '이전/취소'버튼
		if (keyCode == KeyEvent.KEYCODE_F12) {
			Log.e(_toString() + TAG_MAIN, "onKeyDown()" + "[F12][이전]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			keyCode = KeyEvent.KEYCODE_BACK;
		}

		// KEYCODE_F11 -> KEYCODE_MENU : '옵션'버튼 -> '메뉴'버튼
		if (keyCode == KeyEvent.KEYCODE_F11) {
			Log.e(_toString() + TAG_MAIN, "onKeyDown()" + "[F11][옵션]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			keyCode = KeyEvent.KEYCODE_MENU;
		}

		//// UHD셋탑제외
		//// 키연속입력차단:디버그/엔터/디패드센터제외
		//if (/*!IKaraokeTV.DEBUG &&*/ !isUHD2STB() && keyCode != KeyEvent.KEYCODE_DPAD_CENTER && keyCode != KeyEvent.KEYCODE_ENTER) {
		//	if (event.getRepeatCount() > 0) {
		//		return true;
		//	}
		//}

		//// 반주곡/녹음곡 재생시 중지제외한 모든 키차단
		//if (!IKaraokeTV.DEBUG && !isUHD2STB() && (isPlaying() || isPlayingListen()) && isHotKeyCode(keyCode) && !isStopKeyCode(keyCode)) {
		//	_LOG.e(_toString() + TAG_MAIN, "onKeyDown()" + "[BTV][재생][핫키][메뉴][차단]" + method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
		//	return true;
		//}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * <a href="http://pms.skdevice.net/redmine/issues/3430">3430 금영노래방앱에서 노래 실행 시 반주가 끊겨 들리는 현상(v9.0.21)</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3479">3479 노래 재생 후, 메뉴에서 RCU ( > )방향키 계속 누르면 노래가 종료되면서 메인으로 가는 현상</a><br>
	 * <p/>
	 * <pre>
	 * AOSP(BHX-S300)/AOSP(BKO-S300)
	 * 	재생시 중지제외한 모든 키차단:디버그시제외
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2X#StartPlaying()
	 */
	@Override
	public void StartPlaying() {

		super.StartPlaying();

		//if (/*!IKaraokeTV.DEBUG && */!isUHD2STB()) {
		//	hideTopGuide01();
		//	hideTopGuide02();
		//	hideBottomGuide01();
		//}
	}


	/**
	 * <a href="http://pms.skdevice.net/redmine/issues/3430">3430 금영노래방앱에서 노래 실행 시 반주가 끊겨 들리는 현상(v9.0.21)</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3479">3479 노래 재생 후, 메뉴에서 RCU ( > )방향키 계속 누르면 노래가 종료되면서 메인으로 가는 현상</a><br>
	 * <p/>
	 * <pre>
	 * AOSP(BHX-S300)/AOSP(BKO-S300)
	 * 	재생시 중지제외한 모든 키차단:디버그시제외
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListening()
	 */
	@Override
	protected void setListening() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.getState());

		super.setListening();

		//if (/*!IKaraokeTV.DEBUG && */!isUHD2STB()) {
		//	hideTopGuide01();
		//	hideTopGuide02();
		//	//hideBottomGuide01();
		//}
	}

	/**
	 * <a href="http://pms.skdevice.net/redmine/issues/3430">3430 금영노래방앱에서 노래 실행 시 반주가 끊겨 들리는 현상(v9.0.21)</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3479">3479 노래 재생 후, 메뉴에서 RCU ( > )방향키 계속 누르면 노래가 종료되면서 메인으로 가는 현상</a><br>
	 * <p/>
	 * <pre>
	 * 재생시 메뉴차단
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2X#stopPlay(int)
	 */
	@Override
	public void stopPlay(int engage) {

		super.stopPlay(engage);

		showTopGuide01();
		showTopGuide02();
		showBottomGuideMenu(getMethodName());
	}

	@Override
	protected void ShowMenu(String method) {

		//Log.e(_toString() + TAG_MAIN, "ShowMenu() " + (!isUHD2STB() && isPlaying()));
		super.ShowMenu(method);
		//// 재생시 메뉴차단
		//if (!IKaraokeTV.DEBUG && !isUHD2STB() && isPlaying()) {
		//	super.HideMenu();
		//} else {
		//	super.ShowMenu();
		//}

	}

	/**
	 * UHD셋탑만처리(?)
	 */
	@Override
	protected void setTextViewMarquee(TextView tv, boolean enable) {
		if (!isUHD2STB()) {
			return;
		}
		super.setTextViewMarquee(tv, enable);
	}
}
