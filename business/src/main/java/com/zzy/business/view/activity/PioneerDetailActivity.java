package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.PioneerContract;
import com.zzy.common.model.bean.Menu;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.business.presenter.PioneerPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

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
    private WebView webView;
    private int id;
    private Pioneer bean;
    private boolean isReload = false;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("创业先锋");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new PioneerPresenter(this);
            presenter.getDetail(id);
            setOnBackClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleBack();
                }
            });
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
        if(isReload){
            isReload = false;
            try{
                tvLikeNum.setText("赞 ("+bean.getLikeNum()+")");
                tvLikeNum.setTextColor(bean.isLike()?
                        getResources().getColor(R.color.deep_blue)
                        :getResources().getColor(R.color.text_gray));
            }catch (Exception e){
                e.printStackTrace();
            }
            return;
        }
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        btnStudy = findViewById(R.id.btnStudy);
        webView = findViewById(R.id.webView);
        tvLookNum = findViewById(R.id.tvLookNum);
        tvLikeNum = findViewById(R.id.tvLikeNum);
        rlLike = findViewById(R.id.rlLike);

        CommonUtils.webLoadData(webView,bean.getContent());

        tvLikeNum.setText("赞 ("+bean.getLikeNum()+")");
        tvLikeNum.setTextColor(bean.isLike()?
                getResources().getColor(R.color.deep_blue)
                :getResources().getColor(R.color.text_gray));
        tvLookNum.setText("浏览数 :"+bean.getLookNum());
        if(bean.isPlaceTop()){
            tvTitle.setText("         "+bean.getTitle());
        }else{
            tvTitle.setText(bean.getTitle());
        }
        rlLike.setOnClickListener(this);
        btnStudy.setOnClickListener(this);
    }

    private void handleBack(){
        try{
            Intent it = new Intent();
            it.putExtra(ParamConstants.COUNT,bean.getLookNum());
            setResult(RESULT_OK,it);
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            handleBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        presenter.getDetail(id);
        isReload = true;
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
