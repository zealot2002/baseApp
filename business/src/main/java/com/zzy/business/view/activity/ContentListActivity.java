package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.common.model.bean.Content;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.business.view.itemViewDelegate.ContentDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.common.model.bean.GetRichInfo;
import com.zzy.common.widget.recycleAdapter.MyLinearLayoutManager;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创业求助
 */
public class ContentListActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , ContentContract.View, OnRefreshListener {
    private Button btnNew;
    private List<Content> dataList = new ArrayList<>();
    private ContentContract.Presenter presenter;
    private int pageNum = 1;
    private RecyclerView rvDataList;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false,isReload = true;
    private int type;
    private SmartRefreshLayout smartRefreshLayout;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            type = getIntent().getIntExtra(ParamConstants.TYPE, CommonConstants.CONTENT_HELP);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(CommonConstants.CONTENT_HELP == type){
            setTitle("创业求助");
        }else if(CommonConstants.CONTENT_IDEA == type){
            setTitle("创业点子");
        }else if(CommonConstants.CONTENT_EXPERIENCE == type){
            setTitle("分享经验");
        }
        presenter = new ContentPresenter(this);
//        presenter.getList(type,pageNum);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_with_btn_activity;
    }

    private void setupViews() {
        if(rvDataList == null){
            smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
            smartRefreshLayout.setEnableRefresh(true);
            smartRefreshLayout.setOnRefreshListener(this);

            btnNew = findViewById(R.id.btnNew);
            btnNew.setOnClickListener(this);
            if(CommonConstants.CONTENT_HELP == type){
                btnNew.setText("发布求助问题");
            }else if(CommonConstants.CONTENT_IDEA == type){
                btnNew.setText("我要发布");
            }else if(CommonConstants.CONTENT_EXPERIENCE == type){
                btnNew.setText("分享个人经验");
            }
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new MyLinearLayoutManager(this);
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
                        presenter.getList(type,pageNum);
                    }else{
                        presenter.getList(type,++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new ContentDelegate(type));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,dataList.get(position).getId());
                    bundle.putInt(ParamConstants.TYPE,type);
                    startActivity(ContentDetailActivity.class,bundle);
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload(true);
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            if(smartRefreshLayout!=null){
                smartRefreshLayout.finishRefresh();
            }
            List<Content> list = (List<Content>) o;
            if(list == null){
                return;
            }
            setupViews();
            if(isReload){
                isReload = false;
                reset();
                if(rvDataList!=null){
                    rvDataList.scrollToPosition(0);
                }

                dataList.addAll(list);
                adapter.notifyDataSetChanged();
            }else {
                adapter.setLoadMoreData(list);
            }

            if(list.isEmpty()
                    ||list.size()< CommonConstants.PAGE_SIZE
            ){
                smartRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.loadEnd();
                    }
                },10);
                isLoadOver = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
//    private void appendList(List<Content> list) {
//        if(list == null
//                ||list.isEmpty()
//        ){
//            adapter.loadEnd();
//        }
//        if(list.isEmpty()){
//            isLoadOver = true;
//            return;
//        }
//        adapter.setLoadMoreData(list);
//    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            Bundle bundle = new Bundle();
            bundle.putInt(ParamConstants.TYPE,type);
            startActivity(ContentNewActivity.class,bundle);
        }
    }

    @Override
    public void reload(boolean bShow) {
        isReload = true;
        pageNum = 1;
        presenter.getList(type,pageNum);
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
    private void reset() {
        pageNum = 1;
        isLoadOver = false;
        dataList.clear();
        if(adapter!=null) {
            adapter.reset();
        }
    }
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        reload(true);
    }
}
