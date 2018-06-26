package com.onemonth.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mrgao.onemonth.bean.Classify;
import com.mrgao.onemonth.bean.Task;
import com.mrgao.onemonth.bean.TaskGroup;

import com.onemonth.dao.ClassifyDao;
import com.onemonth.dao.TaskDao;
import com.onemonth.dao.TaskGroupDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig classifyDaoConfig;
    private final DaoConfig taskDaoConfig;
    private final DaoConfig taskGroupDaoConfig;

    private final ClassifyDao classifyDao;
    private final TaskDao taskDao;
    private final TaskGroupDao taskGroupDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        classifyDaoConfig = daoConfigMap.get(ClassifyDao.class).clone();
        classifyDaoConfig.initIdentityScope(type);

        taskDaoConfig = daoConfigMap.get(TaskDao.class).clone();
        taskDaoConfig.initIdentityScope(type);

        taskGroupDaoConfig = daoConfigMap.get(TaskGroupDao.class).clone();
        taskGroupDaoConfig.initIdentityScope(type);

        classifyDao = new ClassifyDao(classifyDaoConfig, this);
        taskDao = new TaskDao(taskDaoConfig, this);
        taskGroupDao = new TaskGroupDao(taskGroupDaoConfig, this);

        registerDao(Classify.class, classifyDao);
        registerDao(Task.class, taskDao);
        registerDao(TaskGroup.class, taskGroupDao);
    }
    
    public void clear() {
        classifyDaoConfig.clearIdentityScope();
        taskDaoConfig.clearIdentityScope();
        taskGroupDaoConfig.clearIdentityScope();
    }

    public ClassifyDao getClassifyDao() {
        return classifyDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public TaskGroupDao getTaskGroupDao() {
        return taskGroupDao;
    }

}
