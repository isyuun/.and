package com.kumyoung.gtvkaraoke;
import isyoon.com.devscott.karaengine.Global;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class ContentsDownloader {
	
	
	/** Nested class that performs progress calculations (counting) */
	//	private class ProgressThread extends Thread 
	
//    	Handler mHandler;
//        final static int STATE_DONE = 0;
//        final static int STATE_RUNNING = 1;
       final static int STATE_CONFIG 		= 1;
       final static int STATE_CHKSCHEDULE 	= 2;
       final static int STATE_DOWNLOAD 	= 3;
//        final static int STATE_READY		= 4;
       final static int STATE_SLEEP		= 5;
       final static int STATE_SELFUPDATE	= 7;
        
       int mState;
       int mStateNext;
//        static int total;
 //       static int pos;
        
       public ContentsDownloader()
       {
       }
//     ProgressThread(Handler h) {
//          mHandler = h;
//     }
      
       /*j
       static public void HttpPostData() 
       {
       /// 	SocketPermission("www.company.com:7000-", "connect,accept");
    	   try {
                 //--------------------------
                 //   URL 설정하고 접속하기
                 //--------------------------
                 URL url = new URL("http://korea-com.org/foxmann/lesson01.php");       // URL 설정
                 HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
                
                 //--------------------------
                 //   전송 모드 설정 - 기본적인 설정이다
                 //--------------------------
                 http.setDoInput(true);						// 서버에서 읽기 모드 지정
                 http.setDoOutput(true);					// 서버로 쓰기 모드 지정  
                 http.setDefaultUseCaches(false);                                           
                 http.setRequestMethod("POST");				// 전송 방식은 POST
                
                 //--------------------------
                 // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
                 //
                 http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                 
                 //--------------------------
                 //   서버로 값 전송
                 //--------------------------
                 String myId = "id";
                 String myPWord = "pw";
                 String myTitle = "title";
                 String mySubject = "subject";
                
                 StringBuffer buffer = new StringBuffer();
                 buffer.append("id").append("=").append(myId).append("&");                 // php 변수에 값 대입
                 buffer.append("pword").append("=").append(myPWord).append("&");   // php 변수 앞에 '$' 붙이지 않는다
                 buffer.append("title").append("=").append(myTitle).append("&");           // 변수 구분은 '&' 사용 
                 buffer.append("subject").append("=").append(mySubject);
                 
                 OutputStream os = http.getOutputStream();
                 OutputStreamWriter outStream = new OutputStreamWriter( os, "EUC-KR");
                 PrintWriter writer = new PrintWriter(outStream);
                 writer.write(buffer.toString());
                 writer.flush();
                 
                 //--------------------------
                 //   서버에서 전송받기
                 //--------------------------
                 InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "EUC-KR"); 
                 BufferedReader reader = new BufferedReader(tmp);
                 StringBuilder builder = new StringBuilder();
                 String str;
                 while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                      builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                 }
                 String myResult = builder.toString();                       // 전송결과를 전역 변수에 저장
                 
                 Log.d("ke", myResult);
         //       ((TextView)(findViewById(R.id.text_result))).setText(myResult);
          //      Toast.makeText(MainActivity.this, "전송 후 결과 받음", 0).show();
            } catch (MalformedURLException e) {
                   //
            } catch (IOException e) {
            	e.printStackTrace();
            } // try
       } // HttpPostData
       */ 

       /**
        * KYC Downloader
       */
       /* 아이폰 서버에서 곡을 갖어온다. 
       static public Boolean DownloaderKYC ( int sno, String writeName)
       {
        	//HttpPostData();
            try {
                //http://xla.ikaraoke.kr/ios/GetMusicKY.asp?song_id=%d&auth_id=2233&device_id=1&phone_id=shintest
                
            	URL url = new URL("http://xla.ikaraoke.kr/ios/GetMusicKY.asp");
                HttpURLConnection conn = (HttpURLConnection)  url.openConnection();
                
                // If you invoke the method setDoOutput(true) on the URLConnection, it will always use the POST method.
                conn.setDefaultUseCaches(false);
                conn.setDoInput(true);		//서버로부터 데이타를 받을수 있도록 한다. 
                conn.setDoOutput(true);			//서버로 데이타를 전송할수 있도록한다. 
                
                conn.setRequestMethod("POST");	// 전달방식을 결정한다. 
                //conn.setConnectTimeout(10000);  // 커넥션 타임아웃
                //conn.setAllowUserInteraction(true);
                
                // Http Header Setting
                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=euc-kr");
                StringBuffer sb = new StringBuffer();
                sb.append("song_id").append("=").append( Integer.toString(sno) ).append("&");
                sb.append("auth_id").append("=").append("2233").append("&");
                sb.append("device_id").append("=").append("1").append("&");
                sb.append("phone_id").append("=").append("shintest");
                
                //
                OutputStream output_stream = conn.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter( output_stream , "euc-kr"));
                pw.write(sb.toString());
                pw.flush();
                //-------------------------
				output_stream.close();
        
              
				//---------------------------
                int resCode = 0; // RMS 와의 연결 응답값
                resCode = conn.getResponseCode(); 
                if ( resCode != HttpURLConnection.HTTP_OK )
                {
                	BufferedReader rd_rsp = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                	String line_rsp;
                	while ((line_rsp = rd_rsp.readLine()) != null) {
                		System.out.println(line_rsp);
                	}
                	rd_rsp.close();
                }
                
        
                // write file -----------------------------------------------------------------
                File file = new File ( writeName );
				FileOutputStream out = new FileOutputStream(file);
				System.out.println("open output streamming ");
	
				BufferedInputStream inputStream = new BufferedInputStream( conn.getInputStream() );
				int total = conn.getContentLength();
				int pos  = 0;
				byte[] buffer = new byte[1024*8];
				int len1 = 0;
				while ( ( len1= inputStream.read(buffer) ) >= 0 )
				{
					out.write( buffer, 0, len1 );
					pos += len1;
				}
				
			
				out.close();
                
                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                String line;
                while ((line = rd.readLine()) != null) {
                   System.out.println(line);
                }
           //     wr.close();
                rd.close();
		
            }
            catch (Exception e) {
            	Log.e("ke", "check permissions:W");
            	return false;
            }
//			finish();
			return true;
        }
       */
        
       /**
        * 미디 파일을 받아서 지정된 미디 버퍼에 저장해 놓는다. 
        * @param sno
        * @param writeName
        * @return
        */
       static public Boolean DownloaderMIDI ( int sno )
       {
           	String sNum = String.format("%05d", sno );
           
           	URL urlMIDI = null;
           	URL urlSOK = null;
           	try {
     //      		urlMIDI = new URL( DataHandler.serverKYIP + "svc_media/mmid/"+ sNum + ".mid");
    //       		urlSOK = new URL( DataHandler.serverKYIP + "svc_media/msok/"+ sNum + ".sok");
           		
           		
                urlMIDI = new URL( DataHandler.serverKYIP + "mmfs/mmid/"+ sNum + ".mid");
                urlSOK = new URL( DataHandler.serverKYIP + "mmfs/msok/"+ sNum + ".sok");
   
           	}
           	catch( Exception e )
           	{
           		Log.e("ke", e.getCause() + " : " + e.getMessage() );
            	return false;
           	}
            
           	
            try {
                URLConnection conn = (URLConnection)  urlMIDI.openConnection();
                // write memory -----
				BufferedInputStream inputStream = new BufferedInputStream( conn.getInputStream() );
				int total = conn.getContentLength();
				byte[] buffer = new byte[1024*8];
				int len1 = 0, pos = 0;
				while ( ( len1= inputStream.read(buffer) ) >= 0 )
				{
					System.arraycopy(buffer, 0, Global.Inst().rawMIDI, pos, len1);
					pos += len1;
				}
            }
            catch (Exception e) {
            	Log.e("ke", e.getCause() + " : " + e.getMessage() );
            	return false;
            }
            
            
            try {
                URLConnection conn = (URLConnection)  urlSOK.openConnection();
                // write memory -----
				BufferedInputStream inputStream = new BufferedInputStream( conn.getInputStream() );
//				int total = conn.getContentLength();
				byte[] buffer = new byte[1024*8];
				int len1 = 0, pos = 0;
				while ( ( len1= inputStream.read(buffer) ) >= 0 )
				{
					System.arraycopy(buffer, 0, Global.Inst().rawSOK, pos, len1);
					pos += len1;
				}
            }
            catch (Exception e) {
            	Log.e("ke", e.getCause() + " : " + e.getMessage() );
            	return false;
            }

			
            return true;
        }
       
       
       static public Boolean DownloaderZip ( String strURL, String fileName )
       {
           
           	URL urlZip = null;
           	try {
           		urlZip = new URL( strURL );
           	}
           	catch( Exception e )
           	{
           		Log.e("ke", e.getCause() + " : " + e.getMessage() );
            	return false;
           	}
           	
           	
            File file = new File(fileName);

            
            
           	
            try {
                URLConnection conn = (URLConnection)  urlZip.openConnection();
                // write memory -----
				BufferedInputStream inputStream = new BufferedInputStream( conn.getInputStream() );
				int total = conn.getContentLength();
				byte[] buffer = new byte[1024*8];
				int len1 = 0, pos = 0;
				
				 FileOutputStream fos = new FileOutputStream(file);
                 
				while ( ( len1= inputStream.read(buffer) ) >= 0 )
				{
					///System.arraycopy(buffer, 0, Global.Inst().rawMIDI, pos, len1);
					
					fos.write( buffer);
					pos += len1;
				}
				
				
                 fos.close();
            }
            catch (Exception e) {
            	Log.e("ke", e.getCause() + " : " + e.getMessage() );
            	return false;
            }
            

			
            return true;
        }
       
       

       // post 방식 
       static public Boolean Downloader ( String strUrl, String writeName)
       {
        	//HttpPostData();
            try {
                //http://xla.ikaraoke.kr/ios/GetMusicKY.asp?song_id=%d&auth_id=2233&device_id=1&phone_id=shintest
                
            	URL url = new URL(strUrl);
                HttpURLConnection conn = (HttpURLConnection)  url.openConnection();
                
                // If you invoke the method setDoOutput(true) on the URLConnection, it will always use the POST method.
                conn.setDefaultUseCaches(false);
                conn.setDoInput(true);		//서버로부터 데이타를 받을수 있도록 한다. 
                conn.setDoOutput(true);			//서버로 데이타를 전송할수 있도록한다. 
                
                conn.setRequestMethod("POST");	// 전달방식을 결정한다. 
                //conn.setConnectTimeout(10000);  // 커넥션 타임아웃
                //conn.setAllowUserInteraction(true);
                
                // Http Header Setting
                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=euc-kr");
                
                StringBuffer sb = new StringBuffer();
   //             sb.append("song_id").append("=").append( Integer.toString(sno) ).append("&");
    //            sb.append("auth_id").append("=").append("2233").append("&");
     //           sb.append("device_id").append("=").append("1").append("&");
              sb.append("phone_id").append("=").append("shintest");
                
                //
                OutputStream output_stream = conn.getOutputStream();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter( output_stream , "euc-kr"));
                pw.write(sb.toString());
                pw.flush();
				output_stream.close();
        
              
				//---------------------------
                int resCode = 0; // RMS 와의 연결 응답값
                resCode = conn.getResponseCode(); 
                if ( resCode != HttpURLConnection.HTTP_OK )
                {
                	BufferedReader rd_rsp = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                	String line_rsp;
                	while ((line_rsp = rd_rsp.readLine()) != null) {
                		System.out.println(line_rsp);
                	}
                	rd_rsp.close();
                }
                
        
                // write file -----------------------------------------------------------------
                File file = new File ( writeName );
				FileOutputStream out = new FileOutputStream(file);
				System.out.println("open output streamming ");
	
				BufferedInputStream inputStream = new BufferedInputStream( conn.getInputStream() );
				int total = conn.getContentLength();
				int pos  = 0;
				byte[] buffer = new byte[1024*8];
				int len1 = 0;
				while ( ( len1= inputStream.read(buffer) ) >= 0 )
				{
					out.write( buffer, 0, len1 );
					pos += len1;
				}
				
			
				out.close();
                
                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                String line;
                while ((line = rd.readLine()) != null) {
                   System.out.println(line);
                }
           //     wr.close();
                rd.close();
		
            }
            catch (Exception e) {
            	Log.e("ke", "check permissions:W");
            	return false;
            }
//			finish();
			return true;
        }
       
       
       public static boolean downloadFile(String fileUrl, String writeName){
           URL myFileUrl =null;
           HttpURLConnection conn = null;
           try {
               myFileUrl= new URL(fileUrl);
           } catch (MalformedURLException e) {
               e.printStackTrace();
               return false;
           }
            try {
                conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            
           
               int length = conn.getContentLength();
               int[] bitmapData =new int[length];
               byte[] bitmapData2 =new byte[length];
           
           
               try {
                InputStream is = conn.getInputStream();
                
                
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
       
       // write file -----------------------------------------------------------------
               File file = new File ( writeName );
               FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
       
        
               BufferedInputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream( conn.getInputStream() );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
               int total = conn.getContentLength();
           int pos  = 0;
           byte[] buffer = new byte[1024*8];
           int len1 = 0;
           try {
            while ( ( len1= inputStream.read(buffer) ) >= 0 )
               {
                   out.write( buffer, 0, len1 );
                   pos += len1;
               }
            
            
           } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           }
   
               try {
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
               
               
          return true;
     }


}
