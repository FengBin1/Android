package com.example.page;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wechat.R;
import com.example.wechat.RightAdapter;
import com.example.wechat.UserBean;

import java.io.Serializable;
import java.util.List;
public class ChatFragment extends Fragment {
    private ListView lv_list;
    public ChatFragment() {
    }
    public com.example.page.ChatFragment getInstance(List<ChatBean> list) {
        com.example.page.ChatFragment chatFragment = new com.example.page.ChatFragment();
        //通过Bundle对象传递数据可以保证在设备横竖屏切换时传递的数据不丢失
        Bundle bundle = new Bundle();
        //将需要传递的字符串以键值对的形式传入bundle对象
        bundle.putSerializable("list", (Serializable) list);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_layout, container, false);
        lv_list = view.findViewById(R.id.lv_list);
        if (getArguments() != null) {
            List<ChatBean> list = (List<ChatBean>) getArguments().
                    getSerializable("list");
            ChatAdapter adapter = new ChatAdapter(getActivity(), list);
            lv_list.setAdapter(adapter);
        }
        return view;
    }
}
