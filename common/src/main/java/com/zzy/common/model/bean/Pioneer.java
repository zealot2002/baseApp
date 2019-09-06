package com.zzy.common.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Pioneer {
    private int id;
    private String title;
    private String date;
    private String lookNum;
    private boolean isPlaceTop;
    private String from;
    private String content;
    private String likeNum;
    private boolean isLike;
    private String phone;

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

    public String getLookNum() {
        return lookNum;
    }

    public void setLookNum(String lookNum) {
        this.lookNum = lookNum;
    }

    public boolean isPlaceTop() {
        return isPlaceTop;
    }

    public void setPlaceTop(boolean placeTop) {
        isPlaceTop = placeTop;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
