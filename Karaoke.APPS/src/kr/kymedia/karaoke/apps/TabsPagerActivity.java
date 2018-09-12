package kr.kymedia.karaoke.apps;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;
import com.viewpagerindicator.TitlePageIndicator2;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.adpt.TabsPagerAdapter;
import kr.kymedia.karaoke.apps.impl.ITabPagerActivity;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * @author isyoon
 */
public class TabsPagerActivity extends PurchaseActivity implements ITabPagerActivity, ViewPager.OnPageChangeListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public void onPageScrollStateChanged(int state) {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + state);


	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


	}

	@Override
	public void onPageSelected(int position) {

		Intent intent = getIntent();
		intent.putExtra("tabIndex", position);
	}

	@Override
	public void notifyDataSetChanged() {

		if (mTabsAdapter != null) {
			mTabsAdapter.notifyDataSetChanged();
		}

		if (mIndicator != null) {
			mIndicator.notifyDataSetChanged();
		}

		// if (mTabsAdapter.getCount() > 2) {
		// mPager.setOffscreenPageLimit(mTabsAdapter.getCount() - 1);
		// }
		mPager.setOffscreenPageLimit(0);

		Intent intent = getIntent();
		int index = intent.getIntExtra("tabIndex", getCount() - 1);
		setCurrentFragment(index);

	}

	private TabsPagerAdapter mTabsAdapter;
	private ViewPager mPager;
	private PageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());


		super.onCreate(savedInstanceState);

		View v = null;

		v = findViewById(R.id.fragment1);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		v = findViewById(R.id.tab_indicator_pager);
		if (v != null) {
			v.setVisibility(View.VISIBLE);
		}

		mTabsAdapter = new TabsPagerAdapter(getApp().getApplicationContext(), getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mTabsAdapter);

		TitlePageIndicator2 indicator = (TitlePageIndicator2) findViewById(R.id.indicator);
		mIndicator = indicator;
		indicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(this);

		final float density = getResources().getDisplayMetrics().density;

		indicator.setBackgroundColor(getColors(R.color.solid_black));
		WidgetUtils.setBackground(getApp(), indicator, R.drawable.bg_viewpager);
		// indicator.setFooterColor(getColors(R.color.solid_orange));
		indicator.setFooterColor(getColors(R.color.solid_light));
		indicator.setFooterLineHeight(1 * density); // 1dp
		indicator.setFooterIndicatorHeight(3 * density); // 3dp
		indicator.setFooterIndicatorStyle(IndicatorStyle.Underline);
		indicator.setTextColor(getColors(R.color.text_white));
		indicator.setTypeface(Typeface.create("", Typeface.BOLD));
		indicator.setSelectedColor(getColors(R.color.text_white));
		indicator.setSelectedBold(true);
		// indicator.setPadding(100, 100, 100, 100);
		// indicator.setTitlePadding(10.0f);
		// indicator.setTopPadding(10.0f);
		indicator.setClipPadding(-50.0f);

	}

	@Override
	public void addTabs() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		int index = getIntent().getIntExtra("tabIndex", getCount() - 1);
		setCurrentFragment(index);
	}

	@Override
	public void addTab(String tag, String name, Class<?> cls, Bundle args) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, tag);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, name);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + cls);
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, "" + args);
		if (mTabsAdapter != null) {
			mTabsAdapter.addTab(tag, name, cls, args);
		}
	}

	@Override
	public int getCount() {
		if (mTabsAdapter != null) {
			return mTabsAdapter.getCount();
		} else {
			return 0;
		}
	}

	@Override
	public _BaseFragment getAt(int position) {
		_BaseFragment fragment = null;
		if (position > -1 && position < mTabsAdapter.getCount()) {
			fragment = (_BaseFragment) mTabsAdapter.getAt(mPager, position);
			// if (_IKaraoke.DEBUG || IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + position + "-" + fragment);
		}
		return fragment;
	}

	@Override
	public _BaseFragment getCurrentFragment() {
		_BaseFragment fragment = super.getCurrentFragment();

		try {
			if (mPager != null && mTabsAdapter != null && mTabsAdapter.getCount() > 0) {
				int position = mPager.getCurrentItem();
				fragment = getAt(position);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return fragment;
	}

	protected int getCurrentPosition() {
		int position = -1;
		if (mPager != null && mTabsAdapter != null && mTabsAdapter.getCount() > 0) {
			position = mPager.getCurrentItem();
		}
		return position;
	}

	@Override
	public void setCurrentFragment(int index) {
		if (index > -1 && index < mTabsAdapter.getCount()) {
			mIndicator.setCurrentItem(index);
		}
	}

	@Override
	public void clear() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		mTabsAdapter.clear(mPager);
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	protected void finalize() throws Throwable {

		super.finalize();
		mTabsAdapter = null;
		mPager = null;
		mIndicator = null;
	}

	public CharSequence getPageTitle(int position) {
		return mTabsAdapter.getPageTitle(position);
	}
}
