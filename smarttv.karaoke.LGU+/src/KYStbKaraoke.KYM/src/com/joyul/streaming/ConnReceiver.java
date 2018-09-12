package com.joyul.streaming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;



public class ConnReceiver extends BroadcastReceiver
{ 
    private static final int STATE_NONE = 0;
    private static final int STATE_WIFI_CONNECTED = 1;
    private static final int STATE_MOBILE_CONNECTED = 2;
    private static final int STATE_ETHERNET_CONNECTED = 2;
    private int state = STATE_NONE;
    
    @Override
    public void onReceive(Context context, Intent intent) {
        
        try
        {
          String action = intent.getAction();
          
          /*
          // 네트웍에 변경이 일어났을때 발생하는 부분
          if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
              ConnectivityManager connectivityManager =
                  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
              NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
              //NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
              //Toast.makeText(context,"Active Network Type : " + activeNetInfo.getTypeName() , Toast.LENGTH_SHORT).show();
              //Toast.makeText(context,"Mobile Network Type : " + mobNetInfo.getTypeName() , Toast.LENGTH_SHORT).show();
              
          }
          */
          
         // Toast.makeText(context, "네트웍 상태 변경 인지 ", Toast.LENGTH_SHORT).show();
          
          ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
         // NetworkInfo niWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
         // NetworkInfo niMobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
          NetworkInfo niEthernet = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
          if (state == STATE_NONE)
          {
              if (niEthernet.getState() == State.CONNECTED)
              {
                  state = STATE_ETHERNET_CONNECTED;
                  
//                  Toast.makeText(context, "이더넷 네트웍이 연결 되었습니다" , Toast.LENGTH_SHORT).show();
              }
          }
          else if (state == STATE_ETHERNET_CONNECTED)
          {
              if (niEthernet.getState() == State.DISCONNECTED || niEthernet.getState() == State.DISCONNECTING)
              {
                  state = STATE_NONE;
                  Log.d("CBR", "이더넷 끊어졌다!");
                  
//                  Toast.makeText(context, "네트웍이 단절되었습니다", Toast.LENGTH_SHORT).show();
              }
          }
        } catch ( Exception e)
        {
            e.printStackTrace();
            
                  Toast.makeText(context, "network excpetion", Toast.LENGTH_SHORT).show();
        }
      }

}	