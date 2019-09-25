package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zzy.business.R;
import com.zzy.business.contract.GoodsContract;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.model.bean.Goods;
import com.zzy.business.presenter.GoodsPresenter;
import com.zzy.business.view.itemViewDelegate.GoodsDelegate;
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
 * 我要买|卖东西列表
 */
public class GoodsListActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , GoodsContract.View {
    private Button btnNew;
    private int goodType = 0;
    private RecyclerView rvDataList;
    private List<Goods> dataList = new ArrayList<>();
    private GoodsContract.Presenter presenter;
    private int pageNum = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            goodType = getIntent().getIntExtra(ParamConstants.TYPE,CommonConstants.GOODS_BUY);
            presenter = new GoodsPresenter(this);
            presenter.getList(goodType,pageNum);
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_with_btn_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        if(goodType == CommonConstants.GOODS_BUY){
            setTitle("我要买东西");
            btnNew.setText("发布求购信息");
        }else{
            setTitle("我要卖东西");
            btnNew.setText("发布售卖信息");
        }
        btnNew.setOnClickListener(this);
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
                        presenter.getList(goodType,pageNum);
                    }else{
                        presenter.getList(goodType,++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new GoodsDelegate(this));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ParamConstants.ID,dataList.get(position).getId());
                    startActivity(goodType==CommonConstants.GOODS_BUY?
                            GoodsDetailBuyActivity.class:GoodsDetailSellActivity.class,bundle);
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            startActivity(goodType==CommonConstants.GOODS_BUY?
                    GoodsNewBuyActivity.class:GoodsNewSellActivity.class);
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getList(goodType,pageNum);
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            if(pageNum!=1){
                appendList((List<Goods>) o);
                return;
            }
            dataList.addAll((List<Goods>) o);
            setupViews();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    private void appendList(List<Goods> list) {
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
}
