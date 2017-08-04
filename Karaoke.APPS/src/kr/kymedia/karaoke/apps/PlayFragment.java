/*
 * Copyright 2011 Tiew.he Android Open Source Project
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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	PlayFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ PlayFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPItemShareData;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.api.LyricsUtil;
import kr.kymedia.karaoke.apps.gcm.GCM2Activity;
import kr.kymedia.karaoke.apps.impl.IBaseDialog;
import kr.kymedia.karaoke.apps.impl.IPlayFragment;
import kr.kymedia.karaoke.data.SongItem;
import kr.kymedia.karaoke.data.SongUtil;
import kr.kymedia.karaoke.kpop._playSingFragment;
import kr.kymedia.karaoke.kpop.listenpost;
import kr.kymedia.karaoke.play3.Player;
import kr.kymedia.karaoke.play3.Player.TYPE;
import kr.kymedia.karaoke.play3.SongView;
import kr.kymedia.karaoke.record.SongRecorder5;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.util.Util;
import kr.kymedia.karaoke.widget.SeekBarClickThumb;
import kr.kymedia.karaoke.widget.SeekBarClickThumb.OnClickSeekBarChangeListener;
import kr.kymedia.karaoke.widget.TrackerTime;
import kr.kymedia.karaoke.widget.util.BitmapUtils2;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 19.
 * @version 1.0
 * @since 2012. 11. 8.
 * @version 1.1 자막
 */

