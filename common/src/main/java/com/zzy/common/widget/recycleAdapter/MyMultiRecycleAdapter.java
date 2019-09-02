package com.zzy.common.widget.recycleAdapter;

import android.content.Context;
import com.zzy.common.R;
import java.util.List;


public class MyMultiRecycleAdapter<T> extends MultiBaseAdapter<T> {
    protected Context mContext;
    protected List<T> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;
/*****************************************************************************************************/
    public MyMultiRecycleAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
        setLoadEndView(R.layout.elf_list_load_end);
        setLoadingView(R.layout.elf_list_loading);
        setLoadFailedView(R.layout.elf_list_load_failed);
    }

    @Override
    protected void convert(ViewHolder holder, final T data, int position, int viewType) {
        mItemViewDelegateManager.convert(holder, data, holder.getAdapterPosition());
    }

    public MyMultiRecycleAdapter addItemViewDelegate(ItemViewDelegate itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return mItemViewDelegateManager.getItemViewLayoutId(viewType);
    }

    @Override
    protected int getViewType(int position, T data) {
        return mItemViewDelegateManager.getItemViewType(data,position);
    }
}
