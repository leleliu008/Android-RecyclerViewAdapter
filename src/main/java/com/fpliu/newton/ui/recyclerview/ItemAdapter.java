package com.fpliu.newton.ui.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * RecyclerView的万能Adapter
 *
 * @author 792793182@qq.com 2017-07-18.
 */
public abstract class ItemAdapter<T, H extends ItemViewHolderAbs> extends RecyclerView.Adapter<H> implements List<T>, View.OnClickListener {

    //数据项集合
    private List<T> mItems;

    private OnItemClickListener<T, H> onItemClickListener;

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

    public void setOnItemClickListener(OnItemClickListener<T, H> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public abstract void onBindViewHolder(H holder, int position, T item);

    @Override
    public void onBindViewHolder(H holder, int position) {
        if (onItemClickListener != null) {
            View itemView = holder.getItemView();
            itemView.setTag(R.id.id_recycler_view_item_holder, holder);
            itemView.setTag(R.id.id_recycler_view_item_position, position);
            itemView.setOnClickListener(this);
        }
        onBindViewHolder(holder, position, getItem(position));
    }

    @Override
    public void onClick(View view) {
        Object positionObj = view.getTag(R.id.id_recycler_view_item_position);
        if (positionObj == null) {
            return;
        }
        H holder = (H) view.getTag(R.id.id_recycler_view_item_holder);
        int position = (int) positionObj;
        T item = getItem(position);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(holder, position, item);
        }
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
            notifyItemRangeInserted(size() - collection.size(), size());
        }
        return flag;
    }

    @Override
    public void clear() {
        if (mItems != null) {
            mItems.clear();
            notifyItemMoved(0, size() - 1);
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
        return mItems.isEmpty();
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
