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
 * filename	:	ITabPagerActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app.impl
 *    |_ ITabPagerActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.impl;

import android.os.Bundle;

import kr.kymedia.karaoke.apps._BaseFragment;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 8. 2.
 * @version 1.0
 * @see
 */
public interface ITabPagerActivity {

	void addTabs();

	void addTab(String tag, String name, Class<?> cls, Bundle args);

	int getCount();

	_BaseFragment getAt(int index);

	void setCurrentFragment(int index);

	_BaseFragment getCurrentFragment();

	void notifyDataSetChanged();

	void clear();

}
