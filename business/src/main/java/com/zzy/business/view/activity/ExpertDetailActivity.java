package com.zzy.business.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Expert;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;


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
            closeLoading();
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
        return R.layout.busi_expert_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        tvPhone = findViewById(R.id.tvPhone);
        webView = findViewById(R.id.webView);

        tvTitle.setText(bean.getName());
        tvPhone.setText("联系方式："+bean.getPhone());

        CommonUtils.webLoadData(webView,bean.getIntroduction());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void reload(boolean bShow) {
    }


}
