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

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import kr.kymedia.karaoke.widget._ImageView;
//import kr.kymedia.karaoke.widget.ImageButton;
//import kr.kymedia.karaoke.widget.ImageView;
import android.widget.TextView;

import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
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

public class SingListAdapter extends _BaseListAdapter {

	Locale locale;

	public SingListAdapter(Context context, int resource, List<KPItem> items,
			OnClickListener listener, IImageDownLoader imageDownLoader) {
		super(context, resource, items, listener, imageDownLoader);
		locale = context.getResources().getConfiguration().locale;
	}

	class ViewHolder {
		TextView order;
		TextView is_new;
		TextView id;
		TextView title;
		TextView name;
		_ImageView image;
		ImageButton mic;
		ImageButton movie;
		ImageButton arrow;
		ImageButton btn_sing;
		ImageButton btn_listen;
		ImageButton btn_play;
		_ImageView mark_mysing;
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
				res = WidgetUtils.getResourceID(getContext(), "order");
				vh.order = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "is_new");
				vh.is_new = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "id");
				vh.id = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "title");
				vh.title = (TextView) convertView.findViewById(res);
				WidgetUtils.setTextViewMarquee(vh.title, true);

				res = WidgetUtils.getResourceID(getContext(), "name");
				vh.name = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "image");
				vh.image = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "arrow");
				vh.arrow = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "mic");
				vh.mic = (ImageButton) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "movie");
				vh.movie = (ImageButton) convertView.findViewById(res);

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

				convertView.setTag(vh);

			} catch (Exception e) {
				// TODO Auto-generated catch bmovie
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

			if (vh.order != null) {
				value = Integer.toString(position + 1);
				putValue(vh.order, value);
			}

			value = list.getValue("is_new");
			if (vh.is_new != null) {
				if (value != null) {
					if (("Y").equalsIgnoreCase(value)) {
						res = WidgetUtils.getResource(getContext(), "hint_item_is_new", "string");
						putValue(vh.is_new, getContext().getString(res));
					} else {
						putValue(vh.is_new, "");
					}
				} else {
					vh.is_new.setVisibility(View.GONE);
				}
			}

			value = list.getValue("song_id");
			if (vh.id != null) {
				putValue(vh.id, value);
			}

			if (!("KO").equalsIgnoreCase(locale.getLanguage()) || TextUtil.isEmpty(value)
					|| !TextUtils.isDigitsOnly(value)) {
				vh.id.setVisibility(View.GONE);
			} else {
				vh.id.setVisibility(View.VISIBLE);
			}

			value = list.getValue("title");
			if (vh.title != null) {
				putValue(vh.title, value);
			}

			value = list.getValue("artist");
			if (vh.name != null) {
				putValue(vh.name, value);
			}

			value = list.getValue("url_img");
			if (vh.image != null) {
				if (value != null) {
					vh.image.setTag(value);
					putURLImage(getContext(), vh.image, value, false, R.drawable.ic_menu_01);
					vh.image.setVisibility(View.VISIBLE);
				} else {
					vh.image.setVisibility(View.GONE);
				}
			}

			value = list.getValue("url_vod_skym");
			if (vh.mic != null) {
				if (URLUtil.isNetworkUrl(value)) {
					vh.mic.setVisibility(View.VISIBLE);
				} else {
					vh.mic.setVisibility(View.GONE);
				}
			}

			value = list.getValue("url_vod");
			if (vh.movie != null) {
				if (URLUtil.isNetworkUrl(value)) {
					vh.movie.setVisibility(View.VISIBLE);
				} else {
					vh.movie.setVisibility(View.GONE);
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
				if (getSelectedPosition() == position) {
					vh.btn_play.setVisibility(View.VISIBLE);
				} else {
					vh.btn_play.setVisibility(View.GONE);
				}
			}

			value = list.getValue("mark_mysing");
			if (vh.mark_mysing != null) {
				if (("Y").equalsIgnoreCase(value)) {
					vh.mark_mysing.setVisibility(View.VISIBLE);
				} else {
					vh.mark_mysing.setVisibility(View.GONE);
				}
			}

			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "getView() " + position + "\n" + item.toString(2));

		}

		return super.getView(position, convertView, parent);
	}

}
