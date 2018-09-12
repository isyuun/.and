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
 * filename	:	TabsPagerTabsActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ TabsPagerTabsActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.viewpagerindicator.TitlePageIndicator2;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * 
 * <pre>
 * 탭버튼액티비티(상품권화면)
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 6. 4.
 * @version 1.0
 */
public class TabsPagerActivity3 extends TabsPagerActivity2 implements OnTouchListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	ViewGroup mTabsButton;

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		TitlePageIndicator2 indicator = (TitlePageIndicator2) findViewById(R.id.indicator);
		indicator.setVisibility(View.GONE);

		mTabsButton = (ViewGroup) findViewById(R.id.tabs);

	}

	@Override
	public void clear() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.clear();

		mTabsButton.removeAllViews();
	}

	/**
	 * <pre>
	 * TODO 탭대신버튼으로대체
	 * </pre>
	 */
	@Override
	public void addTab(String tag, String name, Class<?> cls, Bundle args) {

		super.addTab(tag, name, cls, args);

		final float density = getResources().getDisplayMetrics().density;

		Button tabButton = new Button(getApp().getApplicationContext());
		tabButton.setTextColor(getColors(R.color.solid_white));
		tabButton.setTypeface(Typeface.create("", Typeface.BOLD));
		tabButton.setText(name);
		tabButton.setClickable(false);
		tabButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				String vn = getResourceEntryName(v.getId());
				String cn = v.getClass().getSimpleName();
				if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);


				int position = 0;
				for (int i = 0; i < mTabsButton.getChildCount(); i++) {
					View c = mTabsButton.getChildAt(i);
					if (c.equals(v)) {
						position = i;
						break;
					}
				}

				final int index = position;

				setCurrentFragment(index);
				selectTabButton(index);
				v.setClickable(false);
				v.setPressed(true);
				return false;
			}
		});

		// 코멘트풀면아주골때리는일생긴다~~~한번해봥~~~
		// LayoutParams params = new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// (int) (50 * density)) {
		// });
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (50 * density));
		params.weight = 1.0f;
		mTabsButton.addView(tabButton, params);

		for (int i = 0; i < mTabsButton.getChildCount(); i++) {
			View v = mTabsButton.getChildAt(i);

			int res = R.drawable.btn_tab_01;
			if (getCount() > 1) {
				if (i == 0) {
					res = R.drawable.btn_tab_left_01;
				} else if (i == getCount() - 1) {
					res = R.drawable.btn_tab_right_01;
				} else {
					res = R.drawable.btn_tab_middle_01;
				}
			} else {
				res = R.drawable.btn_tab_middle_01;
			}
			res = R.drawable.btn_tab_01;
			WidgetUtils.setBackground(getApp(), v, res);
		}

	}

	private void selectTabButton(int position) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + position);

		for (int i = 0; i < mTabsButton.getChildCount(); i++) {
			View v = mTabsButton.getChildAt(i);

			if (i == position) {
				v.setPressed(true);
				// ((Button)v).setTextColor(getColors(R.color.solid_black));
			} else {
				v.setPressed(false);
				// ((Button)v).setTextColor(getColors(R.color.solid_white));
			}
		}
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public void setCurrentFragment(int index) {

		super.setCurrentFragment(index);

		selectTabButton(index);

	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public void onPageSelected(int position) {

		super.onPageSelected(position);

		selectTabButton(position);
	}

	@Override
	protected void onResume() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onResume();

		selectTabButton(getCurrentPosition());
	}

}
