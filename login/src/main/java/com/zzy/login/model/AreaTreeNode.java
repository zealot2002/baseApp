package com.zzy.login.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AreaTreeNode implements Parcelable {
    private String name;
    private int code;
    private boolean isSelected;
    private List<AreaTreeNode> children = new ArrayList<>();
    private AreaTreeNode parent;

    public AreaTreeNode() {
    }

    public AreaTreeNode(String name, int code, AreaTreeNode parent) {
        this.name = name;
        this.code = code;
        this.parent = parent;
        if(parent!=null){
            parent.getChildren().add(this);
        }
    }

    protected AreaTreeNode(Parcel in) {
        name = in.readString();
        code = in.readInt();
        isSelected = in.readByte() != 0;
        children = in.createTypedArrayList(AreaTreeNode.CREATOR);
        parent = in.readParcelable(AreaTreeNode.class.getClassLoader());
    }

    public static final Creator<AreaTreeNode> CREATOR = new Creator<AreaTreeNode>() {
        @Override
        public AreaTreeNode createFromParcel(Parcel in) {
            return new AreaTreeNode(in);
        }

        @Override
        public AreaTreeNode[] newArray(int size) {
            return new AreaTreeNode[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<AreaTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<AreaTreeNode> children) {
        this.children = children;
    }

    public AreaTreeNode getParent() {
        return parent;
    }

    public void setParent(AreaTreeNode parent) {
        this.parent = parent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(code);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeTypedList(children);
        dest.writeParcelable(parent, flags);
    }
}
