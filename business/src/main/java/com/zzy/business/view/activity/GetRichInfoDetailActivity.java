package com.zzy.business.view.activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.common.model.bean.GetRichInfo;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.CommonUtils;
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
    private String outerTitle;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            outerTitle = getIntent().getStringExtra(ParamConstants.TITLE);
            if(TextUtils.isEmpty(outerTitle)){
                setTitle("致富信息");
            }else{
                setTitle(outerTitle);
            }
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new GetRichInfoPresenter(this);
            presenter.getDetail(id);
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

        CommonUtils.webLoadData(webView,bean.getContent());

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
        presenter.getDetail(id);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        reload(true);
    }
}
