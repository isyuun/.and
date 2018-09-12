package com.kumyoung.gtvkaraoke;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;

//import com.example.android.apis.R;

//import android.content.Intent;
//import android.net.Uri;
import android.os.Bundle;
//import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
//import android.widget.Toast;

/**
 * 
 * @author macuser
 * 컨텐츠 업데이트와 리스트를 관리한다. 
 *
 */
public class WebUpdater implements Runnable {
	
	private static final String TAG = WebUpdater.class.getSimpleName();
	private static WebUpdater 		instance;
//	private ADPlayer		player = null;
//	private static int 		cur = 0;
	
/*
	private static ArrayList<String> videoList = null;  	
	private static ArrayList<String> downloadList = null;  	
	private static ArrayList<Integer> downloadSizeList = null;  	
	public void SetPlayer( ADPlayer _player )
	{
		player = _player;
	}
*/	
	
	private WebUpdater() 
	{
	}
	
	public static WebUpdater getInstance() 
	{
		if ( instance == null )
		{
		/*	
			videoList = new ArrayList<String>();
			downloadList = new ArrayList<String>();
			downloadSizeList = new ArrayList<Integer>();
		*/	
			instance = new WebUpdater();
		
   			Thread updateThread = new Thread(instance);
			updateThread.start();
		}
		return instance;
	}
	

	/*
	 *  비디오 리스트에 있는것을 하나 꺼내온다.
	 */
	public String NextContent()
	{
/*	
		String str = "";
		if ( videoList.size()  > 0 )
		{
			cur =  ++cur%videoList.size();
			str = videoList.get(cur);
			File f = new File( str ); 
        	if (f.exists())
        	{
        		return str;
        	}
		}	
		else
		{
			str = "/system/media/default.mp4";
        		// "/system/media/default.wmv");
				//str = "res/raw/preview.mp3";
		 	XmlParser p = new XmlParser();
        	if (p.ParserLocal( "/mnt/sdcard/AD/default.xml" ) == true )
        	{
        		for ( int i =0; i < p.contentList.size(); i++)
        			videoList.add( "/mnt/sdcard/AD/" + p.contentList.get(i) );
        		
        	}
        	else
        	{
        		Log.d("native", "not ready SDCARD!!!");
        		videoList.clear();
        	}
			return str;
		}
*/
		return "";
	}
	
	public void run() 
	{
		// ress check
		// doCommand 
		ProgressThread	p = new ProgressThread(mhandler);
		p.start();
		do 
		{
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        
		} while (true);
    }

	/**
	 * 
	 * @param str
	 */
	public void doCommnad( String str )
    {
	/*	
    	mstrCommand = str;
    	new Thread( new Runnable() {
    		public void run() 
    		{
    			mHandler.sendMessage(mHandler.obtainMessage() );
    		}
    	}).start();
    */	
    }

    // Define the Handler that receives messages from the thread and update the progress
    final Handler mhandler = new Handler() {
        public void handleMessage(Message msg) 
        {
/* ppp       	
            int total = msg.getData().getInt("total");
            int pos = msg.getData().getInt("pos");
           
//            int percent =(int) ( (float)pos/(float)total );
//            progressDialog.setProgress((int)( percent * 100) );
            player.setProgressValue( pos/1024, total/1024 );

            if (total <= pos ){
//                dismissDialog(PROGRESS_DIALOG);
///                progressThread.setState(ProgressThread.STATE_DONE);
            }
*/            
        }
    };

    /** Nested class that performs progress calculations (counting) */
    private class ProgressThread extends Thread {
    	
        Handler mHandler;
        final static int STATE_CONFIG 		= 1;
        int mState;
        int mStateNext;
        int total;
        int pos;
        int wait_count = 4;
        ProgressThread(Handler h) {
            mHandler = h;
        }
        
