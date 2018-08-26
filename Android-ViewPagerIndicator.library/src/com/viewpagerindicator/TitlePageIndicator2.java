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
 * 2012 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Android-ViewPagerIndicator
 * filename	:	TitlePageIndicator2.java
 * author	:	isyoon
 *
 * <pre>
 * com.viewpagerindicator
 *    |_ TitlePageIndicator2.java
 * </pre>
 * 
 */

package com.viewpagerindicator;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * 
 * TODO NOTE:
 * 
 * @author isyoon
 * @since 2012. 6. 19.
 * @version 1.0
 */

public class TitlePageIndicator2 extends TitlePageIndicator {

	public TitlePageIndicator2(Context context) {
		super(context);
	}

	public TitlePageIndicator2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TitlePageIndicator2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Set bounds for the right textView including clip padding.
	 * 뷰크기의 비율로 변경.
	 *
	 * @param curViewBound
	 *          current bounds.
	 * @param curViewWidth
	 *          width of the view.
	 */
	@Override
	protected void clipViewOnTheRight(Rect curViewBound, float curViewWidth, int right) {
		float clipPadding = curViewWidth * getClipPadding() / 100f;
		curViewBound.right = (int) (right - clipPadding);
		curViewBound.left = (int) (curViewBound.right - curViewWidth);
		//Log.e("TitlePageIndicator2", "clipViewOnTheRight()" + getWidth() + " : " + curViewBound.left);
	}

	/**
	 * Set bounds for the left textView including clip padding.
	 * 뷰크기의 비율로 번경.
	 * 
	 * @param curViewBound
	 *          current bounds.
	 * @param curViewWidth
	 *          width of the view.
	 */
	@Override
	protected void clipViewOnTheLeft(Rect curViewBound, float curViewWidth, int left) {
		float clipPadding = curViewWidth * getClipPadding() / 100f;
		curViewBound.left = (int) (left + clipPadding);
		curViewBound.right = (int) (clipPadding + curViewWidth);
		//Log.e("TitlePageIndicator2", "clipViewOnTheLeft()" + getWidth() + " : " + curViewBound.right);
	}

}
