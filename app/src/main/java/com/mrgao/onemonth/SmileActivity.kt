package com.mrgao.onemonth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.model.DButil.checkIsFinish
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.TaskDao
import com.onemonth.dao.TaskGroupDao
import kotlinx.android.synthetic.main.activity_smile.*
import org.jetbrains.anko.toast

class SmileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smile)
        val taskDao = DButil.daosession.taskDao
        val taskGroupDao = DButil.daosession.taskGroupDao

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
            checkIsFinish(taskDao, data, classify)
            finish()
            try {

            } catch (e: Exception) {
                toast(e.toString())
            }
        }
    }


}
