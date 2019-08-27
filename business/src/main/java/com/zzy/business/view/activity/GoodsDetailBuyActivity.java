package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.widget.MyEditText;

/**
 * 我要买东西
 */
public class GoodsDetailBuyActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etName,etContact,etPhone,etAddress,etPrice,etDealWay;
    private MyEditText etDesc;
    private Button btnOk;
    private TextView tvScore,tvReport;
    private RatingBar rbScore;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_goods_new_buy_activity;
    }

    private void setupViews() {
        btnOk = findViewById(R.id.btnOk);
//        etPassword = findViewById(R.id.etPassword);
//        btnOk = findViewById(R.id.btnOk);
//        tvToBePioneer = findViewById(R.id.tvToBePioneer);
//        tvForgetPassword = findViewById(R.id.tvForgetPassword);

        btnOk.setOnClickListener(this);

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

    @Override
    public void reload(boolean bShow) {

    }
}
