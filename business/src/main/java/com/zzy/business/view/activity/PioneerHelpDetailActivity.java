package com.zzy.business.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.view.itemViewDelegate.ExpertDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.HelpClass;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 创业帮扶详情
 */
public class PioneerHelpDetailActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener{
    private TextView tvTitle,tvTime,tvAddress,tvTeacher,tvJoinNum;
    private Button btnJoin;
    private WebView webView;
    private int id;
    private HelpClass bean;
    private RecyclerView rvDataList;
    private MyMultiRecycleAdapter adapter;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("创业帮扶详情");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getData() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.getHelpDetail(id,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        updateUI(o);
                    }else if(result == HConstant.FAIL
                            ||result == HConstant.ERROR
                    ){
                        ToastUtils.showShort((String) o);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (HelpClass) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pioneer_service_help_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        tvTime = findViewById(R.id.tvTime);
        tvAddress = findViewById(R.id.tvAddress);
        tvTeacher = findViewById(R.id.tvTeacher);
        tvJoinNum = findViewById(R.id.tvJoinNum);
        webView = findViewById(R.id.webView);
        btnJoin = findViewById(R.id.btnJoin);

        tvTitle.setText(bean.getTitle());
        tvTime.setText("时间："+bean.getTime());
        tvAddress.setText("地点："+bean.getAddress());
        tvTeacher.setText("专家简介："+bean.getTeacher());
        tvJoinNum.setText("目前已有"+bean.getParterNum()+"人报名参加");

        CommonUtils.webLoadData(webView,bean.getContent());

        btnJoin.setOnClickListener(this);

        setupListView();
    }

    private void setupListView() {
        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            adapter = new MyMultiRecycleAdapter(this,new ArrayList(),false);
            adapter.addItemViewDelegate(new ExpertDelegate(this));
            rvDataList.setAdapter(adapter);
        }
        adapter.setNewData(bean.getParterList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnJoin){
            if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
                ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
                return;
            }
            showLoading();
            try{
                HttpProxy.joinClass(id,new CommonDataCallback() {
                    @Override
                    public void callback(int result, Object o, Object o1) {
                        closeLoading();
                        if (result == HConstant.SUCCESS) {
                            reload(true);
                        }else if(result == HConstant.FAIL
                                ||result == HConstant.ERROR
                        ){
                            ToastUtils.showShort((String) o);
                        }
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
                ToastUtils.showShort(e.toString());
            }
        }
    }

    @Override
    public void reload(boolean bShow) {
        getData();
    }
}
