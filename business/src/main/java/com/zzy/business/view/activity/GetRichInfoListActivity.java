package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzy.business.R;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.business.view.adapter.GetRichInfoListAdapter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 致富信息
 */
public class GetRichInfoListActivity extends BaseTitleAndBottomBarActivity{
    private RecyclerView rvDataList;
    private List<GetRichInfo> dataList;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("致富信息");

        repairData();
        setupViews();
    }

    private void setupViews() {
        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            final GetRichInfoListAdapter adapter = new GetRichInfoListAdapter(this);
            rvDataList.setAdapter(adapter);
            adapter.setOnItemClickedListener(new GetRichInfoListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    startActivity(GetRichInfoDetailActivity.class);
                }
            });
            adapter.swapData(dataList);
        }
    }

    private void repairData() {
        dataList = new ArrayList<>();
        dataList.add(new GetRichInfo("《景宁畲族自治县人民政府关于同意东坑镇行政村规模调整方案的批复》的政策解","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("【特级东魁】正宗仙居杨梅新鲜东魁杨梅绿卡就是快乐","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("【特级东魁】正宗仙居杨梅新鲜东魁杨梅绿卡就是快乐","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));
        dataList.add(new GetRichInfo("6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19"));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_activity;
    }

    @Override
    public void reload(boolean bShow) {

    }
}
