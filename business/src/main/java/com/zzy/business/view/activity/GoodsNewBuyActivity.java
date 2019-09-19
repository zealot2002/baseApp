package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Goods;
import com.zzy.common.model.bean.Image;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.FileUploader;
import com.zzy.common.widget.MyEditText;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 我要买东西
 */
public class GoodsNewBuyActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etName,etContact,etPhone,etAddress,etPrice;
    private MyEditText etDesc;
    private Button btnOk;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private Goods bean;

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
        setTitle("我要买东西");

        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPrice = findViewById(R.id.etStartPrice);
        etDesc = findViewById(R.id.etDesc);

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
                                    .start(GoodsNewBuyActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(GoodsNewBuyActivity.this);
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
            bean = new Goods();
            bean.setAddress(etAddress.getText().toString().trim());
            bean.setContact(etContact.getText().toString().trim());
            bean.setDesc(etDesc.getText().toString().trim());
            bean.setName(etName.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setPrice(etPrice.getText().toString().trim());

            for(String s:selectedPhotos){
                Image image = new Image();
                image.setPath(s);
                image.setName(s.substring(s.lastIndexOf('/')));
                bean.getImgList().add(image);
            }
            preSubmit();
        }
    }

    private void preSubmit() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            if(bean.getImgList().isEmpty()){
                submit();
                return;
            }
            new FileUploader().post(bean.getImgList(), new HInterface.DataCallback() {
                @Override
                public void requestCallback(int result, Object data, Object tagData) {
                    MyLog.e("result:" +data.toString());
                    if (result == HConstant.SUCCESS) {
                        bean.setImgList((List<Image>) data);
                        submit();
                    }else if(result == HConstant.FAIL){
                        ToastUtils.showShort((String) data);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    private void submit() {
        try {
            HttpProxy.newGoods(CommonConstants.GOODS_BUY,bean,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        ToastUtils.showShort("成功");
                        finish();
                    }else if(result == HConstant.FAIL
                            ||result == HConstant.ERROR
                    ){
                        ToastUtils.showShort((String) o);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }

    }

    @Override
    public void reload(boolean bShow) {

    }
}
