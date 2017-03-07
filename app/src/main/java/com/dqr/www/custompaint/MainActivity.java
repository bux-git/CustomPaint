package com.dqr.www.custompaint;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dqr.www.custompaint.paint_attr.CustomColorMatrix;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;

    private Button mBtn5;
    private Button mBtn6;
    private Button mBtn7;
    private Button mBtn8;

    private CustomColorMatrix mCustom;
    private CustomColorMatrix mCustomClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);

        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn6 = (Button) findViewById(R.id.btn6);
        mBtn7 = (Button) findViewById(R.id.btn7);
        mBtn8 = (Button) findViewById(R.id.btn8);

        mCustom = (CustomColorMatrix) findViewById(R.id.customView);
        mCustomClick = (CustomColorMatrix) findViewById(R.id.customView_click);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);

        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);
        mCustomClick.setOnClickListener(this);
    }

    private boolean isClick=false;
    @Override
    public void onClick(View v) {

        //生成色彩矩阵
        ColorMatrix colorMatrix = null;
        ColorFilter colorFilter = null;
        switch (v.getId()) {
            case R.id.btn1:
                colorMatrix = null;
                break;
            case R.id.btn2:
                colorMatrix = new ColorMatrix(new float[]{
                        0.5f, 0, 0, 0, 0,
                        0, 0.5f, 0, 0, 0,
                        0, 0, 0.5f, 0, 0,
                        0, 0, 0, 1, 0
                });
                break;
            case R.id.btn3:
                colorMatrix = new ColorMatrix(new float[]{
                        0.33F, 0.59F, 0.11F, 0, 0,
                        0.33F, 0.59F, 0.11F, 0, 0,
                        0.33F, 0.59F, 0.11F, 0, 0,
                        0, 0, 0, 1, 0,
                });
                break;
            case R.id.btn4:
                colorMatrix = new ColorMatrix(new float[]{
                        -1, 0, 0, 1, 1,
                        0, -1, 0, 1, 1,
                        0, 0, -1, 1, 1,
                        0, 0, 0, 1, 0,
                });
                break;
            case R.id.btn5:
                colorMatrix = new ColorMatrix(new float[]{
                        0, 0, 1, 0, 0,
                        0, 1, 0, 0, 0,
                        1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0,
                });
                break;
            case R.id.btn6:
                colorMatrix = new ColorMatrix(new float[]{
                        0.393F, 0.769F, 0.189F, 0, 0,
                        0.349F, 0.686F, 0.168F, 0, 0,
                        0.272F, 0.534F, 0.131F, 0, 0,
                        0, 0, 0, 1, 0,
                });
                break;
            case R.id.btn7:
                colorMatrix = new ColorMatrix(new float[]{
                        1.5F, 1.5F, 1.5F, 0, -1,
                        1.5F, 1.5F, 1.5F, 0, -1,
                        1.5F, 1.5F, 1.5F, 0, -1,
                        0, 0, 0, 1, 0,
                });
                break;
            case R.id.btn8:
                colorMatrix = new ColorMatrix(new float[]{
                        1.438F, -0.122F, -0.016F, 0, -0.03F,
                        -0.062F, 1.378F, -0.016F, 0, 0.05F,
                        -0.062F, -0.122F, 1.483F, 0, -0.02F,
                        0, 0, 0, 1, 0,
                });

                break;

            case R.id.customView_click:
                if(!isClick){
                    colorFilter=new LightingColorFilter(0xFFFFFFFF, 0X00FFFF00);
                    isClick=true;
                }else{
                    isClick=false;
                    colorFilter=null;
                }
                mCustomClick.setColorFilter(colorFilter);
                return;
        }
        if (colorMatrix != null) {
            colorFilter = new ColorMatrixColorFilter(colorMatrix);
        }

        mCustom.setColorFilter(colorFilter);
    }
}
