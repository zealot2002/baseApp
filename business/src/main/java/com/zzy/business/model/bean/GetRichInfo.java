package com.zzy.business.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class GetRichInfo {
    private String title;
    private String from;
    private String time;
    private String content;
    private String goodNum;
    private String lookNum;

    public GetRichInfo(String title, String from, String time) {
        this.title = title;
        this.from = from;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
