package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.PioneeringContract;
import com.zzy.business.model.bean.Job;
import com.zzy.business.model.bean.Pioneering;
import com.zzy.business.presenter.PioneeringPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 创业信息详情
 */
public class PioneeringDetailActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, PioneeringContract.View {
    private EditText etPhone,etContact,etContent;
    private TextView tvReport;
    private PopupEditDialog dialog;

    private int id;
    private PioneeringContract.Presenter presenter;
    private Pioneering bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("创业信息详情");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new PioneeringPresenter(this);
            presenter.getDetail(id);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pioneering_detail_activity;
    }

    private void setupViews() {
        etContent = findViewById(R.id.etContent);
        etPhone = findViewById(R.id.etPhone);
        etContact = findViewById(R.id.etContact);

        etPhone.setText(bean.getPhone());
        etContact.setText(bean.getContact());
        etContent.setText(bean.getContent());

        tvReport = findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this);
    }
    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Pioneering) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvReport){
            if(dialog == null){
                dialog = new PopupEditDialog.Builder(this, "举报原因：","完成",
                        new PopupEditDialog.OnClickOkListener() {
                            @Override
                            public void clickOk(String content) {
                                if(content.isEmpty()){
                                    ToastUtils.showShort("内容不能为空");
                                    return;
                                }
                                presenter.report(id,content);
                                dialog.dismiss();
                            }
                        }
                ).create();
            }
            dialog.show();
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.getDetail(id);
    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("举报成功");
    }
}
