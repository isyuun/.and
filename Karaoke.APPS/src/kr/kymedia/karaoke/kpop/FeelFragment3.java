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
 * filename	:	FeelFragment2.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ FeelFragment2.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import is.yuun.com.kpbird.chipsedittextlibrary.ChipsAdapter;
import is.yuun.com.kpbird.chipsedittextlibrary.ChipsItem;
import is.yuun.com.kpbird.chipsedittextlibrary.ChipsMultiAutoCompleteTextview;
import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke._IKaraoke.LOGIN;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.app.html.HtmlBubble;
import kr.kymedia.karaoke.apps.R;
import kr.kymedia.karaoke.util.TextUtil;

/**
 * <pre>
 * 필작성푸시보내기
 * </pre>
 *
 * @author isyoon
 * @since 2013. 9. 10.
 * @version 1.0
 * @see
 */
public class FeelFragment3 extends FeelFragment2 {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	ArrayList<KPItem> mSelectedKPItemList = new ArrayList<KPItem>();
	ChipsMultiAutoCompleteTextview mRecipientReplyText;
	ArrayList<ChipsItem> mChipsItems = new ArrayList<ChipsItem>();
	ChipsAdapter mChipsItemsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_feel3, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	protected void onActivityCreated() {

		super.onActivityCreated();

		mRecipientReplyText = (ChipsMultiAutoCompleteTextview) findViewById(R.id.edt_reply_text);
		mChipsItemsAdapter = new ChipsAdapter(mContext, mChipsItems);
		mRecipientReplyText.setAdapter(mChipsItemsAdapter);
	}

