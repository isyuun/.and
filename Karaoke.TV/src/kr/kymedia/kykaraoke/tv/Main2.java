package kr.kymedia.kykaraoke.tv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.kykaraoke.api.IKaraokeTV;
import kr.kymedia.kykaraoke.api._Const;
import kr.kymedia.kykaraoke.api._Download;
import kr.kymedia.kykaraoke.api._KPRequest;
import kr.kymedia.kykaraoke.api._VASS;
import kr.kymedia.kykaraoke.tv.app._PlayActivity;
import kr.kymedia.kykaraoke.tv.data.CustomerItem;
import kr.kymedia.kykaraoke.tv.data.ListenItem;
import kr.kymedia.kykaraoke.tv.data.SongItem;
import kr.kymedia.kykaraoke.tv.data.SubMenuItem;
import kr.kymedia.kykaraoke.tv.data.TicketItem;
import kr.kymedia.kykaraoke.tv.data._Util;
import kr.kymedia.kykaraoke.tv.play._Listen;
import kr.kymedia.kykaraoke.tv.play._PlayView;

//for box

/**
 * <pre>
 * home.xml레이아웃적용(설정)
 * sing_list2.xml레이아웃적용
 * 반주곡리스트셀렉터처리
 * </pre>
 * <p/>
 * Copy of {@link kr.kymedia.kykaraoke.Main}
 *
 * @author isyoon
 * @version 1.0
 * @since 2015. 1. 28.
 */
class Main2 extends _PlayActivity implements _Const {
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

	protected void _LOG(String method, Exception e) {
		Log.wtf(_toString() + TAG_ERR + "[AndroidRuntime][System.err]", "[NG]" + method + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + "\n" + Log.getStackTraceString(e));
		setBottomProductText(getString(R.string.message_error_exit_app));
	}

	ViewGroup m_layoutMain;
	ViewGroup layout_menu_sub;
	ViewGroup layout_content;
	LinearLayout m_layoutSongListDetail;
	LinearLayout m_layoutListeningOther = null;
	LinearLayout message_ok = null;

	protected _Remote remote = new _Remote();
	protected _KPRequest KP;
	protected _KPRequest _KP_1012;
	protected _VASS VASS;
	protected _Download download;
	_Util util;
	_Util util_profileHome = null;
	_Util util_profile01 = null;
	_Util util_profile02 = null;
	_Util util_profile03 = null;
	_Util util_profile04 = null;
	_Util util_profile05 = null;
	_Util util_profile06 = null;
	_Util util_profile07 = null;
	_Util util_profile08 = null;
	_Util util_profileListeningOther = null;
	_Listen listen = null;

	protected _Listen getListen() {
		return listen;
	}


	// Common
	protected String sdPath = "";
	protected String video_url_back = "";
	protected int m_iPaneState = PANE_HOME;
	//protected int m_iListBackgroundResourceID;
	protected int m_iRequestPage = 1;
	protected boolean m_bShowMessageOK = false;
	protected boolean m_bListAlreadyReflashed = false;
	private boolean m_bIsHiddenMenu = false;

	protected boolean isShowMenu() {
		return !m_bIsHiddenMenu;
	}

	/**
	 * @see Main2X#ShowMenu(String)
	 */
	protected void ShowMenu(String method) {
		m_bIsHiddenMenu = false;
	}

	/**
	 * @see Main2X#HideMenu(String)
	 */
	protected void HideMenu(String method) {
		m_bIsHiddenMenu = true;
	}

	public Typeface m_typeface;
	// public static Activity ActivityMain = null;
	//protected Drawable m_drawBackground;
	protected static final int IME_SHOW = 9999;

	// 셋탑정보
	public String m_strSTBVender = "";
	protected String p_stbid = "";
	protected String m_orgSTBID = "";
	protected String p_model = "";
	protected String p_mac = "";
	protected String p_readmac = "";
	protected String m_strSTBVER = "";

	// KP
	protected String p_domain = P_DOMAIN_BTV;
	protected String p_appname = "";
	protected String p_apkname;
	protected String p_debug = "";
	protected String p_market = "";
	protected String p_ver = "";
	protected String p_mid = "";
	protected String p_account = "";
	protected String p_ncode = "";
	protected String p_lcode = "";
	protected String p_os = "";
	protected String p_osversion = "";
	protected int p_apkversioncode = 0;
	protected String p_apkversionname = "";
	protected boolean isPassUser() {
		return false;
	}
	protected String p_passcnt = "";
	protected String p_apikey = "";

	// 점수
	LinearLayout m_layoutScore;
	private Timer m_timerHideScore = null;

	// 메세지 박스
	LinearLayout message_common = null;
	Timer m_timerHideMessageCommon = null;
	LinearLayout message_okcancel = null;
	Timer m_timerHideMessageOkCancel = null;
	Timer m_timerShowMessageNotResponse = null;
	TaskShowMessageNotResponse m_taskShowMessageNotResponse = null;
	int m_iMessageOkCancelFocusX = POPUP_CANCEL;
	boolean m_bIsExit = false;

	@Override
	public boolean isFinishing() {
		return super.isFinishing() || (null != video && video.isFinishing());
	}

	// 곡번호 검색
	String m_strInputNumber = "00000";
	String m_strRequestInputNumber = "";
	String m_strNumberSearchResult = "";
	String m_strSearchID = "";
	boolean m_bIsNumberSearch = false;
	boolean m_bHaveNumberSearchResult = false;

	// 이용권 구매
	public String m_strVASSPassword = "";
	///**
	// * 븅신이건머고
	// */
	//public String p_passtype = "";
	/**
	 * 븅신이건머냐
	 */
	//int m_iProcessTicket = 0;
	//public boolean m_bIsFocusedOnTicket = false;
	public boolean m_bIsFocusedOnPassNumber = false;
	public boolean m_bIsGoToCertifyMessage = false;
	public boolean m_bIsGoToPurchaseMessage = false;

	// 이용권
	int m_iTicketMessageFocusX = POPUP_OK;
	int m_iTicketMessageFocusY = 1;
	protected LinearLayout message_ticket = null;
	String m_strInputPass[] = {"0", "0", "0", "0"};
	/**
	 * 인증 센터 - 휴대폰 번호 입력
	 */
	String m_strHPNumber = "";
	/**
	 * 인증 센터 - 휴대폰 번호 입력
	 */
	String auth_phoneno = "";
	/**
	 * 휴대폰 번호 등록
	 */
	LinearLayout message_hp = null;
	/**
	 * 인증 번호 등록 - 인증번호
	 */
	String mStrCertifyNumber = "";
	/**
	 * 인증 번호 등록
	 */
	LinearLayout message_hp_certify = null;
	/**
	 * 이벤트
	 */
	LinearLayout message_hp_event = null;

	// 홈
	String m_strMainSingID = "";
	String m_strMainEventID;
	String m_strMainCustomerID;
	ImageView m_imgProfileHome = null;
	ViewGroup m_layoutHome = null;

	// 반주곡 리스트
	int m_iSongItemCount = 0;
	int m_iCurrentSongListPage = 1;
	int m_iCurrentViewSongListPage;
	int m_iTotalSongListPage = 1;
	//븅신먼지랄이냐.
	//private boolean m_bIsGenre = false;
	protected boolean isGenre() {
		if (null != remote) {
			return remote.isGenre();
		}
		return false;
	}

	ArrayList<SongItem> mSongItems = new ArrayList<SongItem>();
	ArrayList<String> mSongFavors = new ArrayList<String>();

	// 애창곡
	boolean m_bIsRefreshFavorList = false;
	String m_strRequestFavorSongID;

	// 녹음곡 리스트
	int m_iSetListenItemCount = 0;
	int m_iListenItemCount = 0;
	int m_iCurrentListenListPage = 1;
	int m_iCurrentViewListenListPage = 1;
	int m_iTotalListenListPage;
	ImageView m_imgProfile01 = null;
	ImageView m_imgProfile02 = null;
	ImageView m_imgProfile03 = null;
	ImageView m_imgProfile04 = null;
	ImageView m_imgProfile05 = null;
	ImageView m_imgProfile06 = null;
	ImageView m_imgProfile07 = null;
	ImageView m_imgProfile08 = null;
	ImageView m_imgProfileOther = null;
	LinearLayout m_layoutListenListFocus = null;
	ArrayList<ListenItem> mListenItems = new ArrayList<ListenItem>();
	ArrayList<ListenItem> mListenOtherItems = new ArrayList<ListenItem>();

	// 이 녹음곡의 다른 사람 녹음곡
	int m_iSetListenOtherItemCount = 0;
	int m_iListenOtherItemCount = 0;
	int m_iCurrentListenOtherListPage = 1;
	int m_iCurrentViewListenOtherListPage = 1;
	int m_iTotalListenOtherListPage;
	int m_iListenOtherRequestPage = 1;
	String m_strListenOtherListSongID = "";

	// 공지사항or이용안내
	int m_iCurrentCustomerListPage = 1;
	int m_iCustomerListItemCount;
	int m_iCurrentViewCustomerListPage;
	int m_iTotalCustomerListPage;
	int m_iCustomerListDetailTotalPage = 1;
	int m_iCurrentCustomerListDetailPage = 1;
	String m_strRequestCustomerDetailID;
	ArrayList<CustomerItem> mCustomerItems = new ArrayList<CustomerItem>();
	ArrayList<String> url_imgs = new ArrayList<String>();

	// 공지사항or이용안내 상세
	int m_iCustomerListDetailPage = 1;
	String m_strCustomerListDetailTitle;
	String m_strCustomerListDetailDate;
	String m_iEnterCustomerMenu = CUSTOMER_ENTER_KEY;

	// 이벤트 상세
	String b_type = "";
	String term_date = "";
	String e_stats = "";
	boolean m_bIsEventDetail = false;
	boolean m_bDisplayingCustomerDetail = false;
	Bitmap m_bitMapEventOn = null;
	Bitmap m_bitMapEventOff = null;

	// 이벤트 응모
	String m_strEventPopupMessage01 = "";
	String m_strEventPopupMessage02 = "";
	String m_strEventPopupMessage03 = "";
	String m_strEventHP = "";

	// 검색 기능
	int m_iSearchSelfMode = TITLE;
	int m_iSearchLetterMode;
	int m_iSearchLetterDisplay = ON;
	String m_strSearchWord = "";

	// 검색 리스트
	int m_iSearchListItemCount = 0;
	int m_iCurrentSearchListPage = 1;
	int m_iCurrentViewSearchListPage = 1;
	int m_iTotalSearchListPage;
	int m_iRequestSearchListPage = 1;
	ArrayList<SongItem> mSearchItems = new ArrayList<SongItem>();

	/***
	 * 반주중곡번호
	 */
	public String m_strRequestPlaySongID = "";
	/**
	 * 예약된곡번호
	 */
	protected ArrayList<String> song_ids = new ArrayList<String>();
	/**
	 * 반주용MP3주소
	 */
	protected String url_skym = "";
	/**
	 * 가사용skym주소
	 */
	protected String url_lyric = "";
	///**
	// * 가사용skym경로
	// */
	//protected ArrayList<String> path_lyrics = new ArrayList<String>();
	/**
	 * 배경역상주소
	 */
	protected String video_url = "";
	/**
	 * 머여(?)
	 */
	protected String type = "";

	// 녹음곡 재생
	String record_id = "";
	String m_strListenSongUrl = "";
	String m_strListeningTitle = "";
	String m_strListeningNick = "";
	String m_strListeningRecommand = "";
	String m_strListeningSongID = "";
	LinearLayout m_layoutListen;
	ArrayList<String> mListenOtherItemsRecordID = new ArrayList<String>();

	/**
	 * RETURN_DATA=상품코드^(성공/실패)^셋탑아이디^구매번호(TID)^이용권만료일자
	 */
	String RETURN_DATA = "";

	int m_iPlayVideoCount = 1;
	_Video video = null;

	public _Video getVideo() {
		return video;
	}

	public void setVideo(_Video video) {
		this.video = video;
	}

	int m_iDisplayWidth = 0;
	int m_iDisplayHeight = 0;

	// 휴대폰 인증
	boolean m_bIsCertifyedUser = false;
	boolean m_bIsCertifyTimerActivated = false;
	boolean mIsCertifyValidCheck = false;
	CountDownTimer m_cdTimer = null;
	CountDownTimer m_cdTimerPopup = null;
	int m_iCertifyTimerMin = 3;
	int m_iCertifyTimerSec = 0;
	int m_iCertifyTimerMinPopup = 3;
	int m_iCertifyTimerSecPopup = 0;
	int auth_modify_idx = 0;
	String m_strResponseAuthNumber = "";
	_Util util_certifyProfile = null;
	ImageView m_imgCertifyProfile = null;

	// (마이)녹음곡
	boolean m_bIsMyRecordReCertifyBtnFocused = false;
	_Util util_myRecordProfile = null;
	ImageView m_imgMyRecordProfile = null;

	ImageView m_imgMainQuickBtn01 = null;
	ImageView m_imgMainQuickBtn02 = null;
	_Util util_mainQuickBtnOn01 = null;
	_Util util_mainQuickBtnOff01 = null;
	_Util util_mainQuickBtnOn02 = null;
	_Util util_mainQuickBtnOff02 = null;
	SubMenuItem submenuQuickBtn02 = new SubMenuItem();

	//public TicketItem getTicketItemYear() {
	//	if (BuildConfig.DEBUG) Log.d("[KP]" + _toString(), "[KP]" + getMethodName() + _KP_4000.ticketItemYear);
	//	if (null != _KP_4000) {
	//		return _KP_4000.ticketItemYear;
	//	} else {
	//		return null;
	//	}
	//}
	//
	//public TicketItem getTicketItemMonth() {
	//	if (BuildConfig.DEBUG) Log.d("[KP]" + _toString(), "[KP]" + getMethodName() + _KP_4000.ticketItemMonth);
	//	if (null != _KP_4000) {
	//		return _KP_4000.ticketItemMonth;
	//	} else {
	//		return null;
	//	}
	//}
	//
	//public TicketItem getTicketItemDay() {
	//	if (BuildConfig.DEBUG) Log.d("[KP]" + _toString(), "[KP]" + getMethodName() + _KP_4000.ticketItemDay);
	//	if (null != _KP_4000) {
	//		return _KP_4000.ticketItemDay;
	//	} else {
	//		return null;
	//	}
	//}
	//
	//public TicketItem getTicketItemCoupon() {
	//	if (BuildConfig.DEBUG) Log.d("[KP]" + _toString(), "[KP]" + getMethodName() + _KP_4000.ticketItemCoupon);
	//	if (null != _KP_4000) {
	//		return _KP_4000.ticketItemCoupon;
	//	} else {
	//		return null;
	//	}
	//}

	boolean m_bPlayMenuBackground = false;
	//int m_iCouponFocus = 1;
	String m_strCouponSerial = "";
	_Util util_shopItem01 = null;
	_Util util_shopItem02 = null;
	Bitmap m_bitMapShopItem01 = null;
	Bitmap m_bitMapShopItem02 = null;
	boolean m_bCouponUser = false;
	String m_strCouponTerm = "";
	// private final Bitmap m_bitMapKYLogo = null;
	_Util util_kyLogo = null;

	int m_iVideoState = PLAY_STOP;

	boolean m_bIsFocusedOnBook = false;

	_Util util_MIC = null;
	Bitmap m_bitMapMic = null;

	boolean m_bIsBeforeRegistMyRecord = true;

	// for BOX
	// SystemInfo si = new SystemInfo();
	// SystemInfo bi = new SystemInfo();
	protected boolean m_bLoadMICLibrary = false;

	Animation animLoadingRotate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		video = get_Application().getVideoActivity();

		Display display = getWindowManager().getDefaultDisplay();
		Point displaySize = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			display.getRealSize(displaySize);
		} else {
			display.getSize(displaySize);
		}
		m_iDisplayHeight = displaySize.y;
		m_iDisplayWidth = displaySize.x;

		Log.e(_toString() + TAG_MAIN, "onCreate() " + displaySize);

		setContentView(R.layout.home);

		/*
		 * 취득한 세탑 정보 등을 화면에 뿌릴 때 쓴다 (상단 예약곡 텍스트뷰)
		 * TextView txtEngageListMain = (TextView)findViewById(R.id.txt_top_engage_list_main);
		 * txtEngageListMain.setText(m_iDisplayWidth + "_" + m_iDisplayHeight);
		 */
		// m_imgMainQuickBtn02 = (ImageView) findViewById(R.id.img_main_quick_2);

		setBaseLayout();

		setContentViewKaraoke(m_layoutHome);

		if (!m_bPlayMenuBackground) {
			showBackBoard();
		}

		_childViews();

		// if (m_strSTBVender != SMART_TEST) {
		// HideVirtualRemote();
		// }
		HideVirtualRemote();

		addLoading();

		startLoading(getMethodName(), LOADING_LONG);
	}

	private void _childViews() {
		ViewGroup main = (ViewGroup) findViewById(android.R.id.content);
		_childViews(main);

	}

	@Override
	protected void onStart() {
		// if (P_DEVICE == SMART_BOX) {
		// // for BOX
		// if (m_bLoadMICLibrary) {
		// Miclib.openMic();
		// }
		// }

		super.onStart();
	}

	/**
	 * <pre>
	 * 데모어플은계속간다~~~
	 * </pre>
	 */
	@Override
	protected void onStop() {
		super.onStop();

		if (P_APPNAME_DEMO.equalsIgnoreCase(m_strSTBVender)) {
			return;
		}

		// if (P_DEVICE == SMART_BOX) {
		// // for BOX
		// if (m_bLoadMICLibrary) {
		// Miclib.closeMic();
		// }
		// }

		// 1. 반주곡 STOP
		// Play playActivity = (Play)Play.ActivityPlay;
		// if (playActivity != null) {
		// stopPlay(PLAY_STOP);
		// }
		stopPlay(PLAY_STOP);

		// 2. 녹음곡 STOP
		if (isListening()) {
			exitListening();
		}

	}

	/**
	 * 데모어플은계속간다~~~쭉~~~
	 */
	@Override
	protected void onPause() {
		super.onPause();

		/**
		 * 데모어플은계속간다~~~쭉~~~
		 */
		if (P_APPNAME_DEMO.equalsIgnoreCase(m_strSTBVender)) {
			return;
		}

		/*
		 * Bbox 예약 시청 알림 팝업
		 */
		// 1. 반주곡 재생 중이면 배경화면을 이미지로 전환
		// Play playActivity = (Play)Play.ActivityPlay;
		// if (playActivity != null) {
		// ///*
		// //if (video.isPlaying() && getVideoState() == STOP) {
		// // video.stopBackgroundVideo(m_strMainBackgroundVideoUrl, PAUSE);
		// //
		// // Intent intent = new Intent(_Main1.this, Play.class);
		// // intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		// // startActivity(intent);
		// //
		// // TaskPauseVideo temp = new TaskPauseVideo();
		// // Timer tSetPause = new Timer();
		// // tSetPause.schedule(temp, 500);
		// //}
		// //*/
		// stopPlay(PLAY_STOP);
		// ShowMessageCommon(CLOSE_AUTO_LONG, getString(R.string.common_info), "다른 서비스의 실행으로 반주곡이 중지되었습니다.");
		// }
		if (isPlaying()) {
			stopPlay(PLAY_STOP);
			ShowMessageCommon(CLOSE_AUTO_LONG, getString(R.string.common_info), "다른 서비스의 실행으로 반주곡이 중지되었습니다.");
		}

		// 2. 녹음곡 중지
		if (isListening()) {
			exitListening();
			ShowMessageCommon(CLOSE_AUTO_LONG, getString(R.string.common_info), "다른 서비스의 실행으로 녹음곡이 중지되었습니다.");
		}
	}

	class TaskPauseVideo extends TimerTask {
		@Override
		public void run() {
			setVideoState(PLAY_PAUSE);
		}
	}

	public int getVideoState() {
		return m_iVideoState;
	}

	public void setVideoState(int state) {
		m_iVideoState = state;
	}

	View mLayoutListBack;
	View mArrowListLeft;
	View mArrowListRight;

	public void setContentViewKaraoke(View view) {
		boolean bInit = false;

		String strEngage = "";
		String strEngageList = "";
		String strSongNumber = "00000";

		TextView txtEngage = (TextView) findViewById(R.id.txt_top_engage);
		TextView txtEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		TextView txtSongNumber = (TextView) findViewById(R.id.txt_top_song_number);

		if (txtEngage != null && txtEngage.getText() != null) {
			strEngage = /*(String) */txtEngage.getText().toString();
		}
		if (strEngageList != null && txtEngageList.getText() != null) {
			strEngageList = /*(String) */txtEngageList.getText().toString();
		}
		if (strSongNumber != null && txtSongNumber.getText() != null) {
			strSongNumber = /*(String) */txtSongNumber.getText().toString();
		}

		if (view == m_layoutHome) {
			m_iPaneState = PANE_HOME;
			m_layoutHome.setVisibility(View.VISIBLE);
		} else {
			m_iPaneState = PANE_MAIN;
			m_layoutHome.setVisibility(View.INVISIBLE);
		}

		if (layout_menu_sub == null) {
			bInit = true;
		}

		if (bInit) {
			// if (m_strSTBVender != SMART_TEST) {
			HideVirtualRemote();
			// }

			// 2Depth 서브메뉴
			layout_menu_sub = (ViewGroup) findViewById(R.id.layout_menu_sub);

			// 3Depth 컨텐츠
			layout_content = (ViewGroup) findViewById(R.id.layout_content);

			mLayoutListBack = findViewById(R.id.layout_list_back);

			mArrowListLeft = findViewById(R.id.list_arrow_left);

			mArrowListRight = findViewById(R.id.list_arrow_right);
		}

		// 상단 예약 현황을 새로 갱신(?)

		if (!strEngage.equals("")) {
			txtEngage.setText(strEngage);
			txtEngageList.setText(strEngageList);
		}

		if (!strSongNumber.equals("00000")) {
			SetTopNumber(strSongNumber);
		}

		if (!m_bPlayMenuBackground) {
			if (!isPlaying()) {
				showBackBoard();
			} else {
				hideBackBoard();
			}
		}

		// 븅신아!!!머하러
		// ImageView logo = (ImageView) findViewById(R.id.img_ky_logo);
		// logo.setImageBitmap(m_bitMapKYLogo);
	}

	/**
	 * 일단까고
	 */
	@Override
	public void addContentView(View view, LayoutParams params) {

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(view) + ":" + view + ", " + params);

		super.addContentView(view, params);

		_childViews(view);
	}

	/**
	 * 팝업전용
	 */
	private void addPopup(View view, LayoutParams params) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(view) + ":" + view + ", " + params);
		addContentView(view, params);
	}

	protected void addView(ViewGroup parent, int layout, int id) {
		//if (parent != null) {
		//	parent.setVisibility(View.INVISIBLE);
		//}
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ViewGroup g = (ViewGroup) inflate(layout, null);
		g.setLayoutParams(params);
		addView(parent, g);
		//if (parent != null) {
		//	parent.setVisibility(View.VISIBLE);
		//}
	}

	protected void addView(ViewGroup p,  View c, LayoutParams params) {
		//if (parent != null) {
		//	parent.setVisibility(View.INVISIBLE);
		//}
		c.setLayoutParams(params);
		addView(p, c);
		//if (parent != null) {
		//	parent.setVisibility(View.VISIBLE);
		//}
	}

	protected void removeAllViewsContent() {
		if (layout_content != null) {
			layout_content.removeAllViews();
		}
	}

	protected void removeAllViewsMenuSub() {
		if (layout_menu_sub != null) {
			layout_menu_sub.removeAllViews();
		}
	}

	public void addViewListenItem(ViewGroup parent, ViewGroup view) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(view) + ":" + view + "->" + parent);

		addView(parent, view);
	}

	/**
	 * 여서보인다.
	 *
	 * @see #addView(ViewGroup, int, int)
	 */
	protected void addViewSingMenu() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_menu_sub, R.layout.menu_sing, R.id.menu_sing);
		// m_parentSubMenu.findViewById(R.id.sing_sub).setVisibility(View.VISIBLE);
	}

	protected void addViewSingList() {
	}

	/**
	 * 여서보인다.
	 *
	 * @see #addView(ViewGroup, int, int)
	 */
	protected void addViewMyMenu() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_menu_sub, R.layout.menu_my, R.id.menu_my);
		// m_parentSubMenu.findViewById(R.id.my_sub).setVisibility(View.VISIBLE);
	}

	protected void addViewMyList() {
	}

	/**
	 * 여서보인다.
	 *
	 * @see #addView(ViewGroup, int, int)
	 */
	protected void addViewListenMenu() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "m_iMenuListenFocus:" + remote.m_iMenuListenFocus);
		addView(layout_menu_sub, R.layout.menu_listen, R.id.menu_listen);
		// m_parentSubMenu.findViewById(R.id.listen_sub).setVisibility(View.VISIBLE);
	}

	protected void addViewListenList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "m_iMenuListenFocus:" + remote.m_iMenuListenFocus);
		addView(layout_content, R.layout.listen_list, R.id.listen_list);
	}

	/**
	 * 여서보인다.
	 *
	 * @see #addView(ViewGroup, int, int)
	 */
	protected void addViewShopMenu() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_menu_sub, R.layout.menu_shop, R.id.menu_shop);
	}

	protected void addViewShopList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_content, R.layout.shop_ticket, R.id.shop_ticket);
	}

	/**
	 * 여서보인다.
	 *
	 * @see #addView(ViewGroup, int, int)
	 */
	protected void addViewCustomerMenu() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_menu_sub, R.layout.menu_customer, R.id.menu_customer);
		// m_parentSubMenu.findViewById(R.id.customer_sub).setVisibility(View.VISIBLE);
		m_bDisplayingCustomerDetail = false;
	}

	protected void addViewCustomerList() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_content, R.layout.customer_list, R.id.customer_list);
		m_bDisplayingCustomerDetail = false;
	}

	protected void addViewCustomerListEvent() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_content, R.layout.customer_list_event, R.id.customer_list_event);
		m_bDisplayingCustomerDetail = false;
	}

	protected void addViewCustomerApp() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_content, R.layout.customer_app, R.id.customer_app);
		m_bDisplayingCustomerDetail = false;
	}

	protected void addViewCustomerMic() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName());
		addView(layout_content, R.layout.customer_mic, R.id.customer_mic);
		m_bDisplayingCustomerDetail = false;
		//마이크이미지업데이트
		ImageView imgMic = (ImageView) findViewById(R.id.img_mic);
		if (imgMic != null) {
			// imgMic.setImageResource(R.drawable.help_buy_mike_stb);
			imgMic.setImageBitmap(util_MIC.m_bitMap);
		}
	}

	/**
	 * <pre>
	 * 좀!!!날리지좀마라
	 * 일단가리고...
	 * 존나일주일간븅신됐다...
	 * </pre>
	 */
	protected void addView(final ViewGroup p, final ViewGroup g) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + getResourceEntryName(g) + ":" + g + "->" + p);

		p.removeAllViews();
		p.addView(g);

		_childViews(g);

		requestFocus(g);
	}

	private void addView(final ViewGroup p, final View c) {

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + getResourceEntryName(c) + ":" + c + "->" + p);

		p.addView(c);

		_childViews(c);

		requestFocus(c);
	}

	//private void addView(final ViewGroup p, final View c) {
	//
	//	if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + getResourceEntryName(c) + ":" + c + "->" + p);
	//
	//	p.removeAllViews();
	//	p.addView(c);
	//
	//	_childViews(p);
	//}

	/**
	 * 폰트좀가만놔둔다.
	 */
	public Typeface getTypeface() {
		return m_typeface;
	}

	/**
	 * <pre>
	 * 븅신대삽질-아주뒤지라고빡을치지
	 *   뷰그룹에뷰추가할때마다
	 *   호출해야한다.안하믄???
	 *   폰트좀가만놔둔다.
	 * </pre>
	 *
	 * @see Main2#addContentView(View, LayoutParams)
	 * @see Main2#addView(ViewGroup, ViewGroup)
	 * @see Main2#addView(ViewGroup, View)
	 * @see Main2#_childViews()
	 * @see Main3#setContentViewKaraoke(View)
	 * @see Main3XXXXX#addViewShopTabs()
	 */
	protected void _childViews(View p) {
		// if (BuildConfig.DEBUG) _LOG.d(_toString(), getMethodName() + p);
		if (p instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) p).getChildCount(); i++) {
				View c = ((ViewGroup) p).getChildAt(i);

				if (c instanceof ViewGroup) {
					_childViews(c);
				} else {
					// 폰트좀가만놔둔다.
					if (c instanceof TextView) {
						if (getTypeface() != null) {
							((TextView) c).setTypeface(getTypeface());
						}
					}
					setClick(c);
				}
			}
		} else {
			// 폰트좀가만놔둔다.
			if (p instanceof TextView) {
				if (getTypeface() != null) {
					((TextView) p).setTypeface(getTypeface());
				}
			}
			setClick(p);
		}
	}

	/**
	 * <pre>
	 * EditText에 클릭리스너설정시 소프트키보드 안보임.
	 * </pre>
	 */
	protected void setClick(View v) {
		if (v != null) {
			// 포커스클리어
			// v.clearFocus();
			// v.setSelected(false);
			// v.setFocusable(false);
			// v.setFocusableInTouchMode(false);
			clearFocus(v);

			// 클릭확인
			v.setClickable(true);
			if (v instanceof EditText) {
				// 소프트키보드
				v.setOnClickListener(null);
			} else {
				v.setOnClickListener(this);
			}

			// 포커스확인
			v.setOnFocusChangeListener(this);

			// 터치확인
			v.setOnTouchListener(this);
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3#hideBackBoard()
	 */
	@Deprecated
	public void hideBackBoard() {
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3#showBackBoard()
	 */
	@Deprecated
	public void showBackBoard() {
	}

	/**
	 * <pre>
	 * 볼륨키는 어쩌구 이 븅신들아!!!
	 * 븅신색깔핫키기능이관
	 * 븅신키:그럼메뉴숨긴상태에서 씹짜키하고엔터키는?
	 * </pre>
	 *
	 * @see Main2#onKeyDown(int, android.view.KeyEvent)
	 * @see Main2XXXXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main3XXX#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6X#onKeyDown(int, android.view.KeyEvent)
	 * @see Main6XX#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[OK]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event + _checkKaraokeMenuFoci());

		if (event == null) {
			return super.onKeyDown(keyCode, event);
		}

		// if (event != null && event.getRepeatCount() > 0)
		// {
		// }

		// 홈키입력시(종료처리)
		if (keyCode == KeyEvent.KEYCODE_TV || keyCode == KeyEvent.KEYCODE_HOME) {
			Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[홈키][종료]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			finish();
			return super.onKeyDown(keyCode, event);
		}

		//// 로딩 중이면 RETURN키 제외한 입력 차단
		//// 븅신...로딩...지랄한다...왜...뒤지라고...굿을하지...
		//if (isLoading()) {
		//	if (keyCode == KeyEvent.KEYCODE_BACK) {
		//		//if (!m_bIsLongLoading) {
		//		if (loading_time == LOADING_TIME.LOADING_SHORT) {
		//			stopLoading(getMethodName());
		//		} else {
		//			return false;
		//		}
		//	} else {
		//		return false;
		//	}
		//}

		// 메세지박스 떠있으면 차단
		if (isShowMessageOkCancel()) {
			setSelectedMessageOkCancel(keyCode);

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				HideMessageOkCancel();
			}

			if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
				// 이용권 구매 메뉴로 가겠냐는 메세지에서 [확인] 또는 [취소] 선택했을 때, 녹음곡 감상 상태였으면 다시 메뉴로 돌려준다
				if (m_bIsGoToPurchaseMessage) {
					if (remote.m_iState == STATE_LISTENING) {
						RemoveListenDisplay();
						ShowMenu(getMethodName());
					}
				}

				if (m_iMessageOkCancelFocusX == POPUP_OK) {
					Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][확인]" + ":m_bIsGoToPurchaseMessage:" + m_bIsGoToPurchaseMessage + ":m_bIsExit" + m_bIsExit);
					if (m_bIsGoToPurchaseMessage) {
						Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][구매]" + ":m_bIsGoToPurchaseMessage:" + m_bIsGoToPurchaseMessage + ":m_bIsExit" + m_bIsExit);
						goShop(true, 1);
					}
					//븅신머냐이게...
					//else {
					//	Log.wtf(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][종료]" + ":m_bIsGoToPurchaseMessage:" + m_bIsGoToPurchaseMessage + ":m_bIsExit" + m_bIsExit);
					//	finish();
					//}
					if (m_bIsExit) {
						Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[팝업][종료]" + ":m_bIsGoToPurchaseMessage:" + m_bIsGoToPurchaseMessage + ":m_bIsExit" + m_bIsExit);
						finish();
					}
				}
				HideMessageOkCancel();
			}
			return false;
		}

		if (m_bShowMessageOK) {
			if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_BACK) {
				if (m_bIsExit) {
					finish();
				} else {
					HideMessageOk();
				}
			}
			return false;
		}

		if (CheckNumberKey(keyCode)) {
			Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[번호][입력]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			if (!m_bIsFocusedOnPassNumber) {
				ShowNumberSearch(keyCode);
			} else {
				inputPPXPass(keyCode);
			}
			return false;
		}

		if (m_bIsNumberSearch) {
			Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[검색][종료]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
			if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
				if (m_bHaveNumberSearchResult) {
					m_strSearchID = m_strInputNumber;
					HideNumberSearch();
					addEngageSong(m_strSearchID);

					// 140422 재생 중이 아닐 때만 '예약곡 시작' 으로 바뀌도록 함
					if (!isPlaying()) {
						showBottomGuideStartSong();
					}
				}
			} else if (keyCode == KeyEvent.KEYCODE_BACK) {
				HideNumberSearch();
			}

			return false;
		}

		// 븅신키:그럼메뉴숨긴상태에서 씹짜키하고엔터키는?
		// 븅신키:그럼메뉴숨긴상태에서 녹음곡재생떄는어쨰?
		// 재생 중인 메뉴 숨김 상태면 특정 키 제외 입력 차단
		if (!isShowMenu() && (isPlaying() || isListening())) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_MENU:
				case KeyEvent.KEYCODE_BACK:
				case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
				case KeyEvent.KEYCODE_MEDIA_STOP:
				case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
				case KeyEvent.KEYCODE_MEDIA_REWIND:
				case KeyEvent.KEYCODE_PROG_RED:
				case KeyEvent.KEYCODE_PROG_GREEN:
				case KeyEvent.KEYCODE_PROG_YELLOW:
				case KeyEvent.KEYCODE_PROG_BLUE:
					break;
				default:
					Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[메뉴][재생][차단]" + loading_method + ":" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
					return true;
			}
		}

		// 이븅신새꺄!!!이건머냐
		// if (keyCode == KeyEvent.KEYCODE_CHANNEL_UP) {
		// ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), "CHANNEL_UP");
		// return false;
		// }

		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) { // RIGHT
			remote.inputKey(REMOTE_RIGHT);
			displayGUI(REMOTE_RIGHT);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) { // LEFT
			remote.inputKey(REMOTE_LEFT);
			displayGUI(REMOTE_LEFT);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) { // UP
			remote.inputKey(REMOTE_UP);
			displayGUI(REMOTE_UP);
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) { // DOWN
			if (remote.m_iState == STATE_SONG_LIST || remote.m_iState == STATE_MY_LIST) {
				if (CheckNotContentsSongList()) {
					Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[패드][다음]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
					displayGUI(REMOTE_RIGHT);
					return false;
				}
			} else if (remote.m_iState == STATE_CUSTOMER_LIST || remote.m_iState == STATE_CUSTOMER_LIST_EVENT) {
				if (CheckNotContentsCustomerList()) {
					Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[패드][다음]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
					displayGUI(REMOTE_RIGHT);
					return false;
				}
			} else if (remote.m_iState == STATE_SEARCH_LIST) {
				if (CheckNotContentsSearchList()) {
					Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[패드][다음]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
					displayGUI(REMOTE_RIGHT);
					return false;
				}
			} else if (remote.m_iState == STATE_MY_RECORD_LIST) {
				if (CheckNotContentsMyRecordList()) {
					Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[패드][다음]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
					displayGUI(REMOTE_RIGHT);
					return false;
				}
			}
			remote.inputKey(REMOTE_DOWN);
			displayGUI(REMOTE_DOWN);
		}

		if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			clickGUI();
		}

		if (keyCode == KeyEvent.KEYCODE_BACK) { // BACK
			// 숨김 상태고 반주곡 재생 중이면 메뉴 다시 보여주고 반주곡 중지 처리
			if (m_bIsHiddenMenu && isPlaying()) {
				Log.i(_toString() + TAG_MAIN, "onKeyDown()" + "[재생][중지]" + isLoading() + ":" + remote.getState() + ":" + keyCode + ", " + event);
				stopPlay(PLAY_STOP);
				return false;
			}

			// 어떤 화면에서 Back키를 눌렀는지 저장해두었다가, 종료할지 말지를 결정
			int temp = remote.m_iState;

			exitGUI();

			ShowMessageExit(temp);

			return false;
		}

		// 븅신색깔핫키기능이관
		// if (keyCode == KeyEvent.KEYCODE_PROG_RED) // RED
		// {
		// RemoveListenDisplay();
		// goHome();
		// return true;
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_MEDIA_REWIND) // RED
		// {
		// RemoveListenDisplay();
		// goHome();
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_PROG_GREEN) // GREEN
		// {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "GREEN, [KEYCODE_PROG_GREEN]");
		// if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
		// // BTV:BOX->재생
		// if (isPlaying()) {
		// stopPlay(PLAY_STOP);
		// } else {
		// String strNextSong = getEngageSong();
		//
		// if (!strNextSong.equals("")) {
		// RemoveListenDisplay();
		//
		// // 예약곡이 있으면 예약곡 시작
		// // 븅신아곡이시작하고지원야지
		// // deleteEngageSong();
		// m_strRequestPlaySongID = strNextSong;
		// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
		// }
		// }
		// } else {
		// // BTV:STB->중지
		// // if (isPlaying())
		// {
		// // toggleMenu();
		// stopPlay(PLAY_STOP);
		// }
		// }
		// return true;
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) // GREEN
		// {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "YELLOW, [KEYCODE_MEDIA_STOP]");
		//
		// // 재생 중일때
		// // if (isPlaying())
		// {
		// // if (BuildConfig.DEBUG) _LOG.i(_toString(), "YELLOW, [KEYCODE_MEDIA_STOP] Now Playing");
		// //
		// // if (m_bIsHiddenMenu) {
		// // if (BuildConfig.DEBUG) _LOG.i(_toString(), "YELLOW, SHOW");
		// // ShowMenu(getMethodName());
		// // } else {
		// // if (BuildConfig.DEBUG) _LOG.i(_toString(), "YELLOW, HIDE");
		// // HideMenu(getMethodName());
		// // }
		// // 재생 중지
		// stopPlay(PLAY_STOP);
		// }
		// return true;
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_PROG_YELLOW) // YELLOW
		// {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "YELLOW, [KEYCODE_PROG_YELLOW]");
		// if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
		// // BTV:BOX->중지
		// // if (isPlaying())
		// {
		// // toggleMenu();
		// stopPlay(PLAY_STOP);
		// }
		// } else {
		// // BTV:STB->재생
		// if (isPlaying())
		// {
		// // 재생 중지
		// stopPlay(PLAY_STOP);
		// } else {
		// // 재생 중이 아닐 때
		// String strNextSong = getEngageSong();
		//
		// if (!strNextSong.equals("")) {
		// RemoveListenDisplay();
		//
		// // 예약곡이 있으면 예약곡 시작
		// // 븅신아곡이시작하고지원야지
		// // deleteEngageSong();
		// m_strRequestPlaySongID = strNextSong;
		// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
		// }
		// }
		// }
		// return true;
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) // YELLOW
		// {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "GREEN, [KEYCODE_MEDIA_PLAY_PAUSE]");
		//
		// // 재생 중일 때
		// if (isPlaying()) {
		// // 재생 중지
		// stopPlay(PLAY_STOP);
		// } else {
		// // 재생 중이 아닐 때
		// String strNextSong = getEngageSong();
		//
		// if (!strNextSong.equals("")) {
		// RemoveListenDisplay();
		//
		// // 예약곡이 있으면 예약곡 시작
		// // 븅신아곡이시작하고지원야지
		// // deleteEngageSong();
		// m_strRequestPlaySongID = strNextSong;
		// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
		// }
		// }
		// return true;
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_PROG_BLUE) // BLUE
		// {
		//
		// if (m_bIsCertifyTimerActivated) {
		// m_cdTimer.cancel();
		// }
		//
		// RemoveListenDisplay();
		// goSearch();
		//
		// return true;
		// }
		//
		// if (keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) // BLUE
		// {
		//
		// if (m_bIsCertifyTimerActivated) {
		// m_cdTimer.cancel();
		// }
		//
		// RemoveListenDisplay();
		// goSearch();
		//
		// return true;
		// }

		return super.onKeyDown(keyCode, event);
	}

	public void onBtnHomeSing(View view) {
	}

	public void onBtnHomeListen(View view) {
	}

	public void onBtnHomeMy(View view) {
	}

	public void onBtnHomeShop(View view) {
	}

	public void onBtnHomeCustomer(View view) {
	}

	public void onBtnMainSing(View view) {
	}

	public void onBtnMainListen(View view) {
	}

	public void onBtnMainMy(View view) {
	}

	public void onBtnMainShop(View view) {
	}

	public void onBtnMainCustomer(View view) {
	}

	protected void onClickBtnLeft(View view) {
		if (isShowMessageOkCancel()) {
			m_iMessageOkCancelFocusX = POPUP_OK;
			Button btnOK = (Button) findViewById(R.id.btn_message_okcancel_ok);
			Button btnCancel = (Button) findViewById(R.id.btn_message_okcancel_cancel);
			btnOK.setBackgroundResource(R.drawable.pop_btn_01_on);
			btnCancel.setBackgroundResource(R.drawable.pop_btn_01_off);
			return;
		}

		remote.inputKey(REMOTE_LEFT);

		displayGUI(REMOTE_LEFT);
	}

	protected void onClickBtnRight(View view) {
		if (isShowMessageOkCancel()) {
			m_iMessageOkCancelFocusX = POPUP_CANCEL;
			Button btnOK = (Button) findViewById(R.id.btn_message_okcancel_ok);
			Button btnCancel = (Button) findViewById(R.id.btn_message_okcancel_cancel);
			btnOK.setBackgroundResource(R.drawable.pop_btn_01_off);
			btnCancel.setBackgroundResource(R.drawable.pop_btn_01_on);
			return;
		}

		remote.inputKey(REMOTE_RIGHT);

		displayGUI(REMOTE_RIGHT);
	}

	/**
	 * 한줄위로이동
	 */
	protected void onClickBtnUp(View view) {
		remote.inputKey(REMOTE_UP);
		displayGUI(REMOTE_UP);
	}

	/**
	 * 한줄아래이동
	 */
	protected void onClickBtnDown(View view) {
		if (remote.m_iState == STATE_SONG_LIST || remote.m_iState == STATE_MY_LIST) {
			if (CheckNotContentsSongList()) {
				displayGUI(REMOTE_RIGHT);
				return;
			}
		} else if (remote.m_iState == STATE_CUSTOMER_LIST || remote.m_iState == STATE_CUSTOMER_LIST_EVENT) {
			if (CheckNotContentsCustomerList()) {
				displayGUI(REMOTE_RIGHT);
				return;
			}
		} else if (remote.m_iState == STATE_SEARCH_LIST) {
			if (CheckNotContentsSearchList()) {
				displayGUI(REMOTE_RIGHT);
				return;
			}
		} else if (remote.m_iState == STATE_MY_RECORD_LIST) {
			if (CheckNotContentsMyRecordList()) {
				displayGUI(REMOTE_RIGHT);
				return;
			}
		}
		remote.inputKey(REMOTE_DOWN);
		displayGUI(REMOTE_DOWN);
	}

	protected String _checkKaraokeMenuFoci() {
		return "";
	}

	protected void displayGUI(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		switch (remote.m_iState) {
			case STATE_HOME_MENU:
				displayMenuHome(keyID);
				break;
			case STATE_MAIN_MENU:
				displayMenuMain(keyID);
				break;
			case STATE_SING_MENU:
				displayMenuSing(keyID);
				break;
			case STATE_SING_GENRE:
				displayMenuSingGenre(keyID);
				break;
			case STATE_SONG_LIST:
				displayListSing(keyID);
				break;
			case STATE_SONG_LIST_DETAIL:
				displayDetailSong(keyID);
				break;
			case STATE_MY_MENU:
				displayMenuMy(keyID);
				break;
			case STATE_MY_LIST:
				displayListMy(keyID);
				break;
			case STATE_SHOP_MENU:
				displayMenuShop(keyID);
				break;
			case STATE_SHOP_TICKET:
				displayShopTicket(keyID);
				break;
			case STATE_MESSAGE_PPX_INFO:
				setSelectedPPXInfoOkCancel(keyID);
				break;
			case STATE_MESSAGE_PPX_PASS:
				displayPPXPass(keyID);
				break;
			case STATE_MESSAGE_PPX_NOTICE:
				setSelectedPPMNotice(keyID);
				break;
			case STATE_MESSAGE_GO_CERTIFY:
				displayGOCertify(keyID);
				break;
			case STATE_SHOP_CERTIFY:
				displayShopCertify(keyID);
				break;
			case STATE_CUSTOMER_MENU:
				displayMenuCustomer(keyID);
				break;
			case STATE_CUSTOMER_LIST:
			case STATE_CUSTOMER_LIST_EVENT:
				displayListCustomer(keyID);
				break;
			case STATE_CUSTOMER_LIST_DETAIL:
				displayDetailCustomer(keyID);
				break;
			case STATE_SEARCH_MENU:
				displayMenuSearch(keyID);
				break;
			case STATE_SEARCH_SELF:
				displaySearchSelf(keyID);
				break;
			case STATE_SEARCH_LETTER_KOR:
				displaySearchLetter(keyID);
				break;
			case STATE_SEARCH_LETTER_ENG:
				displaySearchLetter(keyID);
				break;
			case STATE_SEARCH_LETTER_NUM:
				displaySearchLetter(keyID);
				break;
			case STATE_SEARCH_LIST:
				displayListSearch(keyID);
				break;
			case STATE_LISTEN_MENU:
				displayMenuListen(keyID);
				break;
			case STATE_LISTEN_LIST:
				displayListListen(keyID);
				break;
			case STATE_SEARCH_LIST_DETAIL:
				displayDetailSong(keyID);
				break;
			case STATE_LISTENING:
				displayListening(keyID);
				break;
			case STATE_LISTEN_OTHER:
				displayListenOther(keyID);
				break;
			case STATE_CERTIFY_HP:
			case STATE_EVENT_HP:
				displayCertifyHP(keyID);
				break;
			case STATE_CERTIFY:
				displayCertify(keyID);
				break;
			case STATE_MY_RECORD_LIST:
				displayListMyRecord(keyID);
				break;
			case STATE_MY_RECORD_NONE:
			case STATE_MY_RECORD_BEFORE:
				displayMyRecordNone(keyID);
				break;
		}
	}

	protected void clickGUI() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		switch (remote.m_iState) {
			case STATE_HOME_MENU:
				openHomeMenu();
				break;
			case STATE_MAIN_MENU:
				openMainMenu();
				break;
			case STATE_SING_MENU:
				clickMenuSing();
				break;
			case STATE_SING_GENRE:
				clickMenuSingGenre();
				break;
			case STATE_SONG_LIST:
				clickListSong();
				break;
			case STATE_SONG_LIST_DETAIL:
				clickDetailSong();
				break;
			case STATE_SHOP_MENU:
				clickMenuShop();
				break;
			case STATE_CUSTOMER_MENU:
				clickMenuCustomer();
				break;
			case STATE_CUSTOMER_LIST:
			case STATE_CUSTOMER_LIST_EVENT:
				clickListCustomer();
				break;
			case STATE_CUSTOMER_LIST_DETAIL:
				clickDetailEvent();
				break;
			case STATE_SEARCH_MENU:
				clickMenuSearch();
				break;
			case STATE_SEARCH_SELF:
				clickSearchSelf();
				break;
			case STATE_SEARCH_LETTER_KOR:
				clickSearchLetter();
				break;
			case STATE_SEARCH_LETTER_ENG:
				clickSearchLetter();
				break;
			case STATE_SEARCH_LETTER_NUM:
				clickSearchLetter();
				break;
			case STATE_SEARCH_LIST:
				clickListSearch();
				break;
			case STATE_LISTEN_MENU:
				clickMenuListen();
				break;
			case STATE_LISTEN_LIST:
				clickListListen();
				break;
			case STATE_MY_MENU:
				clickMenuMy();
				break;
			case STATE_MY_LIST:
				clickListSong();
				break;
			case STATE_SEARCH_LIST_DETAIL:
				clickDetailSearch();
				break;
			case STATE_LISTENING:
				clickListening();
				break;
			case STATE_SHOP_CERTIFY:
				clickShopCertify();
				break;
			case STATE_CERTIFY_HP:
			case STATE_EVENT_HP:
				clickCertifyHP();
				break;
			case STATE_CERTIFY:
				clickCertify();
				break;
			case STATE_LISTEN_OTHER:
				clickListeningOther();
				break;
			case STATE_SHOP_TICKET:
				clickShopTicket();
				break;
			case STATE_MESSAGE_PPX_INFO:
				clickPPXInfo();
				break;
			case STATE_MESSAGE_PPX_PASS:
				clickPPXPass();
				break;
			case STATE_MESSAGE_PPX_NOTICE:
				clickPPXNotice();
				break;
			case STATE_MESSAGE_GO_CERTIFY:
				clickGOCertify();
				break;
			case STATE_MESSAGE_PPX_SUCCESS:
				exitPPV();
				break;
			case STATE_MY_RECORD_LIST:
				clickListListen();
				break;
			case STATE_MY_RECORD_NONE:
			case STATE_MY_RECORD_BEFORE:
				clickMyRecordNone();
				break;
		}
	}

	protected void exitGUI() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "STATE Exit = " + String.valueOf(remote.m_iState));

		switch (remote.m_iState) {
			case STATE_HOME_MENU:
				break;
			case STATE_MAIN_MENU:
				goHome();
				break;
			case STATE_SING_MENU:
				exitMenuSing();
				break;
			case STATE_SING_GENRE:
				exitMenuSingGenre();
				break;
			case STATE_SONG_LIST:
				exitListSong();
				break;
			case STATE_SONG_LIST_DETAIL:
				exitDetailSong();
				break;
			case STATE_MY_MENU:
				exitMenuMy();
				break;
			case STATE_MY_LIST:
				exitListMy();
				break;
			case STATE_SHOP_MENU:
				exitMenuShop();
				break;
			case STATE_SHOP_TICKET:
				exitTicket();
				break;
			case STATE_MESSAGE_PPX_INFO:
			case STATE_MESSAGE_PPX_PASS:
			case STATE_MESSAGE_PPX_SUCCESS:
			case STATE_MESSAGE_PPX_NOTICE:
			case STATE_MESSAGE_GO_CERTIFY:
				exitPPV();
				break;
			case STATE_SHOP_CERTIFY:
				exitCertify();
				break;
			case STATE_CUSTOMER_MENU:
				exitMenuCustomer();
				break;
			case STATE_CUSTOMER_LIST:
			case STATE_CUSTOMER_LIST_EVENT:
				exitListCustomer();
				break;
			case STATE_CUSTOMER_LIST_DETAIL:
				exitDetailCustomer();
				break;
			case STATE_SEARCH_MENU:
				exitMenuSearch();
				break;
			case STATE_SEARCH_SELF:
				exitSearchSelf();
				break;
			case STATE_SEARCH_LETTER_KOR:
			case STATE_SEARCH_LETTER_ENG:
			case STATE_SEARCH_LETTER_NUM:
				exitSearchLetter();
				break;
			case STATE_LISTEN_LIST:
				exitListListen();
				break;
			case STATE_LISTEN_MENU:
				exitMenuListen();
				break;
			case STATE_LISTENING:
				exitListening();
				break;
			case STATE_SEARCH_LIST_DETAIL:
				exitDetailSearch();
				break;
			case STATE_SEARCH_LIST:
				exitListSearch();
				break;
			case STATE_LISTEN_OTHER:
				exitListeningOther();
				break;
			case STATE_CERTIFY_HP:
			case STATE_EVENT_HP:
				exitCertifyHP();
				break;
			case STATE_CERTIFY:
				exitCertifyNumber();
				break;
			case STATE_MY_RECORD_LIST:
				exitListMyRecord();
				break;
			case STATE_MY_RECORD_NONE:
			case STATE_MY_RECORD_BEFORE:
				exitMyRecordNone();
				break;
			default:
				goHome();
				break;
		}

	}

	ViewGroup sing_list;
	List<SingLine> sing_line = new ArrayList<SingLine>();

	protected void inflateListSong(int res) {
	}

	protected void resetSongList() {
	}

	/**
	 * 븅신머하는건지멘트도안넣고
	 */
	protected Timer m_timerStartSing = null;

	class TaskStartSing extends TimerTask {
		@Override
		public void run() {
			sendMessage(COMPLETE_TIMER_START_SING_NOW);
		}
	}

	// /**
	// * 븅신머하는건지멘트도안넣고
	// */
	// private final Timer m_timerStartSongNext = null;
	//
	// private class TaskStartSingNext extends TimerTask {
	// @Override
	// public void run() {
	// sendMessage(COMPLETE_TIMER_START_SING_NEXT);
	// }
	// }

	/**
	 * 예약곡:이전(previous)/이후(next)
	 *
	 * @see Main3XX#previous()
	 */
	protected void previous() {
	}

	/**
	 * 예약곡:이전(previous)/이후(next)
	 *
	 * @see Main3XX#next()
	 */
	protected void next() {
	}

	/**
	 * 이븅신새꺄!!!어느정보 반복패턴이 있으면 함수로 만들어야지 그걸 죄처박고 앉았냐!!!
	 *
	 * @author isyoon
	 * @see Main2X#startSing(java.lang.String)
	 * @see Main2X#requestSing(int)
	 */
	protected void startSing(String song) {
	}

	protected void openHomeMenu() {
	}

	protected void openMainMenu() {
	}

	protected void clickMenu(int m_iMainMenuFocus) {
	}

	/**
	 * 고객센터->공지사하상세보기
	 */
	protected void goNotice(boolean home) {
	}

	/**
	 * 서브메뉴값초기화
	 */
	protected void resetMenuSub(String method) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RS]" + getMethodName() + method + ":" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		m_bListAlreadyReflashed = false;
		remote.m_iMenuSingFocus = 1;
		remote.m_iMenuSingGenreFocus = 1;
		remote.m_iMenuListenFocus = 1;
		remote.m_iMenuMyFocus = 1;
		remote.m_iMenuShopFocus = 1;
		remote.m_iMenuCustomerFocus = 1;
	}

	/**
	 * 전체메뉴값초기화
	 */
	protected void resetMenus(String method) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), "[RS]" + getMethodName() + method + ":" + m_bListAlreadyReflashed + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		m_bListAlreadyReflashed = false;
		remote.m_iMenuHomeFocus = 1;
		remote.m_iMenuHomeFocusY = 1;
		remote.m_iMenuMainFocus = 1;
		remote.m_iMenuSingFocus = 1;
		remote.m_iMenuSingGenreFocus = 1;
		remote.m_iMenuListenFocus = 1;
		remote.m_iMenuMyFocus = 1;
		remote.m_iMenuShopFocus = 1;
		remote.m_iMenuCustomerFocus = 1;
	}

	/**
	 * 고객센터->이벤트상세보기
	 */
	protected void goEvent(boolean home) {
	}

	/**
	 * 녹음곡감상->바로가기
	 */
	protected void goListen() {
	}

	/**
	 * 노래부르기->바로가기
	 */
	protected void goSing() {
	}

	/**
	 * 고객센터->메뉴이동
	 */
	protected void goCustomer(boolean home, int focus) {
	}

	/**
	 * 노래방샵->메뉴이동
	 */
	protected void goShop(boolean home, int focus) {
	}

	/**
	 * 마이노래방
	 */
	protected void goMyList(boolean home) {
	}

	/**
	 * 녹음곡감상
	 */
	protected void goListenList(boolean home) {
	}

	/**
	 * 노래부르기
	 */
	protected void goSingList(boolean home) {
	}

	public void clickMenuSing() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":remote.m_iMenuSingFocus:" + remote.m_iMenuSingFocus + ":" + KP.result_code + ":" + "\n" + mSongItems);
		try {
			switch (remote.m_iMenuSingFocus) {
				case 1:
					remote.m_iState = STATE_SONG_LIST;
					displayListSing(REMOTE_NONE);

					Button btnHot = (Button) findViewById(R.id.btn_sing_sub_hot);
					if (btnHot != null) {
						//븅신개삽지랄
						//btnHot.setBackgroundResource(R.drawable.tab_focus_off);
						btnHot.setPressed(true);
					}
					break;
				case 2:
					remote.m_iState = STATE_SONG_LIST;
					displayListSing(REMOTE_NONE);

					Button btnNew = (Button) findViewById(R.id.btn_sing_sub_new);
					if (btnNew != null) {
						//븅신개삽지랄
						//btnNew.setBackgroundResource(R.drawable.tab_focus_off);
						btnNew.setPressed(true);
					}
					break;
				case 3:
					remote.m_iState = STATE_SING_GENRE;
					displayMenuSingGenre(REMOTE_NONE);

					Button btnGenre = (Button) findViewById(R.id.btn_sing_sub_genre);
					if (btnGenre != null) {
						//븅신개삽지랄
						//btnGenre.setBackgroundResource(R.drawable.tab_focus_off);
						btnGenre.setPressed(true);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickMenuSingGenre() {
		remote.m_iState = STATE_SONG_LIST;
		displayListSing(REMOTE_NONE);

		try {
			switch (remote.m_iMenuSingGenreFocus) {
				case 1:
					Button btnBallad = (Button) findViewById(R.id.btn_sing_sub_genre_ballad);
					if (null != btnBallad) {
						//븅신개삽지랄
						//btnBallad.setBackgroundResource(R.drawable.tab_focus_off);
						btnBallad.setPressed(true);
					}
					break;
				case 2:
					Button btnDance = (Button) findViewById(R.id.btn_sing_sub_genre_dance);
					if (null != btnDance) {
						//븅신개삽지랄
						//btnDance.setBackgroundResource(R.drawable.tab_focus_off);
						btnDance.setPressed(true);
					}
					break;
				case 3:
					Button btnTrot = (Button) findViewById(R.id.btn_sing_sub_genre_trot);
					if (null != btnTrot) {
						//븅신개삽지랄
						//btnTrot.setBackgroundResource(R.drawable.tab_focus_off);
						btnTrot.setPressed(true);
					}
					break;
				case 4:
					Button btnRock = (Button) findViewById(R.id.btn_sing_sub_genre_rock);
					if (null != btnRock) {
						//븅신개삽지랄
						//btnRock.setBackgroundResource(R.drawable.tab_focus_off);
						btnRock.setPressed(true);
					}
					break;
				case 5:
					Button btnPop = (Button) findViewById(R.id.btn_sing_sub_genre_pop);
					if (null != btnPop) {
						//븅신개삽지랄
						//btnPop.setBackgroundResource(R.drawable.tab_focus_off);
						btnPop.setPressed(true);
					}
					break;
				case 6:
					Button btnAni = (Button) findViewById(R.id.btn_sing_sub_genre_ani);
					if (null != btnAni) {
						//븅신개삽지랄
						//btnAni.setBackgroundResource(R.drawable.tab_focus_off);
						btnAni.setPressed(true);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void inflateDetailSong() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + m_bIsHiddenMenu + ":" + remote.getState());

		try {
			if (m_bIsHiddenMenu) {
				if (BuildConfig.DEBUG) Log.w(_toString() + TAG_ERR, "[NG]" + getMethodName() + m_bIsHiddenMenu + ":" + remote.getState());
				hideDetailSong();
				return;
			}

			if (remote.m_iState == STATE_MY_LIST && remote.m_iMenuMyFocus > 2) {
				if (BuildConfig.DEBUG) Log.w(_toString() + TAG_ERR, "[NG]" + getMethodName() + m_bIsHiddenMenu + ":" + remote.getState());
				hideDetailSong();
				return;
			}

			if (m_layoutSongListDetail == null) {
				m_layoutSongListDetail = (LinearLayout) inflate(R.layout.sing_detail, null);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				addContentView(m_layoutSongListDetail, param);
			} else {
				showDetailSong();
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickListSong() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":m_iSongListFocus:" + remote.m_iSongListFocus);

		remote.m_iSongListDetailFocus = 1;

		// 마이노래방->녹음곡
		if (remote.m_iState == STATE_MY_LIST && remote.m_iMenuMyFocus > 2) {
			if (BuildConfig.DEBUG) Log.w(_toString() + TAG_ERR, "[NG]" + getMethodName() + m_bIsHiddenMenu + ":" + remote.getState());
			remote.m_iState = STATE_MY_RECORD_BEFORE;
			return;
		}

		remote.m_iState = STATE_SONG_LIST_DETAIL;

		try {
			inflateDetailSong();

			int index = remote.m_iSongListFocus - 1;
			if (index < sing_line.size() && index > -1) {
				sing_line.get(index).layout.setPressed(true);
			}

			if (m_layoutSongListDetail != null) {
				Button btnFavor = (Button) m_layoutSongListDetail.findViewById(R.id.btn_detail_favor);
				if (btnFavor != null) {
					if (isFavor(remote.m_iSongListFocus)) {
						btnFavor.setText(getString(R.string.favor_delete));
					} else {
						btnFavor.setText(getString(R.string.favor_add));
					}
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickDetailSong() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + remote.m_iSongListFocus + ":" + remote.m_iSongListDetailFocus);

		if (remote.m_iSongListFocus < 1) {
			return;
		}

		switch (remote.m_iSongListDetailFocus) {
			case 1:
				// 시작
				m_strRequestPlaySongID = getSongIDFromSingList();
				startSing(getSongIDFromSingList());
				// 이븅신새끼하는짓좀보소
				// if (!TextUtil.isEmpty(m_strRequestPlaySongID) && TextUtil.isNumeric(m_strRequestPlaySongID)) {
				// if (isPlaying()) {
				// // 재생 중지
				// stopPlay(PLAY_NEXT);
				//
				// // 140422 예약된 반주곡을 자동 시작할 때는 중지 처리가 완전히 끝난 뒤에 새 요청 시작 (반주곡 상세메뉴)
				// m_timerStartCurrentSong = new Timer();
				// TaskStartCurrentSong startTask = new TaskStartCurrentSong();
				//
				// StartLoading(LOADING_LONG);
				// m_timerStartCurrentSong.schedule(startTask, 10000);
				// } else {
				// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
				// }
				// }
				break;
			case 2:
				// 예약
				addEngageSong(getSongIDFromSingList());
				ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), song_ids.get(song_ids.size() - 1) + "번 반주곡이 예약되었습니다.");

				// 140422 재생 중이 아닐 때만 '예약곡 시작' 으로 바뀌도록 함
				if (!isPlaying()) {
					showBottomGuideStartSong();
				}
				break;
			case 3:
				// 애창곡 등록 or 삭제
				// if (remote.m_iSubMainMenuFocus == 3 && remote.m_iMenuMyFocus == 2)
				// {
				// m_bIsRefreshFavorList = true;
				// }

				m_strRequestFavorSongID = getSongIDFromSingList();
				KP(REQUEST_FAVOR, KP_3010, "", "");

				if (mSongFavors.size() > remote.m_iSongListFocus - 1) {
					if (isFavor(remote.m_iSongListFocus)) {
						mSongFavors.set(remote.m_iSongListFocus - 1, "N");
					} else {
						mSongFavors.set(remote.m_iSongListFocus - 1, "Y");
					}
				}

				displayListSing(REMOTE_NONE);
				break;
			case 4:
				break;
		}

		exitDetailSong();
	}

	public void clickMenuListen() {
		remote.m_iListenListFocusX = 1;
		remote.m_iListenListFocusY = 1;

		try {
			remote.m_iState = STATE_LISTEN_LIST;
			displayListListen(REMOTE_NONE);

			switch (remote.m_iMenuListenFocus) {
				case 1:
					Button btnNow = (Button) findViewById(R.id.btn_listen_sub_now);
					if (null != btnNow) {
						//븅신개삽지랄
						//btnNow.setBackgroundResource(R.drawable.tab_focus_off);
						btnNow.setPressed(true);
					}
					break;
				case 2:
					Button btnWeek = (Button) findViewById(R.id.btn_listen_sub_week);
					if (null != btnWeek) {
						//븅신개삽지랄
						//btnWeek.setBackgroundResource(R.drawable.tab_focus_off);
						btnWeek.setPressed(true);
					}
					break;
				case 3:
					Button btnBest = (Button) findViewById(R.id.btn_listen_sub_best);
					if (null != btnBest) {
						//븅신개삽지랄
						//btnBest.setBackgroundResource(R.drawable.tab_focus_off);
						btnBest.setPressed(true);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	protected void stopListen() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (listen != null) {
			if (isListening()) {
				KP(REQUEST_LISTEN_PLAYED_TIME, KP_1012, M1_MENU_LISTEN, M2_LISTEN_TIMELINE);
			}
			listen.stop();
		}

	}

	public void clickListListen() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		int index = getFocusedListenListItemIndex();

		// 녹음곡이 재생될 것이므로, 반주곡 재생 중이었다면 중지시킨다
		if (isPlaying()) {
			stopPlay(PLAY_STOP);
		}

		try {
			if (remote.m_iMenuMainFocus == 3) {
				//마이노래방->녹음곡
				if (m_bIsMyRecordReCertifyBtnFocused) {
					//노래방샵->인증센터
					goShop(true, 2);
					//븅신...이런게삽질이야
					//resetMainMenu();
					//goShop(true, 1);
					//
					//remote.m_iMenuShopFocus = 2;
					//displayMenuShop(REMOTE_RIGHT);
					//
					//Button btnShopSubCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
					//btnShopSubCertify.setVisibility(View.VISIBLE);
				} else {
					//index = getFocusedListenListItemIndex();
					if (mListenItems != null && index < mListenItems.size() && index > -1) {
						record_id = mListenItems.get(index).record_id;
						KP(REQUEST_LISTEN_SONG, KP_2016, M1_MENU_LISTEN, M2_LISTEN_TIMELINE);
					}
				}
			} else {
				//녹음곡감상
				//index = getFocusedListenListItemIndex();
				if (mListenItems != null && index < mListenItems.size() && index > -1) {
					record_id = mListenItems.get(index).record_id;

					switch (remote.m_iMenuListenFocus) {
						case 1:
							KP(REQUEST_LISTEN_SONG, KP_2016, M1_MENU_LISTEN, M2_LISTEN_TIMELINE);
							break;
						case 2:
							KP(REQUEST_LISTEN_SONG, KP_2016, M1_MENU_LISTEN, M2_LISTEN_WEEK);
							break;
						case 3:
							KP(REQUEST_LISTEN_SONG, KP_2016, M1_MENU_LISTEN, M2_LISTEN_TOP100);
							break;
					}
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	private void addListening() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + m_layoutListen);
		if (m_layoutListen == null) {
			m_layoutListen = (LinearLayout) inflate(R.layout.listening, null);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			addContentView(m_layoutListen, param);
		}
		m_layoutListen.setVisibility(View.VISIBLE);
	}

	protected void removeListening() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + m_layoutListen);
		if (m_layoutListen == null) {
			m_layoutListen = (LinearLayout) findViewById(R.id.listening);
		}
		// if (m_layoutListen != null) {
		// m_layoutListen.setVisibility(View.INVISIBLE);
		// ((ViewManager) m_layoutListen.getParent()).removeView(m_layoutListen);
		// m_layoutListen = null;
		// }
		removeView(m_layoutListen);
		m_layoutListen = null;

		removeListeningOther();
	}

	private void removeListeningOther() {
		if (m_layoutListeningOther == null) {
			m_layoutListeningOther = (LinearLayout) findViewById(R.id.listen_other);
		}
		// if (m_layoutListeningOther != null) {
		// ((ViewManager) m_layoutListeningOther.getParent()).removeView(m_layoutListeningOther);
		// m_layoutListeningOther = null;
		// }
		removeView(m_layoutListeningOther);
		m_layoutListeningOther = null;
		mListenOtherItems.clear();
	}

	public void clickListening() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());
		// 이용권이 없으면 알림 팝업 띄우고 리턴
		if (!isPassUser()) {
			m_bIsGoToPurchaseMessage = true;
			ShowMessageOkCancel(getString(R.string.common_info), getString(R.string.ticket_have_no));
			return;
		}

		m_strRequestPlaySongID = m_strListeningSongID;

		stopListen();

		removeListening();

		// 녹음곡 플레이 상태에서 확인 버튼 눌러 반주곡 재생으로 갈 때, 내 녹음곡이면 STATE 구분
		if (remote.m_iMenuMainFocus != 3) {
			remote.m_iState = STATE_LISTEN_LIST;
		} else {
			remote.m_iState = STATE_MY_RECORD_LIST;
		}

		KP(REQUEST_SONG_PLAY, KP_1016, "", "");
	}

	public void clickListeningOther() {
		if (mListenOtherItemsRecordID.size() >= remote.m_iListenOther) {
			exitListeningOther();

			stopListen();

			// 하단 첫번째 가이드 텍스트 그룹을 감춤
			hideBottomGuide01();

			removeListening();

			if (remote.m_iListenOther > 0 && mListenOtherItemsRecordID != null && (remote.m_iListenOther - 1) < mListenOtherItemsRecordID.size()) {
				record_id = mListenOtherItemsRecordID.get(remote.m_iListenOther - 1);

				if (BuildConfig.DEBUG) Log.i(_toString(), "clicked Listen ID : " + record_id);

				switch (remote.m_iMenuListenFocus) {
					case 1:
						KP(REQUEST_LISTEN_SONG_OTHER, KP_2016, M1_MENU_LISTEN, M2_LISTEN_TIMELINE);
						break;
					case 2:
						KP(REQUEST_LISTEN_SONG_OTHER, KP_2016, M1_MENU_LISTEN, M2_LISTEN_WEEK);
						break;
					case 3:
						KP(REQUEST_LISTEN_SONG_OTHER, KP_2016, M1_MENU_LISTEN, M2_LISTEN_TOP100);
						break;
				}
			}
		}
	}

	public void clickMenuMy() {
		//녹음곡
		if (remote.m_iMenuMyFocus == 3) {
			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + KP.result_code + ":" + "\n" + mListenItems);
		} else {
			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + KP.result_code + ":" + "\n" + mSongItems);
		}

		try {
			switch (remote.m_iMenuMyFocus) {
				case 1:
					remote.m_iState = STATE_MY_LIST;
					displayListMy(REMOTE_NONE);

					TextView txtNumberRecent = sing_line.get(0).txt_number;
					if (txtNumberRecent.getText().equals("") || txtNumberRecent.getText().equals(null)) {
						return;
					}

					Button btnRecent = (Button) findViewById(R.id.btn_my_sub_recent);
					if (btnRecent != null) {
						//븅신개삽지랄
						//btnRecent.setBackgroundResource(R.drawable.tab_focus_off);
						btnRecent.setPressed(true);
					}
					break;
				case 2:
					remote.m_iState = STATE_MY_LIST;
					displayListMy(REMOTE_NONE);

					TextView txtNumberFavor = sing_line.get(0).txt_number;
					if (txtNumberFavor.getText().equals("") || txtNumberFavor.getText().equals(null)) {
						return;
					}

					Button btnFavor = (Button) findViewById(R.id.btn_my_sub_favor);
					if (btnFavor != null) {
						//븅신개삽지랄
						//btnFavor.setBackgroundResource(R.drawable.tab_focus_off);
						btnFavor.setPressed(true);
					}
					break;
				case 3:
					if (m_iTotalListenListPage > 0) {
						remote.m_iState = STATE_MY_RECORD_LIST;
						m_bIsMyRecordReCertifyBtnFocused = false;

						displayListMyRecord(REMOTE_NONE);
					} else if (m_iTotalListenListPage == 0) {
						m_bIsBeforeRegistMyRecord = false;

						remote.m_iState = STATE_MY_RECORD_NONE;
						m_bIsMyRecordReCertifyBtnFocused = false;

						Button btnHelp = (Button) findViewById(R.id.btn_already_certify_help);
						if (btnHelp != null) {
							btnHelp.setBackgroundResource(R.drawable.my_record_btn_on);
						}
					} else if (m_iTotalListenListPage == -1) {
						m_bIsBeforeRegistMyRecord = true;

						remote.m_iState = STATE_MY_RECORD_BEFORE;
						m_bIsMyRecordReCertifyBtnFocused = false;

						Button btnHelp = (Button) findViewById(R.id.btn_already_certify_help);
						if (btnHelp != null) {
							btnHelp.setBackgroundResource(R.drawable.my_record_btn_on);
						}
					}

					Button btnRecord = (Button) findViewById(R.id.btn_my_sub_record);
					if (btnRecord != null) {
						//븅신개삽지랄
						//btnRecord.setBackgroundResource(R.drawable.tab_focus_off);
						btnRecord.setPressed(true);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickMenuShop() {
		//try {
		//	switch (remote.m_iMenuShopFocus) {
		//		case 1:
		//			remote.m_iState = STATE_SHOP_TICKET;
		//			remote.setIndex(0);
		//			remote.m_iShopTicketFocusY = 1;
		//			m_bIsFocusedOnTicket = false;
		//
		//			displayShopTicket(REMOTE_NONE);
		//
		//			Button btnTicket = (Button) findViewById(R.id.btn_shop_sub_ticket);
		//			if (btnTicket != null) {
		//				//븅신개삽지랄
		//				//btnTicket.setBackgroundResource(R.drawable.tab_focus_off);
		//				btnTicket.setPressed(true);
		//			}
		//
		//			Button btnPurchase = (Button) findViewById(R.id.btn_shop_purchase);
		//			if (btnPurchase != null) {
		//				btnPurchase.setBackgroundResource(R.drawable.tab_focus_long_on);
		//			}
		//
		//			ViewGroup layoutTicket = (ViewGroup) findViewById(R.id.layout_shop_ticket);
		//			if (layoutTicket != null) {
		//				layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_month_on);
		//			}
		//			break;
		//		case 2:
		//			remote.m_iState = STATE_SHOP_CERTIFY;
		//
		//			Button btnCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
		//			if (btnCertify != null) {
		//				//븅신개삽지랄
		//				//btnCertify.setBackgroundResource(R.drawable.tab_focus_off);
		//				btnCertify.setPressed(true);
		//			}
		//
		//			if (/*!m_bIsCertifyedUser*/TextUtil.isEmpty(KP.auth_date)) {
		//				ImageView imgCertify = (ImageView) findViewById(R.id.img_shop_certify);
		//				if (imgCertify != null) {
		//					imgCertify.setImageResource(R.drawable.shop_certify_on);
		//				}
		//			} else {
		//				LinearLayout layoutCertify = (LinearLayout) findViewById(R.id.layout_already_certify);
		//				if (layoutCertify != null) {
		//					layoutCertify.setBackgroundResource(R.drawable.shop_certify_already_on);
		//				}
		//			}
		//			break;
		//	}
		//} catch (Exception e) {
		//	if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		//}
	}

	public void clickShopTicket() {
		//if (m_bIsFocusedOnTicket) {
		//	if (remote.key() == TICKET_TYPE.TICKET_TYPE_COUPON) {
		//		EditText editCoupon = (EditText) findViewById(R.id.edit_coupon);
		//		Editable eaCoupon = null;
		//
		//		if (editCoupon != null) {
		//			eaCoupon = editCoupon.getText();
		//		}
		//		if (eaCoupon != null) {
		//			m_strCouponSerial = eaCoupon.toString();
		//		}
		//
		//		m_strCouponSerial.replace(" ", "");
		//
		//		if (TextUtil.isEmpty(m_strCouponSerial)) {
		//			ShowMessageOk(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.coupon_not_input_serial));
		//		} else {
		//			if (m_strCouponSerial.length() != 12) {
		//				ShowMessageOk(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.coupon_wrong_input_serial));
		//				return;
		//			}
		//
		//			KP(REQUEST_COUPON_REGIST, KP_0014, M2_MENU, M2_MENU_SHOP);
		//		}
		//	} else {
		//		//고객센터->모바일설치안내
		//		goCustomer(true, 4);
		//		//븅신...이런게삽질이야
		//		//LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//		//LinearLayout layoutSubMenu = null;
		//		//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//		//Button btnSubMainCustomer = (Button) findViewById(R.id.btn_main_customer);
		//		//
		//		//resetMainMenu();
		//		//
		//		//LinearLayout.LayoutParams lpMainOn = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
		//		//lpMainOn.setMargins(0, 0, 0, 0);
		//		//lpMainOn.weight = 5;
		//		//
		//		//btnSubMainCustomer.setLayoutParams(lpMainOn);
		//		//btnSubMainCustomer.setBackgroundResource(R.drawable.sub_icon_05_focus);
		//		//
		//		//remote.m_iState = STATE_CUSTOMER_MENU;
		//		//remote.m_iMenuMainFocus = 5;
		//		//remote.m_iMenuCustomerFocus = 4;
		//		//
		//		//addViewCustomerMenu();
		//		//
		//		//addViewCustomerListEvent();
		//		//
		//		//displayMenuCustomer(REMOTE_ENTER);
		//	}
		//} else {
		//	if (remote.key() == TICKET_TYPE.TICKET_TYPE_COUPON) {
		//	} else {
		//		// bgkimt 이미 사용중인 이용권이 있으면 이용권 구매 차단
		//		if (!BuildConfig.DEBUG && p_passtype != TICKET_TYPE_NONE) {
		//			ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.ticket_already_use));
		//		} else {
		//
		//			// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//			//if (m_iProcessTicket == TICKET_INDEX_DAY) {
		//			if (remote.key() == TICKET_TYPE.TICKET_TYPE_DAY) {
		//				//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_ppv_info, null);
		//				//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//				//addContentView(message_ticket, param);
		//				message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_ppv_info);
		//			} else {
		//				//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_ppm_info, null);
		//				//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//				//addContentView(message_ticket, param);
		//				message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_ppm_info);
		//			}
		//
		//			//long lTime = System.currentTimeMillis();
		//			//Date date = new Date(lTime);
		//			//Calendar cal = Calendar.getInstance();
		//			//cal.setTime(date);
		//			//cal.add(Calendar.DATE, 1);
		//			//SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년MM월dd일 HH:MM");
		//			//String strCurDate = CurDateFormat.format(cal.getTime());
		//
		//			//if (m_iProcessTicket == TICKET_INDEX_DAY) {
		//			if (remote.key() == TICKET_TYPE.TICKET_TYPE_DAY) {
		//				TextView txtDate = (TextView) findViewById(R.id.txt_message_ticket_day_late_content);
		//				//txtDate.setText("1일 (" + strCurDate + "까지)");
		//				String date = TextUtil.getToday(getString(R.string.message_info_ticket_end_date_format), 1);
		//				txtDate.setText("1일 " + date);
		//			}
		//
		//			remote.m_iState = STATE_MESSAGE_PPX_INFO;
		//			m_iTicketMessageFocusX = 1;
		//			m_iTicketMessageFocusY = 1;
		//		}
		//	}
		//}
	}

	public void clickPPXInfo() {
		try {
			switch (m_iTicketMessageFocusX) {
				case POPUP_OK:
					exitPPV();
					//if (m_iProcessTicket == TICKET_INDEX_DAY) {
					//if (remote.key() == TICKET_TYPE_DAY) {
					//	remote.m_iState = STATE_MESSAGE_PPX_PASS;
					//	m_bIsFocusedOnPassNumber = true;
					//} else {
					//	remote.m_iState = STATE_MESSAGE_PPX_NOTICE;
					//}
					switch (remote.product_type()) {
						case NONE:
							break;
						case PPM:
							remote.m_iState = STATE_MESSAGE_PPX_NOTICE;
							break;
						case PPV:
							remote.m_iState = STATE_MESSAGE_PPX_PASS;
							m_bIsFocusedOnPassNumber = true;
							break;
						case CPN:
							break;
					}

					m_iTicketMessageFocusX = 1;
					m_iTicketMessageFocusY = 1;

					//if (m_iProcessTicket == TICKET_INDEX_DAY) {
					//if (remote.key() == TICKET_TYPE_DAY) {
					//	//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_pass, null);
					//	//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					//	//addContentView(message_ticket, param);
					//	message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_pass);
					//} else {
					//	//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_notice, null);
					//	//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					//	//addContentView(message_ticket, param);
					//	message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_notice);
					//}
					switch (remote.product_type()) {
						case NONE:
							break;
						case PPM:
							message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_notice);
							break;
						case PPV:
							message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_pass);
							break;
						case CPN:
							break;
					}

					break;
				case POPUP_CANCEL:
				default:
					exitPPV();
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	/**
	 * @see Main3XXXXX#showMessagePPX()
	 */
	protected void showMessagePPX() {
	}

	public void clickPPXPass() {
		//switch (m_iTicketMessageFocusX) {
		//	case POPUP_OK:
		//		if (m_iTicketMessageFocusY == 2) {
		//			if (m_strInputPass[0] == "" || m_strInputPass[1] == "" || m_strInputPass[2] == "" || m_strInputPass[3] == "") {
		//				TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);
		//				txtPassResult.setText(getString(R.string.ticket_popup_pass_info_03));
		//			} else {
		//				m_strVASSPassword = "";
		//				m_strVASSPassword = m_strInputPass[0] + m_strInputPass[1] + m_strInputPass[2] + m_strInputPass[3];
		//
		//				//if (m_iProcessTicket == TICKET_INDEX_DAY) {
		//				//if (remote.key() == TICKET_TYPE_DAY) {
		//				//	VASS(REQUEST_VASS_PASSWORD_FOR_DAY);
		//				//} else {
		//				//	VASS(REQUEST_VASS_PASSWORD_FOR_MONTH);
		//				//}
		//				switch(remote.product_type()) {
		//					case NONE:
		//						break;
		//					case PPM:
		//					case PPV:
		//						VASS(REQUEST_VASS.REQUEST_VASS_PPX_PASSWORD);
		//						break;
		//					case CPN:
		//						break;
		//				}
		//			}
		//		}
		//		break;
		//	case POPUP_BACK:
		//		exitPPV();
		//		remote.m_iState = STATE_MESSAGE_PPX_INFO;
		//		m_iTicketMessageFocusX = 1;
		//		showMessagePPX();
		//		break;
		//	case POPUP_CANCEL:
		//		exitPPV();
		//		break;
		//}
	}

	public void clickPPXNotice() {
		TextView txtNotice = (TextView) findViewById(R.id.txt_message_ticket_notice);
		TextView txtNoticePage = (TextView) findViewById(R.id.txt_message_ticket_notice_page);

		TicketItem item = remote.getTicketItem();

		try {
			switch (m_iTicketMessageFocusY) {
				case 1:
					txtNotice.setText(TextUtil.isEmpty(item.product_note_01) ? getString(R.string.app_ticket_product_note_01) : item.product_note_01);
					txtNoticePage.setText(getString(R.string.ticket_popup_bill_info_page_01));
					break;

				case 2:
					txtNotice.setText(TextUtil.isEmpty(item.product_note_02) ? getString(R.string.app_ticket_product_note_02) : item.product_note_02);
					txtNoticePage.setText(getString(R.string.ticket_popup_bill_info_page_02));
					break;

				case 3:
					if (m_iTicketMessageFocusX == 1) {
						exitPPV();

						remote.m_iState = STATE_MESSAGE_PPX_PASS;
						m_bIsFocusedOnPassNumber = true;
						m_iTicketMessageFocusX = 1;
						m_iTicketMessageFocusY = 1;

						message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_pass);
					} else {
						exitPPV();
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickGOCertify() {
		exitPPV();

		if (m_iTicketMessageFocusX == 1) {
			exitTicket();

			remote.m_iMenuShopFocus = 2;
			displayMenuShop(REMOTE_RIGHT);

			// clickMenuShop();
		}
	}

	/**
	 * @see Main3XXXXX#clickShopCertify()
	 */
	public void clickShopCertify() {
	}

	public void clickCertifyHP() {
		if (remote.m_iCertifyHPFocusY == 2) {
			if (remote.m_iCertifyHPFocusX == 1) {
				TextView txtCertifyHPMessage = (TextView) findViewById(R.id.txt_certify_hp_message_auth);

				// 확인
				EditText editHP01 = (EditText) findViewById(R.id.edit_message_hp_01);
				EditText editHP02 = (EditText) findViewById(R.id.edit_message_hp_02);
				EditText editHP03 = (EditText) findViewById(R.id.edit_message_hp_03);

				Editable eaHP01 = editHP01.getText();
				m_strHPNumber = eaHP01.toString();

				if (editHP01.getText().toString().length() < 3) {
					txtCertifyHPMessage.setText("휴대폰 번호를 정확히 입력해주세요.");
					return;
				}

				Editable eaHP02 = editHP02.getText();
				m_strHPNumber = m_strHPNumber + eaHP02.toString();

				if (editHP02.getText().toString().length() < 3) {
					txtCertifyHPMessage.setText("휴대폰 번호를 정확히 입력해주세요.");
					return;
				}

				Editable eaHP03 = editHP03.getText();
				m_strHPNumber = m_strHPNumber + eaHP03.toString();

				if (editHP03.getText().toString().length() < 4) {
					txtCertifyHPMessage.setText("휴대폰 번호를 정확히 입력해주세요.");
					return;
				}

				if (BuildConfig.DEBUG) Log.i(_toString(), "CERTIFYED HP NUM = [" + auth_phoneno + "]");

				// ADDEVENT 원래는 기존 인증번호랑 같은 휴대폰 번호일 때 예외처리 : 나중에 정리해줘
				if (remote.m_iMenuMainFocus != 5) {
					// 디버그모드시무조껀
					if (!BuildConfig.DEBUG && mIsCertifyValidCheck) {

						if (auth_phoneno.equals(m_strHPNumber)) {
							txtCertifyHPMessage.setText("기존 인증된 휴대폰 번호와 같은 번호입니다.");
							return;
						}

						String strCertifyTimerSec = String.valueOf(m_iCertifyTimerSecPopup);
						String strCertifyTimerMin = String.valueOf(m_iCertifyTimerMinPopup);

						if (m_iCertifyTimerMinPopup == 0) {
							ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), "인증번호 재전송 가능 시간까지 " + strCertifyTimerSec + "초 남았습니다.");
						} else {
							ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), "인증번호 재전송 가능 시간까지 " + strCertifyTimerMin + "분 " + strCertifyTimerSec + "초 남았습니다.");
						}

						return;
					}

					KP(REQUEST_AUTH_NUMBER, KP_9001, M1_MAIN, M2_MENU);
				} else {
					// ADDEVENT 입력한 휴대폰 번호를 실제 KP로 날려주는거 아래에 추가해줘
					KP(REQUEST_EVENT_HP, KP_0013, M1_MENU_HELP, M2_HELP_EVENT);
				}

				if (BuildConfig.DEBUG) Log.i(_toString(), "HP NUM = [" + m_strHPNumber + "]");
			} else {
				// 취소
				exitCertifyHP();
			}
		}
	}

	public void clickCertify() {
		if (remote.m_iCertifyFocusY == 1) {
			if (remote.m_iCertifyFocusX == 2) {
				// 재전송
				if (m_bIsCertifyTimerActivated) {
					TextView txtCertifyMessage = (TextView) findViewById(R.id.txt_certify_message);
					if (txtCertifyMessage != null) {
						txtCertifyMessage.setText("입력시간 경과 후 재전송이 가능합니다.");
					}
					return;

					// m_cdTimer.cancel();
				}

				KP(REQUEST_AUTH_NUMBER, KP_9001, M1_MAIN, M2_MENU);
			}
		} else {
			if (remote.m_iCertifyFocusX == 1) {
				EditText editAuth = (EditText) findViewById(R.id.edit_message_certify);
				Editable eaAuth = null;
				if (editAuth != null) {
					eaAuth = editAuth.getText();
				}
				if (eaAuth != null) {
					mStrCertifyNumber = eaAuth.toString();
				}

				if (TextUtil.isEmpty(mStrCertifyNumber) || mStrCertifyNumber.length() < 6) {
					TextView txtCertifyMessage = (TextView) findViewById(R.id.txt_certify_message);
					if (txtCertifyMessage != null) {
						txtCertifyMessage.setText("인증번호를 정확히 입력해주세요.");
					}
					return;
				}

				// 확인
				KP(REQUEST_AUTH_NUMBER_CORRECT, KP_9001, M1_MAIN, M2_MENU);
			} else {
				// 취소
				exitCertifyNumber();
			}
		}
	}

	public void clickMenuCustomer() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + ":" + KP.result_code + ":" + "\n" + mCustomerItems);
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu);

		remote.m_iCustomerListFocus = 1;

		if (m_iEnterCustomerMenu != CUSTOMER_ENTER_KEY) {
			displayMenuCustomer(REMOTE_LEFT);
			m_iEnterCustomerMenu = CUSTOMER_ENTER_KEY;
			return;
		}

		m_iEnterCustomerMenu = CUSTOMER_ENTER_KEY;

		try {
			switch (remote.m_iMenuCustomerFocus) {
				case 1:
					remote.m_iState = STATE_CUSTOMER_LIST_EVENT;
					displayListCustomer(REMOTE_NONE);

					Button btnEvent = (Button) findViewById(R.id.btn_customer_sub_event);
					if (btnEvent != null) {
						//븅신개삽지랄
						//btnEvent.setBackgroundResource(R.drawable.tab_focus_off);
						btnEvent.setPressed(true);
					}
					break;
				case 2:
					remote.m_iState = STATE_CUSTOMER_LIST;
					displayListCustomer(REMOTE_NONE);

					Button btnCustomer = (Button) findViewById(R.id.btn_customer_sub_notice);
					if (btnCustomer != null) {
						//븅신개삽지랄
						//btnCustomer.setBackgroundResource(R.drawable.tab_focus_off);
						btnCustomer.setPressed(true);
					}
					break;
				case 3:
					remote.m_iState = STATE_CUSTOMER_LIST;
					displayListCustomer(REMOTE_NONE);

					Button btnInfo = (Button) findViewById(R.id.btn_customer_sub_info);
					if (btnInfo != null) {
						//븅신개삽지랄
						//btnInfo.setBackgroundResource(R.drawable.tab_focus_off);
						btnInfo.setPressed(true);
					}
					break;
				case 4:
					//븅신개삽지랄...안쓸껀왜만들어선!!!
					//remote.m_iState = STATE_CUSTOMER_APP;
					break;
				case 5:
					//븅신개삽지랄...안쓸껀왜만들어선!!!
					//remote.m_iState = STATE_CUSTOMER_MIC;
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickListCustomer() {
		// 공지사항or이용안내 상세내용 출력
		try {
			int page = m_iCurrentViewCustomerListPage - 1;
			int index = remote.m_iCustomerListFocus + (page * 6);
			index--;
			m_strRequestCustomerDetailID = mCustomerItems.get(index).id;
			m_iCustomerListDetailPage = 1;

			int OP = 0;
			String M2 = "";

			switch (remote.m_iMenuCustomerFocus) {
				case 1: // ADDEVENT 리스트에서 OK눌렀을 때 상세화면가는 KP 아래에 추가해줘
					OP = REQUEST_EVENT_LIST_DETAIL;
					M2 = M2_HELP_EVENT;
					break;
				case 2: // 공지사항
					OP = REQUEST_CUSTOMER_LIST_DETAIL;
					M2 = M2_HELP_NOTICE;
					break;
				case 3: // 이용안내
					OP = REQUEST_CUSTOMER_LIST_DETAIL;
					M2 = M2_HELP_USEINFO;
					break;
			}

			KP(OP, KP_0011, M1_MENU_HELP, M2);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickMenuSearch() {
		try {
			switch (remote.m_iSearchSubMenuFocus) {
				case 1:
					remote.m_iState = STATE_SEARCH_SELF;
					displaySearchSelf(REMOTE_NONE);

					Button btnSearchSubSelf = (Button) findViewById(R.id.btn_search_sub_self);
					if (btnSearchSubSelf != null) {
						btnSearchSubSelf.setBackgroundResource(R.drawable.tab_focus_off);
					}
					break;
				case 2:
					remote.m_iState = STATE_SEARCH_LETTER_KOR;
					m_iSearchLetterMode = KOR;
					displaySearchLetter(REMOTE_NONE);

					Button btnSearchSubLetter = (Button) findViewById(R.id.btn_search_sub_letter);
					if (btnSearchSubLetter != null) {
						btnSearchSubLetter.setBackgroundResource(R.drawable.tab_focus_off);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickSearchSelf() {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ST]" + ":m_iSearchSelfFocus:" + remote.m_iSearchSelfFocus);
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (m_bIsFocusedOnBook) {
			//고객센터->마이크구매안내
			goCustomer(true, 5);
			//븅신...이런게삽질이야
			//// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//LinearLayout layoutSubMenu = null;
			//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			//Button btnSubMain = null;
			//
			//setContentViewKaraoke(m_layoutMain);
			//
			//layoutSubMenu = (LinearLayout) inflate(R.layout.customer_sub, null);
			//layoutSubMenu.setLayoutParams(lp);
			//addView(layout_menu_sub, layoutSubMenu);
			//
			//remote.m_iState = STATE_CUSTOMER_MENU;
			//remote.m_iMenuMainFocus = 5;
			//remote.m_iMenuCustomerFocus = 5;
			//
			//resetMainMenu();
			//
			//LinearLayout.LayoutParams lpMainOn = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
			//lpMainOn.setMargins(0, 0, 0, 0);
			//lpMainOn.weight = 5;
			//
			//btnSubMain = (Button) findViewById(R.id.btn_main_customer);
			//btnSubMain.setBackgroundResource(R.drawable.sub_icon_05_focus);
			//btnSubMain.setLayoutParams(lpMainOn);
			//
			//displayMenuCustomer(REMOTE_ENTER);
			return;
		}

		try {
			switch (remote.m_iSearchSelfFocus) {
				case 1:
					Button btnSearchSelfType = (Button) findViewById(R.id.btn_search_self_type);

					if (m_iSearchSelfMode == TITLE) {
						m_iSearchSelfMode = ARTIST;
						if (btnSearchSelfType != null) {
							btnSearchSelfType.setText(getString(R.string.search_singer));
						}
					} else {
						m_iSearchSelfMode = TITLE;
						if (btnSearchSelfType != null) {
							btnSearchSelfType.setText(getString(R.string.search_title));
						}
					}
					break;
				case 2:
					if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
						EditText editWord = (EditText) findViewById(R.id.edit_search_self_word);
						Editable eaSearchWord = null;
						if (editWord != null) {
							eaSearchWord = editWord.getText();
						}
						if (eaSearchWord != null) {
							m_strSearchWord = eaSearchWord.toString();
						}

						m_strSearchWord.replace(" ", "");

						//editWord.clearFocus();
						//editWord.setSelected(false);
						//editWord.setFocusable(false);
						//editWord.setFocusableInTouchMode(false);
						clearFocus(editWord);

						if (m_strSearchWord.equals("") || m_strSearchWord.equals(null)) {
							ShowMessageOk(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.search_not_keyword));
						} else {
							m_iCurrentViewSearchListPage = 1;
							m_iCurrentSearchListPage = 1;
							m_iRequestSearchListPage = 1;

							if (m_iSearchSelfMode == TITLE) {
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_1);
							} else {
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_2);
							}
						}
					}
					break;
				case 3:
					EditText editWord = (EditText) findViewById(R.id.edit_search_self_word);
					Editable eaSearchWord = null;
					if (editWord != null) {
						eaSearchWord = editWord.getText();
					}
					if (eaSearchWord != null) {
						m_strSearchWord = eaSearchWord.toString();
					}

					//editWord.clearFocus();
					//editWord.setSelected(false);
					//editWord.setFocusable(false);
					//editWord.setFocusableInTouchMode(false);
					clearFocus(editWord);

					m_strSearchWord.replace(" ", "");

					if (m_strSearchWord.equals("") || m_strSearchWord.equals(null)) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), getString(R.string.search_not_keyword));
					} else {
						m_iCurrentViewSearchListPage = 1;
						m_iCurrentSearchListPage = 1;
						m_iRequestSearchListPage = 1;

						if (m_iSearchSelfMode == TITLE) {
							KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_1);
						} else {
							KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_2);
						}
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + "[ED]" + ":m_iSearchSelfFocus:" + remote.m_iSearchSelfFocus);
	}

	public void clickSearchLetter() {
		if (remote.m_iSearchLetterFocusY == 3) {
			//고객센터->마이크구매안내
			goCustomer(true, 5);
			//븅신...이런게삽질이야
			//// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//LinearLayout layoutSubMenu = null;
			//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			//Button btnSubMain = null;
			//
			//setContentViewKaraoke(m_layoutMain);
			//
			//layoutSubMenu = (LinearLayout) inflate(R.layout.customer_sub, null);
			//layoutSubMenu.setLayoutParams(lp);
			//addView(layout_menu_sub, layoutSubMenu);
			//
			//remote.m_iState = STATE_CUSTOMER_MENU;
			//remote.m_iMenuMainFocus = 5;
			//remote.m_iMenuCustomerFocus = 5;
			//
			//resetMainMenu();
			//
			//LinearLayout.LayoutParams lpMainOn = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
			//lpMainOn.setMargins(0, 0, 0, 0);
			//lpMainOn.weight = 5;
			//
			//btnSubMain = (Button) findViewById(R.id.btn_main_customer);
			//btnSubMain.setBackgroundResource(R.drawable.sub_icon_05_focus);
			//btnSubMain.setLayoutParams(lpMainOn);
			//
			//displayMenuCustomer(REMOTE_ENTER);
			return;
		}

		try {
			// 검색 구분 클릭 : 한글/영문/숫자 전환
			if (remote.m_iSearchLetterFocusX == 0) {
				if (m_iSearchLetterMode == KOR) {
					m_iSearchLetterMode = ENG;
					remote.m_iState = STATE_SEARCH_LETTER_ENG;

					LinearLayout layoutKor01 = (LinearLayout) findViewById(R.id.layout_search_letter_input_kor_01);
					LinearLayout layoutKor02 = (LinearLayout) findViewById(R.id.layout_search_letter_input_kor_02);
					LinearLayout layoutEng01 = (LinearLayout) findViewById(R.id.layout_search_letter_input_eng_01);
					LinearLayout layoutEng02 = (LinearLayout) findViewById(R.id.layout_search_letter_input_eng_02);

					layoutKor01.setVisibility(View.GONE);
					layoutKor02.setVisibility(View.GONE);
					layoutEng01.setVisibility(View.VISIBLE);
					layoutEng02.setVisibility(View.VISIBLE);
				} else if (m_iSearchLetterMode == ENG) {
					m_iSearchLetterMode = NUM;
					remote.m_iState = STATE_SEARCH_LETTER_NUM;

					LinearLayout layoutEng01 = (LinearLayout) findViewById(R.id.layout_search_letter_input_eng_01);
					LinearLayout layoutEng02 = (LinearLayout) findViewById(R.id.layout_search_letter_input_eng_02);

					LinearLayout layoutNum01 = (LinearLayout) findViewById(R.id.layout_search_letter_input_num_01);
					LinearLayout layoutNum02 = (LinearLayout) findViewById(R.id.layout_search_letter_input_num_02);

					layoutEng01.setVisibility(View.GONE);
					layoutEng02.setVisibility(View.GONE);
					layoutNum01.setVisibility(View.VISIBLE);
					layoutNum02.setVisibility(View.VISIBLE);
				} else if (m_iSearchLetterMode == NUM) {
					m_iSearchLetterMode = KOR;
					remote.m_iState = STATE_SEARCH_LETTER_KOR;

					LinearLayout layoutNum01 = (LinearLayout) findViewById(R.id.layout_search_letter_input_num_01);
					LinearLayout layoutNum02 = (LinearLayout) findViewById(R.id.layout_search_letter_input_num_02);

					LinearLayout layoutKor01 = (LinearLayout) findViewById(R.id.layout_search_letter_input_kor_01);
					LinearLayout layoutKor02 = (LinearLayout) findViewById(R.id.layout_search_letter_input_kor_02);

					layoutNum01.setVisibility(View.GONE);
					layoutNum02.setVisibility(View.GONE);
					layoutKor01.setVisibility(View.VISIBLE);
					layoutKor02.setVisibility(View.VISIBLE);
				}

				displaySearchLetter(REMOTE_NONE);
				// 색인 클릭
			} else {
				if (m_iSearchLetterMode == KOR) {
					m_strSearchWord = SEARCH_LETTER_KOR[remote.m_iSearchLetterFocusX][remote.m_iSearchLetterFocusY];
				} else if (m_iSearchLetterMode == ENG) {
					m_strSearchWord = SEARCH_LETTER_ENG[remote.m_iSearchLetterFocusX][remote.m_iSearchLetterFocusY];
				} else if (m_iSearchLetterMode == NUM) {
					m_strSearchWord = SEARCH_LETTER_NUM[remote.m_iSearchLetterFocusX][remote.m_iSearchLetterFocusY];
				}

				m_iCurrentViewSearchListPage = 1;
				m_iCurrentSearchListPage = 1;
				m_iRequestSearchListPage = 1;

				KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_4);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickListSearch() {
		remote.m_iSongListDetailFocus = 1;

		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ":m_iSongListFocus:" + remote.m_iSongListFocus);

		remote.m_iState = STATE_SEARCH_LIST_DETAIL;

		try {
			inflateDetailSong();

			if (m_layoutSongListDetail != null) {
				Button btnFavor = (Button) m_layoutSongListDetail.findViewById(R.id.btn_detail_favor);
				if (btnFavor != null) {
					if (isFavor(remote.m_iSearchListFocus)) {
						btnFavor.setText(getString(R.string.favor_delete));
					} else {
						btnFavor.setText(getString(R.string.favor_add));
					}
				}
			}

			LinearLayout layoutList1 = null;
			LinearLayout layoutList2 = null;
			LinearLayout layoutList3 = null;
			LinearLayout layoutList4 = null;
			LinearLayout layoutList5 = null;

			switch (remote.m_iSearchListFocus) {
				case 1:
					if (remote.m_iSearchSubMenuFocus == 1) {
						layoutList1 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_1);
					} else {
						layoutList1 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_1);
					}
					layoutList1.setBackgroundResource(R.drawable.list_focus_off);
					break;
				case 2:
					if (remote.m_iSearchSubMenuFocus == 1) {
						layoutList2 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_2);
					} else {
						layoutList2 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_2);
					}
					layoutList2.setBackgroundResource(R.drawable.list_focus_off);
					break;
				case 3:
					if (remote.m_iSearchSubMenuFocus == 1) {
						layoutList3 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_3);
					} else {
						layoutList3 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_3);
					}
					layoutList3.setBackgroundResource(R.drawable.list_focus_off);
					break;
				case 4:
					if (remote.m_iSearchSubMenuFocus == 1) {
						layoutList4 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_4);
					} else {
						layoutList4 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_4);
					}
					layoutList4.setBackgroundResource(R.drawable.list_focus_off);
					break;
				case 5:
					layoutList5 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_5);
					layoutList5.setBackgroundResource(R.drawable.list_focus_off);
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void clickDetailSearch() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + remote.m_iSearchListFocus + ":" + remote.m_iSongListDetailFocus);

		if (remote.m_iSearchListFocus < 1) {
			return;
		}

		switch (remote.m_iSongListDetailFocus) {
			case 1:
				// 시작
				m_strRequestPlaySongID = getSongIDFromSearchList();
				startSing(getSongIDFromSearchList());
				// 이븅신새끼하는짓좀보소
				// if (!TextUtil.isEmpty(m_strRequestPlaySongID) && TextUtil.isNumeric(m_strRequestPlaySongID)) {
				// if (isPlaying()) {
				// // 재생 중지
				// stopPlay(PLAY_STOP);
				//
				// // 140422 예약된 반주곡을 자동 시작할 때는 중지 처리가 완전히 끝난 뒤에 새 요청 시작 (곡검색 상세메뉴)
				// m_timerStartCurrentSong = new Timer();
				// TaskStartCurrentSong startTask = new TaskStartCurrentSong();
				//
				// StartLoading(LOADING_LONG);
				// m_timerStartCurrentSong.schedule(startTask, 10000);
				// } else {
				// KP(REQUEST_SONG_PLAY, KP_1016, "", "");
				// }
				// }
				break;
			case 2:
				// 예약
				addEngageSong(getSongIDFromSearchList());
				ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), song_ids.get(song_ids.size() - 1) + "번 반주곡이 예약되었습니다.");

				// 140422 재생 중이 아닐 때만 '예약곡 시작' 으로 바뀌도록 함
				if (!isPlaying()) {
					showBottomGuideStartSong();
				}
				break;
			case 3:
				// 애창곡 등록 or 삭제
				if (remote.m_iMenuMainFocus == 3 && remote.m_iMenuMyFocus == 2) {
					m_bIsRefreshFavorList = true;
				}

				m_strRequestFavorSongID = getSongIDFromSearchList();
				KP(REQUEST_FAVOR, KP_3010, "", "");

				if (isFavor(remote.m_iSearchListFocus)) {
					mSongFavors.set(remote.m_iSearchListFocus - 1, "N");
				} else {
					mSongFavors.set(remote.m_iSearchListFocus - 1, "Y");
				}

				displayListSearch(REMOTE_NONE);
				break;
			case 4:
				break;
		}

		exitDetailSearch();
	}

	public void clickMyRecordNone() {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "m_bIsMyRecordReCertifyBtnFocused:" + m_bIsMyRecordReCertifyBtnFocused + ":m_bIsBeforeRegistMyRecord:" + m_bIsBeforeRegistMyRecord);
		//exitMyRecordNone();

		if (m_bIsMyRecordReCertifyBtnFocused) {
			//노래방샵->인증센터
			goShop(true, 2);
			//븅신...이런게삽질이야
			//resetMainMenu();
			//goShop(true, 1);
			//
			//remote.m_iMenuShopFocus = 2;
			//displayMenuShop(REMOTE_RIGHT);
			//
			//Button btnShopSubCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
			//if (btnShopSubCertify != null) {
			//	btnShopSubCertify.setVisibility(View.VISIBLE);
			//}
		} else {
			if (!m_bIsBeforeRegistMyRecord) {
				//고객센터->모바일설치안내
				goCustomer(true, 4);
				//븅신...이런게삽질이야
				//// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//LinearLayout layoutSubMenu = null;
				//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				//
				//LinearLayout.LayoutParams lpMainOn = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
				//lpMainOn.setMargins(0, 0, 0, 0);
				//lpMainOn.weight = 5;
				//
				//resetMainMenu();
				//
				//Button btnSubMainCustomer = (Button) findViewById(R.id.btn_main_customer);
				//btnSubMainCustomer.setLayoutParams(lpMainOn);
				//btnSubMainCustomer.setBackgroundResource(R.drawable.sub_icon_05_focus);
				//
				//layoutSubMenu = (LinearLayout) inflate(R.layout.customer_sub, null);
				//layoutSubMenu.setLayoutParams(lp);
				//addView(layout_menu_sub, layoutSubMenu);
				//
				//remote.m_iState = STATE_CUSTOMER_MENU;
				//remote.m_iMenuMainFocus = 5;
				//remote.m_iMenuCustomerFocus = 4;
				//displayMenuCustomer(REMOTE_ENTER);
			} else {
				//노래방샵->이용권구매
				goShop(true, 1);
			}
		}
	}

	public void clickDetailEvent() {
		if (remote.m_iMenuCustomerFocus != 1) {
			return;
		}

		if (!("E").equalsIgnoreCase(b_type)) {
			return;
		}

		KP(REQUEST_EVENT_APPLY, KP_0012, M1_MENU_HELP, M2_HELP_EVENT);
	}

	/**
	 * @see Main2XXXX#displayMenuHome(int)
	 */
	public void displayMenuHome(int keyID) {
	}

	/**
	 * @see Main2XXXX#displayMenuMain(int)
	 */
	public void displayMenuMain(int keyID) {
	}

	/**
	 * 조회하구와또하나?
	 *
	 * @see #displayMenuMain(int)
	 */
	public void displayMenuSing(int keyID) {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "displayMenuSing >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + String.valueOf(keyID));
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iMenuSingFocus:" + remote.m_iMenuSingFocus);

		if (keyID == REMOTE_UP || keyID == REMOTE_RETURN) {
			exitMenuSing();
			return;
		}

		if (keyID == REMOTE_DOWN) {
			clickMenuSing();
			return;
		}

		// 노래부르기 서브메뉴로 새로 진입해 들어온 경우 초기화
		if (keyID == REMOTE_INIT) {
			remote.m_iState = STATE_SING_MENU;
		}

		// 하단 반주곡 리스트가 새로 출력되어야 하는 경우 포커스 & 페이징 초기화
		if (keyID == REMOTE_INIT || keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			remote.m_iSongListFocus = 1;
			//인기장르
			if (remote.m_iMenuSingFocus == 3) {
				remote.m_iSongListFocus = 2;
			}
			m_iSongItemCount = 0;
			m_iCurrentSongListPage = 1;
			m_iCurrentViewSongListPage = 1;
			m_iTotalSongListPage = 1;
			m_iRequestPage = 1;
		}

		resetSingSubMenu();

		Button btnHot = (Button) findViewById(R.id.btn_sing_sub_hot);
		Button btnNew = (Button) findViewById(R.id.btn_sing_sub_new);
		Button btnGenre = (Button) findViewById(R.id.btn_sing_sub_genre);

		String M1 = "";
		String M2 = "";

		switch (remote.m_iMenuSingFocus) {
			case 1:
				if (btnHot != null) {
					//븅신개삽지랄
					//btnHot.setBackgroundResource(R.drawable.tab_focus_on);
					btnHot.setSelected(true);
				}
				M2 = M2_SING_HOT;
				break;
			case 2:
				if (btnNew != null) {
					//븅신개삽지랄
					//btnNew.setBackgroundResource(R.drawable.tab_focus_on);
					btnNew.setSelected(true);
				}
				M2 = M2_SING_RECENT;
				break;
			case 3:
				if (btnGenre != null) {
					//븅신개삽지랄
					//btnGenre.setBackgroundResource(R.drawable.tab_focus_on);
					btnGenre.setSelected(true);
				}
				M1 = M1_SING_GENRE;
				M2 = M2_GENRE_1;
				break;
		}

		setListBackground(getMethodName());

		// 노래부르기->인기장르
		if (remote.m_iMenuMainFocus == 1 && remote.m_iMenuSingFocus == 3) {
			ShowGenre();
		} else {
			HideGenre();
		}

		if (keyID != REMOTE_NONE && keyID != REMOTE_RETURN) {
			initSongListIndex();

			//if (remote.m_iMenuSingFocus != 3) {
			//	HideGenre();
			//
			//	//븅신개삽지랄
			//	//findViewById(R.id.txt_song_page).setVisibility(View.VISIBLE);
			//	//if (sing_line.get(5).layout != null) {
			//	//	sing_line.get(5).layout.setVisibility(View.VISIBLE);
			//	//}
			//	// if (findViewById(R.id.back_sing_list_list_1) != null) {
			//	// findViewById(R.id.back_sing_list_list_1).setVisibility(View.VISIBLE);
			//	// }
			//} else {
			//	ShowGenre();
			//
			//	//븅신개삽지랄
			//	//findViewById(R.id.txt_song_page).setVisibility(View.INVISIBLE);
			//	//if (sing_line.get(5).layout != null) {
			//	//	sing_line.get(5).layout.setVisibility(View.VISIBLE);
			//	//}
			//	// if (findViewById(R.id.back_sing_list_list_1) != null) {
			//	// findViewById(R.id.back_sing_list_list_1).setVisibility(View.GONE);
			//	// }
			//
			//	resetSingSubGenreMenu();
			//	remote.m_iMenuSingGenreFocus = 1;
			//
			//}

			if (remote.m_iMenuSingFocus == 3) {
				resetSingSubGenreMenu();
				remote.m_iMenuSingGenreFocus = 1;
			}


			if (keyID != REMOTE_INIT) {
				KP(REQUEST_SONG_LIST, KP_1000, M1, M2);
			}
		}

		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void displayMenuSingGenre(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iMenuSingGenreFocus:" + remote.m_iMenuSingGenreFocus);

		if (keyID == REMOTE_UP) {
			exitMenuSingGenre();
			return;
		}

		if (keyID == REMOTE_DOWN) {
			clickMenuSingGenre();
			return;
		}

		// 하단 반주곡 리스트가 새로 출력되어야 하는 경우 포커스 & 페이징 초기화
		if (keyID == REMOTE_INIT || keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			remote.m_iSongListFocus = 2;
			m_iSongItemCount = 0;
			m_iCurrentSongListPage = 1;
			m_iCurrentViewSongListPage = 1;
			m_iTotalSongListPage = 1;
			m_iRequestPage = 1;
		}

		resetSingSubGenreMenu();

		String M2 = null;
		try {
			Button btnBallad = (Button) findViewById(R.id.btn_sing_sub_genre_ballad);
			Button btnDance = (Button) findViewById(R.id.btn_sing_sub_genre_dance);
			Button btnTrot = (Button) findViewById(R.id.btn_sing_sub_genre_trot);
			Button btnRock = (Button) findViewById(R.id.btn_sing_sub_genre_rock);
			Button btnPop = (Button) findViewById(R.id.btn_sing_sub_genre_pop);
			Button btnAni = (Button) findViewById(R.id.btn_sing_sub_genre_ani);

			M2 = "";

			switch (remote.m_iMenuSingGenreFocus) {
				case 1:
					//븅신개삽지랄
					//btnBallad.setBackgroundResource(R.drawable.tab_focus_on);
					btnBallad.setSelected(true);
					M2 = M2_GENRE_1;
					break;
				case 2:
					//븅신개삽지랄
					//btnDance.setBackgroundResource(R.drawable.tab_focus_on);
					btnDance.setSelected(true);
					M2 = M2_GENRE_2;
					break;
				case 3:
					//븅신개삽지랄
					//btnTrot.setBackgroundResource(R.drawable.tab_focus_on);
					btnTrot.setSelected(true);
					M2 = M2_GENRE_3;
					break;
				case 4:
					//븅신개삽지랄
					//btnRock.setBackgroundResource(R.drawable.tab_focus_on);
					btnRock.setSelected(true);
					M2 = M2_GENRE_4;
					break;
				case 5:
					//븅신개삽지랄
					//btnPop.setBackgroundResource(R.drawable.tab_focus_on);
					btnPop.setSelected(true);
					M2 = M2_GENRE_5;
					break;
				case 6:
					//븅신개삽지랄
					//btnAni.setBackgroundResource(R.drawable.tab_focus_on);
					btnAni.setSelected(true);
					M2 = M2_GENRE_6;
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

		if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			KP(REQUEST_SONG_LIST, KP_1000, M1_SING_GENRE, M2);
		}
	}

	/**
	 * @see Main2XXXX#displayListSong(int)
	 */
	public void displayListSing(int keyID) {
	}

	public void displayDetailSong(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iSongListDetailFocus:" + remote.m_iSongListDetailFocus + ":" + m_layoutSongListDetail);

		try {
			if (m_layoutSongListDetail != null) {
				Button btnStart = (Button) findViewById(R.id.btn_detail_play);
				Button btnEngage = (Button) findViewById(R.id.btn_detail_engage);
				Button btnFavor = (Button) findViewById(R.id.btn_detail_favor);
				Button btnClose = (Button) findViewById(R.id.btn_detail_close);

				btnStart.setBackgroundResource(R.drawable.list_pop_focus_01_off);
				btnEngage.setBackgroundResource(R.drawable.list_pop_focus_02_off);
				btnFavor.setBackgroundResource(R.drawable.list_pop_focus_03_off);
				btnClose.setBackgroundResource(R.drawable.list_pop_focus_04_off);

				switch (remote.m_iSongListDetailFocus) {
					case 1:
						btnStart.setBackgroundResource(R.drawable.list_pop_focus_01_over);
						break;
					case 2:
						btnEngage.setBackgroundResource(R.drawable.list_pop_focus_02_over);
						break;
					case 3:
						btnFavor.setBackgroundResource(R.drawable.list_pop_focus_03_over);
						break;
					case 4:
						btnClose.setBackgroundResource(R.drawable.list_pop_focus_04_over);
						break;
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void displayMenuListen(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iMenuListenFocus:" + remote.m_iMenuListenFocus);

		if (keyID == REMOTE_UP || keyID == REMOTE_RETURN) {
			exitMenuListen();
			return;
		}

		if (keyID == REMOTE_DOWN) {
			clickMenuListen();
			return;
		}

		// 븅신아!!!여기는왜안넣는데.
		// 노래부르기 서브메뉴로 새로 진입해 들어온 경우 초기화
		if (keyID == REMOTE_INIT) {
			remote.m_iState = STATE_LISTEN_MENU;
		}

		// 하단 반주곡 리스트가 새로 출력되어야 하는 경우 포커스 & 페이징 초기화
		if (keyID == REMOTE_INIT || keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			remote.m_iListenListFocusX = 1;
			remote.m_iListenListFocusY = 1;
			m_iListenItemCount = 0;
			m_iCurrentListenListPage = 1;
			m_iCurrentViewListenListPage = 1;
			m_iTotalListenListPage = 1;
			m_iRequestPage = 1;
		}

		Button btnNow = (Button) findViewById(R.id.btn_listen_sub_now);
		Button btnWeek = (Button) findViewById(R.id.btn_listen_sub_week);
		Button btnBest = (Button) findViewById(R.id.btn_listen_sub_best);

		resetListenSubMenu();

		String M2 = "";

		switch (remote.m_iMenuListenFocus) {
			case 1:
				M2 = M2_LISTEN_TIMELINE;
				if (null != btnNow) {
					//븅신개삽지랄
					//btnNow.setBackgroundResource(R.drawable.tab_focus_on);
					btnNow.setSelected(true);
				}
				break;
			case 2:
				M2 = M2_LISTEN_WEEK;
				if (null != btnWeek) {
					//븅신개삽지랄
					//btnWeek.setBackgroundResource(R.drawable.tab_focus_on);
					btnWeek.setSelected(true);
				}
				break;
			case 3:
				M2 = M2_LISTEN_TOP100;
				if (null != btnBest) {
					//븅신개삽지랄
					//btnBest.setBackgroundResource(R.drawable.tab_focus_on);
					btnBest.setSelected(true);
				}
				break;
			default:
				M2 = M2_LISTEN_TIMELINE;
				if (null != btnNow) {
					//븅신개삽지랄
					//btnNow.setBackgroundResource(R.drawable.tab_focus_on);
					btnNow.setSelected(true);
				}
				break;
		}

		if (keyID != REMOTE_NONE && keyID != REMOTE_RETURN) {
			// m_iSetListenItemCount = 0;
			m_iListenItemCount = 0;

			KP(REQUEST_LISTEN_LIST, KP_2100, M1_MENU_LISTEN, M2);
		}
	}

	/**
	 * 븅신아왜막았나맞춰봐
	 */
	public void displayListListen(int keyID) {
	}

	/**
	 * <pre>
	 * 다른녹음곡키입력변경
	 * 기존 : REMOTE_DOWN.
	 * 변경 : REMOTE_MENU
	 * </pre>
	 */
	public void displayListening(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState());

		//if (keyID == REMOTE_DOWN)
		{
			m_iListenOtherRequestPage = 1;
			KP(REQUEST_LISTEN_OTHER, "KP_2001", "", "");
			if (BuildConfig.DEBUG) Log.i(_toString(), "current Listening ID : " + m_strListeningSongID);
		}
	}

	public void displayListenOther(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState());

		SetListenOtherFocus(keyID);
	}

	public void displayMenuMy(int keyID) {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "displayMenuMy >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + String.valueOf(keyID));
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (remote.m_iMenuMyFocus != 3) {
			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + KP.result_code + ":" + "\n" + mSongItems);
		} else {
			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":remote.m_iMenuMyFocus:" + remote.m_iMenuMyFocus + ":" + KP.result_code + ":" + "\n" + mListenItems);
		}

		if (keyID == REMOTE_UP || keyID == REMOTE_RETURN) {
			exitMenuMy();
			return;
		}

		if (keyID == REMOTE_DOWN) {
			clickMenuMy();
			return;
		}

		// 하단 반주곡 리스트가 새로 출력되어야 하는 경우 포커스 & 페이징 초기화
		if (keyID == REMOTE_INIT || keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			remote.m_iSongListFocus = 1;
			m_iSongItemCount = 0;
			m_iCurrentSongListPage = 1;
			m_iCurrentViewSongListPage = 1;
			m_iTotalSongListPage = 1;
			m_iRequestPage = 1;

			m_iCurrentListenListPage = 1;
			m_iCurrentViewListenListPage = 1;
			m_iTotalListenListPage = 1;
		}

		resetMySubMenu();

		String M1 = M1_MENU_MYLIST;
		String M2 = "";

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.sing_bg);
		//setListBackground(getMethodName());

		switch (remote.m_iMenuMyFocus) {
			case 1:
				M2 = M2_MYLIST_RECENT;
				break;
			case 2:
				M2 = M2_MYLIST_FAVORITE;
				break;
			case 3:
				break;
			default:
				M2 = M2_MYLIST_RECENT;
				break;
		}

		try {
			if (remote.m_iState != STATE_MAIN_MENU) {
				Button btnRecent = (Button) findViewById(R.id.btn_my_sub_recent);
				Button btnFavor = (Button) findViewById(R.id.btn_my_sub_favor);
				Button btnRecord = (Button) findViewById(R.id.btn_my_sub_record);

				switch (remote.m_iMenuMyFocus) {
					case 1:
						M2 = M2_MYLIST_RECENT;
						if (btnRecent != null) {
							//븅신개삽지랄
							//btnRecent.setBackgroundResource(R.drawable.tab_focus_on);
							btnRecent.setSelected(true);
						}
						break;
					case 2:
						if (btnFavor != null) {
							//븅신개삽지랄
							//btnFavor.setBackgroundResource(R.drawable.tab_focus_on);
							btnFavor.setSelected(true);
						}
						break;
					case 3:
						if (btnRecord != null) {
							//븅신개삽지랄
							//btnRecord.setBackgroundResource(R.drawable.tab_focus_on);
							btnRecord.setSelected(true);
						}
						break;
					default:
						if (btnRecent != null) {
							//븅신개삽지랄
							//btnRecent.setBackgroundResource(R.drawable.tab_focus_on);
							btnRecent.setSelected(true);
						}
						break;
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

		if (keyID != REMOTE_NONE && keyID != REMOTE_RETURN) {
			if (remote.m_iMenuMyFocus != 3) {
				// 컨텐츠 초기화 및 마이 노래방 컨텐츠로 변경
				addViewMyList();

				// SettingListBackground(layoutList, R.drawable.sing_bg);
				setListBackground(getMethodName());

				initMyListIndex();

				KP(REQUEST_SONG_LIST, KP_3000, M1, M2);
			} else {
				m_iRequestPage = 1;
				m_bIsMyRecordReCertifyBtnFocused = false;

				KP(REQUEST_MY_RECORD_LIST, KP_3001, M1_MENU_MYLIST, M2_MYLIST_RECORD);
			}
		}
	}

	/**
	 * @see Main2XXXX#displayListMy(int)
	 */
	public void displayListMy(int keyID) {
	}

	protected void displayMenuShop(int keyID) {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":m_iMenuShopFocus:" + remote.m_iMenuShopFocus);
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "displayMenuShop >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + REMOTE.getKeyID(keyID));
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":m_iMenuShopFocus:" + remote.m_iMenuShopFocus);

		if (keyID == REMOTE_UP) {
			exitMenuShop();
			return;
		}

		if (keyID == REMOTE_ENTER || keyID == REMOTE_DOWN) {
			clickMenuShop();
			return;
		}

		if (keyID != REMOTE_NONE && keyID != REMOTE_RETURN) {
			switch (remote.m_iMenuShopFocus) {
				case 1:
					KP(REQUEST_TICKET_SALES_STATE, "", "", "");
					break;
				case 2:
					KP(REQUEST_CERTIFY_STATE, KP_9000, M1_MAIN, M2_MENU);
					break;
				default:
					KP(REQUEST_TICKET_SALES_STATE, "", "", "");
					break;
			}
		}

		try {
			resetShopSubMenu();

			Button btnShopSubTicket = (Button) findViewById(R.id.btn_shop_sub_ticket);
			Button btnShopSubCertify = (Button) findViewById(R.id.btn_shop_sub_certify);

			switch (remote.m_iMenuShopFocus) {
				case 1:
					if (btnShopSubTicket != null) {
						//븅신개삽지랄
						//btnShopSubTicket.setBackgroundResource(R.drawable.tab_focus_on);
						btnShopSubTicket.setSelected(true);
					}
					break;
				case 2:
					if (btnShopSubCertify != null) {
						//븅신개삽지랄
						//btnShopSubCertify.setBackgroundResource(R.drawable.tab_focus_on);
						btnShopSubCertify.setSelected(true);
					}
					break;
				default:
					if (btnShopSubTicket != null) {
						//븅신개삽지랄
						//btnShopSubTicket.setBackgroundResource(R.drawable.tab_focus_on);
						btnShopSubTicket.setSelected(true);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

	}

	public void displayShopTicket(int keyID) {
		//if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.key());
		//
		//ViewGroup layoutTicket = (ViewGroup) findViewById(R.id.layout_shop_ticket);
		//Button btnPurchase = (Button) findViewById(R.id.btn_shop_purchase);
		//Button btnKPInfo = (Button) findViewById(R.id.btn_shop_kp_info);
		//
		//EditText editCoupon = null;
		//Button btnCouponRegist = null;
		//
		//if (!m_bCouponUser) {
		//	editCoupon = (EditText) findViewById(R.id.edit_coupon);
		//	btnCouponRegist = (Button) findViewById(R.id.btn_coupon_regist);
		//}
		//
		//Button btnDay = (Button) findViewById(R.id.btn_shop_tab_1);
		//Button btnMonth = (Button) findViewById(R.id.btn_shop_tab_2);
		//Button btnCoupon = (Button) findViewById(R.id.btn_shop_tab_3);
		//
		//try {
		//	if (keyID == REMOTE_UP) {
		//		if (m_bIsFocusedOnTicket) {
		//			m_bIsFocusedOnTicket = false;
		//			remote.m_iShopTicketFocusY = 1;
		//			m_iCouponFocus = 1;
		//			btnKPInfo.setBackgroundResource(R.drawable.shop_focus_02_off);
		//
		//			switch (remote.key()) {
		//				case TICKET_TYPE_NONE:
		//					Log.wtf(_toString(), getMethodName() + remote.key());
		//				case TICKET_TYPE_YEAR:
		//					break;
		//				case TICKET_TYPE_MONTH:
		//					layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_month_on);
		//					break;
		//				case TICKET_TYPE_DAY:
		//					layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_day_on);
		//					break;
		//				case TICKET_TYPE_COUPON:
		//					editCoupon.setBackgroundResource(R.drawable.coupon_input_off);
		//					btnCouponRegist.setBackgroundResource(R.drawable.coupon_btn_regist_off);
		//					btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//					//editCoupon.clearFocus();
		//					//editCoupon.setSelected(false);
		//					//editCoupon.setFocusable(false);
		//					//editCoupon.setFocusableInTouchMode(false);
		//					clearFocus(editCoupon);
		//					break;
		//			}
		//			btnPurchase.setBackgroundResource(R.drawable.tab_focus_long_on);
		//		} else {
		//			exitTicket();
		//			return;
		//		}
		//	}
		//} catch (Exception e) {
		//	if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		//}
		//
		//if (keyID == REMOTE_DOWN) {
		//	if (remote.key() == TICKET_TYPE.TICKET_TYPE_COUPON && m_bCouponUser) {
		//		return;
		//	}
		//
		//	m_bIsFocusedOnTicket = true;
		//	remote.m_iShopTicketFocusY = 2;
		//}
		//
		//if (keyID == REMOTE_RIGHT && remote.key() == TICKET_TYPE.TICKET_TYPE_COUPON && !m_bIsFocusedOnTicket) {
		//	m_iCouponFocus = 1;
		//}
		//
		//if (remote.key() == TICKET_TYPE.TICKET_TYPE_COUPON && m_bIsFocusedOnTicket) {
		//	if (keyID == REMOTE_LEFT) {
		//		m_iCouponFocus = 1;
		//	} else if (keyID == REMOTE_RIGHT) {
		//		m_iCouponFocus = 2;
		//	}
		//}
		//
		////LinearLayout layoutLeft = (LinearLayout) findViewById(R.id.layout_shop_ticket_left);
		////LinearLayout layoutRight = (LinearLayout) findViewById(R.id.layout_shop_ticket_right);
		////LinearLayout layoutCoupon = (LinearLayout) findViewById(R.id.layout_shop_coupon_regist);
		////
		////if (layoutLeft == null || layoutRight == null || layoutCoupon == null) {
		////	return;
		////}
		//
		//TextView txtSubscripte = (TextView) findViewById(R.id.txt_shop_info);
		//
		//if (btnDay != null) {
		//	btnDay.setBackgroundResource(R.drawable.shop_ticket_tab_off);
		//}
		//
		//if (btnMonth != null) {
		//	btnMonth.setBackgroundResource(R.drawable.shop_ticket_tab_off);
		//}
		//if (btnCoupon != null) {
		//	btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_off);
		//}
		//
		//ImageView item = (ImageView) findViewById(R.id.img_shop_ticket);
		//
		//if (!m_bIsFocusedOnTicket) {
		//	TextView txtPrice = (TextView) findViewById(R.id.txt_shop_price);
		//
		//	switch (remote.key()) {
		//		case TICKET_TYPE_NONE:
		//			Log.wtf(_toString(), getMethodName() + remote.key());
		//		case TICKET_TYPE_YEAR:
		//			break;
		//		case TICKET_TYPE_MONTH:
		//			if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + getTicketItemMonth());
		//			//layoutLeft.setVisibility(View.VISIBLE);
		//			//layoutRight.setVisibility(View.VISIBLE);
		//			//layoutCoupon.setVisibility(View.GONE);
		//
		//			txtSubscripte.setText(R.string.app_text_ticket_vat);
		//			btnKPInfo.setVisibility(View.VISIBLE);
		//
		//			btnDay.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//			layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_month_on);
		//			item.setImageBitmap(util_shopItem01.m_bitMap);
		//			// txtPrice.setText(getString(R.string.ticket_month_price));
		//			txtPrice.setText(getTicketItemMonth().price + "원");
		//			break;
		//		case TICKET_TYPE_DAY:
		//			if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + getTicketItemDay());
		//			editCoupon.setText("");
		//
		//			//layoutLeft.setVisibility(View.VISIBLE);
		//			//layoutRight.setVisibility(View.VISIBLE);
		//			//layoutCoupon.setVisibility(View.GONE);
		//
		//			txtSubscripte.setText(R.string.app_text_ticket_vat);
		//			btnKPInfo.setVisibility(View.VISIBLE);
		//
		//			btnMonth.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//			layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_day_on);
		//			item.setImageBitmap(util_shopItem02.m_bitMap);
		//			// txtPrice.setText(getString(R.string.ticket_day_price));
		//			txtPrice.setText(getTicketItemDay().price + "원");
		//			break;
		//		case TICKET_TYPE_COUPON:
		//			//layoutLeft.setVisibility(View.GONE);
		//			//layoutRight.setVisibility(View.GONE);
		//			//layoutCoupon.setVisibility(View.VISIBLE);
		//
		//			LinearLayout layoutRegisted = (LinearLayout) findViewById(R.id.layout_coupon_registed);
		//			LinearLayout layoutRegist = (LinearLayout) findViewById(R.id.layout_coupon_not_regist);
		//			if (m_bCouponUser) {
		//				layoutRegisted.setVisibility(View.VISIBLE);
		//				layoutRegist.setVisibility(View.GONE);
		//
		//				btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//				layoutTicket.setBackgroundResource(R.drawable.shop_coupon_bg_registed_on);
		//
		//				TextView txtCouponTerm = (TextView) findViewById(R.id.txt_coupon_term);
		//				txtCouponTerm.setText(m_strCouponTerm);
		//			} else {
		//				layoutRegisted.setVisibility(View.GONE);
		//				layoutRegist.setVisibility(View.VISIBLE);
		//
		//				btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//				layoutTicket.setBackgroundResource(R.drawable.shop_coupon_bg_regist_on);
		//			}
		//
		//			txtSubscripte.setText(R.string.coupon_subscripte);
		//			btnKPInfo.setVisibility(View.INVISIBLE);
		//
		//			item.setImageBitmap(null);
		//			break;
		//	}
		//} else {
		//	switch (remote.key()) {
		//		case TICKET_TYPE_NONE:
		//			Log.wtf(_toString(), getMethodName() + remote.key());
		//		case TICKET_TYPE_YEAR:
		//			break;
		//		case TICKET_TYPE_MONTH:
		//			layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_month_off);
		//			btnPurchase.setBackgroundResource(R.drawable.tab_focus_long_off);
		//			btnKPInfo.setBackgroundResource(R.drawable.shop_focus_02_on);
		//			break;
		//		case TICKET_TYPE_DAY:
		//			layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_day_off);
		//			btnPurchase.setBackgroundResource(R.drawable.tab_focus_long_off);
		//			btnKPInfo.setBackgroundResource(R.drawable.shop_focus_02_on);
		//			break;
		//		case TICKET_TYPE_COUPON:
		//			// btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//			btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_off);
		//
		//			if (m_iCouponFocus == 1) {
		//				editCoupon.setBackgroundResource(R.drawable.coupon_input_on);
		//				btnCouponRegist.setBackgroundResource(R.drawable.coupon_btn_regist_off);
		//
		//				// editCoupon.setText("");
		//
		//				requestFocus(editCoupon);
		//
		//				setIMELocation();
		//			} else {
		//				editCoupon.setBackgroundResource(R.drawable.coupon_input_off);
		//				btnCouponRegist.setBackgroundResource(R.drawable.coupon_btn_regist_on);
		//
		//				editCoupon.clearFocus();
		//				editCoupon.setSelected(false);
		//				editCoupon.setFocusable(false);
		//				editCoupon.setFocusableInTouchMode(false);
		//			}
		//			break;
		//	}
		//}
	}

	/**
	 * <pre>
	 * 으그븅신아
	 * </pre>
	 */
	public void setSelectedPPXInfoOkCancel(int keyID) {
	}

	/**
	 * @see Main2XXXX#displayPPXPass(int)
	 */
	public void displayPPXPass(int keyID) {
	}

	/**
	 * @see Main2XXXX#setSelectedPPMNotice(int)
	 */
	public void setSelectedPPMNotice(int keyID) {
	}

	public void displayGOCertify(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		Button btnOK = (Button) findViewById(R.id.btn_message_ticket_go_certify_ok);
		Button btnCancel = (Button) findViewById(R.id.btn_message_ticket_go_certify_cancel);

		try {
			if (keyID == REMOTE_LEFT) {
				m_iTicketMessageFocusX = 1;

				btnOK.setBackgroundResource(R.drawable.pop_btn_01_on);
				btnCancel.setBackgroundResource(R.drawable.pop_btn_01_off);
			} else if (keyID == REMOTE_RIGHT) {
				m_iTicketMessageFocusX = 2;

				btnOK.setBackgroundResource(R.drawable.pop_btn_01_off);
				btnCancel.setBackgroundResource(R.drawable.pop_btn_01_on);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void displayShopCertify(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		if (keyID == REMOTE_UP) {
			exitCertify();
			return;
		}
	}

	/**
	 * @see Main2X#displayCertifyHP(int)
	 */
	@Deprecated
	public void displayCertifyHP(int keyID) {
	}

	/**
	 * @see Main2X#displayCertify(int)
	 */
	@Deprecated
	public void displayCertify(int keyID) {
	}

	protected void displayMenuCustomer(int keyID) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + "\n" + mCustomerItems);

		Button btnCustomerSubEvent = (Button) findViewById(R.id.btn_customer_sub_event);
		Button btnCustomerSubNotice = (Button) findViewById(R.id.btn_customer_sub_notice);
		Button btnCustomerSubInfo = (Button) findViewById(R.id.btn_customer_sub_info);
		Button btnCustomerSubApp = (Button) findViewById(R.id.btn_customer_sub_app);
		Button btnCustomerSubMic = (Button) findViewById(R.id.btn_customer_sub_mic);

		if (keyID == REMOTE_DOWN) {
			if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus);
			if (m_bDisplayingCustomerDetail) {
				remote.m_iState = STATE_CUSTOMER_LIST_DETAIL;

				switch (remote.m_iMenuCustomerFocus) {
					case 1:
						if (btnCustomerSubEvent != null) {
							//븅신개삽지랄
							//btnCustomerSubEvent.setBackgroundResource(R.drawable.tab_focus_off);
							btnCustomerSubEvent.setPressed(true);
						}
						break;
					case 2:
						if (btnCustomerSubNotice != null) {
							//븅신개삽지랄
							//btnCustomerSubNotice.setBackgroundResource(R.drawable.tab_focus_off);
							btnCustomerSubNotice.setPressed(true);
						}
						break;
					case 3:
						if (btnCustomerSubInfo != null) {
							//븅신개삽지랄
							//btnCustomerSubInfo.setBackgroundResource(R.drawable.tab_focus_off);
							btnCustomerSubInfo.setPressed(true);
						}
						break;
				}

				if (remote.m_iMenuCustomerFocus == 1) {
					if (m_bIsEventDetail) {
						ImageView imgEventDetailOn = (ImageView) findViewById(R.id.img_customer_detail_content);
						if (imgEventDetailOn != null) {
							imgEventDetailOn.setImageBitmap(m_bitMapEventOn);
						}
					}
				}
			} else {
				clickMenuCustomer();
			}
			return;
		}

		if (keyID == REMOTE_UP || keyID == REMOTE_RETURN) {
			exitMenuCustomer();
			return;
		}

		resetCustomerSubMenu();

		if (remote.m_iState != STATE_MAIN_MENU) {
			if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus);
			switch (remote.m_iMenuCustomerFocus) {
				case 1:
					if (btnCustomerSubEvent != null) {
						//븅신개삽지랄
						//btnCustomerSubEvent.setBackgroundResource(R.drawable.tab_focus_on);
						btnCustomerSubEvent.setSelected(true);
					}
					break;
				case 2:
					if (btnCustomerSubNotice != null) {
						//븅신개삽지랄
						//btnCustomerSubNotice.setBackgroundResource(R.drawable.tab_focus_on);
						btnCustomerSubNotice.setSelected(true);
					}
					break;
				case 3:
					if (btnCustomerSubInfo != null) {
						//븅신개삽지랄
						//btnCustomerSubInfo.setBackgroundResource(R.drawable.tab_focus_on);
						btnCustomerSubInfo.setSelected(true);
					}
					break;
				case 4:
					if (btnCustomerSubApp != null) {
						//븅신개삽지랄
						//btnCustomerSubApp.setBackgroundResource(R.drawable.tab_focus_on);
						btnCustomerSubApp.setSelected(true);
					}
					break;
				case 5:
					if (btnCustomerSubMic != null) {
						//븅신개삽지랄
						//btnCustomerSubMic.setBackgroundResource(R.drawable.tab_focus_on);
						btnCustomerSubMic.setSelected(true);
					}
					break;
			}
		}

		setListBackground(getMethodName());

		if (keyID == REMOTE_NONE) {
			return;
			//switch (remote.m_iMenuCustomerFocus) {
			//	case 1:
			//		break;
			//	case 2:
			//		break;
			//	default:
			//		return;
			//}
		}

		try {
			TextView txtPage = (TextView) findViewById(R.id.txt_customer_sub_page);

			// 컨텐츠 영역에 이전 뷰를 삭제하고 해당 서브메뉴에 맞는 뷰를 보여줌
			switch (remote.m_iMenuCustomerFocus) {
				case 1:
					addViewCustomerListEvent();
					resetCustomerList();
					//if (txtPage != null) {
					//	txtPage.setVisibility(View.VISIBLE);
					//}
					break;
				case 2:
					addViewCustomerList();
					resetCustomerList();
					//if (txtPage != null) {
					//	txtPage.setVisibility(View.VISIBLE);
					//}
					break;
				case 3:
					addViewCustomerList();
					resetCustomerList();
					//if (txtPage != null) {
					//	txtPage.setVisibility(View.VISIBLE);
					//}
					break;
				case 4:
					addViewCustomerApp();
					if (txtPage != null) {
						txtPage.setVisibility(View.INVISIBLE);
					}
					break;
				case 5:
					addViewCustomerMic();
					if (txtPage != null) {
						txtPage.setVisibility(View.INVISIBLE);
					}
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

		if (keyID == REMOTE_LEFT || keyID == REMOTE_RIGHT) {
			m_iEnterCustomerMenu = CUSTOMER_ENTER_KEY;
		}

		if (m_iEnterCustomerMenu != CUSTOMER_ENTER_KEY) {
			initCustomerListIndex();
			return;
		}

		// LEFT, RIGHT로 서브메뉴간 이동할 때
		if (keyID != REMOTE_NONE && keyID != REMOTE_RETURN) {
			// remote.m_iCustomerListFocus = 0;
			initCustomerListIndex();

			if (remote.m_iMenuCustomerFocus == 1) {
				KP(REQUEST_EVENT_LIST, KP_0010, M1_MENU_HELP, M2_HELP_EVENT);
			} else if (remote.m_iMenuCustomerFocus == 2) {
				KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_NOTICE);
			} else if (remote.m_iMenuCustomerFocus == 3) {
				KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_USEINFO);
			} else {
				stopLoading(getMethodName());
			}
		} else {
			stopLoading(getMethodName());
		}

		//if (BuildConfig.DEBUG) Log.i(_toString(), "displayMenuCustomer <");
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + "\n" + mCustomerItems);
	}

	public void displayListCustomer(int keyID) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + REMOTE_STATE.get(keyID) + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + ":m_bDisplayingCustomerDetail:" + m_bDisplayingCustomerDetail);
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + mCustomerItems);

		// if (BuildConfig.DEBUG) LOG.i(_toString(), "displayListCustomer >");
		// if (BuildConfig.DEBUG) LOG.i(_toString(), "keyID=" + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewCustomerListPage + "/toPage=" + m_iTotalCustomerListPage);
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewCustomerListPage + "/totPage=" + m_iTotalCustomerListPage);

		if (m_bDisplayingCustomerDetail) {
			if (remote.m_iMenuCustomerFocus != 1) {
				remote.m_iState = STATE_CUSTOMER_LIST;
				addViewCustomerList();
			} else {
				remote.m_iState = STATE_CUSTOMER_LIST_EVENT;
				addViewCustomerListEvent();
			}
		}

		if (keyID == REMOTE_UP) {
			if (remote.m_iCustomerListFocus == 0) {
				exitListCustomer();
				return;
			}
		}

		// if (m_iTotalCustomerListPage > 1) {
		// // LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// //SettingListBackground(layoutList, R.drawable.sing_bg_narrow);
		// }

		if (keyID == REMOTE_RIGHT) {
			if (m_iCurrentViewCustomerListPage < m_iTotalCustomerListPage) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "next");
				remote.m_iCustomerListFocus = 1;

				m_iCurrentCustomerListPage++;
				m_iCurrentViewCustomerListPage++;

				// 1, 21, 31... 페이지면 새로 요청
				if (m_iCurrentViewCustomerListPage % 10 == 1) {
					m_iRequestPage++;
					m_iCurrentCustomerListPage = 1;

					switch (remote.m_iMenuCustomerFocus) {
						case 1:
							KP(REQUEST_EVENT_LIST, KP_0010, M1_MENU_HELP, M2_HELP_EVENT);
							break;
						case 2:
							KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_NOTICE);
							break;
						case 3:
							KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_USEINFO);
							break;
					}
				} else {
					moveCustomerListPage();
				}
			} else {
				// 마지막 페이지에서 RIGHT키 입력 시 1페이지로 이동
				if (m_iTotalCustomerListPage > 1) {
					remote.m_iCustomerListFocus = 1;

					m_iCurrentViewCustomerListPage = 1;
					m_iCurrentCustomerListPage = 1;
					int temp = m_iRequestPage;
					m_iRequestPage = 1;
					// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
					if (temp != m_iRequestPage) {
						switch (remote.m_iMenuCustomerFocus) {
							case 1:
								KP(REQUEST_EVENT_LIST, KP_0010, M1_MENU_HELP, M2_HELP_EVENT);
								break;
							case 2:
								KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_NOTICE);
								break;
							case 3:
								KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_USEINFO);
								break;
						}
					}

					moveCustomerListPage();
				}
			}
		} else if (keyID == REMOTE_LEFT) {
			if (m_iCurrentViewCustomerListPage > 1) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "prev");
				remote.m_iCustomerListFocus = 1;

				m_iCurrentCustomerListPage--;
				m_iCurrentViewCustomerListPage--;

				// 10, 20, 30... 페이지면 새로 요청
				if (m_iCurrentViewCustomerListPage % 10 == 0) {
					m_iRequestPage--;
					m_iCurrentCustomerListPage = 10;

					switch (remote.m_iMenuCustomerFocus) {
						case 1:
							KP(REQUEST_EVENT_LIST, KP_0010, M1_MENU_HELP, M2_HELP_EVENT);
							break;
						case 2:
							KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_NOTICE);
							break;
						case 3:
							KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_USEINFO);
							break;
					}
				} else {
					moveCustomerListPage();
				}
			} else {
				// 1페이지에서 LEFT키 입력 시 마지막 페이지로 이동
				if (m_iTotalCustomerListPage > 1) {
					remote.m_iCustomerListFocus = 1;

					m_iCurrentViewCustomerListPage = m_iTotalCustomerListPage;
					m_iCurrentCustomerListPage = m_iTotalCustomerListPage % 10;
					if (m_iCurrentCustomerListPage == 0) {
						m_iCurrentCustomerListPage = 10;
					} else {
						int temp = m_iRequestPage;
						m_iRequestPage = (m_iTotalCustomerListPage / 10) + 1;
						// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
						if (temp != m_iRequestPage) {
							switch (remote.m_iMenuCustomerFocus) {
								case 1:
									KP(REQUEST_EVENT_LIST, KP_0010, M1_MENU_HELP, M2_HELP_EVENT);
									break;
								case 2:
									KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_NOTICE);
									break;
								case 3:
									KP(REQUEST_CUSTOMER_LIST, KP_0010, M1_MENU_HELP, M2_HELP_USEINFO);
									break;
							}
						}
					}

					moveCustomerListPage();
				}
			}
		}

		resetCustomerList();

		try {
			LinearLayout layoutList1 = (LinearLayout) findViewById(R.id.layout_customer_list_list_1);
			LinearLayout layoutList2 = (LinearLayout) findViewById(R.id.layout_customer_list_list_2);
			LinearLayout layoutList3 = (LinearLayout) findViewById(R.id.layout_customer_list_list_3);
			LinearLayout layoutList4 = (LinearLayout) findViewById(R.id.layout_customer_list_list_4);
			LinearLayout layoutList5 = (LinearLayout) findViewById(R.id.layout_customer_list_list_5);
			LinearLayout layoutList6 = (LinearLayout) findViewById(R.id.layout_customer_list_list_6);

			switch (remote.m_iCustomerListFocus) {
				case 1:
					layoutList1.setBackgroundResource(R.drawable.list_focus_on);
					break;
				case 2:
					layoutList2.setBackgroundResource(R.drawable.list_focus_on);
					break;
				case 3:
					layoutList3.setBackgroundResource(R.drawable.list_focus_on);
					break;
				case 4:
					layoutList4.setBackgroundResource(R.drawable.list_focus_on);
					break;
				case 5:
					layoutList5.setBackgroundResource(R.drawable.list_focus_on);
					break;
				case 6:
					layoutList6.setBackgroundResource(R.drawable.list_focus_on);
					break;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + REMOTE_STATE.get(keyID) + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + ":m_bDisplayingCustomerDetail:" + m_bDisplayingCustomerDetail);
	}

	public void displayDetailCustomer(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus);

		if (m_iCustomerListDetailPage == 1) {
			if (keyID == REMOTE_UP) {
				switch (remote.m_iMenuCustomerFocus) {
					case 1:
						if (m_bIsEventDetail) {
							ImageView imgEventDetailOff = (ImageView) findViewById(R.id.img_customer_detail_content);
							if (imgEventDetailOff != null) {
								imgEventDetailOff.setImageBitmap(m_bitMapEventOff);
							}
						}
						break;
					case 2:
						break;
					case 3:
						break;
				}

				displayMenuCustomer(REMOTE_NONE);
				initCustomerListIndex();
				remote.m_iState = STATE_CUSTOMER_MENU;

				m_bDisplayingCustomerDetail = true;
				return;
			}
		}

		if (m_iCustomerListDetailTotalPage > 1) {
			TextView txtPage = (TextView) findViewById(R.id.txt_customer_detail_page);

			if (keyID == REMOTE_UP) {
				if (m_iCurrentCustomerListDetailPage > 1) {
					m_iCurrentCustomerListDetailPage--;
					m_iCustomerListDetailPage--;
					txtPage.setText(String.valueOf(m_iCurrentCustomerListDetailPage) + "/" + String.valueOf(m_iCustomerListDetailTotalPage));

					util = new _Util(handlerKP);
					util.setUtilType(REQUEST_UTIL_CUSTOMER_DETAIL_IMAGE);
					util.setImageUrl(url_imgs.get(m_iCustomerListDetailPage - 1));
					if (BuildConfig.DEBUG) Log.i(_toString(), "Detail URL is " + url_imgs.get(m_iCustomerListDetailPage - 1));
					util.start();
				}
			} else if (keyID == REMOTE_DOWN) {
				if (m_iCurrentCustomerListDetailPage < m_iCustomerListDetailTotalPage) {
					m_iCurrentCustomerListDetailPage++;
					m_iCustomerListDetailPage++;
					txtPage.setText(String.valueOf(m_iCurrentCustomerListDetailPage) + "/" + String.valueOf(m_iCustomerListDetailTotalPage));

					util = new _Util(handlerKP);
					util.setUtilType(REQUEST_UTIL_CUSTOMER_DETAIL_IMAGE);
					util.setImageUrl(url_imgs.get(m_iCustomerListDetailPage - 1));
					if (BuildConfig.DEBUG) Log.i(_toString(), "Detail URL is " + url_imgs.get(m_iCustomerListDetailPage - 1));
					util.start();
				}
			}
		}
	}

	public void displayMenuSearch(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iSearchSubMenuFocus:" + remote.m_iSearchSubMenuFocus);

		if (keyID == REMOTE_UP) {
			exitMenuSearch();
			return;
		}

		if (keyID == REMOTE_DOWN) {
			clickMenuSearch();
			return;
		}

		resetSearchSubMenu();

		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.search_bg_none);
		setListBackground(getMethodName());

		switch (remote.m_iSearchSubMenuFocus) {
			case 1:
				Button btnSearchSubSelf = (Button) findViewById(R.id.btn_search_sub_self);
				btnSearchSubSelf.setBackgroundResource(R.drawable.tab_focus_on);

				// 직접 컨텐츠로 전환
				LinearLayout layoutSearchSelf = null;
				if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
					layoutSearchSelf = (LinearLayout) inflate(R.layout.search_list_self_box, null);
				} else {
					layoutSearchSelf = (LinearLayout) inflate(R.layout.search_list_self, null);
				}

				layoutSearchSelf.setLayoutParams(lp);
				addView(layout_content, layoutSearchSelf);
				break;
			case 2:
				Button btnSearchSubLetter = (Button) findViewById(R.id.btn_search_sub_letter);
				btnSearchSubLetter.setBackgroundResource(R.drawable.tab_focus_on);

				// 색인 컨텐츠로 전환
				LinearLayout layoutSearchLetter = (LinearLayout) inflate(R.layout.search_list_letter, null);
				layoutSearchLetter.setLayoutParams(lp);
				addView(layout_content, layoutSearchLetter);
				break;
		}
	}

	/**
	 * 입력가능최대길이
	 */
	private void setEditMaxLength(EditText edit, int max) {
		edit.setSingleLine();
		int maxLength = max;
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(maxLength);
		edit.setFilters(filterArray);
	}

	public void displaySearchSelf(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iSearchSelfFocus:" + remote.m_iSearchSelfFocus);
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		final EditText edit = (EditText) findViewById(R.id.edit_search_self_word);

		if (!m_bIsFocusedOnBook) {
			if (keyID == REMOTE_UP) {
				if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
					edit.setBackgroundResource(R.drawable.search_input_off);

					edit.clearFocus();
					edit.setSelected(false);
					edit.setFocusable(false);
					edit.setFocusableInTouchMode(false);
				}

				exitSearchSelf();
				return;
			}
		} else {
			m_bIsFocusedOnBook = false;
		}

		LinearLayout layoutTip = (LinearLayout) findViewById(R.id.layout_search_self_list_tip);
		layoutTip.setVisibility(View.VISIBLE);

		LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_search_self_list_list);
		layoutList.setVisibility(View.GONE);

		TextView txtPage = (TextView) findViewById(R.id.txt_search_self_page);
		txtPage.setVisibility(View.INVISIBLE);

		final Button btnType = (Button) findViewById(R.id.btn_search_self_type);
		final Button btnOk = (Button) findViewById(R.id.btn_search_self_ok);

		Button btnBook = (Button) findViewById(R.id.btn_purchase_book_search_self);
		btnBook.setBackgroundResource(R.drawable.tab_focus_long_off);

		if (keyID == REMOTE_DOWN && !m_bIsFocusedOnBook) {
			btnType.setBackgroundResource(R.drawable.search_btn_off);
			edit.setBackgroundResource(R.drawable.search_input_off);

			if (m_strSTBVender != P_APPNAME_SKT_BOX) {
				btnOk.setBackgroundResource(R.drawable.search_ok_btn_off);
			}

			btnBook.setBackgroundResource(R.drawable.tab_focus_long_on);

			edit.clearFocus();
			edit.setSelected(false);
			edit.setFocusable(false);
			edit.setFocusableInTouchMode(false);

			m_bIsFocusedOnBook = true;
			return;
		}

		clearFocus(btnType);
		clearFocus(edit);
		clearFocus(btnOk);

		switch (remote.m_iSearchSelfFocus) {
			case 1:
				btnType.setBackgroundResource(R.drawable.search_btn_on);
				edit.setBackgroundResource(R.drawable.search_input_off);

				hideSoftKeyboard(edit);
				clearFocus(edit);
				edit.setOnEditorActionListener(null);

				requestFocus(btnType);
				btnType.setClickable(true);
				btnType.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(v) + ":" + v);
						clickSearchSelf();
					}
				});
				break;
			case 2:
				btnType.setBackgroundResource(R.drawable.search_btn_off);
				edit.setBackgroundResource(R.drawable.search_input_on);

				// if (m_strSTBVender == SMART_STB || m_strSTBVender == SMART_UHD) {
				// btnOk.setBackgroundResource(R.drawable.search_ok_btn_off);
				// }
				if (m_strSTBVender != P_APPNAME_SKT_BOX) {
					btnOk.setBackgroundResource(R.drawable.search_ok_btn_off);
				}

				// editWord.setText("");

				requestFocus(edit);

				if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
					setIMELocation();
					edit.setOnEditorActionListener(new OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
							if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(v) + ":" + v + ", " + keyCode + ", " + event);
							if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
								clickSearchSelf();
							}
							return false;
						}
					});
					// editWord.setOnEditorActionListener(new OnEditorActionListener() {
					// @Override
					// public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
					// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + getResourceEntryName(v) + ":" + v + ", " + keyCode + ", " + event);
					// if (keyCode == KeyEvent.KEYCODE_ENDCALL || keyCode == KeyEvent.KEYCODE_ENTER || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					// clickSearchSelf();
					// }
					// return false;
					// }
					// });
				} else {
					showSoftKeyboard(edit);
				}
				break;
			case 3:
				edit.setBackgroundResource(R.drawable.search_input_off);
				btnOk.setBackgroundResource(R.drawable.search_ok_btn_on);

				hideSoftKeyboard(edit);
				clearFocus(edit);
				edit.setOnEditorActionListener(null);

				requestFocus(btnOk);
				btnOk.setClickable(true);
				btnOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + getResourceEntryName(v) + ":" + v);
						clearFocus(btnOk);
						clickSearchSelf();
					}
				});
				break;
		}
	}

	public void displaySearchLetter(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iSearchLetterFocusX:" + remote.m_iSearchLetterFocusX + ":remote.m_iSearchLetterFocusY:" + remote.m_iSearchLetterFocusY);
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "displaySearchLetter >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + REMOTE_STATE.get(keyID) + "/m_iSearchLetterFocusX=" + remote.m_iSearchLetterFocusX + "/m_iSearchLetterFocusY=" + remote.m_iSearchLetterFocusY);
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + "/m_iSearchLetterFocusX=" + remote.m_iSearchLetterFocusX + "/m_iSearchLetterFocusY=" + remote.m_iSearchLetterFocusY);

		if (remote.m_iSearchLetterFocusY != 3) {
			if (keyID == REMOTE_UP) {
				if (remote.m_iSearchLetterFocusY == 0) {
					exitSearchLetter();
					return;
				}
			}
		}

		if (keyID != REMOTE_INIT) {
			LinearLayout layoutTip = (LinearLayout) findViewById(R.id.layout_search_letter_list_tip);
			if (layoutTip != null) {
				layoutTip.setVisibility(View.VISIBLE);
			}

			LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_search_letter_list_list);
			if (layoutList != null) {
				layoutList.setVisibility(View.GONE);
			}

			TextView txtPage = null;
			if (m_iSearchLetterMode == KOR) {
				txtPage = (TextView) findViewById(R.id.txt_search_letter_kor_page);
			} else if (m_iSearchLetterMode == ENG) {
				txtPage = (TextView) findViewById(R.id.txt_search_letter_eng_page);
			} else if (m_iSearchLetterMode == NUM) {
				txtPage = (TextView) findViewById(R.id.txt_search_letter_num_page);
			}
			if (txtPage != null) {
				txtPage.setVisibility(View.INVISIBLE);
			}
		}

		resetSearchLetter();

		Button btnBook = (Button) findViewById(R.id.btn_purchase_book_search_letter);
		btnBook.setBackgroundResource(R.drawable.tab_focus_long_off);

		if (remote.m_iSearchLetterFocusY == 3) {
			btnBook.setBackgroundResource(R.drawable.tab_focus_long_on);
			return;
		}

		if (m_iSearchLetterMode == KOR) {
			Button btnKorMode = null;
			Button btnKor0000 = null;

			switch (remote.m_iSearchLetterFocusY) {
				case 1:
					switch (remote.m_iSearchLetterFocusX) {
						case 0:
							btnKorMode = (Button) findViewById(R.id.btn_search_letter_type_kor);
							break;
						case 1:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_01);
							break;
						case 2:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_02);
							break;
						case 3:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_03);
							break;
						case 4:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_04);
							break;
						case 5:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_05);
							break;
						case 6:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_06);
							break;
						case 7:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_07);
							break;
						case 8:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_08);
							break;
						case 9:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_09);
							break;
						case 10:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_10);
							break;
						case 11:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_11);
							break;
						case 12:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_12);
							break;
						case 13:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_13);
							break;
						case 14:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_01_14);
							break;
					}
					break;
				case 2:
					switch (remote.m_iSearchLetterFocusX) {
						case 1:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_02_01);
							break;
						case 2:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_02_02);
							break;
						case 3:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_02_03);
							break;
						case 4:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_02_04);
							break;
						case 5:
							btnKor0000 = (Button) findViewById(R.id.btn_search_letter_kor_02_05);
							break;
					}
					break;
			}

			if (btnKorMode != null) {
				btnKorMode.setBackgroundResource(R.drawable.search_btn_on);
			}

			if (btnKor0000 != null) {
				if (m_iSearchLetterDisplay == SELECTED) {
					btnKor0000.setBackgroundResource(R.drawable.help_btn_word_selected);
				} else {
					btnKor0000.setBackgroundResource(R.drawable.help_btn_word_on);
				}
			}
		} else if (m_iSearchLetterMode == ENG) {
			Button btnEngMode = null;
			Button btnEng0000 = null;

			switch (remote.m_iSearchLetterFocusY) {
				case 1:
					switch (remote.m_iSearchLetterFocusX) {
						case 0:
							btnEngMode = (Button) findViewById(R.id.btn_search_letter_type_eng);
							break;
						case 1:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_01);
							break;
						case 2:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_02);
							break;
						case 3:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_03);
							break;
						case 4:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_04);
							break;
						case 5:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_05);
							break;
						case 6:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_06);
							break;
						case 7:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_07);
							break;
						case 8:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_08);
							break;
						case 9:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_09);
							break;
						case 10:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_10);
							break;
						case 11:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_11);
							break;
						case 12:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_12);
							break;
						case 13:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_13);
							break;
						case 14:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_01_14);
							break;
					}
					break;
				case 2:
					switch (remote.m_iSearchLetterFocusX) {
						case 1:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_01);
							break;
						case 2:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_02);
							break;
						case 3:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_03);
							break;
						case 4:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_04);
							break;
						case 5:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_05);
							break;
						case 6:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_06);
							break;
						case 7:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_07);
							break;
						case 8:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_08);
							break;
						case 9:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_09);
							break;
						case 10:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_10);
							break;
						case 11:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_11);
							break;
						case 12:
							btnEng0000 = (Button) findViewById(R.id.btn_search_letter_eng_02_12);
							break;
					}
					break;
			}

			if (btnEngMode != null) {
				btnEngMode.setBackgroundResource(R.drawable.search_btn_on);
			}

			if (btnEng0000 != null) {
				if (m_iSearchLetterDisplay == SELECTED) {
					btnEng0000.setBackgroundResource(R.drawable.help_btn_word_selected);
				} else {
					btnEng0000.setBackgroundResource(R.drawable.help_btn_word_on);
				}
			}
		} else if (m_iSearchLetterMode == NUM) {
			Button btnNumMode = null;
			Button btnNum0000 = null;

			switch (remote.m_iSearchLetterFocusX) {
				case 0:
					btnNumMode = (Button) findViewById(R.id.btn_search_letter_type_num);
					break;
				case 1:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_01);
					break;
				case 2:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_02);
					break;
				case 3:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_03);
					break;
				case 4:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_04);
					break;
				case 5:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_05);
					break;
				case 6:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_06);
					break;
				case 7:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_07);
					break;
				case 8:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_08);
					break;
				case 9:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_09);
					break;
				case 10:
					btnNum0000 = (Button) findViewById(R.id.btn_search_letter_num_01_10);
					break;
			}

			if (btnNumMode != null) {
				btnNumMode.setBackgroundResource(R.drawable.search_btn_on);
			}

			if (btnNum0000 != null) {
				if (m_iSearchLetterDisplay == SELECTED) {
					btnNum0000.setBackgroundResource(R.drawable.help_btn_word_selected);
				} else {
					btnNum0000.setBackgroundResource(R.drawable.help_btn_word_on);
				}
			}
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "displaySearchLetter <");
	}

	public void displayListSearch(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":remote.m_iSearchListFocus:" + remote.m_iSearchListFocus);
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "displayListSearch >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewSearchListPage + "/toPage=" + m_iTotalSearchListPage);
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewSearchListPage + "/toPage=" + m_iTotalSearchListPage);

		if (keyID == REMOTE_UP) {
			// 리스트에서 상위로 올라옴
			if (remote.m_iSearchListFocus == 0) {
				exitListSearch();
				return;
			}
		}

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// if (m_iTotalSearchListPage > 1) {
		// if (remote.m_iSearchSubMenuFocus == 1) {
		// SettingListBackground(layoutList, R.drawable.search_bg_self_narrow);
		// } else {
		// SettingListBackground(layoutList, R.drawable.search_bg_letter_narrow);
		// }
		// } else {
		// if (remote.m_iSearchSubMenuFocus == 1) {
		// SettingListBackground(layoutList, R.drawable.search_bg_self_list);
		// } else {
		// SettingListBackground(layoutList, R.drawable.search_bg_letter_list);
		// }
		// }
		setListBackground(getMethodName());

		if (keyID == REMOTE_RIGHT) {
			if (m_iCurrentViewSearchListPage < m_iTotalSearchListPage) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "next");

				remote.m_iSearchListFocus = 1;
				m_iCurrentSearchListPage++;
				m_iCurrentViewSearchListPage++;

				// 1, 21, 31... 페이지면 새로 요청
				if (m_iCurrentViewSearchListPage % 10 == 1) {
					m_iRequestSearchListPage++;
					m_iCurrentSearchListPage = 1;

					switch (remote.m_iSearchSubMenuFocus) {
						case 1:
							if (m_iSearchSelfMode == TITLE) {
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_1);
							} else {
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_2);
							}
							break;
						case 2:
							KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_4);
							break;
					}
				} else {
					moveSearchListPage();
				}
			} else {
				// 마지막 페이지에서 RIGHT키 = 첫번째 페이지로 이동
				if (m_iTotalSearchListPage > 1) {
					remote.m_iSearchListFocus = 1;

					m_iCurrentViewSearchListPage = 1;
					m_iCurrentSearchListPage = 1;
					int temp = m_iRequestSearchListPage;
					m_iRequestSearchListPage = 1;

					if (temp != m_iRequestSearchListPage) {
						// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
						switch (remote.m_iSearchSubMenuFocus) {
							case 1:
								if (m_iSearchSelfMode == TITLE) {
									KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_1);
								} else {
									KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_2);
								}
								break;
							case 2:
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_4);
								break;
						}
					}

					moveSearchListPage();
				}
			}
		} else if (keyID == REMOTE_LEFT) {
			if (m_iCurrentViewSearchListPage > 1) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "prev");

				remote.m_iSearchListFocus = 1;
				m_iCurrentSearchListPage--;
				m_iCurrentViewSearchListPage--;

				// 10, 20, 30... 페이지면 새로 요청
				if (m_iCurrentViewSearchListPage % 10 == 0) {
					m_iRequestSearchListPage--;
					m_iCurrentSearchListPage = 10;

					switch (remote.m_iSearchSubMenuFocus) {
						case 1:
							if (m_iSearchSelfMode == TITLE) {
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_1);
							} else {
								KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_2);
							}
							break;
						case 2:
							KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_4);
							break;
					}
				} else {
					moveSearchListPage();
				}
			} else {
				// 1페이지에서 LEFT키 = 마지막 페이지로 이동
				if (m_iTotalSearchListPage > 1) {
					remote.m_iSearchListFocus = 1;

					m_iCurrentViewSearchListPage = m_iTotalSearchListPage;
					m_iCurrentSearchListPage = m_iTotalSearchListPage % 10;
					if (m_iCurrentSearchListPage == 0) {
						m_iCurrentSearchListPage = 10;
					} else {
						int temp = m_iRequestSearchListPage;
						m_iRequestSearchListPage = (m_iTotalSearchListPage / 10) + 1;

						if (temp != m_iRequestSearchListPage) {
							// 이전 Request했던 페이지와 다른 데이터(새로받음)이면 KP 새로 요청
							switch (remote.m_iSearchSubMenuFocus) {
								case 1:
									if (m_iSearchSelfMode == TITLE) {
										KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_1);
									} else {
										KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_2);
									}
									break;
								case 2:
									KP(REQUEST_SEARCH_LIST, KP_0020, M1_MENU_SEARCH, M2_SEARCH_4);
									break;
							}
						}
					}

					moveSearchListPage();
				}
			}
		}

		resetSearchList();

		LinearLayout layoutList1 = null;
		LinearLayout layoutList2 = null;
		LinearLayout layoutList3 = null;
		LinearLayout layoutList4 = null;
		LinearLayout layoutList5 = null;

		TextView txtNumber1 = null;
		TextView txtNumber2 = null;
		TextView txtNumber3 = null;
		TextView txtNumber4 = null;
		TextView txtNumber5 = null;

		ImageView imgIcon1 = null;
		ImageView imgIcon2 = null;
		ImageView imgIcon3 = null;
		ImageView imgIcon4 = null;
		ImageView imgIcon5 = null;

		// 직접검색
		if (remote.m_iSearchSubMenuFocus == 1) {
			layoutList1 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_1);
			layoutList2 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_2);
			layoutList3 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_3);
			layoutList4 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_4);
			layoutList5 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_5);

			txtNumber1 = (TextView) findViewById(R.id.txt_search_self_number_1);
			txtNumber2 = (TextView) findViewById(R.id.txt_search_self_number_2);
			txtNumber3 = (TextView) findViewById(R.id.txt_search_self_number_3);
			txtNumber4 = (TextView) findViewById(R.id.txt_search_self_number_4);
			txtNumber5 = (TextView) findViewById(R.id.txt_search_self_number_5);

			imgIcon1 = (ImageView) findViewById(R.id.img_search_self_icon_1);
			imgIcon2 = (ImageView) findViewById(R.id.img_search_self_icon_2);
			imgIcon3 = (ImageView) findViewById(R.id.img_search_self_icon_3);
			imgIcon4 = (ImageView) findViewById(R.id.img_search_self_icon_4);
			imgIcon5 = (ImageView) findViewById(R.id.img_search_self_icon_5);
			// 색인검색
		} else {
			layoutList1 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_1);
			layoutList2 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_2);
			layoutList3 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_3);
			layoutList4 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_4);

			txtNumber1 = (TextView) findViewById(R.id.txt_search_letter_number_1);
			txtNumber2 = (TextView) findViewById(R.id.txt_search_letter_number_2);
			txtNumber3 = (TextView) findViewById(R.id.txt_search_letter_number_3);
			txtNumber4 = (TextView) findViewById(R.id.txt_search_letter_number_4);

			imgIcon1 = (ImageView) findViewById(R.id.img_search_letter_icon_1);
			imgIcon2 = (ImageView) findViewById(R.id.img_search_letter_icon_2);
			imgIcon3 = (ImageView) findViewById(R.id.img_search_letter_icon_3);
			imgIcon4 = (ImageView) findViewById(R.id.img_search_letter_icon_4);
		}

		String strColorOn = "#FFFFFF";

		switch (remote.m_iSearchListFocus) {
			case 1:
				layoutList1.setBackgroundResource(R.drawable.list_focus_on);
				txtNumber1.setTextColor(Color.parseColor(strColorOn));
				if (isFavor(remote.m_iSearchListFocus)) {
					imgIcon1.setImageResource(R.drawable.common_bullet_favor_off);
				} else {
					imgIcon1.setImageResource(R.drawable.common_bullet_note_on);
				}
				break;
			case 2:
				layoutList2.setBackgroundResource(R.drawable.list_focus_on);
				txtNumber2.setTextColor(Color.parseColor(strColorOn));
				if (isFavor(remote.m_iSearchListFocus)) {
					imgIcon2.setImageResource(R.drawable.common_bullet_favor_off);
				} else {
					imgIcon2.setImageResource(R.drawable.common_bullet_note_on);
				}
				break;
			case 3:
				layoutList3.setBackgroundResource(R.drawable.list_focus_on);
				txtNumber3.setTextColor(Color.parseColor(strColorOn));
				if (isFavor(remote.m_iSearchListFocus)) {
					imgIcon3.setImageResource(R.drawable.common_bullet_favor_off);
				} else {
					imgIcon3.setImageResource(R.drawable.common_bullet_note_on);
				}
				break;
			case 4:
				layoutList4.setBackgroundResource(R.drawable.list_focus_on);
				txtNumber4.setTextColor(Color.parseColor(strColorOn));
				if (isFavor(remote.m_iSearchListFocus)) {
					imgIcon4.setImageResource(R.drawable.common_bullet_favor_off);
				} else {
					imgIcon4.setImageResource(R.drawable.common_bullet_note_on);
				}
				break;
			case 5:
				layoutList5.setBackgroundResource(R.drawable.list_focus_on);
				txtNumber5.setTextColor(Color.parseColor(strColorOn));
				if (isFavor(remote.m_iSearchListFocus)) {
					imgIcon5.setImageResource(R.drawable.common_bullet_favor_off);
				} else {
					imgIcon5.setImageResource(R.drawable.common_bullet_note_on);
				}
				break;
		}
	}

	public void displayEngageSong() {
		TextView textEngageCount = (TextView) findViewById(R.id.txt_top_engage);
		TextView textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);

		//if (m_iPaneState == PANE_HOME) {
		//	textEngageCount = (TextView) findViewById(R.id.txt_top_engage);
		//	textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		//} else {
		//	textEngageCount = (TextView) findViewById(R.id.txt_top_engage);
		//	textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		//}

		int iSize = song_ids.size();

		if (iSize == 0) {
			if (textEngageCount != null) {
				textEngageCount.setText(getString(R.string.menu_top_engage));
			}
			if (textEngageList != null) {
				textEngageList.setText("");
			}
			return;
		}

		String strCount = "";

		if (iSize < 10) {
			strCount = "0";
		}

		strCount = strCount + Integer.toString(iSize);

		if (textEngageCount != null) {
			textEngageCount.setText("예약곡 " + strCount);
		}

		String strEngageList = song_ids.get(0);
		int iMakeCount = 1;
		while (iMakeCount < song_ids.size()) {
			strEngageList = strEngageList + "  " + song_ids.get(iMakeCount);

			iMakeCount++;

			if (iMakeCount == 5) {
				break;
			}
		}

		if (textEngageList != null) {
			textEngageList.setText(strEngageList);
		}
	}

	public void displayListMyRecord(int keyID) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":remote.m_iSongListFocus:" + remote.m_iSongListFocus);
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "displayListMyRecord >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "keyID=" + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewListenListPage + "/toPage=" + m_iTotalListenListPage);
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + "/curPage=" + m_iCurrentViewListenListPage + "/toPage=" + m_iTotalListenListPage);

		if (keyID == REMOTE_UP) {
			if (remote.m_iSongListFocus == 0 || m_bIsMyRecordReCertifyBtnFocused) {
				m_bIsMyRecordReCertifyBtnFocused = false;
				exitListMyRecord();
				return;
			}
		}

		if (m_iCurrentViewListenListPage == 1 && keyID == REMOTE_LEFT) {
			m_bIsMyRecordReCertifyBtnFocused = true;

			Button btnReCertify = (Button) findViewById(R.id.btn_already_certify_re);
			if (btnReCertify != null) {
				btnReCertify.setBackgroundResource(R.drawable.my_record_btn_on);
			}

			resetMyRecordList();
			return;
		}

		if (m_bIsMyRecordReCertifyBtnFocused && keyID == REMOTE_RIGHT) {
			m_bIsMyRecordReCertifyBtnFocused = false;

			Button btnReCertify = (Button) findViewById(R.id.btn_already_certify_re);
			if (btnReCertify != null) {
				btnReCertify.setBackgroundResource(R.drawable.my_record_btn_off);
			}

			remote.m_iSongListFocus = 1;
			keyID = REMOTE_NONE;
		}

		// if (m_iTotalListenListPage > 1) {
		// // LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.sing_bg_narrow);
		// }
		setListBackground(getMethodName());

		if (keyID == REMOTE_RIGHT) {
			if (m_iCurrentViewListenListPage < m_iTotalListenListPage) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "next");
				remote.m_iSongListFocus = 1;

				m_iCurrentListenListPage++;
				m_iCurrentViewListenListPage++;

				// 1, 21, 31... 페이지면 새로 요청
				if (m_iCurrentViewListenListPage % 10 == 1) {
					m_iRequestPage++;
					m_iCurrentListenListPage = 1;

					KP(REQUEST_MY_RECORD_LIST, KP_3001, M1_MENU_MYLIST, M2_MYLIST_RECORD);
				} else {
					moveMyRecordListPage();
					displayListMyRecord(REMOTE_NONE);
				}
			}
		} else if (keyID == REMOTE_LEFT) {
			if (m_iCurrentViewListenListPage > 1) {
				if (BuildConfig.DEBUG) Log.i(_toString(), "prev");
				remote.m_iSongListFocus = 1;

				m_iCurrentListenListPage--;
				m_iCurrentViewListenListPage--;

				// 10, 20, 30... 페이지면 새로 요청
				if (m_iCurrentViewListenListPage % 10 == 0) {
					m_iRequestPage--;
					m_iCurrentListenListPage = 10;

					KP(REQUEST_MY_RECORD_LIST, KP_3001, M1_MENU_MYLIST, M2_MYLIST_RECORD);
				} else {
					moveMyRecordListPage();
				}
			}
		}

		if (findViewById(R.id.my_record_list) == null) {
			return;
		}

		resetMyRecordList();

		LinearLayout layoutList1 = (LinearLayout) findViewById(R.id.layout_my_record_list_1);
		LinearLayout layoutList2 = (LinearLayout) findViewById(R.id.layout_my_record_list_2);
		LinearLayout layoutList3 = (LinearLayout) findViewById(R.id.layout_my_record_list_3);
		LinearLayout layoutList4 = (LinearLayout) findViewById(R.id.layout_my_record_list_4);
		LinearLayout layoutList5 = (LinearLayout) findViewById(R.id.layout_my_record_list_5);
		LinearLayout layoutList6 = (LinearLayout) findViewById(R.id.layout_my_record_list_6);

		String strColorOn = "#FFFFFF";

		TextView txtRecommand1 = (TextView) findViewById(R.id.txt_my_record_recommand_count_1);
		TextView txtRecommand2 = (TextView) findViewById(R.id.txt_my_record_recommand_count_2);
		TextView txtRecommand3 = (TextView) findViewById(R.id.txt_my_record_recommand_count_3);
		TextView txtRecommand4 = (TextView) findViewById(R.id.txt_my_record_recommand_count_4);
		TextView txtRecommand5 = (TextView) findViewById(R.id.txt_my_record_recommand_count_5);
		TextView txtRecommand6 = (TextView) findViewById(R.id.txt_my_record_recommand_count_6);

		TextView txtTitle1 = (TextView) findViewById(R.id.txt_my_record_title_1);
		TextView txtTitle2 = (TextView) findViewById(R.id.txt_my_record_title_2);
		TextView txtTitle3 = (TextView) findViewById(R.id.txt_my_record_title_3);
		TextView txtTitle4 = (TextView) findViewById(R.id.txt_my_record_title_4);
		TextView txtTitle5 = (TextView) findViewById(R.id.txt_my_record_title_5);
		TextView txtTitle6 = (TextView) findViewById(R.id.txt_my_record_title_6);

		TextView txtDate1 = (TextView) findViewById(R.id.txt_my_record_date_1);
		TextView txtDate2 = (TextView) findViewById(R.id.txt_my_record_date_2);
		TextView txtDate3 = (TextView) findViewById(R.id.txt_my_record_date_3);
		TextView txtDate4 = (TextView) findViewById(R.id.txt_my_record_date_4);
		TextView txtDate5 = (TextView) findViewById(R.id.txt_my_record_date_5);
		TextView txtDate6 = (TextView) findViewById(R.id.txt_my_record_date_6);

		switch (remote.m_iSongListFocus) {
			case 1:
				layoutList1.setBackgroundResource(R.drawable.my_record_list_focus_on);
				txtRecommand1.setTextColor(Color.parseColor(strColorOn));
				txtTitle1.setTextColor(Color.parseColor(strColorOn));
				txtDate1.setTextColor(Color.parseColor(strColorOn));
				break;
			case 2:
				layoutList2.setBackgroundResource(R.drawable.my_record_list_focus_on);
				txtRecommand2.setTextColor(Color.parseColor(strColorOn));
				txtTitle2.setTextColor(Color.parseColor(strColorOn));
				txtDate2.setTextColor(Color.parseColor(strColorOn));
				break;
			case 3:
				layoutList3.setBackgroundResource(R.drawable.my_record_list_focus_on);
				txtRecommand3.setTextColor(Color.parseColor(strColorOn));
				txtTitle3.setTextColor(Color.parseColor(strColorOn));
				txtDate3.setTextColor(Color.parseColor(strColorOn));
				break;
			case 4:
				layoutList4.setBackgroundResource(R.drawable.my_record_list_focus_on);
				txtRecommand4.setTextColor(Color.parseColor(strColorOn));
				txtTitle4.setTextColor(Color.parseColor(strColorOn));
				txtDate4.setTextColor(Color.parseColor(strColorOn));
				break;
			case 5:
				layoutList5.setBackgroundResource(R.drawable.my_record_list_focus_on);
				txtRecommand5.setTextColor(Color.parseColor(strColorOn));
				txtTitle5.setTextColor(Color.parseColor(strColorOn));
				txtDate5.setTextColor(Color.parseColor(strColorOn));
				break;
			case 6:
				layoutList6.setBackgroundResource(R.drawable.my_record_list_focus_on);
				txtRecommand6.setTextColor(Color.parseColor(strColorOn));
				txtTitle6.setTextColor(Color.parseColor(strColorOn));
				txtDate6.setTextColor(Color.parseColor(strColorOn));
				break;
		}
	}

	public void displayMyRecordNone(int keyID) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + REMOTE_STATE.get(keyID) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":m_bIsMyRecordReCertifyBtnFocused:" + m_bIsMyRecordReCertifyBtnFocused);

		if (keyID == REMOTE_UP) {
			exitMyRecordNone();
		}

		if (keyID == REMOTE_LEFT) {
			m_bIsMyRecordReCertifyBtnFocused = true;

			Button btnRe = (Button) findViewById(R.id.btn_already_certify_re);
			btnRe.setBackgroundResource(R.drawable.my_record_btn_on);

			Button btnHelp = (Button) findViewById(R.id.btn_already_certify_help);
			btnHelp.setBackgroundResource(R.drawable.my_record_btn_off);
		} else if (keyID == REMOTE_RIGHT) {
			m_bIsMyRecordReCertifyBtnFocused = false;

			Button btnRe = (Button) findViewById(R.id.btn_already_certify_re);
			btnRe.setBackgroundResource(R.drawable.my_record_btn_off);

			Button btnHelp = (Button) findViewById(R.id.btn_already_certify_help);
			btnHelp.setBackgroundResource(R.drawable.my_record_btn_on);
		}
	}

	protected void resetMainMenu() {
	}

	public void resetSingSubMenu() {
		try {
			Button btnHot = (Button) findViewById(R.id.btn_sing_sub_hot);
			Button btnNew = (Button) findViewById(R.id.btn_sing_sub_new);
			Button btnGenre = (Button) findViewById(R.id.btn_sing_sub_genre);

			if (btnHot != null) {
				//븅신개삽지랄
				//btnHot.setBackgroundColor(Color.TRANSPARENT);
				btnHot.setPressed(false);
				btnHot.setSelected(false);
			}
			if (btnNew != null) {
				//븅신개삽지랄
				//btnNew.setBackgroundColor(Color.TRANSPARENT);
				btnNew.setPressed(false);
				btnNew.setSelected(false);
			}
			if (btnGenre != null) {
				//븅신개삽지랄
				//btnGenre.setBackgroundColor(Color.TRANSPARENT);
				btnGenre.setPressed(false);
				btnGenre.setSelected(false);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetSingSubGenreMenu() {
		try {
			Button btnBallad = (Button) findViewById(R.id.btn_sing_sub_genre_ballad);
			Button btnDance = (Button) findViewById(R.id.btn_sing_sub_genre_dance);
			Button btnTrot = (Button) findViewById(R.id.btn_sing_sub_genre_trot);
			Button btnRock = (Button) findViewById(R.id.btn_sing_sub_genre_rock);
			Button btnPop = (Button) findViewById(R.id.btn_sing_sub_genre_pop);
			Button btnAni = (Button) findViewById(R.id.btn_sing_sub_genre_ani);

			//븅신개삽지랄
			//btnBallad.setBackgroundColor(Color.TRANSPARENT);
			//btnDance.setBackgroundColor(Color.TRANSPARENT);
			//btnTrot.setBackgroundColor(Color.TRANSPARENT);
			//btnRock.setBackgroundColor(Color.TRANSPARENT);
			//btnPop.setBackgroundColor(Color.TRANSPARENT);
			//btnAni.setBackgroundColor(Color.TRANSPARENT);
			//clearFocus
			btnBallad.clearFocus();
			btnDance.clearFocus();
			btnTrot.clearFocus();
			btnRock.clearFocus();
			btnPop.clearFocus();
			btnAni.clearFocus();
			//setPressed
			btnBallad.setPressed(false);
			btnDance.setPressed(false);
			btnTrot.setPressed(false);
			btnRock.setPressed(false);
			btnPop.setPressed(false);
			btnAni.setPressed(false);
			//setSelected
			btnBallad.setSelected(false);
			btnDance.setSelected(false);
			btnTrot.setSelected(false);
			btnRock.setSelected(false);
			btnPop.setSelected(false);
			btnAni.setSelected(false);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	protected void unselectSongList() {
	}

	// protected void setSingListImgIcons() {
	// if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + "m_iSongListFocus:" + remote.m_iSongListFocus);
	//
	// int note = R.drawable.common_bullet_note_off;
	//
	// switch (remote.m_iMenuSingFocus) {
	// case 1:
	// note = R.drawable.common_bullet_note_off;
	// break;
	// case 2:
	// note = R.drawable.common_bullet_new;
	// break;
	// case 3:
	// note = R.drawable.common_bullet_note_off;
	// break;
	// }
	//
	// int resId = note;
	//
	// resId = note;
	// if (isFavor(1)) {
	// resId = R.drawable.common_bullet_favor_off;
	// }
	// icoNote1.setImageResource(resId);
	//
	// resId = note;
	// if (isFavor(2)) {
	// resId = R.drawable.common_bullet_favor_off;
	// }
	// icoNote2.setImageResource(resId);
	//
	// resId = note;
	// if (isFavor(3)) {
	// resId = R.drawable.common_bullet_favor_off;
	// }
	// icoNote3.setImageResource(resId);
	//
	// resId = note;
	// if (isFavor(4)) {
	// resId = R.drawable.common_bullet_favor_off;
	// }
	// icoNote4.setImageResource(resId);
	//
	// resId = note;
	// if (isFavor(5)) {
	// resId = R.drawable.common_bullet_favor_off;
	// }
	// icoNote5.setImageResource(resId);
	//
	// resId = note;
	// if (isFavor(6)) {
	// resId = R.drawable.common_bullet_favor_off;
	// }
	// icoNote6.setImageResource(resId);
	// }

	public void resetListenSubMenu() {
		try {
			Button btnNow = (Button) findViewById(R.id.btn_listen_sub_now);
			Button btnWeek = (Button) findViewById(R.id.btn_listen_sub_week);
			Button btnBest = (Button) findViewById(R.id.btn_listen_sub_best);

			//btnNow.setBackgroundColor(Color.TRANSPARENT);
			//btnWeek.setBackgroundColor(Color.TRANSPARENT);
			//btnBest.setBackgroundColor(Color.TRANSPARENT);
			if (null != btnNow) {
				//븅신개삽지랄
				//btnNow.setBackgroundColor(Color.TRANSPARENT);
				btnNow.setPressed(false);
				btnNow.setSelected(false);
			}
			if (null != btnWeek) {
				//븅신개삽지랄
				//btnWeek.setBackgroundColor(Color.TRANSPARENT);
				btnWeek.setPressed(false);
				btnWeek.setSelected(false);
			}
			if (null != btnBest) {
				//븅신개삽지랄
				//btnBest.setBackgroundColor(Color.TRANSPARENT);
				btnBest.setPressed(false);
				btnBest.setSelected(false);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetMySubMenu() {
		try {
			Button btnRecent = (Button) findViewById(R.id.btn_my_sub_recent);
			Button btnFavor = (Button) findViewById(R.id.btn_my_sub_favor);
			Button btnRecord = (Button) findViewById(R.id.btn_my_sub_record);

			if (btnRecent != null) {
				//븅신개삽지랄
				//btnRecent.setBackgroundColor(Color.TRANSPARENT);
				btnRecent.setPressed(false);
				btnRecent.setSelected(false);
			}
			if (btnFavor != null) {
				//븅신개삽지랄
				//btnFavor.setBackgroundColor(Color.TRANSPARENT);
				btnFavor.setPressed(false);
				btnFavor.setSelected(false);
			}
			if (btnRecord != null) {
				//븅신개삽지랄
				//btnRecord.setBackgroundColor(Color.TRANSPARENT);
				btnRecord.setPressed(false);
				btnRecord.setSelected(false);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetShopSubMenu() {
		try {
			Button btnShopSubTicket = (Button) findViewById(R.id.btn_shop_sub_ticket);
			Button btnShopSubCertify = (Button) findViewById(R.id.btn_shop_sub_certify);

			if (btnShopSubTicket != null) {
				//븅신개삽지랄
				//btnShopSubTicket.setBackgroundColor(Color.TRANSPARENT);
				btnShopSubTicket.setPressed(false);
				btnShopSubTicket.setSelected(false);
			}
			if (btnShopSubCertify != null) {
				//븅신개삽지랄
				//btnShopSubCertify.setBackgroundColor(Color.TRANSPARENT);
				btnShopSubCertify.setPressed(false);
				btnShopSubCertify.setSelected(false);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetCustomerSubMenu() {
		try {
			Button btnCustomerSubEvent = (Button) findViewById(R.id.btn_customer_sub_event);
			Button btnCustomerSubCustomer = (Button) findViewById(R.id.btn_customer_sub_notice);
			Button btnCustomerSubInfo = (Button) findViewById(R.id.btn_customer_sub_info);
			Button btnCustomerSubApp = (Button) findViewById(R.id.btn_customer_sub_app);
			Button btnCustomerSubMic = (Button) findViewById(R.id.btn_customer_sub_mic);

			if (btnCustomerSubEvent != null) {
				//븅신개삽지랄
				//btnCustomerSubEvent.setBackgroundColor(Color.TRANSPARENT);
				//btnCustomerSubEvent.setPressed(false);
				btnCustomerSubEvent.setSelected(false);
			}
			if (btnCustomerSubCustomer != null) {
				//븅신개삽지랄
				//btnCustomerSubCustomer.setBackgroundColor(Color.TRANSPARENT);
				//btnCustomerSubCustomer.setPressed(false);
				btnCustomerSubCustomer.setSelected(false);
			}
			if (btnCustomerSubInfo != null) {
				//븅신개삽지랄
				//btnCustomerSubInfo.setBackgroundColor(Color.TRANSPARENT);
				//btnCustomerSubInfo.setPressed(false);
				btnCustomerSubInfo.setSelected(false);
			}
			if (btnCustomerSubApp != null) {
				//븅신개삽지랄
				//btnCustomerSubApp.setBackgroundColor(Color.TRANSPARENT);
				//btnCustomerSubApp.setPressed(false);
				btnCustomerSubApp.setSelected(false);
			}
			if (btnCustomerSubMic != null) {
				//븅신개삽지랄
				//btnCustomerSubMic.setBackgroundColor(Color.TRANSPARENT);
				//btnCustomerSubMic.setPressed(false);
				btnCustomerSubMic.setSelected(false);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetCustomerList() {
		try {
			LinearLayout layoutList1 = (LinearLayout) findViewById(R.id.layout_customer_list_list_1);
			LinearLayout layoutList2 = (LinearLayout) findViewById(R.id.layout_customer_list_list_2);
			LinearLayout layoutList3 = (LinearLayout) findViewById(R.id.layout_customer_list_list_3);
			LinearLayout layoutList4 = (LinearLayout) findViewById(R.id.layout_customer_list_list_4);
			LinearLayout layoutList5 = (LinearLayout) findViewById(R.id.layout_customer_list_list_5);
			LinearLayout layoutList6 = (LinearLayout) findViewById(R.id.layout_customer_list_list_6);

			if (layoutList1 != null) {
				layoutList1.setBackgroundColor(Color.TRANSPARENT);
			}
			if (layoutList2 != null) {
				layoutList2.setBackgroundColor(Color.TRANSPARENT);
			}
			if (layoutList3 != null) {
				layoutList3.setBackgroundColor(Color.TRANSPARENT);
			}
			if (layoutList4 != null) {
				layoutList4.setBackgroundColor(Color.TRANSPARENT);
			}
			if (layoutList5 != null) {
				layoutList5.setBackgroundColor(Color.TRANSPARENT);
			}
			if (layoutList6 != null) {
				layoutList6.setBackgroundColor(Color.TRANSPARENT);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetSearchSubMenu() {
		try {
			Button btnSearchSubSelf = (Button) findViewById(R.id.btn_search_sub_self);
			Button btnSearchSubLetter = (Button) findViewById(R.id.btn_search_sub_letter);

			btnSearchSubSelf.setBackgroundColor(Color.TRANSPARENT);
			btnSearchSubLetter.setBackgroundColor(Color.TRANSPARENT);
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetSearchLetter() {
		try {
			if (m_iSearchLetterMode == KOR) {
				Button btnKor0100 = (Button) findViewById(R.id.btn_search_letter_type_kor);
				Button btnKor0101 = (Button) findViewById(R.id.btn_search_letter_kor_01_01);
				Button btnKor0102 = (Button) findViewById(R.id.btn_search_letter_kor_01_02);
				Button btnKor0103 = (Button) findViewById(R.id.btn_search_letter_kor_01_03);
				Button btnKor0104 = (Button) findViewById(R.id.btn_search_letter_kor_01_04);
				Button btnKor0105 = (Button) findViewById(R.id.btn_search_letter_kor_01_05);
				Button btnKor0106 = (Button) findViewById(R.id.btn_search_letter_kor_01_06);
				Button btnKor0107 = (Button) findViewById(R.id.btn_search_letter_kor_01_07);
				Button btnKor0108 = (Button) findViewById(R.id.btn_search_letter_kor_01_08);
				Button btnKor0109 = (Button) findViewById(R.id.btn_search_letter_kor_01_09);
				Button btnKor0110 = (Button) findViewById(R.id.btn_search_letter_kor_01_10);
				Button btnKor0111 = (Button) findViewById(R.id.btn_search_letter_kor_01_11);
				Button btnKor0112 = (Button) findViewById(R.id.btn_search_letter_kor_01_12);
				Button btnKor0113 = (Button) findViewById(R.id.btn_search_letter_kor_01_13);
				Button btnKor0114 = (Button) findViewById(R.id.btn_search_letter_kor_01_14);
				Button btnKor0201 = (Button) findViewById(R.id.btn_search_letter_kor_02_01);
				Button btnKor0202 = (Button) findViewById(R.id.btn_search_letter_kor_02_02);
				Button btnKor0203 = (Button) findViewById(R.id.btn_search_letter_kor_02_03);
				Button btnKor0204 = (Button) findViewById(R.id.btn_search_letter_kor_02_04);
				Button btnKor0205 = (Button) findViewById(R.id.btn_search_letter_kor_02_05);

				btnKor0100.setBackgroundResource(R.drawable.search_btn_off);
				btnKor0101.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0102.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0103.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0104.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0105.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0106.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0107.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0108.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0109.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0110.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0111.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0112.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0113.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0114.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0201.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0202.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0203.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0204.setBackgroundResource(R.drawable.help_btn_word_off);
				btnKor0205.setBackgroundResource(R.drawable.help_btn_word_off);
			} else if (m_iSearchLetterMode == ENG) {
				Button btnEng0100 = (Button) findViewById(R.id.btn_search_letter_type_eng);
				Button btnEng0101 = (Button) findViewById(R.id.btn_search_letter_eng_01_01);
				Button btnEng0102 = (Button) findViewById(R.id.btn_search_letter_eng_01_02);
				Button btnEng0103 = (Button) findViewById(R.id.btn_search_letter_eng_01_03);
				Button btnEng0104 = (Button) findViewById(R.id.btn_search_letter_eng_01_04);
				Button btnEng0105 = (Button) findViewById(R.id.btn_search_letter_eng_01_05);
				Button btnEng0106 = (Button) findViewById(R.id.btn_search_letter_eng_01_06);
				Button btnEng0107 = (Button) findViewById(R.id.btn_search_letter_eng_01_07);
				Button btnEng0108 = (Button) findViewById(R.id.btn_search_letter_eng_01_08);
				Button btnEng0109 = (Button) findViewById(R.id.btn_search_letter_eng_01_09);
				Button btnEng0110 = (Button) findViewById(R.id.btn_search_letter_eng_01_10);
				Button btnEng0111 = (Button) findViewById(R.id.btn_search_letter_eng_01_11);
				Button btnEng0112 = (Button) findViewById(R.id.btn_search_letter_eng_01_12);
				Button btnEng0113 = (Button) findViewById(R.id.btn_search_letter_eng_01_13);
				Button btnEng0114 = (Button) findViewById(R.id.btn_search_letter_eng_01_14);
				Button btnEng0201 = (Button) findViewById(R.id.btn_search_letter_eng_02_01);
				Button btnEng0202 = (Button) findViewById(R.id.btn_search_letter_eng_02_02);
				Button btnEng0203 = (Button) findViewById(R.id.btn_search_letter_eng_02_03);
				Button btnEng0204 = (Button) findViewById(R.id.btn_search_letter_eng_02_04);
				Button btnEng0205 = (Button) findViewById(R.id.btn_search_letter_eng_02_05);
				Button btnEng0206 = (Button) findViewById(R.id.btn_search_letter_eng_02_06);
				Button btnEng0207 = (Button) findViewById(R.id.btn_search_letter_eng_02_07);
				Button btnEng0208 = (Button) findViewById(R.id.btn_search_letter_eng_02_08);
				Button btnEng0209 = (Button) findViewById(R.id.btn_search_letter_eng_02_09);
				Button btnEng0210 = (Button) findViewById(R.id.btn_search_letter_eng_02_10);
				Button btnEng0211 = (Button) findViewById(R.id.btn_search_letter_eng_02_11);
				Button btnEng0212 = (Button) findViewById(R.id.btn_search_letter_eng_02_12);

				btnEng0100.setBackgroundResource(R.drawable.search_btn_off);
				btnEng0101.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0102.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0103.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0104.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0105.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0106.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0107.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0108.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0109.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0110.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0111.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0112.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0113.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0114.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0201.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0202.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0203.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0204.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0205.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0206.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0207.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0208.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0209.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0210.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0211.setBackgroundResource(R.drawable.help_btn_word_off);
				btnEng0212.setBackgroundResource(R.drawable.help_btn_word_off);
			} else if (m_iSearchLetterMode == NUM) {
				Button btnNum0100 = (Button) findViewById(R.id.btn_search_letter_type_num);
				Button btnNum0101 = (Button) findViewById(R.id.btn_search_letter_num_01_01);
				Button btnNum0102 = (Button) findViewById(R.id.btn_search_letter_num_01_02);
				Button btnNum0103 = (Button) findViewById(R.id.btn_search_letter_num_01_03);
				Button btnNum0104 = (Button) findViewById(R.id.btn_search_letter_num_01_04);
				Button btnNum0105 = (Button) findViewById(R.id.btn_search_letter_num_01_05);
				Button btnNum0106 = (Button) findViewById(R.id.btn_search_letter_num_01_06);
				Button btnNum0107 = (Button) findViewById(R.id.btn_search_letter_num_01_07);
				Button btnNum0108 = (Button) findViewById(R.id.btn_search_letter_num_01_08);
				Button btnNum0109 = (Button) findViewById(R.id.btn_search_letter_num_01_09);
				Button btnNum0110 = (Button) findViewById(R.id.btn_search_letter_num_01_10);

				btnNum0100.setBackgroundResource(R.drawable.search_btn_off);
				btnNum0101.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0102.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0103.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0104.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0105.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0106.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0107.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0108.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0109.setBackgroundResource(R.drawable.help_btn_word_off);
				btnNum0110.setBackgroundResource(R.drawable.help_btn_word_off);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void resetSearchList() {
		try {
			LinearLayout layoutList1 = null;
			LinearLayout layoutList2 = null;
			LinearLayout layoutList3 = null;
			LinearLayout layoutList4 = null;
			LinearLayout layoutList5 = null;

			if (remote.m_iSearchSubMenuFocus == 1) {
				layoutList1 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_1);
				layoutList2 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_2);
				layoutList3 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_3);
				layoutList4 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_4);
				layoutList5 = (LinearLayout) findViewById(R.id.layout_search_self_list_list_5);
			} else {
				layoutList1 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_1);
				layoutList2 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_2);
				layoutList3 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_3);
				layoutList4 = (LinearLayout) findViewById(R.id.layout_search_letter_list_list_4);
			}

			layoutList1.setBackgroundColor(Color.TRANSPARENT);
			layoutList2.setBackgroundColor(Color.TRANSPARENT);
			layoutList3.setBackgroundColor(Color.TRANSPARENT);
			layoutList4.setBackgroundColor(Color.TRANSPARENT);
			if (remote.m_iSearchSubMenuFocus == 1) {
				layoutList5.setBackgroundColor(Color.TRANSPARENT);
			}

			TextView txtNumber1 = null;
			TextView txtNumber2 = null;
			TextView txtNumber3 = null;
			TextView txtNumber4 = null;
			TextView txtNumber5 = null;

			if (remote.m_iSearchSubMenuFocus == 1) {
				txtNumber1 = (TextView) findViewById(R.id.txt_search_self_number_1);
				txtNumber2 = (TextView) findViewById(R.id.txt_search_self_number_2);
				txtNumber3 = (TextView) findViewById(R.id.txt_search_self_number_3);
				txtNumber4 = (TextView) findViewById(R.id.txt_search_self_number_4);
				txtNumber5 = (TextView) findViewById(R.id.txt_search_self_number_5);
			} else {
				txtNumber1 = (TextView) findViewById(R.id.txt_search_letter_number_1);
				txtNumber2 = (TextView) findViewById(R.id.txt_search_letter_number_2);
				txtNumber3 = (TextView) findViewById(R.id.txt_search_letter_number_3);
				txtNumber4 = (TextView) findViewById(R.id.txt_search_letter_number_4);
			}

			String strColorOff = "#83D5BE";

			txtNumber1.setTextColor(Color.parseColor(strColorOff));
			txtNumber2.setTextColor(Color.parseColor(strColorOff));
			txtNumber3.setTextColor(Color.parseColor(strColorOff));
			txtNumber4.setTextColor(Color.parseColor(strColorOff));
			if (remote.m_iSearchSubMenuFocus == 1) {
				txtNumber5.setTextColor(Color.parseColor(strColorOff));
			}

			ImageView imgIcon1 = null;
			ImageView imgIcon2 = null;
			ImageView imgIcon3 = null;
			ImageView imgIcon4 = null;
			ImageView imgIcon5 = null;

			if (remote.m_iSearchSubMenuFocus == 1) {
				imgIcon1 = (ImageView) findViewById(R.id.img_search_self_icon_1);
				imgIcon2 = (ImageView) findViewById(R.id.img_search_self_icon_2);
				imgIcon3 = (ImageView) findViewById(R.id.img_search_self_icon_3);
				imgIcon4 = (ImageView) findViewById(R.id.img_search_self_icon_4);
				imgIcon5 = (ImageView) findViewById(R.id.img_search_self_icon_5);
			} else {
				imgIcon1 = (ImageView) findViewById(R.id.img_search_letter_icon_1);
				imgIcon2 = (ImageView) findViewById(R.id.img_search_letter_icon_2);
				imgIcon3 = (ImageView) findViewById(R.id.img_search_letter_icon_3);
				imgIcon4 = (ImageView) findViewById(R.id.img_search_letter_icon_4);
			}

			if (isFavor(1)) {
				imgIcon1.setImageResource(R.drawable.common_bullet_favor_off);
			} else {
				imgIcon1.setImageResource(R.drawable.common_bullet_note_off);
			}

			if (isFavor(2)) {
				imgIcon2.setImageResource(R.drawable.common_bullet_favor_off);
			} else {
				imgIcon2.setImageResource(R.drawable.common_bullet_note_off);
			}

			if (isFavor(3)) {
				imgIcon3.setImageResource(R.drawable.common_bullet_favor_off);
			} else {
				imgIcon3.setImageResource(R.drawable.common_bullet_note_off);
			}

			if (isFavor(4)) {
				imgIcon4.setImageResource(R.drawable.common_bullet_favor_off);
			} else {
				imgIcon4.setImageResource(R.drawable.common_bullet_note_off);
			}

			if (remote.m_iSearchSubMenuFocus == 1) {
				if (isFavor(5)) {
					imgIcon5.setImageResource(R.drawable.common_bullet_favor_off);
				} else {
					imgIcon5.setImageResource(R.drawable.common_bullet_note_off);
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	/**
	 * @see Main2XXXX#resetPPXPass()
	 */
	@Deprecated
	public void resetPPXPass() {
	}

	/**
	 * @see Main2XXXX#resetPPMNotice()
	 */
	@Deprecated
	public void resetPPMNotice() {
	}

	/**
	 * @see Main2X#resetCertifyHP()
	 */
	@Deprecated
	public void resetCertifyHP() {
	}

	/**
	 * @see Main2X#resetCertify()
	 */
	@Deprecated
	public void resetCertify() {
	}

	public void resetMyRecordList() {
		try {
			LinearLayout layoutList1 = (LinearLayout) findViewById(R.id.layout_my_record_list_1);
			LinearLayout layoutList2 = (LinearLayout) findViewById(R.id.layout_my_record_list_2);
			LinearLayout layoutList3 = (LinearLayout) findViewById(R.id.layout_my_record_list_3);
			LinearLayout layoutList4 = (LinearLayout) findViewById(R.id.layout_my_record_list_4);
			LinearLayout layoutList5 = (LinearLayout) findViewById(R.id.layout_my_record_list_5);
			LinearLayout layoutList6 = (LinearLayout) findViewById(R.id.layout_my_record_list_6);

			layoutList1.setBackgroundColor(Color.TRANSPARENT);
			layoutList2.setBackgroundColor(Color.TRANSPARENT);
			layoutList3.setBackgroundColor(Color.TRANSPARENT);
			layoutList4.setBackgroundColor(Color.TRANSPARENT);
			layoutList5.setBackgroundColor(Color.TRANSPARENT);
			layoutList6.setBackgroundColor(Color.TRANSPARENT);

			String strColorOff = "#83D5BE";

			TextView txtRecommand1 = (TextView) findViewById(R.id.txt_my_record_recommand_count_1);
			TextView txtRecommand2 = (TextView) findViewById(R.id.txt_my_record_recommand_count_2);
			TextView txtRecommand3 = (TextView) findViewById(R.id.txt_my_record_recommand_count_3);
			TextView txtRecommand4 = (TextView) findViewById(R.id.txt_my_record_recommand_count_4);
			TextView txtRecommand5 = (TextView) findViewById(R.id.txt_my_record_recommand_count_5);
			TextView txtRecommand6 = (TextView) findViewById(R.id.txt_my_record_recommand_count_6);

			TextView txtTitle1 = (TextView) findViewById(R.id.txt_my_record_title_1);
			TextView txtTitle2 = (TextView) findViewById(R.id.txt_my_record_title_2);
			TextView txtTitle3 = (TextView) findViewById(R.id.txt_my_record_title_3);
			TextView txtTitle4 = (TextView) findViewById(R.id.txt_my_record_title_4);
			TextView txtTitle5 = (TextView) findViewById(R.id.txt_my_record_title_5);
			TextView txtTitle6 = (TextView) findViewById(R.id.txt_my_record_title_6);

			TextView txtDate1 = (TextView) findViewById(R.id.txt_my_record_date_1);
			TextView txtDate2 = (TextView) findViewById(R.id.txt_my_record_date_2);
			TextView txtDate3 = (TextView) findViewById(R.id.txt_my_record_date_3);
			TextView txtDate4 = (TextView) findViewById(R.id.txt_my_record_date_4);
			TextView txtDate5 = (TextView) findViewById(R.id.txt_my_record_date_5);
			TextView txtDate6 = (TextView) findViewById(R.id.txt_my_record_date_6);

			txtRecommand1.setTextColor(Color.parseColor(strColorOff));
			txtRecommand2.setTextColor(Color.parseColor(strColorOff));
			txtRecommand3.setTextColor(Color.parseColor(strColorOff));
			txtRecommand4.setTextColor(Color.parseColor(strColorOff));
			txtRecommand5.setTextColor(Color.parseColor(strColorOff));
			txtRecommand6.setTextColor(Color.parseColor(strColorOff));

			txtTitle1.setTextColor(Color.parseColor(strColorOff));
			txtTitle2.setTextColor(Color.parseColor(strColorOff));
			txtTitle3.setTextColor(Color.parseColor(strColorOff));
			txtTitle4.setTextColor(Color.parseColor(strColorOff));
			txtTitle5.setTextColor(Color.parseColor(strColorOff));
			txtTitle6.setTextColor(Color.parseColor(strColorOff));

			txtDate1.setTextColor(Color.parseColor(strColorOff));
			txtDate2.setTextColor(Color.parseColor(strColorOff));
			txtDate3.setTextColor(Color.parseColor(strColorOff));
			txtDate4.setTextColor(Color.parseColor(strColorOff));
			txtDate5.setTextColor(Color.parseColor(strColorOff));
			txtDate6.setTextColor(Color.parseColor(strColorOff));

			if (!m_bIsMyRecordReCertifyBtnFocused) {
				Button btnReCertify = (Button) findViewById(R.id.btn_already_certify_re);
				btnReCertify.setBackgroundResource(R.drawable.my_record_btn_off);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void exitMenuSing() {
		resetSingSubMenu();

		remote.m_iState = STATE_MAIN_MENU;

		btn_main_sing.setBackgroundResource(R.drawable.sub_icon_01_on);
	}

	public void exitMenuSingGenre() {
		resetSingSubGenreMenu();

		remote.m_iState = STATE_SING_MENU;

		displayMenuSing(REMOTE_NONE);
	}

	public void exitListSong() {
		unselectSongList();

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);

		remote.m_iSongListFocus = 1;
		//인기장르
		if (remote.m_iMenuSingFocus == 3) {
			remote.m_iSongListFocus = 2;
		}

		// 인기장르 목록 → 인기장르 서브메뉴
		if (remote.m_iMenuMainFocus == 1 && remote.m_iMenuSingFocus == 3) {
			// SettingListBackground(layoutList, R.drawable.sing_bg_genre);
			remote.m_iState = STATE_SING_GENRE;
			displayMenuSingGenre(REMOTE_NONE);
			// 마이노래방 목록 → 마이노래방 서브메뉴
		} else if (remote.m_iMenuMainFocus == 3) {
			// SettingListBackground(layoutList, R.drawable.sing_bg);
			remote.m_iState = STATE_MY_MENU;
			displayMenuMy(REMOTE_NONE);
			// etc
		} else {
			// SettingListBackground(layoutList, R.drawable.sing_bg);
			remote.m_iState = STATE_SING_MENU;
			displayMenuSing(REMOTE_NONE);
		}

		setListBackground(getMethodName());
	}

	// private void removeSongListDetail() {
	// if (m_layoutSongListDetail != null) {
	// ((ViewManager) m_layoutSongListDetail.getParent()).removeView(m_layoutSongListDetail);
	// m_layoutSongListDetail = null;
	// }
	// }

	protected void hideDetailSong() {
		if (m_layoutSongListDetail != null) {
			m_layoutSongListDetail.setVisibility(View.INVISIBLE);
		}
	}

	protected void showDetailSong() {
		if (m_layoutSongListDetail != null) {
			m_layoutSongListDetail.setVisibility(View.VISIBLE);
		}
	}

	public void exitDetailSong() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		hideDetailSong();

		switch (remote.m_iMenuMainFocus) {
			// 노래부르기
			case 1:
				remote.m_iState = STATE_SONG_LIST;
				displayListSing(REMOTE_NONE);
				break;
			// 마이노래방
			case 3:
				remote.m_iState = STATE_MY_LIST;
				displayListMy(REMOTE_NONE);
				break;
		}
	}

	public void exitMenuListen() {
		resetListenSubMenu();

		remote.m_iState = STATE_MAIN_MENU;

		btn_main_listen.setBackgroundResource(R.drawable.sub_icon_02_on);
	}

	public void exitListListen() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.search_bg_none);
		setListBackground(getMethodName());

		if (m_layoutListenListFocus != null) {
			// ((ViewManager) m_layoutListenListFocus.getParent()).removeView(m_layoutListenListFocus);
			removeView(m_layoutListenListFocus);
			m_layoutListenListFocus = null;
		}

		// remote.m_iListenListFocusX = 1;
		// remote.m_iListenListFocusY = 1;

		if (remote.m_iMenuMainFocus == 2) {
			remote.m_iState = STATE_LISTEN_MENU;
			displayMenuListen(REMOTE_NONE);
		} else {
			remote.m_iState = STATE_MY_MENU;
			displayMenuMy(REMOTE_NONE);
		}
	}


	protected void exitListeningState() {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":" + remote.m_iMenuMainFocus);
		if (m_iPaneState == PANE_HOME) {
			remote.m_iState = STATE_HOME_MENU;
		} else {
			if (remote.m_iMenuMainFocus != 3) {
				remote.m_iState = STATE_LISTEN_LIST;
			} else {
				remote.m_iState = STATE_MY_RECORD_LIST;
			}
		}

	}

	public void exitListening() {
		stopListen();

		// 하단 첫번째 가이드 텍스트 그룹을 감춤
		// hideBottomGuide01();

		removeListening();

		exitListeningState();

		ShowMenu(getMethodName());
	}

	public void exitListeningOther() {
		remote.m_iState = STATE_LISTENING;

		removeListeningOther();

		// 하단 첫번째 가이드를 '다른 녹음곡 보기' 로 변경
		showBottomGuideListenOther(getMethodName());
	}

	public void exitMenuMy() {
		remote.m_iState = STATE_MAIN_MENU;

		resetMySubMenu();

		btn_main_my.setBackgroundResource(R.drawable.sub_icon_03_on);
	}

	public void exitListMy() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		unselectSongList();

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.sing_bg);
		setListBackground(getMethodName());

		remote.m_iState = STATE_MY_MENU;
		remote.m_iSongListFocus = 1;

		displayMenuMy(REMOTE_NONE);
	}

	public void exitMenuShop() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		resetShopSubMenu();

		remote.m_iState = STATE_MAIN_MENU;

		btn_main_shop.setBackgroundResource(R.drawable.sub_icon_04_on);
	}

	public void exitTicket() {
		//if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		//remote.m_iState = STATE_SHOP_MENU;
		//
		//resetShopSubMenu();
		//
		//try {
		//	Button btnCoupon = (Button) findViewById(R.id.btn_shop_tab_3);
		//
		//	if (remote.key() == TICKET_TYPE.TICKET_TYPE_COUPON && m_bIsFocusedOnTicket) {
		//		m_bIsFocusedOnTicket = false;
		//		remote.m_iShopTicketFocusY = 1;
		//		m_iCouponFocus = 1;
		//
		//		EditText editCoupon = (EditText) findViewById(R.id.edit_coupon);
		//		Button btnCouponRegist = (Button) findViewById(R.id.btn_coupon_regist);
		//
		//		editCoupon.setBackgroundResource(R.drawable.coupon_input_off);
		//		btnCouponRegist.setBackgroundResource(R.drawable.coupon_btn_regist_off);
		//		btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_on);
		//
		//		editCoupon.clearFocus();
		//		editCoupon.setSelected(false);
		//		editCoupon.setFocusable(false);
		//		editCoupon.setFocusableInTouchMode(false);
		//		return;
		//	}
		//
		//	Button btnPurchase = (Button) findViewById(R.id.btn_shop_purchase);
		//	if (btnPurchase != null) {
		//		btnPurchase.setBackgroundResource(R.drawable.tab_focus_long_off);
		//	}
		//
		//	ViewGroup layoutTicket = (ViewGroup) findViewById(R.id.layout_shop_ticket);
		//
		//	switch (remote.key()) {
		//		case TICKET_TYPE_NONE:
		//			Log.wtf(_toString(), getMethodName() + remote.key());
		//		case TICKET_TYPE_YEAR:
		//			break;
		//		case TICKET_TYPE_MONTH:
		//			layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_month_off);
		//			break;
		//		case TICKET_TYPE_DAY:
		//			layoutTicket.setBackgroundResource(R.drawable.shop_ticket_bg_day_off);
		//			break;
		//		case TICKET_TYPE_COUPON:
		//			if (m_bCouponUser) {
		//				layoutTicket.setBackgroundResource(R.drawable.shop_coupon_bg_registed_off);
		//			} else {
		//				layoutTicket.setBackgroundResource(R.drawable.shop_coupon_bg_regist_off);
		//			}
		//			break;
		//	}
		//
		//	Button btnDay = (Button) findViewById(R.id.btn_shop_tab_1);
		//	Button btnMonth = (Button) findViewById(R.id.btn_shop_tab_2);
		//
		//	switch (remote.key()) {
		//		case TICKET_TYPE_NONE:
		//			Log.wtf(_toString(), getMethodName() + remote.key());
		//		case TICKET_TYPE_YEAR:
		//			break;
		//		case TICKET_TYPE_MONTH:
		//			btnDay.setBackgroundResource(R.drawable.shop_ticket_tab_selected);
		//			break;
		//		case TICKET_TYPE_DAY:
		//			btnMonth.setBackgroundResource(R.drawable.shop_ticket_tab_selected);
		//			break;
		//		case TICKET_TYPE_COUPON:
		//			btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_selected);
		//			break;
		//	}
		//
		//	Button btnKPInfo = (Button) findViewById(R.id.btn_shop_kp_info);
		//	btnKPInfo.setBackgroundResource(R.drawable.shop_focus_02_off);
		//
		//	Button btnShopSubTicket = (Button) findViewById(R.id.btn_shop_sub_ticket);
		//	if (btnShopSubTicket != null) {
		//		//븅신개삽지랄
		//		//btnShopSubTicket.setBackgroundResource(R.drawable.tab_focus_on);
		//		btnShopSubTicket.setSelected(true);
		//	}
		//} catch (Exception e) {
		//	if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		//}
	}

	public void exitPPV() {
		removeView(message_ticket);
		message_ticket = null;

		if (m_bIsGoToCertifyMessage) {
			// bgkimt 이용권 구매했으니까 인증할래? 띄운다
			m_bIsGoToCertifyMessage = false;

			//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_go_certify, null);
			//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			//addContentView(message_ticket, param);
			message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_go_certify);

			remote.m_iState = STATE_MESSAGE_GO_CERTIFY;
			m_iTicketMessageFocusX = 1;
		} else {
			remote.m_iState = STATE_SHOP_TICKET;
		}

		m_bIsFocusedOnPassNumber = false;
		m_strInputPass[0] = "";
		m_strInputPass[1] = "";
		m_strInputPass[2] = "";
		m_strInputPass[3] = "";
	}

	public void exitCertify() {
		if (/*!m_bIsCertifyedUser*/TextUtil.isEmpty(KP.auth_date)) {
			ImageView imgCertify = (ImageView) findViewById(R.id.img_shop_certify);
			if (imgCertify != null) {
				imgCertify.setImageResource(R.drawable.shop_certify_off);
			}
		} else {
			LinearLayout layoutCertify = (LinearLayout) findViewById(R.id.layout_already_certify);
			if (layoutCertify != null) {
				layoutCertify.setBackgroundResource(R.drawable.shop_certify_already_off);
			}
		}

		remote.m_iState = STATE_SHOP_MENU;

		resetShopSubMenu();

		Button btnShopSubCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
		if (btnShopSubCertify != null) {
			//븅신개삽지랄
			//btnShopSubCertify.setBackgroundResource(R.drawable.tab_focus_on);
			btnShopSubCertify.setSelected(true);
		}
	}

	/**
	 * 팝업창날리기
	 *
	 * @see Main2X#removeView(android.view.View)
	 */
	protected void removeView(View popup) {
	}

	/**
	 * @see Main2X#exitCertifyHP()
	 */
	@Deprecated
	public void exitCertifyHP() {
	}

	public void exitCertifyNumber() {
		remote.m_iState = STATE_SHOP_CERTIFY;
		remote.m_iCertifyFocusX = 1;
		remote.m_iCertifyFocusY = 1;

		if (m_bIsCertifyTimerActivated) {
			m_cdTimer.cancel();
		}

		// ((ViewManager) message_hp_certify.getParent()).removeView(message_hp_certify);
		removeView(message_hp_certify);
		message_hp_certify = null;
	}

	public void exitMenuCustomer() {
		resetCustomerSubMenu();

		remote.m_iState = STATE_MAIN_MENU;
		// remote.m_iMenuCustomerFocus = 1;

		btn_main_customer.setBackgroundResource(R.drawable.sub_icon_05_on);
	}

	public void exitListCustomer() {
		resetCustomerList();

		setListBackground(getMethodName());

		remote.m_iState = STATE_CUSTOMER_MENU;
		remote.m_iCustomerListFocus = 1;

		displayMenuCustomer(REMOTE_NONE);
	}

	/**
	 * 븅신개지랄떨고자빠젔네
	 */
	public void exitDetailCustomer() {
		//if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + ":m_iEnterCustomerMenu:" + m_iEnterCustomerMenu + ":remote.m_iMenuCustomerFocus:" + remote.m_iMenuCustomerFocus);
		//
		//// 메인화면에서 타고 들어온거면 상세화면에서 빠져나갈 때 서브메뉴로 올려줌
		//if (m_iEnterCustomerMenu != CUSTOMER_ENTER_KEY) {
		//	//remote.m_iMenuHomeFocus = 5;
		//
		//	// LinearLayout layoutCustomerBack = (LinearLayout) findViewById(R.id.layout_sing);
		//	// SettingListBackground(layoutCustomerBack, R.drawable.sing_bg);
		//	setListBackground(getMethodName());
		//
		//	if (remote.m_iMenuCustomerFocus != 1) {
		//		addViewCustomerList();
		//	} else {
		//		addViewCustomerListEvent();
		//	}
		//
		//	remote.m_iState = STATE_CUSTOMER_MENU;
		//
		//	clickMenuCustomer();
		//} else {
		//	// if (m_iTotalCustomerListPage > 1) {
		//	// // LinearLayout layoutCustomerBack = (LinearLayout) findViewById(R.id.layout_sing);
		//	// SettingListBackground(layoutCustomerBack, R.drawable.sing_bg_narrow);
		//	// }
		//	setListBackground(getMethodName());
		//
		//	if (remote.m_iMenuCustomerFocus != 1) {
		//		addViewCustomerList();
		//		remote.m_iState = STATE_CUSTOMER_LIST;
		//	} else {
		//		addViewCustomerListEvent();
		//		remote.m_iState = STATE_CUSTOMER_LIST_EVENT;
		//	}
		//
		//	moveCustomerListPage();
		//	displayListCustomer(REMOTE_NONE);
		//}
	}

	/**
	 * <pre>
	 * "검색->이전"메뉴오류
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#exitMenuSearch()
	 */
	public void exitMenuSearch() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ST]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		resetMenus(getMethodName());
		goSingList(true);
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[ED]" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	public void exitSearchSelf() {
		Button btnType = (Button) findViewById(R.id.btn_search_self_type);
		EditText editWord = (EditText) findViewById(R.id.edit_search_self_word);

		btnType.setBackgroundResource(R.drawable.search_btn_off);
		editWord.setBackgroundResource(R.drawable.search_input_off);
		if (m_strSTBVender != P_APPNAME_SKT_BOX) {
			Button btnOk = (Button) findViewById(R.id.btn_search_self_ok);
			btnOk.setBackgroundResource(R.drawable.search_ok_btn_off);
		}

		remote.m_iState = STATE_SEARCH_MENU;
		remote.m_iSearchSelfFocus = 1;
		m_iSearchSelfMode = TITLE;
		m_bIsFocusedOnBook = false;

		displayMenuSearch(REMOTE_NONE);
	}

	public void exitSearchLetter() {
		resetSearchLetter();

		remote.m_iState = STATE_SEARCH_MENU;
		remote.m_iSearchLetterFocusX = 0;
		remote.m_iSearchLetterFocusY = 1;

		displayMenuSearch(REMOTE_NONE);
	}

	public void exitListSearch() {
		resetSearchList();

		m_iTotalSearchListPage = 0;
		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.search_bg_none);
		setListBackground(getMethodName());

		remote.m_iSearchListFocus = 1;

		if (remote.m_iSearchSubMenuFocus == 1) {
			remote.m_iSearchSelfFocus = 1;
			remote.m_iState = STATE_SEARCH_SELF;
			displaySearchSelf(REMOTE_NONE);
		} else {
			remote.m_iSearchLetterFocusX = 0;
			remote.m_iSearchLetterFocusY = 1;

			switch (m_iSearchLetterMode) {
				case KOR:
					remote.m_iState = STATE_SEARCH_LETTER_KOR;
					break;
				case ENG:
					remote.m_iState = STATE_SEARCH_LETTER_ENG;
					break;
				case NUM:
					remote.m_iState = STATE_SEARCH_LETTER_NUM;
					break;
			}

			displaySearchLetter(REMOTE_NONE);
		}
	}

	public void exitDetailSearch() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		hideDetailSong();

		remote.m_iState = STATE_SEARCH_LIST;
		displayListSearch(REMOTE_NONE);
	}

	public void exitListMyRecord() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		m_bIsMyRecordReCertifyBtnFocused = false;

		resetMyRecordList();

		remote.m_iSongListFocus = 1;

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.sing_bg);
		setListBackground(getMethodName());

		remote.m_iState = STATE_MY_MENU;
		displayMenuMy(REMOTE_NONE);
	}

	public void exitMyRecordNone() {
		remote.m_iState = STATE_MY_MENU;
		displayMenuMy(REMOTE_NONE);

		Button btnRe = (Button) findViewById(R.id.btn_already_certify_re);
		if (btnRe != null) {
			btnRe.setBackgroundResource(R.drawable.my_record_btn_off);
		}

		Button btnHelp = (Button) findViewById(R.id.btn_already_certify_help);
		if (btnHelp != null) {
			btnHelp.setBackgroundResource(R.drawable.my_record_btn_off);
		}
	}

	public void inputPPXPass(int keycode) {
		String strInputPass = "";
		boolean bErase = false;

		switch (keycode) {
			case KeyEvent.KEYCODE_0:
				strInputPass = "0";
				break;
			case KeyEvent.KEYCODE_1:
				strInputPass = "1";
				break;
			case KeyEvent.KEYCODE_2:
				strInputPass = "2";
				break;
			case KeyEvent.KEYCODE_3:
				strInputPass = "3";
				break;
			case KeyEvent.KEYCODE_4:
				strInputPass = "4";
				break;
			case KeyEvent.KEYCODE_5:
				strInputPass = "5";
				break;
			case KeyEvent.KEYCODE_6:
				strInputPass = "6";
				break;
			case KeyEvent.KEYCODE_7:
				strInputPass = "7";
				break;
			case KeyEvent.KEYCODE_8:
				strInputPass = "8";
				break;
			case KeyEvent.KEYCODE_9:
				strInputPass = "9";
				break;
			case 18:
				bErase = true;
				break;
		}

		if (m_iTicketMessageFocusY == 1) {
			if (m_iTicketMessageFocusX == 1) {
				if (!strInputPass.equals("")) m_strInputPass[0] = strInputPass;

				ImageView imgPass01 = (ImageView) findViewById(R.id.img_ticket_pass_01);
				if (bErase) {
					imgPass01.setImageResource(R.drawable.blank);
					m_strInputPass[0] = "";
				} else {
					imgPass01.setImageResource(R.drawable.pop_pw_off);
					m_iTicketMessageFocusX = 2;
				}
			} else if (m_iTicketMessageFocusX == 2) {
				if (!strInputPass.equals("")) m_strInputPass[1] = strInputPass;

				ImageView imgPass02 = (ImageView) findViewById(R.id.img_ticket_pass_02);
				if (bErase) {
					imgPass02.setImageResource(R.drawable.blank);
					m_strInputPass[1] = "";
				} else {
					imgPass02.setImageResource(R.drawable.pop_pw_off);
					m_iTicketMessageFocusX = 3;
				}
			} else if (m_iTicketMessageFocusX == 3) {
				if (!strInputPass.equals("")) m_strInputPass[2] = strInputPass;

				ImageView imgPass03 = (ImageView) findViewById(R.id.img_ticket_pass_03);
				if (bErase) {
					imgPass03.setImageResource(R.drawable.blank);
					m_strInputPass[2] = "";
				} else {
					imgPass03.setImageResource(R.drawable.pop_pw_off);
					m_iTicketMessageFocusX = 4;
				}
			} else if (m_iTicketMessageFocusX == 4) {
				if (!strInputPass.equals("")) m_strInputPass[3] = strInputPass;

				ImageView imgPass04 = (ImageView) findViewById(R.id.img_ticket_pass_04);
				if (bErase) {
					imgPass04.setImageResource(R.drawable.blank);
					m_strInputPass[3] = "";
				} else {
					imgPass04.setImageResource(R.drawable.pop_pw_off);
					m_iTicketMessageFocusX = 1;
					m_iTicketMessageFocusY = 2;
				}

				// m_bIsFocusedOnPassNumber = false;
			}
		}

		displayPPXPass(REMOTE_NONE);
	}

	protected void initSongListIndex() {
		m_iCurrentViewSongListPage = 1;
		m_iRequestPage = 1;
	}

	protected void initMyListIndex() {
		m_iCurrentViewSongListPage = 1;
		m_iRequestPage = 1;
	}

	protected void initListenListIndex() {
		m_iCurrentViewListenListPage = 1;
		m_iRequestPage = 1;
	}

	protected void initCustomerListIndex() {
		m_iCurrentViewCustomerListPage = 1;
		m_iCurrentCustomerListPage = 1;
		m_iRequestPage = 1;
	}

	public void goHome() {
	}

	public void goSearch() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName());

		if (m_bIsHiddenMenu) {
			ShowMenu(getMethodName());
		}

		//if (isGenre())
		{
			HideGenre();
		}

		// 녹음곡 재생 중이면 녹음곡 중지 처리
		stopListen();

		// hideBottomGuide01();

		// if (m_layoutListenListFocus != null) {
		// ((ViewManager) m_layoutListenListFocus.getParent()).removeView(m_layoutListenListFocus);
		// m_layoutListenListFocus = null;
		// }
		removeView(m_layoutListenListFocus);
		m_layoutListenListFocus = null;

		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		setContentViewKaraoke(m_layoutMain);

		HideGenre();

		// 상단 메인메뉴 영역의 포커스를 잃어준다
		resetMainMenu();

		LinearLayout layoutSubMenu = (LinearLayout) inflate(R.layout.menu_search, null);
		layoutSubMenu.setLayoutParams(lp);
		addView(layout_menu_sub, layoutSubMenu);

		LinearLayout layoutContent = null;
		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			layoutContent = (LinearLayout) inflate(R.layout.search_list_self_box, null);
		} else {
			layoutContent = (LinearLayout) inflate(R.layout.search_list_self, null);
		}
		layoutContent.setLayoutParams(lp);
		addView(layout_content, layoutContent);

		// 븅신...상단 메인메뉴 영역의 포커스를 잃어준다...메
		//Button btnSubMainSing = (Button) findViewById(R.id.btn_main_sing);
		//LinearLayout.LayoutParams lpOff = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
		//lpMainOff.setMargins(0, PixelFromDP(12), 0, PixelFromDP(12));
		//lpMainOff.weight = 3;
		//btnSubMainSing.setLayoutParams(lpMainOff);

		Button btnSelf = (Button) findViewById(R.id.btn_search_sub_self);
		btnSelf.setBackgroundResource(R.drawable.tab_focus_on);

		remote.m_iState = STATE_SEARCH_MENU;
		remote.m_iSearchSubMenuFocus = 1;
		remote.m_iSearchSelfFocus = 1;
		remote.m_iSearchLetterFocusX = 0;
		remote.m_iSearchLetterFocusY = 1;
		remote.m_iSearchListFocus = 1;
		m_iSearchSelfMode = TITLE;
		m_iSearchLetterMode = KOR;
		m_bIsFocusedOnBook = false;

		// LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_sing);
		// SettingListBackground(layoutList, R.drawable.search_bg_none);
		setListBackground(getMethodName());
	}

	public void setHOME() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + isPassUser());

		video.startBlankVideo(video_url_back, PLAY_STOP);

		TextView txtSingTitle = (TextView) findViewById(R.id.txt_home_sing_title);
		TextView txtSingArtist = (TextView) findViewById(R.id.txt_home_sing_artist);
		txtSingTitle.setText(KP.song_title);
		txtSingArtist.setText(KP.song_artist);

		TextView txtListenTitle = (TextView) findViewById(R.id.txt_home_listen_title);
		TextView txtListenArtist = (TextView) findViewById(R.id.txt_home_listen_artist);
		txtListenTitle.setText(KP.listen_title);
		txtListenArtist.setText("by " + KP.listen_nickname);

		// 듣기 프로필 이미지
		m_imgProfileHome = (ImageView) findViewById(R.id.img_main_listen_profile);
		if (util_profileHome != null) {
			if (util_profileHome.m_bitMap != null) {
				util_profileHome.m_bitMap.recycle();
			}
		}
		util_profileHome = new _Util(handlerKP);
		util_profileHome.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_HOME);
		util_profileHome.setImageUrl(KP.url_img);
		util_profileHome.start();
		 //putURLImage((ImageView) findViewById(R.id.img_main_listen_profile), KP.url_img, false, 0);

		// 이벤트 배너
		//util = new _Util(handlerKP);
		//util.setUtilType(REQUEST_UTIL_MAIN_EVENT_IMAGE);
		//util.setImageUrl(KP.event_url_img);
		//util.start();
		putURLImage((ImageView) findViewById(R.id.btn_home_event_05), KP.event_url_img, false);

		// 퀵버튼2
		util_mainQuickBtnOff02 = new _Util(handlerKP);
		util_mainQuickBtnOff02.setUtilType(REQUEST_UTIL_MAIN_QUICK_IMAGE_02_OFF);
		util_mainQuickBtnOff02.setImageUrl(KP.m_strMainQuickBtnOffUrl02);
		util_mainQuickBtnOff02.start();

		util_mainQuickBtnOn02 = new _Util(handlerKP);
		util_mainQuickBtnOn02.setUtilType(REQUEST_UTIL_MAIN_QUICK_IMAGE_02_ON);
		util_mainQuickBtnOn02.setImageUrl(KP.m_strMainQuickBtnOnUrl02);
		util_mainQuickBtnOn02.start();

		Button btnCustomer = (Button) findViewById(R.id.btn_main_notice);
		btnCustomer.setText(KP.notice_title);

		// KY로고
		util_kyLogo = new _Util(handlerKP);
		util_kyLogo.setUtilType(REQUEST_UTIL_KY_LOGO);
		util_kyLogo.setImageUrl(KP.url_kylogo);
		util_kyLogo.start();
		//util_kyLogo.putURLImage((ImageView) findViewById(R.id.img_ky_logo), KP.url_kylogo, false, R.drawable.ky_logo);

		// 마이크 이미지
		if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			util_MIC = new _Util(handlerKP);
			util_MIC.setUtilType(REQUEST_UTIL_MIC);
			util_MIC.setImageUrl(MIC_URL_BOX);
			util_MIC.start();
		} else {
			util_MIC = new _Util(handlerKP);
			util_MIC.setUtilType(REQUEST_UTIL_MIC);
			util_MIC.setImageUrl(MIC_URL_STB);
			util_MIC.start();
		}
	}

	protected String setBottomTicketText() {
		return null;
	}

	/**
	 * 줄바꿈제거
	 */
	protected void setBottomProductText(String text) {
		/**
		 * Newline
		 */
		text = text.replace("\n", " ");
		/**
		 * Carriage return
		 */
		text = text.replace("\r", "");
		/**
		 * 디버그표시
		 */
		if (BuildConfig.DEBUG) {
			text = "[DEBUG]" + text;
		}
		TextView tv = (TextView) findViewById(R.id.txt_bottom_product);
		tv.setSingleLine(true);
		tv.setText(text);
		setTextViewMarquee(tv, true);
	}

	///**
	// * 븅신개지랄
	// */
	//@Deprecated
	//public void addViewKaraoke(LinearLayout layout, View view) {
	//	//layout.removeAllViews();
	//	//layout.addView(view);
	//	//
	//	//ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
	//	//_childViews(root);
	//}
	//
	///**
	// * 븅신개지랄
	// */
	//@Deprecated
	//public void moveListenListPage() {
	//	//m_iSetListenItemCount = 0;
	//	//
	//	//m_iListenItemCount = (m_iCurrentListenListPage - 1) * 8;
	//	//
	//	//LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//	//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
	//	//
	//	//LinearLayout layoutContent = (LinearLayout) inf.inflate(R.layout.listen_list, null);
	//	//layoutContent.setLayoutParams(lp);
	//	//addViewKaraoke((LinearLayout) layout_content, layoutContent);
	//	//
	//	//LinearLayout layoutItem1 = (LinearLayout) inf.inflate(R.layout.listen_item, null);
	//	//layoutItem1.setLayoutParams(lp);
	//	//LinearLayout layoutListItem1 = (LinearLayout) findViewById(R.id.layout_listen_list_1);
	//	//addViewKaraoke(layoutListItem1, layoutItem1);
	//	//
	//	////if (remote.m_iSubMainMenuFocus == 2) {
	//	////	TextView txtTotalPage = (TextView) findViewById(R.id.txt_listen_sub_page);
	//	////	txtTotalPage.setText("page " + String.valueOf(m_iCurrentViewListenListPage) + "/" + String.valueOf(m_iTotalListenListPage));
	//	////} else {
	//	////	TextView txtTotalPage = (TextView) findViewById(R.id.txt_my_sub_page);
	//	////	txtTotalPage.setText("page " + String.valueOf(m_iCurrentViewListenListPage) + "/" + String.valueOf(m_iTotalListenListPage));
	//	////}
	//	//
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_1));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_2));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_3));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_4));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_5));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_6));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_7));
	//	//setListenItem((LinearLayout) findViewById(R.id.layout_listen_list_8));
	//}
	//
	///**
	// * 븅신개지랄
	// */
	//@Deprecated
	//public void setListenItem(LinearLayout item) {
	//	//if (mListenItems.size() <= m_iListenItemCount) {
	//	//	return;
	//	//}
	//	//
	//	//LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//	//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	//	//
	//	//LinearLayout layoutItem = (LinearLayout) inf.inflate(R.layout.listen_item, null);
	//	//layoutItem.setLayoutParams(lp);
	//	//addViewKaraoke(item, layoutItem);
	//	//
	//	//// 프로필 이미지
	//	//switch (m_iSetListenItemCount) {
	//	//	case 0:
	//	//		m_imgProfile01 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile01 != null) {
	//	//			if (util_profile01.m_bitMap != null) {
	//	//				util_profile01.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile01 = new _Util(handlerKP);
	//	//		util_profile01.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_1);
	//	//		util_profile01.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile01.start();
	//	//		break;
	//	//	case 1:
	//	//		m_imgProfile02 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile02 != null) {
	//	//			if (util_profile02.m_bitMap != null) {
	//	//				util_profile02.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile02 = new _Util(handlerKP);
	//	//		util_profile02.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_2);
	//	//		util_profile02.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile02.start();
	//	//		break;
	//	//	case 2:
	//	//		m_imgProfile03 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile03 != null) {
	//	//			if (util_profile03.m_bitMap != null) {
	//	//				util_profile03.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile03 = new _Util(handlerKP);
	//	//		util_profile03.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_3);
	//	//		util_profile03.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile03.start();
	//	//		break;
	//	//	case 3:
	//	//		m_imgProfile04 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile04 != null) {
	//	//			if (util_profile04.m_bitMap != null) {
	//	//				util_profile04.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile04 = new _Util(handlerKP);
	//	//		util_profile04.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_4);
	//	//		util_profile04.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile04.start();
	//	//		break;
	//	//	case 4:
	//	//		m_imgProfile05 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile05 != null) {
	//	//			if (util_profile05.m_bitMap != null) {
	//	//				util_profile05.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile05 = new _Util(handlerKP);
	//	//		util_profile05.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_5);
	//	//		util_profile05.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile05.start();
	//	//		break;
	//	//	case 5:
	//	//		m_imgProfile06 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile06 != null) {
	//	//			if (util_profile06.m_bitMap != null) {
	//	//				util_profile06.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile06 = new _Util(handlerKP);
	//	//		util_profile06.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_6);
	//	//		util_profile06.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile06.start();
	//	//		break;
	//	//	case 6:
	//	//		m_imgProfile07 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile07 != null) {
	//	//			if (util_profile07.m_bitMap != null) {
	//	//				util_profile07.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile07 = new _Util(handlerKP);
	//	//		util_profile07.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_7);
	//	//		util_profile07.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile07.start();
	//	//		break;
	//	//	case 7:
	//	//		m_imgProfile08 = (ImageView) layoutItem.findViewById(R.id.img_listen_profile);
	//	//		if (util_profile08 != null) {
	//	//			if (util_profile08.m_bitMap != null) {
	//	//				util_profile08.m_bitMap.recycle();
	//	//			}
	//	//		}
	//	//		util_profile08 = new _Util(handlerKP);
	//	//		util_profile08.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_8);
	//	//		util_profile08.setImageUrl(mListenItems.get(m_iListenItemCount).url_profile);
	//	//		util_profile08.start();
	//	//		break;
	//	//}
	//	//
	//	//// 제목 - 가수
	//	//TextView txtTitle = (TextView) layoutItem.findViewById(R.id.txt_listen_title);
	//	//txtTitle.setText(mListenItems.get(m_iListenItemCount).title + " - " + mListenItems.get(m_iListenItemCount).artist);
	//	//
	//	//// 닉네임
	//	//TextView txtNick = (TextView) layoutItem.findViewById(R.id.txt_listen_nickname);
	//	//txtNick.setText("by " + mListenItems.get(m_iListenItemCount).nickname);
	//	//
	//	//// 추천 횟수
	//	//TextView txtRecommand = (TextView) layoutItem.findViewById(R.id.txt_listen_recommand);
	//	//txtRecommand.setText(mListenItems.get(m_iListenItemCount).heart);
	//	//
	//	//// 듣기 횟수
	//	//String strListenCount = mListenItems.get(m_iListenItemCount).hit;
	//	//if (strListenCount.length() < 6) {
	//	//	while (strListenCount.length() < 6) {
	//	//		strListenCount = "0" + strListenCount;
	//	//	}
	//	//}
	//	//
	//	//TextView txtListen = (TextView) layoutItem.findViewById(R.id.txt_listen_count);
	//	//txtListen.setText(strListenCount);
	//	//
	//	//// 등록일
	//	//TextView txtDate = (TextView) layoutItem.findViewById(R.id.txt_listen_day);
	//	//txtDate.setText(mListenItems.get(m_iListenItemCount).reg_date);
	//	//
	//	//m_iSetListenItemCount++;
	//	//m_iListenItemCount++;
	//}

	public void setListenOtherItem(LinearLayout item) {
		if (mListenOtherItems.size() <= m_iListenOtherItemCount) {
			return;
		}

		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//
		// LinearLayout layoutItem = (LinearLayout) inflate(R.layout.listen_other_profile, null);
		// layoutItem.setLayoutParams(lp);
		// addViewKaraoke(item, layoutItem);
		LinearLayout layoutItem = null;
		int id = getIdentifier(String.format("listen_other_profile_%d", m_iSetListenOtherItemCount + 1), "id");
		if (item.findViewById(id) != null) {
			layoutItem = (LinearLayout) item.findViewById(id);
		} else {
			layoutItem = (LinearLayout) inflate(R.layout.listen_other_profile, null);
			layoutItem.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			layoutItem.setId(id);
			addView(item, layoutItem);
		}

		String url = mListenOtherItems.get(m_iListenOtherItemCount).url_profile;
		ImageView v = (ImageView) layoutItem.findViewById(R.id.img_listen_other_profile);
		putURLImage(v, url, false);

		// 프로필 이미지
		switch (m_iSetListenOtherItemCount) {
			case 0:
				// m_imgProfile01 = v;
				// if (util_profile01 != null) {
				// if (util_profile01.m_bitMap != null) {
				// util_profile01.m_bitMap.recycle();
				// }
				// }
				// util_profile01 = new _Util(handlerKP);
				// util_profile01.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_1);
				// util_profile01.setImageUrl(url);
				// util_profile01.start();
				util_profile01.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 1:
				// m_imgProfile02 = v;
				// if (util_profile02 != null) {
				// if (util_profile02.m_bitMap != null) {
				// util_profile02.m_bitMap.recycle();
				// }
				// }
				// util_profile02 = new _Util(handlerKP);
				// util_profile02.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_2);
				// util_profile02.setImageUrl(url);
				// util_profile02.start();
				util_profile02.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 2:
				// m_imgProfile03 = v;
				// if (util_profile03 != null) {
				// if (util_profile03.m_bitMap != null) {
				// util_profile03.m_bitMap.recycle();
				// }
				// }
				// util_profile03 = new _Util(handlerKP);
				// util_profile03.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_3);
				// util_profile03.setImageUrl(url);
				// util_profile03.start();
				util_profile03.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 3:
				// m_imgProfile04 = v;
				// if (util_profile04 != null) {
				// if (util_profile04.m_bitMap != null) {
				// util_profile04.m_bitMap.recycle();
				// }
				// }
				// util_profile04 = new _Util(handlerKP);
				// util_profile04.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_4);
				// util_profile04.setImageUrl(url);
				// util_profile04.start();
				util_profile04.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 4:
				// m_imgProfile05 = v;
				// if (util_profile05 != null) {
				// if (util_profile05.m_bitMap != null) {
				// util_profile05.m_bitMap.recycle();
				// }
				// }
				// util_profile05 = new _Util(handlerKP);
				// util_profile05.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_5);
				// util_profile05.setImageUrl(mListenOtherItems.get(m_iListenOtherItemCount).url_profile);
				// util_profile05.setImageUrl(url);
				// util_profile05.start();
				util_profile05.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 5:
				// m_imgProfile06 = v;
				// if (util_profile06 != null) {
				// if (util_profile06.m_bitMap != null) {
				// util_profile06.m_bitMap.recycle();
				// }
				// }
				// util_profile06 = new _Util(handlerKP);
				// util_profile06.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_6);
				// util_profile06.setImageUrl(url);
				// util_profile06.start();
				util_profile06.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 6:
				// m_imgProfile07 = v;
				// if (util_profile07 != null) {
				// if (util_profile07.m_bitMap != null) {
				// util_profile07.m_bitMap.recycle();
				// }
				// }
				// util_profile07 = new _Util(handlerKP);
				// util_profile07.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_7);
				// util_profile07.setImageUrl(url);
				// util_profile07.start();
				util_profile07.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
			case 7:
				// m_imgProfile08 = v;
				// if (util_profile08 != null) {
				// if (util_profile08.m_bitMap != null) {
				// util_profile08.m_bitMap.recycle();
				// }
				// }
				// util_profile08 = new _Util(handlerKP);
				// util_profile08.setUtilType(REQUEST_UTIL_PROFILE_IMAGE_8);
				// util_profile08.setImageUrl(url);
				// util_profile08.start();
				util_profile08.setImageUrl(url);
				mListenOtherItemsRecordID.add(mListenOtherItems.get(m_iListenOtherItemCount).record_id);
				break;
		}

		// 닉네임
		TextView txtNick = (TextView) layoutItem.findViewById(R.id.txt_listen_other_profile_nick);
		txtNick.setText("by " + mListenOtherItems.get(m_iListenOtherItemCount).nickname);

		m_iSetListenOtherItemCount++;
		m_iListenOtherItemCount++;
	}

	public void setListeningState() {
		HideMenu(getMethodName());

		// 녹음곡 재생 상태 레이아웃 출력
		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// m_layoutListen = (LinearLayout) inflate(R.layout.listening, null);
		// LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// addContentView(m_layoutListen, param);
		addListening();

		ImageView imgProfile = (ImageView) findViewById(R.id.img_listening_profile);

		String url = null;
		// 프로필 이미지
		if (m_iPaneState == PANE_HOME) {
			imgProfile.setImageBitmap(util_profileHome.m_bitMap);
		} else {
			if (remote.m_iMenuMainFocus != 3) {
				//switch (remote.m_iListenListFocusX) {
				//	case 1:
				//		if (remote.m_iListenListFocusY == 1) {
				//			// img_listen_profile.setImageBitmap(util_profile01.m_bitMap);
				//			if (util_profile01 != null) {
				//				url = util_profile01.getImageUrl();
				//			}
				//		} else {
				//			// img_listen_profile.setImageBitmap(util_profile05.m_bitMap);
				//			if (util_profile05 != null) {
				//				url = util_profile05.getImageUrl();
				//			}
				//		}
				//		break;
				//	case 2:
				//		if (remote.m_iListenListFocusY == 1) {
				//			// img_listen_profile.setImageBitmap(util_profile02.m_bitMap);
				//			if (util_profile02 != null) {
				//				url = util_profile02.getImageUrl();
				//			}
				//		} else {
				//			// img_listen_profile.setImageBitmap(util_profile06.m_bitMap);
				//			if (util_profile06 != null) {
				//				url = util_profile06.getImageUrl();
				//			}
				//		}
				//		break;
				//	case 3:
				//		if (remote.m_iListenListFocusY == 1) {
				//			// img_listen_profile.setImageBitmap(util_profile03.m_bitMap);
				//			if (util_profile03 != null) {
				//				url = util_profile03.getImageUrl();
				//			}
				//		} else {
				//			// img_listen_profile.setImageBitmap(util_profile07.m_bitMap);
				//			if (util_profile07 != null) {
				//				url = util_profile07.getImageUrl();
				//			}
				//		}
				//		break;
				//	case 4:
				//		if (remote.m_iListenListFocusY == 1) {
				//			// img_listen_profile.setImageBitmap(util_profile04.m_bitMap);
				//			if (util_profile04 != null) {
				//				url = util_profile04.getImageUrl();
				//			}
				//		} else {
				//			// img_listen_profile.setImageBitmap(util_profile08.m_bitMap);
				//			if (util_profile08 != null) {
				//				url = util_profile08.getImageUrl();
				//			}
				//		}
				//		break;
				//}
				try {
					int i = remote.m_iListenListFocusX + 4 * (remote.m_iListenListFocusY - 1) - 1;
					int index = (i + (m_iCurrentListenListPage - 1) * 8);
					if (mListenItems != null && index < mListenItems.size() && index > -1) {
						final ListenItem item = mListenItems.get(index);
						url = item.url_profile;
					}
				} catch (Exception e) {
					if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
				}
			} else {
				// img_listen_profile.setImageBitmap(util_myRecordProfile.m_bitMap);
				if (util_myRecordProfile != null) {
					url = util_myRecordProfile.getImageUrl();
				}
			}

			putURLImage(imgProfile, url, false, R.drawable.profile_default);

		}

		TextView txtTitle = (TextView) findViewById(R.id.txt_listening_title);
		if (txtTitle != null) {
			txtTitle.setText(KP.listenItem.title + " - " + KP.listenItem.artist);
		}

		TextView txtNick = (TextView) findViewById(R.id.txt_listening_nick);
		if (txtNick != null) {
			txtNick.setText("by " + KP.listenItem.nickname);
		}

		String strListenCount = KP.listenItem.hit;
		if (strListenCount != null && strListenCount.length() < 6) {
			while (strListenCount.length() < 6) {
				strListenCount = "0" + strListenCount;
			}
		}

		TextView txtCount = (TextView) findViewById(R.id.txt_listening_listen_count);
		if (txtCount != null) {
			txtCount.setText(strListenCount);
		}

		// TextView txt_listen_day = (TextView)findViewById(R.id.txt_listening_date);
		// txt_listen_day.setText(KP.listenItem.mStrDate);

		TextView txtRecommand = (TextView) findViewById(R.id.txt_listening_recommand);
		if (txtRecommand != null) {
			txtRecommand.setText(KP.listenItem.heart);
		}

		// 하단 첫번째 가이드를 '다른 녹음곡 보기' 로 변경
		showBottomGuideListenOther(getMethodName());
	}

	public void setListeningStateOther() {
		HideMenu(getMethodName());

		// 녹음곡 재생 상태 레이아웃 출력
		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// m_layoutListen = (LinearLayout) inflate(R.layout.listening, null);
		// LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// addContentView(m_layoutListen, param);
		addListening();

		ImageView imgProfileOther = (ImageView) findViewById(R.id.img_listening_profile);

		String url = "";
		switch (remote.m_iListenOther) {
			case 1:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile01.m_bitMap);
				url = util_profile01.getImageUrl();
				break;
			case 2:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile02.m_bitMap);
				url = util_profile02.getImageUrl();
				break;
			case 3:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile03.m_bitMap);
				url = util_profile03.getImageUrl();
				break;
			case 4:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile04.m_bitMap);
				url = util_profile04.getImageUrl();
				break;
			case 5:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile05.m_bitMap);
				url = util_profile05.getImageUrl();
				break;
			case 6:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile06.m_bitMap);
				url = util_profile06.getImageUrl();
				break;
			case 7:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile07.m_bitMap);
				url = util_profile07.getImageUrl();
				break;
			case 8:
				// imgProfileOther.setImageBitmap(null);
				// imgProfileOther.setImageBitmap(util_profile08.m_bitMap);
				url = util_profile08.getImageUrl();
				break;
		}

		putURLImage(imgProfileOther, url, false);

		/*
		 * m_imgProfileOther = (ImageView)findViewById(R.id.img_listening_profile); if (util_profileListeningOther != null) { if (util_profileListeningOther.m_bitMap != null) {
		 * util_profileListeningOther.m_bitMap.recycle(); } } util_profileListeningOther = new _Util(handlerKP); util_profileListeningOther.setUtilType(_REQUEST_UTIL_PROFILE_IMAGE_OTHER);
		 * util_profileListeningOther .setImageUrl(mListenItems.get(m_iListenOtherItemCount).url_profile); util_profileListeningOther.start();
		 */

		TextView txtTitle = (TextView) findViewById(R.id.txt_listening_title);
		txtTitle.setText(KP.listenItem.title + " - " + KP.listenItem.artist);

		TextView txtNick = (TextView) findViewById(R.id.txt_listening_nick);
		txtNick.setText("by " + KP.listenItem.nickname);

		String strListenCount = KP.listenItem.hit;
		if (strListenCount != null && strListenCount.length() < 6) {
			while (strListenCount.length() < 6) {
				strListenCount = "0" + strListenCount;
			}
		}

		TextView txtCount = (TextView) findViewById(R.id.txt_listening_listen_count);
		txtCount.setText(strListenCount);

		// TextView txt_listen_day = (TextView)findViewById(R.id.txt_listening_date);
		// txt_listen_day.setText(KP.listenItem.mStrDate);

		TextView txtRecommand = (TextView) findViewById(R.id.txt_listening_recommand);
		txtRecommand.setText(KP.listenItem.heart);

		// 하단 첫번째 가이드를 '다른 녹음곡 보기' 로 변경
		showBottomGuideListenOther(getMethodName());
	}

	public void setListenOtherPage() {
		remote.m_iState = STATE_LISTEN_OTHER;
		remote.m_iListenOther = 1;
		remote.m_iListenOtherBefore = 1;
		m_iCurrentListenOtherListPage = 1;
		m_iCurrentViewListenOtherListPage = 1;

		// 하단 첫번째 가이드를 '다른 녹음곡 닫기' 로 변경
		showBottomGuideListenOther(getMethodName());

		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		m_layoutListeningOther = (LinearLayout) inflate(R.layout.listen_other, null);

		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addContentView(m_layoutListeningOther, param);

		m_iSetListenOtherItemCount = 0;
		m_iListenOtherItemCount = 0;

		// 총 페이지가 1페이지 이상이면 좌우 화살표 있는 배경으로 전환
		LinearLayout layoutOther = (LinearLayout) findViewById(R.id.layout_listen_other);

		if (m_iTotalListenOtherListPage > 1) {
			layoutOther.setBackgroundResource(R.drawable.listen_play_other_bg_narrow);
		} else {
			layoutOther.setBackgroundResource(R.drawable.listen_play_other_bg);
		}

		mListenOtherItemsRecordID.clear();
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_01));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_02));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_03));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_04));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_05));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_06));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_07));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_08));
	}

	public void setCustomerDetailContent() {
		remote.m_iState = STATE_CUSTOMER_LIST_DETAIL;
		m_iCurrentCustomerListDetailPage = 1;

		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		LinearLayout layoutContent = (LinearLayout) inflate(R.layout.customer_list_detail, null);
		layoutContent.setLayoutParams(lp);
		addView(layout_content, layoutContent);

		m_strCustomerListDetailTitle = KP.m_strCustomerDetailTitle;

		if (remote.m_iMenuCustomerFocus == 1) {
			if (("E").equalsIgnoreCase(b_type)) {
				m_bIsEventDetail = true;
				m_strCustomerListDetailDate = KP.term_date;
				m_iCustomerListDetailTotalPage = 1;
			} else {
				m_bIsEventDetail = false;
				m_strCustomerListDetailDate = KP.m_strCustomerDetailDate;
				m_iCustomerListDetailTotalPage = url_imgs.size();
			}
		} else {
			m_bIsEventDetail = false;
			m_strCustomerListDetailDate = KP.m_strCustomerDetailDate;
			m_iCustomerListDetailTotalPage = url_imgs.size();
		}

		TextView txtTitle = (TextView) findViewById(R.id.txt_customer_detail_title);
		TextView txtDate = (TextView) findViewById(R.id.txt_customer_detail_date);
		TextView txtPage = (TextView) findViewById(R.id.txt_customer_detail_page);

		txtTitle.setText(m_strCustomerListDetailTitle);
		txtDate.setText(m_strCustomerListDetailDate);

		txtPage.setText(String.valueOf(m_iCurrentCustomerListDetailPage) + "/" + String.valueOf(m_iCustomerListDetailTotalPage));

		util = new _Util(handlerKP);
		if (m_bIsEventDetail) {
			util.setUtilType(REQUEST_UTIL_EVENT_DETAIL_ON);
		} else {
			util.setUtilType(REQUEST_UTIL_CUSTOMER_DETAIL_IMAGE);
		}
		util.setImageUrl(url_imgs.get(m_iCustomerListDetailPage - 1));
		util.start();

		setListBackground(getMethodName());
	}

	protected void setPageTextSongList() {
	}

	/**
	 * @see Main2XXXX#moveSingListPage()
	 */
	public void moveSingListPage() {
	}

	ViewGroup listen_list;
	List<ListenLine> listen_line = new ArrayList<ListenLine>();

	/**
	 * @see Main2XXXX#inflateListListen(int)
	 */
	protected void inflateListListen(int res) {
	}

	protected void resetListenList() {
	}

	/**
	 * @see Main2XXXX#setPageTextListenList()
	 */
	protected void setPageTextListenList() {
	}

	/**
	 * @see Main2XXXX#startListenListPage()
	 */
	protected void startListenListPage() {
	}

	public void moveListenOtherPage() {
		mListenOtherItemsRecordID.clear();
		m_iSetListenOtherItemCount = 0;
		m_iListenOtherItemCount = (m_iCurrentListenOtherListPage - 1) * 8;

		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_01));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_02));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_03));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_04));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_05));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_06));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_07));
		setListenOtherItem((LinearLayout) findViewById(R.id.layout_listen_other_08));
	}

	/**
	 * @see Main2XXXX#moveMyListPage()
	 */
	public void moveMyListPage() {
	}

	protected void setPageTextCustomerList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "page " + m_iCurrentViewCustomerListPage + "/" + m_iTotalCustomerListPage);
		TextView txtPage = (TextView) findViewById(R.id.txt_customer_sub_page);

		if (txtPage != null) {
			txtPage.setText("page " + String.valueOf(m_iCurrentViewCustomerListPage) + "/" + String.valueOf(m_iTotalCustomerListPage));
			if (m_iTotalCustomerListPage > 0) {
				txtPage.setVisibility(View.VISIBLE);
			} else {
				txtPage.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void moveCustomerListPage() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "moveCustomerListPage >");

		setPageTextCustomerList();

		m_iCustomerListItemCount = (m_iCurrentCustomerListPage - 1) * 6;

		TextView txtTitle = null, txtDate = null;
		ImageView imgStats = null;

		int iViewCount = 1;
		while (iViewCount < 7) {
			try {
				switch (iViewCount) {
					case 1:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_1);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_1);
						break;
					case 2:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_2);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_2);
						break;
					case 3:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_3);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_3);
						break;
					case 4:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_4);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_4);
						break;
					case 5:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_5);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_5);
						break;
					case 6:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_6);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_6);
						break;
				}

				if (remote.m_iMenuCustomerFocus == 1) {
					switch (iViewCount) {
						case 1:
							imgStats = (ImageView) findViewById(R.id.img_customer_ing_1);
							break;
						case 2:
							imgStats = (ImageView) findViewById(R.id.img_customer_ing_2);
							break;
						case 3:
							imgStats = (ImageView) findViewById(R.id.img_customer_ing_3);
							break;
						case 4:
							imgStats = (ImageView) findViewById(R.id.img_customer_ing_4);
							break;
						case 5:
							imgStats = (ImageView) findViewById(R.id.img_customer_ing_5);
							break;
						case 6:
							imgStats = (ImageView) findViewById(R.id.img_customer_ing_6);
							break;
					}
				}

				if (m_iCustomerListItemCount == mCustomerItems.size()) {
					txtTitle.setText("");
					txtDate.setText("");

					if (remote.m_iMenuCustomerFocus == 1) {
						imgStats.setVisibility(View.INVISIBLE);
					}
				} else {
					txtTitle.setText(mCustomerItems.get(m_iCustomerListItemCount).title);

					String strDate = "";

					if (remote.m_iMenuCustomerFocus == 1) {
						if (mCustomerItems.get(m_iCustomerListItemCount).e_stats.equals("null")) {
							imgStats.setVisibility(View.INVISIBLE);
						} else if (mCustomerItems.get(m_iCustomerListItemCount).e_stats.equals("Y")) {
							imgStats.setImageResource(R.drawable.event_stats_ing);
							imgStats.setVisibility(View.VISIBLE);
						} else if (mCustomerItems.get(m_iCustomerListItemCount).e_stats.equals("N")) {
							imgStats.setImageResource(R.drawable.event_stats_end);
							imgStats.setVisibility(View.VISIBLE);
						}

						if (!mCustomerItems.get(m_iCustomerListItemCount).term_date.equals("null")) {
							strDate = mCustomerItems.get(m_iCustomerListItemCount).term_date;
						} else {
							strDate = mCustomerItems.get(m_iCustomerListItemCount).reg_date;
						}
					} else {
						if (!mCustomerItems.get(m_iCustomerListItemCount).reg_date.equals("null")) {
							strDate = mCustomerItems.get(m_iCustomerListItemCount).reg_date;
						}
					}

					txtDate.setText(strDate);
				}
			} catch (Exception e) {
				if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
			}

			if (m_iCustomerListItemCount < mCustomerItems.size()) {
				m_iCustomerListItemCount++;
			}

			iViewCount++;
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "moveCustomerListPage <");
	}

	public void moveEventListPage() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "moveEventListPage >");

		setPageTextCustomerList();

		m_iCustomerListItemCount = (m_iCurrentCustomerListPage - 1) * 6;

		TextView txtTitle = null, txtDate = null;

		int iViewCount = 1;
		while (iViewCount < 7) {
			try {
				switch (iViewCount) {
					case 1:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_1);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_1);
						break;
					case 2:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_2);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_2);
						break;
					case 3:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_3);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_3);
						break;
					case 4:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_4);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_4);
						break;
					case 5:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_5);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_5);
						break;
					case 6:
						txtTitle = (TextView) findViewById(R.id.txt_customer_title_6);
						txtDate = (TextView) findViewById(R.id.txt_customer_date_6);
						break;
				}

				if (m_iCustomerListItemCount == mCustomerItems.size()) {
					txtTitle.setText("");
					txtDate.setText("");
				} else {
					txtTitle.setText(mCustomerItems.get(m_iCustomerListItemCount).title);
					txtDate.setText(mCustomerItems.get(m_iCustomerListItemCount).reg_date);

				}
			} catch (Exception e) {
				if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
			}

			if (m_iCustomerListItemCount < mCustomerItems.size()) {
				m_iCustomerListItemCount++;
			}

			iViewCount++;
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "moveEventListPage <");
	}

	protected void setPageTextSearchList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "page " + m_iCurrentViewCustomerListPage + "/" + m_iTotalCustomerListPage);
		TextView txtPage = null;

		if (remote.m_iSearchSubMenuFocus == 1) {
			txtPage = (TextView) findViewById(R.id.txt_search_self_page);
		} else {
			if (m_iSearchLetterMode == KOR) {
				txtPage = (TextView) findViewById(R.id.txt_search_letter_kor_page);
			} else if (m_iSearchLetterMode == ENG) {
				txtPage = (TextView) findViewById(R.id.txt_search_letter_eng_page);
			} else if (m_iSearchLetterMode == NUM) {
				txtPage = (TextView) findViewById(R.id.txt_search_letter_num_page);
			}
		}

		if (txtPage != null) {
			txtPage.setText("page " + String.valueOf(m_iCurrentViewSearchListPage) + "/" + String.valueOf(m_iTotalSearchListPage));
			if (m_iTotalSearchListPage > 0) {
				txtPage.setVisibility(View.VISIBLE);
			} else {
				txtPage.setVisibility(View.INVISIBLE);
			}
		}
	}

	public void moveSearchListPage() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "moveSearchListPage >");

		// 직접검색 : 한 페이지 당 5개
		if (remote.m_iSearchSubMenuFocus == 1) {
			setPageTextSearchList();
			m_iSearchListItemCount = (m_iCurrentSearchListPage - 1) * 5;
			// 색인검색 : 한 페이지 당 4개
		} else {
			setPageTextSearchList();
			m_iSearchListItemCount = (m_iCurrentSearchListPage - 1) * 4;
		}

		TextView txtNumber = null;
		TextView txtTitle = null;
		TextView txtSinger = null;
		ImageView imgIcon = null;

		mSongFavors.clear();

		int iViewCount = 1;
		while (iViewCount < 6) {
			switch (iViewCount) {
				case 1:
					if (remote.m_iSearchSubMenuFocus == 1) {
						txtNumber = (TextView) findViewById(R.id.txt_search_self_number_1);
						txtTitle = (TextView) findViewById(R.id.txt_search_self_title_1);
						txtSinger = (TextView) findViewById(R.id.txt_search_self_singer_1);
						imgIcon = (ImageView) findViewById(R.id.img_search_self_icon_1);
					} else {
						txtNumber = (TextView) findViewById(R.id.txt_search_letter_number_1);
						txtTitle = (TextView) findViewById(R.id.txt_search_letter_title_1);
						txtSinger = (TextView) findViewById(R.id.txt_search_letter_singer_1);
						imgIcon = (ImageView) findViewById(R.id.img_search_letter_icon_1);
					}
					break;
				case 2:
					if (remote.m_iSearchSubMenuFocus == 1) {
						txtNumber = (TextView) findViewById(R.id.txt_search_self_number_2);
						txtTitle = (TextView) findViewById(R.id.txt_search_self_title_2);
						txtSinger = (TextView) findViewById(R.id.txt_search_self_singer_2);
						imgIcon = (ImageView) findViewById(R.id.img_search_self_icon_2);
					} else {
						txtNumber = (TextView) findViewById(R.id.txt_search_letter_number_2);
						txtTitle = (TextView) findViewById(R.id.txt_search_letter_title_2);
						txtSinger = (TextView) findViewById(R.id.txt_search_letter_singer_2);
						imgIcon = (ImageView) findViewById(R.id.img_search_letter_icon_2);
					}
					break;
				case 3:
					if (remote.m_iSearchSubMenuFocus == 1) {
						txtNumber = (TextView) findViewById(R.id.txt_search_self_number_3);
						txtTitle = (TextView) findViewById(R.id.txt_search_self_title_3);
						txtSinger = (TextView) findViewById(R.id.txt_search_self_singer_3);
						imgIcon = (ImageView) findViewById(R.id.img_search_self_icon_3);
					} else {
						txtNumber = (TextView) findViewById(R.id.txt_search_letter_number_3);
						txtTitle = (TextView) findViewById(R.id.txt_search_letter_title_3);
						txtSinger = (TextView) findViewById(R.id.txt_search_letter_singer_3);
						imgIcon = (ImageView) findViewById(R.id.img_search_letter_icon_3);
					}
					break;
				case 4:
					if (remote.m_iSearchSubMenuFocus == 1) {
						txtNumber = (TextView) findViewById(R.id.txt_search_self_number_4);
						txtTitle = (TextView) findViewById(R.id.txt_search_self_title_4);
						txtSinger = (TextView) findViewById(R.id.txt_search_self_singer_4);
						imgIcon = (ImageView) findViewById(R.id.img_search_self_icon_4);
					} else {
						txtNumber = (TextView) findViewById(R.id.txt_search_letter_number_4);
						txtTitle = (TextView) findViewById(R.id.txt_search_letter_title_4);
						txtSinger = (TextView) findViewById(R.id.txt_search_letter_singer_4);
						imgIcon = (ImageView) findViewById(R.id.img_search_letter_icon_4);
					}
					break;
				case 5:
					txtNumber = (TextView) findViewById(R.id.txt_search_self_number_5);
					txtTitle = (TextView) findViewById(R.id.txt_search_self_title_5);
					txtSinger = (TextView) findViewById(R.id.txt_search_self_singer_5);
					imgIcon = (ImageView) findViewById(R.id.img_search_self_icon_5);
					break;
			}

			if (m_iSearchListItemCount == mSearchItems.size()) {
				txtNumber.setText("");
				txtTitle.setText("");
				txtSinger.setText("");
				imgIcon.setVisibility(View.INVISIBLE);
			} else {
				txtNumber.setText(mSearchItems.get(m_iSearchListItemCount).song_id);
				txtTitle.setText(mSearchItems.get(m_iSearchListItemCount).title);
				txtSinger.setText(mSearchItems.get(m_iSearchListItemCount).artist);
				imgIcon.setVisibility(View.VISIBLE);
				if (mSearchItems.get(m_iSearchListItemCount).mark_favorite.equals("Y")) {
					imgIcon.setImageResource(R.drawable.common_bullet_favor_off);
				}
				mSongFavors.add(mSearchItems.get(m_iSearchListItemCount).mark_favorite);
				if (BuildConfig.DEBUG) Log.i(_toString(), "ADD Favor : " + mSearchItems.get(m_iSearchListItemCount).song_id);

				m_iSearchListItemCount++;
			}

			iViewCount++;

			if (remote.m_iSearchSubMenuFocus == 2) {
				if (iViewCount == 5) {
					return;
				}
			}
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "moveSearchListPage <");
	}

	public void moveMyRecordListPage() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "moveMyRecordListPage >");

		m_imgMyRecordProfile = (ImageView) findViewById(R.id.img_my_record_profile);
		TextView txtMyRecordHit = (TextView) findViewById(R.id.txt_my_record_hit);
		TextView txtMyRecordRecomman = (TextView) findViewById(R.id.txt_my_record_recommand);
		TextView txtMyRecordNick = (TextView) findViewById(R.id.txt_my_record_nickname);
		TextView txtMyRecordPhone = (TextView) findViewById(R.id.txt_my_record_phoneno);
		TextView txtMyRecordDate = (TextView) findViewById(R.id.txt_my_record_date);

		auth_phoneno = readKaraoke();
		auth_phoneno = checkKaraoke();

		txtMyRecordPhone.setText(auth_phoneno);

		//if (util_myRecordProfile != null) {
		//	if (util_myRecordProfile.m_bitMap != null) {
		//		util_myRecordProfile.m_bitMap.recycle();
		//	}
		//}
		//util_myRecordProfile = new _Util(handlerKP);
		//util_myRecordProfile.setUtilType(REQUEST_UTIL_MY_RECORD_PROFILE_IMAGE);
		//util_myRecordProfile.setImageUrl(KP.my_auth_url_profile);
		//util_myRecordProfile.start();
		putURLImage(m_imgMyRecordProfile, KP.my_auth_url_profile, false);

		if (KP.my_auth_hit.equals("null")) {
			txtMyRecordHit.setText("000000");
		} else {
			String strListenCount = KP.my_auth_hit;
			if (strListenCount.length() < 6) {
				while (strListenCount.length() < 6) {
					strListenCount = "0" + strListenCount;
				}
			}

			txtMyRecordHit.setText(strListenCount);
		}
		if (KP.my_auth_heart.equals("null")) {
			txtMyRecordRecomman.setText("000000");
		} else {
			String strListenCount = KP.my_auth_heart;
			if (strListenCount.length() < 6) {
				while (strListenCount.length() < 6) {
					strListenCount = "0" + strListenCount;
				}
			}

			txtMyRecordRecomman.setText(strListenCount);
		}

		if (KP.my_auth_nickname.equals("-")) {
			txtMyRecordNick.setText("");
		} else {
			txtMyRecordNick.setText(KP.my_auth_nickname);
		}

		txtMyRecordDate.setText(KP.auth_date);

		// m_iSetListenItemCount = 0;

		m_iListenItemCount = (m_iCurrentListenListPage - 1) * 6;

		TextView txtTotalPage = (TextView) findViewById(R.id.txt_song_page);
		if (m_iTotalListenListPage == 0) {
		} else {
			txtTotalPage.setText("page " + String.valueOf(m_iCurrentViewListenListPage) + "/" + String.valueOf(m_iTotalListenListPage));
		}

		ImageView imgRecommand = null;
		TextView txtRecommandCount = null, txtTitle = null, txtDate = null;

		int iViewCount = 1;
		while (iViewCount < 7) {
			switch (iViewCount) {
				case 1:
					imgRecommand = (ImageView) findViewById(R.id.img_my_record_recommand_1);
					txtRecommandCount = (TextView) findViewById(R.id.txt_my_record_recommand_count_1);
					txtTitle = (TextView) findViewById(R.id.txt_my_record_title_1);
					txtDate = (TextView) findViewById(R.id.txt_my_record_date_1);
					break;
				case 2:
					imgRecommand = (ImageView) findViewById(R.id.img_my_record_recommand_2);
					txtRecommandCount = (TextView) findViewById(R.id.txt_my_record_recommand_count_2);
					txtTitle = (TextView) findViewById(R.id.txt_my_record_title_2);
					txtDate = (TextView) findViewById(R.id.txt_my_record_date_2);
					break;
				case 3:
					imgRecommand = (ImageView) findViewById(R.id.img_my_record_recommand_3);
					txtRecommandCount = (TextView) findViewById(R.id.txt_my_record_recommand_count_3);
					txtTitle = (TextView) findViewById(R.id.txt_my_record_title_3);
					txtDate = (TextView) findViewById(R.id.txt_my_record_date_3);
					break;
				case 4:
					imgRecommand = (ImageView) findViewById(R.id.img_my_record_recommand_4);
					txtRecommandCount = (TextView) findViewById(R.id.txt_my_record_recommand_count_4);
					txtTitle = (TextView) findViewById(R.id.txt_my_record_title_4);
					txtDate = (TextView) findViewById(R.id.txt_my_record_date_4);
					break;
				case 5:
					imgRecommand = (ImageView) findViewById(R.id.img_my_record_recommand_5);
					txtRecommandCount = (TextView) findViewById(R.id.txt_my_record_recommand_count_5);
					txtTitle = (TextView) findViewById(R.id.txt_my_record_title_5);
					txtDate = (TextView) findViewById(R.id.txt_my_record_date_5);
					break;
				case 6:
					imgRecommand = (ImageView) findViewById(R.id.img_my_record_recommand_6);
					txtRecommandCount = (TextView) findViewById(R.id.txt_my_record_recommand_count_6);
					txtTitle = (TextView) findViewById(R.id.txt_my_record_title_6);
					txtDate = (TextView) findViewById(R.id.txt_my_record_date_6);
					break;
			}

			if (m_iTotalListenListPage != 0) {
				if (m_iListenItemCount >= mListenItems.size()) {
					imgRecommand.setVisibility(View.INVISIBLE);
					txtRecommandCount.setText("");
					txtTitle.setText("");
					txtDate.setText("");
				} else {
					imgRecommand.setVisibility(View.VISIBLE);
					txtRecommandCount.setText(mListenItems.get(m_iListenItemCount).heart);
					txtTitle.setText(mListenItems.get(m_iListenItemCount).title);
					txtDate.setText(mListenItems.get(m_iListenItemCount).reg_date);
				}

				m_iListenItemCount++;
			} else {
				// imgRecommand.setVisibility(View.INVISIBLE);
			}

			iViewCount++;
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "moveMyRecordListPage <");
	}

	Button btn_home_main_01;
	Button btn_home_main_02;
	Button btn_home_main_03;
	Button btn_home_main_04;
	Button btn_home_main_05;

	View btn_home_event_01;
	View btn_home_event_02;
	View btn_home_event_03;
	View btn_home_event_04;
	View btn_home_event_05;
	View layout_home_notice_menu;
	View bal_sing;
	TextView txt_home_sing_title;
	TextView txt_home_sing_artist;
	TextView txt_home_listen_title;
	TextView txt_home_listen_artist;
	View bal_listen;

	Button btn_main_sing;
	Button btn_main_listen;
	Button btn_main_my;
	Button btn_main_shop;
	Button btn_main_customer;

	View bg_main;
	View layout_body;

	/**
	 * @see Main2XXXX#setBaseLayout()
	 */
	protected void setBaseLayout() {
		findViewById(R.id.main).setVisibility(View.VISIBLE);
	}

	protected void setListBackground(String method) {
	}

	/**
	 * @see Main2X#ShowMessageAlert(java.lang.String)
	 */
	@Deprecated
	public void ShowMessageAlert(String message) {
	}

	public void ShowMessageOk(int type, String title, String message) {
		try {
			// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (message_ok == null || !ViewCompat.isAttachedToWindow(message_ok)) {
				//message_ok = (LinearLayout) inflate(R.layout.message_ok, null);
				//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				//addContentView(message_ok, param);
				message_ok = (LinearLayout) addPopup(R.layout.message_ok);

			}

			if (message_ok != null) {
				message_ok.setVisibility(View.VISIBLE);
			}

			TextView textTitle = (TextView) findViewById(R.id.txt_message_ok_title);
			TextView textMessage = (TextView) findViewById(R.id.txt_message_ok_message);

			textTitle.setText(title);
			textMessage.setText(message);

			m_bShowMessageOK = true;

			if (type == POPUP_EXIT) {
				m_bIsExit = true;
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	public void HideMessageOk() {
		// if (message_ok != null) {
		// ((ViewManager) message_ok.getParent()).removeView(message_ok);
		// m_bShowMessageOK = false;
		//
		// // OK 메세지박스가 떠있으면서 직접검색 메뉴인 상태는 '검색어를 입력해주세요' 팝업인 경우임
		// // 이 때는 팝업을 닫고 메뉴단을 새로 그려줘야 IME가 올라옴
		// if (remote.m_iState == STATE_SEARCH_SELF) {
		// displaySearchSelf(REMOTE_NONE);
		// }
		// }
		removeView(message_ok);
		message_ok = null;
		m_bShowMessageOK = false;

		// OK 메세지박스가 떠있으면서 직접검색 메뉴인 상태는 '검색어를 입력해주세요' 팝업인 경우임
		// 이 때는 팝업을 닫고 메뉴단을 새로 그려줘야 IME가 올라옴
		if (remote.m_iState == STATE_SEARCH_SELF) {
			displaySearchSelf(REMOTE_NONE);
		}
	}

	public void StartPlaying() {
		HideMenu(getMethodName());

		// 상단 곡번호 등록
		SetTopNumber(m_strRequestPlaySongID);
		if (BuildConfig.DEBUG) Log.i(_toString(), "TOP Number = " + m_strRequestPlaySongID);
	}

	public void stopPlay(int engage) {
		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ST]" + PLAY_ENGAGE.get(engage) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		if (m_bIsHiddenMenu) {
			ShowMenu(getMethodName());
		}

		// hideBottomGuide01();

		// Play playActivity = (Play)Play.ActivityPlay;
		// if (playActivity != null) {
		// if (engage == NEXT) {
		// playActivity.finish();
		// } else {
		// playActivity.finish();
		// }
		// }

		/*
		 * 동영상 배경화면 사용할 시, 반주곡 중지하면서 멈춤
		 */
		if (video != null && video.isPlaying()) {
			video.stopMusicVideo(video_url_back, engage);
		}

		// if (m_strSTBVender != P_APPNAME_SKT_BOX) {
		// if (arrPlayList.size() > 0) {
		// SetBottomGuideText02(R.drawable.common_btn_yellow, getString(R.string.menu_bottom_start_song)); // 재생이 중지됨 = 반주곡 시작
		// } else {
		// HideBottomGuide02();
		// }
		// } else {
		// if (arrPlayList.size() > 0) {
		// SetBottomGuideText02(R.drawable.common_btn_green, getString(R.string.menu_bottom_start_song)); // 재생이 중지됨 = 반주곡 시작
		// } else {
		// HideBottomGuide02();
		// }
		// }

		SetTopNumber("00000");

		if (player != null) {
			if (isPlaying()) {
				KP(REQUEST_SONG_PLAYED_TIME, KP_1012, M1_MENU_SING, M2_SING_HOT);
			}
		}

		if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + "[ED]" + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void ShowGenre() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + remote.isGenre());

		//m_bIsGenre = true;

		findViewById(R.id.layout_sing_genre).setVisibility(View.VISIBLE);

		//븅신개삽지랄
		//LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		//lp1.weight = 3.0f;
		//findViewById(R.id.layout_list_blank1).setLayoutParams(lp1);
		//
		//LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		//lp2.weight = 10.0f;
		//findViewById(R.id.layout_list_center).setLayoutParams(lp2);
		//
		//LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		//lp3.weight = 1.0f;
		//findViewById(R.id.layout_list_blank2).setLayoutParams(lp3);
	}

	protected void HideGenre() {
		if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + remote.isGenre());

		//m_bIsGenre = false;

		findViewById(R.id.layout_sing_genre).setVisibility(View.INVISIBLE);

		//븅신개삽지랄
		//LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		//lp1.weight = 1.0f;
		//findViewById(R.id.layout_list_blank1).setLayoutParams(lp1);
		//
		//LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		//lp2.weight = 12.0f;
		//findViewById(R.id.layout_list_center).setLayoutParams(lp2);
		//
		//LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		//lp3.weight = 1.0f;
		//findViewById(R.id.layout_list_blank2).setLayoutParams(lp3);
	}


	ViewGroup mPopupLoading;
	ImageView mImageLoading;
	ImageView mImageLoadingBg;
	View mSpaceLoading;

	private void addLoading() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		animLoadingRotate = AnimationUtils.loadAnimation(this, R.anim.loading_rotate);

		ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		mPopupLoading = (ViewGroup) inflate(R.layout.loading, null);
		addContentView(mPopupLoading, param);

		mImageLoading = (ImageView) mPopupLoading.findViewById(R.id.img_loading);
		mImageLoadingBg = (ImageView) mPopupLoading.findViewById(R.id.img_loading_bg);
		mSpaceLoading = mPopupLoading.findViewById(R.id.space_loading);
	}

	//private void removeLoading() {
	//	if (BuildConfig.DEBUG) _LOG.e(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	//
	//	if (mPopupLoading != null) {
	//		// ((ViewManager) mPopupLoading.getParent()).removeView(mPopupLoading);
	//		removeView(mPopupLoading);
	//		mPopupLoading = null;
	//	}
	//}

	private boolean isLoading = true;

	protected boolean isLoading() {
		//return (mPopupLoading != null && (mPopupLoading.getVisibility() == View.VISIBLE));
		return isLoading;
	}

	//private boolean m_bIsLongLoading = false;

	/**
	 * 로딩팝업호출구분
	 */
	protected String loading_method;
	/**
	 * 로딩팝업종류구분
	 */
	protected LOADING_TIME loading_time = LOADING_TIME.LOADING_NONE;


	/**
	 * 븅신...로딩...지랄한다...왜...뒤지라고...굿을하지...
	 */
	protected void startLoading(String method, int time) {
		/*if (BuildConfig.DEBUG) */Log.wtf(_toString(), "startLoading() " + method + ":" + LOADING_TIME.get(time) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		this.isLoading = true;
		this.loading_method = method;
		this.loading_time = LOADING_TIME.get(time);

		mPopupLoading.setVisibility(View.VISIBLE);
		mPopupLoading.bringToFront();

		post(showLoading);
	}

	private Runnable showLoading = new Runnable() {
		@Override
		public void run() {
			if (loading_time == LOADING_TIME.LOADING_SHORT) {
				//m_bIsLongLoading = false;
				mImageLoadingBg.setVisibility(View.INVISIBLE);
				mSpaceLoading.setVisibility(View.GONE);
			} else {
				//m_bIsLongLoading = true;
				mImageLoadingBg.setVisibility(View.VISIBLE);
				mSpaceLoading.setVisibility(View.VISIBLE);
			}
			mImageLoading.startAnimation(animLoadingRotate);
		}
	};

	/**
	 * 븅신...로딩...지랄한다...왜...뒤지라고...굿을하지...
	 */
	protected void stopLoading(String method) {
		/*if (BuildConfig.DEBUG) */Log.wtf(_toString(), "stopLoading() " + method + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));

		//m_bIsLongLoading = false;
		isLoading = false;

		mPopupLoading.setVisibility(View.INVISIBLE);

		mImageLoading.clearAnimation();
	}

	protected void showLoadingPopup() {
		if (mPopupLoading != null) {
			mPopupLoading.setVisibility(View.VISIBLE);
		}
	}

	protected void hideLoadingPopup() {
		if (mPopupLoading != null) {
			mPopupLoading.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3#HideVirtualRemote()
	 */
	@Deprecated
	protected void HideVirtualRemote() {
	}

	protected boolean CheckNumberKey(int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1 || keyCode == KeyEvent.KEYCODE_2 || keyCode == KeyEvent.KEYCODE_3 || keyCode == KeyEvent.KEYCODE_4 || keyCode == KeyEvent.KEYCODE_5 || keyCode == KeyEvent.KEYCODE_6 || keyCode == KeyEvent.KEYCODE_7 || keyCode == KeyEvent.KEYCODE_8 || keyCode == KeyEvent.KEYCODE_9 || keyCode == 18) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @see kr.kymedia.kykaraoke.tv.Main3#CheckNotContentsSongList()
	 */
	protected boolean CheckNotContentsSongList() {
		//if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotContentsSongList >");
		//if (BuildConfig.DEBUG) Log.i(_toString(), "m_iSongListFocus = " + String.valueOf(remote.m_iSongListFocus));
		//
		//if (remote.m_iState == STATE_SONG_LIST || remote.m_iState == STATE_MY_LIST) {
		//	TextView txtCheck = null;
		//
		//	if (remote.m_iSongListFocus == 6) {
		//		return true;
		//	}
		//
		//	switch(remote.m_iSongListFocus)
		//	{
		//		case 1 :
		//			txtCheck =  (TextView)findViewById(R.id.txt_sing_number_2);
		//			break;
		//		case 2 :
		//			txtCheck =  (TextView)findViewById(R.id.txt_sing_number_3);
		//			break;
		//		case 3 :
		//			txtCheck =  (TextView)findViewById(R.id.txt_sing_number_4);
		//			break;
		//		case 4 :
		//			txtCheck =  (TextView)findViewById(R.id.txt_sing_number_5);
		//			break;
		//		case 5 :
		//			txtCheck =  (TextView)findViewById(R.id.txt_sing_number_6);
		//			break;
		//	}
		//
		//	if (txtCheck.getText().equals("")) {
		//		if (BuildConfig.DEBUG) Log.i(_toString(), "true");
		//		return true;
		//	} else {
		//		if (BuildConfig.DEBUG) Log.i(_toString(), "false");
		//		return false;
		//	}
		//}
		//
		//return true;
		return false;
	}

	protected boolean CheckNotContentsMyRecordList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotContentsMyRecordList >");
		if (BuildConfig.DEBUG) Log.i(_toString(), "m_iSongListFocus = " + String.valueOf(remote.m_iSongListFocus));

		if (remote.m_iState == STATE_MY_RECORD_LIST) {
			TextView txtCheck = null;

			if (remote.m_iSongListFocus == 6) {
				return true;
			}

			switch (remote.m_iSongListFocus) {
				case 1:
					txtCheck = (TextView) findViewById(R.id.txt_my_record_title_2);
					break;
				case 2:
					txtCheck = (TextView) findViewById(R.id.txt_my_record_title_3);
					break;
				case 3:
					txtCheck = (TextView) findViewById(R.id.txt_my_record_title_4);
					break;
				case 4:
					txtCheck = (TextView) findViewById(R.id.txt_my_record_title_5);
					break;
				case 5:
					txtCheck = (TextView) findViewById(R.id.txt_my_record_title_6);
					break;
			}

			if (txtCheck != null && txtCheck.getText() == "") {
				if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotContentsMyRecordList true");
				return true;
			} else {
				if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotContentsMyRecordList false");
				return false;
			}
		}

		return true;
	}

	protected void CheckNotItemOnListenList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotItemOnListenList >");

		int iLastPageItemCount = mListenItems.size() % 8;
		int iListenFocus = 1;

		switch (remote.m_iListenListFocusX) {
			case 1:
				if (remote.m_iListenListFocusY == 1) {
					iListenFocus = 1;
				} else {
					iListenFocus = 5;
				}
				break;
			case 2:
				if (remote.m_iListenListFocusY == 1) {
					iListenFocus = 2;
				} else {
					iListenFocus = 6;
				}
				break;
			case 3:
				if (remote.m_iListenListFocusY == 1) {
					iListenFocus = 3;
				} else {
					iListenFocus = 7;
				}
				break;
			case 4:
				if (remote.m_iListenListFocusY == 1) {
					iListenFocus = 4;
				} else {
					iListenFocus = 8;
				}
				break;
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "current img_focus = " + String.valueOf(iListenFocus));

		if (iListenFocus > iLastPageItemCount) {
			iListenFocus = iLastPageItemCount;

			switch (iListenFocus) {
				case 1:
					remote.m_iListenListFocusY = 1;
					remote.m_iListenListFocusX = 1;
					break;
				case 2:
					remote.m_iListenListFocusY = 1;
					remote.m_iListenListFocusX = 2;
					break;
				case 3:
					remote.m_iListenListFocusY = 1;
					remote.m_iListenListFocusX = 3;
					break;
				case 4:
					remote.m_iListenListFocusY = 1;
					remote.m_iListenListFocusX = 4;
					break;
				case 5:
					remote.m_iListenListFocusY = 2;
					remote.m_iListenListFocusX = 1;
					break;
				case 6:
					remote.m_iListenListFocusY = 2;
					remote.m_iListenListFocusX = 2;
					break;
				case 7:
					remote.m_iListenListFocusY = 2;
					remote.m_iListenListFocusX = 3;
					break;
				case 8:
					remote.m_iListenListFocusY = 2;
					remote.m_iListenListFocusX = 4;
					break;
			}

			displayListListen(REMOTE_NONE);
			if (BuildConfig.DEBUG) Log.i(_toString(), "after img_focus = " + String.valueOf(iListenFocus));
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotItemOnListenList <");
	}

	protected boolean CheckNotContentsCustomerList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState());
		if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotContentsCustomerList >");
		if (BuildConfig.DEBUG) Log.i(_toString(), "m_iCustomerListFocus = " + String.valueOf(remote.m_iCustomerListFocus));

		if (remote.m_iState == STATE_CUSTOMER_LIST) {
			TextView txtCheck = null;

			if (remote.m_iCustomerListFocus == 6) {
				return true;
			}

			switch (remote.m_iCustomerListFocus) {
				case 1:
					txtCheck = (TextView) findViewById(R.id.txt_customer_title_2);
					break;
				case 2:
					txtCheck = (TextView) findViewById(R.id.txt_customer_title_3);
					break;
				case 3:
					txtCheck = (TextView) findViewById(R.id.txt_customer_title_4);
					break;
				case 4:
					txtCheck = (TextView) findViewById(R.id.txt_customer_title_5);
					break;
				case 5:
					txtCheck = (TextView) findViewById(R.id.txt_customer_title_6);
					break;
			}

			if (txtCheck != null) {
				if (txtCheck.getText() == "") {
					if (BuildConfig.DEBUG) Log.i(_toString(), "true");
					return true;
				} else {
					if (BuildConfig.DEBUG) Log.i(_toString(), "false");
					return false;
				}
			}
		}

		return true;
	}

	protected boolean CheckNotContentsSearchList() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "CheckNotContentsSearchList >");
		if (BuildConfig.DEBUG) Log.i(_toString(), "m_iSearchListFocus = " + String.valueOf(remote.m_iSearchListFocus));

		if (remote.m_iState == STATE_SEARCH_LIST) {
			TextView txtCheck = null;

			if (remote.m_iSearchSubMenuFocus == 1) {
				if (remote.m_iSearchListFocus == 5) {
					return true;
				}

				switch (remote.m_iSearchListFocus) {
					case 1:
						txtCheck = (TextView) findViewById(R.id.txt_search_self_number_2);
						break;
					case 2:
						txtCheck = (TextView) findViewById(R.id.txt_search_self_number_3);
						break;
					case 3:
						txtCheck = (TextView) findViewById(R.id.txt_search_self_number_4);
						break;
					case 4:
						txtCheck = (TextView) findViewById(R.id.txt_search_self_number_5);
						break;
				}
			} else {
				if (remote.m_iSearchListFocus == 4) {
					return true;
				}

				switch (remote.m_iSearchListFocus) {
					case 1:
						txtCheck = (TextView) findViewById(R.id.txt_search_letter_number_2);
						break;
					case 2:
						txtCheck = (TextView) findViewById(R.id.txt_search_letter_number_3);
						break;
					case 3:
						txtCheck = (TextView) findViewById(R.id.txt_search_letter_number_4);
						break;
				}
			}

			if (txtCheck.getText() == "") {
				if (BuildConfig.DEBUG) Log.i(_toString(), "true");
				return true;
			} else {
				if (BuildConfig.DEBUG) Log.i(_toString(), "false");
				return false;
			}
		}

		return true;
	}

	/**
	 * CODING DEAD S.3
	 *
	 * @see Main2X#showBottomGuideStartSong()
	 */
	protected void showBottomGuideStartSong() {
	}

	/**
	 * CODING DEAD S.3
	 *
	 * @see Main2X#showBottomGuideStopSong()
	 */
	protected void showBottomGuideStopSong() {
	}

	/**
	 * CODING DEAD S.3
	 *
	 * @see Main2X#showBottomGuideListenOther(String)
	 */
	protected void showBottomGuideListenOther(String method) {
	}

	/**
	 * CODING DEAD S.3
	 *
	 * @see Main2X#showBottomGuideTicketNone()
	 */
	protected void showBottomGuideTicketNone() {
	}

	/**
	 * CODING DEAD S.3
	 *
	 * @see Main2X#hideBottomGuide01()
	 */
	public void hideBottomGuide01() {
	}

	/**
	 * CODING DEAD S.3
	 *
	 * @see Main2X#hideBottomGuide02()
	 */
	public void hideBottomGuide02() {
	}

	protected void SetListenOtherFocus(int keyID) {
		if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + REMOTE_STATE.get(keyID));

		if (keyID != REMOTE_NONE) {
			if (remote.m_iListenOther != remote.m_iListenOtherBefore) {
				// 전체 페이지 수가 1 이상
				if (m_iTotalListenOtherListPage > 1) {
					if (keyID == REMOTE_RIGHT) {
						// 전체 페이지 수가 2
						if (m_iTotalListenOtherListPage == 2) {
							if (remote.m_iListenOther == 9) {
								remote.m_iListenOther = 1;
								remote.m_iListenOtherBefore = 8;
							}
						} else {
							// 9페이지 전
							if (m_iCurrentViewListenOtherListPage < 9) {
								// 마지막 페이지 -1 페이지 전
								if (m_iCurrentViewListenOtherListPage < m_iTotalListenOtherListPage - 1) {
									if (remote.m_iListenOther == 9) {
										remote.m_iListenOther = 1;
										remote.m_iListenOtherBefore = 8;

										m_iCurrentViewListenOtherListPage++;
										m_iCurrentListenOtherListPage++;

										// 페이지가 이동되었으니 아이템들을 갱신
										moveListenOtherPage();
									}
								} else {
									if (remote.m_iListenOther == 9) {
										remote.m_iListenOther = 8;
										remote.m_iListenOtherBefore = 8;
									}
								}
								// 9페이지
							} else {
								if (remote.m_iListenOther == 9) {
									remote.m_iListenOther = 8;
									remote.m_iListenOtherBefore = 8;
								}
							}
						}
					} else if (keyID == REMOTE_LEFT) {
						if (m_iCurrentViewListenOtherListPage > 1) {
							if (remote.m_iListenOther == 0) {
								remote.m_iListenOther = 8;
								remote.m_iListenOtherBefore = 1;

								// 이전 페이지가 있으면 페이지 이동
								m_iCurrentViewListenOtherListPage--;
								m_iCurrentListenOtherListPage--;

								// 페이지가 이동되었으니 아이템들을 갱신
								moveListenOtherPage();
							}
						} else {
							// 현재 1 페이지인데 - 전체가 2페이지
							if (m_iTotalListenOtherListPage == 2) {
								if (remote.m_iListenOther == 0) {
									remote.m_iListenOther = 8;
									remote.m_iListenOtherBefore = 1;
								}
								// 현재 1 페이지인데 - 전체가 3페이지 이상
							} else {
								if (remote.m_iListenOther == 0) {
									remote.m_iListenOther = 1;
									remote.m_iListenOtherBefore = 1;
								}
							}
						}
					}
					// 전체 페이지 수가 1
				} else {
					if (remote.m_iListenOther == 0 && keyID == REMOTE_LEFT) {
						remote.m_iListenOther = mListenOtherItemsRecordID.size();
						remote.m_iListenOtherBefore = 1;
					} else if (mListenOtherItemsRecordID.size() < remote.m_iListenOther && keyID == REMOTE_RIGHT) {
						// 없는 아이템으로 포커스를 이동하려고 한다
						if (m_iTotalListenOtherListPage == m_iCurrentViewListenOtherListPage) {
							remote.m_iListenOther = 1;
							remote.m_iListenOtherBefore = mListenOtherItemsRecordID.size();
						}
					}
				}

				showListenOtherFocus(remote.m_iListenOtherBefore, false);
			}
		}

		showListenOtherFocus(remote.m_iListenOther, true);
	}

	protected void showListenOtherFocus(int key, boolean focus) {
		try {
			String strColorFocus = "#FFFFFF";
			if (focus) {
				strColorFocus = "#00CA66";
			}

			LinearLayout layout = null;

			switch (key) {
				case 1:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_01);
					break;
				case 2:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_02);
					break;
				case 3:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_03);
					break;
				case 4:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_04);
					break;
				case 5:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_05);
					break;
				case 6:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_06);
					break;
				case 7:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_07);
					break;
				case 8:
					layout = (LinearLayout) findViewById(R.id.layout_listen_other_08);
					break;
			}

			if (layout != null) {
				LinearLayout layoutLeft = (LinearLayout) layout.findViewById(R.id.layout_listen_other_profile_focus_left);
				LinearLayout layoutRight = (LinearLayout) layout.findViewById(R.id.layout_listen_other_profile_focus_right);
				LinearLayout layoutUp = (LinearLayout) layout.findViewById(R.id.layout_listen_other_profile_focus_up);
				LinearLayout layoutDown = (LinearLayout) layout.findViewById(R.id.layout_listen_other_profile_focus_down);
				LinearLayout layoutImg = (LinearLayout) layout.findViewById(R.id.layout_listen_other_profile_focus_img);

				if (focus) {
					layoutLeft.setBackgroundColor(Color.parseColor(strColorFocus));
					layoutRight.setBackgroundColor(Color.parseColor(strColorFocus));
					layoutUp.setBackgroundColor(Color.parseColor(strColorFocus));
					layoutDown.setBackgroundColor(Color.parseColor(strColorFocus));
					layoutImg.setBackgroundColor(Color.parseColor(strColorFocus));
				} else {
					layoutLeft.setBackgroundColor(Color.TRANSPARENT);
					layoutRight.setBackgroundColor(Color.TRANSPARENT);
					layoutUp.setBackgroundColor(Color.TRANSPARENT);
					layoutDown.setBackgroundColor(Color.TRANSPARENT);
					layoutImg.setBackgroundColor(Color.parseColor(strColorFocus));
				}
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}
	}

	protected void ShowNumberSearch(int keyCode) {
		if (!m_bIsNumberSearch) {
			// LinearLayout layoutTopBG = (LinearLayout) findViewById(R.id.layout_top);
			// if (layoutTopBG != null) {
			// layoutTopBG.setBackgroundResource(R.drawable.common_top_bg_search);
			// }

			LinearLayout layoutTopCommon = (LinearLayout) findViewById(R.id.layout_top_common);
			if (layoutTopCommon != null) {
				layoutTopCommon.setVisibility(View.GONE);
			}

			LinearLayout layoutTopSearch = (LinearLayout) findViewById(R.id.layout_top_search);
			if (layoutTopSearch != null) {
				layoutTopSearch.setVisibility(View.VISIBLE);
			}
		}

		switch (keyCode) {
			case KeyEvent.KEYCODE_0:
				AddSearchNumber("0");
				break;
			case KeyEvent.KEYCODE_1:
				AddSearchNumber("1");
				break;
			case KeyEvent.KEYCODE_2:
				AddSearchNumber("2");
				break;
			case KeyEvent.KEYCODE_3:
				AddSearchNumber("3");
				break;
			case KeyEvent.KEYCODE_4:
				AddSearchNumber("4");
				break;
			case KeyEvent.KEYCODE_5:
				AddSearchNumber("5");
				break;
			case KeyEvent.KEYCODE_6:
				AddSearchNumber("6");
				break;
			case KeyEvent.KEYCODE_7:
				AddSearchNumber("7");
				break;
			case KeyEvent.KEYCODE_8:
				AddSearchNumber("8");
				break;
			case KeyEvent.KEYCODE_9:
				AddSearchNumber("9");
				break;
		}

		TextView txtSearchNumber = (TextView) findViewById(R.id.txt_top_search_number);
		if (txtSearchNumber != null) {

		}
		txtSearchNumber.setText(m_strInputNumber);

		removeNumberSearchZero(m_strInputNumber);

		KP(REQUEST_NUMBER_SEARCH, KP_0020, M1_MENU_SEARCH, M2_SEARCH_3);

		m_bIsNumberSearch = true;
	}

	protected void RemoveListenDisplay() {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (isListening()) {
			remote.m_iState = STATE_LISTEN_LIST;
		}

		removeListening();

		if (m_iPaneState != PANE_HOME) {
			removeView(m_layoutListenListFocus);
			m_layoutListenListFocus = null;
		}

		// hideBottomGuide01();

		stopListen();
	}

	private void removeNumberSearchZero(String num) {
		String strNew = "";
		String strGet = "";
		int i = 0;
		boolean bZero = true;

		while (i < 5) {
			// 0이 아닌 걸 찾아라
			strGet = m_strInputNumber.substring(i, i + 1);

			if (bZero) {
				if (!strGet.equals("0")) {
					bZero = false;
				}
			}

			if (!bZero) {
				strNew = strNew + m_strInputNumber.substring(i, i + 1);
			}

			i++;
		}

		m_strRequestInputNumber = strNew;

		if (BuildConfig.DEBUG) Log.i(_toString(), "m_strRequestInputNumber = " + m_strRequestInputNumber);
	}

	protected void HideNumberSearch() {
		// LinearLayout layoutTopBG = (LinearLayout) findViewById(R.id.layout_top);
		// if (layoutTopBG != null) {
		// layoutTopBG.setBackgroundResource(R.drawable.common_top_bg);
		// }

		LinearLayout layoutTopCommon = (LinearLayout) findViewById(R.id.layout_top_common);
		if (layoutTopCommon != null) {
			layoutTopCommon.setVisibility(View.VISIBLE);
		}

		LinearLayout layoutTopSearch = (LinearLayout) findViewById(R.id.layout_top_search);
		if (layoutTopSearch != null) {
			layoutTopSearch.setVisibility(View.GONE);
		}

		m_strInputNumber = "00000";
		m_bHaveNumberSearchResult = false;
		m_bIsNumberSearch = false;
	}

	private void AddSearchNumber(String num) {
		String strNew = "";
		int i = 1;

		while (i < 5) {
			strNew = strNew + m_strInputNumber.substring(i, i + 1);
			i++;
		}

		strNew = strNew + num;
		m_strInputNumber = strNew;

		if (BuildConfig.DEBUG) Log.i(_toString(), "m_strInputNumber = " + m_strInputNumber);
	}

	protected void ShowNumberSearchResult(String result) {
		TextView txtSearchNumber = (TextView) findViewById(R.id.txt_top_search_item);
		txtSearchNumber.setText(result);
		setTextViewMarquee(txtSearchNumber, true);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
	}

	protected void removeNullSpace() {
		if (BuildConfig.DEBUG) Log.i(_toString(), "removeNullSpace = \"" + m_strSearchWord + "\"");

		// 먼헛짓이냐.
		// String strGet = "";
		// String strNew = "";
		// int i = 0;
		//
		// while (i < m_strSearchWord.length()) {
		// // 공백을 찾아라
		// strGet = m_strSearchWord.substring(i, i + 1);
		//
		// if (!strGet.equals(" ")) {
		// strNew = strNew + strGet;
		// }
		//
		// i++;
		// }
		//
		// m_strSearchWord = strNew;
		m_strSearchWord = TextUtil.trim(m_strSearchWord);

		if (BuildConfig.DEBUG) Log.i(_toString(), "removeNullSpace = \"" + m_strSearchWord + "\"");
	}

	protected _KPRequest KP(_KPRequest KP) {
		return null;
	}

	_PlayView player;

	public _PlayView getPlayer() {
		return player;
	}

	protected void setPlayer() {
	}

	protected void setVideo() {
	}

	protected void setListen() {
	}

	int KP_REQUEST;
	String OP;
	String M1;
	String M2;

	/**
	 * @see Main2#KP(int, String, String, String)
	 * @see Main2X#KP(int, String, String, String)
	 * @see Main3X#KP(int, String, String, String)
	 * @see Main3XXX#KP(int, String, String, String)
	 * @see Main3XXXXX#VASSPPX(COMPLETE_VASS)
	 */
	public void KP(int request, String OP, String M1, String M2) {
		if (BuildConfig.DEBUG) Log.wtf("[KP]" + _toString(), getMethodName() + "[ST]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);

		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "KP >");
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "OP=" + OP + "/M1=" + M1 + "/M2=" + M2);
		//if (KP_REQUEST != REQUEST_DAY_PURCHASE_COMPLETE) {
		//	startTaskShowMessageNotResponse();
		//}

		/**
		 * 전문초기화
		 */
		KP = KP(KP);
		_KP_1012 = KP(_KP_1012);

		/**
		 * 전문URL설정
		 */
		switch (request) {
			case REQUEST_MAIN:
				KP.setMainUrl(OP, M1, M2);
				break;
			case REQUEST_MY_SUB_MENU:
				KP.setMyMenuUrl();
				break;
			case REQUEST_SHOP_SUB_MENU:
				KP.setShopMenuUrl();
				break;
			case REQUEST_SONG_LIST:
				KP.setSongListUrl(OP, M1, M2, m_iRequestPage);
				break;
			case REQUEST_FAVOR:
				KP.setFavorUrl(OP, M1, M2, m_strRequestFavorSongID);
				break;
			case REQUEST_LISTEN_LIST:
				KP.setListenListUrl(OP, M1, M2, m_iRequestPage);
				break;
			case REQUEST_CUSTOMER_LIST:
				KP.setCustomerListUrl(OP, M1, M2, m_iRequestPage);
				break;
			case REQUEST_EVENT_LIST:
				KP.setCustomerListUrl(OP, M1, M2, m_iRequestPage);
				break;
			case REQUEST_EVENT_LIST_DETAIL:
			case REQUEST_CUSTOMER_LIST_DETAIL:
				KP.setCustomerListDetailUrl(OP, M1, M2, m_strRequestCustomerDetailID);
				break;
			case REQUEST_SEARCH_LIST:
				removeNullSpace();
				try {
					KP.setSearchListUrl(OP, M1, M2, URLEncoder.encode(m_strSearchWord, "UTF-8"), m_iRequestSearchListPage);
				} catch (Exception e) {
					if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
					KP.setSearchListUrl(OP, M1, M2, m_strSearchWord, m_iRequestSearchListPage);
				}
				break;
			case REQUEST_SONG_PLAY:
				VASS(REQUEST_VASS.REQUEST_VASS_PPX_CHECK_PLAY);
				break;
			case REQUEST_SONG_PLAYED_TIME:
				_KP_1012.setSongPlayedTimeUrl(OP, M1, M2, m_strRequestPlaySongID, type);
				break;
			case REQUEST_LISTEN_SONG:
			case REQUEST_LISTEN_SONG_OTHER:
				if (remote.m_iMenuMainFocus == 3) {
					KP.setListenSongUrl(OP, M1, M2, record_id, p_mid);
				} else {
					KP.setListenSongUrl(OP, M1, M2, record_id, "");
				}
				break;
			case REQUEST_LISTEN_PLAYED_TIME:
				_KP_1012.setSongPlayedTimeUrl(OP, M1, M2, record_id, "N");
				break;
			case REQUEST_LISTEN_OTHER:
				KP.setListenOtherUrl(OP, m_strListeningSongID, m_iListenOtherRequestPage);
				break;
			case REQUEST_NUMBER_SEARCH:
				KP.setNumberSearchUrl(OP, M1, M2, m_strRequestInputNumber);
				break;
			case REQUEST_AUTH_NUMBER:
				KP.setAuthNumberUrl(OP, m_strHPNumber, "");
				break;
			case REQUEST_CERTIFY_STATE:
				KP.setCertifyStateUrl(OP);
				break;
			case REQUEST_AUTH_NUMBER_CORRECT:
				KP.setAuthNumberCorrectUrl(OP, m_strHPNumber, mStrCertifyNumber);
				break;
			case REQUEST_MY_RECORD_LIST:
				KP.setListenListUrl(OP, M1, M2, m_iRequestPage);
				break;
			case REQUEST_TICKET_PURCHASE_COMPLETE:
				break;
			case REQUEST_TICKET_SALES_STATE:
				KP.setTicketSalesStateUrl(KP_4002);
				break;
			case REQUEST_EVENT_APPLY:
				KP.setEventApply(m_strRequestCustomerDetailID);
				break;
			case REQUEST_EVENT_HP:
				KP.setEventHP(m_strRequestCustomerDetailID, m_strHPNumber);
				break;
			case REQUEST_COUPON_REGIST:
				KP.setCouponRegistUrl(m_strCouponSerial);
				break;
		}

		KP.setRequestType(request);
		_KP_1012.setRequestType(request);

		/**
		 * 로딩처리
		 */
		switch (request) {
			case REQUEST_SONG_PLAYED_TIME:
			case REQUEST_LISTEN_PLAYED_TIME:
			case REQUEST_TICKET_PURCHASE_COMPLETE:
				break;
			default:
				//if (KP_REQUEST != REQUEST_SONG_PLAYED_TIME && KP_REQUEST != REQUEST_LISTEN_PLAYED_TIME && KP_REQUEST != REQUEST_DAY_PURCHASE_COMPLETE && KP_REQUEST != REQUEST_MONTH_PURCHASE_COMPLETE)
			{
				int time;
				if (request == REQUEST_SONG_PLAY) {
					if (getPlayer().isPitchTempo()) {
						time = LOADING_SING;
					} else {
						time = LOADING_LONG;
					}
				} else if (request == REQUEST_LISTEN_SONG) {
					if (getListen().isPitchTempo()) {
						time = LOADING_LISTEN;
					} else {
						time = LOADING_LONG;
					}
				} else {
					time = LOADING_SHORT;
				}
				startLoading(getMethodName() + REQUEST_KP.get(request), time);
				}
			break;
		}

		if (request == REQUEST_SONG_PLAY) {
			if (BuildConfig.DEBUG) Log.w(_toString(), getMethodName() + "[RQ]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
			return;
		}

		/**
		 * 전문스타토!!!
		 */
		switch (request) {
			case REQUEST_SONG_PLAYED_TIME:
			case REQUEST_LISTEN_PLAYED_TIME:
				_KP_1012.start();
				break;
			case REQUEST_TICKET_PURCHASE_COMPLETE:
				break;
			default:
				KP.start();
				break;
		}

		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "KP <");

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + REQUEST_KP.get(request) + ", " + OP + ", " + M1 + ", " + M2);
	}

	public String getEngageSong() {
		if (song_ids.size() != 0) {
			return song_ids.get(0);
		}

		return "";
	}

	protected void addEngageSong(String song_id) {
		song_ids.add(song_id);

		TextView textEngageCount = (TextView) findViewById(R.id.txt_top_engage);
		TextView textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);

		//if (m_iPaneState == PANE_HOME) {
		//	textEngageCount = (TextView) findViewById(R.id.txt_top_engage);
		//	textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		//} else {
		//	textEngageCount = (TextView) findViewById(R.id.txt_top_engage);
		//	textEngageList = (TextView) findViewById(R.id.txt_top_engage_list);
		//}

		int iSize = song_ids.size();
		String strCount = "";

		if (iSize < 10) {
			strCount = "0";
		}

		strCount = strCount + Integer.toString(iSize);

		textEngageCount.setText("예약곡 " + strCount);

		String strEngageList = song_ids.get(0);
		int iMakeCount = 1;
		while (iMakeCount < song_ids.size()) {
			strEngageList = strEngageList + "  " + song_ids.get(iMakeCount);

			iMakeCount++;

			if (iMakeCount == 5) {
				break;
			}
		}

		textEngageList.setText(strEngageList);
	}

	/**
	 * <pre>
	 * 븅신개삽질한다.
	 * </pre>
	 */
	public int getFocusedListenListItemIndex() {
		//int iIndex = 0;
		//
		//switch (remote.m_iListenListFocusX) {
		//	case 1:
		//		if (remote.m_iListenListFocusY == 1) {
		//			iIndex = 0;
		//		} else {
		//			iIndex = 4;
		//		}
		//		break;
		//	case 2:
		//		if (remote.m_iListenListFocusY == 1) {
		//			iIndex = 1;
		//		} else {
		//			iIndex = 5;
		//		}
		//		break;
		//	case 3:
		//		if (remote.m_iListenListFocusY == 1) {
		//			iIndex = 2;
		//		} else {
		//			iIndex = 6;
		//		}
		//		break;
		//	case 4:
		//		if (remote.m_iListenListFocusY == 1) {
		//			iIndex = 3;
		//		} else {
		//			iIndex = 7;
		//		}
		//		break;
		//}
		//
		//iIndex = iIndex + m_iListenItemCount - 8;
		//
		//return iIndex;

		int index = 0;

		if (remote.m_iMenuMainFocus == 3) {
			//마이노래방->녹음곡
			//index = getFocusedMyRecordListIndex();
			int idx = remote.m_iSongListFocus - 1;
			index = (idx + (m_iCurrentListenListPage - 1) * 6);
			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + index + ":" + remote.m_iSongListFocus + ":" + m_iListenItemCount + "-" + m_iCurrentListenListPage + "/" + m_iTotalListenListPage);
		} else {
			//녹음곡감상
			if (remote.m_iListenListFocusY > 0) {
				int idx = (remote.m_iListenListFocusX + ((remote.m_iListenListFocusY - 1) * 4) - 1);
				index = (idx + (m_iCurrentListenListPage - 1) * 8);
			}
			if (BuildConfig.DEBUG) Log.wtf(_toString(), getMethodName() + index + ":" + remote.m_iListenListFocusX + "," + remote.m_iListenListFocusY + ":" + m_iListenItemCount + "-" + m_iCurrentListenListPage + "/" + m_iTotalListenListPage);
		}

		return index;
	}

	public int getFocusedMyRecordListIndex() {
		int iIndex = 0;

		iIndex = remote.m_iSongListFocus - 1;

		iIndex = iIndex + m_iListenItemCount - 6;

		return iIndex;
	}

	public void setIMELocation() {
		Intent imeIntent = new Intent("com.dasan.keyboard.SET_LAYOUT");
		Bundle extras = new Bundle();
		int height1 = m_iDisplayHeight / 3;
		int height2 = height1 / 4;
		int width1 = m_iDisplayWidth / 5;
		int width2 = width1 / 10;
		extras.putInt("top", m_iDisplayHeight - height1 - height2);
		extras.putInt("left", m_iDisplayWidth - width1 - width2);
		imeIntent.putExtras(extras);
		sendBroadcast(imeIntent);
		mHandler.sendEmptyMessageDelayed(IME_SHOW, 100);
	}

	Handler.Callback mCallbackEditSearchSelf = new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			EditText v = null;

			switch (msg.what) {
				case IME_SHOW:
					v = (EditText) findViewById(R.id.edit_search_self_word);
					showSoftKeyboard(v);
					break;
			}

			return false;
		}
	};

	Handler mHandler = new Handler(mCallbackEditSearchSelf);

	protected boolean isFavor(int focus) {
		if (mSongFavors.size() >= focus) {
			focus = focus - 1;
			if (mSongFavors.get(focus).equals("Y")) {
				return true;
			}
		}

		return false;
	}

	protected boolean isPlaying() {
		boolean ret = (player != null && player.isPlaying());
		//if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ret);
		return ret;
	}

	public boolean isListening() {
		boolean ret = (listen != null && listen.isPlaying());
		//if (BuildConfig.DEBUG) Log.d(_toString(), getMethodName() + ret);
		return ret;
	}

	private Bitmap GetBitmapClippedCircle(Bitmap bitmap) {
		Bitmap output;

		if (bitmap.getWidth() > bitmap.getHeight()) {
			output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Config.ARGB_8888);
		} else {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		float r = 0;

		if (bitmap.getWidth() > bitmap.getHeight()) {
			r = bitmap.getHeight() / 2;
		} else {
			r = bitmap.getWidth() / 2;
		}

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(r, r, r, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	protected String getSongIDFromSingList() {
		// TextView textCurrent = null;
		//
		// switch (remote.m_iSongListFocus) {
		// case 1:
		// textCurrent = txtNumber1;
		// break;
		// case 2:
		// textCurrent = txtNumber2;
		// break;
		// case 3:
		// textCurrent = txtNumber3;
		// break;
		// case 4:
		// textCurrent = txtNumber4;
		// break;
		// case 5:
		// textCurrent = txtNumber5;
		// break;
		// case 6:
		// textCurrent = txtNumber6;
		// break;
		// }

		int index = remote.m_iSongListFocus - 1;
		TextView textCurrent = sing_line.get(index).txt_number;

		if (textCurrent != null) {
			return (String) textCurrent.getText();
		} else {
			return null;
		}
	}

	private String getSongIDFromSearchList() {
		TextView textCurrent = null;

		switch (remote.m_iSearchListFocus) {
			case 1:
				if (remote.m_iSearchSubMenuFocus == 1) {
					textCurrent = (TextView) findViewById(R.id.txt_search_self_number_1);
				} else {
					textCurrent = (TextView) findViewById(R.id.txt_search_letter_number_1);
				}
				break;
			case 2:
				if (remote.m_iSearchSubMenuFocus == 1) {
					textCurrent = (TextView) findViewById(R.id.txt_search_self_number_2);
				} else {
					textCurrent = (TextView) findViewById(R.id.txt_search_letter_number_2);
				}
				break;
			case 3:
				if (remote.m_iSearchSubMenuFocus == 1) {
					textCurrent = (TextView) findViewById(R.id.txt_search_self_number_3);
				} else {
					textCurrent = (TextView) findViewById(R.id.txt_search_letter_number_3);
				}
				break;
			case 4:
				if (remote.m_iSearchSubMenuFocus == 1) {
					textCurrent = (TextView) findViewById(R.id.txt_search_self_number_4);
				} else {
					textCurrent = (TextView) findViewById(R.id.txt_search_letter_number_4);
				}
				break;
			case 5:
				textCurrent = (TextView) findViewById(R.id.txt_search_self_number_5);
				break;
		}

		return (String) textCurrent.getText();
	}

	protected View addPopup(int resource, int id) {
		View popup = inflate(resource, null);
		if (popup != null) {
			if (id > 0) {
				popup.setId(id);
			}
			ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			addPopup(popup, param);
			popup.bringToFront();
		}
		return popup;
	}

	protected View addPopup(int resource) {
		return addPopup(resource, 0);
	}

	private void addMessageCommon(int close) {
		LinearLayout message_common = null;

		try {
			if (close == CLOSE_AUTO_MID_BOTTOMRIGHT) {
				if (findViewById(R.id.message_common_bottomright) == null) {
					//message_common = (LinearLayout) inflate(R.layout.message_common_bottomright, null);
					//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					//addContentView(message_common, param);
					message_common = (LinearLayout) addPopup(R.layout.message_common_bottomright);
				}
				message_common = (LinearLayout) findViewById(R.id.message_common_bottomright);
			} else {
				if (findViewById(R.id.message_common) == null) {
					//message_common = (LinearLayout) inflate(R.layout.message_common, null);
					//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					//addContentView(message_common, param);
					message_common = (LinearLayout) addPopup(R.layout.message_common);
				}
				message_common = (LinearLayout) findViewById(R.id.message_common);
			}
		} catch (Exception e) {
			if (BuildConfig.DEBUG) _LOG(getMethodName(), e);
		}

		this.message_common = message_common;
	}

	/**
	 * <pre>이런개시발원래닫히는걸머하러...</pre>
	 */
	protected void ShowMessageCommon(int close, String title, String message) {
		addMessageCommon(close);

		if (message_common != null) {
			message_common.setVisibility(View.VISIBLE);
		}

		TextView textTitle = null;
		TextView textMessage = null;

		if (close == CLOSE_AUTO || close == CLOSE_AUTO_LONG) {
			textTitle = (TextView) findViewById(R.id.btn_message_common_title);
			textMessage = (TextView) findViewById(R.id.btn_message_common_message);
		} else {
			textTitle = (TextView) findViewById(R.id.btn_message_common_bottomright_title);
			textMessage = (TextView) findViewById(R.id.btn_message_common_bottomright_message);
		}

		textTitle.setText(title);
		textMessage.setText(message);

		//if (close == CLOSE_AUTO) {
		//	m_timerHideMessageCommon = new Timer();
		//	TaskHideMessageCommon hideTask = new TaskHideMessageCommon();
		//	m_timerHideMessageCommon.schedule(hideTask, TIMER_CLOSE_AUTO);
		//} else if (close == CLOSE_AUTO_LONG) {
		//	m_timerHideMessageCommon = new Timer();
		//	TaskHideMessageCommon hideTask = new TaskHideMessageCommon();
		//	m_timerHideMessageCommon.schedule(hideTask, TIMER_CLOSE_AUTO_LONG);
		//} else if (close == CLOSE_AUTO_MID_BOTTOMRIGHT) {
		//	m_timerHideMessageCommon = new Timer();
		//	TaskHideMessageCommon hideTask = new TaskHideMessageCommon();
		//	m_timerHideMessageCommon.schedule(hideTask, TIMER_CLOSE_AUTO);
		//} else {
		//	m_timerHideMessageCommon = new Timer();
		//	TaskHideMessageCommon hideTask = new TaskHideMessageCommon();
		//	m_timerHideMessageCommon.schedule(hideTask, TIMER_CLOSE_AUTO);
		//}

		long delay = TIMER_CLOSE_AUTO;

		if (close == CLOSE_AUTO_LONG) {
			delay = TIMER_CLOSE_AUTO_LONG;
		}

		if (m_timerHideMessageCommon != null) {
			m_timerHideMessageCommon.cancel();
			m_timerHideMessageCommon.purge();
			m_timerHideMessageCommon = null;
		}

		m_timerHideMessageCommon = new Timer();
		TaskHideMessageCommon hideTask = new TaskHideMessageCommon();
		m_timerHideMessageCommon.schedule(hideTask, delay);
	}

	/**
	 * @see Main2X#HideMessageCommon()
	 */
	@Deprecated
	protected void HideMessageCommon() {
	}

	protected void ShowMessageNotResponse(String title, String message) {
		if (mPopupLoading == null) {
			return;
		}

		// _LOG(CLASS, "ShowMessageNotResponse");

		stopLoading(getMethodName());

		ShowMessageCommon(CLOSE_AUTO_LONG, title, message);

		m_timerHideMessageCommon = new Timer();

		TaskHideMessageCommon hideTask = new TaskHideMessageCommon();

		m_timerHideMessageCommon.schedule(hideTask, 2000);
	}

	protected boolean isShowMessageOkCancel() {
		return (message_okcancel != null && message_okcancel.getVisibility() == View.VISIBLE);
	}

	protected void ShowMessageExit(int state) {
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ST]" + MENU_STATE.get(state) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
		if (state == STATE_HOME_MENU) {
			m_bIsGoToPurchaseMessage = false;
			String title = getString(R.string.common_exit);
			String message = getString(R.string.exit_app);
			// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (message_okcancel == null) {
				//message_okcancel = (LinearLayout) inflate(R.layout.message_okcancel, null);
				//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				//addContentView(message_okcancel, param);
				message_okcancel = (LinearLayout) addPopup(R.layout.message_okcancel);
			}

			if (message_okcancel != null) {
				message_okcancel.setVisibility(View.VISIBLE);
			}

			message_okcancel.bringToFront();

			TextView textTitle = (TextView) findViewById(R.id.txt_message_okcancel_title);
			TextView textMessage = (TextView) findViewById(R.id.txt_message_okcancel_message);

			textTitle.setText(title);
			textMessage.setText(message);


			stopLoading(getMethodName());

			m_iMessageOkCancelFocusX = POPUP_CANCEL;

			setSelectedMessageOkCancel(KeyEvent.KEYCODE_DPAD_CENTER);

			// if (BuildConfig.DEBUG)
			{
				setBottomProductText(message);
			}
		}
		if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[ED]" + MENU_STATE.get(state) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState));
	}

	protected void ShowMessageOkCancel(String title, String message) {
		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (message_okcancel == null) {
			//message_okcancel = (LinearLayout) inflate(R.layout.message_okcancel, null);
			//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			//addContentView(message_okcancel, param);
			message_okcancel = (LinearLayout) addPopup(R.layout.message_okcancel);
		}

		if (message_okcancel != null) {
			message_okcancel.setVisibility(View.VISIBLE);
		}

		message_okcancel.bringToFront();

		TextView textTitle = (TextView) findViewById(R.id.txt_message_okcancel_title);
		TextView textMessage = (TextView) findViewById(R.id.txt_message_okcancel_message);

		textTitle.setText(title);
		textMessage.setText(message);
	}

	protected void HideMessageOkCancel() {
		// if (message_okcancel != null) {
		// ((ViewManager) message_okcancel.getParent()).removeView(message_okcancel);
		// message_okcancel = null;
		// m_iMessageOkCancelFocusX = POPUP_CANCEL;
		// }
		removeView(message_okcancel);
		message_okcancel = null;
		m_iMessageOkCancelFocusX = POPUP_CANCEL;
		message_okcancel = null;
	}

	/**
	 * @see Main2XXXX#setSelectedMessageOkCancel(int)
	 */
	protected void setSelectedMessageOkCancel(int keyCode) {
	}

	class TaskHideMessageCommon extends TimerTask {
		@Override
		public void run() {
			// sendMessage(COMPLETE_TIMER_HIDE_MESSAGE_COMMON);
			handlerKP.removeCallbacks(hideMessageCommon);
			handlerKP.post(hideMessageCommon);
		}
	}

	Runnable hideMessageCommon = new Runnable() {

		@Override
		public void run() {

			HideMessageCommon();
		}
	};

	class TaskShowMessageNotResponse extends TimerTask {
		@Override
		public void run() {
			sendMessage(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE);
		}
	}

	protected void startTaskShowMessageNotResponse() {
		m_timerShowMessageNotResponse = new Timer();
		m_taskShowMessageNotResponse = new TaskShowMessageNotResponse();
		m_timerShowMessageNotResponse.schedule(m_taskShowMessageNotResponse, TIMEOUT_NOT_RESPONSE);
	}

	protected void stopTaskShowMessageNotResponse() {
		if (m_timerShowMessageNotResponse != null) {
			m_timerShowMessageNotResponse.cancel();
			m_timerShowMessageNotResponse.purge();
			m_timerShowMessageNotResponse = null;
		}

		if (m_taskShowMessageNotResponse != null) {
			m_taskShowMessageNotResponse.cancel();
			m_taskShowMessageNotResponse = null;
		}
	}

	protected int PixelFromDP(int pixel) {
		float density = getApplicationContext().getResources().getDisplayMetrics().density;
		return (int) (pixel * density + 0.5f);
	}

	@SuppressWarnings("unused")
	private String GetMacAddress() {
		WifiManager mngr = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifi = mngr.getConnectionInfo();
		String addrMAC = wifi.getMacAddress();

		return addrMAC;
	}

	protected void SetTopNumber(String num) {
		TextView txtSongNumber = (TextView) findViewById(R.id.txt_top_song_number);
		txtSongNumber.setText(num);
	}

	private void SetCertifyState() {
		TextView txtCertifyStatePhone = (TextView) findViewById(R.id.txt_certify_state_phone);
		TextView txtCertifyStateDate = (TextView) findViewById(R.id.txt_certify_state_date);
		TextView txtCertifyStateMark = (TextView) findViewById(R.id.txt_certify_state_mark);
		TextView txtCertifyStateModify = (TextView) findViewById(R.id.txt_certify_state_modify);

		auth_phoneno = readKaraoke();
		auth_phoneno = checkKaraoke();

		txtCertifyStatePhone.setText(auth_phoneno);
		txtCertifyStateDate.setText(KP.auth_date);
		txtCertifyStateMark.setText("- 인증번호 변경은 월 " + KP.auth_mark_auth_idx + "회에 한해 가능합니다.");

		int iModifyCount = Integer.valueOf(KP.auth_modify_idx);
		if (iModifyCount <= 0) {
			txtCertifyStateModify.setText("- 고객님의 변경 가능 횟수는 0회 입니다.");
		} else {
			txtCertifyStateModify.setText("- 고객님의 변경 가능 횟수는 " + KP.auth_modify_idx + "회 입니다.");
		}

		m_imgCertifyProfile = (ImageView) findViewById(R.id.img_certify_profile);
		//if (util_certifyProfile != null) {
		//	if (util_certifyProfile.m_bitMap != null) {
		//		util_certifyProfile.m_bitMap.recycle();
		//	}
		//}
		//util_certifyProfile = new _Util(handlerKP);
		//util_certifyProfile.setUtilType(REQUEST_UTIL_CERTIFY_PROFILE_IMAGE);
		//util_certifyProfile.setImageUrl(KP.auth_url_profile);
		//util_certifyProfile.start();
		putURLImage(m_imgCertifyProfile, KP.auth_url_profile, false);
	}

	public void ShowScore() {
		// if (m_layoutScore != null) {
		// ((ViewManager) m_layoutScore.getParent()).removeView(m_layoutScore);
		// m_layoutScore = null;
		// }
		removeView(m_layoutScore);
		m_layoutScore = null;

		// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		m_layoutScore = (LinearLayout) inflate(R.layout.score, null);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		addContentView(m_layoutScore, param);

		String scoreTemp1 = MakeRandomScore(5, 9);
		String scoreTemp2 = MakeRandomScore(0, 9);

		String score1 = scoreTemp1.substring(0, 1);
		String score2 = scoreTemp2.substring(0, 1);

		if (BuildConfig.DEBUG) Log.i(_toString(), "SCORE 1 = " + score1);
		if (BuildConfig.DEBUG) Log.i(_toString(), "SCORE 2 = " + score2);

		ImageView imgScore01 = (ImageView) findViewById(R.id.img_score_01);
		imgScore01.setImageResource(GetScoreImage(score1));

		ImageView imgScore02 = (ImageView) findViewById(R.id.img_score_02);
		imgScore02.setImageResource(GetScoreImage(score2));

		ImageView imgScoreComment = (ImageView) findViewById(R.id.img_score_comment);

		int s = Integer.parseInt(score1);
		switch (s) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				imgScoreComment.setImageResource(R.drawable.play_comment_50);
				break;
			case 6:
				imgScoreComment.setImageResource(R.drawable.play_comment_60);
				break;
			case 7:
				imgScoreComment.setImageResource(R.drawable.play_comment_70);
				break;
			case 8:
				imgScoreComment.setImageResource(R.drawable.play_comment_80);
				break;
			case 9:
				imgScoreComment.setImageResource(R.drawable.play_comment_9);
				break;
		}

		// String strNextSong = getEngageSong();
		// if (!strNextSong.equals("")) {
		// m_strRequestPlaySongID = strNextSong;

		m_timerHideScore = new Timer();

		TaskHideScore hideTask = new TaskHideScore();

		m_timerHideScore.schedule(hideTask, 3000);
		// }
	}

	public void HideScore() {
		// if (m_layoutScore != null) {
		// ((ViewManager) m_layoutScore.getParent()).removeView(m_layoutScore);
		// m_layoutScore = null;
		// }
		removeView(m_layoutScore);
		m_layoutScore = null;
		// 다음곡유무확인
		if (!TextUtil.isEmpty(getEngageSong())) {
			startSing(null);
		}
		// 이븅신새끼하는짓좀보소
		// String strNextSong = getEngageSong();
		// if (!strNextSong.equals("")) {
		// // 점수를 제거한 뒤 예약곡 재생
		// // 140422 예약된 반주곡을 자동 시작할 때는 중지 처리가 완전히 끝난 뒤에 새 요청 시작 (점수 제거 후)
		// m_strRequestPlaySongID = strNextSong;
		// m_timerStartNextSong = new Timer();
		// TaskStartSingNext startTask = new TaskStartSingNext();
		//
		// StartLoading(LOADING_LONG);
		// m_timerStartNextSong.schedule(startTask, 4000);
		// }
	}

	class TaskHideScore extends TimerTask {
		@Override
		public void run() {
			sendMessage(COMPLETE_TIMER_HIDE_SCORE);
		}
	}

	private int GetScoreImage(String s) {
		int score = Integer.parseInt(s);

		int d = 0;

		switch (score) {
			case 0:
				d = R.drawable.play_score_0;
				break;
			case 1:
				d = R.drawable.play_score_1;
				break;
			case 2:
				d = R.drawable.play_score_2;
				break;
			case 3:
				d = R.drawable.play_score_3;
				break;
			case 4:
				d = R.drawable.play_score_4;
				break;
			case 5:
				d = R.drawable.play_score_5;
				break;
			case 6:
				d = R.drawable.play_score_6;
				break;
			case 7:
				d = R.drawable.play_score_7;
				break;
			case 8:
				d = R.drawable.play_score_8;
				break;
			case 9:
				d = R.drawable.play_score_9;
				break;
		}

		return d;
	}

	private String MakeRandomScore(int s, int e) {
		return String.valueOf((Math.floor(Math.random() * (e - s + 1)) + s));
	}

	/**
	 * @see Main2X#writeKaraoke(String)
	 */
	@Deprecated
	protected String writeKaraoke(String line) {
		// String noteName = "kykaraoke.txt";
		//
		// if (line != null) {
		// try {
		// OutputStreamWriter out = new OutputStreamWriter(openFileOutput(noteName, 0));
		//
		// out.write(line);
		// out.close();
		// } catch (FileNotFoundException e) {
		// if (BuildConfig.DEBUG)  _LOG(getMethodName(), e);
		// } catch (IOException e) {
		// if (BuildConfig.DEBUG)  _LOG(getMethodName(), e);
		// }
		// }
		return line;
	}

	/**
	 * @see Main2X#readKaraoke()
	 */
	@Deprecated
	// @SuppressLint("SdCardPath")
	protected String readKaraoke() {
		// final String PATH = "/data/data/kr.kymedia.kykaraoke/files/";
		//
		// String fileName = "kykaraoke.txt";
		// boolean filecheck = false;
		// File iFile = new File(PATH);
		// if (!iFile.exists()) {
		// m_strCertifyedHPNumber = "00000000000";
		// return "00000000000";
		// }
		//
		// File[] fList2 = iFile.listFiles();
		//
		// for (int i = 0; i < fList2.length; i++) {
		// String searchFileName = fList2[i].getName();
		//
		// if (fileName.equals(searchFileName)) {
		// filecheck = true;
		// }
		// }
		//
		// if (filecheck == true) {
		// try {
		// InputStream in = openFileInput(fileName);
		//
		// if (in != null) {
		// InputStreamReader tmp = new InputStreamReader(in);
		// BufferedReader reader = new BufferedReader(tmp);
		// String str;
		// StringBuffer buf = new StringBuffer();
		//
		// try {
		// while ((str = reader.readLine()) != null) {
		// buf.append(str);
		// }
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		//
		// try {
		// in.close();
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "READ TEXT IS : " + buf.toString());
		// m_strCertifyedHPNumber = buf.toString();
		// return buf.toString();
		// } catch (IOException e) {
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "READ TEXT IS : " + buf.toString());
		// if (BuildConfig.DEBUG)  _LOG(getMethodName(), e);
		// }
		// }
		// } catch (FileNotFoundException e) {
		// if (BuildConfig.DEBUG)  _LOG(getMethodName(), e);
		// }
		// }
		//
		// m_strCertifyedHPNumber = "00000000000";
		// return "00000000000";
		return null;
	}

	/**
	 * @see Main2X#checkKaraoke()
	 */
	protected String checkKaraoke() {
		return null;
	}

	public void StartPlayActivity() {
		// HideLoading();
		//
		// Bundle bundle = new Bundle();
		// bundle.putInt(KEY_ACTIVITY, ACTIVITY_PLAYER);
		// bundle.putStringArrayList(SONGPLAYER_SKYM, arrStartPlayList);
		// Intent intent = new Intent(getApplicationContext(), Play.class);
		// if (bundle != null) {
		// intent.putExtras(bundle);
		// }
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(intent);
	}

	public void sendMessage(int state) {
		Bundle b = new Bundle();
		b.putInt("state", state);

		Message msg = handlerKP.obtainMessage();
		msg.setData(b);
		handlerKP.sendMessage(msg);
	}

	final Handler handlerKP = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			KP(msg);
		}
	};

	/**
	 * <pre>
	 * 전문조회실패
	 *  응답오류
	 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
	 *  전문결과표시
	 *    결과없음("result_code": "00901")
	 * </pre>
	 *
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(int, String, String, String)
	 * @see kr.kymedia.kykaraoke.tv.Main2#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3X#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXX#KP(android.os.Message)
	 * @see kr.kymedia.kykaraoke.tv.Main3XXXXX#KP(android.os.Message)
	 */
	protected void KP(Message msg) {
		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ST]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg + ":" + KP.result_code);

		int state = msg.getData().getInt("state");

		if (state != COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE) {
			stopTaskShowMessageNotResponse();
		}

		ImageView imgCustomerEvent = (ImageView) findViewById(R.id.img_customer_detail_content);
		TextView txtCertifyLastTimer = (TextView) findViewById(R.id.txt_certify_last_time);
		TextView txtCertifyTimer = (TextView) findViewById(R.id.txt_certify_timer);

		/**
		 * <pre>
		 * 전문조회실패
		 *  응답오류
		 *    조회오류시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
		 *    타임아웃시(COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE)
		 *  전문결과표시
		 *    결과없음("result_code": "00901")
		 * </pre>
		 */
		String op = KP.p_op;
		String code = KP.result_code;
		String message = KP.result_message;
		message += "\n";
		message += "(" + op + ":" + code + ")";
		/**
		 *    결과없음("result_code": "00901")
		 */
		if (("00901").equalsIgnoreCase(code)) {
			message = KP.result_message;
		}

		switch (state) {
			case COMPLETE_TIMER_SHOW_MESSAGE_NOT_RESPONSE:
			case COMPLETE_ERROR_REQUEST_NOT_RESPONSE:
				message = getString(R.string.message_error_network_timeout);
				if (!TextUtil.isEmpty(KP.result_message)) {
					message = KP.result_message;
					message += "\n" + "(" + KP.p_op + ":" + KP.result_code + ")";
				}
				ShowMessageNotResponse(getString(R.string.common_info), message);
				break;
			case COMPLETE_MAIN: // 메인
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_MAIN]");

				if (!("00000").equalsIgnoreCase(code)) {
					if (code.equals("99998")) {
						stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
						if (BuildConfig.DEBUG) Log.i(_toString(), message);
						ShowMessageOk(POPUP_EXIT, getString(R.string.common_new_version), message);
					} else {
						stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
						ShowMessageCommon(CLOSE_OK, getString(R.string.common_info), message);
					}
					return;
				}

				p_mid = KP.p_mid;
				if (BuildConfig.DEBUG) Log.i(_toString(), "[m_strMid] = " + p_mid);
				m_strMainSingID = KP.song_id;
				m_strMainEventID = KP.event_id;
				m_strMainCustomerID = KP.notice_id;
				record_id = KP.listen_id;

				submenuQuickBtn02 = KP.subMainQuickBtn02;

				video_url_back = KP.video_url_back;
				if (BuildConfig.DEBUG) Log.i(_toString(), "bg video url is " + video_url_back);

				if (KP.is_cpn.equals("Y")) {
					m_bCouponUser = true;
					m_strCouponTerm = KP.cpn_term;
				}

				setHOME();
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_HOME: // 메인 LISTEN 프로필 이미지
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfileHome.setImageBitmap(null);
				m_imgProfileHome.setImageBitmap(GetBitmapClippedCircle(util_profileHome.m_bitMap));
				break;
			case COMPLETE_UTIL_EVENT_IMAGE: // 메인 이벤트 이미지
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				// ImageView imgEvent = (ImageView) findViewById(R.id.img_main_event);
				// imgEvent.setImageBitmap(util.m_bitMap);
				if (findViewById(R.id.btn_home_event_05) != null) {
					((ImageButton) findViewById(R.id.btn_home_event_05)).setImageBitmap(util.m_bitMap);
				}
				break;
			case COMPLETE_SONG_LIST: // 반주곡 리스트
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_SONG_LIST]");

				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (m_iPaneState == PANE_HOME) {
					return;
				}

				if (!("00000").equalsIgnoreCase(code)) {
					if (!TextUtil.isEmpty(message)) {
						// 에러가 생겨서 곡 목록을 못 불러왔음 : 현재 목록 상태를 다 비워줘
						// LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						// LinearLayout layoutContent = null;
						// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

						// addKaraokeViewSongList();

						// 페이지 정보도 Hidden
						if (remote.m_iMenuMainFocus == 1) {
							TextView txtPage = (TextView) findViewById(R.id.txt_song_page);
							txtPage.setVisibility(View.INVISIBLE);
						} else if (remote.m_iMenuMainFocus == 3) {
							TextView txtPage = (TextView) findViewById(R.id.txt_song_page);
							txtPage.setVisibility(View.INVISIBLE);
						}

						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
					}
					return;
				} else {
					if (remote.m_iMenuMainFocus == 1) {
						if (remote.m_iMenuSingFocus != 3) {
							TextView txtPage = (TextView) findViewById(R.id.txt_song_page);
							txtPage.setVisibility(View.VISIBLE);
						}
					} else if (remote.m_iMenuMainFocus == 3) {
						TextView txtPage = (TextView) findViewById(R.id.txt_song_page);
						txtPage.setVisibility(View.VISIBLE);
					}
				}

				// 애창곡 목록에서 애창곡을 삭제한 후 리스트 갱신인 경우 인덱스 / 페이지 보정
				if (m_bIsRefreshFavorList) {
					if (BuildConfig.DEBUG) Log.i(_toString(), "MY Favor Refresh");

					// 마지막 페이지에서, 마지막 곡을 삭제했을 때
					if (m_iCurrentViewSongListPage == m_iTotalSongListPage && CheckNotContentsSongList()) {
						if (m_iTotalSongListPage == KP.total_page) {
							if (BuildConfig.DEBUG) Log.i(_toString(), "MY Favor Refresh : img_focus");

							// 마지막 곡을 삭제했는데 전페 페이지 수는 그대로 : 포커스만 하나 위로 올림
							remote.m_iSongListFocus = remote.m_iSongListFocus - 1;
						} else {
							if (BuildConfig.DEBUG) Log.i(_toString(), "MY Favor Refresh : page");

							// 마지막 곡을 삭제했더니 전체 페이지 수가 줄어듬 : 페이지를 하나 좌로 밈
							m_iCurrentViewSongListPage = m_iCurrentViewSongListPage - 1;
							m_iCurrentSongListPage = m_iCurrentSongListPage - 1;
							remote.m_iSongListFocus = 6;
						}

						displayListMy(REMOTE_NONE);
					}

					m_bIsRefreshFavorList = false;
				}

				mSongItems = KP.arraySongItem;
				m_iTotalSongListPage = KP.total_page;

				if (remote.m_iMenuMainFocus == 3) {
					moveMyListPage();
				} else if (remote.m_iMenuMainFocus == 1) {
					moveSingListPage();
				}

				if (remote.m_iState == STATE_SONG_LIST) {
					displayListSing(REMOTE_NONE);
				} else if (remote.m_iState == STATE_MY_LIST) {
					displayListMy(REMOTE_NONE);
				}
				break;
			case COMPLETE_SONG_PLAY: // 반주곡 시작
				// if (BuildConfig.DEBUG) _LOG.i("BOXTEST", "COMPLETE_SONG_PLAY");
				// if (!KP.m_strResultCode.equals("00000")) {
				// if (!KP.m_strResultMessage.equals("")) {
				// ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), KP.m_strResultMessage);
				// }
				// return;
				// }
				//
				// m_strPlaySongUrl = KP.m_strPlaySongUrl;
				// m_strPlayVideoUrl = KP.m_strPlayVideoUrl;
				// m_strPlaySongType = KP.m_strPlaySongType;
				//
				// download = new Download2(handlerKARA);
				// download.setFileName("sing.skym");
				// download.setType(FILE_SONG);
				// download.setUrl(m_strPlaySongUrl);
				// download.newPath = sdPath;
				// download.start();
				break;
			case COMPLETE_DOWN_SONG: // 반주곡 파일 다운로드
				// if (BuildConfig.DEBUG) _LOG.i("BOXTEST", "_COMPLETE_SONG");
				// // HideLoading();
				//
				// String temp = sdPath + File.separator + "sing.skym";
				// arrStartPlayList.clear();
				// arrStartPlayList.add(temp);
				//
				// /*
				// * 동영상 배경화면을 사용할 시, 반주곡 재생시키면서 플레이
				// */
				// if (!m_strPlayVideoUrl.equals("")) {
				// video.startBackgroundVideo(m_strPlayVideoUrl, NEWDRAW);
				// }
				//
				// // StartPlayActivity();
				break;
			case COMPLETE_SONG_PLAYED_TIME: // 반주곡 재생 종료 시간 로그
				break;
			case COMPLETE_FAVOR: // 애창곡 등록 or 삭제
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_FAVOR]");

				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (BuildConfig.DEBUG) Log.i(_toString(), "FAVOR Result Code : " + code);
				if (!("00000").equalsIgnoreCase(code)) {
					ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
					return;
				}

				ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), KP.result_message_favor);

				// 애창곡 목록에서 애창곡이 삭제된 경우 현재 페이지의 목록을 다시 불러와야 함
				if (m_bIsRefreshFavorList) {
					// 애창곡이 하나밖에 없었는데 삭제한거면 목록 요청 없이 첫번째 곡 비워주고 서브메뉴로 포커스 이동
					if (m_iCurrentViewSongListPage == 1 && remote.m_iSongListFocus == 1 && CheckNotContentsSongList()) {
						m_bIsRefreshFavorList = false;

						sing_line.get(0).txt_number.setText("");
						sing_line.get(0).txt_title.setText("");
						sing_line.get(0).txt_singer.setText("");
						sing_line.get(0).ico_note.setVisibility(View.INVISIBLE);

						exitListMy();
						return;
					}
				}

				String OP = "";
				String M1 = "";
				String M2 = "";

				switch (remote.m_iState) {
					case STATE_SONG_LIST:
						OP = KP_1000;

						switch (remote.m_iMenuSingFocus) {
							case 1:
								M1 = M1_MENU_SING;
								M2 = M2_SING_HOT;
								break;
							case 2:
								M1 = M1_MENU_SING;
								M2 = M2_SING_RECENT;
								break;
							case 3:
								M1 = M1_SING_GENRE;

								if (remote.m_iMenuSingGenreFocus == 1) {
									M2 = M2_GENRE_1;
								} else if (remote.m_iMenuSingGenreFocus == 2) {
									M2 = M2_GENRE_2;
								} else if (remote.m_iMenuSingGenreFocus == 3) {
									M2 = M2_GENRE_3;
								} else if (remote.m_iMenuSingGenreFocus == 4) {
									M2 = M2_GENRE_4;
								} else if (remote.m_iMenuSingGenreFocus == 5) {
									M2 = M2_GENRE_5;
								} else {
									M2 = M2_GENRE_6;
								}
								break;
						}

						KP(REQUEST_SONG_LIST, OP, M1, M2);
						break;
					case STATE_MY_LIST:
						OP = KP_3000;

						switch (remote.m_iMenuMyFocus) {
							case 1:
								M1 = M1_MENU_MYLIST;
								M2 = M2_MYLIST_RECENT;
								break;
							case 2:
								M1 = M1_MENU_MYLIST;
								M2 = M2_MYLIST_FAVORITE;
								break;
						}

						KP(REQUEST_SONG_LIST, OP, M1, M2);
						break;
					case STATE_SEARCH_LIST:
						OP = KP_0020;
						M1 = M1_MENU_SEARCH;

						switch (remote.m_iSearchSubMenuFocus) {
							case 1:
								if (m_iSearchSelfMode == TITLE) {
									M2 = M2_SEARCH_1;
								} else {
									M2 = M2_SEARCH_2;
								}
								break;
							case 2:
								M2 = M2_SEARCH_4;
								break;
						}

						KP(REQUEST_SEARCH_LIST, OP, M1, M2);
						break;
				}
				break;
			case COMPLETE_LISTEN_LIST: // 녹음곡 리스트
				if (!("00000").equalsIgnoreCase(code)) {
					stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
					ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
					return;
				}

				m_iTotalListenListPage = KP.total_page;
				mListenItems = KP.arrayListenItem;

				setPageTextListenList();
				startListenListPage();

				if (m_iCurrentViewListenListPage == m_iTotalListenListPage) {
					CheckNotItemOnListenList();
				}

				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				break;
			case COMPLETE_MY_RECORD_LIST: // (마이)녹음곡 리스트
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				// LayoutInflater infMyRecord = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				LinearLayout layoutMy = null;

				if (/*KP.auth_date.equals("null")*/TextUtil.isEmpty(KP.auth_date)) {
					stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

					m_iTotalListenListPage = -1;

					layoutMy = (LinearLayout) inflate(R.layout.my_record_before_certify, null);
					layoutMy.setLayoutParams(lp);
					addView(layout_content, layoutMy);
					return;
				}

				m_iTotalListenListPage = KP.total_page;
				mListenItems = KP.arrayListenItem;

				if (m_iTotalListenListPage == 0) {
					m_bIsCertifyedUser = true;
					layoutMy = (LinearLayout) inflate(R.layout.my_record, null);
				} else {
					m_bIsCertifyedUser = true;
					layoutMy = (LinearLayout) inflate(R.layout.my_record_list, null);
				}

				layoutMy.setLayoutParams(lp);
				addView(layout_content, layoutMy);

				moveMyRecordListPage();
				break;
			case COMPLETE_UTIL_MY_RECORD_PROFILE_IMAGE: // 녹음곡 리스트 : 프로필 이미지 8
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgMyRecordProfile.setImageBitmap(null);
				m_imgMyRecordProfile.setImageBitmap(util_myRecordProfile.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_1: // 녹음곡 리스트 : 프로필 이미지 1
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile01.setImageBitmap(null);
				m_imgProfile01.setImageBitmap(util_profile01.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_2: // 녹음곡 리스트 : 프로필 이미지 2
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile02.setImageBitmap(null);
				m_imgProfile02.setImageBitmap(util_profile02.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_3: // 녹음곡 리스트 : 프로필 이미지 3
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile03.setImageBitmap(null);
				m_imgProfile03.setImageBitmap(util_profile03.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_4: // 녹음곡 리스트 : 프로필 이미지 4
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile04.setImageBitmap(null);
				m_imgProfile04.setImageBitmap(util_profile04.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_5: // 녹음곡 리스트 : 프로필 이미지 5
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile05.setImageBitmap(null);
				m_imgProfile05.setImageBitmap(util_profile05.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_6: // 녹음곡 리스트 : 프로필 이미지 6
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile06.setImageBitmap(null);
				m_imgProfile06.setImageBitmap(util_profile06.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_7: // 녹음곡 리스트 : 프로필 이미지 7
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile07.setImageBitmap(null);
				m_imgProfile07.setImageBitmap(util_profile07.m_bitMap);
				break;
			case COMPLETE_UTIL_PROFILE_IMAGE_8: // 녹음곡 리스트 : 프로필 이미지 8
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgProfile08.setImageBitmap(null);
				m_imgProfile08.setImageBitmap(util_profile08.m_bitMap);
				break;
			case COMPLETE_LISTEN_OTHER: // 이 녹음곡의 다른 사람 녹음곡 리스트
				if (!("00000").equalsIgnoreCase(code)) {
					stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
					ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
					return;
				}

				m_iTotalListenOtherListPage = KP.total_page;
				mListenOtherItems = KP.arrayListenItem;
				setListenOtherPage();
				SetListenOtherFocus(REMOTE_NONE);
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				break;
			case COMPLETE_LISTEN_SONG: // 녹음곡 재생 정보
			case COMPLETE_LISTEN_OTHER_SONG:
				// if (!KP.m_strResultCode.equals("00000")) {
				// HideLoading();
				// ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), KP.m_strResultMessage);
				// return;
				// }
				//
				// m_strListenSongUrl = KP.m_strListenSongUrl;
				// m_strListeningSongID = KP.m_strListeningSongID;
				//
				// download = new Download2(handlerKARA);
				// download.setFileName("listen.m4a");
				// if (state == COMPLETE_LISTEN_SONG) {
				// download.setType(FILE_LISTEN);
				// } else {
				// download.setType(FILE_LISTEN_OTHER);
				// }
				// download.setUrl(m_strListenSongUrl);
				// download.newPath = sdPath;
				// download.start();
				break;
			case COMPLETE_DOWN_LISTEN: // 녹음곡 파일 다운로드
			case COMPLETE_DOWN_LISTEN_OTHER:
				// startListen(state);
				break;
			case COMPLETE_CUSTOMER_LIST: // 공지사항 or 이용안내 리스트
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_CUSTOMER_LIST]");

				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (!("00000").equalsIgnoreCase(code)) {
					if (!TextUtil.isEmpty(message)) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
						return;
					}
				}

				mCustomerItems = KP.arrayCustomerItem;
				m_iTotalCustomerListPage = KP.total_page;
				m_iCustomerListItemCount = 0;

				moveCustomerListPage();
				break;
			case COMPLETE_CUSTOMER_LIST_DETAIL: // 공지사항 or 이용안내 상세 정보
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_CUSTOMER_LIST_DETAIL]");

				if (!("00000").equalsIgnoreCase(code)) {
					stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

					if (!TextUtil.isEmpty(message)) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
						return;
					}
				}

				// LinearLayout layoutCustomerBack = (LinearLayout) findViewById(R.id.layout_sing);
				// SettingListBackground(layoutCustomerBack, R.drawable.sing_bg);
				url_imgs = KP.url_imgs;

				if (remote.m_iMenuCustomerFocus == 1) {
					b_type = KP.b_type;
					term_date = KP.term_date;
					e_stats = KP.e_stats;
				}

				setCustomerDetailContent();
				break;
			case COMPLETE_UTIL_CUSTOMER_DETAIL_IMAGE: // 공지사항 or 이용안내 상세 이미지
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				imgCustomerEvent.setImageBitmap(util.m_bitMap);
				break;
			case COMPLETE_UTIL_EVENT_DETAIL_ON: {
				m_bitMapEventOn = util.m_bitMap;

				util = new _Util(handlerKP);
				util.setUtilType(REQUEST_UTIL_EVENT_DETAIL_OFF);
				String img = url_imgs.get(0);
				String off = img.substring(0, img.length() - 5);
				off = off + "2.png";

				// 또븅신년놈들이지랄을처놨구나...
				if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[OK]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + img);
				if (BuildConfig.DEBUG) Log.e(_toString() + TAG_ERR, "[NG]" + getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + off);

				util.setImageUrl(off);
				util.start();
				break;
			}
			case COMPLETE_UTIL_EVENT_DETAIL_OFF: // 공지사항 or 이용안내 상세 이미지
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				m_bitMapEventOff = util.m_bitMap;

				imgCustomerEvent.setImageBitmap(m_bitMapEventOn);
				break;
			case COMPLETE_SEARCH_LIST: // 검색 리스트
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (!("00000").equalsIgnoreCase(code)) {
					ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
					return;
				}

				// 직접 검색
				if (remote.m_iSearchSubMenuFocus == 1) {
					LinearLayout layoutTip = (LinearLayout) findViewById(R.id.layout_search_self_list_tip);
					layoutTip.setVisibility(View.GONE);

					LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_search_self_list_list);
					layoutList.setVisibility(View.VISIBLE);
					// 색인 검색
				} else {
					LinearLayout layoutTip = (LinearLayout) findViewById(R.id.layout_search_letter_list_tip);
					layoutTip.setVisibility(View.GONE);

					LinearLayout layoutList = (LinearLayout) findViewById(R.id.layout_search_letter_list_list);
					layoutList.setVisibility(View.VISIBLE);
				}

				mSearchItems = KP.arraySongItem;
				m_iTotalSearchListPage = KP.total_page;

				moveSearchListPage();

				if (m_iRequestSearchListPage == 1) {
					if (remote.m_iSearchSubMenuFocus == 1) {
						EditText editWord = (EditText) findViewById(R.id.edit_search_self_word);
						editWord.setBackgroundResource(R.drawable.search_input_off);
						if (m_strSTBVender != P_APPNAME_SKT_BOX) {
							Button btnOk = (Button) findViewById(R.id.btn_search_self_ok);
							btnOk.setBackgroundResource(R.drawable.search_ok_btn_off);
						}
					} else {
						m_iSearchLetterDisplay = SELECTED;
						displaySearchLetter(REMOTE_INIT);
						m_iSearchLetterDisplay = ON;
					}
				}

				remote.m_iState = STATE_SEARCH_LIST;
				displayListSearch(REMOTE_NONE);
				break;
			case COMPLETE_NUMBER_SEARCH: // 곡번호 검색
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (code.equals("00901")) {
					if (BuildConfig.DEBUG) Log.i(_toString(), "NO Number Search Result");
					m_bHaveNumberSearchResult = false;
					ShowNumberSearchResult(getString(R.string.message_error_search_not_result));
					return;
				}

				if (!("00000").equalsIgnoreCase(code)) {
					m_bHaveNumberSearchResult = false;
					ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
					return;
				}

				if (BuildConfig.DEBUG) Log.i(_toString(), "Got Number Search Result");
				m_bHaveNumberSearchResult = true;
				ShowNumberSearchResult(KP.m_strNumberSearchResult);
				break;
			case COMPLETE_AUTH_NUMBER:  // 인증 번호 등록
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (!("00000").equalsIgnoreCase(code)) {
					// auth_num 인증번호, 즉 KP.m_strResponseAuthNumber가 안왔으면 문제가 있는거고,
					// 이 경우 추가 메세지 박스를 띄우지 않고 인증번호 입력 팝업창 텍스트에 바로 결과를 찍어준다
					if (KP.auth_num.equals("null") || KP.auth_num.equals("")) {
						if (remote.m_iState == STATE_CERTIFY_HP) {
							TextView txtCertifyHPMessage = (TextView) findViewById(R.id.txt_certify_hp_message_auth);
							txtCertifyHPMessage.setText(message);
						} else {
							TextView txtCertifyMessage = (TextView) findViewById(R.id.txt_certify_message);
							txtCertifyMessage.setText(message);

							if (m_bIsCertifyTimerActivated) {
								m_cdTimer.cancel();
							}

							if (txtCertifyLastTimer != null) {
								txtCertifyLastTimer.setText("");
							}
							if (txtCertifyTimer != null) {
								txtCertifyTimer.setText("");
							}
						}
						return;
					}
				}

				m_strResponseAuthNumber = KP.auth_num;

				// 휴대폰 번호 입력 팝업 삭제
				if (remote.m_iState != STATE_CERTIFY) {
					remote.m_iCertifyHPFocusX = 1;
					remote.m_iCertifyHPFocusY = 1;
					// ((ViewManager) message_hp.getParent()).removeView(message_hp);
					removeView(message_hp);
					message_hp = null;
				} else {
					if (m_bIsCertifyTimerActivated) {
						m_cdTimer.cancel();
					}

					exitCertifyNumber();
				}

				// 인증 번호 입력 팝업 출력
				remote.m_iCertifyFocusX = 1;
				remote.m_iCertifyFocusY = 1;

				//message_hp_certify = (LinearLayout) inflate(R.layout.message_hp_certify, null);
				//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				//addContentView(message_hp_certify, param);
				message_hp_certify = (LinearLayout) addPopup(R.layout.message_hp_certify);

				remote.m_iState = STATE_CERTIFY;

				displayCertify(REMOTE_NONE);

				m_iCertifyTimerSec = 0;
				m_iCertifyTimerMin = 3;

				if (txtCertifyLastTimer != null) {
					txtCertifyLastTimer.setVisibility(View.VISIBLE);
				}

				m_cdTimer = new CountDownTimer(180000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
						m_iCertifyTimerSec--;

						if (m_iCertifyTimerSec == -1) {
							m_iCertifyTimerSec = 59;
							m_iCertifyTimerMin--;
						}

						String strCertifyTimerSec = String.format("%02d", m_iCertifyTimerSec);
						String strCertifyTimerMin = String.format("%02d", m_iCertifyTimerMin);

						TextView txtCertifyTimer = (TextView) findViewById(R.id.txt_certify_timer);
						txtCertifyTimer.setText(strCertifyTimerMin + " : " + strCertifyTimerSec);

						if (m_iCertifyTimerSec == 0 && m_iCertifyTimerMin == 0) {
							this.cancel();
						}
					}

					@Override
					public void onFinish() {
						m_bIsCertifyTimerActivated = false;

						TextView txtCertifyLastTime = (TextView) findViewById(R.id.txt_certify_last_time);
						txtCertifyLastTime.setVisibility(View.GONE);

						TextView txtCertifyTimer = (TextView) findViewById(R.id.txt_certify_timer);
						txtCertifyTimer.setText("3분이 초과되어 인증 번호 재전송이 필요합니다.");
					}
				}.start();

				m_bIsCertifyTimerActivated = true;

				m_iCertifyTimerMinPopup = 3;
				m_iCertifyTimerSecPopup = 0;

				m_cdTimerPopup = new CountDownTimer(180000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {
						m_iCertifyTimerSecPopup--;

						if (m_iCertifyTimerSecPopup == -1) {
							m_iCertifyTimerSecPopup = 59;
							m_iCertifyTimerMinPopup--;
						}

						if (m_iCertifyTimerSecPopup == 0 && m_iCertifyTimerMinPopup == 0) {
							this.cancel();
						}
					}

					@Override
					public void onFinish() {
						mIsCertifyValidCheck = false;
					}
				}.start();

				mIsCertifyValidCheck = true;
				break;
			case COMPLETE_CERTIFY_STATE:  // 휴대폰 번호 등록
				// LayoutInflater infCertify = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				LinearLayout.LayoutParams lpShopCertify = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

				if (TextUtil.isEmpty(KP.auth_date)) {
					stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

					LinearLayout layoutShopCertify = (LinearLayout) inflate(R.layout.shop_certify, null);
					layoutShopCertify.setLayoutParams(lpShopCertify);
					addView(layout_content, layoutShopCertify);

					auth_modify_idx = 1;
				} else {
					LinearLayout layoutShopCertify = (LinearLayout) inflate(R.layout.shop_certify_already, null);
					layoutShopCertify.setLayoutParams(lpShopCertify);
					addView(layout_content, layoutShopCertify);

					SetCertifyState();

					auth_modify_idx = Integer.parseInt(KP.auth_modify_idx);

					m_bIsCertifyedUser = true;

					if (remote.m_iState == STATE_SHOP_CERTIFY) {
						LinearLayout layoutCertify = (LinearLayout) findViewById(R.id.layout_already_certify);
						layoutCertify.setBackgroundResource(R.drawable.shop_certify_already_on);
					}
				}
				break;
			case COMPLETE_EVENT_APPLY:  // 이벤트 응모하기
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_EVENT_APPLY]");

				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (!("00000").equalsIgnoreCase(code)) {
					if (!TextUtil.isEmpty(message)) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
						return;
					}
				}

				remote.m_iCertifyHPFocusX = 1;
				remote.m_iCertifyHPFocusY = 1;

				//message_hp_event = (LinearLayout) inflate(R.layout.message_hp_event, null);
				//LinearLayout.LayoutParams paramEvent = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				//addContentView(message_hp_event, paramEvent);
				message_hp_event = (LinearLayout) addPopup(R.layout.message_hp_event);

				remote.m_iState = STATE_EVENT_HP;

				displayCertifyHP(REMOTE_NONE);
				break;
			case COMPLETE_EVENT_HP:  // 이벤트 응모하기
				if (BuildConfig.DEBUG) Log.i(_toString(), "[COMPLETE_EVENT_APPLY]");

				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (!("00000").equalsIgnoreCase(code)) {
					if (!TextUtil.isEmpty(message)) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
						return;
					}
				}

				exitCertifyHP();
				ShowMessageOk(POPUP_OK, getString(R.string.common_info), message);
				break;
			case COMPLETE_UTIL_CERTIFY_PROFILE_IMAGE:
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				m_imgCertifyProfile.setImageBitmap(null);
				m_imgCertifyProfile.setImageBitmap(util_certifyProfile.m_bitMap);
				break;
			case COMPLETE_AUTH_NUMBER_CORRECT:
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				// if (m_strInputAuthNumber.equals(m_strResponseAuthNumber)) {
				if (code.equals("00001")) {
					if (m_bIsCertifyTimerActivated) {
						m_cdTimer.cancel();
					}

					exitCertifyNumber();

					ShowMessageOk(POPUP_OK, getString(R.string.certify_success), message);
					// ShowMessageOk(OK, getString(R.string.certify_success), getString(R.string.certify_success_message));
					m_bIsCertifyedUser = true;

					// 인증이 완료되었으므로 전화번호를 로컬에 저장하자
					writeKaraoke(m_strHPNumber);

					KP(REQUEST_CERTIFY_STATE, KP_9000, M1_MAIN, M2_MENU);
				} else {
				/*
				 * if (m_bIsCertifyTimerActivated) { m_cdTimer.cancel(); }
				 *
				 * TextView txtCertifyLastTimer = (TextView)findViewById(R.id.txt_certify_last_time); TextView txtCertifyTimer = (TextView)findViewById(R.id.txt_certify_timer);
				 * txtCertifyLastTimer.setText(""); txtCertifyTimer.setText("");
				 */

					TextView txtCertifyMessage = (TextView) findViewById(R.id.txt_certify_message);
					txtCertifyMessage.setText(message);
				}
				break;
			case COMPLETE_MY_SUB_MENU:
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
			/*
			 * if (KP.arraySubMenuItem.size() == 3) { if (KP.arraySubMenuItem.get(2).m2.equals(MYLIST_RECORD)) { Button btnRecord = (Button)findViewById(R.id.btn_my_sub_record);
			 * btnRecord.setVisibility(View.VISIBLE); } }
			 */

				if (remote.m_iMenuMainFocus == 3) {
					Button btnRecord = (Button) findViewById(R.id.btn_my_sub_record);
					btnRecord.setVisibility(View.VISIBLE);

					//KP(REQUEST_SONG_LIST, KP_3000, M1_MENU_MYLIST, M2_MYLIST_RECENT);
					displayMenuMy(REMOTE_INIT);
				}
				break;
			case COMPLETE_SHOP_SUB_MENU:
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
			/*
			 * if (KP.arraySubMenuItem.size() == 2) { if (KP.arraySubMenuItem.get(1).m2.equals(SHOP_AUTH)) { Button btnCertify = (Button)findViewById(R.id.btn_shop_sub_certify);
			 * btnCertify.setVisibility(View.VISIBLE); } }
			 */

				Button btnCertify = (Button) findViewById(R.id.btn_shop_sub_certify);
				btnCertify.setVisibility(View.VISIBLE);

				break;
			case COMPLETE_TIMER_HIDE_MESSAGE_COMMON:
				HideMessageCommon();
				break;
			case COMPLETE_TIMER_HIDE_SCORE:
				HideScore();
				break;
			case COMPLETE_TIMER_START_SING_NOW:
			case COMPLETE_TIMER_START_SING_NEXT:
				KP(REQUEST_SONG_PLAY, KP_1016, "", "");
				break;
			case COMPLETE_UTIL_MAIN_QUICK_IMAGE_01_ON:
				break;
			case COMPLETE_UTIL_MAIN_QUICK_IMAGE_01_OFF:
				break;
			case COMPLETE_UTIL_MAIN_QUICK_IMAGE_02_ON:
				break;
			case COMPLETE_UTIL_MAIN_QUICK_IMAGE_02_OFF:
				if (m_imgMainQuickBtn02 != null && util_mainQuickBtnOff02 != null) {
					m_imgMainQuickBtn02.setImageBitmap(util_mainQuickBtnOff02.m_bitMap);
				}
				break;
			case COMPLETE_TICKET_SALES_STATE:
				// HideLoading();

				if (!("00000").equalsIgnoreCase(code)) {
					stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
					if (!TextUtil.isEmpty(message)) {
						ShowMessageOk(POPUP_EXIT, getString(R.string.common_info), message);
					}
					return;
				}

				////isyoon:홈화면에선 처리 안한다.
				//if (PANE_STATE.PANE_HOME == PANE_STATE.get(m_iPaneState)) {
				//	if (BuildConfig.DEBUG) Log.e(_toString(), getMethodName() + "[NO]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + remote.getState() + ":" + PANE_STATE.get(m_iPaneState) + ":" + msg + ":" + code);
				//	return;
				//}
				//
				//븅신오류나면머?
				//if (getTicketItemMonth() != null && getTicketItemDay() != null) {
				//	addViewShopList();
				//
				//	if (KP.m_bShowCouponMenu) {
				//		remote.m_bShowCouponMenu = true;
				//		findViewById(R.id.btn_shop_tab_3).setVisibility(View.VISIBLE);
				//	} else {
				//		remote.m_bShowCouponMenu = false;
				//		findViewById(R.id.btn_shop_tab_3).setVisibility(View.INVISIBLE);
				//	}
				//
				//	TextView txt_shop_price = (TextView) findViewById(R.id.txt_shop_price);
				//	txt_shop_price.setText(getTicketItemMonth().price + "원");
				//
				//	util_shopItem01 = new _Util(handlerKP);
				//	util_shopItem01.setUtilType(REQUEST_UTIL_SHOP_ITEM_01);
				//	util_shopItem01.setImageUrl(getTicketItemMonth().url_img);
				//	util_shopItem01.start();
				//} else {
				//	removeAllViewsContent();
				//	stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
				//	return;
				//}
				break;
			//case COMPLETE_UTIL_SHOP_ITEM_01:
			//	ImageView item1 = (ImageView) findViewById(R.id.img_shop_item_01);
			//	item1.setImageBitmap(util_shopItem01.m_bitMap);
			//
			//	m_bitMapShopItem01 = util_shopItem01.m_bitMap;
			//
			//	util_shopItem02 = new _Util(handlerKP);
			//	util_shopItem02.setUtilType(REQUEST_UTIL_SHOP_ITEM_02);
			//	util_shopItem02.setImageUrl(getTicketItemDay().url_img);
			//	util_shopItem02.start();
			//	break;
			//case COMPLETE_UTIL_SHOP_ITEM_02:
			//	stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));
			//
			//	m_bitMapShopItem02 = util_shopItem02.m_bitMap;
			//	break;
			case COMPLETE_COUPON_REGIST:
				stopLoading(getMethodName() + COMPLETE_KP.get(msg.getData().getInt("state")));

				if (!("00000").equalsIgnoreCase(code)) {
					if (!TextUtil.isEmpty(message)) {
						ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);
						return;
					}
				}

				ShowMessageCommon(CLOSE_AUTO, getString(R.string.common_info), message);

				m_bCouponUser = true;
				m_strCouponTerm = KP.cpn_term;

				//m_bIsFocusedOnTicket = false;
				/**
				 * 이건remote에정의하고
				 */
				remote.m_iShopTicketFocusY = 0;
				remote.m_iShopTicketFocusX = 1;
				// 이건아니고?그지?
				//m_iCouponFocus = 1;

				ViewGroup layoutTicket = (ViewGroup) findViewById(R.id.layout_shop_ticket);
				//Button btnCoupon = (Button) findViewById(R.id.btn_shop_tab_3);
				LinearLayout layoutRegisted = (LinearLayout) findViewById(R.id.layout_coupon_registed);
				LinearLayout layoutRegist = (LinearLayout) findViewById(R.id.layout_coupon_not_regist);

				layoutRegisted.setVisibility(View.VISIBLE);
				layoutRegist.setVisibility(View.GONE);

				//btnCoupon.setBackgroundResource(R.drawable.shop_ticket_tab_on);
				//layoutTicket.setBackgroundResource(R.drawable.shop_coupon_bg_registed_on);

				TextView txtCouponTerm = (TextView) findViewById(R.id.txt_coupon_term);
				txtCouponTerm.setText(m_strCouponTerm);

				setBottomProductText(m_strCouponTerm);
				break;
			case COMPLETE_UTIL_KY_LOGO:
				// ImageView logo = (ImageView) findViewById(R.id.img_ky_logo);
				// logo.setImageBitmap(util_kyLogo.m_bitMap);
				//
				// m_bitMapKYLogo = util_kyLogo.m_bitMap;
				break;
			case COMPLETE_UTIL_MIC:
				m_bitMapMic = util_MIC.m_bitMap;
				break;
		}

		if (BuildConfig.DEBUG) Log.i(_toString(), getMethodName() + "[ED]" + COMPLETE_KP.get(msg.getData().getInt("state")) + ":" + msg + ":" + KP.result_code);
	}

	protected final Handler handlerVASS = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			VASS(msg);
		}
	};

	/**
	 * @see Main3XXXX#VASS(REQUEST_VASS)
	 */
	public void VASS(final REQUEST_VASS request) {
	}

	public void VASS(Message msg) {
		if (BuildConfig.DEBUG) Log.i("[VASS]" + _toString(), getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")) + ":" + msg);
		// if (BuildConfig.DEBUG) _LOG.i(_toString(), "handleMessage = " + String.valueOf(state));

		int state = msg.getData().getInt("state");

		switch (state) {
			//case COMPLETE_VASS_DAY_CHECK:
			//
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		p_passtype = TICKET_TYPE_DAY;
			//		p_passtype = p_passtype;
			//		if (BuildConfig.DEBUG) Log.i(_toString(), "TICKET = " + p_passtype);
			//		setBottomProductText(getString(R.string.message_info_ticket_have_ppv) + VASS.getTicketEndDate());
			//
			//		KP(REQUEST_MAIN, KP_0000, M1_MAIN, M2_MENU);
			//	} else {
			//		VASS(REQUEST_VASS_MONTH_CHECK);
			//	}
			//
			//	break;
			//case COMPLETE_VASS_MONTH_CHECK:
			//
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		p_passtype = TICKET_TYPE_MONTH;
			//		p_passtype = p_passtype;
			//		if (BuildConfig.DEBUG) Log.i(_toString(), "TICKET = " + p_passtype);
			//		setBottomProductText(getString(R.string.message_info_ticket_have_ppm));
			//	} else {
			//		p_passtype = p_passtype;
			//		if (BuildConfig.DEBUG) Log.i(_toString(), "TICKET = " + p_passtype);
			//		setBottomProductText(getString(R.string.message_error_ticket_no));
			//	}
			//
			//	// p_passtype = TICKET_TYPE_NONE;
			//	// m_strPassType = TICKET_TYPE_NONE;
			//
			//	KP(REQUEST_MAIN, KP_0000, M1_MAIN, M2_MENU);
			//	break;
			//case COMPLETE_VASS_DAY_CHECK_PLAY:
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		p_passtype = TICKET_TYPE_DAY;
			//		p_passtype = p_passtype;
			//
			//		setBottomProductText(getString(R.string.message_info_ticket_have_ppv) + VASS.getTicketEndDate());
			//
			//		TryPlaySong();
			//	} else {
			//		VASS(REQUEST_VASS_MONTH_CHECK_PLAY);
			//	}
			//	break;
			//case COMPLETE_VASS_MONTH_CHECK_PLAY:
			//
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		p_passtype = TICKET_TYPE_MONTH;
			//		p_passtype = p_passtype;
			//
			//		setBottomProductText(getString(R.string.message_info_ticket_have_ppm));
			//	} else {
			//		p_passtype = TICKET_TYPE_NONE;
			//		p_passtype = p_passtype;
			//
			//		if (m_bCouponUser) {
			//			setBottomProductText(m_strCouponTerm);
			//		} else {
			//			setBottomProductText(getString(R.string.message_error_ticket_no));
			//		}
			//	}
			//
			//	TryPlaySong();
			//	break;
			//case COMPLETE_VASS_PASSWORD_FOR_DAY:
			//	if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "COMPLETE_VASS_PASSWORD_FOR_DAY");
			//
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		VASS(REQUEST_VASS_DAY_PURCHASE);
			//
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "COMPLETE_VASS_PASSWORD_FOR_DAY - result is Y end");
			//	} else {
			//		stopLoading(getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")));
			//
			//		TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);
			//		txtPassResult.setText(getString(R.string.ticket_popup_pass_error));
			//
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "COMPLETE_VASS_PASSWORD_FOR_DAY - result is not Y end");
			//	}
			//	break;
			//case COMPLETE_VASS_PASSWORD_FOR_MONTH:
			//	if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "COMPLETE_VASS_PASSWORD_FOR_MONTH");
			//
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		VASS(REQUEST_VASS_MONTH_PURCHASE);
			//
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "COMPLETE_VASS_PASSWORD_FOR_MONTH - result is Y end");
			//	} else {
			//		stopLoading(getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")));
			//
			//		TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);
			//		txtPassResult.setText(getString(R.string.ticket_popup_pass_error));
			//
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "COMPLETE_VASS_PASSWORD_FOR_MONTH - result is not Y end");
			//	}
			//	break;
			//case COMPLETE_VASS_DAY_PURCHASE:
			//case COMPLETE_VASS_MONTH_PURCHASE:
			//	if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "_COMPLETE_VASS_*****_PURCHASE");
			//
			//	stopLoading(getMethodName() + COMPLETE_VASS.get(msg.getData().getInt("state")));
			//
			//	if (null != VASS && ("Y").equalsIgnoreCase(VASS.isSuccess())) {
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "_COMPLETE_VASS_*****_PURCHASE : result is Y");
			//
			//		exitPPV();
			//
			//		//if (m_iProcessTicket == TICKET_INDEX_DAY) {
			//		if (remote.key() == TICKET_TYPE.TICKET_TYPE_DAY) {
			//			//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_ppv_info, null);
			//			//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			//			//addContentView(message_ticket, param);
			//			message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_ppv_info);
			//		} else {
			//			//message_ticket = (LinearLayout) inflate(R.layout.message_ticket_ppm_info, null);
			//			//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			//			//addContentView(message_ticket, param);
			//			message_ticket = (LinearLayout) addPopup(R.layout.message_ticket_ppm_info);
			//		}
			//
			//		remote.m_iState = STATE_MESSAGE_PPX_SUCCESS;
			//
			//		LinearLayout layoutOKCancel = (LinearLayout) findViewById(R.id.layout_ppv_okcancel);
			//		layoutOKCancel.setVisibility(View.GONE);
			//
			//		LinearLayout layoutComment = (LinearLayout) findViewById(R.id.layout_ppv_comment);
			//		layoutComment.setVisibility(View.VISIBLE);
			//
			//		TextView txtRed = (TextView) findViewById(R.id.txt_message_ticket_day_bill_red);
			//		TextView txtBlack = (TextView) findViewById(R.id.txt_message_ticket_day_bill_black);
			//		txtRed.setVisibility(View.VISIBLE);
			//		txtBlack.setVisibility(View.VISIBLE);
			//
			//		//if (m_iProcessTicket == TICKET_INDEX_DAY) {
			//		if (remote.key() == TICKET_TYPE.TICKET_TYPE_DAY) {
			//			// 일이용권
			//			// ==============================================================tamashii
			//			String temp = VASS.getPurchaseResult();
			//
			//			// 이런미친븅신새끼!!!또꼬라박고지랄이네!!!
			//			// if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			//			// // ==============================================================tamashii
			//			// temp = temp + "^" + VASS_PRODUCT_ID_DAY_SKT_BOX;
			//			// } else {
			//			// // ==============================================================tamashii
			//			// temp = temp + "^" + VASS_PRODUCT_ID_DAY_SKB_STBUHD;
			//			// }
			//			temp = temp + "^" + VASS.getTicketItemDay().id_product;
			//
			//			// TextView txtEngageListMain = (TextView)findViewById(R.id.txt_top_engage_list);
			//			// txtEngageListMain.setText(temp);
			//
			//			RETURN_DATA = temp;
			//			if (BuildConfig.DEBUG) Log.i("Main2", RETURN_DATA);
			//			//RETURN_DATA = Base64.encodeToString(temp.getBytes(), Base64.NO_WRAP);
			//
			//			KP(REQUEST_DAY_PURCHASE_COMPLETE, KP_4001, M2_MENU_SHOP, M2_SHOP_TICKET);
			//
			//			//long lTime = System.currentTimeMillis();
			//			//Date date = new Date(lTime);
			//			//Calendar cal = Calendar.getInstance();
			//			//cal.setTime(date);
			//			//cal.add(Calendar.DATE, 1);
			//			//SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년MM월dd일HH시");
			//			//String strCurDate = CurDateFormat.format(cal.getTime());
			//			String strCurDate = TextUtil.getToday(getString(R.string.message_info_ticket_end_date_format), 1);
			//			strCurDate = TextUtil.getToday(strCurDate, getString(R.string.message_info_ticket_end_date_format), 0);
			//
			//			TextView txtDate = (TextView) findViewById(R.id.txt_message_ticket_day_late_content);
			//			txtDate.setText("1일 "+ strCurDate);
			//
			//			//setBottomProductText(getString(R.string.message_info_ticket_have_ppv) + " (" + strCurDate + "까지)");
			//			setBottomProductText(getString(R.string.message_info_ticket_have_ppv) + " " + strCurDate);
			//
			//			p_passtype = TICKET_TYPE_DAY;
			//		} else {
			//			// 월이용권
			//			String temp = VASS.getPurchaseResult();
			//
			//			// 이런미친븅신새끼!!!또꼬라박고지랄이네!!!
			//			// if (P_APPNAME_SKT_BOX.equalsIgnoreCase(m_strSTBVender)) {
			//			// // ==============================================================tamashii
			//			// temp = temp + "^" + "20991231235959" + "^" + VASS_PRODUCT_ID_MONTH_SKT_BOX;
			//			// } else {
			//			// // ==============================================================tamashii
			//			// temp = temp + "^" + "20991231235959" + "^" + VASS_PRODUCT_ID_MONTH_SKB_STBUHD;
			//			// }
			//			temp = temp + "^" + "20991231235959" + "^" + VASS.getTicketItemMonth().id_product;
			//
			//			// TextView txtEngageListMain = (TextView)findViewById(R.id.txt_top_engage_list);
			//			// txtEngageListMain.setText(temp);
			//
			//			RETURN_DATA = temp;
			//			if (BuildConfig.DEBUG) Log.i("Main2", RETURN_DATA);
			//			//RETURN_DATA = Base64.encodeToString(temp.getBytes(), Base64.NO_WRAP);
			//
			//			KP(REQUEST_MONTH_PURCHASE_COMPLETE, KP_4001, M2_MENU_SHOP, M2_SHOP_TICKET);
			//
			//			setBottomProductText(getString(R.string.message_info_ticket_have_ppm));
			//
			//			setBottomProductText(getString(R.string.message_info_ticket_have_ppm));
			//
			//			p_passtype = TICKET_TYPE_MONTH;
			//			m_bIsGoToCertifyMessage = true;
			//		}
			//
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "_COMPLETE_VASS_*****_PURCHASE : result is Y end");
			//	} else {
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "_COMPLETE_VASS_*****_PURCHASE : result is not Y");
			//
			//		// String strPurchaseResult = VASS.getPurchaseResult();
			//		// String strVASSErrorCode = strPurchaseResult.substring(strPurchaseResult.length() - 4, strPurchaseResult.length());
			//		String code = VASS.getVASSErrorCode();
			//
			//		//if (BuildConfig.DEBUG)
			//		{
			//			switch (state) {
			//				case COMPLETE_VASS_DAY_PURCHASE:
			//					code = VASS.getTicketItemDay().id_product + ":" + VASS.getVASSErrorCode();
			//					break;
			//				case COMPLETE_VASS_MONTH_PURCHASE:
			//					code = VASS.getTicketItemMonth().id_product + ":" + VASS.getVASSErrorCode();
			//					break;
			//				default:
			//					break;
			//			}
			//		}
			//
			//		String text = getString(R.string.message_error_ticket_buy) + "(" + code + ")";
			//
			//		TextView txtPassResult = (TextView) findViewById(R.id.txt_message_ticket_pass_info_sub);
			//		if (null != txtPassResult) {
			//			txtPassResult.setText(text);
			//			WidgetUtils.setTextViewMarquee(txtPassResult, true);
			//		}
			//
			//		if (BuildConfig.DEBUG) Log.i("Main2 - vassHandler", "_COMPLETE_VASS_*****_PURCHASE : result is not Y end");
			//	}
			//	break;
		}
	}

	@Deprecated
	public void TryPlaySong() {
		//hideBottomGuide01();
		//hideBottomGuide02();
		//
		//// 이용권이 없으면 알림 팝업 띄우고 리턴
		//if (!isPassUser()) {
		//	stopLoading(getMethodName());
		//
		//	stopTaskShowMessageNotResponse();
		//
		//	m_bIsGoToPurchaseMessage = true;
		//
		//	ShowMessageOkCancel(getString(R.string.common_info), getString(R.string.ticket_have_no));
		//
		//	ShowMenu(getMethodName());
		//
		//	return;
		//}
		//
		//KP.setSongPlayUrl(KP_1016, "", "", m_strRequestPlaySongID);
		//
		//KP.start();
	}

}