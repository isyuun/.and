/*
 * Copyventright 2011 The Android Open Source Project
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
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	Karaoke.TV
 * filename	:	Main23.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main23.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import kr.kymedia.karaoke.play.impl.ISongPlay.ERROR;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.api.IKaraokeTV;

/**
 * <pre>
 * 열기 취소
 * 	긴급취소
 * 	긴급종료
 * 	긴급차단
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 5. 12.
 */
class Main3XXX extends Main3XX {
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
	 * 열기
	 */
	private boolean isOpening = false;

	@Override
	protected boolean isLoading() {
		// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + isOpening + ":" + super.isLoading() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		return (isOpening || super.isLoading());
	}

	/**
	 * @see Main2XX#startLoading(String, int)
	 */
	@Override
	protected void startLoading(String method, int time) {
		super.startLoading(method, time);
		isOpening = true;
	}

	/**
	 * @see Main2XX#stopLoading(String)
	 */
	@Override
	protected void stopLoading(String method) {
		super.stopLoading(method);
		isOpening = false;
	}

	/**
	 * 취소
	 */
	private boolean isCancel = false;

	/**
	 * <pre>
	 * 보류:열기취소
	 * </pre>
	 */
	protected void showCancel(final String msg) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying:" + isPlaying() + ":isMusicVideo:" + video.isMusicVideo());

		String str = msg;

		// if (!TextUtil.isEmpty(msg))
		// {
		// str += " " + getString(R.string.common_cancel);
		// str += " " + getString(R.string.common_ok);
		// }

		Log.i(_toString() + TAG_MAIN, str);

		if (isPlaying()) {
			str = "";
		}

		if (isCancel) {
			str = "";
		}

		if (!(isOpening || video.isMusicVideo())) {
			str = "";
		}

		// SetBottomGuideText02(R.drawable.btn_return, str); // 메뉴 열기/닫기 = 반주곡 열기 중지
		SetBottomGuideText03(str);

