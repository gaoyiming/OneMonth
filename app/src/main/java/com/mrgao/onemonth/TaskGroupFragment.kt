package com.mrgao.onemonth

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mrgao.onemonth.base.BaseDatabindingFragment
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.bean.TaskGroup
import com.mrgao.onemonth.databinding.FragmentTodayTaskBinding
import com.mrgao.onemonth.model.DButil
import com.mrgao.onemonth.rx.RxBus
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
        RxBus.instance.register(String::class.java).subscribe(
                { freshTag ->
                    if ("TODAYTASK_REFRESH".endsWith(freshTag)) {
                        getData()
                    }
                }
        )
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
//            override fun convert(holder: BindingHolder<*>?, position: Int, t: TaskGroup?) {
//                super.convert(holder, position, t)
//                val binding = holder!!.binding as ItemTaskBinding
//                val taskDao = DButil.getDaosession().taskGroupDao
//                val itemTaskLeftBinding = binding.smMenuViewLeft as ItemTaskLeftBinding
//                itemTaskLeftBinding.left.setOnClickListener {
//                    startActivity<SmileActivity>("id" to taskList[position].id)
//                    binding.sml.scrollX = 0
//
//                }
//                val itemTaskReghtBinding = binding.smMenuViewRight as ItemTaskReghtBinding
//                itemTaskReghtBinding.right.setOnClickListener {
//                    binding.sml.scrollX = 0
//
//                    taskDao.delete(taskList[position])
//                    RxBus.instance.post("TODAYTASK_REFRESH")
//                }
//            }

        }
        recyclerView.adapter = bindingAdapter
        bindingAdapter.dates = taskList
    }


}
