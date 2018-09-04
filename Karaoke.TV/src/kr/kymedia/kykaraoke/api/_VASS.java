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
 * filename	:	_VASS.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv.api
 *    |_ _VASS.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.api;

import java.util.LinkedHashMap;

import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @since 2015. 5. 21.
 * @version 1.0
 */
public interface _VASS extends _Const {

	public void start();

	public void sendMessage(int state);

	public void setVASSParam(String stbid, String mac);

	public void setRequest(REQUEST_VASS request);

	public void setVASSUrl(String password);

	public void sendRequest() throws Exception;

	public void parseVASSResult(String response);

	public String isSuccess();

	public String getVASSErrorCode();

	public void putTicketItems(LinkedHashMap<String, TicketItem> items);

	LinkedHashMap<String, TicketItem> getTicketItems();

	public void setTicketKey(String key);

	TicketItem getTicketItem();

	/**
	 * RETURN_DATA=상품코드^(성공/실패)^셋탑아이디^구매번호(TID)^이용권만료일자
	 */
	public String RETURN_DATA();
}
