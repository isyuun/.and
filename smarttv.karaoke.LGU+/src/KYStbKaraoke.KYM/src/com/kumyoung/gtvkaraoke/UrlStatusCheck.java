package com.kumyoung.gtvkaraoke;


import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

/**
 * 
 * @author macuser
 * 
 *
 */
public class UrlStatusCheck implements Runnable {
	
	private static final String TAG = UrlStatusCheck.class.getSimpleName();
	public String url = null;  

	public long timeOut = 0;  

	public UrlStatusCheck( String url, int timeOut )  
	{  
		// 상태 체크할 URL 과 타임아웃 시간을 받는다.  
		this.url = url;  
		this.timeOut = (long) timeOut;  
	}  

	// URL 상태 코드를 리턴한다.  
	public int getResponseCode()  
	{  
		// 실제 URL 상태를 체크할 클래스  
		UrlCheckProcess aUrlCheckProcess = new UrlCheckProcess( url );  
		aUrlCheckProcess.start(); // RUN 실행  

		try  
		{  
			// 상태 체크가 끝나거나 타임 아웃될 때까지 대기 상태  
			aUrlCheckProcess.join( timeOut ) ;  
		}  
		catch( Exception e )  
		{
		   
		    Log.e("ke", "UrlCheckProcess join exception :" + e.getMessage() );
		    
		}  

	// 타임아웃 시간 내에 URL 상태 체크가 되지 않았을 경우  
		if( aUrlCheckProcess.isConnected() == false )  
		{  
		    synchronized( aUrlCheckProcess )  {  
		        System.out.println("Timed out!!");  
		        //ppp aUrlCheckProcess.stop();  
		        aUrlCheckProcess.reapResources();  
	    }  
	}  

	return  aUrlCheckProcess.responseCode();  
} // runnalbel 

	@Override
	public void run() {

	}  
	}  

	class UrlCheckProcess extends Thread  
	{  
		HttpURLConnection aHttpURLConnection = null;  
		String url = null;  
		boolean isConnected = false;  
		int _responseCode = 0;  
		public UrlCheckProcess( String url )  
		{  
			this.url = url;  
		}  
		public void run()  
		{  
			//System.out.println("START");  
			try  
			{  
				//System.out.println("URL 접근 주소 : " + url );  

				/*  
				 * URL 객체 생성후 컨넥션한 뒤 응답코드를 반환한다.  
				 * 응답코드에 대한 상태는 다음과 같다.  
				 * 5XX : 서버 오류  
				 * 4XX : 클라이언트 오류  
				 * 3XX : 위치 재지정/방향 재지정  
				 * 2XX : 일반적으로 OK  
				 */  
				URL aURL = new URL( url );  
				aHttpURLConnection = (HttpURLConnection) aURL.openConnection();  
				_responseCode = aHttpURLConnection.getResponseCode();  
			}  
			catch( Exception e )  
			{
			   Log.e("ke", "response exception :" +e.getMessage() ); 
			   
			   _responseCode = 200;      // check point
			}  

			//System.out.println("Finished");  
			isConnected = true; // 여기까지 오게되면 상태 체크가 완료된것임.  
		}  

		// URL 상태 체크가 완료되었는지 확인  
		public boolean isConnected()  
		{  
			return isConnected ;  
		}  

		// URL 응답 결과 코드를 반환  
		public int responseCode()  
		{  
			return _responseCode;  
		} 

		// 사용한 자원 해제 부분  
		public void reapResources()  
		{  
			System.out.println("Reap the resources.") ;  
			aHttpURLConnection.disconnect();  
		}  
	
}
