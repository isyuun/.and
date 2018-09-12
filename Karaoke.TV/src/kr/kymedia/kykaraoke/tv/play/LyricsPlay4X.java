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
 * filename	:	PlayView4.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.play
 *    |_ PlayView4.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.play;

import android.content.Context;

import kr.kymedia.karaoke.play.AudioTrackPlay2;
import kr.kymedia.karaoke.play.MediaPlayerPlay2;
import kr.kymedia.karaoke.play.SongPlay;
import kr.kymedia.karaoke.play._SoundTouchPlay;
import kr.kymedia.kykaraoke.tv.BuildConfig;

/**
 * <pre>
 * SongPlay(SoundTouchPlay)사용
 * </pre>
 *
 * @see SongPlay
 * @see AudioTrackPlay2
 * @see MediaPlayerPlay2
 * @see _SoundTouchPlay
 * @author isyoon
 * @since 2015. 3. 12.
 * @version 1.0
 */
class LyricsPlay4X extends LyricsPlay3X {
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

	public LyricsPlay4X(Context context) {
		super(context);
	}

	private SongPlay song;

	public SongPlay getSong() {
		return song;
	}

	public void setSong(SongPlay song) {
		this.song = song;
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.LyricsPlay2#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		if (song != null) {
			return song.isPlaying();
		} else {
			return super.isPlaying();
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.LyricsPlay2#getCurrentPosition()
	 */
	@Override
	public int getCurrentPosition() {

		// return super.getCurrentPosition();
		int ret = 0;
		if (song != null) {
			ret = song.getCurrentTime();
		} else {
			ret = super.getCurrentPosition();
		}
		return ret;
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.play.LyricsPlay2#getDuration()
	 */
	@Override
	public int getDuration() {

		// return super.getDuration();
		int ret = 0;
		if (song != null) {
			ret = song.getTotalTime();
		} else {
			ret = super.getDuration();
		}
		return ret;
	}

}
