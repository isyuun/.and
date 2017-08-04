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
 * project	:	Karaoke.KPOP.LIB
 * filename	:	ResycleUtils.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.util
 *    |_ ResycleUtils.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.widget;

/**
 *
 * TODO<br>
 * <pre>
 * NOTE:이미지메모리누수관리
 * 이런시발쓰란거야말란거야!!!!
 * </pre>
 *
 * @author	isyoon
 * @since	2013. 7. 17.
 * @version 1.0
 * @see
 * ResycleUtils.java
 */
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @author givenjazz
 * 
 */
@Deprecated
public class RecycleUtils {

	private RecycleUtils() {
	};

	public static void recursiveRecycleView(View root) {
		if (root == null)
			return;
		if (root.getBackground() != null) {
			root.getBackground().setCallback(null);
		}
		root.setBackgroundDrawable(null);
		if (root instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) root;
			int count = group.getChildCount();
			for (int i = 0; i < count; i++) {
				recursiveRecycleView(group.getChildAt(i));
			}

			if (!(root instanceof AdapterView)) {
				group.removeAllViews();
			}

		}

		if (root instanceof _ImageView) {
			// 몬지랄이냐이게....
			// //[출처] Android 개발가이드 - Bitmap.recycle()은 가급적 호출해 주는 편이 좋다. (Android 2.x 버전)|작성자 코드멍키
			// Drawable d;
			// Bitmap b;
			// d = ((ImageView) root).getDrawable();
			// if (d instanceof BitmapDrawable) {
			// b = ((BitmapDrawable) d).getBitmap();
			// b.recycle();
			// } // 현재로서는 BitmapDrawable 이외의 drawable 들에 대한 직접적인 메모리 해제는 불가능하다.
			// d.setCallback(null);
			// ((ImageView) root).setImageDrawable(null);
			root = null;
		}

		root = null;

		return;
	}

	@Deprecated
	public static void recursivePauseRecycle(View root) {
		if (root == null)
			return;
		root.setBackgroundDrawable(null);
		if (root instanceof ViewGroup) {
			ViewGroup group = (ViewGroup) root;
			int count = group.getChildCount();
			for (int i = 0; i < count; i++) {
				recursivePauseRecycle(group.getChildAt(i));
			}

		}

		if (root instanceof _ImageView) {
			if (((_ImageView) root).getDrawable() instanceof BitmapDrawable) {
				((BitmapDrawable) ((_ImageView) root).getDrawable()).getBitmap().recycle();
			}
		}

		root = null;

		return;
	}
}
