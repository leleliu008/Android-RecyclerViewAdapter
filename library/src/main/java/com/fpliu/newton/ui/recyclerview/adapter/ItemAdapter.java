package com.fpliu.newton.ui.recyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.fpliu.newton.ui.recyclerview.OnItemClickListener;
import com.fpliu.newton.ui.recyclerview.R;
import com.fpliu.newton.ui.recyclerview.holder.ItemViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的万能Adapter
 *
 * @author 792793182@qq.com 2017-07-18.
 */
public abstract class ItemAdapter<T> extends RecyclerView.Adapter<ItemViewHolder> implements List<T>, OnItemClickListener<T>, View.OnClickListener {

    //数据项集合
    private List<T> mItems;

    private OnItemClickListener<T> onItemClickListener = this;

    /**
     * 构造方法
     *
     * @param items 要显示的项的数据列表
     */
    public ItemAdapter(List<T> items) {
        mItems = items;
    }

    public void setItems(List<T> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        if (onItemClickListener != null) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.newInstance(onBindLayout(parent, viewType), parent);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        View itemView = holder.getItemView();
        itemView.setTag(R.id.id_recycler_view_item_holder, holder);
        itemView.setTag(R.id.id_recycler_view_item_position, position);
        itemView.setOnClickListener(this);
        onBindViewHolder(holder, position, getItem(position));
    }

    public abstract int onBindLayout(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(ItemViewHolder holder, int position, T item);

    @Override
    public void onItemClick(ItemViewHolder holder, int position, T item) {

    }

    @Override
    public void onClick(View view) {
        Object positionObj = view.getTag(R.id.id_recycler_view_item_position);
        if (positionObj == null) {
            return;
        }
        ItemViewHolder holder = (ItemViewHolder) view.getTag(R.id.id_recycler_view_item_holder);
        int position = (int) positionObj;
        T item = getItem(position);
        onItemClickListener.onItemClick(holder, position, item);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public List<T> getItems() {
        return mItems;
    }

    public T getItem(int position) {
        return mItems == null ? null : mItems.get(position);
    }

    public T getLastItem() {
        return mItems == null || mItems.isEmpty() ? null : mItems.get(size() - 1);
    }

    @Override
    public void add(int location, T object) {
        if (object == null) {
            return;
        }

        if (mItems == null) {
            mItems = new ArrayList<T>();
        }

        mItems.add(location, object);
        notifyItemInserted(location);
    }

    @Override
    public boolean add(T object) {
        if (mItems == null) {
            mItems = new ArrayList<>();
        }

        boolean flag = mItems.add(object);
        if (flag) {
            notifyItemInserted(size() - 1);
        }
        return flag;
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        if (collection == null) {
            return false;
        }

        if (mItems == null) {
            mItems = new ArrayList<T>();
        }

        boolean flag = mItems.addAll(location, collection);
        if (flag) {
            notifyItemRangeInserted(location, location + collection.size() - 1);
        }
        return flag;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        if (collection == null) {
            return false;
        }

        if (mItems == null) {
            mItems = new ArrayList<T>();
        }

        boolean flag = mItems.addAll(collection);
        if (flag) {
            int size = size();
            notifyItemRangeInserted(size - collection.size(), size);
        }
        return flag;
    }

    @Override
    public void clear() {
        if (mItems != null) {
            int size = size();
            if (size > 0) {
                size -= 1;
            }
            mItems.clear();
            notifyItemMoved(0, size);
        }
    }

    @Override
    public boolean contains(Object object) {
        return mItems == null ? false : mItems.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return mItems == null ? false : mItems.containsAll(collection);
    }

    @Override
    public T get(int location) {
        return mItems == null ? null : mItems.get(location);
    }

    @Override
    public int indexOf(Object object) {
        return mItems == null ? -1 : mItems.indexOf(object);
    }

    @Override
    public Iterator<T> iterator() {
        return mItems == null ? null : mItems.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return mItems == null ? -1 : mItems.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return mItems == null ? null : mItems.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int location) {
        return mItems == null ? null : mItems.listIterator(location);
    }

    @Override
    public T remove(int location) {
        if (mItems == null) {
            return null;
        }

        T t = mItems.remove(location);
        notifyDataSetChanged();
        return t;
    }

    public T removeLastItem() {
        if (mItems == null || mItems.isEmpty()) {
            return null;
        }

        T t = mItems.remove(size() - 1);
        notifyDataSetChanged();
        return t;
    }

    @Override
    public boolean remove(Object object) {
        if (mItems == null) {
            return false;
        }

        boolean flag = mItems.remove(object);
        notifyDataSetChanged();
        return flag;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            return false;
        }

        if (mItems == null) {
            return false;
        }

        boolean flag = mItems.removeAll(collection);
        notifyDataSetChanged();
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            return false;
        }

        if (mItems == null) {
            return false;
        }

        boolean flag = mItems.retainAll(collection);
        notifyDataSetChanged();
        return flag;
    }

    @Override
    public T set(int location, T object) {
        if (mItems == null) {
            return null;
        }

        T t = mItems.set(location, object);
        notifyItemChanged(location);
        return t;
    }

    @Override
    public int size() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public boolean isEmpty() {
        return mItems == null ? true : mItems.isEmpty();
    }

    @Override
    public List<T> subList(int start, int end) {
        return mItems == null ? null : mItems.subList(start, end);
    }

    @Override
    public Object[] toArray() {
        return mItems == null ? null : mItems.toArray();
    }

    @Override
    public <E> E[] toArray(E[] array) {
        return mItems == null ? null : mItems.toArray(array);
    }
}
