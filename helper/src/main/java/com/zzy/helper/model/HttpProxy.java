package com.zzy.helper.model;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.jsonParser.CommonParser;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONObject;

/**
 * Created by haoran on 2019/2/26.
 */
public class HttpProxy {

    public static void getGlobalConfig(final HInterface.DataCallback callback) {
        try {
            JSONObject reqBody = new JSONObject();
            HttpUtils.getInstance().req(HttpConstants.GET_GLOBAL_CONFIG,
                    reqBody, callback, new CommonParser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
