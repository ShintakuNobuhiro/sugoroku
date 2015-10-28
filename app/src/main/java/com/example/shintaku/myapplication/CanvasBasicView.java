package com.example.shintaku.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

class CanvasBasicView extends View {

    private Paint mPaint = new Paint();

    public CanvasBasicView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // Viewの描画サイズを指定する
        setMeasuredDimension(10000, 1920);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("Hello Custom View!", 50, 50, mPaint);
        int x = 1400;
        int y = 50;
        int sx = 400;
        int sy = 150;
        final int dy = 300;
        final int recentcell = 5;
        final int cell = 10;
        canvas.translate(0, -dy * recentcell);

        //線路
        int stroke = 20;
        int height = 50;
        Rect rect = new Rect(x + sx / 2 - stroke, y, x + sx / 2 + stroke, y + height);
        int j = 0;
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        while (height * j < 22 * dy) {
            if (j % 2 == 1) {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            } else {
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (j % 6 >= 3) {
                canvas.drawRect(rect, mPaint);
            }
            rect.offset(0, height);
            j++;
        }

        //マス
        RectF rectf = new RectF(x, y, x + sx, y + sy);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 22; i++) {
            canvas.drawRoundRect(rectf, 30, 30, mPaint);
            canvas.drawText("新青森", x + sy / 2, (y + sy / 2) + dy * i, mPaint);
            rectf.offset(0, dy);
        }

        //アニメーション(ちらつく)
        final ValueAnimator anim = ValueAnimator.ofFloat(0, -1);
// アニメーションの時間を設定する
        anim.setDuration(1000);
        final float[] tmp = new float[1];
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tmp[0] = (Float) (anim.getAnimatedValue());
                canvas.translate(0, tmp[0]);
                postInvalidate();
                if (tmp[0] <= -dy * (cell - recentcell)) {
                    Log.d("t", String.valueOf(tmp[0]));
                    //anim.end();
                }
            }
        });
        anim.start();

    }
}