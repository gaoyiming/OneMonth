package com.mrgao.onemonth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mrgao.onemonth.base.BaseDatabindingFragment
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.bean.Task
import com.mrgao.onemonth.databinding.FragmentTodayTaskBinding
import com.mrgao.onemonth.model.DButil
import kotlinx.android.synthetic.main.fragment_today_task.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TodayTaskFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TodayTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TodayTaskFragment : BaseDatabindingFragment<FragmentTodayTaskBinding>() {
    lateinit var bindingAdapter: BindingAdapter<Task>
    lateinit var taskList: ArrayList<Task>
    override fun getLayoutId(): Int {
        return R.layout.fragment_today_task
    }

    override fun initdate() {
        super.initdate()
        taskList = DButil.getDaosession().taskDao.loadAll() as ArrayList<Task>
    }

    override fun initUI(view: View?, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bindingAdapter = object : BindingAdapter<Task>(taskList, R.layout.item_task, BR.task) {}
        recyclerView.adapter = bindingAdapter
        bindingAdapter.dates = taskList

    }


}
