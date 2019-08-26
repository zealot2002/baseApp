package com.zzy.home.model.wrapper;

import com.zzy.home.model.bean.Banner;
import com.zzy.home.model.bean.News;
import com.zzy.home.model.bean.SaleInfo;
import com.zzy.home.model.bean.User;

import java.util.ArrayList;
import java.util.List;


public class HomeCtx {
    private List<Banner> bannerList;
    private List<News> newsList;
    private List<SaleInfo> saleInfoList;
    private User user;

    public HomeCtx() {
        bannerList = new ArrayList<>();
        newsList = new ArrayList<>();
        saleInfoList = new ArrayList<>();
        user = new User();
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public List<SaleInfo> getSaleInfoList() {
        return saleInfoList;
    }

    public void setSaleInfoList(List<SaleInfo> saleInfoList) {
        this.saleInfoList = saleInfoList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
