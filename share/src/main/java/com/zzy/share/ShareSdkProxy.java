package com.zzy.share;

import android.app.Activity;
import android.content.Context;

import com.zzy.share.model.AppId;
import com.zzy.share.model.ShareBean;
import com.zzy.share.presenter.SharePresenter;
import com.zzy.share.utils.SerializeUtils;
import com.zzy.share.view.IShareView;
import com.zzy.share.view.ShareDialog;
import com.zzy.share.model.AppId;
import com.zzy.share.utils.SerializeUtils;

import static com.zzy.share.utils.Utils.getSerializePath;
import static com.zzy.share.utils.Utils.getSerializePath;

/**
 * Created by haoran on 2018/8/3.
 */

public class ShareSdkProxy implements IShareSdkProxy {

    private ShareSdkProxy() {
    }

    public interface OnShareClickListener {
        void onShareClick(@ShareChannel int channel);
    }

    private static class Holder {
        private static final ShareSdkProxy IN = new ShareSdkProxy();
    }


    public static ShareSdkProxy getInstance() {
        return Holder.IN;
    }

    @Override
    public void init(Context context, String[] appIds) {

        if (context != null && appIds != null && appIds.length == 3) {
            try {
                SerializeUtils.serialization(getSerializePath(context), new AppId(appIds[0], appIds[1], appIds[2]));
            } catch (Exception e) {
                throw new RuntimeException(" ShareSdk serialized error： " + e);
            }

        } else {
            throw new RuntimeException(" ShareSdk init error ");
        }

    }

    @Override
    public IShareView createShareDialog(Context context,int[] shareChannel, int column) {
        return ShareDialog.get().createShareDialog(context, shareChannel, column);
    }


    /**
     * @param dialog    dialog
     * @param activity  the current activity to accept the result of the share sdk
     * @param shareBean the model of share function
     */
    @Override
    public void setOnShareClickListener(final IShareView dialog, final Activity activity, final ShareBean shareBean) {
        dialog.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onShareClick(int channel) {
                switch (channel) {
                    case ShareChannel.CHANNEL_WECHAT:
                        SharePresenter.start(activity).onShareWx(shareBean);
                        break;
                    case ShareChannel.CHANNEL_WECHAT_MOMENT:
                        SharePresenter.start(activity).onShareWxCircle(shareBean);
                        break;
                    case ShareChannel.CHANNEL_QQ:
                        SharePresenter.start(activity).onShareQQ(shareBean);
                        break;
                    case ShareChannel.CHANNEL_QQ_ZONE:
                        SharePresenter.start(activity).onShareQZone(shareBean);
                        break;
                    case ShareChannel.CHANNEL_WEIBO:
                        SharePresenter.start(activity).onShareWeiBo(shareBean);
                        break;
                    case ShareChannel.CHANNEL_MORE:
                        break;
                    case ShareChannel.CHANNEL_CANNEL:
                        break;

                }
            }
        });

    }


}
