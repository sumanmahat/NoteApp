package com.example.contactlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;



    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFFFFD966);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = ((View)this.getParent()).getHeight();
        int lineHeight = getLineHeight();
        int numberLines = height / lineHeight;

        Rect r = mRect;
        Paint p= mPaint;

        int baseLine = getLineBounds(0,r);
        for (int i = 0;i < numberLines; i++){
            canvas.drawLine(r.left, baseLine + 1, r.right, baseLine + 1, p);

            baseLine += lineHeight;
        }

        super.onDraw(canvas);
    }
}
