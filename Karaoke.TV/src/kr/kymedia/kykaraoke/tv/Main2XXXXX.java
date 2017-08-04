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
 * project	:	Karaoke.TV
 * filename	:	Main211.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main211.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 븅신키:처리개선
 * SKB SMART_UHD(BHX-UH200)
 * 	리모콘숫자키무반응: UI메뉴 이동 중 부정기적인 리모콘 숫자키입력 불효화 현상
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 5. 29.
 */
class Main2XXXXX extends Main2XXXX {
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

	public View[] getChildViews(ViewGroup group) {
		try {
			int childCount = 0;

			if (group != null) {
				childCount = group.getChildCount();
			}

			final View[] childViews = new View[childCount];

			if (group != null) {
				for (int i = 0; i < childCount; i++) {
					childViews[i] = group.getChildAt(i);
				}
			}

			return childViews;
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
		return null;
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void resetCertify() {

		if (IKaraokeTV.DEBUG) {
			super.resetCertify();
		} else {
			try {
				super.resetCertify();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void resetCertifyHP() {

		if (IKaraokeTV.DEBUG) {
			super.resetCertifyHP();
		} else {
			try {
				super.resetCertifyHP();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void exitCertifyHP() {

		if (IKaraokeTV.DEBUG) {
			super.exitCertifyHP();
		} else {
			try {
				super.exitCertifyHP();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	protected void stop(int engage) {
	}

	private void stop() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString() + TAG_MAIN, getMethodName());
		stop(PLAY_STOP);
	}

	/**
	 * <a href="http://pms.skdevice.net/redmine/issues/3674">3674 금영노래방 키보드활성화 상태에서 홈으로 이동시 키보드가 사라지지 않는 문제(공통)</a>
	 * <p/>
	 * <pre>
	 * 븅신키:볼륨키는 어쩌구 이 븅신들아!!!
	 * 븅신키:키보드:볼륨처리
	 * 븅신키:3674 금영노래방 키보드활성화 상태에서 홈으로 이동시 키보드가 사라지지 않는 문제(공통)
	 * 븅신키:팝업표시후 숫자키처리시 연타 입력차단
	 * 븅신키:팝업표시후 백버튼처리시 팝업종료
	 * 븅신키:팝업표시후 핫키목록(색깔키:메뉴키) 팝업종료
	 * 븅신키:핫키입력중복차단
	 * 븅신키:핫키입력시 키패드숨기기
	 * 븅신키:그럼메뉴숨긴상태에서 씹짜키하고엔터키는?
	 * 븅신키:메뉴숨기기시(녹음곡재생제외) 핫키외차단
	 * 븅신키:븅신색깔핫키기능이관
	 * 다른녹음곡키입력변경
	 *    기존 : REMOTE_DOWN.
	 *    변경 : REMOTE_MENU
	 * 다른녹음곡키입력변경
	 *    기존 KeyEvent.KEYCODE_ENTER/KeyEvent.KEYCODE_DPAD_CENTER
	 *    변경 KeyEvent.KEYCODE_MEDIA_PLAY/case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
	 * </pre>
	 *
	 * @see Main2#onKeyDown(int, android.view.KeyEvent)
	 * @see Main2XXXXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main3XXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6X#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6XX#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.w(_toString() + TAG_MAIN, "onKeyDown()" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);

		if (event == null) {
			return super.onKeyDown(keyCode, event);
		}

		// 븅신키:볼륨키는 어쩌구 이 븅신들아!!!
		// +-볼륨버튼동작
		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				volumeUp(keyCode, event);
				return true;
			// break;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				volumeDown(keyCode, event);
				return true;
			// break;
			default:
				break;
		}

		// 븅신키:키보드:볼륨처리
		if (/*!isShowPopups() && */!isShowSoftKeyboard() && event.isCtrlPressed() && event.isShiftPressed()) {
			Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[볼륨][차단]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
			switch (keyCode) {
				case KeyEvent.KEYCODE_EQUALS:
				case KeyEvent.KEYCODE_NUMPAD_ADD:
					volumeUp(keyCode, event);
					return true;
				// break;
				case KeyEvent.KEYCODE_MINUS:
				case KeyEvent.KEYCODE_NUMPAD_SUBTRACT:
					volumeDown(keyCode, event);
					return true;
				// break;
				default:
					break;
			}
		}

		// 븅신키:핫키입력시 키패드숨기기
		if (isHotKeyCode(keyCode)) {
			hideSoftKeyboardNoWhereFast();
		}

		// 븅신키:핫키입력중복차단
		if (!isDEMOAPP() && (isLoading() || event.getRepeatCount() > 0) && isHotKeyCode(keyCode)) {
			Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[핫키][차단]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
			return true;
		}

		// 븅신키:3674 금영노래방 키보드활성화 상태에서 홈으로 이동시 키보드가 사라지지 않는 문제(공통)
		if (isShowSoftKeyboard() && isHotKeyCode(keyCode)) {
			Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[패드][차단]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
			hideSoftKeyboardNoWhereFast();
		}

		// 븅신키:팝업표시후
		if (isShowPopups()) {
			// 븅신키:팝업표시후 숫자키처리시 연타 입력차단
			if (/*isShowPopups() && */isNumberKeyCode(keyCode) && event != null && event.getRepeatCount() > 0) {
				Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][숫자]" + loading_method + ":" + isLoading() + ":getFocusedChild():" + getFocusedChild() + ":" + remote.getState() + ":" + event);
				return true;
			}

			// 븅신키:팝업표시후 백버튼처리시 팝업종료
			if (/*isShowPopups() && */keyCode == KeyEvent.KEYCODE_BACK) {
				Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][종료]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
				exitPopups();
				return true;
			}

			// 븅신키:팝업표시후 핫키목록(색깔키:메뉴키) 팝업종료
			if (/*isShowPopups() && */isHotKeyCode(keyCode)) {
				Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][핫키]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
				exitPopups();
				return true;
			}

		} else {
			// 븅신키:그럼메뉴숨긴상태에서 씹짜키하고엔터키는?
			// 븅신키:메뉴숨기기시(녹음곡재생제외) 핫키외차단
			if (/*!isShowPopups() && */!isShowMenu() && !isListening() && !isHotKeyCode(keyCode)) {
				Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[메뉴][숨김][차단]" + isShowMenu() + ":" + remote.getState() + ":" + keyCode + ", " + event);
				return true;
			}
		}

		// 븅신키:븅신색깔핫키기능이관
		switch (keyCode) {
			case KeyEvent.KEYCODE_PROG_GREEN:
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "GREEN, [KEYCODE_PROG_GREEN]");
				if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
					// BTV:BOX->재생
					keyCode = KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
				} else {
					// BTV:STB->중지
					keyCode = KeyEvent.KEYCODE_MEDIA_STOP;
				}
				break;
			case KeyEvent.KEYCODE_PROG_YELLOW:
				if (IKaraokeTV.DEBUG) Log.wtf(_toString(), "GREEN, [KEYCODE_PROG_YELLOW]");
				if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
					// BTV:BOX->중지
					keyCode = KeyEvent.KEYCODE_MEDIA_STOP;
				} else {
					// BTV:STB->재생
					keyCode = KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
				}
				break;
			default:
				break;
		}

		// 븅신키:븅신색깔핫키기능이관
		switch (keyCode) {
			//재생시백버튼중지
			case KeyEvent.KEYCODE_BACK:
				// 숨김 상태고 반주곡 재생 중이면 메뉴 다시 보여주고 반주곡 중지 처리
				if (!isShowMenu() && isPlaying()) {
					stop();
					return true;
				}
				break;
			// isyoon:메뉴기능
			case KeyEvent.KEYCODE_MENU:
				//다른녹음곡키입력변경
				if (isListening() || isListeningState()) {
					toggleListening();
				} else {
					toggleMenu();
				}
				return true;
			case KeyEvent.KEYCODE_MEDIA_PLAY:
			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
				Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[재생][시작]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
				//다른녹음곡키입력변경
				if (isListeningState()) {
					clickListening();
				} else {
					// 예약곡확인후처리
					if (!TextUtil.isEmpty(getEngageSong())) {
						// 지랄!!!븅신들!!!
						startSing(null);
					}
				}
				return true;
			case KeyEvent.KEYCODE_MEDIA_STOP:
				Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[재생][종료]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
				stopPlay(PLAY_STOP);
				return true;
			case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
				Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[재생][이전]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
				// |◀◀
				previous();
				return true;
			case KeyEvent.KEYCODE_MEDIA_NEXT:
				Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[재생][다음]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + event);
				// ▶▶|
				next();
				return true;
			case KeyEvent.KEYCODE_PROG_RED:
			case KeyEvent.KEYCODE_MEDIA_REWIND:
				RemoveListenDisplay();
				goHome();
				return true;
			case KeyEvent.KEYCODE_PROG_BLUE:
			case KeyEvent.KEYCODE_SEARCH:
				if (m_bIsCertifyTimerActivated) {
					m_cdTimer.cancel();
				}
				RemoveListenDisplay();
				goSearch();
				return true;
			case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
				if (m_bIsCertifyTimerActivated) {
					m_cdTimer.cancel();
				}
				RemoveListenDisplay();
				goSearch();
				return true;
			default:
				break;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * <pre>
	 * 다른녹음곡키입력변경
	 *    기존 KeyEvent.KEYCODE_ENTER/KeyEvent.KEYCODE_DPAD_CENTER
	 *    변경 KeyEvent.KEYCODE_MEDIA_PLAY/case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
	 * </pre>
	 * @see Main2#clickGUI()
	 */
	@Override
	protected void clickGUI() {
		switch (remote.m_iState) {
			case STATE_LISTENING:
				break;
			default:
				super.clickGUI();
				break;
		}
	}

	@Override
	protected void ShowMessageCommon(int close, String title, String message) {
		super.ShowMessageCommon(close, title, message);
		ShowMenu(getMethodName());
	}

	@Override
	protected void ShowMessageNotResponse(String title, String message) {
		super.ShowMessageNotResponse(title, message);
		ShowMenu(getMethodName());
	}

	@Override
	public void ShowMessageAlert(String message) {
		super.ShowMessageAlert(message);
		ShowMenu(getMethodName());
	}

	@Override
	public void ShowMessageOk(int type, String title, String message) {
		super.ShowMessageOk(type, title, message);
		ShowMenu(getMethodName());
	}

	@Override
	protected void ShowMessageOkCancel(String title, String message) {
		super.ShowMessageOkCancel(title, message);
		ShowMenu(getMethodName());
	}
}
