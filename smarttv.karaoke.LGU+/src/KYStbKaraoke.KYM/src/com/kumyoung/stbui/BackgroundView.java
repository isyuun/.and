package com.kumyoung.stbui;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kumyoung.common.Constants;
import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
//import android.view.MotionEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;

import android.graphics.Bitmap.Config;


public class BackgroundView extends View {
    
	private static final String TAG = BackgroundView.class.getSimpleName();
    final private Lock lock = new ReentrantLock();
    
	private Bitmap 		bitmapMemory ;
	private Canvas 		canvasMemory = null;
    
	private final int 	WIDTH		= Constants.width;
	private final int 	HEIGHT		= Constants.height; 
//    private Bitmap 		bmBG = null;// = new Bitmap;

	private final int 	image_count = 5;

	private int 		late = 20;
	private boolean		fadein = false;
	float		alpha_val = 0.0f;
	int			ready = 0;
	Bitmap		[]bmReady;
   
	/*
     * 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 	
     */
    public BackgroundView(Context context) {
        super(context);
//        Log.w(TAG,"TitleView("+context+")");
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public BackgroundView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        //Log.w(TAG,"TitleView("+context+","+attrs+")");
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public BackgroundView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        
        
        //Log.w(TAG,"TitleView("+context+","+attrs+","+defStyle+"),text:"+text);
      
        bitmapMemory = Bitmap.createBitmap(WIDTH, HEIGHT, Config.ARGB_8888);
    	canvasMemory = new Canvas(bitmapMemory);
    	
    	bmReady = new Bitmap[image_count];
    	bmReady[0] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_01 );
    	bmReady[1] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_02 );
    	bmReady[2] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_03 );
    	bmReady[3] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_04 );
    	bmReady[4] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_05 );
    /*	
    	bmReady[5] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_06 );
    	bmReady[6] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_07 );
    	bmReady[7] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_08 );
    	bmReady[8] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_09 );
    	bmReady[9] = BitmapFactory.decodeResource( context.getResources(), R.drawable.bg_10 );
    */	
    	Runnable r = new Runnable() {
    		public void run() {
    			while (true)
    			{
    				//back_update();
    				swap_update();
    			}	
    		}
    	};
    	new Thread(r).start();
 
   }

    @Override
    protected void onDraw(Canvas canvas) {
    
        final Paint paint = new Paint();
/*				
        paint.setColor(0xff00ff00);
        canvas.drawRect( new Rect(0,0, WIDTH, HEIGHT), paint );
*/       
       	paint.setAlpha( (int)(255 * alpha_val) );
        canvas.drawBitmap( bmReady[ready], 0, 0, paint);
        super.onDraw( canvas );	// draw cross
    }
    
    
    /**
     * 
     */
    private void swap_update()
    {
    	
 			alpha_val = 1.0f;
			ready = (++ready%image_count);		//이미지 변경 
			this.postInvalidate();   	
 
    	
       		try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        

   	
    }
    
    
    /**
     *  
     */
    private void back_update()
    {
    	
    	late --;
    	if (  late < 0)
    	{
      		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        
			if ( fadein == true)
			{
				alpha_val = 1.0f;
//				ready = (++ready%4);
//				this.postInvalidate();
				fadein = false;
			}
			else
			{
				alpha_val = 0.0f;
				ready = (++ready%4);		//이미지 변경 
				this.postInvalidate();
				fadein = true;
			}
    		late = 20;
    	}
    	else 
    	{
    	
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        
			
			if ( fadein )
			{
				if ( alpha_val < 1.0f )
				{
					alpha_val += 0.05f;
					this.postInvalidate();
				}
			}
			else
			{
				if ( alpha_val > 0.0f )
				{
					alpha_val -= 0.05f;
					this.postInvalidate();
				}
			}
    	}
    	
    	
    }
 
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:     heightSize = HEIGHT/*bmBG.getHeight()*/;	break;	
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED: widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = WIDTH /*bmBG.getWidth()*/;		break;
        case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }

}