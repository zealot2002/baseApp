package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.zzy.business.R;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 致富信息详情
 */
public class GetRichInfoDetailActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, GetRichInfoContract.View {
    private RelativeLayout rlLike;
    private TextView tvTitle,tvDate,tvLikeNum,tvLookNum;
    private ImageView ivPic;
    private WebView webView;
    private int id;
    private GetRichInfoContract.Presenter presenter;
    private GetRichInfo bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("致富信息");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new GetRichInfoPresenter(this);
            presenter.getRichInfoDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (GetRichInfo) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_get_rich_info_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        webView = findViewById(R.id.webView);
        tvLookNum = findViewById(R.id.tvLookNum);
        tvLikeNum = findViewById(R.id.tvLikeNum);
        ivPic = findViewById(R.id.ivPic);
        rlLike = findViewById(R.id.rlLike);

//        webView.loadData(testHtml,"text/html","utf-8");
        webView.loadData(bean.getContent(),"text/html","utf-8");

        tvDate.setText("时间: "+bean.getDate());

        tvLikeNum.setText("赞 ("+bean.getLikeNum()+")");
        tvLookNum.setText("浏览数 :"+bean.getLookNum());
        if(bean.isPlaceTop()){
            ivPic.setVisibility(View.VISIBLE);
            tvTitle.setText("         "+bean.getTitle());
        }else{
            tvTitle.setText(bean.getTitle());
        }
//        if(!bean.isLike()){
            rlLike.setOnClickListener(this);
//        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlLike){
            presenter.like(id);
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getRichInfoDetail(id);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        reload(true);
    }
}
