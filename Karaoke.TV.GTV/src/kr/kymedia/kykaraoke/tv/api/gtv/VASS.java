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
 * filename	:	VASS.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api.gtv
 *    |_ VASS.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.api.gtv;

import java.util.LinkedHashMap;

import kr.kymedia.kykaraoke.api._VASS;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-06-28
 */
public class VASS extends Thread implements _VASS {
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

	public VASS() {
	}

	@Override
	public void sendMessage(int state) {
	}

	@Override
	public void setVASSParam(String stbid, String mac) {
	}

	@Override
	public void setRequest(REQUEST_VASS request) {
	}

	@Override
	public void setVASSUrl(String password) {
	}

	@Override
	public void run() {
		try {
			sendRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendRequest() throws Exception {
	}

	@Override
	public void parseVASSResult(String response) {
	}

	@Override
	public String isSuccess() {
		return null;
	}

	String errorCode;

	public void setVASSErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getVASSErrorCode() {
		return this.errorCode;
	}

	@Override
	public void putTicketItems(LinkedHashMap<String, TicketItem> items) {

	}

	@Override
	public LinkedHashMap<String, TicketItem> getTicketItems() {
		return null;
	}

	@Override
	public void setTicketKey(String key) {

	}

	@Override
	public TicketItem getTicketItem() {
		return null;
	}

	@Override
	public String RETURN_DATA() {
		return null;
	}

}
