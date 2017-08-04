package com.kumyoung.stbui;


import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;
import com.kumyoung.gtvkaraoke.CustomDialog;
import com.kumyoung.gtvkaraoke.Database;
import kr.kumyoung.gtvkaraoke.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class PaddingView  extends AbstractLV {
   
	private static final String TAG = PaddingView.class.getSimpleName();
    private Bitmap 			bmBG;
    
   
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public PaddingView(Context context) 
    {
        super(context);
        Log.w(TAG,"CustomView("+context+")");

        contax = context;
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public PaddingView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        Log.w(TAG,"CustomView("+context+","+attrs+")");
       
       	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_title_bg );
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public PaddingView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
       
        this.text = attrs.getAttributeValue(null,"text");
        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;
        
//        setFocusableInTouchMode(true);
//        setFocusable(true);
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
    	super.onDraw(canvas);
    }
  
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
//       if ( show_key == true )
//        	return super.onKeyDown(keyCode, event); 
      
        switch ( keyCode )
        {
        
        case KMsg.MSG_KBD_DOWN : 
        			break;
        case KMsg.MSG_KBD_UP:			
        			break;
        case KMsg.MSG_KBD_LEFT:
        			break;
        case KMsg.MSG_KBD_RIGHT:
   					break;
      	case KMsg.MSG_KBD_STOP: 
  					return super.onKeyDown( KeyEvent.KEYCODE_BACK, event); 
        case KeyEvent.KEYCODE_ENTER : 
        			return true;
        	

       	default:
        // PARENT가 처리하게 내버려 둔다. 
        case KeyEvent.KEYCODE_BACK:
        			return super.onKeyDown(keyCode, event); 
        }
        invalidate();
//      return super.onKeyDown(keyCode, event); 
        return true;		
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:     heightSize = bmBG.getHeight();	break;	
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED:    widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = Global.DPFromPixel(400);/* bmBG.getWidth()*/;		break;
        case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
        }

//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }

    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w(TAG,"onTouchEvent("+event+")");
        switch(event.getAction()) {
        case MotionEvent.ACTION_UP:
            break;
        case MotionEvent.ACTION_DOWN:
            break;
        case MotionEvent.ACTION_MOVE:
            break;
        }
        return super.onTouchEvent(event);
    }
   

    @Override
    public boolean onCheckIsTextEditor() 
    {
        return true;
    }
}
    