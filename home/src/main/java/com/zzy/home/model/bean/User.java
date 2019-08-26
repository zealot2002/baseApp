package com.zzy.home.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class User {
    private String id;
    private String name;
    private String score;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
