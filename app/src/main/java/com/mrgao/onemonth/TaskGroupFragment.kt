package com.mrgao.onemonth

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mrgao.onemonth.base.BaseDatabindingFragment
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.base.BindingHolder
import com.mrgao.onemonth.bean.TaskGroup
import com.mrgao.onemonth.databinding.FragmentTodayTaskBinding
import com.mrgao.onemonth.databinding.ItemTaskgroupBinding
import com.mrgao.onemonth.databinding.ItemTaskgroupLeftBinding
import com.mrgao.onemonth.databinding.ItemTaskgroupReghtBinding
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.rx.RxBus
import com.onemonth.dao.TaskDao
import com.onemonth.dao.TaskGroupDao
import kotlinx.android.synthetic.main.fragment_today_task.*
import java.text.SimpleDateFormat
import java.util.*


class TaskGroupFragment : BaseDatabindingFragment<FragmentTodayTaskBinding>() {
    lateinit var bindingAdapter: BindingAdapter<TaskGroup>
    var taskList: ArrayList<TaskGroup> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.fragment_today_task
    }

    override fun initdate() {
        super.initdate()
        getData()
        RxBus.instance.register(String::class.java).subscribe { freshTag ->
            if ("TODAYTASK_REFRESH".endsWith(freshTag)) {
                getData()
            }
        }
    }

    private fun getData() {
//        val date = Date()
//        val format = SimpleDateFormat("yyyyMMdd")
//        val dateFormat = format.format(date)
         taskList = DButil.getDaosession().taskGroupDao.loadAll() as ArrayList<TaskGroup>


        bindingAdapter.dates = taskList
    }

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bindingAdapter = object : BindingAdapter<TaskGroup>(taskList, R.layout.item_taskgroup, BR.taskgroup) {
            override fun convert(holder: BindingHolder<*>?, position: Int, t: TaskGroup?) {
                super.convert(holder, position, t)
                val binding = holder!!.binding as ItemTaskgroupBinding
                val taskDao = DButil.getDaosession().taskDao
                val itemTaskLeftBinding = binding.smMenuViewLeft as ItemTaskgroupLeftBinding
                val createTime = taskList[position].createTime
                itemTaskLeftBinding.left.setOnClickListener {
                    binding.sml.scrollX = 0
                    deleteTaskAfterToday(taskDao, createTime)

                }
                val itemTaskReghtBinding = binding.smMenuViewRight as ItemTaskgroupReghtBinding
                itemTaskReghtBinding.right.setOnClickListener {
                    binding.sml.scrollX = 0

                    deleteAllTask(taskDao, createTime)
                }
            }

        }
        recyclerView.adapter = bindingAdapter
        bindingAdapter.dates = taskList
    }


    @SuppressLint("SimpleDateFormat")
    private fun deleteTaskAfterToday(taskDao: TaskDao, createTime: Long) {
        val date = Date()
        val sdf = SimpleDateFormat("yyyyMMdd")
        val dateNowStr = sdf.format(date).toInt()
        val list = taskDao.queryBuilder().where(TaskDao.Properties.CreateTime.eq(createTime)).list()
        for (task in list) {
            if (task.data.toInt() > dateNowStr)
                taskDao.delete(task)
        }
        RxBus.instance.post("TODAYTASK_REFRESH")
    }

    private fun deleteAllTask(taskDao: TaskDao, createTime: Long) {
        val list = taskDao.queryBuilder().where(TaskDao.Properties.CreateTime.eq(createTime)).list()
        for (task in list) {
            taskDao.delete(task)
        }
        val taskGroupDao = DButil.getDaosession().taskGroupDao
        val taskGroup = taskGroupDao.queryBuilder().where(TaskGroupDao.Properties.CreateTime.eq(createTime)).unique()
        taskGroupDao.delete(taskGroup)
        RxBus.instance.post("TODAYTASK_REFRESH")
    }


}
