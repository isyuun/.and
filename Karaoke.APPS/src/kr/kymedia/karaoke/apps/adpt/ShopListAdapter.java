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
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
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

public class ShopListAdapter extends _BaseListAdapter {

	public ShopListAdapter(Context context, int resource, List<KPItem> items,
			OnClickListener listener, IImageDownLoader imageDownLoader) {
		super(context, resource, items, listener, imageDownLoader);
		// TODO Auto-generated constructor stub
	}

	class ViewHolder {
		_ImageView image1;
		_ImageView image2;
		_ImageView image3;
		TextView title;
		TextView price;
		// TextView unit;
		Button button;
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
				res = WidgetUtils.getResourceID(getContext(), "image1");
				vh.image1 = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "image2");
				vh.image2 = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "image3");
				vh.image3 = (_ImageView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "title");
				vh.title = (TextView) convertView.findViewById(res);
				WidgetUtils.setTextViewMarquee(vh.title, true);

				res = WidgetUtils.getResourceID(getContext(), "price");
				vh.price = (TextView) convertView.findViewById(res);

				// res = WidgetUtils.getResourceID(getContext(), "unit");
				// vh.unit = (TextView) convertView.findViewById(res);

				res = WidgetUtils.getResourceID(getContext(), "button");
				vh.button = (Button) convertView.findViewById(res);

				convertView.setTag(vh);

			} catch (Exception e) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + Log2.getStackTraceString(e));
			}

			// TextUtil.setTypeFaceBold(viewHolder.title);
			// TextUtil.setTypeFaceBold(viewHolder.artist);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		// Bind the data efficiently with the holder.
		String value = "";
		if (list != null) {

			value = list.getValue("url_img1");
			if (vh.image1 != null) {
				if (value != null) {
					vh.image1.setTag(value);
					putURLImage(getContext(), vh.image1, value, false, R.drawable.bg_trans);
					vh.image1.setVisibility(View.VISIBLE);
				} else {
					vh.image1.setVisibility(View.GONE);
				}
			}

			value = list.getValue("url_img2");
			if (vh.image2 != null) {
				if (value != null) {
					vh.image2.setTag(value);
					putURLImage(getContext(), vh.image2, value, false, R.drawable.bg_trans);
					vh.image2.setVisibility(View.VISIBLE);
				} else {
					vh.image2.setVisibility(View.GONE);
				}
			}

			value = list.getValue("url_img3");
			if (vh.image3 != null) {
				if (value != null) {
					vh.image3.setTag(value);
					putURLImage(getContext(), vh.image3, value, false, R.drawable.bg_trans);
					vh.image3.setVisibility(View.VISIBLE);
				} else {
					vh.image3.setVisibility(View.INVISIBLE);
				}
			}

			value = list.getValue(LOGIN.KEY_GOODSNAME);
			if (vh.title != null) {
				putValue(vh.title, value);
			}

			value = list.getValue("goodsprice");
			if (vh.price != null) {
				putValue(vh.price, value);
			}

			// if (vh.unit != null) {
			// value = list.getString("currency");
			// putValue(vh.unit, value);
			// }

			value = list.getValue("using_yn");
			if (vh.button != null) {
				// 구매확인
				if (("Y").equalsIgnoreCase(value)) {
					vh.button.setText(WidgetUtils.getResource(getContext(), "title_ticket_goodsname_use", "string"));
				} else {
					vh.button.setText(WidgetUtils.getResource(getContext(), "title_ticket_goodsname_iap_msg_buy", "string"));
				}
				value = list.getValue("text");
				vh.button.setText(value);
			}

			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, "getView() " + position + "\n" + item.toString(2));

		}

		return super.getView(position, convertView, parent);
	}

}
