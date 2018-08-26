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
 * filename	:	AuditionViewFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ AuditionViewFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps.adpt.AuditionEntryListAdapter;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.OnComplexTouchListener;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 5. 24.
 * @version 1.0
 * @see
 */
class AuditionViewFragment extends playListFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_audtion_view, container, false);

		onCreateView();

		return mRootView;
	}

	/**
	 * <pre>
	 * KP_6034	오디션 상세 정보 요청
	 * 
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 */
	KPnnnn KP_6034;
	/**
	 * <pre>
	 * KP_6035
	 * 	오디션 공개/비공개
	 * 	및 참여곡 	탈락/1위선정 요청
	 * 
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 */
	KPnnnn KP_6035;
	// /**
	// * KP_6036 오디션 참여 녹음곡 목록
	// */
	// KPnnnn KP_6036;
	/**
	 * KP_6038 오디션 개최 이용권 구매
	 */
	@Deprecated
	KPnnnn KP_6038;
	/**
	 * <pre>
	 * KP_6039
	 * 오디션 참여 제한 /
	 * 오디션 녹음곡 듣기
	 * 제한 설정
	 * </pre>
	 */
	KPnnnn KP_6039;

	class ViewHolder {
		_ImageView image;
		ImageButton lock;
		TextView title;
		_ImageView flag;
		TextView name;
		TextView person;
		TextView reward;
		// TextView reply;
		TextView date;
		// TextView best;
		_ImageView best;
		_ImageView hot;
		_ImageView audition;
		_ImageView first;
		ImageButton arrow;
		ImageButton btn_edit;
		ImageButton btn_del;
		ImageButton btn_sing;
		ImageButton btn_listen;
	}

	public ViewHolder getView(Context context, View convertView) {
		ViewHolder vh = new ViewHolder();

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + convertView + vh);

		int res = 0;

		try {
			res = WidgetUtils.getResourceID(context, "image");
			vh.image = (_ImageView) convertView.findViewById(res);
			// res = WidgetUtils.getDrawableID(context, "ic_menu_01");
			// vh.image.setImageDrawable(ic_menu_01);

			res = WidgetUtils.getResourceID(context, "lock");
			vh.lock = (ImageButton) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "title");
			vh.title = (TextView) convertView.findViewById(res);
			WidgetUtils.setTextViewMarquee(vh.title, true);

			res = WidgetUtils.getResourceID(context, "flag");
			vh.flag = (_ImageView) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "name");
			vh.name = (TextView) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "person");
			vh.person = (TextView) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "reward");
			vh.reward = (TextView) convertView.findViewById(res);

			// res = WidgetUtils.getResourceID(context, "reply");
			// vh.reply = (TextView) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "date");
			vh.date = (TextView) convertView.findViewById(res);

			// res = WidgetUtils.getResourceID(context, "best");
			// vh.best = (TextView) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "best");
			vh.best = (_ImageView) convertView.findViewById(res);
			if (vh.best != null) {
				vh.best.setVisibility(View.GONE);
			}

			res = WidgetUtils.getResourceID(context, "hot");
			vh.hot = (_ImageView) convertView.findViewById(res);
			if (vh.hot != null) {
				vh.hot.setVisibility(View.GONE);
			}

			res = WidgetUtils.getResourceID(context, "audition");
			vh.audition = (_ImageView) convertView.findViewById(res);
			if (vh.audition != null) {
				vh.audition.setVisibility(View.GONE);
			}

			res = WidgetUtils.getResourceID(context, "first");
			vh.first = (_ImageView) convertView.findViewById(res);
			if (vh.first != null) {
				vh.first.setVisibility(View.GONE);
			}

			res = WidgetUtils.getResourceID(context, "arrow");
			vh.arrow = (ImageButton) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "btn_edit");
			vh.btn_edit = (ImageButton) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "btn_del");
			vh.btn_del = (ImageButton) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "btn_sing");
			vh.btn_sing = (ImageButton) convertView.findViewById(res);

			res = WidgetUtils.getResourceID(context, "btn_listen");
			vh.btn_listen = (ImageButton) convertView.findViewById(res);

			convertView.setTag(vh);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return vh;
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public void KP_init() {

		super.KP_init();

		KP_6034 = KP_init(mContext, KP_6034);
		KP_6035 = KP_init(mContext, KP_6035);
		// KP_6036 = KP_init(mContext, KP_6036);
		KP_nnnn.p_opcode = "KP_6036";
		KP_6038 = KP_init(mContext, KP_6038);
		KP_6039 = KP_init(mContext, KP_6039);

	}

	AuditionEntryListAdapter mAuditionEntryListAdapter = null;

	/**
	 * <pre>
	 * 파라미터스트링맵생성
	 * </pre>
	 */
	@Override
	protected Map<String, String> KP_0000(KPItem info, KPItem list) {
		super.KP_0000(info, list);

		// if (info == null) {
		// info = getInfo();
		// }
		//
		// if (list == null) {
		// list = getList();
		// }

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return null;
		}

		if (info != null) {
			p_type = info.getValue("type");
		}

		if (TextUtil.isEmpty(p_type)) {
			p_type = list.getValue("type");
		}

		String audition_id = list.getValue("audition_id");
		if (TextUtil.isEmpty(audition_id)) {
			audition_id = getList().getValue("id");
		}
		String uid = list.getValue("uid");

		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "RECORD");
		params.put("page", Integer.toString(page));
		params.put("line", Integer.toString(30));
		params.put("audition_id", audition_id);
		params.put("uid", uid);

		return params;
	}

	/**
	 * <pre>
	 * KP_6034	오디션 상세 정보 요청
	 * </pre>
	 *
	 */
	private void KP_6034() {
		// startLoading(__CLASSNAME__, getMethodName());

		Map<String, String> params = KP_0000(getInfo(), getList());

		if (params != null) {
			KP_6034.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_6034", true, params);
		}
	}

	/**
	 * <pre>
	 * KP_6035
	 * 	오디션 공개/비공개
	 * 	및 참여곡 탈락/1위선정 요청
	 * 
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 * 
	 */
	private void KP_6035(View lock, KPItem info, KPItem list) {
		// startLoading(__CLASSNAME__, getMethodName());

		Map<String, String> params = KP_0000(info, list);

		if (params != null) {
			// 공개여부확인
			String is_on = list.getValue("is_on");

			if (("Y").equalsIgnoreCase(is_on)) {
				mode = "OFF";
				list.putValue("is_on", "N");
			} else {
				mode = "ON";
				list.putValue("is_on", "Y");
			}

			int res = 0;

			if (("ON").equalsIgnoreCase(mode)) {
				res = R.drawable.ic_unlock;
			} else {
				res = R.drawable.ic_lock;
			}

			((ImageButton) lock).setImageResource(res);

			params.put("mode", mode);

			KP_6035.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_6035", true, params);
		}
	}

	/**
	 * <pre>
	 * KP_6035
	 * 	오디션 공개/비공개
	 * 	및 참여곡 탈락/1위선정 요청
	 * 
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 * 
	 */
	private void KP_6035(String btn_type, KPItem info, KPItem list) {
		// startLoading(__CLASSNAME__, getMethodName());

		Map<String, String> params = KP_0000(info, list);

		if (params != null) {
			// 공개여부확인

			if (btn_type.equalsIgnoreCase("1")) {
				// “1”: 오디션 참여
				mode = "";
			} else if (btn_type.equalsIgnoreCase("2")) {
				// “2”: 탈락시키기
				mode = "FAIL";
			} else if (btn_type.equalsIgnoreCase("3")) {
				// “3”: 1위 선정하기
				mode = "CHOICE";
			} else {
				mode = "";
			}

			params.put("mode", mode);

			KP_6035.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_6035", true, params);
		}
	}

	/**
	 * <pre>
	 * KP_6036	오디션 참여 녹음곡 목록
	 * </pre>
	 */
	private void KP_6036() {
		// startLoading(__CLASSNAME__, getMethodName());

		Map<String, String> params = KP_0000(getInfo(), getList());

		if (params != null) {
			boolean clear = true;
			if (page > 1) {
				clear = false;
			} else {
				clear = true;
			}

			KP_nnnn.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_6036", clear, params);
		}
	}

	/**
	 * <pre>
	 * KP_6034	오디션 상세 정보 요청
	 * </pre>
	 *
	 */
	private void KP_6039() {
		// startLoading(__CLASSNAME__, getMethodName());

		Map<String, String> params = KP_0000(getInfo(), getList());

		if (params != null) {
			KP_6039.KP_0000(getApp().p_mid, p_m1, p_m2, "KP_6039", true, params);
		}
	}

	/**
	 * <pre>
	 * KP_6034	오디션 상세 정보 요청
	 * 
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 */
	private void KP6034() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		final View convertView = findViewById(R.id.item_audition_title);
		final ViewHolder vh = getView(mContext, convertView);

		KPItem list = KP_6034.getList(0);

		if (list == null) {
			return;
		}

		int res = 0;
		View v = null;
		String value = "";

		if (vh.image != null) {
			value = list.getValue("url_profile");
			if (URLUtil.isNetworkUrl(value)) {
				vh.image.setTag(value);
				putURLImage(mContext, vh.image, value, true, R.drawable.ic_menu_01);
				vh.image.setVisibility(View.VISIBLE);
			} else {
				vh.image.setVisibility(View.GONE);
			}
		}

		// is_on 오디션 공개/비공개 유무(“Y”: 공개, “N”: 비공개, “”:아이콘 비노출)
		if (vh.lock != null) {
			value = list.getValue("is_on");
			if (value != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.lock.setImageDrawable(WidgetUtils.getDrawable(mContext, "ic_unlock"));
					vh.lock.setVisibility(View.VISIBLE);
				} else if (("N").equalsIgnoreCase(value)) {
					vh.lock.setImageDrawable(WidgetUtils.getDrawable(mContext, "ic_lock"));
					vh.lock.setVisibility(View.VISIBLE);
				} else {
					vh.lock.setVisibility(View.GONE);
				}
			} else {
				vh.lock.setVisibility(View.GONE);
			}
		}

		v = findViewById(R.id.audition_title);
		if (v != null) {
			value = list.getValue("audition_title");
			putValue(v, value);
			WidgetUtils.setTextViewMarquee((TextView) v);
		}

		v = mHeaderView.findViewById(R.id.audition_button_foreground);
		if (v != null) {
			value = list.getValue("btn_text");
			putValue(v, value);
		}

		// btn_type 오디션 버튼 타입
		// “-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
		v = mHeaderView.findViewById(R.id.audition_button_background);
		if (("-1").equalsIgnoreCase(list.getValue("btn_type"))) {
			v.setClickable(false);
			WidgetUtils.setBackground(getApp(), v, R.drawable.btn_myholic);
			((Button) mHeaderView.findViewById(R.id.audition_button_foreground)).setCompoundDrawables(
					null, null, null, null);
		} else if (("1").equalsIgnoreCase(list.getValue("btn_type"))) {
			v.setClickable(true);
			WidgetUtils.setBackground(getApp(), v, R.drawable.btn_normal_counter_01);
			((Button) mHeaderView.findViewById(R.id.audition_button_foreground)).setCompoundDrawables(
					null, null, WidgetUtils.getDrawable(getApp(), R.drawable.ic_action_mic_on), null);
		} else {
			v.setClickable(true);
			WidgetUtils.setBackground(getApp(), v, R.drawable.btn_normal_counter_01);
			((Button) mHeaderView.findViewById(R.id.audition_button_foreground)).setCompoundDrawables(
					null, null, null, null);
		}

		if (vh.title != null) {
			value = list.getValue("title");
			value += " - " + list.getValue("artist");
			putValue(vh.title, value);
		}

		// if (vh.name != null) {
		// value = list.getString("nick");
		// putValue(vh.name, value);
		// }
		if (vh.name != null) {
			value = list.getValue(LOGIN.KEY_NICKNAME);
			putValue(vh.name, value);
			// int lvl = !TextUtil.isEmpty(list.getString("level")) ? TextUtil.parseInt(list.getString("level")) : 0;
			// int color = WidgetUtils.getColor(context, "text_thick");
			// if (lvl == 4) {
			// color = WidgetUtils.getColor(context, "solid_orange");
			// vh.name.setTextColor(color);
			// } else if (lvl == 5) {
			// color = WidgetUtils.getColor(context, "solid_pink");
			// vh.name.setTextColor(color);
			// } else {
			// color = WidgetUtils.getColor(context, "text_thick");
			// vh.name.setTextColor(color);
			// }
		}

		if (vh.flag != null) {
			value = list.getValue("ncode");
			if (value != null) {
				try {
					res = WidgetUtils.getDrawableID(mContext, "img_flag_" + value.toLowerCase());
					vh.flag.setImageResource(res);
					vh.flag.setVisibility(View.VISIBLE);
				} catch (Exception e) {

					// e.printStackTrace();
				}
			} else {
				vh.flag.setVisibility(View.GONE);
			}
		}

		if (vh.person != null) {
			value = list.getValue("max_cnt");
			value = TextUtil.numberFormat(value);
			putValue(vh.person, value);
		}

		if (vh.reward != null) {
			value = list.getValue("premiums");
			value = TextUtil.numberFormat(value);
			putValue(vh.reward, value);
		}

		// if (vh.reply != null) {
		// value = list.getString("comment_cnt");
		// value = TextUtil.numberFormat(value);
		// putValue(vh.reply, value);
		// }

		if (vh.date != null) {
			value = list.getValue("date");
			putValue(vh.date, value);
		}

		if (vh.date != null) {
			value = list.getValue("reg_date");
			putValue(vh.date, value);
		}

		// if (vh.best != null) {
		// value = list.getString("top_txt");
		// putValue(vh.best, value);
		// }

		if (vh.best != null) {
			value = list.getValue("mark_best");
			if (("Y").equalsIgnoreCase(value)) {
				vh.best.setVisibility(View.VISIBLE);
			} else {
				vh.best.setVisibility(View.GONE);
			}
		}

		if (vh.hot != null) {
			value = list.getValue("mark_hot");
			if (("Y").equalsIgnoreCase(value)) {
				vh.hot.setVisibility(View.VISIBLE);
			} else {
				vh.hot.setVisibility(View.GONE);
			}
		}

		if (vh.audition != null) {
			value = list.getValue("mark_audition");
			if (("Y").equalsIgnoreCase(value)) {
				vh.audition.setVisibility(View.VISIBLE);
			} else {
				vh.audition.setVisibility(View.GONE);
			}
		}

		if (vh.first != null) {
			value = list.getValue("mark_first");
			if (("Y").equalsIgnoreCase(value)) {
				vh.first.setVisibility(View.VISIBLE);
			} else {
				vh.first.setVisibility(View.GONE);
			}
		}

		// 본인 게시물여부(Y:본인,N:타회원,공란:비로그인)
		value = list.getValue("my_contents");
		if (("Y").equalsIgnoreCase(value)) {
			if (vh.btn_del != null) {
				vh.btn_del.setVisibility(View.VISIBLE);
			}
			if (vh.btn_edit != null) {
				vh.btn_edit.setVisibility(View.VISIBLE);
			}
		} else if (("N").equalsIgnoreCase(value)) {
			// 타인
			if (vh.btn_del != null) {
				vh.btn_del.setVisibility(View.GONE);
			}
			if (vh.btn_edit != null) {
				vh.btn_edit.setVisibility(View.GONE);
			}
		} else {
			// 비로그인
			if (vh.btn_del != null) {
				vh.btn_del.setVisibility(View.GONE);
			}
			if (vh.btn_edit != null) {
				vh.btn_edit.setVisibility(View.GONE);
			}
		}

		if (vh.btn_sing != null) {
			value = list.getValue("song_id");
			if (!TextUtil.isEmpty(value)) {
				vh.btn_sing.setVisibility(View.VISIBLE);
			} else {
				vh.btn_sing.setVisibility(View.GONE);
			}
			vh.btn_sing.setVisibility(View.GONE);
		}

		if (vh.btn_listen != null) {
			value = list.getValue("record_id");
			if (!TextUtil.isEmpty(value)) {
				vh.btn_listen.setVisibility(View.VISIBLE);
			} else {
				vh.btn_listen.setVisibility(View.GONE);
			}
			vh.btn_listen.setVisibility(View.GONE);
		}
	}

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		if (mAuditionEntryListAdapter == null) {
			mAuditionEntryListAdapter = new AuditionEntryListAdapter(getBaseActivity(), R.layout.item_audition_entry,
					KP_nnnn.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, getApp().getImageDownLoader());
		}

		mAuditionEntryListAdapter.setNotifyOnChange(false);

		mHeaderView = inflate(R.layout.include_audition_title, null, false);
		mHeaderView.setId(R.id.include_audition_title);
		mHeaderView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

		setListAdapter(mAuditionEntryListAdapter);
		// getListView().setDivider(null);

		KP_6034.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[onKPnnnnSuccess][" + opcode + "]" + what);


				KP6034();
				if (TextUtil.isEmpty(mode)) {
					KP_6036();
				}
			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				KPnnnnResult(what, opcode, r_code, r_message, r_info);

			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

		KP_6035.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {

			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[onKPnnnnResult][" + opcode + "]" + what);


				KPnnnnResult(what, opcode, r_code, r_message, r_info);

				if (what == _IKaraoke.STATE_DATA_QUERY_START) {
					return;
				}

				String result_sub_code = "";

				if (r_info != null) {
					result_sub_code = r_info.getValue("result_sub_code");
				}

				if (!TextUtil.isEmpty(result_sub_code)) {
					return;
				}

				// “FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
				if (("FAIL").equalsIgnoreCase(mode) || ("CHOICE").equalsIgnoreCase(mode)) {
					KPItem list = KP_6034.getList(0);

					String btn_type = list.getValue("btn_type");

					if (("FAIL").equalsIgnoreCase(mode) && ("2").equalsIgnoreCase(btn_type)) {
						// KP_6036();
						KPItem item = KP_nnnn.getList(KP_index);
						// “FAIL”: 탈락시키기
						mAuditionEntryListAdapter.remove(item);
						mAuditionEntryListAdapter.notifyDataSetChanged();
					} else if (("CHOICE").equalsIgnoreCase(mode) && ("3").equalsIgnoreCase(btn_type)) {
						// KP_6036();
						// for (int i = 0; i < mAuditionEntryListAdapter.getCount(); i++) {
						// KPItem item = KP_nnnn.getList(i);
						// if (i == KP_index) {
						// //“CHOICE”: 1위선정하기
						// item.putString("mark_first", "Y");
						// } else {
						// item.putString("mark_first", "N");
						// }
						// item.putString("btn_type", null);
						// }
						// mAuditionEntryListAdapter.notifyDataSetChanged();
						refresh();
						setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
					}
				} else {
					KP_6034();
				}
			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

		KP_6039.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message,
					KPItem r_info) {

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[onKPnnnnSuccess][" + opcode + "]");

				onContextItemSelected(R.id.context_play_sing, KP_6034, 0, true);
			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[onKPnnnnResult][" + opcode + "]");

				KPnnnnResult(what, opcode, r_code, r_message, r_info);
			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			@Deprecated
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

		OnComplexTouchListener l;

		// findViewById(R.id.chk_login_check2).setOnTouchListener(this);
		l = new OnComplexTouchListener();
		l.add(findViewById(R.id.audition_button_background));
		l.add(findViewById(R.id.audition_button_foreground));
		l.setOnTouchListener(this);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.KP_nnnn();

		mode = "";
		isAuditionButtons = false;

		KP_6034();
	}

	@Override
	public void KP_list(int page) {
		super.KP_list(page);
		// startLoading(__CLASSNAME__, getMethodName());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);


		super.onItemClick(parent, v, position, id);

		// mListenListAdapter.setSelectedPosition(KP_index);

		onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		String name = getResourceEntryName(view.getId());
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + name + "," + position + "," + id);
		boolean ret = super.onItemLongClick(parent, view, position, id);
		showContextMenu(view, true, false);
		return ret;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		String vn = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_index + ", " + vn);

		KPItem info = KP_nnnn.getInfo();
		KPItem list = getList();
		ArrayList<KPItem> lists = KP_nnnn.getLists();

		if (lists != null && KP_index > -1 && KP_index < lists.size()) {
			KPItem item = lists.get(KP_index);
			list = item;
			list.merge(item);
		}

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		try {
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "info - " + info.toString(2));
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "list - " + list.toString(2));
		} catch (Exception e) {

			e.printStackTrace();
		}

		p_type = "REORD";
		p_type = !TextUtil.isEmpty(list.getValue("type")) ? list.getValue("type") : p_type;

		play_id = list.getValue("record_id");

		if (!TextUtil.isEmpty(list.getValue("id"))) {
			play_id = list.getValue("id");
		}

		if (("REORD").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("record_id"))) {
				play_id = list.getValue("record_id");
			}
		} else if (("UID").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("uid"))) {
				play_id = list.getValue("uid");
			}
		} else if (("SONG").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("song_id"))) {
				play_id = list.getValue("song_id");
			}
		} else if (("ARTIST").equalsIgnoreCase(p_type)) {
			if (!TextUtil.isEmpty(list.getValue("artist_id"))) {
				play_id = list.getValue("artist_id");
			}
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "type - " + p_type);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "play_id - " + play_id);

		if (TextUtil.isEmpty(play_id)) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}

		// listen.xml 에서 메뉴 불러오기
		MenuInflater inflater = getBaseActivity().getMenuInflater();
		inflater.inflate(R.menu.context_listen, menu);

		String title = getString(R.string.context_title_listen);
		if (!TextUtil.isEmpty(list.getValue("title"))) {
			title = list.getValue("title");
		}
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.setHeaderTitle(title);

		// LayoutInflater layout = getBaseActivity().getLayoutInflater();
		// View view = layout.inflate( R.layout.context_view_for_button, null );
		// menu.setHeaderView( view );

		// 내녹음곡듣기시
		MenuItem item = null;
		if (p_m1.equalsIgnoreCase(getString(R.string.M1_MENU_MYHOLIC))
				&& p_m2.equalsIgnoreCase(getString(R.string.M2_MY_REC))) {
			// USER's (사용자의 전체 녹음곡)
			item = menu.findItem(R.id.context_user_record);
			setContextMenuItemVisible(item, false);
			// 다른사용자녹음곡리스트인경우
			if (("UID").equalsIgnoreCase(p_type) && !getApp().p_mid.equalsIgnoreCase(play_id)) {
				// 녹음곡삭제
				item = menu.findItem(R.id.context_delete_listen);
				setContextMenuItemVisible(item, false);
			}
		} else {
			// 녹음곡삭제
			item = menu.findItem(R.id.context_delete_listen);
			setContextMenuItemVisible(item, false);
		}

		// MV(유튜브영상) 유부확인
		String url_vod = list.getValue("url_vod");
		if (!URLUtil.isNetworkUrl(url_vod)) {
			item = menu.findItem(R.id.context_play_movie);
			setContextMenuItemVisible(item, false);
		}

		super.onCreateContextMenu(menu, v, menuInfo);
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

		super.onListItemClick(v);

		KPItem list = KP_nnnn.getList(KP_index);

		final String btn_type = list.getValue("btn_type");

		final String title = ((Button) findViewById(R.id.audition_button_foreground)).getText().toString();
		final String message = list.getValue("btn_message");

		if (v.getId() == R.id.lock) {
			KP_6035(v, KP_nnnn.getInfo(), KP_nnnn.getList(KP_index));
		} else if (v.getId() == R.id.btn_type) {
			getBaseActivity().showAlertDialog(title, message, getString(R.string.btn_title_confirm),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							KP_6035(btn_type, KP_nnnn.getInfo(), KP_nnnn.getList(KP_index));

						}
					}, getString(R.string.btn_title_cancel), null, true, null);
		} else if (v.getId() == R.id.btn_del) {
			onContextItemSelected(R.id.context_delete_listen, KP_nnnn, KP_index, false);
		} else if (v.getId() == R.id.btn_sing) {
			onContextItemSelected(R.id.context_play_sing, KP_nnnn, KP_index, true);
		} else if (v.getId() == R.id.btn_play || v.getId() == R.id.btn_listen) {
			onContextItemSelected(R.id.context_play_listen, KP_nnnn, KP_index, true);
		} else {
		}

	}

	/**
	 * 
	 * <pre>
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 *
	 */
	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn + ", " + (v.getParent() instanceof ListView));


		super.onClick(v);

		try {
			KPItem list = KP_6034.getList(0);

			String btn_type = list.getValue("btn_type");

			if (v.getId() == R.id.lock) {
				KP_6035(v, KP_6034.getInfo(), KP_6034.getList(0));
			} else if (v.getId() == R.id.audition_button_background || v.getId() == R.id.audition_button_foreground) {
				if (("1").equalsIgnoreCase(btn_type)) {
					KP_6039();
				} else if (("2").equalsIgnoreCase(btn_type) || ("3").equalsIgnoreCase(btn_type)) {
					showAuditionButtons();
				}
			} else if (v.getId() == R.id.image) {
				String uid = list.getValue("uid");
				if (!TextUtil.isEmpty(uid)) {
					// onContextItemSelected(mContext, R.id.context_go_holic, info, list, true);
					onContextItemSelected(R.id.context_go_holic, KP_6034, 0, true);
				}
			} else {
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	boolean isAuditionButtons = false;

	/**
	 * 
	 * <pre>
	 * btn_type	오디션 버튼 타입
	 * 	“-1”: 비활성, “1”: 오디션 참여, “2”: 탈락시키기, “3”: 1위 선정하기
	 * mode	“ON”: 공개, “OFF”: 비공개, 
	 * 	“FAIL”: 탈락시키기, “CHOICE”: 1위선정하기
	 * </pre>
	 *
	 */
	public void showAuditionButtons() {
		try {
			KPItem list = KP_6034.getList(0);

			String btn_type = list.getValue("btn_type");

			if (TextUtil.isEmpty(btn_type)) {
				return;
			}

			if (!("2").equalsIgnoreCase(btn_type) && !("3").equalsIgnoreCase(btn_type)) {
				return;
			}

			isAuditionButtons = !isAuditionButtons;

			for (int i = 0; i < KP_nnnn.getListCount(); i++) {
				KPItem item = mAuditionEntryListAdapter.getItem(i);

				if (isAuditionButtons) {
					item.putValue("btn_type", btn_type);
				} else {
					item.putValue("btn_type", null);
				}
			}

			mAuditionEntryListAdapter.notifyDataSetChanged();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onRefresh() {
		// super.onRefresh();
		KP_nnnn();
	}

}
