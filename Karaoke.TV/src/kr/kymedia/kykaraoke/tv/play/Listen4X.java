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
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Listen4X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.play
 *    |_ Listen4X.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.play;

import android.content.Context;
import android.media.AudioTrack;
import android.os.Handler;
import android.util.Log;

import kr.kymedia.karaoke.play.SongPlay;
import kr.kymedia.karaoke.play._SoundTouchPlay;
import kr.kymedia.karaoke.play.impl.ISongPlay;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * SongPlay(SoundTouchPlay)사용
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-01-07
 */
class Listen4X extends Listen4 implements ISongPlay {
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

	public Listen4X(Context context) {
		super(context);
		Log.e(_toString(), getMethodName() + ":" + type + ":" + isPitchTempo + ":" + song);
		start();
	}

	private TYPE type = TYPE.SOUNDTOUCHPLAY;

	/**
	 * 재생플레이어선택
	 * TYPE.SOUNDTOUCHPLAY:사운드터치재생
	 * TYPE.MEDIAPLAYERPLAY:미디어플레이재생
	 */
	public void setType(TYPE type) {
		this.type = type;

		if (!IKaraokeTV.IS_ABOVE_JELLY_BEAN) {
			this.type = TYPE.MEDIAPLAYERPLAY;
		}

		//미디어플레이어시 음정템포 막기
		if (this.type == TYPE.MEDIAPLAYERPLAY) {
			isPitchTempo = false;
		}

		Log.e(_toString(), getMethodName() + ":" + type + ":" + isPitchTempo + ":" + song);
	}

	/**
	 * 음정/템포 사용여부
	 */
	boolean isPitchTempo = true;

	/**
	 * 음정/템포 사용여부
	 */
	public boolean isPitchTempo() {
		return ((type == TYPE.SOUNDTOUCHPLAY) & isPitchTempo);
	}

	/**
	 * 음정/템포 사용여부
	 */
	public void setIsPitchTempo(boolean isPitchTempo) {
		this.isPitchTempo = isPitchTempo;
		if (type == TYPE.MEDIAPLAYERPLAY) {
			this.isPitchTempo = false;
		}
	}

	/**
	 * 재생플레이어선택
	 * TYPE.SOUNDTOUCHPLAY:사운드터치재생
	 * TYPE.MEDIAPLAYERPLAY:미디어플레이재생
	 */
	public TYPE getType() {
		return this.type;
	}

	void setListen() {
	}

	protected _SoundTouchPlay song;

	public SongPlay getSong() {
		return song;
	}

	@Override
	public String getPath() {
		return null;
	}

	protected void init(boolean init) {
	}

	@Override
	public void start() {
		setListen();

		init(true);
	}

	@Override
	public void prepare() {

	}

	@Override
	public boolean open(String path) throws Exception {
		Log.w(_toString(), getMethodName() + "[ST]" + ":" + type + ":" + isPitchTempo + ":" + song);
		//path = "http://211.236.190.103:8080/svc_media/mmp3/78535.mp3";
		//path = "http://resource.kymedia.kr/record/kpop/20120712/89/120712BSJM92K89.m4a";
		this.path = path;

		try {
			if (type == TYPE.SOUNDTOUCHPLAY) {
				if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + path);

				reset();

				if (song == null) {
					song = new _SoundTouchPlay(getContext());
					//song.setIsRetry(false);
				}

				boolean ret = false;

				if (song != null) {
					Log.i(_toString(), getMethodName() + "[setHandler]" + song);
					song.setHandler(this.handler);
					Log.i(_toString(), getMethodName() + "[setOnListener]" + song);
					song.setOnListener(this);
					Log.i(_toString(), getMethodName() + "[open]" + song);
					ret = song.open(path);
					Log.i(_toString(), getMethodName() + "[setIsRetry]");
					setIsRetry(!song.isRetry());
				}

				return ret;

			} else {
				super.open(path);
			}
		} catch (Exception e) {

			onError(ERROR.SOUNDTOUCHPLAY, e);
			Log.e(_toString() + TAG_ERR, "[NG]" + getMethodName() + "\n" + Log.getStackTraceString(e));
			// e.printStackTrace();
			stop();
		}

		Log.w(_toString(), getMethodName() + "[ED]" + ":" + type + ":" + isPitchTempo + ":" + song);

