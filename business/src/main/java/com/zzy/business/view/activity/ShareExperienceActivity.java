package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zzy.business.R;
import com.zzy.business.model.bean.PbRecord;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

import java.util.List;

/**
 * 分享经验
 */
public class ShareExperienceActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Button btnNew;
    private RecyclerView rvDataList;
    private List<PbRecord> dataList;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("分享经验");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_with_btn_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        btnNew.setText("分享个人经验");
        btnNew.setOnClickListener(this);
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
