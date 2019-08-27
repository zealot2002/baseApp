package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzy.business.R;
import com.zzy.business.model.bean.PbRecord;
import com.zzy.business.view.adapter.PbListAdapter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录
 */
public class PhoneBookListActivity extends BaseTitleAndBottomBarActivity{
    private RecyclerView rvDataList;
    private List<PbRecord> dataList;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("通讯录列表");

        prepareData();
        setupViews();
    }

    private void setupViews() {
        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            final PbListAdapter adapter = new PbListAdapter(this);
            rvDataList.setAdapter(adapter);
            adapter.setOnItemClickedListener(new PbListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
//                    //todo  get data
//                    for(int i=0;i<menuList.size();i++){
//                        menuList.get(i).setSelected(i==position?true:false);
//                    }
//                    adapter.notifyDataSetChanged();
                }
            });
            adapter.swapData(dataList);
        }
    }

    private void prepareData() {
        dataList = new ArrayList<>();
        dataList.add(new PbRecord("张三1", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三2", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三3", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三4", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三5", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三1", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三2", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三3", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三4", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
        dataList.add(new PbRecord("张三5", CommonConstants.TEST_IMG_URL,"东坑代表","15010889999"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_activity;
    }

    @Override
    public void reload(boolean bShow) {

    }
}
