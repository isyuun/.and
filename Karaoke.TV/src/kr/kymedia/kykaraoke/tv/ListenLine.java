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
 * filename	:	SingLine.java
 * author	:	isyoon
 *
 * <pre
 * kr.kymedia.kykaraoke.tv
 *    |_ SingLine.java
 * </pre
 * 
 */

package kr.kymedia.kykaraoke.tv;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * <pre
 * 
 * </pre
 *
 * @author isyoon
 * @since 2015. 7. 15.
 * @version 1.0
 */
class ListenLine {
	ViewGroup layout;
	View img_focus;
	/**
	 * 프로필
	 */
	ImageView img_listen_profile;
	/**
	 * 제목 - 가수
	 */
	TextView txt_listen_title;
	/**
	 * 닉네임
	 */
	TextView txt_listen_nickname;
	/**
	 * 추천 횟수
	 */
	TextView txt_listen_recommand;
	/**
	 * 듣기 횟수
	 */
	TextView txt_listen_count;
	/**
	 * 등록일
	 */
	TextView txt_listen_day;

	public ListenLine(ViewGroup layout) {
		super();
		this.layout = layout;

		//포커스
		this.img_focus = this.layout.findViewById(R.id.img_focus);

		// 프로필
		this.img_listen_profile = (ImageView) this.layout.findViewById(R.id.img_listen_profile);

		// 제목 - 가수
		this.txt_listen_title = (TextView) this.layout.findViewById(R.id.txt_listen_title);

		// 닉네임
		this.txt_listen_nickname = (TextView) this.layout.findViewById(R.id.txt_listen_nickname);

		// 추천 횟수
		this.txt_listen_recommand = (TextView) this.layout.findViewById(R.id.txt_listen_recommand);

		// 듣기 횟수
		this.txt_listen_count = (TextView) this.layout.findViewById(R.id.txt_listen_count);

		// 등록일
		this.txt_listen_day = (TextView) this.layout.findViewById(R.id.txt_listen_day);
	}

	public void clearFocus() {
		clearFocus(this.layout);
		clearFocus(this.img_focus);
		clearFocus(this.img_listen_profile);
		clearFocus(this.txt_listen_title);
		clearFocus(this.txt_listen_nickname);
		clearFocus(this.txt_listen_recommand);
		clearFocus(this.txt_listen_count);
		clearFocus(this.txt_listen_day);
	}

	protected void clearFocus(View v) {
		if (v != null) {
			v.setSelected(false);
			v.clearFocus();
			v.setFocusable(false);
			v.setFocusableInTouchMode(false);
		}
	}

	private final Handler handler = new Handler();

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

	// public void setImageDrawable(Drawable dra_note) {
	// if (dra_note == this.dra_note) {
	// return;
	// }
	// this.dra_note = dra_note;
	// postDelayed(startImageDrawable, 100);
	// // this.ico_note.setImageDrawable(dra_note);
	// }
	//
	// public void setImageResource(int res_note) {
	// if (res_note == this.res_note) {
	// return;
	// }
	// this.res_note = res_note;
	// postDelayed(startImageResource, 100);
	// // this.ico_note.setImageResource(res_note);
	// }
	//
	// private class setImageDrawable extends AsyncTask<Void, Integer, Integer> {
	//
	// @Override
	// protected Integer doInBackground(Void... params) {
	// post(setImageDrawable);
	// return null;
	// }
	// }
	//
	// private final Runnable setImageDrawable = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// img_listen_profile.setImageDrawable(dra_note);
	// }
	// };
	//
	// private final Runnable startImageDrawable = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// (new setImageDrawable()).execute();
	// }
	// };
	//
	// private class setImageResource extends AsyncTask<Void, Integer, Integer> {
	//
	// @Override
	// protected Integer doInBackground(Void... params) {
	// post(setImageResource);
	// return null;
	// }
	// }
	//
	// private final Runnable setImageResource = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// img_listen_profile.setImageResource(res_note);
	// }
	// };
	//
	// private final Runnable startImageResource = new Runnable() {
	//
	// @Override
	// public void run() {
	//
	// (new setImageResource()).execute();
	// }
	// };

}
