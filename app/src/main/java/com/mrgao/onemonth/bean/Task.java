package com.mrgao.onemonth.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 文 件 名: Task
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/11 20:38
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
@Entity
public class Task {

    private long createTime;
    private long endTime;
    private long changeTime;
    private String  classify;
    private String  grade;
    private String  type;
    private String  repeat;

    private String  title;
    private String  content;
    private String repeatType;
    private boolean isFinish;
    private boolean isDelay;
    @Generated(hash = 896285853)
    public Task(long createTime, long endTime, long changeTime, String classify,
            String grade, String type, String repeat, String title, String content,
            String repeatType, boolean isFinish, boolean isDelay) {
        this.createTime = createTime;
        this.endTime = endTime;
        this.changeTime = changeTime;
        this.classify = classify;
        this.grade = grade;
        this.type = type;
        this.repeat = repeat;
        this.title = title;
        this.content = content;
        this.repeatType = repeatType;
        this.isFinish = isFinish;
        this.isDelay = isDelay;
    }
    @Generated(hash = 733837707)
    public Task() {
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getEndTime() {
        return this.endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public long getChangeTime() {
        return this.changeTime;
    }
    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }
    public String getClassify() {
        return this.classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getGrade() {
        return this.grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRepeat() {
        return this.repeat;
    }
    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getRepeatType() {
        return this.repeatType;
    }
    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }
    public boolean getIsFinish() {
        return this.isFinish;
    }
    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
    public boolean getIsDelay() {
        return this.isDelay;
    }
    public void setIsDelay(boolean isDelay) {
        this.isDelay = isDelay;
    }


}
