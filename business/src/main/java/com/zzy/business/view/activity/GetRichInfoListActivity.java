package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzy.business.R;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.common.model.bean.GetRichInfo;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.business.view.itemViewDelegate.GetRichInfoDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 致富信息
 */
public class GetRichInfoListActivity extends BaseTitleAndBottomBarActivity implements GetRichInfoContract.View, OnRefreshListener {
    private RecyclerView rvDataList;
    private List<GetRichInfo> dataList = new ArrayList<>();
    private GetRichInfoContract.Presenter presenter;
    private int pageNum = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;
    private SmartRefreshLayout smartRefreshLayout;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("致富信息");

        presenter = new GetRichInfoPresenter(this);
//        presenter.getList(pageNum);
    }

    private void setupViews() {
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);
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
            adapter.addItemViewDelegate(new GetRichInfoDelegate(this));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    GetRichInfo bean = dataList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,bean.getId());
                    if(bean.getType().equals("致富信息")){
                        startActivity(GetRichInfoDetailActivity.class,bundle);
                    }else if(bean.getType().equals("招聘")){
                        startActivity(JobDetailActivity.class,bundle);
                    }else if(bean.getType().equals("买")){
                        startActivity(GoodsDetailBuyActivity.class,bundle);
                    }else if(bean.getType().equals("卖")){
                        startActivity(GoodsDetailSellActivity.class,bundle);
                    }
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_activity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload(true);
    }

    @Override
    public void reload(boolean bShow) {
        reset();
        presenter.getList(pageNum);
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            if(smartRefreshLayout!=null){
                smartRefreshLayout.finishRefresh();
            }
            if(pageNum!=1){
                appendList((List<GetRichInfo>) o);
                return;
            }
            dataList.addAll((List<GetRichInfo>) o);
            setupViews();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    private void appendList(List<GetRichInfo> list) {
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
    public void showError(String s) {
        ToastUtils.showShort(s);
        if(adapter!=null){
            adapter.loadFailed();
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        reload(true);
    }

    private void reset() {
        pageNum = 1;
        isLoadOver = false;
        dataList.clear();
        if(adapter!=null){
            adapter.reset();
        }
    }
}
