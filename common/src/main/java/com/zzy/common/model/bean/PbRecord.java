package com.zzy.common.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class PbRecord {
    private String name;
    private String imgUrl;
    private String remarks;
    private String phone;

    public PbRecord() {
    }

    public PbRecord(String name, String imgUrl, String remarks, String phone) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.remarks = remarks;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
