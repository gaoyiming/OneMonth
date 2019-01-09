package com.mrgao.onemonth.base;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Created by mr.gao on 2017/5/4.
 */

public class EasyDiff<T> extends DiffUtil.Callback {
    private final List<T> olddatas;
    private final List<T> newDatas;

    public EasyDiff(List<T> olddatas, List<T> newDatas) {
        this.olddatas = olddatas;
        this.newDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return olddatas.size();
    }

    @Override
    public int getNewListSize() {
        return newDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return olddatas.get(oldItemPosition).equals(newDatas.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return olddatas.get(oldItemPosition).equals(newDatas.get(newItemPosition));
    }

//    @Nullable
//    @Override//这个方法用来局部刷新
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//
//
//
//        T s1 = newDatas.get(newItemPosition);
//        Bundle bundle = new Bundle();
//        bundle.p("key",T);
//        return bundle;
//    }
}
