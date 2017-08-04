package com.kumyoung.stbui;


//import com.ky.stbkaraoke.KYStbKaraokeActivity;
//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

import com.kumyoung.gtvkaraoke.CustomDialog;
import com.kumyoung.gtvkaraoke.Database;
import kr.kumyoung.gtvkaraoke.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

public class FavoriteSongListView extends AbstractLV {
    
	private static final String TAG = FavoriteSongListView.class.getSimpleName();
    
	
	private Bitmap 			bmBG;
    private Bitmap 			bmNothing;
    
    private Bitmap 			bmFocus;
    
    
    private	int			curPos = 0;
    private int 		lines = 8;
    private int			redraw_count = 0;
    private int 		cur_sno = 101;
    private Bitmap 		bmNumber = null;
    
    
    private PopupWindow	popup =null;
   
    /*
    LinearLayout linear;
    Button[] btnLine = new Button[8];    
    */ 
    
   
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public FavoriteSongListView(Context context) 
    {
        super(context);
        Log.w(TAG,"CustomView("+context+")");

        contax = context;
        
//        bmTop = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_title);
//        bmBottom = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_bottom);
        

        
//        bitmapList = Bitmap.createBitmap(480,180, Config.ARGB_8888);
//        canvasList = new Canvas(bitmapList);
        
        
       /* 
        linear = (LinearLayout) findViewById(R.id.linearLayoutList);
        for (int i = 0; i < 8; i++)
        {
            btnLine[i] = new Button( context );
            btnLine[i].setText("sadf");
            btnLine[i].Height = 20;
            btnLine[i].Width = 10;
            btnLine[i].Left = 20 + 11 * i;
            btnLine[i].Top = 660;
            btnLine[i].BackColor = Color.Blue;
            btnLine[i].Tag = i.ToString();
        //    btnLine[i].Click += new System.EventHandler(this.btnWord_Click);
           
            linear.addView( btnLine[i],
                                  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT ,
                                          LinearLayout.LayoutParams.WRAP_CONTENT ));
        }
       */ 
        
        //setFocusable(true);
 
        
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public FavoriteSongListView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        Log.w(TAG,"CustomView("+context+","+attrs+")");
        
       	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_bg );
       	bmNothing = BitmapFactory.decodeResource( contax.getResources(), R.drawable.info );
       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_f );
       	
        bmNumber = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_num_s);
        
        //setFocusable(true);
        
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public FavoriteSongListView(Context context,AttributeSet attrs,int defStyle) 
    {
        super(context,attrs,defStyle);
        
        this.text = attrs.getAttributeValue(null,"text");
        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;
       
//      adapter = new ArrayAdapter<String> (contax, R.layout.listviewitem, m_item_string);
        
         
        // 리소스 파일에 정의된 id_list 라는 ID 의 리스트뷰를 얻는다.
//      ListView list = (ListView) findViewById(R.id.listSongView1);
        // 리스트뷰에 ArrayAdapter 객체를 설정하여 리스트뷰에 데이터와 출력 형태를 지정한다.
        // list.setAdapter(adapter);        	

//        bmTop 		= BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_title);
//        bmBottom 	= BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_bottom);
        
        
//        bitmapList = Bitmap.createBitmap(480,480, Config.ARGB_8888);
//        canvasList = new Canvas(bitmapList);
        
        //setFocusable(true);
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
     *  onMeasure() 메소드에서 결정된 width 와 height 을 가지고 어플리케이션 전체 화면에서 현재 뷰가 그려지는 bound 를 돌려준다.
     *  
     *  이 메소드에서는 일반적으로 이 뷰에 딸린 children 들을 위치시키고 크기를 조정하는 작업을 한다.
     *  유의할점은 넘어오는 파라메터가 어플리케이션 전체를 기준으로 위치를 돌려준다.
     *  
     *  super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
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
    
      

    /**
     * 
     * @param cv
     */
    void onDrawList( Canvas cv)
    {
    	final Paint p = new Paint();
    	
        p.setColor(backgroundColor);
//        cv.drawRect(0,0,Constants.width,480, p);
        
        p.setAntiAlias(true); // 테두리를 부드럽게한다
        p.setColor(Color.WHITE);
       
        
//        int h = 32;
		p.setTextSize( 18 );
		
		int row = Global.DPFromPixel(33 + 2);
        if (text != null) {
//			canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
//			FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
        }

        
    	Boolean b = hasFocus();
        if ( b )
        	p.setColor(Color.WHITE);
        else
        	p.setColor(Color.RED);
       
        
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        
        int lineperheight = row;
       
        
       
        if ( bmBG != null )
        	cv.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 

       
      
//        if ( bmSelected != null )
//        	cv.drawBitmap ( bmSelected, 0, curPos*row, null ); 		// 버퍼에 그리기 
        
               
        if ( !KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) )
        {
        	if ( b )
        	{
        		//draw focus
        		cv.drawBitmap ( bmFocus, 0, 8 + curPos*row -4, null ); 		// 버퍼에 그리기 
        	}
        }
 
        	
        //cv.drawRect( 0, curPos*row, width, curPos*row+row, p);

        boolean blank = true;
        if ( blank )
        {
        	if ( bmNothing != null )
        		cv.drawBitmap ( bmNothing, 100, 50, null ); 		// 버퍼에 그리기 
        }
        else
        {
        
        	for ( int i = 0; i < lines; i++ )
        	{
        	int y = ( row * i);
        	int typo_y = y +  Global.DPFromPixel(32)/*font h*/;
        	
       		String[] columns = {"sno","title", "artist"};
       		Database.Inst().query_song_info( cur_sno +  i, columns);	
        	
        	drawSongNumber( cv, 14, 16+y,  (cur_sno+i) );
        	
        	p.setColor( 0xFFD09090);
        	cv.drawText( columns[1], Global.DPFromPixel(105), typo_y, p );
        	p.setColor( 0xFFD0D0D0);
        	cv.drawText( columns[2], Global.DPFromPixel(536), typo_y, p); 
//        	cv.drawText("*", 955, y, p); 
        	
        	}
        
        
        }
    	
 
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
    	
    //	lines = 20;
    	
    	Log.w(TAG,"onDraw("+canvas+")" + (++redraw_count) );
    	
    
    	
    	onDrawList(canvas );

 //      if ( bmTop != null )
//        	canvas.drawBitmap ( bmTop, 0, 0, null ); 		// 버퍼에 그리기 
        
 //      if ( bmBottom != null )
//        	canvas.drawBitmap ( bmBottom, 0, getMeasuredHeight()-bmBottom.getHeight(), null ); 		// 버퍼에 그리기 
        
//        canvas.drawBitmap( bitmapList, 240, bmTop.getHeight(), null );
    	
 
/*
        final Paint p = new Paint();

       // p.setColor(backgroundColor);
       // canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(), p);
        
        p.setAntiAlias(true); // 테두리를 부드럽게한다
		p.setColor(Color.WHITE);
		p.setTextSize( 20 );
	
		
        canvas.drawText("["+redraw_count+"]", 10, 15, p ); // 왼쪽 아래를 0,0 으로 보고있음
*/        
        
        super.onDraw( canvas );
        
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
    	
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        switch ( keyCode )
        {
        case KMsg.MSG_KBD_UP:	
        			if ( curPos > 0 )
        				curPos--; 
        			break;
        			
        case KMsg.MSG_KBD_DOWN : 
        			if ( curPos < (lines-1))
        				curPos++;
        			break;
        			
        			
        case KMsg.MSG_KBD_LEFT:
        			cur_sno -= lines;
        			break;
        			
        case KMsg.MSG_KBD_RIGHT:
        			cur_sno += lines;
        			break;
        			
        			
      	case KMsg.MSG_KBD_STOP: 
//        		Global.Inst().app.doMenu( 99 );
      			return super.onKeyDown( KeyEvent.KEYCODE_BACK, event); 
        			
        case KeyEvent.KEYCODE_ENTER : 
    		//	fireCommand( event );
    			return super.onKeyDown( KeyEvent.KEYCODE_BACK, event); 
       
        	
        // parent가 처리하도록 한다. 
        default:
        	return super.onKeyDown(keyCode, event); 
 
        }
        invalidate();
        return true;
    }
    private boolean fireCommand(KeyEvent event)
    {
    	
//    	if ( mode == _NOTTHING )
    	return true;
    }
    
   

    /**
     * 
     * @return
     */
    public Dialog reservedDialog()
    {
    	Dialog dialog = null;
    	
    	CustomDialog.Builder customBuilder = new CustomDialog.Builder(contax);
	   		customBuilder.setTitle("Custom title")
					.setMessage("Custom body")
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) 
						{
					//		KYStbKaraokeActivity.this.dismissDialog(CUSTOM_DIALOG);
						}
					})
					.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
				
	    dialog = customBuilder.create();
	    return dialog;
    }
    
    /**
     * 이 view 에 touch 가 일어날때 실행됨.
     * 
     * 기본적으로 touch up 이벤트가 일어날때만 잡아내며 
     * setClickable(true) 로 셋팅하면 up,move,down 모두 잡아냄
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w(TAG,"onTouchEvent("+event+")");
        switch(event.getAction()) {
        case MotionEvent.ACTION_UP:
//            backgroundColor = Color.RED;
//            text = tempText;
        	//setFocusable( true );
            break;
        case MotionEvent.ACTION_DOWN:
//            backgroundColor = Color.YELLOW;
//            tempText = text;
//            text = "Clicked!";
            break;
        case MotionEvent.ACTION_MOVE:
//            backgroundColor = Color.TRANSPARENT;
//            text = "Moved!";
//        	setVisibility(View.INVISIBLE);
            break;
        }
//        invalidate();
        return super.onTouchEvent(event);
    }
  
   
   
/* 
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
 //       invalidate();
        this.postInvalidate();
    }
*/
    
    private void drawSongNumber( Canvas c, int xx, int yy, int sno )
    {
		//canvas.drawText( strSNO, Global.Inst().DPFromPixel(247), Global.Inst().DPFromPixel(65), p );
    	final int width  = bmNumber.getWidth() / 10;
    	final int height = bmNumber.getHeight();
    	
    	int x = 0;
    	int y = 0;
    	
    	String strSNO = String.format("%05d", sno) ;
    	
        Rect src = new Rect(0, 0, width, height);
        Rect dst = new Rect(0, 0, width, height);

		c.save(); // 현재 변환식을 저장
		c.translate(  Global.DPFromPixel(xx), Global.DPFromPixel(yy) );
		
		for ( int i = 0; i < 5; i++)
		{
			int n = strSNO.charAt(i);
			src.left = width*( n-48) ;
			src.right = (width* (n-48)) + width;

			c.drawBitmap(bmNumber,	src,
									dst, null );
			c.translate(  Global.DPFromPixel(width), Global.DPFromPixel(0) );
		}
	   	c.restore();
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
}
