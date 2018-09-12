package kr.kymedia.kykaraoke.tv.data;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.kymedia.kykaraoke.api._Const;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;

class Util extends Thread implements _Const {
	public Handler handler;
	public Bitmap m_bitMap;
	public String m_strImgUrl;
	public int m_iUtilType;

	public Util(Handler h) {
		handler = h;
		m_bitMap = null;
		m_strImgUrl = "";
		m_iUtilType = 0;
	}

	public void setUtilType(int type) {
		m_iUtilType = type;
	}

	public void setImageUrl(String url) {
		m_strImgUrl = url;
	}

	public String getImageUrl() {
		return m_strImgUrl;
	}

	public void sendMessage(int state) {
		Bundle b = new Bundle();
		b.putInt("state", state);

		Message msg = handler.obtainMessage();
		msg.setData(b);
		handler.sendMessage(msg);
	}

	@Override
	public void run() {
		try {
			LoadImageFromWeb();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadImageFromWeb() {
		try {
			/*
			 * InputStream is = (InputStream) new URL(url).getContent();
			 * Drawable d = Drawable.createFromStream(is, "src");
			 * return d;
			 */

			/*
			 * URL imageURL = new URL(m_strImgUrl);
			 * HttpURLConnection conn = (HttpURLConnection)imageURL.openConnection();
			 * BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 10240);
			 * Bitmap bm = BitmapFactory.decodeStream(bis);
			 * Drawable d = new BitmapDrawable(bm);
			 */

			URL imageURL = new URL(m_strImgUrl);
			HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 10240);
			Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			m_bitMap = bm;

			switch (m_iUtilType)
			{
			case REQUEST_UTIL_MAIN_EVENT_IMAGE:
				sendMessage(COMPLETE_UTIL_EVENT_IMAGE);
				break;
			case REQUEST_UTIL_CUSTOMER_DETAIL_IMAGE:
				sendMessage(COMPLETE_UTIL_CUSTOMER_DETAIL_IMAGE);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_1:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_1);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_2:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_2);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_3:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_3);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_4:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_4);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_5:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_5);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_6:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_6);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_7:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_7);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_8:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_8);
				break;
			case REQUEST_UTIL_PROFILE_IMAGE_HOME:
				sendMessage(COMPLETE_UTIL_PROFILE_IMAGE_HOME);
				break;
			case REQUEST_UTIL_CERTIFY_PROFILE_IMAGE:
				sendMessage(COMPLETE_UTIL_CERTIFY_PROFILE_IMAGE);
				break;
			case REQUEST_UTIL_MY_RECORD_PROFILE_IMAGE:
				sendMessage(COMPLETE_UTIL_MY_RECORD_PROFILE_IMAGE);
				break;
			case REQUEST_UTIL_MAIN_QUICK_IMAGE_01_ON:
				sendMessage(COMPLETE_UTIL_MAIN_QUICK_IMAGE_01_ON);
				break;
			case REQUEST_UTIL_MAIN_QUICK_IMAGE_01_OFF:
				sendMessage(COMPLETE_UTIL_MAIN_QUICK_IMAGE_01_OFF);
				break;
			case REQUEST_UTIL_MAIN_QUICK_IMAGE_02_ON:
				sendMessage(COMPLETE_UTIL_MAIN_QUICK_IMAGE_02_ON);
				break;
			case REQUEST_UTIL_MAIN_QUICK_IMAGE_02_OFF:
				sendMessage(COMPLETE_UTIL_MAIN_QUICK_IMAGE_02_OFF);
				break;
			case REQUEST_UTIL_EVENT_DETAIL_ON:
				sendMessage(COMPLETE_UTIL_EVENT_DETAIL_ON);
				break;
			case REQUEST_UTIL_EVENT_DETAIL_OFF:
				sendMessage(COMPLETE_UTIL_EVENT_DETAIL_OFF);
				break;
			case REQUEST_UTIL_SHOP_ITEM_01:
				sendMessage(COMPLETE_UTIL_SHOP_ITEM_01);
				break;
			case REQUEST_UTIL_SHOP_ITEM_02:
				sendMessage(COMPLETE_UTIL_SHOP_ITEM_02);
				break;
			case REQUEST_UTIL_KY_LOGO:
				sendMessage(COMPLETE_UTIL_KY_LOGO);
				break;
			case REQUEST_UTIL_MIC:
				sendMessage(COMPLETE_UTIL_MIC);
				break;
			}
		} catch (Exception e) {
			// System.out.println(e);
			e.printStackTrace();
		}
	}
}