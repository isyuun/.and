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
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	ISUHandler2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * com.kumyoung.stbcomm
 *    |_ ISUHandler2.java
 * </pre>
 */
package com.kumyoung.stbcomm;

import android.util.Log;

import kr.kymedia.kykaraoke.tv.BuildConfig;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-08-04
 */
class ISUHandler2 extends ISUHandler {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	public ISUHandler2() {
	}

	@Override
	public String GetMessageFromCode(String strCode) {
		String ret = super.GetMessageFromCode(strCode);
		Log.wtf("[VASS]" + _toString(), "GetMessageFromCode()" + ret);
		return ret;
	}

	@Override
	public void getProductList(String strContID, String strMacAddr) {
		super.getProductList(strContID, strMacAddr);
		Log.wtf("[VASS]" + _toString(), "getProductList()");
	}

	@Override
	public void getSubService() {
		super.getSubService();
		Log.wtf("[VASS]" + _toString(), "getProductList()");
	}

	@Override
	public boolean subServiceConfirmAuth(String strContID, String strMacAddr) {
		boolean ret = super.subServiceConfirmAuth(strContID, strMacAddr);
		Log.wtf("[VASS]" + _toString(), "subServiceConfirmAuth()" + (ret ? "[OK]" : "[NG]") + ret);
		return ret;
	}

	@Override
	public boolean subServiceReq(String strContID, String strMacAddr, String ProductCode) {
		boolean ret = super.subServiceReq(strContID, strMacAddr, ProductCode);
		Log.wtf("[VASS]" + _toString(), "subServiceReq()" + (ret ? "[OK]" : "[NG]") + ret);
		return ret;
	}
}
