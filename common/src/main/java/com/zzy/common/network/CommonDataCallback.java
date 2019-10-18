package com.zzy.common.network;


import com.zzy.common.R;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

abstract public class CommonDataCallback implements HInterface.DataCallback {

    @Override
    public void requestCallback(int result, Object data, Object tagData) {
        if (result == HConstant.FAIL) {
            ToastUtils.showShort(" "+data.toString());
        } else if (result == HConstant.ERROR) {
            ToastUtils.showShort((String)data);
        }else if (result == HConstant.INTERCEPTED) {
            //do nothing
        }
        callback(result, data, tagData);
    }

    public abstract void callback(int result, Object data, Object tagData);
}
