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
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP
 * filename	:	BaseActivity2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseActivity2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.ArrayList;

import is.yuun.yanzm.products.quickaction.lib.ActionItem2;
import is.yuun.yanzm.products.quickaction.lib.QuickAction3;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

//import com.igaworks.AdPOPcornSDKExample.AdPOPcornAnimationFactory;
//import yanzm.products.quickaction.lib.ActionItem;
//import yanzm.products.quickaction.lib.QuickAction2;

/**
 * 
 * NOTE:드로어메뉴로대체사용안함.<br>
 * 커스텀옵션메뉴처리<br>
 * 프로그래스처리<br>
 * 
 * @author isyoon
 * @since 2012. 11. 27.
 * @version 1.0
 */
@Deprecated
class _xBaseActivity4 extends BaseActivity3 implements OnLongClickListener, OnTouchListener {

	View mMenuView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mMenuView = findViewById(R.id.include_menu);

		// 시스템서비스로부터 진동을 울릴 수 있는 Vibrator 객체를 얻는다.
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		WidgetUtils.setOnLongClickListener(getApp().getApplicationContext(),
				(ViewGroup) findViewById(R.id.include_menu), this, true);
	}

	boolean mMenuVisible = false;

	/**
	 * 퀵액션메뉴<br>
	 * <br>
	 * 
	 * @see #onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	QuickAction3 mQuickActionMoreMenu = null;
	/**
	 * 퀵액션아이템<br>
	 * <br>
	 */
	ArrayList<ActionItem2> mActionItems = new ArrayList<ActionItem2>();

	protected boolean showOptionMenuMore(View v) {
		if (v == null) {
			return false;
		}
		// ContextMenu를 onCreateContextMenu에서 QuickActionMenu로 변환후
		boolean ret = v.showContextMenu();
		// QuickActionMenu를 보인다.
		showQuickActionOptionMenuMore(v);
		return ret;
	}

	protected void showQuickActionOptionMenuMore(View v) {

		if (v == null) {
			return;
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + v.toString());

		int orientation = getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mQuickActionMoreMenu = new QuickAction3(v, R.layout.popup_horizontal,
					QuickAction3.STYLE_BUTTON, R.layout.quickaction_item_button);
		} else {
			mQuickActionMoreMenu = new QuickAction3(v, R.layout.popup_vertical2, QuickAction3.STYLE_LIST,
					R.layout.quickaction_item_list);
		}

		// mQuickActionMoreMenu.setOnLongClickListener(this);
		for (final ActionItem2 action : mActionItems) {
			action.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					onOptionsItemSelected(action.getItemId(), "", true);
					if (mQuickActionMoreMenu != null) {
						mQuickActionMoreMenu.dismiss();
					}
				}
			});
			mQuickActionMoreMenu.addActionItem(action);
		}

		mQuickActionMoreMenu.show();
	}

	Vibrator mVibrator = null;

	@Override
	public boolean onLongClick(View v) {

		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG)
			Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + cn);
		String description = v.getContentDescription().toString();
		if (!TextUtil.isEmpty(description)) {
			// 지정된 수치값만큼 진동을 발생시킨다.
			mVibrator.vibrate(50);
			getApp().popupToast(v.getContentDescription(), Toast.LENGTH_SHORT);
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		return false;
	}

	@Deprecated
	public void setOptionMenuMoreVisible(Menu menu) {
		// 새로고침
		setOptionMenuMoreItemVisible(menu, R.id.menu_refresh, true);
		// 공지사항
		setOptionMenuMoreItemVisible(menu, R.id.menu_notice, true);
		// menu_login 로그인
		setOptionMenuMoreItemVisible(menu, R.id.menu_login, !getApp().isLoginUser());
		// menu_logout 로그아웃
		setOptionMenuMoreItemVisible(menu, R.id.menu_logout, getApp().isLoginUser());
		// menu_myholic MY HOLIC
		// setOptionMenuMoreItemVisible(menu, R.id.menu_myholic, BaseFragment.isLogin());
		setOptionMenuMoreItemVisible(menu, R.id.menu_myholic, getApp().isLoginUser());
		// menu_setting 설정
		setOptionMenuMoreItemVisible(menu, R.id.menu_setting, getApp().isLoginUser());
		// menu_setting 설정
		setOptionMenuMoreItemVisible(menu, R.id.menu_setting_notification, false);
		// menu_reserve 프로필수정
		setOptionMenuMoreItemVisible(menu, R.id.menu_profile, false);
		// menu_ticket TICKET
		// setOptionMenuMoreItemVisible(menu, R.id.menu_ticket,
		setOptionMenuMoreItemVisible(menu, R.id.menu_ticket, false);
		setOptionMenuMoreItemVisible(menu, R.id.menu_shop, false);
		// 검색
		setOptionMenuMoreItemVisible(menu, R.id.menu_search, false);
		// 멘트작성
		// setOptionMenuMoreItemVisible(menu, R.id.menu_comment, false);
		// menu_feel FEEL
		setOptionMenuMoreItemVisible(menu, R.id.menu_feel, false);
		// menu_audition AUDITION
		setOptionMenuMoreItemVisible(menu, R.id.menu_audition, false);
		// menu_audition AUDITION
		setOptionMenuMoreItemVisible(menu, R.id.menu_audition_open, getApp().isLoginUser());
		if (_IKaraoke.APP_PACKAGE_NAME_ONSPOT.equalsIgnoreCase(getPackageName())) {
			setOptionMenuMoreItemVisible(menu, R.id.menu_audition_open, false);
		}
		// menu_sing SING
		setOptionMenuMoreItemVisible(menu, R.id.menu_sing, false);
		// menu_listen LISTEN
		setOptionMenuMoreItemVisible(menu, R.id.menu_listen, false);
		// 멘트작성
		setOptionMenuMoreItemVisible(menu, R.id.menu_comment, false);
		// menu_feel FEEL
		setOptionMenuMoreItemVisible(menu, R.id.menu_feel, false);
		// 도움말
		setOptionMenuMoreItemVisible(menu, R.id.menu_help, false);
		// menu_reserve 예약곡 목록
		setOptionMenuMoreItemVisible(menu, R.id.menu_reserve, false);
		// menu_mysing 나의 애창곡
		setOptionMenuMoreItemVisible(menu, R.id.menu_mysing, false);
		// menu_record 나의 녹음곡
		setOptionMenuMoreItemVisible(menu, R.id.menu_record, false);
		// menu_iap_msg_buying 구매곡
		setOptionMenuMoreItemVisible(menu, R.id.menu_iap_msg_buying, false);
		// menu_add 추가
		setOptionMenuMoreItemVisible(menu, R.id.menu_add, false);

	}

	public void setOptionMenuMoreItemVisible(Menu menu, int id, boolean visible) {
		MenuItem item = menu.findItem(id);
		if (item != null) {
			item.setVisible(visible);
			item.setVisible(visible);
		}
		// View v = findViewById(id);
		// if (v != null) {
		// if (visible) {
		// v.setVisibility(View.VISIBLE);
		// } else {
		// v.setVisibility(View.GONE);
		// }
		// }
	}

	/**
	 * 상속함수마지막에호출해라!!!
	 * 
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu, android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		String vn = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn);
		int id = v.getId();
		if (id == R.id.menu_more) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu_more, menu);
			setOptionMenuMoreVisible(menu);
			// QuickActionMenu생성후
			if (makeQuickActionOptionMenuMore(menu) > 0) {
				// ContextMenu메뉴클리어
				menu.clear();
			}
		} else {
			super.onCreateContextMenu(menu, v, menuInfo);
		}
	}

	protected int makeQuickActionOptionMenuMore(Menu menu) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + menu.toString());

		// WindowManager wm = (WindowManager) getBaseActivity().getSystemService(Context.WINDOW_SERVICE);
		// Display display = wm.getDefaultDisplay();
		// int rotation = display.getRotation();

		int ret = 0;
		mActionItems.clear();
		MenuItem item = null;
		for (int i = 0; i < menu.size(); i++) {
			item = menu.getItem(i);
			if (item.isVisible() && item.isEnabled()) {
				onCreateQuickActionMenuMore(item);
				ret++;
			}
		}

		return ret;
	}

	protected void onCreateQuickActionMenuMore(final MenuItem item) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + item.toString());

		String title = item.getTitle().toString();
		Drawable icon = item.getIcon();
		final ActionItem2 action = new ActionItem2();

		action.setIcon(icon);
		action.setTitle(title);
		action.setItemId(item.getItemId());

		mActionItems.add(action);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		super.onPostCreate(savedInstanceState);

		// menu_more등록
		if (findViewById(R.id.menu_more) != null) {
			registerForContextMenu(findViewById(R.id.menu_more));
		}

	}

	/**
	 * <pre>
	 * 메뉴 보이기/가리기
	 * 로딩시작
	 * </pre>
	 */
	@Override
	protected void onPause() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		super.onPause();

		post(new Runnable() {

			@Override
			public void run() {

				setMenuVisibility(false);

			}
		});
	}

	/**
	 * <pre>
	 * TODO
	 * 로딩종료
	 * </pre>
	 * 
	 * @see BaseActivity2#onResume()
	 */
	@Override
	protected void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		super.onResume();

		stopLoadingDialog(null);
	}

	@Override
	public void onBackPressed() {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		try {
			if (isMenuShowing()) {
				setMenuVisibility(false);
			} else {
				super.onBackPressed();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "keyCode:" + keyCode + ", event:" + event);

		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			// 모어메뉴보이면
			setMenuVisibility(!mMenuVisible);
			break;

		default:
			break;
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @see BaseActivity2#onMenuClick(android.view.View)
	 */
	@Override
	public void onMenuClick(View v) {

		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);
		super.onMenuClick(v);

		_BaseFragment fragment = getCurrentFragment();

		if (fragment == null) {
			return;
		}

		int id = v.getId();
		if (id == R.id.menu_more) {
			showOptionMenuMore(v);
		} else {
			startLoadingDialog(null);
			onOptionsItemSelected(v.getId(), "", true);
		}
	}

	public boolean isMenuShowing() {
		if (mMenuView == null) {
			return false;
		}

		int visibility = mMenuView.getVisibility();

		if (visibility == View.VISIBLE) {
			return true;
		} else {
			return false;
		}
	}

	AnimationListener mMenuAnal = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());


		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());


		}

		@Override
		public void onAnimationEnd(Animation arganimation) {
			if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

		}
	};

	public boolean startAnimationShowHide(View view, boolean visible) {
		AnimationSet set = new AnimationSet(true);
		Animation ani = null;
		if (visible) {
			// ani = AdPOPcornAnimationFactory.CreateBouncingAnimation(0, 0, 200, 0);
			ani = new TranslateAnimation(0, 0, 200, 0);
			view.setVisibility(View.VISIBLE);
		} else {
			// ani = AdPOPcornAnimationFactory.CreateBouncingAnimation(0, 0, 0, 200);
			ani = new TranslateAnimation(0, 0, 0, 200);
			view.setVisibility(View.GONE);
		}
		ani.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
		ani.setAnimationListener(mMenuAnal);
		set.addAnimation(ani);
		view.setAnimation(set);
		return visible;
	}

	/**
	 * 메뉴 보이기/가리기
	 */
	public boolean setMenuVisibility(boolean visible) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + visible);


		if (mMenuView != null) {
			startAnimationShowHide(mMenuView, visible);
		} else {
			visible = false;
		}

		mMenuVisible = visible;

		if (mQuickActionMoreMenu != null) {
			mQuickActionMoreMenu.dismiss();
		}

		return visible;
	}

}
