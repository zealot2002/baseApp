package com.zzy.business.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.Image;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 发布内容
 */
public class FriendNewActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , ContentContract.View {
    private EditText etContent;
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
            type = getIntent().getIntExtra(ParamConstants.TYPE, CommonConstants.CONTENT_FRIEND);
        }catch (Exception e){
            e.printStackTrace();
        }
        setTitle("发布朋友圈");
        setupViews();
        presenter = new ContentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_friend_new_activity;
    }

    private void setupViews() {
        etContent = findViewById(R.id.etContent);

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
                                    .start(FriendNewActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(FriendNewActivity.this);
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
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOk){
            bean = new Content();
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