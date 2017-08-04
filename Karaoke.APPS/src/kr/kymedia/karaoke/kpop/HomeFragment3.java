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
 * project	:	Karaoke.KPOP
 * filename	:	HomeFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ HomeFragment.java
 * </pre>
 *
 */

package kr.kymedia.karaoke.kpop;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget._ImageViewRounded;

//import kr.kymedia.karaoke.widget.ImageView;

/**
 *
 * TODO
 * NOTE:그리드레이아웃 타일스타일 <br>
 *
 * @author isyoon
 * @since 2012. 2. 28.
 * @version 1.0
 */
class HomeFragment3 extends HomeFragment implements OnClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	int width = 0;
	int height = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_home3, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {

		super.onConfigurationChanged(newConfig);

		width = getView().getWidth();
		height = getView().getHeight();

		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + "width:" + width + ", height:" + height);
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message,
			String r_info) {


		KPnnnnResult(what, p_opcode, r_code, r_message, r_info);

		if (what != _IKaraoke.STATE_DATA_QUERY_START) {
			// KP_0003 동적메인메뉴 요청
			if (("KP_0003").equalsIgnoreCase(p_opcode)) {

				KPItem info = KP_nnnn.getInfo();

				putLogin(info);

				if (info == null) {
					info = new KPItem();
					KP_nnnn.setInfo(info);
				}

				KPnnnn();

				if (what == _IKaraoke.STATE_DATA_QUERY_SUCCESS && ("00000").equalsIgnoreCase(r_code)) {
				}
			}
		}

	}

	class RowCol {
		SparseBooleanArray map = new SparseBooleanArray();

		int nx = 4;
		int ny = 6;

		RowCol(int nx, int ny) {
			this.nx = nx;
			this.ny = ny;
			map.clear();
		}

		int getNum(int x, int y) {
			int num = x + (y * this.nx);
			return num;
		}

		void put(int x, int y) {
			int num = getNum(x, y);
			map.put(num, true);
			// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, x + "," + y + "-" + num);
			if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, "num:" + num);
		}

		void put(int x, int y, int rx, int ry) {
			for (int i = 0; i < rx; i++) {
				for (int j = 0; j < ry; j++) {
					put(x + i, y + j);
				}
			}
		}

		boolean get(int x, int y) {
			int num = getNum(x, y);
			boolean ret = false;
			try {
				ret = map.get(num);
			} catch (Exception e) {

				// e.printStackTrace();
			}
			return ret;
		}

		boolean get(int x, int y, int rx, int ry) {
			boolean ret = false;

			if (x + rx > nx) {
				return true;
			}

			for (int i = 0; i < rx; i++) {
				if (ret) {
					break;
				}
				for (int j = 0; j < ry; j++) {
					ret = get(x + i, y + j);
					if (ret) {
						break;
					}
				}
			}

			return ret;
		}

	}

	class GridView {
		int index;
		int num;
		int col;
		int row;
		int colSpan;
		int rowSpan;

		public GridView(int index, int num, int col, int row, int colSpan, int rowSpan) {
			super();
			this.index = index;
			this.num = num;
			this.col = col;
			this.row = row;
			this.colSpan = colSpan;
			this.rowSpan = rowSpan;
		}

	}

	RowCol rowCol;
	int pagerowcount = 6;
	int pagecolcount = 4;
	ArrayList<GridView> gridViews = new ArrayList<HomeFragment3.GridView>();

	public void putGridViewTiles() {
		KPItem info = KP_nnnn.getInfo();
		if (info != null) {
			pagerowcount = !TextUtil.isEmpty(info.getValue("pagerowcount"))
					&& TextUtils.isDigitsOnly(info.getValue("pagerowcount")) ? Integer.parseInt(info
					.getValue("pagerowcount")) : pagerowcount;
			pagecolcount = !TextUtil.isEmpty(info.getValue("pagecolcount"))
					&& TextUtils.isDigitsOnly(info.getValue("pagecolcount")) ? Integer.parseInt(info
					.getValue("pagecolcount")) : pagecolcount;
		}

		rowCol = new RowCol(pagecolcount, pagerowcount);

		width = getView().getWidth();
		height = getView().getHeight();

		GridLayout grid = (GridLayout) findViewById(R.id.grid);
		grid.removeAllViews();

		gridViews.clear();

		int row = 0;
		int col = 0;

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		String value = "";
		View v = null;

		for (int i = 0; i < KP_nnnn.getListCount(); i++) {

			KPItem item = KP_nnnn.getList(i);

			if (item == null) {
				continue;
			}

			View view = inflater.inflate(R.layout.item_home, (ViewGroup) getView(), false);

			if (view == null) {
				continue;
			}

			row = i / pagecolcount;
			col = i % pagecolcount;

			int rowSpan = !TextUtil.isEmpty(item.getValue("rowspan"))
					&& TextUtils.isDigitsOnly(item.getValue("rowspan")) ? Integer.parseInt(item
					.getValue("rowspan")) : 1;
			if (rowSpan > pagerowcount) {
				rowSpan = 1;
			}

			int colSpan = !TextUtil.isEmpty(item.getValue("colspan"))
					&& TextUtils.isDigitsOnly(item.getValue("colspan")) ? Integer.parseInt(item
					.getValue("colspan")) : 1;
			if (colSpan > pagecolcount) {
				colSpan = 1;
			}

			// test
			// rowSpan = (int) (Math.random() * (rowCount / 3)) + 1;
			// colSpan = (int) (Math.random() * (colCount / 2)) + 1;

			int index = 0;
			while (rowCol.get(col, row, colSpan, rowSpan)) {
				index++;
				col = index % pagecolcount;
				if (index / pagecolcount - row == 1) {
					row = index / pagecolcount;
				}
			}

			rowCol.put(col, row, colSpan, rowSpan);

			GridLayout.Spec rowSpec = GridLayout.spec(row, rowSpan, GridLayout.FILL);
			GridLayout.Spec colSpec = GridLayout.spec(col, colSpan, GridLayout.FILL);
			grid.addView(view, new GridLayout.LayoutParams(rowSpec, colSpec));

			v = view.findViewById(R.id.image);
			if (v != null) {
				if (URLUtil.isNetworkUrl(item.getValue("url_profile"))) {
					value = item.getValue("url_profile");
					((_ImageViewRounded) v).setScaleType(ScaleType.CENTER_CROP);
				} else {
					value = item.getValue("url_img");
					((_ImageViewRounded) v).setScaleType(ScaleType.FIT_XY);
				}

				int res = R.anim.slide_in_left;
				switch ((int) (Math.random() * 4) + 1) {
				case 1:
					res = R.anim.slide_in_left;
					break;

				case 2:
					res = R.anim.slide_in_right_self;
					break;

				case 3:
					res = R.anim.slide_in_from_bottom;
					break;

				case 4:
					res = R.anim.slide_in_from_top;
					break;

				default:
					break;
				}
				Animation ani = AnimationUtils.loadAnimation(getApp(), res);
				v.setAnimation(ani);

				putURLImage(mContext, (ImageView) v, value, false, 0);

				final int click = i;

				v.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						KP_index = click;
						HomeFragment3.this.onClick(v);
					}
				});
			}

			if (view.findViewById(R.id.progress) != null) {
				view.findViewById(R.id.progress).setVisibility(View.GONE);
			}

			GridView gridView = new GridView(i, rowCol.getNum(col, row), col, row, colSpan, rowSpan);

			v = view.findViewById(R.id.index);
			if (v != null) {
				value = "";
				value += "idx:" + gridView.index;
				value += "\nnum:" + gridView.num;
				value += "\nrow:" + gridView.row;
				value += "\ncol:" + gridView.col;
				value += "\nrowspan:" + gridView.rowSpan;
				value += "\ncolspan:" + gridView.colSpan;
				if (_IKaraoke.DEBUG) {
					putValue(v, value);
				} else {
					v.setVisibility(View.GONE);
				}
			}

			gridViews.add(gridView);

		}

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + grid.getColumnCount() + "," + grid.getRowCount());

		KPresize();
	}

	public void KPresize() {

		width = getView().getWidth();
		height = getView().getHeight();

		final int w = width / pagecolcount;
		final int h = height / pagerowcount;

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + pagecolcount + "," + pagerowcount);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + width + "," + height);
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + w + "," + h);

		GridLayout grid = (GridLayout) findViewById(R.id.grid);
		ScrollView scroll = (ScrollView) findViewById(R.id.scroll);

		scroll.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				boolean isNotScroll = (height >= pagerowcount * h);
				return isNotScroll;
			}
		});

		int row = 0;
		int col = 0;

		for (int i = 0; i < gridViews.size(); i++) {

			GridView gridView = gridViews.get(i);

			col = gridView.col + gridView.colSpan;
			row = gridView.row;

			int colSpan = gridView.colSpan;
			while (!rowCol.get(col, row, 1, 1) && col < grid.getColumnCount()) {
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + col + "," + row);
				colSpan++;
				col++;
			}
			gridView.colSpan = colSpan;

			col = gridView.col;
			row = gridView.row + gridView.rowSpan;

			int rowSpan = gridView.rowSpan;
			while (!rowCol.get(col, row, 1, 1) && row < grid.getRowCount()) {
				// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + col + "," + row);
				rowSpan++;
				row++;
			}
			gridView.rowSpan = rowSpan;

			rowCol.put(gridView.col, gridView.row, gridView.colSpan, gridView.rowSpan);

			View view = grid.getChildAt(i);
			GridLayout.LayoutParams param = (GridLayout.LayoutParams) view.getLayoutParams();
			param.rowSpec = GridLayout.spec(gridView.row, gridView.rowSpan, GridLayout.FILL);
			param.columnSpec = GridLayout.spec(gridView.col, gridView.colSpan, GridLayout.FILL);
			param.width = w * colSpan;
			param.height = h * rowSpan;
			view.setLayoutParams(param);

			// v = view.findViewById(R.id.index);
			// if (v != null) {
			// value = "";
			// value += "idx:" + gridView.index;
			// value += "\nnum:" + gridView.num;
			// value += "\nrow:" + gridView.row;
			// value += "\ncol:" + gridView.col;
			// value += "\nrowspan:" + gridView.rowSpan;
			// value += "\ncolspan:" + gridView.colSpan;
			// if (_IKaraoke.DEBUG) {
			// putValue(v, value);
			// } else {
			// v.setVisibility(View.GONE);
			// }
			// }

		}

	}

	@Override
	public void KP_nnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.KP_nnnn();
	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.refresh();
	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		super.KPnnnn();

		putGridViewTiles();
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public int getMenuTilesIndex(int id) {

		// return super.getMenuButtonIndex(id);
		int ret = KP_index;
		return ret;
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		KP_index = getMenuTilesIndex(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + KP_index + " " + vn + ", " + cn);


		super.onClick(v);
	}

	@Override
	public void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + isQueryOnResume);

		super.onResume();
		KPresize();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		if (gridViews != null) {
			gridViews.clear();
		}
		gridViews = null;
	}

}
