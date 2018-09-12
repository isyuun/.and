package com.kumyoung.stbui;

//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

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

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

@Deprecated
public class SelectView extends AbstractTV/*View*/ {
    
	private static final String TAG = SelectView.class.getSimpleName();
	private String 		buf[] = {"시작", "예약", "예약(코러스)", "애창곡으로 등록", "닫기" };
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public SelectView(Context context) 
    {
        super(context);
        _prepare(1, 5, 0);

        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_bg );
//       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_f );
       	
        btn = new button[MAX_ITEMS];
        for ( int i = 0; i < MAX_ITEMS; i++)
        {
        	btn[i] = new button( 	
        						0,  24 + i*35, 200, 50, 
        						null, 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.main_menu_f), 
        						buf[i] );
        }
        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(Constants.width)/*bmBG.getWidth()*/, Global.DPFromPixel(Constants.height)/*bmBG.getHeight()*/, Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
   		{
  			Paint paint = new Paint();
            paint.setColor(Color.argb(0x80, 0, 0, 0));
   			paint.setStyle(Paint.Style.FILL);
   			canvasMemory.drawRect(new Rect( 0,  0, Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height) ), paint );

   			canvasMemory.drawBitmap ( bmBG, Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2, Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2, null ); 		// 버퍼에 그리기 
//        	canvasMemory.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
   		}
   	
   		
   		// draw
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);
	    
	  
	    
	    
	    
	    /***/
	   /* 
        final Paint p = new Paint();
       	p.setColor( 0x7fffffFF );   	//빨간 계열 비트맵으로 그린다.	
        canvasMemory.drawLine(0, 0,   bmBG.getWidth(), bmBG.getHeight(), p);
        canvasMemory.drawLine(0, bmBG.getHeight(), bmBG.getWidth(), 0, p); 
        
       */ 
//        setFocusable(true);
    }
   
    @Override
     public void onSubDraw( Canvas canvas )
     {
    	Log.v(TAG, "call onSubDraw");
     }

     @Override
    public void doCommand(int keycode) {
    	
       	container.dismiss();
        
    }
    
   /* 
    public void makeFocus()
    {
    	requestFocus();
    }
       public void doFocusChange(Boolean focused, int direction )
    {
    	   
            if (focused) {
                switch (direction) {
                    case View.FOCUS_DOWN:
                       // mSelectedRow = 0;
                    	
                    	Log.e(TAG, "FOCUS_DOWN");
                        
                        break;
                    case View.FOCUS_UP:
//                        mSelectedRow = mNumRows - 1;
                        
                    	Log.e(TAG, "FOCUS_UP");
                        break;
                    case View.FOCUS_LEFT:  // fall through
                    case View.FOCUS_RIGHT:
                    	
                    	Log.e(TAG, "FOCUS_LEFT/RIGHT");
                             // set the row that is closest to the rect
                        if (previouslyFocusedRect != null) {
                            int y = previouslyFocusedRect.top
                                    + (previouslyFocusedRect.height() / 2);
                            int yPerRow = getHeight() / mNumRows;
                            mSelectedRow = y / yPerRow;
                        } else {
                            mSelectedRow = 0;
                        }
                             
                        break;
                    default:
                        // can't gleam any useful information about what internal
                        // selection should be...
                    	Log.e(TAG, "FOCUS_DEFAULT");
                        return;
                }
                
               
            }
            invalidate();
    }
    
    */
     @Override
     public Boolean lostFocus(int keyCode)
     {
         return false;
     }    
   
}