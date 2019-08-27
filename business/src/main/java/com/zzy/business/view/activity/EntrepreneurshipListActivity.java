package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zzy.business.R;
import com.zzy.business.model.bean.Entrepreneurship;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

import java.util.List;

/**
 * 我要创业列表
 */
public class EntrepreneurshipListActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Button btnNew;
    private RecyclerView rvDataList;
    private List<Entrepreneurship> dataList;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我要创业");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_with_btn_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        btnNew.setText("发布创业信息");
        btnNew.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            startActivity(EntrepreneurshipNewActivity.class);
        }

    }

    @Override
    public void reload(boolean bShow) {

    }
}
