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
 * project	:	Karaoke.APP
 * filename	:	BaseAdActivity2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseAdActivity2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

/**
 * 
 * TODO<br>
 * 
 * <pre>
 * 0) 애드립 - AD
 * <b>adlibr.3.8.jar</b>
 * <b>구분 : 광고</b>
 * 	ID/PW : aon@kymedia.kr/~aon6390
 * 	예) 배너충전 - "AD1"
 * 	광고구분 :
 * 		AD
 * 	광고종류 :
 * 		1. 배너충전
 * 		2. 팝업충전(이용권/보상광고)
 * 		3. 배너광고
 * 		4. 팝업광고
 * </pre>
 * 
 * @author isyoon
 * @since 2013. 11. 29.
 * @version 1.0
 */
public class BaseAdActivity2 extends BaseAdActivity {
	//
	// // 일반 Activity 에서의 adlib 연동
	// private AdlibManager _amanager;
	//
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	//
	// //_amanager = new AdlibManager();
	// //_amanager.onCreate(this);
	//
	// // 일반적인 연동은 추가적으로 구현필요
	// //setContentView(R.layout.main2);
	// //this.setAdsContainer(R.id.ads);
	// }
	//
	// protected void onDestroy() {
	// if (_amanager != null) {
	// _amanager.onDestroy(this);
	// }
	// super.onDestroy();
	// }
	//
	// // xml 에 지정된 ID 값을 이용하여 BIND 하는 경우
	// public void setAdsContainer(int rid) {
	// if (_amanager != null) {
	// _amanager.setAdsContainer(rid);
	// }
	// }
	//
	// // 동적으로 Container 를 생성하여, 그 객체를 통하여 BIND 하는 경우
	// public void bindAdsContainer(AdlibAdViewContainer a) {
	// if (_amanager != null) {
	// _amanager.bindAdsContainer(a);
	// }
	// }
	//
	// // 전면광고 ??
	// private void getInterstitialView(int i, int j, Handler handler) {
	//
	// if (_amanager != null) {
	// _amanager.getInterstitialView(getApp(), i, j, handler);
	// }
	// }
	//
	// // 전면광고 호출
	// public void loadInterstitialAd() {
	// if (_amanager != null) {
	// _amanager.loadInterstitialAd(this);
	// }
	// }
	//
	// // 전면광고 호출 (광고 수신 성공, 실패 여부를 받고 싶을 때 handler 이용)
	// public void loadInterstitialAd(Handler h) {
	// if (_amanager != null) {
	// _amanager.loadInterstitialAd(this, h);
	// }
	// }
	//
	// public void setVersionCheckingListner(AdlibVersionCheckingListener l) {
	// if (_amanager != null) {
	// _amanager.setVersionCheckingListner(l);
	// }
	// }
	//
	// // AD 영역을 동적으로 삭제할때 호출하는 메소드
	// public void destroyAdsContainer() {
	// if (_amanager != null) {
	// _amanager.destroyAdsContainer();
	// }
	// }
	//
	// // 애드립 연동에 필요한 구현부 끝
	//
	// private RelativeLayout iconBack;
	// private AdlibRewardIconView iconView;
	// private com.mocoplex.adlib.AdlibAdViewContainer avc = null;
	//
	// // AndroidManifest.xml에 권한과 activity를 추가하여야 합니다.
	// protected void initAdribr() {
	// try {
	// _amanager = new AdlibManager();
	// _amanager.onCreate(this);
	//
	// // AdlibActivity 를 상속받은 액티비티이거나,
	// // 일반 Activity 에서는 AdlibManager 를 동적으로 생성한 후 아래 코드가 실행되어야 합니다. (AdlibTestProjectActivity4.java)
	//
	// // Manifest 에서 <uses-permission android:name="android.permission.GET_TASKS" /> 부분 권한 추가를 확인해주세요.
	//
	// // 광고 스케줄링 설정을 위해 아래 내용을 프로그램 실행시 한번만 실행합니다. (처음 실행되는 activity에서 한번만 호출해주세요.)
	// // 광고 subview 의 패키지 경로를 설정합니다. (실제로 작성된 패키지 경로로 수정해주세요.)
	//
	// // 쓰지 않을 광고플랫폼은 삭제해주세요.
	// AdlibConfig.getInstance().bindPlatform("ADAM", "test.adlib.project.ads.SubAdlibAdViewAdam");
	// //AdlibConfig.getInstance().bindPlatform("ADMOB", "test.adlib.project.ads.SubAdlibAdViewAdmob");
	// AdlibConfig.getInstance().bindPlatform("CAULY", "test.adlib.project.ads.SubAdlibAdViewCauly");
	// AdlibConfig.getInstance().bindPlatform("TAD", "test.adlib.project.ads.SubAdlibAdViewTAD");
	// AdlibConfig.getInstance().bindPlatform("NAVER", "test.adlib.project.ads.SubAdlibAdViewNaverAdPost");
	// AdlibConfig.getInstance().bindPlatform("SHALLWEAD", "test.adlib.project.ads.SubAdlibAdViewShallWeAd");
	// //AdlibConfig.getInstance().bindPlatform("INMOBI", "test.adlib.project.ads.SubAdlibAdViewInmobi");
	// //AdlibConfig.getInstance().bindPlatform("MMEDIA", "test.adlib.project.ads.SubAdlibAdViewMMedia");
	// //AdlibConfig.getInstance().bindPlatform("MOBCLIX", "test.adlib.project.ads.SubAdlibAdViewMobclix");
	// //AdlibConfig.getInstance().bindPlatform("ADMOBECPM", "test.adlib.project.ads.SubAdlibAdViewAdmobECPM");
	// AdlibConfig.getInstance().bindPlatform("UPLUSAD", "test.adlib.project.ads.SubAdlibAdViewUPlusAD");
	// //AdlibConfig.getInstance().bindPlatform("MEZZO", "test.adlib.project.ads.SubAdlibAdViewMezzo");
	// //AdlibConfig.getInstance().bindPlatform("AMAZON", "test.adlib.project.ads.SubAdlibAdViewAmazon");
	// //AdlibConfig.getInstance().bindPlatform("ADHUB", "test.adlib.project.ads.SubAdlibAdViewAdHub");
	// // 쓰지 않을 플랫폼은 JAR 파일 및 test.adlib.project.ads 경로에서 삭제하면 최종 바이너리 크기를 줄일 수 있습니다.
	//
	// // SMART* dialog 노출 시점 선택시 / setAdlibKey 키가 호출되는 activity 가 시작 activity 이며 해당 activity가 종료되면 app 종료로 인식합니다.
	// // adlibr.com 에서 발급받은 api 키를 입력합니다.
	// // https://sec.adlibr.com/admin/dashboard.jsp
	// // ADLIB - API - KEY 설정
	// AdlibConfig.getInstance().setAdlibKey(ADLIBR_API_KEY);
	//
	// // 동적으로 adview 를 생성합니다.
	// if (avc == null) {
	// avc = new com.mocoplex.adlib.AdlibAdViewContainer(getApp());
	// LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	// avc.setLayoutParams(p);
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// protected void showAdPopupAdribr() {
	// if (_IKaraoke.DEBUG)Log.e("ADLIBr", getMethodName());
	//
	// try {
	// // 전면광고를 호출합니다.
	// //loadInterstitialAd();
	// // optional : 전면광고의 수신 성공, 실패 이벤트 처리가 필요한 경우엔 handler를 이용하실 수 있습니다.
	// loadInterstitialAd(new Handler() {
	// public void handleMessage(Message message) {
	// stopLoadingDialog(null);
	// try {
	// switch (message.what) {
	// case AdlibManager.DID_SUCCEED:
	// if (_IKaraoke.DEBUG)Log.e("ADLIBr", "onReceiveAd " + (String) message.obj);
	// break;
	// // 전면배너 스케줄링 사용시, 각각의 플랫폼의 수신 실패 이벤트를 받습니다.
	// case AdlibManager.DID_ERROR:
	// if (_IKaraoke.DEBUG)Log.e("ADLIBr", "onFailedToReceiveAd " + (String) message.obj);
	// break;
	// // 전면배너 스케줄로 설정되어있는 모든 플랫폼의 수신이 실패했을 경우 이벤트를 받습니다.
	// case AdlibManager.INTERSTITIAL_FAILED:
	// if (_IKaraoke.DEBUG)Log.e("ADLIBr", "All Failed.");
	// break;
	// case AdlibManager.INTERSTITIAL_CLOSED:
	// if (_IKaraoke.DEBUG)Log.e("ADLIBr", "onClosedAd " + (String) message.obj);
	// break;
	// }
	// } catch (Exception e) {
	//
	// }
	// }
	// });
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }
	//
	// protected void showAdDialogAdribr() {
	// // 전면배너를 이용하여 다른 형태로 구성하고 싶으신 경우 사용하는 method 입니다.
	//
	// // getInterstitialView(width(dp), maxHeight(dp), handler);
	// // width의 비율에 맞춰 height가 계산되어진 view를 반환합니다.
	// // 비율에 맞는 height가 maxHeight보다 클 경우에는 maxHeight에 맞춰 비율이 변형된 view를 반환합니다.
	// getInterstitialView(200, 300, new Handler() {
	// public void handleMessage(Message message) {
	// try {
	// switch (message.what) {
	// case AdlibManager.DID_SUCCEED:
	// AdlibInterstitialView iView = (AdlibInterstitialView) message.obj;
	//
	// // getViewHeight 함수를 통해 실제 노출될 height 값(dp)을 알 수 있습니다.
	// //int viewHeight = iView.getViewHeight();
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(BaseAdActivity2.this);
	// builder.setTitle("Title");
	// builder.setView(iView);
	//
	// builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	//
	// }
	// });
	//
	// builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	//
	// }
	// });
	//
	// AlertDialog alert = builder.create();
	// alert.show();
	// alert.getWindow().setLayout(dpToPx(230), LayoutParams.WRAP_CONTENT);
	//
	// break;
	// case AdlibManager.DID_ERROR:
	// Toast.makeText(BaseAdActivity2.this, "광고수신 실패 :(", Toast.LENGTH_SHORT).show();
	// break;
	// }
	// } catch (Exception e) {
	//
	// }
	// }
	// });
	// }
	//
	// protected void showAdribrIcon() {
	// if (_IKaraoke.DEBUG)Log.e("ADLIBr", getMethodName());
	//
	// hideAdribrIcon();
	//
	// Handler handler = new Handler() {
	// public void handleMessage(Message message) {
	// try {
	// switch (message.what) {
	// case AdlibManager.DID_SUCCEED:
	// iconView = (AdlibRewardIconView) message.obj;
	//
	// RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
	// iconView.getWidthSize(), iconView.getHeightSize());
	// params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
	// params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
	// params.leftMargin = dpToPx(40);
	// params.topMargin = dpToPx(40);
	//
	// if (iconBack == null) {
	// iconBack = new RelativeLayout(BaseAdActivity2.this);
	// addContentView(iconBack, new LayoutParams(LayoutParams.MATCH_PARENT,
	// LayoutParams.MATCH_PARENT));
	// }
	// iconBack.addView(iconView, params);
	// break;
	// case AdlibManager.DID_ERROR:
	// break;
	// }
	// } catch (Exception e) {
	//
	// }
	// }
	// };
	//
	// AdlibRewardLink.getInstance().getRewardLinkInfo(this, ADLIBR_API_KEY, handler);
	// }
	//
	// protected void hideAdribrIcon() {
	// if (iconBack != null) {
	// iconBack.removeAllViews();
	// iconView = null;
	// }
	// }
	//
	// //protected void onPause() {
	// // _amanager.onPause();
	// // super.onPause();
	// //}
	// @Override
	// protected void onPause() {
	// if (_amanager != null) {
	// _amanager.onPause();
	// }
	//
	// //// 리워드 링크 아이콘을 사용하는 Activity 에서는 반드시 onPause 에서 pauseRewardLink(Context ctx) 를 호출해 주세요.
	// //AdlibRewardLink.getInstance().pauseRewardLink(this);
	// super.onPause();
	// }
	//
	// //protected void onResume() {
	// // _amanager.onResume(this);
	// // super.onResume();
	// //}
	// @Override
	// protected void onResume() {
	// if (_amanager != null) {
	// _amanager.onResume(this);
	// }
	//
	// //// 리워드 링크 아이콘을 배치하기 위해서는 Activity 의 onResume 에서 rewardLink(Context ctx, String rewardLinkId, int x, int y, int align) 를 호출해 주세요.
	// //
	// //// x축, y축 padding은 아래와 같이 pixel 값(+)으로 직접 계산해 주셔야 합니다.
	// //Display mDisplay = getWindowManager().getDefaultDisplay();
	// //int width = mDisplay.getWidth();
	// //int h = dpToPx(90);
	// //
	// //// rewardLinkId는 애드립 홈페이지에서 발급받은 링크 ID로 대체하세요.
	// //// align 위치를 기준으로 x축으로 x pixel, y축으로 y pixel만큼 이동한 지점에 아이콘이 배치됩니다.
	// //// 아이콘 위치는 아이콘의 중심점 기준입니다.
	// //// 아래의 경우 좌측 하단을 중심으로 아이콘의 중심점이 x축으로 디바이스 width의 절반만큼, y축으로 90dp만큼 이동한 위치에 아이콘이 배치됩니다.
	// //AdlibRewardLink.getInstance().rewardLink(this, ADLIBR_API_KEY, width / 2, h,
	// // AdlibRewardIcon.ALIGN_LEFT_BOTTOM); // <-- 테스트 키 입니다.
	// super.onResume();
	// }
	//
	// public int dpToPx(int dp) {
	// return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
	// }
	//
	// @Override
	// boolean initAd(String ad_vendor) {
	//
	// boolean ret = super.initAd(ad_vendor);
	//
	// if (("").equalsIgnoreCase(ad_vendor)) {
	// } else if (("AD1").equalsIgnoreCase(ad_vendor)) {
	// initAdribr();
	// } else if (("AD2").equalsIgnoreCase(ad_vendor)) {
	// initAdribr();
	// } else if (("AD3").equalsIgnoreCase(ad_vendor)) {
	// initAdribr();
	// } else if (("AD4").equalsIgnoreCase(ad_vendor)) {
	// initAdribr();
	// }
	//
	// return ret;
	// }
	//
	// @Override
	// boolean showAdPopup(String ad_vendor) {
	//
	// boolean ret = super.showAdPopup(ad_vendor);
	//
	// if (("AD1").equalsIgnoreCase(ad_vendor)) {
	// } else if (("AD2").equalsIgnoreCase(ad_vendor)) {
	// showAdPopupAdribr();
	// ret = true;
	// } else if (("AD3").equalsIgnoreCase(ad_vendor)) {
	// } else if (("AD4").equalsIgnoreCase(ad_vendor)) {
	// showAdPopupAdribr();
	// ret = true;
	// }
	//
	// stopLoadingDialog(null);
	//
	// return ret;
	// }
	//
	// @Override
	// boolean showAdBanner(String ad_banner) {
	//
	// boolean ret = super.showAdBanner(ad_banner);
	//
	// if (("AD1").equalsIgnoreCase(ad_banner)) {
	// addAdBarBanner(avc);
	// bindAdsContainer(avc);
	// } else if (("AD2").equalsIgnoreCase(ad_banner)) {
	// addAdBarBanner(avc);
	// bindAdsContainer(avc);
	// } else if (("AD3").equalsIgnoreCase(ad_banner)) {
	// addAdBarBanner(avc);
	// bindAdsContainer(avc);
	// } else if (("AD4").equalsIgnoreCase(ad_banner)) {
	// addAdBarBanner(avc);
	// bindAdsContainer(avc);
	// }
	//
	// return ret;
	// }
	//
	// @Override
	// protected void removeAdBanner() {
	//
	// try {
	// if (avc != null) {
	// destroyAdsContainer();
	// avc = null;
	// }
	// super.removeAdBanner();
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	// }
	// }

}
