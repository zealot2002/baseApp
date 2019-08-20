package com.zzy.home.model.wrapper;



import com.zzy.home.model.bean.main.BannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * MainFragment ctx
 * @author zzy
 * @date 2018/11/30
 */

public class HfCtx {
    private List<BannerBean> bannerList;

    public HfCtx() {
        bannerList = new ArrayList<>();
    }

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

}
