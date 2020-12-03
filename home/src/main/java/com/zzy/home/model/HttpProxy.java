package com.zzy.home.model;


import com.zzy.common.constants.HttpConstants;
import com.zzy.common.network.HttpUtils;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.home.model.jsonParser.main.BannerListParser;
import com.zzy.home.model.jsonParser.main.NoticeListParser;

import org.json.JSONObject;

public class HttpProxy {

    /*homeFragment start*/
    // 获取banner列表
    public static void getBannerList(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("size", 640);
        reqBody.put("apdid", 50);
        HttpUtils.getInstance().req(
                 HttpConstants.BANNER_LIST,
                reqBody,
                callback,
                new BannerListParser());
    }

    public static void getNoticeList(HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("pagetype", 1);
        reqBody.put("page", 1);
        reqBody.put("limit", 5);
        HttpUtils.getInstance().req(
                HttpConstants.NOTICE_LIST,
                reqBody,
                callback,
                new NoticeListParser());
    }
    /*homeFragment end*/
}