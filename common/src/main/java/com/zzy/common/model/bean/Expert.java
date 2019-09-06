package com.zzy.common.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Expert {
    private String name;
    private String introduction;
    private String phone;

    public Expert(String name, String introduction, String phone) {
        this.name = name;
        this.introduction = introduction;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
