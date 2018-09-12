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
 * filename	:	ListenFragment2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenFragment2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps.adpt.ReplyListAdapter;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * TODO NOTE:<br>
 * 필기능추가
 * 
 * @author isyoon
 * @since 2012. 7. 3.
 * @version 1.0
 * @author isyoon
 * @since 2012. 7. 23.
 * @version 1.0
 * @see #onPrepared(MediaPlayer)
 */

class playListenFragment2 extends playListenFragment implements MediaPlayer.OnPreparedListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	ReplyListAdapter mReplyListAdapter = null;

	/**
	 * 삭제용 KP_6001 FEEL관리(파일업로드)
	 */
	KPnnnn KP_6001;
	/**
	 * KP_6002 FEEL 상세화면 요청
	 */
	KPnnnn KP_6002;
	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	KPnnnn KP_6003;
	/**
	 * KP_2017 댓글요청
	 */
	KPnnnn KP_2017;
	/**
	 * KP_2018 댓글/신고 등록/수정/삭제 FEEL 게시물/녹음곡 통합 댓글/신고 등록 KP_2015 대체
	 */
	KPnnnn KP_2018;

	String reply_mode = "ADD";

	@Override
	protected void startTickViewSwitcher() {

		// super.startTickViewSwitcher();
	}

	@Override
	protected void stopTickViewSwitcher() {

		// super.stopTickViewSwitcher();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_listen2, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_6001 = KP_init(mContext, KP_6001);

		KP_6002 = KP_init(mContext, KP_6002);

		KP_6003 = KP_init(mContext, KP_6003);

		KP_2017 = KP_init(mContext, KP_2017);

		KP_2018 = KP_init(mContext, KP_2018);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		// 당겨고침제외
		getSwipeRefreshLayout().setEnabled(false);

		View v = null;

		v = findViewById(R.id.show);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}

		v = findViewById(R.id.title);
		WidgetUtils.setTextViewMarquee((TextView) v);


		if (mReplyListAdapter == null) {
			int res[] = { R.layout.item_message_right, R.layout.item_message_left };
			mReplyListAdapter = new ReplyListAdapter(getActivity(), res, KP_2017.getLists(), new View.OnClickListener() {

				@Override
				public void onClick(View view) {

					onListItemClick(view);
				}
			}, getApp().getImageDownLoader());
		}

		mReplyListAdapter.setNotifyOnChange(false);
		setListAdapter(mReplyListAdapter);
		// getListView().setDivider(null);

		// showPlayer();
		showShowButton();
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// 녹음곡 재생화면 복귀시 재시작 오류
		// 플레이어가가없거나(화면처음시작) 거나 중지시(재생재시작)에만 한다.
		if (getPlayer() == null || !getPlayer().isPlaying()) {
			super.KP_nnnn();
		}

	}

	@Override
	public void KP_list(int page) {

		int inc = page - this.page;

		KP_2017(inc);

		super.KP_list(page);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);


		if (("KP_2016").equalsIgnoreCase(p_opcode)) {
			// 녹음곡재생
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					// KP_list(1);
				} else {
					getApp().popupToast(r_message, Toast.LENGTH_LONG);
				}
			}
		} else if (("KP_2017").equalsIgnoreCase(p_opcode)) {
			// 댓글목록
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				// KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				setRefreshComplete();
				KPlist();
				KP2017();
			}
		} else if (("KP_2018").equalsIgnoreCase(p_opcode)) {
			// 댓글작성
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP_list(1);
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					EditText t = (EditText) findViewById(R.id.edt_reply_text);
					if (t != null) {
						t.setText("");
					}
				}
			}
		} else if (("KP_6001").equalsIgnoreCase(p_opcode)) {
			// 필삭제
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					getApp().popupToast(r_message, Toast.LENGTH_LONG);
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					close();
				}
			}
		} else if (("KP_6002").equalsIgnoreCase(p_opcode)) {
			// 필내용
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					KP6002();
					KP_2017(0);
				}
			}
		} else if (("KP_6003").equalsIgnoreCase(p_opcode)) {
			// KP_6003 좋아요(하트) 등록/수정요청
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
				}
				KP6003();
			}
		}
	}

	/**
	 * KP_6002 FEEL 상세화면 요청
	 */
	protected void KP6002() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		KPItem list = KP_6002.getList(0);

		View v = null;
		String value = "";
		if (list != null) {

			play_id = list.getValue("feel_id");

			if (findViewById(R.id.include_feel_title) != null) {

				// 국기
				_ImageView i = (_ImageView) findViewById(R.id.include_feel_title).findViewById(R.id.flag);
				value = list.getValue("ncode");
				if (value != null) {
					try {
						i.setImageResource(getResource("img_flag_" + value.toLowerCase(), "drawable"));
					} catch (Exception e) {

						// e.printStackTrace();
					}
				} else {
					i.setVisibility(View.GONE);
				}

				// 닉네임
				value = list.getValue(LOGIN.KEY_NICKNAME);
				TextView t = (TextView) findViewById(R.id.include_feel_title).findViewById(R.id.name);
				putValue(t, value);

				// int lvl = !TextUtil.isEmpty(list.getString("level")) ? TextUtil.parseInt(list.getString("level")) : 0;
				// int color = getResources().getColor(R.color.text_thick);
				// if (lvl == 4) {
				// color = getResources().getColor(R.color.solid_orange);
				// t.setTextColor(color);
				// } else if (lvl == 5) {
				// color = getResources().getColor(R.color.solid_pink);
				// t.setTextColor(color);
				// } else {
				// color = getResources().getColor(R.color.text_thick);
				// t.setTextColor(color);
				// }

				// 이미지(프로필)
				value = list.getValue("url_profile");
				v = findViewById(R.id.include_feel_title).findViewById(R.id.image);
				putURLImage(mContext, (ImageView) v, value, false, R.drawable.ic_menu_01);
				v.setFocusable(false);
				v.setClickable(true);
				v.setOnClickListener(this);

				// hit
				value = list.getValue("hit");
				v = findViewById(R.id.include_feel_title).findViewById(R.id.hit);
				putValue(v, value);

				// heart
				value = list.getValue("heart");
				v = findViewById(R.id.include_feel_title).findViewById(R.id.heart);
				putValue(v, value);

				// reply
				value = list.getValue("comment_cnt");
				v = findViewById(R.id.include_feel_title).findViewById(R.id.reply);
				putValue(v, value);

				// date
				value = list.getValue("reg_date");
				v = findViewById(R.id.date);
				putValue(v, value);
			}

			// 이미지(필정보)
			value = list.getValue("url_comment");
			v = findViewById(R.id.feel_image);
			if (v != null) {
				if (URLUtil.isNetworkUrl(value)) {
					putURLImage(mContext, (ImageView) v, value, true, R.drawable.ic_menu_01);
					v.setVisibility(View.VISIBLE);
				} else {
					v.setVisibility(View.GONE);
				}
				v.setFocusable(false);
				v.setClickable(true);
				v.setOnClickListener(this);
			}

			// //제목
			// value = list.getString("title");
			// v = findViewById(R.id.title);
			// putValue(v, value);

			// 내용
			value = list.getValue("comment");
			v = findViewById(R.id.feel_text);
			putValue(v, value);

			// 본인 게시물여부(Y:본인,N:타회원,공란:비로그인)
			value = list.getValue("my_contents");
			if (("Y").equalsIgnoreCase(value)) {
				v = findViewById(R.id.btn_feel_del);
				v.setVisibility(View.VISIBLE);
				v = findViewById(R.id.btn_feel_edit);
				v.setVisibility(View.VISIBLE);
			} else if (("N").equalsIgnoreCase(value)) {
				// 타인
				v = findViewById(R.id.btn_feel_del);
				v.setVisibility(View.GONE);
				v = findViewById(R.id.btn_feel_edit);
				v.setVisibility(View.GONE);
			} else {
				v = findViewById(R.id.btn_feel_del);
				v.setVisibility(View.GONE);
				v = findViewById(R.id.btn_feel_edit);
				v.setVisibility(View.GONE);
			}

			// 하트표시
			try {
				if (value != null) {
					if (("Y").equalsIgnoreCase(value)) {
						v = findViewById(R.id.btn_heart);
						v.setVisibility(View.GONE);
					} else {
						v = findViewById(R.id.btn_heart);
						v.setVisibility(View.VISIBLE);
					}
				} else {
					v.setVisibility(View.GONE);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			// 링크버튼
			value = list.getValue("furl");
			v = findViewById(R.id.btn_link);
			if (URLUtil.isNetworkUrl(value)) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}

			// 반주곡버튼
			value = list.getValue("song_id");
			v = findViewById(R.id.btn_sing);
			if (!TextUtil.isEmpty(value)) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}

			// 녹음곡버튼
			value = list.getValue("record_id");
			v = findViewById(R.id.btn_listen);
			if (!TextUtil.isEmpty(value)) {
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}

		} else {
		}
	}

	/**
	 * KP_2017 댓글요청 <br>
	 * 결과가 없어도 처리
	 */
	protected void KP2017() {
		KP2011(KP_2017, page);
		if (KP_2017.getLists() != null) {
			if (KP_2017.getLists().size() > 0) {
				hidePlayer();
			} else {
				showPlayer();
			}
		} else {
			showPlayer();
		}
	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_play != null ? KP_play.p_opcode : "");

		super.KPnnnn();

		KPItem list = KP_play.getList(0);

		String value = "";
		View v = null;

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		value = list.getValue("is_heart");
		v = findViewById(R.id.chk_heart);
		if (v != null) {
			if (("Y").equalsIgnoreCase(value)) {
				((CheckBox) v).setChecked(true);
			} else {
				((CheckBox) v).setChecked(false);
			}
		}

	}

	int people_page = 0;

	@Override
	public void KP_2011(int inc) {

		int page = this.people_page + inc;
		if (page > 0) {
			if (page <= this.page && page <= this.total_page) {
				KP2011(KP_2017, page);
				this.people_page = page;
			} else {
				KP_list(this.page + 1);
			}
		}
	}

	/**
	 * KP_2017 댓글요청
	 */
	protected void KP_2017(int inc) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + inc);

		page = page + inc;

		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		// 처음조회
		if (inc == 0) {
			page = 1;
			total_page = 0;
		}

		// 페이징확인
		if (total_page != 0 && (page > total_page || page < 1)) {
			page = page - inc;
			return;
		}

		if (!TextUtil.isEmpty(play_id)) {
			KP_2017.KP_2017(getApp().p_mid, p_m1, p_m2, play_id, page);
			if (page == 1) {
				if (getBaseListAdapter() != null) {
					getBaseListAdapter().notifyDataSetChanged();
				}
			}
			people_page = page;
		}
	}

	/**
	 * KP_2018 댓글/신고 등록/수정/삭제 FEEL 게시물/녹음곡 통합 댓글/신고 등록 KP_2015 대체
	 */
	protected void KP_2018(String siren_code) {

		EditText t = (EditText) findViewById(R.id.edt_reply_text);
		String comment = t.getText().toString();

		if (TextUtil.isEmpty(comment)) {
			return;
		} else {
			t.setText("");
		}
		getApp().hideSoftInput(t);

		String seq = "";

		KPItem list = KP_2017.getList(KP_index);

		if (list != null) {
			seq = list.getValue("seq");
		} else {
			reply_mode = "ADD";
			seq = "";
		}

		if (!TextUtil.isEmpty(play_id)) {
			KP_2018.KP_2018(getApp().p_mid, p_m1, p_m2, play_id, reply_mode, seq, siren_code, comment, "");
		}
	}

	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	protected void KP_6003() {
		if (!TextUtil.isEmpty(play_id)) {
			KP_6003.KP_6003(getApp().p_mid, p_m1, p_m2, "RECORD", play_id, "");
		}
	}

	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	protected void KP6003() {

		TextView v = (TextView) findViewById(R.id.heart);
		CheckBox c = (CheckBox) findViewById(R.id.chk_heart);

		KPItem list = KP_6003.getList(0);
		String is_heart = "";
		String heart = v.getText().toString();

		if (list != null) {
			is_heart = list.getValue("is_heart");
			heart = list.getValue("heart");
		}

		// ArrayList<KPItem> lists = KP_6002.getLists();
		// KPItem item = null;
		// int position = 0;
		// String record_id = list.getString("record_id");
		//
		// for (int i = 0; i < lists.size(); i++) {
		// item = lists.get(i);
		// if (record_id.equalsIgnoreCase(item.getString("record_id"))) {
		// position = i;
		// break;
		// }
		// }
		//
		// if (position < lists.size()) {
		// item = lists.get(position);
		// item.putString("is_heart", is_heart);
		// }

		try {
			if (("Y").equalsIgnoreCase(is_heart)) {
				c.setChecked(true);
				// int cnt = 0;
				// cnt = TextUtil.parseInt(v.getText().toString());
				// cnt++;
				// v.setText(Integer.toString(cnt));
			} else {
				c.setChecked(false);
				// int cnt = 0;
				// cnt = TextUtil.parseInt(v.getText().toString());
				// cnt = cnt - 1 < 0 ? 0 : cnt - 1;
				// v.setText(Integer.toString(cnt));
			}
			v.setText(heart);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void KPlist() {

		KPlist(KP_2017.getInfo(), KP_2017.getLists());

		// if (KP_2017.getLists() != null) {
		// if (KP_2017.getLists().size() > 0) {
		// hidePlayer();
		// } else {
		// showPlayer();
		// }
		// } else {
		// showPlayer();
		// }
	}

	@Override
	public void onPrepared(MediaPlayer mp) {

		super.onPrepared(mp);

		showPlayer();
		KP2017();
	}

	@Override
	public void onClick(View v) {
		// String vn = getResourceEntryName(v.getId());
		// String cn = v.getClass().getSimpleName();
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		KPItem list = null;

		if (v.getId() == R.id.show) {
			boolean checked = ((CheckBox) v).isChecked();
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + checked);
			if (!TextUtil.isEmpty(play_id)) {
				showPlayer(checked);
			}
		} else if (v.getId() == R.id.txt_song_read || v.getId() == R.id.btn_song_read) {
			// 독음
			if (findViewById(R.id.scr_song_read).getVisibility() == View.GONE) {
				findViewById(R.id.txt_song_read).setVisibility(View.VISIBLE);
				findViewById(R.id.scr_song_read).setVisibility(View.VISIBLE);
				((TextView) findViewById(R.id.btn_song_read)).setText("▲ " + getString(R.string.btn_title_read));
			} else {
				findViewById(R.id.txt_song_read).setVisibility(View.GONE);
				findViewById(R.id.scr_song_read).setVisibility(View.GONE);
				((TextView) findViewById(R.id.btn_song_read)).setText("▼ " + getString(R.string.btn_title_read));
			}
		} else if (v.getId() == R.id.btn_feel_del) {
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete), getString(R.string.btn_title_delete),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							deleteFeelPost();
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_feel_edit) {
			openFeelPost("UPDATE", KP_6002, 0);
		} else if (v.getId() == R.id.btn_del) {
			// deleteFeelPost();
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete), getString(R.string.btn_title_delete),
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							deleteReplyPost();
						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_edit) {
		} else if (v.getId() == R.id.btn_reply_save) {
			KP_2018("");
		} else if (v.getId() == R.id.feel_image) {
			list = KP_6002.getList(0);
			if (list != null) {
				String url = list.getValue("url_org_comment");
				if (!TextUtil.isNetworkUrl(url)) {
					url = list.getValue("url_comment");
				}
				openWebView(webview.class, getString(R.string.M1_MENU_LISTEN), getString(R.string.M2_MENU_LISTEN), getString(R.string.menu_listen), url,
						"IMAGE", false);
			}
		} else if (v.getId() == R.id.chk_heart) {
			KP_6003();
		} else if (v.getId() == R.id.btn_reply_show) {
			reply_mode = "ADD";
			KP_index = -1;
			showReplyPost();
		} else if (v.getId() == R.id.btn_link) {
			openFeelUrl();
		} else if (v.getId() == R.id.btn_sing) {
			onContextItemSelected(R.id.context_play_sing, KP_6002, 0, true);
		} else if (v.getId() == R.id.btn_listen) {
			KP_2016();
		} else {
		}
	}

	@Override
	public void onListItemClick(View v) {
		if (getListView() == null) {
			return;
		}

		String nm = getResourceEntryName(v.getId());
		int position = getPositionForView((View) v.getParent());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position);
		if (position == AdapterView.INVALID_POSITION) {
			return;
		}

		KP_nnnn = KP_2017;


		super.onListItemClick(v);

		int index = position - getListView().getHeaderViewsCount();


		KPItem list = KP_2017.getList(index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (v.getId() == R.id.image) {
			String uid = list.getValue("uid");
			if (!TextUtil.isEmpty(uid)) {
				onContextItemSelected(R.id.context_go_holic, KP_2017, index, true);
			}
		} else {
			// super.onItemViewClick(v);
			onClick(v);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		String nm = getResourceEntryName(v.getId());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_index + " - " + vn + ", " + nm + "," + cn);


		// super.onItemClick(parent, v, position, id);
		KPItem list = KP_2017.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		return super.onItemLongClick(parent, view, position, id);
	}

	protected void deleteFeelPost() {
		if (!TextUtil.isEmpty(play_id)) {
			KPItem list = KP_6002.getList(0);
			if (list != null) {
				String feel_type = list.getValue("feel_type");
				KP_6001.KP_6001(getApp().p_mid, p_m1, p_m2, "POST", play_id, "DEL", "", "", feel_type, "", "", "", "", "", "");
			}
		}
	}

	@Override
	protected void showPlayer() {

		super.showPlayer();
		showReplyPost(View.GONE);

		View v = null;
		v = findViewById(R.id.viewSwitcher);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void hidePlayer() {

		super.hidePlayer();
		showReplyPost(View.VISIBLE);

		View v = null;
		v = findViewById(R.id.viewSwitcher);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}
	}

	protected void showReplyPost() {
		View v = findViewById(R.id.include_reply);
		if (v != null) {
			if (v.getVisibility() == View.GONE) {
				showReplyPost(View.VISIBLE);
			} else {
				showReplyPost(View.GONE);
			}
		}
	}

	protected void showReplyPost(int visibility) {
		boolean isPlayerShow = false;
		View v = null;
		v = findViewById(R.id.show);
		if (v != null) {
			isPlayerShow = ((CheckBox) v).isChecked();
		}

		// 플레이어가 보이면 무조건 숨긴다.
		if (isPlayerShow) {
			visibility = View.GONE;
		}

		// 로그인유저가 아니면 무조건 숨긴다.
		if (!isLoginUser()) {
			visibility = View.GONE;
		}

		v = findViewById(R.id.include_reply);
		if (v != null) {
			v.setVisibility(visibility);
		}
	}

	protected void deleteReplyPost() {
		if (!TextUtil.isEmpty(play_id)) {
			KPItem list = KP_2017.getList(KP_index);
			if (list != null) {
				String seq = list.getValue("seq");
				KP_2018.KP_2018(getApp().p_mid, p_m1, p_m2, play_id, "DEL", seq, "", "", "");
			}
		}
	}

	protected void openFeelUrl() {
		KPItem list = KP_6002.getList(0);

		if (list != null) {
			String url = list.getValue("furl");
			// url = "http://www.youtube.com/watch?v=9FhdYOZoKcs&feature=plcp";

			final String query[] = TextUtils.split(url, "\\?");
			if (query.length == 2) {
				openWebView(webview.class, getString(R.string.M1_MENU_LISTEN), getString(R.string.M2_MENU_LISTEN), getString(R.string.menu_listen), url,
						"POST", false);
			} else {
				openWebView(webview.class, getString(R.string.M1_MENU_LISTEN), getString(R.string.M2_MENU_LISTEN), getString(R.string.menu_listen), url,
						"GET", false);
			}
		}
	}

	/**
	 * // KP_2016 녹음곡재생
	 */
	protected void KP_2016() {
		// FEEL시녹음곡재생제한
		KPItem list = KP_6002.getList(0);
		play_id = list.getValue("record_id");
		KP_play.KP_2016(getApp().p_mid, getString(R.string.M1_MENU_LISTEN), getString(R.string.M2_LISTEN_PLAY), "RECORD", play_id);
	}

	@Override
	public void open() {

		super.open();

		hidePlayer();
	}

	/**
	 */
	@Override
	public boolean play() {

		boolean ret = super.play();
		if (ret) {
			if (!TextUtil.isEmpty(play_id)) {
				showPlayerBar();
			}
			View v = findViewById(R.id.show);
			boolean redraw = ((CheckBox) v).isChecked();
			setRedraw(!redraw);
		}
		return ret;
	}

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
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ":" + isOnPauseClose + "," + isOnPausePause);
	}

	@Override
	protected void setOnPause() {

		super.setOnPause();
	}

	@Override
	public boolean resume() {

		boolean ret = super.resume();
		if (ret) {
			if (!TextUtil.isEmpty(play_id)) {
				showPlayerBar();
			}
			View v = findViewById(R.id.show);
			boolean redraw = ((CheckBox) v).isChecked();
			setRedraw(!redraw);
		}
		return ret;
	}

	@Override
	protected boolean showUploadButton() {

		boolean ret = super.showUploadButton();

		showShowButton();
		return ret;
	}

}
