package com.mrgao.onemonth.model;

import com.mrgao.onemonth.base.BaseApplication;
import com.onemonth.dao.DaoMaster;
import com.onemonth.dao.DaoSession;


/**
 * 文 件 名: DButil
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/11 20:50
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public class DButil {
  public static DaoSession getDaosession(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(), "onemonth.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
      return daoMaster.newSession();

    }


}
