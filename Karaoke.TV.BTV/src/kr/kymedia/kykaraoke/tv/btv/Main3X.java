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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV.BTV
 * filename	:	Main3X.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ Main3X.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.btv;

import android.util.Log;

import kr.kymedia.kykaraoke.BuildConfig;

/**
 * <pre>
 * 가사처리슬립추가:강제할당
 * 
 * Jh SHIN <jh.shin@st.com>
 * 9월 9일 (2일 전)
 * 
 * Jimmy, Simon, Hj, (주)케이와이미디어 윤인식., 노형석매니저님, 정훈팀장님, 김광남팀장님, 박유미사원에게 
 * 안녕하세요.
 *  
 * 저희쪽 의견은
 * -          자막처리시에 1초에 15번 정도 ( 15fps)로 일정하게 처리하게 하면
 * 시스템 부하가 크게 줄어들고,
 * 
 * -          화면 처리 속도고, 사용자가 봤을때 괜찮은 수준으로 보입니다.
 * 
 * 
 * 15fps인 경우는, 1000/15 = 66.7 ms의 딜레이를
 * 30fps 인 경우는, 1000/30 = 33.3 ms 의 딜레이를 설정하면 되는데,
 * 윤인식 차장님 자리에서 테스트 했을때,
 * 제 의견은 HUMAX/ST박스의 경우는 15fps가 적당할 것으로 보입니다.
 *  
 * Best regards,
 * JH SHIN
 * </pre>
 *
 * @author isyoon
 * @since 2015. 9. 8.
 * @version 1.0
 */
class Main3X extends Main3 {
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

	/**
	 * <pre>
	 * UHD(BHX-UH200)/AOSP(BHX-S300)/AOSP(BKO-S300)
	 * 반주곡 재생시 이미지처리
	 * </pre>
	 * <p/>
	 * <a href="http://pms.skdevice.net/redmine/issues/3430">3430 금영노래방앱에서 노래 실행 시 반주가 끊겨 들리는 현상(v9.0.21)</a><br>
	 * <a href="http://pms.skdevice.net/redmine/issues/3479">3479 노래 재생 후, 메뉴에서 RCU ( > )방향키 계속 누르면 노래가 종료되면서 메인으로 가는 현상</a><br>
	 */
	@Override
	protected void setVideo() {
		super.setVideo();

		Log.e(_toString(), getMethodName() + p_model);

		// 배경동영상막기
		//if (isBHXUH200() || isBHXS300() || isBKOS300())
		{
			setPlayVideo(false);
		}
	}

	@Override
	protected void setPlayer() {
		SLEEP_TIME = 66L;
		super.setPlayer();
	}

	@Override
	protected void play() {
		super.play();
	}
}
