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
 * project	:	Karaoke.APP
 * filename	:	BaseFragment3.java
 * author	:	wyvan
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseFragment3.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPparam;
import kr.kymedia.karaoke.api.Log;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * TODO<br>
 * 
 * <pre>
 * 전문비동기처리
 * </pre>
 * 
 * @author wyvan
 * @since 2013. 11. 30.
 * @version 1.0
 * @see BaseActivity3
 */
class BaseFragment3 extends BaseFragment2 {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {
		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
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

		super.onCreate(savedInstanceState);

		onCreate();

	}

	/**
	 * <pre>
	 * 사용자정보생생
	 * 1.국가정보 스트링/어댑터
	 * 2.로그인확인:비로그인시로그인처리한다.
	 * {@link #onCreate(Bundle)}에서 호출
	 * </pre>
	 */
	private void onCreate() {
		if (_IKaraoke.DEBUG) Log.e(_toString(), getMethodName());
		// 로그인확인:비로그인시로그인처리한다.
		checkLogin(false);
	}

	/**
	 * <pre>
	 * 플래그먼트내 컨트롤 생성처리
	 * {@link #onActivityCreated(Bundle)}에서 호출
	 * </pre>
	 * 
	 */
	protected void onActivityCreated() {
		started = false;
	}

	/**
	 * <pre>
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * </pre>
	 * 
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingIdInfo()
	 */
	private void KP_param() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			if (getApp().KPparam == null || getBaseActivity().isACTIONMAIN()) {
				getApp().KP_param();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 전문처리시작
	 * 
	 * <pre>
	 * 비동기처리를위해 AsyncTask로 호출한다.
	 * 0.화면클릭리스너오픈 - {@link WidgetUtils#setOnClickListener(Context, ViewGroup, android.view.View.OnClickListener, boolean)}
	 * 1.인텐트데이터 - {@link #getIntentKPItem()}
	 * 2.각전문초기화 - {@link #KP_init()}
	 * 3.옵션메뉴활성화 - {@link #setHasOptionsMenu(boolean)}
	 * 4.UI제어시 반드시 Runnable사용한다.
	 * java.lang.IllegalStateException: The content of the adapter has changed but List
	 * View did not receive a notification. Make sure the content of your adapter is no
	 * t modified from a background thread, but only from the UI thread. [in ListView(2
	 * 131427573, class android.widget.ListView) with Adapter(class android.widget.Head
	 * erViewListAdapter)]
	 * </pre>
	 * 
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingIdInfo()
	 * 
	 */
	protected void start() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		WidgetUtils.setOnClickListener(mContext, (ViewGroup) getView(), this, true);
		// WidgetUtils.setOnLongClickListener(mContext, (ViewGroup) getBaseActivity().getWindow()
		// .getDecorView().findViewById(android.R.id.content), this, true);

		// 이전화면에서의 주요정보를 전달받는다.
		getIntentKPItem();

		// 전문초기화
		KP_init();

		// 메뉴활성화
		setHasOptionsMenu(true);

		// 타이틀처리
		if (getBaseActivity() != null && !TextUtil.isEmpty(getMenuName())) {
			if (this == getBaseActivity().getCurrentFragment()) {
				getBaseActivity().setTitle(getMenuName());
			}
		}
	}

	private boolean started = false;

	void KP_start() {
		if (getActivity() != null) {
			(new KP_start()).execute();
		}
	}
	/**
	 * 전문처리시작
	 * 
	 * <pre>
	 * 반드시 비동기처리를위해 AsyncTask로 호출한다.
	 * 1.폰정보확인
	 * 2.저장정보확인
	 * 3.로그인정보확인
	 * 4.전문초기화및조회시작
	 * </pre>
	 * 
	 * @see KPparam#KPparam(Context, boolean)
	 * @see KPparam#getAdvertisingIdInfo()
	 * 
	 */
	private class KP_start extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {

			KP_param();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			if (isAttached()) {
				new Runnable() {
					@Override
					public void run() {
						start();
						KP_nnnn();
						started = true;
					}
				}.run();
			}
		}

	}

	/**
	 * 조회함수호출(KP_nnnn()/KP_list())<br>
	 * 상속된 프래그먼트 메서드의 마지막에 호출한다.<br>
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
	 * 
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);


		super.onActivityCreated(savedInstanceState);


		onActivityCreated();

		KP_start();
	}

	@Override
	public void onResume() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + "started:" + started);


		super.onResume();

		if (started) {

		}

		started = true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + getApp().isRefresh + ", requestCode:" + requestCode + ", resultCode:" + resultCode + ", data:" + data);

		setResult(resultCode, data);


		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case _IKaraoke.KARAOKE_INTENT_ACTION_DEFAULT:
			break;

		case _IKaraoke.KARAOKE_INTENT_ACTION_REFRESH:
			if (resultCode == _IKaraoke.KARAOKE_RESULT_REFRESH) {
				getApp().isRefresh = true;
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "isRefresh:" + getApp().isRefresh);
			} else if (resultCode == _IKaraoke.KARAOKE_RESULT_FINISH) {
				close();
			}
			break;
		case _IKaraoke.KARAOKE_INTENT_ACTION_LOGIN:
			if (resultCode == _IKaraoke.KARAOKE_RESULT_REFRESH) {
				getApp().isRefresh = true;
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "isRefresh:" + getApp().isRefresh);
			}
			break;

		case _IKaraoke.KARAOKE_INTENT_ACTION_SHARE:
			break;

		default:
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + requestCode + ", " + resultCode + ", " + data);
			break;
		}
	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.refresh();
	}

}
