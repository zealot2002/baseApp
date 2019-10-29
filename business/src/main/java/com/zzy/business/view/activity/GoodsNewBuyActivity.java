package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzy.business.R;
import com.zzy.business.contract.GoodsContract;
import com.zzy.business.presenter.GoodsPresenter;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Goods;
import com.zzy.common.model.bean.Image;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.FileHandler;
import com.zzy.common.utils.InputFilter.EmojiExcludeFilter;
import com.zzy.common.utils.InputFilter.LengthFilter;
import com.zzy.common.widget.MyEditText;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface.DataCallback;
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
    private int id;
    private Goods bean;
    private int type;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            presenter = new GoodsPresenter(this);
            type = getIntent().getIntExtra(ParamConstants.TYPE,0);
            id = getIntent().getIntExtra(ParamConstants.ID,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(type != CommonConstants.MY_GOODS_BUY){
            //new goods
            bean = new Goods();
            setupViews();
        }else {
            presenter.getDetail(CommonConstants.GOODS_SELL,id);
        }
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

        etName.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(20)}
        );

        etContact.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(20)}
        );
        etDesc.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        if(type == CommonConstants.MY_GOODS_BUY){
            fillValue();
        }

        if(!TextUtils.isEmpty(bean.getState())
                &&bean.getState().equals("成交")){
            btnOk.setVisibility(View.GONE);
            btnDel.setVisibility(View.GONE);
        }

        prepareImage();
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
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList(ParamConstants.DATA,selectedPhotos);
                            bundle.putInt(ParamConstants.INDEX,position);
                            Intent intent = new Intent();
                            intent.setClass(GoodsNewBuyActivity.this,ImageViewerActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,ImageViewerActivity.REQUEST_CODE);
                        }
                    }
                }));

    }

    private void prepareImage() {
        if(bean.getImgList().isEmpty()){
            setupPhotoPicker();
            return;
        }
        showLoading();
        List<String> urlList = new ArrayList<>();
        for(Image image:bean.getImgList()){
            urlList.add(image.getPath());
        }
        new FileHandler().savePicToLocal(urlList, new DataCallback() {
            @Override
            public void requestCallback(int result, Object data, Object tagData) {
                closeLoading();
                if (result == HConstant.SUCCESS) {
                    List<String> imgNames = (List<String>) data;
                    selectedPhotos.addAll(imgNames);
                    setupPhotoPicker();
                }else if(result == HConstant.FAIL){
                    ToastUtils.showLong((String) data);
                }
            }
        });
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
            bean.setContact(etContact.getText().toString().trim());
            bean.setDesc(etDesc.getText().toString().trim());
            bean.setName(etName.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setStartPrice(etStartPrice.getText().toString().trim());
            bean.setEndPrice(etEndPrice.getText().toString().trim());

            bean.getImgList().clear();
            for(String s:selectedPhotos){
                Image image = new Image();
                image.setPath(s);
                image.setName(s.substring(s.lastIndexOf('/')));
                bean.getImgList().add(image);
            }
            if(TextUtils.isEmpty(bean.getName())){
                ToastUtils.showShort("请填写标题");
                return;
            }
            if(TextUtils.isEmpty(bean.getPhone())){
                ToastUtils.showShort("请填写联系电话");
                return;
            }
            if(TextUtils.isEmpty(bean.getContact())){
                ToastUtils.showShort("请填写联系人");
                return;
            }
            if(TextUtils.isEmpty(bean.getDesc())){
                ToastUtils.showShort("请填写物品描述");
                return;
            }
            if(TextUtils.isEmpty(bean.getStartPrice())){
                ToastUtils.showShort("请填写起始价格");
                return;
            }
            if(TextUtils.isEmpty(bean.getEndPrice())){
                ToastUtils.showShort("请填写最高价格");
                return;
            }
            if(Float.valueOf(bean.getStartPrice())>Float.valueOf(bean.getEndPrice())){
                ToastUtils.showShort("起始价格不得大于最高价格");
                return;
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
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Goods) o;
            bean.setId(id);
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
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
        finish();
    }
}
