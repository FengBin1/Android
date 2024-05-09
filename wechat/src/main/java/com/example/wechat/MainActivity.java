package com.example.wechat;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private static final String TAG = "MainActivity";
    private FragmentTransaction fragmentTransaction;
    private LeftFragment leftFragment;
    private TextView tv_recommend, tv_must_buy;
    private RightFragment rightFragment;
    private ImageView imageView1, imageView2, imageView3, imageView4;


    private Map<String, List<UserBean>> map;

    @Override
    protected void onResume() {
        super.onResume();
        // 在这里重新加载数据或执行其他必要的操作
        setData();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        init();
        clickEvent();
// 获取TextView的实例
        tv_recommend = findViewById(R.id.tv_recommend);
        tv_must_buy = findViewById(R.id.tv_must_buy);
        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageview2);
        imageView3 = findViewById(R.id.imageview3);
        imageView4 = findViewById(R.id.imageview4);
    }

    private void init() {
        fragmentManager = getFragmentManager();//获取fragmentManager
        //通过findFragmentById()方法获取leftFragment
        leftFragment = (LeftFragment) fragmentManager.findFragmentById(R.id.left);
        //获取左侧菜单栏中的控件
        tv_recommend = leftFragment.getView().findViewById(R.id.tv_recommend);
        tv_must_buy = leftFragment.getView().findViewById(R.id.tv_must_buy);
    }

    private void setData() {
        DBHelper dbHelper = new DBHelper(this);
        List<ChatMessage> chatContentList = dbHelper.getSortedChatContent();
        List<ChatMessage> UserDetails = dbHelper.getAllUserDetails();

        map = new HashMap<>();
        List<UserBean> list1 = new ArrayList<>();
        List<UserBean> list2 = new ArrayList<>();
        for (ChatMessage message : chatContentList) {
            int id = message.getSenderId();
            String content = message.getContent();
            String time = message.getTime();
            String name = message.getSenderName();
            int img = message.getImg();
            // 创建一个UserBean对象，将ChatMessage中的内容添加到UserBean中
            UserBean bean = new UserBean(id, name, content, time, img, "activity_page1");
            list1.add(bean);
        }

        map.put("1", list1);//将推荐菜单列表的数据添加到map集合中
        for (ChatMessage userDetails : UserDetails) {
            int id = userDetails.getSenderId();
            String name = userDetails.getSenderName();
            int img = userDetails.getImg();
            // 创建一个UserBean对象，将ChatMessage中的内容添加到UserBean中
            UserBean bean = new UserBean(id, name, "", "", img, "activity_page1");
            list2.add(bean);
        }
        map.put("2", list2); //将进店必买菜单列表的数据添加到map集合中
    }

    private void clickEvent() {
        tv_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用switchData()方法填充Rightfragment中的数据
                switchData(map.get("1"));
                tv_recommend.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_must_buy.setTextColor(getResources().getColor(R.color.black));
                imageView1.setImageResource(R.drawable.wechat2);
                imageView2.setImageResource(R.drawable.txl);
            }
        });
        tv_must_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchData(map.get("2"));
                tv_must_buy.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_recommend.setTextColor(getResources().getColor(R.color.black));
                imageView1.setImageResource(R.drawable.wechat1);
                imageView2.setImageResource(R.drawable.txl2);
            }
        });
        //设置首次进入界面后，默认需要显示的数据
        switchData(map.get("1"));
    }

    /**
     * 填充Activity右侧的Fragment，并传递列表数据list
     */

    public void switchData(List<UserBean> list) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();//开启一个事务
        //通过调用getInstance()方法实例化RightFragment
        rightFragment = new RightFragment().getInstance(list);
        //调用replace()方法
        fragmentTransaction.replace(R.id.right, rightFragment);
        fragmentTransaction.commit();
    }

}
