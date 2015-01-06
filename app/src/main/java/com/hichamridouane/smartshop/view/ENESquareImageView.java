package com.hichamridouane.smartshop.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A custom {@link android.widget.ImageView} that is sized to be a perfect square, otherwise
 * functions like a typical {@link android.widget.ImageView}.
 * 
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public class ENESquareImageView extends ImageView {

	/**
	 * @param context
	 *            The {@link android.content.Context} to use
	 * @param attrs
	 *            The attributes of the XML tag that is inflating the view.
	 */
	public ENESquareImageView(final Context context, final AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public void requestLayout() {
		forceLayout();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMeasure(final int widthSpec, final int heightSpec) {
		super.onMeasure(widthSpec, heightSpec);
		final int mSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
		setMeasuredDimension(mSize, mSize);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO Auto-generated method stub
		super.setImageBitmap(bm);
	}

}