class PlayFragment extends BaseListFragment implements IPlayFragment, IBaseDialog, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnPreparedListener, MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener, OnClickListener, OnTouchListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected ViewSwitcher mViewSwitcher = null;
	protected Timer mTickViewSwitcher = null;
	protected Timer mTickComments = null;

	/**
	 * KP_2011 댓글 좌우 스크롤
	 */
	protected KPnnnn KP_2011 = null;

	/**
	 * 댓글 좌우 스크롤
	 * 
	 * @param inc
	 */
	public void KP_2011(int inc) {
		page = page + inc;

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		// 처음조회
		if (inc == 0) {
			page = 1;
			total_page = 0;
		}

		// 페이징확인
		if (total_page != 0 && (page > total_page || page < 1)) {
			page = page - inc;
			return;
		}

		// 순차조회필요없음
		KP_2011.clear();
		KP_2011.KP_2011(getApp().p_mid, p_m1, p_m2, play_id, page);

	}

	/**
	 * 댓글좌우스크롤처리 <br>
	 * 결과가 없어도처리
	 */
	public void KP2011(KPnnnn KP_xxxx, int page) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		if (page < 1) {
			return;
		}

		if (KP_xxxx == null) {
			KP_xxxx = KP_2011;
		}

		KPItem info = KP_xxxx.getInfo();
		ArrayList<KPItem> lists = KP_xxxx.getLists();

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (info != null) {
			total_page = TextUtil.parseInt(info.getValue("total_page") == null ? "1" : info.getValue("total_page"));
		}

		int res = 0;
		String name = "";
		String num = "";
		String url = "";
		String ment = "";

		View v = null;

		KPItem list = null;

		for (int i = 0; i < 5; i++) {
			num = String.format("%02d", i + 1);
			url = null;
			ment = null;

			list = null;

			int index = i + (page - 1) * 5;
			if (index < lists.size()) {
				list = lists.get(index);
			}

			if (list != null) {
				name = list.getValue("name");
				url = list.getValue("url_profile");
				ment = "(" + name + ":)\n" + list.getValue("ment");
			}

			if (getView() == null) {
				return;
			}

			res = getResource("btn_player_people_" + num, "id");
			v = findViewById(res);
			if (v != null) {
				// if (URLUtil.isNetworkUrl(url)) {
				// putURLImage(mContext, (ImageView) v, url, false);
				// } else {
				// ((ImageView) v).setImageResource(R.drawable.ic_menu_01);
				// }
				putURLImage(mContext, (ImageView) v, url, false, R.drawable.ic_menu_01);
			}

			res = getResource("txt_comment_" + num, "id");
			v = findViewById(res);
			if (v != null) {
				putValue(v, ment);
				v.setVisibility(View.GONE);
				if (URLUtil.isNetworkUrl(url)) {
					BitmapUtils2.putURLCompoundDrawable(mContext, (TextView) v, url, BitmapUtils2.Position.left);
				} else {
					// Drawable d = getDrawable("ic_menu_01");
					Drawable d = WidgetUtils.getDrawable(getApp(), R.drawable.ic_menu_01);
					if (d != null) {
						d.setBounds(new Rect(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()));
						Drawable ds[] = ((TextView) v).getCompoundDrawables();
						ds[0] = d;
						// if (pos == Position.left) {
						// ds[0] = d;
						// } else if (pos == Position.top) {
						// ds[1] = d;
						// } else if (pos == Position.right) {
						// ds[2] = d;
						// } else if (pos == Position.bottom) {
						// ds[3] = d;
						// }
						((TextView) v).setCompoundDrawables(ds[0], ds[1], ds[2], ds[3]);

					}
				}
			}
		}
	}

	public void showComment(int idx) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + idx);

		ArrayList<KPItem> lists = KP_2011.getLists();
		KPItem list = null;
		String ment = null;

		if (lists == null) {
			showCommentEdit();
			return;
		}

		if (idx < 0) {
			showCommentEdit();
			return;
		}

		list = null;
		if (idx < lists.size()) {
			list = lists.get(idx);
		} else {
			showCommentEdit();
			return;
		}

		if (list != null) {
			ment = list.getValue("ment");
		}

		if (ment == null || TextUtil.isEmpty(ment)) {
			showCommentEdit();
			return;
		}

		String num = String.format("%02d", idx + 1);
		int res = getResource("txt_comment_" + num, "id");
		View v = findViewById(res);

		if (v != null) {
			if (v.getVisibility() != View.VISIBLE) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}
		}

	}

	/**
	 * 평가 & 댓글 비로그인회원차단
	 */
	//
	public void showCommentEdit() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		Bundle args = new Bundle();
		if (!isLoginUser()) {
			args.putString("message", getString(R.string.errmsg_login));
			getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, args);
			return;
		}

		View v = findViewById(R.id.include_player_reply);
		if (v == null) {
			return;
		}

		if (v.getVisibility() != View.VISIBLE) {
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}

		setOnPause();
	}

	/**
	 * onPause()관련 액션설정 1. SNS/코멘트 작성 중에는 기다려 2. 아니면 종료처리
	 */
	protected void setOnPause() {
		View v = findViewById(R.id.include_player_reply);
		if (v != null && v.getVisibility() == View.GONE) {
			isOnPauseClose = false;
			isOnPausePause = false;
			setRedraw(true);
		} else {
			isOnPauseClose = true;
			isOnPausePause = true;
			setRedraw(false);
		}
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isOnPauseClose + ", " + isOnPausePause);
	}

	// protected void startTickViewSwitcher() {
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName());
	// }
	protected void startTickViewSwitcher() {

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
		if (mViewSwitcher != null && mTickViewSwitcher == null) {
			mTickViewSwitcher = new Timer();
			mTickViewSwitcher.schedule(new switchViewSwitcher(), _IKaraoke.TIMER_FLIPER * 2, _IKaraoke.TIMER_FLIPER);
		}
	}

	class switchViewSwitcher extends TimerTask {
		@Override
		public void run() {
			// mPlayHandler.post(switchViewSwitcher);
		}
	}

	// 타이머 생성
	TimerTask switchViewSwitcher = new TimerTask() {
		@Override
		public void run() {
			if (mViewSwitcher != null) {
				mViewSwitcher.showNext();
			}
		}
	};

	// protected void stopTickViewSwitcher() {
	// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName());
	// if (mTimerTick != null) {
	// mTimerTick.cancel();
	// mTimerTick.purge();
	// mTimerTick = null;
	// }
	// }

	protected void stopTickViewSwitcher() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		if (mTickViewSwitcher != null) {
			mTickViewSwitcher.cancel();
			mTickViewSwitcher.purge();
			mTickViewSwitcher = null;
		}
	}

	protected void startTickComments() {

		// super.startTimer();
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
		if (mTickComments == null) {
			mTickComments = new Timer();
			mTickComments.schedule(new switchComments(), 0, _IKaraoke.TIMER_FLIPER);
		}
	}

	class switchComments extends TimerTask {
		@Override
		public void run() {
			// mPlayHandler.post(switchComments);
		}
	}

	// 타이머 생성
	TimerTask switchComments = new TimerTask() {
		@Override
		public void run() {
			try {
				ArrayList<KPItem> lists = KP_2011.getLists();
				if (lists != null && lists.size() > 0) {
					Random ran = new Random();
					int num = ran.nextInt(lists.size());
					if (num < lists.size()) {
						showComment(num);
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	};

	protected void stopTickComments() {

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		try {
			if (mTickComments != null) {
				mTickComments.cancel();
				mTickComments.purge();
				mTickComments = null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private Player player = null;

	protected Player getPlayer() {
		return player;
	}

	/**
	 * 반주곡/녹음곡 재생구분 PlayView.TYPE.SONG : 반주곡 PlayView.TYPE.RECORD : 녹음곡
	 */
	protected Player.TYPE mPlayType = Player.TYPE.SING;

	/**
	 * 녹음여부확인
	 */
	private boolean isRecord = false;
	/**
	 * 녹음클래스
	 */
	protected SongRecorder5 mRecord = null;

	protected Timer mTimerTick = null;
	private TextView mTextViewCur = null;
	private TextView mTextViewTot = null;
	private SeekBarClickThumb mPlayTimeBar = null;
	private TrackerTime mTrackerTime = null;

	public CheckBox mRecButton = null;

	public ImageButton mUpButton = null;

	/**
	 * 녹음시간확인용 -1 : 녹음종료<br>
	 * 밀리세컨드
	 */
	private long rec_start = -1;

	/**
	 * 녹음시간확인용
	 */
	private long getRecTime() {
		long ret = 0;

		try {
			if (rec_start > -1) {
				ret = (System.currentTimeMillis() - rec_start);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + ret + "," + rec_time);

		return ret;
	}

	/**
	 * 재생시간확인용 -1 : 재생종료
	 */
	private long mPlayTimeStart;

	/**
	 * 녹음시간확인용
	 */
	long getPlayTime() {
		long ret = 0;

		try {
			// 1초이상 현재보다 이전
			if (mPlayTimeStart > 0 && mPlayTimeStart < System.currentTimeMillis()) {
				ret = System.currentTimeMillis() - mPlayTimeStart;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ret;
	}

	public void setPlayTime() {
		if (_IKaraoke.DEBUG) {
			if (findViewById(R.id.textPlay) != null) {
				((TextView) findViewById(R.id.textPlay)).setVisibility(View.VISIBLE);
			}
		} else {
			if (findViewById(R.id.textPlay) != null) {
				((TextView) findViewById(R.id.textPlay)).setVisibility(View.GONE);
			}
			return;
		}

		long tak = getPlayTime();
		int cur = mPlayTimeBar.getProgress();
		int tot = mPlayTimeBar.getMax();
		String min, sec, msec;

		min = String.format(Locale.getDefault(), "%02d", cur / 60000);
		sec = String.format(Locale.getDefault(), "%02d", (cur % 60000) / 1000);
		msec = String.format(Locale.getDefault(), "%02d", (cur % 1000) / 10);
		String pos = min + ":" + sec + "." + msec;

		min = String.format(Locale.getDefault(), "%02d", tot / 60000);
		sec = String.format(Locale.getDefault(), "%02d", (tot % 60000) / 1000);
		msec = String.format(Locale.getDefault(), "%02d", (tot % 1000) / 10);
		String dur = min + ":" + sec + "." + msec;

		min = String.format(Locale.getDefault(), "%02d", tak / 60000);
		sec = String.format(Locale.getDefault(), "%02d", (tak % 60000) / 1000);
		msec = String.format(Locale.getDefault(), "%02d", (tak % 1000) / 10);
		String tik = min + ":" + sec + "." + msec;

		String play = pos + "(" + tik + ")" + "/" + dur;
		// play += "-" + getID();
		if (findViewById(R.id.textPlay) != null) {
			((TextView) findViewById(R.id.textPlay)).setText(play);
		}

		min = String.format(Locale.getDefault(), "%02d", cur / 60000);
		sec = String.format(Locale.getDefault(), "%02d", (cur % 60000) / 1000);
		msec = String.format(Locale.getDefault(), "%02d", (cur % 1000) / 10);
		pos = min + ":" + sec;
		if (findViewById(R.id.cur) != null) {
			((TextView) findViewById(R.id.cur)).setText(pos);
		}

		min = String.format(Locale.getDefault(), "%02d", tot / 60000);
		sec = String.format(Locale.getDefault(), "%02d", (tot % 60000) / 1000);
		msec = String.format(Locale.getDefault(), "%02d", (tot % 1000) / 10);
		dur = min + ":" + sec;
		if (findViewById(R.id.tot) != null) {
			((TextView) findViewById(R.id.tot)).setText(dur);
		}
	}

	/**
	 * onPause 플래그초기화 isCloseOnPause = false isPauseOnPause = false
	 * 
	 * @see #isOnPauseClose
	 * @see #isOnPausePause
	 */
	protected void resetOnPause() {
		isOnPauseClose = false;
		isOnPausePause = false;
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + isOnPauseClose + ", " + isOnPausePause);
	}

	/**
	 * 잠깐그리고멈춘다.
	 */
	public void setRedrawPause() {
		setRedraw(false);
		postDelayed(new Runnable() {

			@Override
			public void run() {

				setRedraw(true);
			}
		}, 100);
	}

	/**
	 * 드로우차단 true : 멈춤 false : 드로우시작 hownam: 화면회전전환시 일시정지할 필요있습니다.느려지네요.
	 * 
	 * @param redraw
	 */
	public void setRedraw(boolean redraw) {
		try {
			if (player != null) {
				player.setRedraw(redraw);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 재생관련전문전용
	 */
	protected KPnnnn KP_play = null;
	/**
	 * // KP_1011 파일업로드 (폐기->유지)
	 */
	private KPnnnn KP_1011 = null;
	/**
	 * // KP_1012 재생시간 기록
	 */
	private KPnnnn KP_1012 = null;

	// protected Handler mHandlerPlay = new Handler() {
	//
	// /**
	// * /**
	// *
	// * @see android.os.Handler#handleMessage(android.os.Message)
	// */
	// @Override
	// public void handleMessage(Message msg) {
	//
	// super.handleMessage(msg);
	//
	// if (isDetached() || isDestroyed()) {
	// return;
	// }
	//
	// int cur = msg.arg1;
	// int tot = msg.arg2;
	// String err = "";
	// Bundle bundle = msg.getData();
	// if (bundle != null) {
	// err += bundle.getString("result_message");
	// if (msg.what == STATE_SONG_PLAY_ERROR) {
	// err += " - " + bundle.getString("opcode");
	// err += "(" + bundle.getString("result_code") + ")";
	// }
	// }
	//
	// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + cur + ":" + tot);
	//
	// if (msg.what == STATE_SONG_PLAY_ERROR) {
	// getApp().popupToast(err, Toast.LENGTH_LONG);
	// } else if (msg.what == STATE_SONG_PLAY_MESSAGE) {
	// getApp().popupToast(err, Toast.LENGTH_LONG);
	// } else if ( msg.what == STATE_SONG_PLAY_START ) {
	// onPrepared(null);
	// } else if ( msg.what == STATE_SONG_PLAY_RUNNING ) {
	// onCompletion(null);
	// } else {
	// //일시정지에서 왜넘어오는지 모르것다.
	// if (player != null && !player.isPlaying()) {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "[NG]" + cur + ":" + tot);
	// return;
	// }
	//
	// //cur=0은무시한다.줸장...
	// if (cur > 0) {
	// updateTimeStamp(cur, tot);
	// }
	//
	// showUploadButton();
	//
	// if (isUploadUser() && isRecord && isRecording()) {
	// checkRecordFile();
	// }
	// }
	// }
	//
	// };
	protected HandlerPlay mHandlerPlay;

	static class HandlerPlay extends Handler {
		WeakReference<PlayFragment> m_HandlerObj;

		HandlerPlay(PlayFragment handlerobj) {
			m_HandlerObj = new WeakReference<PlayFragment>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			PlayFragment handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			handlerobj.handleMessagePlay(msg);
		}
	}

	public void handleMessagePlay(Message msg) {

		if (isDetached() || isDestroyed()) {
			return;
		}

		int cur = msg.arg1;
		int tot = msg.arg2;
		String err = "";
		Bundle bundle = msg.getData();
		if (bundle != null) {
			err += bundle.getString("result_message");
			if (msg.what == _IKaraoke.STATE_SONG_PLAY_ERROR) {
				err += " - " + bundle.getString("opcode");
				err += "(" + bundle.getString("result_code") + ")";
			}
		}

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + cur + ":" + tot);

		if (msg.what == _IKaraoke.STATE_SONG_PLAY_ERROR) {
			getApp().popupToast(err, Toast.LENGTH_LONG);
		} else if (msg.what == _IKaraoke.STATE_SONG_PLAY_MESSAGE) {
			getApp().popupToast(err, Toast.LENGTH_LONG);
		} else if (msg.what == _IKaraoke.STATE_SONG_PLAY_START) {
			onPrepared(null);
		} else if (msg.what == _IKaraoke.STATE_SONG_PLAY_RUNNING) {
			onCompletion(null);
		} else {
			// 일시정지에서 왜넘어오는지 모르것다.
			if (player != null && !player.isPlaying()) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG]" + cur + ":" + tot);
				return;
			}

			// cur=0은무시한다.줸장...
			if (cur > 0) {
				updateTimeStamp(cur, tot);
			}

			showUploadButton();

			if (isUploadUser() && isRecord && isRecording()) {
				checkRecordFile();
			}
		}
	}

	/**
	 * 무료곡재생시 재생시간을 play_time으로 치환
	 * 
	 * @param cur
	 * @param tot
	 */
	void updateTimeStamp(int cur, int tot) {

		try {

			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "-" + cur + ":" + tot);

			if (mPlayType == Player.TYPE.SING) {
				if (isPassUser()) {
					// 녹음처리중처리
					if (isRecord) {
						mPlayTimeBar.setEnabled(false);
					} else {
						mPlayTimeBar.setEnabled(true);
					}
				} else {
					// 1분미리듣기처리
					if (tot > play_time) {
						tot = play_time;
					}
					if (cur > play_time) {
						cur = play_time;
					}
					mPlayTimeBar.setEnabled(false);
				}

			} else {
				mPlayTimeBar.setEnabled(true);
			}

			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + cur + ":" + tot);

			String pos = String.format("%02d:%02d", cur / 60000, (cur % 60000) / 1000);
			String dur = String.format("%02d:%02d", tot / 60000, (tot % 60000) / 1000);

			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + pos + " / " + dur);

			mTextViewCur.setText(pos);
			mTextViewTot.setText(dur);

			// 타임트래커가없어지믄처리
			if (!mTrackerTime.isShow() || !mTrackerTime.isShowing()) {
				if (mPlayTimeBar.getMax() != tot) {
					mPlayTimeBar.setMax(tot);
				}
				mPlayTimeBar.setProgress(cur);
			}

			setPlayTime();

		} catch (Exception e) {

			// e.printStackTrace();
		}
	}

	public void setProgressButton() {

		if (mPlayTimeBar == null) {
			return;
		}

		if (player == null) {
			return;
		}

		int id = R.drawable.btn_pause;

		if (!player.isPlaying() || player.isPaused()) {
			id = R.drawable.btn_play;
		}

		mPlayTimeBar.setThumb(id);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + player.isPaused());
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_play = KP_init(mContext, KP_play);

		KP_2011 = KP_init(mContext, KP_2011);

		KP_1012 = KP_init(mContext, KP_1012);
		KP_1012.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			@Deprecated
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			@Deprecated
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

		KP_1011 = KP_init(mContext, KP_1011);
		KP_1011.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				// KP_1011 업로드결과확인
				if (what == _IKaraoke.STATE_DATA_QUERY_START) {
					return;
				}
				try {
					getBaseActivity().dismissDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
					getBaseActivity().removeDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
				} catch (Exception e) {

					// e.printStackTrace();
				}

				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

				if (("00000").equalsIgnoreCase(r_code)) {
					// 녹음곡 업로드완료
					// String packageName = "kr.kymedia.karaoke.kpop";
					// String className = "kr.kymedia.karaoke.kpop.feelpost";
					// openACTIONSHARE(KP_1011.getInfo(), getList(), true, packageName, className);
					// return;
				} else {
					// 녹음곡 업로드실패
					close();
				}
			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			@Deprecated
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			@Deprecated
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

	}

	@Override
	protected void onActivityCreated() {
		super.onActivityCreated();

		try {
			player();
		} catch (Exception e) {

			String where = __CLASSNAME__ + ".onActivityCreated()";
			showMediaError(MEDIAERROR.TYPE.MEDIAPLAYER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_PLAYER_START_ERROR, Log2.getStackTraceString(e), true);
			e.printStackTrace();
		}

		resetOnPause();

		mRecButton = (CheckBox) findViewById(R.id.rec);
		mRecButton.setOnClickListener(this);

		if (mPlayType == Player.TYPE.SING) {
			mRecButton.setVisibility(View.VISIBLE);
		} else if (mPlayType == Player.TYPE.LISTEN) {
			mRecButton.setVisibility(View.GONE);
		}

		mTextViewCur = (TextView) findViewById(R.id.cur);
		mTextViewTot = (TextView) findViewById(R.id.tot);

		mPlayTimeBar = (SeekBarClickThumb) findViewById(R.id.seek);
		mPlayTimeBar.setEnabled(false);
		// mSeekBarTime.setClickable(true);
		mPlayTimeBar.setOnClickSeekBarChangeListener(new OnClickSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				// int pos = seekBar.getProgress();
				// int max = seekBar.getMax();
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + fromUser + "," + pos + ", " + max);

				if (fromUser) {
					if (!mTrackerTime.isShow() || !mTrackerTime.isShowing()) {
						View v = findViewById(R.id.seek);
						mTrackerTime.showPopupTime(v, true);
					}
					mTrackerTime.updatePopupTime(progress);
				} else {
					if (player == null) {
						return;
					}

					int cur = player.currentTime();
					int tot = player.totalTime();

					// 로그인안되면훅가게한다.
					if (mPlayType == Player.TYPE.SING && !isPassUser()) {
						if (tot > play_time && cur > play_time) {
							setCurTime(tot, true);
						}
					}

					play_prog = progress;

				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// int pos = seekBar.getProgress();
				// int max = seekBar.getMax();
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + pos + ", " + max + ", " + pos / 1000);


				View v = findViewById(R.id.include_player_bar);
				mTrackerTime.showPopupTime(v, true);

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// int pos = seekBar.getProgress();
				// int max = seekBar.getMax();
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + pos + ", " + max + ", " + pos / 1000);


				View v = findViewById(R.id.include_player_bar);
				mTrackerTime.showPopupTime(v, false);

				setCurTime(seekBar.getProgress(), false);
			}

			/**
			 * 프로그래스버튼클릭시처리
			 */
			@Override
			public void onClickSeekBarTumb() {
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
				if (player != null) {
					if (!player.isPlaying() || player.isPaused()) {
						resume();
					} else {
						pause();
					}
				}
			}

		}, this);

		mTrackerTime = new TrackerTime(getBaseActivity(), getResources().getColor(R.color.solid_white));

		mUpButton = (ImageButton) findViewById(R.id.up);
		if (mUpButton != null) {
			mUpButton.setVisibility(View.GONE);
		}

		if (mPlayType == Player.TYPE.SING) {
			hideShowButton();
		}

		setRecord(false, false);

	}

	protected void player() {
		if (mHandlerPlay == null) {
			mHandlerPlay = new HandlerPlay(this);
		}

		if (player == null) {
			player = new Player(getBaseActivity(), mPlayType, mHandlerPlay);
		}

		ViewGroup play = (ViewGroup) findViewById(R.id.play);
		if (play != null) {
			// player.setBackground();
			// player.setBackgroundTransparent(true);
			// if (_IKaraoke.IS_ABOVE_ICE_CREAM_SANDWICH) {
			// player.setBackgroundTransparent(true);
			// } else {
			// //진저브레드 재생배경 투명오류
			// setPlayerBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.bg_play));
			// }
			player.setBackgroundTransparent(true);
			play.addView((View) player.getPlayView());
			// setRedrawPause();
		}

		sync_type = getApp().getSharedPreferences().getBoolean("sync_type", false);

		// 자막위치
		setLyricAlign(Player.ALIGN.BOTTOM);

		// 자막마진
		setLyricMargin();

		isShowPlayer = (CheckBox) findViewById(R.id.show);
		isShowPlayer.setEnabled(false);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		// 일시정지상태초기화
		resetOnPause();

	}

	@Override
	public Intent openACTIONSHARE(KPItem info, KPItem list, boolean open, String packageName, String className) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + packageName + className);

		Intent ret = super.openACTIONSHARE(info, list, open, packageName, className);
		resetOnPause();
		return ret;
	}

	/**
	 * 
	 * 
	 * @see BaseFragment2#onResume()
	 */
	@Override
	public void onResume() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onResume();

		cancelPlayNotification();

		// if (player != null) {
		// //resume();
		// /*if (player.isPaused()) {
		// setRedrawPause();
		// } else {
		// setRedraw(false);
		// }*/
		// }
		setRedraw(false);
	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {

		// 일단기냥냅둬
		// isOnPauseClose = false;
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + ":" + isOnPauseClose + "," + isOnPausePause);

		if (isOnPauseClose) {
			close();
		} else {
			if (isOnPausePause) {
				// onPause()인경우 일시정지
				pause();
			} else {
				// onPause()인경우 드로정지
				setRedraw(true);
				// 상태표시줄에재생중표시
				openPlayNotification();
			}
		}

		super.onPause();
	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onDestroyView();

		setDestroyed(true);

		release();
	}

	/**
	 * 
	 * 
	 * @see android.support.v4.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		super.onDestroy();

		KP_1012();

		cancelPlayNotification();
	}

	protected void putSongInfo() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		try {
			KPItem list = getList();

			if (list == null) {
				getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
				return;
			}

			String id = "";
			String title = "";
			String artist = "";
			String heart = "";

			if (mPlayType == Player.TYPE.SING) {
				id = list.getValue("song_id");
				title = list.getValue("title");
				artist = list.getValue("artist");
			} else if (mPlayType == Player.TYPE.LISTEN) {
				id = list.getValue("song_id");
				title = list.getValue("title");
				if (!TextUtil.isEmpty(list.getValue("artist"))) {
					title += "-" + list.getValue("artist");
				}

				artist = list.getValue(LOGIN.KEY_NICKNAME);
			}
			heart = list.getValue("heart");

			TextView v = null;

			// v = (TextView) findViewById(R.id.id);
			// putValue(v, id);
			// v = (TextView) findViewById(R.id.title);
			// putValue(v, title);
			// v = (TextView) findViewById(R.id.name);
			// putValue(v, artist);
			// v = (TextView) findViewById(R.id.heart);
			// heart = TextUtil.numberFormat(heart);
			// putValue(v, heart);

			if (findViewById(R.id.include_player_title) != null) {
				v = (TextView) findViewById(R.id.include_player_title).findViewById(R.id.id);
				putValue(v, id);
				v = (TextView) findViewById(R.id.include_player_title).findViewById(R.id.title);
				putValue(v, title);
				v = (TextView) findViewById(R.id.include_player_title).findViewById(R.id.name);
				putValue(v, artist);
				v = (TextView) findViewById(R.id.include_player_title).findViewById(R.id.heart);
				heart = TextUtil.numberFormat(heart);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ":" + getApp().p_passtype);


		super.KP_nnnn();

		putSongInfo();

	}

	/**
	 * // KP_1012 재생시간 기록 반주곡재생시간기록
	 * 
	 */
	public void KP_1012() {
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + getPlayTime());

		// 로그인확인
		// if (getApp().p_mid == null || TextUtil.isEmpty(getApp().p_mid)) {
		// return;
		// }

		// 이용권확인
		// if (getApp().p_passtype == null || TextUtil.isEmpty(getApp().p_passtype) || getApp().p_passtype.equalsIgnoreCase("N")) {
		// return;
		// }

		// 녹음곡재생시간도 기록(광고 통계용)
		// 반주곡여부확인
		if (mPlayType != Player.TYPE.SING) {
			return;
		}

		if (KP_play == null) {
			return;
		}

		// 1초미만확인
		if (getPlayTime() < 60000) {
			return;
		}

		KPItem info = KP_play.getInfo();
		KPItem list = KP_play.getList(0);

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			String play_id = list.getValue("play_id");
			String song_id = list.getValue("song_id");
			String type = list.getValue("type");
			long time = getPlayTime() / 1000;
			// KP_1012 재생시간 기록
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + time);
			if (time > 60) {
				KP_1012.KP_1012(getApp().p_mid, p_m1, p_m2, play_id, Long.toString(time), song_id, type);
				mPlayTimeStart = 0;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + "\n" + r_message + "\n" + r_info);

		if (mBackPressed) {
			return;
		}

		if (("KP_1010").equalsIgnoreCase(p_opcode) || ("KP_1016").equalsIgnoreCase(p_opcode) || ("KP_2010").equalsIgnoreCase(p_opcode) || ("KP_2016").equalsIgnoreCase(p_opcode)) {
			// KP_1010/KP_1013 반주곡재생 //KP_2010/KP_2016 녹음곡재생
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}
			// 재생정상
			this.p_opcode = p_opcode;
			this.r_code = r_code;
			this.r_message = r_message;
			this.r_info = r_info;
			if (("00000").equalsIgnoreCase(r_code) || r_code.equalsIgnoreCase("99980") || r_code.equalsIgnoreCase("99981")) {
				KPnnnn();
			} else {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				stop();
			}
		} else if (("KP_4011").equalsIgnoreCase(p_opcode) || ("KP_4003").equalsIgnoreCase(p_opcode)) {
			// 기존:KP_4011 holic 상품권 구매(홀릭차감)<br>
			// 신규:KP_4003(이용권 구매:KP_4002 -> KP_4003 연동)<br>
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}
			getApp().KP4003();
			setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
			if (("00000").equalsIgnoreCase(r_code)) {
				getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), r_message, getString(R.string.alert_title_confirm), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						KP_nnnn();
					}
				}, null, null, true, new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						KP_nnnn();
					}
				});
			} else {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
			}
		} else if (("KP_1011").equalsIgnoreCase(p_opcode)) {
			// KP_1011 업로드결과확인
			if (what == _IKaraoke.STATE_DATA_QUERY_START) {
				return;
			}
			try {
				getBaseActivity().dismissDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
				getBaseActivity().removeDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
			} catch (Exception e) {

				// e.printStackTrace();
			}

			KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

			if (("00000").equalsIgnoreCase(r_code)) {
				// 녹음곡 업로드완료
				// String packageName = "kr.kymedia.karaoke.kpop";
				// String className = "kr.kymedia.karaoke.kpop.feelpost";
				// openACTIONSHARE(KP_1011.getInfo(), getList(), true, packageName, className);
			} else {
				// 녹음곡 업로드실패
				close();
			}

		} else if (("KP_1012").equalsIgnoreCase(p_opcode)) {
			// KP_1012 재생시간 기록
			return;
		}

	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_play != null ? KP_play.p_opcode : "");
		super.KPnnnn();

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[BF]:" + getApp().p_passtype + "," + play_time + "," + rec_time);

		// 재생종류
		// play_type
		// L:1분미리듣기
		// F:일일무료곡
		// P:유료재생
		// play_type = "L";
		// if (isPass()) {
		// play_type = "P";
		// }
		KPItem info = KP_play.getInfo();

		if (info == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "info", info);
			return;
		}

		boolean save = false;

		if (!TextUtil.isEmpty(info.getValue("is_recommend"))) {
			if (("true").equalsIgnoreCase(info.getValue("is_recommend"))) {
				getApp().is_recommend = true;
			} else {
				getApp().is_recommend = false;
			}
			save = true;
		}

		if (!TextUtil.isEmpty(info.getValue("getApp().kp_error_report"))) {
			if (("true").equalsIgnoreCase(info.getValue("getApp().kp_error_report"))) {
				getApp().kp_error_report = true;
			} else {
				getApp().kp_error_report = false;
			}
			save = true;
		}

		if (!TextUtil.isEmpty(info.getValue(LOGIN.KEY_MID))) {
			getApp().p_mid = info.getValue(LOGIN.KEY_MID);
			save = true;
		}

		if (!TextUtil.isEmpty(info.getValue(LOGIN.KEY_EMAIL))) {
			getApp().p_email = info.getValue(LOGIN.KEY_EMAIL);
			save = true;
		}

		if (!TextUtil.isEmpty(info.getValue(LOGIN.KEY_PASSTYPE))) {
			getApp().p_passtype = info.getValue(LOGIN.KEY_PASSTYPE);
			save = true;
		}

		if (save) {
			// putLogin();
		}

		KPItem list = KP_play.getList(0);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (_IKaraoke.DEBUG) {
			TextView debug = (TextView) getBaseActivity().findViewById(R.id.debug);
			if (debug != null) {
				debug.setText(list.toString(2));
			}
		}

		String value = "";

		// try {
		// value = list.getString("rec_byte");
		// if (!TextUtil.isEmpty(value) && TextUtils.isDigitsOnly(value)) {
		// rec_byte = Long.parseLong(value);
		// }
		// } catch (Exception e) {
		//
		// e.printStackTrace();
		// }

		try {
			value = list.getValue("is_upload");
			if (!TextUtil.isEmpty(value)) {
				is_upload = list.getValue("is_upload");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			value = list.getValue("rec_time");
			if (!TextUtil.isEmpty(value) && TextUtils.isDigitsOnly(value)) {
				rec_time = Long.parseLong(value) * 1000;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			if (!TextUtil.isEmpty(list.getValue("play_time"))) {
				play_time = TextUtil.parseInt(list.getValue("play_time")) * 1000;
			}
		} catch (Exception e) {

			// e.printStackTrace();
		}

		// 1분미리듣기시간확인
		if (mPlayType == Player.TYPE.SING) {
			if (!isPassUser() && play_time < 1000) {
				String where = __CLASSNAME__ + ".KPnnnn()";
				String emessage = "play_time error";
				String song_id = getList().getValue("song_id");
				String record_id = getList().getValue("record_id");
				// showMediaError(MEDIAERROR.TYPE.MEDIAPLAYER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.ERROR, MEDIAERROR.KYNNNN_DATA_ERROR, emessage, false);
				KP_9999(getApp().p_mid, p_m1, p_m2, MEDIAERROR.TYPE.MEDIAPLAYER, MEDIAERROR.LEVEL.E, song_id, record_id, where, MEDIAERROR.WHAT_MEDIA_ERROR,
						MEDIAERROR.EXTRA_KYNNNN_DATA_ERROR, "MEDIAERROR.ERROR", "MEDIAERROR.KYNNNN_DATA_ERROR", info, list, emessage);
				// popupPlayerMessage(emessage + "\n" + where);
				play_time = 60000;
			}
		}


		open();

		// 무료곡확인/1분미리듣기확인
		// 반주곡재생시 play_time으로만 확인한다.
		if (mPlayType == Player.TYPE.SING && play_time != 0) {
			getApp().popupToast(R.string.warning_nologin_preview, Toast.LENGTH_SHORT);
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[AF]:" + getApp().p_passtype + "," + play_time + "," + rec_time);
	}

	String p_opcode = "";
	String r_code = "";
	String r_message = "";
	String r_info = "";

	/**
	 * 로그인여부실행전확인
	 * @see BaseFragment5#onContextItemSelected(Context, int, KPItem, KPItem, boolean)
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mp);

		try {
			mPlayTimeStart = System.currentTimeMillis();

			KPItem info = KP_play.getInfo();

			if (info != null) {
				r_code = info.getValue("result_code");
				r_message = info.getValue("result_message");
				r_info = info.toString();
			}

			if (mp != null) {
				updateTimeStamp(0, mp.getDuration());
			}

			showUploadButton();

			if (isLoginUser()) {
				// 로그인사용자
				if (("99980").equalsIgnoreCase(r_code) || ("99981").equalsIgnoreCase(r_code)) {
					// 2.1. 99980:Holic으로 상품구매가 가능할 경우
					// 2.2. 99981:Holic으로 상품구매가 불가능할 경우
					isOnPausePause = false;
					isOnPauseClose = false;
					KPnnnnResult(_IKaraoke.STATE_DATA_QUERY_ERROR, p_opcode, r_code, r_message, r_info);
					stopLoading(__CLASSNAME__, getMethodName());
					// setRedrawPause();
					return;
				}
			} else {
				// 비로그인사용자
				// 무료곡확인/1분미리듣기확인
				// 반주곡재생시 play_time으로만 확인한다.
				if (mPlayType == Player.TYPE.SING && play_time != 0) {

					Bundle args = new Bundle();
					// args.putString("message", getString(R.string.warning_nologin_preview));
					args.putString("message", r_message);

					/**
					 * @see BaseFragment5#onContextItemSelected(Context, int, KPItem, KPItem, boolean)
					 */
					//로그인여부실행전확인
					//if (!isLoginUser()) {
					//	getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, args);
					//}
					// 이용권여부확인
					// if (!isPassUser()) {
					// getBaseActivity().showDialog2(DIALOG_ALERT_NOPASS_YESNO, args);
					// }
					stopLoading(__CLASSNAME__, getMethodName());
					// setRedrawPause();
					return;
				}
			}

			resetOnPause();

			stopLoading(__CLASSNAME__, getMethodName());

			if (mPlayType == Player.TYPE.SING) {
				play();
				mPlayTimeBar.setEnabled(false);
			} else {
				mPlayTimeBar.setEnabled(true);
			}

			isShowPlayer.setEnabled(true);

			// 화면켜짐설정
			if (mp != null) {
				mp.setScreenOnWhilePlaying(true);
			}

			setProgressButton();

		} catch (Exception e) {

			String where = __CLASSNAME__ + ".onPrepared(...)";
			showMediaError(MEDIAERROR.TYPE.MEDIAPLAYER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_PLAYER_START_ERROR, Log2.getStackTraceString(e), true);
			e.printStackTrace();
		}

	}

	// /**
	// * 재생종류구분:play_type - L:1분미리듣기 F:일일무료곡 P:유료재생
	// */
	// @Deprecated
	// protected String play_type = "L";
	/**
	 * 재생가능시간:play_time<br>
	 * 밀리세컨드
	 */
	private int play_time = 0;
	/**
	 * 실제재생된시간:확인용
	 */
	private int play_prog = 0;
	/**
	 * 업로드 가능여부:is_upload - (Y:업로드가능,N:불가능)
	 */
	private String is_upload = "N";
	/**
	 * 녹음곡업로드 버튼노툴시간:rec_time - 모델에 따른 녹음곡 파일 가능 크기 0 : 끝까지녹음.<br>
	 * 밀리세컨드
	 */
	private long rec_time = 0;
	// /**
	// * 사용안함:녹음곡업로드 파일사이즈 - "rec_byte": 0, : 모델에 따른 녹음곡 파일 가능 크기
	// */
	// @Deprecated
	// protected long rec_byte = 0;
	/**
	 * 다국어독음표시여부
	 */
	private String is_dualgasa = "Y";
	/**
	 * 싱크타입 setUseSystemTime(boolean b) : true(시스템시간 사용), false(디폴트, getCurrentTime() 사용)
	 * 
	 * @see Player#setSyncTime(int)
	 */
	private boolean sync_type = false;

	/**
	 * onPause()시 종료여부:(false) true : 화면종료 false: 일시정지<br>
	 * 기냥냅둔다.<br>
	 */
	protected boolean isOnPauseClose = false;
	/**
	 * onPause()시 일시정지여부:(false) SNS/멘트작성시 종료여부
	 */
	protected boolean isOnPausePause = false;

	/**
	 * 사용자정보<br>
	 * 반주곡정보<br>
	 * 녹음곡정보<br>
	 */
	protected String getPlayInfo() {
		if (player == null) {
			return null;
		}

		String ret = "";
		try {
			ret += "\tgetApp().p_mid:" + getApp().p_mid;
			ret += "\n\tCODE:";
			ret += "\t" + isLoginUser();
			ret += "\t" + isPassUser();
			ret += "\t" + isUploadUser();
			ret += "\t" + getApp().p_passtype;
			ret += "\t" + is_upload;
			ret += "\n\tTIME:\t" + play_prog + "\t(" + player.currentTime() + "/" + player.totalTime() + ")";
			ret += "\n\tgetRecTime():" + getRecTime();
			ret += "\n\tplay_time:" + play_time;
			ret += "\n\trec_time:" + rec_time;
			ret += "\n\tis_dualgasa:" + is_dualgasa;
			ret += "\n\tsync_type:" + sync_type;
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ret);
		return ret;
	}

	/**
	 * 미디어/업로드 what/extra 내용화면표시<br>
	 * KP_9999전송<br>
	 * <b>오류발생시반드시리스너차단한다!!!</b><br>
	 * 
	 * @see kr.kymedia.karaoke.play3.Player#event(android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnErrorListener, android.media.MediaPlayer.OnPreparedListener)
	 * @see kr.kymedia.karaoke.record.SongRecorder2#setOnErrorListener(android.media.MediaRecorder.OnErrorListener)
	 * @see kr.kymedia.karaoke.record.SongRecorder2#setOnErrorListener(android.media.MediaRecorder.OnErrorListener)
	 */
	protected String showMediaError(MEDIAERROR.TYPE type, MEDIAERROR.LEVEL level, String where, int what, int extra, String emessage, boolean alert) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "!!![MEDIAERROR]!!!" + getMethodName() + type);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "!!![MEDIAERROR]!!!" + getMethodName() + where + ":" + what + ", " + extra);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "!!![MEDIAERROR]!!!" + getMethodName() + emessage);

		shutdownMediaError(type, level);

		isOnPauseClose = !alert;
		isOnPausePause = !alert;

		if (alert) {

			// pause();
			// 플레이어오류시는일시정지할필요없다.
			if (type != MEDIAERROR.TYPE.MEDIAPLAYER && player != null && player.isPlaying()) {
				player.pause();
			}
			// 레코더오류시는녹음중지할할필요없다.
			if (type != MEDIAERROR.TYPE.MEDIARECORDER && mRecord != null && mRecord.isRecording()) {
				stopRecord(false);
			}
		}

		// 추가변수
		String song_id = getList().getValue("song_id");
		String record_id = getList().getValue("record_id");
		KPItem info = KP_play.getInfo();
		KPItem list = KP_play.getList(0);

		String wname = "MEDIAERROR_UNKNOWN";
		String ename = "MEDIAERROR_UNKNOWN";

		String message = "";

		// String stype = "";
		Field[] fields = null;
		if (type == MEDIAERROR.TYPE.MEDIAPLAYER) {
			fields = MediaPlayer.class.getDeclaredFields();
		} else if (type == MEDIAERROR.TYPE.MEDIARECORDER) {
			fields = MediaRecorder.class.getDeclaredFields();
		} else if (type == MEDIAERROR.TYPE.MEDIAUPLOAD) {
			message = getString(R.string.error_alert_upload);
			// stype = "UPLOAD";
		}

		try {

			// System.out.println(fields.length); //gives no of fields
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[MediaError]");

			if (fields != null) {
				for (Field field : fields) {
					// System.out.println(field.getName()); //gives the names of the fields
					String fname = "";
					try {
						fname = field.get(field).toString();
					} catch (Exception e) {

						// e.printStackTrace();
						continue;
					}

					// if (TextUtil.isEmpty(fname) || !TextUtils.isDigitsOnly(fname)) {
					// continue;
					// }

					int value = -99999;
					try {
						value = Integer.parseInt(fname);
					} catch (Exception e) {

						// e.printStackTrace();
						continue;
					}

					if (value == -99999) {
						continue;
					}

					String name = field.getName();

					if (value == what && !TextUtil.isEmpty(name) && TextUtil.isEmpty(wname)) {
						wname = name;
					}

					if (value == extra && !TextUtil.isEmpty(name) && TextUtil.isEmpty(ename)) {
						ename = name;
					}

					// if (!TextUtil.isEmpty(wname) && !TextUtil.isEmpty(ename)) {
					// break;
					// }

					// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, "" + what + ":" + extra + " : (" + value + "-" + name + ") - (" + wname + "-" + ename + ")");

				}
			}

			if (type == MEDIAERROR.TYPE.MEDIAPLAYER) {
				message = getString(R.string.warning_nostream_retry);
				// what
				if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
					wname = "MediaPlayer.MEDIA_ERROR_UNKNOWN";
				} else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
					wname = "MediaPlayer.MEDIA_ERROR_SERVER_DIED";
				} else {
					wname = "MediaPlayer.MEDIAERROR_UNKNOWN";
				}
				// extra
				if (extra == MediaPlayer.MEDIA_ERROR_IO) {
					ename = "MediaPlayer.MEDIA_ERROR_IO";
				} else if (extra == MediaPlayer.MEDIA_ERROR_MALFORMED) {
					ename = "MediaPlayer.MEDIA_ERROR_MALFORMED";
				} else if (extra == MediaPlayer.MEDIA_ERROR_UNSUPPORTED) {
					ename = "MediaPlayer.MEDIA_ERROR_UNSUPPORTED";
				} else if (extra == MediaPlayer.MEDIA_ERROR_TIMED_OUT) {
					ename = "MediaPlayer.MEDIA_ERROR_TIMED_OUT";
				} else {
					ename = "MediaPlayer.MEDIAERROR_UNKNOWN";
				}
			} else if (type == MEDIAERROR.TYPE.MEDIARECORDER) {
				message = getString(R.string.warning_record_error);
				// what
				if (what == MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN) {
					wname = "MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN";
				} else if (what == MediaRecorder.MEDIA_ERROR_SERVER_DIED) {
					wname = "MediaRecorder.MEDIA_ERROR_SERVER_DIED";
				} else {
					wname = "MediaRecorder.MEDIAERROR_UNKNOWN";
				}
			} else if (type == MEDIAERROR.TYPE.MEDIAUPLOAD) {
				message = getString(R.string.error_alert_upload);
				// stype = "UPLOAD";
			}

			// MEDIAERROR.EXTRA_PLAYER_START_ERROR = -22222;
			// MEDIAERROR.EXTRA_PLAYER_FILE_ERROR = -33333;
			// MEDIAERROR.EXTRA_RECORD_START_ERROR = -44444;
			// MEDIAERROR.EXTRA_RECORD_FILE_ERROR = -55555;
			// MEDIAERROR.EXTRA_KYNNNN_DATA_ERROR = -88888;
			if (extra == MEDIAERROR.EXTRA_PLAYER_START_ERROR) {
				ename = "MEDIAERROR.EXTRA_PLAYER_START_ERROR";
			} else if (extra == MEDIAERROR.EXTRA_PLAYER_FILE_ERROR) {
				ename = "MEDIAERROR.EXTRA_PLAYER_FILE_ERROR";
			} else if (extra == MEDIAERROR.EXTRA_RECORD_START_ERROR) {
				ename = "MEDIAERROR.EXTRA_RECORD_START_ERROR";
			} else if (extra == MEDIAERROR.EXTRA_RECORD_FILE_ERROR) {
				ename = "MEDIAERROR.EXTRA_RECORD_FILE_ERROR";
			} else if (extra == MEDIAERROR.EXTRA_KYNNNN_DATA_ERROR) {
				ename = "MEDIAERROR.EXTRA_KYNNNN_DATA_ERROR";
			}

			if (_IKaraoke.DEBUG) {
				message += "\n\tWHERE:\t" + where;
			}

			message += "\n\tEMESSAGE:\t" + emessage;
			message += "\n\tID:\t" + song_id + ":" + record_id;
			message += "\n\tWHAT:\t" + what;
			message += "\n\tEXTRA:\t" + extra;

			String error = message;
			error += "\n\tWHAT:\t" + wname;
			error += "\n\tEXTRA:\t" + ename;
			error += getPlayInfo();

			if (_IKaraoke.DEBUG) {
				message = error;
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, error);

			if (alert) {
				getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), message, getString(R.string.btn_title_confirm), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						// close();
						getBaseActivity().finish();
					}
				}, null, null, false, new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						// close();
						getBaseActivity().finish();
					}
				});

			} else {
				getApp().popupToast(message, Toast.LENGTH_SHORT);
			}

			KP_9999(getApp().p_mid, p_m1, p_m2, type, level, song_id, record_id, where, what, extra, wname, ename, info, list, error);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return message;
	}

	/**
	 * 미디어/업로드 emsssage 내용화면표시<br>
	 * KP_9999전송<br>
	 * <b>오류발생시반드시리스너차단한다!!!</b><br>
	 * 
	 * @see kr.kymedia.karaoke.play3.Player#event(android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnErrorListener, android.media.MediaPlayer.OnPreparedListener)
	 * @see kr.kymedia.karaoke.record.SongRecorder2#setOnErrorListener(android.media.MediaRecorder.OnErrorListener)
	 * @see kr.kymedia.karaoke.record.SongRecorder2#setOnErrorListener(android.media.MediaRecorder.OnErrorListener)
	 */
	protected void shutdownMediaError(MEDIAERROR.TYPE type, MEDIAERROR.LEVEL level) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + type + ", " + level);

		// 오류발생시리스너차단-KP_9999중복발생
		if (type == MEDIAERROR.TYPE.MEDIAPLAYER && player != null) {
			// if (level == MEDIAERROR.LEVEL.E) {
			// pv.Eevent(null);
			// } else if (level == MEDIAERROR.LEVEL.I) {
			// } else if (level == MEDIAERROR.LEVEL.W) {
			// } else {
			// pv.event(null, null, null);
			// }
			player.event(null, null, null);
		}

		// 오류발생시리스너차단-KP_9999중복발생
		if (type == MEDIAERROR.TYPE.MEDIARECORDER && mRecord != null) {
			// if (level == MEDIAERROR.LEVEL.E) {
			// mRecord.setOnErrorListener(null);
			// } else if (level == MEDIAERROR.LEVEL.I) {
			// mRecord.setOnInfoListener(null);
			// } else if (level == MEDIAERROR.LEVEL.W) {
			// mRecord.setOnInfoListener(null);
			// } else {
			// mRecord.setOnErrorListener(null);
			// mRecord.setOnInfoListener(null);
			// }
			mRecord.setOnErrorListener(null);
			mRecord.setOnInfoListener(null);
		}
	}

	/**
	 * 미디어플레이어오류<br>
	 * 오류발생시리스너차단-KP_9999전문차단<br>
	 * <br>
	 */
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mp + ":" + what + ", " + extra);

		if (mBackPressed) {
			return true;
		}

		// String path = "";
		// if (pv != null) {
		// path = pv.getSong();
		// }
		//
		// if (URLUtil.isNetworkUrl(path)) {
		// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "URLUtil.isNetworkUrl(...) OK !!!" + path);
		// getBaseActivity().showDialog2(DIALOG_PROGRESS_SONG_DOWNWARN, null);
		// } else {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "URLUtil.isNetworkUrl(...) NG !!!" + path);
		// getApp().popupToast(R.string.error_localfile_play, Toast.LENGTH_SHORT);
		// close();
		// }

		// 재생시주의점
		String where = __CLASSNAME__ + "." + getMethodName();
		showMediaError(MEDIAERROR.TYPE.MEDIAPLAYER, MEDIAERROR.LEVEL.E, where, what, extra, "OnMediaPlayerError", true);

		// if (play_view != null) {
		// play_view.close();
		// }

		stopRecord(false);

		stopLoading(__CLASSNAME__, getMethodName());

		// 화면켜짐설정
		mp.setScreenOnWhilePlaying(false);

		// !!!!!중요:false시 MediaPlayer.onCompletion(...)까지 호출!!!!!
		return true;
	}

	/**
	 * 미디어레코더오류<br>
	 * 오류발생시리스너차단-KP_9999전문차단<br>
	 * 현재는 오류추적만한다.<br>
	 * <br>
	 */
	@Override
	public void onError(MediaRecorder mr, int what, int extra) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mr + ":" + what + ", " + extra);

		String where = __CLASSNAME__ + "." + getMethodName();
		showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, what, extra, "OnMediaRecorderError", true);
	}

	/**
	 * 미디어레코더오류<br>
	 * 오류발생시리스너차단-KP_9999전문차단<br>
	 * 현재는 오류추적만한다.<br>
	 * <br>
	 */
	@Deprecated
	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {

		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName() + mr + ":" + what + ", " + extra);
	}

	/**
	 * SongRecorder녹음중확인
	 */
	@Override
	public boolean isRecording() {

		boolean ret = false;

		if (mRecord != null) {
			ret = mRecord.isRecording();
		}

		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName() + ret);

		return ret;
	}

	/**
	 * 녹음업로드가능사용자확인
	 * 
	 * <pre>
	 * 1. 로그인사용자
	 * 2. 이용권사용자
	 * 3. 업로드가능사용자
	 * </pre>
	 * 
	 * @see BaseFragment2#isPassUser()
	 * @see #is_upload
	 */
	public boolean isUploadUser() {

		boolean ret = false;
		if (isPassUser() && ("Y").equalsIgnoreCase(is_upload)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 녹음곡 업로드 여부는 getApp().p_passtype/is_upload로 판단한다.<br>
	 * 
	 * <pre>
	 * 0. exist:true - 파일유무만확인 
	 * 1. 녹음곡파일 유무확인
	 * 2. 녹음곡파일 사이즈확인
	 * 	갤럭시탭에서오류발생 파일사이즈확인이 안된다. 이런개젖같은~~~~~~
	 * </pre>
	 * 
	 * @see #mRecord
	 * @see #mPlayType
	 * @see BaseFragment2#getApp().p_passtype
	 * @see #is_upload
	 * @see #rec_time
	 */
	private boolean checkRecordFile() {

		boolean ret = true;
		boolean check = false;
		String where = __CLASSNAME__ + ".checkRecordFile()";
		String emessage = "";

		File file = null;
		boolean exist = false;
		String path = "";
		long len = -1;

		try {
			path = mRecord.getPath();
			file = new File(path);
			if (file != null) {
				exist = file.exists();
			}
			if (exist) {
				len = file.length();
			}
		} catch (Exception e) {

			emessage = "File Error";
			emessage += "\n\t" + Log2.getStackTraceString(e);
			if (_IKaraoke.DEBUG) {
				emessage += "\n\n[디버그정보]:\n\tfile:" + file;
				emessage += "\n\tcheck:" + check;
				emessage += "\n\texists:" + exist;
				emessage += "\n\tlength:" + len;
			}
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_FILE_ERROR, emessage, true);
			e.printStackTrace();
		}

		if (getRecTime() > 5000) {
			check = true;
		}

		if (isUploadUser() && isRecord && !isRecording()) {
			if (isRecord) {
				emessage = "Recording Error";
				if (_IKaraoke.DEBUG) {
					emessage += "\n\n[디버그정보]:\n\tfile:" + file;
					emessage += "\n\tcheck:" + check;
					emessage += "\n\texists:" + exist;
					emessage += "\n\tlength:" + len;
				}
				showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_FILE_ERROR, emessage, true);
			}
			stopRecord(true);
			ret = false;
			return ret;
		}

		if (check && isUploadUser()) {
			// 갤럭시탭에서오류발생 파일사이즈확인이 안된다. 이런개젖같은~~~
			if (file != null && exist /* && len > rec_byte */) {
				ret = true;
			} else {
				ret = false;
			}
		} else {
			ret = false;
		}

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ret + ":" + len + "->" + getRecTime() + ":" + rec_time + "-check:" + check + ",record:" + isRecordUser() + "-" + path);

		if (check && !ret) {

			if (!exist) {
				emessage = "File Not Exists Error";
			}

			if (len == 0) {
				emessage = "File Not Record Error";
			}

			if (_IKaraoke.DEBUG) {
				emessage += "\n\n[디버그정보]:\n\tfile:" + file;
				emessage += "\n\tcheck:" + check;
				emessage += "\n\texists:" + exist;
				emessage += "\n\tlength:" + len;
			}
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_FILE_ERROR, emessage, true);
			stopRecord(true);
		}

		return ret;
	}

	/**
	 * 백버튼처리
	 */
	@Override
	public boolean onBackPressed() {

		isOnPausePause = true;
		isOnPauseClose = true;

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isOnPauseClose + ", " + isOnPausePause);

		// if (isRecording() && warnRecord(true)) {
		// isOnPausePause = false;
		// isOnPauseClose = false;
		// pause();
		// return false;
		// } else {
		// pause();
		// return super.onBackPressed();
		// }

		pause();

		// deleteRecord();
		// getBaseActivity().deleteAllFile();

		return super.onBackPressed();
	}

	@Override
	protected void openListenPost(KPnnnn KP_xxxx, int index) {

		isOnPauseClose = false;

		// super.openListenPost(KP_xxxx, index);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		// FEEL작성중복막기
		// if (this instanceof ListenPostFragment) {
		// return;
		// }

		KPItem list = KP_xxxx.getList(index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		list.putValue("record_path", mRecord.getPath());

		// 다음 액티비티로 넘길 Bundle 데이터를 만든다.
		// Intent intent = putIntentData(listenpost.class, KP_xxxx, index);
		Intent intent = putIntentData(getActivity().getApplicationContext(), listenpost.class, KP_xxxx.getInfo(), list);

		if (intent != null) {
			// 액티비티
			startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH);
		}
	}

	/**
	 * 녹음
	 */
	private boolean warnRecord(final boolean cancel) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + cancel + "-" + isUploadUser() + ":" + checkRecordFile());

		boolean ret = false;

		try {
			if (isUploadUser() && checkRecordFile()) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[OK]");

				if (cancel) {
					// openListenPost2(KP_play, 0);
				} else {
					openListenPost(KP_play, 0);
					ret = true;
				}

			} else {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "[NG]");
				if (isUploadUser()) {
					ret = true;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + cancel + "," + ret);
		return ret;
	}

	public void showShowButton() {
		try {

			if (findViewById(R.id.include_player_bar) == null) {
				return;
			}

			View v = findViewById(R.id.include_player_bar).findViewById(R.id.view3);

			if (v != null) {
				v.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void hideShowButton() {
		try {
			View v = findViewById(R.id.include_player_bar);
			if (v == null) {
				return;
			}

			v = v.findViewById(R.id.view3);
			if (v == null) {
				return;
			}

			if (v != null) {
				v.setVisibility(View.GONE);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 업로드버튼표시여부확인
	 */
	protected boolean showUploadButton() {
		// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName());

		if (player == null) {
			return false;
		}

		try {
			boolean show = false;

			long btn_time = rec_time;

			// rec_time: 0 이거나 재생시간보다 작은경우
			if (btn_time == 0 || btn_time > player.totalTime()) {
				// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, getMethodName() + "player.totalTime():" + player.totalTime() + " - btn_time:" + btn_time);
				btn_time = player.totalTime() - 1000;
			}

			if (player.currentTime() > 0 && getRecTime() > btn_time) {
				show = true;
			}

			if (show && mUpButton.getVisibility() != View.VISIBLE) {
				// if (_IKaraoke.DEBUG)Log.w(__CLASSNAME__, getMethodName() + player.currentTime() + "/" + player.totalTime() + " - getRecTime():" + getRecTime() + ", btn_time:" + btn_time + ", rec_time:" + rec_time);
			}

			if (isUploadUser() && show) {
				mUpButton.setVisibility(View.VISIBLE);
				if (mPlayType == Player.TYPE.SING) {
					showShowButton();
				}
				return true;
			} else {
				mUpButton.setVisibility(View.GONE);
				if (mPlayType == Player.TYPE.SING) {
					hideShowButton();
				}
				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		mUpButton.setVisibility(View.GONE);

		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		KP_1012();

		// 화면켜짐설정
		mp.setScreenOnWhilePlaying(false);

		cancelPlayNotification();

		// 반주곡 유료사용자 경우 녹음곡 업로드 가능
		if (mPlayType == Player.TYPE.SING) {
			if (!warnRecord(false)) {
				close();
			}
		} else {
			close();
		}

		pause();
	}

	@Override
	public void onDownloadFileComplete(String path) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + path);

		super.onDownloadFileComplete(path);

		open();
	}

	@Override
	public void uploadFile() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		super.uploadFile();

	}

	@Override
	public void onUploadFileComplete(String path) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + path);

		super.onUploadFileComplete(path);

	}

	@Override
	public void cancelUploadFile() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		super.cancelUploadFile();

	}

	@Override
	public void onUploadFileCancel(String path) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + path);

		super.onUploadFileCancel(path);

	}

	public void popupPlayerMessage(String message) {
		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + message);

			if (mBackPressed) {
				return;
			}

			// String text = getString(R.string.error_alert_play);
			// text += " : " + message;
			String text = message;
			sendMessage(mHandlerPlay, _IKaraoke.STATE_SONG_PLAY_MESSAGE, KP_play.p_opcode, "", text, "");
			// isOnPauseClose = true;
			// isOnPausePause = true;
			// close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 오픈에러강제로종료된다<br>
	 * 절대로 UI에 접근하지 않는다.<br>
	 * 
	 * @author isyoon
	 * 
	 */
	public void popupPlayerError(String message) {
		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + message);

			if (mBackPressed) {
				return;
			}

			String text = getString(R.string.error_alert_play);
			text += " : " + message;
			sendMessage(mHandlerPlay, _IKaraoke.STATE_SONG_PLAY_ERROR, KP_play.p_opcode, "99999", text, "");
			isOnPauseClose = true;
			isOnPausePause = true;
			close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void open() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			(new open()).execute();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 절대로 UI에 접근하지 않는다.
	 * 
	 * @author isyoon
	 * 
	 */
	private class open extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {

			open(params);
			return null;
		}

	}

	/**
	 * <pre>
	 * 리플레쉬버튼시 MediaPlayer Error(-38, 0) 오류무시
	 * </pre>
	 */
	private void open(Void... params) {
		if (player == null) {
			return;
		}

		String id = getList().getValue("song_id");
		String song = "";
		String lyrc = "";

		KPItem list = KP_play.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		String title = "";
		String singer = "";
		String composer = "";
		String writer = "";

		if (list != null) {
			is_dualgasa = list.getValue("is_dualgasa");
			// 싱크타입설정처리
			// sync_type = Boolean.parseBoolean(list.getString("sync_type"));
			title = list.getValue("title");
			singer = list.getValue("singer");
			composer = list.getValue("composer");
			writer = list.getValue("wirter");
		}

		if (_IKaraoke.DEBUG) {
			// getApp().popupToast(getPlayInfo(), Toast.LENGTH_LONG);
			popupPlayerMessage(getPlayInfo());
		}

		// 중지시가사싱크오류-open전에실행
		// 다국어발음가사처리
		String lcode = getApp().mLocale.getLanguage().toUpperCase();
		if (("N").equalsIgnoreCase(is_dualgasa) || ("KO").equalsIgnoreCase(lcode)) {
			player.setRuby(false);
		} else {
			player.setRuby(true);
			if (("JA").equalsIgnoreCase(lcode)) {
				player.setLang(Player.RUBY.JAP);
			} else {
				player.setLang(Player.RUBY.ENG);
			}
		}

		// 중지시가사싱크오류-open전에실행
		// 옵티머스계열오류
		// 프로토콜 : KP_1013(반주곡재생), KP_2016(녹음곡재생)
		// - Response list 정보 : "sync_time"
		// 출력 변수 추가("false":현재 가사 싱크 방식, "true":디바이스 시간 기준 가사 싱크 처리)
		// PlayView.java
		// setUseSystemTime(boolean b) : true(시스템시간 사용), false(디폴트, getCurrentTime() 사용)
		player.setUseSystem(sync_type);

		if (mPlayType == Player.TYPE.SING) {
			song = list.getValue("url_skym");
		} else if (mPlayType == Player.TYPE.LISTEN) {
			song = list.getValue("url_record");
		}

		// 가사확인
		lyrc = list.getValue("url_lyric");

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ": start [INIT]");
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "song: " + song);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "lyrc: " + lyrc);

		// KPOP HOLIC - JAPAN 암호화 URL 정보
		// 1. 녹음곡 URL : url_record
		// 2. 반주곡 URL : url_skym
		// 3. skym 가사 URL : url_lyric
		String APP_KEY = _IKaraoke.APP_DECRIPTION_KEY;

		if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
			APP_KEY = _IKaraoke.APP_DECRIPTION_KEY_JPOP;
		}

		if (!TextUtil.isNetworkUrl(song)) {
			song = SongUtil.makeDecryption(APP_KEY, song);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "song: " + song);
		}
		if (!TextUtil.isNetworkUrl(lyrc)) {
			lyrc = SongUtil.makeDecryption(APP_KEY, lyrc);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "lyrc: " + lyrc);
		}

		if (song != null) {

			File file = null;

			String path = _IKaraoke.SKYM_PATH + File.separator + id + ".skym";

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "path: " + path);

			// test
			// getList().put("song_id", "3641");
			// id = "3641";
			// song = "http://cyms.chorus.co.kr/cykara_dl.asp?song_id=3641";
			// lyrc = song;
			// path = SKYM_PATH + File.separator + id + ".skym";

			SongItem item = new SongItem();
			item.down = 1;
			item.order = "";
			item.number = getList().getValue("song_id");
			item.title = getList().getValue("title");
			item.artist = getList().getValue("artist");
			item.info = getList().getValue("title") + "-" + getList().getValue("artist");
			item.url = song;
			item.dst = path;

			// try {
			// getBaseActivity().setSongItem(item);
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// popupPlayerFatalError(Log.getStackTraceString(e));
			// return;
			// }

			try {
				file = new File(path);
			} catch (Exception e) {

				e.printStackTrace();
				popupPlayerError(Log2.getStackTraceString(e));
				return;
			}

			boolean local = false;
			if (file != null && file.exists()) {
				// 로컬파일유무확인
				song = path;
				local = true;
			}

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ": down [START]" + local);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "file: " + file);

			if (local) {
				// 가사파일로컬오픈
				lyrc = song;
			} else {
				// 가사파일다운로드
				try {
					lyrc = LyricsUtil.down(lyrc);
				} catch (Exception e) {

					e.printStackTrace();
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "Lyric File Error\n" + lyrc);
					popupPlayerError(Log2.getStackTraceString(e));
					return;
				}
			}

			if (TextUtil.isEmpty(lyrc)) {
				popupPlayerError("Lyric File Error\n" + lyrc);
				return;
			}

			try {
				file = new File(lyrc);
			} catch (Exception e) {

				e.printStackTrace();
				popupPlayerError(Log2.getStackTraceString(e));
				return;
			}
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ": down [END]");

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ": open [START]");
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + song);
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + lyrc);

			try {
				if (player == null) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ": open [STOP]" + player);
					if (file.exists()) {
						file.delete();
					}
					return;
				}

				// !!!!!플레이어오픈처리!!!!
				try {
					// 리플레쉬버튼시 MediaPlayer Error(-38, 0) 오류무시 - 열기전에이벤트차단...
					if (player != null) {
						player.event(null, null, null);
					}

					if (file != null && file.exists()) {
						if (player != null && !player.open(song, lyrc)) {
							popupPlayerError("Lyric File Error\n" + lyrc);
						}
					} else {
						popupPlayerError("Lyric File Error\n" + lyrc);
					}
					// FileInputStream fileIS = new FileInputStream(new File(lyrc));
					// if (fileIS != null && fileIS.available() > 0) {
					// pv.open(song, fileIS);
					// }
				} catch (Exception e) {

					// e.printStackTrace();
					android.util.Log.e("player", "open() - ERROR!!!\n" + Log2.getStackTraceString(e));
					popupPlayerError(Log2.getStackTraceString(e));
					return;
				}

				// 리플레쉬버튼시 MediaPlayer Error(-38, 0) 오류무시 - 열기후에이벤트연결...
				if (player != null) {
					player.event(this, this, this);
					player.setTitle(title);
					player.setSinger(singer);
					player.setComposer(composer);
					player.setWriter(writer);
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ": open [END]");

	}

	/**
	 * 재생알림바표시
	 */
	private void openPlayNotification() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		// if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + getBaseActivity().getIntent());
		// if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + getBaseActivity().getIntent().getExtras());

		if (KP_play == null) {
			return;
		}

		if (player == null) {
			return;
		}

		if (!player.isPlaying()) {
			return;
		}

		try {
			KPItem info = KP_play.getInfo();
			KPItem list = getList();
			KPItemShareData shareData = new KPItemShareData(info, list);

			String sTitle = shareData.title;
			String sArtist = shareData.artist;
			String sName = shareData.name;

			long when = System.currentTimeMillis();
			String ticker = getBaseActivity().getString(R.string.menu_playing);
			CharSequence title = sTitle + "-" + sArtist;
			CharSequence message = sName;

			Intent nIntent = getBaseActivity().getIntent();
			// String action = nIntent.getAction();
			// Set<String> categories = nIntent.getCategories();
			// int flags = nIntent.getFlags();
			//
			// nIntent.setAction(action);
			// if (categories != null) {
			// for (String category : categories) {
			// nIntent.addCategory(category);
			// }
			// }
			// nIntent.addFlags(flags);
			// nIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

			nIntent = new Intent(getApp(), GCM2Activity.class);

			// !!!![PUSH]!!!!
			// [context]kr.kymedia.karaoke.app._Application@1d49384f
			// [index]194087
			// [id]context_play_listen
			// [type]record
			// [ticker]녹음곡 알림
			// [title]녹음곡 알림
			// [message]짱그님이 새로운 녹음곡을 등록하였습니다.
			nIntent.putExtra("index", "0");
			nIntent.putExtra("ticker", ticker);
			nIntent.putExtra("title", title);
			nIntent.putExtra("message", message);
			if (this instanceof _playSingFragment) {
				nIntent.putExtra("id", "context_play_sing");
				nIntent.putExtra("type", "song");
			} else {
				nIntent.putExtra("id", "context_play_listen");
				nIntent.putExtra("type", "record");
			}

			nIntent.putExtra("info", getInfo().toString());
			nIntent.putExtra("list", getList().toString());

			if (nIntent != null) {
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + nIntent);
				if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + nIntent.getExtras());
			}

			PendingIntent pIntent = PendingIntent.getActivity(getApp(), 0, nIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + pIntent);

			getApp().openNotification(pIntent, _IKaraoke.KARAOKE_RESULT_PLAYING, when, ticker, title, message, true, false, false);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 재생알림바제거
	 */
	private void cancelPlayNotification() {
		getApp().cancelNotification(_IKaraoke.KARAOKE_RESULT_PLAYING);
	}

	@Override
	public boolean play() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		boolean ret = true;

		// 녹음오류처리
		if (ret) {
			startRecord(true);
		}

		if (isRecord) {
			if (player != null) {
				ret = player.record();
			}
		} else {
			if (player != null) {
				// 자막위치
				setLyricAlign(Player.ALIGN.BOTTOM);

				// 자막마진
				setLyricMargin();

				ret = player.play();
			}
		}
		setRedraw(false);

		return ret;
	}

	@Override
	public boolean stop() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		cancelPlayNotification();

		boolean ret = false;
		// 녹음오류처리
		stopRecord(true);
		if (player != null && player.isPlaying()) {
			ret = player.stop();
		}
		return ret;
	}

	// private boolean isPaused = false;
	//
	// protected boolean isPaused() {
	// return isPaused;
	// }

	@Override
	public boolean pause() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		boolean ret = false;

		// 녹음오류처리
		stopRecord(true);

		if (player != null && player.isPlaying()) {
			ret = player.pause();
		}

		setProgressButton();

		setRedraw(true);

		return ret;
	}

	@Override
	public boolean resume() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		boolean ret = false;
		// 녹음오류처리
		resumeRecord();

		if (player != null) {
			if (!player.isPlaying()) {
				player.play();
			} else if (player.isPaused()) {
				player.resume();
			}
		}

		setProgressButton();

		setRedraw(false);

		return ret;
	}

	@Override
	public boolean restart() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		pause();
		if (player != null) {
			player.seek(0);
		} else {
			String where = __CLASSNAME__ + ".restart()";
			String message = "play_time error";
			showMediaError(MEDIAERROR.TYPE.MEDIAPLAYER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_PLAYER_START_ERROR, message, true);
		}
		play();

		return true;
	}

	@Override
	public void release() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		try {
			if (player != null) {
				player.destroy();
				player = null;
			}
			// 녹음오류처리
			releaseRecord();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void close() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		cancelPlayNotification();

		stop();

		release();

		super.close();
	}

	// @Override
	// protected boolean checkLogin(boolean alert) {
	//
	// //return super.checkLogin(alert);
	// boolean ret = isLogin();
	//
	// if (!ret) {
	// Bundle args = new Bundle();
	// args.putString("message", getString(R.string.errmsg_login));
	// getBaseActivity().showDialog2(DIALOG_ALERT_NOLOGIN_YESNO, args);
	// }
	//
	// return ret;
	// }

	/**
	 * 중요:<br>
	 * 시작시 녹음여부를 결정한다.<br>
	 * 재생시 녹음상태를 표시한다.<br>
	 * <br>
	 * 
	 * @see #isRecord
	 * @see #onClick(View)
	 * @see #startRecord(boolean)
	 * @see #stopRecord(boolean)
	 */
	public void setRecord(boolean record, boolean toast) {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "record:" + record + ", toast:" + toast + " - "+ isRecord);

		if (mRecButton != null) {
			mRecButton.setChecked(record);
		}

		Player.STATUS status = Player.STATUS.STOP;

		if (record) {
			status = Player.STATUS.REC;
		} else {
			status = Player.STATUS.PLAY;
		}

		if (player != null) {
			player.status(status);
		}

		String message = "";

		// 비로그인, 쿠폰/이용권 없는 로그인 상태에서 반주곡 재생시 "녹음중지되었습니다..." 메시지 삭제
		if (isPassUser() && mPlayType == Player.TYPE.SING) {
			if (record) {
				message = getString(R.string.warning_onrecord_play);
			} else {
				message = getString(R.string.warning_norecord_play);
			}

			if (_IKaraoke.DEBUG) {
				message += "\n\n[디버그정보]";
				message += getPlayInfo();
			}

		}

		// 녹음중지관련토스트
		if (toast) {

			if (mPlayType == Player.TYPE.SING) {
				if (!TextUtil.isEmpty(message.trim())) {
					getApp().popupToast(message, Toast.LENGTH_SHORT);
				}
			}
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "record:" + record + ", toast:" + toast + " - " + isRecord);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, message);

		isRecord = record;

	}

	protected void prepareRecord() throws Exception {

		boolean compressed = true;
		// Bundle bundle = getBaseActivity().getIntent().getExtras();
		// if (bundle != null) {
		// compressed = bundle.getBoolean(SONGPLAYER_RECORDCOMPRESS);
		// }

		String id = getList().getValue("song_id");
		String time = android.text.format.DateFormat.format("yyyyMMdd-hhmmss", System.currentTimeMillis()).toString();

		mRecord = new SongRecorder5(id + "-" + time, compressed);

		mRecord.setOnErrorListener(this);
		mRecord.setOnInfoListener(this);

	}

	/**
	 * 녹음시작
	 */
	@Override
	public void startRecord(boolean toast) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "toast:" + toast + " - " + isRecord);

		try {

			if (isRecording()) {
				return;
			}

			boolean isRecord = false;
			if (mPlayType == Player.TYPE.SING) {
				mRecButton.setVisibility(View.VISIBLE);
				if (isPassUser()) {
					isRecord = true;
				} else {
					isRecord = false;
				}
			} else if (mPlayType == Player.TYPE.LISTEN) {
				mRecButton.setVisibility(View.GONE);
				isRecord = false;
			}

			setRecord(isRecord, toast);

			rec_start = -1;

			if (!this.isRecord) {
				return;
			}

			rec_start = System.currentTimeMillis();

			prepareRecord();

			setRecord(true, toast);

			mRecord.start();

		} catch (Exception e) {
			setRecord(false, false);

			String where = __CLASSNAME__ + ".startRecord()";
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_START_ERROR, Log2.getStackTraceString(e), true);
			e.printStackTrace();
		}
	}

	/**
	 * 녹음중지
	 */
	@Override
	public void stopRecord(boolean toast) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "toast:" + toast + " - " + isRecord);

		try {

			if (!isRecord) {
				return;
			}

			if (!isRecording()) {
				return;
			}

			rec_start = -1;

			setRecord(false, toast);

			if (mRecord != null) {
				mRecord.stop();
				mRecord.release();
			}

		} catch (Exception e) {
			setRecord(false, false);

			String where = __CLASSNAME__ + ".stopRecord(...)";
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_START_ERROR, Log2.getStackTraceString(e), false);
			e.printStackTrace();
		}
	}

	// @Deprecated
	// @Override
	// public void pauseRecord() {
	// }

	@Deprecated
	@Override
	public void resumeRecord() {
		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "," + isRecord);

			if (!isRecord) {
				return;
			}

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

			if (mRecord != null) {
				mRecord.resume();
			}

		} catch (Exception e) {

			e.printStackTrace();
			String where = __CLASSNAME__ + ".resumeRecord()";
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_START_ERROR, Log2.getStackTraceString(e), false);
		}
	}

	@Override
	public void releaseRecord() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "," + isRecord);

		try {
			if (mRecord != null) {
				stopRecord(false);
				mRecord.release();
				mRecord = null;
			}
		} catch (Exception e) {

			String where = __CLASSNAME__ + ".releaseRecord()";
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_START_ERROR, Log2.getStackTraceString(e), false);
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRecord() {
		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isRecord);

			// if (!isRecord) {
			// return;
			// }

			if (mRecord != null) {
				String path = mRecord.getPath();
				File file = new File(path);
				if (file != null && file.exists()) {
					file.delete();
				}
			}

		} catch (Exception e) {

			String where = __CLASSNAME__ + ".deleteRecord()";
			showMediaError(MEDIAERROR.TYPE.MEDIARECORDER, MEDIAERROR.LEVEL.E, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_RECORD_FILE_ERROR, Log2.getStackTraceString(e), false);
			e.printStackTrace();
		}
	}

	@Deprecated
	@Override
	public void uploadRecord() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// if (!isRecord) {
		// return;
		// }

		if (mRecord == null) {
			return;
		}

		KPItem info = getInfo();
		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (info != null) {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
		}
		if (list != null) {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		}

		// startLoading(__CLASSNAME__, getMethodName());

		// android.os.NetworkOnMainThreadException
		// (new UploadRecordAsyncTask()).execute();
		String path = mRecord.getPath();

		getApp().popupToast(R.string.alert_title_upload_running, Toast.LENGTH_SHORT);

		String audition_id = getList().getValue("audition_id");
		if (TextUtil.isEmpty(audition_id)) {
			audition_id = getList().getValue("id");
		}
		String song_id = getList().getValue("song_id");

		try {
			// KP_1011 파일업로드 (폐기->유지)
			KP_1011.KP_1011(getApp().p_mid, p_m1, p_m1, song_id, "RECORD", audition_id, path);
		} catch (Exception e) {

			e.printStackTrace();
			getApp().popupToast(R.string.alert_title_upload_error, Toast.LENGTH_SHORT);
		}
	}

	// /**
	// * 절대로 UI에 접근하지 않는다.
	// *
	// * @author isyoon
	// *
	// */
	// private class UploadRecordAsyncTask extends AsyncTask<Void, Integer, String> {
	//
	// @Override
	// protected String doInBackground(Void... params) {
	//
	// uploadRecordTask();
	// return null;
	// }
	//
	// }
	//
	// /**
	// * 절대로 UI에 접근하지 않는다.
	// *
	// * @author isyoon
	// *
	// */
	// Runnable uploadRecord = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// uploadRecordTask();
	// }
	// };
	//
	// /**
	// * 절대로 UI에 접근하지 않는다.
	// *
	// * @author isyoon
	// *
	// */
	// public void uploadRecordTask() {
	// String path = mRecord.getPath();
	// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "uploadRecordTask() : " + path);
	//
	// popupToast(R.string.alert_title_upload_running, Toast.LENGTH_SHORT);
	//
	// String song_id = getList().getString("song_id");
	// try {
	// KP_1011.KP_1011(getApp().p_mid, p_m1, p_m1, song_id, "RECORD", path);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// popupToast(R.string.alert_title_upload_error, Toast.LENGTH_SHORT);
	// }
	//
	// String path = mRecord.getPath();
	// File file = new File(path);
	// if (file != null && file.exists()) {
	// file.delete();
	// }
	//
	// try {
	// getBaseActivity().dismissDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
	// getBaseActivity().removeDialog2(DIALOG_PROGRESS_SONG_UPLOAD);
	// } catch (Exception e) {
	//
	// //e.printStackTrace();
	// }
	//
	// popupToast(R.string.alert_title_upload_complete, Toast.LENGTH_SHORT);
	//
	// }

	/**
	 * 
	 * <pre>
	 * 1분미리듣기처리
	 * </pre>
	 * 
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v.getId() == R.id.seek) {
			if (mPlayType == Player.TYPE.SING) {

				// 1분미리듣기처리
				if (!isPassUser()) {
					getApp().popupToast(R.string.warning_preview_slide, Toast.LENGTH_SHORT);
					return true;
				}

				// 녹음처리중처리
				if (isRecord) {
					getApp().popupToast(R.string.warning_onrecord_slide, Toast.LENGTH_SHORT);
					return true;
				}
			}
		}

		return super.onTouch(v, event);
	}

	@Override
	public void setCurTime(int pos, boolean force) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + pos + ", " + play_time + ", " + force);

		if (mPlayType == Player.TYPE.SING && !force) {

			// 1분미리듣기처리
			if (!isPassUser() || play_time != 0) {
				// getApp().popupToast(R.string.warning_preview_slide, Toast.LENGTH_SHORT);
				return;
			}

			// 녹음처리중처리
			if (isRecord) {
				// getApp().popupToast(R.string.warning_onrecord_slide, Toast.LENGTH_SHORT);
				return;
			}
		}

		if (player != null) {
			player.seek(pos);
		}

		// if (player != null && player.isPaused()) {
		// setRedrawPause();
		// }
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		resetOnPause();

		int id = v.getId();
		if (id == R.id.rec) {

			// 1분미리듣기처리-녹음처리
			if (!isPassUser()) {
				getApp().popupToast(R.string.warning_preview_slide, Toast.LENGTH_SHORT);
				((CheckBox) v).setChecked(false);
				return;
			}

			boolean checked = ((CheckBox) v).isChecked();
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + checked);

			if (checked) {
				// refresh();
				// stop();
				// KPnnnn();
				restart();
			} else {
				stopRecord(true);
			}
		} else if (id == R.id.up) {
			KP_1012();
			warnRecord(false);
			pause();
		} else if (v instanceof SongView || v.getId() == R.id.img_play) {
			// showPlayerBar(!isShowPlayerBar);
		} else {
		}

	}

	/**
	 * <pre>
	 * 자막위치
	 * </pre>
	 * 
	 */
	protected void setLyricAlign(Player.ALIGN align) {
		try {
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "align:" + align);

			if (player != null) {
				player.setLyricAlign(align);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 자막마진
	 * </pre>
	 * 
	 */
	protected void setLyricMargin() {
		try {
			// getPlayer().setLyricMargin(115);
			float dp = getResources().getDimension(R.dimen.height_bottom_play_bar);
			int h = (int) Util.dp2px(mContext, dp);
			h = (int) dp;
			h = findViewById(R.id.include_player_bar).getHeight();
			h = getResources().getDimensionPixelSize(R.dimen.height_bottom_play_bar);

			float den = getResources().getDisplayMetrics().density;
			int dpi = getResources().getDisplayMetrics().densityDpi;
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "den:" + den + ", dpi:" + dpi + " -> h:" + h);

			if (player != null) {
				player.setLyricMargin(h);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void showPlayer(boolean show) {
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + show);

		if (_IKaraoke.DEBUG) {
			getApp().popupToast(getPlayInfo(), Toast.LENGTH_LONG);
		}

		if (findViewById(R.id.edt_reply_text) != null) {
			WidgetUtils.setEditable(findViewById(R.id.edt_reply_text), isLoginUser());
			WidgetUtils.hideSoftKeyboard(getApp(), findViewById(R.id.edt_reply_text));
		}

		if (findViewById(R.id.include_reply) != null) {
			if (/* !show && */isLoginUser()) {
				findViewById(R.id.include_reply).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.include_reply).setVisibility(View.GONE);
			}
		}

		if (show) {
			showPlayer();
		} else {
			hidePlayer();
		}

	}

	protected CheckBox isShowPlayer;

	public boolean isShowPlayer() {
		if (isShowPlayer != null) {
			return isShowPlayer.isChecked();
		} else {
			return true;
		}
	}

	protected void showPlayer() {
		if (isShowPlayer != null) {
			isShowPlayer.setChecked(true);
		}

		View v = null;

		v = findViewById(R.id.viewSwitcher);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}

		v = findViewById(R.id.view2);
		if (v != null) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.ABOVE, 0);
			params.addRule(RelativeLayout.BELOW, 0);
			v.setLayoutParams(params);
		}

		if (mPlayType == Player.TYPE.SING) {
			showSongInfo();
		} else {
			showPeople();
		}

		if (player != null) {
			player.setView(true);
			player.redraw();
		}

		setLyricMargin();

		// postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// showPlayerBar(false);
		// }
		// }, 5000);

	}

	protected void hidePlayer() {
		if (player != null) {
			player.setView(false);
		}

		if (isShowPlayer != null) {
			isShowPlayer.setChecked(false);
		}

		View v = null;

		v = findViewById(R.id.viewSwitcher);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.view2);
		if (v != null) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
			params.addRule(RelativeLayout.ABOVE, 0);
			params.addRule(RelativeLayout.BELOW, R.id.view0);
			v.setLayoutParams(params);
		}

		if (mPlayType == Player.TYPE.SING) {
			hideSongInfo();
		} else {
			hidePeople();
		}

		// showPlayerBar(true);
	}

	/**
	 * 재생바(진행바/싱크바/타이틀바) 보이기/숨기기
	 */
	protected boolean isShowPlayerBar = true;

	public boolean isShowPlayerBar() {
		return isShowPlayerBar;
	}

	/**
	 * 재생바(진행바/싱크바/타이틀바) 보이기/숨기기
	 */
	public void showPlayerBar(boolean show) {

		if (!isShowPlayer.isChecked()) {
			return;
		}


		isShowPlayerBar = show;

		showPlayer(true);

		if (show) {
			// showPlayerSubject();
			showPlayerBar();
		} else {
			// hidePlayerSubject();
			hidePlayerBar();
		}

		// showPlayerSubject();
	}

	// private void showPlayerSubject() {
	// View v = findViewById(R.id.include_player_title);
	//
	// if (v != null) {
	// //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
	// //params.height = (int) getResources().getDimension(R.dimen.height_bottom_play_bar);
	// //v.setLayoutParams(params);
	// v.setVisibility(View.VISIBLE);
	// }
	//
	// }
	//
	// private void hidePlayerSubject() {
	// View v = findViewById(R.id.include_player_title);
	//
	// if (v != null) {
	// RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
	// params.height = (int) 0;
	// v.setLayoutParams(params);
	// v.setVisibility(View.GONE);
	// }
	//
	// }

	public void showPlayerBar() {
		View v = findViewById(R.id.include_player_bar);

		if (v != null) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
			params.height = (int) getResources().getDimension(R.dimen.height_bottom_play_bar);
			v.setLayoutParams(params);
		}

	}

	public void hidePlayerBar() {
		View v = findViewById(R.id.include_player_bar);

		if (v != null) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
			params.height = 0;
			v.setLayoutParams(params);
		}

	}

	public void showPeople() {
		View v = null;

		v = findViewById(R.id.view3);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}
	}

	public void hidePeople() {
		View v = null;

		v = findViewById(R.id.view3);
		if (v != null) {
			v.setVisibility(View.GONE);
		}
	}

	public void hideSongInfo() {
		View v = null;

		v = findViewById(R.id.view4);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.scr_song_read);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.txt_song_read);
		if (v != null) {
			findViewById(R.id.txt_song_read).setVisibility(View.GONE);
		}

		v = findViewById(R.id.btn_song_read);
		if (v != null) {
			((TextView) v).setText("▼ " + getString(R.string.btn_title_read));
		}
		findViewById(R.id.btn_song_read).setVisibility(View.GONE);
	}

	public void showSongInfo() {
		View v = null;

		v = findViewById(R.id.view4);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}
	}

	@Deprecated
	public void showViewSwitcher() {
		View v = mViewSwitcher;

		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}
	}

	@Deprecated
	public void hideViewSwitcher() {
		View v = mViewSwitcher;

		if (v != null) {
			v.setVisibility(View.GONE);
		}
	}

	/**
	 * @see BaseListFragment#refresh()
	 */
	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		startLoading(__CLASSNAME__, getMethodName(), true);


		mPlayTimeBar.setEnabled(false);
		pause();
		stop();
		super.refresh();

	}

	@Override
	protected void openFeelPost(String mode, KPnnnn KP_xxxx, int index) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_play.equals(KP_xxxx) + ":" + KP_xxxx + "-" + KP_play);

		KPItem info = new KPItem();
		KPItem list = new KPItem();

		try {
			// 무조건무조건이야~~~
			KP_xxxx = KP_play;

			if (KP_xxxx.getInfo() != null) {
				info = KP_xxxx.getInfo();
			}

			if (KP_xxxx.getList(index) != null) {
				list = KP_xxxx.getList(index);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			// FEEL 등록인 경우 FEEL등록
			// 화면 설정 값
			// K : 반주곡화면에서 등록
			// R : 녹음곡화면에서 등록
			// T : 일반화면에서 등록
			// 화면을 제외한 화면에서 등록
			// A : 오디션 화면에서 등록
			if (mPlayType == TYPE.SING) {
				list.putValue("feel_type", "K");
			} else if (mPlayType == TYPE.LISTEN) {
				list.putValue("feel_type", "R");
			} else {
			}

			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));

			isOnPausePause = false;
			isOnPauseClose = false;

			super.openFeelPost(mode, KP_xxxx, index);
		} catch (Exception e) {

			e.printStackTrace();
		}

		stopLoading(__CLASSNAME__, getMethodName());
	}
}
