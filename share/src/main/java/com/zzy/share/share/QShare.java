package com.zzy.share.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zzy.share.R;
import com.zzy.share.ShareListener;
import com.zzy.share.model.IShareModel;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.zzy.share.utils.Utils.isQQClientAvailable;


/**
 * Created by haoran on 2018/8/7.
 */

public class QShare {


    private Activity mActivity;
    private Tencent tencent;
    private boolean isShareToQQ;
    private ShareListener shareListener;
    private AtomicBoolean init = new AtomicBoolean(false);


    private IUiListener mIUiListener = new IUiListener() {

        @Override
        public void onComplete(Object o) {
            if (shareListener != null) {
                shareListener.onShareSuccess();
            }
        }

        @Override
        public void onError(UiError uiError) {
            if (shareListener != null) {
                shareListener.onShareFail();
            }
        }

        @Override
        public void onCancel() {
            if (shareListener != null) {
                shareListener.onShareCancel();
            }
        }
    };


    public QShare(Activity activity, String appId, boolean shareToQQ,ShareListener listener) {
        this.isShareToQQ = shareToQQ;
        this.shareListener = listener;
        this.mActivity = activity;
        if (isQQClientAvailable(activity)) {
            try {
                tencent = Tencent.createInstance(appId, activity);
                init.set(true);
            } catch (Exception e) {
                init.set(false);
                listener.onSdkSetupError(e.getMessage());
                e.printStackTrace();
            }
        }else {
            init.set(false);
            listener.onSdkSetupError(mActivity.getResources().getString(R.string.qq_uninstall));
        }

    }

    public void onActivityResultData(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
    }

    public void sendWebShareMessage(IShareModel share) {

        if (!init.get()) return;
        final String title = share.getTitle();
        final String content = share.getContent();
        String actionUrl = share.getActionUrl();
        final String imgUrl = share.getImgUrl();
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        if (isShareToQQ) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(TextUtils.isEmpty(actionUrl)){
            actionUrl = "https://www.hengyirong.com/";
            Toast.makeText(mActivity, "QQ分享链接不能为空", Toast.LENGTH_SHORT).show();
        }
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, actionUrl);
        tencent.shareToQQ(mActivity, params, mIUiListener);

    }

    private void sendTextMessage(IShareModel share) {

        if (!init.get()) return;
        final String title = share.getTitle();
        final String content = share.getContent();
        String actionUrl = share.getActionUrl();
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        if (isShareToQQ) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        if(TextUtils.isEmpty(actionUrl)){
            actionUrl = "https://www.hengyirong.com/";
            Toast.makeText(mActivity, "QQ分享链接不能为空", Toast.LENGTH_SHORT).show();
        }
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, actionUrl);
        tencent.shareToQQ(mActivity, params, mIUiListener);

    }


    public void sendImageMessage(IShareModel share) {

        if (!init.get()) return;
        final String title = share.getTitle();
        final String imgUrl = share.getImgUrl();
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, title);
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        if (isShareToQQ) {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        tencent.shareToQQ(mActivity, params, mIUiListener);

    }

}
