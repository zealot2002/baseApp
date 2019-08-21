package com.zzy.business.view.activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.Menu;
import com.zzy.business.view.adapter.MenuListAdapter;
import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 产业分布
 */
public class IndustrialDistributionActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private EditText etPhone,etPassword;
    private Button btnOk;
    private TextView tvToBePioneer,tvForgetPassword;


    private RecyclerView rvMenuList,rvDataList;
    private List<Menu> menuList;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("产业分布");

        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_industrial_distribution_activity;
    }

    private void setupViews() {
        updateMenuViews();
//        etPhone = findViewById(R.id.etPhone);
//        etPassword = findViewById(R.id.etPassword);
//        btnOk = findViewById(R.id.btnOk);
//        tvToBePioneer = findViewById(R.id.tvToBePioneer);
//        tvForgetPassword = findViewById(R.id.tvForgetPassword);
//
//        btnOk.setOnClickListener(this);
//        tvToBePioneer.setOnClickListener(this);
//        tvForgetPassword.setOnClickListener(this);
    }
    private void updateMenuViews() {
        if(rvMenuList == null){
            repairMenu();
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
                    //todo  get data
                    for(int i=0;i<menuList.size();i++){
                        menuList.get(i).setSelected(i==position?true:false);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            adapter.swapData(menuList);
        }
    }

    private void repairMenu() {
        menuList = new ArrayList<>();
        menuList.add(new Menu("东坑村",true));
        menuList.add(new Menu("北溪村",false));
        menuList.add(new Menu("深阳村",false));
    }

    @Override
    public void onClick(View v) {
//        if(v.getId() == R.id.btnOk){
//            // TODO: 2019/8/19   to login
//        }else if(v.getId() == R.id.tvToBePioneer){
//
//        }else if(v.getId() == R.id.tvForgetPassword){
//
//        }

    }
}
