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
 * filename	:	__VASS.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.api
 *    |_ __VASS.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.api.btv;

import android.os.Handler;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @since 2016-04-26
 * @version 1.0
 */
public class __VASS extends VASS3 {
	/*
	 * VASS
	 */
	final static public String VASS_REQUEST_PAGE = "http://vass.hanafostv.com:8080/service/serviceAction.hm?";
	final static public String M_PPV_PURCHASE = "purchasePpvProduct";
	final static public String M_PPM_PURCHASE = "purchasePpxProduct";
	final static public String M_PPV_CHECK = "checkPurchasePpvProduct";
	final static public String M_PPM_CHECK = "checkPurchasePpmProduct";
	final static public String VASS_PPM_PROMISE = "10";
	final static public String M_PASSWORD_CHECK = "checkPurchasePw";

	public __VASS(Handler h) {
		super(h);
	}
}
