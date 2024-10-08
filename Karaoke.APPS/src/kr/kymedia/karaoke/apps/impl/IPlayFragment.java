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
 * project	:	Karaoke.KPOP
 * filename	:	BaseFragmentInterface.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseFragmentInterface.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.impl;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 2. 28.
 * @version 1.0
 * @see IBaseFragment.java
 */

public interface IPlayFragment extends IBaseFragment {

	void open();

	boolean play();

	boolean stop();

	boolean pause();

	boolean resume();

	boolean restart();

	void release();

	void close();

	// @Deprecated
	// void startRecord();

	// @Deprecated
	// void stopRecord();

	void startRecord(boolean toast);

	void stopRecord(boolean toast);

	// void pauseRecord();

	@Deprecated
	void resumeRecord();

	void releaseRecord();

	void deleteRecord();

	void uploadRecord();

	// int getCurTime();

	// int getTotTime();

	void setCurTime(int pos, boolean force);

	boolean isRecording();

}
