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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV.LGT
 * filename	:	Main3.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.gtv
 *    |_ Main3.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.gtv;

import android.util.Base64;
import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;

import kr.kymedia.kykaraoke.api.IKaraokeTV;

/**
 * <pre>
 * 셋탑정보확인
 * </pre>
 *
 * @author isyoon
 * @since 2015. 4. 15.
 * @version 1.0
 */
class Main2 extends Main1 {
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

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP_param(boolean)
	 * @see kr.kymedia.kykaraoke.tv.gtv.Main#getVender()
	 */
	@Override
	protected void KP_param(boolean adsID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");
		super.KP_param(adsID);
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void getVender() {
		// super.getVender();
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + m_strSTBVender + ":" + getIntent() + getIntent().getExtras() + ":" + getVideo());

		clearVender();

		m_strSTBVender = P_APPNAME_GTV;
		p_appname = P_APPNAME_GTV;
		p_apikey = P_APIKEY_GTV;

		// m_strMacOrigin = KPparam.getMacAdress();
		// m_strMac = KPparam.getMacAdress();
		if (getVideo() != null && getVideo() instanceof __Video) {
			m_orgSTBID = ((__Video) getVideo()).getRemoteLG().subScriberNo;
			p_readmac = ((__Video) getVideo()).getRemoteLG().mac;
			p_mac = ((__Video) getVideo()).getRemoteLG().mac;
		}
		p_stbid = Base64.encodeToString(m_orgSTBID.getBytes(), Base64.NO_WRAP);
		p_model = KPparam.getModel();

		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + m_strSTBVender + ":" + getIntent() + getIntent().getExtras() + ":" + getVideo());
	}

}
