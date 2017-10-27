package com.fpliu.newton.ui.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Constructor;

/**
 * 万能的RecyclerView.ViewHolder实现（抽象）
 *
 * @author 792793182@qq.com 2017-07-18.
 */
public abstract class ItemViewHolderAbs<SubClass> extends RecyclerView.ViewHolder {

    private static ImageLoader imageLoader;

    /**
     * 条目对应的视图
     */
    private View itemView;

    private SparseArray<View> widgetViews;

    private View view;

    public ItemViewHolderAbs(View itemView) {
        super(itemView);
        this.itemView = itemView;
        this.widgetViews = new SparseArray<>();
    }

    public static void setImageLoader(ImageLoader imageLoader) {
        ItemViewHolderAbs.imageLoader = imageLoader;
    }

    /**
     * 获取ItemViewHolderAbs子类的实例
     *
     * @param layoutId 布局的ID
     * @param parent   父容器
     * @param clazz    ItemViewHolderAbs子类
     * @param <T>
     * @return
     */
    public static <T extends ItemViewHolderAbs> T newInstance(int layoutId, ViewGroup parent, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(View.class);
            constructor.setAccessible(true);
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return constructor.newInstance(view);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定要操作的控件的ID
     *
     * @param viewId 要操作的控件的ID
     * @return 本类的实例
     */
    public SubClass id(int viewId) {
        view = findView(viewId);
        return self();
    }

    /**
     * 获得指定控件的直接父容器
     *
     * @return 本类的实例
     */
    public SubClass parent() {
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            view = (View) parent;
        }
        return self();
    }