		setBottomProductText(getString(R.string.message_load_exit_app));
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#TryPlaySong()
	 */
	@Override
	public void TryPlaySong() {

		Log.i(_toString() + TAG_MAIN, "TryPlaySong() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());

		isOpening = true;

		showCancel("확인");

		(new TryPlaySong()).execute();
		// postDelayed(TryPlaySong, 100);

		Log.i(_toString() + TAG_MAIN, "TryPlaySong() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	private void super_TryPlaySong() {
		super.TryPlaySong();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#TryPlaySong()
	 */
	private class TryPlaySong extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			postDelayed(TryPlaySong, 100);
			return null;
		}

	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#TryPlaySong()
	 */
	private final Runnable TryPlaySong = new Runnable() {
		@Override
		public void run() {
			super_TryPlaySong();
		}
	};

	@Override
	protected void down() {

		Log.i(_toString() + TAG_MAIN, "down() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());

		isOpening = true;

		showCancel("다운");

		postDelayed(new Runnable() {

			@Override
			public void run() {

				super_down();
			}
		}, 100);

		Log.i(_toString() + TAG_MAIN, "down() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	protected void super_down() {
		super.down();
	}

	@Override
	protected void start() {

		Log.i(_toString() + TAG_MAIN, "start() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());

		isOpening = true;

		showCancel("시작");

		postDelayed(new Runnable() {

			@Override
			public void run() {

				super_start();
			}
		}, 100);

		Log.i(_toString() + TAG_MAIN, "start() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	private void super_start() {
		super.start();
	}

	@Override
	protected void open() {

		Log.i(_toString() + TAG_MAIN, "open() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + m_strRequestPlaySongID + ":" + url_lyric);
		if (TextUtil.isEmpty(url_lyric)) {
			return;
		}

		isOpening = true;

		showCancel("열기");

		(new open()).execute();

		Log.i(_toString() + TAG_MAIN, "open() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	protected void super_open() {
		super.open();
	}

	private final Runnable open = new Runnable() {
		@Override
		public void run() {
			super_open();
		}
	};

	private class open extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			postDelayed(open, 500);
			return null;
		}

	}

	@Override
	void CANCEL() {
		super.CANCEL();
		showCancel("");
	}

	@Override
	protected void play() {

		Log.i(_toString() + TAG_MAIN, "play() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());

		isOpening = false;

		showCancel("");

		postDelayed(new Runnable() {

			@Override
			public void run() {

				super_play();
			}
		}, 100);

		isCancel = false;

		Log.i(_toString() + TAG_MAIN, "play() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	private void super_play() {
		// _LOG.e(_toString() + TAG_MAIN, "super_play() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
		if (isCancel) {
			clear();
			if (player != null) {
				player.stop();
			}
		} else {
			super.play();
		}
		// _LOG.i(_toString() + TAG_MAIN, "super_play() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	@Override
	protected void stop(int engage) {

		Log.i(_toString() + TAG_MAIN, "stop() " + "[ST]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());

		isOpening = false;

		super.stop(engage);

		Log.i(_toString() + TAG_MAIN, "stop() " + "[ED]" + "opening:" + isOpening + ":cancel:" + isCancel + ":isPlaying" + isPlaying());
	}

	/**
	 * <pre>
	 * 전문취소 처리로 해야하나???
	 * </pre>
	 * @see Main2#KP(int, String, String, String)
	 * @see Main2X#KP(int, String, String, String)
	 * @see Main3X#KP(int, String, String, String)
	 * @see Main3XXX#KP(int, String, String, String)
	 * @see Main3XXXXX#VASSPPX(COMPLETE_VASS)
	 */
	@Override
	public void KP(final int request, final String OP, final String M1, final String M2) {
		// if (BuildConfig.DEBUG) _LOG.i(_toString() + TAG_MAIN, "KP() " + "[ST]" + REQUEST_KP.get(KP_REQUEST) + ", " + OP + ", " + M1 + ", " + M2);

		switch (request) {
			case REQUEST_SONG_PLAY:
				if (BuildConfig.DEBUG) Log.i(_toString() + TAG_MAIN, "KP() " + "[RQ]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
				isOpening = true;
				showCancel("조회");
				break;
			default:
				break;
		}

		super.KP(request, OP, M1, M2);

		// if (BuildConfig.DEBUG) _LOG.i(_toString() + TAG_MAIN, "KP() " + "[ED]" + REQUEST_KP.get(KP_REQUEST) + ", " + OP + ", " + M1 + ", " + M2);
	}

	/**
	 * <pre>
	 * 전문조회실패
	 *  응답오류
	 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *  전문결과표시
	 *    결과없음("result_code": "00901")
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	@Override
	protected void KP(Message msg) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg + ":" + KP.result_code);

		int state = msg.getData().getInt("state");

		//전문조회실패
		if (state == COMPLETE_ERROR_REQUEST_NOT_RESPONSE || state == COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE) {
			//응답오류
			showCancel("실패");
		} else {
			//전문결과
			SetBottomGuideText03("");
		}

		super.KP(msg);

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg + ":" + KP.result_code);
	}

	/**
	 * <pre>
	 * 전문취소 처리로 해야하나???
	 * </pre>
	 *
	 * @see Main3X#VASS(android.os.Message)
	 */
	@Override
	public void VASS(Message msg) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg + ":" + VASS.isSuccess());

		int state = msg.getData().getInt("state");

		//전문조회실패
		if (state == COMPLETE_ERROR_REQUEST_NOT_RESPONSE || state == COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE) {
			//응답오류
			showCancel("실패");
		} else {
			//전문결과
			SetBottomGuideText03("");
		}

		super.VASS(msg);

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg + ":" + VASS.isSuccess());
	}

	/**
	 * 긴급종료(5초)
	 */
	final static long TIME_EXIT = 5 * 1000;
	/**
	 * 긴급취소(3초)
	 */
	final static long TIME_CANCEL = 3 * 1000;

	/**
	 * 토~~~우스트
	 */
	Toast toast;

	private void showToast(Context context, CharSequence text, int duration) {
		if (toast != null) {
			toast.cancel();
		}

		toast = Toast.makeText(context, text, duration);

		if (toast != null) {
			toast.show();
		}
	}

	private void calcelToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

	/**
	 * <pre>
	 *   강제중지
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
		if (event == null) {
			return super.onKeyDown(keyCode, event);
		}

		if (BuildConfig.DEBUG) Log.v(_toString(), getMethodName() + ":" + remote.getState() + ":" + keyCode + ", " + event);

		boolean cancel = false;

		// 백키
		if (event != null && keyCode == KeyEvent.KEYCODE_BACK) {
			// 시작시간
			long downTime = event.getDownTime();
			// 발생시간
			long eventTime = event.getEventTime();
			// 누른시간
			long termTime = eventTime - downTime;

			if (termTime > TIME_EXIT) {
				// 긴급종료(5초)
				Log.wtf(_toString() + TAG_MAIN, "[취소]" + "onKeyDown()" + "[!!!EXIT!!!]" + ":" + remote.getState() + ":" + termTime + ":" + event.getRepeatCount());
				calcelToast();
				CANCEL();
				finish();
				return true;
			}

			if (termTime > TIME_CANCEL) {
				// 긴급취소(3초)
				Log.wtf(_toString() + TAG_MAIN, "[취소]" + "onKeyDown()" + "[!!!CANCEL!!!]" + ":" + remote.getState() + ":" + termTime + ":" + event.getRepeatCount());
				// cancel = true;
				CANCEL();
				stop(PLAY_STOP);
				return true;
			}

			// 긴급차단
			if (event.getRepeatCount() > 0) {
				Log.wtf(_toString() + TAG_MAIN, "[취소]" + "onKeyDown()" + "[!!!HOME!!!]" + ":" + remote.getState() + ":" + termTime + ":" + event.getRepeatCount());
				if (event.getRepeatCount() > 4) {
					if (BuildConfig.DEBUG) showToast(getApplicationContext(), "좀만~~~좀~~~더!!!!", Toast.LENGTH_SHORT);
					goHome();
				}
				return true;
			}

		}

		// 오픈 로딩시 강제중지
		if (isOpening && isLoading()) {
			// 색키
			if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
				// 노랑
				if (keyCode == KeyEvent.KEYCODE_PROG_YELLOW || keyCode == KeyEvent.KEYCODE_PROG_RED) {
					cancel = true;
				}
			} else {
				// 녹색
				if (keyCode == KeyEvent.KEYCODE_PROG_GREEN || keyCode == KeyEvent.KEYCODE_PROG_RED) {
					cancel = true;
				}
			}
			// 중지
			if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
				cancel = true;
			}
		}

		if (cancel) {
			CANCEL();
			stop(PLAY_STOP);
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 오류발생시:점수표시/다음곡재생안함
	 */
	protected boolean isError = false;

	/**
	 * 오류발생시:점수표시/다음곡재생안함
	 */
	@Override
	public void ShowScore() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + isError);
		if (!isError) {
			super.ShowScore();
		}
		isError = false;
	}

	/**
	 * 오류발생시:점수표시/다음곡재생안함
	 */
	@Override
	public void HideScore() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		if (!isError) {
			super.HideScore();
		}
		isError = false;
	}

	/**
	 * 오류처리및예약곡(현재곡)삭제
	 *
	 * @param isError
	 */
	protected void delEngageSong(boolean isError) {
		this.isError = isError;
		delEngageSong();
	}

	/**
	 * 오류발생시:점수표시/다음곡재생안함
	 */
	@Override
	public void onDownError(ERROR t, Exception e) {

		Log.d(_toString() + TAG_SING, "onDownError() ");
		delEngageSong(true);
		super.onDownError(t, e);
	}

	/**
	 * 오류발생시:점수표시/다음곡재생안함
	 */
	@Override
	protected void onPlayerPrepared(MediaPlayer mp) {
		isError = false;
		super.onPlayerPrepared(mp);
	}

	/**
	 * 오류발생시:점수표시/다음곡재생안함
	 */
	@Override
	protected void onPlayerCompletion(MediaPlayer mp) {
		super.onPlayerCompletion(mp);
	}

	@Override
	protected boolean onPlayerError(MediaPlayer mp, int what, int extra) {
		delEngageSong(true);
		return super.onPlayerError(mp, what, extra);
	}

	@Override
	protected void onVideoPrepared(MediaPlayer mp) {
		super.onVideoPrepared(mp);
	}

	@Override
	protected void onVideoCompletion(MediaPlayer mp) {
		super.onVideoCompletion(mp);
	}

	@Override
	protected boolean onVideoInfo(MediaPlayer mp, int what, int extra) {
		return super.onVideoInfo(mp, what, extra);
	}

	@Override
	protected boolean onVideoError(MediaPlayer mp, int what, int extra) {
		return super.onVideoError(mp, what, extra);
	}

	@Override
	protected void onListenPrepared(MediaPlayer mp) {
		super.onListenPrepared(mp);
		//녹음곡중지버튼표시
		showBottomGuideStopSong();
	}

	@Override
	protected void onListenCompletion(MediaPlayer mp) {
		super.onListenCompletion(mp);
	}

	@Override
	protected boolean onListenError(MediaPlayer mp, int what, int extra) {
		return super.onListenError(mp, what, extra);
	}

}
