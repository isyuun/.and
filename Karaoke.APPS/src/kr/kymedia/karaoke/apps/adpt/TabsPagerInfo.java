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
 * 2014 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	_BaseTabInfo.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.app.adpt
 *    |_ _BaseTabInfo.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.adpt;

import android.os.Bundle;

/**
 *
 * TODO<br>
 * 
 * <pre></pre>
 *
 * @author isyoon
 * @since 2014. 4. 24.
 * @version 1.0
 */
public class TabsPagerInfo {

	public String tag;
	public String name;
	public Class<?> cls;
	public Bundle args;

	public TabsPagerInfo(String tag, String name, Class<?> cls, Bundle args) {
		this.tag = tag;
		this.name = name;
		this.cls = cls;
		this.args = args;
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
		this.tag = null;
		this.name = null;
		this.cls = null;
		if (this.args != null) {
			this.args.clear();
		}
		this.args = null;
	}
}
