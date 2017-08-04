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
 * 2015 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	BaseActivity.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke.play
 *    |_ BaseActivity.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.ImageDownLoader3;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import kr.kymedia.kykaraoke.tv.BuildConfig;
import kr.kymedia.kykaraoke.tv._Main;
import kr.kymedia.kykaraoke.tv.api.IKaraokeTV;

/**
 * <pre>
 * inflate함수추가
 * </pre>
 *
 * @author isyoon
 * @since 2015. 2. 3.
 * @version 1.0
 */
public class _Activity extends kr.kymedia.karaoke.app.Activity implements OnClickListener, OnTouchListener, OnFocusChangeListener {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private String _toString() {

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

	public void doAlertExit(String title, String message) {
		Log._LOG(_toString(), "doAlertExit() "/*getMethodName()*/, title, message);

		AlertDialog.Builder ab = new AlertDialog.Builder(this);

		ab.setTitle(title).setMessage(message).setPositiveButton("확 인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		}).setCancelable(true).setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});

		AlertDialog dialog = ab.create();
		dialog.show();
	}

	public void doAlert(String title, String message, final boolean exit) {
		Log._LOG(_toString(), "doAlert() "/*getMethodName()*/ + message, "" + exit);

		AlertDialog.Builder ab = new AlertDialog.Builder(this);

		ab.setTitle(title).setMessage(message).setPositiveButton("확 인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (exit) {
					finish();
				}
			}
		}).setCancelable(exit).setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				if (exit) {
					finish();
				}
			}
		});

		AlertDialog dialog = ab.create();
		dialog.show();
	}

	public _Application get_Application() {
		super.getApplication();
		return (_Application) getApplicationContext();
	}

	private LayoutInflater inflater;

	/**
	 * @see android.view.LayoutInflater Inflate a new view hierarchy from the specified xml resource. Throws {@link InflateException} if there is an error.
	 * 
	 * @param resource
	 *          ID for an XML layout resource to load (e.g., <code>R.layout.main_page</code>)
	 * @param root
	 *          Optional view to be the parent of the generated hierarchy.
	 * @return The root View of the inflated hierarchy. If root was supplied, this is the root View; otherwise it is the root of the inflated XML file.
	 */
	public View inflate(int resource, ViewGroup root) {
		return inflater.inflate(resource, root);
	}

	/**
	 * Inflate a new view hierarchy from the specified xml resource. Throws
	 * {@link InflateException} if there is an error.
	 *
	 * @param resource ID for an XML layout resource to load (e.g.,
	 *        <code>R.layout.main_page</code>)
	 * @param root Optional view to be the parent of the generated hierarchy (if
	 *        <em>attachToRoot</em> is true), or else simply an object that
	 *        provides a set of LayoutParams values for root of the returned
	 *        hierarchy (if <em>attachToRoot</em> is false.)
	 * @param attachToRoot Whether the inflated hierarchy should be attached to
	 *        the root parameter? If false, root is only used to create the
	 *        correct subclass of LayoutParams for the root view in the XML.
	 * @return The root View of the inflated hierarchy. If root was supplied and
	 *         attachToRoot is true, this is root; otherwise it is the root of
	 *         the inflated XML file.
	 */
	public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
		return inflater.inflate(resource, root, attachToRoot);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

		super.onCreate(savedInstanceState, persistentState);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	ViewGroup mRootView;

	protected ViewGroup getRootView() {
		return mRootView;
	}

	protected View getFocusedChild() {
		return mRootView.getFocusedChild();
	}

	@Override
	public void setContentView(int layoutResID) {

		super.setContentView(layoutResID);

		// mRootView = (ViewGroup) findViewById(R.id.main);
		// mRootView = (ViewGroup) findViewById(android.R.id.content);
		mRootView = (ViewGroup) getWindow().getDecorView().getRootView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}

	public void startMainActivity(Bundle bundle) {
		Intent intent = new Intent(getApplicationContext(), _Main.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Log.wtf(_toString(), "startMainActivity()" + bundle + ":" + intent);
		startActivity(intent);
	}

	// @Deprecated
	// public void startPlayActivity(Bundle bundle) {
	// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + bundle);
	// Intent intent = new Intent(getApplicationContext(), Play.class);
	// if (bundle != null) {
	// intent.putExtras(bundle);
	// }
	// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// startActivity(intent);
	// }

	// public void startVideoActivity(Bundle bundle) {
	// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), getMethodName() + bundle);
	// Intent intent = new Intent(getApplicationContext(), _Video.class);
	// if (bundle != null) {
	// intent.putExtras(bundle);
	// }
	// startActivity(intent);
	// }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);
	}

	public ImageDownLoader3 getImageDownLoader() {
		return get_Application().getImageDownLoader();
	}

	public void putURLImage(final ImageView v, final String url, final boolean resize) {
		if (TextUtil.isNetworkUrl(url) && v != null) {
			//v.setImageBitmap(null);
			// v.setVisibility(View.INVISIBLE);
			getImageDownLoader().putURLImage(getApplicationContext(), v, url, resize, 0);
		}
	}

	public void putURLImage(final ImageView v, final String url, final boolean resize, final int imageRes) {
		if (TextUtil.isNetworkUrl(url) && v != null) {
			//v.setImageBitmap(null);
			// v.setVisibility(View.INVISIBLE);
			getImageDownLoader().putURLImage(getApplicationContext(), v, url, resize, imageRes);
		}
	}

	@Override
	public View findViewById(int id) {
		// if (IKaraokeTV.DEBUG) _LOG.d(_toString(), getMethodName() + getResourceEntryName(id));

		return super.findViewById(id);
	}

	public String getResourceEntryName(int resid) {

		if (resid > 0) {
			try {
				return getResources().getResourceEntryName(resid);
			} catch (NotFoundException e) {

				if (IKaraokeTV.DEBUG) e.printStackTrace();
				return "" + resid;
			}
		}

		return "" + resid;
	}

	public String getResourceEntryName(View v) {
		try {
			int resid = 0;

			if (v != null) {
				resid = v.getId();
			}

			return getResourceEntryName(resid);
		} catch (Exception e) {

			e.printStackTrace();
			return "(null)";
		}
	}

	public int getIdentifier(String name, String defType) {
		try {
			return getResources().getIdentifier(name, defType, getPackageName());
		} catch (Exception e) {

			// if (IKaraokeTV.DEBUG) _LOG.e(_toString(), _LOG.getStackTraceString(e));
			if (IKaraokeTV.DEBUG) e.printStackTrace();
			return 0;
		}
	}

	protected void setTextViewMarquee(final TextView tv, boolean enable) {
		if (tv != null) {
			WidgetUtils.setTextViewMarquee(tv, enable);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "onTouch() " + getResourceEntryName(v) + ", " + event + ":" + v);
		return false;
	}

	@Override
	public void onClick(View v) {

		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "onClick() " + getResourceEntryName(v) + ", " + getResourceEntryName(getFocusedChild()) + ":" + v + ":" + getFocusedChild());
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "onFocusChange() " + getResourceEntryName(v) + ", " + hasFocus + ":" + getResourceEntryName(getFocusedChild()) + ":" + v + ":" + getFocusedChild());
	}

	/**
	 * <pre>
	 * 포커스해제
	 * </pre>
	 */
	protected void clearFocus(View v) {
		if (v instanceof EditText) if (IKaraokeTV.DEBUG) Log.v("[KYK.keyboard]", "clearFocus() " + getResourceEntryName(v) + ":" + v);
		if (v != null) {
			v.setSelected(false);
			v.clearFocus();
			v.setFocusable(false);
			v.setFocusableInTouchMode(false);
		}
	}

	/**
	 * <pre>
	 * 포커스요청
	 * </pre>
	 */
	protected void requestFocus(View v) {
		if (v instanceof EditText) if (IKaraokeTV.DEBUG) Log.v("[KYK.keyboard]", "requestFocus() " + getResourceEntryName(v) + ":" + v);
		if (v != null) {
			v.setFocusableInTouchMode(true);
			v.setFocusable(true);
			v.requestFocus();
			v.setSelected(false);
		}
	}

	/**
	 * <pre>
	 * 소프트키보드 - 유무확인
	 * </pre>
	 */
	protected boolean isShowSoftKeyboard() {

		boolean ret = false;
		try {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			ret = imm.isAcceptingText();
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "isShowSoftKeyboard() " + ":" + ret + ":" + getResourceEntryName(getFocusedChild()) + ":" + getFocusedChild());
		return ret;
	}

	private View keyView;

	/**
	 * <pre>
	 * 소프트키보드 - 보이기
	 * </pre>
	 */
	protected void showSoftKeyboard(View v) {

		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "showSoftKeyboard() " + getResourceEntryName(v) + ":" + getResourceEntryName(getFocusedChild()) + ":" + v + ":" + getFocusedChild());
		try {
			if (v != null) {
				// 포커스먼저
				// v.requestFocus();
				// 키보드보임
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(v, 0);
				this.keyView = v;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 소프트키보드 - 가리기
	 * </pre>
	 */
	protected void hideSoftKeyboardNoWhereFast() {
		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "hideSoftKeyboardNoWhereFast() " + getResourceEntryName(getFocusedChild()) + ":" + getFocusedChild());
		try {
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			if (this.keyView != null) {
				hideSoftKeyboard(this.keyView);
			} else {
				hideSoftKeyboard(getFocusedChild());
			}
			this.keyView = null;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 소프트키보드 - 가리기
	 * </pre>
	 */
	protected void hideSoftKeyboard(View v) {
		if (IKaraokeTV.DEBUG) Log.i("[KYK.keyboard]", "hideSoftKeyboard() " + getResourceEntryName(v) + ":" + getResourceEntryName(getFocusedChild()) + ":" + v + ":" + getFocusedChild());
		try {
			if (v != null) {
				// 키보드숨김
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				// 포커스나중
				// v.clearFocus();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void setVisible(View v, boolean visible) {
		if (v == null) {
			return;
		}

		if (visible && v.getVisibility() != View.VISIBLE) {
			v.setVisibility(View.VISIBLE);
		}

		if (!visible && v.getVisibility() != View.INVISIBLE) {
			v.setVisibility(View.INVISIBLE);
		}
	}

	// protected void setVisibility(View popup, int visibility) {
	// try {
	// if (popup != null) {
	// popup.setVisibility(View.VISIBLE);
	// }
	// } catch (Exception e) {
	//
	// _LOG.e(_toString(), "removePopup()" + "[NG]" + getResourceEntryName(popup) + "," + visibility);
	// e.printStackTrace();
	// }
	// }
	protected void setVisibility(View v, int visibility) {
		if (v == null) {
			return;
		}
		v.setVisibility(visibility);
	}

	protected int getResource(String name, String defType) {
		try {
			return getResources().getIdentifier(name, defType, getPackageName());
		} catch (Exception e) {
			// e.printStackTrace();
			return 0;
		}
	}
}
