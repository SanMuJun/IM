
package com.exampled.san.im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.exampled.san.im.R;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by San on 2016/11/18.
 */
public class GroupListAdapter extends BaseAdapter {

    private Context mContent;
    private List<EMGroup> mGroups=new ArrayList<>();
    public GroupListAdapter(Context context) {
        mContent=context;
    }
    public void refresh(List<EMGroup> groups){
        if(groups!=null&&groups.size()>=0){
            mGroups.clear();
            mGroups.addAll(groups);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return mGroups==null ? 0 :mGroups.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent) {

        //创建或获取viewHolder
        ViewHolder holder=null;
        if(converView==null){
            holder=new ViewHolder();
            converView=View.inflate(mContent, R.layout.item_grouplist,null);
            holder.name= (TextView) converView.findViewById(R.id.tv_grouplist_name);
            converView.setTag(holder);
        }else{
            holder=(ViewHolder) converView.getTag();
        }
        //获取当前item数据
        EMGroup emGroup = mGroups.get(position);
        //显示数据
        holder.name.setText(emGroup.getGroupName());
        //返回数据
        return converView;
    }
    private class ViewHolder{
            private TextView name;

    }
}
