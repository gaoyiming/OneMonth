package com.mrgao.onemonth.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 文 件 名:  JudgeByDay
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/28 15:16
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
@Entity
public class JudgeByDay {
    @Id(autoincrement = true)
    private Long id;
    private String classify;
    private boolean isFinish;
    private String finishRate;
    private int finishNum;
    private int totalNum;
    private String data;
    @Generated(hash = 718129705)
    public JudgeByDay(Long id, String classify, boolean isFinish, String finishRate,
            int finishNum, int totalNum, String data) {
        this.id = id;
        this.classify = classify;
        this.isFinish = isFinish;
        this.finishRate = finishRate;
        this.finishNum = finishNum;
        this.totalNum = totalNum;
        this.data = data;
    }
    @Generated(hash = 739375887)
    public JudgeByDay() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getClassify() {
        return this.classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public boolean getIsFinish() {
        return this.isFinish;
    }
    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
    public String getFinishRate() {
        return this.finishRate;
    }
    public void setFinishRate(String finishRate) {
        this.finishRate = finishRate;
    }
    public int getFinishNum() {
        return this.finishNum;
    }
    public void setFinishNum(int finishNum) {
        this.finishNum = finishNum;
    }
    public int getTotalNum() {
        return this.totalNum;
    }
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }


}
