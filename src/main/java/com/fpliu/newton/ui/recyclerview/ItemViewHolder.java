package com.fpliu.newton.ui.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * 默认的实现，也可以实现你自己的
 *
 * @author 792793182@qq.com 2017-07-24.
 */
public class ItemViewHolder extends ItemViewHolderAbs<ItemViewHolder> {

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    public static ItemViewHolder newInstance(int layoutId, ViewGroup parent) {
       return newInstance(layoutId, parent, ItemViewHolder.class);
    }
}
