package com.kumyoung.stbui;

import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KEnvr;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOS;
import isyoon.com.devscott.karaengine.KOSD;
import isyoon.com.devscott.karaengine.KPlay;
import isyoon.com.devscott.karaengine.KTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//import isyoon.com.devscott.karaengine.Disp.disp_t;

import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;
import com.kumyoung.gtvkaraoke.CustomDialog;
import com.kumyoung.gtvkaraoke.DataHandler;
import kr.kumyoung.gtvkaraoke.R;

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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.view.animation.*;


public class MainMenuView extends AbstractTV {


	private static final String TAG = MainMenuView.class.getSimpleName();
    private String 		text = null;
    //private Bitmap 		bmBG;// = new Bitmap;
	PopupWindow 		popup = null;
//	OSDThread			mThread;
	

	static int bakPos = 0;
    private int 		id[] = 
    { 	
    		R.drawable.main_menu_new,
    		R.drawable.main_menu_top,
    		R.drawable.main_menu_lately,
    								
    		R.drawable.main_menu_genre,
    		R.drawable.main_menu_theme1,
    		
    		R.drawable.main_menu_theme2,
    								
    		R.drawable.main_menu_search,
    		R.drawable.main_menu_favorite,
    		R.drawable.main_menu_guide
    };
    								
    
    @Override
    public String className()
    {
        return TAG;
    }
 
   
    public MainMenuView(Context context) {
        super(context);
        _prepare(3, 3, 0);
    }
    public MainMenuView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MainMenuView(Context context,AttributeSet attrs,int defStyle) {
    	
        super(context,attrs,defStyle);
        
        _prepare(3, 3, 0);
        SetPos( bakPos);		// restore savePos
        
        this.text = attrs.getAttributeValue(null,"text");
        
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_bg);
        int width = bmBG.getWidth();
        int height = bmBG.getHeight();
        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
        	canvasMemory.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
   		

   		Bitmap []bmButton = new Bitmap[MAX_ITEMS];
   		for( int i = 0; i < MAX_ITEMS; i++)
   		{
        	bmButton[i] = BitmapFactory.decodeResource( contax.getResources() , id[i] );
   		}
   			
   		
        {
            Bitmap bitmap = BitmapFactory.decodeFile( DataHandler.getInstance().getExternalPath() + "theme1.png");
            if ( bitmap!= null ) {
                bitmap.setDensity( 200/* bm.getDensity() */);
                bmButton[4]= bitmap;
            }
            
            Bitmap bitmap2 = BitmapFactory.decodeFile( DataHandler.getInstance().getExternalPath() + "theme2.png");
            if ( bitmap2 != null ) {
                bitmap2.setDensity( 200/* bm.getDensity() */);
                bmButton[5]= bitmap2;
            }
        }       
   		
        btn = new button[MAX_ITEMS];
        int y = 0;
        for ( int i = 0; i < MAX_ITEMS; i++ )
        {
			int _x = 404 * (i % 3);
			int _y = ((i / 3) * 92);
			int _w = 400;
			int _h = 100;
        	
			// if (!isInEditMode()) {
			// Log.d(TAG, "MainMenuView()" + "[RECT]" + "(" + _x + "," + _y + "," + _w + "," + _h + ")" + ":" + getContext().getResources().getResourceEntryName(id[i]));
			// }
			
        	btn[i] = new button( _x,
        						 _y,
        						 _w,
        						 _h,
        						bmButton[i],
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.main_menu_f), null );
       		btn[i].drawTranslate(canvasMemory);
        }
        	
	   
	    /***/
      
        /*
        final Paint p = new Paint();
       	p.setColor( 0x7fffffFF );   	//빨간 계열 비트맵으로 그린다.	
       	
        canvasMemory.drawLine(0, 0,      width, height, p);
        canvasMemory.drawLine(0, height, width, 0, p);
        */
               
        if ( ViewManager.Inst().lpBottomMenu != null)
        {
        	ViewManager.Inst().lpBottomMenu.isShowBack = false;
        	ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
        }
        
        
