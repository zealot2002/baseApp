package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.PbRecord;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.widget.PopupDialog;

import java.util.List;

/**
 * 创业先锋
 */
public class EntrepreneurshipVanguardActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Button btn1,btn2,btn3,btn4,btn5,btn6;
    private RecyclerView rvDataList;
    private List<PbRecord> dataList;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业先锋");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_entrepreneurship_vanguard_activity;
    }

    private void setupViews() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);

        btn1.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        resetBtns();
        if(v.getId() == R.id.btn1){
            btn1.setSelected(true);
        }else if(v.getId() == R.id.btn2){
            btn2.setSelected(true);
        }else if(v.getId() == R.id.btn3){
            btn3.setSelected(true);
        }else if(v.getId() == R.id.btn4){
            btn4.setSelected(true);
        }else if(v.getId() == R.id.btn5){
            btn5.setSelected(true);
        }else if(v.getId() == R.id.btn6){
            btn6.setSelected(true);
        }
    }

    private void resetBtns() {
        btn1.setSelected(false);
        btn2.setSelected(false);
        btn3.setSelected(false);
        btn4.setSelected(false);
        btn5.setSelected(false);
        btn6.setSelected(false);
    }
}
