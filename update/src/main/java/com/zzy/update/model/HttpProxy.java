package com.zzy.update.model;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.update.model.jsonParser.CheckUpdateParser;

import org.json.JSONObject;

public class HttpProxy {
    public static void checkVersion(HInterface.DataCallback callback) throws Exception {
//        JSONObject reqBody = new JSONObject();
//        reqBody.put("type", 1);
//        reqBody.put("version", AppUtils.getVersionCode());
//        reqBody.put("versioncode", AppUtils.getVersionName());
//
//        HttpUtils.getInstance().req(
//                HttpConstants.APP_UPDATE,
//                reqBody,
//                callback,
//                new CheckUpdateParser());
    }
}