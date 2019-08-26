package com.zzy.home.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hedgehog.ratingbar.RatingBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzy.business.view.activity.GoodsListActivity;
import com.zzy.business.view.activity.EntrepreneurshipFriendsActivity;
import com.zzy.business.view.activity.EntrepreneurshipHelpActivity;
import com.zzy.business.view.activity.EntrepreneurshipListActivity;
import com.zzy.business.view.activity.EntrepreneurshipServiceActivity;
import com.zzy.business.view.activity.EntrepreneurshipVanguardActivity;
import com.zzy.business.view.activity.FeedbackActivity;
import com.zzy.business.view.activity.GetRichInfoActivity;
import com.zzy.business.view.activity.IndustrialDistributionActivity;
import com.zzy.business.view.activity.JobListActivity;
import com.zzy.business.view.activity.ShareExperienceActivity;
import com.zzy.business.view.activity.SpecialDkActivity;
import com.zzy.business.view.adapter.PbListAdapter;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.R;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.wrapper.HomeCtx;
import com.zzy.home.presenter.HomePresenter;
import com.zzy.home.view.adapter.NewsListAdapter;


/**
 * 首页
 */
public class HomeActivity extends BaseAppActivity implements View.OnClickListener, HomeContract.View, OnRefreshListener {
    private TextView tvUserName,tvScore;
    private ImageView ivPic;
    private Button btnSpecialDk,btnIndustrialDistribution,btnGetRichInfo,
            btnEntrepreneurshipService,btnEntrepreneurshipVanguard;
    private Button btnRecruit,btnEntrepreneurship,btnBuyGoods,btnSellGoods,
            btnEntrepreneurshipHelp,btnFeedback,btnShareExperience,btnEntrepreneurshipFriends;

    private HomeContract.Presenter presenter;
    private HomeCtx ctx;
    private LoadingHelper loadingHelper;

    private RecyclerView rvNewsList;
    private NewsListAdapter adapter;
    private SmartRefreshLayout smartRefreshLayout;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rlRoot));
        loadingHelper = new LoadingHelper(this);
        setupViews();
        presenter = new HomePresenter(this);
        presenter.start();
    }

    private void setupViews() {
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);

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

        rvNewsList = findViewById(R.id.rvNewsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvNewsList.setLayoutManager(layoutManager);
        rvNewsList.setItemAnimator(new DefaultItemAnimator());

        /*adapter*/
        adapter = new NewsListAdapter(this);
        rvNewsList.setAdapter(adapter);
        adapter.setOnItemClickedListener(new NewsListAdapter.Listener() {
            @Override
            public void onItemClicked(int position) {
//                    //todo  get data
//                    for(int i=0;i<menuList.size();i++){
//                        menuList.get(i).setSelected(i==position?true:false);
//                    }
//                    adapter.notifyDataSetChanged();
            }
        });
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
                startActivity(JobListActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurship){
                startActivity(EntrepreneurshipListActivity.class);
            }else if(v.getId() == R.id.btnBuyGoods){
                Bundle bundle = new Bundle();
                bundle.putInt(ParamConstants.TYPE,0);
                startActivity(GoodsListActivity.class,bundle);
            }else if(v.getId() == R.id.btnSellGoods){
                Bundle bundle = new Bundle();
                bundle.putInt(ParamConstants.TYPE,1);
                startActivity(GoodsListActivity.class,bundle);
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

    @Override
    public void showError(String s) {
        smartRefreshLayout.finishRefresh();
        ToastUtils.showShort(s);
    }

    @Override
    public void showLoading() {
        loadingHelper.showLoading();
    }

    @Override
    public void closeLoading() {
        loadingHelper.closeLoading();
    }

    @Override
    public void showDisconnect() {
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void updateUI(Object o) {
        try{
            smartRefreshLayout.finishRefresh();
            ctx = (HomeCtx) o;
            updateViews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateViews() {
        tvUserName.setText(ctx.getUser().getName());
        tvScore.setText(ctx.getUser().getScore());
        ImageLoader.loadImage(ivPic,ctx.getBannerList().get(0).getImgUrl());
        adapter.swapData(ctx.getNewsList());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        presenter.start();
    }
}
