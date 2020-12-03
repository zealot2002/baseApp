package com.zzy.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.zhy.autolayout.utils.ScreenUtils;
import com.zzy.common.constants.BroadcastConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.constants.SPConstants;
import com.zzy.common.network.HttpUtils;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.common.constants.ActionConstants;
import com.zzy.web.constants.H5ActionConstants;
import com.zzy.web.utils.InnerUtils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

public class AndroidInterface {

    /*一些指令，移交Activity去做*/
    interface AndroidInterfaceListener {
        void onSendMsg(String msg);
    }

    private static final String TAG = "AndroidInterface";
    private Context context;
    private AndroidInterfaceListener androidInterfaceListener;
    private HashMap<String, String> cacheMap = new HashMap<>();

    /******************************************************************************************************************/
    public AndroidInterface(Context context) {
        this.context = context;
    }

    public void setAndroidInterfaceListener(AndroidInterfaceListener androidInterfaceListener) {
        this.androidInterfaceListener = androidInterfaceListener;
    }

    @JavascriptInterface
    public String sendMsg(final String msg) {
        Log.e(TAG, "H5 call sendMsg msg:" + msg);
        try {
            JSONTokener jsonParser = new JSONTokener(msg);
            JSONObject obj = (JSONObject) jsonParser.nextValue();
            String action = obj.getString("action");
            if (action.equals(H5ActionConstants.CLOSE_CUR_WIN)) {
                if (context != null) {
                    ((Activity) context).finish();
                }
            } else if (action.equals(H5ActionConstants.GET_USER_ID)
                    || action.equals(H5ActionConstants.GET_TOKEN)
            ) {
                String token = SPHelper.getString(SPConstants.TOKEN, "");
                Log.e(TAG, "token:" + token);
                return token;
            } else if (action.equals(H5ActionConstants.GET_PLATFORM)) {
                return "android";
            } else if (action.equals(H5ActionConstants.GET_SCREEN_WIDTH)) {
                int[] ints = ScreenUtils.getScreenSize(context,true);
                return ints[0]+"";
            } else if (action.equals(H5ActionConstants.SHOW_SHARE_WIN)) {
                InnerUtils.showShare(context,
                        obj.getString("title"),
                        obj.getString("content"),
                        obj.getString("imgUrl"),
                        obj.getString("targetUrl"));
            } else if (action.equals(H5ActionConstants.OPEN_WIN)) {
                String winName = obj.getString("winName");
                if (winName.equals("productDetail")) {
                    String id = obj.getString("id");
                    Bundle bundle = new Bundle();
                    bundle.putString(ParamConstants.ID, id);
                    SCM.getInstance().req(context, ActionConstants.ENTRY_DLB_DETAIL_ACTIVITY_ACTION, bundle);
                } else if (winName.equals("login")) {
                    SCM.getInstance().req(context, ActionConstants.ENTRY_LOGIN_ACTIVITY_ACTION);
                } else if (winName.equals("productFragment")) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.INDEX, 1);
                    SCM.getInstance().req(context,
                            ActionConstants.ENTRY_HOME_ACTIVITY_ACTION, bundle);
                }
            } else if (action.equals(H5ActionConstants.NOTIFY_SOME_RESULT)) {
                String type = obj.getString("type");
                String result = obj.getString("result");
                handleNotifySomeResult(type, result);
            } else if (action.equals(H5ActionConstants.OPEN_FREE_AUTHORIZE)) {
                SCM.getInstance().req(context, ActionConstants.OPEN_FREE_AUTHORIZE_ACTION);
            } else if (action.equals(H5ActionConstants.GET_APP_HEADER)) {
                return HttpUtils.getInstance().getAppHeader();
            } else if (action.equals(H5ActionConstants.SET_TITLEBAR_RIGHT_ICON)) {
                if (androidInterfaceListener != null) {
                    androidInterfaceListener.onSendMsg(msg);
                }
            } else if (action.equals(H5ActionConstants.SET_DATA)) {
                String key = obj.getString("key");
                String value = obj.getString("value");
                cacheMap.put(key, value);
            } else if (action.equals(H5ActionConstants.GET_DATA)) {
                String key = obj.getString("key");
                if (cacheMap.containsKey(key)) {
                    return cacheMap.get(key);
                }
                return "";
            } else if (action.equals(H5ActionConstants.GET_TODAY_STEP_NUM)) {

                final StringBuilder sb = new StringBuilder();
                SCM.getInstance().req(context, ActionConstants.GET_STEP_NUM_ACTION,
                        new ScCallback() {
                            @Override
                            public void onCallback(boolean b, Bundle bundle, String s) {
                                if (bundle != null) {
                                    sb.append(bundle.getString(ParamConstants.DATA, ""));
                                }
                            }
                        });
                return sb.toString();
            } else {
                return "unknown action!!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void handleNotifySomeResult(String type, String result) {
        if (type == null || result == null) {
            return;
        }
        Intent intent = new Intent();

        if (type.equals("charge")
                && result.equals("success")) {
            intent.setAction(BroadcastConstants.CHARGE_SUCCESS);
        } else if (type.equals("withdraw")
                && result.equals("success")) {
            intent.setAction(BroadcastConstants.WITHDRAW_SUCCESS);
        } else if (type.equals("modifyFreeAuthorize")
                && result.equals("success")) {
            intent.setAction(BroadcastConstants.FREE_AUTHORIZE_SUCCESS);
        }
        context.sendBroadcast(intent);
    }
}
