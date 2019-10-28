package com.zzy.business.view.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzy.business.R;
import com.zzy.business.contract.MyLogContract;
import com.zzy.business.presenter.MyLogPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.model.bean.Log;
import com.zzy.common.utils.InputFilter.EmojiExcludeFilter;
import com.zzy.common.utils.InputFilter.LengthFilter;
import com.zzy.common.utils.InputFilter.SpecialExcludeFilter;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 写日志
 */
public class MyLogNewActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , MyLogContract.View {
    private EditText etContent;
    private Button btnOk;
    private MyLogContract.Presenter presenter;
    private Log bean;

/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("写日志");
        setupViews();
        presenter = new MyLogPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_log_new_activity;
    }

    private void setupViews() {
        etContent = findViewById(R.id.etContent);

        etContent.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(500)}
        );
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            bean = new Log();
            bean.setContent(etContent.getText().toString().trim());
            presenter.create(bean);
        }
    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
    }

    @Override
    public void onSuccess() {
        finish();
    }
}
