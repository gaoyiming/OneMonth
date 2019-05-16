package com.mrgao.onemonth.bean;

import java.util.List;

/**
 * 文 件 名: BaseBeanL
 * 创 建 人: mr.gao
 * 创建日期: 2019/3/18 10:47
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 *
 * @author mr.gao
 */
public class BaseBeanL<T> {


    private int code;
    private String message;
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
