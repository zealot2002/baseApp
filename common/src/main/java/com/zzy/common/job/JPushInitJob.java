package com.zzy.common.job;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.zzy.commonlib.log.MyLog;


/**
 * @author zzy
 * @date 2018/9/13
 */
public class JPushInitJob extends Job {

    static final String TAG = "JPushInitJob";

    @Override
    @NonNull
    protected Result onRunJob(@NonNull Params params) {
        MyLog.e(TAG, "start - job");
        try {
//            SCM.getInstance().req(AppUtils.getApp(), ActionConstants.INIT_PUSH_ACTION, new ScCallback() {
//                @Override
//                public void onCallback(boolean b, Bundle bundle, String s) {
//
//                }
//            });
        } catch (Exception e) {
            MyLog.e(TAG, e.toString());
            e.printStackTrace();
            return Result.FAILURE;
        }

        return Result.SUCCESS;
    }

    public static void execute() {

        new JobRequest.Builder(JPushInitJob.TAG)
                .setExecutionWindow(3000L, 4000L)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }

}