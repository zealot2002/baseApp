package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zzy.business.contract.MyArchivesContract;
import com.zzy.business.presenter.MyArchivesPresenter;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.model.bean.Archives;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.business.R;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import me.gujun.android.taggroup.TagGroup;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 我的档案
 */
public class MyArchivesActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , MyArchivesContract.View {
    private View lFirst,lNext;
    private Button btnModify,btnOk;
    private TextView tvName,tvPhone,tvArea1,tvArea2,tvArea3,tvAddress,tvBirthday,tvSex,tvIdNo,tvUserType,tvIsCompany;
    private MyArchivesContract.Presenter presenter;
    private Archives bean;

    //next
    private RadioGroup rgIsCompany;
    private RadioButton btnYes;
    private View lCompany,lPersonal;
    private EditText etCompanyName,etCompanyScope;
    private MaterialSpinner spinnerUserType;
    //person
    private TagGroup tagView;
    private List<String> userTypeList;

    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的档案");
        presenter = new MyArchivesPresenter(this);
        presenter.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_archives_activity;
    }

    private void setupViews() {
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvArea1 = findViewById(R.id.tvArea1);
        tvArea2 = findViewById(R.id.tvArea2);
        tvArea3 = findViewById(R.id.tvArea3);
        tvAddress = findViewById(R.id.tvAddress);
        tvBirthday = findViewById(R.id.tvBirthday);
        tvSex = findViewById(R.id.tvSex);
        tvIdNo = findViewById(R.id.tvIdNo);
        tvUserType = findViewById(R.id.tvUserType);
        tvIsCompany = findViewById(R.id.tvIsCompany);
        lFirst = findViewById(R.id.lFirst);
        lNext = findViewById(R.id.lNext);

        btnModify = findViewById(R.id.btnModify);
        btnModify.setOnClickListener(this);

        tvName.setText(bean.getName());
        tvPhone.setText(bean.getPhone());
        tvArea1.setText(bean.getArea1());
        tvArea2.setText(bean.getArea2());
        tvArea3.setText(bean.getArea3());
        tvAddress.setText(bean.getAddress());
        tvBirthday.setText(bean.getBirthday());
        tvSex.setText(bean.getSex());
        tvIdNo.setText(bean.getIdNo());
        tvUserType.setText(bean.getUserType());
        tvIsCompany.setText(bean.getIsCompany());

    }

    private void showNext() {
        lFirst.setVisibility(View.GONE);
        lNext.setVisibility(View.VISIBLE);

        userTypeList = new ArrayList<>();
        userTypeList.add("企业");
        userTypeList.add("个人创业者");
        userTypeList.add("低收入农户");
        userTypeList.add("种植养殖农户");
        userTypeList.add("其他");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item, userTypeList);
        adapter4.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerUserType = findViewById(R.id.spinnerUserType);
        spinnerUserType.setAdapter(adapter4);
        spinnerUserType.setSelection(0);

        lPersonal = findViewById(R.id.lPersonal);

        lCompany = findViewById(R.id.lCompany);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCompanyScope = findViewById(R.id.etCompanyScope);
        setupPhotoPicker();

        btnYes = findViewById(R.id.btnYes);
        rgIsCompany = findViewById(R.id.rgIsCompany);
        rgIsCompany.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                lCompany.setVisibility(checkedId == R.id.btnYes?View.VISIBLE:View.GONE);
                lPersonal.setVisibility(checkedId == R.id.btnYes?View.GONE:View.VISIBLE);
            }
        });
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        tagView = findViewById(R.id.tagView);
        tagView.setTags(bean.getSkills());
        tagView.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {
                if(tagGroup.getTags().length > 6){
                    ToastUtils.showShort("最多可以添加6个技能");
                }
            }

            @Override
            public void onDelete(TagGroup tagGroup, String tag) {
                if(tagGroup.getTags().length < 6){

                }
            }
        });
    }
    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Archives) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
        }
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
                                    .setPhotoCount(1)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(MyArchivesActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(MyArchivesActivity.this);
                        }
                    }
                }));

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnModify){
            showNext();
        }else if(v.getId() == R.id.btnOk){
            bean.setUserType(userTypeList.get(spinnerUserType.getSelectedItemPosition()));
            bean.setIsCompany(btnYes.isChecked()?"是":"否");
            bean.setCompanyName(etCompanyName.getText().toString().trim());
            bean.setCompanyScope(etCompanyScope.getText().toString().trim());
            bean.getSkills().clear();
            for(String s:tagView.getTags()){
                if(bean.getSkills().contains(s)){
                    continue;
                }
                bean.getSkills().add(s);
            }
            for(String s:selectedPhotos){
                bean.setCompanyImgUrl(s);
            }
            presenter.updateArchives(bean);
        }
    }

    @Override
    public void reload(boolean bShow) {
        presenter.start();
    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("成功");
        finish();
    }

    @Override
    public void showErr(String s) {
        ToastUtils.showShort(s);
    }
}
