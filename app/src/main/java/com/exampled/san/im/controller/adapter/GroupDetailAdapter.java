
package com.exampled.san.im.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exampled.san.im.R;
import com.exampled.san.im.model.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by San on 2016/11/19.
 */

//群详情适配器
public class GroupDetailAdapter extends BaseAdapter {

    private Context mContext;
    private Boolean mIsCanModify; //是否允许添加和删除成员
    private List<UserInfo> mUsers=new ArrayList<>();
    private boolean mIsDeleteModel;//
    private OnGroupDetailListener mOnGroupDetailListener;
    public GroupDetailAdapter(Context context,Boolean isCanModify,OnGroupDetailListener onGroupDetailListener) {
        mContext=context;
        mIsCanModify=isCanModify;
        mOnGroupDetailListener=onGroupDetailListener;

    }
    //获取当前的删除模式
    public boolean ismIsDeleteModel() {
        return mIsDeleteModel;
    }
    //设置当前删除模式
    public void setmIsDeleteModel(boolean mIsDeleteModel) {
        this.mIsDeleteModel = mIsDeleteModel;
    }
    //刷新数据
    public  void refresh( List<UserInfo> users){
        if(users!=null&&users.size()>=0){
            mUsers.clear();
            //添加加号和减号
            initUsers();
            mUsers.addAll(0,users);
        }
        notifyDataSetChanged();
    }
    private void initUsers() {
        UserInfo add = new UserInfo("add");
        UserInfo delete = new UserInfo("delete");
        mUsers.add(delete);
        mUsers.add(0,add);

    }
    @Override
    public int getCount() {
        return mUsers==null?0:mUsers.size();
    }
    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        //获取或创建viewholder
        ViewHolder holder=null;
        if(converView==null){
            holder=new ViewHolder();
            converView=View.inflate(mContext, R.layout.item_groupdetail,null);
            holder.photo= (ImageView) converView.findViewById(R.id.iv_group_detail_photo);
            holder.delete= (ImageView) converView.findViewById(R.id.iv_group_detail_delete);
            holder.name= (TextView) converView.findViewById(R.id.tv_group_detail_name);
            converView.setTag(holder);
        }else{
            holder=(ViewHolder) converView.getTag();
        }
        //获取item数据
        final UserInfo userInfo = mUsers.get(position);
        //显示数据
        if(mIsCanModify){//群主开发了群权限
            //布局的处理
            if(position==getCount()-1){//减号的处理
                if(mIsDeleteModel){
                    converView.setVisibility(View.INVISIBLE);
                }else{
                    converView.setVisibility(View.VISIBLE);
                    holder.photo.setImageResource(R.drawable.em_smiley_minus_btn_pressed);
                    holder.delete.setVisibility(View.GONE);
                    holder.name.setVisibility(View.INVISIBLE);
                }
            }else if (position==getCount()-2){//加号的处理
                //删除模式判断
                if(mIsDeleteModel){
                    converView.setVisibility(View.INVISIBLE);
                }else{
                    converView.setVisibility(View.VISIBLE);
                    holder.photo.setImageResource(R.drawable.em_smiley_add_btn_pressed);
                    holder.delete.setVisibility(View.GONE);
                    holder.name.setVisibility(View.INVISIBLE);
                }
            }else{//群成员
                converView.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);
                holder.name.setText(userInfo.getName());
                holder.photo.setImageResource(R.drawable.em_default_avatar);
                if(mIsDeleteModel){
                    holder.delete.setVisibility(View.VISIBLE);
                }else {
                    holder.delete.setVisibility(View.GONE);
                }
            }
            //点击事件处理
            if(position==getCount()-1){//减号
                holder.photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!mIsDeleteModel){
                            mIsDeleteModel=true;
                            notifyDataSetChanged();
                        }
                    }
                });
            }else if(position==getCount()-2){//加号
                        holder.photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnGroupDetailListener.onAddMembers();
                    }
                });
            }else{
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnGroupDetailListener.onDeleteMember(userInfo);
                    }
                });
            }
        }else{//普通的群成员
            if(position==getCount()-1&&position==getCount()-2){
                converView.setVisibility(View.GONE);
            }else {
                converView.setVisibility(View.VISIBLE);

                //名称
                holder.name.setText(userInfo.getName());
                //头像
                holder.photo.setImageResource(R.drawable.em_default_avatar);
                //删除
                holder.delete.setVisibility(View.GONE);
            }
        }
        //返回数据
        return converView;
    }
    public class ViewHolder{
        private ImageView photo;
        private ImageView delete;
        private TextView name;
    }
    public interface OnGroupDetailListener{
        //添加群成员方法
        void onAddMembers();
        //删除群成员的方法
        //void onDeleteMember();
        //删除群成员
        void onDeleteMember(UserInfo user);
    }
}





