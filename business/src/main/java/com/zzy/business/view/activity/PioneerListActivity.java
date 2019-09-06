package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzy.business.R;
import com.zzy.business.contract.PioneerContract;
import com.zzy.common.model.bean.Menu;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.business.presenter.PioneerPresenter;
import com.zzy.business.view.adapter.GridMenuListAdapter;
import com.zzy.business.view.itemViewDelegate.PioneerDelegate;
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
 * 创业先锋列表
 */
public class PioneerListActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, PioneerContract.View {
    private PioneerContract.Presenter presenter;
    private RecyclerView rvMenuList,rvDataList;

    //menu
    private List<Menu> menuList;
    private GridMenuListAdapter gridMenuListAdapter;
    private int menuIndex = 0;
    //data
    private int pageNum = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;
    private List<Pioneer> dataList = new ArrayList<>();
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业先锋");

        presenter = new PioneerPresenter(this);
        presenter.getTypeList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pioneer_list_activity;
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
                        presenter.getList(menuList.get(menuIndex).getName(),pageNum);
                    }else{
                        presenter.getList(menuList.get(menuIndex).getName(),++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new PioneerDelegate(this));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,dataList.get(position).getId());
                    bundle.putString(ParamConstants.TYPE,menuList.get(menuIndex).getName());
                    startActivity(PioneerDetailActivity.class,bundle);
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
                appendList((List<Pioneer>) o);
                return;
            }
            dataList.addAll((List<Pioneer>) o);
            setupViews();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    private void appendList(List<Pioneer> list) {
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

    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void updateMenuList(final List<Menu> menuList) {
        this.menuList = menuList;
        if(rvMenuList == null){
            rvMenuList = findViewById(R.id.rvMenuList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,4);
            rvMenuList.setLayoutManager(layoutManager);
            rvMenuList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            gridMenuListAdapter = new GridMenuListAdapter(this);
            rvMenuList.setAdapter(gridMenuListAdapter);
            gridMenuListAdapter.setOnItemClickedListener(new GridMenuListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    refreshMenu(position);
                    pageNum = 1;
                    dataList.clear();
                    presenter.getList(menuList.get(menuIndex).getName(),pageNum);

//                    //todo  get data
//                    for(int i=0;i<menuList.size();i++){
//                        menuList.get(i).setSelected(i==position?true:false);
//                    }
//                    adapter.notifyDataSetChanged();
                }
            });
        }
        gridMenuListAdapter.swapData(menuList);
    }

    private void refreshMenu(int position) {
        menuIndex = position;
        for(int i=0;i<menuList.size();i++){
            menuList.get(i).setSelected(i==position?true:false);
        }
        gridMenuListAdapter.swapData(menuList);
    }
}
