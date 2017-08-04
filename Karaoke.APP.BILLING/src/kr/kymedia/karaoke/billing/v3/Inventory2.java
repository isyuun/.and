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
 * 2014 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.APP
 * filename	:	Inventory2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.billing.v3
 *    |_ Inventory2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.billing.v3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * TODO<br>
 * 
 * <pre></pre>
 *
 * @author isyoon
 * @since 2014. 12. 5.
 * @version 1.0
 */
public class Inventory2 extends Inventory {

	/** Returns a list of all owned product IDs. */
	public List<String> getAllOwnedSkus() {
		return new ArrayList<String>(mPurchaseMap.keySet());
	}

	/** Returns a list of all owned product IDs of a given type */
	public List<String> getAllOwnedSkus(String itemType) {
		List<String> result = new ArrayList<String>();
		for (Purchase p : mPurchaseMap.values()) {
			if (p.getItemType().equals(itemType)) result.add(p.getSku());
		}
		return result;
	}

	/** Returns a list of all purchases. */
	public List<Purchase> getAllPurchases() {
		return new ArrayList<Purchase>(mPurchaseMap.values());
	}

	/** Returns a list of all purchases of a given type */
	public List<Purchase> getAllPurchases(String itemType) {
		ArrayList<Purchase> result = new ArrayList<Purchase>();
		for (Purchase p : mPurchaseMap.values()) {
			if (p.getItemType().equals(itemType)) result.add(p);
		}
		return result;
	}
}
