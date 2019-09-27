package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.zzy.business.R;
import com.zzy.business.view.adapter.GridMenuListAdapter;
import com.zzy.common.model.HttpProxy;
import com.zzy.common.model.bean.Industry;
import com.zzy.business.view.adapter.MenuListAdapter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.model.bean.Menu;
import com.zzy.common.network.CommonDataCallback;
import com.zzy.common.utils.CommonUtils;
import com.zzy.commonlib.http.HConstant;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.NetUtils;
import com.zzy.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 产业分布
 */
public class IndustrialDistributionActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private RecyclerView rvMenuList,rvTagList;
    private GridMenuListAdapter gridTagListAdapter;
    private List<Industry> industryList;
    private int curMenuIndex = 0;
    private List<Menu> menuList = new ArrayList<>();
    private List<Menu> tagList = new ArrayList<>();
    private WebView webView;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("产业分布");
        getData();
    }

    private void getData() {
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.getIndustryList(new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        try{
                            industryList = (List<Industry>) o;
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
        return R.layout.busi_industrial_distribution_activity;
    }


    @Override
    public void updateUI(Object o) {
        super.updateUI(o);
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateTagView() throws Exception{
        if(rvTagList == null){
            rvTagList = findViewById(R.id.rvTagList);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
            rvTagList.setLayoutManager(layoutManager);
            rvTagList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            gridTagListAdapter = new GridMenuListAdapter(this);
            rvTagList.setAdapter(gridTagListAdapter);
            gridTagListAdapter.setOnItemClickedListener(new GridMenuListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    for(int i=0;i<tagList.size();i++){
                        tagList.get(i).setSelected(i == position?true:false);
                    }
                    try {
                        updateTagView();
                        getTagData(industryList.get(curMenuIndex).getItemList().get(position).getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        gridTagListAdapter.swapData(tagList);
    }

    private void getTagData(int id) {
        CommonUtils.webLoadData(webView,"");
        if (!NetUtils.isNetworkAvailable(AppUtils.getApp())) {
            ToastUtils.showShort(AppUtils.getApp().getResources().getString(R.string.no_network_tips));
            return;
        }
        showLoading();
        try{
            HttpProxy.getIndustryDetail(id,new CommonDataCallback() {
                @Override
                public void callback(int result, Object o, Object o1) {
                    closeLoading();
                    if (result == HConstant.SUCCESS) {
                        try{
                            String s = (String) o;
                            //显示富文本，http标签由content提供
                            CommonUtils.webLoadData(webView,s);
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

    private void setupViews() {
        try{
            webView = findViewById(R.id.webView);
            setupMenuViews();
            tagList.clear();
            for(int i=0;i<industryList.get(0).getItemList().size();i++){
                Industry.Item item = industryList.get(0).getItemList().get(i);
                tagList.add(new Menu(item.getName(),i == 0?true:false));
            }
            updateTagView();
            if(!industryList.get(curMenuIndex).getItemList().isEmpty()){
                getTagData(industryList.get(curMenuIndex).getItemList().get(0).getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void setupMenuViews() {
        if(rvMenuList == null){
            rvMenuList = findViewById(R.id.rvMenuList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvMenuList.setLayoutManager(layoutManager);
            rvMenuList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            final MenuListAdapter adapter = new MenuListAdapter(this);
            rvMenuList.setAdapter(adapter);
            adapter.setOnItemClickedListener(new MenuListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    try{
                        CommonUtils.webLoadData(webView,"");
                        for(int i=0;i<menuList.size();i++){
                            menuList.get(i).setSelected(i==position?true:false);
                        }
                        curMenuIndex = position;
                        adapter.notifyDataSetChanged();

                        tagList.clear();
                        for(int i=0;i<industryList.get(curMenuIndex).getItemList().size();i++){
                            Industry.Item item = industryList.get(curMenuIndex).getItemList().get(i);
                            tagList.add(new Menu(item.getName(),i == 0?true:false));
                        }
                        updateTagView();
                        getTagData(industryList.get(curMenuIndex).getItemList().get(0).getId());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            for(int i=0;i<industryList.size();i++){
                menuList.add(new Menu(industryList.get(i).getName(),i==0?true:false));
            }
            adapter.swapData(menuList);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void reload(boolean bShow) {

    }
}
