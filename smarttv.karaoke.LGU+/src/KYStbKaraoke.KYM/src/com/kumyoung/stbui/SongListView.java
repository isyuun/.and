package com.kumyoung.stbui;

//import java.util.ArrayList;

import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;
import com.kumyoung.gtvkaraoke.CustomDialog;
import com.kumyoung.gtvkaraoke.DataHandler;
import com.kumyoung.gtvkaraoke.Database;
import kr.kumyoung.gtvkaraoke.R;
import com.kumyoung.gtvkaraoke.UrlStatusCheck;
import com.kumyoung.stbcomm.SIMClientHandlerLGU;
import com.kumyoung.gtvkaraoke.XmlParser;

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

import com.kumyoung.stbui.TitleBarView;

public class SongListView extends AbstractLV implements OnDismissListener {
    
	private static final String TAG = SongListView.class.getSimpleName();
	
	private Bitmap 			bmBG;
    private Bitmap 			bmBGline;
    private Bitmap 			bmFocus;
    private Bitmap			bmFocusDim;
    private Bitmap			bmRank;
    
    private Bitmap			bmArrowLeft;
    private Bitmap			bmArrowRight;
    private Bitmap			bmArrowLeftActive;
    private Bitmap			bmArrowRightActive;

   
    final private int  		ROW_HEIGHT = (33 + 2)*2;
    final private int  		FONT_SIZE = 32; 
    final private int  		SINGER_X = 1000;
    final private int  		TYPO_Y = 64;
    final private int 		NUMBER_Y = 32;
    
    private int 			arrow_down = 0;
    
    private	int				curPos = 0;
    private int 			lines = 8;
    private int 			cur_page = 0;
    private int 			max_page = 0;
    private int				max_items = 0;
    private Rect			[]rcRect = null;
    
    private Rect			rcArrowLeft;
    private Rect			rcArrowRight;

    private String 			searchKeyword = "akdsfjisf";
    
    
    ArrayList<Integer> 	contentList;
    ArrayList<String> 	contentTitleList;
    ArrayList<String> 	contentSingerList;
    
    private Bitmap 		bmNumber = null;
    private Bitmap 		bmRankNumber = null;
   
    private Canvas		canvasMemory = null;
    private Bitmap		bitmapMemory = null;
    private Bitmap 		bmNothing = null;

    private PopupWindow	popup =null;
    private AbstractLV	headerView = null;
    private TitleBarView titleBarView = null;
    
    private int 	arrow_width = 40;
   
    private	int			min = 1;
    private	int			max = 1;
    
    private int		 	query_max = 8;
    
    private int mouseX = 0;
    private int mouseY = 0;
    
    private String	m_modeName;
    private boolean show_rank = false;
//  private	int			mode = 0;		// 리스트 출력 포멧 
//  public void 		setMode( int n )
//  {
//		mode = n;
//  }
    public void 	setTitleBarView( TitleBarView pp)
    {
    	titleBarView = pp;
    }
    public void 	setHeaderView( AbstractLV pp)
    {
    	headerView = pp;
    }
    
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public SongListView(Context context) 
    {
        super(context);
//        Log.w(TAG,"CustomView("+context+")");
    }
    
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public SongListView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
//        Log.w(TAG,"CustomView("+context+","+attrs+")");
       

        m_modeName = attrs.getAttributeValue(4);
        boolean large = true;
        if ( m_modeName != null)
        {
        	if ( m_modeName.equals("songsearch"))
        		large = false;
        }
        
