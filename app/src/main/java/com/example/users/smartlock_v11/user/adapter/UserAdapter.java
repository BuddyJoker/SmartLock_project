package com.example.users.smartlock_v11.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;
import com.example.users.smartlock_v11.user.bean.UserBean;
import com.example.users.smartlock_v11.user.bean.UserInfoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BigBoss on 2017/8/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private List<UserInfoBean.ResultBean> result;
    private UserInfoBean results;
    public UserAdapter(Context contexts,List<UserInfoBean.ResultBean> result) {
        this.mContext=contexts;
        this.result=result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        ViewHolder vh=new ViewHolder(view);
        return vh;
    }

    //数据绑定
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfoBean.ResultBean resultBean=result.get(position);
        holder.mTextView.setText(resultBean.getUser_info());
        holder.user_id.setText(resultBean.getUid());
    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_user_id)
        TextView user_id;
        @Bind(R.id.item_user_name)
        TextView mTextView;
        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
