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
 * project	:	Karaoke.APP
 * filename	:	FlagListAdapter.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.data.adpt
 *    |_ FlagListAdapter.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.adpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import kr.kymedia.karaoke.util.Util;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 10. 30.
 * @version 1.0
 * @see FlagListAdapter.java
 */
public class FlagListAdapter extends ArrayAdapter<String> {
	// android.R.layout.simple_list_item_
	int textViewResourceId;
	HashMap<String, String> country = new HashMap<String, String>();
	Context context;

	public FlagListAdapter(Context context, int textViewResourceId, ArrayList<String> nnames, HashMap<String, String> country) {
		super(context, textViewResourceId, nnames);
		this.context = context;
		this.textViewResourceId = textViewResourceId;
		this.country = country;
	}

	private void getCountry(View v, int position) {
		// Log.v("count", Integer.toString(getCount()));
		String nname = getItem(position);
		TextView cpText = (TextView) v.findViewById(android.R.id.text1);
		// cpText.setTextSize(14);
		cpText.setText(nname);

		String ncode = this.country.get(nname);
		Drawable flag = WidgetUtils.getDrawable(getContext(), "img_flag_" + ncode.toLowerCase());
		int w = (int) Util.dp2px(getContext(), 30.0f);
		int h = (int) Util.dp2px(getContext(), 25.0f);
		flag = WidgetUtils.resize(getContext(), flag, w, h);

		cpText.setCompoundDrawablesWithIntrinsicBounds(flag, null, null, null);
		cpText.setCompoundDrawablePadding(10);
		cpText.setTextColor(context.getResources().getColor(R.color.black));

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(textViewResourceId, null);
		}

		getCountry(v, position);

		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// notifyDataSetChanged();
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(android.R.layout.simple_spinner_dropdown_item, null);
		}

		getCountry(v, position);

		return v;
	}

	public int getFlagsIndex(String ncode) {
		try {
			Set<String> nnames = country.keySet();
			for (String nname : nnames) {
				if (country.get(nname).equalsIgnoreCase(ncode)) {
					return getPosition(nname);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return -1;
	}

	public String getFlagsNcode(int index) {
		try {
			String nname = getItem(index);
			return this.country.get(nname);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "";
	}
}
