package com.zzy.update.model;

/**
 * @author zzy
 * @date 2018/12/16
 */

public class UpdateBean {
    private boolean isForce;
    private String downloadUrl;
    private String versionName;
    private String changeList;


    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getChangeList() {
        return changeList;
    }

    public void setChangeList(String changeList) {
        this.changeList = changeList;
    }
}
