package com.example.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wechat.R;
import com.example.wechat.UserBean;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChatBean> mList;

    public ChatAdapter(Context context, List<ChatBean> list) {
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
        ChatAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false);
            holder = new ChatAdapter.ViewHolder();
            holder.chat_text = convertView.findViewById(R.id.chat_text);
            holder.chat_img = convertView.findViewById(R.id.chat_img);
            convertView.setTag(holder);
        } else {
            holder = (ChatAdapter.ViewHolder) convertView.getTag();
        }

        ChatBean bean = mList.get(position);
        holder.chat_text.setText(bean.getText());
        holder.chat_img.setImageResource(bean.getImg());

        return convertView;
    }

    static class ViewHolder {
        TextView chat_text ;// 添加 tv_id 字段
        ImageView chat_img;
    }
}
