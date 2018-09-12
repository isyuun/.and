package com.kumyoung.stbui;

import isyoon.com.devscott.karaengine.Global;

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
import android.util.Log;
import android.view.View;

@Deprecated
public class SelectThemeView extends AbstractTV/*View*/ {
    
	private static final String TAG = SelectThemeView.class.getSimpleName();
	private String 		buf[] = {	"테마 1", 	
									"테마 2", 	
									"테마 3", 	
									"테마 4", 
									"닫기" 	
								};
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public SelectThemeView(Context context) 
    {
        super(context);
        _prepare(1, 5, 0);

        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_bg );
//       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_f );
        
        btn = new button[MAX_ITEMS];
        for ( int i = 0; i < 5; i++ )
        {
        	
        	int x = 230;
        	int y = 178 + (i)*35;   			
        	
        	btn[i] = 		new button(  x + ( 170),  y, 200, 50, null,
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.genre_close_f), buf[i] );
        }
         
        // close
//        btn[MAX_ITEMS-1] = 	new button( 280 + 123,  148 + 35*6 , 200, 50, null,
 //       						BitmapFactory.decodeResource( contax.getResources() , R.drawable.genre_close_f), buf[MAX_ITEMS-1] );

        
        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height),/* bmBG.getWidth(), bmBG.getHeight(),*/ Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
   		{
   			Paint paint = new Paint();
            paint.setColor(Color.argb(0x80, 0, 0, 0));
   			paint.setStyle(Paint.Style.FILL);
   			canvasMemory.drawRect(new Rect( 0,  0, Global.DPFromPixel(Constants.width), Global.DPFromPixel( Constants.height)), paint );
   			
   			int x = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
   			int y =Global.DPFromPixel( Constants.height)/2 - bmBG.getHeight()/2;        	
   			
   			canvasMemory.drawBitmap ( bmBG, x, y, null ); 		// 버퍼에 그리기 
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