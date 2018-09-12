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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	BaseActivity7.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseActivity7.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;;
import kr.kymedia.karaoke.PermissionUtil;
import kr.kymedia.karaoke.kpop._home;

/**
 * <pre>
 * 안드로이드 6.0 마시멜롱 런타임 권한
 * <a href="https://developer.android.com/intl/ko/training/permissions/index.html">Working with System Permissions</a>
 * <a href="https://www.google.com/design/spec/patterns/permissions.html">Permissions</a>
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-20
 */
class BaseActivity7 extends BaseActivity6 {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "requestCode:" + requestCode + "-" + _IKaraoke.KARAOKE_REQUEST.get(requestCode) + ", resultCode:" + resultCode + "-" + _IKaraoke.KARAOKE_RESULT.get(resultCode) + " - " + data);

		if ((this instanceof _home) && null != _IKaraoke.KARAOKE_REQUEST.get(requestCode)) {
			switch (_IKaraoke.KARAOKE_REQUEST.get(requestCode)) {
				//case KARAOKE_INTENT_ACTION_DEFAULT:
				//	break;
				//case KARAOKE_INTENT_ACTION_LOGIN:
				//	break;
				//case KARAOKE_INTENT_ACTION_REFRESH:
				//	break;
				//case KARAOKE_INTENT_ACTION_SHARE:
				//	break;
				//case KARAOKE_INTENT_ACTION_COMMENT:
				//	break;
				//case KARAOKE_INTENT_ACTION_PICK:
				//	break;
				//case KARAOKE_INTENT_ACTION_PICK_FROM_IMAGE:
				//	break;
				//case KARAOKE_INTENT_ACTION_PICK_FROM_CAMERA:
				//	break;
				//case KARAOKE_INTENT_ACTION_CROP_FROM_IMAGE:
				//	break;
				//case KARAOKE_INTENT_ACTION_CROP_FROM_CAMERA:
				//	break;
				//case KARAOKE_INTENT_ACTION_CROP_FROM_FILE:
				//	break;
				case KARAOKE_INTENT_ACTION_PERMISSION_UPDATE:
					refresh();
					break;
				default:
					break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * @see BaseActivity6#checkPermissionCONTACTS(boolean)
	 * @see BaseActivity6#checkPermissionPHONE(boolean)
	 */
	public boolean checkPermission4Account(boolean request) {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		clearRequests();

		try {
			ret &= checkPermissionCONTACTS(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret &= checkPermissionPHONE(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * @see BaseActivity6#checkPermissionSTORAGE(boolean)
	 * @see BaseActivity6#checkPermissionMICROPHONE(boolean)
	 */
	public boolean checkPermission4Sing(boolean request) {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		clearRequests();

		try {
			ret &= checkPermissionSTORAGE(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret &= checkPermissionMICROPHONE(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * @see BaseActivity6#checkPermissionSTORAGE(boolean)
	 */
	public boolean checkPermission4Listen(boolean request) {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		clearRequests();

		try {
			ret &= checkPermissionSTORAGE(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * @see BaseActivity6#checkPermissionSTORAGE(boolean)
	 * @see BaseActivity6#checkPermissionCAMERA(boolean)
	 */
	public boolean checkPermission4Profile(boolean request) {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		clearRequests();

		try {
			ret &= checkPermissionSTORAGE(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ret &= checkPermissionCAMERA(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * @see BaseActivity6#checkPermissionCONTACTS(boolean)
	 */
	public boolean checkPermission4Login(boolean request) {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		clearRequests();

		try {
			ret &= checkPermissionCONTACTS(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	@Override
	boolean checkSDCardExist() {
		boolean ret = false;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + "[SDCARD][CHECK]" + ret);
		try {
			if (!checkPermissionSTORAGE(false)) {
				ret |= true;
				requestPermissions();
			} else {
				ret = super.checkSDCardExist();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + "[SDCARD][CHECK]" + ret);
		return ret;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		boolean check = false;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + check);

		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		int idx = 0;

		List<PermissionInfo> ps = PermissionUtil.getPermissionInfos4Group(this, Manifest.permission_group.STORAGE);

		ArrayList<String> requests = new ArrayList<>();
		for (PermissionInfo key : ps) {
			if (_IKaraoke.DEBUG) Log.e(_toString() + getPackageName(), "[CHECK]" + key);
			requests.add(key.name);
		}

		for (String key : permissions) {
			PermissionInfo p;
			try {
				p = getPackageManager().getPermissionInfo(key, PackageManager.GET_META_DATA);
				Log.e(_toString() + getPackageName(), "[RESULT]" + p + ":" + (requests.indexOf(p.name) > -1) + "->" + grantResults[idx]);
				if (ps != null && requests.indexOf(p.name) > -1 && PackageManager.PERMISSION_GRANTED == grantResults[idx]) {
					check |= true;
				}
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
				continue;
			}
		}

		if (check) {
			checkSDCardExist();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + check);
	}
}
