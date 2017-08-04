package com.kumyoung.stbui;

//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KBook;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KPlay;

import com.kumyoung.gtvkaraoke.DataHandler;
import kr.kumyoung.gtvkaraoke.R;
import com.kumyoung.stbcomm.SIMClientHandlerLGU;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

public class ContextMenuView extends AbstractTV/*View*/ {
    
	private static final String TAG = ContextMenuView.class.getSimpleName();
	private String 		buf[] = {"시작", "예약", "애창곡으로 등록", "닫기" };
	
	private int			sno = 0;	
	private int favorite_type = 0;

	
	private int ROW_Y	= 70;
	private int ROW_H	= 65;
	private int ROW_W	= 310;
	
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public ContextMenuView(Context context) 
    {
        super(context);
        // 
        _prepare(1, 4, 0);

        Log.w(TAG,"CustomView("+context+")");
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_bg );
//       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_f );
       	
        btn = new button[MAX_ITEMS];
        for ( int i = 0; i < MAX_ITEMS; i++)
        {
        	btn[i] = new button( 0,  (7*2)+ i*ROW_Y, ROW_W, ROW_H, 
        						null, 
        						BitmapFactory.decodeResource( 	contax.getResources(), 
        														R.drawable.list_pop_f), 
        						buf[i] );
        }
        
        // create memory 
       	bitmapMemory = Bitmap.createBitmap( bmBG.getWidth(), bmBG.getHeight(), Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
   		{
 // 			Paint paint = new Paint();
 //           paint.setColor(Color.argb(0x80, 0, 0, 0));
//   			paint.setStyle(Paint.Style.FILL);
//   			canvasMemory.drawRect(new Rect( 0,  0, Constants.width, 540), paint );

   			canvasMemory.drawBitmap ( bmBG, 0,0, null ); 		// 버퍼에 그리기 
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
    
         // over 
    
    @Override
    public void onSubDraw( Canvas canvas )
    {
    	 
    }
     
     public void setCurrentSongNumber( int _sno )
     {
    	sno = _sno;  
     }
     
     //
     public void setSubMenu(String str)
     {
		btn[2].SetText( str );

		// clear memory map 
   		canvasMemory.drawBitmap ( bmBG, 0,0, null ); 		// 버퍼에 그리기 
   			
		// redraw memory map
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);
	    
	    
	    favorite_type = 1;	

     }
    
     
    @Override
    public void doCommand(int keyCode) 
    {
    
    	if ( container == null)
    	{
    		Log.e(TAG, "can't exit.  Plase implement setParent method");
    	}
    	else
    	{
    		switch ( GetPos() )
    		{
    		case 0:		// 우선 시작 
    			if ( DataHandler.isReachable == false)
    			{
    				Global.Inst().app.doMenu(2);
    			}
    			else
    			{
    				KBook.INSERT_BOOK( sno, false);
    				KPlay.Inst().play_send( KMsg.MSG_KBD_START );
    			}
        		break;
    		case 1:		// 예약  
    	 		KBook.MEMORY_BOOK( sno, false); 
    	 		break;
//    		case 2: 	// 우선예약 
 //   			KBook.INSERT_BOOK( sno, false);
  //  			break;
    		case 2:		// 애창곡  등록 
    			if ( favorite_type == 0)
    			{
    				//SIMClientHandler.cont_id				
    				DataHandler.SetRegistFavoriteSong( SIMClientHandlerLGU.cont_no, sno);	
    				DataHandler.retString = DataHandler.resultMessage[0];
    			}
    			else
    			{
    				//-------------------------
    				DataHandler.UnRegistFavoriteSong( SIMClientHandlerLGU.cont_no, sno );
    				DataHandler.retString = DataHandler.resultMessage[0];
    			}
    			break;
    			
    		case 3:
    		default:
    			break;
    		}
        	container.dismiss();
    	}
    }
   
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {

	   Log.w(TAG,"onTouchEvent("+event+")");
       int  mouseX =(int) event.getX();
       int  mouseY =(int) event.getY();

       
  		int cur = -1;
        for ( int i = 0; i < MAX_ITEMS; i++)
        {
           if ( 	btn[i].ptInRect( mouseX, mouseY ) )
           {	
        	   cur = i;
           } 
        }
   	
       switch(event.getAction()) {
       case MotionEvent.ACTION_UP:
           //backgroundColor = Color.RED;
           if ( cur >= 0 )
           {
        	   SetPos( cur );
           }
           invalidate();
           break;
       case MotionEvent.ACTION_DOWN:
           //backgroundColor = Color.YELLOW;
           text = "Clicked!";
           if ( cur >= 0 )
           {
           		SetPos( cur );
        	   doCommand(0) ;
           }
           invalidate();
           break;
           
       case MotionEvent.ACTION_MOVE:
           //backgroundColor = Color.TRANSPARENT;
           //text = "Moved!";
           break;
          
       case MotionEvent.ACTION_POINTER_UP:
           //backgroundColor = Color.BLUE;
           //text = "Outside";
           break;
       }
       return super.onTouchEvent(event);
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
     /*                        // set the row that is closest to the rect
                        if (previouslyFocusedRect != null) {
                            int y = previouslyFocusedRect.top
                                    + (previouslyFocusedRect.height() / 2);
                            int yPerRow = getHeight() / mNumRows;
                            mSelectedRow = y / yPerRow;
                        } else {
                            mSelectedRow = 0;
                        }
     */                        
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
        @Override
        public Boolean lostFocus(int keyCode)
        {
            return false;
        }    
   
}