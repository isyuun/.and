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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 7.
 * @version 1.0
 * @see NaviListAdapter.java
 */

public class MenuListAdapter extends _BaseListAdapter {

	public MenuListAdapter(Context context, int resource, List<KPItem> items,
			OnClickListener listener, IImageDownLoader imageDownLoader) {
		super(context, resource, items, listener, imageDownLoader);
	}

	class ViewHolder {
		View head;
		View menu;
		TextView id;
		TextView text;
		TextView title;
		TextView name;
		_ImageView image;
		ImageButton arrow;
		_ImageView flag;
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
				res = WidgetUtils.getResourceID(getContext(), "head");
				vh.head = convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "menu");
				vh.menu = convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "id");
				vh.id = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "text");
				vh.text = (TextView) convertView.findViewById(res);
				WidgetUtils.setTextViewMarquee(vh.text, true);

				res = WidgetUtils.getResourceID(getContext(), "title");
				vh.title = (TextView) convertView.findViewById(res);
				WidgetUtils.setTextViewMarquee(vh.title, true);

				res = WidgetUtils.getResourceID(getContext(), "flag");
				vh.flag = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "name");
				vh.name = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "image");
				vh.image = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "arrow");
				vh.arrow = (ImageButton) convertView.findViewById(res);

				convertView.setTag(vh);

			} catch (Exception e) {

				// e.printStackTrace();
			}

			// TextUtil.setTypeFaceBold(viewHolder.title);
			// TextUtil.setTypeFaceBold(viewHolder.artist);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		String value = "";
		if (list != null) {

			value = list.getValue("song_id");
			if (vh.id != null) {
				putValue(vh.id, value);
				vh.id.setVisibility(View.GONE);
			}

			value = list.getValue("menu_name");
			if (vh.title != null) {
				putValue(vh.title, value);
			}

			if (vh.title != null) {
				value = "";
				if (!TextUtil.isEmpty(list.getValue("title"))) {
					value = list.getValue("title");
				}

				if (!TextUtil.isEmpty(list.getValue("artist"))) {
					value += " - " + list.getValue("artist");
				}

				if (!TextUtil.isEmpty(value)) {
					putValue(vh.title, value);
				}
			}

			value = list.getValue("menu_artist");
			if (vh.name != null) {
				putValue(vh.name, value);
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

			value = list.getValue("ncode");
			if (vh.flag != null) {
				if (value != null) {
					try {
						vh.flag.setImageDrawable(WidgetUtils.getDrawable(getContext(),
								"img_flag_" + value.toLowerCase()));
					} catch (Exception e) {

						// e.printStackTrace();
					}
				} else {
					vh.flag.setVisibility(View.GONE);
				}
			}

			value = list.getValue("url_img");
			if (vh.image != null) {
				if (TextUtil.isNetworkUrl(value)) {
					// Log.e(__CLASSNAME__, getMethodName() + value);
					vh.image.setTag(value);
					putURLImage(getContext(), vh.image, value, false, R.drawable.ic_menu_01);
					vh.image.setVisibility(View.VISIBLE);
				}
			}

			value = list.getValue("url_profile");
			if (vh.image != null) {
				if (TextUtil.isNetworkUrl(value)) {
					// Log.e(__CLASSNAME__, getMethodName() + value);
					vh.image.setTag(value);
					putURLImage(getContext(), vh.image, value, false, R.drawable.ic_menu_01);
					vh.image.setVisibility(View.VISIBLE);
				}
			}

			value = list.getValue("icon");
			if (vh.image != null) {
				if (!TextUtil.isEmpty(value)) {
					// Log.e(__CLASSNAME__, getMethodName() + value);
					vh.image.setTag(value);
					// putURLImage(getContext(), vh.image, value, false, R.drawable.ic_menu_01);
					vh.image.setImageDrawable(WidgetUtils.getDrawable(getContext(), value));
					vh.image.setPadding(6, 6, 6, 6);
					vh.image.setVisibility(View.VISIBLE);
				}
			}

			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "getView() " + position + "\n" + item.toString(2));

			value = list.getValue("menu_name");
			if (vh.text != null) {
				putValue(vh.text, value);
			}

			// 메뉴분류명
			String menu_id = list.getValue("menu_id");
			if (TextUtil.isEmpty(menu_id)) {
				if (vh.head != null) {
					vh.head.setVisibility(View.VISIBLE);
				}
				if (vh.menu != null) {
					vh.menu.setVisibility(View.GONE);
				}
			} else {
				if (vh.head != null) {
					vh.head.setVisibility(View.GONE);
				}
				if (vh.menu != null) {
					vh.menu.setVisibility(View.VISIBLE);
				}
			}

		}

		return super.getView(position, convertView, parent);
	}

	@Override
	public void release() {

		// super.release();
	}

}
