package com.kumyoung.stbui;




import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KEnvr;
import isyoon.com.devscott.karaengine.KMenu;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;


public class OSDView extends AbstractLV {
	
	private static final String TAG = OSDView.class.getSimpleName();
    private final String	strIdleMsg = "숫자키패드로 번호를 입력하세요";
	
    private String 		text = null;
    private Bitmap 		bmBG;// = new Bitmap;
    private Bitmap		bmFocus;
    private Bitmap 		bmNumber;
    
    private Bitmap		bmBookIcon;	
    
    private Context		contax = null;

    private int			SNO = 0;
    private String		strTitle = "";
    private String		strArtist= "";
    

    private final int number_x = (247 + 130);
    private final int number_y = 24;
    private final int title_x  = (323 + 190);
    private final int title_y  = (45 + 10); //65
    
    private final int focus_x  = (236 + 130);
    private final int focus_y  = (4); //39;
    private final int songlist_y = 56; // 64
    
    
    private final int fontSize = (12*2); 
    private final int fontLargeSize = (18*2); 
  
    private final int OSD_Y		= 40;
    private final int RSV_X = 950;
    
   
    
    private static int redraw_count = 0;
//	OSDThread			mThread;
    
   
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public OSDView(Context context) {
        super(context);
        Log.w(TAG,"CustomView("+context+")");
        contax = context; 
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public OSDView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        Log.w(TAG,"CustomView("+context+","+attrs+")");
        
        contax = context;
    }
   
   
    @Override
    public String className()
    {
        return TAG;
    }
 
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public OSDView(Context context,AttributeSet attrs,int defStyle) {
    	
        super(context,attrs,defStyle);

        this.text = attrs.getAttributeValue(null,"text");
        
        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);

        contax = context;
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.book_bg);
        bmFocus = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_input_f );
        bmNumber = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_num_b);

        bmBookIcon = BitmapFactory.decodeResource( contax.getResources(), R.drawable.book_icon );
		
    }
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
    	//Log.d("ke", "inva  onDraw" + SNO + ":" +  ++redraw_count ); 
        //Log.w(TAG,"onDraw("+canvas+")");
        
        final Paint p = new Paint();
        if ( KOSD.Inst().is(KOSD.eOSD_BOOK_EDIT) )
			p.setAlpha( (int)(255 * 1) );
        else
			p.setAlpha( (int)(255 * 0.7) );

        
 //       p.setColor(backgroundColor);
//        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
        
        p.setAntiAlias(true); // 테두리를 부드럽게한다
		p.setColor(Color.WHITE);
       
/*  
        if (text != null) {
//           p.setColor(Color.TRANSPARENT);
//            canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
            FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
        }
*/ 
		if ( bmBG != null )
        	canvas.drawBitmap ( bmBG, 0, Global.DPFromPixel(OSD_Y), p ); 		// 버퍼에 그리기 
	
		if ( strTitle.length() == 0)
		{
			p.setTextSize( Global.DPFromPixel( fontSize) );
			
			canvas.drawText( strIdleMsg, 	Global.DPFromPixel( title_x), 	
					Global.DPFromPixel(OSD_Y +title_y-2 ) , p );
		}
		else
		{
			p.setTextSize( Global.DPFromPixel( fontLargeSize ) );
			
			int measuredTextWidth  = (int) Math.ceil( p.measureText( strTitle ) ) ;
			
			canvas.clipRect( 0, 0, Global.DPFromPixel(880), bmBG.getHeight(), Op.REPLACE);
			//곡 제목 
			canvas.drawText( strTitle, 	Global.DPFromPixel(title_x), 	
										Global.DPFromPixel(OSD_Y+title_y ) , p );
			
			canvas.clipRect( 0, 0, bmBG.getWidth(), bmBG.getHeight(), Op.REPLACE);
		}

		
		p.setTextSize( Global.DPFromPixel( fontLargeSize) );
		
        /**
         * 
         */
    	if ( KOSD.Inst().GetBookRealCnt() > 0 )
    	{
            for (int n = 0; n < Math.min(KOSD.Inst().GetBookRealCnt(), 4) ; n++)
            {
            	String strList = String.format("%05d", KOSD.Inst().read_book(n) );
    			int dpX = Global.DPFromPixel( RSV_X ) + Global.DPFromPixel((70*2)*n);		
    			if ( bmBookIcon != null )
    				canvas.drawBitmap( bmBookIcon, dpX, Global.DPFromPixel(OSD_Y+ songlist_y -24), null );
    			
    			canvas.drawText( strList, dpX +Global.DPFromPixel((42)), Global.DPFromPixel( OSD_Y+ songlist_y), p);
            }	
    	}

    	/**
    	 * 
    	 */
        if ( KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) )
        {
        	if ( bmFocus != null)
        	{
        		canvas.drawBitmap ( bmFocus, Global.DPFromPixel( focus_x), Global.DPFromPixel(OSD_Y+ focus_y ), p ); 		// 버퍼에 그리기 
        	}
        }
        else
        {	
        }
        
        //곡 번호 
		drawSongNumber( canvas, SNO );
		
      
	
		/* count */		
		p.setTextSize( Global.DPFromPixel(40) );
    	String strCount = String.format("%02d",  KOSD.Inst().GetBookCnt() ) ;
    	
    	int x = bmBG.getWidth() - Global.DPFromPixel(120);
		canvas.drawText( strCount , x,  Global.DPFromPixel( OSD_Y+60),  p ); // 왼쪽 아래를 0,0 으로 보고있음
