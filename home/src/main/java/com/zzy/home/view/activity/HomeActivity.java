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
import com.zzy.business.view.activity.ContentListActivity;
import com.zzy.business.view.activity.FriendsCircleActivity;
import com.zzy.business.view.activity.GoodsListActivity;
import com.zzy.business.view.activity.PioneeringListActivity;
import com.zzy.business.view.activity.PioneerServiceActivity;
import com.zzy.business.view.activity.PioneerListActivity;
import com.zzy.business.view.activity.GetRichInfoListActivity;
import com.zzy.business.view.activity.IndustrialDistributionActivity;
import com.zzy.business.view.activity.JobListActivity;
import com.zzy.business.view.activity.SpecialDkActivity;
import com.zzy.business.view.other.SpeedyLinearLayoutManager;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.BannerHolderView;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.common.widget.PopupDialog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.R;
import com.zzy.home.contract.HomeContract;
import com.zzy.home.model.bean.Banner;
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

    private PopupDialog dialog;
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
        btnEntrepreneurshipHelp = findViewById(R.id.btnHelp);
        btnFeedback = findViewById(R.id.btnIdea);
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

    private void setupList(){
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
        rvNewsList.postDelayed(mRunnable,500);

        rvSaleList = findViewById(R.id.rvSaleList);
        SpeedyLinearLayoutManager layoutManager2 = new SpeedyLinearLayoutManager(this);
        layoutManager2.setMillisecondsPerInch(6000f);
        rvSaleList.setLayoutManager(layoutManager2);
        /*newsListAdapter*/
        saleListAdapter = new SaleListAdapter(this);
        rvSaleList.setAdapter(saleListAdapter);

        rvSaleList.setNestedScrollingEnabled(false);//禁止滑动
        rvSaleList.setEnabled(false);
    }
    private AnimRunnable mRunnable = new AnimRunnable();
    private class AnimRunnable implements Runnable{

        @Override
        public void run() {
            aniIndex +=5;
            rvNewsList.smoothScrollToPosition(aniIndex);
            rvSaleList.smoothScrollToPosition(aniIndex);
            rvNewsList.postDelayed(this,5000);
        }
    }

    private void startAni(){
        if(rvNewsList!=null){
            if(!bAniStart){
                rvNewsList.postDelayed(mRunnable,500);
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
        for(Banner banner:ctx.getBannerList()){
            list.add(banner.getImgUrl());
        }
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
            Bundle bundle = new Bundle();
            if(v.getId() == R.id.btnSpecialDk){
                startActivity(SpecialDkActivity.class);
            }else if(v.getId() == R.id.btnIndustrialDistribution){
                startActivity(IndustrialDistributionActivity.class);
            }else if(v.getId() == R.id.btnGetRichInfo){
                startActivity(GetRichInfoListActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurshipService){
                startActivity(PioneerServiceActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurshipVanguard){
                startActivity(PioneerListActivity.class);
            }else if(v.getId() == R.id.btnRecruit){
                startActivity(JobListActivity.class);
            }else if(v.getId() == R.id.btnEntrepreneurship){
                startActivity(PioneeringListActivity.class);
            }else if(v.getId() == R.id.btnBuyGoods){
                bundle.putInt(ParamConstants.TYPE,CommonConstants.GOODS_BUY);
                startActivity(GoodsListActivity.class,bundle);
            }else if(v.getId() == R.id.btnSellGoods){
                bundle.putInt(ParamConstants.TYPE,CommonConstants.GOODS_SELL);
                startActivity(GoodsListActivity.class,bundle);
            }else if(v.getId() == R.id.btnHelp){
                bundle.putInt(ParamConstants.TYPE, CommonConstants.CONTENT_HELP);
                startActivity(ContentListActivity.class,bundle);
            }else if(v.getId() == R.id.btnIdea){
                bundle.putInt(ParamConstants.TYPE, CommonConstants.CONTENT_IDEA);
                startActivity(ContentListActivity.class,bundle);
            }else if(v.getId() == R.id.btnShareExperience){
                bundle.putInt(ParamConstants.TYPE, CommonConstants.CONTENT_EXPERIENCE);
                startActivity(ContentListActivity.class,bundle);
            }else if(v.getId() == R.id.btnEntrepreneurshipFriends){
                startActivity(FriendsCircleActivity.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showWaitingPopup() {
        if(dialog == null){
            dialog = new PopupDialog.Builder(this,"正在开发中...","完成").create();
        }
        dialog.show();
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
        presenter.start();
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
        setupList();
        tvUserName.setText(ctx.getUser().getName());
        tvScore.setText(ctx.getUser().getScore()+"积分");
//        ImageLoader.loadImage(ivPic,ctx.getBannerList().get(0).getImgUrl());
        newsListAdapter.swapData(ctx.getNewsList());
        saleListAdapter.swapData(ctx.getSaleInfoList());
        updateBanner();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        presenter.start();
    }
}
