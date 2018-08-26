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
 * 2012 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	SingFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ SingFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.play3.Player;
import kr.kymedia.karaoke.util.TextUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * TODO NOTE:<br>
 * 녹화기능(보류)
 * 
 * @author isyoon
 * @since 2012. 2. 28.
 * @version 1.0
 */
@Deprecated
class playSingFragment2 extends playFragment2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_sing2, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		mPlayType = Player.TYPE.SING;

		super.onActivityCreated();

		findViewById(R.id.scr_song_read).setVisibility(View.GONE);
		findViewById(R.id.txt_song_read).setVisibility(View.GONE);
		((TextView) findViewById(R.id.btn_song_read))
				.setText("▼ " + getString(R.string.btn_title_read));
		findViewById(R.id.btn_song_read).setVisibility(View.GONE);

	}

	/**
	 * onPause 플래그초기화 isCloseOnPause = true isPauseOnPause = true
	 * 
	 * @see isCloseOnPause
	 * @see isPauseOnPause
	 */
	@Override
	protected void resetOnPause() {

		super.resetOnPause();
		// isCloseOnPause = true;
		// isPauseOnPause = true;
		if (getPlayer() != null && getPlayer().isPlaying()) {
			isOnPauseClose = false;
			isOnPausePause = false;
		} else {
			isOnPauseClose = true;
			isOnPausePause = true;
		}
		isOnPauseClose = false;
		isOnPausePause = false;
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + isOnPauseClose + ", " + isOnPausePause);
	}

	String audition_id;

	@Override
	protected void start() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.start();

		KPItem info = getInfo();
		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (info != null) {
			p_type = info.getValue("type");
		}

		if (TextUtil.isEmpty(p_type)) {
			p_type = list.getValue("type");
		}

		play_id = list.getValue("song_id");
		// play_id = list.getString("play_id");

		audition_id = list.getValue("audition_id");
		if (TextUtil.isEmpty(audition_id)) {
			audition_id = getList().getValue("id");
		}

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_play.p_opcode = "KP_1016";

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		super.KP_nnnn();
		if (KP_play == null) {
			return;
		}

		KP_play.KP_1016(getApp().p_mid, p_m1, p_m2, play_id, audition_id);

	}

	/**
	 * 
	 * @see PlayFragment#KPnnnn()
	 */
	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_play != null ? KP_play.p_opcode : "");

		super.KPnnnn();

		KPItem list = KP_play.getList(0);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		View v = null;
		try {
			String gasa = list.getValue("msg_" + getApp().mLocale.getCountry().toUpperCase());
			v = findViewById(R.id.btn_song_read);
			if (gasa != null && !TextUtil.isEmpty(gasa) && !gasa.equalsIgnoreCase("N")) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}
			findViewById(R.id.btn_song_read).setVisibility(View.GONE);

			v = findViewById(R.id.txt_song_read);
			((TextView) v).setText(gasa);
			((TextView) v).setGravity(Gravity.CENTER);
			((TextView) v).setTextAppearance(mContext, R.style.TextXLargeWhite);
		} catch (Exception e) {

			e.printStackTrace();
		}

		v = findViewById(R.id.include_player_title).findViewById(R.id.id);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.include_player_title).findViewById(R.id.heart);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.include_player_title).findViewById(R.id.arrow);
		if (v != null) {
			v.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		if (v.getId() == R.id.txt_song_read || v.getId() == R.id.btn_song_read) {
			// 독음
			if (findViewById(R.id.scr_song_read).getVisibility() == View.GONE) {
				findViewById(R.id.txt_song_read).setVisibility(View.VISIBLE);
				findViewById(R.id.scr_song_read).setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.btn_song_read)).setText("▲ "
						+ getString(R.string.btn_title_read));
			} else {
				findViewById(R.id.txt_song_read).setVisibility(View.GONE);
				findViewById(R.id.scr_song_read).setVisibility(View.GONE);
				((TextView) findViewById(R.id.btn_song_read)).setText("▼ "
						+ getString(R.string.btn_title_read));
			}
		} else {
		}

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
	}

	/**
	 * 공유창이오픈한면 업로드완료로 간주 이전화면(녹음곡리스트)이 업데이트되도록한다.
	 * 
	 * @see PlayFragment#openACTIONSHARE(kr.kymedia.karaoke.kpop.data.KPItem, kr.kymedia.karaoke.kpop.data.KPItem)
	 */
	@Override
	public Intent openACTIONSHARE(KPItem info, KPItem list, boolean open, String packageName, String className) {

		Intent ret = super.openACTIONSHARE(info, list, open, packageName, className);
		setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
		return ret;
	}

	@Override
	protected void openFeelPost(String mode, KPnnnn KP_xxxx, int index) {

		if (KP_xxxx == null) {
			KP_xxxx = KP_play;
		}

		KPItem info = KP_xxxx.getInfo();
		KPItem list = KP_xxxx.getList(KP_index);

		// FEEL 등록인 경우 FEEL등록
		// 화면 설정 값
		// K : 반주곡 화면에서 등록
		// R : 녹음곡재생 화면에서 등록
		// T : 반주곡/녹음곡 및 오디션
		// 화면을 제외한 화면에서 등록
		// A : 오디션 화면에서 등록
		list.putValue("feel_type", "K");

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		isOnPausePause = false;
		isOnPauseClose = false;

		super.openFeelPost(mode, KP_xxxx, index);
	}

}
