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
 * filename	:	Video2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Video2.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.play.MusicVideoView;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * <pre>
 * 스트리밍처리
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 1. 28.
 */
class Video3 extends Video2 implements OnPreparedListener, OnCompletionListener, OnErrorListener, OnInfoListener {
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

	OnCompletionListener mOnCompletionListener;

	public void setOnCompletionListener(OnCompletionListener l) {
		mOnCompletionListener = l;
	}

	public OnCompletionListener getOnCompletionListener() {
		return mOnCompletionListener;
	}

	OnErrorListener mOnErrorListener;

	public void setOnErrorListener(OnErrorListener l) {
		mOnErrorListener = l;
	}

	public OnErrorListener getOnErrorListener() {
		return mOnErrorListener;
	}

	OnInfoListener mOnInfoListener;

	public void setOnInfoListener(OnInfoListener l) {
		mOnInfoListener = l;
	}

	public OnInfoListener getOnInfoListener() {
		return mOnInfoListener;
	}

	OnPreparedListener mOnPreparedListener;

	public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
		mOnPreparedListener = l;
	}

	public OnPreparedListener getOnPreparedListener() {
		return mOnPreparedListener;
	}

	LinearLayout layoutMusicVideo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + savedInstanceState);
		super.onCreate(savedInstanceState);

		addMusicVideo();
	}

	public void hideBackBoard() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		postDelayed(new Runnable() {

			@Override
			public void run() {

				if (findViewById(R.id.layout_background_loading) != null) {
					findViewById(R.id.layout_background_loading).setVisibility(View.INVISIBLE);
				}
				// if (mMusicVideo != null) {
				// 	mMusicVideo.setVisibility(View.VISIBLE);
				// }
			}
		}, 1000);
	}

	public void showBackBoard() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		postDelayed(new Runnable() {

			@Override
			public void run() {

				if (findViewById(R.id.layout_background_loading) != null) {
					findViewById(R.id.layout_background_loading).setVisibility(View.VISIBLE);
				}
				// if (mMusicVideo != null) {
				// 	mMusicVideo.setVisibility(View.INVISIBLE);
				// }
			}
		}, 500);
	}

	/**
	 * <pre>
	 *  무반주시영상:젖같아서...안튼당~~~
	 * </pre>
	 */
	@Deprecated
	public void startBlankVideo(String url, int next) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + url + ", next:" + next);
		isMusicVideo = false;
		if (BuildConfig.DEBUG) {
			url = TEST_BG;
		}
		//startVideo(url, REDRAW);
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + url + ", next:" + next);
	}

	/**
	 * 동영상반복처리가안된다시발~~~
	 *
	 * @see android.media.MediaPlayer#setLooping(boolean)
	 */
	private String url;

	/**
	 * 동영상반복처리가안된다시발~~~
	 *
	 * @see android.media.MediaPlayer#setLooping(boolean)
	 */
	protected boolean isMusicVideo = false;

	public boolean isMusicVideo() {
		return isMusicVideo;
	}

	/**
	 *
	 */
	public void setPlayVideoUrl(String url) {
		this.url = url;
	}

	/**
	 * 반주시영상
	 */
	public void startMusicVideo(String url, final int playtype) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + url + ", playtype:" + playtype);
		isMusicVideo = true;
		startVideo(url, playtype);
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + url + ", playtype:" + playtype);
	}

	private void addMusicVideo() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + layoutMusicVideo + ":" + mMusicVideo);

		if (layoutMusicVideo == null) {
			layoutMusicVideo = (LinearLayout) findViewById(R.id.layout_background_video);
		}

		mMusicVideo = (MusicVideoView) findViewById(R.id.musicVideoView);

		if (mMusicVideo == null) {
			layoutMusicVideo.removeAllViews();
			mMusicVideo = new MusicVideoView(this);
			mMusicVideo.setId(R.id.musicVideoView);
			layoutMusicVideo.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			mMusicVideo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			layoutMusicVideo.addView(mMusicVideo);
		}
	}

	private void removeMusicVideo() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + layoutMusicVideo + ":" + mMusicVideo);

		if (mMusicVideo != null) {
			mMusicVideo.stopPlayback();
			mMusicVideo = null;
		}

		if (layoutMusicVideo != null) {
			layoutMusicVideo.removeAllViews();
		}
	}

	private void startVideo(String url) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + isMusicVideo + ":" + url);
		stop();

		mMusicVideo.setVideoPath(url);
		mMusicVideo.setVisibility(View.VISIBLE);

		this.url = url;

		setVideo();

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + isMusicVideo + ":" + url);
	}

	protected void reset() {


	}

	/**
	 * [!!!중요!!!]테스트/데모 배포시 여기만 차단하면 비디오는 작살난다.
	 * <p/>
	 * <pre>
	 * 배경영상오픈
	 * </pre>
	 */
	protected void startVideo(String url, int playtype) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + isMusicVideo + ":" + url + ", playtype:" + playtype);
		Log.i(_toString() + TAG_VIDEO, "startVideo() " + isMusicVideo + ":" + "" + ", playtype:" + playtype);

		showBackBoard();

		if (playtype != REDRAW) {
			m_bIsRedraw = false;
		} else {
			m_bIsRedraw = true;
		}

		// if (mBlankVideo != null) {
		// mBlankVideo.stopPlayback();
		// }
		//
		// // ==========================================================
		// mBlankVideo = null;
		// // mBackVideo = new BackgroundVideoView(this);
		// // ==========================================================

		addMusicVideo();

		mMusicVideo.setVideoPath(url);
		mMusicVideo.setVisibility(View.VISIBLE);

		this.url = url;

		setVideo();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + isMusicVideo + ":" + url + ", playtype:" + playtype);
	}

	private void setVideo() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());

		if (mMusicVideo != null) {
			mMusicVideo.setOnPreparedListener(Video3.this);

			mMusicVideo.setOnCompletionListener(Video3.this);

			mMusicVideo.setOnErrorListener(Video3.this);

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
				mMusicVideo.setOnInfoListener(Video3.this);
			}
		}

	}

	/**
	 * 반복처리:되는것도있고안되는것도있다.
	 */
	private boolean isLooping = false;

	/**
	 * 반복처리:되는것도있고안되는것도있다.
	 */
	public void setLooping(boolean looping) {
		this.isLooping = looping;
	}

	/**
	 * @see Main3XX#onCreate()
	 * @see Main3XX#setPlayer()
	 * @see Main3XX#setVideo()
	 * @see Main3XX#setListen()
	 * @see Main3XX#start()
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {

		if (BuildConfig.DEBUG) Log.i(_toString() + TAG_VIDEO, "onPrepared() " + "[ST]" + mp + ":" + mp.isLooping() + ":" + isMusicVideo);
		try {
			mp.setLooping(this.isLooping);
			play();
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (BuildConfig.DEBUG) Log.i(_toString() + TAG_VIDEO, "onPrepared() " + "[ED]" + mp + ":" + mp.isLooping() + ":" + isMusicVideo);
		if (mOnPreparedListener != null) {
			mOnPreparedListener.onPrepared(mp);
		}
	}

	/**
	 * @see Main3XX#onCreate()
	 * @see Main3XX#setPlayer()
	 * @see Main3XX#setVideo()
	 * @see Main3XX#setListen()
	 * @see Main3XX#start()
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {

		if (BuildConfig.DEBUG) Log.i(_toString() + TAG_VIDEO, "onCompletion() " + mp + ":" + mp.isLooping() + ":" + isMusicVideo);
		// 반복처리
		if (!mp.isLooping()) {
			// startVideo(Video3.this.url, REDRAW);
			startVideo(Video3.this.url);
		}
		if (mOnCompletionListener != null) {
			mOnCompletionListener.onCompletion(mp);
		}
	}

	/**
	 * @see Main3XX#onCreate()
	 * @see Main3XX#setPlayer()
	 * @see Main3XX#setVideo()
	 * @see Main3XX#setListen()
	 * @see Main3XX#start()
	 */
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		if (BuildConfig.DEBUG) Log.i(_toString() + TAG_VIDEO, "onError() " + mp + ":" + isMusicVideo);
		if (mOnErrorListener != null) {
			mOnErrorListener.onError(mp, what, extra);
		}
		removeMusicVideo();
		return false;
	}

	/**
	 * @see Main3XX#onCreate()
	 * @see Main3XX#setPlayer()
	 * @see Main3XX#setVideo()
	 * @see Main3XX#setListen()
	 * @see Main3XX#start()
	 */
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {

		// if (BuildConfig.DEBUG) _LOG.w(_toString() + TAG_VIDEO, "onInfo() " + mp + "(" + what + ", " + extra + ")" + ":" + isMusicVideo);
		if (mOnInfoListener != null) {
			mOnInfoListener.onInfo(mp, what, extra);
		}
		return false;
	}

	@Override
	public void stopMusicVideo(String url, int next) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + url + ", next:" + next);
		if (mMusicVideo != null) {
			mMusicVideo.setVisibility(View.INVISIBLE);
		}

		super.stopMusicVideo(url, next);

		startBlankVideo(url, next);

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + url + ", next:" + next);
	}

	@Deprecated
	@Override
	public void stopBlankVideo() {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		// if (mBlankVideo != null) {
		// mBlankVideo.setVisibility(View.INVISIBLE);
		// }
		super.stopBlankVideo();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	public void open() throws Exception {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		isMusicVideo = true;
		open(this.url);
	}

	String path;

	protected boolean open(String path) throws Exception {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + path);
		this.path = path;
		try {
			startMusicVideo(path, NEWDRAW);
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean play() throws Exception {

		try {
			if (mMusicVideo != null) {
				mMusicVideo.setVisibility(View.VISIBLE);
				mMusicVideo.start();
			}
		} catch (Exception e) {

			e.printStackTrace();
			throw (e);
		}
		return true;
	}

	public boolean stop() {

		return true;
	}

	// @Override
	// protected void onResume() {
	//
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ST]");
	// super.onResume();
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ED]");
	// }
	//
	// @Override
	// protected void onPause() {
	//
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ST]");
	// super.onPause();
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ED]");
	// }
	//
	// @Override
	// protected void onStop() {
	//
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ST]");
	// super.onStop();
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ED]");
	// }
	//
	// @Override
	// protected void onDestroy() {
	//
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ST]");
	// super.onDestroy();
	// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + "[ED]");
	// }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + newConfig);
		super.onConfigurationChanged(newConfig);
	}

}
