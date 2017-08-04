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
 * project	:	Karaoke.KPOP
 * filename	:	BaseActivity4.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseActivity4.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.adpt.MenuListAdapter;
import kr.kymedia.karaoke.kpop._LoginFragment;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * 
 * TODO<br>
 * NOTE:<br>
 * 
 * <pre>
 * DrawerLayout 드로어메뉴
 * <a href="http://developer.android.com/design/patterns/navigation-drawer.html">Navigation Drawer</a>
 * <a href="http://developer.android.com/training/implementing-navigation/nav-drawer.html">Creating a Navigation Drawer
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 6. 18.
 * @version 1.0
 */
class BaseActivity4 extends BaseActivity3 implements OnItemClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	private DrawerLayout mDrawerLayout;

	protected void lockDrawerLayout() {
		if (mDrawerLayout != null) {
			// mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		}
	}

	protected void unlockDrawerLayout() {
		if (mDrawerLayout != null) {
			mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		}
	}

	protected boolean isUseDrawaerLaytout = true;

	/**
	 * <pre>
	 * android.support.v4.app.ActionBarDrawerToggle
	 * This class is deprecated.
	 * Please use ActionBarDrawerToggle in support-v7-appcompat.
	 * @see <a
	 *      href="http://stackoverflow.com/questions/26433306/navigation-drawer-menudrawer-android-5-lollipop-style">Navigation drawer (menudrawer) Android 5 (lollipop) style</a>
	 * @see <a
	 *      href="http://developer.android.com/reference/android/support/v4/app/ActionBarDrawerToggle.html">ActionBarDrawerToggle</a>
	 * </pre>
	 * 
	 * 
	 */
	protected void attachDrawerLayout() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		// mDrawerLayout, /* DrawerLayout object */
		// android.R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		// R.string.btn_title_open, /* "open drawer" description */
		// R.string.btn_title_close /* "close drawer" description */
		// )
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.string.btn_title_open, /* "open drawer" description */
		R.string.btn_title_close /* "close drawer" description */
				) {

					/** Called when a drawer has settled in a completely closed state. */
					@Override
					public void onDrawerClosed(View view) {
						// getSupportActionBar().setTitle(mTitle);
						// invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
					}

					/** Called when a drawer has settled in a completely open state. */
					@Override
					public void onDrawerOpened(View drawerView) {
						KP_menu(__CLASSNAME__, getMethodName());
					}
				};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		new Runnable() {
			@Override
			public void run() {
				if (mDrawerToggle != null) {
					mDrawerToggle.syncState();
				}
			}
		}.run();

		if (isUseDrawaerLaytout) {
			unlockDrawerLayout();
			mDrawerToggle.setDrawerIndicatorEnabled(true);
		} else {
			lockDrawerLayout();
			mDrawerToggle.setDrawerIndicatorEnabled(false);
		}

		if (isACTIONMAIN()) {
			mDrawerToggle.setDrawerIndicatorEnabled(true);
		} else {
			mDrawerToggle.setDrawerIndicatorEnabled(false);
		}
	}

	protected void detachDrawerLayout() {
		ViewGroup vg = (ViewGroup) findViewById(android.R.id.content).getRootView();
		if (vg != null && mDrawerLayout != null) {
			vg.removeView(mDrawerLayout);
		}
	}

	protected boolean isLockDrawerLayout() {
		boolean ret = false;

		if (mDrawerLayout != null) {
			ret = mDrawerLayout.getDrawerLockMode(GravityCompat.START) == DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
		}

		return ret;
	}

	protected boolean isUnLockDrawerLayout() {
		boolean ret = false;

		if (mDrawerLayout != null) {
			ret = mDrawerLayout.getDrawerLockMode(GravityCompat.START) == DrawerLayout.LOCK_MODE_UNLOCKED;
		}

		return ret;
	}

	private ActionBarDrawerToggle mDrawerToggle;

	private View mMenuHeader = null;
	private ListView mMenuList = null;
	private MenuListAdapter mMenuListAdapter = null;
	private View mMenuProgress = null;

	private boolean mMenuVisible = false;

	public boolean setMenuVisibility(boolean visible) {

		// return super.setMenuVisibility(visible);

		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName() + visible + mDrawerLayout);

		mMenuVisible = visible;

		if (mDrawerLayout != null /* && isUnLockDrawerLayout() */) {
			if (visible) {
				mDrawerLayout.openDrawer(GravityCompat.START);
			} else {
				mDrawerLayout.closeDrawers();
			}
		}

		return false;
	}

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
	 * 홈화면로고처리
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName());

		super.onCreate(savedInstanceState);

		attachDrawerLayout();

		try {
			/**
			 * 이게먼...개지랄이야...구글...이븅신들아...
			 */
			getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_home_64x64);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName() + getApp().KP_menu + ":" + mMenuListAdapter.getCount());
	}

	@Override
	public void setActionMenuVisible(Menu menu) {

		super.setActionMenuVisible(menu);
		if (!isACTIONMAIN()) {
			getSupportActionBar().setLogo(null);
			getSupportActionBar().setIcon(null);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		if (mDrawerToggle != null) {
			mDrawerToggle.syncState();
		}
	}

	/**
	 * 
	 * <pre>
	 * 오류보고1 
	 * 메뉴 진입 후 화면 상단의 홈버튼(메인화면 바로가기) 동작 없는 현상 발견 : 특정 메뉴에서만 발생하는 문제는 아니며 전 메뉴에서 발생함
	 * </pre>
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName() + item);
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		// if (mDrawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }
		// Handle your other action bar items...

		int id = item.getItemId();

		if (id == android.R.id.home || id == R.id.menu_home) {
			// //홈화면시에만 :
			if (isACTIONMAIN()) {
				setMenuVisibility(!mMenuVisible);
				return true;
			} else {
				// isRefresh = true;
				return super.onOptionsItemSelected(item);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private void KP_menu_move(final int index) {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName() + index);

		if (index < 0) {
			return;
		}

		final KPItem info = getApp().KP_menu.getInfo();
		final KPItem list = getApp().KP_menu.getList(index);

		if (list == null) {
			return;
		}

		setMenuVisibility(false);

		post(new Runnable() {

			@Override
			public void run() {

				if (getApp().KP_menu.getLists() != null && index < getApp().KP_menu.getListCount()) {

					String menu_id = list.getValue("menu_id");
					String menu_name = list.getValue("menu_name");

					if (!TextUtil.isEmpty(menu_id)) {

						boolean menu_refresh = false;

						// 로그인/로그아웃시 메뉴클리어
						if (("menu_logout").equalsIgnoreCase(menu_id.toLowerCase().trim())) {
							if (getApp().isLoginUser()) {
								if (isACTIONMAIN()) {
									menu_refresh = true;
								} else {
									getApp().isRefresh = true;
								}
							}
						}

						if (menu_id.contains("menu_")) {
							onOptionsItemSelected(getResource(menu_id, "id"), menu_name, true);
						} else if (menu_id.contains("context_")) {
							onContextItemSelected(getApp(), getResource(menu_id, "id"), info, list, true);
						}

						if (menu_refresh) {
							// KP_menu_refresh(__CLASSNAME__, getMethodName());
							refresh();
						}

					}
				}
			}
		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (mDrawerToggle != null) {
			mDrawerToggle.onConfigurationChanged(newConfig);
		}
	}

	public void KP_menu_init() {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName());

		try {
			getApp().KP_menu = KP_init(getApp(), getApp().KP_menu);

			getApp().KP_menu.setOnKPnnnnListner(mKPmenuListener);

			mMenuList = (ListView) findViewById(R.id.include_menu).findViewById(R.id.menu_list);
			if (mMenuList == null) {
				return;
			}

			mMenuProgress = findViewById(R.id.include_menu).findViewById(R.id.menu_progress);

			mMenuList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

			// if (mMenuHeader == null) {
			// mMenuHeader = getLayoutInflater().inflate(R.layout.item_user, null, false);
			// }
			// if (mMenuList.getHeaderViewsCount() == 0) {
			// mMenuList.addHeaderView(mMenuHeader);
			// }
			mMenuHeader = findViewById(R.id.menu_head);

			mMenuList.setHeaderDividersEnabled(false);

			WidgetUtils.setOnClickListener(getApp(), (ViewGroup) mMenuHeader, new OnClickListener() {

				@Override
				public void onClick(View v) {

					String nm = getResourceEntryName(v.getId());
					if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + nm);

					if (v.getId() == R.id.refresh) {
						KP_menu_refresh(__CLASSNAME__, getMethodName());
					} else {
						if (getCurrentFragment() != null) {
							// getCurrentFragment().showProfileEdit(true);
							getCurrentFragment().onOptionsItemSelected(R.id.menu_profile_edit, getString(R.string.menu_holichome), true);
						}
					}
				}
			}, true);

			if (mMenuList != null && getApp().KP_menu != null) {
				if (mMenuListAdapter == null) {
					mMenuListAdapter = new MenuListAdapter(getApp(), R.layout.item_menu, getApp().KP_menu.getLists(), new View.OnClickListener() {

						@Override
						public void onClick(View view) {

							onListItemClick(view);
						}
					}, null);
				}

				mMenuListAdapter.setNotifyOnChange(false);

				mMenuList.setAdapter(mMenuListAdapter);
				mMenuList.setDivider(null);

				mMenuList.setOnItemClickListener(this);

				mMenuListAdapter.notifyDataSetChanged();
			}

			// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, getMethodName() + isRefresh + ":" + getApp().KP_menu.getListCount() + ":" + mMenuListAdapter.getCount());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void KP_init() {

		super.KP_init();

		KP_menu_init();
	}

	@Override
	public void onListItemClick(View v) {

		super.onListItemClick(v);

		if (v.getId() == R.id.refresh) {
			KP_menu_refresh(__CLASSNAME__, getMethodName());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		String nm = getResourceEntryName(v.getId());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + nm + "," + position + "," + id);

		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawers();
		}

		final int index = position - mMenuList.getHeaderViewsCount();

		if (index < 0) {
		} else {
			KP_menu_move(index);
		}

	}

	public void setRefreshComplete() {
		try {
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	KPnnnnListener mKPmenuListener = new KPnnnnListener() {

		@Override
		public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {


		}

		@Override
		public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message, KPItem r_info) {

			KP_menu_update();

		}

		@Override
		public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {

			if (mMenuProgress != null) {
				mMenuProgress.setVisibility(View.VISIBLE);
			}
			KP_menu_clear(__CLASSNAME__, getMethodName());
		}

		@Override
		public void onKPnnnnProgress(long size, long total) {


		}

		@Override
		@Deprecated
		public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			if (mMenuProgress != null) {
				mMenuProgress.setVisibility(View.GONE);
			}
		}

		@Override
		public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {

			// KP_menu_update();
			KP_menu_clear(__CLASSNAME__, getMethodName());

		}

		@Override
		public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {

			// KP_menu_update();
			KP_menu_clear(__CLASSNAME__, getMethodName());

		}

		@Override
		@Deprecated
		public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {

			// KP_menu_update();
			KP_menu_clear(__CLASSNAME__, getMethodName());

		}
	};

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		// KP_menu(__CLASSNAME__, getMethodName());
	}

	/**
	 * <pre>
	 * KP_0004 옵션메뉴내용조회
	 * 어플옵션메뉴기능대체
	 * </pre>
	 */
	private void KP_menu(String name, String method) {
		if (!isUseDrawaerLaytout) {
			return;
		}

		// 드로어메뉴락시/로그인화면시제외
		if (getCurrentFragment() instanceof _LoginFragment) {
			// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu()" + "EXIT:LOGIN");
			return;
		}

		// if (isLockDrawerLayout()) {
		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu()" + "EXIT:isLockDrawerLayout()" + isLockDrawerLayout());
		// return;
		// }

		if (getApp().KP_menu == null) {
			return;
		}

		getApp().KP_menu.setOnKPnnnnListner(mKPmenuListener);

		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName() + getApp().KP_menu.getListCount() + ":" + mMenuListAdapter.getCount());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getApp().p_mid);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getApp().p_nickname);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + getApp().p_email);

		if (/* mMenuListAdapter.getCount() == 0 && */getApp().KP_menu.getListCount() == 0) {
			KP_menu_kp0004();
		} else {
			KP_menu_update();
		}

	}

	private void KP_menu_kp0004() {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName());

		if (!isUseDrawaerLaytout) {
			return;
		}

		// 드로어메뉴락시/로그인화면시제외
		if (getCurrentFragment() instanceof _LoginFragment) {
			// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_kp0004()" + "EXIT:LOGIN");
			return;
		}

		// if (isLockDrawerLayout()) {
		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_kp0004()" + "EXIT:isLockDrawerLayout()" + isLockDrawerLayout());
		// return;
		// }

		if (getApp().KP_menu == null) {
			KP_menu_init();
		}

		if (getApp().KP_menu == null) {
			return;
		}

		if (getApp().KP_menu.isQuerying()) {
			return;
		}

		// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_kp0004()" + getApp().KP_menu.getListCount() + ":" + getApp().p_mid);

		getApp().KP_menu.setOnKPnnnnListner(mKPmenuListener);

		String mid = getApp().p_mid;
		String m1 = getString(R.string.M1_MAIN);
		String m2 = getString(R.string.M2_MENU);

		if (getCurrentFragment() != null) {
			m1 = getCurrentFragment().p_m1;
			m2 = getCurrentFragment().p_m2;
		}

		getApp().KP_menu.KP_0004(mid, m1, m2);
	}

	protected void KP_menu_update() {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName());

		if (!isUseDrawaerLaytout) {
			return;
		}

		// 드로어메뉴락시/로그인화면시제외
		if (getCurrentFragment() instanceof _LoginFragment) {
			// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_update()" + "EXIT:LOGIN");
			return;
		}

		// if (isLockDrawerLayout()) {
		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_update()" + "EXIT:isLockDrawerLayout()" + isLockDrawerLayout());
		// return;
		// }

		if (getApp().KP_menu == null) {
			KP_menu_init();
		}

		if (getApp().KP_menu == null) {
			return;
		}

		// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_update()" + getApp().KP_menu.getListCount() + ":" + getApp().p_mid);

		try {

			getApp().KP_menu.setOnKPnnnnListner(mKPmenuListener);

			if (mMenuListAdapter != null) {
				mMenuListAdapter.notifyDataSetChanged();
			}

			if (mMenuProgress != null) {
				mMenuProgress.setVisibility(View.GONE);
			}

			if (mMenuHeader == null) {
				return;
			}

			KPItem info = getApp().KP_menu.getInfo();


			String name = getApp().p_nickname;
			String email = getApp().p_email;
			String url_profile = "";
			String ncode = "";

			if (info != null) {
				// 프로필이미지
				url_profile = info.getValue("url_profile");
				// 국기이미지
				ncode = info.getValue("ncode");

			}

			// 프로필이미지
			if (URLUtil.isNetworkUrl(url_profile)) {
				// ((ImageView) mMenuHeader.findViewById(R.id.image)).setTag(url_profile);
				putURLImage(getApp(), ((_ImageView) mMenuHeader.findViewById(R.id.image)), url_profile, false, 0);
				if (mMenuHeader.findViewById(R.id.image) != null) {
					mMenuHeader.findViewById(R.id.image).setVisibility(View.VISIBLE);
				}
			}

			// 국기이미지
			if (!TextUtil.isEmpty(ncode)) {
				try {
					((_ImageView) mMenuHeader.findViewById(R.id.flag)).setImageDrawable(WidgetUtils.getDrawable(getApp(), "img_flag_" + ncode.toLowerCase()));
				} catch (Exception e) {

					// e.printStackTrace();
				}
			} else {
				if (mMenuHeader.findViewById(R.id.flag) != null) {
					mMenuHeader.findViewById(R.id.flag).setVisibility(View.GONE);
				}
			}

			if (mMenuHeader.findViewById(R.id.name) != null) {
				((TextView) mMenuHeader.findViewById(R.id.name)).setText(name);
			}

			if (mMenuHeader.findViewById(R.id.email) != null) {
				((TextView) mMenuHeader.findViewById(R.id.email)).setText(email);
			}

			if (mMenuHeader.findViewById(R.id.info) != null) {
				if (getApp().isLoginUser()) {
					mMenuHeader.findViewById(R.id.info).setVisibility(View.VISIBLE);
				} else {
					mMenuHeader.findViewById(R.id.info).setVisibility(View.GONE);
				}
				mMenuHeader.findViewById(R.id.info).setVisibility(View.VISIBLE);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void KP_menu_clear(String name, String method) {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName());

		if (!isUseDrawaerLaytout) {
			return;
		}

		// 드로어메뉴락시/로그인화면시제외
		if (getCurrentFragment() instanceof _LoginFragment) {
			// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_clear()" + "EXIT:LOGIN");
			return;
		}

		// if (isLockDrawerLayout()) {
		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_clear()" + "EXIT:isLockDrawerLayout()" + isLockDrawerLayout());
		// return;
		// }

		if (getApp().KP_menu == null) {
			return;
		}

		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_clear()" + name + "::" + method + ":" + isRefresh + ":" + getApp().KP_menu.getListCount() + ":" + mMenuListAdapter.getCount());

		if (getApp().KP_menu == null && getApp().KP_menu.getLists() != null) {
			getApp().KP_menu.getLists().clear();
			if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getApp().p_mid + ":" + getApp().KP_menu.getListCount() + ":" + mMenuListAdapter.getCount());
		}

		if (mMenuListAdapter != null) {
			mMenuListAdapter.clear();
			mMenuListAdapter.notifyDataSetChanged();
		}
	}

	protected void KP_menu_refresh(String name, String method) {
		if (_IKaraoke.DEBUG) Log2.e("KP_menu:" + __CLASSNAME__, getMethodName());

		if (!isUseDrawaerLaytout) {
			return;
		}

		// 드로어메뉴락시/로그인화면시제외
		if (getCurrentFragment() instanceof _LoginFragment) {
			// if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_refresh()" + "EXIT:LOGIN");
			return;
		}

		// if (isLockDrawerLayout()) {
		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_refresh()" + "EXIT:isLockDrawerLayout()" + isLockDrawerLayout());
		// return;
		// }

		if (getApp().KP_menu == null) {
			return;
		}

		// //if (_IKaraoke.DEBUG)Log.e("KP_menu:" + __CLASSNAME__, "KP_menu_refresh()" + name + "::" + method + ":" + isRefresh + ":" + getApp().KP_menu.getListCount() + ":" + mMenuListAdapter.getCount());

		KP_menu_clear(name, method);

		KP_menu(name, method);
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 * @see BaseActivity2#onKPnnnnResult(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, String r_info) {

		super.onKPnnnnResult(what, opcode, r_code, r_message, r_info);
	}

	@Override
	protected void start() {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getApp().isRefresh);

		super.start();

		KP_menu(__CLASSNAME__, "start()");
	}

	@Override
	protected void refresh() {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getApp().isRefresh);

		super.refresh();

		KP_menu_refresh(__CLASSNAME__, getMethodName());
	}

	@Override
	protected void onResume() {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getApp().isRefresh);


		super.onResume();

		if (getApp().KP_menu != null) {
			getApp().KP_menu.setOnKPnnnnListner(mKPmenuListener);
		}

	}

	@Override
	public void onBackPressed() {

		// if (isACTIONMAIN() && !isExit()) {
		// setMenuVisibility(true);
		// }
		super.onBackPressed();
	}

}
