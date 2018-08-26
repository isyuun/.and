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
 * filename	:	PlayView3.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.play
 *    |_ PlayView3.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.play;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.api._Const;

/**
 * <pre>
 * 추가 {@link LyricsPlayThread}
 * 추가 {@link LyricsPlayThreadX}
 * </pre>
 *
 * @author isyoon
 * @since 2015. 2. 5.
 * @version 1.0
 */
class LyricsPlay3 extends LyricsPlay2 {
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

	public LyricsPlay3(Context context) {
		super(context);
	}

	@Override
	protected void init() {

		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName());
		super.init();

		// bgkim 각각의 폰트 사이즈는 비율로 조절
		// int h = getWindowManager().getDefaultDisplay().getHeight();
		// int w = getWindowManager().getDefaultDisplay().getWidth();
		int h = 0;
		int w = 0;

		Display display = getWindowManager().getDefaultDisplay();
		Point displaySize = new Point();

		// if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			display.getRealSize(displaySize);
			w = displaySize.x;
			h = displaySize.y;
		}

		Log.e(_toString() + _Const.TAG_LYRIC, "init() " + w + "," + h + ":" + displaySize);

		int iSongInfoPosition = w / 2;
		setSongInfoPosition(iSongInfoPosition);
		int iTitleFontSize = h / 13;
		setTitleFontSize(iTitleFontSize);
		int iLyricsFontSize = h / 12;
		setLyricsFontSize(iLyricsFontSize);
		int iSingerFontSize = h / 14;
		setSingerFontSize(iSingerFontSize);
		int iReadyFontSize = h / 18;
		setReadyFontSize(iReadyFontSize);
		setStrokeSize(4);
	}

}
