package com.zzy.common.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Content {
    private int id;
    private int userId;
    private String userName;
    private String userHeadUrl;
    private String location;

    private String title;
    private boolean isPlaceTop;
    private String from;
    private String date;
    private String content;
    private String likeNum;
    private String lookNum;
    private String replyNum;
    private boolean isLike;
    private String type;
    private List<Image> imgList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();

    public Content() {
    }

    public Content(String title, String from, String date) {
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

    public List<Image> getImgList() {
        return imgList;
    }

    public void setImgList(List<Image> imgList) {
        this.imgList = imgList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }
}
