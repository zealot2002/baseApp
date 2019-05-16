package com.zzy.common.job;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.scm.ActionConstants;


/**
 * @author zzy
 * @date 2018/9/13
 */
public class ShareInitJob extends Job {

    static final String TAG = "ShareInitJob";

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        MyLog.e(TAG,"start - job");
        try {
            SCM.getInstance().req(AppUtils.getApp(), ActionConstants.INIT_SHARE_ACTION, new ScCallback() {
                @Override
                public void onCallback(boolean b, Bundle bundle, String s) {

                }
            });
        } catch (Exception e) {
            MyLog.e(TAG,e.toString());
            e.printStackTrace();
            return Result.FAILURE;
        }

        return Result.SUCCESS;
    }

    public static void execute() {

        new JobRequest.Builder(ShareInitJob.TAG)
                .setExecutionWindow(4000L, 5000L)
                .setRequirementsEnforced(true)
                .build()
                .schedule();

    }

}