package com.kumyoung.stbui;


import isyoon.com.devscott.karaengine.Global;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kumyoung.common.Constants;
import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff;


public class TitleView extends AbstractLV {
    
	private static final String TAG = TitleView.class.getSimpleName();
	
    final private Lock lock = new ReentrantLock();
	
    private String textTitle = null;
    private String textSinger = null;
    private String textLyric = null;
    private String textWriter = null;
    
    private String textDescription = null;
//    private String tempText;

    
//    private int aligment = 1;
//    private int left = 0;
    
	private Bitmap 		bitmapMemory ;
	private Canvas 		canvasMemory = null;
    
	private final int 	WIDTH		= 1600;   
	private final int 	HEIGHT		= (680); 
    private Bitmap 		bmBG = null;// = new Bitmap;
	
	float		alpha_val = 0.0f;
	int			ready = 999;
	Bitmap		[]bmReady;
   

	final private int AUTHOR_FONT = 50;
	
	/*
     */
    public TitleView(Context context) {
        super(context);
//        Log.w(TAG,"TitleView("+context+")");
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public TitleView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        //Log.w(TAG,"TitleView("+context+","+attrs+")");
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public TitleView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        
        //this.textTitle = attrs.getAttributeValue(null,"text");
        this.textTitle = new String("text");
        	
        Log.d("ke", "TitleView initial = textTitle " + this.textTitle );
        		
        
        //Log.w(TAG,"TitleView("+context+","+attrs+","+defStyle+"),text:"+text);
      
        bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(WIDTH), 
        									Global.DPFromPixel(HEIGHT), Config.ARGB_8888);
    	canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

   
/* background image
    	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.cloud );
       int width 	= bmBG.getWidth();
        int height 	= bmBG.getHeight();
        canvasMemory.drawBitmap ( bmBG, WIDTH/2 - width/2, 0, null ); 		// 버퍼에 그리기 
*/       
        bmReady = new Bitmap[5];
    	bmReady[0] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.start_num0 );
    	bmReady[1] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.start_num1 );
    	bmReady[2] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.start_num2 );
    	bmReady[3] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.start_num3 );
    	bmReady[4] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.start_num4 );
    	
    	
    	
        if ( ViewManager.Inst().lpBottomMenu != null)
        {
        	ViewManager.Inst().lpBottomMenu.isShowBack = false;
        	ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
        }
        
   }

    @Override
    protected void onDraw(Canvas canvas) {
    
        final Paint paint = new Paint();
				
        paint.setColor(0x00000000);
        canvas.drawRect( new Rect(0,0, Global.DPFromPixel(WIDTH), Global.DPFromPixel(HEIGHT)), paint );
       
        /*
        if ( textTitle == "" ) 
        {
        	paint.setAlpha( (int)(255 * 0.3) );
        	canvas.drawBitmap(bitmapMemory, 0, 0, paint);
        }
        else
        */	
        {
         	paint.setAlpha( (int)(255 * alpha_val) );
        	canvas.drawBitmap(bitmapMemory, 0, 0, paint);
        }
      
        if ( ready != 999)
        {
        	canvas.drawBitmap( bmReady[ready], Global.DPFromPixel(200), Global.DPFromPixel(HEIGHT-140), null );
        }
            
        super.onDraw( canvas );	// draw cross
    }

    
    
    private void alpha_draw()
    {
    	for ( alpha_val = 0.0f; alpha_val < 1.0f; alpha_val+=0.05f )
    	{
    		
    		Log.d("ke", "redraw alpha = " + alpha_val + this.textTitle );
    		this.postInvalidate();
    		try {
				//Thread.sleep(200 * 1);
				Thread.sleep(180 * 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        
    	}
    }
     
    private void alpha_clear_draw()
    {
    	
    
    	for ( alpha_val = 1.0f; alpha_val > 0.0f; alpha_val-=0.05f )
    	{
    		
    		Log.d("ke","redraw alpha = " + alpha_val );
    		this.postInvalidate();
    		try {
				Thread.sleep(180 * 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        
    	}

//    	this.textTitle =  new String("");
//    	this.textDescription = new String("");
//    	this.postInvalidate();

	   
    }
    
  
    
    public void dump()
    {
    	Log.d("ke", "Dump -----" + this.textTitle );
    	Runnable r = new Runnable() {
    		public void run() {
				alpha_draw();
    		}
    	};
    	new Thread(r).start();
    }
 
    /**
     * 
     * @param _title
     * @param _textDescription
     */
    public void setTitle(String _title, String _description, String strSinger, String strWriter, String strLyric ) {
   
        _title.trim();          // both space trim
        _description.trim();
        String strTemp =(  _title + " " +  _description);
   
        final String[] array;
        array = strTemp.split("\\(");
        
         if (    array.length > 0 )  
                this.textTitle = array[0];
                
         if (    array.length > 1 )
             this.textDescription = ("(" + array[1]);
        
        
    	lock.lock();
 //   	this.textTitle = new String( _title );
//    	this.textDescription = new String ( _description);
    	
    	this.textSinger = new String( strSinger);
    	this.textWriter = new String( strWriter );
    	this.textLyric = new String( strLyric );
    	
    	
    	Log.d("ke", "textTitle :" + this.textTitle + "_title " + _title );
        	 
        final Paint paint = new Paint();
        paint.setAntiAlias(true); // 테두리를 부드럽게한다
       	paint.setTextSize( Global.Inst().DPFromPixel(100));
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        	
        //  너무 길면 사이즈를 줄인다. 
        int measuredTextWidthCheck = (int) Math.ceil( paint.measureText(this.textTitle) ) ;
        if ( measuredTextWidthCheck > 900)
        {
       		paint.setTextSize( Global.Inst().DPFromPixel(60	));
       		measuredTextWidthCheck = (int) Math.ceil( paint.measureText(this.textTitle) ) ;
       		if ( measuredTextWidthCheck > 900)
       			paint.setTextSize( Global.Inst().DPFromPixel(54) );
        }
 
       
        Log.d(TAG, "setText TITLE :" + textTitle );
        ///canvasMemory.drawColor( 0x3fff00ff, PorterDuff.Mode.CLEAR );   	// 이미지 겹치지 않
        
       	paint.setColor( 0x00000000);		// alpha 지정 
        canvasMemory.drawRect( new Rect(0,0, WIDTH, HEIGHT), paint );
     		        
        int measuredTextWidth = (int) Math.ceil( paint.measureText(textTitle)) ;
        int dpLeft 	= ( (Global.DPFromPixel(WIDTH)/2) - (measuredTextWidth/2) );
        int dpY = Global.DPFromPixel(HEIGHT)/5 *2; 
        int dpB = Global.DPFromPixel( 6 );
        int dpC = Global.DPFromPixel( 5 );
        int dpD = Global.DPFromPixel( 3 );
        int dpE = Global.DPFromPixel( 2 );
        paint.setColor(Color.BLACK);
        
        canvasMemory.drawText(textTitle,dpLeft -dpB, dpY+ 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +dpB, dpY+ 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +0, dpY+ (-dpB), paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +0, dpY+ (+dpB), paint); // 왼쪽 아래를 0,0 으로 보고있음

        canvasMemory.drawText(textTitle,dpLeft -dpC, dpY- dpC, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +dpC, dpY+ dpC, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft -dpC, dpY+ dpC, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +dpC, dpY- dpC, paint); // 왼쪽 아래를 0,0 으로 보고있음
 
        
        
      	canvasMemory.drawText(textTitle,dpLeft -dpD ,dpY+ 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +dpD, dpY+ 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +0, dpY+ (-dpD), paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +0, dpY+ (+dpD), paint); // 왼쪽 아래를 0,0 으로 보고있음

        canvasMemory.drawText(textTitle,dpLeft -dpE, dpY+ (-dpE), paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +dpE, dpY+ (-dpE), paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft +dpE, dpY+ (+dpE), paint); // 왼쪽 아래를 0,0 으로 보고있음
        canvasMemory.drawText(textTitle,dpLeft -dpE, dpY+ (+dpE), paint); // 왼쪽 아래를 0,0 으로 보고있음
        	
        paint.setColor( 0xFF9fFF9f);
        canvasMemory.drawText(textTitle,dpLeft, dpY+0, paint); // 왼쪽 아래를 0,0 으로 보고있음
        
       
      // description  
       if( this.textDescription!= null)
        {
            paint.setTextSize( Global.Inst().DPFromPixel( 62 ));
            paint.setColor(Color.BLACK);
            
                    measuredTextWidth = (int) Math.ceil( paint.measureText(textDescription)) ;
                    dpLeft 	= ( (Global.DPFromPixel(WIDTH)/2) - (measuredTextWidth/2) );
 
           
            dpY += 70;
            
            canvasMemory.drawText(textDescription,dpLeft -dpD, dpY+ 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
            canvasMemory.drawText(textDescription,dpLeft +dpD, dpY+ 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
            canvasMemory.drawText(textDescription,dpLeft +0, dpY+ (-dpD), paint); // 왼쪽 아래를 0,0 으로 보고있음
            canvasMemory.drawText(textDescription,dpLeft +0, dpY+ (+dpD), paint); // 왼쪽 아래를 0,0 으로 보고있음

            paint.setColor( 0xFFFFFF9f);
            canvasMemory.drawText( this.textDescription,dpLeft, dpY, paint); // 왼쪽 아래를 0,0 으로 보고있음
        }
        
        
       
        
        // author
        paint.setTextSize( Global.Inst().DPFromPixel(AUTHOR_FONT) );
        
        int  temp_width = (int) Math.ceil( paint.measureText( textLyric)  );
        if ( temp_width < (int) Math.ceil( paint.measureText( textWriter) ))
             temp_width = (int) Math.ceil( paint.measureText( textWriter) );
        if ( temp_width < (int) Math.ceil( paint.measureText( textSinger) ))
             temp_width = (int) Math.ceil( paint.measureText( textSinger) );
        
        measuredTextWidth =  temp_width;
        
//        dpLeft  = Global.DPFromPixel(  (Constants.width/4 *3) - measuredTextWidth );
        dpLeft  = Global.DPFromPixel((WIDTH)) - measuredTextWidth ;
        if ( textLyric.length() > 0 )
        {
        	int top = (Global.DPFromPixel(HEIGHT)/6)*4;
        	paint.setColor(Color.BLACK);
        	canvasMemory.drawText(/*"작사 : "+*/textLyric,dpLeft-dpE, top-dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"작곡 : "+*/textWriter,dpLeft-dpE, top+50-dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"노래 : "+*/textSinger,dpLeft-dpE, top+100-dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	
        	canvasMemory.drawText(/*"작사 : "+*/textLyric,dpLeft+dpE, top+dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"작곡 : "+*/textWriter,dpLeft+dpE, top+50+dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"노래 : "+*/textSinger,dpLeft+dpE, top+100+dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
 
        	
        	canvasMemory.drawText(/*"작사 : "+*/textLyric,dpLeft-dpE, top, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"작곡 : "+*/textWriter,dpLeft-dpE, top+50, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"노래 : "+*/textSinger,dpLeft-dpE, top+100, paint); // 왼쪽 아래를 0,0 으로 보고있음

        	canvasMemory.drawText(/*"작사 : "+*/textLyric,dpLeft+0, top+dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"작곡 : "+*/textWriter,dpLeft+0, top+50+dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"노래 : "+*/textSinger,dpLeft+0, top+100+dpE, paint); // 왼쪽 아래를 0,0 으로 보고있음
 
        	paint.setColor(0xFF9f7fFF);
        	canvasMemory.drawText(/*"작사 : "+*/textLyric,dpLeft, top, 		paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"작곡 : "+*/textWriter,dpLeft, top+50, 	paint); // 왼쪽 아래를 0,0 으로 보고있음
        	canvasMemory.drawText(/*"노래 : "+*/textSinger,dpLeft, top+100, 	paint); // 왼쪽 아래를 0,0 으로 보고있음
    	}
    	
        lock.unlock();
    }
    public void setReady(int n)
    {
    	ready = n;
    	
        this.postInvalidate();
    }
    
    public void abort()
    {
    	
    	this.textTitle =  new String("");
    	this.textSinger =  new String("");
    	this.textWriter =  new String("");
    	this.textLyric =  new String("");
    	this.textDescription = new String("");
    	
    	
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
    	Log.d("ke", "Abort : title "+ this.textTitle );
        this.postInvalidate();
    }
   
    
    /*
     * 지연하여 타이틀을 지움 
     */
    public void delayClear()
    {
    	
   
    	Log.d("ke", "delayClear  ");
    	Log.d("ke", "delayClear  ");
    	Log.d("ke", "delayClear  ");
    	Log.d("ke", "delayClear  ");
    	

	    //if ( alpha_val > 0.3f)		// 지운상태가 아니라면 지울것이다. 
	    {
	    	Runnable r = new Runnable() {
    		public void run() {
				alpha_clear_draw();
    		}
	    	    	};
    		new Thread(r).start();
	    }
	    
    
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:     heightSize = Global.DPFromPixel(HEIGHT)/*bmBG.getHeight()*/;	break;	
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED: widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = Global.DPFromPixel(WIDTH)/*bmBG.getWidth()*/;		break;
        case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }

}