package com.zzy.business.view.activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.ielse.imagewatcher.ImageWatcher;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zzy.business.R;
import com.zzy.business.contract.ContentContract;
import com.zzy.business.presenter.ContentPresenter;
import com.zzy.business.view.itemViewDelegate.FriendDelegate;
import com.zzy.business.view.itemViewDelegate.GoodsDelegate;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Content;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.business.view.adapter.MessageAdapter;
import com.zzy.business.view.other.GlideSimpleLoader;
import com.zzy.business.view.other.MessagePicturesLayout;
import com.zzy.business.view.other.SpaceItemDecoration;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.model.bean.GetRichInfo;
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
 * 创业朋友圈
 */
public class FriendsCircleActivity extends BaseTitleAndBottomBarActivity implements
        View.OnClickListener, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener , ContentContract.View, OnRefreshListener {
    private Button btnNew;

    private ImageWatcher vImageWatcher;
    private MyMultiRecycleAdapter adapter;
    private RecyclerView rvDataList;
    private List<FriendsCircle> dataList = new ArrayList<>();
    private PopupEditDialog dialog;
    private ContentContract.Presenter presenter;
    private EditText etMsg;
    private SmartRefreshLayout smartRefreshLayout;
    private OnLoadMoreListener onLoadMoreListener;
    private int pageNum = 1;
    private boolean isLoadOver = false,isReload = true;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业朋友圈");

        presenter = new ContentPresenter(this);
//        getData(pageNum);

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
            if(smartRefreshLayout!=null){
                smartRefreshLayout.finishRefresh();
            }
            List<FriendsCircle> list = (List<FriendsCircle>) o;
            if(list == null){
                return;
            }
            setupViews();
            if(isReload){
                isReload = false;
                reset();
                if(rvDataList!=null){
                    rvDataList.scrollToPosition(0);
                }

                dataList.addAll(list);
                adapter.notifyDataSetChanged();
            }else {
                adapter.setLoadMoreData(list);
            }

            if(list.isEmpty()
                    ||list.size()< CommonConstants.PAGE_SIZE
            ){
                smartRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.loadEnd();
                    }
                },10);
                isLoadOver = true;
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
//    private void appendList(List<FriendsCircle> list) {
//        if(list == null
//                ||list.isEmpty()
//        ){
//            adapter.loadEnd();
//        }
//        if(list.isEmpty()){
//
//        }
//        adapter.setLoadMoreData(list);
//    }
    @Override
    protected int getLayoutId() {
        return R.layout.busi_friends_circle_activity;
    }

//    private void showRlMsg(int msgType){
//        this.msgType = msgType;
//        rlMsg.setVisibility(View.VISIBLE);
//        etMsg.requestFocus();
//    }

    private void setupViews() {
        if(smartRefreshLayout == null){
            smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
            smartRefreshLayout.setEnableRefresh(true);
            smartRefreshLayout.setOnRefreshListener(this);

            btnNew = findViewById(R.id.btnNew);
            btnNew.setOnClickListener(this);
            etMsg = findViewById(R.id.etMsg);

            rvDataList = findViewById(R.id.rvDataList);
            rvDataList.setLayoutManager(new MyLinearLayoutManager(this));
            rvDataList.addItemDecoration(new SpaceItemDecoration(this).setSpace(14).setSpaceColor(0xD30440B8));

            /*adapter*/
            adapter = new MyMultiRecycleAdapter(this,dataList,true);
            //设置不满一屏幕，自动加载第二页
            adapter.openAutoLoadMore();
            //加载更多的事件监听
            onLoadMoreListener = new OnLoadMoreListener() {
                @Override
                public void onLoadMore(boolean isReload) {
                    if(isLoadOver){
                        return;
                    }
                    if(isReload){
                        getData(pageNum);
                    }else{
                        getData(++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new FriendDelegate(new FriendDelegate.OnEventListener() {
                @Override
                public void onReport(final int position) {
                    if(dialog == null){
                        dialog = new PopupEditDialog.Builder(FriendsCircleActivity.this, "举报原因：","完成",
                                new PopupEditDialog.OnClickOkListener() {
                                    @Override
                                    public void clickOk(String content) {
                                        if(content.isEmpty()){
                                            ToastUtils.showShort("内容不能为空");
                                            return;
                                        }
                                        presenter.report(Integer.valueOf(dataList.get(position).getId()),content);
                                        dialog.dismiss();
                                    }
                                }
                        ).create();
                    }
                    dialog.show();
                }

                @Override
                public void onComment(int position) {
//                presenter.createComment(Integer.valueOf(dataList.get(position).getId()),);
                }

                @Override
                public void onLike(int position) {
                    presenter.like(Integer.valueOf(dataList.get(position).getId()));
                }
            },this));
            rvDataList.setAdapter(adapter);
//
            // **************   xml 方式加载  ********  推荐使用后面demo的iwHelper

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
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            startActivity(FriendNewActivity.class);
        }
    }

    @Override
    public void reload(boolean bShow) {
        try{
            isReload = true;
            pageNum = 1;
            getData(pageNum);
        }catch (Exception e){
            ToastUtils.showShort(e.toString());
            e.printStackTrace();
        }
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
        ToastUtils.showShort("成功");
        reload(true);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        reload(true);
    }

    private void reset() {
        pageNum = 1;
        isLoadOver = false;
        dataList.clear();
        if(adapter!=null){
            adapter.reset();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reload(true);
    }
}
