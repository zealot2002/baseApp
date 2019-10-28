package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzy.business.R;
import com.zzy.business.contract.PioneerContract;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.model.bean.Menu;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.business.presenter.PioneerPresenter;
import com.zzy.business.view.adapter.GridMenuListAdapter;
import com.zzy.business.view.itemViewDelegate.PioneerDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.PioneerService;
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
    private static final int REQUEST_CODE = 500;
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
    private boolean isLoadOver = false,isReload = true;
    private List<Pioneer> dataList = new ArrayList<>();
    private int curPosition = 0;
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
                    curPosition = position;
                    Intent it = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,dataList.get(position).getId());
                    bundle.putString(ParamConstants.TYPE,menuList.get(menuIndex).getName());
                    it.setClass(PioneerListActivity.this,PioneerDetailActivity.class);
                    it.putExtras(bundle);
                    startActivityForResult(it,REQUEST_CODE);
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE == requestCode && resultCode == RESULT_OK){
            if (data != null) {
                String lookNum = data.getStringExtra(ParamConstants.COUNT);
                dataList.get(curPosition).setLookNum(lookNum);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            List<Pioneer> list = (List<Pioneer>) o;
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
                rvDataList.postDelayed(new Runnable() {
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

    @Override
    public void onClick(View v) {
    }

    @Override
    public void reload(boolean bShow) {
        isReload = true;
        pageNum = 1;
        presenter.getList(menuList.get(menuIndex).getName(),pageNum);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(menuList!=null){
//            reload(true);
//        }
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
                    isReload = true;
                    pageNum = 1;
                    presenter.getList(menuList.get(menuIndex).getName(),pageNum);
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


    private void reset() {
        pageNum = 1;
        isLoadOver = false;
        dataList.clear();
        if(adapter!=null){
            adapter.reset();
        }
    }
}
