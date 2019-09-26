package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zzy.business.R;
import com.zzy.business.contract.MyLogContract;
import com.zzy.business.presenter.MyLogPresenter;
import com.zzy.business.view.itemViewDelegate.LogDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.model.bean.Log;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的创业日志
 */
public class MyLogListActivity extends BaseTitleAndBottomBarActivity
        implements MyLogContract.View, View.OnClickListener {
    private Button btnNew;
    private RecyclerView rvDataList;
    private List<Log> dataList = new ArrayList<>();
    private MyLogContract.Presenter presenter;
    private int pageNum = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的创业日志");
        presenter = new MyLogPresenter(this);
        presenter.getList(pageNum);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_with_btn_activity;
    }

    private void setupViews() {
        if(rvDataList == null){
            btnNew = findViewById(R.id.btnNew);
            btnNew.setText("创建新日志");
            btnNew.setOnClickListener(this);

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
            adapter.addItemViewDelegate(new LogDelegate(this));
            rvDataList.setAdapter(adapter);
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            if(pageNum!=1){
                appendList((List<Log>) o);
                return;
            }
            dataList.addAll((List<Log>) o);
            setupViews();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    private void appendList(List<Log> list) {
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
        if(v.getId() == R.id.btnNew){
            startActivity(MyLogNewActivity.class);
        }
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
