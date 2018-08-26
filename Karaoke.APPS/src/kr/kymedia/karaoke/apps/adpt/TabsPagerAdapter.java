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
 * filename	:	TabsAdapter2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ TabsAdapter2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.adpt;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.api.Log2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * <pre>
 * <a href="http://stackoverflow.com/questions/8785221/retrieve-a-fragment-from-a-viewpager">Retrieve a Fragment from a ViewPager</a>
 * </pre>
 * 
 * @author isyoon
 * @since 2012. 5. 29.
 * @version 1.0
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	private Context mContext;
	// private FragmentManager mFragmentManager;
	private ArrayList<TabsPagerInfo> mTabs = new ArrayList<TabsPagerInfo>();
	SparseArray<Fragment> mFragments = new SparseArray<Fragment>();

	public TabsPagerAdapter(Context context, FragmentManager fragmentManager) {
		super(fragmentManager);
		// mFragmentManager = fragmentManager;
		mContext = context;
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
		mContext = null;
		// mFragmentManager = null;
		mTabs = null;
		mFragments = null;
	}

	public void addTab(String tag, String name, Class<?> cls, Bundle args) {
		TabsPagerInfo info = new TabsPagerInfo(tag, name, cls, args);
		mTabs.add(info);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;

		if (position > -1 && position < getCount()) {
			TabsPagerInfo info = mTabs.get(position);
			fragment = Fragment.instantiate(mContext, info.cls.getName(), info.args);
		}

		// if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + position + "-" + fragment);
		mFragments.put(position, fragment);
		return fragment;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		Fragment fragment = (Fragment) super.instantiateItem(container, position);
		mFragments.put(position, fragment);
		return fragment;
	}

	public Fragment getAt(ViewGroup container, int position) {
		Fragment fragment = null;

		if (position > -1 && position < getCount()) {
			// String tag = "android:switcher:" + container.getId() + ":" + position;;
			// fragment = mFragmentManager.findFragmentByTag(tag);
			fragment = mFragments.get(position);
			// if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + position + "-" + mFragments.size() + ":" + fragment);
		}

		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		TabsPagerInfo info = mTabs.get(position);
		return info.name;
	}

	/**
	 * <pre>
	 * mFragments.clear() 즐대호출하지않는다.
	 * 뒤지것네.
	 * </pre>
	 */
	public void clear(ViewGroup container) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + container);
		try {
			// int len = mTabs.size();
			// for (int i = 0; i < len; i++) {
			// int position = mFragments.size() - 1;
			// if (position > -1) {
			// destroyItem(container, position, getAt(container, position));
			// }
			// }
			mTabs.clear();
			notifyDataSetChanged();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Deprecated
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		super.destroyItem(container, position, object);
		mFragments.remove(position);
		if (_IKaraoke.DEBUG || _IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + position + "-" + mFragments.size() + ":" + object);
	}

}
