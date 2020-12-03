package com.zzy.home.model.wrapper;

import com.zzy.common.bean.NoticeBean;
import com.zzy.home.model.bean.main.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment ctx
 * @author zzy
 * @date 2018/11/30
 */

public class HfCtx {
    private List<BannerBean> bannerList;
    private List<NoticeBean> noticeList;

    public HfCtx() {
        bannerList = new ArrayList<>();
        noticeList = new ArrayList<>();
    }

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public List<NoticeBean> getNoticeList() {
        return noticeList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public void setNoticeList(List<NoticeBean> noticeList) {
        this.noticeList = noticeList;
    }
}
