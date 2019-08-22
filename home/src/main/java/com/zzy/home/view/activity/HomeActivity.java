package com.zzy.home.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.business.view.activity.BuyGoodsListActivity;
import com.zzy.business.view.activity.EntrepreneurshipActivity;
import com.zzy.business.view.activity.EntrepreneurshipFriendsActivity;
import com.zzy.business.view.activity.EntrepreneurshipHelpActivity;
import com.zzy.business.view.activity.EntrepreneurshipListActivity;
import com.zzy.business.view.activity.EntrepreneurshipServiceActivity;
import com.zzy.business.view.activity.EntrepreneurshipVanguardActivity;
import com.zzy.business.view.activity.FeedbackActivity;
import com.zzy.business.view.activity.GetRichInfoActivity;
import com.zzy.business.view.activity.IndustrialDistributionActivity;
import com.zzy.business.view.activity.RecruitListActivity;
import com.zzy.business.view.activity.SellGoodsListActivity;
import com.zzy.business.view.activity.ShareExperienceActivity;
import com.zzy.business.view.activity.SpecialDkActivity;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.R;


/**
 * 首页
 */
public class HomeActivity extends BaseAppActivity implements View.OnClickListener {

    private TextView tvUserName,tvScore;
    private ImageView ivPic;
    private Button btnSpecialDk,btnIndustrialDistribution,btnGetRichInfo,
            btnEntrepreneurshipService,btnEntrepreneurshipVanguard;
    private Button btnRecruit,btnEntrepreneurship,btnBuyGoods,btnSellGoods,
            btnEntrepreneurshipHelp,btnFeedback,btnShareExperience,btnEntrepreneurshipFriends;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rlRoot));
        setupViews();
    }

    private void setupViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvScore = findViewById(R.id.tvScore);
        ivPic = findViewById(R.id.ivPic);

        btnSpecialDk = findViewById(R.id.btnSpecialDk);
        btnIndustrialDistribution = findViewById(R.id.btnIndustrialDistribution);
        btnGetRichInfo = findViewById(R.id.btnGetRichInfo);
        btnEntrepreneurshipService = findViewById(R.id.btnEntrepreneurshipService);
        btnEntrepreneurshipVanguard = findViewById(R.id.btnEntrepreneurshipVanguard);
        btnRecruit = findViewById(R.id.btnRecruit);
        btnEntrepreneurship = findViewById(R.id.btnEntrepreneurship);
        btnBuyGoods = findViewById(R.id.btnBuyGoods);
        btnSellGoods = findViewById(R.id.btnSellGoods);
        btnEntrepreneurshipHelp = findViewById(R.id.btnEntrepreneurshipHelp);
        btnFeedback = findViewById(R.id.btnFeedback);
        btnShareExperience = findViewById(R.id.btnShareExperience);
        btnEntrepreneurshipFriends = findViewById(R.id.btnEntrepreneurshipFriends);

        btnSpecialDk.setOnClickListener(this);
        btnIndustrialDistribution.setOnClickListener(this);
        btnGetRichInfo.setOnClickListener(this);
        btnEntrepreneurshipService.setOnClickListener(this);
        btnEntrepreneurshipVanguard.setOnClickListener(this);
        btnRecruit.setOnClickListener(this);
        btnEntrepreneurship.setOnClickListener(this);
        btnBuyGoods.setOnClickListener(this);
        btnSellGoods.setOnClickListener(this);
        btnEntrepreneurshipHelp.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnShareExperience.setOnClickListener(this);
        btnEntrepreneurshipFriends.setOnClickListener(this);
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                AppUtils.exitApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.btnSpecialDk){
                startActivity(SpecialDkActivity.class);
            }else if(v.getId() == R.id.btnIndustrialDistribution){
                startActivity(IndustrialDistributionActivity.class);
            }else if(v.getId() == R.id.btnGetRichInfo){
                startActivity(GetRichInfoActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurshipService){
                startActivity(EntrepreneurshipServiceActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurshipVanguard){
                startActivity(EntrepreneurshipVanguardActivity.class);
            }else if(v.getId() == R.id.btnRecruit){
                startActivity(RecruitListActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurship){
                startActivity(EntrepreneurshipListActivity.class);
            }else if(v.getId() == R.id.btnBuyGoods){
                startActivity(BuyGoodsListActivity.class);
            }else if(v.getId() == R.id.btnSellGoods){
                startActivity(SellGoodsListActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurshipHelp){
                startActivity(EntrepreneurshipHelpActivity.class);
            }else if(v.getId() == R.id.btnFeedback){
                startActivity(FeedbackActivity.class);
            }else if(v.getId() == R.id.btnShareExperience){
                startActivity(ShareExperienceActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurshipFriends){
                startActivity(EntrepreneurshipFriendsActivity.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