//        if ( DataHandler.getInstance().have_userinfo == true )
 //           Reloaded();
 
    }
    
/*    
    @Override
    protected void onDraw(Canvas canvas) 
    {
 //   	Log.d("ke", "inva  onDraw" +  ++redraw_count ); 
    	
//        if (text != null) {
//           p.setColor(Color.TRANSPARENT);
//            canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
//            FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
 //       }
		canvas.drawBitmap(bitmapMemory, 0, 0, null );
		
//		if ( bmEvent != null )
 //       	canvas.drawBitmap ( bmEvent, Global.Inst().DPFromPixel(650), 0, null ); 		// 버퍼에 그리기 
		
		//곡 번호 
//		canvas.drawText( strSNO, Global.Inst().DPFromPixel(247), Global.Inst().DPFromPixel(65), p );
		//곡 제목 
 //       canvas.drawText( strTitle, 	Global.Inst().DPFromPixel(323), 	Global.Inst().DPFromPixel(65) , p );
        //canvas.drawText( strArtist,	620, 45 , p );
//        canvas.drawText( this.strList, Global.Inst().DPFromPixel(530), Global.Inst().DPFromPixel(65), p);
        
        
    	Boolean b = hasFocus();
    	if ( b )
    	{
    		if ( !KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) )
    		{
    			//draw focus
    			btn[curPos].draw(canvas, true );
    		}
    		
    	}
        
        
        
        final Paint p = new Paint();

/// p.setColor(backgroundColor);
// canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
        
        p.setAntiAlias(true); // 테두리를 부드럽게한다
		p.setColor(Color.WHITE);
		p.setTextSize( 20 );//Global.Inst().DPFromPixel(32) );
        canvas.drawText("["+redraw_count+"]" + "["+mouseX+","+mouseY+"]", 10, 15, p ); // 왼쪽 아래를 0,0 으로 보고있음
        
        Log.w(TAG,"onDraw("+canvas+")");
        //super.onDraw(canvas);
          
    }
*/    
    
    
    /*
     * 현재 view 가 focus 상태일때 key 를 누르면 이 메소드가 호출됨.
     * 즉 이 메소드를 사용하려면 setFocusable(true) 여야함. 
     * 
     * 그리고 super 메소드에서는 기본적인 키 작업(예를들면 BACK 키 누르면 종료)을 처리하기 때문에 일반적으로 return 시에 호출하는게 좋다.
     * 만약 기본적인 작업을 하지않게 하려면 super 함수를 호출하지 않아도 된다.
     * 
     * 다른 event 메소드들도 유사하게 동작한다.
     */
     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
       
        if ( Global.Inst().app.key_accept == false)
            return false;
       
        switch ( keyCode )
        {
            case KeyEvent.KEYCODE_BACK:
                SoundManager.getInstance().playSound(2);
                //doCommand(keyCode);
   		       
    			Global.Inst().app.doMenu( 10 );
                return true;
        
       		default:
        		    break;
        }
 
        
        
        return super.onKeyDown(keyCode, event); 
     }

    /* 
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
        switch ( keyCode )
        {
        case KMsg.MSG_KBD_LEFT:
        				if( curPos > 0 )
        					curPos--; 
        				else
        					curPos = (MAX_ITEMS-1);
        				break;
        				
        case KMsg.MSG_KBD_RIGHT: 
        				if ( curPos < MAX_ITEMS-1)
        					curPos++; 
        				else
        					curPos = 0;
        				break;
        				
        case KMsg.MSG_KBD_UP:		
        				curPos -= COLS;
        				if ( curPos < 0)
        					curPos += MAX_ITEMS;
        				break;
        				
        case KMsg.MSG_KBD_DOWN : 
        				curPos+= COLS;
        				if( curPos >= MAX_ITEMS )
        					curPos -= MAX_ITEMS;
        				break;
        
        
        case KeyEvent.KEYCODE_ENTER : 
        				doCommand();
        				
        				invalidate();
        				return true;
        }
       
        invalidate();
        return super.onKeyDown(keyCode, event); 
    }
*/ 
    /*
     * 이 view 에 touch 가 일어날때 실행됨.
     * 
     * 기본적으로 touch up 이벤트가 일어날때만 잡아내며 
     * setClickable(true) 로 셋팅하면 up,move,down 모두 잡아냄
     */

  
    /*
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
        
 //       invalidate();
        this.postInvalidate();
    }
   */ 

    
   
    
   
    @Override
    public void onSubDraw( Canvas canvas )
    {
    	 
    }

     
    Boolean bLogin = false;
    @Override
    public void doCommand(int keyCode) 
    {
/*    	
    	if ( DataHandler.have_ticket == 0 )    	{
    		BuyTicketView popupview = new BuyTicketView(contax);
       		popup = new ModalWindow( popupview , Constants.width, 540, true);		// full 화면을 채워 마우스 포인터를 못가게 한다. 
            			
      		//popup.showAsDropDown(this,  0, 0);
       		popup.showAtLocation(this, Gravity.CENTER , 0, 0);	
       			
            //popup.setOutsideTouchable(false); //윈도우 밖에 터치를 받아들일때 사용한다. 
       		popupview.setSpot();
       		popupview.setParent( popup );		// 종료하기위하여 
       		
            //this.setFocusable(false);
    	}
 */   
    	
       	switch ( GetPos() )
       	{
       		case 0:	KPlay.Inst().play_send( KMsg.MSG_KBD_NEWSONG_SONGLIST );break;
       		case 1:	KPlay.Inst().play_send( KMsg.MSG_KBD_TOP100_SONGLIST );break;
       		case 2:	KPlay.Inst().play_send( KMsg.MSG_KBD_LATELY_SONGLIST );break;
       			
      		// 장르선택 
       		case 3:			// MSG_KBD_GENRE_SONGLIST 
       		{
        			SelectGenreView popupview = new SelectGenreView(contax);
        			popup = new PopupWindow( popupview , Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height), true);		// full 화면을 채워 마우스 포인터를 못가게 한다. 
        			//popup.setOutsideTouchable(true); 윈도우 밖에 터치를 받아들일때 사용한다. 
        			//popup.setBackgroundDrawable(new BitmapDrawable() );	윈도우 밖에 터치를 받아들이기 위해 사용 
        			//popup.setAnimationStyle(R.anim.popup_show);
        			/*
        			popupview.setOnKeyListener( new OnKeyListener() 
        			{
        				@Override
				        public boolean onKey(View v, int keyCode, KeyEvent event) 
        				{
        		//			((SelectGenreView)popup.getContentView()).onKeyDown(keyCode, event);

        		//			if ( ((SelectGenreView)popup.getContentView()).isQuit( ) )
        		//			{
        		//				popup.dismiss();
        		//			}
        					return  false;	
        				}
        			});
        			*/
        			//popup.showAsDropDown(this,  0, 0);
        			popup.showAtLocation(this, Gravity.CENTER , 0, 0);	
        			
        			popupview.setSpot();
        			popupview.setParent( popup );		// 종료하기위하여 
        			
       		}
   			break;
   			
       		case 4: 	// theme1
       				KPlay.Inst().play_send( KMsg.MSG_KBD_GENRE_THEME1_SONGLIST ); break;		
       				
       		case 5: /*
   			 		SelectThemeView popupview = new SelectThemeView(contax);
        			popup = new ModalWindow( popupview, Constants.width, 540, true);
 	       			popup.showAtLocation(this, Gravity.CENTER , 0, 0);	
 	       			
        			popupview.setSpot();
        			popupview.setParent( popup );		// 종료하기위하여 
       			    */
       				KPlay.Inst().play_send( KMsg.MSG_KBD_GENRE_THEME2_SONGLIST ); break;		
       				
       		case 6: KPlay.Inst().play_send( KMsg.MSG_KBD_SEARCH_SONGLIST );		break;// 직접검색 
       		case 7: KPlay.Inst().play_send( KMsg.MSG_KBD_FAVORITE_SONGLIST );	break;
       		case 8: KPlay.Inst().play_send( KMsg.MSG_KBD_HELP );				break;
       		default:
       				break;
       	}
        		
