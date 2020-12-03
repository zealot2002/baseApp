package com.zzy.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.meituan.android.walle.WalleChannelReader;
import com.zzy.common.constants.SPConstants;
import com.zzy.flysp.core.spHelper.SPHelper;

import org.json.JSONException;
import org.json.JSONObject;

public final class CommonUtils {
    //"MI 5;xiaomi;99000855259808"
    public static String getDeviceInfo(Context context) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(android.os.Build.MODEL);
        sb.append(";");
        sb.append(WalleChannelReader.getChannel(context));
        sb.append(";");
        sb.append(getImei(context));
        sb.append(";");
        sb.append(android.os.Build.VERSION.RELEASE);
        return sb.toString();
    }

    public static final String getImei(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if(imei == null) {
                imei = "";
            }
            return imei;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
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
