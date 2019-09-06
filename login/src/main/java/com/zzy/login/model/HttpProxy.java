package com.zzy.login.model;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.model.jsonParser.CommonParser;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.encryptUtils.MD5Utils;

import org.json.JSONObject;

public class HttpProxy {



    public static void login(String un, String pw, HInterface.DataCallback callback) throws Exception {
        JSONObject reqBody = new JSONObject();
        reqBody.put("MOBILE_NO", un);
        reqBody.put("PASSWORD", MD5Utils.encode(pw));
        HttpUtils.getInstance().req(
                HttpConstants.CONTENT_LIKE,
                reqBody,
                callback,
                new CommonParser());
    }

}