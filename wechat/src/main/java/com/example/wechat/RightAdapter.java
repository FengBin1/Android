package com.example.wechat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.page.Page1Activity;

import java.util.List;

public class RightAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserBean> mList;

    public RightAdapter(Context context, List<UserBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_sale = convertView.findViewById(R.id.tv_sale);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.iv_img = convertView.findViewById(R.id.iv_img);
            holder.tv_id = convertView.findViewById(R.id.tv_id); // 添加 tv_id 初始化
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserBean bean = mList.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_sale.setText(bean.getSales());
        holder.tv_price.setText(bean.getPrice());
        holder.iv_img.setImageResource(bean.getImg());
        holder.tv_id.setText(String.valueOf(position + 1)); // 设置专属 ID
        Log.d("RightAdapter", "Item ID: " + bean.getId());
        // 添加点击事件，跳转到Page1Activity，并传递所点击的 ID
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Page1Activity.class);
                intent.putExtra("id", bean.getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name, tv_sale, tv_price, tv_id; // 添加 tv_id 字段
        ImageView iv_img;
    }
}
