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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV
 * filename	:	Main6.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main6.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import kr.kymedia.karaoke.play.AudioTrackPlay2;
import kr.kymedia.karaoke.play.MediaPlayerPlay2;
import kr.kymedia.karaoke.play.SongPlay;
import kr.kymedia.karaoke.play._SoundTouchPlay;
import kr.kymedia.karaoke.play.impl.ISongPlay;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.play._Listener;

/**
 * <pre>
 * SongPlay(SoundTouchPlay)사용
 * 재시도기능추가(음성:10초/영상:30초)
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @see SongPlay
 * @see AudioTrackPlay2
 * @see MediaPlayerPlay2
 * @see _SoundTouchPlay
 * @since 2015. 3. 13.
 */
class Main4 extends Main3XXXXX {
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
	void CANCEL() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.CANCEL();

		if (player != null) {
			player.cancel();
		}
		if (video != null) {
			video.cancel();
		}
		if (listen != null) {
			listen.cancel();
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.PlayView4X#setType(ISongPlay.TYPE)
	 */
	@Override
	protected void setPlayer() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		super.setPlayer();

		player.setType(ISongPlay.TYPE.SOUNDTOUCHPLAY);

		player.setOnListener(new _Listener() {

			@Override
			public void onPrepared() {

				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + player.getPath());
				onPlayerPrepared(null);
			}

			@Override
			public void onTime(int t) {
				// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + player.getPath());
				super.onTime(t);
				if (isLoading()) {
					stopLoading(getMethodName());
				}
			}

			@Override
			public void onError() {
				// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + player.getPath());
				Log.wtf(_toString() + TAG_SING, "onError() ");
				delEngageSong(true);
				CANCEL();
				stop(PLAY_ERROR);
				String msg = getString(R.string.message_error_sing) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
				ShowMessageAlert(msg);
			}

			@Override
			public void onError(ISongPlay.ERROR t, Exception e) {
				// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + e.getMessage() + "(" + t + ")" + player.getPath());
				Log.wtf(_toString() + TAG_SING, "onError() " + "(" + t + ", " + e + ")"/* + player.getPath() */ + "\n" + Log.getStackTraceString(e));
				delEngageSong(true);
				CANCEL();
				stop(PLAY_ERROR);
				String msg = getString(R.string.message_error_sing) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
				if (BuildConfig.DEBUG) {
					msg += "\n" + e.getMessage();
				} else {
					msg += "\n" + getString(R.string.message_error_commend_retry);
				}
				ShowMessageAlert(msg);
			}

			@Override
			public void onRetry(int count) {

				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + count);
				if (BuildConfig.DEBUG) Toast.makeText(getApplicationContext(), player + ":Retry - " + count, Toast.LENGTH_LONG).show();
				startLoading(getMethodName(), LOADING_LONG);
			}

			@Override
			public void onTimeout(long timeout) {

				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + timeout);
				if (BuildConfig.DEBUG) Toast.makeText(getApplicationContext(), player + ":Timeout - " + timeout, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onCompletion() {

				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
				stop(PLAY_NEXT);
				stopLoading(getMethodName());
				ShowScore();
			}

		});
	}

	@Override
	protected void setVideo() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		super.setVideo();

		video.setOnListener(new _Listener() {

			@Override
			public void onError() {
				//if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + video_url);
				Log.wtf(_toString() + TAG_SING, "onError() ");
				showBackBoard();
				CANCEL();
				stop(PLAY_ERROR);
				String msg = getString(R.string.message_error_video) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
				ShowMessageAlert(msg);
			}

			@Override
			public void onError(ISongPlay.ERROR t, Exception e) {
				//if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + e.getMessage() + "(" + t + ")" + video_url);
				Log.wtf(_toString() + TAG_SING, "onError() " + "(" + t + ", " + e + ")"/* + player.getPath() */ + "\n" + Log.getStackTraceString(e));
				e.printStackTrace();
				showBackBoard();
				CANCEL();
				stop(PLAY_ERROR);
				String msg = getString(R.string.message_error_video) + "(" + getString(R.string.message_error_title_number) + m_strRequestPlaySongID + ")";
				if (BuildConfig.DEBUG) {
					msg += "\n" + e.getMessage();
				} else {
					msg += "\n" + getString(R.string.message_error_commend_retry);
				}
				ShowMessageAlert(msg);
			}

			@Override
			public void onRetry(int count) {
				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + count);
				if (BuildConfig.DEBUG) Toast.makeText(getApplicationContext(), video + ":Retry - " + count, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onTimeout(long timeout) {
				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + timeout);
				if (BuildConfig.DEBUG) Toast.makeText(getApplicationContext(), player + ":Timeout - " + timeout, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void setListen() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		super.setListen();

		final SeekBar seekBar = (SeekBar) player.findViewById(R.id.seekBar);

		listen.setOnListener(new _Listener() {

			@Override
			public void onPrepared() {
				super.onPrepared();
				onListenPrepared(null);
				if (seekBar != null) {
					seekBar.setProgress(0);
					seekBar.setMax(listen.getTotalTime());
				}
			}

			@Override
			public void onTime(int t) {
				super.onTime(t);
				if (seekBar != null) {
					seekBar.setProgress(t);
				}
			}

			@Override
			public void onError() {
				//if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + m_strListenSongUrl);
				Log.wtf(_toString() + TAG_SING, "onError() ");
				RemoveListenDisplay();
				CANCEL();
				stop(PLAY_ERROR);
				String msg = getString(R.string.message_error_listen) + "(" + getString(R.string.message_error_title_number) + record_id + ")";
				ShowMessageAlert(msg);
			}

			@Override
			public void onError(ISongPlay.ERROR t, Exception e) {
				//if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + e.getMessage() + "(" + t + ")" + m_strListenSongUrl);
				Log.wtf(_toString() + TAG_SING, "onError() " + "(" + t + ", " + e + ")"/* + player.getPath() */ + "\n" + Log.getStackTraceString(e));
				e.printStackTrace();
				RemoveListenDisplay();
				CANCEL();
				stop(PLAY_ERROR);
				String msg = getString(R.string.message_error_listen) + "(" + getString(R.string.message_error_title_number) + record_id + ")";
				if (BuildConfig.DEBUG) {
					msg += "\n" + e.getMessage();
				} else {
					msg += "\n" + getString(R.string.message_error_commend_retry);
				}
				ShowMessageAlert(msg);
			}

			@Override
			public void onRetry(int count) {
				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + count);
				if (BuildConfig.DEBUG) Toast.makeText(getApplicationContext(), listen + ":Retry - " + count, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onTimeout(long timeout) {
				if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + timeout);
				if (BuildConfig.DEBUG) Toast.makeText(getApplicationContext(), player + ":Timeout - " + timeout, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onCompletion() {
				super.onCompletion();
				onListenCompletion(null);
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		if (player != null) {
			player.release();
		}
		if (listen != null) {
			listen.release();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
