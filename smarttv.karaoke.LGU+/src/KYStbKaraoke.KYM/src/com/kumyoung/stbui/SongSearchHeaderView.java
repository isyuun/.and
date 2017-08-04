package com.kumyoung.stbui;


import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.KMsg;
import isyoon.com.devscott.karaengine.KOSD;

import com.kumyoung.gtvkaraoke.ATVKaraokeActivity;
import com.kumyoung.gtvkaraoke.CustomDialog;
import com.kumyoung.gtvkaraoke.Database;
import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.app.Dialog;
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

/*
class ModalWindow extends PopupWindow
{
	public ModalWindow (View v, int x, int y, boolean b)
	{
        super(v, x, y, b);
	}
	public String strText = "";
}
*/

public class SongSearchHeaderView  extends AbstractLV {
   
	private static final String TAG = SongSearchHeaderView.class.getSimpleName();
	//private ArrayList<String> 		list;
	Boolean show_key = false;
	
//    private String 		text = null;
 //   private int 		backgroundColor = 0x7f303030; //Color.RED;
	PopupWindow popup = null;
    private Bitmap 			bmFocus;
    private int 			lines = 2;
 //   private int			redraw_count = 0;
    
    
    private final int		FONT_SIZE = 36;
    private Bitmap 			bmNumber = null;
    private Bitmap			bmInputFocus = null;
    
    private Bitmap			bmItem1 	= null;
    private Bitmap			bmItem2 	= null;
    private Bitmap			bmBG = null;
    
    
    // 한글입력
//    private EditableInputConnection mEii;
//	AutomataKr	automata = null;
	
    
    private Rect		[]rcControl = null;
    
    
    public String strText = "";
    
    
    // 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다. 
    public SongSearchHeaderView(Context context) 
    {
        super(context);
        Log.w(TAG,"CustomView("+context+")");

        contax = context;
        
//        bmTop = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_title);
//        bmBottom = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_bottom);
//        bitmapList = Bitmap.createBitmap(480,180, Config.ARGB_8888);
//        canvasList = new Canvas(bitmapList);
    }
    /*
     *  리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
     *  대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
     */
    public SongSearchHeaderView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
        Log.w(TAG,"CustomView("+context+","+attrs+")");

        
        bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_title_bg );
       	bmFocus= BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_input_title_f);
        bmNumber = BitmapFactory.decodeResource( contax.getResources(), R.drawable.main_num_s);
        
        bmInputFocus = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_input_f );
        
        bmItem1 = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_input_title1 );
        bmItem2 = BitmapFactory.decodeResource( contax.getResources(), R.drawable.search_input_title2 );
        
        //setFocusable(true);
        rcControl = new Rect[2];
        rcControl[0] = new Rect( 125*2, 15*2, 248*2, 50*2);
        rcControl[1] = new Rect( 248*2, 15*2, 500*2, 50*2);
    }
    
    /*
     * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
     */
    public SongSearchHeaderView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
       
        this.text = attrs.getAttributeValue(null,"text");
        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;
        
