package com.example.wechat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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


    //推荐菜单列表数据
    private String[] names1 = {"表姐", "大姐",
            "二姐","大爷","微信支付"};
    private String[] sales1 = {"[微信红包] 恭喜发财，大吉大利", "[图片]",
            "[文件] 实验1-NumPy数值计算基础...","[转账] 您发起了一笔转账","微信支付凭证"};
    private String[] prices1 = {"20:23", "05:42", "06:15", "19:27", "12:00"};
    private int[] imgs1 = {R.drawable.oneone, R.drawable.onetwo,
            R.drawable.onethree,
            R.drawable.onefour,
            R.drawable.onefive};
    //进店必买菜单列表数据
    private String[] names2 = {"川越月美", "川越朝美", "川越星美"};
    private String[] sales2 = {"故事主角，十分吝啬", "月美的女儿",
            "朝美的妹妺"};
    private String[] prices2 = {"26岁", "6岁", "2岁"};
    private int[] imgs2 = {R.drawable.twoone, R.drawable.twotwo,
            R.drawable.twothree};
    private Map<String,List<UserBean>> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        init();
        clickEvent();
        DBHelper dbHelper = new DBHelper(this);
        List<ChatMessage> chatContentList = dbHelper.getSortedChatContent();
    }
    private void init() {
        fragmentManager = getFragmentManager();//获取fragmentManager
        //通过findFragmentById()方法获取leftFragment
        leftFragment = (LeftFragment) fragmentManager.findFragmentById(R.id.left);
        //获取左侧菜单栏中的控件
        tv_recommend = leftFragment.getView().findViewById(R.id.tv_recommend);
        tv_must_buy = leftFragment.getView().findViewById(R.id.tv_must_buy);
    }
    private void setData(){
        map=new HashMap<>();
        List<UserBean> list1=new ArrayList<>();
        List<UserBean> list2=new ArrayList<>();
        for (int i=0;i<names1.length;i++){
            UserBean bean = new UserBean(i+1, names1[i], sales1[i], prices1[i], imgs1[i],"activity_page1");
            list1.add(bean);
        }
        map.put("1",list1);//将推荐菜单列表的数据添加到map集合中
        for (int i=0;i<names2.length;i++){
            UserBean bean = new UserBean(i+1, names1[i], sales1[i], prices1[i], imgs1[i],"activity_page1");
            list2.add(bean);
        }
        map.put("2",list2); //将进店必买菜单列表的数据添加到map集合中
    }
    private void clickEvent() {
        tv_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用switchData()方法填充Rightfragment中的数据
                switchData(map.get("1"));
            }
        });
        tv_must_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchData(map.get("2"));
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
