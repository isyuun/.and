package com.kumyoung.stbui;


import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import kr.kymedia.karaoke.util.gtv.Util;

import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.DataHandler;
import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

public class AdView extends AbstractTV {

	private static final String TAG = AdView.class.getSimpleName();

	//private String 		text = null;
    //private Bitmap 		bmBG = null;// = new Bitmap;
//	OSDThread			mThread;
    
	PopupWindow popup = null;
    
    
	int ad_width = 420;// 150;
	int ad_height = 300;// 150;
   
    public AdView(Context context) {
        super(context);
//        Log.w(TAG,"CustomView("+context+")");

        contax = context; 
    }
    public AdView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
//        Log.w(TAG,"CustomView("+context+","+attrs+")");
        
        contax = context;
    }

    public AdView(Context context,AttributeSet attrs,int defStyle) {
    	super(context,attrs,defStyle);
        _prepare( 1, 1, 0);
        this.text = attrs.getAttributeValue(null,"text");
        
//        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);

        contax = context;
        
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.event );
        int width = bmBG.getWidth();
        int height = bmBG.getHeight();
       
        // create memory 
       	bitmapMemory = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

		// draw
		if (bmBG != null)
			canvasMemory.drawBitmap(bmBG, 0, 0, null); // 버퍼에 그리기

        Bitmap bm = null; 
       
        
        /*
		 * // try {
		 * // URL aURL = new URL( DataHandler.serverIP + DataHandler.ad_image );
		 * //
		 * // URLConnection conn = aURL.openConnection();
		 * // conn.connect();
		 * // InputStream is = conn.getInputStream();
		 * // BufferedInputStream bis = new BufferedInputStream(is);
		 * // bm = BitmapFactory.decodeStream(bis);
		 * // bis.close();
		 * // is.close();
		 * //// i.setImageBitmap(bm);
		 * //
		 * // ad_width = bm.getWidth();
		 * // ad_height = bm.getHeight();
		 * // } catch (IOException e) {
		 * //// i.setImageResource(R.drawable.file_not_found);
		 * // bm = BitmapFactory.decodeResource( contax.getResources(), R.drawable.event );
		 * // Log.e("DEBUGTAG", "Remote Image Exception : "+ e.getMessage());
		 * //
		 * // return;
		 * // }
        */ 
 
        
        bm = BitmapFactory.decodeResource( contax.getResources(), R.drawable.event );
        {
            Bitmap bitmap = BitmapFactory.decodeFile( DataHandler.getExternalPath()  + "adimage.png");
            //Bitmap bitmap = BitmapFactory.decodeFile( DataHandler.getInstance().getExternalPath() + "theme1.png");
            if ( bitmap != null )
            {
                bitmap.setDensity( 320/* bm.getDensity() */);
                bm = bitmap;
            }
        }
        
		// draw
   		if ( bm != null )
        	canvasMemory.drawBitmap ( bm, 0, 0, null ); 		// 버퍼에 그리기 

        //ad_width = bm.getWidth();

        btn = new button[MAX_ITEMS];
       
        for ( int i = 0; i < MAX_ITEMS; i++)
        	btn[i] = new button( 	0, 0, 200, 50, 
                				null, //BitmapFactory.decodeResource( contax.getResources(), R.drawable.event ), 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.event_f)
        						,"" // AD
        					);

   		// draw
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);

	   
	   /* 
        final Paint p = new Paint();
       	p.setColor( 0x7fffffFF );   	//빨간 계열 비트맵으로 그린다.	
        canvasMemory.drawLine(0, 0,   width, height, p);
        canvasMemory.drawLine(0, height, width, 0, p);
       */ 
		if (Global.isDebugGrid == true)
		{
			final Paint p = new Paint();

			p.setColor(Color.GREEN);
			p.setStyle(Style.STROKE);
			canvasMemory.drawRect(0, 0, width - 1, height - 1, p);

			p.setColor(0x7fffffFF);   	// 빨간 계열 비트맵으로 그린다.
			canvasMemory.drawLine(0, 0, width, height, p);
			canvasMemory.drawLine(0, height, width, 0, p);

			p.setColor(Color.RED);
			p.setStyle(Style.STROKE);
			canvasMemory.drawRect(
					Global.DPFromPixel(0),
					Global.DPFromPixel(0),
					Global.DPFromPixel(ad_width - 1),
					Global.DPFromPixel(ad_height - 1),
					p);

			String text = getMethodName() + "[RECT]";
			// text += "\n[RECT]" + "metrics.densityDpi:" + metrics.densityDpi;
			// text += "\n[RECT]" + "metrics.density:" + metrics.density;
			// text += "\n[RECT]" + "metrics.scaledDensity:" + metrics.scaledDensity;
			text += "\n[RECT][PX](" + "w:" + width + ", h:" + height + ")";
			text += "\n[RECT][DP:NG](" + "w:" + Global.DPFromPixel(width) + ", h:" + Global.DPFromPixel(height) + ")";
			text += "\n[RECT][DP:OK](" + "w:" + Util.px2dp(getContext(), width) + ", h:" + Util.px2dp(getContext(), height) + ")";
			text += "\n[RECT][PX]AD(" + "w:" + ad_width + ", h:" + ad_height + ")";
			text += "\n[RECT][DP:NG]AD(" + "w:" + Global.DPFromPixel(ad_width) + ", h:" + Global.DPFromPixel(ad_height) + ")";
			text += "\n[RECT][DP:OK]AD(" + "w:" + Util.px2dp(getContext(), ad_width) + ", h:" + Util.px2dp(getContext(), ad_height) + ")";
			Log.e(TAG, text);
		}

