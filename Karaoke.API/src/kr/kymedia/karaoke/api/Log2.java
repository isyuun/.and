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
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	if (_IKaraoke.DEBUG)Log.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.util
 *    |_ if (_IKaraoke.DEBUG)Log.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.api;

import kr.kymedia.karaoke._IKaraoke;

/**
 *
 * TODO
 * NOTE:전문API로그용
 *
 * @author isyoon
 * @since 2012. 5. 4.
 * @version 1.0
 */

public class Log2 {

	private static boolean enable = _IKaraoke.DEBUG;

	// private static boolean enable = true;

	// public Log() {
	// // TODO Auto-generated constructor stub
	// }

	/**
	 * @return the enable
	 */
	public static boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable
	 *          the enable to set
	 */
	public static void setEnable(boolean enable) {
		Log2.enable = enable;
	}

	public static int v(String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.v(tag, msg);
		} else {
			return 0;
		}
	}

	public static int v(String tag, String msg, Throwable tr)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.v(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.d(tag, msg);
		} else {
			return 0;
		}
	}

	public static int d(String tag, String msg, Throwable tr)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.d(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.i(tag, msg);
		} else {
			return 0;
		}
	}

	public static int i(String tag, String msg, Throwable tr)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.i(tag, msg, tr);
		} else {
			return 0;
		}
	}

	public static int w(String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.w(tag, msg);
		} else {
			return 0;
		}
	}

	public static int w(String tag, String msg, Throwable tr)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.w(tag, msg, tr);
		} else {
			return 0;
		}
	}

	// public static native boolean isLoggable(String s, int j);

	public static int w(String tag, Throwable tr)
	{
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.w(tag, tr);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.e(tag, msg);
		} else {
			return 0;
		}
	}

	public static int e(String tag, String msg, Throwable tr)
	{
		if (msg == null) {
			msg = "(null)";
		}
		if (_IKaraoke.DEBUG && enable) {
			return android.util.Log.v(tag, msg, tr);
		} else {
			return 0;
		}
	}

	/**
	 * 중요 로그 표시
	 */
	public static int wtf(String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		//if (enable) {
		//	return Log2.e(tag, msg);
		//} else {
		//	return 0;
		//}
		return Log2.e(tag, msg);
	}

	/**
	 * 중요 로그 표시
	 */
	public static int wtf(String tag, Throwable tr)
	{
		//if (enable) {
		//	return Log2.e(tag, tr.getMessage());
		//} else {
		//	return 0;
		//}
		return Log2.e(tag, tr.getMessage());
	}

	public static int wtf(String tag, String msg, Throwable tr)
	{
		if (msg == null) {
			msg = "(null)";
		}
		//if (enable) {
		//	return Log2.e(tag, msg, tr);
		//} else {
		//	return 0;
		//}
		return Log2.e(tag, msg, tr);
	}

	public static String getStackTraceString(Throwable tr)
	{
		return android.util.Log.getStackTraceString(tr);
	}

	public static int println(int priority, String tag, String msg)
	{
		if (msg == null) {
			msg = "(null)";
		}
		return android.util.Log.println(priority, tag, msg);
	}

	public static final int VERBOSE = 2;
	public static final int DEBUG = 3;
	public static final int INFO = 4;
	public static final int WARN = 5;
	public static final int ERROR = 6;
	public static final int ASSERT = 7;
}
