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
 * 2014 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	Karaoke.FileExplorer
 * filename	:	CheckableRelativeLayout.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.view
 *    |_ CheckableRelativeLayout.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/**
 *
 * TODO<br>
 * <pre></pre>
 *
 * @author	isyoon
 * @since	2014. 9. 4.
 * @version 1.0
 */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();
	@Override
	public String toString() {

		//return super.toString();
		return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
	}

	protected static String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		//int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		//name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	private boolean isChecked;
	private List<Checkable> checkableViews;

	public List<Checkable> getCheckableViews() {
		return checkableViews;
	}

	public CheckableRelativeLayout(Context context) {
		super(context);
		initialise(null);
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialise(attrs);
	}

	public CheckableRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise(attrs);
	}

	public CheckableRelativeLayout(Context context, int checkableId) {
		super(context);
		initialise(null);
	}

	/*
	  * @see android.widget.Checkable#isChecked()
	  */
	@Override
	public boolean isChecked() {
		return isChecked;
	}

	/*
	  * @see android.widget.Checkable#setChecked(boolean)
	  */
	@Override
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		for (Checkable c : checkableViews) {
			c.setChecked(isChecked);
		}
	}

	/*
	  * @see android.widget.Checkable#toggle()
	  */
	@Override
	public void toggle() {
		this.isChecked = !this.isChecked;
		for (Checkable c : checkableViews) {
			c.toggle();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		final int childCount = this.getChildCount();
		for (int i = 0; i < childCount; ++i) {
			findCheckableChildren(this.getChildAt(i));
		}
	}

	/**
	 * Read the custom XML attributes
	 */
	protected void initialise(AttributeSet attrs) {
		this.isChecked = false;
		this.checkableViews = new ArrayList<Checkable>(5);
	}

	/**
	 * Add to our checkable list all the children of the view that implement the
	 * interface Checkable
	 */
	private void findCheckableChildren(View v) {
		if (v instanceof Checkable) {
			this.checkableViews.add((Checkable) v);
		}

		if (v instanceof ViewGroup) {
			final ViewGroup vg = (ViewGroup) v;
			final int childCount = vg.getChildCount();
			for (int i = 0; i < childCount; ++i) {
				findCheckableChildren(vg.getChildAt(i));
			}
		}
	}
}
