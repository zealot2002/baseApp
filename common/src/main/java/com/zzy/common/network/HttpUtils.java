package com.zzy.common.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.zzy.common.constants.HttpConstants;
import com.zzy.common.constants.SPConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.encryptUtils.MD5Utils;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.ScCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.zzy.commonlib.http.HConstant.HTTP_METHOD_POST;

/**
 * @author zzy
 * @date 2018/7/31
 */

public class HttpUtils {
    
    private static final String TAG = "HttpUtils";
    private static final String contentType = "application/json";
    
    private long timeReduce = 0;
    
    private HttpUtils() {
    }
    
    private static class LazyHolder {
        private static final HttpUtils IN = new HttpUtils();
    }
    
    public static HttpUtils getInstance() {
        return LazyHolder.IN;
    }
    
    public void setTimeReduce(long serverTime) {
        long localTime = System.currentTimeMillis() / 1000;
        timeReduce = serverTime - localTime;
    }
    
    public String getAppHeader() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("v", "1.0.0");
            jsonObject.put("appid", "android");
            jsonObject.put("t", (System.currentTimeMillis() / 1000 + timeReduce));
            jsonObject.put("ver", "1.1");
            jsonObject.put("access-token", SPHelper.getString(SPConstants.TOKEN, ""));
//            jsonObject.put("client-info", CommonUtils.getDeviceInfo(AppUtils.getApp()));
            jsonObject.put("client-version", AppUtils.getVersionName());
            jsonObject.put("appsecret", HttpConstants.APP_SECRET_KEY);
            return jsonObject.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    //sign=md5(path+"|"+v+"|"+appid+"|"+appsecret+"|"+t+"|"+access-token+"|"+client-info+"|"+client-version+"|"+request body)
    //sign：/v1/content/get-banners?access-token=3fb0dca8db31c64c3c823e41ffef0a1b354f|1.0.0|android|123456|1552641169036||hrr|4.9.0.27|{"size":640,"apdid":50,"idfa":"ffffffff-c6a0-2392-0000-000000000000"}
    public Map<String, String> warpHeader(String actionName, String body) throws Exception {
        Map<String, String> headerMap = new LinkedHashMap<>(9);
        //appid
        headerMap.put("x-appid", "android");
        //接口版本
        headerMap.put("x-v", "1.0.0");
        
        //客户端时间
        headerMap.put("x-t", String.valueOf(System.currentTimeMillis() / 1000 + timeReduce));
        //协议版本
        headerMap.put("x-ver", "1.1");
        //token
        headerMap.put("x-access-token", SPHelper.getString(SPConstants.TOKEN, ""));
        //设备信息
        headerMap.put("x-client-info", CommonUtils.getDeviceInfo(AppUtils.getApp()));
        headerMap.put("x-client-version", AppUtils.getVersionName());
        headerMap.put("x-client-from", "2");
        
        String sign = actionName
                + "|" + headerMap.get("x-v")
                + "|" + headerMap.get("x-appid")
                + "|" + HttpConstants.APP_SECRET_KEY
                + "|" + headerMap.get("x-t")
                + "|" + headerMap.get("x-access-token")
                + "|" + headerMap.get("x-client-info")
                + "|" + headerMap.get("x-client-version")
                + "|" + body;
        
        headerMap.put("x-sign", MD5Utils.encodeByMD5(sign));
        return headerMap;
    }
    
    /* request */
    public void req(final String actionName, final JSONObject body, final HInterface.DataCallback callback,
                    final HInterface.JsonParser jsonParser) throws Exception {
        String reqBody = body.toString();
        HInterface.Decrypter decrypter = new HInterface.Decrypter() {
            @Override
            public String decrypt(String s) {
                MyLog.e("请求接口 :" + actionName
                        + "\r\n" +
                        "请求参数 :" + body.toString()
                        + "\r\n" +
                        "返回数据 :" + s
                );
                return s;
            }
        };
        RequestCtx.Builder builder = new RequestCtx.Builder(
                HttpConstants.SERVER_ADDRESS + actionName)
                .method(HTTP_METHOD_POST)
                .headerMap(warpHeader(actionName, reqBody))
                .body(reqBody)
                .contentType(contentType)
                .callback(callback)
                .jsonParser(jsonParser)
                .decrypter(decrypter)
                .retryCount(3)
                .interceptor(new CommonInterceptor())
                .timerout(30);
        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("actionName", actionName);
        tagMap.put("body", body.toString());
        tagMap.put("builder", builder);
        
        builder.tagObj(tagMap);
        RequestCtx ctx = builder.build();
        HProxy.getInstance().request(ctx);
    }
    
    public void req(final String actionName, JSONObject body, final HInterface.DataCallback callback,
                    final HInterface.JsonParser jsonParser, String hostAddr) throws Exception {
        String reqBody = body.toString();
        HInterface.Decrypter decrypter = new HInterface.Decrypter() {
            @Override
            public String decrypt(String s) {
                MyLog.e(actionName + " response：" + s);
                return s;
            }
        };
        RequestCtx.Builder builder = new RequestCtx.Builder(hostAddr + actionName)
                .method(HTTP_METHOD_POST)
                .headerMap(warpHeader(actionName, reqBody))
                .body(reqBody)
                .contentType(contentType)
                .callback(callback)
                .jsonParser(jsonParser)
                .decrypter(decrypter)
                .retryCount(3)
                .interceptor(new CommonInterceptor())
                .timerout(30);
        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("actionName", actionName);
        tagMap.put("body", body.toString());
        tagMap.put("builder", builder);
        
        builder.tagObj(tagMap);
        RequestCtx ctx = builder.build();
        HProxy.getInstance().request(ctx);
    }
    
    private ResImageAdapter resImageAdapter;
    
    public interface ResImageAdapter {
        
        Bitmap downLoadBitmap(Context context, String url, ScCallback callback);
        
        void setGif(Context context, ImageView imageView, Object picResource);
        
        void clear(Context context, View view);
        
    }
    
    public ResImageAdapter getResImageAdapter() {
        return resImageAdapter;
    }
    
    public void setResAdapter(ResImageAdapter adapter) {
        this.resImageAdapter = adapter;
    }
}
