package com.kumyoung.stbui;

import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

abstract public class AbstractTV extends AbstractLV {

	private static final String TAG = AbstractTV.class.getSimpleName();
//    private String 			text = null;
//    protected int 			backgroundColor = 0xFFFFFFFF;
    protected Bitmap 		bmBG;
    protected Bitmap 		bmFocus;
//    protected Context		contax = null;     
    
    private	int				curPos = 0;
    private Boolean 		jump_flag = false;
    
//    private int 			lines = 5;
//    private int 			rows = 10;
//    protected int				redraw_count = 0;
    
    protected button 		btn[];
    
    
    protected int 			row = 0;
    protected int 			col = 0;
    protected int			close = 0;
    protected int			MAX_ITEMS = 0;
    
   
    // memory dc  
	protected Bitmap 		bitmapMemory ;
	protected Canvas 		canvasMemory = null;

	
	private int			mouseX = 0;
	private int			mouseY = 0;
	
    private String 		tempText;

	protected int GetPos() { return curPos; }
	protected void SetPos(int pos) { curPos = pos; }
	


	/* 포커스 변화 리슨어 등록 */
/*	
	FocusListenerClass FocusListener = new FocusListenerClass();
    class FocusListenerClass implements View.OnFocusChangeListener {
    	public void onFocusChange(View v,boolean hasFocus) {
    		
    		
    		invalidate();
    	}
   	}
   */ 
	
