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
 * filename	:	ListenPitch.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.play
 *    |_ ListenPitch.java
 * </pre>
 */
package kr.keumyoung.karaoke.play;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-01-15
 */
class ListenPitch extends Listen4X {
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

	public ListenPitch(Context context) {
		super(context);
		txt_pitch = (TextView) ((Activity) context).findViewById(R.id.txt_pitch);
		Log.wtf(_toString(), getMethodName() + ":" + txt_pitch);
	}

	@Override
	public boolean play() {
		Log.i(_toString(), getMethodName() + ":" + getPitch() + "+" + getPitchMargin());
		boolean ret = super.play();
		if (ret) {
			setPitch(PITCH_NORMAL);
		}
		return ret;
	}

	@Override
	public void stop() {
		Log.i(_toString(), getMethodName() + ":" + getPitch() + "+" + getPitchMargin());
		//setPitch(PITCH_NORMAL);
		super.stop();
	}

	@Override
	public void start() {
		super.start();
		pitch(true);
	}

	@Override
	public void prepare() {
		super.prepare();
		pitch(false);
	}

	private void clearPitch() {
	}

	// private final int[] pitchUDs = { -12,//C♭
	// -10,//D
	// -8, //E
	// -7, //F
	// -5, //G
	// -3, //A
	// -1, //B
	// 0, //C
	// 2, //D
	// 4, //E
	// 5, //F
	// 7, //G
	// 9, //A
	// 11, //B
	// 12, //C#
	// };
	private int noteIndex = 0;

	private final int[] noteUPs = {0, // C
			2, // D
			4, // E
			5, // F
			7, // G
			9, // A
			11, // B
			12, // C#
	};

	private final int[] noteDNs = {0, // C
			-1, // B
			-3, // A
			-5, // G
			-7, // F
			-8, // E
			-10,// D
			-12,// C♭
	};

	private final String[] pitchSTs = {"C4", //-12
			"C#4",//-11
			"D4", //-10
			"D#4",//-9
			"E4", //-8
			"F4", //-7
			"F#4",//-6
			"G4", //-5
			"G#4",//-4
			"A4", //-3
			"A#4",//-2
			"B4", //-1
			"C5", //0
			"C#5",//1
			"D5", //2
			"D#5",//3
			"E5", //4
			"F5", //5
			"F#5",//6
			"G5", //7
			"G#5",//8
			"A5", //9
			"A#5",//10
			"B5", //11
			"C6", //12
	};

	private String pitchSTs(int index) {
		String ret = "???";

		if (index > -1 && index < pitchSTs.length) {
			ret = pitchSTs[index];
		}

		return ret;
	}

	private void noteIndex() {
		int pitch = getPitch();
		for (int i = 0; i < noteDNs.length; i++) {
			if (pitch == noteDNs[i]) {
				noteIndex = i;
				break;
			}
		}
		for (int i = 0; i < noteUPs.length; i++) {
			if (pitch == noteUPs[i]) {
				noteIndex = i;
				break;
			}
		}
		Log.e(_toString(), getMethodName() + pitch + ":" + noteIndex);
	}

	TextView txt_pitch;

	public TextView getTxtPitch() {
		return txt_pitch;
	}

	public void setTxtPitch(TextView txt_pitch) {
		this.txt_pitch = txt_pitch;
		//setPitchText(0);
	}

	@Override
	void setListen() {
		Log.e(_toString(), getMethodName());
		super.setListen();

		txt_pitch = (TextView) ((Activity) context).findViewById(R.id.txt_pitch);
	}

	/**
	 * 절대피치(씪빠)
	 */
	final float absPITCH = pitchSTs.length / 2;
	/**
	 * 단위피치(씪빠)
	 */
	final float untPITCH = 1.0f;
	/**
	 * 절대피치(표시)
	 */
	final float dabsPITCH = pitchSTs.length / 2;
	/**
	 * 단위피치(표시)
	 */
	final float duntPITCH = 1.0f;

	/**
	 * 최대음정:바꿔서 젖되도 난몰랑
	 */
	private final static int PITCH_MAX = 12;
	/**
	 * 여자음정:바꿔서 젖되도 난몰랑
	 */
	private final static int PITCH_FEMALE = 5;
	/**
	 * 보통음정:바꿔서 젖되도 난몰랑
	 */
	private final static int PITCH_NORMAL = 0;
	/**
	 * 남자음정:바꿔서 젖되도 난몰랑
	 */
	private final static int PITCH_MALE = -5;
	/**
	 * 최소음정:바꿔서 젖되도 난몰랑
	 */
	private final static int PITCH_MIN = -12;

	@Override
	protected void init(boolean init) {
		Log.w(_toString(), getMethodName() + init);
		super.init(init);
	}

	private void pitch(boolean init) {
	}

	/**
	 * 음정:-12 to 12
	 */
	public void setPitchDN() {
		if (PITCH_MIN != getPitch()) {
			int pitch = getPitch() - 1;
			setPitch(pitch);
		}
	}

	/**
	 * 음정:-12 to 12
	 */
	public void setPitchUP() {
		if (PITCH_MAX != getPitch()) {
			int pitch = getPitch() + 1;
			setPitch(pitch);
		}
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * 음정:-12 to 12
	 */
	@Override
	public void setPitch(int pitch) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + pitch);

		pitch += PITCH_MARGIN;
		if (PITCH_MARGIN != 0) {
			Log.wtf(_toString(), getMethodName() + pitch);
		}

		super.setPitch(pitch);

		clearPitch();

		setPitchText(pitch);
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * 음정:-12 to 12
	 */
	@Override
	public int getPitch() {
		int ret = super.getPitch();
		ret -= PITCH_MARGIN;
		return ret;
	}

	/**
	 * <pre>
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * 음정:-12 to 12
	 * 이런개젖가튼자바썅나가뒤저라. String.format(...)
	 * <a href="http://stackoverflow.com/questions/6311599/why-is-androids-string-format-dog-slow">stackoverflow Why is android's String.format dog slow?</a>
	 * </pre>
	 */
	private void setPitchText(int pitch) {
		String text = String.format(/*label + */"%d(%s)", pitch - PITCH_MARGIN, pitchSTs(pitch + PITCH_MAX - PITCH_MARGIN));
		if (txt_pitch != null) {
			txt_pitch.setText(text);
		}
	}

	public void setPitchText() {
		int pitch = getPitch() + PITCH_MARGIN;
		setPitchText(pitch);
	}

	public void setPitchF() {
		setPitch(PITCH_FEMALE);
	}

	public void setPitchN() {
		setPitch(PITCH_NORMAL);
	}

	public void setPitchM() {
		setPitch(PITCH_MALE);
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * 음정:-12 to 12
	 */
	private int PITCH_MARGIN = 0;

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * 음정:-12 to 12
	 */
	public void setPitchMargin(int margin) {
		PITCH_MARGIN = margin;
	}

	/**
	 * BKO-S200 기종 녹음곡 음정/템포 오류
	 * 음정:-12 to 12
	 */
	public int getPitchMargin() {
		return PITCH_MARGIN;
	}
}
