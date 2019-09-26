package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.MineContract;
import com.zzy.business.presenter.MinePresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.model.bean.User;
import com.zzy.common.widget.PopupDialog;

/**
 * 我的首页
 */
public class MyMainActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , MineContract.View {
    private ImageView ivUserHead;
    private TextView tvUserName,tvUserRemarks,tvUserScore;
    private Button btnMyArchives, btnMyJob, btnMyPioneering,
            btnMyComment,btnMyGoodsToBuy,btnMyGoodsToSell,
            btnMyLog,btnSettings,btnShareSoftware;
    private MineContract.Presenter presenter;
    private User user;
    private PopupDialog dialog;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的");

        presenter = new MinePresenter(this);
        presenter.getUserInfo();
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
        btnMyJob = findViewById(R.id.btnMyJob);
        btnMyPioneering = findViewById(R.id.btnMyPioneering);
        btnMyComment = findViewById(R.id.btnMyComment);
        btnMyGoodsToBuy = findViewById(R.id.btnMyGoodsToBuy);
        btnMyGoodsToSell = findViewById(R.id.btnMyGoodsToSell);
        btnMyLog = findViewById(R.id.btnMyLog);
        btnSettings = findViewById(R.id.btnSettings);
        btnShareSoftware = findViewById(R.id.btnShareSoftware);


        btnMyArchives.setOnClickListener(this);
        btnMyJob.setOnClickListener(this);
        btnMyPioneering.setOnClickListener(this);

        btnMyComment.setOnClickListener(this);
        btnMyGoodsToBuy.setOnClickListener(this);
        btnMyGoodsToSell.setOnClickListener(this);

        btnMyLog.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnShareSoftware.setOnClickListener(this);

        tvUserName.setText(user.getName());
        tvUserRemarks.setText(user.getTitle());
        tvUserScore.setText(user.getScore()+"积分");
        ImageLoader.loadImage(ivUserHead,user.getHeadUrl());
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            user = (User) o;

            setupViews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if(v.getId() == R.id.btnMyArchives){
            startActivity(MyArchivesActivity.class);
        }else if(v.getId() == R.id.btnMyJob){
            startActivity(MyJobListActivity.class);
        }else if(v.getId() == R.id.btnMyPioneering){
            startActivity(MyPioneeringListActivity.class);
        }else if(v.getId() == R.id.btnMyComment){
            startActivity(MyCommentActivity.class);
        }else if(v.getId() == R.id.btnMyGoodsToBuy){
            bundle.putInt(ParamConstants.TYPE, CommonConstants.MY_GOODS_BUY);
            startActivity(GoodsListActivity.class,bundle);
        }else if(v.getId() == R.id.btnMyGoodsToSell){
            bundle.putInt(ParamConstants.TYPE, CommonConstants.MY_GOODS_SELL);
            startActivity(GoodsListActivity.class,bundle);
        }else if(v.getId() == R.id.btnMyLog){
            startActivity(MyLogListActivity.class);
        }else if(v.getId() == R.id.btnSettings){
            startActivity(SettingsActivity.class);
        }else if(v.getId() == R.id.btnShareSoftware){

        }
    }
    private void showWaitingPopup() {
        if(dialog == null){
            dialog = new PopupDialog.Builder(this,"正在开发中...","完成").create();
        }
        dialog.show();
    }
    @Override
    public void reload(boolean bShow) {
        presenter.getUserInfo();
    }
}
