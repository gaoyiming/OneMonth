package com.mrgao.onemonth.bean;

/**
 * 文 件 名: Classify
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/11 20:47
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 * @author mr.gao
 */

public class Classify {

    private Long id;
    private String classify;
    private String _id;
    private String username;
    private int isDelete;


    public Classify(Long id, String classify) {
        this.id = id;
        this.classify = classify;
    }


    public Classify() {
    }

    public String getClassify() {
        return this.classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
