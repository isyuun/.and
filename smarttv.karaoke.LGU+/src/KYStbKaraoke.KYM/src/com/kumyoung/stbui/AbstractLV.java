package com.kumyoung.stbui; 
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KOSD;
import kr.kymedia.karaoke.util.gtv.Util;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 
 * @author macuser
 * View Class의 최상위 클래서 
 *
 */
abstract public class AbstractLV extends View 
{
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public String toString() {
		super.toString();
		return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	interface control {
	}
	
	class button {
		private Rect			rc;
		private Bitmap bmNormal = null;
		private Bitmap bmActive = null;
		private String text 	= null;
		
		private Boolean bVisible = true;
		
		public int	focusTextColor = Color.WHITE;
		
		public int indent = 0;
		
		
		private int FONT_H	= 32;	
		
		public button( int _x, int _y, int _w, int _h, Bitmap n, Bitmap a, String _text)
		{
			// Log.e(__CLASSNAME__, getMethodName() + "[RECT]" + "(" + _x + "," + _y + "," + _w + "," + _h + ")");
			rc = new Rect ( _x, _y, _x+_w, _y+_h );
			if (n != null) {
				n.setDensity(metrics.densityDpi);
			}
			if (a != null) {
				a.setDensity(metrics.densityDpi);
			}
			this.bmNormal = n;
			this.bmActive = a;
			this.text = _text;
		}
		
		public void SetBitmap ( Bitmap n, Bitmap a )
		{
			if (n != null) {
				n.setDensity(metrics.densityDpi);
			}
			if (a != null) {
				a.setDensity(metrics.densityDpi);
			}
		    this.bmNormal = n;
			this.bmActive = a;
			this.text = null;
			
		}
		
		public void SetText(String _text )
		{
			this.text = _text;
		}
		
		public void SetVisible( boolean b )
		{
			bVisible = b;
		}
		public void Indent( int _l)
		{
			indent = _l;
		}
		
		public void SetLeft( int _x)
		{
			int w = rc.right-rc.left;
			rc.left = _x;
			rc.right = rc.left + w;
		}
	
		
		public boolean GetVisible()
		{
			return bVisible;
		}
		public void draw ( Canvas c , boolean focus)
		{
			
			// // isyoon_20150427
			// c.setDensity(metrics.densityDpi);
			// if (bmNormal != null) {
			// bmNormal.setDensity(metrics.densityDpi);
			// }
			//
			// if (bmActive != null) {
			// bmActive.setDensity(metrics.densityDpi);
			// }

			// String text = getMethodName() + "[RECT]" + focus;
			// text += "\n[RECT][PX](" + "l:" + rc.left + ", t:" + rc.top + ", w:" + rc.width() + ", h:" + rc.height() + ")";
			// text += "\n[RECT][DP:NG](" + "l:" + Global.DPFromPixel(rc.left) + ", t:" + Global.DPFromPixel(rc.top) + ", w:" + Global.DPFromPixel(rc.width()) + ", h:" +
			// Global.DPFromPixel(rc.height()) + ")";
			// text += "\n[RECT][DP:OK](" + "l:" + Util.px2dp(getContext(), rc.left) + ", t:" + Util.px2dp(getContext(), rc.top) + ", w:" + Util.px2dp(getContext(), rc.width()) + ", h:" +
			// Util.px2dp(getContext(), rc.height()) + ")";
			// text += "\n" + bmNormal;
			// // text += event;
			// Log.e(__CLASSNAME__, text);

			if ( bVisible == false )
				return;
			
			if ( focus )
			{
				if ( bmNormal != null )
				{
					c.drawBitmap( bmNormal, Global.DPFromPixel(rc.left+indent), Global.DPFromPixel(rc.top+indent), null);
				}
				if ( bmActive != null )
				{
					c.drawBitmap( bmActive, Global.DPFromPixel(rc.left), Global.DPFromPixel(rc.top), null);
				}
				
				if ( text != null )
				{
					Paint paint = new Paint();
					paint.setAlpha( (int)(255 * 1.0) );
						
					paint.setAntiAlias(true); // 테두리를 부드럽게한다
					paint.setColor( focusTextColor );
					paint.setTextSize( Global.DPFromPixel(FONT_H) );		
					
					c.drawText( text , Global.DPFromPixel(rc.left+(20*2)), Global.DPFromPixel(rc.top+ ((20+6)*2) ), paint ); // 왼쪽 아래를 0,0 으로 보고있음
				}
			}
			else
			{
				Paint paint = new Paint();
				paint.setAlpha( (int)(255 * 0.9) );
			
				if ( bmNormal != null )
				{
					c.drawBitmap( bmNormal, Global.DPFromPixel(rc.left+indent), Global.DPFromPixel(rc.top+indent), paint);
				}
				if ( text != null )
				{
					paint.setAntiAlias(true); // 테두리를 부드럽게한다
					paint.setColor(Color.BLACK);
					paint.setTextSize( Global.DPFromPixel(FONT_H) );		
					
					c.drawText( text , Global.DPFromPixel(rc.left+20*2), Global.DPFromPixel(rc.top+ ((20 +6)*2)), paint ); // 왼쪽 아래를 0,0 으로 보고있음
				}
			}
			
			
			
			
		//
		//	debug
		//
			
			if ( Global.isDebugGrid == true )
			{
			    	final Paint p = new Paint();
			    	p.setColor( Color.GREEN );
			    	p.setStyle(Style.STROKE);
			    	c.drawRect( 
			    			Global.DPFromPixel(rc.left),
			    			Global.DPFromPixel(rc.top),
			    			Global.DPFromPixel(rc.right),
			    			Global.DPFromPixel(rc.bottom),
			    			 p);
			}
 
		}
		/**
		 * for memory 
		 * @param c
		 */
		//상대좌표 
		public void drawTranslate ( Canvas c)
		{
			if ( bVisible == false )
				return;

			// // isyoon_20150427
			// c.setDensity(metrics.densityDpi);
			// if (bmNormal != null) {
			// bmNormal.setDensity(metrics.densityDpi);
			// }
			//
			// if (bmActive != null) {
			// bmActive.setDensity(metrics.densityDpi);
			// }

			// String text = getMethodName() + "[RECT]";
			// text += "\n[RECT][PX](" + "l:" + rc.left + ", t:" + rc.top + ", w:" + rc.width() + ", h:" + rc.height() + ")";
			// text += "\n[RECT][DP:NG](" + "l:" + Global.DPFromPixel(rc.left) + ", t:" + Global.DPFromPixel(rc.top) + ", w:" + Global.DPFromPixel(rc.width()) + ", h:" + Global.DPFromPixel(rc.height()) + ")";
			// text += "\n[RECT][DP:OK](" + "l:" + Util.px2dp(getContext(), rc.left) + ", t:" + Util.px2dp(getContext(), rc.top) + ", w:" + Util.px2dp(getContext(), rc.width()) + ", h:" + Util.px2dp(getContext(), rc.height()) + ")";
			// text += "\n" + bmNormal;
			// // text += event;
			// Log.e(__CLASSNAME__, text);

			c.save(); // 현재 변환식을 저장
			c.translate( Global.DPFromPixel(rc.left), Global.DPFromPixel(rc.top));
	
			Paint paint = new Paint();
			paint.setAlpha( (int)(255 * 0.9) );
			
			if ( bmNormal != null )
			{
				c.drawBitmap( bmNormal, (0+indent), (0+indent), paint);
			}
	
			
			if ( text != null )
			{
			     final Paint p = new Paint();
        
			     p.setAntiAlias(true); // 테두리를 부드럽게한다
			     p.setColor(Color.BLACK);
				 p.setTextSize( Global.DPFromPixel(FONT_H) );		
			     
			     c.drawText (text,  Global.DPFromPixel(40), Global.DPFromPixel((20 +6)*2),  p ); // 왼쪽 아래를 0,0 으로 보고있음
			}
    
			c.restore(); // 원래 변환식으로 복구
			
			
			
			//
			//	debug
			//
			if ( Global.isDebugGrid == true ) 
			{
				final Paint p = new Paint();
				p.setColor( Color.RED );
				p.setStyle(Style.STROKE);
				c.drawRect( 
						Global.DPFromPixel(rc.left),
						Global.DPFromPixel(rc.top),
						Global.DPFromPixel(rc.right),
						Global.DPFromPixel(rc.bottom),
						p);
			}
		}
		
		public boolean ptInRect( int dpX, int dpY)
		{
		
		
		/*	
			String str = 	"l" + rc.left +  "t" + rc.top + "w" + rc.width() +"h" + rc.height() + " dp---" + X + "dpy" + Y ;
			
			Log.w(TAG, str );
			Log.w(TAG, "dpX "+ Global.PixelFromDP(X));
		*/
			
			// String text = getMethodName();
			// text += "\n[RECT][PX](" + dpX + "," + dpY + ")->";
			// text += "\n[RECT][DP:NG](" + Global.DPFromPixel((int) dpX) + "," + Global.DPFromPixel((int) dpY) + ")";
			// text += "\n[RECT][DP:OK](" + Util.px2dp(getContext(), dpX) + "," + Util.px2dp(getContext(), dpY) + ")";
			// text += "\n[RECT][PX](" + "l:" + rc.left + ", t:" + rc.top + ", w:" + rc.width() + ", h:" + rc.height() + ")";
			// text += "\n" + bmNormal;
			// // text += event;
			// Log.e(getClass().getSimpleName(), text);

			return rc.contains( Global.PixelFromDP(dpX), Global.PixelFromDP(dpY));
		}
		
	}
		
	private static final String TAG = AbstractLV.class.getSimpleName();
	
    protected   		Context		contax = null;     
	protected  			String text = null;
    protected int 		backgroundColor = 0xFFFFFFFF;
    protected int		redraw_count = 0;
    
    
   // protected				ModalWindow		container;		// 팝업으로 띄었을경우 페런트 
    protected		PopupWindow container;
   
    public String className()
    {
       return String.format("unknown class");
    }
    
    public void setParent ( PopupWindow p)
    {
    	container = p;
//		this.setOnFocusChangeListener(FocusListener);	/// 페런트의 포커스를 모니터링 한다.  
	}

    DisplayMetrics metrics;
    /**
     * 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
     * @param context
     */
    public AbstractLV(Context context) 
    {
        super(context);
//        Log.w(TAG,"AbstractLV("+context+")");
        contax = context;

		metrics = getResources().getDisplayMetrics();
		String text = getMethodName() + "[RECT]";
		text += "\n[RECT]" + "metrics.densityDpi:" + metrics.densityDpi;
		text += "\n[RECT]" + "metrics.density:" + metrics.density;
		text += "\n[RECT]" + "metrics.scaledDensity:" + metrics.scaledDensity;
		Log.d(TAG, text);
    }
    
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public AbstractLV(Context context,AttributeSet attrs) {
        this(context,attrs,0);
//        Log.w(TAG,"AbstractLV("+context+","+attrs+")");

		metrics = getResources().getDisplayMetrics();
		String text = getMethodName() + "[RECT]";
		text += "\n[RECT]" + "metrics.densityDpi:" + metrics.densityDpi;
		text += "\n[RECT]" + "metrics.density:" + metrics.density;
		text += "\n[RECT]" + "metrics.scaledDensity:" + metrics.scaledDensity;
		Log.d(TAG, text);
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public AbstractLV(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
       
        this.text = attrs.getAttributeValue(null,"text");
//        Log.w(TAG,"AbstractLV("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;

		metrics = getResources().getDisplayMetrics();
		String text = getMethodName() + "[RECT]";
		text += "\n[RECT]" + "metrics.densityDpi:" + metrics.densityDpi;
		text += "\n[RECT]" + "metrics.density:" + metrics.density;
		text += "\n[RECT]" + "metrics.scaledDensity:" + metrics.scaledDensity;
		Log.d(TAG, text);
    }
    
    /**
     * xml 로 부터 모든 뷰를 inflate 를 끝내고 실행된다.
     * 대부분 이 함수에서는 각종 변수 초기화가 이루어 진다.
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
   
    /*
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
   */ 
    
    /*
     *  onMeasure() 메소드에서 결정된 width 와 height 을 가지고 어플리케이션 전체 화면에서 현재 뷰가 그려지는 bound 를 돌려준다.
     *  
     *  이 메소드에서는 일반적으로 이 뷰에 딸린 children 들을 위치시키고 크기를 조정하는 작업을 한다.
     *  유의할점은 넘어오는 파라메터가 어플리케이션 전체를 기준으로 위치를 돌려준다.
     *  
     *  super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        Log.w(TAG,"onLayout("+changed+","+left+","+top+","+right+","+bottom+")");
    }
    
    /*
     * 이 뷰의 크기가 변경되었을때 호출된다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        Log.w(TAG,"onSizeChanged("+w+","+h+","+oldw+","+oldh+")");
    }

    /*
     * 실제로 화면에 그리는 영역으로 View 를 상속하고 이 메소드만 구현해도 제대로 보여지게 된다.
     * 
     * 그릴 위치는 0,0 으로 시작해서 getMeasuredWidth(), getMeasuredHeight() 까지 그리면 된다.
     * 
     * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    
    @Override
    protected void onDraw(Canvas canvas) 
    {
 
    	//Log.w(TAG,"onDraw");
    	//super.onDraw(canvas);

    
    	
    	if ( Global.isDebugGrid)
    	{
    		// debug 
    		final Paint p = new Paint();
    		if ( hasFocus() )
    		{
    			p.setColor( Color.GREEN );
    			p.setStyle(Style.STROKE);
    			canvas.drawRect(0,0,getMeasuredWidth()-1,getMeasuredHeight()-1, p);
    		}
    		// p.setColor(backgroundColor);
    		// canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
    		p.setAntiAlias(true); // 테두리를 부드럽게한다
    		p.setColor(Color.RED);
    		p.setTextSize( 20  );		
      
    		//canvas.drawText("["+redraw_count+"]" + "["+mouseX+","+mouseY+"]", 10, 15, p ); // 왼쪽 아래를 0,0 으로 보고있음
    		//debug
    		canvas.drawText("["+redraw_count+"]", 10, 15, p ); // 왼쪽 아래를 0,0 으로 보고있음

    		canvas.drawLine(0, 0, getMeasuredWidth(), getMeasuredHeight(), p);
    		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), 0, p);
    		redraw_count++;
    	}
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
	/**
	 * <pre>
	 * 20150414 : 0013239
	 * </pre>
	 * 
	 * <a href="http://evts.uplus.co.kr/evts/view.php?id=13239">0013239: [양방향] KY금영노래방 최신곡 HOT100 임의 곡에서 액션메뉴 출력 후 종료버튼을 입력하고 다시 KY금영노래방 진입 시 액션메뉴부터 출력되는 현상</a>
	 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
		Log.w(TAG, "onKeyDown(" + keyCode + "," + event + ")" + "[ST]");

		// 20150414
		// 종료키처리
		try {
			if (keyCode == KeyEvent.KEYCODE_TV || keyCode == 170) {
				Log.e(TAG, "onKeyDown(" + keyCode + "," + event + ")" + "[종료]");
				Activity host = (Activity) getContext();
				host.onKeyDown(keyCode, event);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ( KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ))
		{
			return false;	// booking 중이면 무조건 return;
		}
		
		Log.w(TAG, "onKeyDown(" + keyCode + "," + event + ")" + "[ED]");
        return super.onKeyDown(keyCode, event);
    }
    
    /**
     * 이 view 에 touch 가 일어날때 실행됨.
     * 
     * 기본적으로 touch up 이벤트가 일어날때만 잡아내며 
     * setClickable(true) 로 셋팅하면 up,move,down 모두 잡아냄
     */
   @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.w(TAG,"onTouchEvent("+event+")");
        switch(event.getAction()) {
        case MotionEvent.ACTION_UP:     break;
        case MotionEvent.ACTION_DOWN:	break;
        case MotionEvent.ACTION_MOVE:	break;
        }
//		setFocusable(true);
//		requestFocus();
        return super.onTouchEvent(event);
    }
   
  /* 
   public void popup( View popupview)
   {
        		{
//	        		SelectView popupview = new SelectView(contax);
        			popup = new PopupWindow( popupview, 300, 300, true);
        			popup.setOutsideTouchable(true);
        			popup.setBackgroundDrawable(new BitmapDrawable() );
        			
//        			popup.setAnimationStyle(R.anim.popup_show);
        			popupview.setOnKeyListener( new OnKeyListener() 
        			{
        				@Override
				        public boolean onKey(View v, int keyCode, KeyEvent event) 
        				{
        			
        					((SelectView)popup.getContentView()).onKeyDown(keyCode, event);
        					
        					if ( ((SelectView)popup.getContentView()).isQuit( ) )
        					{
        						popup.dismiss();
        					}
        					// 다시 수행 금지 
        					return  true;	
        				}
        			});
        		}
        		//popup.showAsDropDown(this,  0, 0);
        		popup.showAtLocation(this, Gravity.CENTER, 300, 100);
        		popupview.setFocusable(true);
        		popupview.requestFocus();
        		popupview.setFocusableInTouchMode(true);
   }
  */ 
  
   
   @Override
   protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) 
   {
	   super.onFocusChanged(focused, direction, previouslyFocusedRect);

       if (focused) 
       {
           switch (direction) {
               case View.FOCUS_DOWN:
                  // mSelectedRow = 0;
               	Log.e(TAG, "FOCUS_DOWN");
                   
                   break;
               case View.FOCUS_UP:
//                   mSelectedRow = mNumRows - 1;
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
//           invalidate();
       }
   } 
  
   /**
    * 
    */
   public void setSpot()
   {
       Log.e(TAG, "setSpot this control :" + className() );
	   this.setFocusable(true);
       this.requestFocus();
       this.setFocusableInTouchMode(true);	// Touch시에 포커스를 유지하려면..
       
  
//     if ( ViewManager.Inst().lpCur != ViewManager.Inst().lpOsdView )
       {
    	   ViewManager.Inst().lpAgo = ViewManager.Inst().lpCur;
    	   ViewManager.Inst().lpCur = this;
       }
   }
   
    public void deSpot()
   {
       Log.e(TAG, "deSpot this control ");
       this.clearFocus();
   }
  
   
}



