package com.kumyoung.stbui;



import isyoon.com.devscott.karaengine.Global;

import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.graphics.Bitmap.Config;


public class ScoreView extends AbstractLV {
    
	private static final String TAG = ScoreView.class.getSimpleName();
	
    private String 		textScore = null;
	private Bitmap 		bitmapMemory ;
	private Canvas 		canvasMemory = null;
	
	private final int 	WIDTH		= (900);
	private final int 	HEIGHT		= (680); 
    private Bitmap 		bmBG = null;// = new Bitmap;
    
    private Bitmap		[]	bmNumber;
    private Bitmap		bmComment = null;
	float		alpha_val = 0.0f;
	
	
	final private int SLEEP_INTERVAL = 140;

	private boolean stable = false;
	private int	score_ten = 0;
	private int score_zero = 0;
   
	/*
     * 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 	
     */
    public ScoreView(Context context) {
        super(context);
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public ScoreView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
    }
    
    /*
     * 
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public ScoreView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        
        
        this.textScore = attrs.getAttributeValue(null,"text");
        
        Log.d( TAG, "Score Initit initial = textTitle " + this.textScore );
        
        bitmapMemory = Bitmap.createBitmap( Global.DPFromPixel(WIDTH), Global.DPFromPixel(HEIGHT), Config.ARGB_8888);
    	canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

    	/** */
    	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_bg);
       
        canvasMemory.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
        
        bmNumber = new Bitmap[10];
        
    	bmNumber[0] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num0 );
    	bmNumber[1] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num1 );
    	bmNumber[2] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num2 );
    	bmNumber[3] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num3 );
    	bmNumber[4] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num4 );
    	bmNumber[5] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num5 );
    	bmNumber[6] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num6 );
    	bmNumber[7] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num7 );
    	bmNumber[8] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num8 );
    	bmNumber[9] = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_num9 );
        
    
      	score_ten = (int) (Math.random()* 10.0f);
      	score_zero = (int) (Math.random()* 10.0f);
      	switch ( score_ten )
      	{
      		default:
      		case 0:
      		case 1:
      		case 2:
      		case 3:
      		case 4:
      		case 5:	bmComment = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_comment_50 );	break;
      		case 6:	bmComment = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_comment_60 );	break;
      		case 7:	bmComment = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_comment_70 );	break;
      		case 8: bmComment = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_comment_80 );	break;
      		case 9: bmComment = BitmapFactory.decodeResource( contax.getResources(), R.drawable.score_comment_90 );	break;
      	}
    	
   }

    @Override
    protected void onDraw(Canvas canvas)
    {
    
        Log.d( TAG, "ScoreView  onDraw ");
        final Paint paint = new Paint();
        paint.setColor(0x00000000);
        canvas.drawRect( new Rect(0,0, Global.DPFromPixel(WIDTH), Global.DPFromPixel(HEIGHT)), paint );
       
        // 배경을 그린다. 
        paint.setAlpha( (int)(255 * alpha_val) );
       	canvas.drawBitmap(bitmapMemory, 0, 0, paint);
       	
       	// 랜덤 점수를 그린다. 
       	if ( stable )
       	{
       		int ten = (int) (Math.random()* 10.0f);
       		int zero = (int) (Math.random()* 10.0f);
       	
       		score_ten = score_ten % 5;
       		canvas.drawBitmap(bmNumber[score_ten+5],  Global.DPFromPixel(165+80), 		Global.DPFromPixel(90+80), paint);
       		canvas.drawBitmap(bmNumber[score_zero],   Global.DPFromPixel(160+80+180),   Global.DPFromPixel(90+80), paint);
       	
       		canvas.drawBitmap(bmComment, Global.DPFromPixel(160+80), Global.DPFromPixel(215+170), paint);
       	}
       	else
       	{
        	int ten = (int) (Math.random()* 10.0f);
       		int zero = (int) (Math.random()* 10.0f);
       		ten = ten % 5;
       		
       		canvas.drawBitmap(bmNumber[ten+5],  Global.DPFromPixel(165+80), 	Global.DPFromPixel(90+80), paint);
       		canvas.drawBitmap(bmNumber[zero],   Global.DPFromPixel(160+80+180), Global.DPFromPixel(90+80), paint);
 
       	}
       	
        super.onDraw( canvas );	// draw cross
    }
     
    
    /**
     * 
     * 
     */
    private void alpha_draw()
    {
    
    	for ( alpha_val = 0.0f; alpha_val < 1.0f; alpha_val+=0.04f )
   		{
    	
    		Log.d(TAG, "score fade in " + alpha_val );
    		this.postInvalidate();
    		
    		try {
				Thread.sleep( SLEEP_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        
    	}
    	
    	int count = 0;
			while ( stable == false )
			{
				
				Log.d( TAG, "ScoreView call Invalidate");
  					
  					this.postInvalidate();
  					try {
  						Thread.sleep( SLEEP_INTERVAL);
  					} catch (InterruptedException e) {
  						e.printStackTrace();
  					}        
  					
  				if ( count++ > 100000)
  				{
  					stable = true;
  					Log.d( TAG, "retrun stable " + count);
  				}
			}
			
			
		Log.d(TAG, "STABLE LOGOUT");
		this.postInvalidate();
    }
   
    /**
     * 
     * 
     */
    public void dump_score()
    {
    	stable = false;
    	
       	Log.d(TAG, "Dump -----" + this.textScore);
     	Runnable r = new Runnable() {
  		public void run() {
  			
  				alpha_draw();
  				
 			}
    	};
    	new Thread(r).start();
    }
    
    
    
    
    
    
    public void score_numdisp( int value )
    {
    	Log.d( TAG, "score view  STABLE");
    	
    	stable = true;
    }
 
    /**
     * 
     * @param _title
     * @param _textDescription
     */
    public void setScore(String _title, String _textDescription ) {
    	
    	Log.d( TAG, "setScore " + _title + _textDescription );
    	
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        	case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        	case MeasureSpec.AT_MOST:     heightSize = Global.DPFromPixel(HEIGHT);	break;	
        	case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        	case MeasureSpec.UNSPECIFIED: widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        	case MeasureSpec.AT_MOST:     widthSize = Global.DPFromPixel(WIDTH);		break;
        	case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }
//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }

}