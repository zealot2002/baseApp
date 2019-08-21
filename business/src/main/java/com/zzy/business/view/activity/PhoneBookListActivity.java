package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.PbRecord;
import com.zzy.business.view.adapter.MenuListAdapter;
import com.zzy.business.view.adapter.PbListAdapter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录
 */
public class PhoneBookListActivity extends BaseTitleAndBottomBarActivity{
    private RecyclerView rvPbList;
    private List<PbRecord> dataList;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("通讯录列表");

        repairPb();
        setupViews();
    }

    private void setupViews() {
        if(rvPbList == null){
            rvPbList = findViewById(R.id.rvPbList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvPbList.setLayoutManager(layoutManager);
            rvPbList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            final PbListAdapter adapter = new PbListAdapter(this);
            rvPbList.setAdapter(adapter);
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

    private void repairPb() {
        dataList = new ArrayList<>();
        dataList.add(new PbRecord("张三1","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三2","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三3","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三4","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三1","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三2","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三3","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三4","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三1","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三2","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三3","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
        dataList.add(new PbRecord("张三4","http://img0.imgtn.bdimg.com/it/u=626895405,3008362059&fm=26&gp=0.jpg","东坑代表","15010889999"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_pb_list_activity;
    }

}
