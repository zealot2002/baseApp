package com.zzy.common.utils;

import android.util.Log;
import com.zzy.common.constants.SPConstants;
import com.zzy.flysp.core.spHelper.SPHelper;

import org.json.JSONException;
import org.json.JSONObject;

public final class CommonUtils {


    public static String getLendMinMoney(){
        String ret = "10000";
        try{
            JSONObject globalConfig = getGlobalConfig();
            if (globalConfig.has("lend_money_min")) {
                ret = globalConfig.getString("lend_money_min");
                return ret;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public static long getGesturesPwdTimerout(){
        long ret = 0;
        try{
            JSONObject globalConfig = getGlobalConfig();
            if (globalConfig.has("gesturesPwdTimerout")) {
                ret = globalConfig.getLong("gesturesPwdTimerout")*1000;
                Log.e("zzy","ret:"+ret);
                return ret;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    public static String getCustomPhone(){
        String ret = "95713";
        try{
            JSONObject globalConfig = getGlobalConfig();
            if (globalConfig.has("customer_phone")) {
                ret = globalConfig.getString("customer_phone");
                return ret;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    /* 在线客服 */
    public static String getCustomUrl(){
        String ret = "http://chat.looyuoms.com/chat/chat/p.do?c=20000232&f=10042034&g=10057671&refer=hengyirongapp";
        try{
            JSONObject globalConfig = getGlobalConfig();
            if (globalConfig.has("customer_url")) {
                ret = globalConfig.getString("customer_url");
                return ret;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    /* 帮助中心 */
    public static String getHelpCenterUrl(){
        String ret = "https://appport.hengyirong.com/help/help_center.html";
        try{
            JSONObject globalConfig = getGlobalConfig();
            if (globalConfig.has("help_url")) {
                ret = globalConfig.getString("help_url");
                return ret;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public static String getCustomEmail(){
        String ret = "hyrservice@credithc.com";
        try{
            JSONObject globalConfig = getGlobalConfig();
            if (globalConfig.has("customer_email")) {
                ret = globalConfig.getString("customer_email");
                return ret;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    private static JSONObject getGlobalConfig() throws JSONException {
        return new JSONObject(
                SPHelper.getString(SPConstants.GLOBAL_CONFIG, ""));
    }
}
