package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.PioneeringContract;
import com.zzy.common.model.bean.Pioneering;
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
    private TextView tvContent,tvContact,tvReport;
    private PopupEditDialog dialog;

    private RelativeLayout rlSkill1,rlSkill2,rlSkill3,rlSkill4,rlSkill5,rlSkill6;
    private TextView tvSkill1,tvSkill2,tvSkill3,tvSkill4,tvSkill5,tvSkill6;
    private ImageView ivPic;

    private int id;
    private PioneeringContract.Presenter presenter;
    private Pioneering bean;
    private int caller; //1:  创业信息详情；2：技能详情
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            caller = getIntent().getIntExtra(ParamConstants.CALLER,1);
            if(1 == caller){
                setTitle("创业信息详情");
            }else if(2 == caller){
                setTitle("技能详情");
            }
            presenter = new PioneeringPresenter(this);
            presenter.getDetail(caller,id);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pioneering_detail_activity;
    }

    private void setupViews() {
        tvContact = findViewById(R.id.tvContact);
        etContent = findViewById(R.id.etContent);
        tvContent = findViewById(R.id.tvContent);
        etContact = findViewById(R.id.etContact);
        etPhone = findViewById(R.id.etPhone);
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
        tvReport = findViewById(R.id.tvReport);
        tvReport.setOnClickListener(this);


        etPhone.setText(bean.getPhone());
        etContact.setText(bean.getContact());
        if(1 == caller){
            etContent.setText(bean.getContent());
            tvContact.setText("联  系  人 :   ");
        }else {
            tvContent.setVisibility(View.GONE);
            etContent.setVisibility(View.GONE);
            tvReport.setVisibility(View.GONE);
            tvContact.setText("姓      名 :   ");
        }

        if(!bean.getSkills().isEmpty()){
            ImageLoader.loadImage(ivPic,bean.getHeadUrl());
        }
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
        presenter.getDetail(caller,id);
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
