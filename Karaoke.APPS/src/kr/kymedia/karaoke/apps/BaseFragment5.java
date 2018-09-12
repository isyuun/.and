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
 * filename	:	BaseFragment5.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ BaseFragment5.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.util.EnvironmentUtils;
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
class BaseFragment5 extends BaseFragment4 {
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
	protected void checkEvironment() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName());
		super.checkEvironment();
	}

	/**
	 * @see BaseFragment3#KP_start()
	 */
	@Override
	protected void KP_start() {
		super.KP_start();
	}

	/**
	 * @see BaseApplication#putLogin(KPItem)
	 */
	@Override
	public int putLogin(KPItem info) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName());
		return super.putLogin(info);
	}

	/**
	 * @see BaseApplication#putLogin(KPItem)
	 */
	@Override
	public void putLogout() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName());
		super.putLogout();
	}

	int id = 0;
	String menu_name = null;
	KPItem info = null;
	KPItem list = null;

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
			refresh();
		}
	};
	DialogInterface.OnCancelListener cPermissionRejectListener = new DialogInterface.OnCancelListener() {
		@Override
		public void onCancel(DialogInterface dialog) {
			if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[REJECT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
			refresh();
		}
	};


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + savedInstanceState);
		super.onActivityCreated(savedInstanceState);

		getBaseActivity().setPermissionRequestListener(pPermissionRequestListener, nPermissionRequestListener, cPermissionRequestListener);
		getBaseActivity().setPermissionRejectListener(pPermissionRejectListener, nPermissionRejectListener, cPermissionRejectListener);

		if (getBaseActivity().isACTIONMAIN()) {
			if (isLoginUser()) {
				getBaseActivity().checkPermission4Account(true);
			}
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		if (getBaseActivity().isACTIONMAIN()) {
			if (isLoginUser()) {
				getBaseActivity().checkPermission4Account(true);
			}
		}
	}

	protected void getGoogleAccount() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ACCOUNT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
		Account accounts[] = EnvironmentUtils.getGoogleAccount(getBaseActivity());
		String email = "";
		if (accounts.length > 0) {
			email = accounts[0].name;
		}

		if (null != getApp().KPparam) {
			getApp().KPparam.setGmail(email);
		}

	}

	protected void newChooseGoogleAccount() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[ACCOUNT]" + (null != getApp().KPparam ? getApp().KPparam.getGmail() : null));
		if (TextUtil.isEmpty(((EditText) findViewById(R.id.edt_login_email)).getText())) {
			EnvironmentUtils.newChooseGoogleAccount(this, _IKaraoke.KARAOKE_INTENT_ACTION_GOOGLE_ACCOUNT_PICKER, false);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "requestCode:" + requestCode + "-" + _IKaraoke.KARAOKE_REQUEST.get(requestCode) + ", resultCode:" + resultCode + "-" + _IKaraoke.KARAOKE_RESULT.get(resultCode) + " - " + data);
		super.onActivityResult(requestCode, resultCode, data);

		if (null != data && null != data.getExtras() && requestCode == _IKaraoke.KARAOKE_INTENT_ACTION_GOOGLE_ACCOUNT_PICKER && resultCode == _IKaraoke.KARAOKE_RESULT_OK) {
			if (_IKaraoke.DEBUG) android.util.Log.e(_toString(), getMethodName() + "requestCode:" + requestCode + "-" + _IKaraoke.KARAOKE_REQUEST.get(requestCode) + ", resultCode:" + resultCode + "-" + _IKaraoke.KARAOKE_RESULT.get(resultCode) + " - " + data.getExtras());

			String name = null;
			for (String key : data.getExtras().keySet()) {
				android.util.Log.e(_toString(), "[ACCOUNT]" + "[" + key + "]" + data.getExtras().get(key));
				name = key;
				if (AccountManager.KEY_ACCOUNT_NAME.equalsIgnoreCase(name)) {
					break;
				}
			}
			if (!TextUtil.isEmpty(name)) {
				String email = data.getStringExtra(name);
				getApp().KPparam.setGmail(email);
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + ":requestCode:" + requestCode + ", permissions:" + permissions + ". grantResults:" + grantResults);
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		boolean refresh = false;

		int idx = 0;
		//for (String key : permissions) {
		//	if (_IKaraoke.DEBUG) Log.e(_toString() + getPackageName(), "[NULL][NG]" + "[" + key + "]" + "[" + permissions[idx] + "]" + "[" + grantResults[idx] + "]");
		//	if (PackageManager.PERMISSION_GRANTED != grantResults[idx]) {
		//		granted &= false;
		//	}
		//	idx++;
		//}
		for (String key : permissions) {
			if (null == getBaseActivity().getRequests().get(key)) {
				Log.e(_toString() + getPackageName(), "[NULL][NG]"/* + "[" + getBaseActivity().getRequests().get(key).group + "]"*/ + getBaseActivity().getRequests().get(key) + ":" + "[" + permissions[idx] + "]" + "[" + grantResults[idx] + "]");
				continue;
			}
			if (getBaseActivity().getRequests().get(key).grant != grantResults[idx]) {
				refresh |= true;
				Log.e(_toString() + getPackageName(), "[REFRESH]" + "[" + getBaseActivity().getRequests().get(key).group + "]" + getBaseActivity().getRequests().get(key) + ":" + getBaseActivity().getRequests().get(key).show + ":" + getBaseActivity().getRequests().get(key).grant + "->" + grantResults[idx]);
			} else {
				refresh &= false;
				Log.e(_toString() + getPackageName(), "[RESULT]" + "[" + getBaseActivity().getRequests().get(key).group + "]" + getBaseActivity().getRequests().get(key) + ":" + getBaseActivity().getRequests().get(key).show + ":" + getBaseActivity().getRequests().get(key).grant + "->" + grantResults[idx]);
			}
			idx++;
		}

		if (id > 0 && refresh) {
			if (TextUtil.isEmpty(menu_name) && null != info && null != list) {
				onContextItemSelected(getBaseActivity(), this.id, this.info, this.list, true);
			} else if (!TextUtil.isEmpty(menu_name) && null == info && null == list) {
				onOptionsItemSelected(this.id, this.menu_name, true);
			}
		}
	}

	/**
	 * @see kr.kymedia.karaoke.apps.BaseFragment2#onOptionsItemSelected(int, String, boolean)
	 */
	@Override
	protected Intent onOptionsItemSelected(int id, String menu_name, boolean open) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[PERMISSION]" + getResourceEntryName(id) + ":" + menu_name + " - " + open);

		boolean rejected = false;

		if (id == R.id.menu_profile_edit) {
			//권한확인
			if (!getBaseActivity().checkPermission4Profile(true)) {
				rejected |= true;
			}
		}

		this.id = 0;
		this.menu_name = null;
		this.info = null;
		this.list = null;

		if (open && rejected) {
			this.id = id;
			this.menu_name = menu_name;
			this.info = null;
			this.list = null;
			return null;
		}

		return super.onOptionsItemSelected(id, menu_name, open);
	}

	protected boolean checkPermission4Sing(boolean request) {
		boolean ret;
		ret = getBaseActivity().checkPermission4Sing(request) && getBaseActivity().checkSDCardExist();
		return ret;
	}

	protected boolean checkPermission4Listen(boolean request) {
		boolean ret;
		ret = getBaseActivity().checkPermission4Listen(request) && getBaseActivity().checkSDCardExist();
		return ret;
	}

	/**
	 * 로그인여부실행전확인
	 * @see kr.kymedia.karaoke.apps.BaseFragment2#onContextItemSelected(Context, int, KPItem, KPItem, boolean)
	 */
	@Override
	protected Intent onContextItemSelected(final Context context, final int id, final KPItem info, final KPItem list, final boolean open) {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName() + "[PERMISSION]" + getResourceEntryName(context, id) + " - " + open);

		boolean rejected = false;

		if (id == R.id.context_play_sing) {
			//권한확인
			if (checkPermission4Sing(false)) {
				//로그인여부실행전확인
				if (!isLoginUser()) {
					rejected |= true;
					getBaseActivity().showAlertDialog(
							//아이콘
						/*android.R.attr.alertDialogIcon*/0,
							//타이틀
							getString(R.string.alert_title_confirm),
							//메시지
							getString(R.string.warning_nologin_preview),
							//확인
							getString(R.string.btn_title_login), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									onOptionsItemSelected(R.id.menu_login, "", true);
								}
							},
							//거부
							getString(R.string.btn_title_ignore), new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (checkPermission4Sing(false)) {
										BaseFragment5.super.onContextItemSelected(context, id, info, list, open);
									} else {
										getApp().popupToast(getString(R.string.permission_message_notice), Toast.LENGTH_LONG);
									}
								}
							},
							//취소
							false, null);
				}
			} else {
				rejected |= true;
				getBaseActivity().requestPermissions();
			}
		} else if (id == R.id.context_play_listen) {
			//권한확인
			if (!checkPermission4Listen(false)) {
				rejected |= true;
				getBaseActivity().requestPermissions();
			}
		}

		this.id = 0;
		this.menu_name = null;
		this.info = null;
		this.list = null;

		if (open && rejected) {
			this.id = id;
			this.menu_name = null;
			this.info = info;
			this.list = list;
			return null;
		}

		return super.onContextItemSelected(context, id, info, list, open);
	}

}
