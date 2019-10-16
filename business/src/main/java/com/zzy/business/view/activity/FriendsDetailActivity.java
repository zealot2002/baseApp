package com.zzy.business.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.business.view.itemViewDelegate.ContentCommentDelegate;
import com.zzy.business.view.itemViewDelegate.FriendDelegate;
import com.zzy.business.view.other.GlideSimpleLoader;
import com.zzy.business.view.other.MessagePicturesLayout;
import com.zzy.business.view.other.SpaceItemDecoration;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.HttpConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.glide.ImageLoader;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Comment;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.common.model.bean.Image;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.common.widget.recycleAdapter.MyLinearLayoutManager;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.PxUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 朋友圈详情
 */
public class FriendsDetailActivity extends BaseTitleAndBottomBarActivity implements
        View.OnClickListener, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener , ContentContract.View {

    private LinearLayout lLike;
    private ImageWatcher vImageWatcher;
    private ImageView iAvatar;
    private TextView tNickname, tTime, tContent,tvCommentNum,tvLikeNum,tvComment,tvSubmit;
    private MessagePicturesLayout lPictures;

    private ContentContract.Presenter presenter;
    private int id;
    private Content bean;

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
        setTitle("创业朋友圈");

        try{
            id = getIntent().getIntExtra(ParamConstants.ID,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        presenter = new ContentPresenter(this);
        presenter.getDetail(id);
        setupViews();
    }

    private void getData(int pageNum) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.getFriendList(pageNum,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        try{
                            updateUI(o);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
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

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            bean = (Content) o;
            updateViews();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.busi_friends_detail_activity;
    }

    private void showRlMsg(int msgType){
        this.msgType = msgType;
        rlMsg.setVisibility(View.VISIBLE);
        etMsg.requestFocus();
    }

    private void setupCommentList() {
        if(rvCommentList == null){
            rvCommentList = findViewById(R.id.rvCommentList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvCommentList.setLayoutManager(layoutManager);
            rvCommentList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            adapter = new MyMultiRecycleAdapter(this,new ArrayList<Comment>(),false);
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

    private void updateViews(){
        ImageLoader.loadImage(iAvatar,bean.getUserHeadUrl());
        tNickname.setText(bean.getUserName());
        tTime.setText(bean.getDate());
        tvCommentNum.setText(bean.getLookNum());
        tvLikeNum.setText(bean.getLikeNum());
        tContent.setText(bean.getContent());

        List<Uri> uris = new ArrayList<>();
        for(Image image:bean.getImgList()){
            Uri uri = Uri.parse(image.getPath());
            uris.add(uri);
        }
        lPictures.set(uris,uris);
        adapter.setNewData(bean.getCommentList());
    }
    private void setupViews() {
        if(etMsg == null) {
            etMsg = findViewById(R.id.etMsg);
            rlMsg = findViewById(R.id.rlMsg);
            iAvatar =  findViewById(R.id.i_avatar);
            tNickname =  findViewById(R.id.t_nickname);
            tTime = findViewById(R.id.t_time);
            tvCommentNum = findViewById(R.id.tvCommentNum);
            lLike = findViewById(R.id.lLike);
            tvLikeNum = findViewById(R.id.tvLikeNum);
            tvComment = findViewById(R.id.tvComment);
            tContent = findViewById(R.id.t_content);
            tvSubmit = findViewById(R.id.tvSubmit);

            tvSubmit.setOnClickListener(this);
            lLike.setOnClickListener(this);
            tvComment.setOnClickListener(this);

            lPictures = findViewById(R.id.l_pictures);
            lPictures.setCallback(this);

            // 一般来讲， ImageWatcher 需要占据全屏的位置
            vImageWatcher = (ImageWatcher) findViewById(R.id.v_image_watcher);
            // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
            int statusBarHeight = StatusBarUtils.getStatusBarHeight(this) + PxUtils.dp2px(this,70);
            vImageWatcher.setTranslucentStatus(statusBarHeight);
            // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
            vImageWatcher.setErrorImageRes(R.mipmap.error_picture);
            // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
            vImageWatcher.setOnPictureLongPressListener(this);
            vImageWatcher.setLoader(new GlideSimpleLoader());
            vImageWatcher.addOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
                @Override
                public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, Uri uri, float animatedValue, int actionTag) {
//                Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
                }

                @Override
                public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
                    if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
//                    Toast.makeText(getApplicationContext(), "点击了图片 [" + position + "]" + uri + "", Toast.LENGTH_SHORT).show();
                    } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
//                    Toast.makeText(getApplicationContext(), "退出了查看大图", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            setupCommentList();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.lLike){
            presenter.like(id);
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
    public void reload(boolean bShow) {
        presenter.getDetail(id);
    }

    @Override
    public void onThumbPictureClick(ImageView i, SparseArray<ImageView> imageGroupList, List<Uri> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }

    @Override
    public void onPictureLongPress(ImageView v, Uri uri, int pos) {
//        Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        // 没有使用helper初始化
        if (!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void showError(String s) {
        ToastUtils.showShort(s);
    }

    @Override
    public void onSuccess() {
        if(rlMsg!=null){
            rlMsg.setVisibility(View.GONE);
        }
        reload(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload(true);
    }
}
