package com.vj.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * {@link SurfaceView} for displaying a squared camera preview.
 *
 * @author Vimal
 */
public class CameraPreview extends SurfaceView {
    private static final double ASPECT_RATIO = 4.0 / 3.0;
	private static final double FACTOR = 1;

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreview(Context context) {
        super(context);
    }

    /**
     * Measure the view and its content to determine the measured width and the
     * measured height.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        if (width > height * ASPECT_RATIO) {
             width = (int) ((height * ASPECT_RATIO ) * FACTOR);
        } else {
            height = (int) ((width / ASPECT_RATIO ) * FACTOR);
        }

        setMeasuredDimension(width, height);
    }
}