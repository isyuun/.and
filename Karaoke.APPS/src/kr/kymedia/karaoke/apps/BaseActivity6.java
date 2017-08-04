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
 * filename	:	BaseActivity6.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseActivity6.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.kpop._home;
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
 * @since 2016-05-16
 */
class BaseActivity6 extends BaseActivity5 {
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

	/**
	 * @see BaseActivity3#KP_start()
	 */
	@Override
	void KP_start() {
		super.KP_start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + savedInstanceState);
		super.onCreate(savedInstanceState);

		//test
		//this.requests = getPermissions();
		//requestPermissions();
	}

	PERMISSION PERMISSION(String name, String group) throws Exception {
		PERMISSION ret = getApp().PERMISSION(this, name, group);
		return ret;
	}

	public HashMap<String, PERMISSION> getPermissions() {
		return getApp().getPermissions();
	}

	/**
	 * 요청된권한
	 */
	private HashMap<String, PERMISSION> requests = new HashMap<>();

	public HashMap<String, PERMISSION> getRequests() {
		return requests;
	}

	//private int checkSelfPermission(Context context, String permission) {
	//	int ret = ContextCompat.checkSelfPermission(context, permission);
	//	//if (PackageManager.PERMISSION_GRANTED != ret) {
	//	//	if (_IKaraoke.DEBUG) Log.i(_toString(), "[GRANT]" + "[NO]" + grant + ":" + ret);
	//	//} else {
	//	//	if (_IKaraoke.DEBUG) Log.i(_toString(), "[GRANT]" + "[OK]" + grant + ":" + ret);
	//	//}
	//	return ret;
	//}
	//
	//private boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
	//	boolean ret = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
	//	//if (ret) {
	//	//	if (_IKaraoke.DEBUG) Log.i(_toString(), "[SHOW]" + "[OK]" + grant + ":" + ret);
	//	//} else {
	//	//	if (_IKaraoke.DEBUG) Log.i(_toString(), "[SHOW]" + "[NG]" + grant + ":" + ret);
	//	//}
	//	return ret;
	//}

	private void requestPermissions(final Activity activity, final String[] permissions, final int requestCode) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + ":activity:" + activity + ", permissions:" + permissions + ", requestCode:" + requestCode);
		ActivityCompat.requestPermissions(activity, permissions, requestCode);
	}

	public void refresh(String method) {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + method + ":" + getApp().isRefresh);

		refresh();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + ":requestCode:" + requestCode + ", permissions:" + permissions + ", grantResults:" + grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		getCurrentFragment().onRequestPermissionsResult(requestCode, permissions, grantResults);

		int idx = 0;

		boolean refresh = false;

		ArrayList<String> grants = new ArrayList<>();
		ArrayList<String> rejects = new ArrayList<>();

		for (String key : permissions) {
			if (null == this.requests.get(key)) {
				Log.e(_toString() + getPackageName(), "[NULL][NG]"/* + "[" + this.requests.get(key).group + "]"*/ + this.requests.get(key) + ":" + "[" + permissions[idx] + "]" + "[" + grantResults[idx] + "]");
				continue;
			}
			if (this.requests.get(key).grant != grantResults[idx]) {
				refresh |= true;
				Log.e(_toString() + getPackageName(), "[REFRESH]" + "[" + this.requests.get(key).group + "]" + this.requests.get(key) + ":" + this.requests.get(key).show + ":" + this.requests.get(key).grant + "->" + grantResults[idx]);
			} else {
				Log.e(_toString() + getPackageName(), "[RESULT]" + "[" + this.requests.get(key).group + "]" + this.requests.get(key) + ":" + this.requests.get(key).show + ":" + this.requests.get(key).grant + "->" + grantResults[idx]);
			}
			this.requests.get(key).grant = grantResults[idx];
			if (PackageManager.PERMISSION_GRANTED == grantResults[idx]) {
				grants.add(key);
			} else {
				rejects.add(key);
			}
			idx++;
		}

		getApp().putPermissions();

		if ((this instanceof _home) && refresh) {
			refresh(getMethodName());
		}

		ArrayList<String> groups = new ArrayList<>();

		groups.clear();
		for (String key : rejects) {
			if (null != this.requests.get(key) && groups.indexOf(this.requests.get(key).group) < 0) {
				groups.add(this.requests.get(key).group);
			}
		}
		showPermissionRejectedAlert(null, rejects, groups);

		groups.clear();
		for (String key : grants) {
			if (null != this.requests.get(key) && groups.indexOf(this.requests.get(key).group) < 0) {
				groups.add(this.requests.get(key).group);
			}
		}
		showPermissionGrantedAlert(null, grants, groups);

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ":requestCode:" + requestCode + ", permissions:" + permissions + ", grantResults:" + grantResults);
	}

	protected void showInstalledAppDetailsActivity() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName());

		final Intent intent = new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:" + getPackageName()));
		//intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_PERMISSION_UPDATE);
	}

	DialogInterface.OnClickListener pPermissionRequestListener = null;
	DialogInterface.OnClickListener nPermissionRequestListener = null;
	DialogInterface.OnCancelListener cPermissionRequestListener = null;

	public void setPermissionRequestListener(DialogInterface.OnClickListener p, DialogInterface.OnClickListener n, DialogInterface.OnCancelListener c) {
		this.pPermissionRequestListener = p;
		this.nPermissionRequestListener = n;
		this.cPermissionRequestListener = c;
	}

	private void showPermissionRequestAlert(String purpose, final ArrayList<String> requests, final ArrayList<String> groups) {
		Log.e(_toString(), getMethodName() + "[ST]" + purpose + ":" + requests + ":" + groups);

		if (null == requests || requests.size() < 1) {
			return;
		}

		if (null == groups || groups.size() < 1) {
			return;
		}

		Set<String> gs = new HashSet<>();
		gs.addAll(groups);
		groups.clear();
		groups.addAll(gs);

		List<String> labels = new ArrayList<>();
		String message = (!TextUtil.isEmpty(purpose) ? purpose + "\n" : "");

		message += getString(R.string.permission_message_notice) + "\n";
		for (String group : groups) {
			try {
				PermissionGroupInfo info = getPackageManager().getPermissionGroupInfo(group, PackageManager.GET_META_DATA);
				labels.add(info.loadLabel(getPackageManager()).toString());
				message += "\n" + String.format(getString(R.string.permission_message_requests), info.loadLabel(getPackageManager()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		message += "\n";
		message += "\n" + String.format(getString(R.string.permission_message_guide), labels);
		message += "\n";
		message += "\n";

		if (_IKaraoke.DEBUG) getApp().popupToast(message, Toast.LENGTH_SHORT);

		showAlertDialog(
				//아이콘
				android.R.attr.alertDialogIcon,
				//타이틀
				getString(R.string.permission_title_request),
				//메시지
				message,
				//확인
				getString(R.string.btn_title_ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String[] permissions = requests.toArray(new String[requests.size()]);
						requestPermissions(BaseActivity6.this, permissions, 0);
						if (pPermissionRequestListener != null) {
							pPermissionRequestListener.onClick(dialog, which);
						}
					}
				},
				//거부
				getString(R.string.btn_title_cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showPermissionRejectedAlert(null, requests, groups);
						if (nPermissionRequestListener != null) {
							nPermissionRequestListener.onClick(dialog, which);
						}
					}
				},
				//취소
				true, new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						showPermissionRejectedAlert(null, requests, groups);
						if (cPermissionRequestListener != null) {
							cPermissionRequestListener.onCancel(dialog);
						}
					}
				});

		Log.e(_toString(), getMethodName() + "[ED]" + purpose + ":" + requests + ":" + groups);
	}

	DialogInterface.OnClickListener pPermissionRejectListener = null;
	DialogInterface.OnClickListener nPermissionRejectListener = null;
	DialogInterface.OnCancelListener cPermissionRejectListener = null;

	public void setPermissionRejectListener(DialogInterface.OnClickListener p, DialogInterface.OnClickListener n, DialogInterface.OnCancelListener c) {
		this.pPermissionRejectListener = p;
		this.nPermissionRejectListener = n;
		this.cPermissionRejectListener = c;
	}

	private void showPermissionRejectedAlert(String purpose, final ArrayList<String> rejects, final ArrayList<String> groups) {
		Log.e(_toString(), getMethodName() + "[ST]" + purpose + ":" + rejects + ":" + groups);

		if (null == rejects || rejects.size() < 1) {
			return;
		}

		if (null == groups || groups.size() < 1) {
			return;
		}

		Set<String> gs = new HashSet<>();
		gs.addAll(groups);
		groups.clear();
		groups.addAll(gs);

		List<String> labels = new ArrayList<>();
		String message = (!TextUtil.isEmpty(purpose) ? purpose + "\n" : "");

		message += getString(R.string.permission_message_notice) + "\n";
		for (String group : groups) {
			try {
				PermissionGroupInfo info = getPackageManager().getPermissionGroupInfo(group, PackageManager.GET_META_DATA);
				labels.add(info.loadLabel(getPackageManager()).toString());
				message += "\n" + String.format(getString(R.string.permission_message_rejects), info.loadLabel(getPackageManager()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		message += "\n";
		message += "\n" + String.format(getString(R.string.permission_message_guide), labels);
		message += "\n";
		message += "\n";

		if (_IKaraoke.DEBUG) getApp().popupToast(message, Toast.LENGTH_SHORT);

		showAlertDialog(
				//아이콘
				android.R.attr.alertDialogIcon,
				//타이틀
				getString(R.string.permission_title_reject),
				//메시지
				message,
				//확인
				getString(R.string.btn_title_ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showInstalledAppDetailsActivity();
						if (pPermissionRejectListener != null) {
							pPermissionRejectListener.onClick(dialog, which);
						}
					}
				},
				//거부
				getString(R.string.btn_title_cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (nPermissionRejectListener != null) {
							nPermissionRejectListener.onClick(dialog, which);
						}
					}
				},
				//취소
				true, new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						if (cPermissionRejectListener != null) {
							cPermissionRejectListener.onCancel(dialog);
						}
					}
				});

		Log.e(_toString(), getMethodName() + "[ED]" + purpose + ":" + rejects + ":" + groups);
	}

	private void showPermissionGrantedAlert(String purpose, final ArrayList<String> grants, final ArrayList<String> groups) {
		Log.e(_toString(), getMethodName() + "[ST]" + purpose + ":" + grants + ":" + groups);

		if (null == grants || grants.size() < 1) {
			return;
		}

		if (null == groups || groups.size() < 1) {
			return;
		}

		Set<String> gs = new HashSet<>();
		gs.addAll(groups);
		groups.clear();
		groups.addAll(gs);

		List<String> labels = new ArrayList<>();
		String message = (!TextUtil.isEmpty(purpose) ? purpose + "\n" : "");

		message += getString(R.string.permission_message_granted) + "\n";
		for (String group : groups) {
			try {
				PermissionGroupInfo info = getPackageManager().getPermissionGroupInfo(group, PackageManager.GET_META_DATA);
				labels.add(info.loadLabel(getPackageManager()).toString());
				message += "\n" + String.format(getString(R.string.permission_message_grants), info.loadLabel(getPackageManager()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		message += "\n";
		message += "\n" + String.format(getString(R.string.permission_message_guide), labels);
		message += "\n";
		message += "\n";

		if (_IKaraoke.DEBUG) getApp().popupToast(message, Toast.LENGTH_SHORT);

		showAlertDialog(
				//아이콘
				android.R.attr.alertDialogIcon,
				//타이틀
				getString(R.string.permission_title_grant),
				//메시지
				message,
				//확인
				getString(R.string.btn_title_ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				},
				//거부
				null, null,
				//취소
				true, null);

		Log.e(_toString(), getMethodName() + "[ED]" + purpose + ":" + grants + ":" + groups);
	}

	protected void requestPermissions() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + this.requests);

		ArrayList<String> requests = new ArrayList<>();

		boolean show = false;

		for (String key : this.requests.keySet()) {
			show |= this.requests.get(key).show = ActivityCompat.shouldShowRequestPermissionRationale(this, key);
			if (_IKaraoke.DEBUG) Log.e(_toString(), "[REQUEST]" + "[" + this.requests.get(key).group + "]" + this.requests.get(key).name + ":" + this.requests.get(key).show + ":" + this.requests.get(key).grant);
			requests.add(key);
		}

		if (_IKaraoke.IS_ABOVE_MARSHMELLOW || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			ArrayList<String> groups = new ArrayList<>();

			for (String key : requests) {
				if (groups.indexOf(this.requests.get(key).group) < 0) {
					groups.add(this.requests.get(key).group);
				}
			}

			if (requests.size() > 0) {
				if (show) {
					showPermissionRequestAlert(null, requests, groups);
				} else {
					requestPermissions(this, requests.toArray(new String[requests.size()]), 0);
				}
			}
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + show);
	}


	protected void clearRequests() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + this.requests);

		if (null != this.requests) {
			this.requests.clear();
		}
	}

	/**
	 * 폰정보
	 */
	protected boolean checkPermissionPHONE(boolean request) throws Exception {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		if (request) {
			this.requests.clear();
		}

		if (null != getPermissions().get(Manifest.permission.READ_PHONE_STATE)) {
			PERMISSION permission = PERMISSION(Manifest.permission.READ_PHONE_STATE, Manifest.permission_group.PHONE);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (null != getPermissions().get(Manifest.permission.CALL_PHONE)) {
			PERMISSION permission = PERMISSION(Manifest.permission.CALL_PHONE, Manifest.permission_group.PHONE);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (null != getPermissions().get(Manifest.permission.PROCESS_OUTGOING_CALLS)) {
			PERMISSION permission = PERMISSION(Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission_group.PHONE);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * SD카드
	 */
	protected boolean checkPermissionSTORAGE(boolean request) throws Exception {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		if (request) {
			this.requests.clear();
		}

		if (null != getPermissions().get(Manifest.permission.READ_EXTERNAL_STORAGE)) {
			PERMISSION permission = PERMISSION(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission_group.STORAGE);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (null != getPermissions().get(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			PERMISSION permission = PERMISSION(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission_group.STORAGE);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * 녹음
	 */
	protected boolean checkPermissionMICROPHONE(boolean request) throws Exception {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		if (request) {
			this.requests.clear();
		}

		if (null != getPermissions().get(Manifest.permission.RECORD_AUDIO)) {
			PERMISSION permission = PERMISSION(Manifest.permission.RECORD_AUDIO, Manifest.permission_group.MICROPHONE);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * 주소록
	 */
	protected boolean checkPermissionCONTACTS(boolean request) throws Exception {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		if (request) {
			this.requests.clear();
		}

		if (null != getPermissions().get(Manifest.permission.GET_ACCOUNTS)) {
			PERMISSION permission = PERMISSION(Manifest.permission.GET_ACCOUNTS, Manifest.permission_group.CONTACTS);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (null != getPermissions().get(Manifest.permission.READ_CONTACTS)) {
			PERMISSION permission = PERMISSION(Manifest.permission.READ_CONTACTS, Manifest.permission_group.CONTACTS);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (null != getPermissions().get(Manifest.permission.WRITE_CONTACTS)) {
			PERMISSION permission = PERMISSION(Manifest.permission.WRITE_CONTACTS, Manifest.permission_group.CONTACTS);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

	/**
	 * 카메라
	 */
	protected boolean checkPermissionCAMERA(boolean request) throws Exception {
		boolean ret = true;
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + request);

		if (request) {
			this.requests.clear();
		}

		if (null != getPermissions().get(Manifest.permission.CAMERA)) {
			PERMISSION permission = PERMISSION(Manifest.permission.CAMERA, Manifest.permission_group.CAMERA);
			if (null != permission && PackageManager.PERMISSION_GRANTED != permission.grant) {
				ret &= false;
				this.requests.put(permission.name, permission);
			}
		}

		if (request) {
			requestPermissions();
		}

		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + ret);
		return ret;
	}

}
