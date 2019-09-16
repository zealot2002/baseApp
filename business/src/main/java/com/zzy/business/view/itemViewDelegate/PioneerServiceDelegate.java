package com.zzy.business.view.itemViewDelegate;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zzy.business.R;
import com.zzy.common.model.bean.Pioneer;
import com.zzy.common.model.bean.PioneerService;
import com.zzy.common.widget.recycleAdapter.ItemViewDelegate;
import com.zzy.common.widget.recycleAdapter.ViewHolder;

public class PioneerServiceDelegate implements ItemViewDelegate<PioneerService> {
    private TextView tv1, tv2,tv3,tv4;
    private Context context;

    public PioneerServiceDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.busi_pioneer_service_list_item;
    }

    @Override
    public boolean isForViewType(PioneerService item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, PioneerService bean, final int position) {
        try{
            tv1 = holder.itemView.findViewById(R.id.tv1);
            tv2 = holder.itemView.findViewById(R.id.tv2);
            tv3 = holder.itemView.findViewById(R.id.tv3);
            tv4 = holder.itemView.findViewById(R.id.tv4);

            if(!bean.getTv1().isEmpty()){
                tv1.setText(bean.getTv1());
            }
            if(!bean.getTv1().isEmpty()){
                tv2.setText(bean.getTv2());
            }
            if(!bean.getTv3().isEmpty()){
                tv3.setText(bean.getTv3());
                tv3.setVisibility(View.VISIBLE);
            }else{
                tv3.setVisibility(View.GONE);
            }
            if(!bean.getTv4().isEmpty()){
                tv4.setText(bean.getTv4());
                tv4.setVisibility(View.VISIBLE);
            }else{
                tv4.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