//		this.setFocusable(false);
       	bakPos = GetPos();
   	}
   
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        	case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        	case MeasureSpec.AT_MOST:     heightSize = bitmapMemory.getHeight();	break;	
        	case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        	case MeasureSpec.UNSPECIFIED:    widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        	case MeasureSpec.AT_MOST:     widthSize = bitmapMemory.getWidth();		break;
        	case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }
        //Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
		Log.w(TAG, "onMeasure() " + "(" + widthMode + "," + heightMode + ")" + ":" + "(" + widthMeasureSpec + "," + heightMeasureSpec + ")" + "->" + "(" + widthSize + "," + heightSize + ")");
        setMeasuredDimension(widthSize, heightSize);
    }
    
    @Override
    public Boolean lostFocus(int keyCode)
    {
       	Log.d(TAG, "MAINMENU : FOCUS_LOST");
        ViewManager.Inst().lpAdView.setSpot(); 
        return true;
    }
    
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) 
    {
 	    super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) 
        {
            switch (direction) {
                case View.FOCUS_DOWN: Log.d(TAG, "MAINMENU: FOCUS_DOWN");                  break;
                case View.FOCUS_UP:   Log.d(TAG, "MAINMENU: FOCUS_UP");                    break;
                case View.FOCUS_LEFT:  // fall through
                case View.FOCUS_RIGHT: Log.e(TAG, "MAINMENU: FOCUS_LEFT/RIGHT");           break;
                case View.FOCUS_BACKWARD:	Log.d(TAG, "MAINMENU: FOCUS_BACKWARD");       	break;
                case View.FOCUS_FORWARD:     	Log.d(TAG, "MAINMENU: FOCUS_FORWARD");     	break;
                default:                	Log.d(TAG, "MAINMENU: FOCUS_DEFAULT");         	break;
            }
        }
    } 
    
    
    public void Reloaded ()
    {
 /* 
//    	if ( DataHandler.isReachable == false)
//    	{
//    		return;
//    	}
//    	
//    	
//        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_bg);
//        int width = bmBG.getWidth();
//        int height = bmBG.getHeight();
//        
//        // create memory 
//       	bitmapMemory = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//        canvasMemory = new Canvas(bitmapMemory);
//
//        // draw
//   		if ( bmBG != null )
//        	canvasMemory.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
//   		
//
//   		Bitmap []bmButton = new Bitmap[MAX_ITEMS];
//   		for( int i = 0; i < MAX_ITEMS; i++)
//        	bmButton[i] = BitmapFactory.decodeResource( contax.getResources() , id[i] );
//        
//   		try {
//            URL aURL = new URL( DataHandler.serverKYIP + DataHandler.theme1_image );
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bmButton[4] = BitmapFactory.decodeStream(bis);
//            //bmButton[4].setDensity(320);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//        } 
//   		
//        try {
//            URL aURL = new URL( DataHandler.serverKYIP + DataHandler.theme2_image );
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bmButton[5] = BitmapFactory.decodeStream(bis);
//            //bmButton[5].setDensity(320);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//        } 
//        
//        
//        btn = new button[MAX_ITEMS];
//        int y = 0;
//        for ( int i = 0; i < MAX_ITEMS; i++ )
//        {
//        	btn[i] = new button( ( (i*2) + 400*(i%3)),  
//        						  ((i/3)* 92), 
//        						  400, 100, 
//        						bmButton[i] ,
//        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.main_menu_f), null );
//        
//        	if ( i == 4 || i == 5)
//        	    btn[i].Indent(10);
//        
//        	//btn[i].Indent( 8);
//       		btn[i].drawTranslate(canvasMemory);
//        }
*/
    }

}

    
    