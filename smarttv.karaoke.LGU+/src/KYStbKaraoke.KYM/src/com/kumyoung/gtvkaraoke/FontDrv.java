package com.kumyoung.gtvkaraoke;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

public class FontDrv {
	
	private static FontDrv _instance;	
	
	static {
	    _instance = new FontDrv();
	  }
	  private FontDrv() {}
	  public static FontDrv getInstance() {
		    return _instance;
		  }

	
	private static final String TAG = DrawingPanel.class.getSimpleName();

	public Glyphs 		glyphs;		// the glyphs	with font

	public void font_load(Resources mRes)
	{
	/*	
 		glyphs = new Glyphs(
 				mRes, 
            	BitmapFactory.decodeResource( mRes, R.drawable.kkk_0 ), 
            	BitmapFactory.decodeResource( mRes, R.drawable.kkk_1 ), 
            	BitmapFactory.decodeResource( mRes, R.drawable.kkk_2 ), 
            	BitmapFactory.decodeResource( mRes, R.drawable.kkk_3 ) 
            	
            	
        );
 	*/
		Log.d(TAG, "Green glyphs loaded");	
	}
}