        public Boolean Downloader (String strURL, String writeName, Integer filelength )
        {
        	total = 0;
            pos = 0;
            int cn = 0;
			try {
				
				Log.d( TAG, "ftp downlaod : " + strURL);
		//		String strDownFile = "http://192.168.10.10/~macuser/test.wmv";
		//		String strDownFile = "ftp://mirror.csclub.uwaterloo.ca/index.html";
//				String strDownFile = "ftp://devscott:some0617@192.168.100.1/Dolphins_1080.wmv";
		//		String strDownFile = "ftp://ads:mooha0931@125.141.226.79/contents/1307578663.avi";
				URL u = new URL( strURL );
					
//				HttpURLConnection c = (HttpURLConnection) u.openConnection();
//				c.setRequestMethod("GET");
//				c.setDoOutput(true);
//				c.connect();

				URLConnection c = u.openConnection();

				System.out.println("Date: " + c.getDate());
			    System.out.println("Type: " + c.getContentType());
			    System.out.println("Exp: " + c.getExpiration());
			    System.out.println("Last M: " + c.getLastModified());
			    System.out.println("Length: " + c.getContentLength());
					
//				inputStream = new URL( strDownFile ).openStream();
//				System.out.println("open input streamming ");
				File file = new File ( writeName );
				FileOutputStream out = new FileOutputStream(file);
				System.out.println("open output streamming ");
	
				BufferedInputStream inputStream = new BufferedInputStream( c.getInputStream() );
				if ( c.getContentLength() < 0 )
					total = filelength;//c.getContentLength();
				else
					total = c.getContentLength();
				
					
				byte[] buffer = new byte[1024*8];
				int len1 = 0;
            
        //  	while (mState == STATE_RUNNING) 
				while ( ( len1= inputStream.read(buffer) ) >= 0 )
				{
			
					out.write( buffer, 0, len1 );
					pos += len1;
					try {
						if ( cn++ % 500 ==  499 )
						{
							Thread.sleep(10);
							Message msg = mHandler.obtainMessage();
							Bundle b = new Bundle();
							b.putInt("total", total);
							b.putInt("pos", pos );
							
							msg.setData(b);
							mHandler.sendMessage(msg);
						}

					//	Thread.sleep(1);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				{
							Message msg = mHandler.obtainMessage();
							Bundle b = new Bundle();
							b.putInt("total", total);
							b.putInt("pos", pos );
							
							msg.setData(b);
							mHandler.sendMessage(msg);
				}
				out.flush();
				out.close();
				
				Log.v( TAG, "file download finished");
			} catch (MalformedURLException e) {
				Log.e( TAG,"download failed : "+ strURL);
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				Log.e( TAG, "file download failed");
				return false;
			}
			
			if ( pos == 0 )
				return false;
			
//			finish();
			return true;
        }
        	
       
        /**
         * 
         */
        public void run() 
        {
        	try 
    		{
    			//stbhb_new.html
    			//stbhd_version
        		//http://211.236.190.103:8080/common2/songs_utf8.list	
        		String urlLive = String.format("http://211.236.190.103:8080/common2/songs.list");
        						
        		URL url = new URL( urlLive );
        		HttpURLConnection http = (HttpURLConnection)url.openConnection();
        		if ( http.getResponseCode() == HttpURLConnection.HTTP_OK )
        		{	
        			BufferedReader in =new BufferedReader(new InputStreamReader(  http.getInputStream(),"euc-kr" )  );
        			StringBuilder builder =new StringBuilder();
        			String str;
        			while(( str = in.readLine())!=null) 
        			{
        				String strNo = str.substring( 0,str.indexOf('\t') );
        				int sno = Integer.parseInt( strNo );
        				int firstTab = str.indexOf('\t') +1;
        				String title_and_singer = str.substring( firstTab, str.length());
            			Database.Inst().insertHashData( sno, title_and_singer );
            			
            		
            //			Log.d("ke", sno + " " + title_and_singer);
            			
            			
        			}
        			
        			Log.d( TAG, "update songlist file");
        			//Toast.makeText(this,"Can't open database connection.",Toast.LENGTH_LONG).show();
        		}
    		} 
    		catch (MalformedURLException e) 
    		{
    			e.printStackTrace();
    			return;
    		} 
    		catch (IOException e)
    		{
    			e.printStackTrace();
    			return;
    		}		
        	
        	
        	
/*
        	mState = STATE_CONFIG;   
        	mStateNext = STATE_CHKSCHEDULE;
        	while ( true )
        	{
        		switch( mState )
        		{
        			case STATE_CONFIG:
        			{
        				XmlParser p = new XmlParser();
        				if ( p.ParserLocal( "/mnt/sdcard/AD/default.xml" ) == true )
        				{
        					for ( int i =0; i < p.contentList.size(); i++)
        						videoList.add( "/mnt/sdcard/AD/" + p.contentList.get(i) );
        				}
        				else
        				{
        					videoList.clear();
        				}
        				this.wait_count = 25; // 25sec;        	
        				mState = STATE_SLEEP;
        				mStateNext = STATE_CHKSCHEDULE;
        			}
        			break;

        			case STATE_CHKSCHEDULE:
        			{
        				
        			    Calendar currentDate = Calendar.getInstance();  
        			    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
        			    String sysdate = df.format(currentDate.getTime());  
        				
        				String urlFTP = String.format("ftp://%s@%s/schedule/%s_%s.xml" 
        												, DataHandler.getInstance().ftpAccount
        												, DataHandler.getInstance().serverURL
        												, DataHandler.getInstance().getDealerID()
        												, sysdate );
 
        				Boolean sucsXML = Downloader( 	urlFTP, "/mnt/sdcard/AD/_default.xml", 0 );
     					if ( sucsXML )
        				{
     						File sdcard = Environment.getExternalStorageDirectory();
     						File from 	= new File(sdcard,"/AD/_default.xml" );
        					File to 	= new File(sdcard,"/AD/default.xml"  );
        					from.renameTo(to);
        				}
     					else
        				{
      					}
     			
     					//
     					//
     					//
     					XmlParser p = new XmlParser();
        				boolean b = p.Parser( urlFTP );
        				if ( b )
        				{
        					downloadList.clear();
        					downloadSizeList.clear();
        					for ( int i =0; i < p.contentList.size(); i++)
        					{
        						downloadList.add( p.contentList.get(i) );
        						downloadSizeList.add( p.contentSizeList.get(i) );
        					}
        			
        					
        					//make_schedule
        					mState = STATE_SLEEP;
        					mStateNext = STATE_DOWNLOAD;
        					this.wait_count = (10);	// 10 sec
        				}
        				else
        				{
        					mState = STATE_SLEEP;
        					mStateNext = STATE_CHKSCHEDULE;
        					this.wait_count = (30*60);	// 30min
        				}
        				
        			}
        			break;
        				
        			case STATE_DOWNLOAD:
        				for ( int i =0; i < downloadList.size(); )
        				{
        					File f = new File( "/mnt/sdcard/AD/" + downloadList.get(i) ); 
        					if (f.exists())
        					{
        						i++;
        					}
        					else
        					{
        					
        						String urlFTP = String.format("ftp://%s@%s/contents/"
        														,DataHandler.getInstance().ftpAccount 
        														,DataHandler.getInstance().serverURL);
        															
        						
        						Boolean sucs = Downloader( 	urlFTP +downloadList.get(i), 
        													"/mnt/sdcard/AD/_"+ downloadList.get(i), downloadSizeList.get(i) );
        						if ( sucs )
        						{
        							File sdcard = Environment.getExternalStorageDirectory();
        							File from 	= new File(sdcard, "/AD/_" + downloadList.get(i) );
        							File to 	= new File(sdcard,"/AD/" + downloadList.get(i)  );
        							from.renameTo(to);
        							
        							i++;
        						}
        						else
        						{
        							downloadList.remove(i);
        							// i를 증가시키지 않는다.
        						}
        					}
        				}
        				
        				{
        					videoList.clear();
        					for ( int i =0; i < downloadList.size(); i++)
        							videoList.add( "/mnt/sdcard/AD/" + downloadList.get(i) );
        					
        					// copy download list to playlist 
        					// write default.xml
        				}
        				
       					
       					mState = STATE_SLEEP;
       					mStateNext = STATE_CHKSCHEDULE;
       					this.wait_count = (30 * 60);	// 30 min 
        				break;
        			
        				
        				
        			case STATE_SLEEP:
        				if ( this.wait_count == (30*60) )
        				{
        					mState = STATE_SELFUPDATE;
        				}
        				
        				try 
        				{
        					Thread.sleep(1000 * 1);
        				} 
        				catch (InterruptedException e) 
        				{
        					e.printStackTrace();
        				}        
        				
        				if ( this.wait_count -- <= 0)
        					mState = mStateNext;
        				
       					Log.d("native", "wait " + this.wait_count  + " : "+ videoList.size() );
        				break;
        				
        			case STATE_SELFUPDATE:
        				{
        					
        					boolean b = need_update ();
        					
        					if ( b )
        					{
        						Calendar currentDate = Calendar.getInstance();  
        						SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
        						String sysdate = df.format(currentDate.getTime());  
        						String saveFile = String.format("/mnt/sdcard/%s.apk", sysdate); 
        						File f = new File( saveFile ); 
        						//if (f.exists())
        						//{
        						//}
        						//else
        						{
        							String urlFTP = String.format("ftp://%s@%s/package/", DataHandler.getInstance().ftpAccount, DataHandler.getInstance().serverURL);
        							Boolean sucs = Downloader( 	urlFTP + "update.apk", saveFile, 0 );
        							if ( sucs )
        							{
        								player.updatePackage( saveFile );
        							}
        						}
   							}
        						
							mState = STATE_SLEEP;
							mStateNext = STATE_CHKSCHEDULE;
							this.wait_count = (29 * 60);	// 29 min  fixed.
        				}
        				break;
        		}
        	}
*/	
        }
        
        /* sets the current state for the thread,
         * used to stop the thread */
        /*
        public void setState(int state) {
            mState = state;
        }
        */
    }
}
