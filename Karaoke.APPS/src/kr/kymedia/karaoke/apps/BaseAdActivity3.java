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
 * project	:	Karaoke.KPOP.OLD
 * filename	:	BaseAdActivity3.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseAdActivity3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.os.Bundle;

/**
 * 
 * TODO NOTE:<br>
 * 무료충전소
 * 
 * @author isyoon
 * @since 2012. 10. 5.
 * @version 1.0
 */

public class BaseAdActivity3 extends BaseAdActivity2 {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	// /**
	// * 무료충전소
	// */
	// private void initAdCharge() {
	// if (_IKaraoke.DEBUG)Log2.e(__CLASSNAME__, getMethodName() + getApp().p_uid);
	//
	// //post(new Runnable() {
	// //
	// // @Override
	// // public void run() {
	// //
	// // initAdTapjoyReward();
	// // initAdTNK();
	// // initAdMetaps();
	// // //initAdFlurry();
	// // //initAdW3i();
	// // //initAdSponsorPay();
	// // }
	// //});
	// }

	@Override
	public void showAdFreezone() {

		super.showAdFreezone();
	}

}
