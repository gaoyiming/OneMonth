package com.mrgao.onemonth.rx

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

/**
 * 文 件 名: Rxbus
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/16 13:59
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
class RxBus private constructor() {

    private val mBus: FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()

    private object Holder {
        val instance = RxBus()
    }

    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    fun <T> register(clz: Class<T>): Flowable<T> {
        return mBus.ofType(clz)
    }

    fun register(): Flowable<Any> {
        return mBus
    }

    fun unregisterAll() {
        //会将所有由mBus生成的Flowable都置completed状态后续的所有消息都收不到了
        mBus.onComplete()
    }

    fun hasSubscribers(): Boolean {
        return mBus.hasSubscribers()
    }

    companion object {

        val instance: RxBus
            get() = Holder.instance
    }
}

