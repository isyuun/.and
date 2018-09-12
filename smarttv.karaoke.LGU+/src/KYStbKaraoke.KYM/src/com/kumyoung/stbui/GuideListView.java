package com.kumyoung.stbui;


//import java.util.ArrayList;
import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

import java.util.ArrayList;

import com.kumyoung.common.Constants;
import com.kumyoung.gtvkaraoke.CustomDialog;
import com.kumyoung.gtvkaraoke.Database;
import kr.kumyoung.gtvkaraoke.R;
import com.kumyoung.gtvkaraoke.XmlParser;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import android.widget.PopupWindow;

//import android.util.TypedValue;
///import android.widget.ArrayAdapter;
//import android.widget.ListView;

public class GuideListView extends AbstractLV {
    
	private static final String TAG = GuideListView.class.getSimpleName();
	
    private int 		backgroundColor = 0x7f303030; //Color.RED;
    private Bitmap 		bmBG;
    private Bitmap 		bmBGline;
    
    private Bitmap 		bmFocus;
    private Bitmap 		bmFocusDim;
    
    private Bitmap 		bmThum;
    private Bitmap 		bmTrack;
    

    private int			scroll_line = 0;
    
    private	int			curPos = 0;
    private int 		lines = 8;
    private int 		cur_page = 0;
    private int 		max_page = 1;
    private int 		max_items = 5;
    
    private int			redraw_count = 0;
    private Bitmap 		bmNumber = null;
   
    
    
    
    final private int  		ROW_HEIGHT = (33 + 2)*2;
    private int 		FONT_CONTENTS_H		= 36;
    private int			FONT_H		= 32;
    
    String []contents;
    
    ArrayList<Integer> 	contentList;
    ArrayList<String> 	contentTitleList;
    ArrayList<String> 	contentSingerList;

    ArrayList<Integer> 	mainList;
    ArrayList<String> 	mainTitleList;
    ArrayList<String> 	mainSingerList;
 
/* 
    
    private String 		title[] = { 
			"노래방서비스 유료 서비스 이용안내",
    		"반주소리가 안들려요.",
    		"고객센터 연락처를 알려주세요.",
    		"애창곡은 무엇입니까?",
    		"노래시작은 어떻게 하나요?",
    		"",
    		"",
    		"",
    		"",
    		"",
    		"",
    		"",
    		
    };
   
    private int	text_count[] = { 12+1, 4, 5, 6, 6, 10, 10} ;
    
    private String		
    	description[][] = {
				{	
					//  1
					"금영 TV노래방서비스를 애용해 주시는 씨앤앰 시청자 여러분께 감사의 말씀드립니다.",
					"본 서비스는 유료 서비스로 월정액, 1일 이용권을 구매하시면 기간내 무제한 ", 
    				"이용이 가능합니다.",
    				"ㅇ 유료 과금 정책",
    				"  - 1일 3곡 무료 (곡 제한없음)",
    				"  - 월정액(월 무제한) : 3,000원 (가입형 상품, 매월 자동 연장)",
    				"    1일권 (24시간 무제한) : 1,000원 (종량형 상품, 연장되지 않음)",
    				" ",
    				" ",
    				" ",
    				" ",
    				" ",
    				null
    				
    	},
    	
    	{			//3
    				"셋탑박스와 TV의 연결 상태를 확인하여 주세요. ",
    				"문제가 없을시 고객센터로  연락주세요.",							
    				"",
    				null
    	},
    		
 		{		//5
    									"아래 번호로 전화하시면, 전문 상담원이 친절히 안내해 드립니다.",
    									"서울 : 1644-1100, 강남구 : 2056-7777,",
    									"경기 : 1644-2100, 울산 : 979-0100",
    									
    									"",
    									null,
 		},
    							
    	{		//8
    									"애창곡은 TV노래방을 보다 편리하게 이용하시도록 ",
    									"선호하는 노래를 등록하여 언제든지 빠르게 ",
    									"선곡할 수 있는 기능입니다. ",
    									"최대 20곡을 가족 애창곡으로 등록하실 수 있으며 ",
    									"노래 예약 시 사용하시면 됩니다.",
    									null,
    	},
    								
 		{		//13
    									"노래방 화면에서 예약 후 적색버튼을 누르시면 ",
    									"바로 노래를 부를 수 있는 화면으로 이동합니다.", 
    									"노래를 부를 수 있는 화면에서도 노래를 추가 하실 수 있으며 ",
    									"시작버튼(적색)을 선택하시면 다음 노래를 시작하실 수 있습니다.",
    								"",
    								null
 		},
 
    };
    										
*/    
   
