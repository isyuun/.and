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
 * filename	:	Main2XX.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.gtv
 *    |_ Main2XX.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.gtv;

import android.os.Message;
import android.util.Log;

import com.kumyoung.gtvkaraoke.BuildConfig;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-08-03
 */
class Main2XX extends Main2X {
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
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(Message)
	 */
	@Override
	protected void KP(Message msg) {
		super.KP(msg);
		///**
		// * 작업전이야 - 시발
		// */
		//for (String key : _KP_4002.getTicketItems().keySet()) {
		//	TicketItem item = _KP_4002.getTicketItems().get(key);
		//	//if (TextUtil.isEmpty(item.p_passtype))
		//	{
		//		if (("1일이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//			item.id_product = "40102";
		//		} else if (("월이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//			item.id_product = "40103";
		//		} else if (("1년약정").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//			item.id_product = "40104";
		//		} else if (("무료쿠폰등록").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//		}
		//		Log.wtf("[VASS]" + _toString(), getMethodName() + "[TEST]" + "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());
		//	}
		//}
		///**
		// * 작업전이야 - 종발
		// */
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#VASS(REQUEST_VASS)
	 */
	@Override
	public void VASS(REQUEST_VASS request) {
		///**
		// * 작업전이야 - 시발
		// */
		//for (String key : remote.getTicketItems().keySet()) {
		//	TicketItem item = remote.getTicketItems().get(key);
		//	//if (TextUtil.isEmpty(item.p_passtype))
		//	{
		//		if (("1일이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//			item.id_product = "40102";
		//		} else if (("월이용권").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//			item.id_product = "40103";
		//		} else if (("1년약정").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//			item.id_product = "40104";
		//		} else if (("무료쿠폰등록").equalsIgnoreCase(TextUtil.trim(item.product_name))) {
		//		}
		//	}
		//	Log.wtf("[VASS]" + _toString(), getMethodName() + "[TEST]" + "[RECV]" + "[KEY]" + key + ":" + item.p_passtype + "[ITEM]" + item.product_name + ":" + item.id_product + ":" + item.product_type + ":" + item.product_type.value());
		//}
		///**
		// * 작업전이야 - 종발
		// */
		super.VASS(request);
	}
}
