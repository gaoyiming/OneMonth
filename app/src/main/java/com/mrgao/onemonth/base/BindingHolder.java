package com.mrgao.onemonth.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mr.gao on 2017/5/4.
 */

public class BindingHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final T mBingding;

    public BindingHolder(T binding) {
        super(binding.getRoot());
        mBingding = binding;
    }

    public T getBinding() {
        return mBingding;
    }

}
