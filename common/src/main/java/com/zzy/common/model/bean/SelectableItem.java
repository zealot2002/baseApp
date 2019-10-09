package com.zzy.common.model.bean;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class SelectableItem {
    private String name;
    private boolean isSelected;

    public SelectableItem() {
    }
    public SelectableItem(String name) {
        this.name = name;
    }
    public SelectableItem(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
