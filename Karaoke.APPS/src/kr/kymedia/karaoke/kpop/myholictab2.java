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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	myholictab2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ myholictab2.java
 * </pre>
 */
package kr.kymedia.karaoke.kpop;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-27
 */
class myholictab2 extends myholictab {
	@Override
	public void addTabs() {

		super.addTabs();

		int index = getIntent().getIntExtra("tabIndex", getCount() - 1);
		setCurrentFragment(index);
	}
}
