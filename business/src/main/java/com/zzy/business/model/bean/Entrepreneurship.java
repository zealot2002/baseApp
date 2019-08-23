package com.zzy.business.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Entrepreneurship {
    private String phone;
    private String contact;
    private String content;

    public Entrepreneurship(String phone, String contact, String content) {
        this.phone = phone;
        this.contact = contact;
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
