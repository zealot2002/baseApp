package com.zzy.business.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.zzy.business.R;
import com.zzy.business.contract.PioneerContract;
import com.zzy.business.presenter.PioneerPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Expert;
import com.zzy.common.model.bean.Menu;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.List;

/**
 * 专家详情
 */
public class ExpertDetailActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener {
    private TextView tvTitle,tvPhone;
    private WebView webView;
    private int id;
    private Expert bean;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("专家详情");
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
            HttpProxy.getExpertDetail(id,new CommonDataCallback() {
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
            bean = (Expert) o;
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
        tvPhone = findViewById(R.id.tvPhone);
        webView = findViewById(R.id.webView);

        tvTitle.setText(bean.getName());
        tvPhone.setText(bean.getPhone());
        webView.loadData(bean.getIntroduction(),"text/html","utf-8");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void reload(boolean bShow) {
    }


}
