package com.zzy.common.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.R;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

public class BottomNavBar extends RelativeLayout implements View.OnClickListener {

    public BottomNavBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.bottom_navbar, this);
        setupViews();
    }

    public BottomNavBar(Context context) {
        this(context, null);
    }

    private void setupViews(){
        LinearLayout llPb = findViewById(R.id.llPb);
        LinearLayout llMine = findViewById(R.id.llMine);
        Button btnHome = findViewById(R.id.btnHome);

        llPb.setOnClickListener(this);
        llMine.setOnClickListener(this);
        btnHome.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.llPb){
                SCM.getInstance().req(getContext(), ActionConstants.ENTRY_PHONE_BOOK_ACTIVITY_ACTION);
            }else if(v.getId() == R.id.llMine){
                SCM.getInstance().req(getContext(), ActionConstants.ENTRY_MY_MAIN_ACTIVITY_ACTION);
            }else if(v.getId() == R.id.btnHome){
                SCM.getInstance().req(getContext(), ActionConstants.ENTRY_HOME_ACTIVITY_ACTION);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
