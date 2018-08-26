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
 * project	:	Karaoke.TV
 * filename	:	_SongPlayerView2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.play
 *    |_ _SongPlayerView2.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.play;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.KaraokePath;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnTimedTextListener;
import android.util.AttributeSet;
import android.util.Log;

/**
 * <pre>
 * 반주곡/녹음곡 스트리밍처리
 * </pre>
 *
 * @author isyoon
 * @since 2015. 2. 3.
 * @version 1.0
 */
class PlayView3 extends PlayView2 {
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

	public PlayView3(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public PlayView3(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PlayView3(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlayView3(Context context) {
		super(context);
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
		mOnCompletionListener = null;
		mOnErrorListener = null;
		mOnInfoListener = null;
		mOnPreparedListener = null;
	}

	/**
	 * <pre>
	 * 211.236.190.103
	 * 0:금영서버: http://211.236.190.103:8080/svc_media/mmp3/08888.mp3
	 * 1:KYM서버: http://resource.kymedia.kr/ky/mp/88/08888.mp3
	 * 2:싸이월드: http://cyms.chorus.co.kr/cykara_dl2.asp?song_id=08888
	 * </pre>
	 */
	int server = 0;

	public String setKaraokeMP3Server() {

		server++;
		if (server > 2) {
			server = 0;
		}

		String ret = "[server:" + server + "]" + getKaraokeMP3(8888);
		return ret;
	}

	public String getKaraokeMP3(int number) {
		String ret;

		// 금영서버: http://211.236.190.103:8080/svc_media/mmp3/08888.mp3
		// KYM서버: http://resource.kymedia.kr/ky/mp/88/08888.mp3
		// 싸이월드: http://cyms.chorus.co.kr/cykara_dl2.asp?song_id=08888
		String host = "http://211.236.190.103:8080/svc_media/";
		String path = "mmp3";
		String form = "/%05d.mp3";

		// KYM서버: http://resource.kymedia.kr/ky/mp/88/08888.mp3
		// host = "http://resource.kymedia.kr/ky/mp/";
		// path = String.format(getResources().getConfiguration().locale, "%05d", number).substring(3);
		// form = "/%05d.mp3";

		// if (server == R.id.radioServerKYS) {
		// //금영서버: http://211.236.190.103:8080/svc_media/mmp3/08888.mp3
		// host = "http://" + KaraokePath.getHosts()[0] + ":8080/svc_media/";
		// path = "mmp3";
		// form = "/%05d.mp3";
		// } else if (server == R.id.radioServerKYM) {
		// //KYM서버: http://resource.kymedia.kr/ky/mp/88/08888.mp3
		// host = "http://" + KaraokePath.getHosts()[1] + "/ky/mp/";
		// path = String.format(getResources().getConfiguration().locale, "%05d", number).substring(3);
		// form = "/%05d.mp3";
		// } else if (server == R.id.radioServerCYW) {
		// //싸이월드: http://cyms.chorus.co.kr/cykara_dl2.asp?song_id=08888
		// host = "http://" + KaraokePath.getHosts()[2] + "/";
		// path = "cykara_dl2.asp?song_id=";
		// form = "%05d";
		// }
		switch (server) {
		case 0:
			// 금영서버: http://211.236.190.103:8080/svc_media/mmp3/08888.mp3
			host = "http://" + KaraokePath.getHosts()[0] + ":8080/svc_media/";
			path = "mmp3";
			form = "/%05d.mp3";
			break;

		case 1:
			// KYM서버: http://resource.kymedia.kr/ky/mp/88/08888.mp3
			host = "http://" + KaraokePath.getHosts()[1] + "/ky/mp/";
			path = String.format(getResources().getConfiguration().locale, "%05d", number).substring(3);
			form = "/%05d.mp3";
			break;

		case 2:
			// 싸이월드: http://cyms.chorus.co.kr/cykara_dl2.asp?song_id=08888
			host = "http://" + KaraokePath.getHosts()[2] + "/";
			path = "cykara_dl2.asp?song_id=";
			form = "%05d";
			break;

		default:
			break;
		}

		ret = String.format(host + path + form, number);

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + server + "-" + number + "-" + ret);

		return ret;
	}

	OnBufferingUpdateListener mOnBufferingUpdateListener;

	public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
		mOnBufferingUpdateListener = listener;
	}

	OnPreparedListener mOnPreparedListener;

	public void setOnPreparedListener(OnPreparedListener listener) {
		mOnPreparedListener = listener;
	}

	OnTimedTextListener mOnTimedTextListener;

	public void setOnTimedTextListener(OnTimedTextListener listener) {
		mOnTimedTextListener = listener;
	}

	OnCompletionListener mOnCompletionListener;

	public void setOnCompletionListener(OnCompletionListener listener) {
		mOnCompletionListener = listener;
	}

	OnInfoListener mOnInfoListener;

	public void setOnInfoListener(OnInfoListener listener) {
		mOnInfoListener = listener;
	}

	OnErrorListener mOnErrorListener;

	public void setOnErrorListener(OnErrorListener listener) {
		mOnErrorListener = listener;
	}

	protected void reset() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		if (getMediaPlayer() != null) {
			getMediaPlayer().setOnBufferingUpdateListener(null);
			getMediaPlayer().setOnPreparedListener(null);
			getMediaPlayer().setOnTimedTextListener(null);
			getMediaPlayer().setOnCompletionListener(null);
			getMediaPlayer().setOnInfoListener(null);
			getMediaPlayer().setOnErrorListener(null);
			getMediaPlayer().reset();
		}
	}

