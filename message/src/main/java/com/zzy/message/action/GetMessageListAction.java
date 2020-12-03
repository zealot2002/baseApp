package com.zzy.message.action;

import android.content.Context;
import android.os.Bundle;

import com.zzy.message.model.HttpProxy;
import com.zzy.annotations.ScActionAnnotation;
import com.zzy.common.bean.NoticeBean;
import com.zzy.common.constants.ActionConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import java.util.ArrayList;

/**
 * @author zzy
 * @date 2018/8/13
 */
@ScActionAnnotation(ActionConstants.GET_MESSAGE_LIST_ACTION)
public class GetMessageListAction implements ScAction {

    @Override
    public void invoke(Context context, final Bundle bundle, String s, final ScCallback scCallback) {
        try {
            HttpProxy.getNoticeList(new HInterface.DataCallback() {
                @Override
                public void requestCallback(int result, Object data, Object tagData) {
                    if (result == HConstant.SUCCESS) {
                        try {
                            ArrayList<NoticeBean> object = (ArrayList<NoticeBean>) data;
                            Bundle ret = new Bundle();
                            ret.putSerializable(ParamConstants.OBJECT,object);
                            if(scCallback!=null)
                                scCallback.onCallback(true, ret, "");
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(scCallback!=null)
                                scCallback.onCallback(false, null, e.toString());
                        }
                    }else if(result == HConstant.INTERCEPTED){

                    } else {
                        if(scCallback!=null)
                            scCallback.onCallback(false, null, (String) data);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if(scCallback!=null)
                scCallback.onCallback(false, null, e.toString());
        }
    }
}
