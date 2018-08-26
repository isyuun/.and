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
 * filename	:	IBaseDialog.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.util
 *    |_ IBaseDialog.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps.impl;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 3. 22.
 * @version 1.0
 * @see _ISongDialogs.java
 */

public interface IBaseDialog {

	@Deprecated
	final static int DIALOG_ALERT_AIDL = 0;
	@Deprecated
	final static int DIALOG_ALERT_NOSONG = 1;
	@Deprecated
	final static int DIALOG_ALERT_EXISTS = 2;
	@Deprecated
	final static int DIALOG_ALERT_YESNO = 3;
	@Deprecated
	final static int DIALOG_ALERT_REG_YESNO = 4;
	@Deprecated
	final static int DIALOG_ALERT_DEL_YESNO = 5;
	@Deprecated
	final static int DIALOG_ALERT_SONGDOWN_COMPLETE = 6;
	@Deprecated
	final static int DIALOG_ALERT_SONGDOWN_CANCEL = 7;
	@Deprecated
	final static int DIALOG_ALERT_SONGDOWN_ERROR = 8;
	final static int DIALOG_ALERT_NOSDCARD = 9;
	@Deprecated
	final static int DIALOG_PROGRESS_SONG_DATALOAD = 10;
	@Deprecated
	final static int DIALOG_PROGRESS_SONG_DOWNWARN = 11;
	@Deprecated
	final static int DIALOG_PROGRESS_SONG_DOWNLOAD = 12;
	@Deprecated
	final static int DIALOG_PROGRESS_SONG_STREMING = 13;
	@Deprecated
	final static int DIALOG_PROGRESS_SONG_BUFFERING = 14;
	final static int DIALOG_PROGRESS_SONG_UPWARN = 15;
	final static int DIALOG_PROGRESS_SONG_UPLOAD = 16;
	final static int DIALOG_ALERT_MESSAGE_CONFIRM = 17;
	final static int DIALOG_ALERT_MESSAGE_YESNO = 18;
	final static int DIALOG_ALERT_LOGIN_DELETE_YESNO = 19;
	final static int DIALOG_ALERT_NOLOGIN_YESNO = 20;
	final static int DIALOG_ALERT_NOPASS_YESNO = 21;
	static final int DIALOG_ALERT_BILLING_NOT_STARTED = 22;
	static final int DIALOG_ALERT_BILLING_NOT_SUPPORTED = 23;
	static final int DIALOG_ALERT_PURCHASE_NOT_SUPPORTED = 24;

	final static int TOAST_ALERT_MESSAGE_EXIT = 1;
	final static int TOAST_ALERT_MESSAGE_RES = 2;
	final static int TOAST_ALERT_MESSAGE_STR = 3;

	static final int RESULT_CANCELED = 0;
	static final int RESULT_OK = -1;
	static final int RESULT_FIRST_USER = 1;
	static final int FOCUSED_STATE_SET[] = null;
	static final int DEFAULT_KEYS_DISABLE = 0;
	static final int DEFAULT_KEYS_DIALER = 1;
	static final int DEFAULT_KEYS_SHORTCUT = 2;
	static final int DEFAULT_KEYS_SEARCH_LOCAL = 3;
	static final int DEFAULT_KEYS_SEARCH_GLOBAL = 4;

	// Dialog createDialog(int id);

	// void onPrepareDialog(int id, Dialog dialog);

	// Dialog onCreateDialog(int id);

	void cancelDownloadFile();

	void onDownloadFileComplete(String path);

	void uploadFile();

	void cancelUploadFile();

	void onUploadFileComplete(String path);

	void onUploadFileCancel(String path);
}
