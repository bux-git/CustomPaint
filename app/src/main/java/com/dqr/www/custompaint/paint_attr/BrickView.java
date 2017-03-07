package com.dqr.www.custompaint.paint_attr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dqr.www.custompaint.R;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-07 9:44
 */

public class BrickView extends View {

    private Paint mPaint;//画笔
    private Paint mSmallPaint;//小圆点画笔
    private int mRadius;//半径
    private int x,y;

    public BrickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
        mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        mRadius=bitmap.getWidth();

        mSmallPaint = new Paint();
        mSmallPaint.setColor(Color.WHITE);
        mSmallPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        canvas.drawCircle(x,y,mRadius,mPaint);
        canvas.drawCircle(x,y,5,mSmallPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                x= (int) event.getX();
                y= (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }
}
