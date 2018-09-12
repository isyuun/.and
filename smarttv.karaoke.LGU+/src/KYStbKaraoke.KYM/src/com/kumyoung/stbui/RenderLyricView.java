package com.kumyoung.stbui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;


public class RenderLyricView extends View {
//public class LyricView extends SurfaceView implements SurfaceHolder.Callback {
    
	private static final String TAG = RenderLyricView.class.getSimpleName();
    private String text = null;
    private int backgroundColor = Color.RED;//TRANSPARENT;
    private String tempText;
    private int px = 0;
    
    private Canvas 		canvas = null;		// the canvas to draw on
	private Context		mContext;
	private SurfaceHolder mHolder;
//	_GameThread			mThread;
	
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public RenderLyricView(Context context) {
        super(context);
        Log.w(TAG,"CustomView("+context+")");
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public RenderLyricView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        Log.w(TAG,"CustomView("+context+","+attrs+")");
        
		// adding the panel to handle events
//		mHolder = getHolder();
//		mHolder.addCallback(this);		// SurfcaeView움직이는 것은 SurfaceHolder임.
	
		mContext = context;
		
//        mThread = new _GameThread();
        
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public RenderLyricView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        
        this.text = attrs.getAttributeValue(null,"text");
        
        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        
    
    }
/*
    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
//		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
*/ 
    /*
     * xml 로 부터 모든 뷰를 inflate 를 끝내고 실행된다.
     * 
     * 대부분 이 함수에서는 각종 변수 초기화가 이루어 진다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onFinishInflate() {
        setClickable(true);
        Log.w(TAG,"onFinishInflate()");
    }
    
    /*
     * 넘어오는 파라메터는 부모뷰로부터 결정된 치수제한을 의미한다.
     * 또한 파라메터에는 bit 연산자를 사용해서 모드와 크기를 같이 담고있다.
     * 모드는 MeasureSpec.getMode(spec) 형태로 얻어오며 다음과 같은 3종류가 있다.
     *         MeasureSpec.AT_MOST : wrap_content (뷰 내부의 크기에 따라 크기가 달라짐)
     *         MeasureSpec.EXACTLY : fill_parent, match_parent (외부에서 이미 크기가 지정되었음)
     *      MeasureSpec.UNSPECIFIED : MODE 가 셋팅되지 않은 크기가 넘어올때 (대부분 이 경우는 없다)
     *  
     *   fill_parent, match_parent 를 사용하면 윗단에서 이미 크기가 계산되어 EXACTLY 로 넘어온다.
     *   이러한 크기는 MeasureSpec.getSize(spec) 으로 얻어낼 수 있다.
     *   
     *   이 메소드에서는 setMeasuredDimension(measuredWidth,measuredHeight) 를 호출해 주어야 하는데
     *   super.onMeasure() 에서는 기본으로 이를 기본으로 계산하는 함수를 포함하고 있다.
     *   
     *   만약 xml 에서 크기를 wrap_content 로 설정했다면 이 함수에서 크기를 계산해서 셋팅해 줘야한다.
     *   그렇지 않으면 무조껀 fill_parent 로 나오게 된다.
     */
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
            heightSize = 20;
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
            widthSize = 100;
            break;
        case MeasureSpec.EXACTLY:        // fill_parent, match_parent (외부에서 이미 크기가 지정되었음)
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
            break;
        }

        
        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        
        setMeasuredDimension(widthSize, heightSize);
    }
    
    
    /*
     *  onMeasure() 메소드에서 결정된 width 와 height 을 가지고 어플리케이션 전체 화면에서 현재 뷰가 그려지는 bound 를 돌려준다.
     *  
     *  이 메소드에서는 일반적으로 이 뷰에 딸린 children 들을 위치시키고 크기를 조정하는 작업을 한다.
     *  유의할점은 넘어오는 파라메터가 어플리케이션 전체를 기준으로 위치를 돌려준다.
     *  
     *  super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        Log.w(TAG,"onLayout("+changed+","+left+","+top+","+right+","+bottom+")");
    }
    
    
    /*
     * 이 뷰의 크기가 변경되었을때 호출된다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        
        Log.w(TAG,"onSizeChanged("+w+","+h+","+oldw+","+oldh+")");
    }
    
    
    /*
     * 실제로 화면에 그리는 영역으로 View 를 상속하고 이 메소드만 구현해도 제대로 보여지게 된다.
     * 
     * 그릴 위치는 0,0 으로 시작해서 getMeasuredWidth(), getMeasuredHeight() 까지 그리면 된다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onDraw(Canvas canvas) {
    	
        final Paint p = new Paint();
        p.setColor(backgroundColor);
       
        //canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
        if (text != null) {
        	p.setColor(Color.BLACK );
        	
        	int y = getMeasuredHeight()/2 + 10;
        	
        	p.setAntiAlias(true); // 테두리를 부드럽게한다
        	p.setTextSize(38);
            canvas.drawText(text, -3, y+ 0, p); // 왼쪽 아래를 0,0 으로 보고있음
            canvas.drawText(text, +3, y+ 0, p); // 왼쪽 아래를 0,0 으로 보고있음
            canvas.drawText(text, 0, y+ (-3), p); // 왼쪽 아래를 0,0 으로 보고있음
            canvas.drawText(text, 0, y+ (+3), p); // 왼쪽 아래를 0,0 으로 보고있음

            
            canvas.drawText(text, -2, y+ (-2), p); // 왼쪽 아래를 0,0 으로 보고있음
            canvas.drawText(text, +2, y+ (-2), p); // 왼쪽 아래를 0,0 으로 보고있음
            canvas.drawText(text, +2, y+ (+2), p); // 왼쪽 아래를 0,0 으로 보고있음
            canvas.drawText(text, -2, y+ (+2), p); // 왼쪽 아래를 0,0 으로 보고있음
        	p.setColor(Color.WHITE);
        	
            canvas.drawText(text, 0, y+0, p); // 왼쪽 아래를 0,0 으로 보고있음
            
//          FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
            
            
            
        }
        
        p.setColor( Color.BLUE );
		p.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.XOR ) );
		        			  
        canvas.drawRect(0,0,px, getMeasuredHeight(), p);
        
        //Log.w(TAG,"onDraw("+canvas+")");
    }
    
    
    /*
     * 현재 view 가 focus 상태일때 key 를 누르면 이 메소드가 호출됨.
     * 즉 이 메소드를 사용하려면 setFocusable(true) 여야함. 
     * 
     * 그리고 super 메소드에서는 기본적인 키 작업(예를들면 BACK 키 누르면 종료)을 처리하기 때문에 일반적으로 return 시에 호출하는게 좋다.
     * 만약 기본적인 작업을 하지않게 하려면 super 함수를 호출하지 않아도 된다.
     * 
     * 다른 event 메소드들도 유사하게 동작한다.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
       return super.onKeyDown(keyCode, event); 
    }
    
    /*
     * 이 view 에 touch 가 일어날때 실행됨.
     * 
     * 기본적으로 touch up 이벤트가 일어날때만 잡아내며 
     * setClickable(true) 로 셋팅하면 up,move,down 모두 잡아냄
     */
