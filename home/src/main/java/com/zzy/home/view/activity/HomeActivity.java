package com.zzy.home.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
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
import com.zzy.business.view.activity.GetRichInfoListActivity;
import com.zzy.business.view.activity.IndustrialDistributionActivity;
import com.zzy.business.view.activity.JobListActivity;
import com.zzy.business.view.activity.ShareExperienceActivity;
import com.zzy.business.view.activity.SpecialDkActivity;
import com.zzy.business.view.adapter.SpeedyLinearLayoutManager;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BannerHolderView;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.R;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.wrapper.HomeCtx;
import com.zzy.home.presenter.HomePresenter;
import com.zzy.home.view.adapter.NewsListAdapter;
import com.zzy.home.view.adapter.SaleListAdapter;

import java.util.ArrayList;
import java.util.List;


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
    private ConvenientBanner banner;

    private RecyclerView rvNewsList,rvSaleList;
    private NewsListAdapter newsListAdapter;
    private SaleListAdapter saleListAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private boolean bAniStart;
    private int aniIndex = 0;
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
        SpeedyLinearLayoutManager layoutManager = new SpeedyLinearLayoutManager(this);
        layoutManager.setMillisecondsPerInch(4000f);
        rvNewsList.setLayoutManager(layoutManager);
        /*newsListAdapter*/
        newsListAdapter = new NewsListAdapter(this);
        rvNewsList.setAdapter(newsListAdapter);
        newsListAdapter.setOnItemClickedListener(new NewsListAdapter.Listener() {
            @Override
            public void onItemClicked(int position) {
                ToastUtils.showShort(position);
            }
        });
        rvNewsList.postDelayed(mRunnable,1000);

        rvSaleList = findViewById(R.id.rvSaleList);
        SpeedyLinearLayoutManager layoutManager2 = new SpeedyLinearLayoutManager(this);
        layoutManager2.setMillisecondsPerInch(8000f);
        rvSaleList.setLayoutManager(layoutManager2);
        /*newsListAdapter*/
        saleListAdapter = new SaleListAdapter(this);
        rvSaleList.setAdapter(saleListAdapter);

        updateBanner();
    }

    private AnimRunnable mRunnable = new AnimRunnable();
    private class AnimRunnable implements Runnable{

        @Override
        public void run() {
            MyLog.e("run !");
            aniIndex +=5;
            rvNewsList.smoothScrollToPosition(aniIndex);
            rvSaleList.smoothScrollToPosition(aniIndex);
            rvNewsList.postDelayed(this,10000);
        }
    }

    private void startAni(){
        if(rvNewsList!=null){
            if(!bAniStart){
                rvNewsList.postDelayed(mRunnable,2000);
                bAniStart = true;
            }
        }
    }
    private void stopAni(){
        if(rvNewsList!=null) {
            rvNewsList.removeCallbacks(mRunnable);
            bAniStart = false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        startAni();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAni();
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

    private void updateBanner() {
        List<String> list = new ArrayList<>();

        list.add(CommonConstants.TEST_IMG_URL);
        list.add(CommonConstants.TEST_IMG_URL);
        list.add(CommonConstants.TEST_IMG_URL);
        list.add(CommonConstants.TEST_IMG_URL);
        banner = findViewById(R.id.banner);
        banner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public BannerHolderView createHolder(View itemView) {
                        return new BannerHolderView(itemView, R.mipmap.icon_default);
                    }
                    @Override
                    public int getLayoutId() {
                        return R.layout.banner_item;
                    }
                }, list);
    }

    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.btnSpecialDk){
                startActivity(SpecialDkActivity.class);
            }else if(v.getId() == R.id.btnIndustrialDistribution){
                startActivity(IndustrialDistributionActivity.class);
            }else if(v.getId() == R.id.btnGetRichInfo){
                startActivity(GetRichInfoListActivity.class);
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
        newsListAdapter.swapData(ctx.getNewsList());
        saleListAdapter.swapData(ctx.getSaleInfoList());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        presenter.start();
    }
}
