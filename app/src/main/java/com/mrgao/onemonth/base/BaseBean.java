package com.mrgao.onemonth.base;

/**
 * 文 件 名: BaseBean
 * 创 建 人: mr.gao
 * 创建日期: 2018/11/15 16:42
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public class BaseBean<T> {

    /**
     * code : 200
     * message : 有新的版本
     * data : {"_id":"5bebf5d4c3666e36a02baf97","versionname":1.1,"versioncode":2,"describe":"增加版本更新","url":"http://52.77.224.141:3000/download/onemonth1.1.apk"}
     */

    private int code;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
