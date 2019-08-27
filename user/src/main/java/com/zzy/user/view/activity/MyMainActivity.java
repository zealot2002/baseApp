package com.zzy.user.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.user.R;

/**
 * 我的首页
 */
public class MyMainActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private ImageView ivUserHead;
    private TextView tvUserName,tvUserRemarks,tvUserScore;
    private Button btnMyArchives,btnMyRecruit,btnMyEntrepreneurship,
            btnMyComment,btnMyGoodsToBuy,btnMyGoodsToSell,
            btnMyEntrepreneurshipLog,btnSettings,btnShareSoftware;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_my_main_activity;
    }

    private void setupViews() {
        ivUserHead = findViewById(R.id.ivUserHead);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserRemarks = findViewById(R.id.tvUserRemarks);
        tvUserScore = findViewById(R.id.tvUserScore);

        btnMyArchives = findViewById(R.id.btnMyArchives);
        btnMyRecruit = findViewById(R.id.btnMyRecruit);
        btnMyEntrepreneurship = findViewById(R.id.btnMyEntrepreneurship);
        btnMyComment = findViewById(R.id.btnMyComment);
        btnMyGoodsToBuy = findViewById(R.id.btnMyGoodsToBuy);
        btnMyGoodsToSell = findViewById(R.id.btnMyGoodsToSell);
        btnMyEntrepreneurshipLog = findViewById(R.id.btnMyEntrepreneurshipLog);
        btnSettings = findViewById(R.id.btnSettings);
        btnShareSoftware = findViewById(R.id.btnShareSoftware);


        btnMyArchives.setOnClickListener(this);
        btnMyRecruit.setOnClickListener(this);
        btnMyEntrepreneurship.setOnClickListener(this);

        btnMyComment.setOnClickListener(this);
        btnMyGoodsToBuy.setOnClickListener(this);
        btnMyGoodsToSell.setOnClickListener(this);

        btnMyEntrepreneurshipLog.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnShareSoftware.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnMyArchives){

        }else if(v.getId() == R.id.btnMyRecruit){

        }else if(v.getId() == R.id.btnMyEntrepreneurship){

        }else if(v.getId() == R.id.btnMyComment){

        }else if(v.getId() == R.id.btnMyGoodsToBuy){

        }else if(v.getId() == R.id.btnMyGoodsToSell){

        }else if(v.getId() == R.id.btnMyEntrepreneurshipLog){

        }else if(v.getId() == R.id.btnSettings){
            startActivity(SettingsActivity.class);
        }else if(v.getId() == R.id.btnShareSoftware){

        }

    }

    @Override
    public void reload(boolean bShow) {

    }
}
