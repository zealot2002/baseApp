package com.zzy.business.view.activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.soundcloud.android.crop.Crop;
import com.zzy.business.R;
import com.zzy.business.contract.MineContract;
import com.zzy.business.presenter.MinePresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.bean.User;
import com.zzy.common.widget.PopupDialog;
import com.zzy.commonlib.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 我的首页
 */
public class MyMainActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , MineContract.View {
    private ImageView ivUserHead;
    private TextView tvUserName,tvUserRemarks,tvUserScore;
    private Button btnMyArchives, btnMyJob, btnMyPioneering,
            btnMyComment,btnMyGoodsToBuy,btnMyGoodsToSell,
            btnMyLog,btnSettings,btnShareSoftware;
    private MineContract.Presenter presenter;
    private User user;
    private PopupDialog dialog;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的");

        presenter = new MinePresenter(this);
        presenter.getUserInfo();
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_my_main_activity;
    }

    private void setupViews() {
        ivUserHead = findViewById(R.id.ivUserHead);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserRemarks = findViewById(R.id.tvUserRemarks);
        tvUserScore = findViewById(R.id.tvUserScore);

        btnMyArchives = findViewById(R.id.btnMyArchives);
        btnMyJob = findViewById(R.id.btnMyJob);
        btnMyPioneering = findViewById(R.id.btnMyPioneering);
        btnMyComment = findViewById(R.id.btnMyComment);
        btnMyGoodsToBuy = findViewById(R.id.btnMyGoodsToBuy);
        btnMyGoodsToSell = findViewById(R.id.btnMyGoodsToSell);
        btnMyLog = findViewById(R.id.btnMyLog);
        btnSettings = findViewById(R.id.btnSettings);
        btnShareSoftware = findViewById(R.id.btnShareSoftware);

        ivUserHead.setOnClickListener(this);
        btnMyArchives.setOnClickListener(this);
        btnMyJob.setOnClickListener(this);
        btnMyPioneering.setOnClickListener(this);

        btnMyComment.setOnClickListener(this);
        btnMyGoodsToBuy.setOnClickListener(this);
        btnMyGoodsToSell.setOnClickListener(this);

        btnMyLog.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnShareSoftware.setOnClickListener(this);
    }

    void updateViews(){
        tvUserName.setText(user.getName());
        tvUserRemarks.setText(user.getTitle());
        tvUserScore.setText(user.getScore()+"积分");
        ImageLoader.loadImage(ivUserHead,user.getHeadUrl());
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            user = (User) o;
            updateViews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if(v.getId() == R.id.btnMyArchives){
            startActivity(MyArchivesActivity.class);
        }else if(v.getId() == R.id.btnMyJob){
            startActivity(MyJobListActivity.class);
        }else if(v.getId() == R.id.btnMyPioneering){
            startActivity(MyPioneeringListActivity.class);
        }else if(v.getId() == R.id.btnMyComment){
            startActivity(MyCommentActivity.class);
        }else if(v.getId() == R.id.btnMyGoodsToBuy){
            bundle.putInt(ParamConstants.TYPE, CommonConstants.MY_GOODS_BUY);
            startActivity(GoodsListActivity.class,bundle);
        }else if(v.getId() == R.id.btnMyGoodsToSell){
            bundle.putInt(ParamConstants.TYPE, CommonConstants.MY_GOODS_SELL);
            startActivity(GoodsListActivity.class,bundle);
        }else if(v.getId() == R.id.btnMyLog){
            startActivity(MyLogListActivity.class);
        }else if(v.getId() == R.id.btnSettings){
            startActivity(SettingsActivity.class);
        }else if(v.getId() == R.id.btnShareSoftware){
            showWaitingPopup("开发中...");
//            CommonUtils.showShare(this,
//                    "标题",
//                    "内容",
//                    "图片url",
//                    "linkUrl"
//                    );
        }else if(v.getId() == R.id.ivUserHead){
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(false)
                    .setPreviewEnabled(false)
                    .start(MyMainActivity.this);
        }
    }
    private void showWaitingPopup(String s) {
        if(dialog == null){
            dialog = new PopupDialog.Builder(this,s,"完成").create();
        }
        dialog.show();
    }
    @Override
    public void reload(boolean bShow) {
        presenter.getUserInfo();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            gotoCrop(selectedPhotos.get(0));
//            Bundle bundle = new Bundle();
//            bundle.putStringArrayList(ParamConstants.DATA,selectedPhotos);
//            bundle.putInt(ParamConstants.INDEX,0);
//            Intent intent = new Intent();
//            intent.setClass(MyMainActivity.this,ImageViewerActivity.class);
//            intent.putExtras(bundle);
//            startActivityForResult(intent,ImageViewerActivity.REQUEST_CODE);
        }else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(data);
            String cropFile = uri.getPath();
            uploadHeader(cropFile);
        }
    }
    private void gotoCrop(String filePath) {
        Uri source = Uri.fromFile(new File(filePath));
        Uri destination = Uri.fromFile(new File(getCacheDir(),System.currentTimeMillis() +"_cropped.jpg"));
        Crop.of(source, destination).withAspect(150,150).start(this);
    }
    private void uploadHeader(String filePath){
        Image image = new Image();
        image.setPath(filePath);
        image.setName(filePath.substring(filePath.lastIndexOf('/')));
        presenter.uploadUserHead(image);
    }

    @Override
    public void onSuccess() {
        btnMyComment.post(new Runnable() {
            @Override
            public void run() {
                presenter.getUserInfo();
            }
        });
    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
    }
}
