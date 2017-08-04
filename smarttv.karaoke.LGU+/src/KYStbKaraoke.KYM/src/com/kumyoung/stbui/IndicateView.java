package com.kumyoung.stbui;

import isyoon.com.devscott.karaengine.Global;

import kr.kumyoung.gtvkaraoke.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

public class IndicateView extends AbstractLV {
    
	private static final String TAG = IndicateView.class.getSimpleName();
    private Bitmap 		bmBG;
    private	int			curPos = 0;
    private int 		maxPage = 8;
    private Bitmap		bmIndicateIcon = null;
    private Bitmap		bmIndicateActiveIcon = null;
    
    public IndicateView(Context context) 
    {
        super(context);
//        Log.w(TAG,"CustomView("+context+")");
        contax = context;
    }
    public IndicateView(Context context,AttributeSet attrs) {
        this(context,attrs,0);
//        Log.w(TAG,"CustomView("+context+","+attrs+")");
      	bmBG = BitmapFactory.decodeResource( contax.getResources(), R.drawable.list_bg_b );
      	bmIndicateIcon = BitmapFactory.decodeResource( contax.getResources(), R.drawable.page_position_dim );
      	bmIndicateActiveIcon = BitmapFactory.decodeResource( contax.getResources(), R.drawable.page_position_f);
    }
    public IndicateView(Context context,AttributeSet attrs,int defStyle) {
        super(context,attrs,defStyle);
        this.text = attrs.getAttributeValue(null,"text");
//        Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);
        contax = context;
   }
    

    /**
     * 
     * @param cv
     */
    void drawContents( Canvas cv )
    {
/*	
    	final Paint p = new Paint();
        p.setColor(backgroundColor);
        p.setAntiAlias(true); // 테두리를 부드럽게한다
        p.setColor(Color.WHITE);
        
		p.setTextSize( 18 );
        if (text != null) {
//			canvas.drawText(text, 10, 15, p); // 왼쪽 아래를 0,0 으로 보고있음
//			FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );
        }

        
        if ( hasFocus() )
        	p.setColor(Color.WHITE);
        else
        	p.setColor(Color.RED);
*/        
    /*
        
		int row = Global.DPFromPixel(2);
		int center = (bmIndicateIcon.getWidth() * maxPage)/2;
		int left = (bmBG.getWidth()/2) - center;
		
		for ( int i = 0; i < maxPage; i++)
		{
			int x = left + Global.DPFromPixel(10) * i;
			
			if ( curPos == i )
				cv.drawBitmap( bmIndicateActiveIcon, x, row, null);
			else
				cv.drawBitmap( bmIndicateIcon, x, row, null);
		}
//        cv.drawText("["+redraw_count+"]", 10, 10, p ); // 왼쪽 아래를 0,0 으로 보고있음
	*/	
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	if ( bmBG != null )
        	canvas.drawBitmap ( bmBG, 0, 0, null ); 		// 버퍼에 그리기 
    	
    	drawContents(canvas);
    	super.onDraw(canvas);
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

//        Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
        setMeasuredDimension(widthSize, heightSize);
    }
}