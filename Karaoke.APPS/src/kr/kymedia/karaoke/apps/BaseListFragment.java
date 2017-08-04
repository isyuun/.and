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
 * filename	:	BaseFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps.adpt._BaseListAdapter;
import kr.kymedia.karaoke.apps.impl.IBaseFragment;
import kr.kymedia.karaoke.apps.impl.IBaseListFragment;
import kr.kymedia.karaoke.util.TextUtil;
import kr.kymedia.karaoke.widget.util.WidgetUtils;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 2. 24.
 * @version 1.0
 */
class BaseListFragment extends _BaseFragment implements IBaseFragment, IBaseListFragment, OnScrollListener, OnItemClickListener, OnItemLongClickListener {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	BaseListFragment() {
		super();
	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

		onCreateView();

		return mRootView;
	}

	protected int mTotalCount = 0;
	protected int total_page = 0;
	protected int page = 1;

	private _BaseListAdapter mBaseListAdapter = null;

	protected _BaseListAdapter getBaseListAdapter() {
		return mBaseListAdapter;
	}

	private ListView mListView = null;
	protected View mHeaderView = null;
	protected View mFooterView = null;

	/**
	 * @return the mListView
	 */
	public ListView getListView() {
		return (ListView) mListView;
	}

	/**
	 * @param listView
	 *          the mListView to set
	 */
	public void setListView(ListView listView) {
		this.mListView = listView;
	}

	/**
	 * 
	 * @param position
	 */
	public void setSelection(int position) {

	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		KP_list(1);
	}

	@Override
	protected void start() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.start();

