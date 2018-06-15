package com.mrgao.onemonth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: TextTagsAdapter
 * 创 建 人: mr.gao
 * 创建日期: 2018/6/12 17:35
 * 邮   箱: coder.mrgao@gmail.com
 * 修改时间：
 * 描述：
 */
public abstract class TextTagsAdapter extends TagsAdapter {

    private List<String> dataSet = new ArrayList<>();
    private String content;

    public TextTagsAdapter(@NonNull List<String> data) {
        dataSet.clear();
        dataSet.addAll(data);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.tag_item, null);
        TextView classify = inflate.findViewById(R.id.classify);
        final String s = dataSet.get(position);
        classify.setText(s);
        classify.setOnClickListener(v -> {

            getCheckContent(s);
        });
//        TextView tv = new TextView(context);
//        tv.setText("No." + position);
//        tv.setGravity(Gravity.CENTER);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("Click", "Tag " + position + " clicked.");
//                Toast.makeText(context, "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//        tv.setTextColor(Color.WHITE);
        return inflate;
    }

    public abstract void getCheckContent(String content);

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
//        view.setBackgroundColor(themeColor);
    }
}
