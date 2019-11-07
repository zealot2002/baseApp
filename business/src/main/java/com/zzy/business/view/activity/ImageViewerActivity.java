package com.zzy.business.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.TimeUtils;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.soundcloud.android.crop.Crop;
import com.zzy.business.R;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.utils.CommonUtils;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;



/*
* GoodsImageViewerActivity
*
* */
public class ImageViewerActivity extends BaseAppActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 555;
    private ConvenientBanner banner;
    private ArrayList<String> dataList;
    private TextView tvTitle;
    private RelativeLayout rlBack;
    private TextView tvDel,tvCrop;
    private int curPosition;
/*********************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.lRoot));
        try{
            dataList = getIntent().getStringArrayListExtra(ParamConstants.DATA);
            curPosition = getIntent().getIntExtra(ParamConstants.INDEX,0);
            updateViews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateViews() {
        if(tvTitle == null){
            tvTitle = findViewById(R.id.tvTitle);
            rlBack = findViewById(R.id.rlBack);
            tvDel = findViewById(R.id.tvDel);
            tvCrop = findViewById(R.id.tvCrop);
            rlBack.setOnClickListener(this);
            tvDel.setOnClickListener(this);
            tvCrop.setOnClickListener(this);
            banner = findViewById(R.id.banner);
        }
        tvTitle.setText((1+curPosition)+"/"+dataList.size());
        banner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public BannerHolderView createHolder(View itemView) {
                        return new BannerHolderView(itemView,R.mipmap.icon_default);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.banner_item;
                    }
                },dataList)
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) { }
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) { }
                    @Override
                    public void onPageSelected(int position) {
                        curPosition = position;
                        tvTitle.setText((1+position)+"/"+dataList.size());
                    }
                });
        banner.post(new Runnable() {
            @Override
            public void run() {
                banner.setCurrentItem(curPosition,false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvCrop){
            gotoCrop();
        }else if(v.getId() == R.id.tvDel){
            dataList.remove(curPosition);
            curPosition = 0;
            updateViews();
        }else if(v.getId() == R.id.rlBack){
            finishSelf();
        }
    }

    private void gotoCrop() {
        Uri source = Uri.fromFile(new File(dataList.get(curPosition)));
        Uri destination = Uri.fromFile(new File(getCacheDir(),System.currentTimeMillis() +"_cropped.jpg"));
            Crop.of(source, destination).withAspect(370,300).start(this);
//        Crop.of(source, destination).withAspect(281,150).start(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(data);
            String cropFile = uri.getPath();
            dataList.set(curPosition,cropFile);
            updateViews();
        }
    }
    public class BannerHolderView extends Holder<String> {
        private ImageView ivPic;
        private int errResId;
        public BannerHolderView(View itemView, int errResId) {
            super(itemView);
            this.errResId = errResId;
        }

        @Override
        protected void initView(View itemView) {
            ivPic = itemView.findViewById(com.zzy.common.R.id.ivPic);
        }

        @Override
        public void updateUI(String path) {
            ImageLoader.loadImage(ivPic.getContext(),ivPic,"file://"+path,errResId);
        }
    }

    @Override
    public void onBackPressed() {
        finishSelf();
    }
    private void finishSelf(){
        Intent it = new Intent();
        it.putStringArrayListExtra(ParamConstants.DATA,dataList);
        setResult(RESULT_OK,it);
        finish();
    }
}
