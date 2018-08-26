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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.KPOP
 * filename	:	ListenFragment2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenFragment2.java
 * </pre>
 */

package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.app.html.HtmlBubble;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseListFragment;
import kr.kymedia.karaoke.apps.adpt.ReplyListAdapter;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

;

/**
 * <pre>
 * 1. 필상세전문전용
 * 2. 글내용복사기능추가
 * </pre>
 *
 * @author isyoon
 * @version 2.0
 * @since 2013. 12. 2.
 */
public class FeelFragment extends _BaseListFragment implements OnTouchListener {
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
	public void KP_init() {

		super.KP_init();

		KP_6001 = KP_init(mContext, KP_6001);

		KP_6002 = KP_init(mContext, KP_6002);

		KP_6003 = KP_init(mContext, KP_6003);

		KP_2017 = KP_init(mContext, KP_2017);

		KP_2018 = KP_init(mContext, KP_2018);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_feel2, container, false);

		return mRootView;
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		View v = null;

		v = findViewById(R.id.show);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}

		v = findViewById(R.id.title);
		WidgetUtils.setTextViewMarquee((TextView) v);


		if (mReplyListAdapter == null) {
			int res[] = {R.layout.item_feel_right, R.layout.item_feel_left};
			mReplyListAdapter = new ReplyListAdapter(getActivity(), res, KP_2017.getLists(), new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					onListItemClick(view);
				}
			}, getApp().getImageDownLoader());
		}

		mReplyListAdapter.setNotifyOnChange(false);

		mHeaderView = inflate(R.layout.include_feel_post, null, false);
		mHeaderView.setId(R.id.include_feel_post);
		mHeaderView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

		setListAdapter(mReplyListAdapter);

		getListView().setHeaderDividersEnabled(true);
		if (getListView().getHeaderViewsCount() > 0) {
			mHeaderView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		}

		if (mHeaderView != null) {
			WidgetUtils.setOnLongClickListener(mContext, (ViewGroup) mHeaderView, new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {

					if (v instanceof TextView) {
						String text = ((TextView) v).getText().toString();
						if (!TextUtil.isEmpty(text)) {
							TextUtil.copyText2Clipboard(mContext, text);
							getApp().popupToast(getString(R.string.msg_text_copied_text), Toast.LENGTH_LONG);
						}
					}
					return false;
				}
			}, true);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		setResult(resultCode, data);
	}

	String feel_type;

	@Override
	protected void start() {

		super.start();

		// FEEL시녹음곡재생제한
		// mPlayType = PlayView.TYPE.FEEL;
		KPItem list = getList();

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (list != null && !TextUtil.isEmpty(list.getValue("feel_id"))) {
			play_id = list.getValue("feel_id");
		}

		if (list != null && !TextUtil.isEmpty(list.getValue("feel_type"))) {
			feel_type = list.getValue("feel_type");
		}

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.KP_nnnn();

		// feel_type : "K":반주곡재생, "R":녹음곡재생, "A":오디션, "T":일반 게시물
		if (!TextUtil.isEmpty(feel_type)) {

			if (("K").equalsIgnoreCase(feel_type)) {
				// "K":반주곡재생
			} else if (("R").equalsIgnoreCase(feel_type)) {
				// "R":녹음곡재생
			} else if (("A").equalsIgnoreCase(feel_type)) {
				// "A":오디션
			} else if (("T").equalsIgnoreCase(feel_type)) {
				// "T":일반 게시물
			}

			// hidePlayerBar();

			if (!TextUtil.isEmpty(play_id)) {
				KP_6002.KP_6002(getApp().p_mid, p_m1, p_m2, play_id);
			}
		} else {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", getList());
			close();
			return;
		}

	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {


		if (("KP_6001").equalsIgnoreCase(p_opcode)) {
			// 필삭제
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					getApp().popupToast(r_message, Toast.LENGTH_LONG);
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					close();
				} else {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
					stopLoading(__CLASSNAME__, getMethodName());
				}
			}
		} else if (("KP_6002").equalsIgnoreCase(p_opcode)) {
			// 필내용
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					KP6002();
				} else {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
					stopLoading(__CLASSNAME__, getMethodName());
				}
			}
		} else if (("KP_6003").equalsIgnoreCase(p_opcode)) {
			// KP_6003 좋아요(하트) 등록/수정요청
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP6003();
				}
			}
		} else if (("KP_2017").equalsIgnoreCase(p_opcode)) {
			// 댓글목록
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					KPlist();
					stopLoading(__CLASSNAME__, getMethodName());
				} else {
					// showQueryError(what, p_opcode, r_code, r_message, r_info);
					stopLoading(__CLASSNAME__, getMethodName());
				}
				KP2017();
				setRefreshComplete();
			}
		} else if (("KP_2018").equalsIgnoreCase(p_opcode)) {
			// 댓글작성
			if (what != _IKaraoke.STATE_DATA_QUERY_START) {
				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
					setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					KP_list(1);
					stopLoading(__CLASSNAME__, getMethodName());
					EditText t = (EditText) findViewById(R.id.edt_reply_text);
					if (t != null) {
						t.setText("");
					}
				} else {
					KPnnnnResult(what, p_opcode, r_code, r_message, r_info);
					stopLoading(__CLASSNAME__, getMethodName());
				}
			}
		} else {
			super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
		}
	}

	/**
	 * KP_6002 FEEL 상세화면 요청
	 */
	protected void KP6002() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		try {
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

					// if (t != null) {
					// int lvl = !TextUtil.isEmpty(list.getString("level")) ? TextUtil.parseInt(list.getString("level"))
					// : 0;
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

					// 댓글건수업데이트
					int count = 0;
					if (TextUtils.isDigitsOnly(value)) {
						count = Integer.parseInt(value);
					}
					v = findViewById(R.id.include_feel_post).findViewById(R.id.feel_comment);
					if (v != null) {
						if (count > 0) {
							value = String.format(getString(R.string.hint_feel_comment), count);
							putValue(v, value);
						} else {
							v.setVisibility(View.INVISIBLE);
						}
					}

					// date
					value = list.getValue("reg_date");
					v = findViewById(R.id.date);
					putValue(v, value);
				}

				// 이미지(필정보)
				value = list.getValue("url_comment");
				v = findViewById(R.id.feel_image);
				if (v != null) {
					putURLImage(mContext, (ImageView) v, value, true, R.drawable.ic_menu_01);
					v.setFocusable(false);
					v.setClickable(true);
					v.setOnClickListener(this);
				}

				// 제목
				value = list.getValue("title");
				v = findViewById(R.id.title);
				putValue(v, value);

				// 내용
				value = list.getValue("comment");
				v = findViewById(R.id.feel_text);
				// putValue(v, value);
				putHtml((TextView) v, value);

				// 본인 게시물여부(Y:본인,N:타회원,공란:비로그인)
				value = list.getValue("my_contents");
				if (("Y").equalsIgnoreCase(value)) {
					v = findViewById(R.id.btn_feel_del);
					v.setVisibility(View.VISIBLE);
					v = findViewById(R.id.btn_feel_edit);
					v.setVisibility(View.VISIBLE);
					v = findViewById(R.id.btn_heart);
					v.setVisibility(View.INVISIBLE);
					v = findViewById(R.id.btn_siren);
					v.setVisibility(View.INVISIBLE);
				} else if (("N").equalsIgnoreCase(value)) {
					// 타인
					v = findViewById(R.id.btn_feel_del);
					v.setVisibility(View.INVISIBLE);
					v = findViewById(R.id.btn_feel_edit);
					v.setVisibility(View.INVISIBLE);
					v = findViewById(R.id.btn_siren);
					v.setVisibility(View.VISIBLE);
					v = findViewById(R.id.btn_heart);
					v.setVisibility(View.VISIBLE);
				} else {
					// 비로그인
					v = findViewById(R.id.btn_feel_del);
					v.setVisibility(View.INVISIBLE);
					v = findViewById(R.id.btn_feel_edit);
					v.setVisibility(View.INVISIBLE);
					v = findViewById(R.id.btn_siren);
					v.setVisibility(View.INVISIBLE);
					v = findViewById(R.id.btn_heart);
					v.setVisibility(View.INVISIBLE);
				}

				v = findViewById(R.id.btn_feel_edit);
				if (v != null) {
					v.setVisibility(View.GONE);
				}

				// 하트표시
				value = list.getValue("is_heart");
				if (value != null) {
					if (("Y").equalsIgnoreCase(value)) {
						v = findViewById(R.id.chk_heart);
						((CheckBox) v).setChecked(true);
					} else {
						v = findViewById(R.id.chk_heart);
						((CheckBox) v).setChecked(false);
					}
				}

				// 링크버튼
				value = list.getValue("furl");
				v = findViewById(R.id.btn_link);
				if (v != null) {
					if (URLUtil.isNetworkUrl(value)) {
						v.setVisibility(View.VISIBLE);
					} else {
						v.setVisibility(View.GONE);
					}
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
				v = mHeaderView.findViewById(R.id.btn_listen);
				if (v != null) {
					if (!TextUtil.isEmpty(value)) {
						v.setVisibility(View.VISIBLE);
					} else {
						v.setVisibility(View.GONE);
					}
				}

				v = findViewById(R.id.include_feel_button).findViewById(R.id.btn_listen);
				if (v != null) {
					v.setVisibility(View.GONE);
				}

				// best
				value = list.getValue("top_txt");
				v = findViewById(R.id.best);
				putValue(v, value);

				v = findViewById(R.id.include_feel_button);
				if (v != null) {
					v.setVisibility(View.VISIBLE);
				}

			} else {
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * KP_2017 댓글요청
	 */
	protected void KP2017() {
		// int count = 0;
		// View v = null;
		// String value = "";
		//
		// ArrayList<KPItem> lists = KP_2017.getLists();
		// if (lists != null && lists.size() > 0) {
		// count = KP_2017.getListCount();
		// value = String.format(getString(R.string.hint_feel_comment), count);
		// }
		//
		// //댓글건수업데이트
		// v = findViewById(R.id.include_feel_post).findViewById(R.id.feel_comment);
		// putValue(v, value);
		// v = findViewById(R.id.include_feel_title).findViewById(R.id.reply);
		// putValue(v, Integer.toString(count));
	}

	// @Override
	// protected void putSongInfo() {
	//
	// super.putSongInfo();
	// }

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);

		// KPItem info = KP_nnnn.getInfo();
		//
		// if (info != null) {
		// String gasa = info.getString("msg_" + getApp().mLocale.getCountry().toUpperCase());
		// if (gasa != null && !TextUtil.isEmpty(gasa) && !gasa.equalsIgnoreCase("N")) {
		// findViewById(R.id.btn_song_read).setVisibility(View.VISIBLE);
		// } else {
		// findViewById(R.id.btn_song_read).setVisibility(View.GONE);
		// }
		// findViewById(R.id.btn_song_read).setVisibility(View.GONE);
		// View v = findViewById(R.id.txt_song_read);
		// ((TextView) v).setText(gasa);
		// ((TextView) v).setGravity(Gravity.CENTER);
		// ((TextView) v).setTextAppearance(mContext, R.style.TextXLargeWhite);
		// }


		super.KPnnnn();
	}

	/**
	 * KP_2017 댓글요청
	 */
	protected void KP_2017(int page) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + page + play_id);

		if (!TextUtil.isEmpty(play_id)) {
			KP_2017.KP_2017(getApp().p_mid, p_m1, p_m2, play_id, page);
			if (page == 1) {
				if (getBaseListAdapter() != null) {
					getBaseListAdapter().notifyDataSetChanged();
				}
			}
		} else {
			stopLoading(__CLASSNAME__, getMethodName());
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
		KPItem list = KP_6002.getList(KP_index);
		if (list != null) {
			String feel_id = list.getValue("feel_id");
			if (!TextUtil.isEmpty(feel_id)) {
				KP_6003.KP_6003(getApp().p_mid, p_m1, p_m2, "FEEL", "", feel_id);
			}
		}
	}

	/**
	 * KP_6003 좋아요(하트) 등록/수정요청
	 */
	protected void KP6003() {
		ArrayList<KPItem> lists = KP_6002.getLists();

		KPItem list = KP_6003.getList(0);

		if (list != null) {
			// String feel_id = list.getString("feel_id");
			// String record_id = list.getString("record_id");
			String is_heart = list.getValue("is_heart");
			String heart = list.getValue("heart");

			int position = KP_index;

			KPItem item = lists.get(position);

			item.putValue("is_heart", is_heart);
			item.putValue("heart", heart);

			try {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "item - " + item.toString(2));
			} catch (Exception e) {

				// e.printStackTrace();
			}

			// for (int i = 0; i < lists.size(); i++) {
			// item = lists.get(i);
			// if (feel_id.equalsIgnoreCase(item.getString("feel_id"))) {
			// position = i;
			// break;
			// }
			// }
			// item = lists.get(position);
			// item.putString("is_heart", is_heart);

			// for (int i = 0; i < lists.size(); i++) {
			// item = lists.get(i);
			// if (record_id.equalsIgnoreCase(item.getString("record_id"))) {
			// position = i;
			// break;
			// }
			// }
			// item = lists.get(position);
			// item.putString("is_heart", is_heart);

			try {
				CheckBox c = (CheckBox) findViewById(R.id.chk_heart);
				if (("Y").equalsIgnoreCase(is_heart)) {
					c.setChecked(true);
				} else {
					c.setChecked(false);
				}
				TextView v = (TextView) findViewById(R.id.heart);
				v.setText(heart);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	@Override
	public void KP_list(int page) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + page);


		super.KP_list(page);

		KP_2017(page);
	}

	@Override
	public void KPlist() {

		KPlist(KP_2017.getInfo(), KP_2017.getLists());
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		KPItem list = KP_6002.getList(0);

		if (v.getId() == R.id.image) {
			String uid = list.getValue("uid");
			if (!TextUtil.isEmpty(uid)) {
				onContextItemSelected(R.id.context_go_holic, KP_6002, 0, true);
			}
		} else if (v.getId() == R.id.btn_feel_del) {
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete), getString(R.string.btn_title_delete), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					deleteFeelPost();
				}
			}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_feel_edit) {
			openFeelPost("UPDATE", KP_6002, 0);
		} else if (v.getId() == R.id.btn_siren) {
			onContextItemSelected(R.id.context_siren_open, KP_6002, 0, true);
		} else if (v.getId() == R.id.btn_del) {
			// deleteFeelPost();
			getBaseActivity().showAlertDialog(getString(R.string.alert_title_confirm), getString(R.string.warning_delete), getString(R.string.btn_title_delete), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					deleteReplyPost();
				}
			}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_edit) {
		} else if (v.getId() == R.id.btn_reply_save) {
			KP_2018("");
		} else if (v.getId() == R.id.feel_image) {
			String url = list.getValue("url_org_comment");
			if (!TextUtil.isNetworkUrl(url)) {
				url = list.getValue("url_comment");
			}
			openWebView(webview.class, getString(R.string.M1_MENU_FEEL), getString(R.string.M2_FEEL_INFO), getString(R.string.menu_feel), url, "IMAGE", false);
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
			// KP_2016();
			onContextItemSelected(R.id.context_play_listen, KP_6002, 0, true);
		} else if (v.getId() == R.id.include_feel_html) {
			openFeelUrl();
		} else if (v.getId() == R.id.html_html) {
			openFeelUrl();
		} else if (v.getId() == R.id.html_image) {
			openFeelUrl();
		} else {
		}
	}

	@Override
	protected HtmlBubble parseHtml(String source, String url, String type) {

		HtmlBubble html = super.parseHtml(source, url, type);

		try {
			ViewGroup vg = (ViewGroup) findViewById(R.id.include_feel_html);

			if (vg == null) {
				return null;
			}

			if (TextUtil.isNetworkUrl(url)) {
				vg.setVisibility(View.VISIBLE);
			} else {
				vg.setVisibility(View.GONE);
				return null;
			}

			// if (vg.findViewById(R.id.html_title) != null) {
			// ((TextView) vg.findViewById(R.id.html_title)).setText(html.title);
			// vg.findViewById(R.id.html_title).setVisibility(View.GONE);
			// }
			//
			// if (vg.findViewById(R.id.html_text) != null) {
			// ((TextView) vg.findViewById(R.id.html_text)).setText(html.description);
			// vg.findViewById(R.id.html_text).setVisibility(View.GONE);
			// }

			String fhtm = html.body.replace("\"", "'");
			if (vg.findViewById(R.id.html_html) != null) {
				((TextView) vg.findViewById(R.id.html_html)).setMovementMethod(LinkMovementMethod.getInstance());
				((TextView) vg.findViewById(R.id.html_html)).setText(Html.fromHtml(html.body));
				((TextView) vg.findViewById(R.id.html_html)).setSelected(false);
				vg.findViewById(R.id.html_html).setVisibility(View.VISIBLE);
			}

			if (vg.findViewById(R.id.html_image) != null) {
				putURLImage(mContext, (ImageView) vg.findViewById(R.id.html_image), html.image, false, R.drawable.bg_trans);
			}

			if (vg.findViewById(R.id.html_progress) != null) {
				vg.findViewById(R.id.html_progress).setVisibility(View.GONE);
			}

			if (vg.findViewById(R.id.html_del) != null) {
				vg.findViewById(R.id.html_del).setVisibility(View.GONE);
			}

			KPItem list = KP_6002.getList(0);
			String url_comment = "";
			if (list != null) {
				url_comment = list.getValue("url_comment");
			}

			if (!TextUtil.isNetworkUrl(url_comment) && html != null) {
				// 이미지(필정보)
				url_comment = html.image;
				View v = findViewById(R.id.feel_image);
				if (v != null && URLUtil.isNetworkUrl(url_comment)) {
					putURLImage(mContext, (ImageView) v, url_comment, true, R.drawable.ic_menu_01);
					v.setFocusable(false);
					v.setClickable(true);
					v.setOnClickListener(this);
				}
			}

			if (list != null && TextUtil.isNetworkUrl(url_comment)) {
				list.putValue("url_comment", url_comment);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return html;
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

		// super.onListItemClick(v);
		KP_index = position - getListView().getHeaderViewsCount();


		KPItem info = KP_2017.getInfo();
		KPItem list = KP_2017.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		if (v.getId() == R.id.image) {
			String uid = list.getValue("uid");
			if (!TextUtil.isEmpty(uid)) {
				onContextItemSelected(R.id.context_go_holic, KP_2017, KP_index, true);
			}
		} else if (v.getId() == R.id.btn_siren) {
			// onContextItemSelected(mContext, R.id.context_siren_open, info, list, true);
			onContextItemSelected(R.id.context_siren_open, KP_2017, KP_index, true);
		} else {
			// super.onItemViewClick(v);
			onClick(v);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

		boolean ret = super.onItemLongClick(parent, view, position, id);

		// String name = getResourceEntryName(view.getId());
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + name + "," + KP_index + "," + id);

		KPItem list = null;
		String comment = "";

		if (KP_index < 0) {
			if (KP_6002.getLists() != null && KP_6002.getListCount() > 0) {
				list = KP_6002.getList(0);
			}
		} else {
			if (KP_2017.getLists() != null && KP_index < KP_2017.getListCount() && KP_index > -1) {
				list = KP_2017.getList(KP_index);
			}
		}

		if (list != null) {
			comment = list.getValue("comment");
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "comment:" + comment);

		if (!TextUtil.isEmpty(comment)) {
			TextUtil.copyText2Clipboard(mContext, comment);
			getApp().popupToast(getString(R.string.msg_text_copied_text), Toast.LENGTH_LONG);
		}

		return ret;
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
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName());

		KPItem list = KP_6002.getList(0);

		if (list != null) {
			String record_id = list.getValue("record_id");
			String furl = list.getValue("furl");

			if (!TextUtil.isEmpty(record_id)) {
				// onContextItemSelected(mContext, R.id.context_play_listen, info, list, true);
				onContextItemSelected(R.id.context_play_listen, KP_6002, 0, true);
			} else {
				// final String query[] = TextUtils.split(furl, "\\?");
				// if (query.length == 2) {
				// openWebView(webview.class, getString(R.string.M1_MENU_FEEL),
				// getString(R.string.M2_FEEL_INFO), getString(R.string.menu_feel), furl, "POST", true);
				// } else {
				// openWebView(webview.class, getString(R.string.M1_MENU_FEEL),
				// getString(R.string.M2_FEEL_INFO), getString(R.string.menu_feel), furl, "GET", true);
				// }
				openWebView(webview.class, getString(R.string.M1_MENU_FEEL), getString(R.string.M2_FEEL_INFO), getString(R.string.menu_feel), furl, "GET", true);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
	}

}
