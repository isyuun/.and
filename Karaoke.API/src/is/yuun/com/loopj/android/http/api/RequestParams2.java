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
 * project	:	Karaoke.KPOP.LIB.LGE.SMARTPHONE
 * filename	:	RequestParams2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.http
 *    |_ RequestParams2.java
 * </pre>
 * 
 */

package is.yuun.com.loopj.android.http.api;

import is.yuun.com.loopj.android.http.RequestParams;
import is.yuun.com.loopj.android.http.SimpleMultipartEntityMonitored;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ConcurrentHashMap;

import kr.kymedia.karaoke.api.Log;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 3. 19.
 * @version 1.0
 * @see RequestParams2.java
 */
class RequestParams2 extends RequestParams {
	private static String ENCODING = "UTF-8";
	protected ProgressListener listener;

	public void put(String key, String fileName, String contentType, ProgressListener listener) {
		this.listener = listener;
		if (key != null) {
			if (this instanceof is.yuun.com.loopj.android.http.RequestParams) {
				fileParams.put(key, new FileWrapper(null, fileName, contentType));
			} else {
				fileParams.put(key, new FileWrapper(new File(fileName), contentType));
			}
		}
	}

	SimpleMultipartEntityMonitored multipartEntity = null;

	public SimpleMultipartEntityMonitored getMultipartEntity() {
		return multipartEntity;
	}

	// @Override
	@Override
	public HttpEntity getEntity() {
		HttpEntity entity = null;

		if (!fileParams.isEmpty()) {
			/* SimpleMultipartEntityMonitored */multipartEntity = new SimpleMultipartEntityMonitored(listener);

			for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
				try {
					multipartEntity.addPart(entry.getKey(), entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
				FileWrapper file = entry.getValue();
				InputStream is = null;
				try {
					Log.e("[PARAM]", "file:" + file + file.getFileName());
					is = new FileInputStream(file.getFileName());
				} catch (Exception e) {

					e.printStackTrace();
				}
				if (is != null) {
					// multipartEntity.addPart(entry.getKey(), new InputStreamBody(is, file.getFileName()));
					multipartEntity.addPart(entry.getKey(), file.getFileName(), is, false);
				} else {
					// multipartEntity.addPart(entry.getKey(), new FileBody(new File(file.getFileName())));
					multipartEntity.addPart(entry.getKey(), new File(file.getFileName()), false);
				}
			}

			entity = multipartEntity;
		} else {
			try {
				entity = new UrlEncodedFormEntity(getParamsList(), ENCODING);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return entity;
	}

	public void clear() {
		if (urlParams != null) {
			urlParams.clear();
		}
		if (fileParams != null) {
			fileParams.clear();
		}
		if (urlParamsWithArray != null) {
			urlParamsWithArray.clear();
		}
	}

	public void release() throws Throwable {
		if (urlParams != null) {
			urlParams.clear();
		}
		urlParams = null;
		if (fileParams != null) {
			fileParams.clear();
		}
		fileParams = null;
		if (urlParamsWithArray != null) {
			urlParamsWithArray.clear();
		}
		urlParamsWithArray = null;
		if (multipartEntity != null) {
			multipartEntity.release();
		}
		multipartEntity = null;
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
		release();
	}

}