	protected void _prepare( int _col, int _row, int _close )
	{
        row = _row;
        col = _col;
        
        close = _close;
        MAX_ITEMS = ( (row*col) + _close );
	}
	
	
    /**
     * 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
     * @param context
     */
    public AbstractTV(Context context) 
    {
        super(context);
//        Log.w(TAG,"CustomView("+context+")");

        contax = context;
    }
    
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public AbstractTV(Context context,AttributeSet attrs) {
        this(context,attrs,0);
 //       Log.w(TAG,"CustomView("+context+","+attrs+")");
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public AbstractTV(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
       
        this.text = attrs.getAttributeValue(null,"text");
  //      Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;
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
            heightSize = bitmapMemory.getHeight();
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
            widthSize = bitmapMemory.getWidth();
            break;
        case MeasureSpec.EXACTLY:        // fill_parent, match_parent (외부에서 이미 크기가 지정되었음)
            widthSize = MeasureSpec.getSize(widthMeasureSpec);
            break;
        }
//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
		Log.w(TAG, "onMeasure() " + "(" + widthMode + "," + heightMode + ")" + ":" + "(" + widthMeasureSpec + "," + heightMeasureSpec + ")" + "->" + "(" + widthSize + "," + heightSize + ")");
        setMeasuredDimension(widthSize, heightSize);
    }
   
    
    /*
     * 실제로 화면에 그리는 영역으로 View 를 상속하고 이 메소드만 구현해도 제대로 보여지게 된다.
     * 그릴 위치는 0,0 으로 시작해서 getMeasuredWidth(), getMeasuredHeight() 까지 그리면 된다.
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onDraw(Canvas canvas) {
//    	Log.w(TAG,"onDraw("+canvas+")" + (++redraw_count) );
    	if ( bitmapMemory == null)
    	{
    		Log.e(TAG, "not create bitmapMemory");
    	}
    	else
    	{
    		canvas.drawBitmap(bitmapMemory, 0, 0, null );
    	}
    	
    	if (  backgroundColor == Color.YELLOW )		// 마우스 터치가 있으면 우선적으로 포커스를 그린다. 
    	{	
    		//if (Global.isVoka == false)
    			btn[curPos].draw(canvas, true );
    	}
    	else
    	{
    		if ( /*!KOSD.Inst().is(KOSD.eOSD_BOOK_EDIT) && */hasFocus() )
    		{
    			//if (Global.isVoka == false)
    				btn[curPos].draw(canvas, true );
    		}
    	}
  		
  		onSubDraw( canvas );
       
  		//draw debug method
		super.onDraw(canvas);
    }

    
    /*
     * 현재 view 가 focus 상태일때 key 를 누르면 이 메소드가 호출됨.
     * 즉 이 메소드를 사용하려면 setFocusable(true) 여야함. 
     * 
     * 그리고 super 메소드에서는 기본적인 키 작업(예를들면 BACK 키 누르면 종료)을 처리하기 때문에 일반적으로 return 시에 호출하는게 좋다.
     * 만약 기본적인 작업을 하지않게 하려면 super 함수를 호출하지 않아도 된다.
     * 
     * 다른 event 메소드들도 유G사하게 동작한다.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    
//    	if ( event.getAction() != KeyEvent.ACTION_UP )
//    		return true;
    	
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");

		// 20150414
		// 종료키처리
		try {
			if (keyCode == KeyEvent.KEYCODE_TV || keyCode == 170) {
				Log.e(TAG, "onKeyDown(" + keyCode + "," + event + ")" + "[종료]");
				return super.onKeyDown(keyCode, event);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ( col == 0 && row == 0 &&
        		(	(keyCode != KeyEvent.KEYCODE_ENTER) && 
        			(keyCode != KeyEvent.KEYCODE_DPAD_CENTER)   
        		) 
            ) 
        	return false;
        
        switch ( keyCode )
        {
        case KMsg.MSG_KBD_LEFT:
        	SoundManager.getInstance().playSound(1);
			if( curPos > 0 )
				curPos--; 
			else
			{
				curPos = (MAX_ITEMS-1);
				if ( lostFocus(keyCode) )
				{
					;
				}
				else
					curPos = (MAX_ITEMS-1);
			}
			break;
		
        case KMsg.MSG_KBD_RIGHT: 
        	SoundManager.getInstance().playSound(1);
        	if ( ((curPos+1) % col) == 0)
        	{
				if ( lostFocus(keyCode) )
					break;
        	}
			if ( (curPos+1) < MAX_ITEMS )
				curPos++; 
			else
				curPos = 0;
			break;
			
        case KMsg.MSG_KBD_UP:		
        	
        	SoundManager.getInstance().playSound(1);
        	if ( close>0 && (curPos == (MAX_ITEMS-1)) )
       	{
       			curPos -= 1;
       		}
       		else
       		{
       				curPos -= (col);			//대각선 
       				if ( curPos < 0)
       					curPos += MAX_ITEMS;
       		}
       		break;

        case KMsg.MSG_KBD_DOWN : 
        		SoundManager.getInstance().playSound(1);
        		
       		if ( close > 0)
       		{
        		if (((curPos/col) == (row-1)) )		// CLOSE 있는 경우 
        		{
        		//	curPos = ((MAX_ITEMS-1)/col);
        			curPos = (MAX_ITEMS-1);		// 닫기로 이동 
        			jump_flag  = true;
        		}
        		else	// 걸렸을때 
        		{
        			if ( curPos == (MAX_ITEMS-1) )		//  닫기 위치라면 
        			{
        				
        				//한방향으로 갈것인가?
        				if( jump_flag == true)
        				{
        					curPos = 1;
        					jump_flag = false;
        				}
        				else
        				{
        					curPos = 0;
        					jump_flag = true;
        				}
        			}
        			else
        				curPos+=( (col) );		//COL을 넓이를 더해서 아래로 내려갈수 있다 
       				
       				if( curPos >= MAX_ITEMS )	// over 했다면 
       				{
       					curPos -= MAX_ITEMS;
       				
       					jump_flag = false;
       				}
        		}
       		}
       		else
       		{
       			
       			int cursor_row = ( curPos / col);
       			if ( cursor_row == (row-1))			// 맨 밑에 row라면 
       			{
       				if ( curPos == MAX_ITEMS-1)
       				{
       					curPos = 0;
       				}
       				else
       				{
       					curPos+=( (col) );		//COL을 넓이를 더해서 아래로 내려갈수 있다 
       					curPos -= MAX_ITEMS;
       					curPos += 1;
       				}
       			}
       			else
       			{
        			curPos+=( (col) );		//COL을 넓이를 더해서 아래로 내려갈수 있다 
       				if( curPos >= MAX_ITEMS )	// over 했다면 
       					curPos -= MAX_ITEMS;
       			}
       		}
   			break;
        
        case KeyEvent.KEYCODE_DPAD_CENTER:
        case KeyEvent.KEYCODE_ENTER : 
        	
        		SoundManager.getInstance().playSound(2);
        		doCommand(keyCode);
        		break;
 
        // PARENT가 처리하게 내버려 둔다. 
        case KeyEvent.KEYCODE_BACK:
        		SoundManager.getInstance().playSound(2);
        		doCommand(keyCode);
        		break;
        default:
        	//START, STOP, NUMBER등이 여기서 처리되지 못하고 Activity로 넘겨줄것이다.
        	// 키 처리후 이 핸들에서 한번 드로잉.
        
        	
        	
//        	if ( KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ))
 //       	{
  //      		return false;	// booking 중이면 무조건 return;
  //      	}
        	
       		boolean b =super.onKeyDown(keyCode, event);		// 키를 라우팅 해 준다.   
       		invalidate();
       		return b;
        }
       
        invalidate();
        return true;
    }
    
    
    
    void nextKey()
    {
    	if ( (curPos+1) < MAX_ITEMS )
			curPos++; 
		else
			curPos = 0;
    	invalidate();
    }
    
/*    
    public boolean isQuit(  )
    {
    	return bQuit;
    }
 */   
  
    
    /**
     * 이 view 에 touch 가 일어날때 실행됨.
     * 
     * 기본적으로 touch up 이벤트가 일어날때만 잡아내며 
     * setClickable(true) 로 셋팅하면 up,move,down 모두 잡아냄
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.w(TAG,"onTouchEvent("+event+")" + "focus" + hasFocus() );
        
        
    	mouseX =(int) event.getX();
    	mouseY =(int) event.getY();

    	int cur = -1;
    	for ( int i = 0; i < MAX_ITEMS; i++)
    		if ( btn[i].ptInRect( mouseX, mouseY ) == true )
    		{
    			cur = i;
    			break;
    		}
    	
    	if ( cur == -1)
    	{
    		Log.d(TAG, "Touch not found");
    		return false;
    	}
    	
        switch(event.getAction()) {
        case MotionEvent.ACTION_UP:
            backgroundColor = Color.RED;
            text = tempText;
            if ( cur >= 0 )
            {
            	curPos = cur;
            	doCommand( 0 );
            }
            
            invalidate();
            break;
        case MotionEvent.ACTION_DOWN:
            backgroundColor = Color.YELLOW;
            tempText = text;
            text = "Clicked!";
            if ( cur >= 0 )
            {
            	curPos = cur;
            }
            
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
        return super.onTouchEvent(event);
    }
   
    abstract public void doCommand( int key );  
    abstract public void onSubDraw( Canvas canvas );
  //  abstract public void doFocusChange(Boolean focused, int direction);
    
    abstract public Boolean lostFocus(int key);
}


