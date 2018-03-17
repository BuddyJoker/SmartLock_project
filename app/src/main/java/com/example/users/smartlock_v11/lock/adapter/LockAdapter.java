package com.example.users.smartlock_v11.lock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.lock.bean.LockBean;
import com.example.users.smartlock_v11.user.adapter.UserAdapter;

import java.nio.DoubleBuffer;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2017/8/19.
 */

public class LockAdapter extends RecyclerView.Adapter<LockAdapter.ViewHolder> {

    private Context context;
    //private List<LockBean.ResultBean> result;
    private List<LockBean.SubserverInfoBean> subserverInfoBeans;
    private LockBean results;

    public LockAdapter (Context contexts,List<LockBean.SubserverInfoBean> subserverInfoBeans){
        this.context=contexts;
        this.subserverInfoBeans=subserverInfoBeans;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lock,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(LockAdapter.ViewHolder holder, int position) {
        //LockBean.ResultBean resultBean=result.get(position);
        LockBean.SubserverInfoBean subserverInfoBean=subserverInfoBeans.get(position);
        holder.lock_id.setText(subserverInfoBean.getSubserverID());
        holder.item_lock_port.setText(subserverInfoBean.getSubserverIP());
        holder.item_lock_name.setText(subserverInfoBean.getSubserverNickName());
    }

    @Override
    public int getItemCount() {
        return subserverInfoBeans.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_lock_id)
        TextView lock_id;
        @Bind(R.id.item_lock_port)
        TextView item_lock_port;
        @Bind(R.id.item_lock_name)
        TextView item_lock_name;
        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
