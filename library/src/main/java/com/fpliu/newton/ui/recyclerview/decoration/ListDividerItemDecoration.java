package com.fpliu.newton.ui.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ListDividerItemDecoration extends RecyclerView.ItemDecoration {

    private ColorDrawable colorDrawable;
    private int height;
    private int paddingLeft;
    private int paddingRight;
    private boolean drawLastItem = true;
    private boolean drawBorderLeft = false;
    private boolean drawBorderRight = false;
    private boolean drawBorderTop = false;
    private boolean drawBorderBottom = false;

    public ListDividerItemDecoration color(int color) {
        this.colorDrawable = new ColorDrawable(color);
        return this;
    }

    public ListDividerItemDecoration height(int height) {
        this.height = height;
        return this;
    }

    public ListDividerItemDecoration paddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        return this;
    }

    public ListDividerItemDecoration paddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        return this;
    }

    public ListDividerItemDecoration padding(int padding) {
        this.paddingLeft = padding;
        this.paddingRight = padding;
        return this;
    }

    public ListDividerItemDecoration drawLastItem(boolean drawLastItem) {
        this.drawLastItem = drawLastItem;
        return this;
    }

    public ListDividerItemDecoration drawBorder(boolean drawBorder) {
        this.drawBorderLeft = drawBorder;
        this.drawBorderRight = drawBorder;
        this.drawBorderTop = drawBorder;
        this.drawBorderBottom = drawBorder;
        return this;
    }

    public ListDividerItemDecoration drawBorderLeft(boolean drawBorderLeft) {
        this.drawBorderLeft = drawBorderLeft;
        return this;
    }

    public ListDividerItemDecoration drawBorderRight(boolean drawBorderRight) {
        this.drawBorderRight = drawBorderRight;
        return this;
    }

    public ListDividerItemDecoration drawBorderTop(boolean drawBorderTop) {
        this.drawBorderTop = drawBorderTop;
        return this;
    }

    public ListDividerItemDecoration drawBorderBottom(boolean drawBorderBottom) {
        this.drawBorderBottom = drawBorderBottom;
        return this;
    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildAdapterPosition(view);
//        int orientation = 0;
//        int headerCount = 0;
//        int footerCount = 0;
////        if (parent.getAdapter() instanceof RecyclerArrayAdapter){
////            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
////            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
////        }
//
//        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//        if (layoutManager instanceof StaggeredGridLayoutManager) {
//            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//        } else if (layoutManager instanceof GridLayoutManager) {
//            orientation = ((GridLayoutManager) layoutManager).getOrientation();
//        } else if (layoutManager instanceof LinearLayoutManager) {
//            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
//        }
//
//        if (position >= headerCount && position < parent.getAdapter().getItemCount() - footerCount) {
//            if (orientation == OrientationHelper.VERTICAL) {
//                outRect.bottom = height;
//            } else {
//                outRect.right = height;
//            }
//        }
//    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }

        int orientation = 0;
        int headerCount = 0;
        int dataCount;

        dataCount = parent.getAdapter().getItemCount();

        int dataStartPosition = headerCount;
        int dataEndPosition = headerCount + dataCount;

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        int start, end;
        if (orientation == OrientationHelper.VERTICAL) {
            start = parent.getPaddingLeft() + paddingLeft;
            end = parent.getWidth() - parent.getPaddingRight() - paddingRight;
        } else {
            start = parent.getPaddingTop() + paddingLeft;
            end = parent.getHeight() - parent.getPaddingBottom() - paddingRight;
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (position >= dataStartPosition && position < dataEndPosition - 1) {
                if (orientation == OrientationHelper.VERTICAL) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + height;
                    colorDrawable.setBounds(start, top, end, bottom);
                    colorDrawable.draw(c);
                } else {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin;
                    int right = left + height;
                    colorDrawable.setBounds(left, start, right, end);
                    colorDrawable.draw(c);
                }
            } else if (position == dataEndPosition - 1) {
                if (drawLastItem) {
                    if (orientation == OrientationHelper.VERTICAL) {
                        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                        int top = child.getBottom() + params.bottomMargin;
                        int bottom = top + height;
                        colorDrawable.setBounds(0, top, c.getWidth(), bottom);
                        colorDrawable.draw(c);
                    } else {
                        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                        int left = child.getRight() + params.rightMargin;
                        int right = left + height;
                        colorDrawable.setBounds(left, start, right, end);
                        colorDrawable.draw(c);
                    }
                }
            }
        }

        if (drawBorderTop) {
            colorDrawable.setBounds(0, 0, c.getWidth(), height);
            colorDrawable.draw(c);
        }
        if (drawBorderBottom) {
            colorDrawable.setBounds(0, c.getHeight() - height, c.getWidth(), c.getHeight());
            colorDrawable.draw(c);
        }
        if (drawBorderLeft) {
            colorDrawable.setBounds(0, 0, height, c.getHeight());
            colorDrawable.draw(c);
        }
        if (drawBorderRight) {
            colorDrawable.setBounds(c.getWidth() - height, 0, c.getWidth(), c.getHeight());
            colorDrawable.draw(c);
        }
    }
}