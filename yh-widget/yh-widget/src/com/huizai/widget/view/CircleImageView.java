package com.huizai.widget.view;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;

/**
 * 用法：
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 <com.zcw.widget.CircleImageView
 android:layout_width="120dp"
 android:layout_height="120dp"
 android:clickable="true"
 android:src="@drawable/image1"
 app:borderColor="#fff"
 app:borderWidth="2dp"
 app:hoverColor="#22000000" />
 */
public class CircleImageView extends HoverImageView{

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public CircleImageView(Context context) {
		super(context);
		setup();
	}

	protected void setup() {

	}

	@Override
	public void buildBoundPath(Path borderPath){
		borderPath.reset();

		final int width = getWidth();
		final int height = getHeight();
		final float cx = width * 0.5f;
		final float cy = height * 0.5f;
		final float radius = Math.min(width, height) * 0.5f;

		borderPath.addCircle(cx, cy, radius, Direction.CW);
	}

	@Override
	public void buildBorderPath(Path borderPath) {
		borderPath.reset();

		final float halfBorderWidth = getBorderWidth() * 0.5f;

		final int width = getWidth();
		final int height = getHeight();
		final float cx = width * 0.5f;
		final float cy = height * 0.5f;
		final float radius = Math.min(width, height) * 0.5f;

		borderPath.addCircle(cx, cy, radius - halfBorderWidth, Direction.CW);
	}
}
