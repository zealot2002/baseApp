package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Comment;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.view.itemViewDelegate.CommentDelegate;
import com.zzy.common.widget.recycleAdapter.MyMultiRecycleAdapter;
import com.zzy.common.widget.recycleAdapter.OnItemChildClickListener;
import com.zzy.common.widget.recycleAdapter.OnLoadMoreListener;
import com.zzy.common.widget.recycleAdapter.ViewHolder;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.servercentre.ActionConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的评论
 */
public class MyCommentActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private RelativeLayout rlMenu1,rlMenu2;
    private TextView tv1,tv2;
    //data
    private int pageNum = 1;
    private RecyclerView rvDataList;
    private OnLoadMoreListener onLoadMoreListener;
    private MyMultiRecycleAdapter adapter;
    private boolean isLoadOver = false;
    private List<Comment> dataList = new ArrayList<>();
    private int currentMenu = 0;

    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的评论");
        getList(0,pageNum);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_my_comment_list_activity;
    }

    private void setupViews() {
        rlMenu1 = findViewById(R.id.rlMenu1);
        rlMenu2 = findViewById(R.id.rlMenu2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        rlMenu1.setOnClickListener(this);
        rlMenu2.setOnClickListener(this);

        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

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
                        getList(currentMenu,pageNum);
                    }else{
                        getList(currentMenu,++pageNum);
                    }
                }
            };
            adapter.setOnLoadMoreListener(onLoadMoreListener);
            adapter.addItemViewDelegate(new CommentDelegate(this));
            adapter.setOnItemChildClickListener(R.id.rootView, new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(ViewHolder viewHolder, Object data, int position) {
                    try{
                        Bundle bundle = new Bundle();
                        Comment bean = dataList.get(position);
                        if(!TextUtils.isEmpty(bean.getContentId())){
                            bundle.putInt(ParamConstants.ID,Integer.valueOf(bean.getContentId()));
                        }
                        if(bean.getType().equals("朋友圈")){
                            startActivity(FriendsDetailActivity.class,bundle);
                            return;
                        }
                        if(bean.getType().equals("资讯评论")){
                            startActivity(GetRichInfoDetailActivity.class,bundle);
                            return;
                        }
                        if(bean.getType().equals("求购评论")
                        ){
                            bundle.putInt(ParamConstants.ID,Integer.valueOf(bean.getGoodsId()));
                            startActivity(GoodsDetailBuyActivity.class,bundle);
                            return;
                        }
                        if(bean.getType().equals("售卖评论")
                        ){
                            bundle.putInt(ParamConstants.ID,Integer.valueOf(bean.getGoodsId()));
                            startActivity(GoodsDetailSellActivity.class,bundle);
                            return;
                        }

                        if(bean.getType().equals("求助")){
                            bundle.putInt(ParamConstants.TYPE,CommonConstants.CONTENT_HELP);
                        }else if(bean.getType().equals("意见")){
                            bundle.putInt(ParamConstants.TYPE,CommonConstants.CONTENT_IDEA);
                        }else if(bean.getType().equals("分享经验")){
                            bundle.putInt(ParamConstants.TYPE,CommonConstants.CONTENT_EXPERIENCE);
                        }
                        SCM.getInstance().req(MyCommentActivity.this, ActionConstants.ENTRY_CONTENT_DETAIL_ACTIVITY_ACTION,bundle);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            rvDataList.setAdapter(adapter);
        }
    }

    private void getList(int currentMenu, int pageNum) {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            if(currentMenu == 0){
                HttpProxy.getMyCommentList(pageNum,new CommonDataCallback() {
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
            }else {
                HttpProxy.getMyReplyList(pageNum,new CommonDataCallback() {
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
            }
        }catch(Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        resetMenu();
        if(v.getId() == R.id.rlMenu1){
            rlMenu1.setBackgroundResource(R.color.white);
            tv1.setTextColor(getResources().getColor(R.color.blue));
            reset();
            currentMenu = 0;
            getList(currentMenu,pageNum);
        }else if(v.getId() == R.id.rlMenu2){
            rlMenu2.setBackgroundResource(R.color.white);
            tv2.setTextColor(getResources().getColor(R.color.blue));
            reset();
            currentMenu = 1;
            getList(currentMenu,pageNum);
        }
    }

    private void resetMenu(){
        rlMenu1.setBackgroundResource(R.color.translucent);
        rlMenu2.setBackgroundResource(R.color.translucent);
        tv1.setTextColor(getResources().getColor(R.color.white));
        tv2.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{
            if(pageNum!=1){
                appendList((List<Comment>) o);
                return;
            }
            dataList.addAll((List<Comment>) o);
            setupViews();
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(e.toString());
        }
    }
    private void appendList(List<Comment> list) {
        if(list == null
                ||list.isEmpty()
        ){
            adapter.loadEnd();
        }
        if(list.isEmpty()){
            isLoadOver = true;
            return;
        }
        adapter.setLoadMoreData(list);
    }

    @Override
    public void reload(boolean bShow) {

    }

    private void reset() {
        pageNum = 1;
        isLoadOver = false;
        dataList.clear();
        if(adapter!=null){
            adapter.reset();
        }
    }
}
