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
 * filename	:	BuildUtil.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.util
 *    |_ BuildUtil.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.util.gtv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 *
 * TODO<br>
 * NOTE:<br>
 *
 * @author isyoon
 * @since 2013. 7. 16.
 * @version 1.0
 * @see BuildUtils.java
 */
public class BuildUtils {

	private static PackageManager getPackageManager(Context context) {
		try {
			return context.getPackageManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getPackageName(Context context) {
		try {
			return context.getPackageName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static PackageInfo getPackageInfo(Context context) {
		try {
			return getPackageManager(context).getPackageInfo(getPackageName(context), 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getDate(String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat(dateFormat, Locale.getDefault()).format(calendar.getTime());
	}

	public static String getDate(String dateFormat, long currenttimemillis) {
		return new SimpleDateFormat(dateFormat, Locale.getDefault()).format(currenttimemillis);
	}

	public static long getBuildDate(Context context) {

		long time = 0l;

		try {
			ApplicationInfo ai = getPackageManager(context)
					.getApplicationInfo(getPackageName(context), 0);
			ZipFile zf = new ZipFile(ai.sourceDir);
			ZipEntry ze = zf.getEntry("classes.dex");
			time = ze.getTime();
			zf.close();

		} catch (Exception e) {
			return time;
		}

		return time;
	}

}
