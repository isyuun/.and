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
 * project	:	Karaoke.TV.BTV
 * filename	:	MainX.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.btv
 *    |_ MainX.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.btv;

import kr.kymedia.kykaraoke.BuildConfig;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.os.Bundle;
import android.util.Log;

/**
 * <a href="http://pms.skdevice.net/redmine/issues/3593">3593 com.tvstorm.tv.framework.TVSystem...</a><br>
 * 
 * <pre>
 * DASAN:
 * 	셋탑STB_ID확인
 * 		STBID = SystemInfo.GetSystemProperty("STB_ID");
 * TVSTORM:
 * 	셋탑STBID확인
 * 		STBID = tvSystem.getSystem().getProperty("STBID");
 * 	셋탑시스템연결
 * 	셋탑마이크오픈
 * </pre>
 * 
 * @author isyoon
 * @since 2015. 8. 21.
 * @version 1.0
 */
class MainX extends Main4X {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.e(_toString() + TAG_MAIN, "onCreate()" + savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {

		Log.e(_toString() + TAG_MAIN, "onPause()");
		if (getVideo() != null && getVideo() instanceof VideoX) {
			((VideoX) getVideo()).pauseTVSYSTEM();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.e(_toString() + TAG_MAIN, "onResume()");
		//if (getVideo() != null && getVideo() instanceof __Video) {
		//	((__Video) getVideo()).connectTVSYSTEM();
		//}
		super.onResume();
	}

	@Override
	protected void onStop() {

		Log.e(_toString() + TAG_MAIN, "onStop()");
		super.onStop();
	}

	@Override
	public void finish() {

		Log.e(_toString() + TAG_MAIN, "finish()");
		super.finish();
	}

}
