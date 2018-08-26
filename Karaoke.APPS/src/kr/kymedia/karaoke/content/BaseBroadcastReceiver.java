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
 * filename	:	BaseBroadcastReceiver.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseBroadcastReceiver.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 6. 25.
 * @version 1.0
 */
public class BaseBroadcastReceiver extends BroadcastReceiver {
	// final private String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	/**
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {


	}

}
