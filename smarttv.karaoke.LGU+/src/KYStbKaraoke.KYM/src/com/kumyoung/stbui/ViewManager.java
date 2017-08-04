package com.kumyoung.stbui;

import android.view.inputmethod.InputMethodManager;


public class ViewManager  {
//public class LyricView extends SurfaceView implements SurfaceHolder.Callback {
    
	private static final String TAG = ViewManager.class.getSimpleName();
	private static ViewManager _instance;
	

	public OSDView				lpOsdView = null;
	public static TitleView			_lpTitleView = null;
	public MainMenuView			lpMenuView = null;
	
	
     
    public AdView				lpAdView = null;
	public SongListView			lpSongSearchListView = null;
	public SongSearchHeaderView	lpSongSearchHeaderView = null;
	
	public BackgroundView		lpBackgroundView = null;
	
	public LyricView			lpLyric1View = null;
	public LyricView			lpLyric2View = null;
	public BottomMenu			lpBottomMenu = null;
	
	
	public ScoreView			lpScoreView = null;
	
	public ChallengeFiftyView lpChallengeFiftyView = null;
	
	

	public  AbstractLV			lpCur = null;
	public  AbstractLV			lpAgo = null;
	
	
	public InputMethodManager  imm =  null;
	
	public Boolean 				show_keyboard = false;
	public int	curItem = 0;
	
	static {
		_instance = new ViewManager();
	}
	 
	 
	private ViewManager() 
	{
	}
	  
	public static ViewManager Inst() {
		return _instance;
	} 

}