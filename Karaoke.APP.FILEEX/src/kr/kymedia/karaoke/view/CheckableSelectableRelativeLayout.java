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
 * 2014 All rights (c)KYmedia Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYmedia Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.FileExplorer
 * filename	:	CheckRelativeLayout.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.view
 *    |_ CheckRelativeLayout.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * TODO<br>
 * <pre></pre>
 *
 * @author	isyoon
 * @since	2014. 9. 4.
 * @version 1.0
 */
public class CheckableSelectableRelativeLayout extends CheckableRelativeLayoutBase {

	public CheckableSelectableRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CheckableSelectableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CheckableSelectableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		super.onLayout(changed, l, t, r, b);
	}

	boolean isSelectable = false;

	public boolean isSelectable() {
		return isSelectable;
	}

	public void setSelectable(boolean isSelectable) {
		//Log.w(toString(), getMethodName() + isSelectable);

		this.isSelectable = isSelectable;
		if (c != null) {
			if (isSelectable) {
				c.setVisibility(View.VISIBLE);
			} else {
				c.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void setSelected(boolean selected) {
		//Log.w(toString(), getMethodName() + selected);

		if (isSelectable) {
			super.setSelected(isChecked());
			//super.setChecked(isChecked());
		} else {
			super.setSelected(false);
			//super.setChecked(false);
		}
	}

	@Override
	public void setChecked(boolean isChecked) {
		//Log.w(toString(), getMethodName() + isChecked);

		super.setChecked(isChecked);
	}

}
