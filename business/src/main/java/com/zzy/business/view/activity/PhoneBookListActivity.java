package com.zzy.business.view.activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

/**
 * 通讯录
 */
public class PhoneBookListActivity extends BaseTitleAndBottomBarActivity{
    private EditText etPhone,etPassword;
    private Button btnOk;
    private TextView tvToBePioneer,tvForgetPassword;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("通讯录列表");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_menu_list_item;
    }

}