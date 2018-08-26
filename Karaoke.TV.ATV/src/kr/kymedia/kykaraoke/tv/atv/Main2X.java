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
 * filename	:	Main2X.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.atv
 *    |_ Main2X.java
 * </pre>
 */
package kr.kymedia.kykaraoke.tv.atv;

import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.api._KPRequest;
import kr.kymedia.kykaraoke.tv.api.atv.__VASS;

/**
 * <pre>
 *
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-06-08
 */
public class Main2X extends Main {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
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

	@Override
	protected _KPRequest KP(_KPRequest KP) {
		p_domain = P_DOMAIN_ATV;
		//if (IKaraokeTV.DEBUG) p_domain = P_DOMAIN_ATV_TEST;
		if (IKaraokeTV.DEBUG) Log.i(_toString() + TAG_MAIN, "KP()" + "[ST]" + p_domain);
		return super.KP(KP);
	}

	@Override
	public void KP(int request, String OP, String M1, String M2) {
		if (IKaraokeTV.DEBUG) Log.i(_toString() + TAG_MAIN, "KP()" + "[ST]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
		super.KP(request, OP, M1, M2);
	}

	/**
	 * <pre>
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main3X#initVASS()
	 */
	@Override
	protected void initVASS() {
		super.initVASS();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#VASS(int)
	 */
	@Override
	public void VASS(final REQUEST_VASS request) {
		if (IKaraokeTV.DEBUG) Log.e("[VASS]" + _toString(), getMethodName() + request + ":" + VASS);

		//!!!VASS!!!
		VASS = new __VASS(handlerVASS);

		super.VASS(request);
	}

	@Override
	public void VASS(Message msg) {
		int state = msg.getData().getInt("state");
		if (IKaraokeTV.DEBUG) Log.e("[VASS]" + _toString(), getMethodName() + COMPLETE_VASS.get(state) + ":" + msg);
		if (IKaraokeTV.DEBUG) Log.d("[VASS]" + _toString(), "" + VASS);
		try {
			// test
			switch (state) {
				// // 구매확인차단용:노래방샵 이동후 리모콘 입력안됨
				// case COMPLETE_VASS_DAY_CHECK:
				// case COMPLETE_VASS_MONTH_CHECK:
				// p_passtype = TICKET_TYPE_NONE;
				// m_strPassType = TICKET_TYPE_NONE;
				// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[!!!주의:테스트!!!]" + VASS.isSuccess() + ":" + p_passtype + ":" + m_strPassType);
				// setBottomProductText("[!!!주의:테스트!!!]" + getString(R.string.message_error_ticket_no));
				// break;
				// // 재생확인차단용:노래방샵 이동후 리모콘 입력안됨
				// case COMPLETE_VASS_DAY_CHECK_PLAY:
				// case COMPLETE_VASS_MONTH_CHECK_PLAY:
				// p_passtype = TICKET_TYPE_NONE;
				// m_strPassType = TICKET_TYPE_NONE;
				// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + "[!!!주의:테스트!!!]" + VASS.isSuccess() + ":" + p_passtype + ":" + m_strPassType);
				// setBottomProductText("[!!!주의:테스트!!!]" + getString(R.string.message_error_ticket_no));
				// TryPlaySong();
				// break;
				default:
					super.VASS(msg);
					break;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			Log.wtf("[VASS]" + _toString(), getMethodName() + "[NG]" + COMPLETE_VASS.get(state) + ":" + msg + "\n" + Log.getStackTraceString(e));
			String text = getString(R.string.message_error_ticket_buy) + "(" + getString(R.string.message_error_unknown) + ")";
			TextView txtPassResult = (TextView) findViewById(kr.kymedia.kykaraoke.tv.R.id.txt_message_ticket_pass_info_sub);
			if (null != txtPassResult) {
				txtPassResult.setText(text);
			} else {
				ShowMessageAlert(text);
			}
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#TryPlaySong()
	 */
	@Override
	public void TryPlaySong() {
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");
		super.TryPlaySong();
		if (IKaraokeTV.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}
}
