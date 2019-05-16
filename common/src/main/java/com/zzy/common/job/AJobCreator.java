package com.zzy.common.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * @author zzy
 * @date 2018/9/13
 */
public class AJobCreator implements JobCreator {

    @Override
    @Nullable
    public Job create(@NonNull String tag) {
        switch (tag) {
            case JPushInitJob.TAG:
                return new JPushInitJob();

            case H5ProtocolsInitJob.TAG:
                return new H5ProtocolsInitJob();
            default:
                return null;
        }
    }
}