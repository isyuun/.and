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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.KPOP
 * filename	:	KPItemListAdapter.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop.data
 *    |_ KPItemListAdapter.java
 * </pre>
 */

package kr.kymedia.karaoke.apps.adpt;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget._ImageViewRounded;
import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

;

/**
 * TODO NOTE:<br>
 * 댓글목록
 *
 * @author isyoon
 * @version 1.0
 * @since 2012. 3. 7.
 */

public class ReplyListAdapter extends _BaseListAdapter {

	ArrayList<Integer> mResouces = new ArrayList<Integer>();
	ArrayList<View> mViews = new ArrayList<View>();

	public ReplyListAdapter(Context context, int resource[], List<KPItem> items, OnClickListener listener, IImageDownLoader imageDownLoader) {
		super(context, resource[0], items, listener, imageDownLoader);

		for (int i = 0; i < resource.length; i++) {
			mResouces.add(resource[i]);
			mViews.add(layoutInflater.inflate(resource[i], null));
		}
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
		_ImageView feel_image;
		TextView feel_text;
		ImageButton arrow;
		ImageButton sing;
		ImageButton listen;
		ImageButton btn_del;
		ImageButton btn_edit;
		ImageButton btn_siren;
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

		// 댓글좌우확인
		String is_master = list.getValue("is_master");

		// A ViewHolder keeps references to children views to avoid unneccessary
		// calls
		// to findViewById() on each row.
		ViewHolder vh = null;

		if (mResouces.size() > 1) {
			// 리스트아이템 리소스가 2이면 convertView/vh 항시생성.
			if (("Y").equalsIgnoreCase(is_master)) {
				res = mResouces.get(0);
			} else {
				res = mResouces.get(1);
			}
			convertView = null;
		} else {
			// 리스트아이템 리소스가 1이면 convertView/vh 그냥놔도.
			res = mResouces.get(0);
		}

		// When convertView is not null, we can reuse it directly, there is no need
		// to reinflate it. We only inflate a new View when the convertView supplied
		// by ListView is null.
		if (convertView == null) {
			// convertView = layoutInflater.inflate(this.resource, null);
			// res = mResouces.get(position%mResouces.size());

			convertView = layoutInflater.inflate(res, null);

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

				res = WidgetUtils.getResourceID(getContext(), "feel_image");
				vh.feel_image = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "feel_text");
				vh.feel_text = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "arrow");
				vh.arrow = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_sing");
				vh.sing = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_listen");
				vh.listen = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_del");
				vh.btn_del = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_edit");
				vh.btn_edit = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "btn_siren");
				vh.btn_siren = (ImageButton) convertView.findViewById(res);

				convertView.setTag(vh);
			} catch (Exception e) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + Log2.getStackTraceString(e));
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
				if (URLUtil.isNetworkUrl(value)) {
					vh.image.setTag(value);
					putURLImage(getContext(), vh.image, value, false, R.drawable.ic_menu_01);
					vh.image.setVisibility(View.VISIBLE);
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
			value = TextUtil.numberFormat(value);
			if (vh.hit != null) {
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

			value = list.getValue("url_comment");
			if (vh.feel_image != null) {
				if (URLUtil.isNetworkUrl(value)) {
					vh.feel_image.setTag(value);
					putURLImage(getContext(), vh.feel_image, value, false, R.drawable.bg_trans);
					vh.feel_image.setVisibility(View.VISIBLE);
				} else {
					vh.feel_image.setVisibility(View.GONE);
				}
			}
			// viewHolder.feel_image.setVisibility(View.GONE);

			value = list.getValue("comment");
			if (vh.feel_text != null) {
				putValue(vh.feel_text, value);
			}

			// //반주곡버튼
			// value = list.getString("song_id");
			// if (vh.sing != null) {
			// if (!TextUtil.isEmpty(value)) {
			// vh.sing.setVisibility(View.VISIBLE);
			// } else {
			// vh.sing.setVisibility(View.GONE);
			// }
			// }
			//
			// //녹음곡버튼
			// value = list.getString("record_id");
			// if (vh.listen != null) {
			// if (!TextUtil.isEmpty(value)) {
			// vh.listen.setVisibility(View.VISIBLE);
			// } else {
			// vh.listen.setVisibility(View.GONE);
			// }
			// }

			// 본인 게시물여부(Y:본인,N:타회원,공란:비로그인)
			value = list.getValue("my_contents");
			if (("Y").equalsIgnoreCase(value)) {
				vh.btn_del.setVisibility(View.VISIBLE);
				vh.btn_edit.setVisibility(View.VISIBLE);
				if (vh.btn_siren != null) {
					vh.btn_siren.setVisibility(View.GONE);
				}
			} else if (("N").equalsIgnoreCase(value)) {
				// 타인
				vh.btn_del.setVisibility(View.GONE);
				vh.btn_edit.setVisibility(View.GONE);
				if (vh.btn_siren != null) {
					vh.btn_siren.setVisibility(View.VISIBLE);
				}
			} else {
				vh.btn_del.setVisibility(View.GONE);
				vh.btn_edit.setVisibility(View.GONE);
				if (vh.btn_siren != null) {
					vh.btn_siren.setVisibility(View.GONE);
				}
			}
			vh.btn_edit.setVisibility(View.GONE);

			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "getView() " + position + "\t" + list.toString(2));

		}

		return super.getView(position, convertView, parent);
	}

}
