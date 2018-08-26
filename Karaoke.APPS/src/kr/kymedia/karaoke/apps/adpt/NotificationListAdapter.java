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
 * filename	:	NotificationListAdapter.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.data
 *    |_ NotificationListAdapter.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.adpt;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 *
 * TODO
 * NOTE:
 *
 * @author isyoon
 * @since 2012. 11. 15.
 * @version 1.0
 * @see NotificationListAdapter.java
 */

public class NotificationListAdapter extends _BaseListAdapter {

	public NotificationListAdapter(Context context, int resource, List<KPItem> items,
			OnClickListener listener, IImageDownLoader imageDownLoader) {
		super(context, resource, items, listener, imageDownLoader);
	}

	class ViewHolder {
		TextView title;
		CheckBox check;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		int res = 0;

		// ListView lv = (ListView) parent;
		// boolean check = lv.isItemChecked(position);

		if (items == null || items.isEmpty()) {
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
				res = WidgetUtils.getResourceID(getContext(), "title");
				vh.title = (TextView) convertView.findViewById(res);
				WidgetUtils.setTextViewMarquee(vh.title, false);

				res = WidgetUtils.getResourceID(getContext(), "check");
				vh.check = (CheckBox) convertView.findViewById(res);

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

			value = list.getValue("title");
			if (vh.title != null) {
				putValue(vh.title, value);
			}

			value = list.getValue("is_on");
			if (vh.check != null) {
				vh.check.setVisibility(View.VISIBLE);
				// 구매확인
				boolean checked = Boolean.parseBoolean(value.toLowerCase());
				vh.check.setChecked(checked);
			}

		}

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "getView() " + vh.check + position + "\n" + list.toString(2));

		return super.getView(position, convertView, parent);
	}
}