        if (large)
        {
        	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_bg );
      		bmBGline = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_line );
      		
      		lines = 8;
      		query_max = 8;
        }
        else
        {
         	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_bg_s );
      		bmBGline = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_line_s );
      		
      		lines = 6;
      		query_max = 6;
        }
        	
      	
       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_f );
       	bmFocusDim = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_f_dim );
  
       	bmArrowLeft = BitmapFactory.decodeResource( contax.getResources(), R.drawable.page_arr_left);
       	bmArrowRight = BitmapFactory.decodeResource( contax.getResources(), R.drawable.page_arr_right);
       	
       	bmArrowLeftActive = BitmapFactory.decodeResource( contax.getResources(), R.drawable.page_arr_left_f);
       	bmArrowRightActive = BitmapFactory.decodeResource( contax.getResources(), R.drawable.page_arr_right_f);
   
       
        bmNumber = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_num_s);
        bmRankNumber = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_ranking);
        bmRank = BitmapFactory.decodeResource( contax.getResources(), R.drawable.ranking );
        
        
        bitmapMemory = Bitmap.createBitmap( bmBG.getWidth(), bmBG.getHeight(), Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        if ( canvasMemory != null)
        {
        	canvasMemory.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
        	canvasMemory.drawBitmap ( bmBGline, 0, 0, null ); 		// 버퍼에 그리기 
        }
        
        

        //** list 없을 시*/
       	bmNothing = BitmapFactory.decodeResource( contax.getResources(), R.drawable.info );
        
        
        rcRect = new Rect[8];
		int row = Global.DPFromPixel(ROW_HEIGHT);
        for ( int i = 0; i < lines; i++)
        {
       	  	int y = ( row * i);
        	//int typo_y = y +  Global.DPFromPixel(32);//font ;
        	rcRect[i] = new Rect( 0, y, Constants.width, y+row );
        }
       
        arrow_width = bmArrowLeft.getWidth();
        rcArrowLeft = new Rect( 0, 0, arrow_width, 300);
        rcArrowRight = new Rect( bmBG.getWidth(), 0, bmBG.getWidth()+(arrow_width*2), 300);
 
        doReq( m_modeName );

        if ( ViewManager.Inst().lpBottomMenu != null)
        {
        	ViewManager.Inst().lpBottomMenu.isShowBack = true;
        	ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
        }
    }
  
 /* 
        void doMessage( String strMsg, final boolean needkill  ) {
  		                AlertDialog ad = new AlertDialog.Builder(ATVKaraokeActivity.this).create();  
   		                ad.setCancelable(false); // This blocks the 'BACK' button  
   		                ad.setMessage(strMsg); 
   		                ad.setButton( AlertDialog.BUTTON_POSITIVE, "확 인", new DialogInterface.OnClickListener() {  
   		                        @Override  
   		                        public void onClick(DialogInterface dialog, int which) {  
   		                            dialog.dismiss();                      
   		                            if ( needkill )
   		                            {
   		                            	ATVKaraokeActivity.this.finish();
   		                            }
   		                        }  
   		                });  
   		                ad.show(); 		                
 
        }
    */
    
    public void doReq(String modeName)
    {
    	if ( DataHandler.isReachable == false)
    	{
    		Global.Inst().app.doMenu(2);
    	
    		contentList = null;
    		contentTitleList = null;
    		contentSingerList = null;
    		
    		return;
    	}
    	
    	if ( modeName.equals("list_help"))
    	{
    		
    	}
    	else
        if ( modeName.equals("list_lately"))
        {
        	contentList 		= new ArrayList<Integer> ();
        	contentTitleList	= new ArrayList<String>  ();
        	contentSingerList	= new ArrayList<String>  ();
	   		MakeList( Database.Inst().snoLately, contentList, contentTitleList, contentSingerList, cur_page);
        }
        else
        if ( modeName.equals("list_familyfavorite"))
        {
        //	http://211.236.190.103:8080/common2/favor_list.asp?user_id=junoci&dmc_id=11&tot_cnt=0&so_id=CNM	
   	   		ArrayList<Integer>temp 		= new ArrayList<Integer> ();
   	   		contentList 		= new ArrayList<Integer> ();
   	   		contentTitleList	= new ArrayList<String>  ();
   	   		contentSingerList	= new ArrayList<String>  ();
   	   		
        	int result = DataHandler.GetRegistFavoriteSong(SIMClientHandlerLGU.cont_no, temp);
   	   		if ( result == 1 )	// succesㅓs
   	   		{
   	   			MakeList( temp, contentList, contentTitleList, contentSingerList, cur_page);
   	   			max_page = ( temp.size() /lines) + ( (temp.size() %lines) != 0 ? 1 :0 );
   	   			max_items = temp.size();
   	   			
   	   		}
   	   		else
   	   		if ( result == 2 )
   	   		{
   	   			// 등록하신 가족애창곡이 없습니다.
   	   		}
   	   		else
   	   		{
   	   			// 0 is error
   	   		}
        }
        else
        if ( modeName.equals("songsearch"))
        {
        	/*
        	 * 
        	 */
        	requestSearchData( ViewManager.Inst().curItem , searchKeyword, cur_page);	
        	
        }
        else
        if ( modeName.equals("list_newsong_hot100")) {		// 최신곡 HOT 100
        	
            requestData( 1, cur_page );
        }
        else
        if ( modeName.equals("list_favsong_top100"))		// 인기곡 TOP 100
        {
        	show_rank = true;
            requestData( 2, cur_page );
        }
        else
        if ( modeName.equals("list_genre_trot"))    {    requestGenreData( 1, cur_page );  }	else
        if ( modeName.equals("list_genre_child"))   {    requestGenreData( 2, cur_page );  }	else
        if ( modeName.equals("list_genre_anim"))    {    requestGenreData( 3, cur_page  );  }	else
        if ( modeName.equals("list_genre_pop"))     {    requestGenreData( 4, cur_page  );  }	else
        if ( modeName.equals("list_genre_ballad"))  {    requestGenreData( 5, cur_page  );  }	else
        if ( modeName.equals("list_genre_dance"))   {    requestGenreData( 6, cur_page  );  }	else
        if ( modeName.equals("list_genre_rnb"))     {    requestGenreData( 7, cur_page  );  }	else
        if ( modeName.equals("list_genre_rock"))    {    requestGenreData( 8, cur_page  );  }	else
        if ( modeName.equals("list_genre_7080"))    {    requestGenreData( 9, cur_page  );  }	else
        if ( modeName.equals("list_genre_medley"))  {    requestGenreData( 10, cur_page  );  }	else
        	
        if ( modeName.equals("list_genre_theme1"))  {    requestGenreData( 11, cur_page  );  }	else
        if ( modeName.equals("list_genre_theme2"))  {    requestGenreData( 12, cur_page  );  }	else
        if ( modeName.equals("list_genre_freesong")){    requestGenreData( 13, cur_page  );  }	
        else
        {
        	// data request 
        	requestData ( 0, cur_page );
        } 
    }
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public SongListView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        this.text = attrs.getAttributeValue(null,"text");
        contax = context;
   }
   
    
    public static String utf82euc(String str) 
    { 
        if(str.equals("")) 
            return ""; 
        String result = null; 
         
        try { 
            byte[] raws = str.getBytes("utf-8"); 
            result = new String( raws, "euc-kr" ); 
        }catch( java.io.UnsupportedEncodingException e ) { 
        } 
        return result; 
    } 
    /** 
     * 
     * @param string
     */
    
    
    public void updateList()
    {
    	this.postInvalidate();
    }
    
    public void doRequestData(int cate, String stringKey)
	{
    	String empty = "";
    	if ( stringKey.equals(empty))
    	{
    		searchKeyword = stringKey;
    	}
    	else
    	{
    		searchKeyword = stringKey;
    		requestSearchData( cate, stringKey, 0 );
    	}
    	
    	
        curPos = 0;		// 1page reset
        cur_page = 0;
    	return;
    	
/*    			
   		XmlParser p = new XmlParser();
   		
   		contentList 		= new ArrayList<Integer> ();
   		contentTitleList	= new ArrayList<String>  ();
   		contentSingerList	= new ArrayList<String>  ();
   		
   		
   		String sASP = String.format( "ac_search_songlist_utf.asp?");
   		String sForm = String.format("gb=0&s_cd=02&s_value=" + "" + (string) +"" );
   		
   		Log.d(TAG, "Request :" + sASP + sForm);
   		if ( p.Parser ( sASP + sForm, contentList, contentTitleList, contentSingerList ) )
   			contentList.get(0);
   		
   		
   		Log.d("request", "data size " + contentList.size() );
   		max_page = (contentList.size()/lines) + ( (contentList.size()%lines) != 0 ? 1 :0 );
*/	
	}
    
    /**
     * request mode   곡번호, 곡제목, 가수명, 노래책 04,   	
     */
    private void requestData( int mode, int page )
    {
    	if ( DataHandler.isReachable == false)  {
    		Global.Inst().app.doMenu(2);
    		return;
    	}
   
//    	min = ( page *query_max );
    	
   		XmlParser p = new XmlParser();
   		
   		contentList 		= new ArrayList<Integer> ();
   		contentTitleList	= new ArrayList<String>  ();
   		contentSingerList	= new ArrayList<String>  ();
   
   		
   		String sASP 	= String.format( "ac_search_songlist_utf.asp?");
//   		String sUser 	= String.format( "USER_ID=%s&SUBSCRIBER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", 
//   										 "1347299446", "PID2843110", 
//   										 7, "BSI", 
//   										 "20111123003205943" );
   		
   		String sUser = String.format( "USER_ID=%s&SUBSCRIBER_ID=PID2843110&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s"   
   		                                                                , SIMClientHandlerLGU.cont_no
      	                                                                , SIMClientHandlerLGU.dmc_id
      	                                                                , SIMClientHandlerLGU.so_id
      	                                                                , SIMClientHandlerLGU.getSessionID() );
   		String sOption 	= String.format( "&TYPE=%d&page=%d&max=%d&gb=%d", 1, (page+1), query_max, mode);
//   		ac_search_songlist_utf.asp?USER_ID=1347299446&SUBSCRIBER_ID=PID2843110&DMC_ID=7&SO_ID=BSI&SESSION_ID=20111123003205943&TYPE=1&max=99&	
//   		String sForm = String.format("gb=%d", mode+2 );
   		
   		if ( p.Parser ( DataHandler.serverKYIP + "common2/" + sASP + sUser + sOption , contentList, contentTitleList, contentSingerList ) )
   		{	
   			contentList.get(0);
   		}
   		
   		Log.d("request", "data size " + contentList.size() );
   
   		
   		max_page = ( p.nMaxItems /lines) + ( (p.nMaxItems %lines) != 0 ? 1 :0 );
   		max_items = p.nMaxItems;
    }
    
   
    /**
     * 장르 리스트를 얻어옴 
     * @param mode
     */
    private void requestGenreData(int mode, int page)
    {
   		XmlParser p = new XmlParser();
   		
   		contentList 		= new ArrayList<Integer> ();
   		contentTitleList	= new ArrayList<String>  ();
   		contentSingerList	= new ArrayList<String>  ();
   		
   		String sASP = String.format( "GetGenreSongList_utf.asp?");
//   		String sUser = String.format( "USER_ID=%s&SUBSCRIBER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", 
//   										"1347299446", 
//   		                                    "PID2843110", 
//   										7, "BSI", 
//   										"20111123003205943" );
   		
   		String sUser = String.format( "USER_ID=%s&SUBSCRIBER_ID=PID2843110&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s"   
   		                                                                , SIMClientHandlerLGU.cont_no
      	                                                                , SIMClientHandlerLGU.dmc_id
      	                                                                , SIMClientHandlerLGU.so_id
      	                                                                , SIMClientHandlerLGU.getSessionID() );
   		
   		String sOption = String.format( "&TYPE=%d&page=%d&max=%d&genre=%d", 1, (page+1), query_max, mode); 
   	
 
   		
   		
   		if ( p.Parser (   DataHandler.serverKYIP + "common2/"+ sASP + sUser + sOption , contentList, contentTitleList, contentSingerList ) )
   		{
   			contentList.get(0);
   		}
   		Log.d("request", "data size " + contentList.size() );
   		max_page = (p.nMaxItems/lines) + ( (p.nMaxItems%lines) != 0 ? 1 :0 );
   		max_items = p.nMaxItems;
    }
     
   // s_cd = (0, sno) , ("02", title), ("03", gasu)
    private void requestSearchData( int type, String key, int page)
    {
    	//bgkim 141021 직접검색 시 검색어가 비어있으면 리스트를 뿌리지 않는다
    	if (key.equals("") || key == null) {
    		return;
    	}
    	
    	if ( DataHandler.isReachable == false)
    	{
    		Global.Inst().app.doMenu(2);
    		return;
    	}
 
    	
    	
    	
   		XmlParser p = new XmlParser();
       		
   		contentList 		= new ArrayList<Integer> ();
      	contentTitleList	= new ArrayList<String>  ();
      	contentSingerList	= new ArrayList<String>  ();
     // 	SIMClientHandler.cont_id       
       		
      	String sASP = String.format( "ac_search_songlist_utf.asp?");
      	String sUser = String.format( "USER_ID=%s&DMC_ID=%d&SO_ID=%s"   , SIMClientHandlerLGU.cont_no
      	                                                                , SIMClientHandlerLGU.dmc_id
      	                                                                , SIMClientHandlerLGU.so_id);
      	String s_cd = String.format("%02d", type+2 );
//      	str = URLEncoder.encode(str);
//    public static String utf82euc(String str) 
      	String sOption = String.format( "&TYPE=%d&page=%d&max=%d&s_cd=%s&s_value=%s", 1/*type*/, (page+1), query_max, s_cd, URLEncoder.encode(key) /*utf82euc(key)*/ );
      	if ( p.Parser (  DataHandler.serverKYIP + "common2/"  + sASP + sUser + sOption , contentList, contentTitleList, contentSingerList ) )
     	{
       			contentList.get(0);
       	}
       		
       	Log.d("request", "data size " + contentList.size() );
   		max_page = (p.nMaxItems/lines) + ( (p.nMaxItems%lines) != 0 ? 1 :0 );
   		max_items = p.nMaxItems;
    }
    
    
    
    void drawEmptyList( Canvas cv)
    {
   
    	final Paint p = new Paint();
        p.setAntiAlias(true); // 테두리를 부드럽게한다
        //p.setColor(backgroundColor);
        //p.setColor(Color.WHITE);
       
		
		int row = Global.DPFromPixel(33 + 2);
        
        if ( hasFocus() )
        	p.setColor(Color.WHITE);
        else
        	p.setColor(Color.RED);
       
        
       	cv.drawBitmap ( bmFocusDim, arrow_width+4, 8 + curPos*row -4, null ); 		// 버퍼에 그리기 
        if (/*!KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) &&*/ hasFocus() )  
        {
       		//draw focus
      		cv.drawBitmap ( bmFocus, 	arrow_width+4, 8 + curPos*row -4, null ); 		// 버퍼에 그리기 
        }
      
        
        int y = 0;
        int typo_y = y +  Global.DPFromPixel( TYPO_Y);//font ;
        int x = 95 *2;
        
        p.setTextSize(	 Global.DPFromPixel( FONT_SIZE) ); 
       	p.setColor( 0xFFA0E1E1);
       	cv.drawText( "항목이 없습니다", Global.DPFromPixel(x), typo_y, p );
        	
    }
        
        
   
    /**
     * 
     * @param 
     */
    void drawList( Canvas cv)
    {
    	final Paint p = new Paint();
        p.setAntiAlias(true); // 테두리를 부드럽게한다
        //p.setColor(backgroundColor);
        //p.setColor(Color.WHITE);
       
		
		int dpRow = Global.DPFromPixel( ROW_HEIGHT );
        if (text != null) {
//			canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
//			FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
        }

        
        if ( hasFocus() )
        	p.setColor(Color.WHITE);
        else
        	p.setColor(Color.RED);
       
        
       
//        boolean b = this.hasWindowFocus();
        
        
        cv.drawBitmap ( bmFocusDim, arrow_width+4, 8 + curPos* dpRow -4, null ); 		// 버퍼에 그리기 
        if ( /*!KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) &&*/ hasFocus() )  
        {
       		//draw focus
      		cv.drawBitmap ( bmFocus, 	arrow_width+4, 8 + curPos* dpRow -4, null ); 		// 버퍼에 그리기 
        }
        
      	
        
        if ( max_page != 0)
        {
       	
        	switch ( arrow_down )
        	{
        	case 0:
  	        	cv.drawBitmap ( bmArrowLeft, 	0, 150,  null ); 		// 버퍼에 그리기 
  	        	cv.drawBitmap ( bmArrowRight, 	getMeasuredWidth()-arrow_width, 150,  null ); 		// 버퍼에 그리기 
  	        	break;
  	        	
        	case 1:
  	        	cv.drawBitmap ( bmArrowLeftActive, 	0, 150,  null ); 		// 버퍼에 그리기 
  	        	cv.drawBitmap ( bmArrowRight, 	getMeasuredWidth()-arrow_width, 150,  null ); 		// 버퍼에 그리기 
  	        	break;
        	case 2:
  	        	cv.drawBitmap ( bmArrowLeft, 	0, 150,  null ); 		// 버퍼에 그리기 
  	        	cv.drawBitmap ( bmArrowRightActive, 	getMeasuredWidth()-arrow_width, 150,  null ); 		// 버퍼에 그리기 
  	        	break;
        		
 
        	}
        }
        	
        //cv.drawRect( 0, curPos*row, width, curPos*row+row, p);
        for ( int i = 0; i < lines; i++ )
        {
        	int dpY = ( dpRow * i);
        	int dpNumber_y = dpY +  Global.DPFromPixel( NUMBER_Y);//font ;
        
        	
       		String[] columns = {"sno","title", "artist"};	
       		int idx = ( /*cur_page * lines +*/ i);
       		int sno = 0;
       		
       		if ( contentList != null && ( idx < contentList.size() ) )
       		{
       			sno = contentList.get( idx );
       			columns[1] = contentTitleList.get(idx);
       			columns[2] = contentSingerList.get(idx);
      // 		Database.Inst().query_song_info( sno, columns);	
       		}
        
       		
  	      	if ( sno > 0)
        	{
  	      		int x = 30;
  	      		if ( show_rank )
  	      		{
  	      			drawRankNumber( cv, arrow_width+ (x-15), dpNumber_y,   ((cur_page*lines) + idx+1) );
  	      			x += (50 * 2);
  	      		}
        	
  	      		drawSongNumber( cv, arrow_width+x, dpNumber_y,  (sno) );
        		x += 90*2;
        		
        		
        		p.setTextSize(	 Global.DPFromPixel( FONT_SIZE ) ); 
        		p.setColor( 0xFFA0E1E1);
        		cv.drawText( columns[1], arrow_width+ Global.DPFromPixel(x),  dpY + Global.DPFromPixel(TYPO_Y), p );
        	
        	
        		
        		p.setTextSize(	 Global.DPFromPixel( FONT_SIZE -2) ); 
        		p.setColor( 0xFFD0D0D0);
        		cv.drawText( columns[2], arrow_width+ Global.DPFromPixel(SINGER_X), dpY +Global.DPFromPixel(TYPO_Y), p); 
//        		cv.drawText("*", 955, y, p); 	
        	}
        }
       
    }
    
    /*
     * 실제로 화면에 그리는 영역으로 View 를 상속하고 이 메소드만 구현해도 제대로 보여지게 된다.
     * 
     * 그릴 위치는 0,0 으로 시작해서 getMeasuredWidth(), getMeasuredHeight() 까지 그리면 된다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onDraw(Canvas canvas) {
    	
     /* 
    	
    	int k = 0;
    	if ( k == 0 )
    		return;
      */  
    	
