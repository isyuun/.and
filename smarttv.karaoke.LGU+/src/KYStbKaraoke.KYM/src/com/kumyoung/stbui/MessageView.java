package com.kumyoung.stbui;


//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;
import isyoon.com.devscott.karaengine.KPlay;

import com.kumyoung.common.Constants;
import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

public class MessageView extends AbstractTV/*View*/ {
    
	private static final String TAG = MessageView.class.getSimpleName();
	private String 		buf[] = {	
									"         확  인" 	
								};
	
	private Bitmap 	bmTitle = null;
	private int		FONT_H		= 42;
	
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public MessageView(Context context) 
    {
        super(context);
        
        _prepare(0, 0, 1);
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.message_bg );
//       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_f );
        bmTitle = BitmapFactory.decodeResource( contax.getResources(), R.drawable.icon_info);
        
        btn = new button[MAX_ITEMS];
 /* 
        for ( int i = 0; i < MAX_ITEMS; i++ )
        {/
        	int x = 320;
        	int y = 172 + (i/2)*35;   			
        	
        	btn[i] = 		new button(  x + (i % 2 * 170),  y, 200, 50, null,
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.genre_close_f), buf[i] );
        }
  */       
        int X = Constants.width/2 - Global.PixelFromDP(bmBG.getWidth())/2;
        int Y = Constants.height/2 - Global.PixelFromDP(bmBG.getHeight())/2;
        
        // close
        btn[MAX_ITEMS-1] = 	new button( X+ 290,  Y+ 455 , 300, 80, null,
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.genre_close_f), 
        						buf[MAX_ITEMS-1] );
        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height),/* bmBG.getWidth(), bmBG.getHeight(),*/ Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        
        int dpX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
   		int dpY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;        	
 
        // draw
   		if ( bmBG != null )
   		{
   			Paint paint = new Paint();
            paint.setColor(Color.argb(0x80, 0, 0, 0));
   			paint.setStyle(Paint.Style.FILL);
   			canvasMemory.drawRect(new Rect( 0,  0, Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height)), paint );
   			canvasMemory.drawBitmap ( bmBG, dpX, dpY, null ); 		// 버퍼에 그리기 
   			
   		}
   		if ( bmTitle != null )
   		{
   			canvasMemory.drawBitmap ( bmTitle, 
   										dpX + Global.DPFromPixel(56), 
   										dpY + Global.DPFromPixel(20), 
   										null ); 		// 버퍼에 그리기 
   		}
   	
   		// draw
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);
	    
	    /***/
	    
	   /* 
        final Paint p = new Paint();
       	p.setColor( 0x7fffffFF );   	//빨간 계열 비트맵으로 그린다.	
        canvasMemory.drawLine(0, 0,   				bmBG.getWidth(), bmBG.getHeight(), p);
        canvasMemory.drawLine(0, bmBG.getHeight(), 	bmBG.getWidth(), 0, p); 
//        setFocusable(true);
        
       */ 
    }
    
    @Override
    public void onSubDraw( Canvas canvas )
    {
    	
    	Paint p = new Paint();
        p.setAntiAlias(true); // 테두리를 부드럽게한다
		p.setColor( 0xffFFFFFF);
		p.setTextSize( Global.DPFromPixel( FONT_H ) );
	
        int dpWindowX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
   		int dpWindowY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;        	
   		
   		float w = p.measureText(text, 0, text.length());
   		
		canvas.drawText( text,  dpWindowX +  ( bmBG.getWidth()/2 - w/2), //ppp
								dpWindowY + Global.DPFromPixel(300), p );
   }
    
    @Override
    public void doCommand(int keyCode) {
       	container.dismiss();
    }
    
    @Override
    public Boolean lostFocus(int keyCode)
    {
        return false;
    }   
   
    /**
     * 
     * @param str
     */
    public void setText(String str)
    {
    	text = str;
    }
}