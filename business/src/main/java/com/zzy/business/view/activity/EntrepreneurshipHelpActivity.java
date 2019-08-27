package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;

import java.util.List;

/**
 * 创业求助
 */
public class EntrepreneurshipHelpActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Button btnNew;
    private RecyclerView rvDataList;
    private List<GetRichInfo> dataList;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业求助");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_with_btn_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);

        rvDataList = findViewById(R.id.rvDataList);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            // TODO: 2019/8/19   to login
        }

    }

    @Override
    public void reload(boolean bShow) {

    }
}