/* 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w(TAG,"onTouchEvent("+event+")");
        switch(event.getAction()) {
        case MotionEvent.ACTION_UP:
            backgroundColor = Color.RED;
            text = tempText;
            break;
        case MotionEvent.ACTION_DOWN:
            backgroundColor = Color.YELLOW;
            tempText = text;
            text = "Clicked!";
            break;
        case MotionEvent.ACTION_MOVE:
            backgroundColor = Color.TRANSPARENT;
            text = "Moved!";
            break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
*/    
    
 //   public String getText() {
  //      return text;
   // }
    public void setText(String text) {
        this.text = text;
        
        
 //		invalidate();
        px = 0;		// reset painting 
        this.postInvalidate();
    }
    
    public void setScrolling( int x )
    {
    	px = x;
        this.postInvalidate();
    }
    
    class _GameThread extends Thread 
    {
		public _GameThread() 
		{
            //mCustomBitmap = Bitmap.createBitmap( 960, 540, Bitmap.Config.ARGB_4444 );
		}
		
		private void doDrawDemo ( Canvas canvas )
		{		
			// some draw
		}

//		private void doDraw ( Canvas canvas )
//		{
//			doDrawDemo ( canas );
//		}
		
			
		// GameLoop
		public void run ()
		{
			while( true )
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        // 1초간 대기후
				
		    	Canvas canvas = null;
		 	
		        if( mHolder.getSurface().isValid())
		        {
		        	// drawing lock 
		        	try
		        	{        		
		        		canvas = mHolder. lockCanvas(null);		// canvas를 잠그고 버퍼를 할당. 
		        		
		        		synchronized (mHolder)			// 동기화 유지 
		        		{
		        			
		        		//	canvas.drawBitmap ( imgBack, i, 0, null ); 		// 버퍼에 그리기 
		        		//	canvas.drawBitmap ( mOffscreenBitmap, 0, 0, null);
		        			canvas.drawColor(Color.BLACK);
		        			Paint Pnt = new Paint();
		        			Pnt.setColor(Color.RED);
		        		
		        			// draw glyphs
		        			//glyphs.drawString(canvas, "Po ", 100, 100 );
		        			
		        			Pnt.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.MULTIPLY ) );
		        			  
		        			canvas.drawRect(0,0,px, getMeasuredHeight(), Pnt);
		        			
		        	//		canvas.drawCircle( 0, 0, px, Pnt);
		        		}
		        		
		        		//doDraw( canvas );
		        	}
		        	finally
		        	{
		        		// surce 출력 
		        		if ( canvas != null)
		        			mHolder.unlockCanvasAndPost( canvas );			// Canvas 내용을 View에 전송 
		        	}
		        	
		        	
		        }
				
			}
		}
	}

}