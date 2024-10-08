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
 * 2015 All rights (c)KYGroup Co.,Ltd. reserved.
 * 
 * This software is the confidential and proprietary information
 *  of (c)KYGroup Co.,Ltd. ("Confidential Information").
 * 
 * project	:	KYStbKaraoke
 * filename	:	LyricView.java
 * author	:	isyoon
 *
 * <pre>
 * com.kumyoung.stbui
 *    |_ LyricView.java
 * </pre>
 * 
 */

package com.kumyoung.stbui;

import isyoon.com.devscott.karaengine.Global;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * <pre>
 * </pre>
 *
 * Copy of {@link LyricView}
 * 
 * @author isyoon
 * @since 2015. 4. 29.
 * @version 1.0
 */
public class LyricView extends View {// SurfaceView implements SurfaceHolder.Callback {
	private final String __CLASSNAME__ = (new Exception()).getStackTrace()[0].getFileName();

	@Override
	public String toString() {
		super.toString();
		return getClass().getSimpleName() + '@' + Integer.toHexString(hashCode());
	}

	protected String getMethodName() {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		// int line = Thread.currentThread().getStackTrace()[3].getLineNumber();
		// name = String.format("line:%d - %s() ", line, name);
		name += "() ";
		return name;
	}

	private final String TAG = __CLASSNAME__;
	private String text = null;
	// private int backgroundColor = 0x7f000000;// Color.TRANSPARENT;
	// private String tempText;
	private int px = 0;
	private int ps = 0;
	private int aligment = 0;
	private int left = 0;

	private int redraw_count = 0;

	final private Lock lock = new ReentrantLock();
	private Bitmap bitmapMemory = null;
	private Canvas canvasMemory = null;

	private final int FONT_H = 92;
	private final int WIDTH = 1200;			// HAVEN 400에서 잘림
	private final int HEIGHT = (130);

	private Bitmap maskText = null;

	// 속성이 없는 생성자는 소스상에서 직접 생성할때만 쓰인다.
	public LyricView(Context context) {
		super(context);
		// Log.w(TAG,"CustomView("+context+")");
	}

