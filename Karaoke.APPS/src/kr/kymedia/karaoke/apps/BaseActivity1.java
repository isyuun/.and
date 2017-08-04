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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.KPOP
 * filename	:	BaseActivity1.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseActivity1.java
 * </pre>
 */

package kr.kymedia.karaoke.apps;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.impl.IBaseDialog;
import kr.kymedia.karaoke.content.SearchRecentProvider;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.ImageDownLoader3;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

;
//import com.actionbarsherlock.view.Menu;
//import com.actionbarsherlock.view.MenuInflater;
//import com.actionbarsherlock.view.MenuItem;

/**
 * 기본액티비티1<br>
 * 전문(KPnnnn) 추가 전 기본액티비티<br>
 * 절~대 직접비지니스로직에서직접꼬라박지않는다.
 *
 * @author isyoon
 * @since 2012. 12. 4.
 * @version 1.0
 */
class BaseActivity1 extends BaseActivity implements IBaseDialog, OnClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	public BaseActivity1() {
		super();
		__CLASSNAME__ = this.getClass().getSimpleName();
	}

	public final Cursor managedQuery2(Uri uri, String projection[], String selection, String selectionArgs[], String sortOrder) {
		return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
	}

	private boolean mExit = false;

	public boolean isExit() {
		return mExit;
	}

	public boolean setExit(boolean exit) {
		return mExit = exit;
	}

	public void hideSoftInput() {
		View v = getCurrentFocus();
		if (v != null && v instanceof EditText) {
			getApp().hideSoftInput((EditText) v);
		}

		getApp().hideSoftInput();
	}

	// /**
	// * UI처리용핸들러
	// */
	// protected Handler mHandlerUI = new Handler() {
	//
	// /**
	// * public void dispatchMessage(Message msg) 넣으면 어떻게 될까~~~
	// */
	//
	// @Override
	// public void handleMessage(Message msg) {
	//
	// super.handleMessage(msg);
	//
	// int what = msg.what;
	// int resId = msg.arg1;
	// int dur = msg.arg2;
	// Bundle b = msg.getData();
	// String text = b.getString("TOAST_ALERT_MESSAGE");
	//
	// switch (what) {
	// case TOAST_ALERT_MESSAGE_EXIT:
	// mExit = false;
	// break;
	//
	// case TOAST_ALERT_MESSAGE_RES:
	// popupToast(resId, dur);
	// break;
	//
	// case TOAST_ALERT_MESSAGE_STR:
	// popupToast(text, dur);
	// break;
	//
	// default:
	// break;
	// }
	//
	// }
	//
	// /**
	// * @see android.os.Handler#sendMessageAtTime(android.os.Message, long)
	// */
	// @Override
	// public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
	//
	// return super.sendMessageAtTime(msg, uptimeMillis);
	// }
	//
	// };
	/**
	 * UI처리용핸들러 Need handler for callbacks to the UI thread
	 */
	private HandlerUI mHandlerUI;

	public HandlerUI getHandlerUI() {
		return mHandlerUI;
	}

	static class HandlerUI extends Handler {
		WeakReference<BaseActivity1> m_HandlerObj;

		HandlerUI(BaseActivity1 handlerobj) {
			m_HandlerObj = new WeakReference<BaseActivity1>(handlerobj);
		}

		@Override
		public void handleMessage(Message msg) {
			BaseActivity1 handlerobj = m_HandlerObj.get();
			if (handlerobj == null) {
				return;
			}
			super.handleMessage(msg);
			handlerobj.handleMessageUI(msg);
		}
	}

	public void handleMessageUI(Message msg) {

		int what = msg.what;
		int resId = msg.arg1;
		int dur = msg.arg2;
		Bundle b = msg.getData();
		String text = b.getString("TOAST_ALERT_MESSAGE");

		switch (what) {
			case TOAST_ALERT_MESSAGE_EXIT:
				mExit = false;
				break;

			case TOAST_ALERT_MESSAGE_RES:
				getApp().popupToast(resId, dur);
				break;

			case TOAST_ALERT_MESSAGE_STR:
				getApp().popupToast(text, dur);
				break;

			default:
				break;
		}
	}

	@Override
	public void post(Runnable r) {
		if (mHandlerUI != null) {
			mHandlerUI.post(r);
		}

	}

	@Override
	public void postDelayed(Runnable r, long delayMillis) {
		if (mHandlerUI != null) {
			mHandlerUI.postDelayed(r, delayMillis);
		}

	}

	@Override
	public void removeCallbacks(Runnable r) {
		if (mHandlerUI != null) {
			mHandlerUI.removeCallbacks(r);
		}

	}

	/**
	 * <pre>
	 * 볼륨버튼처리
	 * </pre>
	 *
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// Log.d(__CLASSNAME__, "onKeyDown() keyCode:" + keyCode + ", event:" + event);

		AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

		switch (keyCode) {
			case KeyEvent.KEYCODE_VOLUME_UP:
				mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
				return true;
			case KeyEvent.KEYCODE_VOLUME_DOWN:
				mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
				return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 뒤로가기버튼시어플종료메시지처리
	 *
	 */
	@Override
	public void onBackPressed() {

		try {
			String action = getIntent().getAction();
			if (action != null && action.equalsIgnoreCase(Intent.ACTION_MAIN)) {
				if (!mExit) {
					getApp().popupToast(R.string.msg_alert_exit, Toast.LENGTH_SHORT);
					// popupToast(getString("msg_alert_exit"), Toast.LENGTH_SHORT);
					mExit = true;
					mHandlerUI.sendEmptyMessageDelayed(TOAST_ALERT_MESSAGE_EXIT, 2000);
					return;
				} else {
					super.onBackPressed();
				}
			} else {
				super.onBackPressed();
			}
		} catch (Exception e) {

			// Log.e(__CLASSNAME__, Log.getStackTraceString(e));
		}
	}

	/**
	 * Intent.ACTION_MAIN 여부확인 홈화면여부확인
	 */
	protected boolean isACTIONMAIN() {
		if (Intent.ACTION_MAIN.equalsIgnoreCase(getIntent().getAction())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 액티비티 뷰그룹 레이아웃 뷰클릭시
	 *
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);
	}

	/**
	 * 액티비티 리스트 레이아웃 뷰클릭시<br>
	 * 현재 플래그먼트로 이벤트전달
	 *
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onListItemClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);
	}

	public void onMenuClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onTitleChanged(CharSequence title, int color) {

		super.onTitleChanged(title, color);
	}

	/**
	 *
	 * <pre>
	 *  make the title scroll!
	 *  find the title TextView
	 * </pre>
	 *
	 * @param title
	 */
	private void setTextView2Title(TextView title) {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + title);

		if (title == null) {
			return;
		}

		// make the title scroll!
		// find the title TextView
		WidgetUtils.setTextViewMarquee(title);
		WidgetUtils.setTypeFaceBold(title, 1);
		title.setTypeface(Typeface.create("", Typeface.BOLD));

	}

	/**
	 * 타이틀에 텍스트를 넣는다. 진저브레드는 어쩌냐~~~<br>
	 */
	@Override
	public void setTitle(CharSequence title) {

		super.setTitle(title);

		TextView titleView = (TextView) findViewById(R.id.actionbar_compat_title);

		if (titleView != null) {
			titleView.setVisibility(View.VISIBLE);
			titleView.setText(title);
			titleView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
			setTextView2Title(titleView);
		}

		if (getSupportActionBar() != null) {
			if (TextUtil.isEmpty(title)) {
				getSupportActionBar().setDisplayUseLogoEnabled(true);
				getSupportActionBar().setLogo(R.drawable.tit_logo);
				getSupportActionBar().setIcon(R.drawable.tit_logo);
			}
		}

	}

	boolean checkSDCardExist() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + _IKaraoke.KARAOKE_ROOT);

		try {
			String ext = Environment.getExternalStorageState();

			if (!Environment.MEDIA_MOUNTED.equalsIgnoreCase(ext)) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "NOSDCARD - " + Environment.getExternalStorageState());
				showDialog2(DIALOG_ALERT_NOSDCARD, null);
				return false;
			}

			File dir = null;
			// 노래방루트경로
			dir = new File(_IKaraoke.KARAOKE_ROOT);
			if (!dir.isDirectory()) {
				if (!dir.mkdirs()) {
					showDialog2(DIALOG_ALERT_NOSDCARD, null);
					return false;
				}
			}
			// skym다운경로
			dir = new File(_IKaraoke.SKYM_PATH);
			if (!dir.isDirectory()) {
				if (!dir.mkdirs()) {
					showDialog2(DIALOG_ALERT_NOSDCARD, null);
					return false;
				}
			}
			// 녹음공저장경로
			dir = new File(_IKaraoke.RECORD_PATH);
			if (!dir.isDirectory()) {
				if (!dir.mkdirs()) {
					showDialog2(DIALOG_ALERT_NOSDCARD, null);
					return false;
				}
			}
			// 이미지저장경로
			dir = new File(_IKaraoke.IMAGE_PATH);
			if (!dir.isDirectory()) {
				if (!dir.mkdirs()) {
					showDialog2(DIALOG_ALERT_NOSDCARD, null);
					return false;
				}
			}
			// 이미지캐쉬저장경로
			dir = new File(_IKaraoke.IMAGE_CACHE_PATH);
			if (!dir.isDirectory()) {
				if (!dir.mkdirs()) {
					showDialog2(DIALOG_ALERT_NOSDCARD, null);
					return false;
				}
			}
			/// DB경로(??)
			// dir = new File(DATABASE_PATH);
			// if (!dir.exists()) {
			// dir.mkdirs();
			// }
			// 백업경로(??)
			// dir = new File(BACKIMAGE_PATH);
			// if (!dir.exists()) {
			// dir.mkdirs();
			// }
		} catch (Exception e) {

			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "NOPATH - " + Log2.getStackTraceString(e));
			showDialog2(DIALOG_ALERT_NOSDCARD, null);
			return false;
		}
		return true;
	}

	boolean isDestroyed = false;

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void release() {

		if (getApp().getImageDownLoader() != null) {
			getApp().getImageDownLoader().clear();
			if (getApp().getImageDownLoader() instanceof ImageDownLoader3) {
				((ImageDownLoader3) getApp().getImageDownLoader()).setImageLoadingListener(null);
			}
		}
		recycleFragments();

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
		System.gc();
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.e("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName() + "[AF]");
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	private final List<WeakReference<Fragment>> mFragments = new ArrayList<WeakReference<Fragment>>();

	@Override
	public void onAttachFragment(Fragment fragment) {

		super.onAttachFragment(fragment);

		mFragments.add(new WeakReference<Fragment>(fragment));

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	private void recycleFragments() {
		try {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

			for (WeakReference<Fragment> ref : mFragments) {
				Fragment fragment = ref.get();
				if (fragment != null) {
					ft.remove(fragment);
				}
				fragment = null;
			}

			// ft.commit();
			ft.commitAllowingStateLoss();
		} catch (Exception e) {

			// e.printStackTrace();
		}
	}

	/**
	 * 액티비티에 BaseFragment(DialogFragment) 임베드 시키다. BaseFragment(DialogFragment)는 기존에 레이아웃 <fragment>태그는
	 * 지원이 안된다.
	 *
	 * @param containerViewId
	 * @param fragment
	 * @param tag
	 * @return
	 */
	protected void addFragment(final int containerViewId, final BaseFragment2 fragment, final String tag) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
		try {
			// post(new Runnable() {
			// @SuppressLint("NewApi")
			// public void run() {
			// if (isDestroyed() || isFinishing()) {
			// return;
			// }
			// FragmentManager fm = getSupportFragmentManager();
			// FragmentTransaction ft = fm.beginTransaction();
			// ft.add(R.id.fragment1, fragment, tag);
			// //ft.commit();
			// ft.commitAllowingStateLoss();
			// fm.executePendingTransactions();
			// }
			// });
			if (isDestroyed() || isFinishing()) {
				return;
			}
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.fragment1, fragment, tag);
			// ft.commit();
			ft.commitAllowingStateLoss();
			fm.executePendingTransactions();
		} catch (Exception e) {

			e.printStackTrace();
		}

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	protected void replaceFragment(final int containerViewId, final BaseFragment2 fragment, final String tag) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());
		try {
			// post(new Runnable() {
			// @SuppressLint("NewApi")
			// public void run() {
			// if (isDestroyed() || isFinishing()) {
			// return;
			// }
			// FragmentManager fm = getSupportFragmentManager();
			// FragmentTransaction ft = fm.beginTransaction();
			// ft.replace(R.id.fragment1, fragment, tag);
			// //ft.commit();
			// ft.commitAllowingStateLoss();
			// fm.executePendingTransactions();
			// }
			// });
			if (isDestroyed() || isFinishing()) {
				return;
			}
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.fragment1, fragment, tag);
			// ft.commit();
			ft.commitAllowingStateLoss();
			fm.executePendingTransactions();
		} catch (Exception e) {

			e.printStackTrace();
		}

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	@Override
	public _BaseFragment getCurrentFragment() {
		try {
			_BaseFragment fragment = null;
			// fragment = (BaseFragment2) getSupportFragmentManager().findFragmentById(R.id.fragment1);
			fragment = (_BaseFragment) getSupportFragmentManager().findFragmentByTag("fragment1");
			return fragment;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 커스텀개썅바처리
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onCreate(savedInstanceState);

		mHandlerUI = new HandlerUI(this);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onPostCreate(savedInstanceState);

	}

	/**
	 * @see android.support.v4.app.FragmentActivity#onStart()
	 */
	@Override
	protected void onStart() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.onStart();
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 *
	 * @see com.actionbarsherlock.app.SherlockFragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {

		super.onPause();

		if (getApp().getImageDownLoader() != null) {
			getApp().getImageDownLoader().onPause();
		}

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	/**
	 * 절~~~대 System.gc() 호출하지 않씀니다.
	 */
	@Override
	protected void onResume() {

		super.onResume();

		hideSoftInput();

		if (getApp().getImageDownLoader() != null) {
			getApp().getImageDownLoader().onResume();
		}

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void onDestroy() {

		super.onDestroy();

		isDestroyed = true;

		hideSoftInput();

		mHandlerUI = null;

		release();

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", __CLASSNAME__ + ":" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());
	}

	@Override
	protected void finalize() throws Throwable {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.finalize();
		// release();

		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)Log.d("MEMINFO-HEAPINFO", "_BaseActivity::" + getMethodName());
		// if (_IKaraoke.APP_API_TEST || _IKaraoke.DEBUG)EnvironmentUtils.getMemoryInfo(getApp().getApplicationContext(), __CLASSNAME__ + ":" + getMethodName());

	}

	@Override
	public void cancelDownloadFile() {


	}

	@Override
	public void onDownloadFileComplete(String path) {

		// super.onDownloadFileComplete(path);

		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			fragment.onDownloadFileComplete(path);
		}
	}

	@Override
	public void uploadFile() {

		// super.uploadFile();

		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			fragment.uploadFile();
		}
	}

	@Override
	public void onUploadFileComplete(String path) {

		// super.onUploadFileComplete(path);

		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			fragment.onUploadFileComplete(path);
		}
	}

	@Override
	public void cancelUploadFile() {

		// super.cancelUploadFile();

		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			fragment.cancelUploadFile();
		}
	}

	@Override
	public void onUploadFileCancel(String path) {

		// super.onUploadFileCancel(path);

		_BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			fragment.onUploadFileCancel(path);
		}
	}

	protected void deleteAllFile() {
		Log2.d(__CLASSNAME__, "deleteAllFile()");
		Log2.e(__CLASSNAME__, _IKaraoke.RECORD_PATH);
		EnvironmentUtils.deleteAllFile(_IKaraoke.RECORD_PATH);
		Log2.e(__CLASSNAME__, _IKaraoke.RECORD_PATH);
		EnvironmentUtils.deleteAllFile(_IKaraoke.SKYM_PATH);
	}

	/**
	 * 기능 1. 액티비티종료 2. 반주곡/녹음곡삭제
	 */
	protected void close() {

		finish();
	}

	@Override
	public void finish() {
		deleteAllFile();

		super.finish();
	}

	public boolean showDialog2(int id, Bundle args) {
		return showDialog(id, args);
	}

	public void dismissDialog2(int id) {
		dismissDialog(id);
	}

	public void removeDialog2(int id) {
		removeDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		return super.onCreateDialog(id);
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {

		return super.onCreateDialog(id, args);
	}

	public void saveQueryString(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getApplicationContext(), SearchRecentProvider.AUTHORITY, SearchRecentProvider.MODE);
			suggestions.saveRecentQuery(query, null);
		}
	}

	public void clearQueryString() {
		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getApplicationContext(), SearchRecentProvider.AUTHORITY, SearchRecentProvider.MODE);
		suggestions.clearHistory();
	}

	static boolean mAddShortCut = true;
	final static String EXTRA_SHORTCUT_DUPLICATE = "duplicate";

	/**
	 * [출처] Android ShortCut (바로가기)|작성자 아즈라엘<br>
	 * 임시보류: 중복생성오류 - 갤럭시계열(노트,S) 지랄!!!!
	 */
	@Deprecated
	public void addShortCut() {
		// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "\n" + !isACTIONMAIN() + "\n" + mAddShortCut + "\n" + BaseFragment.mSharedPreferences.getBoolean("desktop_shortcut", true));
		// if (!isACTIONMAIN()) {
		// return;
		// }
		//
		// if (!mAddShortCut) {
		// return;
		// }
		// mAddShortCut = false;
		//
		// if (BaseFragment.getSharedPreferences() == null) {
		// return;
		// }
		//
		// if (!BaseFragment.getSharedPreferences().getBoolean("desktop_shortcut", true)) {
		// return;
		// }
		//
		// SharedPreferences.Editor edit = BaseFragment.getSharedPreferences().edit();
		// edit.putBoolean("desktop_shortcut", false);
		// edit.commit();
		//
		// // //AndroidManifest.xml 에 액티비티 태그의 인텐트 필터 태그에 설정된 내용을 읽어옵니다.
		// // final Intent intent = getIntent();
		// // final String action = intent.getAction();
		// //
		// // //바탕화면의 빈공간을 길게 누르고 있으면, 창이 뜨는데,
		// // //거기서 바로가기 항목이 있는데,
		// // //거기서 현재 프로그램의 바로가기를 선택해서 프로그램을 실행하게 되면,
		// // //AndroidManifest.xml 파일에서 <activity-alias android:name ... 태그를 통해서
		// // //실행을 하게되어, 그 태그에 설정된 내용을 수행하게 됩니다. 인텐트 필터라든지~~
		// // //물론 그냥 프로그램 아이콘을 클릭하여 실행하게 되는 경우에는
		// // //<activity-alias 태그가 아닌,원래대로 <activity ... 태그를 통해서 실행하게 됩니다.
		// // //이 프로그램에서는 AndroidManifest.xml 파일에 <activity-alias 태그에
		// // //인텐트필터의 액션이름을 "android.intent.action.CREATE_SHORTCUT"
		// // //이 값으로 설정해 놓았습니다.
		// // //그리고 Intent.ACTION_CREATE_SHORTCUT 값은 출력해보시면
		// // //"android.intent.action.CREATE_SHORTCUT" 이 값이 나옵니다.
		// // //결국, 아이콘을 눌러서 실행하지 않고, 바탕화면에서 바로가기를 통해서
		// // //현재 프로그램을 실행시켰다면,
		// // //이 if문 조건이 만족하여, 바탕화면에 현재 프로그램의 바로가기
		// // //아이콘을 생성하게 됩니다.
		// // //물론 아이콘을 눌러서 실행한 경우에는 이 if문을 그냥 지나치게 됩니다.
		// // //if (Intent.ACTION_CREATE_SHORTCUT.equals(action))
		// // {
		// // //숏컷실행을 위한 정보를 셋팅한 인텐트 생성
		// // Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		// // shortcutIntent.setClassName(getApp(), this.getClass().getName());
		// // shortcutIntent.putExtra(EXTRA_KEY, getString(R.string.hello_world));
		// //
		// // //만들 숏컷의 아이콘을 지정하고, 위의 숏컷이름을 지정한 인텐트를 설정해줍니다.
		// // Intent newIntent = new Intent();
		// // //프로그램 실행을 위한 정보를 설정한 인텐트 설정
		// // newIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		// // //바로가기 아이콘의 이름을 설정
		// // newIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString("appname"));
		// // /* /res/drawable/ 폴더에 있는 이미지명 */
		// // Parcelable iconResource = Intent.ShortcutIconResource.fromContext(getApp(), R.drawable.ic_launcher);
		// // /* 아이콘 이미지 설정 */
		// // newIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
		// // //바로가기 아이콘 만들기..
		// // setResult(Activity.RESULT_OK, newIntent);
		// // //바탕화면에 아이콘만 만들어 놓고, 현재 프로그램을 종료합니다.
		// // //finish();
		// // //return;
		// // if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "\n" + newIntent);
		// // }
		//
		// Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		// shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// shortcutIntent.setClassName(getApp(), getClass().getName());
		// shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
		// | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		//
		// Intent intent = new Intent();
		// intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		// intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString("appname"));
		// intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
		// Intent.ShortcutIconResource.fromContext(getApp(), R.drawable.ic_launcher));
		// intent.putExtra("duplicate", false);
		// //intent.putExtra(EXTRA_SHORTCUT_DUPLICATE, false);
		// intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		// sendBroadcast(intent);
		//
		// if (_IKaraoke.DEBUG)
		// Log.e(__CLASSNAME__, getMethodName() + "\n" + !isACTIONMAIN() + "\n" + mAddShortCut + "\n"
		// + BaseFragment.getSharedPreferences().getBoolean("desktop_shortcut", true));
	}

}