	protected void release() {
		if (getMediaPlayer() != null) {
			getMediaPlayer().release();
			setMediaPlayer(null);
		}
	}

	protected void init() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		reset();

		if (getMediaPlayer() == null) {
			setMediaPlayer(new MediaPlayer());
		}

		getMediaPlayer().setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

			/**
			 * 재생처리는Main22에서
			 * 
			 * @see kr.kymedia.kykaraoke.tv.Main3XX
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setPlayer()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setVideo()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListen()
			 */
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {

				// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + mp + "(" + percent + "%)");
				if (mOnBufferingUpdateListener != null) {
					mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
				}
			}
		});

		getMediaPlayer().setOnPreparedListener(new OnPreparedListener() {

			/**
			 * 재생처리는Main22에서
			 * 
			 * @see kr.kymedia.kykaraoke.tv.Main3X#onCreate()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setPlayer()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setVideo()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListen()
			 */
			@Override
			public void onPrepared(MediaPlayer mp) {

				if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + mp);
				if (mOnPreparedListener != null) {
					mOnPreparedListener.onPrepared(mp);
				}
			}
		});

		getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {

			/**
			 * 재생처리는Main22에서
			 * 
			 * @see kr.kymedia.kykaraoke.tv.Main3X#onCreate()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setPlayer()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setVideo()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListen()
			 */
			@Override
			public void onCompletion(MediaPlayer mp) {

				if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + mp);
				if (mOnCompletionListener != null) {
					mOnCompletionListener.onCompletion(mp);
				}
			}
		});

		getMediaPlayer().setOnInfoListener(new OnInfoListener() {

			/**
			 * 재생처리는Main22에서
			 * 
			 * @see kr.kymedia.kykaraoke.tv.Main3X#onCreate()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setPlayer()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setVideo()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListen()
			 */
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {

				// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + mp + "(" + what + ", " + extra + ")");
				if (mOnInfoListener != null) {
					mOnInfoListener.onInfo(mp, what, extra);
				}
				return false;
			}
		});

		getMediaPlayer().setOnErrorListener(new OnErrorListener() {

			/**
			 * 재생처리는Main22에서
			 * 
			 * @see kr.kymedia.kykaraoke.tv.Main3X#onCreate()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setPlayer()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setVideo()
			 * @see kr.kymedia.kykaraoke.tv.Main3XX#setListen()
			 */
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {

				if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + mp + "(" + what + ", " + extra + ")");
				if (mOnErrorListener != null) {
					mOnErrorListener.onError(mp, what, extra);
				}
				return false;
			}
		});

	}

	String song_id = "99999";

	public void setSongId(String song_id) {

		this.song_id = song_id;
	}

	String path;

	public String getPath() {
		return path;
	}

	String mp3;

	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	public void open() throws Exception {

		// String path = getApplicationContext().getExternalFilesDir(null) + "/test.skym";
		// if (getLyricList() == null || getLyricList().size() == 0) {
		// path = getApplicationContext().getExternalFilesDir(null) + "/test.skym";
		// } else {
		// path = getLyricList().get(getLyricIdx()).toString();
		// }
		String path = getLyric();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + path);
		open(path);
	}

	/**
	 * skym가사로드
	 * mp3스트리밍
	 * @throws Exception
	 * @see kr.kymedia.kykaraoke.tv.play.PlayView2#open(java.lang.String)
	 */
	@Override
	protected boolean open(String path) throws Exception {

		Log.w(_toString(), "open() " + "[ST]");

		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + path);
		this.path = path;

		try {
			Log.i(_toString(), "open() " + "[getSongData]");
			getSongData().release();
			getSongData().load(path);

			Log.i(_toString(), "open() " + "[open]");
			// (new open()).execute();
			if (TextUtil.isNetworkUrl(mp3)) {
				stream();
			} else {
				local();
			}

		} catch (Exception e) {
			Log.e(_toString(), "open() " + "[NG]" + "\n" + Log.getStackTraceString(e));
			e.printStackTrace();
			stop();
			throw (e);
		}

		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ED]" + path);
		Log.w(_toString(), "open() " + "[ED]");

		return true;
	}

	// /**
	// * <pre>
	// * 쓸데없이시발~~~
	// * </pre>
	// *
	// * @author isyoon
	// *
	// */
	// private class open extends AsyncTask<Void, Integer, String> {
	//
	// @Override
	// protected String doInBackground(Void... params) {
	//
	// _LOG.i(_toString(), "open() " + "[doInBackground]");
	// try {
	// if (TextUtil.isNetworkUrl(mp3)) {
	// stream();
	// } else {
	// local();
	// }
	// } catch (Exception e) {
	//
	// _LOG.e(_toString(), "open() " + "[doInBackground]" + "[NG]" + "\n" + _LOG.getStackTraceString(e));
	// e.printStackTrace();
	// }
	// return null;
	// }
	// }

	protected void stream() throws Exception {
		Log.w(_toString(), "stream() " + "[ST]");

		if (!TextUtil.isNetworkUrl(mp3)) {
			Log.i(_toString(), "stream() " + "[getKaraokeMP3]");
			int number = Integer.parseInt(song_id);
			mp3 = getKaraokeMP3(number);
		}

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + mp3);

		Log.i(_toString(), "stream() " + "[MediaPlayer]");
		init();

		Log.i(_toString(), "stream() " + "[setAudioStreamType]");
		getMediaPlayer().setAudioStreamType(AudioManager.STREAM_MUSIC);

		Log.i(_toString(), "stream() " + "[setDataSource]");
		getMediaPlayer().setDataSource(mp3);

		Log.i(_toString(), "stream() " + "[prepareAsync]");
		getMediaPlayer().prepareAsync();

		Log.w(_toString(), "stream() " + "[ED]");
	}

	protected void local() throws Exception {
		Log.w(_toString(), "local() " + "[ST]");

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + path);
		File sourceFile = new File(path);

		if (sourceFile.exists()) {
			FileInputStream fs = new FileInputStream(sourceFile);
			FileDescriptor fd = fs.getFD();
			getMediaPlayer().setDataSource(fd);
			fs.close(); // 데이터 소스를 설정한 후 스트림을 닫았다.
			getMediaPlayer().prepare();
		}

		Log.e(_toString(), "local() " + "[ED]");
	}

	public void playLyrics() throws Exception {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		try {
			getLyricsPlay().play();
		} catch (Exception e) {

			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName());
			// e.printStackTrace();
			throw (e);
		}
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.PlayView2#play()
	 */
	@Override
	public boolean play() throws Exception {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");

		boolean ret = true;

		try {
			if (getMediaPlayer() != null /* && (ret = super.play()) */) {

				if (getPlayState() == PLAY_ENGAGE.PLAY_STOP) {
					getMediaPlayer().start();
					setPlayState(PLAY_ENGAGE.PLAY_PLAY);
				}

				// getLyricsPlay().play();
			}
		} catch (Exception e) {

			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName());
			// e.printStackTrace();
			throw (e);
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
		return ret;
	}

	@Override
	public void setVisibility(int visibility) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + visibility);
		super.setVisibility(visibility);
		if (getLyricsPlay() != null) {
			getLyricsPlay().setVisibility(visibility);
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.PlayView2#stop()
	 */
	@Override
	public void stop() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + isPlaying() + ":" + getPlayState());
		// boolean ret = super.stop();

		try {
			if (getLyricsPlay() != null && getLyricsPlay().getLyricsPlayThread() != null) {
				getLyricsPlay().getLyricsPlayThread().init();
			}

			if (getPlayState() != PLAY_ENGAGE.PLAY_STOP) {
				// if (isPlaying())
				if (getMediaPlayer() != null) {
					if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[STOP]" + isPlaying() + ":" + getPlayState());
					getMediaPlayer().stop();
				}

				if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[RESET]" + isPlaying() + ":" + getPlayState());
				reset();

				getLyricsPlay().stop();

				getSongData().release();

				setPlayState(PLAY_ENGAGE.PLAY_STOP);
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName() + isPlaying() + ":" + getPlayState());
			e.printStackTrace();
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + isPlaying() + ":" + getPlayState());
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.PlayView2#pause()
	 */
	@Override
	protected void pause() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + isPlaying() + ":" + getPlayState());

		try {
			if (getPlayState() == PLAY_ENGAGE.PLAY_PLAY) {
				if (isPlaying()) {
					if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[PAUSE]" + isPlaying() + ":" + getPlayState());
					getMediaPlayer().pause();
				}
				setPlayState(PLAY_ENGAGE.PLAY_PAUSE);
			}
		} catch (Exception e) {
			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName() + isPlaying() + ":" + getPlayState());
			e.printStackTrace();
		}

		getLyricsPlay().pause();

	}

	@Override
	protected void resume() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		super.resume();

		getLyricsPlay().resume();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.PlayView2#close()
	 */
	@Override
	protected void close() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());

		// super.close();
		stop();
	}

}
