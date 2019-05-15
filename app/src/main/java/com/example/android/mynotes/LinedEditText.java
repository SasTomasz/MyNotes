package com.example.android.mynotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

/**
 * this class create EditText with horizontal lines on it like in paper with lines
 */
public class LinedEditText extends AppCompatEditText {
    Rect mRect;
    Paint mPaint;
    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * Rect object hold coordinates of a rectangle, here used for drawing lines
         * Paint object holds info about styles, colors and how to draw
         */
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0xFFFFD966);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = ((View) this.getParent()).getHeight();
        int lineHight = getLineHeight();
        int numberOfLnes = height / lineHight;
        Rect r = mRect;
        Paint paint = mPaint;

        int baseline = getLineBounds(0, r);

        // draw lines
        for (int i = 0; i < numberOfLnes; i++){
            canvas.drawLine(r.left, baseline + 1, r.right, baseline +1, paint);

            baseline += lineHight;
        }
        super.onDraw(canvas);
    }
}
