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
 * filename	:	ListenPostFragment2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenPostFragment2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import kr.kymedia.karaoke.apps.R;
import android.view.View;

/**
 *
 * TODO
 * NOTE:녹음곡재생확인만(업로드제거)
 *
 * @author isyoon
 * @since 2013. 2. 1.
 * @version 1.0
 * @see
 */

public class ListenPostFragment2 extends ListenPostFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();
		View v = null;

		v = findViewById(R.id.upload_save);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.view1);
		if (v != null) {
			v.setVisibility(View.GONE);
		}
	}

}
