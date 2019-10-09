package com.zzy.update.action;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.zzy.annotations.ScActionAnnotation;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.UpdateBean;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.ScAction;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.servercentre.ActionConstants;
import com.zzy.update.DownloadService;
import com.zzy.update.widget.UpdateDialog;

/**
 * @author zzy
 * @date 2018/8/13
 */
@ScActionAnnotation(ActionConstants.CHECK_UPDATE_ACTION)
public class CheckUpdateAction implements ScAction {

    private static final String TAG = "CheckUpdateAction";

    @Override
    public void invoke(final Context context, Bundle bundle, String s, ScCallback scCallback) {
        try {
            HttpProxy.checkVersion(new HInterface.DataCallback() {
                @Override
                public void requestCallback(int result, Object o, Object o1) {
                    if (result == HConstant.SUCCESS) {
                        UpdateBean updateBean = (UpdateBean) o;
                        if (updateBean.getDownloadUrl() == null
                                || updateBean.getDownloadUrl().isEmpty()
                        ) {
                            ToastUtils.showShort("更新地址为空，暂时无法更新");
                            return;
                        }
                        showUpdateDialog((Activity) context, updateBean);
                    } else {
//                        ToastUtils.showShort((String) o);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog(final Activity activity, final UpdateBean updateBean) {
        UpdateDialog.Builder builder = new UpdateDialog.Builder(activity)
                .changeList(updateBean.getChangeList())
                .versionName(updateBean.getVersionName())
                .isForce(updateBean.isForce())
                .onUpdateListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ToastUtils.showLong("正在后台下载中，\n\r稍后会自动安装");
                        Intent intent = new Intent(activity, DownloadService.class);
                        intent.putExtra(ParamConstants.URL, updateBean.getDownloadUrl());
                        activity.startService(intent);
                    }
                });
        builder.create().show();
    }
}
