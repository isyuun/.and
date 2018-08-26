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
 * filename	:	Video4.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Video4.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv;

import java.util.Timer;
import java.util.TimerTask;

import kr.kymedia.karaoke.play.impl.ISongPlay;
import kr.kymedia.karaoke.play.impl.ISongPlay.Listener;
import kr.kymedia.kykaraoke.tv.api._Const;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.os.Handler;
import android.util.Log;

/**
 * <pre>
 * 재시도기능추가(30초/3회)
 * </pre>
 *
 * @author isyoon
 * @since 2015. 4. 3.
 * @version 1.0
 */
class Video4 extends Video3 implements ISongPlay.Listener {
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
	 * 동영상재시작(시간:10초)
	 */
	protected int TIMER_RETRY = _Const.TIMER_MP4_RETRY;
	/**
	 * 동영상재시작(횟수:3회)
	 */
	protected int COUNT_RETRY = _Const.COUNT_MP4_RETRY;

	final Handler handler = new Handler();

	Timer retryTimer;

	private void startRetry() {
		retryTimer = new Timer();
		RetryTask retryTask = new RetryTask();
		retryTimer.schedule(retryTask, TIMER_RETRY, TIMER_RETRY);
	}

	class RetryTask extends TimerTask {
		@Override
		public void run() {
			// sendMessage(COMPLETE_TIMER_HIDE_MESSAGE_COMMON);
			handler.removeCallbacks(retry);
			handler.post(retry);
		}
	}

	private final Runnable retry = new Runnable() {

		@Override
		public void run() {

			retry();
		}
	};

	int count = 0;

	private void retry() {
		Log.e(_toString(), "retry() " + count);
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ST]" + count);
		try {
			if (count < COUNT_RETRY && !isPlaying()) {
				stop();
				open(path);
				count++;
				onRetry(count);
				onTimeout(TIMER_RETRY);
			} else {
				if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[RO]" + count);
				onError(ISongPlay.ERROR.TRYOUT, new Exception("RETRY OUT ERROR(" + count + ")"));
				stop();
				cancel();
			}
		} catch (Exception e) {

			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName() + count);
			e.printStackTrace();
			stop();
			cancel();
		}
		// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[ED]" + count);
	}

	public void cancel() {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + count);
		try {
			if (retryTimer != null) {
				retryTimer.cancel();
				retryTimer.purge();
				retryTimer = null;
			}
			count = 0;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void open() throws Exception {
		Log.e(_toString(), "open() " + count);

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.open();
		cancel();
		startRetry();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	public boolean play() throws Exception {
		Log.i(_toString(), "play() " + count);

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		boolean ret = false;
		try {
			ret = super.play();
			cancel();
		} catch (Exception e) {

			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName());
			e.printStackTrace();
			onError(ISongPlay.ERROR.MEDIAPLAYERPLAY, e);
		}
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
		return ret;
	}

	@Override
	public boolean stop() {

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		boolean ret = super.stop();
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
		return ret;
	}

	ISongPlay.Listener listener;

	public void setOnListener(Listener listener) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + (listener instanceof Listener) + ":" + listener);

		this.listener = listener;
	}

	@Override
	public void onTime(int t) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + t);

		if (listener != null) {
			listener.onTime(t);
		}
	}

	@Override
	public void onPrepared() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		if (listener != null) {
			listener.onPrepared();
		}
	}

	@Override
	public void onCompletion() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		if (listener != null) {
			listener.onCompletion();
		}
	}

	@Override
	public void onError() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		if (listener != null) {
			listener.onError();
		}
	}

	@Override
	public void onBufferingUpdate(int percent) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + percent);
		if (listener != null) {
			listener.onBufferingUpdate(percent);
		}
	}

	@Override
	public void onRelease() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		if (listener != null) {
			listener.onRelease();
		}
	}

	@Override
	public void onSeekComplete() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		if (listener != null) {
			listener.onSeekComplete();
		}
	}

	@Override
	public void onReady(int count) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + count);
		if (listener != null) {
			listener.onReady(count);
		}
	}

	@Override
	public void onError(ISongPlay.ERROR t, Exception e) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + t + ":" + e);
		if (listener != null) {
			listener.onError(t, e);
		}
	}

	@Override
	public void onRetry(int count) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + count);
		if (listener != null) {
			listener.onRetry(count);
		}
	}

	@Override
	public void onTimeout(long timeout) {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + timeout);
		if (listener != null) {
			listener.onTimeout(timeout);
		}
	}

}
