package com.zzy.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData = new ArrayList<>();
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = datas;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {

        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void appendData(T... datas) {
        Collections.addAll(mData, datas);
        notifyDataSetChanged();
    }

    public void appendData(List<T> data) {
        mData.addAll(data);
    }

    public void addFirst(List<T> data) {
        addItem(0, data);
    }

    public void addFirst(T... datas) {
        addItem(0, datas);
    }


    public void addLast(T... datas) {
        appendData(datas);
    }


    public void addLast(List<T> data) {
        appendData(data);
    }


    public void addItem(int index, T... datas) {
        mData.addAll(index, array2ArrayList(datas));
        notifyDataSetChanged();
    }

    public void addItem(int index, List<T> data) {
        mData.addAll(index, data);
        notifyDataSetChanged();
    }


    public boolean contain(T data) {
        return mData.contains(data);
    }


    public boolean contains(List<T> data) {
        return mData.contains(data);
    }

    public void remove(T data) {
        mData.remove(data);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void removes(List<T> data) {
        mData.removeAll(data);
        notifyDataSetChanged();
    }


    public int index(T data) {
        return mData.indexOf(data);
    }

    public List<T> array2ArrayList(T... objects) {
        return new ArrayList<T>(Arrays.asList(objects));
    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);


}
