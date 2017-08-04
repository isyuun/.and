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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	ListenFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.LEVEL;
import kr.kymedia.karaoke.api.KPnnnn.MEDIAERROR.TYPE;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.play3.Player;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 2. 28.
 * @version 1.0
 * @see playFragment.java
 */

class playListenFragment extends playFragment implements View.OnKeyListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	// protected int total_page = 0;
	// protected int page = 1;

	// ViewSwitcher mViewSwitcher = null;
	// protected Timer mTickViewSwitcher = null;
	// protected Timer mTickComments = null;
	/**
	 * KP_2014 하트쏘기
	 */
	KPnnnn KP_2014 = null;
	/**
	 * KP_2015 녹음곡 댓글입력&수정
	 */
	KPnnnn KP_2015 = null;

	String reply_mode = "ADD";
	String reply_ment = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_listen, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		mPlayType = Player.TYPE.LISTEN;

		super.onActivityCreated();

		View v = null;

		v = findViewById(R.id.include_player_reply);
		if (v != null) {
			v.setVisibility(View.GONE);
		}
		v = findViewById(R.id.txt_reply_heart);
		if (v != null) {
			((TextView) v).setText("0");
		}

		try {
			mViewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (mViewSwitcher != null) {
			mViewSwitcher.setInAnimation(getBaseActivity(), R.anim.slide_right_in);
			mViewSwitcher.setOutAnimation(getBaseActivity(), R.anim.slide_left_out);
		}

		// 국가별SNS기능차단
		v = findViewById(R.id.btn_player_menu_sns);
		if (v != null && ("CN").equalsIgnoreCase(getApp().mLocale.getCountry())) {
			v.setVisibility(View.GONE);
		}

		getView().setOnKeyListener(this);

		showPlayer(true);
		hideShowButton();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return false;
	}

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

		p_type = "RECORD";
		p_type = !TextUtil.isEmpty(info.getValue("type")) ? info.getValue("type") : p_type;
		p_type = !TextUtil.isEmpty(list.getValue("type")) ? list.getValue("type") : p_type;

		play_id = list.getValue("record_id");
		if (p_type != null) {
			if (("RECORD").equalsIgnoreCase(p_type)) {
				play_id = list.getValue("record_id");
			} else if (("UID").equalsIgnoreCase(p_type)) {
				play_id = list.getValue("uid");
			} else if (("SONG").equalsIgnoreCase(p_type)) {
				// play_id = list.getString("play_id");
				play_id = list.getValue("song_id");
			} else if (("ARTIST").equalsIgnoreCase(p_type)) {
				play_id = list.getValue("artist_id");
			} else if (("FEEL").equalsIgnoreCase(p_type)) {
				play_id = list.getValue("feel_id");
			}
		}

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
		}

	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_play.p_opcode = "KP_2016";

		KP_2014 = KP_init(mContext, KP_2014);

		KP_2015 = KP_init(mContext, KP_2015);

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.KP_nnnn();
		if (KP_play == null) {
			return;
		}

		if (!TextUtil.isEmpty(play_id)) {
			KP_play.KP_2016(getApp().p_mid, p_m1, p_m2, p_type, play_id);
		}

	}

	/**
	 * 
	 * @see PlayFragment#onKPnnnnQueryResult(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);


		if (("KP_2016").equalsIgnoreCase(p_opcode)) {
			// 녹음곡재생
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (("00000").equalsIgnoreCase(r_code)) {
					// KP_2011(0);
				} else {
					// getApp().popupToast(r_message, Toast.LENGTH_LONG);
					String where = __CLASSNAME__ + ".onKPnnnnResult()";
					showMediaError(TYPE.MEDIAPLAYER, LEVEL.I, where, MEDIAERROR.WHAT_MEDIA_ERROR, MEDIAERROR.EXTRA_PLAYER_START_ERROR, r_message, true);
				}
			}
		} else if (("KP_2011").equalsIgnoreCase(p_opcode)) {
			// 댓글좌우스크롤
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				// if (("00000").equalsIgnoreCase(r_code)) {
				// recvKP2011();
				// } else {
				// popupToast(r_message, Toast.LENGTH_LONG);
				// }
				KP2011(KP_2011, page);
			}
		} else if (("KP_2014").equalsIgnoreCase(p_opcode)) {
			// 하트쏘기
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				} else {
					getApp().popupToast(r_message, Toast.LENGTH_LONG);
				}
			}
		} else if (("KP_2015").equalsIgnoreCase(p_opcode)) {
			// 녹음곡댓글입력수정
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (("00000").equalsIgnoreCase(r_code)) {
					KP_2011(0);
				} else {
					getApp().popupToast(r_message, Toast.LENGTH_LONG);
				}
			}
		}
	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_play != null ? KP_play.p_opcode : "");


		KPItem list = KP_play.getList(0);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		String id = null;
		String title = null;
		String nickname = null;
		String heart = null;

		id = list.getValue("song_id");
		title = list.getValue("title");
		nickname = list.getValue(LOGIN.KEY_NICKNAME);
		heart = list.getValue("heart");

		if (!TextUtil.isEmpty(list.getValue("artist"))) {
			title += "-" + list.getValue("artist");
		}

		View v = null;
		try {
			v = findViewById(R.id.include_player_title).findViewById(R.id.id);
			putValue(v, id);
			v.setVisibility(View.GONE);
			v = findViewById(R.id.include_player_title).findViewById(R.id.title);
			putValue(v, title);
			v = findViewById(R.id.include_player_title).findViewById(R.id.name);
			putValue(v, nickname);
			v = findViewById(R.id.include_player_title).findViewById(R.id.heart);
			heart = TextUtil.numberFormat(heart);
			putValue(v, heart);

			// v = (TextView) findViewById("include_feel_title").findViewById(R.id.id);
			// putValue(v, id);
			// v = (TextView) findViewById("include_feel_title").findViewById(R.id.title);
			// putValue(v, title);
			// v = (TextView) findViewById("include_feel_title").findViewById(R.id.name);
			// putValue(v, nickname);
			// v = (TextView) findViewById("include_feel_title").findViewById(R.id.heart);
			// heart = TextUtil.numberFormat(heart);
			// putValue(v, heart);

		} catch (Exception e) {

			// e.printStackTrace();
		}

		super.KPnnnn();
	}

	/**
	 * 
	 * @see PlayFragment#onPause()
	 */
	@Override
	public void onPause() {

		super.onPause();
	}

	/**
	 * 
	 * @see PlayFragment#onResume()
	 */
	@Override
	public void onResume() {

		super.onResume();
		resetOnPause();
		setRedraw(false);
	}

	/**
	 * 
	 * @see PlayFragment#onDestroy()
	 */
	@Override
	public void onDestroy() {

		super.onDestroy();
		stopTickViewSwitcher();
		stopTickComments();
	}

	@Override
	public void open() {

		super.open();
		startTickViewSwitcher();
		startTickComments();
	}

	/**
	 * 
	 * @see PlayFragment#close()
	 */
	@Override
	public void close() {

		stopTickViewSwitcher();
		stopTickComments();
		super.close();
	}

	@Override
	public boolean pause() {

		stopTickViewSwitcher();
		stopTickComments();
		return super.pause();
	}

	/**
	 * onPause 플래그초기화 isCloseOnPause = false isPauseOnPause = false
	 * 
	 * @see isCloseOnPause
	 * @see isPauseOnPause
	 */
	@Override
	protected void resetOnPause() {

		super.resetOnPause();
		isOnPauseClose = false;
		isOnPausePause = false;
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + isOnPauseClose + ", " + isOnPausePause);
	}

	/**
	 * 
	 * @see PlayFragment#resume()
	 */
	@Override
	public boolean resume() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		startTickViewSwitcher();
		startTickComments();
		return super.resume();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {

		setOnPause();
		super.onCompletion(mp);
	}

	@Override
	public Intent onOptionsItemSelected(int id, String menu_name, boolean open) {

		resetOnPause();
		isOnPauseClose = true;
		return super.onOptionsItemSelected(id, "", open);
	}

	@Override
	protected Intent onContextItemSelected(int id, KPnnnn KP_xxxx, int index, boolean open) {

		resetOnPause();
		// if (mPlayType == PlayView.TYPE.RECORD && !isPaused()) {
		// isCloseOnPause = true;
		// }
		return super.onContextItemSelected(id, KP_xxxx, index, open);
	}

	/**
	 * 
	 * @see PlayFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// String vn = getResourceEntryName(v.getId());
		// String cn = v.getClass().getSimpleName();
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		resetOnPause();

		stopTickViewSwitcher();
		stopTickComments();

		if (v.getId() == R.id.arrow_left) {
			KP_2011(-1);
		} else if (v.getId() == R.id.arrow_right) {
			KP_2011(1);
		} else if (v.getId() == R.id.btn_player_people_01) {
			showComment(0);
		} else if (v.getId() == R.id.btn_player_people_02) {
			showComment(1);
		} else if (v.getId() == R.id.btn_player_people_03) {
			showComment(2);
		} else if (v.getId() == R.id.btn_player_people_04) {
			showComment(3);
		} else if (v.getId() == R.id.btn_player_people_05) {
			showComment(4);
		} else if (v.getId() == R.id.txt_comment_01) {
			v.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txt_comment_02) {
			v.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txt_comment_03) {
			v.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txt_comment_04) {
			v.setVisibility(View.GONE);
		} else if (v.getId() == R.id.txt_comment_05) {
			v.setVisibility(View.GONE);
		} else if (v.getId() == R.id.arrow) {
			mViewSwitcher.showNext();
		} else if (v.getId() == R.id.btn_player_menu_sing) {
			// 반주곡재생시닫는다.
			isOnPauseClose = true;
			onContextItemSelected(R.id.context_play_sing, KP_play, KP_index, true);
		} else if (v.getId() == R.id.btn_player_menu_sns) {
			// SNS공유
			openACTIONSHARE(KP_play.getInfo(), getList(), true, null, null);
		} else if (v.getId() == R.id.btn_player_menu_03) {
		} else if (v.getId() == R.id.heart || v.getId() == R.id.btn_player_menu_reply) {
			// 평가 & 댓글
			showCommentEdit();
		} else if (v.getId() == R.id.txt_reply_heart) {
			addHeart();
		} else if (v.getId() == R.id.btn_reply_save) {
			KP_2014nKP_2015();
		} else if (v.getId() == R.id.btn_player_menu_myholic) {
			// GO HOLIC
			onContextItemSelected(R.id.context_go_holic, KP_play, KP_index, true);
		} else {
		}

	}

	public void addHeart() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		View v = null;
		int reply_heart = 0;

		v = findViewById(R.id.txt_reply_heart);
		if (v != null) {
			try {
				reply_heart = TextUtil.parseInt(((TextView) v).getText().toString());
			} catch (Exception e) {

				// e.printStackTrace();
				reply_heart = 0;
			}
		}

		if (reply_heart > 0) {
			reply_heart = 0;
		}

		v = findViewById(R.id.txt_reply_heart);
		if (v != null) {
			((TextView) v).setText(Integer.toString(++reply_heart));
		}
	}

	@SuppressWarnings("deprecation")
	public void KP_2014nKP_2015() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		View v = null;
		int reply_heart = 0;
		String comment = "";

		v = findViewById(R.id.include_player_reply);
		if (v == null) {
			return;
		}

		KPItem list = KP_play.getList(0);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		String uid = list.getValue("uid");

		v = findViewById(R.id.txt_reply_heart);
		if (v != null) {
			reply_heart = TextUtil.parseInt(((TextView) v).getText().toString());
		}

		v = findViewById(R.id.edt_reply_comment);
		if (v != null) {
			comment = ((TextView) v).getText().toString();
		}

		// 하트갯수확인후쏜다
		int heart = 0;
		String num = ((TextView) findViewById(R.id.heart)).getText().toString();
		num = TextUtil.unNumberFormat(num);
		try {
			heart = TextUtil.parseInt(num);
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (heart != heart + reply_heart) {
			KP_2014.KP_2014(getApp().p_mid, p_m1, p_m2, uid, play_id, Integer.toString(reply_heart));
			heart += reply_heart;
			((TextView) findViewById(R.id.heart)).setText(Integer.toString(heart));
		}
		// 코멘트입력을확인한다.
		if (!TextUtil.isEmpty(comment)) {
			if (!TextUtil.isEmpty(reply_ment)) {
				reply_mode = "UPDATE";
			}
			KP_2015.KP_2015(getApp().p_mid, p_m1, p_m2, reply_mode, play_id, comment);
			reply_ment = comment;
		}

		v = findViewById(R.id.include_player_reply);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.edt_reply_comment);
		getApp().hideSoftInput((EditText) v);
		// setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (getPlayer() != null && getPlayer().isPlaying() && (!isOnPausePause || !isOnPauseClose)) {
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}

		// 코멘트작성 중에는 기다려
		setOnPause();

	}

	/**
	 * 백버튼처리
	 */
	@Override
	public boolean onBackPressed() {

		View v = findViewById(R.id.include_player_reply);
		if (v == null) {
			return super.onBackPressed();
		}
		if (v.getVisibility() == View.VISIBLE) {
			showCommentEdit();
			setRedraw(false);
			return false;
		} else {
			return super.onBackPressed();
		}
	}

}
