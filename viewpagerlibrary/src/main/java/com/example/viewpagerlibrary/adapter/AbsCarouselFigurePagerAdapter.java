package com.example.viewpagerlibrary.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyaodong on 2018/3/22.
 */

public abstract class AbsCarouselFigurePagerAdapter<T> extends PagerAdapter {

    protected List<T> mData = new ArrayList<>();
    protected List<T> mRealList = new ArrayList<>();
    protected int mRealPosition;
    protected Context mContext;
    protected ItemClickListener<T> mItemClickListener;

    public AbsCarouselFigurePagerAdapter(Context context){
        mContext = context;
    }

    public void bindData(List<T> list) {
        mRealList.clear();
        mRealList.addAll(list);
        mData = generateLunBoList(list);
        notifyDataSetChanged();
    }

    public interface ItemClickListener<T> {
        void onClick(int position, T content);
    }
    public void setOnItemClickListener(ItemClickListener<T> listener){
        mItemClickListener = listener;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SparseArray<View> sparseArray = new SparseArray<>();
        if (sparseArray.get(position) == null) {
            View childView = createChildViewItem();
            container.addView(childView);
            addChildData(childView, position);
            sparseArray.put(position, childView);
            return childView;
        } else {
            View childView = sparseArray.get(position);
            container.addView(childView);
            return childView;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public List<T> getData(){
        return mData;
    }

    protected abstract View createChildViewItem();

    protected abstract void addChildData(View childView, int position);

    public List<T> generateLunBoList(List<T> list) {
        mData.clear();
        if (list != null && list.size() <= 1) {
            mData.addAll(list);
        } else if (list != null) {
            mData.addAll(list);
            mData.add(list.get(0));
            mData.add(list.get(1));
            mData.add(0, list.get(getRealTotalNum() - 1));
            mData.add(0, list.get(getRealTotalNum() - 2));
        }
        return mData;
    }

    public int getRealTotalNum() {
        return mRealList.size();
    }

    public int getRealPosition(int originPosition) {
        if (getCount() == 1) {
            mRealPosition = originPosition;
            return mRealPosition;
        }
        if (originPosition == 0) originPosition = getCount() - 4;
        else if (originPosition == 1) originPosition = getCount() - 3;
        else if (originPosition == getCount() - 2) originPosition = 2;
        else if (originPosition == getCount() - 1) originPosition = 3;
        return mRealPosition = originPosition - 2 ;
    }


}
