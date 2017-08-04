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
 * filename	:	Main3X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main3X.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;

import kr.kymedia.karaoke.play.impl.ISongPlay.ERROR;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.tv.api._Download.onDownloadListener;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.api._Download;
import kr.kymedia.kykaraoke.tv.api._KPRequest;

/**
 * <pre>
 * 븅신들땜에정말케삽질한다.
 * 개쓰레기어플오류담당. 진짜 더럽게 돼진다!!!
 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 4. 1.
 */
class Main2X extends Main2 implements onDownloadListener, OnFocusChangeListener {
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
	protected void clickGUI() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + ":" + remote.getState());
		super.clickGUI();
		ShowMenu(getMethodName());
	}

	/**
	 * @see Main2#KP(int, String, String, String)
	 * @see Main2X#KP(int, String, String, String)
	 * @see Main3X#KP(int, String, String, String)
	 * @see Main3XXX#KP(int, String, String, String)
	 * @see Main3XXXXX#VASSPPX(COMPLETE_VASS)
	 */
	@Override
	public void KP(int request, String OP, String M1, String M2) {
		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + REQUEST_KP.get(KP_REQUEST) + ", " + OP + ", " + M1 + ", " + M2);
		super.KP(request, OP, M1, M2);

		switch (request) {
			case REQUEST_SONG_LIST:
			case REQUEST_LISTEN_LIST:
				if (IKaraokeTV.DEBUG) Log.wtf(_toString() + TAG_MAIN, "KP() " + "[RQ]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
				break;
			case REQUEST_SONG_PLAY:
				if (IKaraokeTV.DEBUG) Log.wtf(_toString() + TAG_MAIN, "KP() " + "[RQ]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
				_KP_1016 = KP(_KP_1016);
				if (_KP_1016 != null) {
					_KP_1016.setRequestType(REQUEST_SONG_PLAY);
					_KP_1016.setSongPlayUrl(KP_1016, "", "", m_strRequestPlaySongID);
				}
				break;
			case REQUEST_LISTEN_SONG:
			case REQUEST_LISTEN_SONG_OTHER:
				if (IKaraokeTV.DEBUG) Log.wtf(_toString() + TAG_MAIN, "KP() " + "[RQ]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
				tryPlayListen();
				break;
			default:
				break;
		}

		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + REQUEST_KP.get(KP_REQUEST) + ", " + OP + ", " + M1 + ", " + M2);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#TryPlaySong()
	 */
	@Override
	public void TryPlaySong() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		tryPlaySong();

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 반주곡조회
	 *
	 * @see kr.kymedia.kykaraoke.tv.api.Const#REQUEST_SONG_PLAY
	 */
	private _KPRequest _KP_1016;

	protected _KPRequest _KP_1016() {
		return _KP_1016;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#TryPlaySong()
	 */
	private void tryPlaySong() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		hideDetailSong();
		hideBottomGuide01();
		hideBottomGuide02();

		// 이용권이 없으면 알림 팝업 띄우고 리턴
		if (!isPassUser()) {
			stopLoading(getMethodName());

			stopTaskShowMessageNotResponse();

			m_bIsGoToPurchaseMessage = true;
			ShowMessageOkCancel(getString(R.string.common_info), getString(R.string.ticket_have_no));
			return;
		}

		if (_KP_1016 != null && !_KP_1016.isAlive()) {
			_KP_1016.start();
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 녹음곡조회
	 *
	 * @see kr.kymedia.kykaraoke.tv.api.Const#REQUEST_LISTEN_SONG
	 * @see kr.kymedia.kykaraoke.tv.api.Const#REQUEST_LISTEN_SONG_OTHER
	 */
	private void tryPlayListen() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		String OP = KP_2016;
		String M1 = M1_MENU_LISTEN;
		String M2 = M2_LISTEN_TIMELINE;

		if (remote.m_iMenuMainFocus == 3) {
			KP.setListenSongUrl(OP, M1, M2, record_id, p_mid);
		} else {
			KP.setListenSongUrl(OP, M1, M2, record_id, "");
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	@Override
	protected void KP(Message msg) {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
		super.KP(msg);

		int state = msg.getData().getInt("state");

		switch (state) {
			case COMPLETE_SONG_PLAY: // 반주곡 시작
				if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				break;
			case COMPLETE_DOWN_SONG: // 반주곡 파일 다운로드
				if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				break;
			case COMPLETE_AUTH_NUMBER:  // 휴대폰인증번호입력
				if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
				break;
			case COMPLETE_LISTEN_LIST: // 녹음곡 리스트
				if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);
				if (mListenItems.size() > 0) {
					startLoading(getMethodName(), LOADING_SHORT);
				}
				break;
		}

	}

	/**
	 * <pre>
	 * 가사파일만받는거다~~~
	 * </pre>
	 */
	protected void down() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());

		if (_KP_1016 == null) {
			ShowMessageNotResponse(getString(R.string.common_info), getString(R.string.message_error_network_timeout));
			return;
		}

		HideMenu(getMethodName());

		if (!("00000").equals(_KP_1016.result_code)) {
			stopLoading(getMethodName());
			if (!TextUtil.isEmpty(_KP_1016.result_message)) {
				ShowMessageOk(CLOSE_OK, getString(R.string.common_info), _KP_1016.result_message);
			}
			return;
		}

		this.url_lyric = _KP_1016.url_lyric;
		this.url_skym = _KP_1016.url_skym;
		this.video_url = _KP_1016.video_url;
		this.type = _KP_1016.type;

		download = new _Download(handlerKP);
		download.setFileName("sing.skym");
		download.setType(REQUEST_FILE_SONG);
		download.setMp3(url_skym);
		download.setLyc(url_lyric);
		download.setNewPath(sdPath);
		download.start();
		download.setListener(this);
	}

	@Override
	public void onDownError(ERROR t, final Exception e) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + e.getMessage() + "(" + t + ")" + url_lyric);
		post(new Runnable() {
			@Override
			public void run() {
				stopPlay(PLAY_STOP);
				stopLoading(getMethodName());
				try {
					String msg = getString(R.string.message_error_lyric) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
					if (IKaraokeTV.DEBUG) {
						msg += "\n" + e.getMessage();
					} else {
						msg += "\n" + getString(R.string.message_error_commend_retry);
					}
					ShowMessageAlert(msg);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				Log.e(_toString() + TAG_MAIN, "downLyrics() " + "[NG]" + "\n" + Log.getStackTraceString(e));
			}
		});
	}

	@Override
	public void onDownRetry(int count) {


	}

	@Override
	public void onDownTimeout(long timeout) {


	}

	/**
	 * <pre>
	 * 아무것도안받는거다~~~
	 * </pre>
	 */
	protected void downListen(int state) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + COMPLETE_KP.get(state));

		if (!("00000").equals(KP.result_code)) {
			stopLoading(getMethodName());
			if (!TextUtil.isEmpty(KP.result_message)) {
				ShowMessageOk(CLOSE_OK, getString(R.string.common_info), KP.result_message);
			}
			return;
		}

		m_strListenSongUrl = KP.url_record;
		m_strListeningSongID = KP.song_id;

		switch (state) {
			case COMPLETE_LISTEN_SONG:
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "_COMPLETE_LISTEN");
				sendMessage(COMPLETE_DOWN_LISTEN);
				break;
			case COMPLETE_LISTEN_OTHER_SONG:
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "_COMPLETE_LISTEN_OTHER_DOWN");
				sendMessage(COMPLETE_DOWN_LISTEN_OTHER);
				break;
			default:
				break;
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#VASS(android.os.Message)
	 */
	@Override
	public void VASS(Message msg) {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")) + ":" + msg);
		super.VASS(msg);
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	public void addViewListenItem(ViewGroup parent, ViewGroup view) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.addViewListenItem(parent, view);
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void setListeningState() {

		if (IKaraokeTV.DEBUG) {
			super.setListeningState();
		} else {
			try {
				super.setListeningState();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	protected void unselectSongList() {
		if (IKaraokeTV.DEBUG) {
			super.unselectSongList();
		} else {
			try {
				super.unselectSongList();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void resetShopSubMenu() {

		if (IKaraokeTV.DEBUG) {
			super.resetShopSubMenu();
		} else {
			try {
				super.resetShopSubMenu();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void resetCustomerList() {

		if (IKaraokeTV.DEBUG) {
			super.resetCustomerList();
		} else {
			try {
				super.resetCustomerList();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void resetMySubMenu() {

		if (IKaraokeTV.DEBUG) {
			super.resetMySubMenu();
		} else {
			try {
				super.resetMySubMenu();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void exitMyRecordNone() {

		if (IKaraokeTV.DEBUG) {
			super.exitMyRecordNone();
		} else {
			try {
				super.exitMyRecordNone();
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	/**
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 */
	@Override
	public void displayListSing(int keyID) {

		if (IKaraokeTV.DEBUG) {
			super.displayListSing(keyID);
		} else {
			try {
				super.displayListSing(keyID);
			} catch (Exception e) {
				if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			}
		}
	}

	@Override
	public void clickDetailSong() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.clickDetailSong();

		if (remote.m_iSongListFocus < 1) {
			return;
		}

		switch (remote.m_iSongListDetailFocus) {
			case 1:
				// 븅신아~~~멀까?
				break;
			default:
				break;
		}
	}

	@Override
	public void clickDetailSearch() {

		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.m_iSongListDetailFocus);
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.clickDetailSearch();

		if (remote.m_iSearchListFocus < 1) {
			return;
		}

		switch (remote.m_iSongListDetailFocus) {
			case 1:
				// 븅신아~~~멀까?
				break;
			default:
				break;
		}
	}

	@Override
	public void exitListSong() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		exitDetailSong();
		super.exitListSong();
	}

	@Override
	public void exitListSearch() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		exitDetailSearch();
		super.exitListSearch();
	}

	///**
	// * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	// */
	//@Override
	//protected void ShowGenre() {
	//
	//	if (IKaraokeTV.DEBUG) {
	//		super.ShowGenre();
	//	} else {
	//		try {
	//			super.ShowGenre();
	//		} catch (Exception e) {
	//			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
	//		}
	//	}
	//}

	///**
	// * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	// */
	//@Override
	//protected void HideGenre() {
	//
	//	if (IKaraokeTV.DEBUG) {
	//		super.HideGenre();
	//	} else {
	//		try {
	//			super.HideGenre();
	//		} catch (Exception e) {
	//			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
	//		}
	//	}
	//}

	@Override
	public void goSearch() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		switch (remote.m_iState) {
			case STATE_SEARCH_MENU:
			case STATE_SEARCH_SELF:
			case STATE_SEARCH_LETTER_KOR:
			case STATE_SEARCH_LETTER_ENG:
			case STATE_SEARCH_LETTER_NUM:
			case STATE_SEARCH_LIST:
			case STATE_SEARCH_LIST_DETAIL:
				break;
			default:
				super.goSearch();
				break;
		}
	}

	/**
	 * <pre>
	 * 븅신아저장하지않습니다!!!
	 * 안되는경우는어떨꺼여~~~
	 * </pre>
	 */
	@Deprecated
	@Override
	protected String writeKaraoke(String line) {

		// line = "";
		// final String PATH = "kykaraoke.txt";
		//
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ST]" + PATH + ":" + line + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		//
		// if (line != null) {
		// try {
		// OutputStreamWriter out = new OutputStreamWriter(openFileOutput(PATH, Context.MODE_PRIVATE));
		// out.write(line);
		// out.close();
		// } catch (Exception e) {
		// line = null;
		// // if (IKaraokeTV.DEBUG) _LOG.e(_toString() + TAG_ERR,  "[NG]" + getMethodName() + "[READ_ERROR]" + PATH + "\n" + _LOG.getStackTraceString(e));
		// _LOG.e(_toString(), "writeKaraoke() " + "[NG]" + "[READ_ERROR]" + PATH + "\n" + _LOG.getStackTraceString(e));
		// // e.printStackTrace();
		// }
		// }
		//
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ED]" + PATH + ":" + line + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		return line;
	}

	/**
	 * <pre>
	 * 븅신아읽어오지않습니다!!!
	 * 안되는경우는어떨꺼여~~~
	 * </pre>
	 */
	@Deprecated
	@Override
	protected String readKaraoke() {

		String line = super.readKaraoke();
		// final String PATH = getFilesDir().getPath();
		// String fileName = "kykaraoke.txt";
		//
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ST]" + PATH + File.separator + fileName + ":" + line + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		//
		// boolean filecheck = false;
		// File iFile = new File(PATH);
		//
		// if (iFile.exists()) {
		// File[] fList2 = iFile.listFiles();
		//
		// String searchFileName = "";
		//
		// for (int i = 0; i < fList2.length; i++) {
		// searchFileName = fList2[i].getName();
		//
		// if (fileName.equals(searchFileName)) {
		// filecheck = true;
		// }
		// }
		//
		// if (filecheck == true) {
		// try {
		// InputStream in = openFileInput(fileName);
		//
		// if (in != null) {
		// InputStreamReader tmp = new InputStreamReader(in);
		// BufferedReader reader = new BufferedReader(tmp);
		// String str;
		// StringBuffer buf = new StringBuffer();
		//
		// while ((str = reader.readLine()) != null) {
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[CK]" + str);
		// buf.append(str);
		// }
		// in.close();
		// line = buf.toString();
		// }
		// } catch (Exception e) {
		// // if (IKaraokeTV.DEBUG) _LOG.e(_toString() + TAG_ERR,  "[NG]" + getMethodName() + "[READ_ERROR]" + PATH + File.separator + fileName + "\n" + _LOG.getStackTraceString(e));
		// _LOG.e(_toString(), "readKaraoke() " + "[NG]" + "[READ_ERROR]" + PATH + File.separator + fileName + "\n" + _LOG.getStackTraceString(e));
		// // e.printStackTrace();
		// }
		// } else {
		// // if (IKaraokeTV.DEBUG) _LOG.e(_toString() + TAG_ERR,  "[NG]" + getMethodName() + "[FILE_ERROR]" + fileName);
		// _LOG.e(_toString(), "readKaraoke() " + "[NG]" + "[FILE_ERROR]" + fileName);
		// }
		//
		// } else {
		// // if (IKaraokeTV.DEBUG) _LOG.e(_toString() + TAG_ERR,  "[NG]" + getMethodName() + "[PATH_ERROR]" + PATH);
		// _LOG.e(_toString(), "readKaraoke() " + "[NG]" + "[PATH_ERROR]" + PATH);
		// }
		//
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ED]" + PATH + File.separator + fileName + ":" + line + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		return line;
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#checkKaraoke()
	 */
	@Override
	protected String checkKaraoke() {

		String ret = super.checkKaraoke();

		if (!TextUtil.isEmpty(auth_phoneno)) {
			ret = auth_phoneno;
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ret + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (KP != null && TextUtil.isEmpty(auth_phoneno)) {
			auth_phoneno = KP.auth_phoneno;
			if (!TextUtil.isEmpty(auth_phoneno)) {
				ret = writeKaraoke(auth_phoneno);
			}
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ret + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		return ret;
	}

	/**
	 * @see Main2XXXX#setSelectedOkCancel(int, int, int, int)
	 */
	protected int setSelectedOkCancel(int ok, int cancel, int val, int keyID) {

		int ret = val;
		return ret;
	}

	/**
	 * @see Main2XXXX#setSelectedOkBackCancel(int, int, int, int, int)
	 */
	protected int setSelectedOkBackCancel(int ok, int back, int cancel, int val, int keyID) {

		int ret = val;
		return ret;
	}

	///**
	// * 휴대폰 번호 등록 -> 열기
	// */
	//@Override
	//public void clickShopCertify() {
	//	if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	//	// bgkimt [인증번호등록] 버튼 눌렀을 때 인증센터 팝업 띄운다. 월정액 이용권 없으면 튕겨준다
	//	// bgkimt 인증번호 변경 가능 횟수가 다 했어도 튕겨준다.
	//	if (IKaraokeTV.DEBUG || p_passtype == TICKET_TYPE_MONTH) {
	//		if (IKaraokeTV.DEBUG || auth_modify_idx > 0) {
	//			remote.m_iCertifyHPFocusX = 1;
	//			remote.m_iCertifyHPFocusY = 1;
	//
	//			//message_hp = (LinearLayout) inflate(R.layout.message_hp, null);
	//			//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	//			//addContentView(message_hp, param);
	//			message_hp = (LinearLayout) addPopup(R.layout.message_hp);
	//
	//			remote.m_iState = STATE_CERTIFY_HP;
	//
	//			displayCertifyHP(REMOTE_NONE);
	//		} else {
	//			ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.message_error_certify_count));
	//		}
	//	} else {
	//		ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.message_error_certify_ticket_month_no));
	//	}
	//	if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	//}

	/**
	 * <pre>
	 * 휴대폰 번호 등록
	 * onKeyDown중간이라포커스를잃는경우가생긴다.->이시팔무하는짓이랴.
	 * </pre>
	 */
	private EditText focusEdit;

	/**
	 * <pre>
	 * 휴대폰 번호 등록 -> 리셋
	 * onKeyDown중간이라포커스를잃는경우가생긴다.->이시팔무하는짓이랴.
	 * </pre>
	 */
	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#resetCertifyHP()
	 */
	@Override
	public void resetCertifyHP() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		this.focusEdit = null;

		EditText editHP01 = (EditText) findViewById(R.id.edit_message_hp_01);
		EditText editHP02 = (EditText) findViewById(R.id.edit_message_hp_02);
		EditText editHP03 = (EditText) findViewById(R.id.edit_message_hp_03);
		Button btnOk = (Button) findViewById(R.id.btn_message_hp_ok);
		Button btnCancel = (Button) findViewById(R.id.btn_message_hp_cancel);

		hideSoftKeyboardNoWhereFast();

		clearFocus(editHP01);
		clearFocus(editHP02);
		clearFocus(editHP03);

		btnOk.setSelected(false);
		clearFocus(btnOk);
		btnCancel.setSelected(false);
		clearFocus(btnCancel);

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	private final Runnable requestFocusNhideSoftKeyBoard = new Runnable() {
		@Override
		public void run() {
			requestFocus(focusEdit);
			hideSoftKeyboard(focusEdit);
		}
	};

	/**
	 * <pre>
	 * 휴대폰 번호 등록 -> 표시
	 * onKeyDown중간이라포커스를잃는경우가생긴다.->이시팔무하는짓이랴.
	 * </pre>
	 */
	@Override
	public void displayCertifyHP(final int keyID) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.m_iCertifyHPFocusX + ":" + remote.m_iCertifyHPFocusY + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		resetCertifyHP();

		EditText editHP01 = (EditText) findViewById(R.id.edit_message_hp_01);
		EditText editHP02 = (EditText) findViewById(R.id.edit_message_hp_02);
		EditText editHP03 = (EditText) findViewById(R.id.edit_message_hp_03);

		EditText edit = editHP01;

		switch (remote.m_iCertifyHPFocusY) {
			case 1:
				if (remote.m_iCertifyHPFocusX == 1) {
					edit = editHP01;
					// clearFocus(editHP02);
					// clearFocus(editHP03);
				} else if (remote.m_iCertifyHPFocusX == 2) {
					edit = editHP02;
					// clearFocus(editHP01);
					// clearFocus(editHP03);
				} else if (remote.m_iCertifyHPFocusX == 3) {
					edit = editHP03;
					// clearFocus(editHP01);
					// clearFocus(editHP02);
				}

				this.focusEdit = edit;
				// onKeyDown중간이라포커스를잃는경우가생긴다.->이시팔무하는짓이랴.
				postDelayed(requestFocusNhideSoftKeyBoard, 100);

				focusEdit.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {

						Log.w(_toString(), "onKey() " + v + "," + keyCode + "," + event);
						return hideSoftKeyboardOnKey(v, keyCode, event);
					}
				});

				// edit.setText("");
				//
				// requestFocus(edit);
				//
				// if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
				// setIMELocation();
				// edit.setOnEditorActionListener(new OnEditorActionListener() {
				// @Override
				// public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				// if (arg2 != null && arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
				// if (remote.m_iCertifyHPFocusX == 1) {
				// remote.m_iCertifyHPFocusX = 2;
				// } else if (remote.m_iCertifyHPFocusX == 2) {
				// remote.m_iCertifyHPFocusX = 3;
				// } else if (remote.m_iCertifyHPFocusX == 3) {
				// remote.m_iCertifyHPFocusX = 1;
				// remote.m_iCertifyHPFocusY = 2;
				// }
				//
				// displayCertifyHP(REMOTE_NONE);
				// }
				// return false;
				// }
				// });
				// } else {
				// // showSoftKeyboard(edit);
				// }
				break;
			case 2:
				remote.m_iCertifyHPFocusX = setSelectedOkCancel(R.id.btn_message_hp_ok, R.id.btn_message_hp_cancel, remote.m_iCertifyHPFocusX, REMOTE_NONE);
				break;
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.m_iCertifyHPFocusX + ":" + remote.m_iCertifyHPFocusY + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 휴대폰 번호 등록 -> 확인/취소 -> 인증 번호 등록
	 * 인증번호재전송시간차단차단
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#clickCertifyHP()
	 */
	@Override
	public void clickCertifyHP() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		// 동일번호입력/인증번호재전송시간차단차단
		mIsCertifyValidCheck = false;
		super.clickCertifyHP();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 휴대폰 번호 등록 -> 종료
	 * </pre>
	 */
	@Override
	public void exitCertifyHP() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		remote.m_iCertifyHPFocusX = 1;
		remote.m_iCertifyHPFocusY = 1;

		if (remote.m_iMenuMainFocus == 5) {
			remote.m_iState = STATE_CUSTOMER_LIST_DETAIL;
			// ((ViewManager) message_hp_event.getParent()).removeView(message_hp_event);
			removeView(message_hp_event);
			message_hp_event = null;
		} else {
			remote.m_iState = STATE_SHOP_CERTIFY;
			// ((ViewManager) message_hp.getParent()).removeView(message_hp);
			removeView(message_hp);
			message_hp = null;
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 인증 번호 등록 -> 리셋
	 * </pre>
	 */
	@Override
	public void resetCertify() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		EditText editCertify = (EditText) findViewById(R.id.edit_message_certify);
		Button btnResend = (Button) findViewById(R.id.btn_message_certify_resend);
		Button btnOk = (Button) findViewById(R.id.btn_message_certify_ok);
		Button btnCancel = (Button) findViewById(R.id.btn_message_certify_cancel);

		hideSoftKeyboardNoWhereFast();

		clearFocus(editCertify);
		clearFocus(btnResend);

		btnOk.setSelected(false);
		btnCancel.setSelected(false);
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 숫자키입력인경우 소프트키보드 차단
	 * </pre>
	 */
	private boolean hideSoftKeyboardOnKey(View v, int keyCode, KeyEvent event) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + v + "," + keyCode + event);

		if (!(v instanceof EditText)) {
			return false;
		}

		if (!(((EditText) v).getInputType() == InputType.TYPE_CLASS_NUMBER)) {
			return false;
		}

		if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
			Log.e(_toString(), getMethodName() + "onKeyDown()" + "[패드][노출][차단]" + v + "," + keyCode + event);
			return true;
		}

		if (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Log.e(_toString(), getMethodName() + "onKeyDown()" + "[팝업][노출][차단]" + v + "," + keyCode + event);
			if (isShowPopups()) {
				exitPopups();
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * 인증 번호 등록 -> 표시
	 * 키패드표시막기
	 * </pre>
	 */
	@Override
	public void displayCertify(int keyID) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		resetCertify();

		EditText edit = (EditText) findViewById(R.id.edit_message_certify);
		Button btnResend = (Button) findViewById(R.id.btn_message_certify_resend);
		// Button btnOk = (Button) findViewById(R.id.btn_message_certify_ok);
		// Button btnCancel = (Button) findViewById(R.id.btn_message_certify_cancel);

		switch (remote.m_iCertifyFocusY) {
			case 1:
				if (remote.m_iCertifyFocusX == 1) {
					edit.setText("");

					// requestFocus(edit);
					this.focusEdit = edit;
					// onKeyDown중간이라포커스를잃는경우가생긴다.->이시팔무하는짓이랴.
					postDelayed(requestFocusNhideSoftKeyBoard, 100);

					focusEdit.setOnKeyListener(new OnKeyListener() {

						@Override
						public boolean onKey(View v, int keyCode, KeyEvent event) {

							Log.w(_toString(), "onKey() " + v + "," + keyCode + "," + event);
							return hideSoftKeyboardOnKey(v, keyCode, event);
						}
					});

					// if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
					// setIMELocation();
					// edit.setOnEditorActionListener(new OnEditorActionListener() {
					// @Override
					// public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
					// if (arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					// remote.m_iCertifyFocusX = 1;
					// remote.m_iCertifyFocusY = 2;
					//
					// displayCertify(REMOTE_NONE);
					// }
					// return false;
					// }
					// });
					// } else {
					// // showSoftKeyboard(edit);
					// }
				} else {
					requestFocus(btnResend);
					btnResend.setSelected(true);
				}
				break;
			case 2:
				// if (remote.m_iCertifyFocusX == 1) {
				// btnOk.setBackgroundResource(R.drawable.pop_btn_01_on);
				// } else if (remote.m_iCertifyFocusX == 2) {
				// btnCancel.setBackgroundResource(R.drawable.pop_btn_01_on);
				// }
				remote.m_iCertifyFocusX = setSelectedOkCancel(R.id.btn_message_certify_ok, R.id.btn_message_certify_cancel, remote.m_iCertifyFocusX, REMOTE_NONE);
				break;
		}
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 인증 번호 등록 -> 확인/취소
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#clickCertify()
	 */
	@Override
	public void clickCertify() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.clickCertify();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 인증 번호 등록 -> 종료:븅신이건머고
	 * </pre>
	 * <p/>
	 * 씹팔뻒하면뻗어~~~지랄!!!소스를어떻게짠거야~~~시팔!!!
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#exitCertify()
	 */
	@Override
	public void exitCertify() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		try {
			super.exitCertify();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
		remote.m_iState = STATE_SHOP_MENU;
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * <pre>
	 * 인증 번호 등록 -> 종료:븅신이건머냐
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#exitCertifyNumber()
	 */
	@Override
	public void exitCertifyNumber() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		try {
			super.exitCertifyNumber();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Deprecated
	private void setLayoutHomeBackground() {
		// LinearLayout layoutHome = (LinearLayout) findViewById(R.id.layout_home);
		//
		// switch (remote.m_iMenuMainFocus) {
		// case 1:
		// case 2:
		// case 3:
		// case 4:
		// case 5:
		// layoutHome.setBackgroundResource(R.drawable.main_bg1);
		// break;
		// case 6:
		// layoutHome.setBackgroundResource(R.drawable.main_bg2);
		// break;
		// case 7:
		// layoutHome.setBackgroundResource(R.drawable.main_bg3);
		// break;
		// case 8:
		// layoutHome.setBackgroundResource(R.drawable.main_bg4);
		// break;
		// case 9:
		// layoutHome.setBackgroundResource(R.drawable.main_bg5);
		// break;
		// case 10:
		// layoutHome.setBackgroundResource(R.drawable.main_bg6);
		// break;
		// case 11:
		// layoutHome.setBackgroundResource(R.drawable.main_bg7);
		// break;
		// }
	}

	private void showListen() {
		showListening();
		showListeningOther();
	}

	private void hideListen() {
		hideListening();
		hideListeningOther();
	}

	private void showListening() {
		if (m_layoutListen == null) {
			m_layoutListen = (LinearLayout) findViewById(R.id.listening);
		}
		if (m_layoutListen != null) {
			m_layoutListen.setVisibility(View.VISIBLE);
		}
	}

	private void hideListening() {
		if (m_layoutListen == null) {
			m_layoutListen = (LinearLayout) findViewById(R.id.listening);
		}
		if (m_layoutListen != null) {
			m_layoutListen.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected boolean isShowMenu() {
		return (super.isShowMenu() || isShowListeningOther());
	}

	private boolean isShowListeningOther() {
		boolean ret = false;
		if (m_layoutListeningOther != null && m_layoutListeningOther.getVisibility() == View.VISIBLE) {
			if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + m_layoutListeningOther.getVisibility() + ":" + m_layoutListeningOther);
			ret = true;
		}
		return ret;
	}

	private void showListeningOther() {
		if (m_layoutListeningOther == null) {
			m_layoutListeningOther = (LinearLayout) findViewById(R.id.listen_other);
		}
		if (m_layoutListeningOther != null) {
			m_layoutListeningOther.setVisibility(View.VISIBLE);
			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + m_layoutListeningOther.getVisibility() + ":" + m_layoutListeningOther);
		}
	}

	private void hideListeningOther() {
		if (m_layoutListeningOther == null) {
			m_layoutListeningOther = (LinearLayout) findViewById(R.id.listen_other);
		}
		if (m_layoutListeningOther != null) {
			m_layoutListeningOther.setVisibility(View.INVISIBLE);
			if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + m_layoutListeningOther.getVisibility() + ":" + m_layoutListeningOther);
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#HideMenu(String)
	 */
	private void hideMenu(boolean top) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + top + ":" + remote.getState());

		setVisibleMain(false);

		setVisibleTop(!top);

		try {
			if (m_iPaneState != PANE_HOME) {
				hideDetailSong();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		if (m_layoutListenListFocus != null) {
			m_layoutListenListFocus.setVisibility(View.INVISIBLE);
		}

		// 하단 가이드 텍스트를 새로 갱신
		showBottomGuideMenu(getMethodName());

		if (isLoading()) {
			hideBottomGuide01();
			hideBottomGuide02();
		}
	}

	private void showMenu(boolean top) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + top + ":" + remote.getState());

		if (m_iPaneState == PANE_HOME) {
			remote.m_iState = STATE_HOME_MENU;
		} else {
			if (m_layoutListenListFocus != null) {
				m_layoutListenListFocus.setVisibility(View.VISIBLE);
			} else {
				if (remote.m_iState == STATE_LISTEN_LIST) {
					displayListListen(REMOTE_NONE);
				}
			}
		}

		setLayoutHomeBackground();

		setVisibleMain(true);

		setVisibleTop(true);

		// 하단 가이드 텍스트를 새로 갱신
		showBottomGuideMenu(getMethodName());

	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3#setVisibleTop(boolean)
	 */
	protected void setVisibleTop(boolean visible) {

	}

	protected void setVisibleMain(boolean visible) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + visible + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (visible) {
			switch (m_iPaneState) {
				case PANE_HOME:
					m_layoutHome.setVisibility(View.VISIBLE);
					m_layoutMain.setVisibility(View.INVISIBLE);
					break;

				case PANE_MAIN:
					m_layoutHome.setVisibility(View.INVISIBLE);
					m_layoutMain.setVisibility(View.VISIBLE);
					break;

				default:
					break;
			}
		} else {
			m_layoutHome.setVisibility(View.INVISIBLE);
			m_layoutMain.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 메뉴 닫힘 = 메뉴 열기 : 먼소리여이게
	 */
	protected void showBottomGuideMenu(String method) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + method);
		setBottomGuideText01(R.drawable.btn_menu, getString(R.string.menu_bottom_open));
	}

	///**
	// * 메뉴 닫힘 = 메뉴 열기 : 먼소리여이게
	// */
	//protected void showBottomGuideMenuOpen(String method) {
	//	if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
	//	setBottomGuideText01(R.drawable.btn_menu, getString(R.string.menu_bottom_open));
	//}
	//
	///**
	// * 메뉴 열림 = 메뉴 닫기 : 먼소리여이게
	// */
	//protected void showBottomGuideMenuClose(String method) {
	//	if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
	//	setBottomGuideText01(R.drawable.btn_menu, getString(R.string.menu_bottom_close));
	//}

	/**
	 * 반주곡이 예약됨 = 반주곡 시작 : 먼소리여이게
	 */
	@Override
	protected void showBottomGuideStartSong() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		String text = getString(R.string.menu_bottom_start_song_now);

		if (!TextUtil.isEmpty(getEngageSong())) {
			text = getString(R.string.menu_bottom_start_song);
		}

		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			setBottomGuideText02(R.drawable.btn_play_pause_green, text);
		} else {
			setBottomGuideText02(R.drawable.btn_play_pause_yellow, text);
		}
	}

	/**
	 * 반주곡 재생 = 반주곡 중지 : 먼소리여이게
	 */
	@Override
	protected void showBottomGuideStopSong() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		// setBottomGuideText02(getString(R.string.menu_bottom_stop_song));
		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			setBottomGuideText02(R.drawable.btn_stop_yellow, getString(R.string.menu_bottom_stop_song));
		} else {
			setBottomGuideText02(R.drawable.btn_stop_green, getString(R.string.menu_bottom_stop_song));
		}
	}

	/**
	 * 하단 첫번째 가이드를 '다른 녹음곡 보기' 로 변경
	 */
	@Override
	protected void showBottomGuideListenOther(String method) {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + method);
		setBottomGuideText01(R.drawable.btn_menu, getString(R.string.menu_bottom_other_listen));
	}

	/**
	 * 현재는 무료 이벤트 기간입니다.
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#showBottomGuideTicketNone()
	 */
	@Override
	protected void showBottomGuideTicketNone() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		setBottomGuideText02(0, getString(R.string.menu_bottom_ticket_free_event));
	}

	@Override
	protected void setBottomProductText(String text) {
		Log.i(_toString() + TAG_MAIN, "setBottomTicketText() " + text);
		super.setBottomProductText(text);
	}

	///**
	// * <pre>
	// * 하단 가이드 텍스트를 새로 갱신
	// * </pre>
	// */
	//private void setBottomGuideText01(String text) {
	//	if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + text);
	//	// if (isPlaying())
	//	{
	//		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
	//			setBottomGuideText01(R.drawable.common_btn_yellow, text);
	//		} else {
	//			setBottomGuideText01(R.drawable.common_btn_green, text);
	//		}
	//	}
	//}
	//
	//private void setBottomGuideText02(String text) {
	//	if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + text);
	//	// if (isPlaying())
	//	{
	//		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
	//			setBottomGuideText02(R.drawable.common_btn_green, text);
	//		} else {
	//			setBottomGuideText02(R.drawable.common_btn_yellow, text);
	//		}
	//	}
	//}

	private void setBottomGuideText01(int resId, String text) {

		try {
			if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + getResourceEntryName(resId) + text);
			showBottomGuide01();
			TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_01);
			txtGuide.setText(text);
			if (resId > 0) {
				txtGuide.setCompoundDrawables(WidgetUtils.getDrawable(getApplicationContext(), resId), null, null, null);
			} else {
				txtGuide.setCompoundDrawables(null, null, null, null);
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void setBottomGuideText02(int resId, String text) {

		try {
			if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + getResourceEntryName(resId) + text);
			showBottomGuide02();
			TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_02);
			txtGuide.setText(text);
			if (resId > 0) {
				txtGuide.setCompoundDrawables(WidgetUtils.getDrawable(getApplicationContext(), resId), null, null, null);
			} else {
				txtGuide.setCompoundDrawables(null, null, null, null);
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	protected void SetBottomGuideText03(String text) {
		setBottomGuideText03(0, text);
		if (TextUtil.isEmpty(text)) {
			hideBottomGuide03();
		} else {
			showBottomGuide03();
		}
	}

	private void setBottomGuideText03(int resId, String text) {

		try {
			if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + getResourceEntryName(resId) + text);
			showBottomGuide03();
			TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_03);
			txtGuide.setText(text);
			if (resId > 0) {
				txtGuide.setCompoundDrawables(WidgetUtils.getDrawable(getApplicationContext(), resId), null, null, null);
			} else {
				txtGuide.setCompoundDrawables(null, null, null, null);
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void hideBottomGuide03() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_03);
		txtGuide.setVisibility(View.INVISIBLE);
	}

	private void showBottomGuide03() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_03);
		txtGuide.setVisibility(View.VISIBLE);
	}


	@Override
	public void hideBottomGuide01() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_01);
		txtGuide.setVisibility(View.GONE);
	}

	private void showBottomGuide01() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_01);
		txtGuide.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideBottomGuide02() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_02);
		txtGuide.setVisibility(View.GONE);
	}

	private void showBottomGuide02() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_bottom_guide_02);
		txtGuide.setVisibility(View.VISIBLE);
	}

	protected void hideTopGuide01() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_top_guide_01);
		txtGuide.setVisibility(View.GONE);
	}

	protected void showTopGuide01() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_top_guide_01);
		txtGuide.setVisibility(View.VISIBLE);
	}

	protected void hideTopGuide02() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_top_guide_02);
		txtGuide.setVisibility(View.GONE);
	}

	protected void showTopGuide02() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		TextView txtGuide = (TextView) findViewById(R.id.txt_top_guide_02);
		txtGuide.setVisibility(View.VISIBLE);
	}

	@Override
	protected void setBaseLayout() {

		super.setBaseLayout();

		// hideBottomGuide01();
		hideBottomGuide02();
		hideBottomGuide03();
	}

	@Override
	public void setContentViewKaraoke(View view) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":" + view);

		hidePopups();

		super.setContentViewKaraoke(view);

		// 하단 가이드 텍스트를 새로 갱신
		//if (!isShowMenu()) {
		//	if (isPlaying()) {
		//		showBottomGuideMenu(getMethodName()); // 메뉴 닫힘 = 메뉴 열기
		//	}
		//} else {
		//	if (isPlaying()) {
		//		showBottomGuideMenu(getMethodName()); // 메뉴 열림 = 메뉴 닫기
		//	}
		//}
		showBottomGuideMenu(getMethodName());

		if (isPlaying()) {
			showBottomGuideStopSong(); // 반주곡 재생 = 반주곡 중지
		} else {
			if (song_ids.size() > 0) {
				showBottomGuideStartSong(); // 반주곡 중지 = 반주곡 시작
			}
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":" + view);
	}

	/**
	 * 반주곡시작
	 *
	 * @see Main2X#play()
	 */
	protected void play() {
		Log.e(_toString() + TAG_MAIN, "play() " + "[ST]");
		try {
			if (player != null) {
				player.play();
				StartPlaying();
			}

			wakeLockAquire();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			String msg = getString(R.string.message_error_sing) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
			if (IKaraokeTV.DEBUG) {
				msg += "\n" + e.getMessage();
			} else {
				msg += "\n" + getString(R.string.message_error_commend_retry);
			}
			ShowMessageAlert(msg);
		}

		try {
			player.playLyrics();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			String msg = getString(R.string.message_error_lyric) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
			if (IKaraokeTV.DEBUG) {
				msg += "\n" + e.getMessage();
			} else {
				msg += "\n" + getString(R.string.message_error_commend_retry);
			}
			ShowMessageAlert(msg);
		}

		delEngageSong();

		Log.e(_toString() + TAG_MAIN, "play() " + "[ED]");
	}

	protected boolean isListeningState() {
		boolean ret = false;
		switch (remote.m_iState) {
			case STATE_LISTENING:
			case STATE_LISTEN_OTHER:
				ret = true;
				break;
			default:
				break;
		}
		return ret;
	}

	/**
	 * <pre>
	 * 다른녹음곡키입력변경
	 *    기존 : REMOTE_DOWN.
	 *    변경 : REMOTE_MENU
	 * </pre>
	 */
	protected void toggleListening() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + isListening() + ":" + isLoading() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (isLoading()) {
			return;
		}

		if (!isListening()) {
			return;
		}


		HideMenu(getMethodName());

		//데이터가없는경우
		if (mListenOtherItems == null || mListenOtherItems.size() == 0) {
			displayListening(REMOTE_MENU);
		} else {
			//레이어가없는경우
			if (m_layoutListeningOther == null) {
				setListenOtherPage();
			} else {
				if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + isShowListeningOther() + ":" + remote.getState());
				if (remote.m_iState == STATE_LISTENING || !isShowListeningOther()) {
					showListeningOther();
					remote.m_iState = STATE_LISTEN_OTHER;
				} else {
					hideListeningOther();
					remote.m_iState = STATE_LISTENING;
				}
			}
		}

		showBottomGuideListenOther(getMethodName());
	}

	/**
	 * 반주곡재생시에만한다.
	 */
	protected void toggleMenu() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + isPlaying() + ":" + isListening() + ":" + isLoading() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		//로딩처리시예외
		if (isLoading()) {
			return;
		}

		// 녹음곡재생시숨김
		if (isListening() || isListeningState()) {
			HideMenu(getMethodName());
		}

		// 반주곡재생시에만
		if (isPlaying()) {
			if (!isShowMenu()) {
				// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "YELLOW, SHOW");
				ShowMenu(getMethodName());
			} else {
				// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "YELLOW, HIDE");
				HideMenu(getMethodName());
			}
		}
	}

	@Override
	protected void ShowMenu(String method) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + method + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (isListening()) {
			return;
		}

		showMenu(true);

		showPopups();

		super.ShowMenu(method);

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + method + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	protected void HideMenu(String method) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + method + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		hideMenu(false);

		hidePopups();

		super.HideMenu(getMethodName());

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + method + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	@Override
	protected void hideDetailSong() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (m_layoutSongListDetail != null) {
			m_layoutSongListDetail.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void showDetailSong() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		switch (remote.getState()) {
			case STATE_SONG_LIST_DETAIL:
			case STATE_SEARCH_LIST_DETAIL:
				if (m_layoutSongListDetail != null) {
					m_layoutSongListDetail.setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 가리지
	 */
	private void hideMessageCommon() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (findViewById(R.id.message_common) != null) {
			findViewById(R.id.message_common).setVisibility(View.INVISIBLE);
		}

		if (findViewById(R.id.message_common_bottomright) != null) {
			findViewById(R.id.message_common_bottomright).setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 보이지
	 */
	private void showMessageCommon() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (message_common != null) {
			message_common.setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void ShowMessageCommon(int close, String title, String message) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + close + ", " + title + ", " + message);
		super.ShowMessageCommon(close, title, message);

		stopLoading(getMethodName());

		if (message_common != null) {
			message_common.bringToFront();
		}

		// if (IKaraokeTV.DEBUG)
		{
			setBottomProductText(message);
		}
	}

	@Override
	protected void ShowMessageNotResponse(String title, String message) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.ShowMessageNotResponse(title, message);

		stopLoading(getMethodName());

		if (message_common != null) {
			message_common.bringToFront();
		}

		// if (IKaraokeTV.DEBUG)
		{
			setBottomProductText(message);
		}
	}

	@Override
	protected void HideMessageCommon() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (findViewById(R.id.message_common) != null) {
			removeView(findViewById(R.id.message_common));
		}
		if (findViewById(R.id.message_common_bottomright) != null) {
			removeView(findViewById(R.id.message_common_bottomright));
		}
		message_common = null;
	}

	@Override
	public void ShowMessageAlert(String message) {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + message);
		ShowMessageOk(CLOSE_OK, getString(R.string.common_error), message);

		if (message_ok != null) {
			message_ok.bringToFront();
		}
	}

	@Override
	public void ShowMessageOk(int type, String title, String message) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + message_ok + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + title + ", " + message);
		super.ShowMessageOk(type, title, message);

		stopLoading(getMethodName());

		if (message_ok != null) {
			message_ok.bringToFront();
		}

		// if (IKaraokeTV.DEBUG)
		{
			setBottomProductText(message);
		}
	}

	@Override
	public void HideMessageOk() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.HideMessageOk();
		message_ok = null;
	}

	@Override
	protected void ShowMessageOkCancel(String title, String message) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + title + ", " + message);
		super.ShowMessageOkCancel(title, message);

		stopLoading(getMethodName());

		m_iMessageOkCancelFocusX = POPUP_CANCEL;

		setSelectedMessageOkCancel(KeyEvent.KEYCODE_DPAD_CENTER);

		// if (IKaraokeTV.DEBUG)
		{
			setBottomProductText(message);
		}
	}

	@Override
	protected void HideMessageOkCancel() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.HideMessageOkCancel();
		message_okcancel = null;
	}

	/**
	 * 팝업창오픈여부확인
	 */
	protected boolean isShowPopups() {
		boolean ret = false;

		ret |= (message_common != null ? (message_common.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (message_ok != null ? (message_ok.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (message_okcancel != null ? (message_okcancel.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (message_hp != null ? (message_hp.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (message_hp_certify != null ? (message_hp_certify.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (message_hp_event != null ? (message_hp_event.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (message_ticket != null ? (message_ticket.getVisibility() == View.VISIBLE) : false);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_common) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_common_bottomright) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_hp) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_hp_certify) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_hp_event) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_ok) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_okcancel) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_ticket_go_certify) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_ticket_notice) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_ticket_pass) != null);
		if (ret) return ret;
		ret |= (findViewById(R.id.message_ticket_ppx_info) != null);
		if (ret) return ret;
		//ret |= (findViewById(R.id.message_ticket_ppv_info) != null);
		//if (ret) return ret;

		//String msg = "";
		//if (IKaraokeTV.DEBUG) {
		//	msg += "\n" + "message_common" + ":" + (message_common != null ? (message_common.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + "message_ok" + ":" + (message_ok != null ? (message_ok.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + "message_okcancel" + ":" + (message_okcancel != null ? (message_okcancel.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + "message_hp" + ":" + (message_hp != null ? (message_hp.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + "mPopupCertify" + ":" + (message_hp_certify != null ? (message_hp_certify.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + "message_hp_event" + ":" + (message_hp_event != null ? (message_hp_event.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + "message_ticket" + ":" + (message_ticket != null ? (message_ticket.getVisibility() == View.VISIBLE) : false);
		//	msg += "\n" + (findViewById(R.id.message_common) != null);
		//	msg += "\n" + (findViewById(R.id.message_common_bottomright) != null);
		//	msg += "\n" + (findViewById(R.id.message_hp) != null);
		//	msg += "\n" + (findViewById(R.id.message_hp_certify) != null);
		//	msg += "\n" + (findViewById(R.id.message_hp_event) != null);
		//	msg += "\n" + (findViewById(R.id.message_ok) != null);
		//	msg += "\n" + (findViewById(R.id.message_okcancel) != null);
		//	msg += "\n" + (findViewById(R.id.message_ticket_go_certify) != null);
		//	msg += "\n" + (findViewById(R.id.message_ticket_notice) != null);
		//	msg += "\n" + (findViewById(R.id.message_ticket_pass) != null);
		//	msg += "\n" + (findViewById(R.id.message_ticket_ppm_info) != null);
		//	msg += "\n" + (findViewById(R.id.message_ticket_ppv_info) != null);
		//}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + ret + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		return ret;
	}

	/**
	 * 팝업창날리기
	 */
	@Override
	protected void removeView(View popup) {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + popup + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.removeView(popup);
		try {
			if (popup != null) {
				if (popup instanceof ViewGroup) {
					((ViewGroup) popup).removeAllViews();
				}
				if (popup.getParent() != null) {
					((ViewManager) popup.getParent()).removeView(popup);
				}
			}
			popup = null;
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	@Override
	protected void setVisibility(View popup, int visibility) {
		//if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(popup) + "," + visibility);
		try {
			super.setVisibility(popup, visibility);
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void hidePopups() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		hideDetailSong();
		hideMessageCommon();

		hideSoftKeyboardNoWhereFast();

		setVisibility(findViewById(R.id.message_common), View.INVISIBLE);

		setVisibility(findViewById(R.id.message_common_bottomright), View.INVISIBLE);

		setVisibility(message_common, View.INVISIBLE);

		setVisibility(message_ok, View.INVISIBLE);

		setVisibility(message_okcancel, View.INVISIBLE);

		setVisibility(message_hp, View.INVISIBLE);

		setVisibility(message_hp_event, View.INVISIBLE);

		setVisibility(message_hp_certify, View.INVISIBLE);

		setVisibility(message_ticket, View.INVISIBLE);
	}

	private void showPopups() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		showDetailSong();
		showMessageCommon();

		setVisibility(findViewById(R.id.message_common), View.INVISIBLE);

		setVisibility(findViewById(R.id.message_common_bottomright), View.INVISIBLE);

		setVisibility(message_common, View.VISIBLE);

		setVisibility(message_ok, View.VISIBLE);

		setVisibility(message_okcancel, View.VISIBLE);

		setVisibility(message_hp, View.VISIBLE);

		setVisibility(message_hp_event, View.VISIBLE);

		setVisibility(message_hp_certify, View.VISIBLE);

		setVisibility(message_ticket, View.VISIBLE);
	}

	/**
	 * 팝업창모두닫기
	 */
	protected void exitPopups() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		try {
			if (message_common != null && message_common.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_common + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				HideMessageCommon();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_common);
		message_common = null;

		try {
			if (message_ok != null && message_ok.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_ok + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				HideMessageOk();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_ok);
		message_ok = null;

		try {
			if (message_okcancel != null && message_okcancel.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_okcancel + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				HideMessageOkCancel();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_okcancel);
		message_okcancel = null;

		try {
			if (message_hp != null && message_hp.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_hp + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				exitCertifyHP();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_hp);
		message_hp = null;

		try {
			if (message_hp_event != null && message_hp_event.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_hp_event + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				exitCertifyHP();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_hp_event);
		message_hp_event = null;

		try {
			if (message_hp_certify != null && message_hp_certify.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_hp_certify + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				exitCertifyNumber();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_hp_certify);
		message_hp_certify = null;

		try {
			if (message_ticket != null && message_ticket.getParent() != null) {
				Log.e(_toString(), "exitPopups()" + message_ticket + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
				exitPPV();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		removeView(message_ticket);
		message_ticket = null;

		String msg = "";
		msg += "\n" + "message_common" + ":" + (message_common != null ? (message_common.getVisibility() == View.VISIBLE) : false);
		msg += "\n" + "message_ok" + ":" + (message_ok != null ? (message_ok.getVisibility() == View.VISIBLE) : false);
		msg += "\n" + "message_okcancel" + ":" + (message_okcancel != null ? (message_okcancel.getVisibility() == View.VISIBLE) : false);
		msg += "\n" + "message_hp" + ":" + (message_hp != null ? (message_hp.getVisibility() == View.VISIBLE) : false);
		msg += "\n" + "mPopupCertify" + ":" + (message_hp_certify != null ? (message_hp_certify.getVisibility() == View.VISIBLE) : false);
		msg += "\n" + "message_hp_event" + ":" + (message_hp_event != null ? (message_hp_event.getVisibility() == View.VISIBLE) : false);
		msg += "\n" + "message_ticket" + ":" + (message_ticket != null ? (message_ticket.getVisibility() == View.VISIBLE) : false);

		removeView(findViewById(R.id.message_common));
		removeView(findViewById(R.id.message_common_bottomright));
		removeView(findViewById(R.id.message_hp));
		removeView(findViewById(R.id.message_hp_certify));
		removeView(findViewById(R.id.message_hp_event));
		removeView(findViewById(R.id.message_ok));
		removeView(findViewById(R.id.message_okcancel));
		removeView(findViewById(R.id.message_ticket_go_certify));
		removeView(findViewById(R.id.message_ticket_notice));
		removeView(findViewById(R.id.message_ticket_pass));
		removeView(findViewById(R.id.message_ticket_ppx_info));
		//removeView(findViewById(R.id.message_ticket_ppv_info));

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + msg);

	}

	/**
	 * 핫키목록(색깔키:메뉴키)
	 * <p/>
	 * <pre>
	 * KeyEvent.KEYCODE_BACK:
	 * KeyEvent.KEYCODE_MENU:
	 * KeyEvent.KEYCODE_PROG_RED:
	 * KeyEvent.KEYCODE_MEDIA_REWIND:
	 * KeyEvent.KEYCODE_PROG_GREEN:
	 * KeyEvent.KEYCODE_MEDIA_STOP:
	 * KeyEvent.KEYCODE_PROG_YELLOW:
	 * KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
	 * KeyEvent.KEYCODE_PROG_BLUE:
	 * KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
	 * KeyEvent.KEYCODE_MEDIA_PREVIOUS:
	 * KeyEvent.KEYCODE_MEDIA_NEXT:
	 * </pre>
	 */
	protected boolean isHotKeyCode(int keyCode) {
		boolean ret = false;

		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
			case KeyEvent.KEYCODE_MENU:
			case KeyEvent.KEYCODE_PROG_RED:
			case KeyEvent.KEYCODE_MEDIA_REWIND:
			case KeyEvent.KEYCODE_PROG_GREEN:
			case KeyEvent.KEYCODE_MEDIA_STOP:
			case KeyEvent.KEYCODE_PROG_YELLOW:
			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
			case KeyEvent.KEYCODE_PROG_BLUE:
			case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
			case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
			case KeyEvent.KEYCODE_MEDIA_NEXT:
				ret = true;
				break;
			default:
				break;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ret);

		return ret;
	}

	/**
	 * 숫자키
	 * <p/>
	 * <pre>
	 * KeyEvent.KEYCODE_0:
	 * KeyEvent.KEYCODE_1:
	 * KeyEvent.KEYCODE_2:
	 * KeyEvent.KEYCODE_3:
	 * KeyEvent.KEYCODE_4:
	 * KeyEvent.KEYCODE_5:
	 * KeyEvent.KEYCODE_6:
	 * KeyEvent.KEYCODE_7:
	 * KeyEvent.KEYCODE_8:
	 * KeyEvent.KEYCODE_9:
	 * KeyEvent.KEYCODE_POUND:
	 * </pre>
	 */
	protected boolean isNumberKeyCode(int keyCode) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_0:
			case KeyEvent.KEYCODE_1:
			case KeyEvent.KEYCODE_2:
			case KeyEvent.KEYCODE_3:
			case KeyEvent.KEYCODE_4:
			case KeyEvent.KEYCODE_5:
			case KeyEvent.KEYCODE_6:
			case KeyEvent.KEYCODE_7:
			case KeyEvent.KEYCODE_8:
			case KeyEvent.KEYCODE_9:
			case KeyEvent.KEYCODE_POUND:
				return true;
			default:
				return false;
		}
	}

	protected boolean isStopKeyCode(int keyCode) {
		boolean ret = false;

		switch (keyCode) {
			case KeyEvent.KEYCODE_ESCAPE:
			case KeyEvent.KEYCODE_BACK:
			case KeyEvent.KEYCODE_PROG_GREEN:
			case KeyEvent.KEYCODE_MEDIA_STOP:
				ret = true;
				break;
			default:
				break;
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ret);

		return ret;
	}

	/**
	 * <pre>
	 * 팝업표시시차단
	 * </pre>
	 */
	@Override
	protected void ShowNumberSearch(int keyCode) {

		if (isShowPopups()) {
			return;
		}
		super.ShowNumberSearch(keyCode);
	}

	/**
	 * <pre>
	 *   데모앱
	 * </pre>
	 */
	protected boolean isDEMOAPP() {
		return (isAPKNAMEAPP() || isAPKNAMESTC() || isAPKNAMEGTV());
	}

	/**
	 * <pre>
	 *   기본앱
	 * </pre>
	 */
	protected boolean isAPKNAMEAPP() {
		boolean ret = P_APKNAME_ATV.equalsIgnoreCase(getPackageName());
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ret + ":" + getPackageName());
		return ret;
	}

	/**
	 * <pre>
	 *   기본앱(ST.COM)
	 * </pre>
	 */
	protected boolean isAPKNAMESTC() {
		boolean ret = P_APKNAME_STC.equalsIgnoreCase(getPackageName());
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ret + ":" + getPackageName());
		return ret;
	}

	/**
	 * <pre>
	 *   GTV
	 * </pre>
	 */
	protected boolean isAPKNAMEGTV() {
		boolean ret = P_APKNAME_GTV.equalsIgnoreCase(getPackageName());
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ret + ":" + getPackageName());
		return ret;
	}

	/**
	 * <pre>
	 *   BTV
	 * </pre>
	 */
	protected boolean isAPKNAMEBTV() {
		boolean ret = P_APKNAME_BTV.equalsIgnoreCase(getPackageName());
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + ret + ":" + getPackageName());
		return ret;
	}

	void CANCEL() {
	}

	/**
	 * <pre>
	 * 로딩긴급차단
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#dispatchKeyEvent(android.view.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		// isyoon:짧은로딩인경우
		if (isLoading() && event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
			int keyCode = event.getKeyCode();
			int keyCount = event.getRepeatCount();
			if (keyCount > 5) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_ESCAPE:
					case KeyEvent.KEYCODE_BACK:
						Log.wtf(_toString() + TAG_MAIN, "dispatchKeyEvent()" + "[긴급][차단]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
						CANCEL();
						stopPlay(PLAY_STOP);
						stopLoading(getMethodName());
						ShowMenu(getMethodName());
						break;
					default:
						break;
				}
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public void displayEngageSong() {

		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		super.displayEngageSong();

		TextView textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		String text = song_ids.toString().replace("[", "").replace("]", "");
		textEngageList.setText(text);

		setTextViewMarquee(textEngageList, true);
	}

	private void addEngageSongFirst(String song) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + song + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		song_ids.add(0, song);
	}

	@Override
	protected void addEngageSong(String song_id) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + song_id + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (!TextUtil.isNumeric(song_id)) {
			return;
		}
		super.addEngageSong(song_id);

		TextView textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		String text = song_ids.toString().replace("[", "").replace("]", "");
		textEngageList.setText(text);

		setTextViewMarquee(textEngageList, true);
	}

	/**
	 * 이전(previous)/이후(next)
	 */
	ArrayList<String> delPlayList = new ArrayList<String>();

	/**
	 * <pre>
	 * 븅신아곡이시작하고지원야지
	 * 이전(previous)/이후(next)
	 * </pre>
	 *
	 * @see Main3XX#start()
	 */
	protected void delEngageSong() {

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + delPlayList + m_strRequestPlaySongID + song_ids);

		try {
			if (song_ids.size() > 0) {

				delPlayList.add(m_strRequestPlaySongID);

				String song = song_ids.get(0);

				if (song == m_strRequestPlaySongID) {

					if (song_ids.size() == 1) {
						song_ids.clear();
					} else {
						int i = 0;

						while (i + 1 < song_ids.size()) {
							song_ids.set(i, song_ids.get(i + 1));
							i++;
						}

						song_ids.remove(i);
					}
				}

				displayEngageSong();
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + delPlayList + m_strRequestPlaySongID + song_ids);
	}

	@Override
	public void clickListListen() {
		Log.e(_toString() + TAG_MAIN, "clickListListen()");
		startLoading(getMethodName(), LOADING_LONG);
		super.clickListListen();
	}

	@Override
	public void clickListeningOther() {
		Log.e(_toString() + TAG_MAIN, "clickListeningOther()");
		startLoading(getMethodName(), LOADING_LONG);
		super.clickListeningOther();
	}

	/**
	 * 이븅신새꺄!!!어느정보 반복패턴이 있으면 함수로 만들어야지 그걸 죄처박고 앉았냐!!!
	 *
	 * @author isyoon
	 * @see Main2X#startSing(java.lang.String)
	 * @see Main2X#requestSing(int)
	 */
	@Override
	protected void startSing(String song) {

		Log.e(_toString() + TAG_MAIN, "startSing() " + "[ST]" + song);

		if (isLoading()) {
			Log.e(_toString() + TAG_MAIN, "startSing() " + "[NG]" + "[로딩]" + song);
			return;
		}

		startLoading(getMethodName(), LOADING_LONG);

		// 현재예약곡확인후 PLAY_STOP/PLAY_NEXT 결정
		int engage = PLAY_STOP;

		// 재생중이고예약곡이있으면
		if (video.isPlaying() && !TextUtil.isEmpty(getEngageSong())) {
			engage = PLAY_NEXT;
		}

		// 예약곡확인
		if (TextUtil.isNumeric(song)) {
			addEngageSongFirst(song);
		} else {
			song = getEngageSong();
		}

		if (!TextUtil.isNumeric(song)) {
			Log.e(_toString() + TAG_MAIN, "startSing() " + "[NG]" + "[곡번]" + song);
			return;
		}

		RemoveListenDisplay();

		requestSing(engage);

		Log.e(_toString() + TAG_MAIN, "startSing() " + "[ED]" + song);
	}

	private void requestSing(int engage) {
		Log.wtf(_toString() + TAG_MAIN, "requestSing() " + "[ST]" + PLAY_ENGAGE.get(engage));

		exitPopups();

		String song = getEngageSong();

		if (!TextUtil.isNumeric(song)) {
			String msg = getString(R.string.message_error_sing_number) + "(" + getString(R.string.message_error_title_number) + song + ")";
			ShowMessageAlert(msg);
			stopPlay(PLAY_STOP);
			return;
		}

		// 재생 중지
		stopPlay(engage);

		// 곡번호설정
		m_strRequestPlaySongID = song;

		if (isPlaying()) {
			// 븅신아취소해야될것아니여.
			if (m_timerStartSing != null) {
				m_timerStartSing.cancel();
				m_timerStartSing.purge();
				m_timerStartSing = null;
			}

			// 140422 예약된 반주곡을 자동 시작할 때는 중지 처리가 완전히 끝난 뒤에 새 요청 시작 (메인 > 부르기)
			m_timerStartSing = new Timer();
			TaskStartSing startSing = new TaskStartSing();

			startLoading(getMethodName(), LOADING_LONG);
			m_timerStartSing.schedule(startSing, TIMEOUT_WAIT_START_SING);
		} else {
			KP(REQUEST_SONG_PLAY, KP_1016, "", "");
		}

		SetTopNumber(m_strRequestPlaySongID);

		Log.wtf(_toString() + TAG_MAIN, "requestSing() " + "[ED]" + PLAY_ENGAGE.get(engage));
	}

	@Override
	public void StartPlaying() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.StartPlaying();

		showBottomGuideMenu(getMethodName());

		// if (arrPlayList.size() == 0) {
		showBottomGuideStopSong(); // 재생이 시작됨 + 예약곡 없음 = 반주곡 중지
		// }

		// if (player != null) {
		// player.setVisibility(View.VISIBLE);
		// }

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	/**
	 * 예약시작표시
	 */
	private void showBottomGuideSong() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + song_ids);

		if (song_ids.size() > 0) {
			showBottomGuideStartSong(); // 재생이 중지됨 = 반주곡 시작
		} else {
			hideBottomGuide02();
		}
	}

	@Override
	protected void stopListen() {
		super.stopListen();

		showBottomGuideSong();
	}

	@Override
	public void stopPlay(int engage) {
		super.stopPlay(engage);

		showBottomGuideSong();
		RemoveListenDisplay();

		//if (isPlaying()) {
		//	startLoading(getMethodName(), LOADING_LONG);
		//	postDelayed(stopLoading, TIMEOUT_HIDE_LOADING_DELAY);
		//}
	}

	///**
	// * 로딩타이머사용안함
	// */
	//@Override
	//protected void startLoading(String method, int time) {
	//	super.startLoading(method, time);
	//	removeCallbacks(stopLoading4Delay);
	//}
	//
	//@Override
	//protected void stopLoading(String method) {
	//	super.stopLoading(method);
	//	removeCallbacks(startLoading);
	//}
}
