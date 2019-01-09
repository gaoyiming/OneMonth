package com.mrgao.onemonth.view.group;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mrgao.onemonth.R;
import com.tubb.smrv.SwipeHorizontalMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rusan on 2017/5/15.
 */

public class RecyclerAdapter extends SecondaryListAdapter<RecyclerAdapter.GroupItemViewHolder, RecyclerAdapter.SubItemViewHolder> {


    private Context context;

    private List<DataTree<String, String>> dts = new ArrayList<>();

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List datas) {
        dts = datas;
        notifyNewData(dts);
    }

    @Override
    public RecyclerView.ViewHolder groupItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_title, parent, false);

        return new GroupItemViewHolder(v);
    }

    @Override
    public RecyclerView.ViewHolder subItemViewHolder(ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);

        return new SubItemViewHolder(v);
    }

    @Override
    public void onGroupItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex) {

        ((GroupItemViewHolder) holder).tvGroup.setText(dts.get(groupItemIndex).getGroupItem());

    }

    @Override
    public void onSubItemBindViewHolder(RecyclerView.ViewHolder holder, int groupItemIndex, int subItemIndex) {

        ((SubItemViewHolder) holder).tvSub.setText(dts.get(groupItemIndex).getSubItems().get(subItemIndex));

    }

    @Override
    public void onGroupItemClick(Boolean isExpand, GroupItemViewHolder holder, int groupItemIndex) {

        Toast.makeText(context, "group item " + String.valueOf(groupItemIndex) + " is expand " +
                String.valueOf(isExpand), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSubItemClick(SubItemViewHolder holder, int groupItemIndex, int subItemIndex) {

        Toast.makeText(context, "sub item " + String.valueOf(subItemIndex) + " in group item " +
                String.valueOf(groupItemIndex), Toast.LENGTH_SHORT).show();

    }

    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroup;

        public GroupItemViewHolder(View itemView) {
            super(itemView);

            tvGroup = (TextView) itemView.findViewById(R.id.classify);
//            val binding = holder!!.binding as ItemTaskBinding
//
//            val taskDao = DButil.daosession.taskDao
//            val itemTaskLeftBinding = binding.smMenuViewLeft as ItemTaskLeftBinding
//            itemTaskLeftBinding.left.setOnClickListener {
//                startActivity<SmileActivity>("id" to taskList[position].id)
//                binding.sml.scrollX = 0
//            }
//            if (defaultClassify != taskList[position].classify) {
//                binding.title.visibility = View.VISIBLE
//            }
//            defaultClassify = taskList[position].classify
//            val itemTaskReghtBinding = binding.smMenuViewRight as ItemTaskReghtBinding
//            itemTaskReghtBinding.right.setOnClickListener {
//                binding.sml.scrollX = 0
//                deleteTask(taskDao, position)
            SwipeHorizontalMenuLayout sml = itemView.findViewById(R.id.sml);
            View left = sml.findViewById(R.id.left);
            left.setOnClickListener(v-> {
//                onLeftClick();
                sml.scrollTo(0,0);
            });

            View right = sml.findViewById(R.id.right);
            right.setOnClickListener(v->{
//                onLeftClick();
                sml.scrollTo(0,0);
            } );


        }


    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvSub;

        public SubItemViewHolder(View itemView) {
            super(itemView);

            tvSub = (TextView) itemView.findViewById(R.id.classify);
        }
    }


}