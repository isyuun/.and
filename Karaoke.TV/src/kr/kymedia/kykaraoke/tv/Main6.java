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
 * filename	:	Main2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke
 *    |_ Main2.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import kr.kymedia.karaoke.util.CPUMEM;
import kr.kymedia.karaoke.util.CPUMEM.CPUMEMListener;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 *
 * <pre>
 * CBT경고표시
 * 예약처리간소화(예약인경우한줄아래로)
 * +-볼륨버튼동작
 * ​테스트용으로 할당한 키목록(BTV제외)​​
 * Shif/Ctrl/Alt키차단
 * Ctrl+Shift:키차단
 * Ctrl+Alt:키차단
 * Ctrl+Alt+Shift:키차단
 * 강제종료
 * CPU/MEM점유율
 * 홈키입력시(종료처리)
 * </pre>
 *
 * @author isyoon
 * @since 2015. 1. 28.
 * @version 1.0
 */
class Main6 extends Main5 implements CPUMEMListener {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (findViewById(R.id.txt_setting_version) != null) {
			findViewById(R.id.txt_setting_version).setVisibility(View.INVISIBLE);
		}

		if (findViewById(R.id.txt_cpu_mem_info) != null) {
			findViewById(R.id.txt_cpu_mem_info).setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * <pre>
	 * Called by AsyncThread
	 * </pre>
	 *
	 * @see Main3X#init
	 */
	@Override
	protected void init() {

		super.init();
		startVersion();
	}

	/**
	 * 예약처리간소화(예약인경우한줄아래로) - 곡목록상세라인번호
	 */
	private int m_iSongListDetailFocusSave = 0;
	/**
	 * 예약처리간소화(예약인경우한줄아래로) - 곡목록상세라인저장
	 */
	private boolean m_bSongListDetailFocusSave = false;

	@Override
	public void displayDetailSong(int keyID) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iSongListDetailFocus);

		if (keyID == REMOTE_NONE) {
			if (remote != null) {
				if (m_iSongListDetailFocusSave > 0) {
					remote.m_iSongListDetailFocus = m_iSongListDetailFocusSave;
				}
			} else {
				m_iSongListDetailFocusSave = 0;
			}
		}

