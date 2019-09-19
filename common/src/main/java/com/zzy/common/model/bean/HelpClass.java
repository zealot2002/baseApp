package com.zzy.common.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 帮扶
 */

public class HelpClass {
    private int id;
    private String title;
    private String time;
    private String address;
    private String teacher;
    private String content;
    private List<Parter> parterList;
    private int parterNum;
    private String isJoined;

    public HelpClass() {
        parterList = new ArrayList<>();
    }

    public static class Parter{
        public String headUrl;
        public String id;

        public Parter(String headUrl, String id) {
            this.headUrl = headUrl;
            this.id = id;
        }
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Parter> getParterList() {
        return parterList;
    }

    public void setParterList(List<Parter> parterList) {
        this.parterList = parterList;
    }

    public int getParterNum() {
        return parterNum;
    }

    public void setParterNum(int parterNum) {
        this.parterNum = parterNum;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }
}