    /**
     * 给TextView设置文本
     *
     * @param resId 字符串的ID
     * @return 本类的实例
     */
    public SubClass text(int resId) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setText(resId);
        }
        return self();
    }

    /**
     * 给TextView设置格式化文本
     *
     * @param resId      字符串的ID
     * @param formatArgs 格式化参数
     * @return 本类的实例
     * @see Context#getString(int, Object...)
     */
    public SubClass text(int resId, Object... formatArgs) {
        Context context = getContext();
        if (context != null) {
            CharSequence text = context.getString(resId, formatArgs);
            text(text);
        }
        return self();
    }

    /**
     * 给TextView设置文本
     *
     * @param text 文本
     * @return 本类的实例
     */
    public SubClass text(CharSequence text) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(text);
        }
        return self();
    }

    /**
     * 给TextView设置文本
     *
     * @param text        文本
     * @param goneIfEmpty 如果文本内容为空，将控件隐藏
     * @return 本类的实例
     */
    public SubClass text(CharSequence text, boolean goneIfEmpty) {
        if (goneIfEmpty && TextUtils.isEmpty(text)) {
            return gone();
        } else {
            return text(text);
        }
    }

    /**
     * 设置文本颜色
     *
     * @param color 文本的颜色
     * @return 本类的实例
     */
    public SubClass textColor(int color) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(color);
        }
        return self();
    }

    /**
     * 设置文本颜色
     *
     * @param colorId 文本的颜色的Id，使用R.color.xx
     * @return 本类的实例
     */
    public SubClass textColorWithId(int colorId) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(tv.getContext().getResources().getColor(colorId));
        }
        return self();
    }

    /**
     * 设置文本的字体
     *
     * @param typeface 字体
     * @return 本类的实例
     */
    public SubClass typeface(Typeface typeface) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTypeface(typeface);
        }
        return self();
    }

    /**
     * 设置文本的大小
     *
     * @param px 文本大小，单位：px
     * @return 本类的实例
     */
    public SubClass textSizePX(float px) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextSize(px);
        }
        return self();
    }

    /**
     * 设置文本的大小
     *
     * @param sp 文本大小，单位：sp
     * @return 本类的实例
     */
    public SubClass textSizeSP(float sp) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        }
        return self();
    }

    /**
     * 设置文本与图标的间距
     *
     * @param padding 文本与图标的间距，单位：px
     * @return 本类的实例
     */
    public SubClass compoundDrawablePadding(int padding) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setCompoundDrawablePadding(padding);
        }
        return self();
    }

    /**
     * 设置文本四周的图标
     *
     * @param left   左边的图标资源ID
     * @param top    上边的图标资源ID
     * @param right  右边的图标资源ID
     * @param bottom 下边的图标资源ID
     * @return 本类的实例
     */
    public SubClass compoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return self();
    }

    /**
     * 设置文本四周的图标
     *
     * @param left   左边的图标资源
     * @param top    上边的图标资源
     * @param right  右边的图标资源
     * @param bottom 下边的图标资源
     * @return 本类的实例
     */
    public SubClass compoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return self();
    }

    public SubClass scaleType(ImageView.ScaleType scaleType) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setScaleType(scaleType);
        }
        return self();
    }

    private void showInfo() {
        Log.i(getClass().getSimpleName(), "imageLoader is not set");
    }

    /**
     * 给ImageView设置图片资源
     *
     * @param resId 图片资源的ID
     * @return 本类的实例
     */
    public SubClass image(int resId) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            if (imageLoader == null) {
                showInfo();
                if (resId == 0) {
                    imageView.setImageBitmap(null);
                } else {
                    imageView.setImageResource(resId);
                }
            } else {
                imageLoader.image(imageView, resId);
            }
        }
        return self();
    }

    /**
     * 给ImageView设置图片资源
     *
     * @param resId 图片资源的ID
     * @return 本类的实例
     */
    public SubClass imageCircle(int resId) {
        if (view instanceof ImageView) {
            if (imageLoader == null) {
                showInfo();
            } else {
                imageLoader.imageCircle((ImageView) view, resId);
            }
        }
        return self();
    }

    /**
     * 给ImageView设置图片资源
     *
     * @param resId 图片资源的ID
     * @return 本类的实例
     */
    public SubClass imageRound(int resId, int radius) {
        if (view instanceof ImageView) {
            if (imageLoader == null) {
                showInfo();
            } else {
                imageLoader.imageRoundRect((ImageView) view, resId, radius);
            }
        }
        return self();
    }

    /**
     * 给ImageView设置图片
     *
     * @param bitmap 位图
     * @return 本类的实例
     */
    public SubClass image(Bitmap bitmap) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageBitmap(bitmap);
        }
        return self();
    }

    /**
     * 显示图片 - 原形
     *
     * @param resource   文件路径、uri、url都可以
     * @param defaultImg 默认图片
     */
    public SubClass image(String resource, int defaultImg) {
        if (view instanceof ImageView) {
            if (imageLoader == null) {
                showInfo();
            } else {
                imageLoader.image((ImageView) view, resource, defaultImg);
            }
        }
        return self();
    }

    /**
     * 显示图片 - 圆形
     *
     * @param resource   文件路径、uri、url都可以
     * @param defaultImg 默认图片
     */
    public SubClass imageCircle(String resource, int defaultImg) {
        if (view instanceof ImageView) {
            if (imageLoader == null) {
                showInfo();
            } else {
                imageLoader.imageCircle((ImageView) view, resource, defaultImg);
            }
        }
        return self();
    }

    /**
     * 显示图片 - 圆角矩形
     *
     * @param radius     弧度
     * @param resource   文件路径、uri、url都可以
     * @param defaultImg 默认图片
     */
    public SubClass imageRound(String resource, int defaultImg, int radius) {
        if (view instanceof ImageView) {
            if (imageLoader == null) {
                showInfo();
            } else {
                imageLoader.imageRoundRect((ImageView) view, resource, defaultImg, radius);
            }
        }
        return self();
    }

    /**
     * 给RatingBar设置rating值
     *
     * @param rating 具体值
     * @return 本类的实例
     */
    public SubClass rating(float rating) {
        if (view instanceof RatingBar) {
            RatingBar ratingBar = (RatingBar) view;
            ratingBar.setRating(rating);
        }
        return self();
    }

    /**
     * Set the adapter of an AdapterView.
     *
     * @param adapter adapter
     * @return self
     */
    public SubClass adapter(Adapter adapter) {
        if (view instanceof AdapterView) {
            AdapterView adapterView = (AdapterView) view;
            adapterView.setAdapter(adapter);
        }
        return self();
    }

    /**
     * Set the adapter of an ExpandableListView.
     *
     * @param adapter adapter
     * @return self
     */
    public SubClass adapter(ExpandableListAdapter adapter) {
        if (view instanceof ExpandableListView) {
            ExpandableListView expandableListView = (ExpandableListView) view;
            expandableListView.setAdapter(adapter);
        }
        return self();
    }

    /**
     * 给View设置一个标记
     *
     * @param tag 标记
     * @return 本类的实例
     */
    public SubClass tag(Object tag) {
        if (view != null) {
            view.setTag(tag);
        }
        return self();
    }

    /**
     * 给View设置一个标记，key是当前View的id
     *
     * @return 本类的实例
     */
    public SubClass tagWithCurrentId(Object tag) {
        if (view != null) {
            view.setTag(view.getId(), tag);
        }
        return self();
    }

    /**
     * 给View设置一个标记
     *
     * @param key 标记的唯一标志
     * @param tag 标记
     * @return 本类的实例
     */
    public SubClass tag(int key, Object tag) {
        if (view != null) {
            view.setTag(key, tag);
        }
        return self();
    }

    /**
     * 获得tag
     */
    public <T> T tag() {
        if (view == null) {
            return null;
        }
        return (T) view.getTag();
    }

    /**
     * 获得指定key的tag
     */
    public <T> T getTag(int key) {
        if (view == null) {
            return null;
        }
        return (T) view.getTag();
    }

    /**
     * 设置是否可操作
     *
     * @param enabled 是否可操作
     * @return 本类的实例
     */
    public SubClass enabled(boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
        }
        return self();
    }

    /**
     * 设置是否被选中
     *
     * @param selected 是否被选中
     * @return 本类的实例
     */
    public SubClass selected(boolean selected) {
        view.setSelected(selected);
        return self();
    }

    /**
     * 设置是否可选择
     *
     * @param checked 是否可选择
     * @return 本类的实例
     */
    public SubClass checked(boolean checked) {
        if (view instanceof Checkable) {
            Checkable checkable = (Checkable) view;
            checkable.setChecked(checked);
        }
        return self();
    }

    /**
     * 是否是选中状态
     */
    public boolean isChecked() {
        if (view instanceof Checkable) {
            Checkable checkable = (Checkable) view;
            return checkable.isChecked();
        }
        return false;
    }

    /**
     * 设置是否可点击
     *
     * @param clickable 是否可点击
     * @return 本类的实例
     */
    public SubClass clickable(boolean clickable) {
        if (view != null) {
            view.setClickable(clickable);
        }
        return self();
    }

    /**
     * 设置当前控件控件的可见性
     *
     * @return 本类的实例
     */
    public SubClass visibility(int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
        return self();
    }

    /**
     * 隐藏当前控件
     *
     * @return 本类的实例
     */
    public SubClass gone() {
        return visibility(View.GONE);
    }

    /**
     * 使当前控件不可见
     *
     * @return 本类的实例
     */
    public SubClass invisible() {
        return visibility(View.INVISIBLE);
    }

    /**
     * 显示当前控件
     *
     * @return 本类的实例
     */
    public SubClass visible() {
        return visibility(View.VISIBLE);
    }

    /**
     * 设置控件的背景
     *
     * @param resId 图片、颜色、drawable等的ID
     * @return 本类的实例
     */
    public SubClass background(int resId) {
        if (view != null) {
            if (resId == 0) {
                view.setBackgroundDrawable(null);
            } else {
                view.setBackgroundResource(resId);
            }
        }
        return self();
    }

    /**
     * 设置控件的背景
     *
     * @param bg 背景
     * @return 本类的实例
     */
    public SubClass background(Drawable bg) {
        if (view != null) {
            view.setBackgroundDrawable(bg);
        }
        return self();
    }

    /**
     * 设置控件的背景颜色
     *
     * @param color 颜色
     * @return 本类的实例
     */
    public SubClass backgroundColor(int color) {
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return self();
    }

    /**
     * 获得TextView的文本
     *
     * @return TextView的文本
     */
    public CharSequence getText() {
        if (view instanceof TextView) {
            return ((TextView) view).getText();
        }
        return "";
    }

    /**
     * Gets the selected item if current view is an adapter view.
     *
     * @return selected
     */
    public Object getSelectedItem() {
        if (view instanceof AdapterView<?>) {
            return ((AdapterView<?>) view).getSelectedItem();
        }
        return null;
    }


    /**
     * Gets the selected item position if current view is an adapter view.
     * <p>
     * Returns AdapterView.INVALID_POSITION if not valid.
     *
     * @return selected position
     */
    public int getSelectedItemPosition() {
        int result = AdapterView.INVALID_POSITION;
        if (view instanceof AdapterView<?>) {
            result = ((AdapterView<?>) view).getSelectedItemPosition();
        }
        return result;
    }

    public SubClass checkedChange(CompoundButton.OnCheckedChangeListener listener) {
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(listener);
        }
        return self();
    }

    /**
     * 注册点击事件
     *
     * @param listener 点击事件监听器
     * @return 本类的实例
     */
    public SubClass clicked(View.OnClickListener listener) {
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return self();
    }

    /**
     * 注册长按事件
     *
     * @param listener 长按事件监听器
     * @return 本类的实例
     */
    public SubClass longClicked(View.OnLongClickListener listener) {
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return self();
    }

    /**
     * 注册AdapterView的条目点击事件
     *
     * @param listener 条目点击事件监听器
     * @return 本类的实例
     */
    public SubClass itemClicked(AdapterView.OnItemClickListener listener) {
        if (view instanceof AdapterView) {
            AdapterView<?> adapterView = (AdapterView<?>) view;
            adapterView.setOnItemClickListener(listener);
        }
        return self();
    }

    /**
     * 注册AdapterView的条目选中事件
     *
     * @param listener 条目选中事件监听器
     * @return 本类的实例
     */
    public SubClass itemSelected(AdapterView.OnItemSelectedListener listener) {
        if (view instanceof AdapterView) {
            AdapterView<?> adapterView = (AdapterView<?>) view;
            adapterView.setOnItemSelectedListener(listener);
        }
        return self();
    }

    /**
     * Set selected item of an AdapterView.
     *
     * @param position The position of the item to be selected.
     * @return self
     */
    public SubClass selection(int position) {
        if (view instanceof AdapterView) {
            AdapterView<?> adapterView = (AdapterView<?>) view;
            adapterView.setSelection(position);
        }
        return self();
    }

    /**
     * Clear a view. Applies to ImageView, WebView, and TextView.
     *
     * @return self
     */
    public SubClass clear() {
        if (view != null) {
            if (view instanceof ImageView) {
                ImageView iv = ((ImageView) view);
                iv.setImageBitmap(null);
            } else if (view instanceof WebView) {
                WebView wv = ((WebView) view);
                wv.stopLoading();
                wv.clearView();
            } else if (view instanceof TextView) {
                TextView tv = ((TextView) view);
                tv.setText("");
            }
        }
        return self();
    }


    /**
     * 设置控件的外边距，所有的单位是dip
     *
     * @param leftDip   左外边距
     * @param topDip    上外边距
     * @param rightDip  右外边距
     * @param bottomDip 下外边距
     * @return 本类的实例
     */
    public SubClass marginDIP(float leftDip, float topDip, float rightDip, float bottomDip) {
        if (view != null) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                Context context = getContext();
                int left = dip2px(context, leftDip);
                int top = dip2px(context, topDip);
                int right = dip2px(context, rightDip);
                int bottom = dip2px(context, bottomDip);
                ((ViewGroup.MarginLayoutParams) lp).setMargins(left, top, right, bottom);
                view.setLayoutParams(lp);
            }
        }
        return self();
    }

    /**
     * 设置控件的外边距
     *
     * @param margin 4个边的外边距一样大，单位：dip
     * @return 本类的实例
     */
    public SubClass marginDIP(float margin) {
        return marginDIP(margin, margin, margin, margin);
    }

    /**
     * 设置控件的外边距，所有的单位是px
     *
     * @param leftPX   左外边距
     * @param topPX    上外边距
     * @param rightPX  右外边距
     * @param bottomPX 下外边距
     * @return 本类的实例
     */
    public SubClass marginPX(int leftPX, int topPX, int rightPX, int bottomPX) {
        if (view != null) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) lp).setMargins(leftPX, topPX, rightPX, bottomPX);
                view.setLayoutParams(lp);
            }
        }
        return self();
    }

    /**
     * 设置控件的外边距
     *
     * @param margin 4个边的外边距一样大，单位：px
     * @return 本类的实例
     */
    public SubClass marginPX(int margin) {
        return marginPX(margin, margin, margin, margin);
    }

    /**
     * 设置控件的内边距，所有的单位是dip
     *
     * @param leftDip   左内边距
     * @param topDip    上内边距
     * @param rightDip  右内边距
     * @param bottomDip 下内边距
     * @return 本类的实例
     */
    public SubClass paddingDIP(float leftDip, float topDip, float rightDip, float bottomDip) {
        if (view != null) {
            Context context = getContext();
            int left = dip2px(context, leftDip);
            int top = dip2px(context, topDip);
            int right = dip2px(context, rightDip);
            int bottom = dip2px(context, bottomDip);
            view.setPadding(left, top, right, bottom);
        }
        return self();
    }

    /**
     * 设置控件的内边距
     *
     * @param padding 4个边的内边距一样大，单位：dip
     * @return 本类的实例
     */
    public SubClass paddingDIP(float padding) {
        return paddingDIP(padding, padding, padding, padding);
    }

    /**
     * 设置控件的内边距，所有的单位是px
     *
     * @param leftPX   左内边距
     * @param topPX    上内边距
     * @param rightPX  右内边距
     * @param bottomPX 下内边距
     * @return 本类的实例
     */
    public SubClass paddingPX(int leftPX, int topPX, int rightPX, int bottomPX) {
        if (view != null) {
            view.setPadding(leftPX, topPX, rightPX, bottomPX);
        }
        return self();
    }

    /**
     * 设置控件的内边距
     *
     * @param padding 4个边的内边距一样大，单位：px
     * @return 本类的实例
     */
    public SubClass paddingPX(int padding) {
        return paddingPX(padding, padding, padding, padding);
    }

    /**
     * 设置控件的宽度
     *
     * @param dip 宽度，可以是具体数值，单位：dip，也可以是ViewGroup.LayoutParams.FILL_PARENT、ViewGroup.LayoutParams.WRAP_CONTENT、ViewGroup.LayoutParams.MATCH_PARENT
     * @return 本类的实例
     */
    public SubClass widthDIP(int dip) {
        size(true, dip, true);
        return self();
    }

    /**
     * 设置控件的高度
     *
     * @param dip 高度，可以是具体数值，单位：dip，也可以是ViewGroup.LayoutParams.FILL_PARENT、ViewGroup.LayoutParams.WRAP_CONTENT、ViewGroup.LayoutParams.MATCH_PARENT
     * @return 本类的实例
     */
    public SubClass heightDIP(int dip) {
        size(false, dip, true);
        return self();
    }

    /**
     * 设置控件的宽度
     *
     * @param px 宽度，可以是具体数值，单位：px，也可以是ViewGroup.LayoutParams.FILL_PARENT、ViewGroup.LayoutParams.WRAP_CONTENT、ViewGroup.LayoutParams.MATCH_PARENT
     * @return 本类的实例
     */
    public SubClass widthPX(int px) {
        size(true, px, false);
        return self();
    }

    /**
     * 设置控件的高度
     *
     * @param px 高度，可以是具体数值，单位：px，也可以是ViewGroup.LayoutParams.FILL_PARENT、ViewGroup.LayoutParams.WRAP_CONTENT、ViewGroup.LayoutParams.MATCH_PARENT
     * @return 本类的实例
     */
    public SubClass heightPX(int px) {
        size(false, px, false);
        return self();
    }

    /**
     * 设置控件的宽度
     *
     * @param width 高度，可以是具体数值，单位：dip或者px，也可以是ViewGroup.LayoutParams.FILL_PARENT、ViewGroup.LayoutParams.WRAP_CONTENT、ViewGroup.LayoutParams.MATCH_PARENT
     * @param isDIP 单位是否是dip，否则是px
     * @return 本类的实例
     */
    public SubClass width(int width, boolean isDIP) {
        size(true, width, isDIP);
        return self();
    }

    /**
     * 设置控件的高度
     *
     * @param height 高度，可以是具体数值，单位：dip或者px，也可以是ViewGroup.LayoutParams.FILL_PARENT、ViewGroup.LayoutParams.WRAP_CONTENT、ViewGroup.LayoutParams.MATCH_PARENT
     * @param isDIP  单位是否是dip，否则是px
     * @return 本类的实例
     */
    public SubClass height(int height, boolean isDIP) {
        size(false, height, isDIP);
        return self();
    }

    private void size(boolean width, int size, boolean dip) {
        if (view != null) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (size > 0 && dip) {
                size = dip2px(getContext(), size);
            }
            if (width) {
                lp.width = size;
            } else {
                lp.height = size;
            }
            view.setLayoutParams(lp);
        }
    }

    /**
     * 开始动画
     *
     * @param animationId 动画资源的ID
     * @param listener    动画事件的回掉
     * @return 本类的实例
     */
    public SubClass animate(int animationId, Animation.AnimationListener listener) {
        Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), animationId);
        animation.setAnimationListener(listener);
        return animate(animation);
    }

    /**
     * 开始动画
     *
     * @param animationId 动画资源的ID
     * @return 本类的实例
     */
    public SubClass animate(int animationId) {
        return animate(animationId, null);
    }

    /**
     * 开始动画
     *
     * @param animation 动画
     * @return 本类的实例
     */
    public SubClass animate(Animation animation) {
        if (view != null && animation != null) {
            view.startAnimation(animation);
        }
        return self();
    }

    /**
     * 触发点击事件
     *
     * @return 本类的实例
     */
    public SubClass click() {
        if (view != null) {
            view.performClick();
        }
        return self();
    }

    /**
     * 触发长按事件
     *
     * @return 本类的实例
     */
    public SubClass longClick() {
        if (view != null) {
            view.performLongClick();
        }
        return self();
    }

    public View inflate(int layoutId, ViewGroup parent, boolean attachToParent) {
        return LayoutInflater.from(getContext()).inflate(layoutId, parent, attachToParent);
    }

    /**
     * 展开或者收起ExpandableListView的指定项
     *
     * @param position 指定项
     * @param expand   展开或者收起
     * @return 本类的实例
     */
    public SubClass expand(int position, boolean expand) {
        if (view instanceof ExpandableListView) {
            ExpandableListView expandableListView = (ExpandableListView) view;
            if (expand) {
                expandableListView.expandGroup(position);
            } else {
                expandableListView.collapseGroup(position);
            }
        }
        return self();
    }

    /**
     * 展开或者收起ExpandableListView的所有项
     *
     * @param expand 展开或者收起
     * @return 本类的实例
     */
    public SubClass expandAll(boolean expand) {
        if (view instanceof ExpandableListView) {
            ExpandableListView expandableListView = (ExpandableListView) view;
            ExpandableListAdapter expandableListAdapter = expandableListView.getExpandableListAdapter();
            if (expandableListAdapter != null) {
                int count = expandableListAdapter.getGroupCount();
                for (int i = 0; i < count; i++) {
                    if (expand) {
                        expandableListView.expandGroup(i);
                    } else {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        }

        return self();
    }

    public <T extends View> T getItemView() {
        return (T) itemView;
    }

    public <T extends View> T getView() {
        return (T) view;
    }

    /**
     * Gets the current view as an image view.
     *
     * @return ImageView
     */
    public ImageView getImageView() {
        return (ImageView) view;
    }

    /**
     * Gets the current view as an Gallery.
     *
     * @return Gallery
     */
    public Gallery getGallery() {
        return (Gallery) view;
    }


    /**
     * Gets the current view as a text view.
     *
     * @return TextView
     */
    public TextView getTextView() {
        return (TextView) view;
    }

    /**
     * Gets the current view as an edit text.
     *
     * @return EditText
     */
    public EditText getEditText() {
        return (EditText) view;
    }

    /**
     * Gets the current view as an progress bar.
     *
     * @return ProgressBar
     */
    public ProgressBar getProgressBar() {
        return (ProgressBar) view;
    }

    /**
     * Gets the current view as seek bar.
     *
     * @return SeekBar
     */

    public SeekBar getSeekBar() {
        return (SeekBar) view;
    }

    /**
     * Gets the current view as a button.
     *
     * @return Button
     */
    public Button getButton() {
        return (Button) view;
    }

    /**
     * Gets the current view as a checkbox.
     *
     * @return CheckBox
     */
    public CheckBox getCheckBox() {
        return (CheckBox) view;
    }

    /**
     * Gets the current view as a listview.
     *
     * @return ListView
     */
    public ListView getListView() {
        return (ListView) view;
    }

    /**
     * Gets the current view as a ExpandableListView.
     *
     * @return ExpandableListView
     */
    public ExpandableListView getExpandableListView() {
        return (ExpandableListView) view;
    }

    /**
     * Gets the current view as a gridview.
     *
     * @return GridView
     */
    public GridView getGridView() {
        return (GridView) view;
    }

    /**
     * Gets the current view as a RatingBar.
     *
     * @return RatingBar
     */
    public RatingBar getRatingBar() {
        return (RatingBar) view;
    }

    /**
     * Gets the current view as a webview.
     *
     * @return WebView
     */
    public WebView getWebView() {
        return (WebView) view;
    }

    /**
     * Gets the current view as a spinner.
     *
     * @return Spinner
     */
    public Spinner getSpinner() {
        return (Spinner) view;
    }

    /**
     * Gets the editable.
     *
     * @return the editable
     */
    public Editable getEditable() {
        if (view instanceof EditText) {
            return ((EditText) view).getEditableText();
        }
        return null;
    }

    private SubClass self() {
        return (SubClass) this;
    }

    private Context getContext() {
        return itemView.getContext();
    }

    private <T extends View> T findView(int viewId) {
        View widgetView = widgetViews.get(viewId);
        if (widgetView == null) {
            widgetView = itemView.findViewById(viewId);
            widgetViews.put(viewId, widgetView);
        }
        return (T) widgetView;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    private static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    public interface ImageLoader {
        /**
         * 显示图片 - 原形
         *
         * @param resource   文件路径、uri、url都可以
         * @param imageView  显示的控件
         * @param defaultImg 默认图片
         */
        void image(ImageView imageView, String resource, int defaultImg);

        /**
         * 显示图片 - 原形
         *
         * @param resId     drawable资源ID
         * @param imageView 显示的控件
         */
        void image(ImageView imageView, int resId);

        /**
         * 显示图片 - 圆形
         *
         * @param resource   文件路径、uri、url都可以
         * @param imageView  显示的控件
         * @param defaultImg 默认图片
         */
        void imageCircle(ImageView imageView, String resource, int defaultImg);

        /**
         * 显示图片 - 圆形
         *
         * @param resId     图片资源ID
         * @param imageView 显示的控件
         */
        void imageCircle(ImageView imageView, int resId);

        /**
         * 显示图片 - 圆角矩形
         *
         * @param resource   文件路径、uri、url都可以
         * @param imageView  显示的控件
         * @param defaultImg 默认图片
         * @param radius     弧度
         */
        void imageRoundRect(ImageView imageView, String resource, int defaultImg, int radius);

        /**
         * 显示图片 - 圆角矩形
         *
         * @param resId     图片资源ID
         * @param imageView 显示的控件
         * @param radius    弧度
         */
        void imageRoundRect(ImageView imageView, int resId, int radius);
    }
}
