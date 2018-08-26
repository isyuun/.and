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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	Main4XXX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main4XXX.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 반주곡 음정/템포
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-01-15
 */
class Main4XX extends Main4X {
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
	protected void setPlayer() {
		super.setPlayer();

		player.setTxtPitch((TextView) findViewById(R.id.txt_pitch));
		player.setTxtTempo((TextView) findViewById(R.id.txt_tempo));

		post(showPitchTempo);
		post(hidePitchTempo);

		hidePitchTempoText();
	}

	@Override
	protected void onPlayerPrepared(MediaPlayer mp) {
		super.onPlayerPrepared(mp);
		if (player != null && player.isPitchTempo()) {
			setTempoPercent(0);
			setPitch(0);
			player.setTempoText();
			player.setPitchText();
			showPitchTempoText();
		}
	}

	/**
	 * <pre>
	 *   음정/템포
	 * </pre>
	 *
	 * @see Main2#onKeyDown(int, android.view.KeyEvent)
	 * @see Main2XXXXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main3XXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main4X#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6X#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!isShowMenu() && isPlaying()) {
			if (player.isPitchTempo()) {
				if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + isShowMenu() + ":" + isPlaying() + ":" + keyCode + ", " + event);
				switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_DOWN:
						setPitchDN();
						break;
					case KeyEvent.KEYCODE_DPAD_UP:
						setPitchUP();
						break;
					case KeyEvent.KEYCODE_DPAD_LEFT:
						setTempoDN();
						break;
					case KeyEvent.KEYCODE_DPAD_RIGHT:
						setTempoUP();
						break;
					case KeyEvent.KEYCODE_DPAD_CENTER:
					case KeyEvent.KEYCODE_ENTER:
						post(togglePitchTempo);
						break;
					case KeyEvent.KEYCODE_BACK:
					case KeyEvent.KEYCODE_ESCAPE:
						post(hidePitchTempo);
						if (isShowPitchTempo()) {
							return true;
						}
						break;
				}
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void setPitchInfo() {
		if (isPlaying()) {
			setSeekPitchInfo();
			seek_pitch_tempo.setProgress(player.getPitch());
		}
	}

	@Override
	protected void setTempoInfo() {
		if (isPlaying()) {
			setSeekTempoInfo();
			seek_pitch_tempo.setProgress(player.getTempoPercent() > 0 ? player.getTempoPercent() / 2 : player.getTempoPercent());
		}
	}

	@Override
	protected void setPitchDN() {
		if (isPlaying()) {
			player.setPitchDN();
			setPitchInfo();
		}
	}

	@Override
	protected void setPitchUP() {
		if (isPlaying()) {
			player.setPitchUP();
			setPitchInfo();
		}
	}

	@Override
	protected void setTempoDN() {
		if (isPlaying()) {
			player.setTempoDN();
			setTempoInfo();
		}
	}

	@Override
	protected void setTempoUP() {
		if (isPlaying()) {
			player.setTempoUP();
			setTempoInfo();
		}
	}

	@Override
	protected void setTempoPercent(int percent) {
		if (isPlaying()) {
			player.setTempoPercent(percent);
		}
	}

	@Override
	protected void setPitch(int pitch) {
		if (isPlaying()) {
			player.setPitch(pitch);
		}
	}

	@Override
	protected void setPitchF() {
		if (isPlaying()) {
			player.setPitchF();
		}
	}

	@Override
	protected void setPitchM() {
		if (isPlaying()) {
			player.setPitchM();
		}
	}

	@Override
	protected void setPitchN() {
		if (isPlaying()) {
			player.setPitchN();
		}
	}

	@Override
	protected void setTempoF() {
		if (isPlaying()) {
			player.setTempoF();
		}
	}

	@Override
	protected void setTempoM() {
		if (isPlaying()) {
			player.setTempoM();
		}
	}

	@Override
	protected void setTempoN() {
		if (isPlaying()) {
			player.setTempoN();
		}
	}

	@Override
	protected void startLoading(String method, int time) {
		super.startLoading(method, time);

		if (null != player && player.isPitchTempo()) {
			switch (loading_time) {
				case LOADING_SING:
					startLoadingPitchTempo();
					break;
				default:
					break;
			}
		}
	}

	@Override
	protected void stopLoading(String method) {
		super.stopLoading(method);

		if (player != null && player.isPitchTempo()) {
			switch (loading_time) {
				case LOADING_SING:
					showPitchTempo();
					break;
				default:
					break;
			}
		}
	}

}
