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
 * 2016 All rights (c)KYmedia Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	BaseApplication4.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseApplication4.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.PermissionUtil;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * <pre>
 * 안드로이드 6.0 마시멜롱 런타임 권한
 * <a href="https://developer.android.com/intl/ko/training/permissions/index.html">Working with System Permissions</a>
 * <a href="https://www.google.com/design/spec/patterns/permissions.html">Permissions</a>
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-18
 */
class BaseApplication4 extends BaseApplication3 {
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
	public void onCreate() {
		super.onCreate();

		putGroups();
		putPermissions();
	}

	PERMISSION PERMISSION(Context context, String name, String group) throws Exception {
		PERMISSION ret = null;
		if (null != getPackageManager().getPermissionInfo(name, PackageManager.GET_META_DATA) && null != getPackageManager().getPermissionGroupInfo(group, PackageManager.GET_META_DATA)) {
			ret = new PERMISSION(name, group, ContextCompat.checkSelfPermission(this, name), false);
			if (null != context && context instanceof _BaseActivity) {
				ret.show = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, name);
			}
		}
		return ret;
	}

	/**
	 * 권한그룹목록(전체)
	 */
	private HashMap<String, ArrayList<String>> groups = new HashMap<>();

	///**
	// * 권한그룹목록(전체)
	// */
	//public HashMap<String, ArrayList<String>> getGroups() {
	//	return groups;
	//}

	private void putGroups() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		this.groups.clear();

		List<PermissionGroupInfo> groups = PermissionUtil.getPermissionGroupInfos(this);

		if (groups != null) {
			for (PermissionGroupInfo group : groups) {
				try {
					PermissionGroupInfo g = getPackageManager().getPermissionGroupInfo(group.name, PackageManager.GET_META_DATA);
					if (_IKaraoke.DEBUG) Log.e(_toString(), "[GROUP][INFO]" + g.name/* + "-" + g.packageName*/ + "-" + g.loadLabel(getPackageManager()) + ":" + g.loadDescription(getPackageManager()) + "-" + g.nonLocalizedDescription + ":" + g.toString());
					ArrayList<String> keys = PermissionUtil.getPermissionNames4Group(this, group.name);
					ArrayList<String> permissions = new ArrayList<>();
					for (String key : keys) {
						PermissionInfo p = getPackageManager().getPermissionInfo(key, PackageManager.GET_META_DATA);
						if (_IKaraoke.DEBUG) Log.i(_toString(), "[PERMISSION][INFO]" + p.name/* + "-" + p.packageName*/ + "-" + p.loadLabel(getPackageManager()) + ":" + p.loadDescription(getPackageManager()) + "-" + p.nonLocalizedDescription + ":" + p.toString());
						permissions.add(key);
					}
					this.groups.put(group.name, permissions);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

	String getGroupName4Permission(String permission) {
		String ret = null;

		for (String key : this.groups.keySet()) {
			ArrayList<String> permissions = this.groups.get(key);
			if (permissions.indexOf(permission) > -1) {
				ret = key;
				break;
			}
		}

		return ret;
	}

	/**
	 * <pre>
	 * 권한상세목록(사용)
	 * 그룹목록확인후거른다.
	 * </pre>
	 */
	private HashMap<String, PERMISSION> permissions = new HashMap<>();

	/**
	 * <pre>
	 * 권한상세목록(사용)
	 * 그룹목록확인후거른다.
	 * </pre>
	 */
	HashMap<String, PERMISSION> getPermissions() {
		return permissions;
	}

	/**
	 * <pre>
	 * 권한상세목록(사용)
	 * 그룹목록확인후거른다.
	 * 뭘까?:그룹에속하지않는권한이란거다.고로관리할필요가없다.
	 * </pre>
	 */
	void putPermissions() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]");

		permissions.clear();
		PackageInfo packageInfo = null;

		try {
			packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
		} catch (Exception e) {
			e.printStackTrace();
		}


		//Get Permissions
		String[] requestedPermissions = packageInfo.requestedPermissions;

		if (requestedPermissions != null) {
			for (String name : requestedPermissions) {
				try {
					PermissionInfo info = getPackageManager().getPermissionInfo(name, PackageManager.GET_META_DATA);
					if (_IKaraoke.DEBUG) Log.i(_toString(), "[PERMISSION][INFO]" + info);
					String group = getGroupName4Permission(name);
					// 뭘까?:그룹에속하지않는권한이란거다.고로관리할필요가없다.
					if (!TextUtil.isEmpty(group)) {
						Log.e(_toString(), "[PERMISSION][INFO][OK][" + group + "][" + getPackageManager().getPermissionInfo(name, PackageManager.GET_META_DATA));
						permissions.put(name, PERMISSION(getBaseActivity(), name, group));
					} else {
						if (_IKaraoke.DEBUG) Log.e(_toString(), "[PERMISSION][INFO][NG][" + group + "][" + getPackageManager().getPermissionInfo(name, PackageManager.GET_META_DATA));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]");
	}

}
