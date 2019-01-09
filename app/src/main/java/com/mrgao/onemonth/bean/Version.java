package com.mrgao.onemonth.bean;

/**
 * 文 件 名: Version
 * 创 建 人: mr.gao
 * 创建日期: 2018/11/14 18:25
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public class Version {

    /**
     * _id : 5bebf5d4c3666e36a02baf97
     * versionName : 1.1
     * versionCode : 2
     * describe : 增加版本更新
     * url : http://52.77.224.141:3000/download/onemonth1.1.apk
     */

    private String _id;
    private double versionName;
    private int versionCode;
    private String describe;
    private String url;
    /**
     * versionName : 1.1
     * title : 增加版本更新
     */

    private String title;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public double getVersionName() {
        return versionName;
    }

    public void setVersionName(double versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
