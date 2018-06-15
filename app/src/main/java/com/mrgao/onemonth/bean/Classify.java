package com.mrgao.onemonth.bean;

import com.moxun.tagcloudlib.view.TagsAdapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 文 件 名: Classify
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/11 20:47
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
@Entity
public class Classify {
    private String classify;

    @Generated(hash = 1827475476)
    public Classify(String classify) {
        this.classify = classify;
    }

    @Generated(hash = 767880343)
    public Classify() {
    }

    public String getClassify() {
        return this.classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
}
