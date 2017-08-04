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

public class SelectGenreView extends AbstractTV/*View*/ {
    
	private static final String TAG = SelectGenreView.class.getSimpleName();
	private String 		buf[] = {	"트로트", 	"동요", 
									"만화", 		"팝송", 
									"발라드", 	"댄스", 
									"R&B", 		"락", 
									"7080", 	"메들리", 
										  "       닫 기" 	
								};
	
	
	
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public SelectGenreView(Context context) 
    {
        super(context);
        
        
        _prepare(2, 5, 1);
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.genre_bg );
//       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_f );
        
        btn = new button[MAX_ITEMS];
        
 		int dpX = Global.DPFromPixel(Constants.width)/2 - bmBG.getWidth()/2;
 		int dpY = Global.DPFromPixel(Constants.height)/2 - bmBG.getHeight()/2;
 
        for ( int i = 0; i < 10; i++ )
        {
   			int dpYRow = dpY + (i/2)* Global.DPFromPixel(70);        	
        	btn[i] = 		new button(  Global.PixelFromDP(dpX) + (i % 2 * (170*2))+ 20*2,  
        								 Global.PixelFromDP(dpYRow) + 40*2, 
        								 300, 
        								 60, 
        								 null,
        								 BitmapFactory.decodeResource( contax.getResources() , R.drawable.genre_list_f), 
        								 buf[i] );
        }
        
        // close

        btn[MAX_ITEMS-1] = 	new button( Global.PixelFromDP(dpX) + 118*2,  
        								Global.PixelFromDP(dpY) + (35*6 *2) + (16*2), 
        								250, 100, null,
        								BitmapFactory.decodeResource( contax.getResources() , R.drawable.genre_close_f), buf[MAX_ITEMS-1] );

        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height),
       						/* bmBG.getWidth(), bmBG.getHeight(),*/ Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
   		{
   			Paint paint = new Paint();
            paint.setColor(Color.argb(0x80, 0, 0, 0));
   			paint.setStyle(Paint.Style.FILL);
   			canvasMemory.drawRect(new Rect( 0,  0, Global.DPFromPixel(Constants.width), Global.DPFromPixel(Constants.height)), paint );
   			canvasMemory.drawBitmap ( bmBG, dpX, dpY, null ); 		// 버퍼에 그리기 
   			
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
    public void doCommand(int key) {
    	
    	if (key ==KeyEvent.KEYCODE_BACK )
    	{
    		container.dismiss();
    		
    		return;
    	}
    		
    		
       	container.dismiss();
        if ( GetPos() != 10)
       	{
       		KPlay.Inst().play_send( KMsg.MSG_KBD_GENRE_TROT_SONGLIST +GetPos()  );
       	}
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