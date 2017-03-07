package com.dqr.www.custompaint.paint_attr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-06 17:41
 */

public class EcgView extends View {
    private Paint mPaint;//画笔
    private Path mPath;//路径对象

    private float x,y;//路径初始坐标
    private float initX;//初始X轴坐标
    private float transX,moveX;//画布移动的距离

    private boolean isCanvasMove;//画布是否需要平移




    public EcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /**
         * 实例化画笔并设置属性
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(7,0,0,Color.GREEN);

        mPath = new Path();
        transX=0;
        isCanvasMove=false;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /**
         * 设置起点坐标
         */
        x=0;
        y=(getMeasuredHeight()/4)+(getMeasuredHeight()/8);

        //初始X轴坐标
        initX=(getMeasuredWidth()/4)+(getMeasuredWidth()/8);

        moveX=getMeasuredWidth()/48;

        mPath.moveTo(x,y);
    }

    /*
    计算坐标
     */
    private void calCoors(){
        if(isCanvasMove==true){
            transX +=4;
        }
        if(x<initX){
            x +=8;
            y -=8;
        }else{
            if(x<initX+(moveX*2)){
                x +=2;
                y +=14;
            }else{
                if(x<initX+(moveX*4)){
                    x +=2;
                    y +=6;
                }else{
                    if(x<getMeasuredWidth()){
                        x +=8;
                    }else{
                        isCanvasMove=true;
                        initX=initX+getMeasuredWidth();
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        mPath.lineTo(x,y);
        //向左平移
        canvas.translate(-transX,0);
        //绘制路径
        calCoors();
        canvas.drawPath(mPath,mPaint);
        invalidate();
    }
}
