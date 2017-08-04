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
 * filename	:	GCM3IntentService.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ GCM3IntentService.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.Intent;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.gcm.GCM2IntentService;

/**
 *
 * 정의된GCM리시버연결만한다.<br>
 * 
 * <pre></pre>
 *
 * @author isyoon
 * @since 2013. 12. 3.
 * @version 1.0
 */
public class _GCMIntentService extends GCM2IntentService {
	final private String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	public _GCMIntentService() {
		super("_GCMIntentService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + intent);


		super.onHandleIntent(intent);

		// Release the wake lock provided by the WakefulBroadcastReceiver.
		_GCMReceiver.completeWakefulIntent(intent);
	}
}
