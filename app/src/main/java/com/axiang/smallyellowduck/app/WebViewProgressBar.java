package com.axiang.smallyellowduck.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.axiang.smallyellowduck.R;

/**
 * Created by a2389 on 2017/8/1.
 */

public class WebViewProgressBar extends View {

    // 进度默认为1
    private int progress = 1;

    // 进度条高度为5
    private final static int HEIGHT = 5;

    private Paint paint;

    // 渐变颜色数组
    // int类型颜色值格式：0x+透明值+颜色的rgb值
    private final static int colors[] = new int[]{0xFF89CC66, 0xFF49CC66, 0xFF09CC66};

    public WebViewProgressBar(Context context) {
        this(context, null);
    }

    public WebViewProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    private void initPaint(Context context) {
        paint = new Paint(Paint.DITHER_FLAG);
        // 填充方式为描边
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔的宽度
        paint.setStrokeWidth(HEIGHT);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 使用抖动效果
        paint.setDither(true);
        // 画笔设置颜色
        paint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        // 颜色渐变从colors[0]到colors[2],透明度从0到1
        // 使用渐变时数组的长度(colors)和透明度数组(new float[]{})长度必须一致，否则会报错的
        LinearGradient linearGradient = new LinearGradient(0, 0, 100, HEIGHT, colors,
                new float[]{0f, 0.5f, 1.0f}, Shader.TileMode.MIRROR);
        // 画笔设置渐变
        paint.setShader(linearGradient);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        // 刷新画笔
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画矩形从（0.0）开始到（progress,height）的区域
        canvas.drawRect(0, 0, getWidth() * progress / 100, HEIGHT, paint);
    }
}
