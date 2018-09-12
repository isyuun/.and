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
 * 2016 All rights (c)KYGroup Co.,Ltd. reserved.
 * <p/>
 * This software is the confidential and proprietary information
 * of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * <p/>
 * project	:	.prj
 * filename	:	PlayFragment2.java
 * author	:	isyoon
 * <p/>
 * <pre>
 * kr.kymedia.karaoke.apps
 *    |_ PlayFragment2.java
 * </pre>
 */
package kr.kymedia.karaoke.apps;

import android.media.MediaPlayer;
import android.widget.Toast;

import kr.kymedia.karaoke.kpop._playListenFragment;
import kr.kymedia.karaoke.kpop._playSingFragment;

/**
 * <pre>
 * 안드로이드 6.0 마시멜롱 런타임 권한
 * <a href="https://developer.android.com/intl/ko/training/permissions/index.html">Working with System Permissions</a>
 * <a href="https://www.google.com/design/spec/patterns/permissions.html">Permissions</a>
 * </pre>
 *
 * @author isyoon
 * @version 1.0
 * @since 2016-05-25
 */
class PlayFragment2 extends PlayFragment {
	protected final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	protected String _toString() {

		return (BuildConfig.DEBUG ? __CLASSNAME__ : getClass().getSimpleName()) + '@' + Integer.toHexString(hashCode());
	}

	@Override
	protected void onActivityCreated() {
		super.onActivityCreated();

		boolean rejected = false;

		if (this instanceof _playSingFragment) {
			//권한확인
			if (!checkPermission4Sing(false)) {
				rejected |= true;
			}
		} else if (this instanceof _playListenFragment) {
			//권한확인
			if (!checkPermission4Listen(false)) {
				rejected |= true;
			}
		}

		if (rejected) {
			getApp().popupToast(getString(R.string.permission_message_notice), Toast.LENGTH_LONG);
			getBaseActivity().showInstalledAppDetailsActivity();
			getActivity().finish();
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		super.onPrepared(mp);
	}
}