//	    if ( DataHandler.getInstance().have_userinfo == true )
//            Reloaded();
    }
    
    



    /*
     * 
     * (non-Javadoc)
     * @see com.ky.stbui.AbstractTV#doCommand()
     */
    public void doCommand(int key)
    {
    	if ( DataHandler.isReachable == false)
    	{
    		Global.Inst().app.doMenu(2);
    		return;
    	}
    	
        //if (DataHandler.AD_use == true )
    	{
    		AdPopupView popupview = new AdPopupView(contax, DataHandler.AD_full_image );
    		popup = new PopupWindow( popupview ,Constants.widthReal, Constants.heightReal, true);		// full 화면을 채워 마우스 포인터를 못가게 한다. 
            			
    		//popup.showAsDropDown(this,  0, 0);
       		popup.showAtLocation(this, Gravity.CENTER , 0, 0);	
       			
       			
       		//popup.setOutsideTouchable(false); //윈도우 밖에 터치를 받아들일때 사용한다. 
       		popupview.setSpot();
       		popupview.setParent( popup );		// 종료하기위하여 
    	}
    }

    /*
     * (non-Javadoc)
     * @see com.ky.stbui.AbstractTV#onSubDraw(android.graphics.Canvas)
     */
    
    @Override
    public void onSubDraw(Canvas canvas)
    {
    		final Paint paint = new Paint();
    		paint.setAntiAlias(true); // 테두리를 부드럽게한다
    		paint.setTextSize(28);
    		paint.setColor(Color.YELLOW);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // height 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED:    // mode 가 셋팅되지 않은 크기가 넘어올때
            heightSize = heightMeasureSpec;
            break;
        case MeasureSpec.AT_MOST:        // wrap_content (뷰 내부의 크기에 따라 크기가 달라짐)
            heightSize = Global.DPFromPixel(ad_height);//bitmapMemory.getHeight();
            break;
        case MeasureSpec.EXACTLY:        // fill_parent, match_parent (외부에서 이미 크기가 지정되었음)
            heightSize = MeasureSpec.getSize(heightMeasureSpec);
            break;
        }
        
        // width 진짜 크기 구하기
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED:    // mode 가 셋팅되지 않은 크기가 넘어올때
            widthSize = widthMeasureSpec;
            break;
        case MeasureSpec.AT_MOST:        // wrap_content (뷰 내부의 크기에 따라 크기가 달라짐)
            widthSize = Global.DPFromPixel(ad_width);//bitmapMemory.getWidth();
            break;
        case MeasureSpec.EXACTLY:        // fill_parent, match_parent (외부에서 이미 크기가 지정되었음)
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
            break;
        }
//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }
    
    @Override
    public Boolean lostFocus(int keyCode)
    {
        Log.e(TAG, "lostFocus");
    	
    	if ( keyCode == KMsg.MSG_KBD_RIGHT ) 
    	{
    		ViewManager.Inst().lpMenuView.nextKey();	// 오른쪽 키로 포커스를 잃으면 카운터를 해줌. 
    	}
    	
        ViewManager.Inst().lpMenuView.setSpot(); 
        return true;
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
           invalidate();
           
           doCommand( 0);
           break;
           
       case MotionEvent.ACTION_DOWN:
           backgroundColor = Color.YELLOW;
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
//       return super.onTouchEvent(event);
       
       return true;

    } 
    
    
    public void Reloaded ()
    {
/*        
    	if ( DataHandler.isReachable == false)
    		return;
    	
        try {
            URL aURL = new URL( DataHandler.serverKYIP + DataHandler.AD_image );
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bmBG = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            
        }
        
        btn = new button[MAX_ITEMS];
        bmBG.setDensity( 320 );
        for ( int i = 0; i < MAX_ITEMS; i++)
        	btn[i] = new button( 	0, 0, 200, 50, 
                				bmBG, //BitmapFactory.decodeResource( contax.getResources(), R.drawable.event ), 
        						BitmapFactory.decodeResource( contax.getResources() , R.drawable.event_f)
        						,"" // AD
        					);

   	
   		// draw
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);
*/
    }
    
    
  
     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
       
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

		
}