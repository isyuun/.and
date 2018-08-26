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

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
class SingLine {
	ViewGroup layout;
	View img_focus;
	TextView txt_rank;
	TextView txt_number;
	ImageView ico_note;
	Drawable dra_note;
	int res_note = R.drawable.common_bullet_note_off;
	ImageView ico_favor;
	ImageView ico_new;
	TextView txt_title;
	TextView txt_singer;

	public SingLine(ViewGroup layout) {
		super();
		this.layout = layout;

		this.img_focus = this.layout.findViewById(R.id.img_focus);

		this.txt_rank = (TextView) this.layout.findViewById(R.id.txt_rank);

		this.txt_number = (TextView) this.layout.findViewById(R.id.txt_number);

		this.ico_note = (ImageView) this.layout.findViewById(R.id.ico_note);

		this.ico_favor = (ImageView) this.layout.findViewById(R.id.ico_favor);

		this.ico_new = (ImageView) this.layout.findViewById(R.id.ico_new);

		this.txt_title = (TextView) this.layout.findViewById(R.id.txt_title);

		this.txt_singer = (TextView) this.layout.findViewById(R.id.txt_singer);

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

	public void setImageDrawable(Drawable draNote) {
		if (draNote == this.dra_note) {
			return;
		}

		this.dra_note = draNote;

		//postDelayed(setImageDrawable, 100);
		ico_note.setImageDrawable(dra_note);
	}

	private final Runnable setImageDrawable = new Runnable() {

		@Override
		public void run() {

			ico_note.setImageDrawable(dra_note);
		}
	};

	//private class setImageDrawable extends AsyncTask<Void, Integer, Integer> {
	//
	//	@Override
	//	protected Integer doInBackground(Void... params) {
	//		post(setImageDrawable);
	//		return null;
	//	}
	//}
	//
	//private final Runnable startImageDrawable = new Runnable() {
	//
	//	@Override
	//	public void run() {
	//
	//		(new setImageDrawable()).execute();
	//	}
	//};

	public void setImageResource(int resNote) {
		if (resNote == this.res_note) {
			return;
		}

		this.res_note = resNote;

		//postDelayed(setImageResource, 100);
		ico_note.setImageResource(res_note);
	}

	private final Runnable setImageResource = new Runnable() {

		@Override
		public void run() {
			ico_note.setImageResource(res_note);
		}
	};

	//private class setImageResource extends AsyncTask<Void, Integer, Integer> {
	//
	//	@Override
	//	protected Integer doInBackground(Void... params) {
	//		post(setImageResource);
	//		return null;
	//	}
	//}
	//
	//private final Runnable startImageResource = new Runnable() {
	//
	//	@Override
	//	public void run() {
	//
	//		(new setImageResource()).execute();
	//	}
	//};
}
