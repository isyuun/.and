package com.kumyoung.stbui;

//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.Global;

import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

public class ChallengeFiftyView extends AbstractTV/*View*/ {
  
	private static final String TAG = ChallengeFiftyView.class.getSimpleName();
	
//    private String 		buf[] = {"시작", "예약", "예약(코러스", "애창곡으로 등록", "닫기" };
    
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public ChallengeFiftyView(Context context) 
    {
    	
    	// 쓰면 안됨;
        super(context);
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public ChallengeFiftyView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public ChallengeFiftyView(Context context,AttributeSet attrs,int defStyle) {
    	
    	
    	
        super(context,attrs,defStyle);
    	_prepare(10,5, 0);
        
        
//        this.text = attrs.getAttributeValue(null,"text");
//        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
//        contax = context;
       
//      adapter = new ArrayAdapter<String> (contax, R.layout.listviewitem, m_item_string);
         
        // 리소스 파일에 정의된 id_list 라는 ID 의 리스트뷰를 얻는다.
//      ListView list = (ListView) findViewById(R.id.listSongView1);
        // 리스트뷰에 ArrayAdapter 객체를 설정하여 리스트뷰에 데이터와 출력 형태를 지정한다.
        // list.setAdapter(adapter);        	

//        bmTop 		= BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_title);
//        bmBottom 	= BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_bottom);
        
        
//        bitmapList = Bitmap.createBitmap(480,480, Config.ARGB_8888);
//        canvasList = new Canvas(bitmapList);
        
//        setFocusable(true);
        
        
            
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.challenge_bg );
//       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_pop_f );
        
        
        
       	
        btn = new button[MAX_ITEMS];
       
        for ( int r = 0; r < row; r++)
        	for ( int c = 0; c < col; c++)
        	{
        		
        		int idx = (r*(col)+c);
        		btn[idx] = new button( 	14 + c * 70,  30 + r* 30, 200, 50, null, BitmapFactory.decodeResource( contax.getResources(), R.drawable.chall_f ),  Integer.toString (idx+1)  );
        	}
        
        
        // create memory 
        
       	bitmapMemory = Bitmap.createBitmap( bmBG.getWidth(), bmBG.getHeight(), Config.ARGB_8888);
        canvasMemory = new Canvas(bitmapMemory);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

        // draw
   		if ( bmBG != null )
        	canvasMemory.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
   	
   		// draw
	    for ( int i =0; i< MAX_ITEMS; i++)
       		btn[i].drawTranslate(canvasMemory);

	    
	   
	    /***/
        final Paint p = new Paint();
       	p.setColor( 0x7fffffFF );   	//빨간 계열 비트맵으로 그린다.	
        canvasMemory.drawLine(0, 0,   bmBG.getWidth(), bmBG.getHeight(), p);
        canvasMemory.drawLine(0, bmBG.getHeight(), bmBG.getWidth(), 0, p); 
     
   } 
    

     public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        switch ( keyCode )
        {
        
        	case KeyEvent.KEYCODE_ENTER : 
        		Global.Inst().app.doMenu( 99 );
        		return false;
       
        }
        return super.onKeyDown(keyCode, event); 
    }
   
    
     // over
     public void doCommand(int key) {
     
     }
     
     // over 
     public void onSubDraw( Canvas canvas )
     {
    	 
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
         case MeasureSpec.AT_MOST:     widthSize = bmBG.getWidth();		break;
         case MeasureSpec.EXACTLY:     widthSize = MeasureSpec.getSize(widthMeasureSpec);		break;
         }

         Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
         setMeasuredDimension(widthSize, heightSize);
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
        public Boolean lostFocus(int keyCodey)
        {
            return false;
        }    
    
}