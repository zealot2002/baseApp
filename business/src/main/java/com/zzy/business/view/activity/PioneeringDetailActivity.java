package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.PioneeringContract;
import com.zzy.business.model.bean.Job;
import com.zzy.business.model.bean.Pioneering;
import com.zzy.business.presenter.PioneeringPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
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

    private RelativeLayout rlSkill1,rlSkill2,rlSkill3,rlSkill4,rlSkill5,rlSkill6;
    private TextView tvSkill1,tvSkill2,tvSkill3,tvSkill4,tvSkill5,tvSkill6;
    private ImageView ivPic;

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

        rlSkill1 = findViewById(R.id.rlSkill1);
        rlSkill2 = findViewById(R.id.rlSkill2);
        rlSkill3 = findViewById(R.id.rlSkill3);
        rlSkill4 = findViewById(R.id.rlSkill4);
        rlSkill5 = findViewById(R.id.rlSkill5);
        rlSkill6 = findViewById(R.id.rlSkill6);
        tvSkill1 = findViewById(R.id.tvSkill1);
        tvSkill2 = findViewById(R.id.tvSkill2);
        tvSkill3 = findViewById(R.id.tvSkill3);
        tvSkill4 = findViewById(R.id.tvSkill4);
        tvSkill5 = findViewById(R.id.tvSkill5);
        tvSkill6 = findViewById(R.id.tvSkill6);
        ivPic = findViewById(R.id.ivPic);

        etPhone.setText(bean.getPhone());
        etContact.setText(bean.getContact());
        etContent.setText(bean.getContent());

        tvReport = findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this);

        bean.setHeadUrl("http://img4.imgtn.bdimg.com/it/u=3136075639,3338708347&fm=26&gp=0.jpg");
        ImageLoader.loadImage(ivPic,bean.getHeadUrl());
        if(bean.getSkills().size() > 0){
            rlSkill1.setVisibility(View.VISIBLE);
            tvSkill1.setText(bean.getSkills().get(0));
        }
        if(bean.getSkills().size() > 1){
            rlSkill2.setVisibility(View.VISIBLE);
            tvSkill2.setText(bean.getSkills().get(1));
        }
        if(bean.getSkills().size() > 2){
            rlSkill3.setVisibility(View.VISIBLE);
            tvSkill3.setText(bean.getSkills().get(2));
        }
        if(bean.getSkills().size() > 3){
            rlSkill4.setVisibility(View.VISIBLE);
            tvSkill4.setText(bean.getSkills().get(3));
        }
        if(bean.getSkills().size() > 4){
            rlSkill5.setVisibility(View.VISIBLE);
            tvSkill5.setText(bean.getSkills().get(4));
        }
        if(bean.getSkills().size() > 5){
            rlSkill6.setVisibility(View.VISIBLE);
            tvSkill6.setText(bean.getSkills().get(5));
        }
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
