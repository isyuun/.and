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
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	.prj
 * filename	:	Remote3.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Remote3.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv;

import java.util.LinkedHashMap;

import kr.kymedia.karaoke.api.Log;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.data.TicketItem;

/**
 * <pre>
 * 노래방이용권
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-07-05
 */
public class Remote3 extends Remote2 {
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

	/**
	 * 4.노래방샵->노래방샵 세로
	 */
	protected int m_iShopTicketFocusY = 0;
	/**
	 * 4.노래방샵->노래방샵 가로
	 */
	protected int m_iShopTicketFocusX = 1;

	public Remote3() {
		super();
	}


	private LinkedHashMap<String, TicketItem> items = new LinkedHashMap<>();

	public void putTicketItems(LinkedHashMap<String, TicketItem> items) {
		if (IKaraokeTV.DEBUG) Log.d("[VASS]" + _toString(), getMethodName() + "" + items);
		this.items = items;
	}

	public LinkedHashMap<String, TicketItem> getTicketItems() {
		return items;
	}

	public void setTicketItem(TicketItem item) {
	}

	public TicketItem getTicketItem() {
		return getTicketItems().get(key());
	}

	/**
	 * (???)
	 */
	private int index = 1;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String key() {
		int index = this.index;

		String ret = "읍~~~따";

		int idx = 0;
		for (String key : items.keySet()) {
			if (index == idx) {
				//if (IKaraokeTV.DEBUG) Log._LOG("[VASS]" + _toString(), getMethodName(), this.index + "->" + index + "->" + idx + "->" + key + items.get(key));
				ret = key;
				break;
			}
			idx++;
		}

		//if (IKaraokeTV.DEBUG) Log._LOG("[VASS]" + _toString(), getMethodName(), this.index + "->" + index + "->" + ret);
		return ret;
	}

	public PRODUCT_TYPE product_type() {
		int index = this.index;

		PRODUCT_TYPE ret = PRODUCT_TYPE.NONE;

		int idx = 0;
		for (String key : items.keySet()) {
			if (index == idx) {
				//if (IKaraokeTV.DEBUG) Log._LOG("[VASS]" + _toString(), getMethodName(), this.index + "->" + index + "->" + idx + "->" + key + items.get(key));
				ret = items.get(key).product_type;
				break;
			}
			idx++;
		}

		//if (IKaraokeTV.DEBUG) Log._LOG("[VASS]" + _toString(), getMethodName(), this.index + "->" + index + "->" + ret);
		return ret;
	}

	public void setShopTicketState(int keyID) {
		if (IKaraokeTV.DEBUG) Log._LOG("[VASS]" + _toString(), getMethodName() + "[ST]", REMOTE_STATE.get(keyID) + ":" + this.index + ":m_iShopTicketFocusY:" + m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + m_iShopTicketFocusX);

		switch (keyID)
		{
			case REMOTE_UP:
				if (m_iShopTicketFocusY == 0) {
					m_iShopTicketFocusY = 2;
				} else {
					m_iShopTicketFocusY--;
				}
				break;
			case REMOTE_DOWN:
				if (m_iShopTicketFocusY == 2) {
					m_iShopTicketFocusY = 0;
				} else {
					m_iShopTicketFocusY++;
				}
				break;
			case REMOTE_LEFT:
				if (m_iShopTicketFocusY == 1) {
					if (index == 0) {
						index = items.size() - 1;
					} else {
						index--;
					}
				} else if (m_iShopTicketFocusY == 2) {
					if (m_iShopTicketFocusX == 1) {
						m_iShopTicketFocusX = 2;
					} else {
						m_iShopTicketFocusX--;
					}
				}
				break;
			case REMOTE_RIGHT:
				if (m_iShopTicketFocusY == 1) {
					if (index == items.size() - 1) {
						index = 0;
					} else {
						index++;
					}
				} else if (m_iShopTicketFocusY == 2) {
					if (m_iShopTicketFocusX == 2) {
						m_iShopTicketFocusX = 1;
					} else {
						m_iShopTicketFocusX++;
					}
				}
				break;
		}

		if (IKaraokeTV.DEBUG) Log._LOG("[VASS]" + _toString(), getMethodName() + "[ED]", REMOTE_STATE.get(keyID) + ":" + this.index + ":m_iShopTicketFocusY:" + m_iShopTicketFocusY + ":m_iShopTicketFocusX:" + m_iShopTicketFocusX);
	}

}