    private Bitmap		bmGuideTitle = null;

    //private	Bitmap		bm
   
    int step  = 0;

   
    int mouseX = 0;
    int mouseY = 0;
    
    Rect		[]rcRect = null; 
    void req ()
    {
   		contentList 		= new ArrayList<Integer> ();
   		contentTitleList	= new ArrayList<String>  ();
   		contentSingerList	= new ArrayList<String>  ();
   		
   		for ( int i = (cur_page*lines); i < Math.min(max_items, (cur_page*lines+lines));  i++)
   		{
   			contentList.add(   mainList.get(i));
   			contentTitleList.add(   mainTitleList.get(i));
   			contentSingerList.add(   mainSingerList.get(i));
   			
   		}
   		
 
    }
    
    
    void requestMainData( )
    {
   
//    	min = ( page *query_max );
    	
   		XmlParser p = new XmlParser();
   		
   		mainList 		= new ArrayList<Integer> ();
   		mainTitleList	= new ArrayList<String>  ();
   		mainSingerList	= new ArrayList<String>  ();
   
   		
   		String sASP 	= String.format( "useinfo_lg_gtv.list");
//   		String sUser 	= String.format( "USER_ID=%s&SUBSCRIBER_ID=%s&DMC_ID=%d&SO_ID=%s&SESSION_ID=%s", 
//  										 "1347299446", "PID2843110", 
//   										 7, "BSI", 
//   										 "20111123003205943" );
//   		String sOption 	= String.format( "&TYPE=%d&page=%d&max=%d&gb=%d", 1, (page+1), query_max, mode);
   		if ( p.Parser ( "http://211.236.190.103:8080/common2/" + sASP, mainList, mainTitleList, mainSingerList ) )
   		{
   			mainList.get(0);
   		}
   		
   		Log.d("request", "data size " + mainList.size() );
   
   		
   		max_page = ( p.nMaxItems /lines) + ( (p.nMaxItems %lines) != 0 ? 1 :0 );
   		max_items = p.nMaxItems;
    }
   

    								
    
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public GuideListView(Context context) 
    {
        super(context);
        Log.w(TAG,"CustomView("+context+")");

        contax = context;
    }
    
    /**
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public GuideListView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        Log.w(TAG,"CustomView("+context+","+attrs+")");
        
      	bmBG 		= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_bg );
      	bmBGline 	= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_line );
      	
       	bmFocus		= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_f );
       	bmFocusDim 	= BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_f_dim );
      	
        bmNumber 	= BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_num_s);        
		bmGuideTitle= BitmapFactory.decodeResource( contax.getResources(), R.drawable.guide_title_bg ); 
		
		
        bmThum = BitmapFactory.decodeResource( contax.getResources(), R.drawable.scroll_thum);
        bmTrack= BitmapFactory.decodeResource( contax.getResources(), R.drawable.scroll_track);
        
        rcRect = new Rect[8];
		
		int dpRow = Global.DPFromPixel( ROW_HEIGHT);
        for ( int i = 0; i < lines; i++)
        {
       	  	int dpY = ( dpRow * i);
        	rcRect[i] = new Rect( 0, dpY, Global.DPFromPixel(Constants.width), dpY+ dpRow );
        }
       
        if ( ViewManager.Inst().lpBottomMenu != null)
        {
        	ViewManager.Inst().lpBottomMenu.isShowBack = true;
        	ViewManager.Inst().lpBottomMenu.UpdateBottomStatus();
        }	
        
        requestMainData();
        
        req();
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public GuideListView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
       
        this.text = attrs.getAttributeValue(null,"text");
        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;
   }
    

    /**
     * 
     * @param cv
     */
    void drawContents( Canvas cv )
    {
    	final Paint p = new Paint();
    	
        p.setColor(backgroundColor);
//        cv.drawRect(0,0,Constants.width,480, p);
        
        p.setAntiAlias(true); // 테두리를 부드럽게한다
        p.setColor(Color.WHITE);
		p.setTextSize( Global.DPFromPixel(FONT_CONTENTS_H) );
		
        if (text != null) {
//			canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
//			FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
        }

        if ( hasFocus() )
        	p.setColor(Color.WHITE);
        else
        	p.setColor(Color.RED);
       
        
        if ( !KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) && hasFocus() )
        {
       		//draw focus
//      		cv.drawBitmap ( bmFocus, 0, 8 + curPos*row -4, null ); 		// 버퍼에 그리기 
        }
       