//    	Log.w(TAG,"onDraw("+canvas+")" + (++redraw_count) );
    	
    	canvas.drawBitmap(bitmapMemory, arrow_width, 0, null );
    	
        if ( m_modeName.equals("list_familyfavorite") && ( contentList==null || contentList.size() == 0) )
        {
         	if ( bmNothing != null )
         		canvas.drawBitmap ( bmNothing, arrow_width+100, 50, null ); 		// 버퍼에 그리기 
        }
        else
        if ( contentList== null || contentList.size() == 0)
        {
        	drawEmptyList(canvas);
        }
        else
        {
    		drawList(canvas );
    	}
        
        if ( titleBarView != null )
        {
        	titleBarView.setPage( (cur_page+1), max_page);
        }
   	
    	super.onDraw(canvas);		// draw super
    }

    
    
    private boolean fireCommand()
    {
    	if ( contentList == null)
    		return false;
    
    	if ( curPos >= contentList.size())			// size check 
    		return false;
   		
  		int sno = contentList.get(  /*(cur_page*lines) +*/ curPos );
  		if ( sno > 0 )
  		{
  			//bgkim 첫 이벤트 팝업이 떠있는 상태에서는 반주곡 상세메뉴를 띄우지 않음
  			if (Global.Inst().app.m_bShowADPopup == true) {
  				return false;
  			}
  			
  			ContextMenuView popupview = new ContextMenuView(contax);
  			popupview.setCurrentSongNumber( (sno) );
  			if ( m_modeName.equals("list_familyfavorite"))
  			{
  				popupview.setSubMenu("애창곡 삭제");
  			}
  			
  			popup = new PopupWindow( popupview, Global.DPFromPixel(400), Global.DPFromPixel(400), true);
  			popup.setOutsideTouchable(true);
  			popup.setBackgroundDrawable(new BitmapDrawable() );
  			
//     		popup.setAnimationStyle(R.anim.popup_show);
/*
       		popupview.setOnKeyListener( new OnKeyListener() 
        	{
        				@Override
				        public boolean onKey(View v, int keyCode, KeyEvent event) 
        				{
//        					((SelectView)popup.getContentView()).onKeyDown(keyCode, event);
       // 					if ( ((SelectView)popup.getContentView()).isQuit( ) )
      //  					{
     //   						popup.dismiss();			// 다시 수행 금지 
    //    						return  true;	
   //     					}
        					return false;
        				}
        	});
*/        			
	
  			
  			popup.setOnDismissListener(this);
  			//popup.showAsDropDown(this,  0, 0);
  			int y = 16+ 50 + ( curPos * (35*2)) ;
  			popup.showAtLocation(this, Gravity.CENTER, Global.Inst().DPFromPixel(270*2), Global.Inst().DPFromPixel(y) );

  			
  			
  			
  			popupview.setSpot();
  			popupview.setParent(popup);		// 자체 종료를 위해 부모를 등록해 준다.
  		
  		}
  		
   		return true;
    }
    
    @Override
    public void onDismiss()
    {
    	/* 예약시 하단에 시작 표출을 위한 것 */ 
    	
        if ( ViewManager.Inst().lpBottomMenu != null)
        	ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
        
       
        
        if ( DataHandler.retString.length() > 0)
        {
        	MessageView popupview = new MessageView(contax);
        	popup = new PopupWindow( popupview , Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height), true);		// full 화면을 채워 마우스 포인터를 못가게 한다. 
	
        	popupview.setText( DataHandler.retString);
        	DataHandler.retString = "";
		
        	popup.showAtLocation(this, Gravity.CENTER , 0, 0);	
        	popupview.setSpot();
        	popupview.setParent( popup );		// 종료하기위하여 
        	
        }
        
        
        if ( m_modeName.equals("list_familyfavorite"))
       {
       
  	   		ArrayList<Integer>temp 		= new ArrayList<Integer> ();
   	   		contentList 		= new ArrayList<Integer> ();
   	   		contentTitleList	= new ArrayList<String>  ();
   	   		contentSingerList	= new ArrayList<String>  ();
           	
   	   		int result = DataHandler.GetRegistFavoriteSong(SIMClientHandlerLGU.cont_no, temp);
   	   		if ( result == 1 )	// succesㅓs
   	   		{
   	   			MakeList( temp, contentList, contentTitleList, contentSingerList, cur_page);
   	   			max_page = ( temp.size() /lines) + ( (temp.size() %lines) != 0 ? 1 :0 );
   	   			max_items = temp.size();
   	   		}
   	   		
   	   		
   	   		invalidate();
   	   		
       }
       
 
    }
    
    
    /**
     * 
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    /*	
   		if ( KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) == true )
   		{
        	return super.onKeyDown(keyCode, event); 
   		}
    */	
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
        switch (keyCode)
        {
        	case KMsg.MSG_KBD_UP:	
        	case KMsg.MSG_KBD_DOWN : 
        	case KMsg.MSG_KBD_LEFT:
        	case KMsg.MSG_KBD_RIGHT:	SoundManager.getInstance().playSound(1);	break;
        	case KeyEvent.KEYCODE_ENTER:SoundManager.getInstance().playSound(2);	break;
        }
        switch ( keyCode )
        {
        case KMsg.MSG_KBD_UP:
        						if ( curPos == 0 && headerView != null)		// headerView가 있다면 포커스를 올린다.
        						{
        	
    								ViewManager.Inst().lpSongSearchListView.doRequestData( 0+2, "");
    								invalidate();
        							ATVKaraokeActivity.edit.setText("");
        							
        							//headerView.setSpot();
        							ATVKaraokeActivity.edit.requestFocus();
        							this.setFocusable(false);
        							break;
        						}
        
        						if ( contentList != null) {
	        						if (curPos > 0)	
	        						{
	        							curPos--; 
	        							break;
	        						}
        						}
        						else 
        						{
        							if ( contentList == null)
        								break;
        						

        						/** 2014.9.17*/		//	curPos = (contentList.size()-1);		// last pos
        							curPos = ( lines -1 );

        							if ( curPos < 0 )
        							{
        									curPos = 0;
        									break;
        							}
        						}

        						
        						 
        case KMsg.MSG_KBD_LEFT:	if ( cur_page > 0)
        							cur_page--;		//	cur_page-= lines;
        						else
        						{
        							if (max_page != 0)
        								cur_page = (max_page-1);		//	cur_page-= lines;
        							else
        								max_page = 0;
        						}
        
        						doReq( m_modeName );
        						if ( contentList != null )
        						{
        							if ( cur_page == (max_page-1))
        							{
        							    curPos = (contentList.size()-1);		// last pos
        							}
        						}
        						break;
        						
        						
        case KMsg.MSG_KBD_DOWN: 
        	
        						if ( contentList == null)
        							break;
        	
        						if (curPos < (contentList.size()-1))
        						{
        							curPos++;
        							break;
        						}
        						else
        							curPos = 0;
        
       case KMsg.MSG_KBD_RIGHT: if ( (cur_page+1)  < max_page )
        							cur_page++;//	cur_page += lines;
       							else
       								cur_page = 0;
       
        						if ( (((cur_page)*lines)+curPos) >= (max_items) )
        						{
        							curPos = ((max_items%lines)-1)/*index가 0부터*/;
        							if ( curPos < 0)
        							{
        								curPos = 0;
        							}
        						}
        						doReq( m_modeName );
        						break;

        						
       
 
        			

        // 종료 
      	case KMsg.MSG_KBD_STOP: //Global.Inst().app.doMenu( 99 );
      							return super.onKeyDown( KeyEvent.KEYCODE_BACK, event); 
      						
      
      	case KeyEvent.KEYCODE_DPAD_CENTER:
        case KeyEvent.KEYCODE_ENTER:		fireCommand();	break;
        
        // parent가 처리하도록 한다. 
        default:
        	return super.onKeyDown(keyCode, event); 
        }
        
        invalidate();
        return true;
    }
    
    
   public Dialog reservedDialog()
   {
	   Dialog dialog = null;
    	
	   CustomDialog.Builder customBuilder = new CustomDialog.Builder(contax);
	   		customBuilder.setTitle("Custom title")
					.setMessage("Custom body")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) 
						{
					//		KYStbKaraokeActivity.this.dismissDialog(CUSTOM_DIALOG);
						}
					})
					.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
				
	    dialog = customBuilder.create();
	    return dialog;
   }
    
   @Override
   public boolean onTouchEvent(MotionEvent event) {

	   Log.w(TAG,"onTouchEvent("+event+")");
       mouseX =(int) event.getX();
       mouseY =(int) event.getY();
       
       int cur = -1;
       arrow_down = 0;
      
       if ( contentList == null)
    	   return false;
    	   
       
       int l = Math.min(lines ,   (contentList.size()) );		// last pos
       for ( int i = 0; i < l; i++)
       {
    	   if ( rcRect[i].contains(mouseX, mouseY ) )
    		   cur = i;
       }
      
       if ( rcArrowLeft.contains(mouseX, mouseY) )
       {
    	   if ( event.getAction() == MotionEvent.ACTION_DOWN )
    	   {
    		   arrow_down = 1;
    		   invalidate();
    	   }
    	   else
    	   if ( event.getAction() == MotionEvent.ACTION_UP)
    	   if ( cur_page > 0)
    	   {
    		   cur_page--;		//	cur_page-= lines;
    		   doReq( m_modeName );
    		   invalidate();
    	   }
    	   return true;
       }
       if ( rcArrowRight.contains(mouseX, mouseY) )
       {
    	   if ( event.getAction() == MotionEvent.ACTION_DOWN )
    	   { 
    		   arrow_down = 2;
    		   invalidate();
    	   }
    	   else 
    	   if ( event.getAction() == MotionEvent.ACTION_UP)
    	   if ( (cur_page*lines+lines)  <  max_page*lines )
    	   {
    		   cur_page++;		//	cur_page += lines;
    		   if ( (cur_page*lines+curPos) >= (max_page*lines) )
    		   {
    			   	curPos = ( (max_page*lines)%lines)-1/*index가 0부터*/;
    			  
    
    		   }
    		   
    		   doReq( m_modeName );
    		   invalidate();
    	   }
    	   return true;
       }
   
   	
       switch(event.getAction()) {
       case MotionEvent.ACTION_UP:
           backgroundColor = Color.RED;
           if ( cur >= 0 )
           {
        	   if ( contentList != null)
        		   curPos = cur;
        	   			   	
        	  // 예외  
        	   if ( m_modeName.equals("songsearch"))
        	   {
        		   ViewManager.Inst().lpSongSearchListView.setFocusable(true);	//풀어준다. 
        		   ViewManager.Inst().lpSongSearchListView.setSpot();
        	   }
           	   fireCommand();
           }
           invalidate();
           break;
       case MotionEvent.ACTION_DOWN:
           backgroundColor = Color.YELLOW;
           text = "Clicked!";
           if ( cur >= 0 )
           {
        	   curPos = cur;
           }
           invalidate();
           break;
           
       case MotionEvent.ACTION_MOVE:
           backgroundColor = Color.TRANSPARENT;
           text = "Moved!";
           break;
          
       case MotionEvent.ACTION_POINTER_UP:
           backgroundColor = Color.BLUE;
           text = "Outside";
           break;
       }
       
       return super.onTouchEvent(event);
   } 
  
   
    private void drawSongNumber( Canvas c, int dpX, int dpY, int sno )
    {
		//canvas.drawText( strSNO, Global.Inst().DPFromPixel(247), Global.Inst().DPFromPixel(65), p );
    	final int dpWidth  = bmNumber.getWidth() / 10;
    	final int dpHeight = bmNumber.getHeight();

    	String strSNO = String.format("%05d", sno) ;
        Rect src = new Rect(0, 0, dpWidth, dpHeight);
        Rect dst = new Rect(0, 0, dpWidth, dpHeight);

		c.save(); // 현재 변환식을 저장
		c.translate(  dpX, dpY );
		for ( int i = 0; i < 5; i++)
		{
			
			int n = strSNO.charAt(i);
			src.left = dpWidth*(n-48);
			src.right= ((dpWidth*(n-48))+dpWidth);
			c.drawBitmap(bmNumber,	src,
									dst, null );
			
			c.translate(  (dpWidth/16)*14, Global.DPFromPixel(0) );
		}
	   	c.restore();
    }
    
    private void drawRankNumber( Canvas c, int dpX, int dpY, int rank )
    {
		//canvas.drawText( strSNO, Global.DPFromPixel(247), Global.DPFromPixel(65), p );
    	final int dpWidth  = bmRankNumber.getWidth() / 10;
    	final int dpHeight = bmRankNumber.getHeight();
    	
    	String strSNO = String.format("%3d", rank) ;
        Rect src = new Rect(0, 0, dpWidth, dpHeight);
        Rect dst = new Rect(0, 0, dpWidth, dpHeight);

		c.save(); // 현재 변환식을 저장
		c.translate(  dpX, dpY );
		for ( int i = 0; i < strSNO.length() ; i++)
		{
			int n = strSNO.charAt(i);
			src.left = dpWidth*( n-48) ;
			src.right = (dpWidth* (n-48)) + dpWidth;
			if ( n != 0x20 )
			{
				c.drawBitmap(bmRankNumber,	src, dst, null );
			}
			
			c.translate( dpWidth, Global.DPFromPixel(0) );
		}
	   	c.drawBitmap( bmRank, 0, 2, null);
	   	c.restore();
    }
 
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        	case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        	case MeasureSpec.AT_MOST:     heightSize = bmBG.getHeight();	break;	
        	case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        	case MeasureSpec.UNSPECIFIED: widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        	case MeasureSpec.AT_MOST:     widthSize = bmBG.getWidth()+(arrow_width*2);		break;
        	case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }
    
   
    /**
     * 
     * @param snoSource
     * @param snoList
     * @param titleList
     * @param singerList
     */
    private void MakeList(ArrayList<Integer> snoSource, 
    								ArrayList<Integer> snoList, ArrayList<String> titleList, ArrayList<String> singerList,
    								int page
    					)
    {
		String[] columns = {"sno", "-----------------------------", "-----"};
		for ( int i = (page*lines); i < snoSource.size() && i < (page*lines) + lines; i++)
		{
			int sno = snoSource.get(i);
			if ( !Database.Inst().query_song_info( sno , columns) )
			{
				columns[0] = "";
				columns[1] = "";
				columns[2] = "";
			}
			snoList.add( sno );
			titleList.add(columns[1]);
			singerList.add(columns[2]);
		}	
    }
	   
   
 
}