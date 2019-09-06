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
import com.zzy.common.model.bean.FriendsCircle;
import com.zzy.business.view.adapter.MessageAdapter;
import com.zzy.business.view.other.GlideSimpleLoader;
import com.zzy.business.view.other.MessagePicturesLayout;
import com.zzy.business.view.other.SpaceItemDecoration;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.utils.PxUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String IMG_1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566982804511&di=233ff4950d43ca92fd40f3c96474279f&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fc%2F53cdd1f7c1f21.jpg";
    private static final String IMG_2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566982804511&di=7e6b2e73cedc63d1ab669e9241f847a1&imgtype=0&src=http%3A%2F%2Fpic30.nipic.com%2F20130619%2F9885883_210838271000_2.jpg";
    private static final String IMG_3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566982804510&di=aaa212b1218de03991505054df7bfe6e&imgtype=0&src=http%3A%2F%2Fpic16.nipic.com%2F20111006%2F6239936_092702973000_2.jpg";
    private static final String IMG_4 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566982804508&di=5aca9dca7c88522322682280f32d5fda&imgtype=0&src=http%3A%2F%2Fpic51.nipic.com%2Ffile%2F20141025%2F8649940_220505558734_2.jpg";
    private static final String IMG_5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566982804508&di=e764c98ffe13a5ed7749f9bccc80aba1&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150625%2FImg415614733.jpg";
    private static final String IMG_6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566982804506&di=598f56b4be8d340a7c116b9238978d17&imgtype=0&src=http%3A%2F%2Fimg.redocn.com%2Fsheying%2F20140731%2Fqinghaihuyuanjing_2820969.jpg";

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创业朋友圈");
        prepareData();
        setupViews();
    }

    private void prepareData() {
        dataList = new ArrayList<>();

        FriendsCircle data1 = new FriendsCircle();
        data1.avatar = "http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA";
        data1.nickname = "萌新-lpe";
        data1.createTime = "昨天 11:21";
        data1.content = "开司还是那么帅";
        data1.pictureList = Arrays.asList(
                Uri.parse(IMG_1)
        );
        data1.pictureThumbList = Arrays.asList(
                Uri.parse(IMG_1)
        );


        FriendsCircle data2 = new FriendsCircle();
        data2.avatar = "http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull";
        data2.nickname = "苦涩";
        data2.createTime = "昨天 09:59";
        data2.content = "唐僧还是厉害啊。为什么？有宝马";
        data2.pictureList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2)
        );
        data2.pictureThumbList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2)
        );

        FriendsCircle data3 = new FriendsCircle();
        data3.avatar = "http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull";
        data3.nickname = "empty";
        data3.createTime = "昨天 08:12";
        data3.content = "各种眼神";
        data3.pictureList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3)
        );
        data3.pictureThumbList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3)
        );

        FriendsCircle data4 = new FriendsCircle();
        data4.avatar = "http://b167.photo.store.qq.com/psb?/V14EhGon2OmAUI/hQN450lNoDPF.dO82PVKEdFw0Qj5qyGeyN9fByKgWd0!/m/dJWKmWNZEwAAnull";
        data4.nickname = "empty";
        data4.createTime = "昨天 06:00";
        data4.content = "人与人间的信任，就像是纸片，一旦破损，就不会再回到原来的样子。";
        data4.pictureList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3),
                Uri.parse(IMG_4)
        );
        data4.pictureThumbList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3),
                Uri.parse(IMG_4)
        );

        FriendsCircle data5 = new FriendsCircle();
        data5.avatar = "http://qlogo3.store.qq.com/qzone/383559698/383559698/100?1416542262";
        data5.nickname = "越线龙马";
        data5.createTime = "前天 14:61";
        data5.content = "雪.触之即化..愿永久飘零";
        data5.pictureList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3),
                Uri.parse(IMG_4),
                Uri.parse(IMG_5)
        );
        data5.pictureThumbList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3),
                Uri.parse(IMG_4),
                Uri.parse(IMG_5)
        );

        FriendsCircle data6 = new FriendsCircle();
        data6.avatar = "http://b162.photo.store.qq.com/psb?/V14EhGon4cZvmh/z2WukT5EhNE76WtOcbqPIgwM2Wxz4Tb7Nub.rDpsDgo!/b/dOaanmAaKQAA";
        data6.nickname = "顺子要不起";
        data6.createTime = "圣诞节";
        data6.content = "颜宇扬的期末总结\n1、有本事冲我来，别再家长会上吓唬我爸\n2、期末考试成绩出来了，我觉得我妈生二胎是非常明智的选择\n3、这场考试对于我的意义就是知道了班上到底有多少人\n4、期末考试不给老师们露一手，他们还真以为自己教的好";
        data6.pictureList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3),
                Uri.parse(IMG_4),
                Uri.parse(IMG_5),
                Uri.parse(IMG_6)
        );
        data6.pictureThumbList = Arrays.asList(
                Uri.parse(IMG_1),
                Uri.parse(IMG_2),
                Uri.parse(IMG_3),
                Uri.parse(IMG_4),
                Uri.parse(IMG_5),
                Uri.parse(IMG_6)
        );
        dataList.add(data1);
        dataList.add(data2);
        dataList.add(data3);
        dataList.add(data4);
        dataList.add(data5);
        dataList.add(data6);
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


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            // TODO: 2019/8/19   to login
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
