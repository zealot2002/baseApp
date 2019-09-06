package com.zzy.common.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Image {
    private String name;
    private String path;

    public Image() {
    }

    public Image(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
