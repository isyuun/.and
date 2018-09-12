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
 * filename	:	_BaseAdActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ _BaseAdActivity.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;

/**
 *
 * TODO<br>
 * 
 * <pre></pre>
 *
 * @author isyoon
 * @since 2013. 11. 29.
 * @version 1.0
 */
public class _BaseAdActivity extends BaseAdActivity2 {

	@Override
	protected void startAd(KPItem info) {

		if (!_IKaraoke.APP_API_TEST)
			super.startAd(info);
	}

	@Override
	protected void startAdTest() {

		if (!_IKaraoke.APP_API_TEST)
			super.startAdTest();

	}

	@Override
	public void refresh() {
		super.refresh();
	}
}
