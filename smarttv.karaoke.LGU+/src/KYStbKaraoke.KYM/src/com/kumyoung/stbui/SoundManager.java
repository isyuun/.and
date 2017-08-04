package com.kumyoung.stbui;

//import isyoon.com.devscott.karaengine.Global;


import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;



public class SoundManager {
	
	  private static SoundManager _instance;
	  static 
	  {
		    _instance = new SoundManager();
	  }
    	
	  private  SoundPool mSoundPool; 
	  private  HashMap<Integer, Integer> mSoundPoolMap; 
	  private  AudioManager  mAudioManager;
	  private  Context mContext;
	  private float streamVolume;
	  
	  private int maxIndex = 0;
    	
    	
    	private SoundManager()
    	{
    	}
    	public static SoundManager getInstance() {
    		    return _instance;
    	}
    		
    	public void initSounds(Context theContext) { 
    		 mContext = theContext;
    	     mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0); 
    	     mSoundPoolMap = new HashMap<Integer, Integer>(); 
    	     mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE); 	     
    	     
    	     
  		float streamCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
   		float streamMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);     
   		 streamVolume = streamCurrent / streamMax;  
 
    	} 
    	
    	public void addSound(int Index,int SoundID)
    	{
    		mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, Index));
    		maxIndex ++;
    	}
    	
    	public void playSound(int index) { 
    		
    		if ( index < maxIndex)
    		{
    		
    			// int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
    			mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f); 
    		} 
    	}
    	
    	public void playLoopedSound(int index) { 
    	   //  int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
    	     mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f); 
    	}
    	
}