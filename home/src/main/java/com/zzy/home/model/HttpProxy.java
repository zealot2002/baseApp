package com.zzy.home.model;


import com.zzy.common.constants.HttpConstants;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.home.model.jsonParser.main.BannerListParser;

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


    /*homeFragment end*/
}