//        setFocusableInTouchMode(true);
//        setFocusable(true);
   }
    
    ///
  /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
        }
        return true;
    }
 */ 
    /**
     * 
     * @param cv
     */
    void drawControl( Canvas cv)
    {
    	
/*
		int row = Global.DPFromPixel(33 + 2);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        
        int lineperheight = row;
 */      
        
       
//       if ( bmBG != null )
//        	cv.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
        
//      if ( bmSelected != null )
//        	cv.drawBitmap ( bmSelected, 0, curPos*row, null ); 		// 버퍼에 그리기 
        
    	
    	int dpX = Global.DPFromPixel( 250 );
    	int dpY = Global.DPFromPixel( 30 );
        if ( ViewManager.Inst().curItem == 0 )
        	cv.drawBitmap ( bmItem1, dpX, dpY, null ); 		// 버퍼에 그리기 
        else
        	cv.drawBitmap ( bmItem2, dpX, dpY, null ); 		// 버퍼에 그리기 
       
        

        //draw focus
        if ( hasFocus())
        {
//        	if ( curPos == 0)
        		cv.drawBitmap ( bmFocus, dpX, dpY/* + curPos*row -4*/, null ); 		// 버퍼에 그리기 
        
//        if ( curPos == 1)
 //       	cv.drawBitmap( bmInputFocus, 248,15, null);
        }
 
        	
        //cv.drawRect( 0, curPos*row, width, curPos*row+row, p);
 
 /*       
        for ( int i = 0; i < lines; i++ )
        {
        	int y = ( row * i);
        	int typo_y = y +  Global.DPFromPixel(32); // font h;
        	
       		String[] columns = {"sno","title", "artist"};
       		Database.Inst().query_song_info( cur_sno +  i, columns);	
        	
        	drawSongNumber( cv, 14, 16+y,  (cur_sno+i) );
        	
        	p.setColor( 0xFFD09090);
        	cv.drawText( columns[1], Global.DPFromPixel(105), typo_y, p );
        	p.setColor( 0xFFD0D0D0);
        	cv.drawText( columns[2], Global.DPFromPixel(536), typo_y, p); 
//        	cv.drawText("*", 955, y, p); 
        	
        }
*/    	
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
    	
//    	Log.w(TAG,"onDraw("+canvas+")" + (++redraw_count) );
    	drawControl(canvas );

    	//canvas.drawBitmap( bitmapList, 240, bmTop.getHeight(), null );
    	
   		Paint paint = new Paint();
        paint.setColor(Color.argb(0x80, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL);
 
    	if ( strText != null )
    	{
    		paint.setTextSize(	 Global.DPFromPixel( FONT_SIZE) ); 
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			
			int dpX = Global.DPFromPixel(260*2);
			int dpY = Global.DPFromPixel(42*2);
    		canvas.drawText( strText, dpX, dpY, paint);
    	}

    	super.onDraw(canvas);
           
    }
    
/*   
    private void toggleFocus()
    {
       	if ( hasFocus() )
		{
        	ViewManager.Inst().lpSongSearchHeaderView.setFocusable(false);
        	ViewManager.Inst().lpSongSearchView.setFocusable(true);
        	ViewManager.Inst().lpSongSearchView.setSpot();
		}
    }
*/ 
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG,"onKeyDown("+keyCode+","+event+")");
        
//       if ( show_key == true )
//        	return super.onKeyDown(keyCode, event); 
      
        switch ( keyCode )
        {
        case KMsg.MSG_KBD_DOWN : 
//        			if ( curPos == 1)
//       			{
 //       				toggleFocus(); 
 //       				break;
  //      			}
        case KMsg.MSG_KBD_UP:			
//        			if ( curPos == 0 )
        				ViewManager.Inst().curItem = ((++ViewManager.Inst().curItem)%2);
        			break;
        			
        case KMsg.MSG_KBD_LEFT:
//        	if ( curPos > 0 )		//ppp
//				curPos--; 
//			else 
//				curPos = (lines-1);
        	break;
        			
        case KMsg.MSG_KBD_RIGHT:
  	//		if ( curPos < (lines-1) )
  	//		{
   	//			curPos++;
   //				fireCommand();
   				
   	//			curPos = 0;		//ppp
  	//		}
  //			else	
 // 				toggleFocus();
        	//curItem = (++curItem %2);
        	
    		ViewManager.Inst().lpSongSearchListView.doRequestData( ViewManager.Inst().curItem, strText);
   			invalidate();
   			
//			ATVKaraokeActivity.edit.setImeOptions(	EditorInfo.IME_ACTION_SEARCH |
//    												EditorInfo.IME_FLAG_NO_EXTRACT_UI
//    												);
   
			ViewManager.Inst().lpSongSearchListView.setFocusable(false); // 풀어준다.
    		ATVKaraokeActivity.edit.requestFocus();
    		
   			//ViewManager.Inst().imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    		break;
   			
      	case KMsg.MSG_KBD_STOP: 
//        	Global.Inst().app.doMenu( 99 );
//       	return false;
  			return super.onKeyDown( KeyEvent.KEYCODE_BACK, event); 
 
        case KeyEvent.KEYCODE_ENTER : 
       		fireCommand();
        	return true;
        	

       	default:
        // PARENT가 처리하게 내버려 둔다. 
        case KeyEvent.KEYCODE_BACK:
        
        	
        //	if ( curPos == 1 )
        	{
        //		ATVKaraokeActivity.edit.dispatchKeyEvent(event);		// back 키를 전달한다. 
        //		strText = ATVKaraokeActivity.edit.getText().toString();		//ppp	
        //		invalidate();
        	}
        
        	
        	return super.onKeyDown(keyCode, event); 
        	
        }
        
        
        invalidate();
