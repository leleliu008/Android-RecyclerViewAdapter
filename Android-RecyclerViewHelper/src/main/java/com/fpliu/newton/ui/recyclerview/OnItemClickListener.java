package com.fpliu.newton.ui.recyclerview;

import com.fpliu.newton.ui.recyclerview.holder.ItemViewHolder;

/**
 * @author 792793182@qq.com 2017-07-25.
 */
public interface OnItemClickListener<T> {
    /**
     * 条目被点击了事件回掉
     *
     * @param holder   ViewHolder
     * @param position 条目的位置
     * @param item     条目对应的数据
     */
    void onItemClick(ItemViewHolder holder, int position, T item);
}
