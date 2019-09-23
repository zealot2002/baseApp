package com.zzy.common.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @date 2018/11/30
 */

public class Industry {
    private String name;
    private List<Item> itemList = new ArrayList<>();

    public static class Item{
        String name;
        int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

}
