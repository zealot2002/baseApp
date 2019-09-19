package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hedgehog.ratingbar.RatingBar;
import com.zzy.business.R;
import com.zzy.business.view.itemViewDelegate.ContentCommentDelegate;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Goods;
import com.zzy.business.utils.InnerUtils;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.widget.MyEditText;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;

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
    private int id;
    private PopupEditDialog dialog;

    private RelativeLayout rlMsg;
    private EditText etMsg;
    private RecyclerView rvCommentList;
    private MyMultiRecycleAdapter adapter;
    private int msgType = 1;//1:new msg; 2:reply
    private int curCommitId;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra(ParamConstants.ID,0);
        getData();
    }

    private void getData() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.getGoodsSellDetail(id,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        updateUI(o);
                    }else if(result == HConstant.FAIL
                            ||result == HConstant.ERROR
                    ){
                        ToastUtils.showShort((String) o);
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Goods) o;
            setupViews();
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

    private void setupCommentList() {
        if(rvCommentList == null){
            rvCommentList = findViewById(R.id.rvCommentList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvCommentList.setLayoutManager(layoutManager);
            rvCommentList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            adapter = new MyMultiRecycleAdapter(this,bean.getCommentList(),false);
            adapter.addItemViewDelegate(new ContentCommentDelegate(id+"",new ContentCommentDelegate.Listener() {
                @Override
                public void onReply(int position) {
                    curCommitId = Integer.valueOf(bean.getCommentList().get(position).getId());
                    showRlMsg(2);
                }
            }));
            rvCommentList.setAdapter(adapter);
        }
    }
    private void showRlMsg(int msgType){
        this.msgType = msgType;
        rlMsg.setVisibility(View.VISIBLE);
        etMsg.requestFocus();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.busi_goods_detail_sell_activity;
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
        rbScore.setmClickable(false);
        tvReport.setOnClickListener(this);

        setupCommentList();
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

    @Override
    public void reload(boolean bShow) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(rlMsg!=null &&rlMsg.getVisibility() == View.VISIBLE){
                rlMsg.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
