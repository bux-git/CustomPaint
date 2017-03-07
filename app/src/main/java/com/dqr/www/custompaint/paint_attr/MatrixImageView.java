package com.dqr.www.custompaint.paint_attr;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-07 14:25
 */

public class MatrixImageView extends android.support.v7.widget.AppCompatImageView {

    private static final int MODE_NOE=0X00123;//默认触摸模式
    private static final int MODE_DRAG=0X00321;//拖拽模式
    private static final int MODE_ZOOM=0X00132;//缩放or旋转模式

    private int mode;//当前触摸模式

    private float preMove=1f;//上次手指移动的距离
    private float saveRotate=0f;//保存了的角度值
    private float rotate=0f;//旋转角度

    private float[] preEventCoor;//上一次触摸点的坐标集合

    private PointF start,mid;//起点 中点对象
    private Matrix currentMatrix,savedMatrix;//当前和保存了的Matrix对象
    private Context mContext;

    public MatrixImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        /**
         * 实例化对象
         */
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        start = new PointF();
        mid = new PointF();

        //模式初始化
        mode=MODE_NOE;
    }

    /**
     * 计算两个触摸点间的距离
     */
    private float calSpacing(MotionEvent event){
        float x=event.getX(0)-event.getX(1);
        float y=event.getY(0)-event.getY(1);
        return (float)Math.sqrt(x*x+y*y);
    }

    /**
     * 计算两个触摸点的中点坐标
     * @param pointF
     * @param event
     */
    private void calMidPoint(PointF pointF,MotionEvent event){
        float x=event.getX(0)+event.getX(1);
        float y=event.getY(0)+event.getY(1);
        pointF.set(x/2,y/2);
    }

    /**
     * 计算旋转角度
     * @param event 事件对象
     * @return 角度
     */
    private float calRotation(MotionEvent event){
        double deltaX=event.getX(1)-event.getX(0);
        double deltaY=event.getY(1)-event.getY(0);
        double radius=Math.atan2(deltaX,deltaY);
        return (float)Math.toDegrees(radius);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN://单点触摸屏幕时
                getParent().requestDisallowInterceptTouchEvent(true);
                savedMatrix.set(currentMatrix);
                start.set(event.getX(),event.getY());
                mode=MODE_DRAG;
                preEventCoor=null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN://第二点接触屏幕时
                preMove = calSpacing(event);
                if(preMove > 10f){
                    savedMatrix.set(currentMatrix);
                    calMidPoint(mid,event);
                    mode=MODE_ZOOM;
                }
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                saveRotate = calRotation(event);
                break;
            case MotionEvent.ACTION_UP://单点离开屏幕时
                //getParent().requestDisallowInterceptTouchEvent(false);
                mode=MODE_NOE;
                break;
            case MotionEvent.ACTION_POINTER_UP://第二个点离开屏幕时
                mode=MODE_DRAG;
                preEventCoor=null;
                break;
            case MotionEvent.ACTION_MOVE://触摸点移动时
                /**
                 * 单点触控拖拽平移
                 */
                if(mode==MODE_DRAG){
                    currentMatrix.set(savedMatrix);
                    float dx=event.getX()-start.x;
                    float dy=event.getY()-start.y;
                    currentMatrix.postTranslate(dx, dy);
                }
                /**
                 * 两点触控拖放旋转
                 */else if(mode==MODE_ZOOM&&event.getPointerCount()==2){
                    float currentMove=calSpacing(event);
                    currentMatrix.set(savedMatrix);
                    /**
                     * 指尖移动距离大于10f缩放
                     */
                    if(currentMove>10f){
                        float scale=currentMove/preMove;
                        currentMatrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    /*
                 * 保持两点时旋转
                 */
                    if (preEventCoor != null) {
                        rotate = calRotation(event);
                        float r =saveRotate- rotate;
                        currentMatrix.postRotate(r, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                    }
                }

                break;
        }
        setImageMatrix(currentMatrix);
        return true;
    }
}
