package com.zzy.common.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.R;

public class BackToolBar extends RelativeLayout {

    public BackToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.back_titlebar, this);
    }

    public BackToolBar(Context context) {
        this(context, null);
    }

    public void setTitle(@NonNull String s) {
        ((TextView) findViewById(R.id.tvTitle)).setText(s);
    }

    public void setRightIv(@DrawableRes int res, OnClickListener listener) {
        ImageView ivRight = findViewById(R.id.ivRight);
        ivRight.setVisibility(VISIBLE);
        ivRight.setImageResource(res);
        ivRight.setOnClickListener(listener);
    }

    public void setOnBackEventListener(OnClickListener listener) {
        View left = findViewById(R.id.llLeft);
        left.setOnClickListener(listener);
    }

    public void setLeftVisibility(int v){
        View left = findViewById(R.id.llLeft);
        left.setVisibility(v);
    }

    public void setRightVisibility(int v){
        View ivRight = findViewById(R.id.ivRight);
        ivRight.setVisibility(v);
    }

    public void setRightEventListener(OnClickListener listener) {
        View ivRight = findViewById(R.id.ivRight);
        ivRight.setOnClickListener(listener);
    }

}
