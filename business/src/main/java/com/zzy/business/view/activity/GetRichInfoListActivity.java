package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzy.business.R;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.business.view.adapter.GetRichInfoListAdapter;
import com.zzy.business.view.itemViewDelegate.GetRichInfoDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 致富信息
 */
public class GetRichInfoListActivity extends BaseTitleAndBottomBarActivity implements GetRichInfoContract.View {
    private RecyclerView rvDataList;
    private List<GetRichInfo> dataList = new ArrayList<>();
    private GetRichInfoContract.Presenter presenter;
    private int pageNum = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("致富信息");

        presenter = new GetRichInfoPresenter(this);
        presenter.getRichInfoList(pageNum);
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
                        presenter.getRichInfoList(pageNum);
                    }else{
                        presenter.getRichInfoList(++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new GetRichInfoDelegate(this));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,dataList.get(position).getId());
                    startActivity(GetRichInfoDetailActivity.class,bundle);
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_activity;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        reload(true);
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getRichInfoList(pageNum);
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
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
    public void onLikeSuccess() {

    }
}
