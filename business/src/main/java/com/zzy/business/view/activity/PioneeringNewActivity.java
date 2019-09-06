package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzy.business.R;
import com.zzy.business.contract.PioneeringContract;
import com.zzy.common.model.bean.Pioneering;
import com.zzy.business.presenter.PioneeringPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 发布创业信息
 */
public class PioneeringNewActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , PioneeringContract.View {
    private EditText etContact,etPhone,etContent;
    private Button btnOk;
    private PioneeringContract.Presenter presenter;
    private Pioneering bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我要创业");

        presenter = new PioneeringPresenter(this);
        bean = new Pioneering();
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pioneering_new_activity;
    }

    private void setupViews() {
        etContact = findViewById(R.id.etContact);
        etPhone = findViewById(R.id.etPhone);
        etContent = findViewById(R.id.etContent);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            bean.setContact(etContact.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setContent(etContent.getText().toString().trim());

            presenter.create(bean);
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
