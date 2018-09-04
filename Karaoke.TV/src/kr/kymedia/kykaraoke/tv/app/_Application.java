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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.TV
 * filename	:	_Application.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.kykaraoke.tv.app
 *    |_ _Application.java
 * </pre>
 */

package kr.kymedia.kykaraoke.tv.app;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.ImageDownLoader3;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv._Main;
import kr.kymedia.kykaraoke.tv._Video;
import kr.kymedia.kykaraoke.api.IKaraokeTV;

/**
 * TODO<br>
 * <p/>
 * <pre></pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 2. 11.
 */
public class _Application extends kr.kymedia.karaoke.app.MultiDexApplication {
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

	private IImageDownLoader mImageDownLoader;

	public void initImageDownLoader() {
		if (mImageDownLoader == null) {
			mImageDownLoader = new ImageDownLoader3(this);

			DisplayImageOptions option = new DisplayImageOptions.Builder()
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.build();

			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
					.defaultDisplayImageOptions(option)
					.threadPoolSize(3) // default:3
					.threadPriority(Thread.NORM_PRIORITY - 2) // default:Thread.NORM_PRIORITY - 2
					.build();

			((ImageDownLoader3) mImageDownLoader).init(config);
			((ImageDownLoader3) mImageDownLoader).setIsAnimation(false);
		}
	}

	public ImageDownLoader3 getImageDownLoader() {
		return (ImageDownLoader3) mImageDownLoader;
	}

	@Override
	public void onCreate() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		super.onCreate();

		initImageDownLoader();
	}

	@Override
	public void onTerminate() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + newConfig);
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
		super.onLowMemory();
	}

	@Override
	public void onTrimMemory(int level) {

		super.onTrimMemory(level);
	}

	_Main main;

	public _Main getMainActivity() {
		return main;
	}

	public void setMainActivity(_Main main) {
		this.main = main;
	}

	_Video video;

	public _Video getVideoActivity() {
		return video;
	}

	public void setVideoActivity(_Video video) {
		this.video = video;
	}

}
