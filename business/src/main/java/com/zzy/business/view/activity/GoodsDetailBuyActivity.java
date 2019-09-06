package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.business.contract.GoodsBuyContract;
import com.zzy.common.model.bean.Goods;
import com.zzy.business.presenter.GoodsBuyPresenter;
import com.zzy.business.utils.InnerUtils;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.MyEditText;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 商品详情
 */
public class GoodsDetailBuyActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, GoodsBuyContract.View {
    private EditText etName,etContact,etPhone,etAddress,etPrice,etDealWay;
    private MyEditText etDesc;
    private Button btnOk;
    private TextView tvScore,tvReport;
    private RatingBar rbScore;
    private ConvenientBanner banner;
    private GoodsBuyContract.Presenter presenter;
    private int id;
    private Goods bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("商品详情");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new GoodsBuyPresenter(this);
            presenter.getDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_goods_detail_buy_activity;
    }

    private void setupViews() {
        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etPrice = findViewById(R.id.etPrice);
        etDealWay = findViewById(R.id.etDealWay);
        etDesc = findViewById(R.id.etDesc);
        tvScore = findViewById(R.id.tvScore);
        tvReport = findViewById(R.id.tvReport);
        rbScore = findViewById(R.id.rbScore);

        tvReport.setOnClickListener(this);
        etName.setText(bean.getName());
        etContact.setText(bean.getContact());
        etPhone.setText(bean.getPhone());
        etAddress.setText(bean.getAddress());
        etPrice.setText(bean.getPrice());
        etDealWay.setText(bean.getDealWay());
        etDesc.setText(bean.getDesc());
        rbScore.setStar(bean.getScore());
        tvScore.setText(InnerUtils.getRatingString(bean.getScore()));
        updateBanner();
    }

    private void updateBanner() {
        banner = findViewById(R.id.banner);
//        banner.setPages(
//                new CBViewHolderCreator() {
//                    @Override
//                    public BannerHolderView createHolder(View itemView) {
//                        return new BannerHolderView(itemView,R.mipmap.icon_default);
//                    }
//                    @Override
//                    public int getLayoutId() {
//                        return R.layout.banner_item;
//                    }
//                }, bean.getImgList());
    }
    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Goods) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btnOk){
//            // TODO: 2019/8/19   to login
//        }else if(v.getId() == R.id.tvToBePioneer){
//
//        }else if(v.getId() == R.id.tvForgetPassword){
//
//        }

    }

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {

    }
}
