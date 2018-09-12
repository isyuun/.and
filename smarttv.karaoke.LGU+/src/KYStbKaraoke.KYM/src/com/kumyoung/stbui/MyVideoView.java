package com.kumyoung.stbui;

import isyoon.com.devscott.karaengine.Global;

import com.kumyoung.gtvkaraoke.DataHandler;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.MediaController;
import android.widget.VideoView;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


public class MyVideoView extends VideoView
								implements OnErrorListener
{
	 
//	private NumberThread mNumberThread;
	public Boolean isComplete = true;
	public MyVideoView (Context context, AttributeSet attrs) {
	  super(context, attrs);
	 }
	 
	 
	 protected void onMeasure(int widthMeasureSpec, int heigthMeasureSpec) {
		 Display dis = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		 setMeasuredDimension(dis.getWidth(), dis.getHeight());
 	 }
/*
	 class NumberThread extends Thread {
	    	 
	        private int i = 0;
	        private boolean isPlay = false;
	 
	        public NumberThread(boolean isPlay){
	            this.isPlay = isPlay;
	        }
	        public void stopThread(){
	            isPlay = !isPlay;
	        }
	        @Override
	        public void run() {
	            super.run();
	            while (isPlay) 
	            {
	                try { Thread.sleep(20000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                mHandler.post(new Runnable() {
	                    @Override
	                    public void run() {
	           //             mTvNumber.setText(""+i++);
	                    }
	                });
	            }
	        }
	    }
*/	    
	    
	
	 /**
	  * 
	  */
	 public void startProc()
	 {
			 
		 /** key monitor */
		this.setOnCompletionListener( new OnCompletionListener()
		{
				@Override
				public void onCompletion(MediaPlayer arg0)
				{
					isComplete = true;
					//Log.e("report", "media error playback");
					start();
				}
		});

		this.setOnErrorListener( new OnErrorListener() 
		{
				@Override
				public boolean onError( MediaPlayer videostream, int what, int extra)
				{
					Log.e("report", "media error playback");
					Log.e("report", "media error playback");
					Log.e("report", "media error playback");
					Log.e("report", "media error playback");
	//				nextPlay();
					return true;
				}
		});
		 
	/*	 
		runOnUiThread(new Runnable() {
				@Override
				public void run()
				{
				}
		});
	*/	
//		mNumberThread = new NumberThread(true);
//      mNumberThread.start();
        
	
		String SD_PATH = DataHandler.videoPath;
		Log.d("video", SD_PATH );
		//String SD_PATH = "http://kumyoung.hscdn.com/GTV_Video/aaa.mp4";
//        String SD_PATH = "http://joyul.iptime.org/mpeg/aaa.mp4";		
     //   String SD_PATH = "http://kumyoung.hscdn.com/M12088RA040120SP0308.mp4";
	  
	    
	    changeVideo();
	 }
	
	 
	 public void changeVideo()
	 {	
    	new Thread( new Runnable() {
    		public void run() 
    		{
    			mHandler.sendMessage(mHandler.obtainMessage() );
    		}
    	}).start();
    	
	 }
	 
	 
	 private Handler mHandler = new Handler() 
	    {
		    	public void handleMessage(Message msg) 
		    	{
		    		doPlay(DataHandler.videoPath);
		    	}
	    };
	 
	 /**
	  * 
	  * @param runnable
	  */
/* 
	 private void runOnUiThread(Runnable runnable) {
		 
		if ( isComplete == true )
		{
			Log.d("report", "doPaly :");
			isComplete = false;
					//doPlay( "/mnt/sdcard/test.mp4");
			
//				String SD_PATH = "http://joyul.iptime.org/aaa.mp4";			//Couldn't open file on client side, trying server side 
//				setVideoURI( Uri.parse(SD_PATH));
				
			String SD_PATH = "http://joyul.iptime.org/aaa.mp4";			//Couldn't open file on client side, trying server side 
 			setVideoURI( Uri.parse(SD_PATH));
 			
 			start();
		}	
	}
*/

	/*    */
	public void doPlay(String strPathFile )
	{
/*		
//		mVideoView.setVideoPath( strPathFile );
//		String SD_PATH = "http://www.joyul.kr/~mini/toystory.mp4";			//Couldn't open file on client side, trying server side 
//		String SD_PATH = "http://192.168.100.39/aaa.mp4";			//Couldn't open file on client side, trying server side 
		Runnable progress = new Runnable() {
  			public void run() {
  				String SD_PATH = "http://joyul.iptime.org/aaa.mp4";			//Couldn't open file on client side, trying server side 
  				mVideoView.setVideoURI( Uri.parse(SD_PATH));
  				mVideoView.start();
  			}
  		};
  		Thread worker = new Thread ( progress );
  		worker.start();
*/  	
	    
	   
	   if (  Global.isDebugGrid == false )
	   {
	    
	       this.setVideoURI( Uri.parse(strPathFile));
	       this.start();
	   }
  		
	}
	
	
	public void doStop()
	{
	
		this.stopPlayback();
		
	}
	
/*	
	private void setOnCompletionListener(
			OnCompletionListener onCompletionListener) {
	}
*/

	/*       */
/*	
	public void nextPlay()
	{
	  	try {
			Thread.sleep(100);
	  	} catch (InterruptedException e) {
	  		e.printStackTrace();
	  	}
		doPlay("http://joyul.iptime.org/aaa.mp4");
	}
*/

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}
}
