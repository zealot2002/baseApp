package com.zzy.business.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.business.model.bean.GetRichInfo;
import com.zzy.common.base.BaseTitleAndBottomBarActivity;
import com.zzy.common.base.BaseToolbarActivity;

/**
 * 致富信息详情
 */
public class GetRichInfoDetailActivity extends BaseTitleAndBottomBarActivity implements View.OnClickListener {
    private RelativeLayout rlPraise;
    private TextView tvTitle,tvDate,tvContent,tvGoodNum,tvLookNum;
    private GetRichInfo bean;
/***********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("致富信息");
        setupViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.busi_get_rich_info_detail_activity;
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.rootView).findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvContent = findViewById(R.id.tvContent);
        tvLookNum = findViewById(R.id.tvLookNum);
        tvGoodNum = findViewById(R.id.tvGoodNum);

        bean = new GetRichInfo("白鹤文化节需要帮忙妇女5人6月20号 白鹤文化节需要帮忙妇女5人","省农业技术推广总部","2019-02-19");
        bean.setContent("的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "\n\n\n\n" +
                "" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "\n\n\n\n" +
                "" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "\n\n\n\n" +
                "" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "\n\n\n\n" +
                "" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "的复古风多福多寿发达发达时阿凡达说发 似懂非懂书法大师范德萨发空间的时刻啦" +
                "\n\n\n\n" +
                "" +
                "放假扣篮大赛九分裤两件大事风口浪尖的撒开了房间的是咖喱饭  ");
        bean.setGoodNum("40");
        bean.setLookNum("2000");

        tvTitle.setText(bean.getTitle());
        tvDate.setText("时间: "+bean.getDate());
        tvContent.setText(bean.getContent());
        tvGoodNum.setText("赞 ("+bean.getGoodNum()+")");
        tvLookNum.setText("浏览数 :"+bean.getLookNum());
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
