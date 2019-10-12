package com.zzy.common.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Goods implements Serializable {
    private int id;
    private String name;
    private String price;
    private String phone;
    private String contact;
    private float score;
    private String address;
    private String desc;
    private String dealWay;
    private String startPrice;
    private String endPrice;
    private List<Image> imgList;
    private List<Comment> commentList;
    public Goods() {
        imgList = new ArrayList<>();
        commentList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDealWay() {
        return dealWay;
    }

    public void setDealWay(String dealWay) {
        this.dealWay = dealWay;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public List<Image> getImgList() {
        return imgList;
    }

    public void setImgList(List<Image> imgList) {
        this.imgList = imgList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
