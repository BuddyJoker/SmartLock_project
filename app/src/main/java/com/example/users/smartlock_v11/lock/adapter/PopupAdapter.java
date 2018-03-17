package com.example.users.smartlock_v11.lock.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.users.smartlock_v11.R;

import java.util.ArrayList;

/**
 * Created by hp on 2018/3/17.
 */

public class PopupAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private ArrayList<String> myItems;
    private int myType;

    public PopupAdapter(Context context, ArrayList<String> items, int type) {
        this.myContext = context;
        this.myItems = items;
        this.myType = type;

        inflater = LayoutInflater.from(myContext);

    }

    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public String getItem(int position) {
        return myItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopupHolder holder = null;
        if (convertView == null) {
            holder = new PopupHolder();
            convertView = inflater.inflate(R.layout.top_popup_item, null);
            holder.itemNameTv = (TextView) convertView
                    .findViewById(R.id.popup_tv);
            if (myType == 0) {
                holder.itemNameTv.setGravity(Gravity.CENTER);
            } else if (myType == 1) {
                holder.itemNameTv.setGravity(Gravity.LEFT);
            } else if (myType == 2) {
                holder.itemNameTv.setGravity(Gravity.RIGHT);
            }
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        String itemName = getItem(position);
        holder.itemNameTv.setText(itemName);
        return convertView;
    }

    private class PopupHolder {
        TextView itemNameTv;
    }
}
