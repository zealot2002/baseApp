package com.zzy.home.view.activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.zzy.commonlib.utils.AppUtils;
import com.zzy.commonlib.utils.ToastUtils;
import com.zzy.home.R;
import com.zzy.home.base.BaseHomeActivity;
import com.zzy.home.view.fragment.CommunityFragment;
import com.zzy.home.view.fragment.HomeFragment;


/**
 * 首页
 */
public class HomeActivity extends BaseHomeActivity{
    private static final String TAG = "HomeActivity";
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected TabContext getTabContext() {
        TabContext tabContext = new TabContext();
        TabBean tab1 = new TabBean("首页",R.mipmap.home_school_icon_normal,R.mipmap.home_school_icon_selected);
        TabBean tab2 = new TabBean("发现",R.mipmap.home_exam_icon_normal,R.mipmap.home_exam_icon_selected);
        TabBean tab3 = new TabBean("拍卖",R.mipmap.home_community_icon_normal,R.mipmap.home_community_icon_selected);
        TabBean tab4 = new TabBean("我的",R.mipmap.home_mine_icon_normal,R.mipmap.home_mine_icon_selected);
        tabContext.getTabBeanList().add(tab1);
        tabContext.getTabBeanList().add(tab2);
        tabContext.getTabBeanList().add(tab3);
        tabContext.getTabBeanList().add(tab4);

        tabContext.setNormalTextColorId(R.color.home_tab_text_color_normal);
        tabContext.setCheckedTextColorId(R.color.home_tab_text_color_checked);

        tabContext.setFragments(new Fragment[]{
                HomeFragment.getInstance(),
                CommunityFragment.getInstance(),
                CommunityFragment.getInstance(),
                CommunityFragment.getInstance(),
                });
        return tabContext;
    }

    @Override
    protected void onShowFragment(int position) {
        if(position == 3){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.orange));
            }
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            }
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                AppUtils.exitApp(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
