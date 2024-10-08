package is.yuun.net.pikanji.camerapreviewsample;

import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

/**
 * CameraPreview class that is extended only for the purpose of testing CameraPreview class.
 * This class is added functionality to set arbitrary preview size, and removed automated retry function to start preview on exception.
 */
@Deprecated
public class ResizableCameraPreview extends CameraPreview {
	final private String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	/**
	 * @param context
	 * @param adjustByAspectRatio
	 * @param addReversedSizes
	 *          is set to true to add reversed values of supported preview-sizes to the list.
	 */
	public ResizableCameraPreview(Context context, int cameraId, LayoutMode mode, boolean addReversedSizes) {
		super(context, cameraId, mode);
		LOG_TAG = __CLASSNAME__;

		if (addReversedSizes) {
			List<Camera.Size> sizes = mPreviewSizeList;
			int length = sizes.size();
			for (int i = 0; i < length; i++) {
				Camera.Size size = sizes.get(i);
				Camera.Size revSize = mCamera.new Size(size.height, size.width);
				sizes.add(revSize);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d(__CLASSNAME__, getMethodName() + "[START]" + mSurfaceConfiguring);

		mCamera.stopPreview();

		Camera.Parameters cameraParams = mCamera.getParameters();
		boolean portrait = isPortrait();

		if (!mSurfaceConfiguring) {
			Camera.Size previewSize = determinePreviewSize(portrait, width, height);
			Camera.Size pictureSize = determinePictureSize(previewSize);
			if (DEBUGGING) {
				Log.d(LOG_TAG, "Desired Preview Size - w: " + width + ", h: " + height);
			}
			mPreviewSize = previewSize;
			mPictureSize = pictureSize;
			mSurfaceConfiguring = adjustSurfaceLayoutSize(previewSize, portrait, width, height);
			if (mSurfaceConfiguring) {
				Log.d(__CLASSNAME__, getMethodName() + "[CHANGE]" + mSurfaceConfiguring);
				return;
			}
		}

		configureCameraParameters(cameraParams, portrait);
		mSurfaceConfiguring = false;

		try {
			mCamera.startPreview();
		} catch (Exception e) {
			Toast.makeText(mContext, "Failed to start preview: " + e.getMessage(), Toast.LENGTH_LONG).show();
			Log.w(LOG_TAG, "Failed to start preview: " + e.getMessage());
		}

		Log.d(__CLASSNAME__, getMethodName() + "[END]" + mSurfaceConfiguring);
	}

	/**
	 * 
	 * @param index
	 *          selects preview size from the list returned by CameraPreview.getSupportedPreivewSizes().
	 * @param width
	 *          is the width of the available area for this view
	 * @param height
	 *          is the height of the available area for this view
	 */
	public void setPreviewSize(int index, int width, int height) {
		Log.d(__CLASSNAME__, getMethodName() + "[START]" + mSurfaceConfiguring);

		mCamera.stopPreview();

		Camera.Parameters cameraParams = mCamera.getParameters();
		boolean portrait = isPortrait();

		Camera.Size previewSize = mPreviewSizeList.get(index);
		Camera.Size pictureSize = determinePictureSize(previewSize);
		if (DEBUGGING) {
			Log.d(LOG_TAG, "Requested Preview Size - w: " + previewSize.width + ", h: " + previewSize.height);
		}
		mPreviewSize = previewSize;
		mPictureSize = pictureSize;
		adjustSurfaceLayoutSize(previewSize, portrait, width, height);
		// boolean layoutChanged = adjustSurfaceLayoutSize(previewSize, portrait, width, height);
		// if (layoutChanged) {
		// Log.d(__CLASSNAME__, getMethodName() + "[CHANGE]" + layoutChanged);
		// mSurfaceConfiguring = true;
		// return;
		// }

		configureCameraParameters(cameraParams, portrait);
		try {
			mCamera.startPreview();
		} catch (Exception e) {
			Toast.makeText(mContext, "Failed to satart preview: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		Log.d(__CLASSNAME__, getMethodName() + "[END]" + mSurfaceConfiguring);

		mSurfaceConfiguring = false;
	}

}
