package com.mrgao.onemonth.utils;

import android.content.Context;

import com.onemonth.dao.DaoMaster;
import com.onemonth.dao.JudgeByDayDao;
import com.onemonth.dao.JudgeByGroupDao;
import com.onemonth.dao.TaskDao;
import com.onemonth.dao.TaskGroupDao;

import org.greenrobot.greendao.database.Database;


public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    //    private static  class SQLiteOpenHelperHodler{
//        private  final MySQLiteOpenHelper instance = new MySQLiteOpenHelper();
//    }
    private static volatile MySQLiteOpenHelper helper = null;

    private MySQLiteOpenHelper(Context context) {
        super(context, "onemonth.db", null);
    }

    public static MySQLiteOpenHelper getInstance(Context context) {
        if (helper == null) {
            synchronized (MySQLiteOpenHelper.class) {
                if (helper == null) {
                    helper = new MySQLiteOpenHelper(context);
                }
            }
        }
        return helper;
    }

//    MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
//        super(context, name, factory);
//    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);

            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);

            }
        }, TaskDao.class, TaskGroupDao.class, JudgeByDayDao.class, JudgeByGroupDao.class);

    }
}