//      return super.onKeyDown(keyCode, event); 
        return true;		
    }
    
    
   
    /**
     * 
     * 
     */
    private void fireCommand()
    {
  			/* focus */
   			//ViewManager.Inst().lpSongSearchView.setSpot();
   			/* */
   			ViewManager.Inst().curItem = (++ViewManager.Inst().curItem)%2;
    		ViewManager.Inst().lpSongSearchListView.doRequestData( ViewManager.Inst().curItem, strText);
   			invalidate();
   			
   			/*
   			//save

   	/*		
   			ATVKaraokeActivity.edit.setOnEditorActionListener(new TextView.OnEditorActionListener() 
   			{
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					if ( actionId == EditorInfo.IME_ACTION_SEARCH)
					{
   						strText = ATVKaraokeActivity.edit.getText().toString();
   						ViewManager.Inst().lpSongSearchView.doRequestData( curItem+2, strText);
   						ViewManager.Inst().lpSongSearchView.updateList();
   						
   						imm.hideSoftInputFromWindow( v.getWindowToken(), 0 );
   						
   						
   						ViewManager.Inst().lpSongSearchHeaderView.setFocusable(false);
   						ViewManager.Inst().lpSongSearchView.setFocusable(true);
   						ViewManager.Inst().lpSongSearchView.setSpot();
   						return true;
					}
					
					return false;
				}
			});
 
*/
	
 /*  	
   			if ( imm != null  )
   			{
   				imm.hideSoftInputFromWindow( ATVKaraokeActivity.edit.getWindowToken(), 0 );
   				imm = null;
 //  				ATVKaraokeActivity.edit.setEnabled(false);
//   				ATVKaraokeActivity.edit.setVisibility(INVISIBLE);		
   				return;
   			}
*/   			
   			
   	
   		/*
   			
			ATVKaraokeActivity.edit.setImeOptions(	EditorInfo.IME_ACTION_SEARCH |
    												EditorInfo.IME_FLAG_NO_EXTRACT_UI
    												);
   
    		ATVKaraokeActivity.edit.requestFocus();
    		
    		// show imm
    //		ATVKaraokeActivity.edit.setImeOptions(EditorInfo.IME_FLAG_NO_ACCESSORY_ACTION| EditorInfo.IME_ACTION_DONE); 
    		
   			ViewManager.Inst().imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
///cc   			ViewManager.Inst().imm.showSoftInput( ATVKaraokeActivity.edit, InputMethodManager.SHOW_FORCED);
   			
   			
   		}
   */ 
     }
    
  /*  
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
   */
    
   
   /**
    * 
   private void drawSongNumber( Canvas c, int xx, int yy, int sno )	{
		//canvas.drawText( strSNO, Global.DPFromPixel(247), Global.DPFromPixel(65), p );
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

			c.drawBitmap(bmNumber,	src, dst, null );
			c.translate(  Global.DPFromPixel(width), Global.DPFromPixel(0) );
		}
	   	c.restore();
    }
    */

    
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        // 진짜 크기 구하기
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = 0;
        switch(heightMode) {
        case MeasureSpec.UNSPECIFIED: heightSize = heightMeasureSpec;	break;	// mode 가 셋팅 안된 크기 
        case MeasureSpec.AT_MOST:    heightSize = Global.DPFromPixel(140);break;// 140
        							// bmBG.getHeight();	break;	
        case MeasureSpec.EXACTLY:     heightSize = MeasureSpec.getSize(heightMeasureSpec);	 break;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = 0;
        switch(widthMode) {
        case MeasureSpec.UNSPECIFIED:    widthSize = widthMeasureSpec; 	break; // mode 가 셋팅되지 않은 크기가 넘어올때
        case MeasureSpec.AT_MOST:     widthSize = Global.DPFromPixel(550); break; /*540*/
        								//bmBG.getWidth();		break;
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
        	if ( rcControl[0].contains( (int)event.getX(), (int)event.getY() ) )
        	{
        		/** 떠 있을지도 모르는 키보드를 가린다. */
        		if ( ViewManager.Inst().imm != null )
        		{
        			ViewManager.Inst().imm.hideSoftInputFromWindow( ATVKaraokeActivity.edit.getWindowToken(), 0 );
        		}
        		fireCommand();
        	}
            break;
        case MotionEvent.ACTION_MOVE:
            break;
        }
        return super.onTouchEvent(event);
    }
    
   
    
    
    
    
   /* 
     public void onSubDraw( Canvas canvas )
     {
 			Paint paint = new Paint();
           paint.setTextSize( 24 );
          
//   			paint.setStyle(Paint.Style.FILL);
   	//		canvasMemory.drawRect(new Rect( 0,  0, Constants.width, Constants.height ), paint );
           
           	int x = 10;
   			int mw = (int) Math.ceil( paint.measureText(strText)) ;
           
   			paint.setColor(Color.argb(0x80, 0xff, 0xff,0x0 ));
   			if ( automata.ingWord != '\0')
   			{
   				Character cr = new Character( automata.ingWord );             // char을 Object로 랩
   				String str = cr.toString();                            // Character를 String으로 형변환	
   				
   				int calc = (int) Math.ceil( paint.measureText( str )) ;
   				//조합중이라면 
   				canvas.drawRect( new Rect( x+ mw -calc, 10, x+mw +3, 30), paint);
   			}
   			else
   			{
   				canvas.drawRect( new Rect( x+ mw, 10, x+mw +3, 30), paint);
   			}
   			paint.setColor(Color.argb(0xFF, 0x0, 0x0,0x0 ));
            paint.setAntiAlias(true);
   			canvas.drawText( strText, x, 28, paint);
     }
     
     */
     
	
