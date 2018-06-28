package com.mrgao.onemonth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mrgao.onemonth.bean.JudgeByDay
import com.mrgao.onemonth.bean.JudgeByGroup
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.JudgeByDayDao
import com.onemonth.dao.JudgeByGroupDao
import com.onemonth.dao.TaskDao
import com.onemonth.dao.TaskGroupDao
import kotlinx.android.synthetic.main.activity_smile.*
import org.jetbrains.anko.toast

class SmileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smile)
        val taskDao = DButil.getDaosession().taskDao
        val taskGroupDao = DButil.getDaosession().taskGroupDao

        rating_bar.setOnRatingBarChangeListener { _, rating, _ -> smiley_rating.setSmiley(rating) }
        commit.setOnClickListener {
            val unique = taskDao.queryBuilder()
                    .where(TaskDao.Properties.Id.eq(intent.extras.get("id"))).unique()
            val data = unique.data
            val classify = unique.classify
            val createTime = unique.createTime
            unique.isFinish = true
            unique.grade = rating_bar.numStars.toString()
            taskDao.update(unique)
            val uniquegroup = taskGroupDao.queryBuilder()
                    .where(TaskGroupDao.Properties.CreateTime.eq(createTime)).unique()
            uniquegroup.finishNum += 1
            taskGroupDao.update(uniquegroup)
            RxBus.instance.post("TODAYTASK_REFRESH")
            CheckIsFinish(taskDao, data, classify)
            finish()
            try {

            } catch (e: Exception) {
                toast(e.toString())
            }
        }
    }

    private fun CheckIsFinish(taskDao: TaskDao, data: String?, classify: String?) {
        val judgeByGroupDao = DButil.getDaosession().judgeByGroupDao
        val judgeByDayDao = DButil.getDaosession().judgeByDayDao
        val tasklist = taskDao.queryBuilder().where(TaskDao.Properties.Data.eq(data)
                , TaskDao.Properties.Classify.eq(classify)).list()
        var classifyIsFinish = true
        tasklist.forEach {
            if (!it.isFinish) {
                classifyIsFinish = false
            }
        }

        val judgeByGroup = judgeByGroupDao.queryBuilder()
                .where(JudgeByGroupDao.Properties.Data.eq(data), JudgeByGroupDao.Properties.Classify.eq(classify)).unique()
        if (judgeByGroup == null) {
            val group = JudgeByGroup()
            group.data = data
            group.classify = classify
            group.isFinish = classifyIsFinish

            judgeByGroupDao.insert(group)
        }
        if (classifyIsFinish) {
            val judgeByDayList = judgeByGroupDao.queryBuilder()
                    .where(JudgeByGroupDao.Properties.Data.eq(data)).list()
            var dayIsFinish = true
            judgeByDayList.forEach {
                if (!it.isFinish) {
                    dayIsFinish = false
                }
            }
            val judgeByDay = judgeByDayDao.queryBuilder()
                    .where(JudgeByDayDao.Properties.Data.eq(data)).list()
            if (judgeByDay == null) {
                val day = JudgeByDay()
                day.data = data
                day.classify = classify
                day.isFinish = dayIsFinish
                judgeByDayDao.insert(day)
            }

        }
    }
}
