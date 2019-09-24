package com.zzy.business.view.activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.ielse.imagewatcher.ImageWatcher;
import com.zzy.business.R;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.business.view.adapter.MessageAdapter;
import com.zzy.business.view.other.GlideSimpleLoader;
import com.zzy.business.view.other.MessagePicturesLayout;
import com.zzy.business.view.other.SpaceItemDecoration;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.common.widget.PopupEditDialog;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.PxUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.List;


/**
 * 创业朋友圈
 */
public class FriendsCircleActivity extends BaseTitleAndBottomBarActivity implements
        View.OnClickListener, MessagePicturesLayout.Callback, ImageWatcher.OnPictureLongPressListener {
    private Button btnNew;

    private ImageWatcher vImageWatcher;
    private MessageAdapter adapter;
    private RecyclerView rvDataList;
    private List<FriendsCircle> dataList;
    private PopupEditDialog dialog;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业朋友圈");
        getData();

    }

    private void getData() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.getFriendList(new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        try{
                            dataList = (List<FriendsCircle>) o;
                            setupViews();
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
    protected int getLayoutId() {
        return R.layout.busi_friends_circle_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);

        rvDataList = findViewById(R.id.rvDataList);
        rvDataList.setLayoutManager(new LinearLayoutManager(this));
        rvDataList.addItemDecoration(new SpaceItemDecoration(this).setSpace(14).setSpaceColor(0xD30440B8));
        rvDataList.setAdapter(adapter = new MessageAdapter(this).setPictureClickCallback(this));
        adapter.set(dataList);
        adapter.setListener(new MessageAdapter.OnEventListener() {
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
                                    report(position,content);
                                    dialog.dismiss();
                                }
                            }
                    ).create();
                }
                dialog.show();

            }
        });
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
//        Utils.fitsSystemWindows(isTranslucentStatus, findViewById(R.id.v_fit));
    }

    private void report(int position,String content) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.reportContent(Integer.valueOf(dataList.get(position).getId()),content,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        ToastUtils.showShort("成功");
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
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            startActivity(FriendNewActivity.class);
        }
    }

    @Override
    public void reload(boolean bShow) {

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
}
