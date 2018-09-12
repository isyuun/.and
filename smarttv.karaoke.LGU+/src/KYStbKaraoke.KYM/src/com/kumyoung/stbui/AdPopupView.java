package com.kumyoung.stbui;


import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.IEventListener;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;
import isyoon.com.devscott.karaengine.KPlay;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//import java.util.ArrayList;
import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.DataHandler;
import kr.kumyoung.gtvkaraoke.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;

/*
public interface IEventListener
{
	public void invokeEvent ( int number, int count );
}
*/

public class AdPopupView extends AbstractLV/*View*/ implements IEventListener {
	
	private static final String TAG = AdPopupView.class.getSimpleName();
	
    private Bitmap 			bmBG;

    // memory dc  
	protected Bitmap 		bitmapMemory ;
	protected Canvas 		canvasMemory = null;
/*
	button []btn;
	int btn_count = 0;
	int curPos = 0;
*/	
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public AdPopupView(Context context, String strPath) 
    {
        super(context);
        
//        _prepare(3, 1, 1);
        Log.w(TAG,"CustomView("+context+")");
       
        //
        // create memory 
        //
        try {
            URL aURL = new URL( DataHandler.serverKYIP + strPath/* DataHandler.AD_full_image*/ );
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bmBG = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            
            // by_jslee 0321 email 변경사항 적용.
            //bmBG.setDensity(320);

			// isyoon_20150427
			bmBG.setDensity(metrics.densityDpi);

            bitmapMemory = Bitmap.createBitmap( bmBG.getWidth(), bmBG.getHeight(), Config.ARGB_8888);
            canvasMemory = new Canvas(bitmapMemory);

    		// isyoon_20150427
    		canvasMemory.setDensity(metrics.densityDpi);

            int x = 0; int y = 0;
  //                  int		x = Constants.width/2 - bmBG.getWidth()/2;
  //                  int		y = Constants.height/2 - bmBG.getHeight()/2;	
 			canvasMemory.drawBitmap ( bmBG, x, y, null ); 		// 버퍼에 그리기 
 			Global.Inst().app.m_bShowADPopup = true;
 	/* 
          bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.bg_01 );
     */ 
  //      int		dpX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
 //       int		dpY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;	
//        canvasMemory.drawBitmap ( bmBG, dpX, dpY, null ); 		// 버퍼에 그리기 
        } catch (IOException e) {
        } 
     
		
 /*     
        btn = new button[2];
        btn[0] = new button(   Global.PixelFromDP(Constants.width )-Global.PixelFromDP(160), 
                               Global.PixelFromDP(Constants.height)-Global.PixelFromDP(80), 
                               60, 50, 
                               BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_ad_close),
                               BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
        btn[0].drawTranslate(canvasMemory);
        btn_count = 1;
        if (DataHandler.AD_useDetail == true )
        {
            btn[1] = new button(   Global.PixelFromDP(Constants.width )-Global.PixelFromDP(300), 
                               Global.PixelFromDP(Constants.height)-Global.PixelFromDP(80), 
                               60, 50, 
                               BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_ad_detail),
                               BitmapFactory.decodeResource( contax.getResources() , R.drawable.key_f), null );
            btn[1].drawTranslate(canvasMemory);
            btn_count = 2;
        }
*/        
    }
    
    public void onSubDraw(Canvas c )
    {
 /*	
    	Paint p = new Paint();
        p.setAntiAlias(true); // 테두리를 부드럽게한다
		p.setColor( 0xA0000000);
		p.setTextSize( 15 );
	
		if ( curStep == 0)
		{
			int pos = sheet[curStep].GetPos();
			c.drawText( description[ pos ],  Global.DPFromPixel(270), Global.DPFromPixel(360), p );
		}
*/		
    }
   
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
		// 20150414
		// 종료키처리
		try {
			if (keyCode == KeyEvent.KEYCODE_TV || keyCode == 170) {
				Log.e(TAG, "onKeyDown(" + keyCode + "," + event + ")" + "[종료]");
				return super.onKeyDown(keyCode, event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		Global.Inst().app.m_bShowADPopup = false;
	    container.dismiss();
			switch ( keyCode )
			{
		/*	
				case KMsg.MSG_KBD_UP : 		up(); 	break;
				case KMsg.MSG_KBD_LEFT : 	left(); break;
				case KMsg.MSG_KBD_RIGHT : 	right();break;
				case KMsg.MSG_KBD_DOWN : 	down(); break;
		 */
				case KeyEvent.KEYCODE_DPAD_CENTER:
				case KeyEvent.KEYCODE_ENTER : 
/*		
				    if (DataHandler.AD_useDetail == true )   {
				        if ( curPos == 1)
				            KPlay.Inst().play_send( KMsg.MSG_KBD_NOTICE_HELPLIST, 1);
				         container.dismiss();
				    }
					break;
*/					
				case KeyEvent.KEYCODE_BACK:
					Global.Inst().app.m_bShowADPopup = false;
				    container.dismiss();
				    return true;
			}
        return true;		
    }
    
    //interface implement
    @Override
	public void invokeEvent( int lparam, int wparam)
    {
//        System.out.println( count + " : " + number + " called" );
        try
        {
            Thread.sleep( 10 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace( );
        }
        Global.Inst().app.m_bShowADPopup = false;
        container.dismiss();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
	   Log.w(TAG,"onTouchEvent("+event+")");
       int mouseX =(int) event.getX();
       int mouseY =(int) event.getY();
       switch(event.getAction()) {
       case MotionEvent.ACTION_UP:
           backgroundColor = Color.RED;
           Global.Inst().app.m_bShowADPopup = false;
           container.dismiss();
           break;
       case MotionEvent.ACTION_DOWN:
           backgroundColor = Color.YELLOW;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:     heightSize = Global.DPFromPixel( 400 );	break;	
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED: widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = Global.DPFromPixel( 200 );		break;
        case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }
		
   
 /*   		
		public void left()	   {   
		    curPos = btn_count-1; 
		    this.postInvalidate();
		}
		public void up() 	    { }
		public void right()		{
		    curPos = 0;
		    this.postInvalidate();
		}
		public void down()		{
		}
		void drawControl( Canvas cv)
		{
		    Log.d(TAG, "curPos =" + curPos );
		    for (int i = 0; i < btn_count;  i++)
		        btn[i].draw(cv,  i==curPos );
		}
*/		
            
		@Override
		protected void onDraw(Canvas canvas) {
		    if ( bitmapMemory == null)
		        Log.e(TAG, "not create bitmapMemory");
		    else
		        canvas.drawBitmap(bitmapMemory, 0, 0, null );
		    super.onDraw(canvas);
//		    drawControl( canvas);
		    Log.e(TAG, "onDraw----");
		}
            
    
    
}


