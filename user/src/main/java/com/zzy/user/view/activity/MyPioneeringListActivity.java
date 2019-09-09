package com.zzy.user.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.Job;
import com.zzy.common.model.bean.Pioneering;
import com.zzy.common.view.itemViewDelegate.PioneeringDelegate;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.user.R;
import com.zzy.user.contract.MyPioneeringContract;
import com.zzy.user.presenter.MyPioneeringPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的招聘
 */
public class MyPioneeringListActivity extends BaseTitleAndBottomBarActivity
        implements MyPioneeringContract.View, View.OnClickListener {
    private RecyclerView rvDataList;
    private List<Pioneering> dataList = new ArrayList<>();
    private MyPioneeringContract.Presenter presenter;
    private int pageNum = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的招聘");
        presenter = new MyPioneeringPresenter(this);
        presenter.getList(pageNum);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_activity;
    }

    private void setupViews() {
        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            adapter = new MyMultiRecycleAdapter(this,dataList,true);
            //设置不满一屏幕，自动加载第二页
            adapter.openAutoLoadMore();
            //加载更多的事件监听
            onLoadMoreListener = new OnLoadMoreListener() {
                @Override
                public void onLoadMore(boolean isReload) {
                    if(isLoadOver){
                        return;
                    }
                    if(isReload){
                        presenter.getList(pageNum);
                    }else{
                        presenter.getList(++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new PioneeringDelegate(this));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,dataList.get(position).getId());
                    startActivity(MyPioneeringDetailActivity.class,bundle);
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            if(pageNum!=1){
                appendList((List<Pioneering>) o);
                return;
            }
            dataList.addAll((List<Pioneering>) o);
            setupViews();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    private void appendList(List<Pioneering> list) {
        if(list == null
                ||list.isEmpty()
        ){
            adapter.loadEnd();
        }
        if(list.isEmpty()){
            isLoadOver = true;
            return;
        }
        adapter.setLoadMoreData(list);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void reload(boolean bShow) {
        presenter.getList(pageNum);
    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
        if(adapter!=null){
            adapter.loadFailed();
        }
    }

    @Override
    public void onSuccess() {

    }
}
