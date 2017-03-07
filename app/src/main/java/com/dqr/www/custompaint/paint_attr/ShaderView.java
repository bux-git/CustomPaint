package com.dqr.www.custompaint.paint_attr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dqr.www.custompaint.R;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-06 20:44
 */

public class ShaderView extends View {

    private static final int RECT_SIZE=400;//矩形尺寸的一半
    private Paint mPaint;//画笔
    private int left, top, right, bottom;//矩形死角坐标

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //实例化画笔
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        //获取位图
        Bitmap bitmap =Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.a),2*RECT_SIZE,2*RECT_SIZE,true);
        //设置着色器
        BitmapShader shader =new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate(150,150);
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int halfWidth=getMeasuredWidth()/2;
        int halfHeight=getMeasuredHeight()/2;

        left=halfWidth-RECT_SIZE;
        top=halfHeight-RECT_SIZE;
        right=halfWidth+RECT_SIZE;
        bottom=halfHeight+RECT_SIZE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制矩形
        canvas.drawRect(left,top,right,bottom,mPaint);
    }
}
