package com.mrgao.onemonth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mrgao.onemonth.base.BindingAdapter
import com.mrgao.onemonth.bean.Classify
import com.mrgao.onemonth.model.DButil
import com.onemonth.dao.ClassifyDao

import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import kotlinx.android.synthetic.main.activity_classify.*


//dialogBuilder
//.withTitle("Modal Dialog")                                  //.withTitle(null)  no title
//.withTitleColor("#FFFFFF")                                  //def
//.withDividerColor("#11000000")                              //def
//.withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
//.withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
//.withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)
//.withIcon(getResources().getDrawable(R.drawable.icon))
//.withDuration(700)                                          //def
//.withEffect(effect)                                         //def Effectstype.Slidetop
//.withButton1Text("OK")                                      //def gone
//.withButton2Text("Cancel")                                  //def gone
//.isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
//.setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
//.setButton1Click(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
//    }
//})
//.setButton2Click(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Toast.makeText(v.getContext(),"i'm btn2",Toast.LENGTH_SHORT).show();
//    }
//})
//.show();

class ClassifyActivity : AppCompatActivity() {
    lateinit var classifyDao: ClassifyDao
    lateinit var bindingAdapter: BindingAdapter<Classify>
    lateinit var classifyLists: ArrayList<Classify>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classifyDao = DButil.getDaosession().classifyDao


        setContentView(R.layout.activity_classify)
        addClassify.setOnClickListener {
            LovelyTextInputDialog(this)
                    .setTopColorRes(R.color.main_color)
                    .setConfirmButton("确定", {
                        addToDatabase(it)
                    })
                    .setIcon(R.mipmap.classify)
                    .setTitle(R.string.addClassify)
                    .setNegativeButton(android.R.string.no, null)
                    .show()
        }

        classifyLists = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bindingAdapter = object : BindingAdapter<Classify>(classifyLists, R.layout.item_classify, BR.classify) {


        }
        recyclerView.adapter = bindingAdapter
        getData()
    }

    private fun getData() {
        classifyLists = classifyDao.loadAll() as ArrayList<Classify>
        bindingAdapter.dates=classifyLists
        bindingAdapter.notifyDataSetChanged()

    }

    private fun addToDatabase(classy: String?) {


        val classify = Classify()
        classify.classify = classy
        val insert = classifyDao.insert(classify)

    }
}
