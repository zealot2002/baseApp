package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.business.model.bean.Goods;
import com.zzy.business.utils.InnerUtils;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.BannerHolderView;
import com.zzy.common.widget.MyEditText;
import com.zzy.common.widget.PopupEditDialog;
/**
 * 商品详情（卖）
 */
public class GoodsDetailSellActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etName,etContact,etPhone,etAddress,etPrice,etDealWay;
    private MyEditText etDesc;
//    private Button btnOk;
    private TextView tvScore,tvReport;
    private RatingBar rbScore;
    private ConvenientBanner banner;
    private Goods bean;
    private PopupEditDialog dialog;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
        updateUI();
    }

    private void updateUI() {
        try{
            bean = (Goods) getIntent().getSerializableExtra(ParamConstants.DATA);
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_goods_detail_sell_activity;
    }

    private void setupViews() {
        setTitle("商品详情");
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
    }

    private void updateBanner() {
        banner = findViewById(R.id.banner);
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
                }, bean.getImgUrlList());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvReport){
            if(dialog == null){
                dialog = new PopupEditDialog.Builder(this, "举报原因：","完成",
                        new PopupEditDialog.OnClickOkListener() {
                            @Override
                            public void clickOk(String content) {
                                // TODO: 2019/8/23  report content
                                dialog.dismiss();
                            }
                        }
                ).create();
            }
            dialog.show();
        }
    }
}
