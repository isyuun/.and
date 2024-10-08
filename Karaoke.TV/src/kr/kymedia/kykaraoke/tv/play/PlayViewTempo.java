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
 * filename	:	PlayViewTempo.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.play
 *    |_ PlayViewTempo.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.play;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.R;

/**
 * <pre>
 * 템포처리
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015-10-07
 */
class PlayViewTempo extends PlayViewPitch {
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

	public PlayViewTempo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public PlayViewTempo(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PlayViewTempo(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PlayViewTempo(Context context) {
		super(context);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	public boolean play() {
		boolean ret = super.play();
		//if (ret) {
		//	setTempoPercent(TEMPO_NORMAL);
		//}
		Log.d(_toString(), getMethodName() + ":" + getTempo());
		return ret;
	}

	@Override
	public void stop() {
		setTempoPercent(TEMPO_NORMAL);
		Log.d(_toString(), getMethodName() + ":" + getTempo());
		super.stop();
	}

	@Override
	public void start() {

		super.start();
		tempo(true);
	}

	@Override
	public void prepare() {

		super.prepare();
		tempo(false);
	}

	private void clearTempo() {


	}

	@Override
	public float getTempo() {
		return super.getTempo();
	}

	TextView txt_tempo;

	public TextView getTxtTempo() {
		return txt_tempo;
	}

	public void setTxtTempo(TextView txt_tempo) {
		this.txt_tempo = txt_tempo;
		//setTempoText(TEMPO_NORMAL);
	}

	@Override
	protected void setPlayView() {

		Log.e(_toString(), getMethodName());
		super.setPlayView();

		txt_tempo = (TextView) findViewById(R.id.txt_tempo);
	}

	/**
	 * 절대템포(씪빠)
	 */
	final float absTEMPO = 50.0f;
	/**
	 * 단위템포(씪빠)
	 */
	final float untTEMPO = 5.0f;
	/**
	 * 절대템포(표시)
	 */
	final float dabsTEMPO = 2.0f;
	/**
	 * 단위템포(표시)
	 */
	final float duntTEMPO = 0.1f;

	public static int TEMPO_MAX = 50;
	public static int TEMPO_FAST = 10;
	public static int TEMPO_NORMAL = 0;
	public static int TEMPO_SLOW = -5;
	public static int TEMPO_MIN = -50;

	@Override
	protected void init(boolean init) {

		Log.w(_toString(), getMethodName() + init);
		super.init(init);
	}

	private void tempo(boolean init) {
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * Tempo percentage must be between -50 and 100 : 젖같네시팔
	 */
	public void setTempoDN() {
		int percent = getTempoPercent();
		if (TEMPO_MIN < percent) {
			percent -= (int) untTEMPO;
			//Tempo percentage must be between -50 and 100
			if (percent > 0) {
				percent -= untTEMPO;
			}
			//_LOG.wtf(_toString(), getMethodName() + this.percent + "->" + percent);
			setTempoPercent(percent);
		}
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * Tempo percentage must be between -50 and 100 : 젖같네시팔
	 */
	public void setTempoUP() {
		int percent = getTempoPercent();
		if (TEMPO_MAX * 2 > percent) {
			percent += (int) untTEMPO;
			//Tempo percentage must be between -50 and 100
			if (percent > 0) {
				percent += untTEMPO;
			}
			Log.wtf(_toString(), getMethodName() + getTempo() + ":" + getTempoPercent() + "->" + percent);
			setTempoPercent(percent);
		}
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * Tempo percentage must be between -50 and 100 : 젖같네시팔
	 */
	@Override
	public void setTempoPercent(final int percent) {
		///_LOG.e(_toString(), getMethodName() + percent);

		super.setTempoPercent(percent);

		clearTempo();

		//handler.post(new Runnable() {
		//
		//	@Override
		//	public void run() {
		//		setTempoText(percent);
		//	}
		//});
		setTempoText(percent);

	}

	/**
	 * <pre>
	 * 이런개젖가튼자바썅나가뒤저라. String.format(...)
	 * <a href="http://stackoverflow.com/questions/6311599/why-is-androids-string-format-dog-slow">stackoverflow Why is android's String.format dog slow?</a>
	 * </pre>
	 */
	private void setTempoText(float percent) {
		String text = String.format(/*label + */"%.1f"/* + "(" + percent + ")"*/, percent > 0 ? ((float) percent) / 100.0f : ((float) percent) / 50.0f);
		if (txt_tempo != null) {
			txt_tempo.setText(text);
		}

		// _LOG.e(_toString(), getMethodName() + "balance:" + balance + " - " + min + "~" + max);

		//setInfo();
	}

	public void setTempoText() {
		int percent = getTempoPercent();
		setTempoText(percent);
	}

	public void setTempoF() {
		setTempoPercent(getTempoPercent() + TEMPO_FAST);
	}

	public void setTempoN() {
		setTempoPercent(TEMPO_NORMAL);
	}

	public void setTempoM() {
		setTempoPercent(getTempoPercent() + TEMPO_SLOW);
	}

}
