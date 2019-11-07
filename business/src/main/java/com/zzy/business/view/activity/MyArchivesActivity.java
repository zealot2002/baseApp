package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dl7.tag.TagLayout;
import com.dl7.tag.TagView;
import com.zzy.business.contract.MyArchivesContract;
import com.zzy.business.presenter.MyArchivesPresenter;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.model.bean.Archives;
import com.zzy.common.model.bean.Image;
import com.zzy.common.utils.FileHandler;
import com.zzy.common.utils.InputFilter.EmojiExcludeFilter;
import com.zzy.common.utils.InputFilter.LengthFilter;
import com.zzy.common.widget.TagEditDialog;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.http.HInterface;
import com.zzy.commonlib.log.MyLog;
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
    private TagLayout tagLayout;
    private TagView tagAdd;
    private TagEditDialog dialog;


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
        new FileHandler().savePicToLocal(urlList, new HInterface.DataCallback() {
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
    private void showNext() {
        prepareImage();
        lFirst.setVisibility(View.GONE);
        lNext.setVisibility(View.VISIBLE);
        btnYes = findViewById(R.id.btnYes);

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

        lPersonal = findViewById(R.id.lPersonal);

        lCompany = findViewById(R.id.lCompany);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCompanyScope = findViewById(R.id.etCompanyScope);

        etCompanyName.setText(bean.getCompanyName());
        etCompanyScope.setText(bean.getCompanyScope());

        rgIsCompany = findViewById(R.id.rgIsCompany);
        rgIsCompany.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                lCompany.setVisibility(checkedId == R.id.btnYes?View.VISIBLE:View.GONE);
                lPersonal.setVisibility(checkedId == R.id.btnYes?View.GONE:View.VISIBLE);
            }
        });

        if(bean.getUserType().equals("企业")){
            spinnerUserType.setSelection(0);
            rgIsCompany.check(R.id.btnYes);
        }else{
            rgIsCompany.check(R.id.btnNo);
            if(bean.getUserType().equals("个人创业者")){
                spinnerUserType.setSelection(1);
            }else if(bean.getUserType().equals("低收入农户")){
                spinnerUserType.setSelection(2);
            }else if(bean.getUserType().equals("种植养殖农户")){
                spinnerUserType.setSelection(3);
            }else if(bean.getUserType().equals("其他")){
                spinnerUserType.setSelection(4);
            }
        }

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        tagLayout = findViewById(R.id.tagLayout);

        tagAdd = findViewById(R.id.tagAdd);
        tagAdd.setOnClickListener(this);

        etCompanyName.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );
        etCompanyScope.setFilters(new InputFilter[]{
                new EmojiExcludeFilter(),
                new LengthFilter(200)}
        );

    }

    @Override
    public void onTagList(List<String> tagList) {
        tagLayout.removeAllViews();
        for(int i = 0;i<tagList.size();i++){
            String s = tagList.get(i);
            tagLayout.addTag(s);
            if(bean.getSkills().contains(s)){
                ((TagView)tagLayout.getChildAt(i)).setChecked(true);
            }
        }

        tagLayout.setTagCheckListener(new TagView.OnTagCheckListener() {
            @Override
            public void onTagCheck(final int position, String s, boolean b) {
                MyLog.e("onTagCheck position : "+position);
                if(b && tagLayout.getCheckedTags().size()>5){
                    ToastUtils.showShort("最多可选6个标签");
                    tagLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((TagView)tagLayout.getChildAt(position)).setChecked(false);
                        }
                    },10);
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
                                    .setPhotoCount(6)
                                    .setShowCamera(true)
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

    private List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        for(int i = 0; i < tagLayout.getChildCount(); ++i) {
            tags.add(((TagView)tagLayout.getChildAt(i)).getText());
        }
        return tags;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnModify){
            presenter.getSkillTagList();
            showNext();
        }else if(v.getId() == R.id.tagAdd){
            dialog = new TagEditDialog.Builder(MyArchivesActivity.this,
                    new TagEditDialog.OnClickOkListener() {
                        @Override
                        public void clickOk(String content) {
                            if(getAllTags().contains(content)){
                                ToastUtils.showShort("此标签已存在");
                                return;
                            }
                            if(content.isEmpty()){
                                ToastUtils.showShort("标签不能为空");
                                return;
                            }
                            tagLayout.addTag(content);
                            ((TagView)tagLayout.getChildAt(tagLayout.getChildCount()-1)).setChecked(true);
                            dialog.dismiss();
                        }
                    }
            ).create();
            dialog.show();
        }else if(v.getId() == R.id.btnOk){
            if(btnYes.isChecked()
                    &&!userTypeList.get(spinnerUserType.getSelectedItemPosition()).equals("企业")){
                ToastUtils.showShort("是否企业注册选'是'，则用户类别必须选'企业'");
                return;
            }
            if(!btnYes.isChecked()
                    &&userTypeList.get(spinnerUserType.getSelectedItemPosition()).equals("企业")){
                ToastUtils.showShort("是否企业注册选'否'，则用户类别不能选'企业'");
                return;
            }
            bean.setUserType(userTypeList.get(spinnerUserType.getSelectedItemPosition()));
            bean.setIsCompany(btnYes.isChecked()?"是":"否");
            bean.setCompanyName(etCompanyName.getText().toString().trim());
            bean.setCompanyScope(etCompanyScope.getText().toString().trim());
            bean.getSkills().clear();
            for(String s:tagLayout.getCheckedTags()){
                bean.getSkills().add(s);
            }
            bean.getImgList().clear();
            for(String s:selectedPhotos){
                Image image = new Image();
                image.setPath(s);
                image.setName(s.substring(s.lastIndexOf('/')));
                bean.getImgList().add(image);
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
        finish();
    }

    @Override
    public void showErr(String s) {
        ToastUtils.showShort(s);
    }
}
