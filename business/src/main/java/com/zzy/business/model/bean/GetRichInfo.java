package com.zzy.business.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class GetRichInfo {
    private String title;
    private String from;
    private String date;
    private String content;
    private String goodNum;
    private String lookNum;

    public GetRichInfo(String title, String from, String date) {
        this.title = title;
        this.from = from;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(String goodNum) {
        this.goodNum = goodNum;
    }

    public String getLookNum() {
        return lookNum;
    }

    public void setLookNum(String lookNum) {
        this.lookNum = lookNum;
    }
}
