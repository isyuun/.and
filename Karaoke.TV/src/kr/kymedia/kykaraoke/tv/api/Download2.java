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
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	Download2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.api
 *    |_ Download2.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import kr.kymedia.karaoke.api.LyricsUtil;
import kr.kymedia.karaoke.play.impl.ISongPlay.ERROR;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.api._Download.onDownloadListener;
import kr.kymedia.kykaraoke.tv.BuildConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 9. 25.
 * @version 1.0
 */
class Download2 extends Download implements _Const {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	String mMp3;

	public void setMp3(String mp3) {
		mMp3 = mp3;
	}

	String mLyc, mFileName;
	int mType;
	Handler handler;
	String newPath;

	public String getNewPath() {
		return newPath;
	}

	public void setNewPath(String path) {
		this.newPath = path;
	}

	@Override
	public void setType(int type) {
		mType = type;
	}

	public void setLyc(String lyc) {
		mLyc = lyc;
	}

	@Override
	public void setFileName(String fileName) {
		mFileName = fileName;
	}

	public Download2(Handler h) {
		super(h);
		// TODO Auto-generated constructor stub
		handler = h;
	}

	@Override
	public void sendMessage(int state) {
		Bundle b = new Bundle();
		b.putInt("state", state);

		Message msg = handler.obtainMessage();
		msg.setData(b);
		handler.sendMessage(msg);
	}

	protected void removeCallbacks(Runnable r) {
		if (handler != null) {
			handler.removeCallbacks(r);
		}
	}

	protected void post(Runnable r) {
		removeCallbacks(r);
		if (handler != null) {
			handler.post(r);
		}
	}

	protected void postDelayed(Runnable r, long delayMillis) {
		removeCallbacks(r);
		if (handler != null) {
			handler.postDelayed(r, delayMillis);
		}
	}

	private onDownloadListener listener;

	public onDownloadListener getListener() {
		return listener;
	}

	public void setListener(onDownloadListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {

		final String url = mLyc;
		final String path = newPath + File.separator + mFileName;
		final int type = mType;

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + type + ":" + url + "->" + path);

		// super.run();
		try {

			if (TextUtil.isNetworkUrl(mMp3)) {
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "lyric.down() " + "[ST]" + type + ":" + url + "->" + path);
				LyricsUtil.down(url, path);
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "lyric.down() " + "[ED]" + type + ":" + url + "->" + path);
			} else {
				down();
			}

			if (interrupted()) {
				File file = new File(path);
				if (file != null && file.exists()) {
					file.delete();
				}
			} else {
				switch (type) {
				case REQUEST_FILE_ARTIST_IMAGE:
					if (IKaraokeTV.DEBUG) Log.i(_toString(), "_COMPLETE_ARTIST_IMAGE");
					sendMessage(COMPLETE_DOWN_ARTIST_IMAGE);
					break;
				case REQUEST_FILE_SONG:
					if (IKaraokeTV.DEBUG) Log.i(_toString(), "_COMPLETE_SONG");
					sendMessage(COMPLETE_DOWN_SONG);
					break;
				case REQUEST_FILE_LISTEN:
					if (IKaraokeTV.DEBUG) Log.i(_toString(), "_COMPLETE_LISTEN");
					sendMessage(COMPLETE_DOWN_LISTEN);
					break;
				case REQUEST_FILE_LISTEN_OTHER:
					if (IKaraokeTV.DEBUG) Log.i(_toString(), "_COMPLETE_LISTEN_OTHER_DOWN");
					sendMessage(COMPLETE_DOWN_LISTEN_OTHER);
					break;
				}
			}

		} catch (final Exception e) {
			if (IKaraokeTV.DEBUG) Log.w(_toString() + TAG_ERR,  "[NG]" + getMethodName() + type + ":" + url + "->" + path);

			e.printStackTrace();
			post(new Runnable() {
				@Override
				public void run() {
					if (listener != null) {
						listener.onDownError(ERROR.OPENING, e);
					}
				}
			});
			return;
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + type + ":" + url + "->" + path);
	}

	public void down() {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + mLyc);

		// int fileType = mType;

		String sdpath = newPath;

		HttpClient downClient = new DefaultHttpClient();
		HttpGet testHttpGet = new HttpGet(mLyc);
		if (IKaraokeTV.DEBUG) Log.i(_toString(), "httpget mUrl = " + mLyc);

		try {
			HttpResponse testResponse = downClient.execute(testHttpGet);
			if (IKaraokeTV.DEBUG) Log.i(_toString(), "excute");

			HttpEntity downEntity = testResponse.getEntity();
			if (downEntity != null) {
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "entity not null");

				int BUFFER_SIZE = 1024 * 10;
				byte[] buffer = new byte[BUFFER_SIZE];

				InputStream testInputStream = null;
				testInputStream = downEntity.getContent();
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "getcontent");
				BufferedInputStream testInputBuf = new BufferedInputStream(testInputStream, BUFFER_SIZE);

				File file = null;
				file = new File(sdpath + File.separator + mFileName);
				file.createNewFile();
				if (IKaraokeTV.DEBUG) Log.i(_toString(), "createnewfile");
				FileOutputStream testFileOutputStream = new FileOutputStream(file, false);
				BufferedOutputStream testOutputBuf = new BufferedOutputStream(testFileOutputStream, BUFFER_SIZE);

				int readSize = -1;

				while ((readSize = testInputBuf.read(buffer)) != -1) {
					// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "readSize = " + String.valueOf(readSize));
					testOutputBuf.write(buffer, 0, readSize);
				}

				// switch (fileType) {
				// case FILE_ARTIST_IMAGE:
				// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "_COMPLETE_ARTIST_IMAGE");
				// sendMessage(_COMPLETE_ARTIST_IMAGE);
				// break;
				// case FILE_SONG:
				// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "_COMPLETE_SONG");
				// sendMessage(_COMPLETE_SONG);
				// break;
				// case FILE_LISTEN:
				// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "_COMPLETE_LISTEN");
				// sendMessage(_COMPLETE_LISTEN);
				// break;
				// case FILE_LISTEN_OTHER:
				// if (IKaraokeTV.DEBUG) _LOG.i(_toString(), "_COMPLETE_LISTEN_OTHER_DOWN");
				// sendMessage(_COMPLETE_LISTEN_OTHER_DOWN);
				// break;
				// }

				if (IKaraokeTV.DEBUG) Log.i(_toString(), "write end");
				testOutputBuf.flush();

				testInputBuf.close();
				testFileOutputStream.close();
				testOutputBuf.close();
			}
		} catch (Exception e) {
			testHttpGet.abort();
			if (IKaraokeTV.DEBUG) Log.i(_toString(), "execute fail");
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + mLyc);
	}

}
