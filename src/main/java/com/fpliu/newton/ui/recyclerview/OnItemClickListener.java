package com.fpliu.newton.ui.recyclerview;

import com.fpliu.newton.ui.recyclerview.holder.ItemViewHolderAbs;

/**
 * @author 792793182@qq.com 2017-07-25.
 */
public interface OnItemClickListener<T, H extends ItemViewHolderAbs> {
    /**
     * 条目被点击了事件回掉
     *
     * @param holder   ViewHolder
     * @param position 条目的位置
     * @param item     条目对应的数据
     */
    void onItemClick(H holder, int position, T item);
}
