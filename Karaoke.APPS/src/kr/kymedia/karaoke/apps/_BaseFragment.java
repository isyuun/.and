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
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	Karaoke.KPOP
 * filename	:	_BaseFragment.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ _BaseFragment.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

//import is.yuun.com.example.android.displayingbitmaps.ui.RecyclingImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import is.yuun.com.example.android.displayingbitmaps.util.ImageWorker;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.JavascriptCallback;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * NOTE:<br>
 * 베이스루트프래그먼트 <h3 style="color: rgb(51, 51, 51); line-height: 20px; margin: 10px 0px; font-family: Roboto, sans-serif;">
 * <font size="2">Lifecycle</font></h3>
 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
 * <font size="2">Though a Fragment's lifecycle is tied to its owning activity, it has its own wrinkle on the standard activity lifecycle. It includes basic activity lifecycle methods such as&nbsp;
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> , but also important are methods
 * related to interactions with the activity and UI generation.</font>
 * </p>
 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
 * <font size="2">The core series of lifecycle methods that are called to bring a fragment up to resumed state (interacting with the user) are:</font>
 * </p>
 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onAttach(android.app.Activity)" style="color: rgb(37, 138, 175);">onAttach(Activity)</a></code>
 * &nbsp;called once the fragment is associated with its activity.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreate(Bundle)</a></code> &nbsp;called to
 * do initial creation of the fragment.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreateView(LayoutInflater, ViewGroup, Bundle)</a></code>
 * &nbsp;creates and returns the view hierarchy associated with the fragment.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onActivityCreated(android.os.Bundle)" style="color: rgb(37, 138, 175);">onActivityCreated(Bundle)</a></code>
 * &nbsp;tells the fragment that its activity has completed its own&nbsp;
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">Activity.onCreate()</a></code> .</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onViewStateRestored(android.os.Bundle)" style="color: rgb(37, 138, 175);">onViewStateRestored(Bundle)</a></code>
 * &nbsp;tells the fragment that all of the saved state of its view hierarchy has been restored.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStart()" style="color: rgb(37, 138, 175);">onStart()</a></code> &nbsp;makes the fragment visible to the
 * user (based on its containing activity being started).</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> &nbsp;makes the fragment interacting
 * with the user (based on its containing activity being resumed).</font></li>
 * </ol>
 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
 * <font size="2">As a fragment is no longer being used, it goes through a reverse series of callbacks:</font>
 * </p>
 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onPause()" style="color: rgb(37, 138, 175);">onPause()</a></code> &nbsp;fragment is no longer interacting
 * with the user either because its activity is being paused or a fragment operation is modifying it in the activity.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStop()" style="color: rgb(37, 138, 175);">onStop()</a></code> &nbsp;fragment is no longer visible to the
 * user either because its activity is being stopped or a fragment operation is modifying it in the activity.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroyView()" style="color: rgb(37, 138, 175);">onDestroyView()</a></code> &nbsp;allows the fragment to
 * clean up resources associated with its View.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroy()" style="color: rgb(37, 138, 175);">onDestroy()</a></code> &nbsp;called to do final cleanup of
 * the fragment's state.</font></li>
 * <li style="margin: 0px 0px 5px;"><font size="2">
 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDetach()" style="color: rgb(37, 138, 175);">onDetach()</a></code> &nbsp;called immediately prior to the
 * fragment no longer being associated with its activity.</font></li>
 * </ol>
 *
 * @author isyoon
 * @version 1.0
 * @since 2013. 8. 7.
 */
public class _BaseFragment extends BaseFragment5 {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	public _BaseFragment() {
	}


