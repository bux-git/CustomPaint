package com.dqr.www.custompaint.paint_attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.dqr.www.custompaint.R;


/**
 * Description：
 * Author：LiuYM
 * Date： 2017-03-06 9:40
 */

public class CustomColorMatrix extends View {

    private Paint mPaint;//画笔
    private Context mContext;//上下文环境引用
    private Bitmap bitmap;//位图

    private int mImgResId;//图片资源ID


    public CustomColorMatrix(Context context) {
        this(context, null);
    }

    public CustomColorMatrix(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomColorMatrix(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomColorMatrix(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet set) {
        initPaint();
        mContext = context;
        /**
         * 读取自定义属性 设置默认属性
         */
        TypedArray array = context.obtainStyledAttributes(set, R.styleable.CustomColorMatrix, 0, R.style.default_custom);
        mImgResId = array.getResourceId(R.styleable.CustomColorMatrix_imgResId, 0);
        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //实例化 画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void initRes(Context context) {
        //获取位图
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), mImgResId);
        bitmap = Bitmap.createScaledBitmap(srcBitmap, getWidth(), getHeight(), true);
        srcBitmap.recycle();
                /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一半的高度
         */
        // x = getResources().getDisplayMetrics().widthPixels / 2 - bitmap.getWidth() / 2;
        // y = getResources().getDisplayMetrics().heightPixels / 2 - bitmap.getHeight() / 2;
    }



    public void setColorFilter(ColorFilter colorFilter){
        mPaint.setColorFilter(colorFilter);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initRes(mContext);
        //绘制位图
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }
}
