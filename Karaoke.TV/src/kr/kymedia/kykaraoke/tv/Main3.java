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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.TV
 * filename	:	Main2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.kykaraoke
 *    |_ Main2.java
 * </pre>
 * 
 */

package kr.kymedia.kykaraoke.tv;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import kr.kymedia.karaoke.widget.util.ImageDownLoader3;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.tv.data._Util;

/**
 *
 * TODO<br>
 * 
 * <pre>
 * home.xml레이아웃적용(기능)
 * 이미지다운로더기능추가
 * 녹음곡리스트
 * </pre>
 *
 * @author isyoon
 * @since 2015. 1. 28.
 * @version 1.0
 * 
 * @see kr.kymedia.kykaraoke.tv.app._Application#mImageDownLoader
 * @see kr.kymedia.kykaraoke.tv.app._Activity#getImageDownLoader()
 * @see kr.kymedia.kykaraoke.tv.app._Activity#putURLImage(android.widget.ImageView, String, boolean, int)
 * @see kr.kymedia.karaoke.widget.util.ImageDownLoader3
 */
class Main3 extends Main2XXXXX {
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

	@Override
	protected void HideVirtualRemote() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		findViewById(R.id.layout_remote).setVisibility(View.INVISIBLE);
	}

	protected void ShowVirtualRemote() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		findViewById(R.id.layout_remote).setVisibility(View.VISIBLE);
	}

	/**
	 * 상단툴바
	 */
	ViewGroup mLayoutTop;

	/**
	 * <pre>
	 * 레이아웃/뷰/컨트롤 초기화
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Main2#setBaseLayout()
	 */
	@Override
	protected void setBaseLayout() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		super.setBaseLayout();

		// 탑
		mLayoutTop = (ViewGroup) findViewById(R.id.layout_top);

		// 홈
		m_layoutHome = (ViewGroup) findViewById(R.id.layout_home);

		// 메인
		m_layoutMain = (ViewGroup) findViewById(R.id.layout_main);

		setVisibleMain(true);

		HideNumberSearch();

		// 예약곡목록
		setTextViewMarquee((TextView) findViewById(R.id.txt_top_engage_list), true);

		// 예약곡목록
		if (findViewById(R.id.txt_top_engage_list) != null) {
			((TextView) findViewById(R.id.txt_top_engage_list)).setText("");
		}

		// 검색곡목록
		if (findViewById(R.id.txt_top_search_item) != null) {
			((TextView) findViewById(R.id.txt_top_search_item)).setText("");
		}

		HideVirtualRemote();

		// 2Depth 서브메뉴
		layout_menu_sub = (ViewGroup) findViewById(R.id.layout_menu_sub);

		// 3Depth 컨텐츠
		layout_content = (ViewGroup) findViewById(R.id.layout_content);

		mLayoutListBack = findViewById(R.id.layout_list_back);

		mArrowListLeft = findViewById(R.id.list_arrow_left);

		mArrowListRight = findViewById(R.id.list_arrow_right);
	}

	@Override
	protected void setVisibleTop(boolean visible) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + visible + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (visible) {
			mLayoutTop.setVisibility(View.VISIBLE);
		} else {
			mLayoutTop.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void hideBackBoard() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":" + PANE_STATE.get(m_iPaneState));
		// if (findViewById(R.id.img_background) != null) {
		// findViewById(R.id.img_background).setVisibility(View.INVISIBLE);
		// }
		if (video != null) {
			video.hideBackBoard();
		}
	}

	@Override
	public void showBackBoard() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":" + PANE_STATE.get(m_iPaneState));
		// if (findViewById(R.id.img_background) != null) {
		// findViewById(R.id.img_background).setVisibility(View.VISIBLE);
		// }
		if (video != null) {
			video.showBackBoard();
		}
	}

	/**
	 * 일단막고
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Main2#setContentViewKaraoke(View)
	 */
	@Deprecated
	@Override
	public void setContentView(View view) {

		// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + getResourceEntryName(view) + ":" + view);
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + view + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		// super.setContentView(view);
	}

	/**
	 * 일단막고
	 * 
	 * @see kr.kymedia.kykaraoke.tv.Main2#setContentViewKaraoke(View)
	 */
	@Deprecated
	@Override
	public void setContentView(View view, LayoutParams params) {

		// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + getResourceEntryName(view) + ":" + view);
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + view + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		// super.setContentView(view, params);
	}

	@Override
	public void setContentViewKaraoke(View view) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + view + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		super.setContentViewKaraoke(view);

		_childViews(view);

		setVisibleMain(true);

	}

	/**
	 * <pre>
	 * 셀렉터사용포커싱처리
	 * 돼지는줄알았네.....
	 * </pre>
	 */
	@Override
	protected void inflateListSong(int res) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(findViewById(res)) + ":" + findViewById(res));
		super.inflateListSong(res);

		for (SingLine line : sing_line) {
			line.layout.setClickable(true);
			line.layout.setOnClickListener(this);
		}
	}

	@Override
	protected void inflateListListen(int res) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(findViewById(res)) + ":" + findViewById(res));
		super.inflateListListen(res);
		//for (ListenLine line : listen_line) {
		//	line.layout.setClickable(true);
		//	line.layout.setOnClickListener(this);
		//	if (line.img_focus != null) {
		//		line.img_focus.setClickable(true);
		//		line.img_focus.setOnClickListener(this);
		//	}
		//}
	}

	@Override
	public void onClick(View v) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(v) + ":" + v);
		super.onClick(v);

		HideMessageCommon();

		if (v.getId() == R.id.img_ky_logo) {
		} else if (v.getId() == R.id.btn_home_main_01) {
			onBtnHomeSing(v);
		} else if (v.getId() == R.id.btn_home_main_02) {
			onBtnHomeListen(v);
		} else if (v.getId() == R.id.btn_home_main_03) {
			onBtnHomeMy(v);
		} else if (v.getId() == R.id.btn_home_main_04) {
			onBtnHomeShop(v);
		} else if (v.getId() == R.id.btn_home_main_05) {
			onBtnHomeCustomer(v);
		} else if (v.getId() == R.id.btn_main_sing) {
			onBtnMainSing(v);
		} else if (v.getId() == R.id.btn_main_listen) {
			onBtnMainListen(v);
		} else if (v.getId() == R.id.btn_main_my) {
			onBtnMainMy(v);
		} else if (v.getId() == R.id.btn_main_shop) {
			onBtnMainShop(v);
		} else if (v.getId() == R.id.btn_main_customer) {
			onBtnMainCustomer(v);
		}
	}

	/**
	 * <pre>
	 * 반주곡리스트유무확인(좀구려~~~)
	 * true:없어
	 * false:있어
	 * </pre>
	 * @see kr.kymedia.kykaraoke.tv.Main2#CheckNotContentsSongList()
	 */
	@Override
	protected boolean CheckNotContentsSongList() {
		boolean ret = true;
		// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + ret);
		int index = 0;

		index = (m_iCurrentSongListPage - 1) * 6 + remote.m_iSongListFocus;

		//인기장르
		if (isGenre()) {
			index = (m_iCurrentSongListPage - 1) * 5 + remote.m_iSongListFocus - 1;
		}

		if ((remote.m_iState == STATE_SONG_LIST || remote.m_iState == STATE_MY_LIST) && remote.m_iSongListFocus < 6) {
			ret = !(index < mSongItems.size());
		} else {
			ret = true;
		}

		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + ret + "-" + remote.isGenre() + ":" + m_iCurrentSongListPage + ":" + remote.m_iSongListFocus + ":" + index + "-" + mSongItems.size());
		return ret;
	}

	@Override
	protected boolean CheckNotContentsMyRecordList() {
		boolean ret = super.CheckNotContentsMyRecordList();
		return ret;
	}

	@Override
	protected void CheckNotItemOnListenList() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.CheckNotItemOnListenList();
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#CheckNotContentsCustomerList()
	 */
	@Override
	protected boolean CheckNotContentsCustomerList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":m_iCustomerListFocus:" + remote.m_iCustomerListFocus);

		boolean ret = true;
		try {
			// ret = super.CheckNotContentsCustomerList();
			int idx = 0;
			if (remote.m_iCustomerListFocus < 6) {
				idx = (m_iCurrentCustomerListPage - 1) * 6;
				// 이동예정인덱스라1안뺀다~~~
				idx += remote.m_iCustomerListFocus;
				ret = !(idx < mCustomerItems.size());
			} else {
				ret = true;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + ret);
		return ret;
	}

	@Override
	protected boolean CheckNotContentsSearchList() {

		boolean ret = super.CheckNotContentsSearchList();
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ret);
		return ret;
	}

	@Override
	protected void clickGUI() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":" + remote.getState());
		hideSoftKeyboardNoWhereFast();
		super.clickGUI();
	}

	@Override
	protected void exitGUI() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":" + remote.getState());
		hideSoftKeyboardNoWhereFast();
		super.exitGUI();
	}

	@Override
	public void HideMessageOk() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.HideMessageOk();
		if (findViewById(R.id.message_ok) != null) {
			findViewById(R.id.message_ok).setVisibility(View.INVISIBLE);
		}

	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#HideMessageCommon()
	 */
	@Override
	protected void HideMessageCommon() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.HideMessageCommon();

	}

	@Override
	protected void ShowMessageNotResponse(String title, String message) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + title + ", " + message);
		// super.ShowMessageNotResponse(title, message);
		stopLoading(getMethodName());
		ShowMessageCommon(CLOSE_AUTO, title, message);
	}

	@Override
	protected void HideMessageOkCancel() {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		super.HideMessageOkCancel();
		if (message_okcancel != null) {
			message_okcancel.setVisibility(View.INVISIBLE);
			m_iMessageOkCancelFocusX = POPUP_CANCEL;
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main2#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + savedInstanceState);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + savedInstanceState);
		super.onPostCreate(savedInstanceState);

		// wakeLockAquire();
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + savedInstanceState + ", " + persistentState);
		super.onPostCreate(savedInstanceState, persistentState);

		// wakeLockAquire();
	}

	@Override
	protected void onResume() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.onResume();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void onPause() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.onPause();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void onStop() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.onStop();
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	protected void onDestroy() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");
		super.onDestroy();

		wakeLockRelease();

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + newConfig);
		super.onConfigurationChanged(newConfig);
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + newConfig);
	}

	@Override
	public void clickMenuSing() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.m_iSongListDetailFocus);
		if (mSongItems == null || mSongItems.size() == 0) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + mSongItems);
			return;
		}
		super.clickMenuSing();
	}

	@Override
	public void clickMenuSingGenre() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.m_iSongListDetailFocus);
		if (mSongItems == null || mSongItems.size() == 0) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + mSongItems);
			return;
		}
		super.clickMenuSingGenre();
	}

	@Override
	public void clickMenuListen() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName());
		if (mListenItems == null || mListenItems.size() == 0) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + mListenItems);
			return;
		}
		super.clickMenuListen();
	}

	@Override
	public void clickListSong() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.m_iSongListFocus);
		if (remote.m_iSongListFocus < 1 || mSongItems == null || mSongItems.size() == 0) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + mSongItems);
			return;
		}
		super.clickListSong();
		if (remote.m_iSongListFocus > 0 && mSongItems != null && mSongItems.size() > 1) {
			displayDetailSong(REMOTE_NONE);
		}
	}

	@Override
	public void clickListSearch() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.m_iSearchListFocus);
		if (remote.m_iSearchListFocus < 1 || mSearchItems == null || mSearchItems.size() == 0) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + mSearchItems);
			return;
		}
		super.clickListSearch();
		if (remote.m_iSearchListFocus > 0 && mSearchItems != null && mSearchItems.size() > 1) {
			displayDetailSong(REMOTE_NONE);
		}
	}

	@Override
	public void clickListListen() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.m_iListenListFocusX + ":" + remote.m_iListenListFocusY);
		if (mListenItems == null || mListenItems.size() == 0) {
			if (BuildConfig.DEBUG) Log.wtf(_toString() + TAG_ERR, "[NG]" + getMethodName() + mListenItems);
			return;
		}
		super.clickListListen();
	}

	/**
	 * <pre>
	 * 이미지다운로더기능추가
	 * </pre>
	 * 
	 * @see kr.kymedia.kykaraoke.tv.app._Activity#putURLImage(android.widget.ImageView, java.lang.String, boolean, int)
	 */
	@Override
	public void putURLImage(ImageView v, String url, boolean resize, int imageRes) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + v + ", " + url + ", " + resize + ", " + getResourceEntryName(findViewById(imageRes)));
		super.putURLImage(v, url, resize, imageRes);
	}

	@Override
	public void setHOME() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]");

		util_profileHome = new _Util(handlerKP);
		util_profile01 = new _Util(handlerKP);
		util_profile02 = new _Util(handlerKP);
		util_profile03 = new _Util(handlerKP);
		util_profile04 = new _Util(handlerKP);
		util_profile05 = new _Util(handlerKP);
		util_profile06 = new _Util(handlerKP);
		util_profile07 = new _Util(handlerKP);
		util_profile08 = new _Util(handlerKP);
		util_profileListeningOther = new _Util(handlerKP);
		util_myRecordProfile = new _Util(handlerKP);

		if (getImageDownLoader() instanceof ImageDownLoader3) {
			((ImageDownLoader3) getImageDownLoader()).setImageLoadingListener(new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {

					if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());

				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

					if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
					// handlerKARA.removeCallbacks(stopLoading);
					// handlerKARA.post(stopLoading);
				}

				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {

					if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
					// if (imageViews.size() > 0) {
					// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + getResourceEntryName(arg1) + ":" + arg1);
					// imageViews.remove(arg1);
					// if (imageViews.size() == 0) {
					// handlerKARA.removeCallbacks(stopLoading);
					// handlerKARA.post(stopLoading);
					// }
					// } else {
					// handlerKARA.removeCallbacks(stopLoading);
					// handlerKARA.post(stopLoading);
					// }
				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {

					if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());
					// handlerKARA.removeCallbacks(stopLoading);
					// handlerKARA.post(stopLoading);
				}
			});
		}

		super.setHOME();

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]");
	}

	Runnable stopLoading = new Runnable() {

		@Override
		public void run() {

			stopLoading(getMethodName());
		}
	};

}
