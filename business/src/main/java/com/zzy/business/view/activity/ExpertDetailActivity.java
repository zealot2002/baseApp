package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.Expert;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.common.base.BaseToolbarActivity;

/**
 * 专家详情
 */
public class ExpertDetailActivity extends BaseToolbarActivity implements View.OnClickListener {
    private TextView tvTitle,tvContent;
    private Expert bean;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_expert_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);

        tvTitle.setText("\n" +
                "胡小平专家介绍");

        tvContent.setText("专家简介：胡小平，男，汉族，副教授，硕士研究生导师，日本冈山大学资源生物科学研究所博士后；有画家胡小平，1955年出生，1978年毕业于安徽师范大学美术系；有杭州电子科技大学教务处副处长\n" +
                "\n" +
                "\n" +
                "\n" +
                "联系方式：18888888888");
    }


    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btnOk){
//            // TODO: 2019/8/19   to login
//        }else if(v.getId() == R.id.tvToBePioneer){
//
//        }else if(v.getId() == R.id.tvForgetPassword){
//
//        }

    }
}
