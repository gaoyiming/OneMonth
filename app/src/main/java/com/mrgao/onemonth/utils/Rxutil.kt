package com.mrgao.onemonth.utils

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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

    fun <Any> rxSchedulerHelper(): ObservableTransformer<Any, Any> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun countdown(time: Int, dealy: Int): Observable<Long>? {
        var time = time
        if (time < 0) time = 0

        val countTime = time
        return Observable.interval(0, dealy.toLong(), TimeUnit.SECONDS)
                .compose(RxUtil.rxSchedulerHelper())
                .map<Long> { increaseTime -> countTime - increaseTime }
                .take((countTime + 1).toLong())

    }



}