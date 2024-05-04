package com.example.page;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.db.DBHelper;
import com.example.wechat.LeftFragment;
import com.example.wechat.R;
import com.example.wechat.RightFragment;
import com.example.wechat.UserBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page1Activity extends AppCompatActivity {
    private TextView tvId;

    private EditText etChatContent;
    private Button btnSend;

    //   展示数据
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ChatFragment chatFragment;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        init();
        // 初始化界面控件
        tvId = findViewById(R.id.tv_name);
        etChatContent = findViewById(R.id.et_chat_content); // 新添加的 EditText 控件
        btnSend = findViewById(R.id.btn_send); // 新添加的 Button 控件
        // 获取传递过来的 ID
        DBHelper dbHelper = new DBHelper(this);

        // 获取传递过来的 ID
        Intent intent = getIntent();
        int id = -1; // 默认值为 -1，表示未找到有效的 ID
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        }
        String name = null; // 用于存储用户名
        int chatimg = 0;
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
            // 获取用户名
            name = dbHelper.getUserNameById(id);
            // 获取用户头像资源 ID 并设置
            chatimg = dbHelper.getUserImgById(id);
        }

// 如果找到有效的 ID 和用户名，则执行相应的操作
        if (id != -1 && name != null) {
            tvId.setText(name);
        }

//        加载界面
        refreshChatContent(id, dbHelper, chatimg);
// 如果找到有效的 ID，则执行相应的操作
        if (id != -1) {
            // 在这里执行一些逻辑操作
            // 点击发送按钮的事件处理
            int finalId = id;
            int finalChatimg = chatimg;
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取当前时间
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String currentTime = dateFormat.format(new Date());

                    // 获取输入框中的内容
                    String content = etChatContent.getText().toString().trim();

                    // 向数据库添加消息记录
                    dbHelper.insertChatMessage(finalId, content, currentTime);

                    // 清空输入框内容
                    etChatContent.setText("");
                    // 刷新聊天记录
                    refreshChatContent(finalId, dbHelper, finalChatimg);
                }
            });
        } else {
            // 处理未找到有效 ID 的情况
            Log.d("Page1Activity", "未找到有效的 ID");
        }

    }

    private void refreshChatContent(int id, DBHelper dbHelper ,int chatimg) {
        // 根据 ID 查询聊天记录并显示
        List<String[]> chatContentList = dbHelper.getChatContentById(id);
        setData(chatimg ,chatContentList);
    }
    //推荐菜单列表数据
    private String[] names1 = {"表姐", "大姐",
            "二姐","大爷","微信支付"};

    private Map<String,List<ChatBean>> map;
    private void init() {
        fragmentManager = getFragmentManager();//获取fragmentManager
        //通过findFragmentById()方法获取leftFragment
        chatFragment = (ChatFragment) fragmentManager.findFragmentById(R.id.left);
    }
    private void setData(int chatimg, List<String[]> chatContentList) {
        map = new HashMap<>();
        List<ChatBean> list1 = new ArrayList<>();
        for (String[] strings : chatContentList) {
            // 在这里直接使用 strings[0] 和 strings[1] 获取消息内容和发送者
            ChatBean chat = new ChatBean(strings[0], chatimg); // 使用chatimg作为头像
            list1.add(chat);
        }
        map.put("1", list1); // 将数据添加到map中
        switchData(map.get("1"));
    }


    public void switchData(List<ChatBean> list) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();//开启一个事务
        //通过调用getInstance()方法实例化RightFragment
        chatFragment = new ChatFragment().getInstance(list);
        //调用replace()方法
        fragmentTransaction.replace(R.id.chat_fragment, chatFragment);
        fragmentTransaction.commit();
    }

}