/*			
		if ( hasFocus() )
		{
			canvas.drawText("["+redraw_count+"]", 30, 30, p ); // 왼쪽 아래를 0,0 으로 보고있음
		}
*/		
        super.onDraw(canvas);
    }
    
    /*
     * 현재 view 가 focus 상태일때 key 를 누르면 이 메소드가 호출됨.
     * 즉 이 메소드를 사용하려면 setFocusable(true) 여야함. 
     * 
     * 그리고 super 메소드에서는 기본적인 키 작(예를들면 BACK 키 누르면 종료)을 처리하기 때문에 일반적으로 return 시에 호출하는게 좋다.
     * 만약 기본적인 작업을 하지않게 하려면 super 함수를 호출하지 않아도 된다.
     * 
     * 다른 event 메소드들도 유사하게 동작한다.
     */
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
        
 /*       
        // new method
        if ( !(KOSD.Inst().is(KOSD.eOSD_BOOK_EDIT)) && KEnvr.is(KEnvr.ENVR_IDLE)  ) {
            if (ViewManager.Inst().lpMenuView != null && 
                    keyCode == KeyEvent.KEYCODE_DPAD_LEFT || 
                    keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || 
                    keyCode == KeyEvent.KEYCODE_DPAD_UP ||   
                    keyCode == KeyEvent.KEYCODE_DPAD_DOWN  )   
            {
                Log.d(TAG, "MainMenu SetSpot");
                ViewManager.Inst().lpMenuView.onKeyDown(keyCode, event);
                return true;
            }
        }
*/        
        
        
        return super.onKeyDown(keyCode, event); 
    }
    
    /*
     * 이 view 에 touch 가 일어날때 실행됨.
     * 
     * 기본적으로 touch up 이벤트가 일어날때만 잡아내며 
     * setClickable(true) 로 셋팅하면 up,move,down 모두 잡아냄
     */
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
 //       invalidate();
        this.postInvalidate();
    }
    
    
    
    /**
     * @param _sno
     * @param _title
     * @param _artist
     */
    public void setInfo( int _sno, String _title, String _artist )
    {
    	this.SNO = _sno;
    	
    	if ( _title != null)
    		this.strTitle = _title;
    	
    	if ( _artist != null)
    		this.strArtist = _artist;
       
        Log.i(TAG, "OSDView setInfo: "+ _sno + " : " + _title); 
        
        this.postInvalidate();
    }
    
    
    public void UpdateSongList( )
    {
    	Log.d(TAG, "update songlist");
//    	this.strList = strList;
    	this.postInvalidate();
    }
    
    /*
    public void setStatusI( int mode )
    {
    	strTitle = strIdleMsg;
    	this.postInvalidate();
    }
    */
    private void drawSongNumber( Canvas c, int sno )
    {
		//canvas.drawText( strSNO, Global.Inst().DPFromPixel(247), Global.Inst().DPFromPixel(65), p );
    	final int width  = 26;// 13;
    	final int height = 40;// 20;
    	
    	int x = 0;
    	int y = 0;
    	String strSNO = String.format("%5d", sno) ;
    	
        Rect src = new Rect(0, 0, Global.DPFromPixel(width), Global.DPFromPixel(height) );
        Rect dst = new Rect(0, 0, Global.DPFromPixel(width), Global.DPFromPixel(height) );

		c.save(); // 현재 변환식을 저장
		c.translate(  Global.DPFromPixel(number_x), Global.DPFromPixel(OSD_Y+ number_y) );
		for ( int i = 0; i < 5; i++)
		{
			int n = strSNO.charAt(i);
			if ( n == 32)
			{
				src.left = Global.DPFromPixel(width*10); 
				src.right= Global.DPFromPixel(width*10+width);
			}
			else
			{
				src.left  = Global.DPFromPixel( width*(n-48) );
				src.right = Global.DPFromPixel( width*(n-48) + width );
				
				c.drawBitmap(bmNumber,	src, dst, null );
			}
			c.translate(  Global.DPFromPixel(width), Global.DPFromPixel(0) );
		}
	   	c.restore();
    }
   
    

 
    
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 진짜 크기 구하기
    	
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:     heightSize = (bmBG.getHeight()) + Global.DPFromPixel(OSD_Y);	break;	
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED:   widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = (bmBG.getWidth());		break;
        case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }
    
        
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) 
    {
 	    super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) 
        {
            switch (direction) {
                case View.FOCUS_DOWN: Log.d(TAG, "        : FOCUS_DOWN");                  break;
                case View.FOCUS_UP:   Log.d(TAG, "        : FOCUS_UP");                    break;
                case View.FOCUS_LEFT:  // fall through
                case View.FOCUS_RIGHT: Log.e(TAG,"        : FOCUS_LEFT/RIGHT");           break;
                case View.FOCUS_BACKWARD:	Log.d(TAG, "        : FOCUS_BACKWARD");       	break;
                case View.FOCUS_FORWARD:     	Log.d(TAG, "        : FOCUS_FORWARD");     	break;
                default:                	Log.d(TAG, "        : FOCUS_DEFAULT");         	break;
            }
        }
    } 

}