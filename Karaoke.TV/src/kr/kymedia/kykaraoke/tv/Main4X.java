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
 * project	:	.prj
 * filename	:	Main4X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Main4X.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.play._PlayView;
import kr.kymedia.kykaraoke.tv.widget.CircularSeekBar;

/**
 * <pre>
 * 음정/템포
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015-11-03
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

	@Override
	protected void startSing(String song) {
		super.startSing(song);
		HideMenu(getMethodName());
	}

	@Override
	protected void ShowMenu(String method) {
		super.ShowMenu(method);
		if (isPlaying()) {
			post(hidePitchTempo);
		}
	}

	@Override
	protected void HideMenu(String method) {
		super.HideMenu(method);
		//if (isPlaying()) {
		//	post(showPitchTempo);
		//	postDelayed(hidePitchTempo, TIMER_HIDE_PITCH_TEMPO);
		//}
	}

	CircularSeekBar seek_pitch_tempo;
	View layout_pitch_tempo;
	TextView text_pitch_tempo;

	View layout_pitch;
	View layout_tempo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		layout_pitch_tempo = findViewById(R.id.layout_pitch_tempo);
		seek_pitch_tempo = (CircularSeekBar) findViewById(R.id.seek_pitch_tempo);
		text_pitch_tempo = (TextView) findViewById(R.id.text_pitch_tempo);

		layout_pitch = findViewById(R.id.layout_pitch);
		layout_tempo = findViewById(R.id.layout_tempo);

		hidePitchTempoText();

		post(hidePitchTempo);
	}

	@Override
	protected void setBaseLayout() {
		super.setBaseLayout();
	}

	@Override
	public void stopPlay(int engage) {
		super.stopPlay(engage);
		hidePitchTempoText();
	}

	private static int TIMER_HIDE_PITCH_TEMPO = 3000;

	protected void setPitchInfo() {
	}

	/**
	 * 음정:-12 to 12
	 */
	protected void setSeekPitchInfo() {
		showPitchTempo();
		String text = getString(R.string.label_pitch) + "\n";
		text += getPitchText();
		text_pitch_tempo.setText(text);
		seek_pitch_tempo.setMax(_PlayView.PITCH_MAX);
		postDelayed(hidePitchTempo, TIMER_HIDE_PITCH_TEMPO);
	}

	protected void setTempoInfo() {
	}

	/**
	 * Tempo percentage must be between -50 and 100
	 */
	protected void setSeekTempoInfo() {
		showPitchTempo();
		String text = getString(R.string.label_tempo) + "\n";
		text += getTempoText();
		text_pitch_tempo.setText(text);
		seek_pitch_tempo.setMax(_PlayView.TEMPO_MAX);
		postDelayed(hidePitchTempo, TIMER_HIDE_PITCH_TEMPO);
	}

	private void togglePitchTempo() {
		if (!isLoading()) {
			if (layout_pitch_tempo != null) {
				if (layout_pitch_tempo.getVisibility() != View.VISIBLE) {
					post(showPitchTempo);
				} else {
					post(hidePitchTempo);
				}
			}
			postDelayed(hidePitchTempo, TIMER_HIDE_PITCH_TEMPO);
		}
	}

	protected Runnable togglePitchTempo = new Runnable() {
		@Override
		public void run() {
			togglePitchTempo();
		}
	};

	protected void showPitchTempo() {
		if (layout_pitch_tempo != null) {
			layout_pitch_tempo.setVisibility(View.VISIBLE);
		}
		String text = getString(R.string.label_pitch) + "\n" + getString(R.string.label_tempo);
		text_pitch_tempo.setText(text);
		seek_pitch_tempo.setProgress(0);
	}

	protected Runnable showPitchTempo = new Runnable() {
		@Override
		public void run() {
			showPitchTempo();
		}
	};

	protected void hidePitchTempo() {
		if (layout_pitch_tempo != null) {
			layout_pitch_tempo.setVisibility(View.INVISIBLE);
		}
		String text = getString(R.string.label_pitch) + "\n" + getString(R.string.label_tempo);
		text_pitch_tempo.setText(text);
		seek_pitch_tempo.setProgress(0);
	}

	protected Runnable hidePitchTempo = new Runnable() {
		@Override
		public void run() {
			hidePitchTempo();
		}
	};

	protected void showPitchTempoText() {
		if (layout_pitch != null) {
			layout_pitch.setVisibility(View.VISIBLE);
		}
		if (layout_tempo != null) {
			layout_tempo.setVisibility(View.VISIBLE);
		}
	}

	protected void hidePitchTempoText() {
		//반주곡/녹음곡 재생시에는 보이도록한다.
		if (!isPlaying() && !isListening()) {
			if (layout_pitch != null) {
				layout_pitch.setVisibility(View.INVISIBLE);
			}
			if (layout_tempo != null) {
				layout_tempo.setVisibility(View.INVISIBLE);
			}
		}
	}

	public String getPitchText() {
		return ((TextView) findViewById(R.id.txt_pitch)).getText().toString();
	}

	public String getTempoText() {
		return ((TextView) findViewById(R.id.txt_tempo)).getText().toString();
	}

	protected boolean isShowPitchTempo() {
		boolean ret = false;
		if (layout_pitch_tempo != null && layout_pitch_tempo.getVisibility() == View.VISIBLE) {
			ret = true;
		}
		return ret;
	}

	protected void setPitchDN() {
	}

	protected void setPitchUP() {
	}

	protected void setTempoDN() {
	}

	protected void setTempoUP() {
	}

	protected void setTempoPercent(int percent) {
	}

	protected void setPitch(final int pitch) {
	}

	protected void setPitchF() {
	}

	protected void setPitchM() {
	}

	protected void setPitchN() {
	}

	protected void setTempoF() {
	}

	protected void setTempoM() {
	}

	protected void setTempoN() {
	}

	protected void startLoadingPitchTempo() {
		hideLoadingPopup();
		post(showPitchTempo);
		seek_pitch_tempo.startLoading();
	}

	protected void stopLoadingPitchTempo() {
		hideLoadingPopup();
		post(hidePitchTempo);
		seek_pitch_tempo.stopLoading();
	}

	@Override
	protected void startLoading(String method, int time) {
		super.startLoading(method, time);

		showLoadingPopup();

		switch (loading_time) {
			case LOADING_SING:
			case LOADING_LISTEN:
				HideMenu(getMethodName());
				break;
		}

		hidePitchTempoText();
	}

	@Override
	protected void stopLoading(String method) {
		super.stopLoading(method);

		stopLoadingPitchTempo();
	}

	@Override
	protected void stop(int engage) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.stop(engage);

		hidePitchTempoText();
	}

	@Override
	protected void stopListen() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		super.stopListen();
		hidePitchTempoText();
	}
}