/*	

    @Override
    public boolean onCheckIsTextEditor() 
    {
        return true;
    }
    
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    	outAttrs.actionLabel = null;
        outAttrs.label = "Composer Test";
        outAttrs.inputType = 	//InputType.TYPE_TEXT_VARIATION_NORMAL;
        						InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
        
     //   windowSoftInputMode   			
        outAttrs.imeOptions = //EditorInfo.IME_ACTION_SEARCH;
        					  EditorInfo.IME_ACTION_DONE;
        if(mEii==null)
        {
        	mEii = new EditableInputConnection(this);
        }
        
        return (InputConnection) mEii;
    }
    
    public class EditableInputConnection extends BaseInputConnection 
    {
    	private final View 	mView;
    	private SpannableStringBuilder mEditable;
    	String mText = new String();
    	public EditableInputConnection(View textview) 
    	{
    		super(textview, true);
    		mView = textview;
    		mEditable = (SpannableStringBuilder) Editable.Factory.getInstance().newEditable("");
    	}
    	public Editable getEditable() 
    	{
    		return mEditable;
    	}
    	
    	public void Reset()
    	{
    		mEditable = (SpannableStringBuilder) Editable.Factory.getInstance().newEditable("");
    	}

    	//이곳을 통해 영문입력을 전달 받으세요. for English
    	@Override 
    	public boolean commitText(CharSequence text, int newCursorPosition) 
    	{
   
    		strText += text.toString();
    		invalidate();
    		
    		
    		ViewManager.Inst().lpSongSearchView.doRequestData( curItem+2, strText);
			Log.d(TAG, "CommitText eng nuwpos " + newCursorPosition );                   
    		ViewManager.Inst().lpSongSearchView.updateList();
			
			return super.commitText(text, newCursorPosition);
    	}

    	//이곳을 통해 한글 입력을 바로바로 전달 받으세요. for Multibyte code
    	@Override
    	public boolean setComposingText(CharSequence text, int newCursorPosition)
    	{
    		boolean ret = super.setComposingText(text, newCursorPosition);
    		
//			Log.d(TAG, text.toString()  + newCursorPosition + getEditable() + text.length()  );
//   		strText += (text.charAt(newCursorPosition));
    		
    		String str = getEditable().toString();
    		strText = str;
    		invalidate();
    	
    		
    	//	curItem    		
    		ViewManager.Inst().lpSongSearchView.doRequestData( curItem+2, strText);
    		//return super.setComposingText(text, newCursorPosition);
    		ViewManager.Inst().lpSongSearchView.updateList();
    		
    		return ret;
    	}
    }
   */ 
    
//    public void ppp(View v)
 //   {
 //		ViewManager.Inst().imm.hideSoftInputFromWindow( v.getWindowToken(), 0 );
  //  }

  
 
}
  /*		softkeyboard
   			ContextKeyboardView popupview = new ContextKeyboardView(contax);			
       		popup = new ModalWindow( popupview, 300, 200, true);
        				
       		popup.setOutsideTouchable(true);
       		popup.setBackgroundDrawable(new BitmapDrawable() );
        				
			popupview.setOnKeyListener( new OnKeyListener() 
			{
        				@Override
				        public boolean onKey(View v, int keyCode, KeyEvent event) 
        				{
        					if ( keyCode == 66)
        					{
        						strText = ((ContextKeyboardView)v).strText;
        						ViewManager.Inst().lpSongSearchView.doRequestData(strText);
        					}
        					
        					if ( keyCode ==KeyEvent.KEYCODE_BACK)
        					{
        						strText = ((ContextKeyboardView)v).strText;
        						return true; } return false; }
			       		});
			       		
			       		int x = Global.DPFromPixel(48);
			       		int y = Global.DPFromPixel(165);
			       		
        				popup.showAtLocation(this, Gravity.BOTTOM, x, y);
  		
        				popupview.setSpot();
        				popupview.setParent(popup);		// 자체 종료를 위해 부모를 등록해 준다.
  
      */ 