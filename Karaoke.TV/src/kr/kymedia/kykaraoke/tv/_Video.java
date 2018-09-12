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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	Video2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Video2.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv;

import android.os.Bundle;

/**
 *
 * TODO<br>
 * 
 * <pre></pre>
 *
 * @author isyoon
 * @since 2015. 1. 28.
 * @version 1.0
 */
public class _Video extends VideoX {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		get_Application().setVideoActivity(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		get_Application().setVideoActivity(this);
		super.onResume();
	}

}
