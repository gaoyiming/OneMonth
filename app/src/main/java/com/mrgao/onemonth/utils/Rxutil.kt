package com.mrgao.onemonth.utils

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 文 件 名: Rxutil
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/25 15:52
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
object RxUtil {
    /**
     * 统一线程处理
     * @param <T>
     * @return
    </T> */

    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }





}