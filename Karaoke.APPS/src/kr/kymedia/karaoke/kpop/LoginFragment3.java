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
 * filename	:	LoginFragment3.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ LoginFragment3.java
 * </pre>
 */
package kr.kymedia.karaoke.kpop;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;
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
 * @since 2016-05-23
 */
class LoginFragment3 extends LoginFragment2 {
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

	DialogInterface.OnClickListener pPermissionRequestListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
		}
	};
	DialogInterface.OnClickListener nPermissionRequestListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
		}
	};
	DialogInterface.OnCancelListener cPermissionRequestListener = new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
		}
	};

	DialogInterface.OnClickListener pPermissionRejectListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
		}
	};
	DialogInterface.OnClickListener nPermissionRejectListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
			newChooseGoogleAccount();
		}
	};
	DialogInterface.OnCancelListener cPermissionRejectListener = new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
			newChooseGoogleAccount();
		}
	};

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment5#onActivityCreated(Bundle)
	 */
	@Override
	protected void onActivityCreated() {
		super.onActivityCreated();

		getBaseActivity().setPermissionRequestListener(pPermissionRequestListener, nPermissionRequestListener, cPermissionRequestListener);
		getBaseActivity().setPermissionRejectListener(pPermissionRejectListener, nPermissionRejectListener, cPermissionRejectListener);

		if (!getBaseActivity().checkPermission4Login(true)) {
			setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		boolean granted = true;

		int idx = 0;
		for (String key : permissions) {
			if (_IKaraoke.DEBUG) Log.e(_toString() + getPackageName(), "[NULL][NG]" + "[" + key + "]" + "[" + permissions[idx] + "]" + "[" + grantResults[idx] + "]");
			if (PackageManager.PERMISSION_GRANTED != grantResults[idx]) {
				granted &= false;
			}
			idx++;
		}

		if (granted) {
			getGoogleAccount();
			setResult(_IKaraoke.KARAOKE_RESULT_REFRESH, null);
		}

	}

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment5#onActivityResult(int, int, Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "requestCode:" + requestCode + "-" + _IKaraoke.KARAOKE_REQUEST.get(requestCode) + ", resultCode:" + resultCode + "-" + _IKaraoke.KARAOKE_RESULT.get(resultCode) + " - " + data);
		super.onActivityResult(requestCode, resultCode, data);

		String mail = (null != getApp().KPparam ? getApp().KPparam.getGmail() : null);

		if (null != data && null != data.getExtras() && requestCode == _IKaraoke.KARAOKE_INTENT_ACTION_GOOGLE_ACCOUNT_PICKER && resultCode == _IKaraoke.KARAOKE_RESULT_OK) {
			if (!TextUtil.isEmpty(mail)) {
				((EditText) findViewById(R.id.edt_login_email)).setText(mail);
			}
		} else {
			getGoogleAccount();
		}
	}

	@Override
	protected boolean checkLogin(boolean login) {
		return super.checkLogin(login);
	}
}