	@Override
	protected void KP6002() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());


		super.KP6002();

		KPItem list = KP_6002.getList(0);

		View v = findViewById(R.id.include_reply);
		if (isLoginUser()) {
			if (v != null) {
				v.setVisibility(View.VISIBLE);
			}
		} else {
			if (v != null) {
				v.setVisibility(View.GONE);
			}
		}

		try {
			final String fimg = list.getValue("url_comment");
			final String furl = list.getValue("furl");
			final String fhtm = list.getValue("fhtm");

			v = findViewById(R.id.include_feel_html);
			if (v != null) {
				if (!TextUtil.isEmpty(fhtm)) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + fhtm);

					((TextView) v.findViewById(R.id.html_html)).setText(Html.fromHtml(fhtm));
					putURLImage(mContext, (ImageView) v.findViewById(R.id.html_image), fimg, false, R.drawable.bg_trans);

					v.setVisibility(View.VISIBLE);
					// if (v.findViewById(R.id.html_title) != null) {
					// v.findViewById(R.id.html_title).setVisibility(View.GONE);
					// }
					// if (v.findViewById(R.id.html_text) != null) {
					// v.findViewById(R.id.html_text).setVisibility(View.GONE);
					// }
					if (v.findViewById(R.id.html_html) != null) {
						v.findViewById(R.id.html_html).setVisibility(View.VISIBLE);
					}
					if (v.findViewById(R.id.html_image) != null) {
						v.findViewById(R.id.html_image).setVisibility(View.VISIBLE);
					}
					if (v.findViewById(R.id.html_progress) != null) {
						v.findViewById(R.id.html_progress).setVisibility(View.GONE);
					}
				} else if (TextUtil.isNetworkUrl(furl)) {
					if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + furl);

					v.setVisibility(View.VISIBLE);
					// if (v.findViewById(R.id.html_title) != null) {
					// v.findViewById(R.id.html_title).setVisibility(View.GONE);
					// }
					// if (v.findViewById(R.id.html_text) != null) {
					// v.findViewById(R.id.html_text).setVisibility(View.GONE);
					// }
					if (v.findViewById(R.id.html_html) != null) {
						v.findViewById(R.id.html_html).setVisibility(View.VISIBLE);
					}
					if (v.findViewById(R.id.html_image) != null) {
						v.findViewById(R.id.html_image).setVisibility(View.VISIBLE);
					}
					if (v.findViewById(R.id.html_progress) != null) {
						v.findViewById(R.id.html_progress).setVisibility(View.VISIBLE);
					}

					postDelayed(new Runnable() {

						@Override
						public void run() {

							(new parseHtml()).execute(furl);

						}
					}, 500);
				} else {
					v.setVisibility(View.GONE);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	protected HtmlBubble parseHtml(String body, String url, String type) {

		return super.parseHtml(body, url, type);
	}

	@Override
	protected void KP2017() {

		super.KP2017();

		addChipsItems();

	}

	void addChipsItems() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		mChipsItems.clear();

		for (KPItem item : KP_2017.getLists()) {
			String uid = item.getValue("uid");
			String name = item.getValue(LOGIN.KEY_NICKNAME);
			ChipsItem chipsItem = new ChipsItem(uid, name);
			mChipsItems.remove(chipsItem);
			mChipsItems.add(chipsItem);
		}

		mChipsItemsAdapter.notifyDataSetChanged();

		mRecipientReplyText.clearRecipientChipsItems();
		mRecipientReplyText.setRecipients();
	}

	void chkRecipientChipsItem() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		ArrayList<KPItem> items = new ArrayList<KPItem>();

		for (KPItem item : mSelectedKPItemList) {
			String uid = item.getValue("uid");

			boolean exit = false;
			for (ChipsItem chipsItem : mRecipientReplyText.getRecipientChipsItems()) {
				if (uid.equalsIgnoreCase(chipsItem.getId())) {
					exit = true;
					break;
				}
			}

			if (!exit) {
				items.add(item);
			}
		}

		for (KPItem item : items) {
			mSelectedKPItemList.remove(item);
		}
	}

	void addRecipientChipsItem(KPItem list) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		try {
			if (list == null) {
				return;
			}

			// 본인금지.
			if (getApp().p_mid.equalsIgnoreCase(list.getValue("uid"))) {
				return;
			}

			// ArrayList<KPItem> items = new ArrayList<KPItem>();
			// if (mSelectedKPItemList.contains(list)) {
			// mSelectedKPItemList.remove(list);
			// } else {
			// //중복사용자제거
			// for (KPItem item : mSelectedKPItemList) {
			// String uid = item.getString("uid");
			//
			// if (uid.equalsIgnoreCase(list.getString("uid"))) {
			// items.add(item);
			// }
			// }
			//
			// for (KPItem item : items) {
			// mSelectedKPItemList.remove(item);
			// }
			//
			// mSelectedKPItemList.add(list);
			// }

			// 중복사용자제거
			boolean exist = false;
			for (KPItem item : mSelectedKPItemList) {
				String uid = item.getValue("uid");

				if (uid.equalsIgnoreCase(list.getValue("uid"))) {
					exist = true;
				}
			}

			if (exist) {
				if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + exist);
				return;
			}

			mSelectedKPItemList.add(list);

			mRecipientReplyText.clearRecipientChipsItems();

			for (KPItem item : mSelectedKPItemList) {
				String uid = item.getValue("uid");
				String name = item.getValue(LOGIN.KEY_NICKNAME);
				// if (_IKaraoke.DEBUG) Log.e(__CLASSNAME__, getMethodName() + "KPItem -> " + uid + "," + name);
				ChipsItem chipItem = new ChipsItem(uid, name);
				mRecipientReplyText.addRecipientChipsItem(chipItem);
			}

			mRecipientReplyText.setRecipients();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		String nm = getResourceEntryName(v.getId());

		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_index + " - " + vn + ", " + nm + "," + cn);

		super.onItemClick(parent, v, position, id);

		KPItem list = KP_2017.getList(KP_index);

		chkRecipientChipsItem();
		addRecipientChipsItem(list);
	}

	@Override
	protected void KP_2018(String siren_code) {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName());

		// super.KP_2018(siren_code);

		String comment = mRecipientReplyText.getText().toString();
		comment = mRecipientReplyText.getMessageText();

		if (TextUtil.isEmpty(comment)) {
			return;
		}

		getApp().hideSoftInput(mRecipientReplyText);

		String seq = "";

		KPItem list = KP_2017.getList(KP_index);

		if (list != null) {
			seq = list.getValue("seq");
		} else {
			reply_mode = "ADD";
			seq = "";
		}

		String uid = "";
		for (ChipsItem chipsItem : mRecipientReplyText.getRecipientChipsItems()) {
			uid += chipsItem.getId() + ",";
			if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + "chipsItem -> " + uid);
		}

		if (!TextUtil.isEmpty(play_id)) {
			KP_2018.KP_2018(getApp().p_mid, p_m1, p_m2, play_id, reply_mode, seq, siren_code, comment, uid);
		}

		mRecipientReplyText.setText("");
	}

	@Override
	public void release() {

		try {
			super.release();
			if (mChipsItems != null) {
				mChipsItems.clear();
			}
			mChipsItems = null;
			if (mChipsItemsAdapter != null) {
				mChipsItemsAdapter.release();
			}
			mChipsItemsAdapter = null;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
