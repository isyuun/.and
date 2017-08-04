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
 * filename	:	BaseImageFragment.java
 * author	:	isyoon
 *
 * <pre>
 * kr.kymedia.karaoke.kpop.app
 *    |_ BaseImageFragment.java
 * </pre>
 * 
 */

package kr.kymedia.karaoke.apps;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.kymedia.karaoke._IKaraoke;
import kr.kymedia.karaoke.api.Log2;
import kr.kymedia.karaoke.util.EnvironmentUtils;
import kr.kymedia.karaoke.widget._ImageView;
import kr.kymedia.karaoke.widget.util.BitmapUtils2;

/**
 * 
 * TODO NOTE:<br>
 * 
 * @author isyoon
 * @since 2012. 7. 11.
 * @version 1.0
 */

public class BaseImageFragment extends _BaseFragment {
	protected String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	AlertDialog.Builder mImagePickerDialog = null;

	private _ImageView mImageView;

	private String mImagePath = "";
	private Uri mImageUri = null;

	private String mImageTempPath = "";
	private Uri mImageTempUri = null;

	public BaseImageFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (_IKaraoke.DEBUG) Log2.d(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);


		super.onCreate(savedInstanceState);

		initCropPicker();
	}

	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	//
	// if (_IKaraoke.DEBUG)Log.d(__CLASSNAME__, getMethodName() + getTag() + ":" + savedInstanceState);
	//
	// super.onActivityCreated(savedInstanceState);
	//
	// }

	public void initCropPicker() {

		mImageTempPath = EnvironmentUtils.getDataPath(getBaseActivity()) + "temp_"
				+ String.valueOf(System.currentTimeMillis()) + ".tmp";

		final String[] items = new String[] { getString(R.string.btn_title_crop_camera),
				getString(R.string.btn_title_crop_image) };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.select_dialog_item,
				items);

		mImagePickerDialog = new AlertDialog.Builder(getBaseActivity());
		mImagePickerDialog.setTitle("Select Image");
		mImagePickerDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) { // pick from camera
				try {
					if (item == 0) {
						mImageTempUri = Uri.fromFile(new File(EnvironmentUtils.getDataPath(getBaseActivity()),
								"capt_" + String.valueOf(System.currentTimeMillis()) + ".tmp"));

						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageTempUri);
						intent.putExtra("return-data", true);

						startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_CAMERA);
					} else { // pick from file
						mImageTempUri = null;

						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);

						startActivityForResult(Intent.createChooser(intent, "Complete action using"),
								_IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_IMAGE);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});

	}

	public String getImagePath() {
		return mImagePath;
	}

	/**
	 * @param mImagePath
	 *          the mImagePath to set
	 */
	public void setImagePath(String mImagePath) {
		this.mImagePath = mImagePath;
	}

	public Uri getImageUri() {
		return mImageUri;
	}

	/**
	 * @param mImageUri
	 *          the mImageUri to set
	 */
	public void setImageUri(Uri mImageUri) {
		this.mImageUri = mImageUri;
	}

	public void setImageView(_ImageView view) {
		mImageView = view;
	}

	public _ImageView getImageView() {
		return mImageView;
	}

	/**
	 * 사진선택
	 */
	public void openImagePicker() {
		try {
			mImageTempUri = null;

			// intent.setAction(Intent.ACTION_GET_CONTENT);
			// intent.setType("image/*");
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
			intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_PICK_FROM_IMAGE);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 사진찍기
	 */
	public void openCameraPicker() {
		try {
			mImageTempUri = Uri.fromFile(new File(EnvironmentUtils.getDataPath(getBaseActivity()),
					"capt_" + String.valueOf(System.currentTimeMillis()) + ".tmp"));

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageTempUri);
			intent.putExtra("return-data", true);

			startActivityForResult(intent, _IKaraoke.KARAOKE_INTENT_ACTION_PICK_FROM_CAMERA);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void openImageCroper() {

		// super.openCameraPicker();
		final AlertDialog dialog = mImagePickerDialog.create();
		dialog.show();
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getBaseActivity().getPackageManager().queryIntentActivities(intent, 0);

		int size = list.size();

		if (size == 0) {
			Toast.makeText(getBaseActivity(), "Can not find image crop app", Toast.LENGTH_SHORT).show();

			return;
		} else {
			intent.setData(mImageTempUri);

			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

				startActivityForResult(i, _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_FILE);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getBaseActivity().getPackageManager().getApplicationLabel(
							res.activityInfo.applicationInfo);
					co.icon = getBaseActivity().getPackageManager().getApplicationIcon(
							res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);

					co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName,
							res.activityInfo.name));

					cropOptions.add(co);
				}

				CropOptionAdapter adapter = new CropOptionAdapter(getBaseActivity(), cropOptions);

				AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
				builder.setTitle("Choose Crop App");
				builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						startActivityForResult(cropOptions.get(item).appIntent,
								_IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_FILE);
					}
				});

				builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {

						if (mImageTempUri != null) {
							getBaseActivity().getContentResolver().delete(mImageTempUri, null, null);
							mImageTempUri = null;
						}
					}
				});

				AlertDialog alert = builder.create();

				alert.show();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case _IKaraoke.KARAOKE_INTENT_ACTION_PICK_FROM_IMAGE:
			if (data != null) {
				mImageTempUri = data.getData();
				putImage(mImageTempUri);
			}
			break;

		case _IKaraoke.KARAOKE_INTENT_ACTION_PICK_FROM_CAMERA:
			if (mImageTempUri != null) {
				putImage(mImageTempUri);
			}
			break;

		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_IMAGE:
			mImageTempUri = data.getData();
			doCrop();
			break;

		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_CAMERA:
			doCrop();
			break;

		case _IKaraoke.KARAOKE_INTENT_ACTION_CROP_FROM_FILE:
			Bundle extras = data.getExtras();

			if (extras != null) {
				// Bitmap bm = extras.getParcelable("data");
				//
				// mImageView.setImageBitmap(bm);
				//
				// BitmapUtils2.resizeImageView2Bitmap(mImageView, bm);
				//
				// FileOutputStream fo = null;
				// try {
				// fo = new FileOutputStream(new File(Uri.parse(mImageTempPath).getPath()));
				// if (bm.compress(CompressFormat.PNG, 100, fo)) {
				// setImageUri(Uri.parse("file://" + mImageTempPath));
				// setImagePath(mImageTempPath);
				// }
				// } catch (Exception e) {
				// e.printStackTrace();
				// setImagePath(null);
				// setImageUri(null);
				// }
				Bitmap bm = extras.getParcelable("data");
				putImage(bm);
			}

			break;

		}
	}

	public void putImage(Uri uri) {
		Bitmap bm = BitmapUtils2.SafeDecodeBitmapFile(mContext, uri);
		putImage(bm);
	}

	public void putImage(Bitmap bm) {
		int QUALITY_DEFAULT = 90;

		try {
			mImageView.setImageBitmap(bm);
			mImageView.setVisibility(View.VISIBLE);

			// BitmapUtils2.resizeImageView2Bitmap(mImageView, bm);

			// JPEG압축파일
			mImageTempUri = BitmapUtils2.putSaveDecodeBitmap(bm, mImageTempPath, CompressFormat.JPEG,
					QUALITY_DEFAULT);

			// 파일유무확인
			File file = new File(mImageTempPath);
			if (file != null && file.exists()) {
				setImagePath(mImageTempPath);
				setImageUri(mImageTempUri);
			}

		} catch (Exception e) {

			e.printStackTrace();
			setImagePath(null);
			setImageUri(null);
		}

	}

	public void deleteImageTemp() {
		File f = new File(mImageTempUri.getPath());
		if (f.exists()) {
			f.delete();
		}
	}

}
