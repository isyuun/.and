/**


 * 
 */

package com.kumyoung.gtvkaraoke;

import isyoon.com.devscott.karaengine.Global;
import isyoon.com.devscott.karaengine.Output;

import com.kumyoung.common.Constants;
import kr.kumyoung.gtvkaraoke.R;
import kr.kumyoung.gtvkaraoke.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * The DrawingPanel holds the drawable surface.
 * 
 * @author devsjcott 
 */

public class DrawingPanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = DrawingPanel.class.getSimpleName();
	
	private Canvas 			canvas = null;		// the canvas to draw on
	private Resources 		mRes;		// resource  
	private Context			mContext;
	private SurfaceHolder 	mHolder;
	GameThread				mThread;
	
	boolean			bFlagTitle  = false;
	boolean			bFlagAuthor = false;
	boolean			bFlagLyric1 = false;
	boolean			bFlagLyric2 = false;

	String 			tmp_title;
	String 			tmp_description;
	String			tmp_author;
	String			tmp_writer;
	String			tmp_singer;
	
	String			tmp_lyric1;
	String			tmp_lyric2;
	
	
		
//	Bitmap			mOffscreenBitmap;
	
	public DrawingPanel(Context context) {
		super(context);
		
	}
	
	public DrawingPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		}
    /**
     * The constructor called from the main JetBoy activity
     * 
     * @param context 
     * @param attrs 
     */
	public DrawingPanel(Context context, AttributeSet attrs ) {
		super(context, attrs);

		// adding the panel to handle events
		mHolder = getHolder();
		mHolder.addCallback(this);		// SurfcaeView움직이는 것은 SurfaceHolder임.
	
		mContext = context;
		mRes = context.getResources();
		// initialise resources
		loadResources();
	
		// making the Panel focusable so it can handle events
		setFocusable(true);
		
		
//		mOffscreenBitmap = Bitmap.createBitmap( 1280, 720, Bitmap.Config.ARGB_4444 );
		
		mThread = new GameThread();	// gamethread 생성 	
	}


	/**
	 * Loads the images
	 */
	
 	private void loadResources() {
       
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// draw text at touch
		try {

			canvas = getHolder().lockCanvas();
			synchronized (getHolder()) {
		
				if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) 
				{
					// clear the screen
					canvas.drawColor(Color.BLACK);
				
					Paint Pnt = new Paint();
					Pnt.setColor(Color.BLUE);
					canvas.drawCircle( 100, 100,80, Pnt);
					
					
					// draw glyphs
					glyphs.drawString(canvas, "Position "
						+ (int) event.getX() + " " + (int) event.getY(),
							(int) event.getX(), (int) event.getY());
				}
			
				if (event.getAction() == MotionEvent.ACTION_UP) {
					canvas.drawColor(Color.BLACK);
					
					glyphs.drawString(canvas, "그리기 at "
						+ (int) event.getX() + " " + (int) event.getY(),
						(int) event.getX(), (int) event.getY());
				}
			}
		} finally {
			if (canvas != null) {
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
		// event was handled
		return true;
	}
*/
	
	public void 	labelTitle( String _tmp_title, String _tmp_description )
	{
		
		tmp_title = _tmp_title;
		tmp_description = _tmp_description;
		
		bFlagTitle = true;
		if ( bFlagTitle)
		{
							
//					Canvas c =new Canvas( mOffscreenBitmap );
						
							//glyphs.drawString(c, tmp_title, 400, 100 );
							//glyphs.drawString(c, "(sub title)"/*tmp_description*/, 100, 150 );
							
							bFlagTitle = false;
		}
	}
	
	public void 	label1Text( String _tmpLyric )
	{
		tmp_lyric1 = _tmpLyric;
		bFlagLyric1 = true;
		if ( bFlagLyric1 )
		{
//					Canvas c =new Canvas( mOffscreenBitmap );
							//glyphs.drawString(c, tmp_lyric1, 150, 350 );
							bFlagLyric1 = false;
		}
	}
	public void 	label2Text( String _tmpLyric )
	{
		tmp_lyric2 = _tmpLyric;
		bFlagLyric2 = true;
		if ( bFlagLyric2 )
		{
//					Canvas c =new Canvas( mOffscreenBitmap );
							//glyphs.drawString(c, tmp_lyric2, 4500, 450 );
							bFlagLyric2 = false;
		}
	}
	
	public void labelAuthor( String _author, String _write, String _singer)
	{
		tmp_author = _author;
		tmp_writer = _write;
		tmp_singer = _singer;
	
		bFlagAuthor = true;
		if ( bFlagAuthor )
		{
							
//						Canvas c =new Canvas( mOffscreenBitmap );
							//glyphs.drawString(c, tmp_author, 900, 400 );
							//glyphs.drawString(c, tmp_writer, 900, 470 );
							//glyphs.drawString(c, tmp_singer, 900, 540 );
							
							bFlagAuthor = false;
		}
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		Log.i(TAG, "call onDraw");
	}

	class GameThread extends Thread {
		
		Bitmap imgBack;
		
		/** The drawable to use as the far background of the animation canvas */
        private Bitmap mBackgroundImageFar;

        /** The drawable to use as the close background of the animation canvas */
        private Bitmap mBackgroundImageNear;
        
        private Bitmap mCustomBitmap;

               // right to left scroll tracker for near and far BG
        private int mBGFarMoveX = 0;
        private int mBGNearMoveX = 0;
        private int mTextMoveY = 0;

        // how far up (close to top) jet boy can fly
        private int mJetBoyYMin = 40;
        private int mJetBoyX = 0;
        private int mJetBoyY = 0;
        
        
		public GameThread() 
		{
			imgBack = BitmapFactory.decodeResource( mContext.getResources(), R.drawable.background_a );
		            
            mBackgroundImageFar = BitmapFactory.decodeResource(mRes, R.drawable.background_a);
        //    mLaserShot = BitmapFactory.decodeResource(mRes, R.drawable.laser);
            mBackgroundImageNear = BitmapFactory.decodeResource(mRes, R.drawable.background_b);
            mCustomBitmap = Bitmap.createBitmap( Constants.width, Constants.height, Bitmap.Config.ARGB_4444 );
			
		}
		
		
		private void doDrawDemo ( Canvas canvas )
		{
			
            // decrement the far background
            mBGFarMoveX = mBGFarMoveX - 1;
            // calculate the wrap factor for matching image draw
            int newFarX = mBackgroundImageFar.getWidth() - (-mBGFarMoveX);
            // if we have scrolled all the way, reset to start
            if (newFarX <= 0) {
                mBGFarMoveX = 0;
                // only need one draw
                canvas.drawBitmap(mBackgroundImageFar, mBGFarMoveX, 0, null);
            } else {
                // need to draw original and wrap
                canvas.drawBitmap(mBackgroundImageFar, mBGFarMoveX, 0, null);
                canvas.drawBitmap(mBackgroundImageFar, newFarX, 0, null);
            }
            

            // Near astor
            mBGNearMoveX = mBGNearMoveX - 2;
            int newNearX = mBackgroundImageNear.getWidth() - (-mBGNearMoveX);
            if (newNearX <= 0) {
                mBGNearMoveX = 0;
                canvas.drawBitmap(mBackgroundImageNear, mBGNearMoveX, 0, null);
            } else {
                canvas.drawBitmap(mBackgroundImageNear, mBGNearMoveX, 0, null);
                canvas.drawBitmap(mBackgroundImageNear, newNearX, 0, null);
            }

         /*
            // scroll text
            mTextMoveY -= 1;
            int newTextY =  mCustomBitmap.getHeight() - (-mTextMoveY);
            if ( (newTextY % 80) == 0 )
            {
     	         Canvas c = new Canvas( mCustomBitmap);
     	         glyphs.drawString(c, "가나다라마바ABCDEF03감수abcdef", 0, newTextY );
            } 
            if (newTextY <= 0) {
                mTextMoveY = 0;
                canvas.drawBitmap(mCustomBitmap, 0, mTextMoveY, null);
            } else {
                canvas.drawBitmap(mCustomBitmap, 0, mTextMoveY, null);
                canvas.drawBitmap(mCustomBitmap, 0, newTextY, null);
            }
          */  

			
		}
	
		private void doDraw ( Canvas canvas )
		{
			
			doDrawDemo ( canvas );
			
			
		}
		
	
		// GameLoop
		public void run ()
		{
			
//			long spand_time = 0;
			long sample_add = 1024;
			long current_sample_bak = 0 ;
			long sample = 0;
			
			boolean bFirst = true;
			
//			float lastTime = 0.0f;
//			float lastMod = 0.0f;
			
			Output.output_sample = 0 ;
			float midi_time_ratio =  1.0f;
			Global.Inst().std_tick= 50000;		// 120 BPM
			int i = 0;

			
			while( true )
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}        // 1초간 대기후
				
//				Log.d("ke", current_sample_bak +" " + Output.sample );
		    	if ( current_sample_bak != Output.output_sample || bFirst )
		        {
		    		bFirst = false;
		    		// 곡 맨 처음엔 여기 들어와야 한다.
		    		sample = Output.output_sample -current_sample_bak ;
//		    		spand_time = System.currentTimeMillis();
		    		current_sample_bak = Output.output_sample;
		    		sample_add  = 0;
		        }
		        else
		        {
///		            long cal =  System.currentTimeMillis() - spand_time;           // 세밀. 
	//	            sample_add = ( cal * 44100);                                    // 세밀 보상해준다. 시간에 따라 샘플 진행수를 
		        }
		    	
//		    	long	delta;
//		    	float 	time = (float)(sample+sample_add) / (float)(midi_time_ratio * 44100);
//		        delta = (int)(time - lastTime);
//		    	delta += lastMod;
		    	
		  //  	float ms = (float)Global.Inst().std_tick/(float)1000000;	// ms per 1tick 
		    	float ms = (float)Global.Inst().std_tick/(float)1000000;	// ms per 1tick 
		    	float ntick = (sample) / (ms*250);
		    //	current_sample_bak -= (sample%(ms*250));
		    	if ( Global.Inst().in_play /*&& !g_pGroup->is_pause*/ )
		    	{
		    		Global.Inst().midi_tick_count += ntick;
		  //  		timeout += ntick;  
		  //		NSLog(@"g_midi_tick %d ntick %d  sampe %d\n",g_midi_tick_count, ntick, (sample+sample_add));
		    	}

		    
		    	
		    	
		    	
		    	Canvas canvas = null;
		    	
		    	if ( canvas == null)
		    		continue;
		    	
		    	//if ( mState == STATE_RUNNING)
		    	{
		    		
		    	}
		    	
		    	
		        if( mHolder.getSurface().isValid())
		        {
		        	// drawing lock 
		        	try
		        	{
		        		
		        		canvas = mHolder. lockCanvas(null);		// canvas를 잠그고 버퍼를 할당. 
		        	/*	
		        		synchronized (mHolder)			// 동기화 유지 
		        		{
						i = (--i %320);
						canvas.drawBitmap ( imgBack, i, 0, null ); 		// 버퍼에 그리기 
						
						canvas.drawBitmap ( mOffscreenBitmap, 0, 0, null);
						canvas.drawColor(Color.BLACK);
						Paint Pnt = new Paint();
						Pnt.setColor(Color.BLUE);
						canvas.drawCircle( 100, 100,80, Pnt);
						// draw glyphs
						//glyphs.drawString(canvas, "Po ", 100, 100 );
				
		        		}
		        	*/
		        		
		        		doDraw( canvas );
		        	
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
	
	@Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
    //        if (thread != null)
     //           thread.pause();

        }
    }

	
}


