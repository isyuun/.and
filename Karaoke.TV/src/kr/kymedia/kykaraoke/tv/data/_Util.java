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
 * filename	:	_Util.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke
 *    |_ _Util.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.data;

import android.os.*;
import android.widget.ImageView;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author isyoon
 * @since 2015. 9. 1.
 * @version 1.0
 */
public class _Util extends Util {

	public _Util(Handler h) {
		super(h);
	}

	protected void removeCallbacks(Runnable r) {
		if (handler != null) {
			handler.removeCallbacks(r);
		}
	}

	protected void post(Runnable r) {
		removeCallbacks(r);
		if (handler != null) {
			handler.post(r);
		}
	}

	protected void postDelayed(Runnable r, long delayMillis) {
		removeCallbacks(r);
		if (handler != null) {
			handler.postDelayed(r, delayMillis);
		}
	}

	//_Activity activity;
	//public _Util(_Activity activity, Handler h) {
	//	super(h);
	//	// TODO Auto-generated constructor stub
	//	this.activity = activity;
	//}

	ImageView v;

	public void setImage(ImageView v) {
	 this.v = v;
	}

	@Override
	public void sendMessage(int state) {
		if (v == null)
		{
			super.sendMessage(state);
		}
	}

	@Override
	public void LoadImageFromWeb() {
		//android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_DISPLAY);
		super.LoadImageFromWeb();
		postDelayed(putURLImage, 500);
	}

	Runnable putURLImage = new Runnable() {
		@Override
		public void run() {
			if (v != null && m_bitMap != null) {
				//v.setImageBitmap(null);
				v.setImageBitmap(m_bitMap);
				v.invalidate();
			}
		}
	};
}
