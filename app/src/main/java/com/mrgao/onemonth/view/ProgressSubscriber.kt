package com.mrgao.onemonth.view

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import com.mrgao.onemonth.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import java.net.ConnectException
import java.net.SocketTimeoutException


/**
 * @ClassName: ProgressSubscriber
 * @Description: ${TODO}
 * @author: Mr_gao
 * @date: 2016/7/21 22:15
 */
abstract class ProgressSubscriber<T> : Observer<T> {
    //    回调接口
    //  private HttpOnNextListener mSubscriberOnNextListener;
    //    弱引用反正内存泄露
    private val mActivity: WeakReference<Context>
    //    是否能取消请求
    private val cancel: Boolean
    //    加载框可自己定义
    private var pd: ProgressDialog? = null


    constructor(context: Context) {

        this.mActivity = WeakReference(context)
        this.cancel = false
        initProgressDialog()
    }

    constructor(context: Context, cancel: Boolean) {

        this.mActivity = WeakReference(context)
        this.cancel = cancel
        initProgressDialog()
    }


    /**
     * 初始化加载框
     */
    private fun initProgressDialog() {
        val context = mActivity.get()
        if (pd == null && context != null) {
            pd = CustomDialog(context, R.style.CustomDialog)
            pd!!.setCancelable(cancel)
            if (cancel) {

                pd!!.setOnCancelListener { dialogInterface -> onCancelProgress() }
            }
        }
    }


    /**
     * 显示加载框
     */
    private fun showProgressDialog() {
        val context = mActivity.get()
        if (pd == null || context == null) return
        if (!pd!!.isShowing) {
            pd!!.show()
        }
    }


    /**
     * 隐藏
     */
    private fun dismissProgressDialog() {
        if (pd != null && pd!!.isShowing) {
            pd!!.dismiss()
        }
    }


    /**
     * 完成，隐藏ProgressDialog
     */


    override fun onComplete() {
        dismissProgressDialog()
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    override fun onError(e: Throwable) {
        val context = mActivity.get() ?: return
        if (e is SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show()
        } else if (e is ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "错误" + e.message, Toast.LENGTH_SHORT).show()
            // Log.i("tag", "error----------->" + e.toString());
        }
        dismissProgressDialog()
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    private fun onCancelProgress() {
        //        if (!this.isUnsubscribed()) {
        //            this.unsubscribe();
        //        }
    }

    override fun onSubscribe(d: Disposable) {
        if (d.isDisposed) {
            d.dispose()
        }
        //showProgressDialog();
    }
}

