package com.zzy.login.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zzy.common.adapter.PhotoAdapter;
import com.zzy.common.adapter.RecyclerItemClickListener;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.model.bean.Archives;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.LoadingHelper;
import com.zzy.common.widget.TagEditDialog;
import com.zzy.commonlib.utils.FileUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.commonlib.utils.ValidateUtils;
import com.zzy.login.R;
import com.zzy.login.contract.LoginContract;
import com.zzy.login.model.AreaTreeNode;
import com.zzy.login.model.jsonParser.AreasParser;
import com.zzy.login.presenter.LoginPresenter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import fr.ganfra.materialspinner.MaterialSpinner;
import me.gujun.android.taggroup.TagGroup;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * register
 */
public class RegisterActivity extends BaseAppActivity implements View.OnClickListener , LoginContract.View {
    private View lFirst,lNext;
    private EditText etName,etPhone,etSms,etInviter,etIdNo, etPw,etAddress;
    private Button btnNext,btnOk;
    private TextView tvSendSms,tvBirthday;
    private MaterialSpinner spinnerCounty,spinnerTown,spinnerVillage,spinnerUserType;
    private RelativeLayout rlBirthday;
    private TimePickerView pvTime;
    private RadioButton btnMan;

    //next
    private RadioGroup rgIsCompany;
    private RadioButton btnYes;
    private View lCompany,lPersonal;
    private EditText etCompanyName,etCompanyScope;

