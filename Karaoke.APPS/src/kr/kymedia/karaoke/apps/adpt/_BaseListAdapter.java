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
 * <p>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p>
 * project	:	Karaoke.KPOP
 * filename	:	KPItemListAdapter.java
 * author	:	isyoon
 * <p>
 * <pre>
 * kr.kymedia.karaoke.kpop.data
 *    |_ KPItemListAdapter.java
 * </pre>
 */
package kr.kymedia.karaoke.apps.adpt;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import is.yuun.com.example.android.displayingbitmaps.util.ImageWorker;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageButton;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget._ImageViewFocused;
import kr.kymedia.karaoke.widget._ImageViewRounded;
import kr.kymedia.karaoke.widget.util.IImageDownLoader;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * <pre>
 * </pre>
 *
 * @author isyoon
 * @since 2012. 3. 7.
 * @version 1.0
 */
public class _BaseListAdapter extends ArrayAdapter<KPItem> {
	String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String text = String.format("%s()", name);
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// text = String.format("line:%d - %s() ", line, name);
		return text;
	}

	// 멤버변수로 해제할 Set을 생성
	private final List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();

	protected void addRecycleViews(ViewGroup convertView) {
		try {
			for (int i = 0; i < convertView.getChildCount(); i++) {
				View v = convertView.getChildAt(i);
				if (v instanceof _ImageView) {
					mRecycleList.add(new WeakReference<View>(v));
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void delRecycleViews() {
		try {
			for (WeakReference<View> ref : mRecycleList) {
				ImageWorker.cancelWork((_ImageView) ref.get());
				((_ImageView) ref.get()).setImageDrawable(null);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// protected Context context;
	protected LayoutInflater layoutInflater;
	protected int resource;
	protected List<KPItem> items;
	protected OnClickListener clistener;
	protected OnLongClickListener llistener;
	// Drawable ic_menu_01;
	final private IImageDownLoader imageDownLoader;

	public void putURLImage(final Context context, final ImageView v, final String url, final boolean resize, final int imageRes) {
		try {
			if (imageDownLoader != null) {
				imageDownLoader.putURLImage(context, v, url, resize, imageRes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public _BaseListAdapter(Context context, int resource, List<KPItem> items,
	// OnClickListener clistener) {
	// super(context, resource, items);
	// __CLASSNAME__ = this.getClass().getSimpleName();
	//
	// //this.context = context;
	// this.layoutInflater = LayoutInflater.from(context);
	// this.resource = resource;
	// this.items = items;
	// this.clistener = clistener;
	// this.imageDownLoader = null;
	// }

	public _BaseListAdapter(Context context, int resource, List<KPItem> items, OnClickListener clistener, IImageDownLoader imageDownLoader) {
		super(context, resource, items);
		__CLASSNAME__ = this.getClass().getSimpleName();

		// this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.resource = resource;
		this.items = items;
		this.clistener = clistener;
		this.imageDownLoader = imageDownLoader;
	}

	public void release() {
		try {
			clear();

			this.layoutInflater = null;
			if (this.items != null) {
				items.clear();
			}
			this.items = null;
			this.clistener = null;
			this.llistener = null;

			delRecycleViews();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		release();
	}

	/**
	 * used to keep selected position in ListView
	 */
	private int selectedPos = -1; // init value for not-selected

	/**
	 *
	 */
	public void setSelectedPosition(int pos) {
		selectedPos = pos;
		// inform the view of this change
		notifyDataSetChanged();
	}

	/**
	 *
	 */
	public int getSelectedPosition() {
		return selectedPos;
	}

	/**
	 * Move selected item "up" in the ViewList.
	 */
	public void moveUp() {
		int selectedPos = getSelectedPosition();
		if (selectedPos > 0) {
			KPItem item = items.remove(selectedPos);
			items.add(selectedPos - 1, item);
			// set selected position in the adapter
			setSelectedPosition(selectedPos - 1);
		}
	}

	/**
	 * Move selected item "down" in the ViewList.
	 */
	public void moveDown() {
		int selectedPos = getSelectedPosition();
		if (selectedPos < items.size() - 1) {
			KPItem item = items.remove(selectedPos);
			items.add(selectedPos + 1, item);
			// set selected position in the adapter
			setSelectedPosition(selectedPos + 1);
		}
	}

	protected void putValue(View v, CharSequence value) {
		if (v == null) {
			return;
		}

		if (v instanceof TextView) {
			putText((TextView) v, value);
		} else if (v instanceof _ImageView) {
		}
	}

	protected void putText(TextView v, CharSequence value) {
		if (v == null) {
			return;
		}
		if (!TextUtil.isEmpty(value)) {
			v.setText(value);
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}
	}

	protected void putHtml(TextView v, CharSequence value) {
		if (v == null) {
			return;
		}
		if (!TextUtil.isEmpty(value)) {
			String text = value.toString();
			if (TextUtil.retrieveLinks(text).size() > 0) {
				v.setMovementMethod(LinkMovementMethod.getInstance());
				v.setText(Html.fromHtml(text));
			} else {
				v.setText(text);
			}
			v.setVisibility(View.VISIBLE);
		} else {
			v.setVisibility(View.GONE);
		}
	}

	// HashMap<String, Integer> alphaIndexer = new HashMap<String, Integer>();
	// String[] sections;
	//
	// /**
	// * 리스트섹션기능사용
	// */
	// public int initSections(String name, boolean sort) {
	// int ret = 0;
	// if (sort) {
	// // 정렬로사용하는경우
	// // elements
	// // here is the tricky stuff
	// alphaIndexer = new HashMap<String, Integer>();
	// // in this hashmap we will store here the positions for
	// // the sections
	//
	// int size = items.size();
	// //for (int i = size - 1; i >= 0; i--) {
	// // String element = items.get(i).getString(name);
	// // if (element != null) {
	// // alphaIndexer.put(element.substring(0, 1), i);
	// // }
	// // //We store the first letter of the word, and its index.
	// // //The Hashmap will replace the value for identical keys are putted in
	// //}
	// for (int i = 0; i < size; i++) {
	// String element = items.get(i).getString(name);
	// if (element != null) {
	// alphaIndexer.put(element.substring(0, 1), i);
	// }
	// //We store the first letter of the word, and its index.
	// //The Hashmap will replace the value for identical keys are putted in
	// }
	//
	// // now we have an hashmap containing for each first-letter
	// // sections(key), the index(value) in where this sections begins
	//
	// // we have now to build the sections(letters to be displayed)
	// // array .it must contains the keys, and must (I do so...) be
	// // ordered alphabetically
	//
	// Set<String> keys = alphaIndexer.keySet(); // set of letters ...sets
	// // cannot be sorted...
	//
	// Iterator<String> it = keys.iterator();
	// ArrayList<String> keyList = new ArrayList<String>(); // list can be
	// // sorted
	//
	// while (it.hasNext()) {
	// String key = it.next();
	// keyList.add(key);
	// }
	//
	// Collections.sort(keyList);
	//
	// sections = new String[keyList.size()]; // simple conversion to an array of object
	// keyList.toArray(sections);
	//
	// // ooOO00K !
	// } else {
	// // 비정렬로사용하는경우
	// ArrayList<String> elements = new ArrayList<String>();
	// for (int i = 0; i < items.size(); i++) {
	// String element = items.get(i).getString(name);
	// if (element != null && element.length() > 1 && element.substring(0, 1) != null) {
	// elements.add(element.substring(0, 1));
	// } else {
	// elements.add("");
	// }
	// }
	// sections = new String[items.size()]; // simple conversion to an array of object
	// elements.toArray(sections);
	// }
	// return ret;
	// }
	//
	// @Override
	// public int getPositionForSection(int section) {
	//
	// int ret = 0;
	// if (sections != null && section < sections.length) {
	// String letter = sections[section];
	// if (alphaIndexer != null && alphaIndexer.size() > 0) {
	// ret = alphaIndexer.get(letter);
	// } else {
	// ret = section;
	// }
	// ret = section;
	// }
	// //KPnnnnItem item = items.get(section);
	// //if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + section + "," + letter + "," + ret);
	// return ret;
	// }
	//
	// @Override
	// public int getSectionForPosition(int position) {
	//
	// //if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + position);
	// return 0;
	// }
	//
	// @Override
	// public Object[] getSections() {
	//
	// //if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName());
	// return sections; // to string will be called each object, to display the letter
	// }

	protected View[] getChildViews(ViewGroup group) {
		int childCount = group.getChildCount();
		final View[] childViews = new View[childCount];

		for (int i = 0; i < childCount; i++) {
			childViews[i] = group.getChildAt(i);
		}

		return childViews;
	}

	/**
	 *
	 * @param group
	 * @param listener
	 */
	void setOnListItemClickListener(ViewGroup group, OnClickListener listener) {
		View[] childViews = getChildViews(group);
		for (View view : childViews) {

			// String name = getResourceEntryName(view.getId());
			// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + name + ", " + view.getId() + ", "
			// + view.getClass().getSimpleName());

			try {

				// if (view instanceof Button)
				// view.setOnClickListener(this);
				// else if (view instanceof ViewGroup)
				// setOnClickListener((ViewGroup) view);

				if (view instanceof ViewGroup) {
					setOnListItemClickListener((ViewGroup) view, listener);
				} else {
					setOnListItemClickListener(view, listener);
				}

			} catch (Exception e) {

				// //e.printStackTrace();
			}

		}
	}

	/**
	 * <b>stackoverflow - android - OnItemClickListener not triggering on custom ListView</b> <br>
	 * The reason your view isn't accepting the event is because the TextView (which is focusable) is
	 * overriding<br>
	 * it and taking all touch/click action for itself.<br>
	 * You just have to set these for the TextView:<br>
	 * <br>
	 * <b>android:focusable="false"</b><br>
	 * <b>android:focusableInTouchMode="false"</b><br>
	 *
	 * @see <a href="http://stackoverflow.com/questions/13081982/onitemclicklistener-not-triggering-on-custom-listview">stackoverflow - android - OnItemClickListener not triggering on custom ListView</a>
	 */
	void setOnListItemClickListener(View v, OnClickListener listener) {
		boolean clickable = true;
		boolean focusable = false;

		// 인스턴스는 상속역순으로 체크하는게 중요!!!
		if (v instanceof CheckBox) {
		} else if (v instanceof Button) {
		} else if (v instanceof EditText) {
		} else if (v instanceof TextView) {
			clickable = false;
		} else if (v instanceof _ImageButton) {
		} else if (v instanceof _ImageViewRounded) {
		} else if (v instanceof _ImageViewFocused) {
			//} else if (v instanceof _ImageView) {
		} else if (v instanceof View) {
			clickable = false;
		}

		v.setFocusable(focusable);
		v.setFocusableInTouchMode(focusable);

		v.setClickable(clickable);
		if (v.isClickable()) {
			if (listener != null) {
				v.setOnClickListener(listener);
			} else {
				v.setClickable(false);
			}
		}
	}

	/**
	 * 선택 리스트 배경처리 선택 타이틀 마퀴처리
	 *
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 클릭이벤트등록
		setOnListItemClickListener((ViewGroup) convertView, this.clistener);

		// change the row color based on selected state
		View v = null;
		int res = 0;
		boolean bold = false;

		if (selectedPos == position) {
			// res = android.R.drawable.list_selector_background;
			res = WidgetUtils.getDrawableID(getContext(), "list_selector_background_transition_light");
			bold = true;
		} else {
			res = android.R.color.transparent;
			// res = WidgetUtils.getDrawableID(getContext(), "list_selector_default");
			bold = false;
		}

		if (res > 0) {
			convertView.setBackgroundResource(res);
		}

		v = convertView.findViewById(WidgetUtils.getResourceID(getContext(), "title"));
		if (v != null) {
			WidgetUtils.setTypeFaceBold((TextView) v, bold);
		}

		v = convertView.findViewById(WidgetUtils.getResourceID(getContext(), "id"));
		if (v != null) {
			WidgetUtils.setTypeFaceBold((TextView) v, bold);
		}

		v = convertView.findViewById(WidgetUtils.getResourceID(getContext(), "name"));
		if (v != null) {
			WidgetUtils.setTypeFaceBold((TextView) v, bold);
		}

		addRecycleViews((ViewGroup) convertView);

		return convertView;
	}

}