		return true;
	}

	@Override
	public void onError(ERROR t, Exception e) {
		super.onError(t, e);
	}

	@Override
	public void onError() {
		super.onError();
	}

	/**
	 * @see _SoundTouchPlay#play()
	 */
	@Override
	public boolean play() {
		boolean ret = false;
		Log.w(_toString(), getMethodName() + "[ST]" + ":" + ret + ":" + getPlayState() + ":" + type + ":" + isPitchTempo + ":" + song);

		try {
			if (type == TYPE.SOUNDTOUCHPLAY) {
				if (song != null && song.isPrepared()) {
					ret = song.play();
				}
				if (ret) {
					setPlayState(PLAY_ENGAGE.PLAY_PLAY);
					stopTry(getMethodName());
				}
			} else {
				ret = super.play();
			}
		} catch (Exception e) {
			Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + "\n" + Log.getStackTraceString(e));
			// e.printStackTrace();
		}

		Log.w(_toString(), getMethodName() + "[ED]" + ":" + ret + ":" + getPlayState() + ":" + type + ":" + isPitchTempo + ":" + song);

		return ret;
	}

	@Override
	public void stop() {
		Log.w(_toString(), getMethodName() + "[ST]" + isPlaying() + ":" + getPlayState() + ":" + type + ":" + isPitchTempo + ":" + song);
		try {
			if (type == TYPE.SOUNDTOUCHPLAY) {
				setPlayState(PLAY_ENGAGE.PLAY_STOP);

				if (song != null/* && song.isPlaying() */) {
					if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[STOP]" + isPlaying() + ":" + getPlayState());
					song.stop();
				}

				if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[RESET]" + isPlaying() + ":" + getPlayState());
				reset();
				release();
			} else {
				super.stop();
			}
			cancel();
		} catch (Exception e) {
			//onError(ERROR.SOUNDTOUCHPLAY, e);
			Log.e(_toString() + TAG_ERR, "[NG]" + getMethodName() + "\n" + Log.getStackTraceString(e));
			//e.printStackTrace();
		}
		Log.w(_toString(), getMethodName() + "[ED]" + isPlaying() + ":" + getPlayState() + ":" + type + ":" + isPitchTempo + ":" + song);
	}

	@Override
	public void seek(int msec) {

	}

	@Override
	public void pause() {
		try {
			if (type == TYPE.SOUNDTOUCHPLAY) {
				if (song != null) {
					song.pause();
					setPlayState(PLAY_ENGAGE.PLAY_PAUSE);
				}
			} else {
				//super.pause();
			}
		} catch (Exception e) {

			onError(ERROR.SOUNDTOUCHPLAY, e);
			Log.e(_toString() + TAG_ERR, "[NG]" + getMethodName() + "\n" + Log.getStackTraceString(e));
			e.printStackTrace();
		}
	}

	@Override
	protected void resume() {
		try {
			if (type == TYPE.SOUNDTOUCHPLAY) {
				if (song != null) {
					song.play();
					setPlayState(PLAY_ENGAGE.PLAY_PLAY);
				}
			} else {
				//super.resume();
			}
		} catch (Exception e) {

			onError(ERROR.SOUNDTOUCHPLAY, e);
			Log.e(_toString() + TAG_ERR, "[NG]" + getMethodName() + "\n" + Log.getStackTraceString(e));
			e.printStackTrace();
		}
	}

	@Override
	public boolean isPlaying() {
		if (type == TYPE.SOUNDTOUCHPLAY) {
			//if (IKaraokeTV.DEBUG) Log.i(_toString() + "MediaPlayer", getMethodName() + song);
			if (song != null) {
				return song.isPlaying();
			} else {
				return false;
			}
		}
		return super.isPlaying();
	}

	public boolean isPause() {
		if (type == TYPE.SOUNDTOUCHPLAY) {
			if (song != null) {
				return song.isPause();
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean isPrepared() {
		if (type == TYPE.SOUNDTOUCHPLAY) {
			if (song != null) {
				return song.isPrepared();
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public void setOnListener(Listener listener) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + (listener instanceof Listener) + ":" + listener);
		super.setOnListener(listener);
		if (song != null) {
			song.setOnListener(this);
		}
	}

	@Override
	public void onPrepared() {
		super.onPrepared();
	}

	/**
	 * 븅신...쓰레기소스에리셋만좆나게하고지랄이네
	 */
	@Override
	protected void reset() {
		Log.w(_toString(), getMethodName());
		if (type == TYPE.SOUNDTOUCHPLAY) {
			if (song != null) {
				song.reset();
			}
		} else {
			super.reset();
		}
	}

	/**
	 * 븅신...쓰레기소스에리셋만좆나게하고지랄이네
	 */
	@Override
	public void release() {
		Log.w(_toString(), getMethodName());
		super.release();
		if (type == TYPE.SOUNDTOUCHPLAY) {
			if (song != null) {
				song.release();
			}
			song = null;
		}
	}

	@Override
	public void close() {
		Log.w(_toString(), getMethodName());
		stop();

		if (song != null) {
			song.close();
		}
	}

	@Override
	public void setHandler(Handler handler) {
		this.handler = handler;

		if (song != null) {
			song.setHandler(this.handler);
		}
	}

	@Override
	public float getMinVolume() {
		if (song != null) {
			return song.getMinVolume();
		}
		return AudioTrack.getMinVolume();
	}

	@Override
	public float getMaxVolume() {
		if (song != null) {
			return song.getMaxVolume();
		}
		return AudioTrack.getMaxVolume();
	}

	@Override
	public void setVolume(final float left, final float right) {
		try {
			if (song != null) {
				song.setVolume(left, right);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void setValance(final float LR) {
		float abs = (getMaxVolume() - getMinVolume()) / 2.0f;

		if (LR == 0) {
			abs = (getMaxVolume() - getMinVolume());
		}

		float left = abs - LR / 2.0f;
		float right = abs + LR / 2.0f;

		setVolume(left, right);
	}

	public int getTotalTime() {
		if (song != null && isPrepared()) {
			return song.getTotalTime();
		} else {
			return 0;
		}
	}

	public int getCurrentTime() {
		if (song != null && isPrepared()) {
			return song.getCurrentTime();
		} else {
			return 0;
		}
	}

	@Override
	public void restart() {
		Log.w(_toString(), getMethodName());
		if (song != null) {
			//if (song instanceof ISongPlay) {
			//	song.setPath(path);
			//	song.restart();
			//} else {
			//	stop();
			//	open(path);
			//}
			song.setPath(path);
			song.restart();
		}
	}

	@Override
	public void repeat() {
		Log.w(_toString(), getMethodName());
		if (song != null) {
			//if (song instanceof ISongPlay) {
			//	song.setPath(path);
			//	song.repeat();
			//} else {
			//	seek(0);
			//	postDelayed(new Runnable() {
			//
			//		@Override
			//		public void run() {
			//			play();
			//		}
			//	}, _SoundTouchPlay.TIME_RESTART);
			//}
			song.setPath(path);
			song.repeat();
		}
	}

	/**
	 * <pre>
	 * 피치변경(반음)
	 * Pitch -1 and +1 octave must be between -12 and 12
	 * </pre>
	 *
	 * @see _SoundTouchPlay#setPitch(int)
	 */
	@Override
	public void setPitch(int value) {
		if (song != null) {
			//Log.w(_toString(), getMethodName() + value + ":" + song);
			song.setPitch(value);
		}
	}

	@Override
	public int getPitch() {
		if (song != null) {
			return song.getPitch();
		}
		return 0;
	}

	/**
	 * <pre>
	 * 템포변경
	 * Tempo must be between 0.5 and 2.0
	 * </pre>
	 *
	 * @see _SoundTouchPlay#setTempo(float)
	 */
	@Override
	public void setTempo(float tempo) {
		if (song != null) {
			// _LOG.wtf(_toString(), getMethodName() + tempo + ":" + song);
			song.setTempo(tempo);
		}
	}

	@Override
	public float getTempo() {
		if (song != null) {
			// _LOG.wtf(_toString(), getMethodName() + song.getTempo() + ":" + song);
			return song.getTempo();
		}
		return 0;
	}

	@Override
	public void setTempoPercent(int percent) {
		if (song != null) {
			// _LOG.wtf(_toString(), getMethodName() + percent + ":" + song);
			song.setTempoPercent(percent);
		}
	}

	@Override
	public int getTempoPercent() {
		if (song != null) {
			// _LOG.wtf(_toString(), getMethodName() + song.getTempoPercent() + ":" + song);
			return song.getTempoPercent();
		}
		return 0;
	}
}
