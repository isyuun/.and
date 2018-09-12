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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	.prj
 * filename	:	BaseActivity.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseActivity.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import kr.kymedia.karaoke.app.AppCompatFragmentActivity;

/**
 * <pre>
 *  으~~~시발~~~개썅바~~~개젖됬네
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-10-20
 */
class BaseActivity extends AppCompatFragmentActivity {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s() ", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	/**
	 * 될수있으믄쓰지말~~~고 getApp()쓴다.
	 *
	 * @see BaseActivity1#getApp()
	 */
	@Deprecated
	@Override
	public Context getApplicationContext() {

		return super.getApplicationContext();
	}

	/**
	 * 어플리케이션확인
	 */
	protected _BaseApplication getApp() {
		// return ((_Application) getApp());
		try {
			return (_BaseApplication) getApplication();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String getResourceEntryName(int resId) {
		String name = "";

		try {
			name = getResources().getResourceEntryName(resId);
			// name += ", " + getResources().getResourceName(resid);
			// name += ", " + getResources().getResourcePackageName(resid);
			// name += ", " + getResources().getResourceTypeName(resid);
		} catch (Exception e) {
			//e.printStackTrace();
		}

		return name;
	}

	protected int getResource(String name, String defType) {
		try {
			return getResources().getIdentifier(name, defType, getPackageName());
		} catch (Exception e) {
			//e.printStackTrace();
			return 0;
		}
	}

	protected String getString(String name) {
		try {
			return getString(getResource(name, "string"));
		} catch (Exception e) {

			// e.printStackTrace();
			return "";
		}
	}

	public void putURLImage(final Context context, final ImageView v, final String url, final boolean resize, final int imageRes) {
		if (v == null) {
			return;
		}
		if (getApp().getImageDownLoader() != null) {
			getApp().getImageDownLoader().putURLImage(context, v, url, resize, imageRes);
		}
	}

	protected int getColors(int resId) {
		int color = 0;
		try {
			color = getResources().getColor(resId);
		} catch (Exception e) {
			// Log.e(__CLASSNAME__, Log.getStackTraceString(e));
		}
		return color;
	}

	int activity_base = R.layout.activity_base3;
	LayoutInflater mInflater = null;

	int mLayoutResID = 0;

	@Override
	public void setContentView(int layoutResID) {

		super.setContentView(layoutResID);
		mLayoutResID = layoutResID;
	}

	/**
	 * <pre>
	 *  으~~~시발~~~개썅바~~~개젖됬네
	 * </pre>
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (mLayoutResID == 0) {
			String name = "activity_" + (getClass().getSimpleName() != null ? getClass().getSimpleName() : "");
			int layoutResID = getResource(name, "layout");
			if (layoutResID > 0) {
				setContentView(layoutResID);
			} else {
				setContentView(activity_base);
			}
		}

		try {
			// As we're using a Toolbar, we should retrieve it and set it
			// to be our ActionBar
			if (findViewById(R.id.toolbar) != null) {
				Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
				setSupportActionBar(toolbar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// if you need to customize anything else about the text, do it here.
		// I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
		// ((TextView)v.findViewById(android.R.id.title)).setText(this.getTitle());
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayShowCustomEnabled(true);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			////커스템개썅바타이틀텍스트
			//View v = mInflater.inflate(R.layout.include_actionbar_title2, null, false);
			//if (v != null) {
			//	// assign the view to the actionbar
			//	getSupportActionBar().setCustomView(v);
			//}
		}

		setStatusBarTranslucent(true);
	}

	protected void setStatusBarTranslucent(boolean makeTranslucent) {
		////if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		//{
		//	View v = findViewById(R.id.include_base);
		//	if (v != null) {
		//		//int paddingTop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? WidgetUtils.getStatusBarHeight(this) : 0;
		//		int paddingTop = 0;
		//		TypedValue tv = new TypedValue();
		//		getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
		//		paddingTop += TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
		//		v.setPadding(0, makeTranslucent ? paddingTop : 0, 0, 0);
		//	}
		//}
		/**
		 * StatusBar투명처리
		 */
		//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			if (makeTranslucent) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			} else {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
		searchAutoComplete.setHintTextColor(getColors(R.color.solid_white_choco));
		searchAutoComplete.setTextColor(getColors(R.color.solid_white_choco));

		return super.onCreateOptionsMenu(menu);
	}
}
