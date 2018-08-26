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
 * project	:	Karaoke.KPOP
 * filename	:	EulaFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop
 *    |_ EulaFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.kpop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.KPItem;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.apps._BaseFragment;
import kr.kymedia.karaoke.apps.R;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 4. 11.
 * @version 1.0
 */

public class EulaFragment extends _BaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	String article_id = "";
	String using_id = "";

	public EulaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRootView = (ViewGroup) inflater.inflate(R.layout.fragment_eula, container, false);

		onCreateView();

		return mRootView;
	}

	@Override
	public void KP_nnnn() {

		super.KP_nnnn();

		KP_nnnn.KP_5000(getApp().p_mid, p_m1, p_m2);
	}

	@Override
	public void KPnnnn() {
		if (_IKaraoke.DEBUG) Log2.e(__CLASSNAME__, getMethodName() + KP_nnnn);

		KPItem info = KP_nnnn.getInfo();

		String eula1 = info.getValue("article_contents");
		String eula2 = info.getValue("using_contents");

		TextView txtEula1 = (TextView) findViewById(R.id.txt_summary_eula1);
		TextView txtEula2 = (TextView) findViewById(R.id.txt_summary_eula2);

		txtEula1.setText(eula1);
		txtEula2.setText(eula2);

		super.KPnnnn();
	}

	@Override
	public void onClick(View v) {
		String vn = getResourceEntryName(v.getId());
		String cn = v.getClass().getSimpleName();
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + vn + ", " + cn);

		super.onClick(v);

		KPItem info = KP_nnnn.getInfo();
		if (info == null) {
			info = new KPItem();
			KP_nnnn.setInfo(info);
		}

		boolean check1 = ((CheckBox) findViewById(R.id.chk_hint_eula1)).isChecked();
		boolean check2 = ((CheckBox) findViewById(R.id.chk_hint_eula2)).isChecked();

		if (v.getId() == R.id.btn_login_next) {
			if (check1 && check2) {
				info.putValue("type", "register");
				startActivity(putIntentData(profile.class, null, KP_index));
				close();
			} else {

				Bundle args = new Bundle();
				String msg = "";

				if (!check1) {
					msg = getString(R.string.summary_setting_eula1);
					args.putString("message", msg);
					getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
					findViewById(R.id.chk_hint_eula1).requestFocus();
				} else if (!check2) {
					msg = getString(R.string.summary_setting_eula2);
					args.putString("message", msg);
					getBaseActivity().showDialog2(DIALOG_ALERT_MESSAGE_CONFIRM, args);
					findViewById(R.id.chk_hint_eula1).requestFocus();
				}
			}
		} else {
		}
	}

}
