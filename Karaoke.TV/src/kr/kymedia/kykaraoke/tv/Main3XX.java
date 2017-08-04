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
 * filename	:	Main22.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main22.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.play._Listen;
import kr.kymedia.kykaraoke.tv.play._PlayView;

/**
 * <pre>
 * 반주곡/녹음곡 스트리밍처리
 * 이전(previous)/이후(next)
 * 동영상재시작제거(뮤비제외)
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 4. 3.
 */
class Main3XX extends Main3X {
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

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + savedInstanceState);
		super.onCreate(savedInstanceState);
		onCreate();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + savedInstanceState);
	}

	private void onCreate() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
	}

	@Override
	protected void startInit() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName());
		super.startInit();
	}

	/**
	 * <pre>
	 * COMPLETE_SONG_PLAY: // 반주곡 파일 다운로드
	 * _COMPLETE_SONG: // 반주곡 시작
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
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg);

		int state = msg.getData().getInt("state");

		// 반주곡스트리밍처리
		switch (state) {
			case COMPLETE_SONG_PLAY: // 반주곡 파일 다운로드
				stopTaskShowMessageNotResponse();
				down();
				break;
			case COMPLETE_DOWN_SONG: // 반주곡 시작
				stopTaskShowMessageNotResponse();
				start();
				break;
			case COMPLETE_LISTEN_SONG: // 녹음곡 재생 정보
			case COMPLETE_LISTEN_OTHER_SONG:
				stopTaskShowMessageNotResponse();
				downListen(state);
				break;
			case COMPLETE_DOWN_LISTEN: // 녹음곡 파일 다운로드
			case COMPLETE_DOWN_LISTEN_OTHER:
				stopTaskShowMessageNotResponse();
				startListen(state);
				break;
			default:
				try {
					super.KP(msg);
				} catch (Exception e) {
					if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
				}
				break;
		}
	}

	/**
	 * <pre>
	 * 가사파일만받는거다~~~
	 * </pre>
	 */
	@Override
	protected void down() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		super.down();
	}

	/**
	 * <pre>
	 * 아무것도안받는거다~~~
	 * </pre>
	 */
	@Override
	protected void downListen(int state) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + COMPLETE_KP.get(state));
		super.downListen(state);
	}

	public void setLyricsMarginBottom(int lyricsMarginBottom) {
		player.setLyricsMarginBottom(lyricsMarginBottom);
	}

	/**
	 * <pre>
	 * AOSP(BHX-S300)
	 * <a href="http://pms.skdevice.net/redmine/issues/3482">3482 일부노래 자막 하단이 잘려서 출력되는 현상</a>
	 * 	48859 - '씨스타 - Shake It' 노래 부르기 진행 중 일부 가사 하단부분이 잘려서 출력됩니다.
	 * </pre>
	 * <p/>
	 * 자막하단여백
	 */
	@Override
	protected void setPlayer() {
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + findViewById(R.id.player));

		player = (_PlayView) findViewById(R.id.player);

		int lyricsMarginBottom = 10;

		//if (findViewById(R.id.layout_bottom) != null) {
		//	lyricsMarginBottom += findViewById(R.id.layout_bottom).getHeight();
		//}
		if (findViewById(R.id.layout_information) != null) {
			lyricsMarginBottom += findViewById(R.id.layout_information).getHeight();
		}
		setLyricsMarginBottom(lyricsMarginBottom);

		// bgkim 폰트 TYPE 적용
		player.setTypeface(getTypeface());
		int iStrokeSize = 4;
		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			iStrokeSize = 6;
		} else {
			iStrokeSize = 4;
		}
		player.setStrokeSize(iStrokeSize);

		player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {

				onPlayerBufferingUpdate(mp, percent);
			}
		});

		player.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {

				onPlayerPrepared(mp);
			}
		});

		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				onPlayerCompletion(mp);
			}
		});

		player.setOnInfoListener(new OnInfoListener() {

			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {

				return onPlayerInfo(mp, what, extra);
			}
		});

		player.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {

				return onPlayerError(mp, what, extra);
			}
		});
	}

	/**
	 * @see android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate(MediaPlayer mp, int percent)
	 */
	protected void onPlayerBufferingUpdate(MediaPlayer mp, int percent) {

		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName() + mp + "(" + percent + "%)");
	}

	/**
	 * @see android.media.MediaPlayer.OnPreparedListener#onPrepared(MediaPlayer mp)
	 */
	protected void onPlayerPrepared(MediaPlayer mp) {
		Log.i(_toString() + TAG_SING, "onPlayerPrepared() " + remote.getState() + ":" + mp);
		stopListen();
		removeListening();
		play();
		stopLoading(getMethodName());
	}

	/**
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(MediaPlayer mp)
	 */
	protected void onPlayerCompletion(MediaPlayer mp) {

		Log.i(_toString() + TAG_SING, "onPlayerCompletion() " + remote.getState() + ":" + mp);
		stop(PLAY_NEXT);
		stopLoading(getMethodName());
		ShowScore();
	}

	/**
	 * @see android.media.MediaPlayer.OnInfoListener#onInfo(MediaPlayer mp, int what, int extra)
	 */
	protected boolean onPlayerInfo(MediaPlayer mp, int what, int extra) {

		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName() + mp + "(" + what + ", " + extra + ")");
		return true;
	}

	/**
	 * @see android.media.MediaPlayer.OnErrorListener#onError(MediaPlayer mp, int what, int extra)
	 */
	protected boolean onPlayerError(MediaPlayer mp, int what, int extra) {

		Log.wtf(_toString() + TAG_SING, "onPlayerError() " + remote.getState() + ":" + mp + "(" + what + ", " + extra + ")");
		String msg = getString(R.string.message_error_sing) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
		msg += "\n" + mp;
		msg += "\n" + "(what:" + what + ",extra:" + extra + ")";
		ShowMessageAlert(msg);
		CANCEL();
		stop(PLAY_ERROR);
		return true;
	}

	protected void setVideo() {
		video = get_Application().getVideoActivity();
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + video);

		video.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {

				onVideoPrepared(mp);
			}
		});

		video.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				onVideoCompletion(mp);
			}
		});

		video.setOnInfoListener(new OnInfoListener() {

			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {

				return onVideoInfo(mp, what, extra);
			}
		});

		video.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {

				return onVideoError(mp, what, extra);
			}
		});

		video.showBackBoard();
	}

	/**
	 * @see android.media.MediaPlayer.OnPreparedListener#onPrepared(MediaPlayer mp)
	 */
	protected void onVideoPrepared(MediaPlayer mp) {

		Log.i(_toString() + TAG_VIDEO, "onVideoPrepared() " + remote.getState() + ":" + mp + ":" + mp.isLooping());
		hideBackBoard();
		open();
	}

	/**
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(MediaPlayer mp)
	 */
	protected void onVideoCompletion(MediaPlayer mp) {

		Log.i(_toString() + TAG_VIDEO, "onVideoCompletion() " + remote.getState() + ":" + mp);
		// showBackground();
		if (video.isMusicVideo()) {
			hideBackBoard();
		} else {
			showBackBoard();
		}
	}

	/**
	 * @see android.media.MediaPlayer.OnInfoListener#onInfo(MediaPlayer mp, int what, int extra)
	 */
	protected boolean onVideoInfo(MediaPlayer mp, int what, int extra) {

		// if (IKaraokeTV.DEBUG) _LOG.i(_toString() + TAG_MAIN, "onVideoInfo() " + mp + "(" + what + ", " + extra + ")");
		return true;
	}

	/**
	 * @see android.media.MediaPlayer.OnErrorListener#onError(MediaPlayer mp, int what, int extra)
	 */
	protected boolean onVideoError(MediaPlayer mp, int what, int extra) {

		Log.wtf(_toString() + TAG_MAIN, "onVideoError() " + remote.getState() + ":" + mp + "(" + what + ", " + extra + ")");
		String msg = getString(R.string.message_error_video) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
		msg += "\n" + mp;
		msg += "\n" + "(what:" + what + ",extra:" + extra + ")";
		ShowMessageAlert(msg);
		showBackBoard();
		CANCEL();
		stop(PLAY_ERROR);
		return true;
	}

	COMPLETE_KP COMPLETE = null;

	protected void setListening() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + this.COMPLETE);

		stopLoading(getMethodName());

		remote.m_iState = STATE_LISTENING;

		switch (this.COMPLETE) {
			case COMPLETE_DOWN_LISTEN:
				setListeningState();
				break;
			case COMPLETE_DOWN_LISTEN_OTHER:
				setListeningStateOther();
				break;
			default:
				break;
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + this.COMPLETE);
	}

	private void startListen(int state) {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + ":" + remote.getState() + ":" + COMPLETE_KP.get(state));

		String path = m_strListenSongUrl;
		// test
		// m_strListenSongUrl = "http://192.168.3.56/listen.m4a";

		try {
			if (listen == null) {
				setListen();
			}
			listen.setPath(path);
			listen.open();
			this.COMPLETE = COMPLETE_KP.get(state);
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			this.COMPLETE = null;
			String msg = getString(R.string.message_error_listen) + "(" + getString(R.string.message_error_title_number) + record_id + ")";
			if (IKaraokeTV.DEBUG) {
				msg += "\n" + e.getMessage();
			} else {
				msg += "\n" + getString(R.string.message_error_commend_retry);
			}
			ShowMessageAlert(msg);
		}

	}

	protected void setListen() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + listen);

		listen = new _Listen(this);

		listen.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {

				onListenBufferingUpdate(mp, percent);
			}
		});

		listen.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {

				onListenPrepared(mp);
			}
		});

		listen.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				onListenCompletion(mp);
			}
		});

		listen.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {

				return onListenError(mp, what, extra);
			}
		});
	}

	/**
	 * @see android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate(MediaPlayer mp, int percent)
	 */
	protected void onListenBufferingUpdate(MediaPlayer mp, int percent) {

		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName() + mp + "(" + percent + "%)");
	}

	/**
	 * @see android.media.MediaPlayer.OnPreparedListener#onPrepared(MediaPlayer mp)
	 */
	protected void onListenPrepared(MediaPlayer mp) {

		Log.i(_toString() + TAG_LISTEN, "onListenPrepared() " + remote.getState() + ":" + mp);
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + isPlaying() + mp);
		if (isPlaying()) {
			listen.stop();
		} else {
			listen.play();
			setListening();
		}

		stopLoading(getMethodName());
	}

	/**
	 * @see android.media.MediaPlayer.OnCompletionListener#onCompletion(MediaPlayer)
	 */
	protected void onListenCompletion(MediaPlayer mp) {

		Log.i(_toString() + TAG_LISTEN, "onListenCompletion() " + remote.getState() + ":" + mp);
		exitListen();
	}

	/**
	 * @see android.media.MediaPlayer.OnErrorListener#onError(MediaPlayer mp, int what, int extra)
	 * @see kr.kymedia.kykaraoke.tv.Main2#RemoveListenDisplay()
	 */
	protected boolean onListenError(MediaPlayer mp, int what, int extra) {

		Log.wtf(_toString() + TAG_LISTEN, "onListenError() " + remote.getState() + ":" + mp + "(" + what + ", " + extra + ")");
		String msg = getString(R.string.message_error_listen) + "(" + getString(R.string.message_error_title_number) + record_id + ")";
		msg += "\n" + mp;
		msg += "\n" + "(what:" + what + ",extra:" + extra + ")";
		ShowMessageAlert(msg);
		RemoveListenDisplay();
		CANCEL();
		stop(PLAY_ERROR);
		return false;
	}

	protected void exitListen() {
		if (m_layoutListeningOther != null) {
			exitListeningOther();
		}
		exitListening();
	}

	@Override
	protected void stopListen() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + isListening() + ":" + listen);
		super.stopListen();
	}

	/**
	 * @see Main3XX#start()
	 */
	@Override
	@Deprecated
	public void StartPlayActivity() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + findViewById(R.id.player));
		// HideLoading();
		//
		// Bundle bundle = new Bundle();
		// bundle.putInt(KEY_ACTIVITY, ACTIVITY_PLAYER);
		// bundle.putStringArrayList(SONGPLAYER_SKYM, arrStartPlayList);
		// Intent intent = new Intent(getApplicationContext(), Play.class);
		// if (bundle != null) {
		// intent.putExtras(bundle);
		// }
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(intent);
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + findViewById(R.id.player));
	}

	/**
	 * <pre>
	 * ret = true : 반주곡/동영상 같이 시작할수 있다.
	 * </pre>
	 */
	protected boolean isMusicVideo(int song, String url) {
		boolean ret = false;
		String number = "99999";
		try {
			number = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
			if (song == Integer.parseInt(number)) {
				ret = true;
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}
		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + ret + ":" + song + "-" + number + ":" + url);
		return ret;
	}

	/**
	 * 동영상사용여부
	 */
	private boolean isPlayVideo = true;

	/**
	 * 동영상사용여부
	 */
	public boolean isPlayVideo() {
		return isPlayVideo;
	}

	/**
	 * 동영상사용여부
	 */
	public void setPlayVideo(boolean isPlayVideo) {
		this.isPlayVideo = isPlayVideo;
		if (video != null) {
			video.setPlayVideo(isPlayVideo);
		}
	}

	/**
	 * <pre>
	 * 동영상재시작제거(뮤비제외)
	 * 시작:동영상오픈
	 * 실패:반주곡오픈
	 * </pre>
	 */
	protected void start() {

		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName());
		// HideLoading();

		//String temp = sdPath + File.separator + "sing.skym";
		//path_lyrics.clear();
		//path_lyrics.add(temp);

		int number = 00000;
		try {
			number = Integer.parseInt(m_strRequestPlaySongID);
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		boolean isMusicVideo = isMusicVideo(number, video_url);

		SetTopNumber(m_strRequestPlaySongID);

		if (isPlayVideo && !TextUtils.isEmpty(video_url)) {
			video.setPlayVideoUrl(video_url);
			// 동영상재시작제거(뮤비제외)
			if (video.isMusicVideo() && !isMusicVideo) {
				// 뮤비아니면그냥쓴다.
				open();
			} else {
				// 뮤비거나처음실행이거나.
				try {
					video.open();
				} catch (Exception e) {
					if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
					String msg = getString(R.string.message_error_video) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
					if (IKaraokeTV.DEBUG) {
						msg += "\n" + e.getMessage();
					} else {
						msg += "\n" + getString(R.string.message_error_commend_retry);
					}
					ShowMessageAlert(msg);
					open();
				}
			}
		} else {
			open();
		}

	}

	/**
	 * <pre>
	 * 동영상재시작제거(뮤비제외)
	 * 이전(previous)/이후(next)
	 * </pre>
	 */
	@Override
	public void stopPlay(int engage) {
		Log.w(_toString(), "stopPlay()" + "[ST]" + PLAY_ENGAGE.get(engage) + ":" + (!TextUtil.isEmpty(getEngageSong())) + ":" + video);
		super.stopPlay(engage);

		// 동영상재시작제거(뮤비제외)
		if (engage == PLAY_STOP || TextUtil.isEmpty(getEngageSong())) {
			if (video != null && video.isPlaying()) {
				video.startBlankVideo(video_url_back, PLAY_STOP);
			}
		}

		if (engage == PLAY_NEXT) {
			showBackBoard();
		}

		if (player != null) {
			player.stop();
		}

		if (listen != null) {
			listen.stop();
		}

		// 이전(previous)/이후(next)
		if (TextUtil.isEmpty(getEngageSong())) {
			delPlayList.clear();
		}

		if (!isShowMenu()) {
			ShowMenu(getMethodName());
		}

		Log.w(_toString(), "stopPlay()" + "[ED]" + engage + ":" + (!TextUtil.isEmpty(getEngageSong())) + ":" + video);
	}

	protected void setKaraokeMP3Server() {
		if (IKaraokeTV.DEBUG) {
			Toast toast = Toast.makeText(getApplicationContext(), player.setKaraokeMP3Server(), Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	public void onClick(View v) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(v) + ":" + v);

		super.onClick(v);

		if (v.getId() == R.id.img_ky_logo) {
			setKaraokeMP3Server();
		}
	}

	/**
	 * [!!!중요!!!]테스트/데모 배포시 여기만 차단하면 비디오는 작살난다.
	 * <p/>
	 * <pre>
	 * 반주곡오픈
	 * </pre>
	 */
	protected void open() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + m_strRequestPlaySongID + ":" + url_lyric);
		if (TextUtil.isEmpty(url_lyric)) {
			return;
		}
		try {
			if (player == null) {
				setPlayer();
			}

			if (player != null) {
				player.setMp3(url_skym);
				//player.setLyric(path_lyrics);
				player.setLyric(sdPath + File.separator + "sing.skym");
				player.setSongId(m_strRequestPlaySongID);
				player.open();
			}

			// hideBackboard();

			clear();
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
			e.printStackTrace();
			String msg = getString(R.string.message_error_sing) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
			if (IKaraokeTV.DEBUG) {
				msg += "\n" + e.getMessage();
			} else {
				msg += "\n" + getString(R.string.message_error_commend_retry);
			}
			ShowMessageAlert(msg);
			CANCEL();
			stop(PLAY_STOP);
		}
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + m_strRequestPlaySongID + ":" + url_lyric);
	}

	@Override
	protected void play() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.play();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	void CANCEL() {
		super.CANCEL();
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]");
		if (download != null) {
			download.interrupt();
		}
		stopPlay(PLAY_STOP);
		stopLoading(getMethodName());
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]");
	}

	protected void clear() {
		// 반주곡
		url_lyric = "";
		url_skym = "";
		video_url = "";
		type = "";

		// 녹음곡
		record_id = "";
		m_strListenSongUrl = "";
		m_strListeningSongID = "";
	}

	/**
	 * <pre>
	 * 반주곡종료
	 * </pre>
	 */
	@Override
	protected void stop(int engage) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + engage);

		super.stop(engage);

		clear();

		// 1. 반주곡 STOP
		// Play playActivity = (Play)Play.ActivityPlay;
		// if (playActivity != null) {
		// stopPlay(PLAY_STOP);
		// }
		stopPlay(engage);

		try {
			if (video != null && engage == PLAY_ERROR) {
				video.startBlankVideo(video_url_back, PLAY_STOP);
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) _LOG(getMethodName(), e);
		}

		// 2. 녹음곡 STOP
		if (isListening()) {
			// exitListening();
			exitListen();
		}

		wakeLockRelease();
	}

	/**
	 * 예약곡:이전(previous)/이후(next)
	 */
	@Override
	protected void next() {
		if (isLoading()) {
			return;
		}

		// ShowLoading(LONG);

		//if (!TextUtil.isEmpty(getEngageSong())) {
		//	HideMenu(getMethodName());
		//}

		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + delPlayList + m_strRequestPlaySongID + song_ids);
		if (!TextUtil.isEmpty(getEngageSong())) {
			startSing(null);
			// stop(PLAY_NEXT);
			// m_strRequestPlaySongID = getEngageSong();
			// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
			// SetTopNumber(m_strRequestPlaySongID);
		} else {
			stopLoading(getMethodName());
		}
	}

	/**
	 * 예약곡:이전(previous)/이후(next)
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#previous()
	 */
	@Override
	protected void previous() {
		if (isLoading()) {
			return;
		}

		// ShowLoading(LONG);

		//if (!TextUtil.isEmpty(getEngageSong())) {
		//	HideMenu(getMethodName());
		//}

		if (IKaraokeTV.DEBUG) Log.d(_toString(), getMethodName() + delPlayList + m_strRequestPlaySongID + song_ids);
		if (delPlayList.size() > 0) {
			String curr = delPlayList.get(delPlayList.size() - 1);
			// 지금재생하고있는곡을예약하면지랄이야
			curr = null;
			// 현재재생중인곡건너뛰고(
			if (isPlaying() && m_strRequestPlaySongID.equalsIgnoreCase(delPlayList.get(delPlayList.size() - 1))) {
				curr = delPlayList.get(delPlayList.size() - 1);
				delPlayList.remove(delPlayList.size() - 1);
				song_ids.add(0, curr);
			}
			// 이전곡을재생하고
			if (delPlayList.size() > 0) {
				song_ids.add(0, delPlayList.get(delPlayList.size() - 1));
				delPlayList.remove(delPlayList.size() - 1);
				next();
			} else {
				// 없으면원복...
				if (!TextUtil.isEmpty(curr)) {
					delPlayList.add(curr);
					song_ids.remove(0);
				}
			}
		} else {
			stopLoading(getMethodName());
		}
	}

	@Override
	public void ShowMessageAlert(String message) {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName() + message);
		super.ShowMessageAlert(message);
		setBottomProductText(message);
	}
}
