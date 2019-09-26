package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.contract.GoodsContract;
import com.zzy.business.presenter.GoodsPresenter;
import com.zzy.business.view.adapter.GridMenuListAdapter;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Goods;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.bean.Menu;
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
public class GoodsNewBuyActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, GoodsContract.View {
    private GoodsPresenter presenter;
    private EditText etName,etContact,etPhone,etStartPrice,etEndPrice;
    private MyEditText etDesc;
    private Button btnOk,btnDel;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private Goods bean;
    private int type;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            type = getIntent().getIntExtra(ParamConstants.TYPE,0);
            bean = (Goods) getIntent().getSerializableExtra(ParamConstants.OBJECT);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(type != CommonConstants.MY_GOODS_BUY){
            //new goods
            bean = new Goods();
        }
        presenter = new GoodsPresenter(this);
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
        etStartPrice = findViewById(R.id.etStartPrice);
        etEndPrice = findViewById(R.id.etEndPrice);
        etDesc = findViewById(R.id.etDesc);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        setupPhotoPicker();
        if(type == CommonConstants.MY_GOODS_BUY){
            fillValue();
        }
    }

    private void fillValue() {
        etName.setText(bean.getName());
        etContact.setText(bean.getContact());
        etPhone.setText(bean.getPhone());
        etStartPrice.setText(bean.getStartPrice());
        etEndPrice.setText(bean.getEndPrice());
        etDesc.setText(bean.getDesc());

        btnDel.setVisibility(View.VISIBLE);
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
//                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .setPreviewEnabled(true)
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
            bean.setContact(etContact.getText().toString().trim());
            bean.setDesc(etDesc.getText().toString().trim());
            bean.setName(etName.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setStartPrice(etStartPrice.getText().toString().trim());
            bean.setEndPrice(etEndPrice.getText().toString().trim());

            for(String s:selectedPhotos){
                Image image = new Image();
                image.setPath(s);
                image.setName(s.substring(s.lastIndexOf('/')));
                bean.getImgList().add(image);
            }
            if(type == CommonConstants.MY_GOODS_BUY){
                presenter.update(CommonConstants.GOODS_BUY,bean);
            }else {
                presenter.create(CommonConstants.GOODS_BUY,bean);
            }
        }if(v.getId() == R.id.btnDel){
            presenter.delete(bean.getId());
        }
    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("成功");
        finish();
    }
}
