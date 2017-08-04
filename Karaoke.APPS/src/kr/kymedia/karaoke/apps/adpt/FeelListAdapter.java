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
 * filename	:	KPItemListAdapter.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.data
 *    |_ KPItemListAdapter.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.adpt;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget._ImageViewRounded;
import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 7.
 * @version 1.0
 * @see NaviListAdapter.java
 */

public class FeelListAdapter extends _BaseListAdapter {

	public FeelListAdapter(Context context, int resource, List<KPItem> items,
			OnClickListener listener, IImageDownLoader imageDownLoader) {
		super(context, resource, items, listener, imageDownLoader);
		// TODO Auto-generated constructor stub
	}

	class ViewHolder {
		_ImageViewRounded image;
		ImageButton lock;
		TextView title;
		_ImageView flag;
		TextView name;
		TextView hit;
		TextView heart;
		TextView reply;
		TextView date;
		TextView best;
		ImageButton arrow;
		ImageButton link;
		ImageButton sing;
		ImageButton listen;
		_ImageView feel_image;
		TextView feel_text;
		ImageButton btn_feel_edit;
		ImageButton btn_feel_del;
		ImageButton btn_siren;
		ViewGroup view0;
		ViewGroup view1;
		CheckBox chk_heart;
		View btn_heart;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int res = 0;

		// ListView lv = (ListView) parent;
		// boolean check = lv.isItemChecked(position);

		if (items.isEmpty()) {
			return convertView;
		}

		KPItem list = items.get(position);

		// A ViewHolder keeps references to children views to avoid unneccessary
		// calls
		// to findViewById() on each row.
		ViewHolder vh = null;

		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			convertView = layoutInflater.inflate(this.resource, null);

			// setEanbleOnItemClickListener((ViewGroup) convertView, this);

			// Creates a ViewHolder and store references to the two children views
			// we want to bind data to.
			vh = new ViewHolder();

			try {
				res = WidgetUtils.getResourceID(getContext(), "image");
				vh.image = (_ImageViewRounded) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "lock");
				vh.lock = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "title");
				vh.title = (TextView) convertView.findViewById(res);
				WidgetUtils.setTextViewMarquee(vh.title, true);

