package com.zzy.home.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.zzy.common.base.BaseLoadingFragment;
import com.zzy.common.bean.message.NoticeBean;
import com.zzy.common.constants.ActionConstants;
import com.zzy.common.constants.BusConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.commonlib.core.BusHelper;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.home.R;
import com.zzy.home.contract.MainContract;
import com.zzy.home.model.bean.main.BannerBean;
import com.zzy.home.model.wrapper.HfCtx;
import com.zzy.home.presenter.MainPresenter;
import com.zzy.sc.core.serverCenter.SCM;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by haoran on 2018/11/15.
 */
public class HomeFragment extends BaseLoadingFragment implements MainContract.View,
        View.OnClickListener, OnRefreshListener {
    private static final String TAG = "HomeFragment";
    private View rootView;
    private Context context;
    private MainContract.Presenter presenter;
    private HfCtx hfCtx;
    private BGABanner banner;
    private MarqueeView marqueeView;

    private boolean needReload;

    /*******************************************************************************************************/
    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment_main;
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        BusHelper.getBus().register(this);
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        presenter = new MainPresenter(this);
        presenter.start();
    }
    
    @Override
    public void onClick(View v) {

    }
    
    @Override
    public void showError(String s) {
    
    }
    
    @Override
    public void reload(boolean b) {
        super.reload(b);
        presenter.start();
    }
    
    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try {
            hfCtx = (HfCtx) o;
            updateViews();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateViews() {
        updateBanner();
        updateNotice();
    }
    private void updateNotice() {
        marqueeView = rootView.findViewById(R.id.marqueeView);
        if(hfCtx.getNoticeList() == null || hfCtx.getNoticeList().isEmpty()) {
            marqueeView.setVisibility(View.GONE);
            return;
        }
        else {
            marqueeView.setVisibility(View.VISIBLE);
        }
        List<String> info = new ArrayList<>();
        for(int i = 0; i < hfCtx.getNoticeList().size(); i++) {
            info.add(hfCtx.getNoticeList().get(i).getTitle());
        }
        marqueeView.startWithList(info);
        
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                try {
                    Bundle bundle = new Bundle();
                    NoticeBean noticeBean = hfCtx.getNoticeList().get(position);
                    bundle.putString(ParamConstants.TITLE, noticeBean.getTitle());
//                    bundle.putString(ParamConstants.CONTENT, noticeBean.getContent());
//                    bundle.putString(ParamConstants.TIME, noticeBean.getTime());
//                    bundle.putInt(ParamConstants.TEXT_SIZE, noticeBean.getTitleTextSize());
//                    bundle.putString(ParamConstants.TEXT_COLOR, noticeBean.getTitleTextColor());

//                    SCM.getInstance().req(AppUtils.getApp(), ActionConstants.ENTRY_NOTICE_DETAIL_ACTIVITY_ACTION, bundle);
                    SCM.getInstance().req(AppUtils.getApp(), ActionConstants.ENTRY_MESSAGE_ACTIVITY_ACTION, bundle);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    private void updateBanner() {
        banner = rootView.findViewById(R.id.banner);

        if(hfCtx.getBannerList().isEmpty()) {
            banner.setVisibility(View.GONE);
            return;
        }
        banner.setVisibility(View.VISIBLE);
        banner.setData(R.layout.home_banner_item, hfCtx.getBannerList(), null);
        banner.setDelegate(new BGABanner.Delegate<ImageView, BannerBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, BannerBean item, int position) {
                try {
                    String tmpUrl = "http://www.baidu.com";
                    Bundle bundle = new Bundle();
                    bundle.putString(ParamConstants.URL, tmpUrl);
                    SCM.getInstance().req(context, ActionConstants.ENTRY_WEB_VIEW_ACTIVITY_ACTION, bundle);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        banner.setAdapter(new BGABanner.Adapter<ImageView, BannerBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, BannerBean item, int position) {
                ImageLoader.loadImage(context, itemView, item.getImgUrl(), R.mipmap.default_banner);
            }
        });

        if(hfCtx.getBannerList().size() < 2) {
            banner.setAutoPlayAble(false);
        }
        else {
            banner.setAutoPlayAble(true);
            banner.startAutoPlay();
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if(needReload) {
            needReload = false;
            reload(true);
        }
    }
    
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(100, true);
        reload(true);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        if(marqueeView != null)
            marqueeView.startFlipping();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        if(marqueeView != null) {
            marqueeView.stopFlipping();
        }
        closeLoading();
    }
    
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        BusHelper.getBus().unregister(this);
        presenter.end();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(value = BusConstants.EVENT_LOGIN_SUCCESS)
                    , @Tag(value = BusConstants.EVENT_LOGOUT_SUCCESS)
            })
    public void onNeedReloadEvent(String s) {
        needReload = true;
    }
}
