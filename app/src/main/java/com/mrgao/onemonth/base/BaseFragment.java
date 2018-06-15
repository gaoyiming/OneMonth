package com.mrgao.onemonth.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文 件 名: BaseFragment
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/9 11:07
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public abstract class BaseFragment extends Fragment {

    private String TAG;
    private OnBackToFirstListener _mBackToFirstListener;

    //    protected T mPresenter;
//    private E mModel;
    private Context mContext;
    private Activity mActivity;
    private View view;


//    @Override
//    public void onAttach(Context context) {
//        mActivity = (Activity) context;
//        mContext = context;
//        super.onAttach(context);
//        if (context instanceof OnBackToFirstListener) {
//            _mBackToFirstListener = (OnBackToFirstListener) context;
//        }
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getLayoutId(), container, false);


    }

    protected abstract int getLayoutId();

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        //设置状态栏透明
//        // setStatusBarColor();
////        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//////        TAG = getClass().getSimpleName();
//////        mPresenter = TUtil.getT(this, 0);
//////        mModel = TUtil.getT(this, 1);
////
////        // if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
////        getBundle(getArguments());
//
//        super.onViewCreated(view, savedInstanceState);
//        initdate();
//        initUI(view, savedInstanceState);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //if (binder != null) binder.unbind();
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        _mBackToFirstListener = null;
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // if (mPresenter != null) mPresenter.detachVM();
    }

//    @Override
//    protected FragmentAnimator onCreateFragmentAnimator() {
//        FragmentAnimator fragmentAnimator = _mActivity.getFragmentAnimator();
//        fragmentAnimator.setEnter(0);
//        fragmentAnimator.setExit(0);
//        return fragmentAnimator;
//    }


    /**
     * 得到Activity传进来的值
     */
    private void getBundle(Bundle bundle) {

    }

    /**
     * 初始化控件
     */
    protected abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /**
     * 在监听器之前把数据准备好
     */
    protected void initdate() {

    }

//    public void setStatusBarColor() {
//        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
//    }
//
//    protected void setToolBar(Toolbar toolbar, String title) {
//        toolbar.setTitle(title);
//        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
//        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
//    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
//    protected TitleBuilder initTitleBar(String title) {
//        return new TitleBuilder(mActivity)
//                .setTitleText(title)
//                .setLeftImage(R.mipmap.ic_back)
//                .setLeftOnClickListener(v -> {
//                    _mActivity.onBackPressed();
//                });
//    }

    /**
     * 处理回退事件
     * 如果是孩子fragment需要重写onBackPressedSupport(){_mBackToFirstListener.onBackToFirstFragment();return true;}
     *
     * @return
     */
//    @Override
//    public boolean onBackPressedSupport() {
//        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
//            popChild();
//        } else {
//            _mBackToFirstListener.onBackToFirstFragment();
//            _mActivity.finish();
//        }
//        return true;
//    }

    private interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }

    public void showToast(String msg) {
    }

    public void jumpTo(Class activity, Bundle bundle) {
        Intent intent = new Intent(getContext(), activity);
        intent.putExtra("bundle", bundle);
        startActivity(intent);

//       getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
}
