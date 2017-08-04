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
 * filename	:	Main4X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.gtv
 *    |_ Main4X.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.gtv;

import android.media.MediaPlayer;
import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;

import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 *  음정/템포
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-03-14
 */
class Main4X extends Main4 {
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
	 * 음정/템포 사용여부
	 */
	private boolean isPitchTempo = true;

	@Override
	protected void setPlayer() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName());

		super.setPlayer();

		//getPlayer().setType(ISongPlay.TYPE.MEDIAPLAYERPLAY);
		getPlayer().setIsPitchTempo(isPitchTempo);
	}

	@Override
	protected void setListen() {
		if (IKaraokeTV.DEBUG) Log.wtf(_toString(), getMethodName());

		super.setListen();

		//getListen().setType(ISongPlay.TYPE.MEDIAPLAYERPLAY);
		getListen().setIsPitchTempo(isPitchTempo);
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 */
	@Override
	protected void onListenPrepared(MediaPlayer mp) {

		//if (isBKOS200())
		{
			//getListen().setIsPitchTempo(false);
			getListen().setTempoPercentMargin(100);
			getListen().setPitchMargin(12);
		}

		super.onListenPrepared(mp);
	}

}
