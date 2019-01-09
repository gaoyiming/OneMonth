package com.mrgao.onemonth.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;



/**
 * Created by mr.gao on 2017/5/4.
 */

public  class BindingAdapter<T> extends RecyclerView.Adapter<BindingHolder> {
    private  int variableId;
    protected List<T> dates;
    private final int layout;
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener itemLongCLickListener;

    public BindingAdapter(List<T> datas, @LayoutRes int layout) {
        this.dates = datas;
        this.layout = layout;
    }
    public BindingAdapter(List<T> datas, @LayoutRes int layout, int variableId) {
        this.dates = datas;
        this.layout = layout;
        this.variableId=variableId;
    }
    public void setDates(List<T> datas) {
        if(datas==null||datas.size()==0){
            setEmptyView();
            return;
        }
        List<T> olddates = new ArrayList<>();
        olddates.addAll(this.dates);
        this.dates = datas;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EasyDiff<>(olddates, this.dates), true);
        diffResult.dispatchUpdatesTo(this);

        //  notifyDataSetChanged();
    }

    private void setEmptyView() {
        List<T> olddates = new ArrayList<>();
        List<T> datas = new ArrayList<>();
        olddates.addAll(this.dates);
        this.dates = datas;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EasyDiff<>(olddates, this.dates), true);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addDates(List<T> datas) {
        List<T> olddates = new ArrayList<>();
        olddates.addAll(this.dates);
        this.dates.addAll(datas);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EasyDiff<>(olddates, this.dates), true);
        diffResult.dispatchUpdatesTo(this);
        //  notifyDataSetChanged();
    }

    public void deleteData(int position) {
        List<T> olddates = new ArrayList<>();
        olddates.addAll(this.dates);

        dates.remove(position);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EasyDiff<>(olddates, this.dates), true);
        diffResult.dispatchUpdatesTo(this);
        olddates = null;
        //  notifyDataSetChanged();
    }

    public void clearDates() {
        List<T> list = new ArrayList<>();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new EasyDiff<>(this.dates, list), true);
        diffResult.dispatchUpdatesTo(this);
        this.dates = list;
        //  notifyDataSetChanged();
    }

    public List<T> getDates() {

        return this.dates;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View inflate = .inflate(layout, parent, false);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layout, parent, false);

        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.click(adapterPosition);
            }
        });
        holder.getBinding().getRoot().setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.click(adapterPosition);
            }
        });
        holder.getBinding().getRoot().setOnLongClickListener(v -> {
            if (itemLongCLickListener != null) {
                itemLongCLickListener.longClick(adapterPosition);
            }

            return false;
        });


        if(variableId!=0) {
            holder.getBinding().setVariable(variableId, dates.get(position));
            holder.getBinding().executePendingBindings();
        }
          convert(holder, position,dates.get(position));
    }

    /**
     * 如果没有传variableId，一定要重写这个方法
     * @param holder
     * @param position
     * @param t
     */
    public  void convert(BindingHolder holder, int position, T t){

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public void setOnItemCLickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnItemLongCLickListener(ItemLongClickListener itemLongCLickListener) {
        this.itemLongCLickListener = itemLongCLickListener;
    }

}