				res = WidgetUtils.getResourceID(getContext(), "flag");
				vh.flag = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "name");
				vh.name = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "hit");
				vh.hit = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "heart");
				vh.heart = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "reply");
				vh.reply = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "date");
				vh.date = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "best");
				vh.best = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "arrow");
				vh.arrow = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_link");
				vh.link = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_sing");
				vh.sing = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_listen");
				vh.listen = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "feel_image");
				vh.feel_image = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "feel_text");
				vh.feel_text = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_feel_edit");
				vh.btn_feel_edit = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_feel_del");
				vh.btn_feel_del = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_siren");
				vh.btn_siren = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "view0");
				vh.view0 = (ViewGroup) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "view1");
				vh.view1 = (ViewGroup) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_heart");
				vh.btn_heart = convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "chk_heart");
				vh.chk_heart = (CheckBox) convertView.findViewById(res);

			} catch (Exception e) {

				// e.printStackTrace();
				Log.wtf(__CLASSNAME__, getMethodName() + "\n" + Log.getStackTraceString(e));
			}

			convertView.setTag(vh);
			// TextUtil.setTypeFaceBold(viewHolder.title);
			// TextUtil.setTypeFaceBold(viewHolder.name);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		String value = "";

		if (vh != null && list != null) {

			value = list.getValue("url_profile");
			if (vh.image != null) {
				vh.image.setVisibility(View.INVISIBLE);
				// vh.image.setImageResource(R.drawable.ic_menu_01);
				if (URLUtil.isNetworkUrl(value)) {
					vh.image.setTag(value);
					putURLImage(getContext(), vh.image, value, false, R.drawable.ic_menu_01);
					// vh.image.setVisibility(View.VISIBLE);
				} else {
					vh.image.setVisibility(View.GONE);
				}
			}

			value = list.getValue("is_on");
			if (vh.lock != null) {
				if (value != null) {
					if (("Y").equalsIgnoreCase(value)) {
						vh.lock.setImageDrawable(WidgetUtils.getDrawable(getContext(), "ic_unlock"));
						vh.lock.setVisibility(View.VISIBLE);
					} else if (("N").equalsIgnoreCase(value)) {
						vh.lock.setImageDrawable(WidgetUtils.getDrawable(getContext(), "ic_lock"));
						vh.lock.setVisibility(View.VISIBLE);
					} else {
						vh.lock.setVisibility(View.GONE);
					}
				} else {
					vh.lock.setVisibility(View.GONE);
				}
			}

			value = list.getValue("title");
			if (vh.title != null) {
				putValue(vh.title, value);
				vh.title.setVisibility(View.INVISIBLE);
			}

			value = list.getValue("ncode");
			if (vh.flag != null) {
				if (value != null) {
					try {
						vh.flag.setImageDrawable(WidgetUtils.getDrawable(getContext(), "img_flag_" + value.toLowerCase()));
					} catch (Exception e) {

						// e.printStackTrace();
					}
				} else {
					vh.flag.setVisibility(View.GONE);
				}
			}

			value = list.getValue(LOGIN.KEY_NICKNAME);
			if (vh.name != null) {
				putValue(vh.name, value);
				// int lvl = !TextUtil.isEmpty(list.getString("level")) ? TextUtil.parseInt(list.getString("level")) : 0;
				// int color = WidgetUtils.getColor(getContext(), "text_thick");
				// if (lvl == 4) {
				// color = WidgetUtils.getColor(getContext(), "solid_orange");
				// vh.name.setTextColor(color);
				// } else if (lvl == 5) {
				// color = WidgetUtils.getColor(getContext(), "solid_pink");
				// vh.name.setTextColor(color);
				// } else {
				// color = WidgetUtils.getColor(getContext(), "text_thick");
				// vh.name.setTextColor(color);
				// }
			}

			value = list.getValue("hit");
			if (vh.hit != null) {
				value = TextUtil.numberFormat(value);
				putValue(vh.hit, value);
			}

			value = list.getValue("heart");
			if (vh.heart != null) {
				value = TextUtil.numberFormat(value);
				putValue(vh.heart, value);
			}

			value = list.getValue("comment_cnt");
			if (vh.reply != null) {
				value = TextUtil.numberFormat(value);
				putValue(vh.reply, value);
			}

			value = list.getValue("date");
			if (vh.date != null) {
				putValue(vh.date, value);
			}

			value = list.getValue("reg_date");
			if (vh.date != null) {
				putValue(vh.date, value);
			}

			value = list.getValue("top_txt");
			if (vh.best != null) {
				putValue(vh.best, value);
			}

			value = list.getValue("url_comment");
			// if (vh.feel_image != null) {
			// if (URLUtil.isNetworkUrl(value)) {
			// vh.feel_image.setTag(value);
			// putURLImage(getContext(), vh.feel_image, value, false, R.drawable.bg_trans);
			// vh.feel_image.setVisibility(View.VISIBLE);
			//
			// RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vh.view1
			// .getLayoutParams();
			// params.topMargin = (int) Util.dp2px(context, -30);
			// vh.view1.setLayoutParams(params);
			// } else {
			// vh.feel_image.setVisibility(View.GONE);
			//
			// RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vh.view1
			// .getLayoutParams();
			// params.topMargin = 0;
			// vh.view1.setLayoutParams(params);
			// }
			// }

			value = list.getValue("comment");
			if (vh.feel_text != null) {
				// if (feel_image) {
				// vh.feel_text.setMaxLines(2);
				// } else {
				// vh.feel_text.setMaxLines(5);
				// }
				putHtml(vh.feel_text, value);
			}

			// 링크버튼
			value = list.getValue("furl");
			// if (vh.link != null) {
			// if (URLUtil.isNetworkUrl(value)) {
			// vh.link.setVisibility(View.VISIBLE);
			// } else {
			// vh.link.setVisibility(View.GONE);
			// }
			// vh.link.setVisibility(View.GONE);
			// }
			if (vh.link != null) {
				vh.link.setVisibility(View.GONE);
			}
			if (vh.feel_text != null) {
				vh.feel_text.setTextAppearance(getContext(), android.R.attr.textAppearanceSmall);
				if (TextUtil.isNetworkUrl(value)) {
					vh.feel_text.setTextColor(getContext().getResources().getColor(R.color.solid_blue));
				} else {
					vh.feel_text.setTextColor(getContext().getResources().getColor(R.color.solid_black));
				}
			}

			// 반주곡버튼
			value = list.getValue("song_id");
			// v = findViewById(R.id.btn_sing);
			if (vh.sing != null) {
				if (!TextUtil.isEmpty(value)) {
					vh.sing.setVisibility(View.VISIBLE);
				} else {
					vh.sing.setVisibility(View.GONE);
				}
			}

			// 녹음곡버튼
			value = list.getValue("record_id");
			// v = findViewById(R.id.btn_listen);
			if (vh.listen != null) {
				if (!TextUtil.isEmpty(value)) {
					vh.listen.setVisibility(View.VISIBLE);
				} else {
					vh.listen.setVisibility(View.GONE);
				}
			}

			value = list.getValue("is_heart");
			if (vh.chk_heart != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.chk_heart.setChecked(true);
				} else {
					vh.chk_heart.setChecked(false);
				}
			}

			// 본인 게시물여부(Y:본인,N:타회원,공란:비로그인)
			value = list.getValue("my_contents");
			if (("Y").equalsIgnoreCase(value)) {
				// 본인
				if (vh.btn_feel_del != null) {
					vh.btn_feel_del.setVisibility(View.VISIBLE);
				}
				if (vh.btn_feel_edit != null) {
					vh.btn_feel_edit.setVisibility(View.VISIBLE);
				}
				if (vh.btn_heart != null) {
					vh.btn_heart.setVisibility(View.GONE);
				}
				if (vh.btn_siren != null) {
					vh.btn_siren.setVisibility(View.GONE);
				}
			} else if (("N").equalsIgnoreCase(value)) {
				// 타인
				if (vh.btn_feel_del != null) {
					vh.btn_feel_del.setVisibility(View.INVISIBLE);
				}
				if (vh.btn_feel_edit != null) {
					vh.btn_feel_edit.setVisibility(View.GONE);
				}
				if (vh.btn_heart != null) {
					vh.btn_heart.setVisibility(View.VISIBLE);
				}
				if (vh.btn_siren != null) {
					vh.btn_siren.setVisibility(View.VISIBLE);
				}
			} else {
				// 비로그인
				if (vh.btn_feel_del != null) {
					vh.btn_feel_del.setVisibility(View.INVISIBLE);
				}
				if (vh.btn_feel_edit != null) {
					vh.btn_feel_edit.setVisibility(View.GONE);
				}
				if (vh.btn_heart != null) {
					vh.btn_heart.setVisibility(View.GONE);
				}
				if (vh.btn_siren != null) {
					vh.btn_siren.setVisibility(View.GONE);
				}
			}

			// 필상세에서처리
			if (vh.btn_feel_del != null) {
				vh.btn_feel_del.setVisibility(View.INVISIBLE);
			}
			if (vh.btn_feel_edit != null) {
				vh.btn_feel_edit.setVisibility(View.GONE);
			}
			if (vh.btn_heart != null) {
				vh.btn_heart.setVisibility(View.GONE);
			}
			if (vh.btn_siren != null) {
				vh.btn_siren.setVisibility(View.GONE);
			}

			// 수정기능삭제
			if (vh.btn_feel_edit != null) {
				vh.btn_feel_edit.setVisibility(View.GONE);
			}

			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "getView() " + position + "\n" + item.toString(2));

		}

		return super.getView(position, convertView, parent);
	}

}