		if (getBaseActivity() != null && KP_nnnn.getLists() != null) {
			new Runnable() {
				public void run() {
					setListAdapter();
				}
			}.run();
		}

	}

	@Override
	public void onResume() {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + ":" + isQueryOnManual + "," + isQueryOnActivityCreate);


		super.onResume();

		if (mListView != null) {
			registerForContextMenu(mListView);
		}

	}

	public void clear() {
		if (mBaseListAdapter != null) {
			mBaseListAdapter.clear();
			mBaseListAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 
	 * 
	 * @see BaseFragment2#onPause()
	 */
	@Override
	public void onPause() {
		if (_IKaraoke.DEBUG) Log2.i(__CLASSNAME__, getMethodName());

		super.onPause();

		if (mListView != null) {
			unregisterForContextMenu(mListView);
		}

	}

	/**
	 * <pre>
	 * TODO
	 * </pre>
	 * 
	 */
	@Override
	public void onDestroy() {

		super.onDestroy();

		if (mListView != null) {
			mListView.setAdapter(null);
		}
		mListView = null;

		if (mHeaderView != null) {
			((ViewGroup) mHeaderView).removeAllViews();
		}
		mHeaderView = null;

		if (mFooterView != null) {
			((ViewGroup) mFooterView).removeAllViews();
		}
		mFooterView = null;

		if (mBaseListAdapter != null) {
			mBaseListAdapter.release();
		}
		mBaseListAdapter = null;

	}

	/**
	 * KP_nnnn 조회함수(목록)<br>
	 * KP_list(int)를 호출한다.<br>
	 * 
	 */
	// public void KP_list() {
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName());
	//
	// //if (mListView != null && _IKaraoke.DEBUG) {
	// // mListView.setBackgroundColor(getResources().getColor(R.color.solid_pink));
	// //}
	//
	// if (KP_nnnn != null) {
	// String log = getMethodName() + "->" + KP_nnnn.getListCount();
	// if (mBaseListAdapter != null) {
	// log += "->" + mBaseListAdapter.getCount();
	// }
	// if (mListView != null) {
	// log += "->" + mListView.getCount();
	// }
	//
	// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, log);
	// }
	//
	// if (mBaseListAdapter != null) {
	// if (mBaseListAdapter.getCount() == 0) {
	// KP_list(1);
	// } else {
	// mBaseListAdapter.notifyDataSetChanged();
	// stopLoading(__CLASSNAME__, getMethodName());
	// }
	// }
	//
	// }

	/**
	 * KP_list 조회함수(목록)<br>
	 * 전체카운트/페이지 저장한다.<br>
	 * 
	 */
	@Override
	public void KP_list(final int page) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + page + "[" + KP_nnnn + "]");


		mTotalCount = 0;
		this.page = page;

		if (page == 1) {
			clear();
		}
	}

	@Override
	public void onKPnnnnResult(int what, String p_opcode, String r_code, String r_message, String r_info) {

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + what + ", " + p_opcode + ", " + r_code + ", " + r_message);

		switch (what) {
		case _IKaraoke.STATE_DATA_QUERY_START:
			break;

		case _IKaraoke.STATE_DATA_QUERY_SUCCESS:
			break;

		case _IKaraoke.STATE_DATA_QUERY_FAIL:
			break;

		case _IKaraoke.STATE_DATA_QUERY_ERROR:
			break;

		case _IKaraoke.STATE_DATA_QUERY_CANCEL:
			break;

		default:
			break;
		}

		if (what != _IKaraoke.STATE_DATA_QUERY_START) {
			KPlist();
		}

		super.onKPnnnnResult(what, p_opcode, r_code, r_message, r_info);
	}

	/**
	 * 리스트어댑터정의호출합수
	 */
	@Override
	public void setListAdapter() {


	}

	// ProgressBar mListLoading;
	@Override
	public void setListAdapter(_BaseListAdapter listAdapter) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + listAdapter + ":" + mListView);

		mBaseListAdapter = listAdapter;

		mListView = (ListView) findViewById(R.id.list);

		if (mListView == null) {
			return;
		}

		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		// Footer
		mFooterView = inflate(R.layout.list_footer, null, false);
		if (mFooterView != null) {
			mListView.setFooterDividersEnabled(false);
			mListView.addFooterView(mFooterView);
			mFooterView.setVisibility(View.GONE);
			WidgetUtils.setOnClickListener(mContext, (ViewGroup) mFooterView, this, true);
		}

		// Header
		if (mHeaderView == null) {
			mHeaderView = inflate(R.layout.include_special_detail, null, false);
			mHeaderView.setId(R.id.include_special_detail);
			mHeaderView.findViewById(R.id.img_special).setVisibility(View.GONE);
		}

		if (mHeaderView != null) {
			mListView.addHeaderView(mHeaderView);
			mListView.setHeaderDividersEnabled(false);
			WidgetUtils.setOnClickListener(mContext, (ViewGroup) mHeaderView, this, true);
		}

		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);

		mListView.setAdapter(mBaseListAdapter);
		mListView.setFastScrollEnabled(true);
		if (_IKaraoke.IS_HONEYCOMB) {
			mListView.setFastScrollEnabled(false);
		}
		mListView.setEmptyView(findViewById(android.R.id.empty));
		mListView.setDivider(null);

		// Create a progress bar to display while the list loads
		// mListLoading = new ProgressBar(getBaseActivity());
		// mListLoading.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// mListLoading.setIndeterminate(true);
		// mListView.setEmptyView(mListLoading);

		if (mListView != null) {
			registerForContextMenu(mListView);
		}

		mBaseListAdapter.notifyDataSetChanged();
	}

	// @Override
	// protected void startLoading(boolean startLoading, String name, String method) {
	//
	// super.startLoading(startLoading, name, method);
	// //mListLoading.setIndeterminate(true);
	// showLoading(true);
	// }
	//
	// @Override
	// protected void stopLoading(boolean startLoading, String name, String method) {
	//
	// super.stopLoading(startLoading, name, method);
	// //mListLoading.setIndeterminate(false);
	// showLoading(false);
	// }
	//
	// /**
	// *
	// * @param shown
	// */
	// private void showLoading(boolean shown) {
	// if (getView() == null) {
	// return;
	// }
	//
	// if (findViewById(R.id.progress) != null) {
	// if (shown) {
	// findViewById(R.id.progress).setVisibility(View.VISIBLE);
	// } else {
	// findViewById(R.id.progress).setVisibility(View.GONE);
	// }
	// }
	// }

	/**
	 * 리스트조회결과처리
	 */
	@Override
	public void KPlist() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		KPlist(KP_nnnn.getInfo(), KP_nnnn.getLists());

		// if (mBaseListAdapter != null) {
		// mBaseListAdapter.initSections("title", false);
		// }
	}

	public void KPlist(KPItem info, ArrayList<KPItem> lists) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + info);

		try {
			// KPnnnnItem info = KP_nnnn.getInfo();
			// ArrayList<KPnnnnItem> lists = KP_nnnn.getLists();

			if (info == null) {
				return;
			}

			int total_page = 1;
			if (!TextUtil.isEmpty(info.getValue("total_page"))) {
				total_page = TextUtil.parseInt(info.getValue("total_page"));
			}

			this.total_page = total_page;

			String url_img = info.getValue("url_img");

			View v = null;

			if (mHeaderView != null) {
				v = mHeaderView.findViewById(R.id.img_special);
			}

			// Hiding Header Views
			// http://pivotallabs.com/users/joe/blog/articles/1759-android-tidbits-6-22-2011-hiding-header-views
			if (v != null) {
				v.setVisibility(View.GONE);
				putURLImage(mContext, (ImageView) v, url_img, false, R.drawable.bg_trans);
				if (URLUtil.isNetworkUrl(url_img)) {
					v.setVisibility(View.VISIBLE);
				} else {
					v.setVisibility(View.GONE);
				}
				// putURLImage(mContext, (ImageView) v, url_img, false);
			}

			if (lists == null) {
				return;
			}

			mTotalCount = lists.size();
			if (mTotalCount > 0) {
				mListView.setFooterDividersEnabled(true);
			} else {
				mListView.setFooterDividersEnabled(false);
			}

			if (total_page == 0 || total_page == page) {
				if (mListView.getFooterViewsCount() > 0) {
					mListView.removeFooterView(mFooterView);
				}
			}

			if (findViewById(R.id.include_list_header) != null) {
				String list_header = info.getValue("list_header_title");
				if (TextUtil.isEmpty(list_header)) {
					findViewById(R.id.include_list_header).setVisibility(View.GONE);
				} else {
					findViewById(R.id.include_list_header).setVisibility(View.VISIBLE);
					((TextView) findViewById(R.id.include_list_header).findViewById(R.id.title)).setText(list_header);
				}
			}

			if (mBaseListAdapter != null) {
				mBaseListAdapter.notifyDataSetChanged();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// if (_IKaraoke.DEBUG)Log.e(__CLASSNAME__, getMethodName() + page);


		int lastItemCount = firstVisibleItem + visibleItemCount;
		int footerCount = mListView.getFooterViewsCount();
		int headerCount = mListView.getHeaderViewsCount();

		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + page + "," + total_page + "," + lastItemCount + ","
		// + totalItemCount + "," + mTotalCount);

		if (mTotalCount > 0 && total_page > 0 && lastItemCount == totalItemCount) {
			if (page < total_page) {
				if (mTotalCount == totalItemCount - footerCount - headerCount) {
					if (mFooterView.getVisibility() != View.VISIBLE) {
						mFooterView.setVisibility(View.VISIBLE);
					}
					KP_list(page + 1);
				}
			}
			// else if (page == total_page) {
			// if (mTotalCount <= totalItemCount - mListView.getFooterViewsCount())
			// {
			// if (mListView.getFooterViewsCount() > 0) {
			// //mListView.removeFooterView(mFooterView);
			// }
			// }
			// }
		}

		// if (_IKaraoke.DEBUG)Log.i(__CLASSNAME__, getMethodName() + page + "," + total_page + ","
		// + lastItemCount + "," + totalItemCount + "," + mTotalCount);

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {


	}

	@Override
	protected boolean showContextMenu(View v, boolean isShowCenter, boolean isShowArrow) {

		boolean ret = false;
		try {
			if (v == null) {
				return ret;
			}
			// ContextMenu를 onCreateContextMenu에서 QuickActionMenu로 변환후
			ret = mListView.showContextMenu();
			// QuickActionMenu를 보인다.
			popupQuickActionContextMenu(mListView, isShowCenter, isShowArrow);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ret;
	}

	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	//
	// String vn = getResourceEntryName(v.getId());
	// String cn = v.getClass().getSimpleName();
	// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, getMethodName() + vn + ", " + cn + "\n" + event);
	// return super.onTouch(v, event);
	// }

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn + ", " + (v.getParent() instanceof ListView));


		super.onClick(v);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		String nm = getResourceEntryName(v.getId());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + nm + "," + cn);

		// 인덱스확인
		KP_index = position - mListView.getHeaderViewsCount();

		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + KP_index + " - " + vn + ", " + nm + "," + cn);
	}

	protected int getPositionForView(View view) {
		int ret = AdapterView.INVALID_POSITION;

		if (mListView == null) {
			return ret;
		}

		if (view == null) {
			return ret;
		}

		try {
			ret = mListView.getPositionForView(view);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return ret;
	}

	/**
	 * 플래그먼트 리스트 레이아웃 뷰클릭시
	 */
	@Override
	public void onListItemClick(View v) {

		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		String nm = getResourceEntryName(v.getId());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + vn + ", " + nm + "," + cn);

		if (mListView == null) {
			return;
		}

		// 인덱스확인
		int position = getPositionForView((View) v.getParent());
		if (position == AdapterView.INVALID_POSITION) {
			return;
		}

		KP_index = position - mListView.getHeaderViewsCount();

		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + KP_index + " - " + vn + ", " + nm + "," + cn);

		KPItem list = KP_nnnn.getList(KP_index);

		if (list == null) {
			getApp().showDataError(__CLASSNAME__, getMethodName(), "list", list);
			return;
		}



		int id = v.getId();
		if (id == R.id.movie) {
			openYouTube(KP_nnnn, KP_index, false, true);
		} else if (id == R.id.arrow || id == R.id.btn_info) {
			showContextMenu(mListView, true, false);
		} else if (id == R.id.image) {
			String uid = list.getValue("uid");
			if (!TextUtil.isEmpty(uid)) {
				onContextItemSelected(R.id.context_go_holic, KP_nnnn, KP_index, true);
			}
		} else {
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		String name = getResourceEntryName(view.getId());
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + name + "," + position + "," + id);

		// mListIndex = position;
		KP_index = position - mListView.getHeaderViewsCount();
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + name + "," + KP_index + "," + id);



		return true;
	}

	@Override
	public void refresh() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		page = 1;
		clear();
		super.refresh();
	}

}
