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
import android.view.View;
import android.widget.TextView;

import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 녹음곡 음정/템포
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-01-15
 */
class Main4XXX extends Main4XX {
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
	protected void setListen() {
		super.setListen();

		listen.setTxtPitch((TextView) findViewById(R.id.txt_pitch));
		listen.setTxtTempo((TextView) findViewById(R.id.txt_tempo));

		post(showPitchTempo);
		post(hidePitchTempo);

		hidePitchTempoText();
	}

	@Override
	protected void showPitchTempo() {
		super.showPitchTempo();
		if (findViewById(R.id.layout_listen_play_sing_on) != null) {
			findViewById(R.id.layout_listen_play_sing_on).setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void hidePitchTempo() {
		super.hidePitchTempo();
		if (findViewById(R.id.layout_listen_play_sing_on) != null) {
			findViewById(R.id.layout_listen_play_sing_on).setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onListenPrepared(MediaPlayer mp) {
		super.onListenPrepared(mp);
		if (listen != null && listen.isPitchTempo()) {
			setTempoPercent(0);
			setPitch(0);
			listen.setTempoText();
			listen.setPitchText();
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
		if (!isShowMenu() && isListening()) {
			if (listen.isPitchTempo()) {
				if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + ":" + !isShowMenu() + ":" + isListening() + ":" + keyCode + ", " + event);
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
		if (isListening()) {
			setSeekPitchInfo();
			seek_pitch_tempo.setProgress(listen.getPitch());
		} else {
			super.setPitchInfo();
		}
	}

	@Override
	protected void setTempoInfo() {
		if (isListening()) {
			setSeekTempoInfo();
			seek_pitch_tempo.setProgress(listen.getTempoPercent() > 0 ? listen.getTempoPercent() / 2 : listen.getTempoPercent());
		} else {
			super.setTempoInfo();
		}
	}

	@Override
	protected void setPitchDN() {
		if (isListening()) {
			listen.setPitchDN();
			setPitchInfo();
		} else {
			super.setPitchDN();
		}
	}

	@Override
	protected void setPitchUP() {
		if (isListening()) {
			listen.setPitchUP();
			setPitchInfo();
		} else {
			super.setPitchUP();
		}
	}

	@Override
	protected void setTempoDN() {
		if (isListening()) {
			listen.setTempoDN();
			setTempoInfo();
		} else {
			super.setTempoDN();
		}
	}

	@Override
	protected void setTempoUP() {
		if (isListening()) {
			listen.setTempoUP();
			setTempoInfo();
		} else {
			super.setTempoUP();
		}
	}

	@Override
	protected void setTempoPercent(int percent) {
		if (isListening()) {
			listen.setTempoPercent(percent);
		} else {
			super.setTempoPercent(percent);
		}
	}

	@Override
	protected void setPitch(int pitch) {
		if (isListening()) {
			listen.setPitch(pitch);
		} else {
			super.setPitch(pitch);
		}
	}

	@Override
	protected void setPitchF() {
		if (isListening()) {
			listen.setPitchF();
		} else {
			super.setPitchF();
		}
	}

	@Override
	protected void setPitchM() {
		if (isListening()) {
			listen.setPitchM();
		} else {
			super.setPitchM();
		}
	}

	@Override
	protected void setPitchN() {
		if (isListening()) {
			listen.setPitchN();
		} else {
			super.setPitchN();
		}
	}

	@Override
	protected void setTempoF() {
		if (isListening()) {
			listen.setTempoF();
		} else {
			super.setTempoF();
		}
	}

	@Override
	protected void setTempoM() {
		if (isListening()) {
			listen.setTempoM();
		} else {
			super.setTempoM();
		}
	}

	@Override
	protected void setTempoN() {
		if (isListening()) {
			listen.setTempoN();
		} else {
			super.setTempoN();
		}
	}

	@Override
	protected void startLoading(String method, int time) {
		super.startLoading(method, time);

		if (null != listen && listen.isPitchTempo()) {
			switch (loading_time) {
				case LOADING_LISTEN:
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

		if (listen != null && listen.isPitchTempo()) {
			switch (loading_time) {
				case LOADING_LISTEN:
					showPitchTempo();
					break;
				default:
					break;
			}
		}
	}

}
