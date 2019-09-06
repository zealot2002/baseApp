package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.Image;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.GlideLoader;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 发布内容
 */
public class ContentNewActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , ContentContract.View {
    private EditText etTitle,etContent;
    private Button btnOk,btnAdd;
    private ContentContract.Presenter presenter;
    private Content bean;
    private int type;

    private ImageConfig imageConfig;
    private LinearLayout llContainer;
    private ArrayList<String> tmpPath = new ArrayList<>();
    private ArrayList<String> resultPath = new ArrayList<>();
    public static final int REQUEST_CODE = 123;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            type = getIntent().getIntExtra(ParamConstants.TYPE, CommonConstants.CONTENT_HELP);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(CommonConstants.CONTENT_HELP == type){
            setTitle("创业求助");
        }else if(CommonConstants.CONTENT_IDEA == type){
            setTitle("创业点子");
        }else if(CommonConstants.CONTENT_EXPERIENCE == type){
            setTitle("分享经验");
        }
        setupViews();
        presenter = new ContentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_content_new_activity;
    }

    private void setupViews() {
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        llContainer = findViewById(R.id.llContainer);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageConfig = new ImageConfig.Builder(
                        new GlideLoader())
                        .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                        .titleBgColor(getResources().getColor(R.color.titleBlue))
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）
                        .mutiSelect()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(6)
                        //设置图片显示容器，参数：、（容器，每行显示数量，是否可删除）
                        .setContainer(llContainer, 3, true)
                        // 已选择的图片路径
                        .pathList(tmpPath)
                        // 拍照后存放的图片路径（默认 /temp/picture）
                        .filePath("/temp")
                        // 开启拍照功能 （默认关闭）
//                        .showCamera()
                        .requestCode(REQUEST_CODE)
                        .build();
                ImageSelector.open(ContentNewActivity.this, imageConfig);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            resultPath = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            tmpPath.clear();
            tmpPath.addAll(resultPath);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            bean = new Content();
            bean.setTitle(etTitle.getText().toString().trim());
            bean.setContent(etContent.getText().toString().trim());
            for(String s:resultPath){
                Image image = new Image();
                image.setPath(s);
                image.setName(s.substring(s.lastIndexOf('/')));
                bean.getImgList().add(image);
            }
            presenter.create(type,bean);
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
        ToastUtils.showShort("成功");
        finish();
    }
}
