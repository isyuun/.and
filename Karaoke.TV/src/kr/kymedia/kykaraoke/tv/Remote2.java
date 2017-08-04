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
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	Remote2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke
 *    |_ Remote2.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv;

import kr.kymedia.kykaraoke.tv.api._Const;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import android.util.Log;

/**
 *
 * <pre>
 * 순환처리(그냥심심해서?했을까!)
 * 리모컨키값enum
 * STATE값enum
 * </pre>
 *
 * @see MENU_STATE
 * @see REMOTE
 * 
 * @author isyoon
 * @since 2015. 2. 13.
 * @version 1.0
 */
class Remote2 extends Remote implements _Const {
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

	public String m_strSTBVender = P_APPNAME_SKT_BOX;

	public Remote2() {
		super();
	}

	@Override
	public void inputKey(int keyID) {
		Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + getState());
		super.inputKey(keyID);
		Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + getState());
	}

	public MENU_STATE getState() {
		return MENU_STATE.get(m_iState);
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Remote#setSearchSelfState(int)
	 */
	@Override
	public void setSearchSelfState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":m_iSearchSelfFocus:" + m_iSearchSelfFocus);

		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iSearchSelfFocus > 1) {
				m_iSearchSelfFocus--;
			}
			break;
		case REMOTE_RIGHT:
			if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
				if (m_iSearchSelfFocus < 2) {
					m_iSearchSelfFocus++;
				}
			} else {
				if (m_iSearchSelfFocus < 3) {
					m_iSearchSelfFocus++;
				}
			}
			break;
		}
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":m_iSearchSelfFocus:" + m_iSearchSelfFocus);
	}

	/**
	 * 
	 * <pre>
	 * 순환처리(그냥심심해서?했을까!)
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Remote#setSongListDetailState(int)
	 */
	@Override
	public void setSongListDetailState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + m_iSongListDetailFocus);

		switch (keyID) {
		case REMOTE_DOWN:
			if (m_iSongListDetailFocus == 4) {
				m_iSongListDetailFocus = 1;
			} else {
				if (m_iSongListDetailFocus < 4) {
					m_iSongListDetailFocus++;
				}
			}
			break;
		case REMOTE_UP:
			if (m_iSongListDetailFocus == 1) {
				m_iSongListDetailFocus = 4;
			} else {
				if (m_iSongListDetailFocus > 1) {
					m_iSongListDetailFocus--;
				}
			}
			break;
		}

		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + m_iSongListDetailFocus);
	}

	/**
	 * <pre>
	 * 순환처리(그냥심심해서?했을까!)
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Remote#setMainMenuState(int)
	 */
	@Override
	public void setMainMenuState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		switch (keyID)
		{
		case REMOTE_RIGHT:
			if (m_iMenuHomeFocusY == 1) {
				if (m_iMenuHomeFocus < 5) {
					m_iMenuHomeFocus++;
				} else if (m_iMenuHomeFocus == 5) {
					m_iMenuHomeFocus = 1;
				}
			} else if (m_iMenuHomeFocusY == 2) {
				if (m_iMenuHomeFocus == 6) {
					m_iMenuHomeFocus = 8;
				} else if (m_iMenuHomeFocus < 10) {
					m_iMenuHomeFocus++;
				} else if (m_iMenuHomeFocus == 10) {
					m_iMenuHomeFocus = 6;
				}
			}
			break;
		case REMOTE_LEFT:
			if (m_iMenuHomeFocusY == 1) {
				if (m_iMenuHomeFocus > 1) {
					m_iMenuHomeFocus--;
				} else if (m_iMenuHomeFocus == 1) {
					m_iMenuHomeFocus = 5;
				}
			} else if (m_iMenuHomeFocusY == 2) {
				if (m_iMenuHomeFocus == 8) {
					m_iMenuHomeFocus = 6;
				} else if (m_iMenuHomeFocus > 6) {
					m_iMenuHomeFocus--;
				} else if (m_iMenuHomeFocus == 6) {
					m_iMenuHomeFocus = 10;
				}
			}
			break;
		case REMOTE_UP:
			if (m_iMenuHomeFocusY == 2) {
				if (m_iMenuHomeFocus == 7) {
					m_iMenuHomeFocus = 6;
				} else {
					m_iMenuHomeFocusY = 1;
					m_iMenuHomeFocus = 1;
				}
			} else if (m_iMenuHomeFocusY == 3) {
				m_iMenuHomeFocusY = 2;
				m_iMenuHomeFocus = 7;
			}
			break;
		case REMOTE_DOWN:
			if (m_iMenuHomeFocusY == 1) {
				m_iMenuHomeFocusY = 2;
				m_iMenuHomeFocus = 6;
			} else if (m_iMenuHomeFocusY == 2) {
				if (m_iMenuHomeFocus == 6) {
					m_iMenuHomeFocus = 7;
				} else {
					m_iMenuHomeFocusY = 3;
					m_iMenuHomeFocus = 11;
				}
			} else {
				m_iMenuHomeFocusY = 1;
				m_iMenuHomeFocus = 1;
			}
			break;
		}
	}

	@Override
	public void setCertifyHPState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iCertifyHPFocusX > 1) {
				m_iCertifyHPFocusX--;
			} else {
				if (m_iCertifyHPFocusY == 1) {
					m_iCertifyHPFocusX = 3;
				} else {
					m_iCertifyHPFocusX = 2;
				}
			}
			break;
		case REMOTE_RIGHT:
			if (m_iCertifyHPFocusY == 1) {
				if (m_iCertifyHPFocusX < 3) {
					m_iCertifyHPFocusX++;
				} else {
					m_iCertifyHPFocusX = 1;
				}
			} else {
				if (m_iCertifyHPFocusX < 2) {
					m_iCertifyHPFocusX++;
				} else {
					m_iCertifyHPFocusX = 1;
				}
			}
			break;
		case REMOTE_DOWN:
			if (m_iCertifyHPFocusY == 1) {
				m_iCertifyHPFocusY = 2;
				m_iCertifyHPFocusX = 1;
			}
			break;
		case REMOTE_UP:
			if (m_iCertifyHPFocusY == 2) {
				m_iCertifyHPFocusY = 1;
				m_iCertifyHPFocusX = 1;
			}
			break;
		}
	}

	@Override
	public void setCertifyState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		switch (keyID)
		{
		case REMOTE_LEFT:
			if (m_iCertifyFocusX > 1) {
				m_iCertifyFocusX--;
			} else {
				m_iCertifyFocusX = 2;
			}
			break;
		case REMOTE_RIGHT:
			if (m_iCertifyFocusX < 2) {
				m_iCertifyFocusX++;
			} else {
				m_iCertifyFocusX = 1;
			}
			break;
		case REMOTE_DOWN:
			if (m_iCertifyFocusY == 1) {
				m_iCertifyFocusY = 2;
				m_iCertifyFocusX = 1;
			}
			break;
		case REMOTE_UP:
			if (m_iCertifyFocusY == 2) {
				m_iCertifyFocusY = 1;
				m_iCertifyFocusX = 1;
			}
			break;
		}
	}

	@Override
	public void setMyRecordListState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID));
		super.setMyRecordListState(keyID);
	}

	@Override
	public void setCustomerSubMenuState(int keyID) {
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + m_iMenuCustomerFocus);
		super.setCustomerSubMenuState(keyID);
		if (IKaraokeTV.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + m_iMenuCustomerFocus);
	}

}
