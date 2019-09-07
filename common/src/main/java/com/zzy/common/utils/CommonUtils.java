package com.zzy.common.utils;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.constants.SPConstants;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.SPUtils;

public final class CommonUtils {
    public static String getUserId(){
        String userId = SPUtils.getString(AppUtils.getApp(), SPConstants.USER_ID,"");
        return userId;
    }
    public static String getToken(){
        String token = SPUtils.getString(AppUtils.getApp(), SPConstants.TOKEN, HttpConstants.TOKEN);
        return token;
    }
}
