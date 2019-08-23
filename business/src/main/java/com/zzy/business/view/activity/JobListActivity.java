package com.zzy.business.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zzy.business.R;
import com.zzy.business.model.bean.Jd;
import com.zzy.business.view.adapter.JobListAdapter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.ParamConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 我要招聘列表
 */
public class JobListActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Button btnNew;
    private RecyclerView rvDataList;
    private List<Jd> dataList;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我要招聘");
        prepareData();
        setupViews();
    }

    private void prepareData() {
        dataList = new ArrayList<>();
        dataList.add(new Jd("雅景多肉基地","省农业技术推广总部",
                "分拣多肉工人","东坑镇深样村",
                "10人","学历不限","2000.00元~3000.00元",
                "13322245678","田女士",
                "1，负责中央空调，冷却塔设备的维护、故障检修、配件更换、定期巡检、日常保养等工作；\n" +
                "2、负责与客户及时沟通，处理客户的故障报修工作；",
                "1、从事中央空调，冷却塔售后2年以上工作经验\n" +
                        "2、可独立完成故障排查、机组保养、维修及主机维修工作，独立完成制冷机清洗；\n" +
                        "3、工作认真踏实，态度积极乐观，具备一定沟通能力及良好的服务意识；\n" +
                        "4、持有电工证、制冷证、司炉证、驾照等条件优秀者可适当放宽要求，公司提供培训机会。",
                "2019-02-19"));

        dataList.add(new Jd("雅景多2肉基地","省农业技术推广总部",
                "分拣多肉工人","东坑镇深样村",
                "10人","学历不限","2000.00元~3000.00元",
                "13322245678","田女士",
                "1，负责中央空调，冷却塔设备的维护、故障检修、配件更换、定期巡检、日常保养等工作；\n" +
                        "2、负责与客户及时沟通，处理客户的故障报修工作；",
                "1、从事中央空调，冷却塔售后2年以上工作经验\n" +
                        "2、可独立完成故障排查、机组保养、维修及主机维修工作，独立完成制冷机清洗；\n" +
                        "3、工作认真踏实，态度积极乐观，具备一定沟通能力及良好的服务意识；\n" +
                        "4、持有电工证、制冷证、司炉证、驾照等条件优秀者可适当放宽要求，公司提供培训机会。",
                "2019-02-19"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_with_btn_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        btnNew.setText("发布招聘信息");
        btnNew.setOnClickListener(this);

        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            final JobListAdapter adapter = new JobListAdapter(this);
            rvDataList.setAdapter(adapter);
            adapter.setOnItemClickedListener(new JobListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    Jd bean = dataList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ParamConstants.DATA,bean);
                    Intent it = new Intent();
                    it.putExtras(bundle);
                    it.setClass(JobListActivity.this, JobDetailActivity.class);
                    startActivity(it);
                }
            });
            adapter.swapData(dataList);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            startActivity(JobNewActivity.class);
        }

    }
}