        cv.drawBitmap( bmGuideTitle, 0, 0, null );
       	drawSongNumber( cv, Global.DPFromPixel(36), Global.DPFromPixel(24),  (curPos+1) );
       // title 
       	String contentsTitle = contentTitleList.get(curPos);
       	cv.drawText( contentsTitle, Global.DPFromPixel(75*2), Global.DPFromPixel(28*2), p );
	
      // 	contentSingerList       	
       //	int max_lines = 
//            	case 1:		resultMessage 	= array[i].split("\n"); break; //array[i].split("\\"); break;
       	
       	
       	
        int total = contents.length;
        
       	p.setTextSize( Global.DPFromPixel(FONT_H) );
       	int top_indent = Global.DPFromPixel(72*2);
        for ( int i = scroll_line; i < Math.min( scroll_line+12, total ); i++ )
        {
//        	if ( description[curPos][scroll_line + i] == null )
 //       		break;
        	
        	int row = Global.DPFromPixel ((24 + 2)*2);
        	int dpY = ( Global.DPFromPixel(5*2) + ((row * (i-scroll_line))*2) );
        	int typo_y = top_indent + dpY;//font ;
        	p.setColor( 0xFFb0b0b0);
//        	cv.drawText( description[curPos][scroll_line+i], Global.DPFromPixel(75), typo_y, p );
        	
        	if ( i < total )
        	{
        		cv.drawText( contents[i], Global.DPFromPixel(75*2), typo_y, p );
        	}
        }	
     
        //-----------------------------------
        // position 
        int dpHeight = bmTrack.getHeight() ;
        // indicate
        int dpPosition = (int) ((dpHeight- Global.DPFromPixel(15)) * ( (float)scroll_line / (float) (total+8) )) ;

