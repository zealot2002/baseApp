package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.zzy.business.R;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.business.contract.PioneerContract;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.model.bean.Menu;
import com.zzy.business.model.bean.Pioneer;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.business.presenter.PioneerPresenter;
import com.zzy.business.view.itemViewDelegate.PioneerDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创业先锋详情
 */
public class PioneerDetailActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , PioneerContract.View {
    private PioneerContract.Presenter presenter;
    private RelativeLayout rlLike;
    private TextView tvTitle,tvLikeNum,tvLookNum;
    private Button btnStudy;
    private ImageView ivPic;
    private WebView webView;
    private int id;
    private Pioneer bean;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("创业先锋");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new PioneerPresenter(this);
            presenter.getPioneerDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Pioneer) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pioneer_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        btnStudy = findViewById(R.id.btnStudy);
        webView = findViewById(R.id.webView);
        tvLookNum = findViewById(R.id.tvLookNum);
        tvLikeNum = findViewById(R.id.tvLikeNum);
        ivPic = findViewById(R.id.ivPic);
        rlLike = findViewById(R.id.rlLike);

        webView.loadData(bean.getContent(),"text/html","utf-8");

        tvLikeNum.setText("赞 ("+bean.getLikeNum()+")");
        tvLookNum.setText("浏览数 :"+bean.getLookNum());
        if(bean.isPlaceTop()){
            ivPic.setVisibility(View.VISIBLE);
            tvTitle.setText("         "+bean.getTitle());
        }else{
            tvTitle.setText(bean.getTitle());
        }
        if(!bean.isLike()){
            rlLike.setOnClickListener(this);
        }

        btnStudy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlLike){
            presenter.like(id);
        }else if(v.getId() == R.id.btnStudy){
            AppUtils.callPhone(this,bean.getPhone());
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getPioneerDetail(id);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        reload(true);
    }

    @Override
    public void updateMenuList(List<Menu> menuList) {

    }

}
