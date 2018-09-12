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
 * filename	:	ListenPostFragment3.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenPostFragment3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO<br>
 * NOTE:<br>
 * 유튜브업로드기능
 * 
 * @author isyoon
 * @since 2013. 8. 27.
 * @version 1.0
 * @see
 */
public class ListenPostFragment3 extends ListenPostFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	void uploadRecord() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		if (TextUtil.getMimeTypeFromFile(mRecordPath).contains("video/")) {
			youTubeUpload();
		} else {
			super.uploadRecord();
		}
	}

	void youTubeUpload() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// KPItem info = getInfo();
		KPItem list = getList();

		String title = list.getValue("feel_title") + " - " + list.getValue(LOGIN.KEY_NICKNAME);
		String mime = TextUtil.getMimeTypeFromFile(mRecordPath);
		String path = mRecordPath;

		ContentValues content = new ContentValues(4);
		content.put(MediaStore.Video.VideoColumns.TITLE, title);
		content.put(MediaStore.Video.VideoColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
		content.put(MediaStore.Video.Media.MIME_TYPE, mime);
		content.put(MediaStore.Video.Media.DATA, path);
		ContentResolver resolver = mContext.getContentResolver();
		Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setPackage("com.google.android.youtube");
		intent.setType("video/*");
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivityForResult(Intent.createChooser(intent, "Share using"), _IKaraoke.KARAOKE_RESULT_REFRESH);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		// super.onActivityResult(requestCode, resultCode, data);

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + data);
	}

}
