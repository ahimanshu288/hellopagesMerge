package com.knowlarity.helpers;

/**
 * Created by LinchPin-Dev on 6/9/2015.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.knowlarity.hellopages.R;

public class DotsProgressBar extends View {
    private float mRadius;

    private Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Handler mHandler = new Handler();
    private int mIndex = 0;
    private int widthSize, heightSize;
    private int margin = 2;
    private int mDotCount = 3;

    public DotsProgressBar(Context context) {
        super(context);
        init(context);
    }

    public DotsProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DotsProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {



        mRadius = context.getResources().getDimension(R.dimen.circle_indicator_radius);

        // dot fill color
        mPaintFill.setStyle(Style.FILL);
        mPaintFill.setColor(Color.rgb(4,158,208));
        // dot background color
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.rgb(221,244,250));
        start();
    }

    public void setDotsCount(int count) {
        mDotCount = count;
    }

    public void start() {
        mIndex = -1;
        mHandler.removeCallbacks(mRunnable);
        mHandler.post(mRunnable);
    }

    public void stop() {
        mHandler.removeCallbacks(mRunnable);
    }

    private int step = 1;
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            mIndex += step;
            if (mIndex < 0) {
                mIndex = 1;
                step = 1;
            } else if (mIndex > (mDotCount - 1)) {
                if ((mDotCount - 2) >= 0) {
                    mIndex = mDotCount - 2;
                    step = -1;
                } else{
                    mIndex = 0;
                    step = 1;
                }

            }

            invalidate();

            mHandler.postDelayed(mRunnable, 300);

        }

    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = (int) mRadius * 2 + getPaddingBottom() + getPaddingTop();
        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float dX = (widthSize - mDotCount * mRadius * 2 - (mDotCount - 1) * margin) / 2.0f;
        float dY = heightSize / 2;
        for (int i = 0; i < mDotCount; i++) {
            if (i == mIndex) {
                canvas.drawCircle(dX, dY, mRadius, mPaintFill);
            } else {
                canvas.drawCircle(dX, dY, mRadius, mPaint);
            }

            dX += (2 * mRadius + margin);
        }

    }
}