	/**
	 * [출처] 안드로이드 하이브리드 앱 - WebView과 App(NDK)간 통신하기 |작성자 구피
	 *
	 * @author isyoon
	 */
	protected class JavaScriptInterface implements JavascriptCallback {
		@JavascriptInterface
		public void onOptionsItemSelected(final String id) {
			android.util.Log.e("[JavaScript]", "[func]onOptionsItemSelected()\n[id]" + id);
			post(new Runnable() {
				@Override
				public void run() {
					try {
						if (getBaseActivity().onOptionsItemSelected(getResource(id, "id"), "", true) == null) {
							_BaseFragment.this.stopLoading(__CLASSNAME__, getMethodName());
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});
		}

		@JavascriptInterface
		public void onContextItemSelected(final String id, final String info, final String list) {
			android.util.Log.e("[JavaScript]", "[func]onContextItemSelected()\n[id]" + id);
			post(new Runnable() {
				@Override
				public void run() {
					try {
						// if (_IKaraoke.DEBUG)Log.e("[JavaScript]", "[func]onContextItemSelected()\n[id]" + id);
						if (_IKaraoke.DEBUG) Log2.w("[JavaScript]", "[info]" + (new KPItem(info)).toString(2));
						if (_IKaraoke.DEBUG) Log2.w("[JavaScript]", "[list]" + (new KPItem(list)).toString(2));
					} catch (Exception e) {

						e.printStackTrace();
					}
					try {
						if (getBaseActivity().onContextItemSelected(getApp().getApplicationContext(), getResource(id, "id"), new KPItem(info), new KPItem(list), true) == null) {
							_BaseFragment.this.stopLoading(__CLASSNAME__, getMethodName());
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			});
		}

		@JavascriptInterface
		public String getParams() {
			android.util.Log.e("[JavaScript]", "[func]getParams()\n");
			// if (_IKaraoke.DEBUG)Log.e("[JavaScript]", "[func]getParams()\n");
			String params = "";
			if (KP_nnnn != null) {
				params = KP_nnnn.getParams();
			}
			if (_IKaraoke.DEBUG) Log2.w("[JavaScript]", "[PARAMS]\n" + params);
			return params;
		}

		@JavascriptInterface
		public void refresh() {
			// _BaseFragment.this.refresh();
			getBaseActivity().refresh();
		}

		@JavascriptInterface
		public void setRefresh(boolean refresh) {
			getApp().isRefresh = refresh;
		}

		@JavascriptInterface
		public void close() {
			_BaseFragment.this.close();
		}

		@JavascriptInterface
		public void finish() {
			getBaseActivity().finish();
		}

		@JavascriptInterface
		public void openUpdate(final String url) {
			_BaseFragment.this.openUpdate(url);
		}

		@JavascriptInterface
		public void openMarketNReward(final String info, final String list) {
			_BaseFragment.this.openMarketNReward(info, list);
		}

		@JavascriptInterface
		public void requestGoogleINAPP(final String info, final String list) {
			_BaseFragment.this.requestGoogleINAPP(info, list);
		}

		@JavascriptInterface
		public void startLoading() {
			_BaseFragment.this.startLoading(__CLASSNAME__, getMethodName(), true);
		}

		@JavascriptInterface
		final protected void stopLoading() {
			_BaseFragment.this.stopLoading(__CLASSNAME__, getMethodName());
		}

		@JavascriptInterface
		final protected void startLoadingDialog() {
			_BaseFragment.this.startLoadingDialog(null);
		}

		@JavascriptInterface
		final protected void stopLoadingDialog() {
			_BaseFragment.this.stopLoadingDialog(null);
		}
	}

	/**
	 * <h3 style="color: rgb(51, 51, 51); line-height: 20px; margin: 10px 0px; font-family: Roboto, sans-serif;">
	 * <font size="2">Lifecycle</font></h3>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">Though a Fragment's lifecycle is tied to its owning activity, it has its own wrinkle on the standard activity lifecycle. It includes basic activity lifecycle methods such as&nbsp;
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> , but also important are methods
	 * related to interactions with the activity and UI generation.</font>
	 * </p>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">The core series of lifecycle methods that are called to bring a fragment up to resumed state (interacting with the user) are:</font>
	 * </p>
	 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onAttach(android.app.Activity)" style="color: rgb(37, 138, 175);">onAttach(Activity)</a></code>
	 * &nbsp;called once the fragment is associated with its activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreate(Bundle)</a></code> &nbsp;called
	 * to do initial creation of the fragment.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreateView(LayoutInflater, ViewGroup, Bundle)</a></code>
	 * &nbsp;creates and returns the view hierarchy associated with the fragment.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onActivityCreated(android.os.Bundle)" style="color: rgb(37, 138, 175);">onActivityCreated(Bundle)</a></code>
	 * &nbsp;tells the fragment that its activity has completed its own&nbsp;
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">Activity.onCreate()</a></code> .</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onViewStateRestored(android.os.Bundle)" style="color: rgb(37, 138, 175);">onViewStateRestored(Bundle)</a></code>
	 * &nbsp;tells the fragment that all of the saved state of its view hierarchy has been restored.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStart()" style="color: rgb(37, 138, 175);">onStart()</a></code> &nbsp;makes the fragment visible to the
	 * user (based on its containing activity being started).</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> &nbsp;makes the fragment interacting
	 * with the user (based on its containing activity being resumed).</font></li>
	 * </ol>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">As a fragment is no longer being used, it goes through a reverse series of callbacks:</font>
	 * </p>
	 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onPause()" style="color: rgb(37, 138, 175);">onPause()</a></code> &nbsp;fragment is no longer interacting
	 * with the user either because its activity is being paused or a fragment operation is modifying it in the activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStop()" style="color: rgb(37, 138, 175);">onStop()</a></code> &nbsp;fragment is no longer visible to
	 * the user either because its activity is being stopped or a fragment operation is modifying it in the activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroyView()" style="color: rgb(37, 138, 175);">onDestroyView()</a></code> &nbsp;allows the fragment
	 * to clean up resources associated with its View.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroy()" style="color: rgb(37, 138, 175);">onDestroy()</a></code> &nbsp;called to do final cleanup of
	 * the fragment's state.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDetach()" style="color: rgb(37, 138, 175);">onDetach()</a></code> &nbsp;called immediately prior to the
	 * fragment no longer being associated with its activity.</font></li>
	 * </ol>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log.d(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);
		super.onCreate(savedInstanceState);
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	// 멤버변수로 해제할 Set을 생성
	private final List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

	protected void addRecycleViews(ViewGroup convertView) {
		try {
			for (int i = 0; i < convertView.getChildCount(); i++) {
				View v = convertView.getChildAt(i);
				if (v instanceof _ImageView) {
					mRecycleList.add(new WeakReference<View>(v));
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void delRecycleViews() {
		try {
			for (WeakReference<View> ref : mRecycleList) {
				if (ref.get() instanceof _ImageView) {
					ImageWorker.cancelWork((_ImageView) ref.get());
					((_ImageView) ref.get()).setImageDrawable(null);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// private void unbindDrawables(View view) {
	// if (view.getBackground() != null) {
	// view.getBackground().setCallback(null);
	// }
	// if (view instanceof ViewGroup) {
	// for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
	//
	// unbindDrawables(((ViewGroup) view).getChildAt(i));
	// }
	// try {
	// ((ViewGroup) view).removeAllViews();
	// } catch (Exception e) {
	//
	// }
	// }
	// }
	private void unbindDrawables(View view) {
		try {
			if (view == null) {
				return;
			}

			if (view.getBackground() != null) {
				view.getBackground().setCallback(null);
			}

			if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindDrawables(((ViewGroup) view).getChildAt(i));
				}
				((ViewGroup) view).removeAllViews();
			}
		} catch (Exception e) {

			if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + Log.getStackTraceString(e));
			// e.printStackTrace();
		}
	}

	/**
	 * <h3 style="color: rgb(51, 51, 51); line-height: 20px; margin: 10px 0px; font-family: Roboto, sans-serif;">
	 * <font size="2">Lifecycle</font></h3>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">Though a Fragment's lifecycle is tied to its owning activity, it has its own wrinkle on the standard activity lifecycle. It includes basic activity lifecycle methods such as&nbsp;
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> , but also important are methods
	 * related to interactions with the activity and UI generation.</font>
	 * </p>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">The core series of lifecycle methods that are called to bring a fragment up to resumed state (interacting with the user) are:</font>
	 * </p>
	 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onAttach(android.app.Activity)" style="color: rgb(37, 138, 175);">onAttach(Activity)</a></code>
	 * &nbsp;called once the fragment is associated with its activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreate(Bundle)</a></code> &nbsp;called
	 * to do initial creation of the fragment.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreateView(LayoutInflater, ViewGroup, Bundle)</a></code>
	 * &nbsp;creates and returns the view hierarchy associated with the fragment.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onActivityCreated(android.os.Bundle)" style="color: rgb(37, 138, 175);">onActivityCreated(Bundle)</a></code>
	 * &nbsp;tells the fragment that its activity has completed its own&nbsp;
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">Activity.onCreate()</a></code> .</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onViewStateRestored(android.os.Bundle)" style="color: rgb(37, 138, 175);">onViewStateRestored(Bundle)</a></code>
	 * &nbsp;tells the fragment that all of the saved state of its view hierarchy has been restored.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStart()" style="color: rgb(37, 138, 175);">onStart()</a></code> &nbsp;makes the fragment visible to the
	 * user (based on its containing activity being started).</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> &nbsp;makes the fragment interacting
	 * with the user (based on its containing activity being resumed).</font></li>
	 * </ol>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">As a fragment is no longer being used, it goes through a reverse series of callbacks:</font>
	 * </p>
	 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onPause()" style="color: rgb(37, 138, 175);">onPause()</a></code> &nbsp;fragment is no longer interacting
	 * with the user either because its activity is being paused or a fragment operation is modifying it in the activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStop()" style="color: rgb(37, 138, 175);">onStop()</a></code> &nbsp;fragment is no longer visible to
	 * the user either because its activity is being stopped or a fragment operation is modifying it in the activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroyView()" style="color: rgb(37, 138, 175);">onDestroyView()</a></code> &nbsp;allows the fragment
	 * to clean up resources associated with its View.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroy()" style="color: rgb(37, 138, 175);">onDestroy()</a></code> &nbsp;called to do final cleanup of
	 * the fragment's state.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDetach()" style="color: rgb(37, 138, 175);">onDetach()</a></code> &nbsp;called immediately prior to the
	 * fragment no longer being associated with its activity.</font></li>
	 * </ol>
	 */
	protected void onCreateView() {
		if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + mRootView);
		addRecycleViews(mRootView);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	/**
	 * 절~~~대 System.gc() 호출하지 않씀니다.
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbindDrawables(mRootView);
	}

	public void release() {
		try {
			if (mHandlerUI != null) {
				post(new Runnable() {

					@Override
					public void run() {

						delRecycleViews();
						recycleKPs();
					}
				});
			}
			mHandlerUI = null;
			mHandlerQuery = null;
		} catch (Exception e) {

			e.printStackTrace();
		}
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		release();
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());

	}

	@Override
	public void onDetach() {
		super.onDetach();
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());

	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * <h3 style="color: rgb(51, 51, 51); line-height: 20px; margin: 10px 0px; font-family: Roboto, sans-serif;">
	 * <font size="2">Lifecycle</font></h3>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">Though a Fragment's lifecycle is tied to its owning activity, it has its own wrinkle on the standard activity lifecycle. It includes basic activity lifecycle methods such as&nbsp;
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> , but also important are methods
	 * related to interactions with the activity and UI generation.</font>
	 * </p>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">The core series of lifecycle methods that are called to bring a fragment up to resumed state (interacting with the user) are:</font>
	 * </p>
	 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onAttach(android.app.Activity)" style="color: rgb(37, 138, 175);">onAttach(Activity)</a></code>
	 * &nbsp;called once the fragment is associated with its activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreate(Bundle)</a></code> &nbsp;called
	 * to do initial creation of the fragment.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)" style="color: rgb(37, 138, 175);">onCreateView(LayoutInflater, ViewGroup, Bundle)</a></code>
	 * &nbsp;creates and returns the view hierarchy associated with the fragment.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onActivityCreated(android.os.Bundle)" style="color: rgb(37, 138, 175);">onActivityCreated(Bundle)</a></code>
	 * &nbsp;tells the fragment that its activity has completed its own&nbsp;
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Activity.html#onCreate(android.os.Bundle)" style="color: rgb(37, 138, 175);">Activity.onCreate()</a></code> .</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onViewStateRestored(android.os.Bundle)" style="color: rgb(37, 138, 175);">onViewStateRestored(Bundle)</a></code>
	 * &nbsp;tells the fragment that all of the saved state of its view hierarchy has been restored.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStart()" style="color: rgb(37, 138, 175);">onStart()</a></code> &nbsp;makes the fragment visible to the
	 * user (based on its containing activity being started).</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onResume()" style="color: rgb(37, 138, 175);">onResume()</a></code> &nbsp;makes the fragment interacting
	 * with the user (based on its containing activity being resumed).</font></li>
	 * </ol>
	 * <p style="margin-top: 0px; margin-bottom: 15px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <font size="2">As a fragment is no longer being used, it goes through a reverse series of callbacks:</font>
	 * </p>
	 * <ol style="margin: 0px 0px 15px 18px; padding: 0px; color: rgb(34, 34, 34); font-family: Roboto, sans-serif; line-height: 19px;">
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onPause()" style="color: rgb(37, 138, 175);">onPause()</a></code> &nbsp;fragment is no longer interacting
	 * with the user either because its activity is being paused or a fragment operation is modifying it in the activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onStop()" style="color: rgb(37, 138, 175);">onStop()</a></code> &nbsp;fragment is no longer visible to
	 * the user either because its activity is being stopped or a fragment operation is modifying it in the activity.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroyView()" style="color: rgb(37, 138, 175);">onDestroyView()</a></code> &nbsp;allows the fragment
	 * to clean up resources associated with its View.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDestroy()" style="color: rgb(37, 138, 175);">onDestroy()</a></code> &nbsp;called to do final cleanup of
	 * the fragment's state.</font></li>
	 * <li style="margin: 0px 0px 5px;"><font size="2">
	 * <code style="color: rgb(0, 102, 0); line-height: 14px;"><a href="http://developer.android.com/reference/android/app/Fragment.html#onDetach()" style="color: rgb(37, 138, 175);">onDetach()</a></code> &nbsp;called immediately prior to the
	 * fragment no longer being associated with its activity.</font></li>
	 * </ol>
	 */
	@Override
	protected void onActivityCreated() {
		super.onActivityCreated();
	}

	@Override
	protected void start() {
		super.start();

		if (findViewById(R.id.edt_reply_text) != null) {
			WidgetUtils.setEditable(findViewById(R.id.edt_reply_text), true);
			WidgetUtils.hideSoftKeyboard(getApp(), findViewById(R.id.edt_reply_text));
		}

		if (findViewById(R.id.include_reply) != null) {
			if (isLoginUser()) {
				findViewById(R.id.include_reply).setVisibility(View.VISIBLE);
			} else {
				findViewById(R.id.include_reply).setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void KP_nnnn() {
		super.KP_nnnn();
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	@Override
	public void KPnnnn() {
		super.KPnnnn();
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG) EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	@Override
	protected Intent onOptionsItemSelected(int id, String menu_name, boolean open) {
		if (_IKaraoke.APP_API_TEST) {
			boolean block = false;
			// 로그아웃막기
			if (id == R.id.menu_logout) {
				block = true;
			}

			if (block) {
				getApp().popupToast("기능불가 - API테스트모드", Toast.LENGTH_SHORT);
				stopLoading(__CLASSNAME__, getMethodName());
				return null;
			}
		}

		return super.onOptionsItemSelected(id, menu_name, open);
	}

	@Override
	protected Intent onContextItemSelected(Context context, int id, KPItem info, KPItem list, boolean open) {
		if (_IKaraoke.APP_API_TEST) {
			boolean block = false;
			// if (id == R.id.context_freezone) {
			// } else if (id == R.id.context_play_sing) {
			// block = true;
			// } else if (id == R.id.context_play_listen) {
			// block = true;
			// } else {
			// block = true;
			// }
			if (block) {
				getApp().popupToast("기능불가 - API테스트모드", Toast.LENGTH_SHORT);
				stopLoading(__CLASSNAME__, getMethodName());
				return null;
			}
		}

		return super.onContextItemSelected(context, id, info, list, open);
	}

	public void putURLImage(final Context context, final ImageView v, final String url, final boolean resize, final int imageRes) {
		try {
			getBaseActivity().putURLImage(context, v, url, resize, imageRes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	private List<WeakReference<KPnnnn>> KP_xxxxx = new ArrayList<WeakReference<KPnnnn>>();

	@Override
	public KPnnnn KP_init(Context context, KPnnnn KP_xxxx) {
		KP_xxxx = super.KP_init(context, KP_xxxx);
		this.KP_xxxxx.add(new WeakReference<KPnnnn>(KP_xxxx));
		return KP_xxxx;
	}

	private void recycleKPs() {
		try {
			for (WeakReference<KPnnnn> ref : this.KP_xxxxx) {
				if (ref.get() != null) {
					KPnnnn KP_xxxx = ref.get();
					KP_xxxx.release();
					KP_xxxx = null;
				}
				ref.clear();
				ref = null;
			}
			this.KP_xxxxx.clear();
			this.KP_xxxxx = null;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
