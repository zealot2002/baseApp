package com.zzy.common.network;

import android.app.Activity;
import android.os.Bundle;

import com.zzy.common.constants.ParamConstants;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.http.HProxy;
import com.zzy.commonlib.http.RequestCtx;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * @author zzy
 * @date 2019/1/22
 * http://118.26.170.236/pages/viewpage.action?pageId=13538849
 * 200	正常
 * 303	重定向 {"data":{"url":"地址","method":"GET/POST","params":"参数"}}
 * 400	参数错误/处理失败/接口校验失败 ... 具体见接口
 * 401	需要登录
 * 403	没有该接口的访问权限
 * 500	服务器错误
 */

public class CommonInterceptor implements HInterface.Interceptor {

    @Override
    public boolean intercept(long receiveTime, String retString, Object tagObj){
//        try{
//            JSONTokener jsonParser = new JSONTokener(retString);
//            JSONObject obj = (JSONObject) jsonParser.nextValue();
//            int errorCode = obj.getInt("code");
//            if (errorCode == 401) {
//                //need SMS_SCENE_LOGIN
//                SCM.getInstance().req(AppUtils.getTopActivityOrApp(), ActionConstants.CLEAR_LOGIN_DATA_ACTION);
//                Bundle bundle1 = new Bundle();
//                bundle1.putInt(ParamConstants.INDEX, 0);
//                if (AppUtils.getTopActivityOrApp() instanceof Activity) {
//                    ToastUtils.showLong("需要重新登录");
//                    SCM.getInstance().req(AppUtils.getTopActivityOrApp(),
//                            ActionConstants.ENTRY_HOME_ACTIVITY_ACTION, bundle1);
//                }
//                return true;
//            }else if (errorCode == 400) {
//                int subCode = obj.getInt("subcode");
//                if(subCode == 4001){
//                    //客户端时间不对
//                    HttpUtils.getInstance().setTimeReduce(receiveTime);
////                    return true;
//                    Map<String,Object> tagMap = (Map<String, Object>) tagObj;
//                    String actionName = (String) tagMap.get("actionName");
//                    String body = (String) tagMap.get("body");
//                    RequestCtx.Builder builder = (RequestCtx.Builder) tagMap.get("builder");
//                    builder.headerMap(HttpUtils.getInstance().warpHeader(actionName, body));
//
//                    RequestCtx ctx = builder.build();
//
//                    HProxy.getInstance().request(ctx);
//                    return true;
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return false;
    }
}
