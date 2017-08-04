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
 * project	:	Karaoke.KPOP.OLD
 * filename	:	WaringFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ WaringFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 8. 7.
 * @version 1.0
 */

class PopupFragment extends _BaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	// -- "닫기" 버튼 선택 시는 팝업 이미지만 닫음.
	// * KP_0000 프로토콜의 "info" 에 팝업 이미지 출력 정보 추가.
	/**
	 * <br>
	 * 1. "is_pop" - "Y" / "N" <br>
	 * - "Y" : 메인에 팝업 이미지 노출 <br>
	 * - "N" : 메인에 팝업 이미지 미노출
	 */
	String is_pop;
	///**
	// * <br>
	// * 2. "btn_type" - "-1", "0", "1", "2" <br>
	// * - "-1" : "강제확인" 버튼 하단에 노출 <br>
	// * - "0" : "확인" 버튼 하단에 노출 <br>
	// * - "1" : "닫기" 버튼 하단에 노출 <br>
	// * - "2" : "확인", "닫기" 버튼 하단에 노출
	// */
	//int btn_type;
	/**
	 * 3. "url_img_pop" : 메인 팝업 이미지 URL
	 */
	String url_img_pop;
	/**
	 * 4. "url_pop" : 메인 팝업 이미지의 "확인" 버튼 선택 시 링크 주소
	 */
	String url_pop;
	String txt_pop;

	public PopupFragment() {
		super();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu1, MenuInflater menuinflater) {
		// super.onCreateOptionsMenu(menu1, menuinflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.w(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_popup, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void start() {
		super.start();

		KPItem info = getInfo();

		is_pop = info.getValue("is_pop");
		//btn_type = TextUtil.parseInt(info.getValue("btn_type"));
		url_img_pop = info.getValue("url_img_pop");
		url_pop = info.getValue("url_pop");
		txt_pop = info.getValue("result_message");

		View v = null;

		v = findViewById(R.id.img_popup);
		if (v != null) {
			if (URLUtil.isNetworkUrl(url_img_pop)) {
				putURLImage(mContext, (ImageView) v, url_img_pop, true, R.drawable.bg_trans);
				v.setVisibility(View.VISIBLE);
			} else {
				v.setVisibility(View.GONE);
			}
		}

		v = findViewById(R.id.txt_popup);
		if (v != null) {
			putValue(v, txt_pop);
			if (TextUtil.isEmpty(txt_pop)) {
				v.setVisibility(View.GONE);
			}
		}

		///**
		// * 2. "btn_type" - "-1", "0", "1", "2"
		// * - "-1" : "강제확인" 버튼 하단에 노출
		// * - "0" : "확인" 버튼 하단에 노출
		// * - "1" : "닫기" 버튼 하단에 노출
		// * - "2" : "확인", "닫기" 버튼 하단에 노출
		// */
		//v = findViewById(R.id.btn_confirm);
		//if (v != null) {
		//	v.setVisibility(View.GONE);
		//}
		//
		//v = findViewById(R.id.btn_close);
		//if (v != null) {
		//	v.setVisibility(View.GONE);
		//}
		//
		//switch (btn_type) {
		//case -1:
		//	v = findViewById(R.id.btn_confirm);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//case 0:
		//	v = findViewById(R.id.btn_confirm);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//case 1:
		//	v = findViewById(R.id.btn_close);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//case 2:
		//	v = findViewById(R.id.btn_confirm);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	v = findViewById(R.id.btn_close);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//
		//default:
		//	v = findViewById(R.id.btn_close);
		//	if (v != null) {
		//		v.setVisibility(View.VISIBLE);
		//	}
		//	break;
		//}
	}

	//@Override
	//public void onClick(View v) {
	//
	//	super.onClick(v);
	//
	//	if (v.getId() == R.id.img_popup || v.getId() == R.id.btn_confirm) {
	//		if (btn_type == -1) {
	//			setResult(_IKaraoke.KARAOKE_RESULT_FINISH, null);
	//			// onOptionsItemSelected(R.id.base, "", true);
	//		}
	//		close();
	//		openWebView(webview.class, "", "", "", url_pop, "POST", true);
	//	} else if (v.getId() == R.id.btn_close) {
	//		close();
	//	} else {
	//	}
	//}
	//
	///**
	// * 백버튼처리
	// */
	//@Override
	//public boolean onBackPressed() {
	//	if (btn_type == -1) {
	//		setResult(_IKaraoke.KARAOKE_RESULT_FINISH, null);
	//		// onOptionsItemSelected(R.id.base, "", true);
	//	}
	//	return super.onBackPressed();
	//}
}
