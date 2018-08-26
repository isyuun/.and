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
 * project	:	Karaoke.KPOP.APP
 * filename	:	MyHolicFragment4.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ MyHolicFragment4.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.apps.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 9. 10.
 * @version 1.0
 * @see
 */
class MyHolicFragment3 extends MyHolicFragment2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_holic3, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KPnnnn() {

		super.KPnnnn();

		KPItem list = KP_nnnn.getList(0);

		// ViewGroup include_profile_my = (ViewGroup) findViewById(R.id.include_profile_my);
		ViewGroup include_profile_people = (ViewGroup) findViewById(R.id.include_profile_people);
		// ViewGroup include_profile_time = (ViewGroup) findViewById(R.id.include_profile_time);

		// int res = 0;
		View v = null;
		String value = "";

		try {
			uid = value = list.getValue("uid");
			try {
				if (value != null) {
					if (getApp().p_mid.equalsIgnoreCase(value)) {
						v = include_profile_people.findViewById(R.id.chk_star);
						v.setVisibility(View.INVISIBLE);
						v = include_profile_people.findViewById(R.id.btn_message);
						v.setVisibility(View.VISIBLE);
					} else {
						v = include_profile_people.findViewById(R.id.chk_star);
						v.setVisibility(View.VISIBLE);
						v = include_profile_people.findViewById(R.id.btn_message);
						v.setVisibility(View.INVISIBLE);
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}

			value = list.getValue("message_cnt");
			v = include_profile_people.findViewById(R.id.btn_message);
			if (v != null && value != null) {
				if (TextUtils.isDigitsOnly(value) && Integer.parseInt(value) > 0) {
					((ImageButton) v).setImageResource(R.drawable.btn_letter_yes);
				} else {
					((ImageButton) v).setImageResource(R.drawable.btn_letter_no);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {

		super.onClick(v);
	}

}
