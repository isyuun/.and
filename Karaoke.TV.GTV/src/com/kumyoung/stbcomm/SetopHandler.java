package com.kumyoung.stbcomm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
		} // try-catch 필요
		Method method = null;
		try {
			// new Class[] { int.class, byte[].class, int.class }
			method = clz.getDeclaredMethod(methodName, new Class[] { byte[].class });
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		String[] hide = null;
		try {
			final String sec_num = "SecretNum";
			hide = (String[]) method.invoke(clz, sec_num.getBytes());

			if (hide != null)
			{
				// _LOG.d("ke", "SecretNum :" + hide[0]);
				secretNum = new String(hide[0]);
			}
			else
			{
				secretNum = new String("0000");
			}
			/*
			 * hide = (String[]) method.invoke(clz, custom_id.getBytes() );
			 * _LOG.d("ke", "Custom :" + String(hide[0]));
			 */
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
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
		} // try-catch 필요

		Method method = null;
		try {
			// new Class[] { int.class, byte[].class, int.class }
			method = clz.getDeclaredMethod(methodName, new Class[] { boolean.class });
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}

		try {
			ret = (Integer) method.invoke(clz, new Object[] { new Boolean(b) });
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}

		return ret;

	}

}