        cv.drawBitmap ( bmTrack, bmBG.getWidth()-Global.DPFromPixel(80), top_indent , null ); 		// 버퍼에 그리기 
       
        
        int dpCenter = bmTrack.getWidth() /2 - bmThum.getWidth()/2;
      	cv.drawBitmap ( bmThum,  bmBG.getWidth()-Global.DPFromPixel(80)  + dpCenter, top_indent + (dpPosition), null ); 		// 버퍼에 그리기 
        
        
    }
    
    /**
     * 
     * @param 
     */
    void drawList( Canvas cv)
    {
    	
    	final Paint p = new Paint();
    	
        p.setColor(backgroundColor);
//        cv.drawRect(0,0,Constants.width,480, p);
        p.setAntiAlias(true); // 테두리를 부드럽게한다
        p.setColor(Color.WHITE);
		p.setTextSize( Global.DPFromPixel( FONT_H ) );
		int dpRow = Global.DPFromPixel( ROW_HEIGHT);
        if (text != null) {
//			canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
//			FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
        }

        
        if ( hasFocus() )
        	p.setColor(Color.WHITE);
        else
        	p.setColor(Color.RED);
       
/*        
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        
        int lineperheight = row;
 */      
//        if ( bmSelected != null )
//        	cv.drawBitmap ( bmSelected, 0, curPos*row, null ); 		// 버퍼에 그리기 
        
               
        
       	cv.drawBitmap ( bmFocusDim, 4, 4 + curPos*dpRow , null ); 		// 버퍼에 그리기 
        if (/* !KOSD.Inst().is( KOSD.eOSD_BOOK_EDIT ) && */hasFocus() )
        {
       		//draw focus
      		cv.drawBitmap ( bmFocus, 4, 4 + curPos*dpRow , null ); 		// 버퍼에 그리기 
        }
 
        	
        //cv.drawRect( 0, curPos*row, width, curPos*row+row, p);
 
        for ( int i = 0; i <  contentList.size(); i++ )
        {
   			int dpY = ( dpRow * i);
   			int dpTypoY = dpY +  Global.DPFromPixel(64);//font ;
        	
       		{
       			int seq = contentList.get( i );        	
       			drawSongNumber( cv, Global.DPFromPixel(28), Global.DPFromPixel(32)+dpY,  (seq) );
       			String title = contentTitleList.get( i );
       			p.setColor( 0xFFD0D0D0);
       			cv.drawText( title, Global.DPFromPixel(105*2), dpTypoY, p );
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
    	
    	Log.w(TAG,"onDraw("+canvas+")" + (++redraw_count) );
    	

    	if ( bmBG != null )
        	canvas.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
    	
    	if ( step == 0 )
    	{
	    	if ( bmBGline != null)
	    		canvas.drawBitmap ( bmBGline, 0, 0, null ); 		// 버퍼에 그리기 
    		drawList(canvas );
    	}
    	else
    	{
    		drawContents(canvas );
    	}

    	super.onDraw(canvas);
        
    }


    /**
     * 
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    
        
        switch (keyCode)
        {
        	case KMsg.MSG_KBD_UP:	
        	case KMsg.MSG_KBD_DOWN : 
        	case KMsg.MSG_KBD_LEFT:
        	case KMsg.MSG_KBD_RIGHT:	SoundManager.getInstance().playSound(1);	break;
        	case KeyEvent.KEYCODE_ENTER:SoundManager.getInstance().playSound(2);	break;
        }
               
               
    	if ( step > 0 )
    	{
    	
    				
    		switch ( keyCode )
    		{
        	case KMsg.MSG_KBD_UP:	
        		if ( scroll_line > 0 )
        			scroll_line--;
        		break;
        	
        	case KMsg.MSG_KBD_DOWN:
        	
        		if ( scroll_line < (contents.length+8))
        			scroll_line++;
        		break;
        	
        	case KeyEvent.KEYCODE_BACK:
        		if ( step > 0 )
        			step --;
        		
        		
        		scroll_line = 0;
        		break;
        		
        	    // parent가 처리하도록 한다. 
            default:
                return super.onKeyDown(keyCode, event); 
        	}
        
    		invalidate();
        	return true;
    	}
    	
    	
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        switch ( keyCode )
        {
        case KMsg.MSG_KBD_UP:	
        						if (curPos > 0)	
        						{
        							curPos--; 
        							break;
        						}
        						else 
        						{
        							curPos = (contentList.size()-1);		// last pos
        							if ( curPos < 0 )
        							{
        								curPos = 0;
        								break;
        							}
        						}
        						 
        case KMsg.MSG_KBD_LEFT:	if ( cur_page > 0)
        							cur_page--;		//	cur_page-= lines;
        						else
        						{
        							if (max_page != 0)
        								cur_page = (max_page-1);		//	cur_page-= lines;
        							else
        								max_page = 0;
        						}
        
        						req( );
        						
        						if ( cur_page == (max_page-1))
        							curPos = (contentList.size()-1);		// last pos
        							
        						
        						break;
        						
        						
        case KMsg.MSG_KBD_DOWN: if (curPos < (contentList.size()-1))
        						{
        							curPos++;
        							break;
        						}
        						else
        							curPos = 0;
        
       case KMsg.MSG_KBD_RIGHT: if ( (cur_page+1)  < max_page )
        							cur_page++;//	cur_page += lines;
       							else
       								cur_page = 0;
       
        						if ( (((cur_page)*lines)+curPos) >= (max_items) )
        						{
        							curPos = ((max_items%lines)-1)/*index가 0부터*/;
        							if ( curPos < 0)
        							{
        								curPos = 0;
        							}
        						}
        						req(  );
        						break;

        

        // 종료 
      	case KMsg.MSG_KBD_STOP: 
        		//Global.Inst().app.doMenu( 99 );
        		//return false;
        		return super.onKeyDown( KeyEvent.KEYCODE_BACK, event); 
        			
        		
        case KeyEvent.KEYCODE_DPAD_CENTER:
        case KeyEvent.KEYCODE_ENTER : 
        		scroll_line = 0;
        		fireCommand();
        		break;
        	
        case KeyEvent.KEYCODE_BACK:
        
        		return super.onKeyDown(keyCode, event); 
       
        	
        // parent가 처리하도록 한다. 
        default:
        	return super.onKeyDown(keyCode, event); 
        }
        
        invalidate();
        return true;
    }
    
    
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
     */
   @Override
      public boolean onTouchEvent(MotionEvent event) {

	   Log.w(TAG,"onTouchEvent("+event+")");
       mouseX =(int) event.getX();
       mouseY =(int) event.getY();

       
       if ( step != 0 )
    	   return true;
       
       
       int cur = -1;
       for ( int i = 0; i < lines; i++)
       {
    	   if ( rcRect[i].contains(mouseX, mouseY ) )
    		   cur = i;
       }
    	   
   	
       switch(event.getAction()) {
       case MotionEvent.ACTION_UP:
           backgroundColor = Color.RED;
           if ( cur >= 0 )
           {
        	   curPos = cur;
           		fireCommand();
           }
           invalidate();
           break;
       case MotionEvent.ACTION_DOWN:
           backgroundColor = Color.YELLOW;
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
  
    
   	private void drawSongNumber( Canvas c, int dpX, int dpY, int sno )
    {
		//canvas.drawText( strSNO, Global.Inst().DPFromPixel(247), Global.Inst().DPFromPixel(65), p );
    	final int dpWidth  = bmNumber.getWidth() / 10;
    	final int dpHeight = bmNumber.getHeight();
    	
    	int x = 0;
    	int y = 0;
    	
    	String strSNO = String.format("%02d", sno) ;
    	
        Rect src = new Rect(0, 0, dpWidth, dpHeight);
        Rect dst = new Rect(0, 0, dpWidth, dpHeight);

		c.save(); // 현재 변환식을 저장
		c.translate(  dpX, dpY );
		for ( int i = 0; i < 2; i++)
		{
			int n = strSNO.charAt(i);
			src.left = dpWidth*( n-48) ;
			src.right = dpWidth*(n-48) + dpWidth;

			c.drawBitmap(bmNumber,	src,
									dst, null );
			c.translate(  dpWidth,0 );
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
    
    
    public void setNoticeItem( int i )
    {
        curPos = i;
        
        step++;
    	String str = contentSingerList.get(curPos);
    	contents = str.split(",");
 
    }
        
 
    /**/
    private void fireCommand()
    {
    	
    	if ( step > 0)
    		step--;
    	else
    	{
    		step++;
    		String str = contentSingerList.get(curPos);
//    		contents = str.split(".");
    		Log.d("ke", str );
    		contents = str.split(",");
    	}
    }
   /* 
    private void req()
    {

    	contentList 		= new ArrayList<Integer> ();
    //    	contentTitleList	= new ArrayList<String>  ();
     //   	contentSingerList	= new ArrayList<String>  ();
	  // 		MakeList( Database.Inst().snoLately, contentList, contentTitleList, contentSingerList, cur_page);
    	
    	switch (cur_page)
    	{
    	case 0:
    		for ( int i = 0; i < 5; i++)
    			contentList.add( i );
    		break;
    		
  //  	case 1:
 //   		for ( int i = 0; i < 5; i++)
//    			contentList.add( 8+i );
 //   		break;
    	}
 
    }
   */ 
 
}