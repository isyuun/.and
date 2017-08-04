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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
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

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 7.
 * @version 1.0
 * @see NaviListAdapter
 */

public class AuditionEntryListAdapter extends _BaseListAdapter {

	public AuditionEntryListAdapter(Context context, int resource, List<KPItem> items,
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
		ImageButton btn_play;
		_ImageView mark_mysing;
		Button btn_type;
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
				// res = WidgetUtils.getDrawableID(getContext(), "ic_menu_01");
				// vh.image.setImageDrawable(ic_menu_01);

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

				// res = WidgetUtils.getResourceID(getContext(), "best");
				// vh.best = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "best");
				vh.best = (_ImageView) convertView.findViewById(res);
				if (vh.best != null) {
					vh.best.setVisibility(View.GONE);
				}

				res = WidgetUtils.getResourceID(getContext(), "hot");
				vh.hot = (_ImageView) convertView.findViewById(res);
				if (vh.hot != null) {
					vh.hot.setVisibility(View.GONE);
				}

				res = WidgetUtils.getResourceID(getContext(), "audition");
				vh.audition = (_ImageView) convertView.findViewById(res);
				if (vh.audition != null) {
					vh.audition.setVisibility(View.GONE);
				}

				res = WidgetUtils.getResourceID(getContext(), "first");
				vh.first = (_ImageView) convertView.findViewById(res);
				if (vh.first != null) {
					vh.first.setVisibility(View.GONE);
				}

				res = WidgetUtils.getResourceID(getContext(), "arrow");
				vh.arrow = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_edit");
				vh.btn_edit = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_del");
				vh.btn_del = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_sing");
				vh.btn_sing = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_listen");
				vh.btn_listen = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_play");
				vh.btn_play = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "mark_mysing");
				vh.mark_mysing = (_ImageView) convertView.findViewById(res);
				if (vh.mark_mysing != null) {
					vh.mark_mysing.setVisibility(View.GONE);
				}

				res = WidgetUtils.getResourceID(getContext(), "btn_type");
				vh.btn_type = (Button) convertView.findViewById(res);

				convertView.setTag(vh);
			} catch (Exception e) {

				// e.printStackTrace();
			}

			// TextUtil.setTypeFaceBold(viewHolder.title);
			// TextUtil.setTypeFaceBold(viewHolder.name);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		String value = "";
		if (list != null) {

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
			value += " - " + list.getValue("artist");
			if (vh.title != null) {
				putValue(vh.title, value);
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

			if (vh.hit != null) {
				value = list.getValue("hit");
				value = TextUtil.numberFormat(value);
				putValue(vh.hit, value);
			}

			value = list.getValue("heart");
			value = TextUtil.numberFormat(value);
			if (vh.heart != null) {
				putValue(vh.heart, value);
			}

			value = list.getValue("comment_cnt");
			value = TextUtil.numberFormat(value);
			if (vh.reply != null) {
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

			// if (vh.best != null) {
			// value = list.getString("top_txt");
			// putValue(vh.best, value);
			// }

			value = list.getValue("mark_best");
			if (vh.best != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.best.setVisibility(View.VISIBLE);
				} else {
					vh.best.setVisibility(View.GONE);
				}
			}

			value = list.getValue("mark_hot");
			if (vh.hot != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.hot.setVisibility(View.VISIBLE);
				} else {
					vh.hot.setVisibility(View.GONE);
				}
			}

			value = list.getValue("mark_audition");
			if (vh.audition != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.audition.setVisibility(View.VISIBLE);
				} else {
					vh.audition.setVisibility(View.GONE);
				}
			}

			value = list.getValue("mark_first");
			if (vh.first != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.first.setVisibility(View.VISIBLE);
				} else {
					vh.first.setVisibility(View.GONE);
				}
			}

			// 본인 게시물여부(Y:본인,N:타회원,공란:비로그인)
			value = list.getValue("my_contents");
			if (("Y").equalsIgnoreCase(value)) {
				// 본인
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

			value = list.getValue("song_id");
			if (vh.btn_sing != null) {
				if (!TextUtil.isEmpty(value)) {
					vh.btn_sing.setVisibility(View.VISIBLE);
				} else {
					vh.btn_sing.setVisibility(View.GONE);
				}
				vh.btn_sing.setVisibility(View.GONE);
			}

			value = list.getValue("record_id");
			if (vh.btn_listen != null) {
				if (!TextUtil.isEmpty(value)) {
					vh.btn_listen.setVisibility(View.VISIBLE);
				} else {
					vh.btn_listen.setVisibility(View.GONE);
				}
				vh.btn_listen.setVisibility(View.GONE);
			}

			if (vh.btn_play != null) {
				int visible = View.GONE;
				if (getSelectedPosition() == position) {
					visible = View.VISIBLE;
				} else {
					visible = View.GONE;
				}
				vh.btn_play.setVisibility(visible);
			}

			value = list.getValue("mark_mysing");
			if (vh.mark_mysing != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.mark_mysing.setVisibility(View.VISIBLE);
				} else {
					vh.mark_mysing.setVisibility(View.GONE);
				}
			}

			value = list.getValue("btn_type");
			if (vh.btn_type != null) {
				if (!TextUtil.isEmpty(value)) {
					vh.btn_type.setVisibility(View.VISIBLE);
					putValue(vh.btn_type, list.getValue("btn_text"));
				} else {
					vh.btn_type.setVisibility(View.GONE);
					putValue(vh.btn_type, null);
				}
			}

			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "getView() " + position + "\n" + item.toString(2));
		}

		return super.getView(position, convertView, parent);
	}

}
