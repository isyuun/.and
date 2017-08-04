package com.kumyoung.stbui;



import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;
import isyoon.com.devscott.karaengine.KPlay;
import android.app.Instrumentation;

import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.DataHandler;
import kr.kumyoung.gtvkaraoke.R;
import com.kumyoung.stbcomm.SIMClientHandlerLGU;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class BottomMenu extends AbstractTV {
    
	private static final String TAG = BottomMenu.class.getSimpleName();
	
    private Bitmap 		bmBG;
    private Bitmap 		bmLogo;
    private Bitmap		bmNotice;
    
 //   final int WIDTH 	= Constants.width;
//    final int HEIGHT 	= 76;
    
    final int btn_short	= 200;
    final int btn_wide	= 140;
   
    final int FONT_H 	= 32;
    final int MESSAGE_X = 440; 
    public boolean  isShowBack = false;
    
    
    public BottomMenu(Context context) {
        super(context);
        _prepare( 6, 1, 0);
        contax = context;
    }
    
    public BottomMenu(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        _prepare( 6, 1, 0);
        //
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.footer_bg);
        bmLogo =  BitmapFactory.decodeResource( contax.getResources(), R.drawable.logo_kyno);
        bmNotice = BitmapFactory.decodeResource( contax.getResources(), R.drawable.notice_bg);
        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( bmBG.getWidth(), bmBG.getHeight(), Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
   		{
//	 		Paint paint = new Paint();
//          paint.setColor(Color.argb(0x80, 0, 0, 0));
//  		paint.setStyle(Paint.Style.FILL);
   			canvasMemory.drawBitmap ( bmBG, 0,0, null ); 		// 버퍼에 그리기 
   			
   			if ( bmLogo != null)
   			   canvasMemory.drawBitmap ( bmLogo,  Global.DPFromPixel(150), 0, null ); 		// 버퍼에 그리기 
  		}
   		
   		//DPfrom pixel 안먹음..   셋팅전인가?
		if ( bmNotice != null)
        	   canvasMemory.drawBitmap ( bmNotice, 240, 8, null ); 		// 버퍼에 그리기 
	
 
        this.text = attrs.getAttributeValue(null,"text");
        contax = context;

        btn = new button[MAX_ITEMS];
      /**/ 
        //int pxRight = Constants.width - 200;
        int pxRight = Global.PixelFromDP(bmBG.getWidth()) - 200;
        
        int btn_area = 190;
        int top = 0;
        
        int l = pxRight;
        btn[0] = new button( l , top , btn_area, 50, 
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_lg_start),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
     	
        l = (pxRight +60);
        btn[1] = new button( l, top, btn_area, 50, 
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_lg_stop),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
       
        btn[5] = new button( l, top, btn_area, 50,
         			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_lg_next),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
        
      /**/ 
       
        l = (pxRight+180);
        btn[2] = new button(l, top, btn_area, 50, 
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_lg_back),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
        
      /**/  
        l = (pxRight+220);
        btn[3] = new button( l, top, btn_area, 50,
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_lg_book),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
        
        btn[4] = new button( l, top,  btn_area, 50,
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_lg_book_cancel),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
      /**/ 

        for ( int i = 0; i < 6; i++)
        {
        	btn[i].SetVisible(false);
        	btn[i].Indent(10);
        }
    }
      
    public BottomMenu(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
    }
    
    /**
     * 
     */
    public void UpdateBottomStatus()
    {
     	btn[0].SetVisible(false);
     	btn[1].SetVisible(false);
     	btn[2].SetVisible(false);
     	btn[3].SetVisible(false);
     	btn[4].SetVisible(false);
     	btn[5].SetVisible(false);
    
     
        int right = Global.PixelFromDP(bmBG.getWidth()) - 200;
     	int x = right;
     	
//     	if ( Global.Inst().isRelease == false  )
//     	{
//     		x = right -200;
//     	}
     		
     	
     	if ( isShowBack == true)
     	{
//     		x-= btn_short;
     		// show back 
     		btn[2].SetVisible( isShowBack );				//이전 
     		btn[2].SetLeft( /*Global.Inst().DPFromPixel*/(x) );
     	}
     	else
     	{
			x+=  /*Global.Inst().DPFromPixel*/(btn_short);
     	}
	
		if ( KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ))
		{
			x-=  /*Global.Inst().DPFromPixel*/(btn_short);;
			btn[3].SetVisible(true);				//예약 
			btn[3].SetLeft( /*Global.Inst().DPFromPixel*/(x) );
		}
	
		if ( Global.Inst().in_play == true ) 
		{
			x-= /*Global.Inst().DPFromPixel*/(btn_short);;
			btn[1].SetVisible(true);				//중지 
			btn[1].SetLeft( /*Global.Inst().DPFromPixel*/(x) );
			
			
			if ( KOSD.Inst().GetBookRealCnt() > 0 )
 			{
				
				x-=  /*Global.Inst().DPFromPixel*/(btn_short);;
				btn[5].SetVisible(true);			//시작 
				btn[5].SetLeft( /*Global.Inst().DPFromPixel*/(x) );
 			}
		}
		
		if ( Global.Inst().in_play == false)
     	{
 			if ( KOSD.Inst().GetBookRealCnt() > 0  )
 			{
				x-= ( /*Global.Inst().DPFromPixel*/(btn_wide) *2);
				btn[4].SetVisible(true);			// 예약취소 
				btn[4].SetLeft( /*Global.Inst().DPFromPixel*/(x) );
 			}
 
 			if ( KOSD.Inst().GetBookCnt() > 0 )
 			{
				x-= /*Global.Inst().DPFromPixel*/(btn_short);;
				btn[0].SetVisible(true);			//시작 
				btn[0].SetLeft( /*Global.Inst().DPFromPixel*/(x) );
 			}
    	}
		
        this.postInvalidate();
    }
    
    
	@Override
	public void doCommand(int key)
	{
//		Log.d(TAG," do command");
		switch (GetPos())
		{
		case 0 : // start
			if ( DataHandler.isReachable == false )
			{
    				Global.Inst().app.doMenu(2);
			}
			else
			{
				KPlay.Inst().play_send( KMsg.MSG_KBD_START );
			}
			break;
			
		case 1:	// stop
       		KPlay.Inst().play_send( KMsg.MSG_KBD_STOP );
			break;
			
		case 2 : 	// back
		    
		    
		    Log.d(TAG, "Back");
			new Thread(new Runnable() {         
				   public void run() {                 
					   new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
				   }   
			}).start();	
			break;
		
		case 3 :
       		KPlay.Inst().play_send( KMsg.MSG_KBD_MEMORY );
       		break;
       		
       	case 4 :
       		KPlay.Inst().play_send( KMsg.MSG_KBD_CANCEL );
       		break;
       		
       	case 5: 	// next song 
       		KPlay.Inst().play_send( KMsg.MSG_KBD_NEXTSONG );
       		break;
       		
	
		}
	} 

	
	@Override
    public void onSubDraw(Canvas canvas) {
    	
    	Paint paint = new Paint();
        paint.setAntiAlias(true); // 테두리를 부드럽게한다
    	
//      p.setColor(backgroundColor);
//      canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
        
        if ( hasFocus() )
        {
        	canvas.drawText( "포커스 있음 ", Global.DPFromPixel(188) +100, Global.DPFromPixel(27), paint );
        }
        
//      canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
        paint.setColor(Color.WHITE);
        paint.setTextSize( Global.DPFromPixel( FONT_H) );		
        for ( int i = 0; i < MAX_ITEMS; i++ )
        {
    	   btn[i].drawTranslate( canvas);
    	//   	btn[i].draw ( canvas , GetPos() == i);
        } 
/*	
//		if ( Global.Inst().blogin == true )
     
       if ( DataHandler.have_month_ticket == true)
    	   canvas.drawText( "월정액 가입자입니다", Global.DPFromPixel(188), Global.DPFromPixel(27), paint );
       else 
       if ( SIMClientHandler.register == true)
    	   canvas.drawText( "로그인 되었습니다", Global.DPFromPixel(188), Global.DPFromPixel(27), paint );
       else
    	   canvas.drawText( "가입이 필요합니다", Global.DPFromPixel(188), Global.DPFromPixel(27), paint );
*/       
      
       if( DataHandler.login_message.equals("null") )      
       {
    	  canvas.drawText( "이용권을 구매하세요.", Global.DPFromPixel( MESSAGE_X ), Global.DPFromPixel(27*2), paint );
       }
       else
       {
    	  Log.d("TAG", DataHandler.login_message );
    	  canvas.drawText( DataHandler.login_message, Global.DPFromPixel( MESSAGE_X), Global.DPFromPixel(27*2), paint );
       }       
    }
     
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:     heightSize = (bmBG.getHeight());		break;
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED: widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = (bmBG.getWidth());		break;
        case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }
    @Override
    public Boolean lostFocus(int keyCode)
    {
        return false;
    }
    
    
   /*
      (non-Javadoc)
      @see com.kumyoung.stbui.AbstractTV#onTouchEvent(android.view.MotionEvent)
        btn[0] = new button( 700 , 5  , 200, 50, 
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_start),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
     	
        btn[1] = new button( 780 , 5  , 200, 50, 
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_stop),
        			BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
    */
    
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {

	   Log.w(TAG,"onTouchEvent("+event+")");
	   
       int mouseX =(int) event.getX();
       int mouseY =(int) event.getY();
       
  		int cur = -1;
  		
       for ( int i = 0; i < MAX_ITEMS; i++)
       {
    		if ( 	btn[i].GetVisible()==true && 
    				btn[i].ptInRect(mouseX, mouseY) == true )
    		{
    		   cur = i;
    		   break;
    		}
       }
   	
       switch(event.getAction()) {
       case MotionEvent.ACTION_UP:
           backgroundColor = Color.RED;
           if ( cur >= 0 )
           {
        	   SetPos(cur);
           	   doCommand( 0 );
           	   
           }
           invalidate();
           
           break;
       case MotionEvent.ACTION_DOWN:
           backgroundColor = Color.YELLOW;
           text = "Clicked!";
           if ( cur >= 0 )
           {
        	   SetPos(cur);
        	   
        	   invalidate();
           }
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
//       return super.onTouchEvent(event);
       
       return true;
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