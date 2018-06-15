package com.mrgao.onemonth.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 文 件 名: TimeConstants
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/13 20:05
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public final class TimeConstants {

    public static final int MSEC = 1;
    public static final int SEC  = 1000;
    public static final int MIN  = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY  = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