		super.displayDetailSong(keyID);
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.m_iSongListDetailFocus);

	}

	/**
	 * <pre>
	 * 리스트팝업위치저장
	 * </pre>
	 */
	private void saveSongListDetail() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		m_iSongListDetailFocusSave = remote.m_iSongListDetailFocus;
		// if (remote != null) {
		// // if (remote.m_iSongListDetailFocus == 4) {
		// // m_iSongListDetailFocusSave = 0;
		// // } else {
		// // m_iSongListDetailFocusSave = remote.m_iSongListDetailFocus;
		// // }
		// // 예약일경우만저장
		// if (remote.m_iSongListDetailFocus == 2) {
		// m_iSongListDetailFocusSave = remote.m_iSongListDetailFocus;
		// } else {
		// m_iSongListDetailFocusSave = 0;
		// }
		// } else {
		// m_iSongListDetailFocusSave = 0;
		// }
	}

	/**
	 * <pre>
	 * 반주곡예약인경우한줄아래로
	 * </pre>
	 */
	private void moveSongListDown() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		// 반주곡예약인경우한줄아래로
		if (m_bSongListDetailFocusSave && m_iSongListDetailFocusSave == 2) {
			switch (remote.m_iState) {
				case STATE_SONG_LIST:
				case STATE_MY_LIST:
				case STATE_SEARCH_LIST:
					onClickBtnDown(getCurrentFocus());
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 예약처리간소화(예약인경우한줄아래로)
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#clickDetailSong()
	 */
	@Override
	public void exitDetailSong() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		saveSongListDetail();

		super.exitDetailSong();

		moveSongListDown();
	}

	@Override
	public void exitDetailSearch() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		saveSongListDetail();

		super.exitDetailSearch();

		moveSongListDown();
	}

	/**
	 * <pre>
	 * 리스트팝업위치지움
	 * </pre>
	 */
	@Override
	public void exitListSong() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());
		super.exitListSong();
		m_iSongListDetailFocusSave = 0;
	}

	/**
	 * <pre>
	 * 리스트팝업위치지움
	 * </pre>
	 */
	@Override
	public void exitListMy() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());
		super.exitListMy();
		m_iSongListDetailFocusSave = 0;
	}

	/**
	 * <pre>
	 * 리스트팝업위치지움
	 * </pre>
	 */
	@Override
	public void exitListSearch() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());
		super.exitListSearch();
		m_iSongListDetailFocusSave = 0;
	}

	/**
	 * <pre>
	 * 리스트팝업위치지움
	 * </pre>
	 */
	@Override
	public void goSearch() {

		super.goSearch();
		m_iSongListDetailFocusSave = 0;
	}

	/**
	 * <pre>
	 * 리스트팝업위치지움
	 * </pre>
	 */
	@Override
	public void goHome() {

		super.goHome();
		m_iSongListDetailFocusSave = 0;
	}

	/**
	 * <pre>
	 * 예약처리간소화(예약인경우한줄아래로)
	 * +-볼륨버튼동작
	 * ​테스트용으로 할당한 키목록(BTV제외)​​
	 * 강제종료
	 * CPU/MEM점유율
	 * 홈키입력시(종료처리)
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
		Log.d(_toString() + TAG_MAIN, "onKeyDown()" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);

		if (event == null) {
			return super.onKeyDown(keyCode, event);
		}

		// 홈키입력시(종료처리)
		if (keyCode == KeyEvent.KEYCODE_TV || keyCode == KeyEvent.KEYCODE_HOME) {
			Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[종료]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			finish();
			return true;
		}

		// 로딩중키차단
		if (isLoading()) {
			Log.e(_toString() + TAG_MAIN, "onKeyDown()" + "[로딩]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			return true;
		}

		// test
		// if (video != null) {
		// video.onKeyDown(keyCode, event);
		// }

		HideMessageCommon();

		// 강제종료
		boolean exit = false;

		m_bSongListDetailFocusSave = true;
		if (keyCode != KeyEvent.KEYCODE_ENTER && keyCode != KeyEvent.KEYCODE_DPAD_CENTER) {
			m_bSongListDetailFocusSave = false;
		}

		if (keyCode == KeyEvent.KEYCODE_ESCAPE) { // ESC
			// 버전정보표시토글
			// CPU/MEM점유율
			// Ctrl+Shift+Esc
			if (event.isCtrlPressed() && event.isShiftPressed()) {
				if (mCPUMEM == null) {
					startCPUMEM();
					showVersion();
				} else {
					stopCPUMEM();
					hideVersion();
				}
				return false;
			}
			// ESCAPE입력차단(어플중단->홈화면이동)
			keyCode = KeyEvent.KEYCODE_BACK;
			super.onKeyDown(keyCode, event);
			CANCEL();
			stop(PLAY_STOP);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MOVE_END) {
			// 강제종료
			// Ctrl+Alt+End(Del불가)
			if (event.isCtrlPressed() && event.isAltPressed()) {
				exit = true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_DEL) {
			// 강제종료
			// Ctrl+Alt+BackSpace
			if (event.isCtrlPressed() && event.isAltPressed()) {
				exit = true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_FORWARD_DEL) {
			// 예약곡지우기
			clearLast();
			// 예약곡클리어
			// Ctrl+Shift+Del
			if (event.isCtrlPressed() && event.isShiftPressed()) {
				clearList();
			}
			// 강제종료
			// Ctrl+Alt+BackSpace
			if (event.isCtrlPressed() && event.isAltPressed()) {
				exit = true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_GRAVE) {
			// 버전정보표시
			// Ctrl+Shift+~
			if (event.isCtrlPressed() && event.isShiftPressed()) {
				toggleVersion();
				return false;
			}
		}

		if (event != null && event.getRepeatCount() > 1) {
			if (exit) {
				Log.w(_toString() + TAG_MAIN, "onKeyDown()" + "[종료]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
				CANCEL();
				finish();
				return true;
			}
		}

		// // Shif/Ctrl/Alt키차단
		// // Ctrl+Shift:키차단
		// // Ctrl+Alt:키차단
		// // Ctrl+Alt+Shift:키차단
		// if ((event.isCtrlPressed() && event.isAltPressed() && event.isShiftPressed())
		// || (event.isCtrlPressed() && event.isAltPressed()) || (event.isCtrlPressed() && event.isShiftPressed())) {
		// Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[특수키]" + "[차단]" + ":Shift:" + event.isShiftPressed() + ":Ctrl:" + event.isCtrlPressed() + ":Alt:" + event.isAltPressed());
		// return false;
		// }

		switch (keyCode) {
			//case KeyEvent.KEYCODE_F1:
			case KeyEvent.KEYCODE_BUTTON_B:
				keyCode = KeyEvent.KEYCODE_PROG_RED;
				break;
			//case KeyEvent.KEYCODE_F2:
			case KeyEvent.KEYCODE_BUTTON_A:
				keyCode = KeyEvent.KEYCODE_PROG_GREEN;
				break;
			//case KeyEvent.KEYCODE_F3:
			case KeyEvent.KEYCODE_BUTTON_Y:
				keyCode = KeyEvent.KEYCODE_PROG_YELLOW;
				break;
			//case KeyEvent.KEYCODE_F4:
			case KeyEvent.KEYCODE_BUTTON_X:
				keyCode = KeyEvent.KEYCODE_PROG_BLUE;
				break;
		}

		// ​테스트용으로 할당한 키목록(BTV제외)
		if (IKaraokeTV.DEBUG || !isAPKNAMEBTV()) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_NUMPAD_0:
					keyCode = KeyEvent.KEYCODE_0;
					break;
				case KeyEvent.KEYCODE_NUMPAD_1:
					keyCode = KeyEvent.KEYCODE_1;
					break;
				case KeyEvent.KEYCODE_NUMPAD_2:
					keyCode = KeyEvent.KEYCODE_2;
					break;
				case KeyEvent.KEYCODE_NUMPAD_3:
					keyCode = KeyEvent.KEYCODE_3;
					break;
				case KeyEvent.KEYCODE_NUMPAD_4:
					keyCode = KeyEvent.KEYCODE_4;
					break;
				case KeyEvent.KEYCODE_NUMPAD_5:
					keyCode = KeyEvent.KEYCODE_5;
					break;
				case KeyEvent.KEYCODE_NUMPAD_6:
					keyCode = KeyEvent.KEYCODE_6;
					break;
				case KeyEvent.KEYCODE_NUMPAD_7:
					keyCode = KeyEvent.KEYCODE_7;
					break;
				case KeyEvent.KEYCODE_NUMPAD_8:
					keyCode = KeyEvent.KEYCODE_8;
					break;
				case KeyEvent.KEYCODE_NUMPAD_9:
					keyCode = KeyEvent.KEYCODE_9;
					break;
				case KeyEvent.KEYCODE_NUMPAD_ENTER:
				case KeyEvent.KEYCODE_BUTTON_START:
					keyCode = KeyEvent.KEYCODE_ENTER;
					break;
				case KeyEvent.KEYCODE_DEL:
					if (!isShowSoftKeyboard()) {
						keyCode = KeyEvent.KEYCODE_BACK;
					}
					break;
				case KeyEvent.KEYCODE_BUTTON_SELECT:
					keyCode = KeyEvent.KEYCODE_BACK;
					break;
				case KeyEvent.KEYCODE_F1:
					keyCode = KeyEvent.KEYCODE_PROG_RED;
					break;
				case KeyEvent.KEYCODE_F2:
					keyCode = KeyEvent.KEYCODE_PROG_GREEN;
					break;
				case KeyEvent.KEYCODE_F3:
					keyCode = KeyEvent.KEYCODE_PROG_YELLOW;
					break;
				case KeyEvent.KEYCODE_F4:
				case KeyEvent.KEYCODE_SEARCH:
					keyCode = KeyEvent.KEYCODE_PROG_BLUE;
					break;
				case KeyEvent.KEYCODE_F5:
					keyCode = KeyEvent.KEYCODE_MENU;
					break;
				case KeyEvent.KEYCODE_F7:
					keyCode = KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
					break;
				case KeyEvent.KEYCODE_F8:
					keyCode = KeyEvent.KEYCODE_MEDIA_STOP;
					break;
				case KeyEvent.KEYCODE_F9:
					keyCode = KeyEvent.KEYCODE_MEDIA_REWIND;
					break;
				case KeyEvent.KEYCODE_F10:
					keyCode = KeyEvent.KEYCODE_MEDIA_FAST_FORWARD;
					break;
				case KeyEvent.KEYCODE_COMMA:
					keyCode = KeyEvent.KEYCODE_MEDIA_PREVIOUS;
					break;
				case KeyEvent.KEYCODE_PERIOD:
					keyCode = KeyEvent.KEYCODE_MEDIA_NEXT;
					break;
				default:
					break;
			}
		}

		// 키패드확인
		if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE)) {
			if (isShowSoftKeyboard()) {
				Log.e(_toString() + TAG_MAIN, "onKeyDown()" + "[패드]" + isShowSoftKeyboard() + ":" + remote.getState() + ":" + keyCode + ", " + event);
				hideSoftKeyboardNoWhereFast();
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	private void clearList() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		song_ids.clear();
		delPlayList.clear();
		displayEngageSong();
	}

	private void clearLast() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		if (song_ids.size() > 0) {
			song_ids.remove(song_ids.size() - 1);
			if (song_ids.size() == 0) {
				delPlayList.clear();
			}
		}
		displayEngageSong();
	}

	CPUMEM mCPUMEM = null;

	private void showCPUMEM() {
		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName());

		try {
			String text = "";

			if (mCPUMEM != null) {
				text = mCPUMEM.getInfo();

				if (mCPUMEM.getUsage() != 0.0f) {

					float cpu = mCPUMEM.getUsage();
					long tot = mCPUMEM.getTotalMemorySize();
					long use = mCPUMEM.getUsedMemorySize();

					String mem = String.format("(%s/%s bytes)", TextUtil.getDecimalFormat("#,##0", use), TextUtil.getDecimalFormat("#,##0", tot));

					text = String.format("cpu: %.0f%%(%f)  \tmem : %s", cpu * 100, cpu, mem);

				}
			}

			// if (IKaraokeTV.DEBUG) _LOG.w(_toString(), getMethodName() + lines);

			TextView info = (TextView) findViewById(R.id.txt_cpu_mem_info);

			if (info != null) {
				info.setVisibility(View.VISIBLE);

				if (!TextUtils.isEmpty(text.trim())) {
					info.setText(text.trim());
				}
			}

		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void hideCPUMEM() {
		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName());

		TextView info = (TextView) findViewById(R.id.txt_cpu_mem_info);
		if (info != null) {
			info.setVisibility(View.INVISIBLE);
		}
	}

	Runnable showCPUMEM = new Runnable() {

		@Override
		public void run() {

			showCPUMEM();
		}
	};

	@Override
	public void onCPUMEMUpdate(CPUMEM cpumem) {

		handlerKP.removeCallbacks(showCPUMEM);
		handlerKP.post(showCPUMEM);
	}

	int pid;

	/**
	 * <pre>
	 * CPU / MEM점유율
	 * </pre>
	 */
	private void startCPUMEM() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());

		try {
			post(new Runnable() {

				@Override
				public void run() {

					Context context = getApplicationContext();
					ActivityManager mgr = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
					List<RunningAppProcessInfo> processes = mgr.getRunningAppProcesses();

					// if (IKaraokeTV.DEBUG) _LOG.d("DEBUG", "Running processes:");
					for (RunningAppProcessInfo p : processes) {
						if (getPackageName().equalsIgnoreCase(p.processName)) {
							Main6.this.pid = p.pid;

							if (IKaraokeTV.DEBUG) Log.w(_toString(), "  process name: " + p.processName);
							if (IKaraokeTV.DEBUG) Log.w(_toString(), "     pid: " + p.pid);

							int[] pids = new int[1];
							pids[0] = p.pid;
							android.os.Debug.MemoryInfo[] MI = mgr.getProcessMemoryInfo(pids);

							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     dalvik private: " + MI[0].dalvikPrivateDirty);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     dalvik shared: " + MI[0].dalvikSharedDirty);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     dalvik pss: " + MI[0].dalvikPss);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     native private: " + MI[0].nativePrivateDirty);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     native shared: " + MI[0].nativeSharedDirty);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     native pss: " + MI[0].nativePss);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     other private: " + MI[0].otherPrivateDirty);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     other shared: " + MI[0].otherSharedDirty);
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     other pss: " + MI[0].otherPss);

							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     total private dirty memory (KB): " + MI[0].getTotalPrivateDirty());
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     total shared (KB): " + MI[0].getTotalSharedDirty());
							if (IKaraokeTV.DEBUG) Log.i(_toString(), "     total pss: " + MI[0].getTotalPss());

							break;
						}
					}

					mCPUMEM = new CPUMEM(pid, 3000, Main6.this, handlerKP);
					mCPUMEM.start();

					showCPUMEM();
				}
			});
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	/**
	 * <pre>
	 * CPU / MEM점유율
	 * </pre>
	 */
	private void stopCPUMEM() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());

		if (mCPUMEM != null) {
			mCPUMEM.stop();
			mCPUMEM = null;
		}

		hideCPUMEM();
	}

	/**
	 * <pre>
	 * 릴리스확인:건들지마!!!
	 * RTM->true
	 * RC->true
	 * CBT:false
	 * DEBUG:false
	 * </pre>
	 */
	private boolean isRelease() {
		boolean ret = false;

		// RTM확인
		if (isRTM()) {
			ret = true;
		}

		// RC확인
		if (isRC()) {
			ret = true;
		}

		// CBT확인
		if (isCBT()) {
			ret = false;
		}

		// 디버그확인
		if (isDebug()) {
			ret = false;
		}

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "isRelease()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		return ret;
	}

	private boolean isDebug() {
		boolean ret = (IKaraokeTV.DEBUG || ("DEBUG").equalsIgnoreCase(p_debug));

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "isDebug()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		return ret;
	}

	/**
	 * <pre>
	 * CBT확인
	 *  1. CBT경고표시:팝업)
	 *  2. 하단버전정보:상세)
	 * </pre>
	 */
	private boolean isCBT() {
		boolean ret = false;

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		// CBT확인
		if (!TextUtil.isEmpty(verionName) && verionName.contains("CBT")) {
			ret = true;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "isCBT()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		return ret;
	}

	/**
	 * CTB버전이하:CBT까지는
	 */
	protected boolean isCBTBelow() {
		boolean ret = (!isRelease() || isCBT() || isDebug());

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), "isRCBelow()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		return ret;
	}

	/**
	 * <pre>
	 * RC확인:
	 *  1. CBT경고표시:안함
	 *  2. 하단버전정보:단순
	 * </pre>
	 */
	private boolean isRC() {
		boolean ret = false;

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		// RC확인
		if (!TextUtil.isEmpty(verionName) && verionName.contains("RC")) {
			ret = true;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "isRC()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		return (!IKaraokeTV.DEBUG && ret);
	}

	/**
	 * RC버전이하:RC까지는
	 */
	protected boolean isRCBelow() {
		boolean ret = (!isRelease() || isRC());

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), "isRCBelow()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);
		return ret;
	}

	/**
	 * <pre>
	 * RTM확인
	 *  1. CBT경고표시:안함
	 *  2. 하단버전정보:안함
	 * </pre>
	 */
	private boolean isRTM() {
		boolean ret = false;

		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		// RTM확인
		if (!TextUtil.isEmpty(verionName) && verionName.contains("RTM")) {
			ret = true;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), "isRTM()" + ":" + ret + "," + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		return (!IKaraokeTV.DEBUG && ret);
	}

	/**
	 * <b><font color="red">릴리스[CBT]전엔반드시노출</font></b> <br>
	 * <b>CBT시<font color="red">"p_degug=CBT"</font>처리</b>
	 *
	 * <pre>
	 * CBT경고창표시
	 * 사이트릴리스인경우가린다.
	 * 각프로젝트의Main에서처리
	 * </pre>
	 *
	 * @see #KP_param(boolean)
	 * @see #p_debug
	 */
	protected void startCBT() {
		String verionName = "";

		if (!TextUtil.isEmpty(p_apkversionname)) {
			verionName = p_apkversionname.toUpperCase();
		}

		Log.i(_toString(), getMethodName() + p_debug + ":" + IKaraokeTV.DEBUG + ":" + verionName);

		if (isCBT()) {
			p_debug = "CBT";
		}

		if (isRC()) {
			p_debug = "RC";
		}

		if (isRTM()) {
			p_debug = "RTM";
		}

		if (IKaraokeTV.DEBUG) {
			p_debug = "DEBUG";
		}

		if (!isRelease()) {
			showCBT(true);
		}

		if (isRCBelow()) {
			showVersion();
		}
	}

	/**
	 * <b><font color="red">릴리스[CBT]전엔반드시노출</font></b> <br>
	 * <b>CBT시<font color="red">"p_degug=CBT"</font>처리</b>
	 *
	 * <pre>
	 * CBT경고창표시
	 * 사이트릴리스인경우가린다.
	 * 각프로젝트의Main에서처리
	 * </pre>
	 *
	 * @see #KP_param(boolean)
	 * @see #p_debug
	 */
	protected void showCBT(boolean show) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		String message = "";

		if (!isRelease()) {
			if (isDebug()) {
				message += getString(R.string.message_error_close_debug_warging);
			} else {
				message += getString(R.string.message_error_close_beta_warging);
			}
		}

		message += "\n" + getString(R.string.message_error_copyright_warging);

		if (show || !isRelease()) {
			// 저작권경고
			if (isDebug()) {
				message += "\n" + getString(R.string.message_error_illegal_warging);
			}
			// 이전오류관련메시지는유지하는게...
			if (!m_bShowMessageOK) {
				ShowMessageOk(POPUP_OK, getString(R.string.common_info), message);
			}
		}
	}

	private void toggleVersion() {
		if (findViewById(R.id.txt_setting_version).getVisibility() != View.VISIBLE) {
			showVersion();
		} else {
			hideVersion();
		}
	}

	/**
	 * 버전정보:보이기
	 */
	private void showVersion() {
		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName());
		findViewById(R.id.txt_setting_version).setVisibility(View.VISIBLE);
	}

	private Runnable hideVersion = new Runnable() {
		@Override
		public void run() {
			hideVersion();
		}
	};

	/**
	 * 버전정보:숨기기
	 */
	private void hideVersion() {
		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName());
		findViewById(R.id.txt_setting_version).setVisibility(View.INVISIBLE);
	}

	/**
	 * 버전정보:앱시작후 버전정보 표시숨기기(10초)
	 */
	protected void startVersion() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());

		post(new Runnable() {

			@Override
			public void run() {

				String version = getString(R.string.category_setting_version);

				try {
					PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

					if (pkgInfo != null) {
						p_apkversioncode = pkgInfo.versionCode;
						p_apkversionname = pkgInfo.versionName;
						version += "(" + "OS-" + p_osversion + ":" + Build.VERSION.SDK_INT + ")";
						if (IKaraokeTV.DEBUG) {
							version += ":" + "DEBUG";
						}
						version += ":" + p_apkversionname + "(" + p_apkversioncode + "-" + p_ver;
						if (!isRelease()) {
							version += ":" + p_debug + ")";
						} else {
							version += ")";
						}
						version += "-" + p_appname + "(" + m_strSTBVender + ")";
						version += "-" + p_model + ":" + m_strSTBVER;
						if (!isRelease()) {
							version += "(" + m_orgSTBID + "|" + p_mac + ")";
						}
						// String installDate = DateUtils.getDate("yyyy/MM/dd hh:mm:ss", pkgInfo.lastUpdateTime);
						//String buildDate = BuildUtils.getDate("yyyy/MM/dd HH:mm", BuildUtils.getBuildDate(getApplicationContext()));
						String buildDate = (new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA)).format(new Date(BuildConfig.TIMESTAMP/* + TimeZone.getTimeZone("Asia/Seoul").getRawOffset()*/));
						version += "-" + buildDate;
						// String installDate = DateUtils.getDate("yyyy/MM/dd hh:mm:ss", pkgInfo.lastUpdateTime);
						TextView tv = (TextView) findViewById(R.id.txt_setting_version);
						if (tv != null) {
							tv.setText(version);
							tv.setSingleLine();
							tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
							WidgetUtils.setTextViewMarquee(tv);
						}
					}

					Log.wtf(_toString() + TAG_MAIN, "[KY노래방]" + version);

					showVersion();

					if (isRelease()) {
						postDelayed(hideVersion, TIMEOUT_HIDE_VERSION_INFO);
					}

					//test
					//postDelayed(hideVersion, TIMEOUT_HIDE_VERSION_INFO);

					startCBT();

				} catch (Exception e) {
					if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
					ShowMessageAlert(getString(R.string.message_error_stb_info) + "\n" + "startVersion() " + "[NG]" + "\n" + Log.getStackTraceString(e));
				}
			}
		});
	}

	@Override
	protected void onStart() {

		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		super.onStart();

		hideCPUMEM();
	}

	@Override
	protected void onStop() {

		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		super.onStop();

		stopCPUMEM();
	}

}
