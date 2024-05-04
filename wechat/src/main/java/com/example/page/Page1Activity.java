package com.example.page;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private TextView tvChatContent;
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
        setData();
        init();
        // 初始化界面控件
        tvId = findViewById(R.id.tv_name);
        tvChatContent = findViewById(R.id.tv_chat_content); // 新添加的 TextView 控件
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
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
            // 获取用户名
            name = dbHelper.getUserNameById(id);
        }

// 如果找到有效的 ID 和用户名，则执行相应的操作
        if (id != -1 && name != null) {
            // 在这里执行一些逻辑操作
            // 设置用户名到 TextView
            tvId.setText(name);
        }
//        加载界面
        refreshChatContent(id, dbHelper);
// 如果找到有效的 ID，则执行相应的操作
        if (id != -1) {
            // 在这里执行一些逻辑操作
            // 点击发送按钮的事件处理
            int finalId = id;
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
                    refreshChatContent(finalId, dbHelper);
                }
            });
        } else {
            // 处理未找到有效 ID 的情况
            Log.d("Page1Activity", "未找到有效的 ID");
        }

    }

    private void refreshChatContent(int id, DBHelper dbHelper) {
        // 根据 ID 查询聊天记录并显示
        List<String[]> chatContentList = dbHelper.getChatContentById(id);
        String chatContent = getChatContent(chatContentList);
        tvChatContent.setText(chatContent);
    }

    private static String getChatContent(List<String[]> chatContentList) {
        StringBuilder chatContentBuilder = new StringBuilder();

        //        // 初始化上一条消息的时间
        String lastTime = null;

        for (String[] message : chatContentList) {
            String content = message[0];
            String time = message[1];
            String sender = message[2];

            // 判断是否需要显示时间
            if (!time.equals(lastTime)) {
                // 如果上一条消息的时间不为空，则插入时间并居中显示
                if (lastTime != null) {
                    chatContentBuilder.append("\n"); // 在上一分钟的消息组之后添加空行
                }

                // 将时间设置为上一条消息的时间，用于下一次比较
                lastTime = time;

                // 计算空格数量以使时间居中显示
                int spaceCount = (90 - time.length()) / 2;

                // 构造居中显示的时间字符串
                StringBuilder timeBuilder = new StringBuilder();
                for (int i = 0; i < spaceCount; i++) {
                    timeBuilder.append(" ");
                }
                timeBuilder.append(time);

                // 拼接并显示时间
                chatContentBuilder.append(timeBuilder.toString()).append("\n");
            }

            // 拼接并显示消息内容和发送者
            chatContentBuilder.append(sender).append(": ").append(content).append("\n");
        }

        return chatContentBuilder.toString();
    }


    //推荐菜单列表数据
    private String[] names1 = {"表姐", "大姐",
            "二姐","大爷","微信支付"};
    private int[] imgs1 = {R.drawable.oneone, R.drawable.onetwo,
            R.drawable.onethree,
            R.drawable.onefour,
            R.drawable.onefive};

    private Map<String,List<ChatBean>> map;
    private void init() {
        fragmentManager = getFragmentManager();//获取fragmentManager
        //通过findFragmentById()方法获取leftFragment
        chatFragment = (ChatFragment) fragmentManager.findFragmentById(R.id.left);
    }
    private void setData(){
        map=new HashMap<>();
        List<ChatBean> list1=new ArrayList<>();
        for (int i=0;i<names1.length;i++){
            ChatBean chat = new ChatBean(names1[i],imgs1[i]);
            list1.add(chat);
        }
        map.put("1",list1);//将推荐菜单列表的数据添加到map集合中
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
