package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zzy.business.R;
import com.zzy.business.model.bean.Goods;
import com.zzy.business.view.adapter.GoodsListAdapter;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.constants.CommonConstants;
import com.zzy.common.constants.ParamConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 我要买东西列表
 */
public class GoodsListActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private Button btnNew;
    private RecyclerView rvDataList;
    private List<Goods> dataList;
    private int goodType = 0;
    /***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            goodType = getIntent().getIntExtra(ParamConstants.TYPE,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        prepareData();
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_list_with_btn_activity;
    }

    private void setupViews() {
        btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);

        if(goodType == 0){
            setTitle("我要买东西");
            btnNew.setText("发布求购信息");
        }else{
            setTitle("我要卖东西");
            btnNew.setText("发布售卖信息");
        }

        if(rvDataList == null){
            rvDataList = findViewById(R.id.rvDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvDataList.setLayoutManager(layoutManager);
            rvDataList.setItemAnimator(new DefaultItemAnimator());

            /*adapter*/
            final GoodsListAdapter adapter = new GoodsListAdapter(this);
            rvDataList.setAdapter(adapter);
            adapter.setOnItemClickedListener(new GoodsListAdapter.Listener() {
                @Override
                public void onItemClicked(int position) {
                    Goods bean = dataList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ParamConstants.DATA,bean);
                    startActivity(goodType==0?GoodsDetailBuyActivity.class:GoodsDetailSellActivity.class,bundle);
                }
            });
            adapter.swapData(dataList);
        }
    }
    private void prepareData() {
        dataList = new ArrayList<>();
        for(int i=0;i<10;i++){
            Goods g1 = new Goods();
            g1.setName("【特级东魁】正宗仙居杨梅新鲜东魁杨梅现摘现发");
            g1.setAddress("丽水市景宁县东坑镇上坑村一组80号");
            g1.setContact("吴昌盛");
            g1.setDesc("ivamus eu tellus eleifend, iaculis orci non, sollicitudin orci. Aenean orci leo, sodales a eleifend id, hendrerit eget nunc. Quisque tellus nulla, interdum quis magna vel, convallis rhoncus erat. Phasellus ac interdum est. Integer dapibus pharetra fermentum. Curabitur ut tellus tellus. Fusce viverra auctor placerat. Etiam nec volutpat enim. Aenean aliquam bibendum augue, vitae tincidunt mi. Aliquam efficitur nec nulla quis fermentum. In hac habitasse platea dictumst. Sed ac tellus vel dui porta hendrerit id a augue. Suspendisse potenti.");
            g1.setDealWay("自提");
            g1.setPhone("15855558888");
            g1.setScore(3);
            g1.setPrice("3,000");
            g1.setStartPrice("80");
            g1.setEndPrice("90");
            g1.getImgUrlList().add(CommonConstants.TEST_IMG_URL);
            g1.getImgUrlList().add(CommonConstants.TEST_IMG_URL);
            g1.getImgUrlList().add(CommonConstants.TEST_IMG_URL);
            g1.getImgUrlList().add(CommonConstants.TEST_IMG_URL);
            dataList.add(g1);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnNew){
            startActivity(goodType==0?GoodsNewBuyActivity.class:GoodsNewSellActivity.class);
        }

    }

    @Override
    public void reload(boolean bShow) {

    }
}
