package com.mrgao.onemonth.model

import com.mrgao.onemonth.base.BaseApplication
import com.mrgao.onemonth.bean.JudgeByDay
import com.mrgao.onemonth.bean.JudgeByGroup
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.*


/**
 * 文 件 名: DButil
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/11 20:50
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
object DButil {
    val daosession: DaoSession
        get() {
            val devOpenHelper = DaoMaster.DevOpenHelper(BaseApplication.getContext(), "onemonth.db", null)
            val daoMaster = DaoMaster(devOpenHelper.writableDb)
            return daoMaster.newSession()

        }
     fun checkIsFinish(taskDao: TaskDao, data: String?, classify: String?) {
        val judgeByGroupDao = DButil.daosession.judgeByGroupDao
        val judgeByDayDao = DButil.daosession.judgeByDayDao
        val taskList = taskDao.queryBuilder().where(TaskDao.Properties.Data.eq(data)
                , TaskDao.Properties.Classify.eq(classify)).list()
        var classifyIsFinish = true
        taskList.forEach {
            if (!it.isFinish) {
                classifyIsFinish = false
            }
        }

        val judgeByGroup = judgeByGroupDao.queryBuilder()
                .where(JudgeByGroupDao.Properties.Data.eq(data),
                        JudgeByGroupDao.Properties.Classify.eq(classify)).unique()
        if (judgeByGroup == null) {
            val group = JudgeByGroup()
            group.data = data
            group.classify = classify
            group.isFinish = classifyIsFinish
            judgeByGroupDao.insert(group)
        } else {
            judgeByGroup.isFinish = true
            judgeByGroupDao.update(judgeByGroup)
        }
        if (classifyIsFinish) {
            val judgeByGroupDao = judgeByGroupDao.queryBuilder()
                    .where(JudgeByGroupDao.Properties.Data.eq(data)).list()
            var dayIsFinish = true
            judgeByGroupDao.forEach {
                if (!it.isFinish) {
                    dayIsFinish = false
                }
            }
            val judgeByDay = judgeByDayDao.queryBuilder()
                    .where(JudgeByDayDao.Properties.Data.eq(data)).unique()
            if (judgeByDay == null) {
                val day = JudgeByDay()
                day.data = data
                day.classify = classify
                day.isFinish = dayIsFinish
                judgeByDayDao.insert(day)
            } else {
                judgeByDay.isFinish = true
                judgeByDayDao.update(judgeByDay)
            }
            RxBus.instance.post("TODAYTASK_REFRESH")
        }
    }

}
