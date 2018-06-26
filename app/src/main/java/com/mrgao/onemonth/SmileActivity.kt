package com.mrgao.onemonth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.TaskDao
import kotlinx.android.synthetic.main.activity_smile.*

class SmileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smile)
        val taskDao = DButil.getDaosession().taskDao
        rating_bar.setOnRatingBarChangeListener { _, rating, _ -> smiley_rating.setSmiley(rating) }
        commit.setOnClickListener {
            try {
                val unique = taskDao.queryBuilder().where(TaskDao.Properties.Id.eq(intent.extras.get("id"))).unique()
                unique.isFinish = true
                unique.grade = rating_bar.numStars.toString()
                taskDao.update(unique)
                RxBus.instance.post("TODAYTASK_REFRESH")
                finish()
            } catch (e: Exception) {
            }
        }
    }
}
