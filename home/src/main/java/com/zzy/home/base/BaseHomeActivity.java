package com.zzy.home.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.common.base.BaseAppActivity;
import com.zzy.common.utils.StatusBarUtils;
import com.zzy.commonlib.base.BaseActivity;
import com.zzy.home.R;

import java.util.ArrayList;
import java.util.List;

/**
* zzy
*/

abstract public class BaseHomeActivity extends BaseAppActivity implements View.OnClickListener {
    private Fragment[] fragments;
    private LinearLayout llTab;
    private LinearLayout[] mTabs;
    private int currentTabIndex;
    private TabContext tabContext;
    private FrameLayout fragmentContainer;
    /***********************************************************************************************/
    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_base_activity);
        fixStatusHeight();

        currentTabIndex = 0;
        tabContext = getTabContext();
        setupTabButton();
        setupFragment();
        showFragment(0);
    }

    private void fixStatusHeight() {
        fragmentContainer = findViewById(R.id.fragment_container);
        int h = StatusBarUtils.getStatusBarHeight(this);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fragmentContainer.getLayoutParams();
        lp.setMargins(0, h, 0, 0);
        fragmentContainer.setLayoutParams(lp);
    }

    private void setupFragment() {
    //            fragments = new Fragment[]{new LoanFragment(), new ServiceFragment(), new MineFragment()};
        fragments = tabContext.fragments;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragments[0]).show(fragments[0]).commitAllowingStateLoss();
    }

    protected abstract TabContext getTabContext();

    /**
     * 初始化Tab
     */
    private void setupTabButton() {
        llTab = findViewById(R.id.llTab);
        mTabs = new LinearLayout[4];
        mTabs[0] = findViewById(R.id.llTab1);
        mTabs[1] = findViewById(R.id.llTab2);
        mTabs[2] = findViewById(R.id.llTab3);
        mTabs[3] = findViewById(R.id.llTab4);

        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        mTabs[2].setOnClickListener(this);
        mTabs[3].setOnClickListener(this);
        mTabs[0].setSelected(true);

        ((TextView) mTabs[0].getChildAt(1)).setText(tabContext.getTabBeanList().get(0).text);
        ((TextView) mTabs[1].getChildAt(1)).setText(tabContext.getTabBeanList().get(1).text);
        ((TextView) mTabs[2].getChildAt(1)).setText(tabContext.getTabBeanList().get(2).text);
        ((TextView) mTabs[3].getChildAt(1)).setText(tabContext.getTabBeanList().get(3).text);
    }

    /**
     * 展示/切换Fragment
     * @param index
     */
    private void showFragment(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;

        changeTabColor(currentTabIndex);
        onShowFragment(index);
    }

    protected abstract void onShowFragment(int position);

    /**
     * 切换时变色
     *
     * @param index
     */
    private void changeTabColor(int index) {
        for (int i = 0; i < llTab.getChildCount(); i++) {
            LinearLayout currentView = (LinearLayout) llTab.getChildAt(i);
            ImageView imageView = (ImageView) currentView.getChildAt(0);
            TextView textView = (TextView) currentView.getChildAt(1);
            if (index == i) {
                imageView.setImageResource(tabContext.tabBeanList.get(i).checkedResId);
                textView.setTextColor(this.getResources().getColor(tabContext.checkedTextColorId));
            } else {
                imageView.setImageResource(tabContext.tabBeanList.get(i).normalResId);
                textView.setTextColor(this.getResources().getColor(tabContext.normalTextColorId));
            }
        }
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.llTab1){
            showFragment(0);
        }else if(v.getId() == R.id.llTab2){
            showFragment(1);
        }else if(v.getId() == R.id.llTab3){
            showFragment(2);
        }else if(v.getId() == R.id.llTab4){
            showFragment(3);
        }
    }

    public static final class TabContext{
        List<TabBean> tabBeanList;
        int normalTextColorId;
        int checkedTextColorId;
        Fragment[] fragments;

        public TabContext() {
            tabBeanList = new ArrayList<>();
        }

        public List<TabBean> getTabBeanList() {
            return tabBeanList;
        }

        public void setTabBeanList(List<TabBean> tabBeanList) {
            this.tabBeanList = tabBeanList;
        }

        public int getNormalTextColorId() {
            return normalTextColorId;
        }

        public void setNormalTextColorId(int normalTextColorId) {
            this.normalTextColorId = normalTextColorId;
        }

        public int getCheckedTextColorId() {
            return checkedTextColorId;
        }

        public void setCheckedTextColorId(int checkedTextColorId) {
            this.checkedTextColorId = checkedTextColorId;
        }

        public Fragment[] getFragments() {
            return fragments;
        }

        public void setFragments(Fragment[] fragments) {
            this.fragments = fragments;
        }
    }

    public static final class TabBean{
        String text;
        int normalResId;
        int checkedResId;

        public TabBean(String text, int normalResId, int checkedResId) {
            this.text = text;
            this.normalResId = normalResId;
            this.checkedResId = checkedResId;
        }
    }
}
