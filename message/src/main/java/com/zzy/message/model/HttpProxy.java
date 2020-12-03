package com.zzy.message.model;

import com.zzy.message.model.jsonParser.NoticeListParser;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.network.HttpUtils;
import com.zzy.commonlib.http.HInterface;

import org.json.JSONObject;

/**
 * Created by haoran on 2019/2/26.
 */
public class HttpProxy {

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

}
