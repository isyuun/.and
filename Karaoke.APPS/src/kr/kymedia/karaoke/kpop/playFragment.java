/*
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 2012 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.KPOP.APP
 * filename	:	PlayFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ PlayFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.KPnnnn;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._PlayFragment;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.ImageDownLoader3;

/**
 * 
 * TODO<br>
 * NOTE:<br>
 * PlayFragment1/2인터페이스
 * 
 * @author isyoon
 * @since 2013. 9. 27.
 * @version 1.0
 */
class playFragment extends _PlayFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());

		super.onResume();
		if (getApp().getImageDownLoader() instanceof ImageDownLoader3) {
			((ImageDownLoader3) getApp().getImageDownLoader()).setImageLoadingListener(animateFirstListener);
		}
	}

	@Override
	public void open() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.open();
	}

	@Override
	public void onClick(View v) {
		// String vn = getResourceEntryName(v.getId());
		// String cn = v.getClass().getSimpleName();
		// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);


		super.onClick(v);

		try {
			KPItem list = KP_play.getList(0);

			if (v.getId() == R.id.image) {
				String uid = list.getValue("uid");
				if (!TextUtil.isEmpty(uid)) {
					onContextItemSelected(R.id.context_go_holic, KP_play, KP_index, true);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private final ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		// List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (_IKaraoke.DEBUG) Log2.e("[UIL]", getMethodName() + loadedImage);
			if (view.getId() == R.id.img_play && loadedImage == null) {
				((_ImageView) view).setImageResource(R.drawable.bg_play);
			}
		}
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 */
	@Override
	public void KPnnnn() {

		super.KPnnnn();

		KPItem list = KP_play.getList(0);

		View v = null;
		String url = "";

		try {
			v = findViewById(R.id.include_player_title).findViewById(R.id.image);
			url = list.getValue("url_profile");
			if (v != null && URLUtil.isNetworkUrl(url)) {
				putURLImage(mContext, (ImageView) v, url, false, R.drawable.ic_menu_01);
			}

			v = findViewById(R.id.img_play);
			url = list.getValue("bg_play");
			if (v != null) {
				if (URLUtil.isNetworkUrl(url)) {
					putURLImage(mContext, (ImageView) v, url, false, R.drawable.bg_play);
					((_ImageView) v).setScaleType(_ImageView.ScaleType.FIT_XY);
				} else {
					((_ImageView) v).setImageResource(R.drawable.bg_play);
					((_ImageView) v).setScaleType(_ImageView.ScaleType.CENTER_CROP);
				}
				((_ImageView) v).setScaleType(_ImageView.ScaleType.CENTER_CROP);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	protected void openListenPost(KPnnnn KP_xxxx, int index) {

		KPItem list = KP_xxxx.getList(index);
		list.putValue("record_path", mRecord.getPath());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\nlist - " + list.toString(2));
		super.openListenPost(KP_xxxx, index);
	}

	@Override
	protected void openListenPost2(KPnnnn KP_xxxx, int index) {

		KPItem list = KP_xxxx.getList(index);
		list.putValue("record_path", mRecord.getPath());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "\nlist - " + list.toString(2));
		super.openListenPost2(KP_xxxx, index);
	}

	@Override
	public void refresh() {

		super.refresh();
	}

}
