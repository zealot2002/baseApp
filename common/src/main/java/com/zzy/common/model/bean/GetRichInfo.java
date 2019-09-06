package com.zzy.common.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class GetRichInfo {
    private int id;
    private String title;
    private boolean isPlaceTop;
    private String from;
    private String date;
    private String content;
    private String likeNum;
    private String lookNum;
    private boolean isLike;
    private String type;

    public GetRichInfo() {
    }

    public GetRichInfo(String title, String from, String date) {
        this.title = title;
        this.from = from;
        this.date = date;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isPlaceTop() {
        return isPlaceTop;
    }

    public void setPlaceTop(boolean placeTop) {
        isPlaceTop = placeTop;
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

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getLookNum() {
        return lookNum;
    }

    public void setLookNum(String lookNum) {
        this.lookNum = lookNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
