package com.lguplus.iptv3.base.settingmanager;

import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.lge.sys.ILgIptvShmRemote;
//import com.lguplus.iptv3.base.lib.framework.common.utils.LogUtil;


public class GetILgIptvShmRemote {
	
	private static final String TAG = GetILgIptvShmRemote.class.getSimpleName();
	public static final int SERVICE_CONNECT_SUCCESS = 200;
	
	
	public String pvsReturnString;
	public String fwVersion;
	public String subScriberNo;
	public String regionCode;
	public String lastPvsEvent;
	public String configXml;
	public String mac;
	
	
	ILgIptvShmRemote mILgIptvShmRemote = null;
	
	protected Context mContext;
	protected Handler mHandler;
	
	public GetILgIptvShmRemote(Context mContext, Handler mHandler) {
		this.mContext = mContext;
		this.mHandler = mHandler;
	}

	
	public void onBindIptvRemote() {
		//if (getServiceTaskName() == false) {
		
		Log.d( TAG, "call onBindIptvRemote!!!! ");		
			Intent intent = new Intent("com.lge.sys.ILgIptvShmRemote");
			String packageName = "com.lge.sys";
			String className = "com.lge.sys.IptvShmService";
			intent.setComponent(new ComponentName(packageName, className));
			try{
				mContext.bindService(intent, srvConn, Context.BIND_AUTO_CREATE);
			}catch (Exception e){
				e.printStackTrace();
			}
//		}
	}

	public String getMacAddress(){
		//SettingManager.getInstance().setValueUP(UPDataManager.UP_KEY_GENERAL_MACADDRESS, mac);
		if (mac != null) {
			Log.d(TAG, "getMacAddress() = " + mac);
		}
		return mac;
	}
	
	public void unbindLgIptvRemote() {
		mContext.unbindService(srvConn);
		
		Log.d(TAG, "disconnect" + "disconnect" + "disconnect"
				+ "disconnect" + "disconnect");
	}

	private ServiceConnection srvConn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d( TAG, "onServiceconnected");
			mILgIptvShmRemote =  ILgIptvShmRemote.Stub.asInterface(service);
			Log.d( TAG, "mILgIptvShmRemote" + mILgIptvShmRemote);
			
			try {
				mac = mILgIptvShmRemote.getMacAddress();
				pvsReturnString = mILgIptvShmRemote.getPvsReturnString();
				fwVersion = mILgIptvShmRemote.getFwVersion();
				subScriberNo = mILgIptvShmRemote.getSubscriberNo();
				regionCode = mILgIptvShmRemote.getRegionCode();
				lastPvsEvent = mILgIptvShmRemote.getLastPvsEvent();
				configXml = mILgIptvShmRemote.getConfigXml();
			/*	
				Log.d("service", "pvrReturnString :"+pvsReturnString );
				Log.d("service", "regionCode :"+regionCode );
				Log.d("service", "lastPvsEvent :"+ lastPvsEvent );
				Log.d("service", "configXml :"+configXml );
			*/	
				mHandler.sendEmptyMessage(1001);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d( TAG, "Disconnected");
			mILgIptvShmRemote = null;
		}
	};
	
	private boolean getServiceTaskName() {
		boolean checked = false;

		ActivityManager am = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> info;

		info = am.getRunningServices(3);

		for (Iterator<ActivityManager.RunningServiceInfo> iterator = info
				.iterator(); iterator.hasNext();) {
			RunningServiceInfo runningTaskInfo = (RunningServiceInfo) iterator
					.next();

			if (runningTaskInfo.service.getClassName().equals("com.lge.sys")) 
			{
				checked = true;
				Log.d( TAG, "Service is Live");
			}
		}

		if (checked == false)
			Log.d( TAG, "Service is Die");

		return checked;
	}
	
}
