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
 * filename	:	BaseAdActivity.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseAdActivity.java
 * </pre>
 */

package kr.kymedia.karaoke.apps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.directtap.DirectTap;
import com.directtap.DirectTapListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tapjoy.TJError;
import com.tapjoy.TJEvent;
import com.tapjoy.TJEventCallback;
import com.tapjoy.TJEventRequest;
import com.tapjoy.TapjoyConnect;
import com.tapjoy.TapjoyDisplayAdNotifier;
import com.tapjoy.TapjoyEarnedPointsNotifier;
import com.tapjoy.TapjoyLog;
import com.tnkfactory.ad.TnkSession;
import com.tnkfactory.ad.TnkStyle;

import net.daum.adam.publisher.AdView.AnimationType;
import net.daum.adam.publisher.AdView.OnAdClosedListener;
import net.daum.adam.publisher.AdView.OnAdFailedListener;
import net.daum.adam.publisher.AdView.OnAdLoadedListener;
import net.daum.adam.publisher.AdView.OnAdWillLoadListener;
import net.daum.adam.publisher.impl.AdError;
import net.metaps.sdk.Const;
import net.metaps.sdk.Factory;
import net.metaps.sdk.Offer;
import net.metaps.sdk.Receiver;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.KPnnnn.KPnnnnListener;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.util.ManifestData;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

//import com.google.ads.Ad;
//import com.google.ads.AdSize;
//import com.google.ads.AdListener;
//import com.google.ads.AdRequest;
//import com.google.ads.AdRequest.ErrorCode;
//import me.kiip.api.Kiip.*;
//import me.kiip.api.Kiip;
//import me.kiip.api.Kiip.ContentListener;
//import me.kiip.api.Kiip.Position;
//import me.kiip.api.Kiip.RequestListener;
//import me.kiip.api.KiipException;
//import me.kiip.api.Resource;
//import me.kiip.sdk.Kiip;
//import me.kiip.sdk.Kiip.Callback;
//import me.kiip.sdk.Kiip.OnContentListener;
//import me.kiip.sdk.Kiip.OnSwarmListener;
//import me.kiip.sdk.Poptart;
//import com.BpENlUeW.kUbSRTCx106950.Airpush;
//import com.flurry.android.FlurryAdListener;
//import com.flurry.android.FlurryAdSize;
//import com.flurry.android.FlurryAdType;
//import com.flurry.android.FlurryAds;
//import com.flurry.android.FlurryAgent;
//import com.fsn.cauly.CaulyAdInfo;
//import com.fsn.cauly.AdView;
//import com.fsn.cauly.CaulyAdInfo;
//import com.fsn.cauly.CaulyAdInfoBuilder;
//import com.fsn.cauly.CaulyAdView;
//import com.fsn.cauly.CaulyAdViewListener;
//import com.fsn.cauly.Logger;
//import com.fsn.cauly.Logger.LogLevel;
//import com.igaworks.adpopcorn.interfaces.APConstant;
//import com.igaworks.adpopcorn.interfaces.AdPOPcornFactory;
//import com.igaworks.adpopcorn.interfaces.IAdPOPcornEventListener;
//import com.igaworks.adpopcorn.interfaces.IAdPOPcornParameter;
//import com.igaworks.adpopcorn.interfaces.IAdPOPcornSDK;
//import com.igaworks.adpopcorn.interfaces.IRequestGetPopiconResult;
//import com.igaworks.AdPOPcornSDKExample.AdPOPcornAnimationFactory;
//import com.chartboost.sdk.Chartboost;
//import com.chartboost.sdk.ChartboostDelegate;
//import com.greystripe.sdk.GSAd;
//import com.greystripe.sdk.GSAdErrorCode;
//import com.greystripe.sdk.GSAdListener;
//import com.greystripe.sdk.GSFullscreenAd;
//import com.greystripe.sdk.GSLeaderboardAdView;
//import com.greystripe.sdk.GSMediumRectangleAdView;
//import com.greystripe.sdk.GSMobileBannerAdView;
//import com.greystripe.sdk.GSSdkInfo;
//import com.nbpcorp.mobilead.sdk.MobileAdListener;
//import com.nbpcorp.mobilead.sdk.MobileAdView;
//15) PlayHaven - "PH" - 삭제
//import com.playhaven.src.common.PHAPIRequest;
//import com.playhaven.src.common.PHConfig;
//import com.playhaven.src.publishersdk.content.PHContent;
//import com.playhaven.src.publishersdk.content.PHPublisherContentRequest;
//import com.playhaven.src.publishersdk.content.PHPublisherContentRequest.PurchaseDelegate;
//import com.playhaven.src.publishersdk.content.PHPurchase;
//import com.playhaven.src.publishersdk.content.PHPublisherContentRequest.ContentDelegate;
//import com.playhaven.src.publishersdk.content.PHPublisherContentRequest.PHDismissType;
//import com.playhaven.src.publishersdk.open.PHPublisherOpenRequest;
//import com.skplanet.tad.AdListenerResponse;
//import com.vungle.sdk.VunglePub;
//import com.vungle.sdk.VunglePub.EventListener;
//import com.nativex.advertiser.AdvertiserManager;
//import com.nativex.monetization.MonetizationManager;
//import com.nativex.monetization.business.Balance;
//import com.nativex.monetization.listeners.CurrencyListenerV1;
//import com.nativex.monetization.listeners.SessionListener;
//import com.nativex.monetization.theme.OriginalTheme;
//import com.inmobi.androidsdk.IMAdListener;
//import com.inmobi.androidsdk.IMAdRequest;
//import com.inmobi.androidsdk.IMAdView;
//import com.iwe.tapad.TapAdPlatform;
//import com.iwe.tapad.define.TapadViewType;
//import com.iwe.tapad.listener.TapadListener;
//import com.jirbo.adcolony.*;

//import com.sponsorpay.SponsorPay;
//import com.sponsorpay.publisher.SponsorPayPublisher;
//import com.sponsorpay.publisher.interstitial.SPInterstitialRequestListener;
//import com.startapp.android.publish.Ad;
//import com.startapp.android.publish.AdEventListener;
//import com.startapp.android.publish.StartAppAd;

/**
 * TODO NOTE:<br>
 * 에드몹액티비티
 *
 * @author isyoon
 * @version 1.0
 * @since 2012. 6. 8.
 */
public class BaseAdActivity extends _BaseActivity implements OnTouchListener {

