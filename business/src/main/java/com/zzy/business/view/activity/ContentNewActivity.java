package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.business.model.bean.Content;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 发布内容
 */
public class ContentNewActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , ContentContract.View {
    private EditText etTitle,etContent;
    private Button btnOk;
    private ContentContract.Presenter presenter;
    private Content bean;
    private int type;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            type = getIntent().getIntExtra(ParamConstants.TYPE, CommonConstants.CONTENT_HELP);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(CommonConstants.CONTENT_HELP == type){
            setTitle("创业求助");
        }else if(CommonConstants.CONTENT_IDEA == type){
            setTitle("创业点子");
        }else if(CommonConstants.CONTENT_EXPERIENCE == type){
            setTitle("分享经验");
        }
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_content_new_activity;
    }

    private void setupViews() {
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
//            bean.setContact(etContact.getText().toString().trim());
//            bean.setPhone(etPhone.getText().toString().trim());
//            bean.setContent(etContent.getText().toString().trim());
//
//            presenter.create(bean);
        }
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
        ToastUtils.showShort("成功");
        finish();
    }
}