    //person
    private TagGroup tagView;
    private LoginContract.Presenter presenter;
    private Archives bean;
    private LoadingHelper loadingHelper;
    private TagEditDialog dialog;
    private List<String> userTypeList;

    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    //area
    private AreaTreeNode areaRoot, selectedNode;
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private List<String> list3 = new ArrayList<>();
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);
        StatusBarUtils.setStatusBarFontIconLight(this, false);
        StatusBarUtils.fixStatusHeight(this, (ViewGroup) findViewById(R.id.rootView));
        try{
            setupViews();
            loadingHelper = new LoadingHelper(this);
            presenter = new LoginPresenter(this);
            bean = new Archives();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupViews() throws JSONException {
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("创业之旅");
        RelativeLayout rlBack = findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etSms = findViewById(R.id.etSms);
        etInviter = findViewById(R.id.etInviter);
        etIdNo = findViewById(R.id.etIdNo);
        etPw = findViewById(R.id.etPw);
        etAddress = findViewById(R.id.etAddress);

        tvSendSms = findViewById(R.id.tvSendSms);
        btnNext = findViewById(R.id.btnNext);
        btnOk = findViewById(R.id.btnOk);
        tvBirthday = findViewById(R.id.tvBirthday);
        rlBirthday = findViewById(R.id.rlBirthday);
        btnMan = findViewById(R.id.btnMan);
        lFirst = findViewById(R.id.lFirst);
        lNext = findViewById(R.id.lNext);

        rlBirthday.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvSendSms.setOnClickListener(this);

        setupSpinner();
    }

    private void setupSpinner() throws JSONException {
        String data = FileUtils.readFileFromAssets(this,"areas.json");
        areaRoot = AreasParser.parse(data);
        selectedNode = areaRoot;
        list1.clear();
        for(int i=0;i<areaRoot.getChildren().size();i++){
            list1.add(areaRoot.getChildren().get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, list1);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerCounty = findViewById(R.id.spinnerCounty);
        spinnerCounty.setAdapter(adapter);
        spinnerCounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int p1, long id) {
                list2.clear();
                for(int i=0;i<areaRoot.getChildren().get(p1).getChildren().size();i++){
                    list2.add(areaRoot.getChildren().get(p1).getChildren().get(i).getName());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_item, list2);
                adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinnerTown = findViewById(R.id.spinnerTown);
                spinnerTown.setAdapter(adapter2);
                spinnerTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int p2, long id) {
                        list3.clear();
                        for(int i=0;i<areaRoot.getChildren().get(p1).getChildren().get(p2).getChildren().size();i++){
                            list3.add(areaRoot.getChildren().get(p1).getChildren().get(p2).getChildren().get(i).getName());
                        }
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(RegisterActivity.this, R.layout.spinner_item, list3);
                        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinnerVillage = findViewById(R.id.spinnerVillage);
                        spinnerVillage.setAdapter(adapter3);
                        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int p3, long id) {
                                selectedNode = areaRoot.getChildren().get(p1).getChildren().get(p2).getChildren().get(p3);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        try{
            if(v.getId() == R.id.btnNext){
                if(!checkData()) {
                    return;
                }
                bean.setName(etName.getText().toString().trim());
                bean.setPhone(etPhone.getText().toString().trim());
                bean.setSms(etSms.getText().toString().trim());
                bean.setInviter(etInviter.getText().toString().trim());
                bean.setArea1(selectedNode.getParent().getParent().getName());
                bean.setArea2(selectedNode.getParent().getName());
                bean.setArea3(selectedNode.getName());
                bean.setAddress(etAddress.getText().toString().trim());
                bean.setBirthday(tvBirthday.getText().toString().trim());
                bean.setSex(btnMan.isChecked()?"男":"女");
                bean.setIdNo(etIdNo.getText().toString().trim());
                bean.setPw(etPw.getText().toString().trim());

                presenter.register1(bean);


            }else if(v.getId() == R.id.tvSendSms){
                if(!ValidateUtils.isMobileNO(etPhone.getText().toString().trim())) {
                    ToastUtils.showShort("无效的手机号码");
                    return;
                }
                presenter.getSms(etPhone.getText().toString().trim());
                cdTimer.start();
                tvSendSms.setOnClickListener(null);
            }else if(v.getId() == R.id.rlBirthday){
                //时间选择器
                if(pvTime == null){
                    pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            tvBirthday.setText(cal.get(Calendar.YEAR)+"年"
                                    +(1+cal.get(Calendar.MONTH))+"月"
                                    +cal.get(Calendar.DAY_OF_MONTH)+"日");
                        }
                    }).build();
                }
                pvTime.show();
            }else if(v.getId() == R.id.ivPic){
//                imageConfig = new ImageConfig.Builder(
//                        new GlideLoader())
//                        .steepToolBarColor(getResources().getColor(R.color.titleBlue))
//                        .titleBgColor(getResources().getColor(R.color.titleBlue))
//                        .titleSubmitTextColor(getResources().getColor(R.color.white))
//                        .titleTextColor(getResources().getColor(R.color.white))
//                        // 开启单选   （默认为多选）
//                        .singleSelect()
//                        .requestCode(REQUEST_CODE)
//                        .build();
//                ImageSelector.open(RegisterActivity.this, imageConfig);
            }else if(v.getId() == R.id.btnOk){
                if(tagView.getTags().length>6){
                    ToastUtils.showShort("最多可以添加6个技能");
                    return;
                }
                bean.setUserType(userTypeList.get(spinnerUserType.getSelectedItemPosition()));
                bean.setIsCompany(btnYes.isChecked()?"是":"否");
                bean.setCompanyName(etCompanyName.getText().toString().trim());
                bean.setCompanyScope(etCompanyScope.getText().toString().trim());
                for(String s:tagView.getTags()){
                    bean.getSkills().add(s);
                }
                for(String s:selectedPhotos){
                    bean.setCompanyImgUrl(s);
                }
                presenter.register2(bean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkData() {
        if(!ValidateUtils.isMobileNO(etPhone.getText().toString().trim())){
            ToastUtils.showShort("无效的手机号码");
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cdTimer!=null){
            cdTimer.cancel();
            cdTimer = null;
        }
    }

    private CountDownTimer cdTimer = new CountDownTimer(30*1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvSendSms.setText((millisUntilFinished / 1000) + " s");
        }

        @Override
        public void onFinish() {
            tvSendSms.setText("发送验证码");
            tvSendSms.setOnClickListener(RegisterActivity.this);
        }
    };

    @Override
    public void showError(String s) {

    }

    @Override
    public void onFirstSuccess(String s) {
        bean.setUserId(s);
        showNext();
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
                                    .start(RegisterActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(RegisterActivity.this);
                        }
                    }
                }));

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
    public void onSuccess() {
        ToastUtils.showShort("注册成功");
        startActivity(LoginActivity.class);
    }

    @Override
    public void showLoading() {
        loadingHelper.showLoading();
    }

    @Override
    public void closeLoading() {
        loadingHelper.closeLoading();
    }

    @Override
    public void showDisconnect() {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void updateUI(Object o) {

    }
}
