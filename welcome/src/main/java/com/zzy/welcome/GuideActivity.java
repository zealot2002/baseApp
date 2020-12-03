package com.zzy.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zzy.common.constants.SPConstants;
import com.zzy.commonlib.base.BaseActivity;
import com.zzy.commonlib.utils.AppUtils;
import com.zzy.flysp.core.spHelper.SPHelper;
import com.zzy.sc.core.serverCenter.SCM;
import com.zzy.sc.core.serverCenter.ScCallback;
import com.zzy.common.constants.ActionConstants;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {

    private BGABanner banner;
    private Button btnOk;

    /****************************************************************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_acitvity_guide);
        SPHelper.save(SPConstants.GUIDE_LOOKER, AppUtils.getVersionName());
        initView();
    }

    public void initView() {
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryMain();
            }
        });
        banner = findViewById(R.id.banner);
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.mipmap.welcome_guide1);
        bannerList.add(R.mipmap.welcome_guide2);
        bannerList.add(R.mipmap.welcome_guide3);

        banner.setData(R.layout.welcome_banner_item, bannerList, null);

        banner.setAdapter(new BGABanner.Adapter<ImageView, Integer>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, Integer resId, int position) {
                itemView.setImageResource(resId);
            }
        });
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    btnOk.setVisibility(View.VISIBLE);
                }else{
                    btnOk.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void entryMain() {
        try {
            SCM.getInstance().req(this, ActionConstants.ENTRY_HOME_ACTIVITY_ACTION, new ScCallback() {
                @Override
                public void onCallback(boolean b, Bundle bundle, String s) {
                    if (b) {
                        // 成功打开Home页面之后
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}