package com.mrgao.onemonth.base;




/**
 * Created by mr.gao on 2017/3/29.
 */

public abstract class BasePresenter<M, T> {
    protected M mModel;
    protected T mView;
 //   private final RxManager mRxManager = new RxManager();

    public void attachVM(T v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
     //   mRxManager.clear();
        mView = null;
        mModel = null;
    }

    protected abstract void onStart();
}
