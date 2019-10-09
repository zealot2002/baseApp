package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.zzy.business.view.adapter.GridMenuListAdapter;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.bean.Goods;
import com.zzy.common.model.bean.Image;
import com.zzy.common.model.bean.Menu;
import com.zzy.common.utils.InputFilter.EmojiExcludeFilter;
import com.zzy.common.utils.InputFilter.LengthFilter;
import com.zzy.common.utils.InputFilter.SpecialExcludeFilter;
import com.zzy.common.widget.MyEditText;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 发布售卖信息
 */
public class GoodsNewSellActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, GoodsContract.View {
    private GoodsPresenter presenter;
    private EditText etName,etContact,etPhone,etAddress,etPrice;
    private MyEditText etDesc;
    private Button btnOk,btnDel;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private Goods bean;
    private int type;
    private RecyclerView rvTagList;
    private ArrayList<Menu> tagList = new ArrayList<Menu>(){{
        add(new Menu("自提",true));
        add(new Menu("送货上门",false));
        add(new Menu("同城面交",false));
        add(new Menu("邮寄",false));
    }};
    private int tagIndex = 0;
    private GridMenuListAdapter gridMenuListAdapter;
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
        if(type != CommonConstants.MY_GOODS_SELL){
            //new goods
            bean = new Goods();
        }
        presenter = new GoodsPresenter(this);
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_goods_new_sell_activity;
    }

    private void setupViews() {
        setTitle("我要卖东西");

        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPrice = findViewById(R.id.etPrice);
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

        setupTagView();
        setupPhotoPicker();
        if(type == CommonConstants.MY_GOODS_SELL){
            fillValue();
        }
    }
    private void fillValue() {
        etName.setText(bean.getName());
        etContact.setText(bean.getContact());
        etPhone.setText(bean.getPhone());
        etPrice.setText(bean.getPrice());
        etAddress.setText(bean.getAddress());
        etDesc.setText(bean.getDesc());

        //dealway
        refreshTagList(bean.getDealWay());
        btnDel.setVisibility(View.VISIBLE);
    }
    private void setupTagView() {
        if(rvTagList == null){
            rvTagList = findViewById(R.id.rvTagList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
            rvTagList.setLayoutManager(layoutManager);
            rvTagList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            gridMenuListAdapter = new GridMenuListAdapter(this);
            rvTagList.setAdapter(gridMenuListAdapter);
            gridMenuListAdapter.setOnItemClickedListener(new GridMenuListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    refreshTagList(position);
                }
            });
        }
        gridMenuListAdapter.swapData(tagList);
    }
    private void refreshTagList(String initTag) {
        if(TextUtils.isEmpty(initTag)){
            return;
        }
        for(int i=0;i<tagList.size();i++){
            Menu m = tagList.get(i);
            m.setSelected(initTag.equals(m.getName())?true:false);
        }
        gridMenuListAdapter.swapData(tagList);
    }
    private void refreshTagList(int position) {
        tagIndex = position;
        for(int i=0;i<tagList.size();i++){
            tagList.get(i).setSelected(i==position?true:false);
        }
        gridMenuListAdapter.swapData(tagList);
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
                                    .start(GoodsNewSellActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(GoodsNewSellActivity.this);
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
            bean.setAddress(etAddress.getText().toString().trim());
            bean.setContact(etContact.getText().toString().trim());
            bean.setDesc(etDesc.getText().toString().trim());
            bean.setName(etName.getText().toString().trim());
            bean.setPhone(etPhone.getText().toString().trim());
            bean.setPrice(etPrice.getText().toString().trim());
            bean.setDealWay(tagList.get(tagIndex).getName());

            bean.getImgList().clear();
            for(String s:selectedPhotos){
                Image image = new Image();
                image.setPath(s);
                image.setName(s.substring(s.lastIndexOf('/')));
                bean.getImgList().add(image);
            }
            if(type == CommonConstants.MY_GOODS_SELL){
                presenter.update(CommonConstants.GOODS_SELL,bean);
            }else {
                presenter.create(CommonConstants.GOODS_SELL,bean);
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
