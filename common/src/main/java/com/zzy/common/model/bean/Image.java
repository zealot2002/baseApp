package com.zzy.common.model.bean;

import java.io.Serializable;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Image implements Serializable {
    private String name;
    private String path;
    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