	View mFreeZone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mFreeZone = findViewById(R.id.ad_freezone);
	}

	/**
	 * 로그기록:KP_4013 전면광고 오픈
	 */
	private KPnnnn KP_4013 = null;
	/**
	 * KP_4014 광고정보조회
	 */
	private KPnnnn KP_4014 = null;

	// Create runnable for posting
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			updateResultsInUi();
		}
	};

	private void updateResultsInUi() {
	}

	protected String is_ad = "";
	protected int ad_time = 30;
	/**
	 * ad_vendor : 화면종료시팝업
	 */
	private String ad_vendor = "";
	/**
	 * ad_banner : 배너광고
	 */
	protected String ad_banner = "";
	/**
	 * ad_freezone : 공짜존
	 */
	protected String ad_freezone = "";
	protected String ad_freezone_info = "";
	protected String ad_freezone_error = "";
	// /**
	// * 광고API광고수확인
	// */
	// protected int ad_amount = 0;
	// protected int ad_played = 0;
	// protected int ad_maximun = 0;

	private ViewGroup mAdBase;
	private View mAdView;

	/**
	 * 테스트용디바이스아이디(애드몹)
	 */
	private String ADMOB_TEST_DEVICE_ID = "";
	// protected com.google.ads.AdView mAdmob = null;
	// protected com.google.ads.InterstitialAd mAdmobInterstitial = null;
	protected com.google.android.gms.ads.AdView mAdmobAdView = null;
	protected com.google.android.gms.ads.InterstitialAd mAdmobInterstitial = null;
	/**
	 * Notification기반광고 홈화면에서 한번만 호출해주는게 신상에 좋을듯
	 */
	// @Deprecated
	// private Airpush mAirPush = null;

	private net.daum.adam.publisher.AdView mAdamAdView = null;

	private net.daum.adam.publisher.AdInterstitial mAdamInterstitial = null;

	// private com.fsn.cauly.CaulyAdView mAdCaulyAdView = null;

	// private com.skplanet.tad.AdView mAdSKTAdView = null;
	// private com.skplanet.tad.AdInterstitial mAdSKTInterstitial;

	// private com.inmobi.androidsdk.IMAdView mAdInMobi = null;

	// private IAdPOPcornSDK mAdPopcorn;
	// private LinearLayout mAdpopcornLayout;
	// private View mAdpopcornView;

	// private TapAdPlatform mAdTapad;

	// private TapjoyConnect mAdTapjoy;

	// private com.chartboost.sdk.Chartboost mAdChartboost = null;

	// private com.jirbo.adcolony.AdColonyVideoAd mAdColony = null;

	// private me.kiip.sdk.Kiip mAdKiip = null;
	// private me.kiip.sdk.Poptart mAdKiipPoptart = null;

	// private com.playhaven.src.publishersdk.content.PHPublisherContentRequest mAdPlayhaven = null;

	// private com.nbpcorp.mobilead.sdk.MobileAdView mAdNaverAdView = null;

	// private View mAdGreyStripeAdView = null;
	// private com.greystripe.sdk.GSFullscreenAd mAdGreyStripeInterstitial = null;

	// private Intent mAdSponsorPay = null;

	protected com.directtap.DirectTapBanner mAdDirectTapBanner = null;
	protected com.directtap.DirectTap.FullScreen mAdDirectTapInterstitial = null;

	@Override
	public void KP_init() {

		super.KP_init();

		Context context = getApp().getApplicationContext();

		KP_4013 = KP_init(context, KP_4013);
		KP_4013.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
				KPnnnnResult(what, opcode, r_code, r_message, r_info != null ? r_info.toString() : "");
			}

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message, KPItem r_info) {

			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

		KP_4014 = KP_init(context, KP_4014);
		KP_4014.setOnKPnnnnListner(new KPnnnnListener() {

			@Override
			public void onKPnnnnResult(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
				KPnnnnResult(what, opcode, r_code, r_message, r_info != null ? r_info.toString() : "");

			}

			@Override
			public void onKPnnnnSuccess(int what, String opcode, String r_code, String r_message, KPItem r_info) {

				startAd(r_info);

			}

			@Override
			public void onKPnnnnStart(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnProgress(long size, long total) {


			}

			@Override
			public void onKPnnnnFinish(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnFail(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnError(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}

			@Override
			public void onKPnnnnCancel(int what, String opcode, String r_code, String r_message, KPItem r_info) {


			}
		});

		if (_IKaraoke.DEBUG) {
			initAdTest();
		}

	}

	/**
	 * 광고조회여부확인 - 화면(Activity)내 중복실행방지
	 */
	protected boolean isQueryAd = true;

	/**
	 * @param isQueryAd the isQueryAd to set
	 */
	public void setQueryAd(boolean isQueryAd) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isQueryAd);

		this.isQueryAd = isQueryAd;
	}

	public void queryAd(String m1, String m2) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isQueryAd);

		if (!isQueryAd) {
			return;
		}

		isQueryAd = false;

		_BaseFragment fragment = getCurrentFragment();

		if (fragment != null && (TextUtil.isEmpty(m1) || TextUtil.isEmpty(m2))) {
			m1 = fragment.p_m1;
			m2 = fragment.p_m2;
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + fragment + ", p_m1:\t" + m1 + ", p_m2:\t" + m2);

		if (!TextUtil.isEmpty(m1) && !TextUtil.isEmpty(m2)) {
			KP_4014.KP_4014(getApp().p_mid, m1, m2);
		}
	}

	@Override
	public void refresh() {
		isQueryAd = true;
		super.refresh();
	}

	// /**
	// * 스피너중복선택방지
	// * @see <a href="http://stackoverflow.com/questions/5624825/spinner-onitemselected-executes-when-it-is-not-suppose-to/5918177#5918177">Stack Overflow - Spinner onItemSelected() executes when it is not suppose to [duplicate]</a>
	// */
	// private boolean isAdSelectedByUser = true;

	/**
	 * 조회처리된ad_vendor를확인
	 *
	 * @see <a href="http://stackoverflow.com/questions/5624825/spinner-onitemselected-executes-when-it-is-not-suppose-to/5918177#5918177">Stack Overflow - Spinner onItemSelected() executes when it is not suppose to [duplicate]</a>
	 */
	private void setAdVendor(String ad_vendor) {
		this.ad_vendor = ad_vendor;
		try {
			if (_IKaraoke.DEBUG) {
				SpinnerAdapter a = ad_vendors.getAdapter();
				if (a != null && !TextUtil.isEmpty(ad_vendor)) {
					// isAdSelectedByUser = false;
					int pos = WidgetUtils.getPosition(a, ad_vendor);
					ad_vendors.setSelection(pos);
					String value = (String) a.getItem(pos);
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName() + pos + ":" + value + "-" + ad_vendor);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 조회처리된ad_banner를확인
	 *
	 * @see <a href="http://stackoverflow.com/questions/5624825/spinner-onitemselected-executes-when-it-is-not-suppose-to/5918177#5918177">Stack Overflow - Spinner onItemSelected() executes when it is not suppose to [duplicate]</a>
	 */
	private void setAdBanner(String ad_banner) {
		this.ad_banner = ad_banner;
		try {
			if (_IKaraoke.DEBUG) {
				SpinnerAdapter a = ad_banners.getAdapter();
				if (a != null && !TextUtil.isEmpty(ad_banner)) {
					// isAdSelectedByUser = false;
					int pos = WidgetUtils.getPosition(a, ad_banner);
					ad_banners.setSelection(pos);
					String value = (String) a.getItem(pos);
					// final boolean check = isAdSelectedByUser;
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName() + pos + ":" + value + "-" + ad_banner);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 광고구분목록
	 */
	private Spinner ad_vendors;
	private Spinner ad_banners;

	/**
	 * 스피너중복선택방지 - 그냥리스너를 땠다 붙였다하느게 신상에 좋것다.
	 *
	 * @see <a href="http://stackoverflow.com/questions/5624825/spinner-onitemselected-executes-when-it-is-not-suppose-to/5918177#5918177">Stack Overflow - Spinner onItemSelected() executes when it is not suppose to [duplicate]</a>
	 */
	private void initAdTest() {
		try {
			ad_vendors = (Spinner) findViewById(R.id.ad_vendors);
			ArrayAdapter<CharSequence> adpt_vendors = ArrayAdapter.createFromResource(getApp(), R.array.ad_vendors, R.layout.ad_vendors);
			// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adpt_vendors.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
			ad_vendors.setAdapter(adpt_vendors);
			ad_vendors.setOnItemSelectedListener(null);

			ad_banners = (Spinner) findViewById(R.id.ad_banners);
			ArrayAdapter<CharSequence> adpt_banners = ArrayAdapter.createFromResource(getApp(), R.array.ad_vendors, R.layout.ad_vendors);
			// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adpt_banners.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
			ad_banners.setAdapter(adpt_banners);
			ad_banners.setOnItemSelectedListener(null);
		} catch (Exception e) {

			e.printStackTrace();
		}
		// isAdSelectedByUser = true;
	}

	boolean isStartAdTest = true;
	/**
	 * 스피너중복선택방지 - 그냥리스너를 땠다 붙였다하느게 신상에 좋것다.
	 *
	 * @see <a href="http://stackoverflow.com/questions/5624825/spinner-onitemselected-executes-when-it-is-not-suppose-to/5918177#5918177">Stack Overflow - Spinner onItemSelected() executes when it is not suppose to [duplicate]</a>
	 */
	Runnable startAdTest = new Runnable() {
		@Override
		public void run() {
			startAdTest();
		}
	};

	/**
	 * 광고테스트
	 */
	protected void startAdTest() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "[startAdTest]" + isStartAdTest);


		if (!isStartAdTest) {
			return;
		}
		isStartAdTest = false;

		// 광고테스트
		if (ad_vendors != null) {
			ad_vendors.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName() + getResourceEntryName(ad_vendors.getId()));

					// if (!isAdSelectedByUser) {
					// isAdSelectedByUser = true;
					// return;
					// }
					// final boolean check = isAdSelectedByUser;

					String value = (String) parent.getItemAtPosition(position);
					if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + position + ":" + value + "-" + ad_vendor);


					is_ad = "Y";
					ad_time = 30;

					ad_vendor = (String) parent.getItemAtPosition(position);
					// ad_banner = ad_vendor;
					ad_freezone = ad_vendor;

					initAd(ad_vendor);

					// showAdBanner(ad_banner);

					isShowAdPopup = true;

					// showAdPopup(ad_vendor);
					// showAdFreezone();

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

					if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + " : " + parent);

				}
			});

		}

		// 광고테스트
		if (ad_banners != null) {
			ad_banners.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName() + getResourceEntryName(ad_banners.getId()));

					// if (!isAdSelectedByUser) {
					// isAdSelectedByUser = true;
					// return;
					// }
					// final boolean check = isAdSelectedByUser;

					String value = (String) parent.getItemAtPosition(position);
					if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + position + ":" + value + "-" + ad_banner);


					is_ad = "Y";
					ad_time = 30;

					ad_banner = (String) parent.getItemAtPosition(position);
					// ad_banner = ad_vendor;
					// ad_freezone = ad_vendor;

					initAd(ad_banner);

					showAdBanner(ad_banner);

					// isShowAdPopup = true;

					// showAdPopup(ad_vendor);
					// showAdFreezone();

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

					if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + " : " + parent);

				}
			});

		}
	}

	;

	/**
	 * 광고여부("is_ad") 확인후 항시노출
	 */
	protected void startAd(KPItem info) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName());

		try {

			if (info == null) {
				return;
			}

			android.util.Log.d("Ads", __CLASSNAME__ + ":initAds:" + getMethodName() + "info - " + info.toString(2));

			is_ad = info.getValue("is_ad");
			ad_time = TextUtil.parseInt(info.getValue("ad_time"));
			ad_vendor = info.getValue("ad_vendor");
			ad_banner = info.getValue("ad_banner");
			ad_freezone = info.getValue("ad_freezone");
			ad_freezone_info = info.getValue("ad_freezone_info");
			ad_freezone_error = info.getValue("ad_freezone_error");

			final Button freezone_info = (Button) findViewById(R.id.ad_freezone_info);
			if (freezone_info != null) {
				String text = ad_freezone_info;
				if (_IKaraoke.DEBUG) {
					text = "[디버그정보]" + ad_freezone + ":" + ad_freezone_info;
				}
				freezone_info.setText(text);
			}

			// if (isACTIONMAIN()) {
			// initAirPush();
			// }

			// test
			// if (_IKaraoke.DEBUG)
			// {
			// is_ad = "Y";
			// ad_time = 30;
			// ad_banner = "G";
			// ad_vendor = "G3";
			// //ad_freezone = "";
			// }

			if (("Y").equalsIgnoreCase(is_ad)) {

				if (!TextUtil.isEmpty(ad_banner)) {
					initAd(ad_banner);
					showAdBanner(ad_banner);
				} else {
					showAdBanner(false);
				}

				if (!TextUtil.isEmpty(ad_vendor)) {
					initAd(ad_vendor);
					// isShowAdPopup = true;
				}

				// 공짜존
				if (!TextUtil.isEmpty(ad_freezone)) {
					initAd(ad_freezone);
					// isShowAdPopup = true;
				}

			}

			if (_IKaraoke.DEBUG) {
				if (!TextUtil.isEmpty(ad_vendor)) {
					setAdVendor(ad_vendor);
				}

				if (!TextUtil.isEmpty(ad_banner)) {
					setAdBanner(ad_banner);
				}
				postDelayed(startAdTest, 500);
			}

		} catch (Exception e) {

			// e.printStackTrace();
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, Log2.getStackTraceString(e));
		}
	}

	private void showAdFreezone(String ad_freezone) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		isShowAdPopup = true;
		showAdPopup(ad_freezone);
	}

	public void showAdFreezone() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// if (TextUtil.isEmpty(ad_freezone)) {
		// return;
		// }

		startAnimationShowHide(mFreeZone, true);

		// final View freezone_button = findViewById(R.id.ad_freezone_button);
		if (mFreeZone != null) {
			mFreeZone.setClickable(true);
			mFreeZone.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName());

					showAdFreezone(ad_freezone);
					startAnimationShowHide(mFreeZone, false);
				}
			});

			mFreeZone.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return true;
				}
			});
		}

		final View freezone_info = findViewById(R.id.ad_freezone_info);
		if (freezone_info != null) {
			freezone_info.setClickable(true);
			// freezone_info.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, getMethodName() + v);
			//
			// freezone.performClick();
			// }
			// });

			freezone_info.setOnTouchListener(new OnTouchListener() {

				boolean cancel = false;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + event);

					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							mFreeZone.setPressed(true);
							cancel = false;
							break;

						case MotionEvent.ACTION_MOVE:
							break;

						case MotionEvent.ACTION_UP:
							if (!cancel) {
								mFreeZone.playSoundEffect(SoundEffectConstants.CLICK);
								mFreeZone.setPressed(false);
								showAdFreezone(ad_freezone);
								startAnimationShowHide(mFreeZone, false);
							}
							break;

						default:
							mFreeZone.setPressed(false);
							cancel = true;
							break;
					}
					return false;
				}
			});
		}

		final View freezone_close = findViewById(R.id.ad_freezone_close);
		if (freezone_close != null) {
			freezone_close.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + v);

					startAnimationShowHide(mFreeZone, false);
				}
			});
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		return false;
	}

	boolean initAd(String ad_vendor) {
		android.util.Log.d("Ads", __CLASSNAME__ + ":initAds:" + getMethodName() + "ad_banner:" + this.ad_banner + ", ad_vendor:" + this.ad_vendor);
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_vendor);
		if (_IKaraoke.DEBUG) Log2.i("Ads", is_ad);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "ad_banner:" + this.ad_banner);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "ad_vendor:" + this.ad_vendor);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "ad_freezone:" + this.ad_freezone);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "" + isShowAdPopup);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "[getApp().p_uid]" + getApp().p_uid);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "[p_mid]" + getApp().p_mid);
		if (_IKaraoke.DEBUG) Log2.i("Ads", "[p_stbid]" + getApp().KPparam.getStbid());

		// if (mAdBanner != null) {
		// return false;
		// }

		// test
		if (_IKaraoke.DEBUG) {
			// Use AdRequest.Builder.addTestDevice("54177550144C9D0C99975C2ED541C514") to get test ads on this device.
			// ADMOB_TEST_DEVICE_ID = "54177550144C9D0C99975C2ED541C514";
		} else {
			ADMOB_TEST_DEVICE_ID = "";
		}

		try {

			// initAdMob(false);
			// 기본:구글애드몹
			if (("G").equalsIgnoreCase(ad_vendor)) {
				// 구글애드몹인경우
				// 1. 배너광고
				initAdMobBanner(false);
			} else if (("G1").equalsIgnoreCase(ad_vendor)) {
				// 구글애드몹인경우
				// 1. 배너광고
				initAdMobBanner(false);
			} else if (("G2").equalsIgnoreCase(ad_vendor)) {
				// 구글애드몹인경우
				// 2. 풀배너광고
				initAdMobBanner(true);
			} else if (("G3").equalsIgnoreCase(ad_vendor)) {
				// 구글애드몹인경우
				// 3. 팝업광고
				initAdMobInterstitial();
			} else if (("D").equalsIgnoreCase(ad_vendor)) {
				// 다음아담인경우
				initAdamBanner();
			} else if (("D1").equalsIgnoreCase(ad_vendor)) {
				// 다음아담인경우
				initAdamBanner();
			} else if (("D2").equalsIgnoreCase(ad_vendor)) {
				// 다음아담인경우
				initAdamInterstitial();
			} else if (("C").equalsIgnoreCase(ad_vendor)) {
				// 카울리인경우
				// initAdCauly();
			} else if (("T").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
				// initAdSKTBanner();
			} else if (("T1").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
				// initAdSKTBanner();
			} else if (("T2").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
				// initAdSKTInterstitial();
			} else if (("I").equalsIgnoreCase(ad_vendor)) {
				// IMOBI인경우
				// initAdInMobi();
				// } else if (("A1").equalsIgnoreCase(ad_vendor)) {
				// mAirPush.startSmartWallAd(); //start random smart wall ad.
				// //1. Dialog Ad
				// mAirPush.startDialogAd(); //start dialog ad.
				// } else if (("A2").equalsIgnoreCase(ad_vendor)) {
				// //2. AppWall Ad
				// mAirPush.startAppWall(); //start app wall.
				// } else if (("A3").equalsIgnoreCase(ad_vendor)) {
				// //3. LandingPage Ad
				// mAirPush.startLandingPageAd(); //start landing page.
			} else if (("J1").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyReward();
			} else if (("J2").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyReward();
			} else if (("J3").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyReward();
			} else if (("J4").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyReward();
			} else if (("J5").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyNoReward();
			} else if (("J6").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyNoReward();
			} else if (("J7").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyNoReward();
			} else if (("J8").equalsIgnoreCase(ad_vendor)) {
				// 7) Tapjoy - J
				initAdTapjoyNoReward();
			} else if (("N1").equalsIgnoreCase(ad_vendor)) {
				// 8) TNK팩토리 - N
				initAdTNK();
			} else if (("N2").equalsIgnoreCase(ad_vendor)) {
				// 8) TNK팩토리 - N
				initAdTNK();
			} else if (("P1").equalsIgnoreCase(ad_vendor)) {
				// 9) 애드팝콘 - P
				// initAdPopcorn();
			} else if (("P2").equalsIgnoreCase(ad_vendor)) {
				// 9) 애드팝콘 - P
				// initAdPopcorn();
			} else if (("M1").equalsIgnoreCase(ad_vendor)) {
				// 10) 미탭스 - M
				initAdMetaps();
			} else if (("M2").equalsIgnoreCase(ad_vendor)) {
				// 10) 미탭스 - M
				initAdMetaps();
			} else if (("TA1").equalsIgnoreCase(ad_vendor)) {
				// 16) TAPAD - TA -> "TA3"
				// initAdTapAd();
			} else if (("TA2").equalsIgnoreCase(ad_vendor)) {
				// 16) TAPAD - TA -> "TA3"
				// initAdTapAd();
			} else if (("TA3").equalsIgnoreCase(ad_vendor)) {
				// 16) TAPAD - TA -> "TA3"
				// initAdTapAd();
			} else if (("CB1").equalsIgnoreCase(ad_vendor)) {
				// ChartBoost
				// initAdChartboost();
			} else if (("CB2").equalsIgnoreCase(ad_vendor)) {
				// ChartBoost
				// initAdChartboost();
			} else if (("AC1").equalsIgnoreCase(ad_vendor)) {
				// AdColony
				// initAdColony();
			} else if (("AC2").equalsIgnoreCase(ad_vendor)) {
				// AdColony
				// initAdColony();
			} else if (("K1").equalsIgnoreCase(ad_vendor)) {
				// kiip
				// initAdKiip();
			} else if (("K2").equalsIgnoreCase(ad_vendor)) {
				// kiip
				// initAdKiip();
			} else if (("W1").equalsIgnoreCase(ad_vendor)) {
				// W3i
				// initAdW3i();
			} else if (("W2").equalsIgnoreCase(ad_vendor)) {
				// W3i
				// initAdW3i();
			} else if (("W3").equalsIgnoreCase(ad_vendor)) {
				// W3i
				// initAdW3i();
			} else if (("PH").equalsIgnoreCase(ad_vendor)) {
				// PlayHaven
				// initAdPlayhaven();
			} else if (("F1").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// initAdFlurry();
				// showBannerFlurry("");
			} else if (("F2").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// initAdFlurry();
			} else if (("F3").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// initAdFlurry();
			} else if (("F4").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// initAdFlurry();
			} else if (("F5").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// initAdFlurry();
			} else if (("NA1").equalsIgnoreCase(ad_vendor)) {
				// 네이버애드포스트
				// initAdNaver();
			} else if (("GS1").equalsIgnoreCase(ad_vendor)) {
				// Greystripe - 1. 배너광고 - 호환오류
				// initAdGreystripeBanner(1);
			} else if (("GS2").equalsIgnoreCase(ad_vendor)) {
				// Greystripe
				// initAdGreystripeBanner(2);
			} else if (("GS3").equalsIgnoreCase(ad_vendor)) {
				// Greystripe
				// initAdGreystripeInterstitial();
			} else if (("GS4").equalsIgnoreCase(ad_vendor)) {
				// Greystripe
				// initAdGreystripeInterstitial();
			} else if (("S1").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// initAdSponsorPay();
			} else if (("S2").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// initAdSponsorPay();
			} else if (("S3").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// initAdSponsorPay();
				// requestSponsorPayInterStitial();
			} else if (("S4").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// initAdSponsorPay();
				// requestSponsorPayInterStitial();
			} else if (("V1").equalsIgnoreCase(ad_vendor)) {
				// Vungle
				// initAdVungle();
			} else if (("V2").equalsIgnoreCase(ad_vendor)) {
				// Vungle
				// initAdVungle();
			} else if (("DT1").equalsIgnoreCase(ad_vendor)) {
				// DirectTap
				initAdDirectTapBanner();
			} else if (("DT2").equalsIgnoreCase(ad_vendor)) {
				// DirectTap
				initAdDirectTapInterstitial();
			} else if (("SA1").equalsIgnoreCase(ad_vendor)) {
				// StartApp
				// initAdStartAppBanner();
			} else if (("SA2").equalsIgnoreCase(ad_vendor)) {
				// StartApp
				// initAdStartAppInterstitial();
			}

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	private void initAdPopupDefault() {
		post(new Runnable() {

			@Override
			public void run() {

				setAdVendor("G3");
				// initAdMobAdView(false);
				initAdMobInterstitial();
			}
		});
	}

	/**
	 * 광고표시여부확인
	 *
	 * @see BaseAdActivity#showAdPopup(String)
	 */
	private boolean isShowAdPopup = false;

	boolean showAdPopup(String ad_vendor) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + is_ad + ":" + ad_vendor);

		if (!("Y").equalsIgnoreCase(is_ad)) {
			isShowAdPopup = false;
			return false;
		}

		if (TextUtil.isEmpty(ad_vendor)) {
			isShowAdPopup = false;
			return false;
		}

		if (!isShowAdPopup) {
			return false;
		}

		isShowAdPopup = false;

		// startLoadingDialog(null);

		boolean ret = false;

		try {
			if (("").equalsIgnoreCase(ad_vendor)) {
				ret = false;
				// } else if (("A0").equalsIgnoreCase(ad_vendor)) {
				// //We notice that you may be initiating SmartWall with airpush.startAppWall()
				// //or another individual call rather than the recommended method of using airpush.startSmartWallAd().
				// //Please note that your earnings may be significantly lowered by doing this.
				// //To ensure maximum earnings, please use the airpush.startSmartWallAd() call which takes
				// //advantage of SmartWall's dynamic optimization between the various subformats of SmartWall,
				// //which include: AppWall, Dialog Ads, LP Ads, and soon Rich Media & Video Ads. Please refer to the SmartWall documentation for
				// //0. start random smart wall ad.
				// mAirPush.startSmartWallAd();
				// } else if (("A1").equalsIgnoreCase(ad_vendor)) {
				// //1. AppWall Ad
				// //mAirPush.startAppWall(); //start app wall.
				// mAirPush.startSmartWallAd();
				// } else if (("A2").equalsIgnoreCase(ad_vendor)) {
				// //2. Dialog Ad
				// //mAirPush.startDialogAd(); //start dialog ad.
				// mAirPush.startSmartWallAd();
				// } else if (("A3").equalsIgnoreCase(ad_vendor)) {
				// //3. LandingPage Ad
				// //mAirPush.startLandingPageAd(); //start landing page.
				// mAirPush.startSmartWallAd();
			} else if (("G3").equalsIgnoreCase(ad_vendor)) {
				showAdPopupAdmob();
				ret = true;
			} else if (("D2").equalsIgnoreCase(ad_vendor)) {
				// 다음아담인경우
				// initAdam();
				showAdPopupAdam();
				// Tapjoy광고
				ret = true;
			} else if (("T").equalsIgnoreCase(ad_banner)) {
				// TAD인경우
			} else if (("T1").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
			} else if (("T2").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
				// if (this.mAdSKTInterstitial != null) {
				// this.mAdSKTInterstitial.showAd();
				// ret = true;
				// }
			} else if (("J1").equalsIgnoreCase(ad_vendor)) {
				// 1. the Offerwall(마켓 플레이스)
				showAdListTapjoy();
				ret = true;
			} else if (("J2").equalsIgnoreCase(ad_vendor)) {
				// 2. Featured Ads(전면 광고)
				showAdPopupTapjoy();
				ret = true;
			} else if (("J3").equalsIgnoreCase(ad_vendor)) {
				// 3. Display Ads(배너 광고)
				showAdBannerTapjoy();
				ret = true;
			} else if (("J4").equalsIgnoreCase(ad_vendor)) {
				// 4. Video Ads(영상 광고(?))
				showAdVideoTapjoy();
				ret = true;
			} else if (("J5").equalsIgnoreCase(ad_vendor)) {
				// 1. the Offerwall(마켓 플레이스)
				showAdListTapjoy();
				ret = true;
			} else if (("J6").equalsIgnoreCase(ad_vendor)) {
				// 2. Featured Ads(전면 광고)
				showAdPopupTapjoy();
				ret = true;
			} else if (("J7").equalsIgnoreCase(ad_vendor)) {
				// 3. Display Ads(배너 광고)
				showAdBannerTapjoy();
				ret = true;
			} else if (("J8").equalsIgnoreCase(ad_vendor)) {
				// 4. Video Ads(영상 광고(?))
				showAdVideoTapjoy();
				ret = true;
			} else if (("N1").equalsIgnoreCase(ad_vendor)) {
				// 8) TNK팩토리 - N
				showAdPopListTNK();
				ret = true;
			} else if (("N2").equalsIgnoreCase(ad_vendor)) {
				// 8) TNK팩토리 - N
				showAdPopupTNK();
				ret = true;
			} else if (("P1").equalsIgnoreCase(ad_vendor)) {
				// 9) 애드팝콘 - P
				// showAdPopListAdPopCorn();
				ret = true;
			} else if (("P2").equalsIgnoreCase(ad_vendor)) {
				// 9) 애드팝콘 - P
				// showAdPopupAdPopCorn();
				ret = true;
			} else if (("M1").equalsIgnoreCase(ad_vendor)) {
				// 10) 미탭스 - M
				showAdPopListMeTaps();
				ret = true;
			} else if (("M2").equalsIgnoreCase(ad_vendor)) {
				// 10) 미탭스 - M
				showAdPopupMeTaps();
				ret = true;
			} else if (("TA1").equalsIgnoreCase(ad_vendor)) {
				// 16) TAPAD - TA -> "TA3"
				// showAdBannerTapAd();
				ret = true;
			} else if (("TA2").equalsIgnoreCase(ad_vendor)) {
				// 16) TAPAD - TA -> "TA3"
				// showAdPopupTapAd();
				ret = true;
			} else if (("TA3").equalsIgnoreCase(ad_vendor)) {
				// 16) TAPAD - TA -> "TA3"
				// showAdPopListTapAd();
				ret = true;
			} else if (("CB1").equalsIgnoreCase(ad_vendor)) {
				// 1. showMoreApps
				// Load and show more apps page
				// mAdChartboost.showMoreApps();
				// ret = true;
			} else if (("CB2").equalsIgnoreCase(ad_vendor)) {
				// 2. showInterstitial
				// Show an interstitial
				// mAdChartboost.showInterstitial();
				// ret = true;
				// } else if (("AC1").equalsIgnoreCase(ad_vendor)) {
				// showAdPopListAdColony();
				// ret = true;
				// } else if (("AC2").equalsIgnoreCase(ad_vendor)) {
				// showAdPopupAdColony();
				// ret = true;
				// } else if (("K1").equalsIgnoreCase(ad_vendor)) {
				// //kiip
				// mAdKiipPoptart.show(this, false);
				// } else if (("K2").equalsIgnoreCase(ad_vendor)) {
				// //kiip
				// mAdKiipPoptart.show(this, true);
			} else if (("W1").equalsIgnoreCase(ad_vendor)) {
				// W3i
				// showAdPopListW3i();
				ret = true;
			} else if (("W2").equalsIgnoreCase(ad_vendor)) {
				// W3i
				// MonetizationManager.showFeaturedOfferDialog(this);
				ret = true;
			} else if (("W3").equalsIgnoreCase(ad_vendor)) {
				// W3i
				// MonetizationManager.showFeaturedOfferInterstitial(this);
				ret = true;
			} else if (("PH").equalsIgnoreCase(ad_vendor)) {
				// PlayHaven
				// mAdPlayhaven.send();
				ret = true;
			} else if (("F1").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// showBannerFlurry();
			} else if (("F2").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// showAdPopupFlurry();
				ret = true;
			} else if (("F3").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// showBannerFlurry();
			} else if (("F4").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// showAdPopupFlurry();
				ret = true;
			} else if (("F5").equalsIgnoreCase(ad_vendor)) {
				// 17) Flurry
				// showAdPopupFlurry();
				ret = true;
			} else if (("GS1").equalsIgnoreCase(ad_vendor)) {
				// Greystripe - 1. 배너광고 - 호환오류
			} else if (("GS2").equalsIgnoreCase(ad_vendor)) {
				// Greystripe - 2. 배너광고(풀) - 호환오류
			} else if (("GS3").equalsIgnoreCase(ad_vendor)) {
				// Greystripe - 3. 팝업광고
				// showAdPopupGreystripe();
				ret = true;
			} else if (("GS4").equalsIgnoreCase(ad_vendor)) {
				// Greystripe - 4. 팝업충전(이용권/보상광고) - 보류
				// showAdPopListGreystripe();
				ret = true;
			} else if (("S1").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
			} else if (("S2").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// showAdPopListSponsorPay();
				ret = true;
			} else if (("S3").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// showAdPopupSponsorPay();
				ret = true;
			} else if (("S4").equalsIgnoreCase(ad_vendor)) {
				// SponsorPay
				// showAdPopupSponsorPay();
			} else if (("V1").equalsIgnoreCase(ad_vendor)) {
				// Vungle
				// showAdPopListVungle();
				ret = true;
			} else if (("V2").equalsIgnoreCase(ad_vendor)) {
				// Vungle
				// showAdPopupVungle();
				ret = true;
			} else if (("DT2").equalsIgnoreCase(ad_vendor)) {
				// DirectTap
				showAdPopupDirectTap();
				ret = true;
			} else if (("SA2").equalsIgnoreCase(ad_vendor)) {
				// StaartApp
				// showAdPopupStartApp();
				ret = true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		String mid = getApp().p_mid;
		KP_4013.KP_4013(mid, "", "", ad_vendor);

		// stopLoading(null);
		postDelayed(new Runnable() {

			@Override
			public void run() {

				stopLoadingDialog(null);
			}
		}, 1000);

		return ret;
	}

	/**
	 * //기본:구글애드몹<br>
	 * 배너미지원광고업체용<br>
	 *
	 * @deprecated
	 */
	private void showAdBannerDefault() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName());
		// android.util.Log.d(__CLASSNAME__, __CLASSNAME__ + ":initAds:" + getMethodName());
		// initAdmob();
		// addAdBanner(mAdmob);
	}

	@Override
	protected void onStart() {

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		super.onStart();
		// if (mAdChartboost != null) {
		// mAdChartboost.onStart(this);
		// }
	}

	@Override
	protected void onPause() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		super.onPause();
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 *
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
		super.onResume();
		// AdPOPcornSDK를 활성화 합니다.
		// getCurrentFragment().getLogin();
		// if (mAdPopcorn != null) {
		// mAdPopcorn.onResume();
		// // 파피콘을 보인다.
		// if (getCurrentFragment() != null) {
		// if (getApp().isLoginUser()) {
		// showAdIconAdPopCorn();
		// } else {
		// hideIconAdPopCorn();
		// }
		// } else {
		// hideIconAdPopCorn();
		// }
		// hideIconAdPopCorn();
		// }

		// Metaps
		// * Run install check process manually<br>
		// * (there is a background process running but you may wish to get the newest data)
		try {
			Factory.runInstallReport();
		} catch (Exception e) {
			// e.printStackTrace();
			// Toast.makeText(this, Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
		}

		// if (mAdStartAppInterstitial != null) {
		// mAdStartAppInterstitial.onResume();
		// }
	}

	/**
	 */
	@Override
	public void onBackPressed() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isShowAdPopup);

		try {
			if (getCurrentFragment() != null) {
				if (getCurrentFragment().onBackPressed() && !showAdPopup(ad_vendor)) {
					super.onBackPressed();
					// if (mAdChartboost != null) {
					// mAdChartboost.onBackPressed();
					// }
				}
			} else {
				super.onBackPressed();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void close() {

		if (!showAdPopup(ad_vendor)) {
			super.close();
		}
	}

	/**
	 * 종료시 팝업오픈여부 확인
	 */
	@Override
	public void finish() {

		dismissAlertDialogsAll();

		super.finish();
	}

	@Override
	protected void onStop() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isShowAdPopup);

		// if (mAdChartboost != null) {
		// mAdChartboost.onStop(this);
		// }

		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + isShowAdPopup);

		stopAd();
		super.onDestroy();
	}

	private void stopAd() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {

			// if (Kiip.getInstance() != null) {
			// Kiip.getInstance().endSession(new Callback() {
			//
			// @Override
			// public void onFinished(Kiip arg0, Poptart arg1) {
			//
			//
			// }
			//
			// @Override
			// public void onFailed(Kiip arg0, Exception arg1) {
			//
			//
			// }
			// });
			//
			// if (mAdKiip != null) {
			// mAdKiip.endSession(new Callback() {
			//
			// @Override
			// public void onFinished(Kiip arg0, Poptart arg1) {
			//
			//
			// }
			//
			// @Override
			// public void onFailed(Kiip arg0, Exception arg1) {
			//
			//
			// }
			// });
			// //mAdKiip = null;
			// mAdKiipPoptart.cancel();
			// mAdKiipPoptart = null;
			// }
			//
			// if (mAdGsFullscreen != null) {
			// }
			// }

			// MonetizationManager.endSession();

			// if (mAdChartboost != null) {
			// mAdChartboost.onStop(this);
			// mAdChartboost.onDestroy(this);
			// mAdChartboost = null;
			// }

			if (mAdView == null) {
				return;
			}

			stopTickAdBannerSwitcher();

			if (mAdmobAdView != null) {
				mAdmobAdView.destroy();
				mAdmobAdView = null;
			}

			if (mAdmobInterstitial != null) {
				// Stop loading the ad.
				// mAdmobInterstitial.stopLoading(__CLASSNAME__, getMethodName());
			}

			if (mAdView != null && mAdView instanceof com.google.android.gms.ads.AdView) {
				((com.google.android.gms.ads.AdView) mAdView).destroy();
			}

			if (mAdamAdView != null) {
				mAdamAdView.destroy();
				mAdamAdView = null;
			}

			if (mAdView != null && mAdView instanceof net.daum.adam.publisher.AdView) {
				((net.daum.adam.publisher.AdView) mAdView).destroy();
			}

			if (mAdamInterstitial != null) {
				mAdamInterstitial = null;
			}

			// if (mAdView != null && mAdView instanceof com.fsn.cauly.CaulyAdView) {
			// ((com.fsn.cauly.CaulyAdView) mAdView).destroy();
			// }

			// if (mAdView != null && mAdView instanceof com.skplanet.tad.AdView) {
			// ((com.skplanet.tad.AdView) mAdView).stopAd();
			// ((com.skplanet.tad.AdView) mAdView).destroyAd();
			// }
			// if (mAdSKTAdView != null) {
			// this.mAdSKTAdView.destroyAd();
			// }
			// if (mAdSKTInterstitial != null) {
			// this.mAdSKTInterstitial.destroyAd();
			// }

			// if (mAdView != null && mAdView instanceof com.nbpcorp.mobilead.sdk.MobileAdView) {
			// ((com.nbpcorp.mobilead.sdk.MobileAdView) mAdView).destroy();
			// }

			// Flurry배너제거
			// if (("F1").equalsIgnoreCase(ad_vendor)) {
			// FlurryAds.removeAd(this, "F1", (ViewGroup) mAdBase);
			// } else if (("F3").equalsIgnoreCase(ad_vendor)) {
			// FlurryAds.removeAd(this, "F3", (ViewGroup) mAdBase);
			// }

			if (mAdDirectTapBanner != null) {
				com.directtap.DirectTap.Banner.dismissOverlay();
				mAdDirectTapBanner.release();
			}

			mAdView = null;
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	boolean showAdBanner(String ad_banner) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ad_banner);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + mAdView);

		try {

			// 기본:구글애드몹
			if (("AD1").equalsIgnoreCase(ad_banner)) {
			} else if (("AD2").equalsIgnoreCase(ad_banner)) {
			} else if (("AD3").equalsIgnoreCase(ad_banner)) {
			} else if (("AD4").equalsIgnoreCase(ad_banner)) {
			} else if (("G").equalsIgnoreCase(ad_banner)) {
				// 구글애드몹인경우
				addAdBarBanner(mAdmobAdView);
			} else if (("G1").equalsIgnoreCase(ad_banner)) {
				// 구글애드몹인경우
				addAdBarBanner(mAdmobAdView);
			} else if (("G2").equalsIgnoreCase(ad_banner)) {
				// 구글애드몹인경우
				addAdFullBanner(mAdmobAdView);
			} else if (("D").equalsIgnoreCase(ad_banner)) {
				// 다음아담인경우
				addAdBarBanner(mAdamAdView);
			} else if (("D1").equalsIgnoreCase(ad_banner)) {
				// 다음아담인경우
				addAdBarBanner(mAdamAdView);
			} else if (("C").equalsIgnoreCase(ad_banner)) {
				// 카울리인경우
				// addAdBarBanner(mAdCaulyAdView);
			} else if (("T").equalsIgnoreCase(ad_banner)) {
				// TAD인경우
				// addAdBarBanner(mAdSKTAdView);
				// addAdBannerForTest(mAdSKTAdView);
			} else if (("T1").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
				// addAdBarBanner(mAdSKTAdView);
				// addAdBannerForTest(mAdSKTAdView);
			} else if (("T2").equalsIgnoreCase(ad_vendor)) {
				// TAD인경우
			} else if (("I").equalsIgnoreCase(ad_banner)) {
				// IMOBI인경우
				// initAdInMobi();
				// addAdBanner(mAdInMobi);
				// } else if (("A1").equalsIgnoreCase(ad_vendor)) {
				// mAirPush.startSmartWallAd(); //start random smart wall ad.
				// //1. Dialog Ad
				// mAirPush.startDialogAd(); //start dialog ad.
				// } else if (("A2").equalsIgnoreCase(ad_vendor)) {
				// //2. AppWall Ad
				// mAirPush.startAppWall(); //start app wall.
				// } else if (("A3").equalsIgnoreCase(ad_vendor)) {
				// //3. LandingPage Ad
				// mAirPush.startLandingPageAd(); //start landing page.
			} else if (("J1").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J2").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J3").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J4").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J5").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J6").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J7").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("J8").equalsIgnoreCase(ad_banner)) {
				// 7) Tapjoy - J
				showAdBannerDefault();
			} else if (("N1").equalsIgnoreCase(ad_banner)) {
				// 8) TNK팩토리 - N
				showAdBannerDefault();
			} else if (("N2").equalsIgnoreCase(ad_banner)) {
				// 8) TNK팩토리 - N
				showAdBannerDefault();
			} else if (("P1").equalsIgnoreCase(ad_banner)) {
				// 9) 애드팝콘 - P
				showAdBannerDefault();
			} else if (("P2").equalsIgnoreCase(ad_banner)) {
				// 9) 애드팝콘 - P
				showAdBannerDefault();
			} else if (("M1").equalsIgnoreCase(ad_banner)) {
				// 10) 미탭스 - M
				showAdBannerDefault();
			} else if (("M2").equalsIgnoreCase(ad_banner)) {
				// 10) 미탭스 - M
				showAdBannerDefault();
			} else if (("TA1").equalsIgnoreCase(ad_banner)) {
				// 16) TAPAD - TA -> "TA3"
				showAdBannerDefault();
			} else if (("TA2").equalsIgnoreCase(ad_banner)) {
				// 16) TAPAD - TA -> "TA3"
				showAdBannerDefault();
			} else if (("TA3").equalsIgnoreCase(ad_banner)) {
				// 16) TAPAD - TA -> "TA3"
			} else if (("CB1").equalsIgnoreCase(ad_banner)) {
				// ChartBoost
				showAdBannerDefault();
			} else if (("CB2").equalsIgnoreCase(ad_banner)) {
				// ChartBoost
				showAdBannerDefault();
			} else if (("AC1").equalsIgnoreCase(ad_banner)) {
				// AdColony
				showAdBannerDefault();
			} else if (("AC2").equalsIgnoreCase(ad_banner)) {
				// AdColony
				showAdBannerDefault();
			} else if (("K1").equalsIgnoreCase(ad_banner)) {
				// kiip
				showAdBannerDefault();
			} else if (("K2").equalsIgnoreCase(ad_banner)) {
				// kiip
				showAdBannerDefault();
			} else if (("W1").equalsIgnoreCase(ad_banner)) {
				// W3i
				showAdBannerDefault();
			} else if (("W2").equalsIgnoreCase(ad_banner)) {
				// W3i
				showAdBannerDefault();
			} else if (("PH").equalsIgnoreCase(ad_banner)) {
				// PlayHaven
				showAdBannerDefault();
			} else if (("F1").equalsIgnoreCase(ad_banner)) {
				// 17) Flurry
				// showAdBannerFlurry("F1");
				// showBannerDefault();
			} else if (("F2").equalsIgnoreCase(ad_banner)) {
				// 17) Flurry
				// showAdBannerFlurry("F1");
				// showBannerDefault();
			} else if (("F3").equalsIgnoreCase(ad_banner)) {
				// 17) Flurry
				// showAdBannerFlurry("F1");
				// showBannerDefault();
			} else if (("F4").equalsIgnoreCase(ad_banner)) {
				// 17) Flurry
				// showAdBannerFlurry("F1");
				// showBannerDefault();
			} else if (("F5").equalsIgnoreCase(ad_banner)) {
				// 17) Flurry
				// showAdBannerFlurry("F1");
				// showBannerDefault();
			} else if (("NA1").equalsIgnoreCase(ad_banner)) {
				// 네이버애드포스트
				// addAdBarBanner(mAdNaverAdView);
			} else if (("GS1").equalsIgnoreCase(ad_banner)) {
				// Greystripe - 1. 배너광고(바) - 호환오류
				// addAdBarBanner((View) mAdGreyStripeAdView);
			} else if (("GS2").equalsIgnoreCase(ad_banner)) {
				// Greystripe - 2. 배너광고(풀) - 호환오류
				// addAdBarBanner((View) mAdGreyStripeAdView);
			} else if (("GS3").equalsIgnoreCase(ad_banner)) {
				// Greystripe - 3. 팝업광고 - JPOP확인요
			} else if (("GS4").equalsIgnoreCase(ad_banner)) {
				// Greystripe - 4. 팝업충전(이용권/보상광고) - 보류
			} else if (("S1").equalsIgnoreCase(ad_banner)) {
				// SponsorPay
				showAdBannerDefault();
			} else if (("S2").equalsIgnoreCase(ad_banner)) {
				// SponsorPay
				showAdBannerDefault();
			} else if (("S3").equalsIgnoreCase(ad_banner)) {
				// SponsorPay
				showAdBannerDefault();
			} else if (("S4").equalsIgnoreCase(ad_banner)) {
				// SponsorPay
				showAdBannerDefault();
			} else if (("V1").equalsIgnoreCase(ad_banner)) {
				// Vungle
				showAdBannerDefault();
			} else if (("V2").equalsIgnoreCase(ad_banner)) {
				// Vungle
				showAdBannerDefault();
			} else if (("DT1").equalsIgnoreCase(ad_banner)) {
				// DirectTap
				showAdBannerDirectTap();
			} else if (("SA1").equalsIgnoreCase(ad_banner)) {
				// StartApp
				// showAdBannerStartApp();
			} else {
				showAdBannerDefault();
			}

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	protected void removeAdBanner() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {

			if (mAdBase == null) {
				mAdBase = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adbase);
			}

			if (mAdBase != null && mAdView != null) {
				RelativeLayout.LayoutParams rparams = (RelativeLayout.LayoutParams) mAdBase.getLayoutParams();
				rparams.height = mAdView.getHeight();
				if (rparams.height == 0) {
					rparams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
				}
				mAdBase.removeAllViews();
				mAdBase.setLayoutParams(rparams);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * 화면에풀뷰광고삽입
	 * </pre>
	 */
	protected void addAdFullBanner(View view) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ad_banner);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + mAdView);

		removeAdBanner();

		View v = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adbase);
		if (v != null) {
			v.setVisibility(View.GONE);
		}

		mAdBase = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adview);
		// if (_IKaraoke.DEBUG) {
		// mAdBase.setBackgroundColor(getResources().getColor(R.color.solid_red));
		// }

		mAdBase.removeAllViews();
		addAdBanner(mAdBase, view);

	}

	/**
	 * <pre>
	 * 화면에바뷰광고삽입
	 * </pre>
	 */
	protected void addAdBarBanner(View view) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ad_banner);
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + mAdView);

		try {
			removeAdBanner();

			View v = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adview);
			if (v != null) {
				v.setVisibility(View.GONE);
			}

			mAdBase = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adbase);
			// if (_IKaraoke.DEBUG) {
			// mAdBase.setBackgroundColor(getResources().getColor(R.color.solid_red));
			// }

			if (mAdBase != null) {
				mAdBase.removeAllViews();
				addAdBanner(mAdBase, view);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * <pre>
	 * 레이어변경없이광고삽입(테스트용)
	 * </pre>
	 */
	@Deprecated
	void addAdBannerForTest(View view) {
		android.util.Log.e("Ads", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + mAdBase + "\n" + view);

		removeAdBanner();

		// if (mAdBase == null) {
		// mAdBase = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adbanner);
		// }
		// mAdBase.addView(view);

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.adbase);
		if (layout != null) {
			layout.removeView(view);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layout.addView(view, params);
		}
	}

	/**
	 * <pre>
	 * 레이어변경하고광고삽입
	 * </pre>
	 */
	private void addAdBanner(View base, View view) {

		mAdBase = (ViewGroup) base;

		android.util.Log.d("Ads", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + mAdBase + "\n" + view);

		try {

			if (mAdBase == null) {
				mAdBase = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adbase);
			}

			mAdView = view;

			if (view == null) {
				return;
			}

			removeAdBanner();

			mAdView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {

					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

				}
			});

			// 배너레이어정리
			mAdBase.addView(mAdView);

			RelativeLayout.LayoutParams params = null;

			// 배너크기/정렬변경
			if (mAdView != null) {
				params = (RelativeLayout.LayoutParams) mAdView.getLayoutParams();
				if (view instanceof net.daum.adam.publisher.AdView) {
					// 다음아담인경우 리사이즈 해야한다.
					// 광고 높이를 48dip로 설정
					params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) 48.0f, getResources().getDisplayMetrics());
					// } else if (view instanceof com.greystripe.sdk.GSMobileBannerAdView) {
					// //Greystripe인경우 리사이즈 해야한다.
					// params.height = (int) Util.dp2px(getApp(), 50);
					// } else if (view instanceof com.greystripe.sdk.GSLeaderboardAdView) {
					// //Greystripe인경우 리사이즈 해야한다.
					// params.height = (int) Util.dp2px(getApp(), 90);
					// } else if (view instanceof com.nbpcorp.mobilead.sdk.MobileAdView) {
					// //네이버인경우 리사이즈 해야한다.
					// params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
					// params.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
				} else if (view instanceof com.directtap.DirectTapBanner) {
				} else {
					params.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
					params.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
				}
				params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				params.addRule(RelativeLayout.CENTER_HORIZONTAL);
				mAdView.setLayoutParams(params);
			}

			// //배너위치변경
			FrameLayout fragment = (FrameLayout) getWindow().getDecorView().findViewById(R.id.fragment1);
			if (fragment != null) {
				params = (RelativeLayout.LayoutParams) fragment.getLayoutParams();
				params.addRule(RelativeLayout.ABOVE, R.id.adbase);
				// if (view instanceof com.greystripe.sdk.GSMediumRectangleAdView) {
				// //Greystripe인경우 위치변경 해야한다.
				// params.addRule(RelativeLayout.ABOVE, 0);
				// }
				fragment.setLayoutParams(params);
			}

			((RelativeLayout) mAdBase).setGravity(Gravity.TOP);
			((RelativeLayout) mAdBase).setGravity(Gravity.CENTER);
			params = (android.widget.RelativeLayout.LayoutParams) mAdBase.getLayoutParams();
			params.width = android.widget.RelativeLayout.LayoutParams.MATCH_PARENT;
			params.height = android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT;
			mAdBase.setLayoutParams(params);

			params = (android.widget.RelativeLayout.LayoutParams) mAdView.getLayoutParams();
			params.width = android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT;
			params.height = android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT;
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			mAdView.setLayoutParams(params);

			// 일단가리고콜백대기후배너전달시보임.
			showAdBanner(false);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void showAdBanner(boolean show) {
		try {
			if (mAdBase == null) {
				mAdBase = (ViewGroup) getWindow().getDecorView().findViewById(R.id.adbase);
			}

			if (mAdBase == null) {
				return;
			}

			if (show) {
				// mAdView.setVisibility(View.VISIBLE);
				mAdBase.setVisibility(View.VISIBLE);
			} else {
				// mAdView.setVisibility(View.GONE);
				mAdBase.setVisibility(View.GONE);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// ViewSwitcher mViewSwitcher = null;
	protected Timer mTickAdMobSwitcher = null;

	@Deprecated
	void startTickAdBannerSwitcher() {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		if (mTickAdMobSwitcher == null) {
			mTickAdMobSwitcher = new Timer();
			mTickAdMobSwitcher.schedule(new switchAdBannerSwitcher(), 0, _IKaraoke.TIMER_ADMOB);
		}
	}

	@Deprecated
	class switchAdBannerSwitcher extends TimerTask {
		public void run() {
			post(switchAdBannerSwitcher);
		}
	}

	@Deprecated
	TimerTask switchAdBannerSwitcher = new TimerTask() {
		public void run() {
			// if (mAdBanner != null && mAdBanner instanceof com.google.android.gms.ads.AdView) {
			// ((com.google.android.gms.ads.AdView) mAdBanner).loadAd(new AdRequest.Builder().build());
			// }
		}
	};

	@Deprecated
	void stopTickAdBannerSwitcher() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		if (mTickAdMobSwitcher != null) {
			mTickAdMobSwitcher.cancel();
			mTickAdMobSwitcher.purge();
			mTickAdMobSwitcher = null;
		}
	}

	/**
	 * <pre>
	 *
	 * </pre>
	 *
	 * @param fullsize : 풀배너여부
	 */
	private void initAdMobBanner(boolean fullsize) {

		try {
			String APP_ID = _IKaraoke.ADMOB_ID_BANNER;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_BANNER_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_BANNER_JAP;
			} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_BANNER_NAVER;
			} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_BANNER_S5;
			}

			android.util.Log.d("Ads - Admob - BANNER", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + this.getClass().getName());
			if (_IKaraoke.DEBUG)
				Log2.i(
						__CLASSNAME__,
						getMethodName() + "Play Services Version id: " + GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE + ":"
								+ +getResources().getInteger(R.integer.google_play_services_version));

			// Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
			mAdmobAdView = new com.google.android.gms.ads.AdView(this);
			mAdmobAdView.setAdUnitId(APP_ID);

			if (fullsize) {
				mAdmobAdView.setAdSize(com.google.android.gms.ads.AdSize.FULL_BANNER);
			} else {
				mAdmobAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
			}

			mAdmobAdView.setAdListener(new AdListener() {

				@Override
				public void onAdClosed() {
					android.util.Log.w("Ads - Admob - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdClosed();
				}

				@Override
				public void onAdFailedToLoad(int errorCode) {
					android.util.Log.w("Ads - Admob - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName() + errorCode);

					super.onAdFailedToLoad(errorCode);
				}

				@Override
				public void onAdLeftApplication() {
					android.util.Log.w("Ads - Admob - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdLeftApplication();
				}

				@Override
				public void onAdLoaded() {
					android.util.Log.w("Ads - Admob - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdLoaded();

					showAdBanner(true);
				}

				@Override
				public void onAdOpened() {
					android.util.Log.w("Ads - Admob - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdOpened();
				}

			});

			// Create an ad request.
			AdRequest adRequest = new AdRequest.Builder().addTestDevice(ADMOB_TEST_DEVICE_ID).build();
			boolean isTestDevice = adRequest.isTestDevice(getApp());
			if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + "test:" + isTestDevice);

			// Start loading the ad in the background.
			mAdmobAdView.loadAd(adRequest);
		} catch (Exception e) {

			// e.printStackTrace();
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "[NG]" + getMethodName() + getPackageName() + ":" + ADMOB_ID);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, Log2.getStackTraceString(e));
		}
	}

	private void initAdMobInterstitial() {

		try {
			String APP_ID = _IKaraoke.ADMOB_ID_POPUP;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_POPUP_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_POPUP_JAP;
			} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_POPUP_NAVER;
			} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADMOB_ID_POPUP_S5;
			}

			android.util.Log.d("Ads - Admob - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName() + this.getClass().getName());
			if (_IKaraoke.DEBUG)
				Log2.i(
						__CLASSNAME__,
						getMethodName() + "Play Services Version id: " + GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE + ":"
								+ +getResources().getInteger(R.integer.google_play_services_version));

			// Create an ad.
			mAdmobInterstitial = new com.google.android.gms.ads.InterstitialAd(this);
			mAdmobInterstitial.setAdUnitId(APP_ID);

			mAdmobInterstitial.setAdListener(new AdListener() {

				@Override
				public void onAdClosed() {
					android.util.Log.w("Ads - Admob - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdClosed();

					finish();
				}

				@Override
				public void onAdFailedToLoad(int errorCode) {
					android.util.Log.w("Ads - Admob - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName() + errorCode);

					super.onAdFailedToLoad(errorCode);
					isShowAdPopup = false;
				}

				@Override
				public void onAdLeftApplication() {
					android.util.Log.w("Ads - Admob - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdLeftApplication();
				}

				@Override
				public void onAdLoaded() {
					android.util.Log.w("Ads - Admob - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdLoaded();
					isShowAdPopup = true;
				}

				@Override
				public void onAdOpened() {
					android.util.Log.w("Ads - Admob - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());

					super.onAdOpened();
				}

			});

			// Create an ad request.
			// Optionally populate the ad request builder.
			AdRequest adRequest = (new AdRequest.Builder()).addTestDevice(ADMOB_TEST_DEVICE_ID).build();
			boolean isTestDevice = adRequest.isTestDevice(getApp());
			if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + "test:" + isTestDevice);

			// Start loading the ad in the background.
			mAdmobInterstitial.loadAd(adRequest);
		} catch (Exception e) {

			// e.printStackTrace();
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "[NG]" + getMethodName() + getPackageName() + ":" + ADMOB_ID);
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, Log2.getStackTraceString(e));
		}

	}

	public void showAdPopupAdmob() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
		try {
			if (mAdmobInterstitial.isLoaded()) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mAdmobInterstitial.isLoaded());
				mAdmobInterstitial.show();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 */
	private void initAdamBanner() {

		try {

			String APP_ID = _IKaraoke.ADAM_ID_BANNER;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADAM_ID_BANNER_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADAM_ID_BANNER_JAP;
				// } else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
				// APP_ID = _IKaraoke.ADAM_ID_BANNER_NAVER;
				// } else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
				// APP_ID = _IKaraoke.ADAM_ID_BANNER_S5;
			}

			android.util.Log.d("Ads - Ad@m - BANNER", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

			if (mAdamAdView != null) {
				// return;
				mAdamAdView.destroy();
				mAdamAdView = null;
			}

			// Ad@m 광고 뷰 생성 및 설정
			mAdamAdView = new net.daum.adam.publisher.AdView(this);

			// 할당 받은 clientId 설정
			mAdamAdView.setClientId(APP_ID);

			// 광고 내려받기 실패했을 경우에 실행할 리스너
			mAdamAdView.setOnAdFailedListener(new OnAdFailedListener() {
				@Override
				public void OnAdFailed(AdError error, String errorMessage) {
					android.util.Log.e("Ads - Ad@m - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName() + error);
				}
			});

			// 광고를 정상적으로 내려받았을 경우에 실행할 리스너
			mAdamAdView.setOnAdLoadedListener(new OnAdLoadedListener() {

				@Override
				public void OnAdLoaded() {
					android.util.Log.w("Ads - Ad@m - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());

					showAdBanner(true);
				}
			});

			// 광고를 불러올때 실행할 리스너
			mAdamAdView.setOnAdWillLoadListener(new OnAdWillLoadListener() {

				@Override
				public void OnAdWillLoad(String arg1) {
					android.util.Log.w("Ads - Ad@m - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName() + arg1);
				}
			});

			// 광고를 닫았을때 실행할 리스너
			mAdamAdView.setOnAdClosedListener(new OnAdClosedListener() {

				@Override
				public void OnAdClosed() {
					android.util.Log.w("Ads - Ad@m - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
				}
			});

			// 광고 갱신 시간 : 기본 60초
			// mAdamAdView.setRequestInterval(12);

			// Animation 효과 : 기본 값은 AnimationType.NONE
			mAdamAdView.setAnimationType(AnimationType.FLIP_HORIZONTAL);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initAdamInterstitial() {
		try {
			String APP_ID = _IKaraoke.ADAM_ID_POPUP;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADAM_ID_POPUP_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.ADAM_ID_POPUP_JAP;
				// } else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
				// APP_ID = ADAM_ID_POPUP_NAVER;
				// } else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
				// APP_ID = ADAM_ID_POPUP_S5;
			}

			android.util.Log.d("Ads - Ad@m - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

			// 1. 전면형 광고 객체 생성
			mAdamInterstitial = new net.daum.adam.publisher.AdInterstitial(this);

			// 2. 전면형 광고 클라이언트 ID를 설정한다.
			mAdamInterstitial.setClientId(APP_ID);

			// 3. (선택)전면형 광고 다운로드시에 실행할 리스너
			mAdamInterstitial.setOnAdLoadedListener(new OnAdLoadedListener() {
				@Override
				public void OnAdLoaded() {
					android.util.Log.w("Ads - Ad@m - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
				}
			});

			// 4. (선택)전면형 광고 다운로드 실패시에 실행할 리스너
			mAdamInterstitial.setOnAdFailedListener(new OnAdFailedListener() {
				@Override
				public void OnAdFailed(AdError error, String errorMessage) {
					android.util.Log.e("Ads - Ad@m - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName() + error);
					showAdPopupAdmob();
				}
			});

			// 5. (선택)전면형 광고를 닫을 시에 실행할 리스너
			mAdamInterstitial.setOnAdClosedListener(new OnAdClosedListener() {
				@Override
				public void OnAdClosed() {
					android.util.Log.w("Ads - Ad@m - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
					finish();
				}
			});

			isShowAdPopup = mAdamInterstitial.isReady();

			initAdMobInterstitial();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 2. Featured Ads(전면 광고)
	public void showAdPopupAdam() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
		try {
			android.util.Log.w("Ads - Ad@m - POPUP", __CLASSNAME__ + getMethodName() + mAdamInterstitial.isReady());
			// 6. 전면형 광고를 불러온다.
			mAdamInterstitial.loadAd();
		} catch (Exception e) {
			android.util.Log.e("Ads - Ad@m - POPUP", __CLASSNAME__ + getMethodName());

			e.printStackTrace();
			close();
		}
	}

	/**
	 */
	// private void initAdCauly() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// //if (mAdCaulyAdView != null) {
	// // return;
	// //}
	//
	// // Cauly 로그 수준 지정 : 로그의 상세함 순서는 다음과 같다.
	// // LogLevel.Info > LogLevel.Warn > LogLevel.Error > LogLevel.None
	// if (_IKaraoke.DEBUG) {
	// Logger.setLogLevel(LogLevel.Debug);
	// }
	//
	// //데이터 채우기
	// //CaulyAdInfo info = new CaulyAdInfo(null);
	// //info.initData(CAULY_ID, "cpc", "all", "all", "off", "default", "yes", this.ad_time, true);
	// // 상세 설정 항목들은 하단 표 참조
	// // 설정하지 않은 항목들은 기본값으로 설정됨
	//
	// /**
	// * Appcode APP 등록 후 부여 받은 APP CODE[발급 ID] 입력 샘플 appcode 배너광고 : CAULY, CAULY-RICHADTEST,
	// * CAULY-PETEST, CAULY-3DTEST 전면광고 : CAULY, CAULY-VIDEOTEST
	// *
	// * Gps() auto | off(기본값)
	// *
	// * Effect() LeftSlide(기본값) : 왼쪽에서 오른쪽으로 슬라이드 RightSlide : 오른쪽에서 왼쪽으로 슬라이드 TopSlide : 위에서 아래로
	// * 슬라이드 BottomSlide : 아래서 위로 슬라이드 FadeIn : 전에 있던 광고가 서서히 사라지는 효과 Circle : 한 바퀴 롤링 None : 애니메이션
	// * 효과 없이 바로 광고 교체
	// *
	// * allowcall() true(기본값) | false
	// *
	// * reloadInterval() min : 15초(기본값) max : 120 초
	// *
	// * dynamicReloadInterval() true(기본값) | false true : 광고에 따라 노출 주기 조정할 수 있도록 하여 광고 수익 상승 효과 기대
	// * false : reloadInterval 설정 값으로 Rolling
	// *
	// * bannerHeight() [Proportional(디바이스 긴방향 해상도의 10%) | Fixed (기본값. 48dp)]
	// *
	// * threadPriority() 스레드 우선 순위 지정 : 1~10(기본값 : 5)
	// */
	// CaulyAdInfo adInfo = new CaulyAdInfoBuilder(CAULY_ID).effect("None")
	// .bannerHeight("Proportional").build();
	//
	// //[adtype : cpc] 3D AD : CAULY-3DTEST, Rich AD : CAULY-RICHADTEST
	// //광고 View
	// //mAdCauly = new AdView(this);
	// // CaulyAdInfo를 이용, CaulyAdView 생성.
	// mAdCaulyAdView = new CaulyAdView(this);
	// mAdCaulyAdView.setAdInfo(adInfo);
	//
	// mAdCaulyAdView.setAdViewListener(new CaulyAdViewListener() {
	//
	// @Override
	// public void onCloseLandingScreen(CaulyAdView adView) {
	//
	// android.util.Log.w("Ads - Cauly", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onFailedToReceiveAd(CaulyAdView adView, int errorCode, String errorMsg) {
	//
	// android.util.Log.w("Ads - Cauly", __CLASSNAME__ + ":initAds:" + getMethodName());
	// android.util.Log.w("Ads - Cauly", "initAds:" + adView.toString());
	// android.util.Log.w("Ads - Cauly", "initAds:" + errorCode + ":" + errorMsg);
	//
	// }
	//
	// @Override
	// public void onReceiveAd(CaulyAdView adView, boolean isChargeableAd) {
	//
	// android.util.Log.w("Ads - Cauly", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// showAdBanner(true);
	// }
	//
	// @Override
	// public void onShowLandingScreen(CaulyAdView adView) {
	//
	// android.util.Log.w("Ads - Cauly", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// });
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// private void initAdSKTBanner() {
	// boolean test = false;
	//
	// String SKTAD_ID = SKTAD_ID_BANNER_KOR;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// SKTAD_ID = SKTAD_ID_BANNER_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// SKTAD_ID = SKTAD_ID_BANNER_JAP;
	// }
	//
	// android.util.Log.d("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + SKTAD_ID);
	//
	// try {
	// //if (mAdSKTAdView != null) {
	// // return;
	// //}
	//
	// // Context 를 parameter 로 AdView 의 객체를 생성합니다.
	// mAdSKTAdView = new com.skplanet.tad.AdView(this);
	//
	// //AXT002001 표준 띠형 (in-line)배너
	// //AXT003001 표준 삽입형 (interstitial) 배너
	// //AXT003002 표준 삽입형 (interstitial) 배너
	// //AX0004345 플로팅
	// SKTAD_ID = "AXT002001";
	// test = true;
	//
	// // 준비 과정에 발급받은 ClientId 를 직접 입력합니다. Ex) AdView.setClientId("AX0000123");
	// mAdSKTAdView.setClientId(SKTAD_ID);
	// // TestMode 를 정합니다. true 인경우 test 광고가 수신됩니다.
	// mAdSKTAdView.setTestMode(test);
	//
	// // 원하는 크기의 Slot 을 설정합니다
	// mAdSKTAdView.setSlotNo(com.skplanet.tad.AdView.Slot.BANNER);
	//
	// // 새로운 광고를 요청하는 주기를 입력합니다. 최소값은 15, 최대값은 60 입니다
	// //mAdSKTAdView.setRefreshInterval(15);
	//
	// // 광고 View 의 Background 의 사용 유무를 설정합니다.
	// //mAdSKTAdView.setUseBackFill(false);
	//
	// // 새로운 받은 광고가 Display 되는 Animation 효과를 설정합니다.
	// //mAdSKTAdView.setAnimationType(com.skplanet.tad.AdView.AnimationType.SLIDE_FROM_TOP_TO_BOTTOM);
	//
	// // AdView 진행 상태를 알 수 있도록 Listener를 등록합니다.
	// mAdSKTAdView.setListener(new com.skplanet.tad.AdListener() {
	//
	// @Override
	// public void onAdClicked() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdExpandClosed() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdExpanded() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdFailed(ErrorCode errorCode) {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + errorCode);
	//
	// }
	//
	// @Override
	// public void onAdLoaded() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdReceived() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// showAdBanner(true);
	// }
	//
	// @Override
	// public void onAdResizeClosed() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdResized() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdWillLoad() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdWillReceive() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdDismissScreen() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdLeaveApplication() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdPresentScreen() {
	//
	// android.util.Log.w("Ads - SKTad - BANNER", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// });
	//
	// // 광고를 요청합니다.
	// mAdSKTAdView.loadAd();
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

	// private void initAdSKTInterstitial() {
	// boolean test = false;
	//
	// String SKTAD_ID = SKTAD_ID_POPUP_KOR;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// SKTAD_ID = SKTAD_ID_POPUP_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// SKTAD_ID = SKTAD_ID_POPUP_JAP;
	// }
	//
	// android.util.Log.d("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + SKTAD_ID);
	//
	// try {
	// //if (mAdSKTAdView != null) {
	// // return;
	// //}
	//
	// // Context 를 parameter 로 AdView 의 객체를 생성합니다.
	// mAdSKTInterstitial = new com.skplanet.tad.AdInterstitial(this);
	//
	// //AXT002001 표준 띠형 (in-line)배너
	// //AXT003001 표준 삽입형 (interstitial) 배너
	// //AXT003002 표준 삽입형 (interstitial) 배너
	// //AX0004345 플로팅
	// SKTAD_ID = "AXT003002";
	// test = true;
	//
	// // 준비 과정에 발급받은 ClientId 를 직접 입력합니다. Ex) AdView.setClientId("AX0000123");
	// mAdSKTInterstitial.setClientId(SKTAD_ID);
	// // TestMode 를 정합니다. true 인경우 test 광고가 수신됩니다.
	// mAdSKTInterstitial.setTestMode(test);
	//
	// // 원하는 크기의 Slot 을 설정합니다. Ex) AdInterstitial.setSlotNo(Slot.INTERSTITIAL);
	// mAdSKTInterstitial.setSlotNo(com.skplanet.tad.AdView.Slot.INTERSTITIAL);
	//
	// // 랜딩시 자동 닫기
	// this.mAdSKTInterstitial.setAutoCloseAfterLeaveApplication(false);
	//
	// // 5초후 자동 닫기
	// this.mAdSKTInterstitial.setAutoCloseWhenNoInteraction(false);
	//
	// // AdView 진행 상태를 알 수 있도록 Listener를 등록합니다.
	// mAdSKTInterstitial.setListener(new com.skplanet.tad.AdInterstitialListener() {
	//
	// @Override
	// public void onAdFailed(ErrorCode errorCode) {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + errorCode);
	//
	// initAdPopupDefault();
	// }
	//
	// @Override
	// public void onAdLoaded() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdReceived() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// isShowAdPopup = true;
	// }
	//
	// @Override
	// public void onAdWillLoad() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdWillReceive() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdDismissScreen() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// finish();
	// }
	//
	// @Override
	// public void onAdLeaveApplication() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdPresentScreen() {
	//
	// android.util.Log.w("Ads - SKTad - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// });
	//
	// // 광고를 요청합니다.
	// mAdSKTInterstitial.loadAd();
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

	// private void initAdInMobi() {
	// // if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	// //
	// // try {
	// // if (mAdInMobi != null) {
	// // return;
	// // }
	// //
	// // int adSlot = com.inmobi.androidsdk.IMAdView.INMOBI_AD_UNIT_320X50;
	// //
	// // float widthDp = Util.getHeightDp(this);
	// // if (widthDp >= 600.0f) {
	// // adSlot = com.inmobi.androidsdk.IMAdView.INMOBI_AD_UNIT_728X90;
	// // }
	// //
	// // mAdInMobi = new com.inmobi.androidsdk.IMAdView(this, adSlot, INMOBI_ID);
	// //
	// // mAdInMobi.setIMAdListener(new IMAdListener() {
	// //
	// // @Override
	// // public void onShowAdScreen(IMAdView imadview) {
	// //
	// // android.util.Log.w("Ads - InMobi", __CLASSNAME__ + ":initAds:" + getMethodName());
	// //
	// // }
	// //
	// // @Override
	// // public void onLeaveApplication(IMAdView imadview) {
	// //
	// // android.util.Log.w("Ads - InMobi", __CLASSNAME__ + ":initAds:" + getMethodName());
	// //
	// // }
	// //
	// // @Override
	// // public void onDismissAdScreen(IMAdView imadview) {
	// //
	// // android.util.Log.w("Ads - InMobi", __CLASSNAME__ + ":initAds:" + getMethodName());
	// //
	// // }
	// //
	// // @Override
	// // public void onAdRequestFailed(IMAdView imadview,
	// // com.inmobi.androidsdk.IMAdRequest.ErrorCode errorcode) {
	// //
	// // android.util.Log.w("Ads - InMobi", __CLASSNAME__ + ":initAds:" + getMethodName());
	// //
	// // }
	// //
	// // @Override
	// // public void onAdRequestCompleted(IMAdView imadview) {
	// //
	// // android.util.Log.w("Ads - InMobi", __CLASSNAME__ + ":initAds:" + getMethodName());
	// //
	// // }
	// // });
	// // } catch (Exception e) {
	// //
	// // e.printStackTrace();
	// // }
	// }

	/**
	 */
	protected void initAdTapjoyReward() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);

		// if (mAdTapjoy != null) {
		// return;
		// }

		try {
			// Enables logging to the console.
			if (_IKaraoke.DEBUG) {
				TapjoyLog.enableLogging(true);
			} else {
				TapjoyLog.enableLogging(false);
			}

			// OPTIONAL: For custom startup flags.
			Hashtable<String, String> flags = new Hashtable<String, String>();
			// flags.put(TapjoyConnectFlag.DISABLE_VIDEO_OFFERS, "true");

			// Connect with the Tapjoy server. Call this when the application first starts.
			// REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
			// REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
			String APP_ID = _IKaraoke.TAPJOY_APP_ID;
			String APP_KEY = _IKaraoke.TAPJOY_APP_KEY;
			String SDK_KEY = _IKaraoke.TAPJOY_SDK_KEY;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.TAPJOY_APP_ID_KOR;
				APP_KEY = _IKaraoke.TAPJOY_APP_KEY_KOR;
				SDK_KEY = _IKaraoke.TAPJOY_SDK_KEY_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.TAPJOY_APP_ID_JAP;
				APP_KEY = _IKaraoke.TAPJOY_APP_KEY_JAP;
				SDK_KEY = _IKaraoke.TAPJOY_SDK_KEY_JAP;
			}

			android.util.Log.d("Ads - Tapjoy - REWARD", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + ":" + APP_ID + ":" + SDK_KEY);

			TapjoyConnect.requestTapjoyConnect(getApp(), APP_ID, APP_KEY, flags);

			// For PAID APPS ONLY. Replace your Paid App Pay-Per-Action ID as the parameter.
			// TapjoyConnect.getTapjoyConnectInstance(this).enablePaidAppWithActionID("ENTER_YOUR_PAID_APP_ACTION_ID_HERE");

			// No longer required. Videos are initialized automatically.
			// Use initVideoAd to receive TapjoyVideoNotifier callbacks or to start manual caching.
			// TapjoyConnect.getTapjoyConnectInstance().initVideoAd(this);
			// TapjoyConnect.getTapjoyConnectInstance().setVideoCacheCount(5);

			// Set our earned points notifier to this class.
			TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier() {

				@Override
				public void earnedTapPoints(int amount) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "amount: " + amount);

					// We must use a handler since we cannot update UI elements from a different thread.
					post(mUpdateResults);
				}

			});

			// 사용자 ID
			// 기본적으로, 위에 언급한 snuid는 UDID (또는 iOS의 Mac Address)를 리턴합니다.
			// 그러나, 어플리케이션이 사용자 id를 사용하고 있을 시 UDID 대신에 사용자 id를 통해 트래킹하려면 다음 메서드를 호출할 수 있습니다.
			// 안드로이드 이용 안내
			TapjoyConnect.getTapjoyConnectInstance().setUserID(getApp().p_uid);

			// mAdTapjoy = TapjoyConnect.getTapjoyConnectInstance();

			// Deprecated. Deprecated since version 10.0.0.
			// Tapjoy ad units now use TJEvent.
			// Retrieves the Full Screen Ad data from the server.
			// Data is returned to the callback
			// TapjoyConnect.getTapjoyConnectInstance().getFullScreenAd(new TapjoyFullScreenAdNotifier() {
			// @Override
			// public void getFullScreenAdResponseFailed(int error) {
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + error);
			// android.util.Log.w("Ads - Tapjoy - reward", __CLASSNAME__ + ":initAds:" + getMethodName()
			// + error);
			//
			// initAdPopupDefault();
			// }
			//
			// @Override
			// public void getFullScreenAdResponse() {
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
			// android.util.Log
			// .w("Ads - Tapjoy - reward", __CLASSNAME__ + ":initAds:" + getMethodName());
			// }
			// });
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void initAdTapjoyNoReward() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);

		// if (mAdTapjoy != null) {
		// return;
		// }

		try {
			// Enables logging to the console.
			if (_IKaraoke.DEBUG) {
				TapjoyLog.enableLogging(true);
			} else {
				TapjoyLog.enableLogging(false);
			}

			// OPTIONAL: For custom startup flags.
			Hashtable<String, String> flags = new Hashtable<String, String>();
			// flags.put(TapjoyConnectFlag.DISABLE_VIDEO_OFFERS, "true");

			// Connect with the Tapjoy server. Call this when the application first starts.
			// REPLACE THE APP ID WITH YOUR TAPJOY APP ID.
			// REPLACE THE SECRET KEY WITH YOUR SECRET KEY.
			String ID = _IKaraoke.TAPJOY_APP_ID;
			String KEY = _IKaraoke.TAPJOY_APP_KEY;
			String NOR = _IKaraoke.TAPJOY_NOR_ID;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				ID = _IKaraoke.TAPJOY_APP_ID_KOR;
				KEY = _IKaraoke.TAPJOY_APP_KEY_KOR;
				NOR = _IKaraoke.TAPJOY_NOR_ID_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				ID = _IKaraoke.TAPJOY_APP_ID_JAP;
				KEY = _IKaraoke.TAPJOY_APP_KEY_JAP;
				NOR = _IKaraoke.TAPJOY_NOR_ID_JAP;
			}

			android.util.Log.e("Ads - Tapjoy - noreward", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + ID + ":" + NOR);

			TapjoyConnect.requestTapjoyConnect(getApp(), ID, KEY, flags);

			// For PAID APPS ONLY. Replace your Paid App Pay-Per-Action ID as the parameter.
			// TapjoyConnect.getTapjoyConnectInstance(this).enablePaidAppWithActionID("ENTER_YOUR_PAID_APP_ACTION_ID_HERE");

			// No longer required. Videos are initialized automatically.
			// Use initVideoAd to receive TapjoyVideoNotifier callbacks or to start manual caching.
			// TapjoyConnect.getTapjoyConnectInstance().initVideoAd(this);
			// TapjoyConnect.getTapjoyConnectInstance().setVideoCacheCount(5);

			// Set our earned points notifier to this class.
			TapjoyConnect.getTapjoyConnectInstance().setEarnedPointsNotifier(new TapjoyEarnedPointsNotifier() {

				@Override
				public void earnedTapPoints(int amount) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "amount: " + amount);

					// We must use a handler since we cannot update UI elements from a different thread.
					post(mUpdateResults);
				}

			});

			// 사용자 ID
			// 기본적으로, 위에 언급한 snuid는 UDID (또는 iOS의 Mac Address)를 리턴합니다.
			// 그러나, 어플리케이션이 사용자 id를 사용하고 있을 시 UDID 대신에 사용자 id를 통해 트래킹하려면 다음 메서드를 호출할 수 있습니다.
			// 안드로이드 이용 안내
			TapjoyConnect.getTapjoyConnectInstance().setUserID(getApp().p_uid);

			// mAdTapjoy = TapjoyConnect.getTapjoyConnectInstance();

			// Deprecated. Deprecated since version 10.0.0. Tapjoy ad units now use TJEvent.
			// Retrieves the Full Screen Ad data from the server.
			// Data is returned to the callback TapjoyFullScreenAdNotifier.
			// This should only be used if the application supports multiple currencies and is NON-MANAGED by Tapjoy.
			// TapjoyConnect.getTapjoyConnectInstance().getFullScreenAdWithCurrencyID(NOR,
			// new TapjoyFullScreenAdNotifier() {
			// @Override
			// public void getFullScreenAdResponseFailed(int error) {
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + error);
			// android.util.Log.w("Ads - Tapjoy - noreward", "initAds:" + __CLASSNAME__
			// + getMethodName() + error);
			//
			// initAdPopupDefault();
			// }
			//
			// @Override
			// public void getFullScreenAdResponse() {
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
			// android.util.Log.w("Ads - Tapjoy - noreward", "initAds:" + __CLASSNAME__
			// + getMethodName());
			// }
			// });
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	//public void spendAdTapjoyPoint(int amount) {
	//	if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	//
	//	try {
	//		Tapjoy.spendTapPoints(amount, new TapjoySpendPointsNotifier() {
	//
	//			@Override
	//			// Notifier for when spending virtual currency succeeds.
	//			public void getSpendPointsResponse(String currencyName, int pointTotal) {
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "currencyName: " + currencyName);
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "pointTotal: " + pointTotal);
	//
	//				// We must use a handler since we cannot update UI elements from a different thread.
	//				post(mUpdateResults);
	//			}
	//
	//			@Override
	//			// Notifier for when spending virtual currency fails.
	//			public void getSpendPointsResponseFailed(String error) {
	//				// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "spendTapPoints error: " + error);
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + error);
	//
	//				// We must use a handler since we cannot update UI elements from a different thread.
	//				post(mUpdateResults);
	//			}
	//
	//		});
	//	} catch (Exception e) {
	//
	//		e.printStackTrace();
	//	}
	//}

	//public void awardTapjoyPoints(int amount) {
	//	try {
	//		Tapjoy.awardTapPoints(amount, new TapjoyAwardPointsNotifier() {
	//
	//			@Override
	//			public void getAwardPointsResponse(String currencyName, int pointTotal) {
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "currencyName: " + currencyName);
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "pointTotal: " + pointTotal);
	//
	//				// We must use a handler since we cannot update UI elements from a different thread.
	//				post(mUpdateResults);
	//			}
	//
	//			@Override
	//			public void getAwardPointsResponseFailed(String error) {
	//				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + error);
	//
	//				// We must use a handler since we cannot update UI elements from a different thread.
	//				post(mUpdateResults);
	//			}
	//
	//		});
	//	} catch (Exception e) {
	//
	//		e.printStackTrace();
	//	}
	//}

	//public void getTapjoyPoints() {
	//	Tapjoy.getTapPoints(new TapjoyNotifier() {
	//
	//		@Override
	//		// This method must be implemented if using the TapjoyConnect.getTapPoints() method.
	//		// It is the callback method which contains the currency and points data.
	//		public void getUpdatePoints(String currencyName, int pointTotal) {
	//			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());
	//			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "currencyName: " + currencyName);
	//			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, "pointTotal: " + pointTotal);
	//
	//			// We must use a handler since we cannot update UI elements from a different thread.
	//			post(mUpdateResults);
	//		}
	//
	//		@Override
	//		// This method must be implemented if using the TapjoyConnect.getTapPoints() method.
	//		// It is the callback method which contains the currency and points data.
	//		public void getUpdatePointsFailed(String error) {
	//			// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "getTapPoints error: " + error);
	//			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + error);
	//
	//			// We must use a handler since we cannot update UI elements from a different thread.
	//			post(mUpdateResults);
	//		}
	//
	//	});
	//}

	// 1. the Offerwall(마켓 플레이스)
	public void showAdListTapjoy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			// This will show Offers web view from where you can download the latest offers.
			TapjoyConnect.getTapjoyConnectInstance().showOffers();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 2. Featured Ads(전면 광고)
	public void showAdPopupTapjoy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// Deprecated. Deprecated since version 10.0.0.
		// Tapjoy ad units now use TJEvent.
		// Displays the Full Screen Ad fullscreen ad.
		// Should be called after getFullScreenAd(TapjoyFullScreenAdNotifier)
		// and after receiving the TapjoyFullScreenAdNotifier.getFullScreenAdResponse() callback.
		// TapjoyConnect.getTapjoyConnectInstance().showFullScreenAd();
		new TJEvent(getApp(), getString("appname"), new TJEventCallback() {

			@Override
			public void sendEventFail(TJEvent event, TJError error) {


			}

			@Override
			public void sendEventCompleted(TJEvent event, boolean contentAvailable) {

				event.showContent();
			}

			@Override
			public void didRequestAction(TJEvent event, TJEventRequest request) {


			}

			@Override
			public void contentIsReady(TJEvent event, int status) {


			}

			@Override
			public void contentDidShow(TJEvent event) {


			}

			@Override
			public void contentDidDisappear(TJEvent event) {


			}
		});

	}

	// 3. Display Ads(배너 광고)
	public void showAdBannerTapjoy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			// TapjoyConnect.getTapjoyConnectInstance().setBannerAdSize(TapjoyDisplayAdSize.TJC_AD_BANNERSIZE_320X50);
			// TapjoyConnect.getTapjoyConnectInstance().setBannerAdSize(TapjoyDisplayAdSize.TJC_AD_BANNERSIZE_640X100);
			// TapjoyConnect.getTapjoyConnectInstance().setBannerAdSize(TapjoyDisplayAdSize.TJC_AD_BANNERSIZE_768X90);
			// 15초마다리플레쉬
			// TapjoyConnect.getTapjoyConnectInstance().enableBannerAdAutoRefresh(true);
			TapjoyConnect.getTapjoyConnectInstance().getDisplayAd(this, new TapjoyDisplayAdNotifier() {

				@Override
				public void getDisplayAdResponse(View view) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

					// adView = view;
					//
					// int ad_width = adView.getLayoutParams().width;
					// int ad_height = adView.getLayoutParams().height;
					//
					// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "adView dimensions: " + ad_width + "x" + ad_height);
					//
					// // Using screen width, but substitute for the any width.
					// int desired_width = adLinearLayout.getMeasuredWidth();
					//
					// if (desired_width > ad_width)
					// desired_width = ad_width;
					//
					// // Resize banner to desired width and keep aspect ratio.
					// LayoutParams layout = new LayoutParams(desired_width, (desired_width*ad_height)/ad_width);
					// adView.setLayoutParams(layout);
					//
					// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "adLinearLayout dimensions: " + adLinearLayout.getMeasuredWidth() + "x" + adLinearLayout.getMeasuredHeight());
					//
					// update_display_ad = true;
					addAdBarBanner(view);

					// We must use a handler since we cannot update UI elements from a different thread.
					post(mUpdateResults);

					showAdBanner(true);
				}

				@Override
				public void getDisplayAdResponseFailed(String error) {
					// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, "getDisplayAd error: " + error);
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + error);

					// We must use a handler since we cannot update UI elements from a different thread.
					post(mUpdateResults);
				}

			});
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 4. Video Ads(영상 광고(?))
	public void showAdVideoTapjoy() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			// No longer required. Videos are initialized automatically.
			// Use initVideoAd to receive TapjoyVideoNotifier callbacks or to start manual caching.
			// TapjoyConnect.getTapjoyConnectInstance().initVideoAd(new TapjoyVideoNotifier() {
			//
			// //================================================================================
			// // CALLBACK Methods
			// //================================================================================
			// //@Override
			// //public void videoReady() {
			// // //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "VIDEO READY");
			// // if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
			// //
			// // // We must use a handler since we cannot update UI elements from a different thread.
			// // mHandler.post(mUpdateResults);
			// //}
			// @Override
			// public void videoStart() {
			//
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "VIDEO READY");
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
			//
			// // We must use a handler since we cannot update UI elements from a different thread.
			// mHandler.post(mUpdateResults);
			// }
			//
			// @Override
			// public void videoError(int statusCode) {
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "VIDEO ERROR: " + statusCode);
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + statusCode);
			//
			// switch (statusCode) {
			// case TapjoyVideoStatus.STATUS_MEDIA_STORAGE_UNAVAILABLE:
			// //displayText = "VIDEO ERROR: No SD card or external media storage mounted on device";
			// break;
			// case TapjoyVideoStatus.STATUS_NETWORK_ERROR_ON_INIT_VIDEOS:
			// //displayText = "VIDEO ERROR: Network error on init videos";
			// break;
			// case TapjoyVideoStatus.STATUS_UNABLE_TO_PLAY_VIDEO:
			// //displayText = "VIDEO ERROR: Error playing video";
			// break;
			// }
			//
			// mHandler.post(mUpdateResults);
			// }
			//
			// @Override
			// public void videoComplete() {
			// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, "VIDEO COMPLETE");
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
			// }
			//
			// });
			// TapjoyConnect.getTapjoyConnectInstance().setVideoCacheCount(5);
			TapjoyConnect.getTapjoyConnectInstance().showOffers();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// /**
	// * 일부단말(폰기능X:갤럭시탭/갤럭시플레이어) 무료충전소(탭애드) 진입오류
	// *
	// */
	// @Deprecated
	// private void initAdTapAd() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// if (mAdTapad != null) {
	// return;
	// }
	//
	// try {
	// mAdTapad = new TapAdPlatform(this);
	// boolean testFlag = false;
	// boolean storeFlag = false;
	//
	// if (_IKaraoke.DEBUG) {
	// testFlag = true;
	// }
	//
	// mAdTapad.registDevice(new TapadListener() {
	//
	// @Override
	// public void onTapadError(Integer arg0, String arg1) {
	//
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, "Ads - TAPAD - onTapadError - " + arg0 + ", " + arg1);
	//
	// }
	//
	// @Override
	// public void onLoadingFinished(TapadViewType arg0) {
	//
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, "Ads - TAPAD - onLoadingFinished - " + arg0);
	//
	// }
	//
	// @Override
	// public void onInitializationResult(Integer arg0) {
	//
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, "Ads - TAPAD - onInitializationResult - " + arg0);
	//
	// }
	// }, TAPAD_ADVERTISER_ID, TAPAD_APP_ID, getApp().p_uid, testFlag, storeFlag);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// @Deprecated
	// public void showAdBannerTapAd() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	//
	// }
	//
	// @Deprecated
	// public void showAdPopupTapAd() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// try {
	// mAdTapad.startPopUpActivity(this, false);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// @Deprecated
	// public void showAdPopListTapAd() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// try {
	// mAdTapad.startFullAdListActivity(this);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// /**
	// */
	// @Deprecated
	// private void initAdPopcorn() {
	// // if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	// //
	// // try {
	// // // 파피콘 이미지 뷰를 추가합니다.
	// // mAdpopcornLayout = (LinearLayout) findViewById(R.id.adpopcorn);
	// // if (mAdpopcornLayout == null) {
	// // return;
	// // }
	// //
	// // IAdPOPcornParameter adpopcornParameter = AdPOPcornFactory.GetAdPOPcornParameter();
	// //
	// // // 필수 입력값 설정
	// // // IGAW로부터 발급 받은 미디어코드를 입력합니다.
	// // //adpopcornParameter.setMc("N721185137");
	// // adpopcornParameter.setMc(ADPOPCORN_KEY_MEDIA);
	// //
	// // // 리워드를 서버에서 지급하는 경우는 필수적으로 입력하셔야 합니다.
	// // // 왜냐하면, 여기 입력한 유저식별값이 서버로 전송되기 때문입니다.
	// // // 클라이언트에서 지급하거나, adPOPcorn 포인트를 사용하신다면 생략하셔도 됩니다.
	// // adpopcornParameter.setUsn(getApp().p_uid);
	// //
	// // // IGAW로부터 발급 받은 해시키를 입력합니다.
	// // adpopcornParameter.setHashKey(ADPOPCORN_KEY_HASH);
	// //
	// // // 선택 입력값 설정
	// // // 유저에 대한 정보를 추가적으로 입력합니다.
	// // // 기본적으로 나이, 성별, 마켓에 대한 정보를 입력할 수 있습니다.
	// // // 해당 정보들은 일정한 포멧을 위하여 APConstant에서 불러와서 사용할 수 있습니다.
	// // // 이 외의 추가 정보가 있다면 .setUserFilter(String key, String value)의 형태로 입력할 수 있습니다.
	// // // adpopcornParameter.setUserFilter(APConstant.KEY_AGE, "31");
	// // // adpopcornParameter.setUserFilter(APConstant.KEY_GENDER, APConstant.VALUE_MALE);
	// // //adpopcornParameter.setUserFilter(APConstant.KEY_VENDOR, APConstant.VALUE_GOOGLE_MARKET);
	// // adpopcornParameter.setUserFilter("vender", APConstant.MARKET.GOOGLE);
	// //
	// //
	// // mAdPopcorn = AdPOPcornFactory.GetAdPOPcornSDK(this, adpopcornParameter);
	// //
	// // // 파피콘 이미지 뷰를 추가합니다.
	// // // ArrayList<View> outViews = new ArrayList<View>();
	// // // CharSequence text = null;
	// // // int flags = 0;
	// // // mAdpopcornLayout.findViewsWithText(outViews, text, flags);
	// // // for (int i = 0; i < outViews.size(); i++) {
	// // // if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + outViews.get(i).toString());
	// // // }
	// // @SuppressWarnings("unchecked")
	// // List<View> outViews = (List<View>) WidgetUtils.findViewsWithClass(mAdpopcornLayout,
	// // mAdPopcorn.getPopiconView().getClass());
	// // if (outViews != null && outViews.size() == 0) {
	// // mAdpopcornView = mAdPopcorn.getPopiconView();
	// // mAdpopcornLayout.addView(mAdpopcornView);
	// // }
	// //
	// // // 애드팝콘 콜백 리스너를 설정합니다.
	// // mAdPopcorn.setEventListener(new IAdPOPcornEventListener() {
	// //
	// // @Override
	// // public void Onpopicon_info(String arg0, Bitmap arg1) {
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void OnRequestedGetPopicon(IRequestGetPopiconResult arg0) {
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void OnCompletedEvent() {
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void OnClosedOfferWallPage() {
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void OnClosedCouponWindow() {
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void OnClosedAdPage() {
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void OnClickedPopicon() {
	// //
	// //
	// // }
	// // });
	// //
	// // //디버그 용 토스트 로그를 활성화 합니다.
	// // //(주의!) 테스트나 개발 단계에서만 사용하십시오.
	// // if (_IKaraoke.DEBUG) {
	// // mAdPopcorn.setLog(true);
	// // } else {
	// // mAdPopcorn.setLog(false);
	// // }
	// //
	// // //파피콘 위치를 설정합니다. (true : 상대좌표(%단위), false : 절대좌표(픽셀단위) )
	// // mAdPopcorn.setPopiconPosition(0, 0, true);
	// //
	// // // 파피콘 애니메이션을 설정합니다.
	// // // 예제 프로젝트에는 위로 떠오르면서 나타나는 듯한 애니메이션이 정의된 클래스가 포함되어 있습니다.
	// // // adpopcornSDK.setPopiconAnimation(AdPOPcornAnimationFactory.CreateBouncingAnimation(0, 0, 20,
	// // // 0));
	// //
	// // // adPOPcorn에서 사용자에게 알려주어야 할 메시지가 있을 경우 얼럿창으로 표현합니다.
	// // // 알려주는 메시지는 아이템 보상 내역, 유저 cs 처리 등이 있습니다.
	// // mAdPopcorn.showNotificationMessage();
	// //
	// // // 애드팝콘 서비스에 연결합니다.
	// // //adpopcornSDK.connect();
	// // mAdPopcorn.adpopcornConnect();
	// //
	// // // 파피콘을 호출합니다.
	// // mAdPopcorn.beginAdpopcorn();
	// //
	// // // 파피콘을 보인다.
	// // if (getCurrentFragment() != null) {
	// // if (getApp().isLoginUser()) {
	// // showAdIconAdPopCorn();
	// // } else {
	// // hideIconAdPopCorn();
	// // }
	// // } else {
	// // hideIconAdPopCorn();
	// // }
	// // hideIconAdPopCorn();
	// // } catch (Exception e) {
	// //
	// // e.printStackTrace();
	// // }
	// }

	// @Deprecated
	// public void showAdIconAdPopCorn() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// try {
	// if (mAdpopcornLayout == null) {
	// return;
	// }
	// Animation ani = AdPOPcornAnimationFactory.CreateBouncingAnimation(0, 0, 100, 0);
	// mAdpopcornLayout.setVisibility(View.VISIBLE);
	// // 파피콘 애니메이션을 설정합니다.
	// // 예제 프로젝트에는 위로 떠오르면서 나타나는 듯한 애니메이션이 정의된 클래스가 포함되어 있습니다.
	// //adpopcornSDK.setPopiconAnimation(ani);
	// mAdpopcornLayout.setAnimation(ani);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// @Deprecated
	// public void hideIconAdPopCorn() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	// try {
	// if (mAdpopcornLayout == null) {
	// return;
	// }
	// mAdpopcornLayout.setVisibility(View.GONE);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// @Deprecated
	// public void showAdPopListAdPopCorn() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// try {
	// if (mAdpopcornView == null) {
	// return;
	// }
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// //mAdpopcornView.performClick();
	// //adpopcornSDK.showADList();
	// mAdPopcorn.showAdList();
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

	// @Deprecated
	// public void showAdPopupAdPopCorn() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// try {
	// if (mAdpopcornView == null) {
	// return;
	// }
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// mAdpopcornView.performClick();
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

	protected void initAdTNK() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);

		try {
			String APP_ID = ManifestData.getString(getApp(), "tnkad_app_id");

			android.util.Log.d("Ads - TNK - REWARD", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

			// TnkSession.enableLogging(_IKaraoke.DEBUG);

			TnkSession.setUserName(this, getApp().p_uid);

			//TnkSession.getUid(this);

			TnkStyle.AdWall.Header.background = R.drawable.actionbar_background;
			TnkStyle.AdWall.Header.textColor = 0xffffffff;
			TnkStyle.AdWall.Header.textSize = 22;

			TnkSession.prepareInterstitialAd(this, TnkSession.CPC);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void showAdPopListTNK() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			String title = getString(R.string.menu_shop_charge);
			// 버튼을 누르면 광고목록창을 띄운다.
			TnkSession.showAdList(this, title);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void showAdPopListTNK(KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			String title = getString(R.string.menu_shop_charge);
			if (list != null) {
				title = list.getValue(LOGIN.KEY_GOODSNAME);
			}
			// 버튼을 누르면 광고목록창을 띄운다.
			TnkSession.showAdList(this, title);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void showAdPopupTNK() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			// 버튼을 누르면 광고목록창을 띄운다.
			// TnkSession.showFeaturedAd(this);
			TnkSession.showInterstitialAd(this);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 */
	// private void initAdChartboost() {
	// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// if (mAdChartboost != null) {
	// return;
	// }
	//
	// mAdChartboost = Chartboost.sharedChartboost();
	//
	// String APP_ID = CHARTBOOST_ID;
	// String APP_KEY = CHARTBOOST_SIGNATURE;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = CHARTBOOST_ID_KOR;
	// APP_KEY = CHARTBOOST_SIGNATURE_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = CHARTBOOST_ID_JAP;
	// APP_KEY = CHARTBOOST_SIGNATURE_JAP;
	// }
	//
	// android.util.Log.d("Ads - Chartboost - POPUP", __CLASSNAME__ + ":initAds:" + getMethodName() + getPackageName() + "@" + APP_ID + " - " + APP_KEY);
	//
	// mAdChartboost.onCreate(this, APP_ID, APP_KEY, new ChartboostDelegate() {
	//
	// @Override
	// public boolean shouldRequestMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// return true;
	// }
	//
	// @Override
	// public boolean shouldRequestInterstitialsInFirstSession() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// return true;
	// }
	//
	// @Override
	// public boolean shouldRequestInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	// return true;
	// }
	//
	// @Override
	// public boolean shouldDisplayMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// return true;
	// }
	//
	// @Override
	// public boolean shouldDisplayLoadingViewForMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// return true;
	// }
	//
	// @Override
	// public boolean shouldDisplayInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	// return true;
	// }
	//
	// @Override
	// public void didShowMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// }
	//
	// @Override
	// public void didShowInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	//
	// }
	//
	// @Override
	// public void didFailToLoadMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// }
	//
	// @Override
	// public void didFailToLoadInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	// initAdPopupDefault();
	// }
	//
	// @Override
	// public void didDismissMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// }
	//
	// @Override
	// public void didDismissInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	// mAdChartboost.cacheInterstitial(location);
	// }
	//
	// @Override
	// public void didCloseMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// }
	//
	// @Override
	// public void didCloseInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	//
	// finish();
	// }
	//
	// @Override
	// public void didClickMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// }
	//
	// @Override
	// public void didClickInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// }
	//
	// @Override
	// public void didCacheMoreApps() {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	//
	// isShowAdPopup = true;
	// }
	//
	// @Override
	// public void didCacheInterstitial(String location) {
	// android.util.Log.w("Ads - Chartboost", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + location);
	//
	//
	// isShowAdPopup = true;
	// }
	//
	// @Override
	// public void didFailToLoadUrl(String arg0) {
	//
	//
	// }
	// });
	//
	// mAdChartboost.onStart(this);
	//
	// mAdChartboost.startSession();
	//
	// mAdChartboost.cacheInterstitial();
	// //mAdChartboost.cacheMoreApps();
	// //mAdChartboost.showInterstitial();
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// @Deprecated
	// private void initAdColony() {
	// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	// //
	// //try {
	// // if (mAdColony != null) {
	// // return;
	// // }
	// //
	// // //The following AdColony static methods are available. AdColony.configure() must be called
	// // //before any of the others except setDeviceID().
	// // AdColony.setDeviceID(getApp().p_uid);
	// //
	// // // Configure ADC once early on before any other ADC calls.
	// // AdColony.configure(this, // Activity reference
	// // "1.0", // Arbitrary app version
	// // ADCOLONY_ID, // ADC App ID from adcolony.com
	// // ADCOLONY_ID_ZONE // Video Zone ID #1 from adcolony.com
	// // );
	// //
	// // //AdColony.enable(true);
	// //
	// // // Notify this object about confirmed virtual currency.
	// // AdColony.addV4VCListener(new AdColonyV4VCListener() {
	// //
	// // @Override
	// // public void onAdColonyV4VCReward(AdColonyV4VCReward reward) {
	// //
	// // android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName() + reward);
	// // //Just an example, see API Details page for more information.
	// // if(reward.success())
	// // {
	// // //Reward user
	// // }
	// // }
	// //
	// // });
	// //
	// // // Create a video ad object.
	// // mAdColony = new AdColonyVideoAd();
	// //
	// //} catch (Exception e) {
	// //
	// // e.printStackTrace();
	// //}
	//
	// }
	public void showAlertNoAd() {
		String title = getString(R.string.alert_title_confirm);
		String message = ad_freezone_error;

		if (TextUtil.isEmpty(message)) {
			message = "There's NO AD";
		}

		if (_IKaraoke.DEBUG) {
			message += "\n\n[디버그정보]";
			message += "\nis_ad:" + is_ad;
			message += "\nad_vendor:" + ad_vendor;
			message += "\nad_banner:" + ad_banner;
			message += "\nad_freezone:" + ad_freezone;
			message += "\nad_freezone_info:" + ad_freezone_info;
			message += "\nad_freezone_error:" + ad_freezone_error;
		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + message);

		showAlertDialog(title, message, getString(R.string.btn_title_confirm), null, null, null, true, null);
	}

	// @Deprecated
	// private boolean checkRewardAdColony() {
	// boolean ret = false;
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ret);
	//
	// ret = mAdColony.getV4VCAvailable();
	//
	// ad_amount += mAdColony.getV4VCAmount();
	// if (ad_amount > 0) {
	// ret = true;
	// }
	//
	// ad_played += mAdColony.getV4VCPlays();
	// ad_maximun += mAdColony.getV4VCPlayCap();
	//
	// int count = ad_maximun - ad_played;
	// if (count > 0) {
	// ret = true;
	// }
	//
	// if (!ret) {
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName() + "!!!NG!!!");
	// }
	//
	// android.util.Log.w("Ads - AdColony", "initAds:" + "ad_freezone_enable:" + mAdColony.getV4VCAvailable());
	// android.util.Log.w("Ads - AdColony", "initAds:" + "ad_freezone_amount:" + ad_amount);
	// android.util.Log.w("Ads - AdColony", "initAds:" + "ad_freezone_played:" + ad_played);
	// android.util.Log.w("Ads - AdColony", "initAds:" + "ad_freezone_maximun:" + ad_maximun);
	//
	// return ret;
	// }
	//
	// @Deprecated
	// public void showAdPopListAdColony() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
	//
	// AdColonyV4VCAd v4vc_ad = new AdColonyV4VCAd( ADCOLONY_ID_ZONE ).withListener(new AdColonyAdListener() {
	//
	// @Override
	// public void onAdColonyAdStarted(AdColonyAd ad) {
	//
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdColonyAdAttemptFinished(AdColonyAd ad) {
	//
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// }).withConfirmationDialog().withResultsDialog();
	//
	// // Debug pop-up showing the number of plays today and the playcap.
	// //Toast.makeText( V4VCDemo.this, ""+v4vc_ad.getRewardName(), Toast.LENGTH_SHORT ).show();
	// //
	// //String status = "Available views: " + v4vc_ad.getAvailableViews();
	// //Toast.makeText( V4VCDemo.this, status, Toast.LENGTH_SHORT ).show();
	// if (v4vc_ad.getAvailableViews() > 0) {
	// v4vc_ad.show();
	// } else {
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName() + "!!!!NG!!!!");
	// showAlertNoAd();
	// }
	//
	// }
	//
	// @Deprecated
	// public void showAdPopupAdColony() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
	//
	// if (!checkRewardAdColony()) {
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName() + "!!!!NG!!!!");
	// showNoRewardAlert();
	// return;
	// }
	//
	// //2. Adding Videos For Virtual Currency
	// mAdColony.show(new AdColonyVideoListener() {
	//
	// @Override
	// public void onAdColonyVideoStarted() {
	//
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdColonyVideoFinished() {
	//
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// });
	//
	// AdColonyVideoAd ad = new AdColonyVideoAd().withListener(new AdColonyAdListener() {
	//
	// @Override
	// public void onAdColonyAdStarted(AdColonyAd ad) {
	//
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onAdColonyAdAttemptFinished(AdColonyAd ad) {
	//
	// android.util.Log.w("Ads - AdColony", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// });
	//
	// ad.show();
	// }

	protected void initAdMetaps() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);

		try {
			String APP_ID = _IKaraoke.METAPS_APPLICATION_ID;
			String APP_KEY = _IKaraoke.METAPS_APPLICATION_KEY;

			if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.METAPS_APPLICATION_ID_KOR;
				APP_KEY = _IKaraoke.METAPS_APPLICATION_KEY_KOR;
			} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
				APP_ID = _IKaraoke.METAPS_APPLICATION_ID_JAP;
				APP_KEY = _IKaraoke.METAPS_APPLICATION_KEY_JAP;
				// } else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
				// APP_ID = _IKaraoke.METAPS_APPLICATION_ID_NAVER;
				// APP_KEY = _IKaraoke.METAPS_APPLICATION_KEY_NAVER;
				// } else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
				// APP_ID = _IKaraoke.METAPS_APPLICATION_ID_S5;
				// APP_KEY = _IKaraoke.METAPS_APPLICATION_KEY_S5;
			}

			android.util.Log.d("Ads - Metaps - REWARD", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID + "-" + APP_KEY);

			// Call as soon as possible initialize() method
			Factory.initialize(this, new Receiver() {

				@Override
				public boolean retrieve(int points, Offer offer) {
					android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName() + points + "," + offer);

					return true;
				}

				@Override
				public boolean finalizeOnError(Offer offer) {

					// StringBuffer buf = new StringBuffer();
					// String appName = offer.getAppName()==null ? "" : offer.getAppName().trim();
					// String appId = offer.getAppId()==null ? "" : offer.getAppId().trim();
					// String campaignId = offer.getCampaignId()==null ? "" : offer.getCampaignId().trim();
					// String status = String.valueOf(offer.getStatus());
					// String lastCode = offer.getErrorCode()==null ? "" : offer.getErrorCode().trim();
					// buf.append(appId).append("\n")
					// .append(campaignId).append("\n")
					// .append(appName).append("\n")
					// .append(status).append("\n")
					// .append(lastCode);
					android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName() + offer);
					return true;
				}
				// }, APP_ID, Const.SDK_MODE_TEST);
			}, APP_ID, Const.SDK_MODE_PRODUCTION);

			// FeaturedAppDialog.prepare(getApp().p_uid, "Coin");
		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(this, Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, Log2.getStackTraceString(e));
		}
	}

	public void showAdPopListMeTaps() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// 버튼을 누르면 광고목록창을 띄운다.
		try {
			Factory.launchOfferWall(this, getApp().p_uid, "Coin");
		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(this, Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, Log2.getStackTraceString(e));
		}

	}

	public void showAdPopupMeTaps() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			// // 버튼을 누르면 광고목록창을 띄운다.
			// FeaturedAppDialogListener listener = new FeaturedAppDialogListener() {
			// @Override
			// public void onCancelDialog(Activity activity) {
			// android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName());
			// //Toast.makeText(activity, "Dialog has been canceled", Toast.LENGTH_SHORT).show();
			// stopLoadingDialog("Ads - Metaps AdDialog has been canceled");
			// }
			//
			// @Override
			// public void onDownloadApplication(Activity activity) {
			// android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName());
			// //Toast.makeText(activity, "Download has been touched", Toast.LENGTH_SHORT).show();
			// stopLoadingDialog("Ads - Metaps Download has been touched");
			// }
			//
			// @Override
			// public void onNoFeaturedApplication(Activity activity) {
			// android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName());
			// //Toast.makeText(activity, "No featured application has been found for this device", Toast.LENGTH_SHORT).show();
			// stopLoadingDialog("Ads - Metaps No featured application has been found for this device");
			// }
			//
			// @Override
			// public void onShowDialog(Activity activity) {
			// android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName());
			// stopLoadingDialog(null);
			// }
			//
			// @Override
			// public void onShowDialogNotPossible(Activity activity) {
			// android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName());
			// //Toast.makeText(activity, "Dialog can not be shown", Toast.LENGTH_SHORT).show();
			// stopLoadingDialog("Ads - Metaps AdDialog can not be shown");
			// }
			//
			// @Override
			// public void onStartWaiting(Activity activity) {
			// android.util.Log.w("Ads - Metaps", __CLASSNAME__ + ":initAds:" + getMethodName());
			// startLoadingDialog(null);
			// }
			// };
			// FeaturedAppDialog.show(this, listener);
			Factory.launchOfferWall(this, _IKaraoke.METAPS_CLIENT_ID, "COIN");
		} catch (Exception e) {
			// e.printStackTrace();
			// Toast.makeText(this, Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, Log2.getStackTraceString(e));
			stopLoadingDialog(null);
		}
	}

	// private void initAdKiip() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// //if (mAdKiip != null) {
	// // return;
	// //}
	// //
	// //// Initialize Kiip
	// //mAdKiip = Kiip.init(getApplication(), KIIP_APP_KEY, KIIP_APP_SECRET);
	// //
	// //Kiip.setInstance(mAdKiip);
	// //
	// //Kiip.getInstance().setOnSwarmListener(new OnSwarmListener() {
	// //
	// // @Override
	// // public void onSwarm(Kiip kiip, String id) {
	// // if (_IKaraoke.DEBUG)Log.e("kiip", getMethodName());
	// //
	// // //Log.d(__CLASSNAME__, "Kiip::onSwarm() id=" + id);
	// //
	// // }
	// //});
	// //
	// //Kiip.getInstance().setOnContentListener(new OnContentListener() {
	// //
	// // @Override
	// // public void onContent(Kiip kiip, String content, int quantity, String transactionId, String signature) {
	// // if (_IKaraoke.DEBUG)Log.e("kiip", getMethodName());
	// //
	// // //Log.d(__CLASSNAME__, "Kiip::onContent() content=" + content + " quantity=" + quantity + " transactionId=" + transactionId + " signature=" + signature);
	// //
	// // }
	// //});
	// //
	// //Kiip.getInstance().startSession(new Callback() {
	// //
	// // @Override
	// // public void onFinished(Kiip kiip, Poptart poptart) {
	// // if (_IKaraoke.DEBUG)Log.e("kiip", getMethodName());
	// //
	// // //Log.d(__CLASSNAME__, "Kiip::onContent() poptart=" + poptart);
	// // mAdKiipPoptart = poptart;
	// //
	// // }
	// //
	// // @Override
	// // public void onFailed(Kiip kiip, Exception exception) {
	// // if (_IKaraoke.DEBUG)Log.e("kiip", getMethodName());
	// //
	// // //Log.d(__CLASSNAME__, "Kiip::onContent() exception=" + exception);
	// //
	// // }
	// //});
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// }

	// protected void initAdW3i() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// int APP_ID = W3I_APP_ID;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = W3I_APP_ID_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = W3I_APP_ID_JAP;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = W3I_APP_ID_NAVER;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = W3I_APP_ID_S5;
	// }
	//
	// android.util.Log.d("Ads - W3i - REWARD", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);
	//
	// //ApplicationInputs inputs = new ApplicationInputs();
	// //// App Id (Required): The application id which was provided by the operations department at W3i.
	// //inputs.setAppId(APP_ID); //Application ID provided by W3i
	// //// PackageName (Required): The package identifier used to redirect to the Android Market.
	// //inputs.setPackageName(getPackageName()); //The package name for your app
	// //// Publisher User Id (Optional): If your application has an internal way to identify a user,
	// //// you may set that using this parameter.
	// //inputs.setPublisherUserId(getApp().p_uid); //Sets user id used by your app
	// //// Application Name (Optional, but recommended): Set the display name for your application,
	// //// which will be shown in the title bar.
	// ////inputs.setApplicationName("KPOP HOLIC - KOR"); //Sets the display name for your app
	// //MonetizationManager.initialize(this, inputs);
	//
	// /*Initialization of AdvertiserManager class*/
	// AdvertiserManager.initialize(this, true);
	// AdvertiserManager.appWasRun(APP_ID); // This is your AppId from NativeX
	//
	// //Application Name Recommended Sets the display name for your application. Shown in the title bar. This parameter is optional in SDK version later than 4.2.3 as it is obtained from the ApplicationContext if not set.
	// //Publisher User Id Optional Use this parameter if your application has an internal way to identify a user.
	// //AppId Required Your uniquely assigned AppId from the NativeX self-service. This is the id of your application in the NativeX servers and is required for identifying it.
	// //PackageName Required The package identifier used to redirect to Google Play. This parameter is optional in SDK version later than 4.2.3 as it is obtained from the ApplicationContext if not set.
	// MonetizationManager.initialize(getApp(), "", APP_ID, getApp().p_uid, getPackageName());
	// MonetizationManager.setTheme(new OriginalTheme());
	// MonetizationManager.enableLogging(_IKaraoke.DEBUG);
	// MonetizationManager.createSession(new SessionListener() {
	//
	// @Override
	// public void createSessionCompleted(boolean success, boolean isOfferWallEnabled, long sessionId) {
	//
	// android.util.Log.w("Ads - w3i", __CLASSNAME__ + ":initAds:" + getMethodName() + success
	// + ":" + isOfferWallEnabled + ":" + sessionId);
	// if (success) {
	// isShowAdPopup = true;
	// }
	// }
	// });
	//
	// MonetizationManager.redeemCurrency(this, new CurrencyListenerV1() {
	//
	// @Override
	// public void onRedeem(List<Balance> arg0) {
	//
	// android.util.Log.w("Ads - w3i", __CLASSNAME__ + ":initAds:" + getMethodName());
	// }
	// });
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdPopListW3i() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// try {
	// MonetizationManager.showOfferWall();
	// //MonetizationManager.showWebOfferWall();
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// /**
	// */
	// @Deprecated
	// private void initAdPlayhaven() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// //if (mAdPlayhaven != null) {
	// // return;
	// //}
	// //
	// //PHConfig.token = PLAYHAVEN_TOKEN;
	// //PHConfig.secret = PLAYHAVEN_SECRET;
	// //
	// ////Recording game opens
	// ////Purpose: helps the server track game usage.
	// ////Notes: Make sure to pass in a Context (usually an Activity) to the PHPublisherOpenRequest constructor.
	// //PHPublisherOpenRequest request = new PHPublisherOpenRequest(this);
	// //request.send();
	// //
	// //// PHNotificationView notifyView = new PHNotificationView(this, "kpop_holic");
	// //// addAdBanner(notifyView);
	// //// notifyView.refresh();
	// //
	// //// Displaying Content
	// //mAdPlayhaven = new PHPublisherContentRequest(this, "kpop_holic");
	// //
	// //// Set your listeners, we only set the content listener here.
	// //mAdPlayhaven.setOnContentListener(new ContentDelegate() {
	// //
	// // @Override
	// // public void requestSucceeded(PHAPIRequest phapirequest, JSONObject jsonobject) {
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phapirequest + "\n" + jsonobject);
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void requestFailed(PHAPIRequest phapirequest, Exception exception) {
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phapirequest + "\n" + exception);
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void willGetContent(PHPublisherContentRequest phpublishercontentrequest) {
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phpublishercontentrequest);
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void willDisplayContent(PHPublisherContentRequest phpublishercontentrequest,
	// // PHContent phcontent) {
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phpublishercontentrequest + "\n" + phcontent);
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void didDisplayContent(PHPublisherContentRequest phpublishercontentrequest,
	// // PHContent phcontent) {
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phpublishercontentrequest + "\n" + phcontent);
	// //
	// //
	// // }
	// //
	// // @Override
	// // public void didDismissContent(PHPublisherContentRequest phpublishercontentrequest,
	// // PHDismissType phdismisstype) {
	// //
	// // try {
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phpublishercontentrequest + "\n"
	// // + phpublishercontentrequest.getContent() + "\n" + phdismisstype);
	// // } catch (Exception e) {
	// //
	// // e.printStackTrace();
	// // }
	// //
	// // switch (phdismisstype) {
	// // case ContentUnitTriggered:
	// // break;
	// //
	// // case CloseButtonTriggered:
	// // break;
	// //
	// // case ApplicationTriggered:
	// // break;
	// //
	// // case NoContentTriggered:
	// // break;
	// //
	// // default:
	// // break;
	// // }
	// //
	// // }
	// //});
	// //
	// //// Set your listeners, we only set the purchace listener here.
	// //mAdPlayhaven.setOnPurchaseListener(new PurchaseDelegate() {
	// //
	// // @Override
	// // public void shouldMakePurchase(PHPublisherContentRequest phpublishercontentrequest,
	// // PHPurchase phpurchase) {
	// //
	// // android.util.Log.w("Ads - Playhaven", __CLASSNAME__ + ":initAds:" + getMethodName() + "\n" + phpublishercontentrequest + "\n" + phpurchase);
	// //
	// // }
	// //});
	// //
	// //mAdPlayhaven.preload();
	//
	// //test
	// //mAdPlayHaven.send();
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	/**
	 * @see <a href="http://stackoverflow.com/questions/15174266/general-flurry-setup-and-initialization">Stack Overflow - General Flurry setup and initialization</a>
	 */
	// protected void initAdFlurry() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// String API_KEY = FLURRY_API_KEY;
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// API_KEY = FLURRY_API_KEY_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// API_KEY = FLURRY_API_KEY_JPOP;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// // API_KEY = FLURRY_API_KEY_NAVER;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// // API_KEY = FLURRY_API_KEY_S5;
	// }
	//
	// android.util.Log.d("Ads - Flurry - REWARD", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + API_KEY);
	//
	// try {
	// FlurryAgent.setLogEnabled(_IKaraoke.DEBUG);
	// FlurryAgent.setUserId(getApp().p_uid);
	// FlurryAgent.setReportLocation(false);
	// FlurryAgent.onStartSession(this, API_KEY);
	// //- 오류발생(안하는게정신건강에좋을듯
	// //Create a Map<String,String> to hold the user cookies
	// Map<String, String> uCookies = new HashMap<String, String>();
	// uCookies.put("userId", getApp().p_uid);
	// FlurryAds.setUserCookies(uCookies);
	// FlurryAds.initializeAds(this);
	// FlurryAds.setAdListener(new FlurryAdListener() {
	//
	// @Override
	// public void spaceDidReceiveAd(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public void spaceDidFailToReceiveAd(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public boolean shouldDisplayAd(String adSpace, FlurryAdType type) {
	// android.util.Log.w("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace
	// + type);
	//
	// return true;
	// }
	//
	// @Override
	// public void onRenderFailed(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public void onApplicationExit(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public void onAdOpened(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public void onAdClosed(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public void onAdClicked(String adSpace) {
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	//
	// }
	//
	// @Override
	// public void onVideoCompleted(String adSpace) {
	//
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	// }
	//
	// @Override
	// public void onRendered(String adSpace) {
	//
	// android.util.Log
	// .d("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName() + adSpace);
	//
	// }
	//
	// });
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdPopListFlurry() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// try {
	// View base = getWindow().getDecorView().findViewById(R.id.base);
	// //FlurryAds.displayAd(this, FLURRY_ADSPACE_POPUP, (ViewGroup) base);
	// if (!FlurryAds.getAd(this, "F5", (ViewGroup) base, FlurryAdSize.FULLSCREEN, 0)) {
	// android.util.Log.w("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + "!!!!NG!!!!");
	// showAlertNoAd();
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdBannerFlurry(String adSpace) {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + mAdBase);
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// try {
	// if (TextUtil.isEmpty(adSpace)) {
	// adSpace = this.ad_vendor;
	// }
	// FlurryAds.getAd(this, adSpace, mAdBase, FlurryAdSize.BANNER_BOTTOM, 0);
	// //FlurryAds.displayAd(this, FLURRY_ADSPACE_BANNER, (ViewGroup) base);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdPopupFlurry() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + mAdBase);
	//
	// // 버튼을 누르면 광고목록창을 띄운다.
	// try {
	// //FlurryAds.displayAd(this, FLURRY_ADSPACE_POPUP, (ViewGroup) base);
	// if (!FlurryAds.getAd(this, this.ad_vendor, mAdBase, FlurryAdSize.FULLSCREEN, 0)) {
	// android.util.Log.w("Ads - Flurry", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + "!!!!NG!!!!");
	// showAlertNoAd();
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	/**
	 */
	// 오류 코드
	// 오류 값
	// 설명
	// ERROR_SUCCESS
	// 0
	// 성공
	// ERROR_NETWORK
	// 3
	// 네트워크 오류가 발생한 경우
	// ERROR_INVALID_REGION
	// 4
	// 광고 영역 설정이 잘못된 경우
	// ERROR_INVALID_STATE
	// 5
	// 함수 호출이 올바르지 않은 경우
	// ERROR_INTERNAL
	// 101
	// 광고 서버에서 오류가 발생한 경우
	// ERROR_INVALID_REQUEST
	// 102
	// 채널 아이디 설정하지 않은 경우
	// ERROR_NO_ADS
	// 103
	// 유효한 광고가 없는 경우
	// ERROR_WAIT_FOR_APPROVAL
	// 104
	// 검수 중인 경우
	// ERROR_INVALID_MEDIA
	// 105
	// 유효하지 않은 퍼블리셔, 미디어, 채널인 경우,
	// 검수 보류 중인 경우,
	// 제재 중인 경우
	// ERROR_INVALID_CHANNEL
	// 106
	// 채널 아이디가 잘못 설정된 경우,
	// 채널 아이디가 유효한 패키지가 아닌 경우
	// ERROR_UNKNOWN
	// -1
	// 알 수 없는 오류
	// private void initAdNaver() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// //if (mAdNaverAdView != null) {
	// // return;
	// //}
	//
	// String APP_ID = NAVERADPOST_ID;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = NAVERADPOST_ID_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = NAVERADPOST_ID_JAP;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = NAVERADPOST_ID_NAVER;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = NAVERADPOST_ID_S5;
	// }
	//
	// android.util.Log.d("Ads - Naver - BANNER", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);
	//
	// mAdNaverAdView = new MobileAdView(this); // 2
	// //mAdNaver.setChannelID("testcid"); // 3
	// mAdNaverAdView.setChannelID(APP_ID);
	// mAdNaverAdView.setTest(_IKaraoke.DEBUG); // 4
	// mAdNaverAdView.setListener(new MobileAdListener() {
	//
	// @Override
	// public void onReceive(int err) {
	//
	// android.util.Log.w("Ads - Naver", __CLASSNAME__ + ":initAds:" + getMethodName() + " : "
	// + err);
	//
	// showAdBanner(true);
	// }
	// }); // 5
	// mAdNaverAdView.start(); // 6
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// private void initAdGreystripeBanner(int type) {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + getPackageName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// String GREYSTRIPE_GUID = GREYSTRIPE_GUID_KOR;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// GREYSTRIPE_GUID = GREYSTRIPE_GUID_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// GREYSTRIPE_GUID = GREYSTRIPE_GUID_JAP;
	// } else {
	// GREYSTRIPE_GUID = GREYSTRIPE_GUID_KOR;
	// }
	//
	// if (_IKaraoke.DEBUG)
	// Log.i(__CLASSNAME__, getMethodName() + GREYSTRIPE_GUID);
	//
	// float widthDp = Util.getWidthDp(this);
	// //배너일경우 해상도에 맞게 강제변환
	// if (type == 1 && widthDp >= 600.0f) {
	// type = 2;
	// } else if (type == 2 && widthDp < 600.0f) {
	// type = 1;
	// }
	//
	// //배너처리
	// try {
	//
	// switch (type) {
	// case 1:
	// mAdGreyStripeAdView = new GSMobileBannerAdView(this, GREYSTRIPE_GUID);
	// break;
	//
	// case 2:
	// mAdGreyStripeAdView = new GSMobileBannerAdView(this, GREYSTRIPE_GUID);
	// break;
	//
	// case 3:
	// mAdGreyStripeAdView = new GSMobileBannerAdView(this, GREYSTRIPE_GUID);
	// break;
	//
	// case 4:
	// //배너일경우 해상도에 맞게 강제변환
	// mAdGreyStripeAdView = new GSMobileBannerAdView(this, GREYSTRIPE_GUID);
	// break;
	//
	// default:
	// break;
	// }
	//
	// if (mAdGreyStripeAdView != null) {
	// if (mAdGreyStripeAdView instanceof GSMobileBannerAdView) {
	// //((GSMobileBannerAdView)mAdGsBanner).addListener(l);
	// ((GSMobileBannerAdView) mAdGreyStripeAdView).refresh();
	// } else if (mAdGreyStripeAdView instanceof GSLeaderboardAdView) {
	// //((GSLeaderboardAdView)mAdGsBanner).addListener(l);
	// ((GSLeaderboardAdView) mAdGreyStripeAdView).refresh();
	// } else if (mAdGreyStripeAdView instanceof GSMediumRectangleAdView) {
	// //((GSLeaderboardAdView)mAdGsBanner).addListener(l);
	// ((GSLeaderboardAdView) mAdGreyStripeAdView).refresh();
	// }
	//
	// }
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// if (_IKaraoke.DEBUG)
	// Log.e(
	// __CLASSNAME__,
	// getMethodName()
	// + String.format("Greystripe device ID: %s", GSSdkInfo.getHashedDeviceId(this)));
	// }

	// private void initAdGreystripeInterstitial() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid + ":"
	// + getPackageName());
	//
	// String APP_ID = GREYSTRIPE_GUID_KOR;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = GREYSTRIPE_GUID_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = GREYSTRIPE_GUID_JAP;
	// }
	//
	// android.util.Log.d("Ads - Greystripe - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);
	//
	// //팝업처리
	// try {
	// mAdGreyStripeInterstitial = new GSFullscreenAd(this, APP_ID);
	// if (mAdGreyStripeInterstitial != null) {
	// mAdGreyStripeInterstitial.addListener(new GSAdListener() {
	//
	// @Override
	// public void onFetchedAd(GSAd ad) {
	// android.util.Log.w("Ads - Greystripe", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + ad);
	//
	// isShowAdPopup = true;
	// }
	//
	// @Override
	// public void onFailedToFetchAd(GSAd ad, GSAdErrorCode error) {
	// android.util.Log.w("Ads - Greystripe", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + error + ":" + ad);
	//
	// if (error == GSAdErrorCode.NO_AD || error == GSAdErrorCode.AD_EXPIRED) {
	// //showAdNoRewardAlert();
	// }
	//
	// initAdPopupDefault();
	// }
	//
	// @Override
	// public void onAdExpansion(GSAd ad) {
	// android.util.Log.w("Ads - Greystripe", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + ad);
	//
	//
	// }
	//
	// @Override
	// public void onAdDismissal(GSAd ad) {
	// android.util.Log.w("Ads - Greystripe", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + ad);
	//
	//
	// finish();
	// }
	//
	// @Override
	// public void onAdCollapse(GSAd ad) {
	// android.util.Log.w("Ads - Greystripe", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + ad);
	//
	//
	// }
	//
	// @Override
	// public void onAdClickthrough(GSAd ad) {
	// android.util.Log.w("Ads - Greystripe", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + ad);
	//
	//
	// }
	// });
	//
	// if (!mAdGreyStripeInterstitial.isAdReady()) {
	// mAdGreyStripeInterstitial.fetch();
	// }
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	//
	// if (_IKaraoke.DEBUG)
	// Log.e(
	// __CLASSNAME__,
	// getMethodName()
	// + String.format("Greystripe device ID: %s", GSSdkInfo.getHashedDeviceId(this)));
	// }
	//
	// public void showAdPopupGreystripe() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + mAdGreyStripeInterstitial.isAdReady());
	// if (mAdGreyStripeInterstitial.isAdReady()) {
	// mAdGreyStripeInterstitial.display();
	// }
	// }
	//
	// public void showAdPopListGreystripe() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName() + mAdGreyStripeInterstitial.isAdReady());
	// if (mAdGreyStripeInterstitial.isAdReady()) {
	// mAdGreyStripeInterstitial.display();
	// }
	// }

	// protected void initAdSponsorPay() {
	// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// //if (mAdSponsorPay != null) {
	// // return;
	// //}
	//
	// String APP_ID = SPONSORPAY_APP_ID;
	// String APP_KEY = SPONSORPAY_SECURITY_TOKEN;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = SPONSORPAY_APP_ID_KOR;
	// APP_KEY = SPONSORPAY_SECURITY_TOKEN_KOR;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = SPONSORPAY_APP_ID_JAP;
	// APP_KEY = SPONSORPAY_SECURITY_TOKEN_JAP;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = SPONSORPAY_APP_ID_NAVER;
	// // APP_TOKEN = SPONSORPAY_SECURITY_TOKEN_NAVER;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = SPONSORPAY_APP_ID_S5;
	// // APP_TOKEN = SPONSORPAY_SECURITY_TOKEN_S5;
	// }
	//
	// android.util.Log.d("Ads - SponsorPay - REWARD", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID + "-" + APP_KEY);
	//
	// SponsorPay.start(APP_ID, getApp().p_uid, APP_KEY, this);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdBannerSponsorPay() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// try {
	// //SponsorPayPublisher.requestOfferBanner(this, new SPOfferBannerListener() {
	// //
	// // @Override
	// // public void onSPOfferBannerAvailable(OfferBanner banner) {
	// // Log.e("Ads - SponsorPay", getMethodName());
	// // addAdBarBanner(banner.getBannerView(BaseAdActivity.this));
	// //
	// // showAdBanner(true);
	// // }
	// //
	// // @Override
	// // public void onSPOfferBannerNotAvailable(OfferBannerRequest bannerRequest) {
	// // Log.e("Ads - SponsorPay", getMethodName());
	// // }
	// //
	// // @Override
	// // public void onSPOfferBannerRequestError(OfferBannerRequest bannerRequest) {
	// // Log.e("Ads - SponsorPay", getMethodName() + "HTTP status code="
	// // + bannerRequest.getHttpStatusCode());
	// // }
	// //});
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdPopListSponsorPay() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// try {
	// mAdSponsorPay = SponsorPayPublisher.getIntentForOfferWallActivity(this, true);
	// startActivityForResult(mAdSponsorPay, 5689);
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// private void requestSponsorPayInterStitial() {
	// android.util.Log.d("Ads - SponsorPay - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@");
	//
	// SponsorPayPublisher.getIntentForInterstitialActivity(this, new SPInterstitialRequestListener() {
	//
	// @Override
	// public void onSPInterstitialAdNotAvailable() {
	//
	// android.util.Log.w("Ads - SponsorPay", "InitAds:" + getMethodName());
	// }
	//
	// @Override
	// public void onSPInterstitialAdError(String error) {
	//
	// android.util.Log.w("Ads - SponsorPay", "InitAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onSPInterstitialAdAvailable(Intent interstitial) {
	//
	// android.util.Log.w("Ads - SponsorPay", "InitAds:" + getMethodName() + interstitial);
	// if (interstitial != null) {
	// mAdSponsorPay = interstitial;
	// isShowAdPopup = true;
	// }
	//
	// }
	// });
	// }
	//
	// public void showAdPopupSponsorPay() {
	// try {
	// if (mAdSponsorPay != null) {
	// startActivityForResult(mAdSponsorPay, 2114);
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

	// /**
	// * <pre>
	// * // This is the app id that you entered into the Vungle Dashboard
	// * String app_id = &quot;com.Put.Your.App.Id.Here&quot;;
	// *
	// * // Optionally provide some demographic information
	// * int user_age = 36;
	// * int user_gender = VunglePub.Gender.UNKNOWN;
	// *
	// * // Initialize the VunglePub library
	// * VunglePub.init(this, VUNGLE_APP_ID, user_age, user_gender);
	// * </pre>
	// */
	// private void initAdVungle() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// try {
	// android.util.Log.d("Ads - Vungle", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + VUNGLE_APP_ID);
	//
	// VunglePub.init(this, VUNGLE_APP_ID);
	//
	// VunglePub.setBackButtonEnabled(true);
	//
	// VunglePub.setEventListener(new EventListener() {
	//
	// @Override
	// public void onVungleView(double watched, double length) {
	//
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName() + watched
	// + "/" + length);
	//
	// }
	//
	// @Override
	// public void onVungleAdStart() {
	//
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	//
	// @Override
	// public void onVungleAdEnd() {
	//
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// }
	// });
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// public void showAdPopListVungle() {
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// if (!VunglePub.isVideoAvailable(_IKaraoke.DEBUG)) {
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + "!!!!NG!!!!");
	// showAlertNoAd();
	// return;
	// }
	//
	// VunglePub.displayIncentivizedAdvert(getApp().p_uid, true);
	// }
	//
	// public void showAdPopupVungle() {
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName());
	//
	// if (!VunglePub.isVideoAvailable(_IKaraoke.DEBUG)) {
	// android.util.Log.w("Ads - Vungle", __CLASSNAME__ + ":initAds:" + getMethodName()
	// + "!!!!NG!!!!");
	// showAlertNoAd();
	// return;
	// }
	//
	// VunglePub.displayAdvert();
	// }
	private void initAdDirectTapBanner() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);

		String APP_ID = _IKaraoke.DIRECTTAP_APP_CODE;

		if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
			APP_ID = _IKaraoke.DIRECTTAP_APP_CODE_KPOP;
		} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
			APP_ID = _IKaraoke.DIRECTTAP_APP_CODE_JPOP;
		}

		android.util.Log.d("Ads - DirectTap - BANNER", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

		new DirectTap.Starter(this, APP_ID).setIconSize(60).setTestMode(_IKaraoke.DEBUG).start();

		mAdDirectTapBanner = new DirectTap.Banner(this).setBannerPosition(Gravity.TOP).setBannerFitScreenWidth(true).build();
	}

	private void initAdDirectTapInterstitial() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);

		String APP_ID = _IKaraoke.DIRECTTAP_APP_CODE;

		if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
			APP_ID = _IKaraoke.DIRECTTAP_APP_CODE_KPOP;
		} else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
			APP_ID = _IKaraoke.DIRECTTAP_APP_CODE_JPOP;
		}

		android.util.Log.d("Ads - DirectTap - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);

		new DirectTap.Starter(this, APP_ID).setIconSize(60).setTestMode(_IKaraoke.DEBUG).start();

		mAdDirectTapInterstitial = new DirectTap.FullScreen(this).setDirectTapListener(new DirectTapListener() {

			@Override
			public void onStartWaiting(Activity activity) {

				android.util.Log.w("Ads - DirectTap", __CLASSNAME__ + ":initAds:" + getMethodName() + activity);

			}

			@Override
			public boolean onShowNotPossible(Activity activity, int cause) {
				android.util.Log.w("Ads - DirectTap", __CLASSNAME__ + ":initAds:" + getMethodName() + "-" + cause + ":" + activity);


				initAdPopupDefault();

				return false;
			}

			@Override
			public void onShow(Activity activity) {

				android.util.Log.w("Ads - DirectTap", __CLASSNAME__ + ":initAds:" + getMethodName() + activity);

			}

			@Override
			public void onDismiss(Activity activity, int cause) {

				android.util.Log.w("Ads - DirectTap", __CLASSNAME__ + ":initAds:" + getMethodName() + "-" + cause + ":" + activity);

				finish();
			}
		});

		if (mAdDirectTapInterstitial != null) {
			isShowAdPopup = true;
		}
	}

	public void showAdBannerDirectTap() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mAdDirectTapBanner);

		if (mAdDirectTapBanner != null) {
			android.util.Log.d("Ads - DirectTap - BANNER", getPackageName() + ":" + __CLASSNAME__ + ":show:" + getMethodName());
			addAdBarBanner(mAdDirectTapBanner);
			showAdBanner(true);
			DirectTap.Banner.makeOverlayVisible(true);
		}
	}

	public void showAdPopupDirectTap() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + mAdDirectTapInterstitial);

		if (mAdDirectTapInterstitial != null) {
			android.util.Log.d("Ads - DirectTap - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":show:" + getMethodName());
			mAdDirectTapInterstitial.show();
		}
	}

	// private com.startapp.android.publish.StartAppAd mAdStartAppInterstitial;
	// private com.startapp.android.publish.banner.bannerstandard.BannerStandard mAdStartAppBanner;
	//
	// private void initAdStartAppBanner() {
	// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// String APP_ID = STARTAPP_APP_ID;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = STARTAPP_APP_ID_KPOP;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = STARTAPP_APP_ID_JPOP;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = STARTAPP_APP_ID_NAVER;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = STARTAPP_APP_ID_S5;
	// }
	//
	// android.util.Log.d("Ads - StartApp - BANNER", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);
	//
	// StartAppAd.init(this, STARTAPP_DEV_ID, APP_ID);
	//
	// mAdStartAppBanner = new com.startapp.android.publish.banner.bannerstandard.BannerStandard(this);
	// mAdStartAppBanner.load();
	// }
	//
	// private void initAdStartAppInterstitial() {
	// //if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ad_banner + ":" + ad_vendor + ":" + getApp().p_uid);
	//
	// String APP_ID = STARTAPP_APP_ID;
	//
	// if (_IKaraoke.APP_PACKAGE_NAME_KPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = STARTAPP_APP_ID_KPOP;
	// } else if (_IKaraoke.APP_PACKAGE_NAME_JPOP.equalsIgnoreCase(getPackageName())) {
	// APP_ID = STARTAPP_APP_ID_JPOP;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_NAVER.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = STARTAPP_APP_ID_NAVER;
	// //} else if (_IKaraoke.APP_PACKAGE_NAME_S5.equalsIgnoreCase(getPackageName())) {
	// // APP_ID = STARTAPP_APP_ID_S5;
	// }
	//
	// android.util.Log.d("Ads - StartApp - POPUP", getPackageName() + ":" + __CLASSNAME__ + ":initAds:" + getMethodName() + "@" + APP_ID);
	//
	// StartAppAd.init(this, STARTAPP_DEV_ID, APP_ID);
	//
	// mAdStartAppInterstitial = new StartAppAd(this);
	// mAdStartAppInterstitial.loadAd(new AdEventListener() {
	//
	// @Override
	// public void onReceiveAd(Ad arg0) {
	//
	// android.util.Log.w("Ads - StartApp", __CLASSNAME__ + ":initAds:" + getMethodName() + arg0);
	//
	// isShowAdPopup = true;
	// }
	//
	// @Override
	// public void onFailedToReceiveAd(Ad arg0) {
	//
	// android.util.Log.w("Ads - StartApp", __CLASSNAME__ + ":initAds:" + getMethodName() + arg0);
	//
	//
	// initAdPopupDefault();
	// }
	// });
	// }
	//
	// public void showAdBannerStartApp() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// if (mAdStartAppBanner != null) {
	// addAdBarBanner(mAdStartAppBanner);
	// showAdBanner(true);
	// }
	// }
	//
	// public void showAdPopupStartApp() {
	// if (_IKaraoke.DEBUG)
	// Log.e(__CLASSNAME__, getMethodName());
	//
	// if (mAdStartAppInterstitial != null) {
	// mAdStartAppInterstitial.showAd();
	// }
	// }
}
