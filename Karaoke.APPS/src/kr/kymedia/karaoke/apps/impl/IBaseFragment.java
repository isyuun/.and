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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	BaseFragmentInterface.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseFragmentInterface.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.impl;

import kr.kymedia.karaoke.api.KPItem;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * 
 * TODO NOTE:<br>
 * BaseFragment들 기본함수 인터페이스
 * 
 * @author isyoon
 * @since 2012. 2. 28.
 * @version 1.0
 */

public interface IBaseFragment {

	Intent putIntentData(Context context, Class<?> cls, KPItem info, KPItem list);

	// Intent getIntentKPItem();

	void KP_init();

	void KP_nnnn();

	void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message,
			String r_info);

	void KPnnnn();

	void onClick(View v);

	void KP_nnnn(KPItem info, KPItem list, boolean clear);

}
