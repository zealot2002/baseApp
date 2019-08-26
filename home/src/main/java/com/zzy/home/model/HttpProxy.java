package com.zzy.home.model;


import com.zzy.common.constants.HttpConstants;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.home.model.jsonParser.HomeDataParser;

import org.json.JSONObject;

public class HttpProxy {

    /*homeFragment start*/
    // 获取banner列表
    public static void getHomeData(final HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("TOKEN", "test token");
        HttpUtils.getInstance().req(
                 HttpConstants.HOME_DATA,
                reqBody,
                callback,
                new HomeDataParser());
    }


    /*homeFragment end*/
}