	/*
	 * 리소스 xml 파일에서 정의하면 이 생성자가 사용된다.
	 * 
	 * 대부분 this 를 이용해 3번째 생성자로 넘기고 모든 처리를 3번째 생성자에서 한다.
	 */
	public LyricView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// Log.w(TAG,"CustomView("+context+","+attrs+")");

	}

	DisplayMetrics metrics;

	/*
	 * xml 에서 넘어온 속성을 멤버변수로 셋팅하는 역할을 한다.
	 */
	/**
	 * <pre>
	 * <a href="http://stackoverflow.com/questions/24332205/android-graphics-picture-not-being-drawn-in-api-14">android.graphics.Picture not being drawn in API 14+</a>
	 * <a href="http://stackoverflow.com/questions/10384613/android-canvas-drawpicture-not-working-in-devices-with-ice-cream-sandwich/14054331#14054331">Android Canvas.drawPicture not working in devices with ice cream sandwich</a>
	 * view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	 * </pre>
	 */
	public LyricView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		this.text = attrs.getAttributeValue(null, "text");

		// Log.w(TAG,"CustomView("+context+","+attrs+","+defStyle+"),text:"+text);

		bitmapMemory = Bitmap.createBitmap(WIDTH, HEIGHT, Config.ARGB_8888);
		canvasMemory = new Canvas(bitmapMemory);
		// pp.setXfermode( new PixelXorXfermode( 0xFF00FFFF ));

		metrics = getResources().getDisplayMetrics();
		String text = getMethodName() + "[RECT]";
		text += "\n[RECT]" + "metrics.densityDpi:" + metrics.densityDpi;
		text += "\n[RECT]" + "metrics.density:" + metrics.density;
		text += "\n[RECT]" + "metrics.scaledDensity:" + metrics.scaledDensity;
		Log.d(TAG, text);

		// isyoon_20150427
		canvasMemory.setDensity(metrics.densityDpi);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
	}

	/*
	 * xml 로 부터 모든 뷰를 inflate 를 끝내고 실행된다.
	 * 
	 * 대부분 이 함수에서는 각종 변수 초기화가 이루어 진다.
	 * 
	 * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
	 */
	@Override
	protected void onFinishInflate() {
		setClickable(true);
		// Log.w(TAG,"onFinishInflate()");
	}

	/*
	 * onMeasure() 메소드에서 결정된 width 와 height 을 가지고 어플리케이션 전체 화면에서 현재 뷰가 그려지는 bound 를 돌려준다.
	 * 
	 * 이 메소드에서는 일반적으로 이 뷰에 딸린 children 들을 위치시키고 크기를 조정하는 작업을 한다.
	 * 유의할점은 넘어오는 파라메터가 어플리케이션 전체를 기준으로 위치를 돌려준다.
	 * 
	 * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// Log.w(TAG,"onLayout("+changed+","+left+","+top+","+right+","+bottom+")");
	}

	/*
	 * 이 뷰의 크기가 변경되었을때 호출된다.
	 * 
	 * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {

		// Log.w(TAG,"onSizeChanged("+w+","+h+","+oldw+","+oldh+")");
	}

	Rect rc;

	private boolean isViewContains(View view, int rx, int ry) {
		int[] l = new int[2];
		view.getLocationOnScreen(l);
		rc = new Rect(l[0], l[1], l[0] + view.getWidth(), l[1] + view.getHeight());
		return rc.contains(rx, ry);
	}

	/*
	 * 실제로 화면에 그리는 영역으로 View 를 상속하고 이 메소드만 구현해도 제대로 보여지게 된다.
	 * 
	 * 그릴 위치는 0,0 으로 시작해서 getMeasuredWidth(), getMeasuredHeight() 까지 그리면 된다.
	 * 
	 * super 메소드에서는 아무것도 하지않기때문에 쓰지 않는다.
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		lock.lock();

		final Paint p = new Paint();

		// 밑에 까는 글자.
		if (this.text != null) {
			// p.setColor( backgroundColor);
			p.setColor(0xff00ffFF);   	// 빨간 계열 비트맵으로 그린다.
			canvas.drawBitmap(bitmapMemory, 0, 0, p);
		}

		// canvas.clipRect(left + Global.DPFromPixel(ps), 0, left + Global.DPFromPixel((px - ps)), HEIGHT);
		int l = left + Global.DPFromPixel(ps);
		int t = 0;
		int r = left + Global.DPFromPixel((px - ps));
		int b = HEIGHT;
		canvas.clipRect(l, t, r, b);

		// String msg = getMethodName() + "[RECT]" + "left:" + left + "(" + "px:" + px + ", ps:" + ps + ")";
		// msg += "[DP?](" + "l:" + l + ", t:" + t + ", r:" + r + ", b:" + b + ")";
		// // text += "[PX?](" + "l:" + Util.dp2px(getContext(), l) + ", t:" + Util.dp2px(getContext(), t) + ", r:" + Util.dp2px(getContext(), r) + ", b:" + Util.dp2px(getContext(), b) + ")";
		//
		// if (r > 0) {
		// Log.i(__CLASSNAME__, msg);
		// } else {
		// Log.e(__CLASSNAME__, msg);
		// }

		if (Global.isDebugGrid == true)
		{
			isViewContains(this, 0, 0);
			// final Paint p = new Paint();
			if (r > 0) {
				p.setColor(Color.GREEN);
			} else {
				p.setColor(Color.RED);
			}
			p.setStyle(Style.STROKE);
			canvas.drawRect(
					l + 1,
					t,
					r - 1,
					b - 1,
					p);
		}

		// final Paint p = new Paint();
		p.setColor(Color.RED);
		p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
		canvas.drawBitmap(maskText, 0, 0, p);
		ps += px;

		lock.unlock();

	}

	public void aligment(int align)
	{
		aligment = align;
	}

	public void setText(String text) {

		// String msg = getMethodName() + "[RECT](" + "px:" + px + ", ps:" + ps + ")" + text;
		//
		// if ((px - ps) > 0) {
		// Log.i(__CLASSNAME__, msg);
		// } else {
		// Log.e(__CLASSNAME__, msg);
		// }

		lock.lock();
		this.text = text;

		// bitmapMemory = Bitmap.createBitmap(480,40, Config.ARGB_8888);
		// canvasMemory = new Canvas(bitmapMemory);

		// canvasMemory.drawColor( 0, PorterDuff.Mode.CLEAR ); // 이미지 겹치지 않
		canvasMemory.drawColor(0xffffffff, PorterDuff.Mode.CLEAR);   	// 이미지 겹치지 않

		// canvasMemory.drawRect(0,0, 480, 40, p);
		// p.setColor( Color.RED );

		int y = getMeasuredHeight() / 2 + 10;
		int width = getMeasuredWidth();

		final Paint paint = new Paint();

		paint.setAntiAlias(true); // 테두리를 부드럽게한다
		paint.setTextSize(Global.Inst().DPFromPixel(FONT_H));
		paint.setColor(Color.BLACK);
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

		int measuredTextWidth = (int) Math.ceil(paint.measureText(text));

		if (aligment == 0)
			left = 0;
		else if (aligment == 1)
			left = (width / 2) - (measuredTextWidth / 2);
		else if (aligment == 2)
			left = width - measuredTextWidth;

		canvasMemory.drawText(text, left - 3, y + 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
		canvasMemory.drawText(text, left + 3, y + 0, paint); // 왼쪽 아래를 0,0 으로 보고있음
		canvasMemory.drawText(text, left + 0, y + (-3), paint); // 왼쪽 아래를 0,0 으로 보고있음
		canvasMemory.drawText(text, left + 0, y + (+3), paint); // 왼쪽 아래를 0,0 으로 보고있음

		canvasMemory.drawText(text, left - 2, y + (-2), paint); // 왼쪽 아래를 0,0 으로 보고있음
		canvasMemory.drawText(text, left + 2, y + (-2), paint); // 왼쪽 아래를 0,0 으로 보고있음
		canvasMemory.drawText(text, left + 2, y + (+2), paint); // 왼쪽 아래를 0,0 으로 보고있음
		canvasMemory.drawText(text, left - 2, y + (+2), paint); // 왼쪽 아래를 0,0 으로 보고있음

		paint.setColor(Color.WHITE);
		canvasMemory.drawText(text, left, y + 0, paint); // 왼쪽 아래를 0,0 으로 보고있음

		// FontDrv.getInstance().glyphs.drawString( canvas, this.text, 0,0 );

		maskText = bitmapMemory.extractAlpha();

		/*
		 * Drawable drawable = new BitmapDrawable(bitmapMemory);
		 * setBackgroundDrawable(drawable);
		 */

		// invalidate();

		px = 0;		// reset painting
		ps = 0;
		this.postInvalidate();

		lock.unlock();
	}

	public void setScrolling(int x)
	{
		// String msg = getMethodName() + "[RECT]" + "x:" + x + "(" + "px:" + px + ", ps:" + ps + ")";
		//
		// if ((px - ps) > 0) {
		// Log.i(__CLASSNAME__, msg);
		// } else {
		// Log.e(__CLASSNAME__, msg);
		// }

		if (px == (x - ps))	// 값이 같으면
			return;

		if (ps >= x)
			return;

		px = (x - ps);

		this.postInvalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 진짜 크기 구하기
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = 0;
		switch (heightMode) {
		case MeasureSpec.UNSPECIFIED:
			heightSize = heightMeasureSpec;
			break;	// mode 가 셋팅 안된 크기
		case MeasureSpec.AT_MOST:
			heightSize = Global.DPFromPixel(HEIGHT) /* bmBG.getHeight() */;
			break;
		case MeasureSpec.EXACTLY:
			heightSize = MeasureSpec.getSize(heightMeasureSpec);
			break;
		}
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = 0;
		switch (widthMode) {
		case MeasureSpec.UNSPECIFIED:
			widthSize = widthMeasureSpec;
			break; // mode 가 셋팅되지 않은 크기가 넘어올때
		case MeasureSpec.AT_MOST:
			widthSize = Global.DPFromPixel(WIDTH) /* bmBG.getWidth() */;
			break;
		case MeasureSpec.EXACTLY:
			widthSize = MeasureSpec.getSize(widthMeasureSpec);
			break;
		}

		// Log.w(TAG,"onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
		setMeasuredDimension(widthSize, heightSize);
	}

	/*
	 * @Override
	 * public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	 * {
	 *
	 * }
	 * 
	 * @Override
	 * public void surfaceCreated(SurfaceHolder arg0)
	 * {
	 *
	 * 
	 * mThread.start();
	 * 
	 * }
	 * 
	 * @Override
	 * public void surfaceDestroyed(SurfaceHolder arg0)
	 * {
	 *
	 * }
	 */

}