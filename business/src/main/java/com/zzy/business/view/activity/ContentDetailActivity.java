package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.business.contract.GetRichInfoContract;
import com.zzy.business.model.bean.Content;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.business.presenter.GetRichInfoPresenter;
import com.zzy.business.view.itemViewDelegate.ContentCommentDelegate;
import com.zzy.business.view.itemViewDelegate.ContentDelegate;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;

/**
 * 内容详情（困难详情|意见详情|经验详情）
 */
public class ContentDetailActivity extends BaseTitleAndBottomBarActivity
        implements View.OnClickListener , ContentContract.View {
    private RelativeLayout rlLike;
    private TextView tvTitle,tvDate,tvLikeNum,tvReport,tvLookNum,tvComment,tvSubmit;
    private ImageView ivPic;
    private WebView webView;
    private PopupEditDialog dialog;
    private int id,type;
    private ContentContract.Presenter presenter;
    private Content bean;
    private RelativeLayout rlMsg;
    private EditText etMsg;
    private RecyclerView rvCommentList;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private int msgType = 1;//1:new msg; 2:reply
    private int curCommitId;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            id = getIntent().getIntExtra(ParamConstants.ID,0);
            type = getIntent().getIntExtra(ParamConstants.TYPE, CommonConstants.CONTENT_HELP);
            if(CommonConstants.CONTENT_HELP == type){
                setTitle("求助详情");
            }else if(CommonConstants.CONTENT_IDEA == type){
                setTitle("创业点子详情");
            }else if(CommonConstants.CONTENT_EXPERIENCE == type){
                setTitle("分享经验详情");
            }
            presenter = new ContentPresenter(this);
            presenter.getDetail(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Content) o;
            setupViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_content_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        webView = findViewById(R.id.webView);
        tvLookNum = findViewById(R.id.tvLookNum);
        tvReport = findViewById(R.id.tvReport);
        tvLikeNum = findViewById(R.id.tvLikeNum);
        tvComment = findViewById(R.id.tvComment);
        ivPic = findViewById(R.id.ivPic);
        rlLike = findViewById(R.id.rlLike);
        rlMsg = findViewById(R.id.rlMsg);
        etMsg = findViewById(R.id.etMsg);
        tvSubmit = findViewById(R.id.tvSubmit);
//        webView.loadData(testHtml,"text/html","utf-8");
        webView.loadData(bean.getContent(),"text/html","utf-8");

        tvDate.setText("时间: "+bean.getDate());

        String likeNum = TextUtils.isEmpty(bean.getLikeNum())?"":bean.getLikeNum();
        tvLikeNum.setText("赞 ("+ likeNum +")");
        tvLookNum.setText("浏览数 :"+bean.getLookNum());
        if(bean.isPlaceTop()){
            ivPic.setVisibility(View.VISIBLE);
            tvTitle.setText("         "+bean.getTitle());
        }else{
            tvTitle.setText(bean.getTitle());
        }

        tvReport.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
//        if(!bean.isLike()){
        rlLike.setOnClickListener(this);
//        }

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
            adapter.addItemViewDelegate(new ContentCommentDelegate(new ContentCommentDelegate.Listener() {
                @Override
                public void onReply(int position) {
                    curCommitId = Integer.valueOf(bean.getCommentList().get(position).getId());
                    showRlMsg(2);
                }
            }));
            rvCommentList.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rlLike){
            presenter.like(id);
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

    private void showRlMsg(int msgType){
        this.msgType = msgType;
        rlMsg.setVisibility(View.VISIBLE);
        etMsg.requestFocus();
    }
    @Override
    public void reload(boolean bShow) {
        presenter.getDetail(id);
    }

    @Override
    public void showError(String s) {

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
    public void onSuccess() {
        ToastUtils.showShort("成功");
        reload(true);
        if(rlMsg!=null){
            rlMsg.setVisibility(View.GONE);
        }
    }
}
