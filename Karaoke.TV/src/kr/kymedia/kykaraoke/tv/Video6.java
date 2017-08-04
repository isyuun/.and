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
 * filename	:	Video6.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.tv
 *    |_ Video6.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv;

import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * 동영상사용여부
 * </pre>
 *
 * @author isyoon
 * @since 2015. 6. 25.
 * @version 1.0
 */
class Video6 extends Video5 {
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
	 * 동영상사용여부
	 */
	private boolean isPlayVideo = true;

	public boolean isPlayVideo() {
		return isPlayVideo;
	}

	public void setPlayVideo(boolean isPlayVideo) {
		this.isPlayVideo = isPlayVideo;
	}

	@Override
	protected void startVideo(String url, int playtype) {

		if (isPlayVideo) {
			super.startVideo(url, playtype);
		}
	}

}
