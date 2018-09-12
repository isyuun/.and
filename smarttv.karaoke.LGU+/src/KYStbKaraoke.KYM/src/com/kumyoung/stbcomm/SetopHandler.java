package com.kumyoung.stbcomm;

import isyoon.com.devscott.karaengine.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import com.kumyoung.stbcomm.SIMClientHandlerLGU;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

//import com.lgdacom.monitor.*;
//import android.widget.Toast;

/**
 * singleton
 */
public class SetopHandler {

	private static final String TAG = SetopHandler.class.getSimpleName();
	private static SetopHandler instance;

	private SetopHandler()
	{
	}

	private static Class clz = null;

	public static SetopHandler getInstance()
	{
		if (instance == null)
		{
			instance = new SetopHandler();
		}
		return instance;
	}

	public String getSecretNum()
	{
		String className = "com.lge.iptv.IptvPlayer";
		String methodName = "readUserPreference";

		String secretNum = "";

		try {
			clz = Class.forName(className);
			Method[] methods = clz.getDeclaredMethods();
			/*
			 * for ( Method m: methods) {
			 * System.out.println(m);
			 * }
			 */

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // try-catch 필요->isyoon:누가그걸모르냐띨빡아.
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		Method method = null;

		try {
			// new Class[] { int.class, byte[].class, int.class }
			method = clz.getDeclaredMethod(methodName, new Class[] { byte[].class });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} // try-catch 필요->isyoon:누가그걸모르냐띨빡아.
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		String[] hide = null;
		try {
			final String sec_num = "SecretNum";
			hide = (String[]) method.invoke(clz, sec_num.getBytes());

			if (hide != null)
			{
				// Log.d("ke", "SecretNum :" + hide[0]);
				secretNum = new String(hide[0]);
			}
			else
			{
				secretNum = new String("0000");
			}
			/*
			 * hide = (String[]) method.invoke(clz, custom_id.getBytes() );
			 * Log.d("ke", "Custom :" + String(hide[0]));
			 */
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} // try-catch 필요->isyoon:누가그걸모르냐띨빡아.
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return (secretNum);
	}

	static Method mReadUPMethod = null;

	public static String[] readUP(String preferenceKey)
	{
		String className = "com.lge.iptv.IptvPlayer";
		String methodName = "readUserPreference";
		try {
			Class clz = Class.forName(className);
			if (mReadUPMethod == null) {
				mReadUPMethod = clz.getDeclaredMethod(methodName, byte[].class);
			}
			Object ret = mReadUPMethod.invoke(clz, preferenceKey.getBytes());
			if (ret != null) {
				return (String[]) ret;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} // try-catch 필요->isyoon:누가그걸모르냐띨빡아.
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	static public int getSBC_TYPE()
	{
		String[] sbcType = readUP("SBC_TYPE");
		int sbcTypeNum = -1;
		try {
			if (sbcType != null)
				sbcTypeNum = Integer.parseInt(sbcType[0]);
		} catch (Exception e) {
			e.getStackTrace();
			sbcTypeNum = -1;
		}

		if (sbcTypeNum != -1 && sbcTypeNum % 1000 == 102) {
			return 0;       // 기업형
		} else {

			return 1;         // 일반형 `
		}
	}

	public int setKaraokeMode(boolean b)
	{

		Log.d(TAG, "setKaraokeMode : " + b);
		int ret = 0;

		String className = "com.lge.iptv.SystemSetting";
		String methodName = "setKaraokeMic";

		try {
			clz = Class.forName(className);
			Method[] methods = clz.getDeclaredMethods();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // try-catch 필요->isyoon:누가그걸모르냐띨빡아.
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		Method method = null;
		try {
			// new Class[] { int.class, byte[].class, int.class }
			method = clz.getDeclaredMethod(methodName, new Class[] { boolean.class });
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			ret = (Integer) method.invoke(clz, new Object[] { new Boolean(b) });
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ret;

	}

}
