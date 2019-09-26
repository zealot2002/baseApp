package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zzy.business.R;
import com.zzy.business.contract.GoodsContract;
import com.zzy.business.view.itemViewDelegate.ContentCommentDelegate;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.model.bean.Goods;
import com.zzy.business.presenter.GoodsPresenter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.BannerHolderView;
import com.zzy.common.widget.MyEditText;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 商品详情(求购)
 */
public class GoodsDetailBuyActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener, GoodsContract.View {
    private EditText etName,etContact,etPhone,etPrice;
    private MyEditText etDesc;
    private TextView tvReport,tvComment,tvSubmit;
    private ConvenientBanner banner;
    private GoodsContract.Presenter presenter;
    private int id;
    private Goods bean;
    private PopupEditDialog dialog;
    private RelativeLayout rlMsg;
    private EditText etMsg;
    private RecyclerView rvCommentList;
    private MyMultiRecycleAdapter adapter;
    private int msgType = 1;//1:new msg; 2:reply
    private int curCommitId;

    private Button btnShare,btnCall;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setTitle("商品详情");
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            presenter = new GoodsPresenter(this);
            presenter.getDetail(CommonConstants.GOODS_BUY,id);
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
        etPrice = findViewById(R.id.etPrice);
        etDesc = findViewById(R.id.etDesc);
        rlMsg = findViewById(R.id.rlMsg);
        etMsg = findViewById(R.id.etMsg);

        btnShare = findViewById(R.id.btnShare);
        btnCall = findViewById(R.id.btnCall);

        tvReport = findViewById(R.id.tvReport);
        tvSubmit = findViewById(R.id.tvSubmit);
        tvComment = findViewById(R.id.tvComment);

        tvReport.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnCall.setOnClickListener(this);

        etName.setText(bean.getName());
        etContact.setText(bean.getContact());
        etPhone.setText(bean.getPhone());
        etPrice.setText(bean.getPrice());
        etDesc.setText(bean.getDesc());
        updateBanner();
        setupCommentList();
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
    private void updateBanner() {
        banner = findViewById(R.id.banner);
        ArrayList<String> imgs = new ArrayList<>();
        for(int i=0;i<bean.getImgList().size();i++){
            imgs.add(bean.getImgList().get(i).getPath());
        }
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
                }, imgs);
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
        if(v.getId() == R.id.btnShare){
            ToastUtils.showShort("分享");
        }else if(v.getId() == R.id.btnCall){
            AppUtils.callPhone(this,bean.getPhone());
        }else if(v.getId() == R.id.tvReport){
            if(dialog == null){
                dialog = new PopupEditDialog.Builder(this, "举报原因：","完成",
                        new PopupEditDialog.OnClickOkListener() {
                            @Override
                            public void clickOk(String content) {
                                if(content.isEmpty()){
                                    ToastUtils.showShort("内容不能为空");
                                    return;
                                }
                                presenter.report(id,content);
                                dialog.dismiss();
                            }
                        }
                ).create();
            }
            dialog.show();
        }else if(v.getId() == R.id.tvComment){
            showRlMsg(1);
        }else if(v.getId() == R.id.tvSubmit){
            if(msgType == 1){
                presenter.createComment(id,etMsg.getText().toString().trim());
            }else {
                presenter.reply(id,curCommitId,etMsg.getText().toString().trim());
            }
        }
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
    @Override
    public void reload(boolean bShow) {
        presenter.getDetail(CommonConstants.GOODS_BUY,id);
    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onSuccess() {
        ToastUtils.showShort("成功");
        reload(true);
        if(rlMsg!=null){
            rlMsg.setVisibility(View.GONE);
        }
    }
}
