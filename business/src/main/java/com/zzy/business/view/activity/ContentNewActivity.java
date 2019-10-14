package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.Image;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.utils.InputFilter.EmojiExcludeFilter;
import com.zzy.common.utils.InputFilter.LengthFilter;
import com.zzy.common.utils.InputFilter.SpecialExcludeFilter;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 发布内容
 */
public class ContentNewActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , ContentContract.View {
    private EditText etTitle,etContent;
    private Button btnOk;
    private ContentContract.Presenter presenter;
    private Content bean;
    private int type;

    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
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

        etTitle.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(20)}
        );
        etContent.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        setupPhotoPicker();
    }

    private void setupPhotoPicker() {
        RecyclerView recyclerView = findViewById(R.id.rvPhotoPicker);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(6)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(ContentNewActivity.this);
                        } else {
//                            PhotoPreview.builder()
//                                    .setPhotos(selectedPhotos)
//                                    .setCurrentItem(position)
//                                    .start(ContentNewActivity.this);

                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList(ParamConstants.DATA,selectedPhotos);
                            bundle.putInt(ParamConstants.INDEX,position);
                            Intent intent = new Intent();
                            intent.setClass(ContentNewActivity.this,ImageViewerActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,ImageViewerActivity.REQUEST_CODE);
                        }
                    }
                }));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }else if (resultCode == RESULT_OK &&
                (requestCode == ImageViewerActivity.REQUEST_CODE)) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(ParamConstants.DATA);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            bean = new Content();
            bean.setTitle(etTitle.getText().toString().trim());
            bean.setContent(etContent.getText().toString().trim());
            for(String s:selectedPhotos){
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
