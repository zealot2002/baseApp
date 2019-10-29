package com.zzy.common.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Pioneering {
    private int id;
    private String title;
    private String date;
    private String content;
    private String contact;
    private String phone;
    private String headUrl;
    private String state;
    private List<String> skills = new ArrayList<>();

    public Pioneering() {
    }

    public Pioneering(String phone, String contact, String content) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
