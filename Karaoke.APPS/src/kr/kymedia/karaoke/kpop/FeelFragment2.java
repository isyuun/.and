/*is
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
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	Karaoke.KPOP
 * filename	:	ListenFragment2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ ListenFragment2.java
 * </pre>
 */

package kr.kymedia.karaoke.kpop;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.URLUtil;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Timer;
import java.util.TimerTask;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.app.html.HtmlBubble;
import kr.kymedia.karaoke.apps.BuildConfig;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.Util;
import kr.kymedia.karaoke.widget.util.ImageDownLoader3;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * <pre>
 * 필상세UX전용
 * 진짜~~~대단하다.인식!!!
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2012. 7. 3.
 */
public class FeelFragment2 extends FeelFragment implements OnTouchListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected Timer mTickFeelLayout = null;

	public View getListContainer() {
		View ret = findViewById(R.id.include_list);
		return ret;
	}

	View mListBack;

	/**
	 * FEEL리스트 상단/하단 이동시 topMargin값
	 */
	int mTopMarginFeelLayout = 0;
	/**
	 * FEEL리스트 상단/하단 이동시 SWPIPE이동값
	 */
	float mMovMarginFeelLayout = 0;

	final float LOWER_MARGIN = 45.0f;
	final float UPPER_MARGIN_RATIO = 3.0f / 5.0f;

	/**
	 * FEEL리스트 SWIPE 가능확인
	 */
	boolean isSwipeEnabled = false;
	/**
	 * FEEL리스트 SWIPE 시작확인
	 */
	boolean isSwipeStarted = false;

	float mDownX = 0;
	float mDownY = 0;
	float mPosX = 0;
	float mPosY = 0;

	private enum SWIPE {
		NONE(0),
		UP(1),
		DOWN(2),
		LEFT(3),
		RIGHT(4);

		private final int value;

		SWIPE(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}

		public static SWIPE get(int value) {
			SWIPE ret = null;
			for (SWIPE state : SWIPE.values()) {
				if (value == state.value()) {
					ret = state;
				}
			}
			return ret;
		}
	}

	private SWIPE mSwipe = SWIPE.NONE;

	@Override
	public void onClick(View v) {
		if (BuildConfig.DEBUG) Log.d(__CLASSNAME__, getMethodName() + isSwipeEnabled + ":" + isSwipeStarted + ":" + getResourceEntryName(v.getId()));
		super.onClick(v);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (BuildConfig.DEBUG) Log.d(__CLASSNAME__, getMethodName() + isSwipeEnabled + ":" + isSwipeStarted + ":" + getResourceEntryName(v.getId()) + ":" + event);

		if (!isSwipeEnabled) {
			return false;
		}

		// SWIPE지점판단
		isSwipeStarted = true;

		//// 최초터치지점확인
		//switch (event.getAction()) {
		//	case MotionEvent.ACTION_DOWN:
		//		mDownX = event.getX();
		//		mDownY = event.getY();
		//		break;
		//	default:
		//		break;
		//}

		return isSwipeStarted;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		boolean ret = false;

		swipeTouch(event);

		if (isSwipeStarted && isSwipeEnabled) {
			// ACTION_MOVE만차단
			switch (event.getAction()) {
				case MotionEvent.ACTION_MOVE:
					ret = true;
			}
		}

		return ret;
	}

	/**
	 * 사용자 swipe시 레이어이동
	 */
	private void swipeTouch(MotionEvent event) {
		//if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + isSwipeEnabled + ":" + isSwipeStarted + ":" + event);

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				// SWIPE UP/DOWN확인
				if (Math.abs(Math.abs(event.getY() - mDownY) - Math.abs(event.getX() - mDownX)) > 0.0f
				/** && Math.abs(event.getY() - mDownY) > 100.0f */) {
					float incY = event.getY() - mPosY;
					swipeTouch((int) incY);
					if (incY > 0) {
						mSwipe = SWIPE.DOWN;
					} else if (incY < 0) {
						mSwipe = SWIPE.UP;
					}
				} else {
					//mSwipe = SWIPE.NONE;
				}
				break;
			case MotionEvent.ACTION_UP:
				switch (mSwipe) {
					case UP:
						bounceTouch(true);
						break;
					case DOWN:
						bounceTouch(false);
						break;
					default:
						break;
				}
				mSwipe = SWIPE.NONE;
				break;
			default:
				break;
		}

		/**
		 * 터치가시작하기전까진
		 */
		if (!isSwipeStarted) {
			mDownX = event.getX();
			mDownY = event.getY();
		}

		mPosX = event.getX();
		mPosY = event.getY();
	}

	/**
	 * 사용자 swipe시 레이어이동
	 */
	private void swipeTouch(int inc) {
		if (!isSwipeEnabled) {
			return;
		}

		if (!isSwipeStarted) {
			return;
		}

		//if (BuildConfig.DEBUG) Log.e(__CLASSNAME__, getMethodName() + inc + ":" + mTopMarginFeelLayout);

		final int MIN_MARGIN = (int) Util.dp2px(getApp(), LOWER_MARGIN);
		float h = getView().getHeight();
		final int MAX_MARGIN = (int) (h * UPPER_MARGIN_RATIO);

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getListContainer().getLayoutParams();

		int margin = params.topMargin + inc;

		if (margin > MIN_MARGIN && margin < MAX_MARGIN) {
			mTopMarginFeelLayout = params.topMargin = margin;
			getListContainer().setLayoutParams(params);
			moveFeelLayoutBackgound();
		} else {
			stopFeelLayout();
		}

		//if (BuildConfig.DEBUG) Log.e(__CLASSNAME__, getMethodName() + inc + ":" + mTopMarginFeelLayout);
	}

	/**
	 * 사용자 SWIPE 종료시 상단/하단으로 이동 거의 건들지 않느게 정신건강에 좋다~~~
	 */
	private void bounceTouch(boolean upper) {
		if (!isSwipeEnabled) {
			return;
		}

		if (!isSwipeStarted) {
			return;
		}

		if (BuildConfig.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "upper:" + upper + "," + mTopMarginFeelLayout);

		if (getListContainer() != null) {
			// 현재위치
			int pos = mTopMarginFeelLayout;

			// 리스트이동처리!
			// 먼저이동하고 역으로 애니메이션한다. 왜냐고?왤까~~~
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getListContainer().getLayoutParams();
			if (upper) {
				findViewById(R.id.feel_text).setOnTouchListener(null);
				mTopMarginFeelLayout = (int) Util.dp2px(getApp(), LOWER_MARGIN);
				params.topMargin = mTopMarginFeelLayout;
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
			} else {
				findViewById(R.id.feel_text).setOnTouchListener(this);
				float height = getView().getHeight();
				mTopMarginFeelLayout = (int) (height * UPPER_MARGIN_RATIO);
				params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
			}
			params.addRule(RelativeLayout.ABOVE, R.id.include_reply);
			params.addRule(RelativeLayout.BELOW, 0);
			params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
			getListContainer().setLayoutParams(params);

			// 애니메이션처리!
			float fromYValue = 0.0f;
			float toYValue = 0.0f;

			AnimationSet set = new AnimationSet(true);
			if (upper) {
				mMovMarginFeelLayout = fromYValue = pos - mTopMarginFeelLayout;
				toYValue = 0.0f;
			} else {
				fromYValue = 0.0f;
				mMovMarginFeelLayout = toYValue = mTopMarginFeelLayout - pos;
			}

			TranslateAnimation ani = new TranslateAnimation(0.0f, 0.0f, fromYValue, toYValue);
			ani.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
			ani.setAnimationListener(anl);
			set.addAnimation(ani);

			// FEEL리스트본체이동
			ani.setZAdjustment(Animation.ZORDER_TOP);
			getListContainer().startAnimation(set);

			// FEEL리스트배경이동
			ani.setZAdjustment(Animation.ZORDER_BOTTOM);
			if (_IKaraoke.IS_ABOVE_JELLY_BEAN) {
				mListBack.startAnimation(set);
			} else {
				moveFeelLayoutBackgound();
				mListBack.startAnimation(set);
			}

		}

		enableFeelLayoutScroll(upper);
	}

	/**
	 * <pre>
	 * 클릭처리시작
	 * 터치리스너를제외하고클릭리스너를등록한다.
	 * </pre>
	 */
	private void startFeelLayoutClick() {
		if (findViewById(R.id.include_feel_title) != null) {
			// 터치제외
			if (findViewById(R.id.include_feel_title).findViewById(R.id.image) != null) {
				findViewById(R.id.include_feel_title).findViewById(R.id.image).setOnTouchListener(null);
			}
			if (findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_del) != null) {
				findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_del).setOnTouchListener(null);
			}
			//if (findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_edit) != null) {
			//	findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_edit).setOnTouchListener(null);
			//}
			// 클릭등록
			if (findViewById(R.id.include_feel_title).findViewById(R.id.image) != null) {
				findViewById(R.id.include_feel_title).findViewById(R.id.image).setOnClickListener(this);
			}
			if (findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_del) != null) {
				findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_del).setOnClickListener(this);
			}
			//if (findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_edit) != null) {
			//	findViewById(R.id.include_feel_title).findViewById(R.id.btn_feel_edit).setOnClickListener(this);
			//}
		}
		if (findViewById(R.id.include_feel_html) != null) {
			WidgetUtils.setOnClickListener(mContext, (ViewGroup) findViewById(R.id.include_feel_html), this, true);
		}
		// 터치제외
		WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_content), null);
		// 클릭등록
		WidgetUtils.setOnClickListener(mContext, (ViewGroup) findViewById(R.id.include_feel_content), this, true);
	}

	/**
	 * 상/하단 이동시 스크롤방지 디스패치에서 차단한다. </pre>
	 */
	protected void enableFeelLayoutScroll(boolean upper) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "enable:" + upper + "," + mTopMarginFeelLayout);

		if (upper) {
			// 상단시 스크롤 가능
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_title), this);
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_text), null);
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_html), null);
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_content), null);
			startFeelLayoutClick();
		} else {
			// 하단시 스크롤 불가
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_title), this);
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_text), this);
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_html), this);
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_content), this);
		}
		findViewById(R.id.include_feel_touch).setOnTouchListener(this);
	}

	/**
	 * 초기조회시 상단/하단으로 이동
	 */
	private void pullFeelLayout(int id, boolean upper, boolean bounce) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + id + ", upper:" + upper + ", bounce:" + bounce);

		if (getListContainer() != null) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getListContainer().getLayoutParams();

			if (upper) {
				mTopMarginFeelLayout = (int) Util.dp2px(getApp(), LOWER_MARGIN);
				params.addRule(RelativeLayout.ABOVE, R.id.include_reply);
				// params.addRule(RelativeLayout.BELOW, 0);
				findViewById(R.id.feel_text).setOnTouchListener(null);
			} else {
				float h = getView().getHeight();
				mTopMarginFeelLayout = (int) (h * UPPER_MARGIN_RATIO);
				params.addRule(RelativeLayout.ABOVE, R.id.include_reply);
				// params.addRule(RelativeLayout.BELOW, 0);
				findViewById(R.id.feel_text).setOnTouchListener(this);
			}

			moveFeelLayoutBackgound();

			if (bounce) {
				params.topMargin = mTopMarginFeelLayout - 80;
				getListContainer().setLayoutParams(params);
			}

			getListContainer().setVisibility(View.VISIBLE);
			mListBack.setVisibility(View.VISIBLE);

			TranslateAnimation ani = null;
			try {
				ani = (TranslateAnimation) AnimationUtils.loadAnimation(getBaseActivity(), id);
			} catch (Exception e) {

				e.printStackTrace();
				return;
			}

			// ani.setDuration(200);
			ani.setAnimationListener(anl);

			// FEEL리스트본체이동
			getListContainer().startAnimation(ani);

			// FEEL리스트배경이동
			if (_IKaraoke.IS_ABOVE_HONEYCOMB) {
				if (!upper) {
					mListBack.startAnimation(ani);
				}
			} else {
				mListBack.startAnimation(ani);
			}

		}

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + mTopMarginFeelLayout);
	}

	/**
	 * 애니메이션처리후원래위치로
	 */
	AnimationListener anl = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "onAnimationStart()" + mTopMarginFeelLayout);
			isSwipeStarted = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "onAnimationRepeat()" + mTopMarginFeelLayout);
			isSwipeStarted = true;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "onAnimationEnd()" + mTopMarginFeelLayout);

			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getListContainer().getLayoutParams();
			params.topMargin = mTopMarginFeelLayout;
			getListContainer().setLayoutParams(params);

			isSwipeStarted = false;

			moveFeelLayoutBackgound();

		}
	};

	@Override
	public void setListAdapter() {

		super.setListAdapter();

		// 당겨고침제외
		getSwipeRefreshLayout().setEnabled(false);

		WidgetUtils.setOnTouchListener(mContext, (ViewGroup) getListContainer(), new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (BuildConfig.DEBUG) Log.i(__CLASSNAME__, getMethodName() + isSwipeEnabled + ":" + isSwipeStarted + ":" + v);
				return false;
			}
		});

		ListView list = (ListView) findViewById(R.id.list);
		if (list != null) {
			list.setBackgroundColor(getResources().getColor(R.color.solid_white));
		}

		startFeelLayout();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		stopFeelLayout();
	}

	private void startFeelLayout() {
		getListContainer().setVisibility(View.GONE);
		mListBack = findViewById(R.id.bg_list);
		mListBack.setVisibility(View.GONE);

		WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_title), null);

		startFeelLayoutClick();

		moveFeelLayoutBackgound();
	}

	private void stopFeelLayout() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		if (mTickFeelLayout != null) {
			mTickFeelLayout.cancel();
			mTickFeelLayout.purge();
			mTickFeelLayout = null;
		}
	}

	/**
	 * <pre>
	 * 스와이프기능시작
	 * </pre>
	 */
	private void startFeelLayoutSwipe() {
		try {
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_title), this);
			startFeelLayoutClick();
			isSwipeEnabled = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 스와이프기능중지
	 * </pre>
	 */
	private void stopFeelLayoutSwipe() {
		try {
			WidgetUtils.setOnTouchListener(mContext, (ViewGroup) findViewById(R.id.include_feel_title), null);
			startFeelLayoutClick();
			isSwipeEnabled = false;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * FEEL리스트 배경이동 애니메이션이동시 먼저호출하면 좋다~아닌가?
	 */
	protected void moveFeelLayoutBackgound() {
		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + bg_list.getTop() + "," + mTopMarginFeelLayout);
		// 뭘~~~까?
		// if (mListBack != null && _IKaraoke.DEBUG) {
		// mListBack.setBackgroundColor(getResources().getColor(R.color.solid_pink));
		// }
		mListBack.clearAnimation();
		final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mListBack.getLayoutParams();
		params.addRule(RelativeLayout.ABOVE, 0);
		params.addRule(RelativeLayout.BELOW, 0);
		// params.addRule(RelativeLayout.ALIGN_TOP, 0);
		params.addRule(RelativeLayout.ALIGN_TOP, getListContainer().getId());
		// params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
		// params.topMargin = mTopMarginFeelLayout;
		params.topMargin = findViewById(R.id.include_feel_title).findViewById(R.id.view1).getHeight();
		// 최상단이동
		if (mListBack.getTop() == 0) {
			params.topMargin += findViewById(R.id.include_feel_button).getHeight() + 30;
		}
		mListBack.setLayoutParams(params);
		// bg_list.requestLayout();
	}

	/**
	 * FEEL리스트 하단이동
	 */
	class pullFeelLayoutLower extends TimerTask {
		@Override
		public void run() {
			post(pullFeelLayoutLower);
		}
	}

	/**
	 * FEEL리스트 하단이동
	 */
	TimerTask pullFeelLayoutLower = new TimerTask() {

		@Override
		public void run() {

			pullFeelLayout(R.anim.slide_in_up_self, false, true);
			stopFeelLayout();
		}

	};

	/**
	 * FEEL리스트 싱단이동
	 */
	private void startFeelLayoutUpper() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// startFeelLayout();

		if (mTickFeelLayout == null) {
			mTickFeelLayout = new Timer();
			mTickFeelLayout.schedule(new pullFeelLayoutUpper(), _IKaraoke.TIMER_TICK / 2);
		}

	}

	/**
	 * FEEL리스트 싱단이동
	 */
	class pullFeelLayoutUpper extends TimerTask {
		@Override
		public void run() {
			post(pullFeelLayoutUpper);
		}
	}

	/**
	 * FEEL리스트 싱단이동
	 */
	TimerTask pullFeelLayoutUpper = new TimerTask() {

		@Override
		public void run() {

			pullFeelLayout(R.anim.slide_in_up_self, true, true);
			stopFeelLayout();
		}

	};

	private final ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + loadedImage);
			if (loadedImage != null && view.getId() == R.id.feel_image) {
				FeelFragment2.this.isSwipeEnabled = true;
			}
		}
	}

	@Override
	public void onResume() {

		super.onResume();

		if (getApp().getImageDownLoader() instanceof ImageDownLoader3) {
			((ImageDownLoader3) getApp().getImageDownLoader()).setImageLoadingListener(animateFirstListener);
		}

	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	protected void KP6002() {

		super.KP6002();

		try {
			KPItem list = KP_6002.getList(0);

			// 이미지(필정보)
			String value = list.getValue("url_comment");

			if (URLUtil.isNetworkUrl(value)) {
				startFeelLayoutSwipe();
			} else {
				stopFeelLayoutSwipe();
			}
			startFeelLayoutUpper();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	protected HtmlBubble parseHtml(String body, String url, String type) {

		HtmlBubble html = super.parseHtml(body, url, type);

		try {
			KPItem list = KP_6002.getList(0);

			String url_comment = "";
			if (list != null) {
				url_comment = list.getValue("url_comment");
			}

			if (URLUtil.isNetworkUrl(url_comment)) {
				startFeelLayoutSwipe();
			} else {
				stopFeelLayoutSwipe();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return html;
	}

}
