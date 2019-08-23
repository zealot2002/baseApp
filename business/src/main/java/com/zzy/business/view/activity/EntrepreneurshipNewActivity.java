package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

/**
 * 发布创业信息
 */
public class EntrepreneurshipNewActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etContact,etPhone,etEntrepreneurshipContent;
    private Button btnOk;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我要创业");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_entrepreneurship_new_activity;
    }

    private void setupViews() {
        etContact = findViewById(R.id.etContact);
        etPhone = findViewById(R.id.etPhone);
        etEntrepreneurshipContent = findViewById(R.id.etEntrepreneurshipContent);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){

        }

    }
}
