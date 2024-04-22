package com.example.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    MyBroadcastReceiverOne one;
    MyBroadcastReceiverTwo two;
    MyBroadcastReceiverThree three;
// 冯斌
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver();
        init();
    }
    private void registerReceiver(){
        // 动态注册MyBroadcastReceiverTwo广播
        two = new MyBroadcastReceiverTwo();
        IntentFilter filter2 = new IntentFilter();
        filter2.setPriority(2000);
        filter2.addAction("Intercept_Stitch");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(two, filter2, Context.RECEIVER_NOT_EXPORTED); // 添加RECEIVER_NOT_EXPORTED标志
        }

        // 动态注册MyBroadcastReceiverOne广播
        one = new MyBroadcastReceiverOne();
        IntentFilter filter1 = new IntentFilter();
        filter1.setPriority(800);
        filter1.addAction("Intercept_Stitch");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(one, filter1, Context.RECEIVER_NOT_EXPORTED); // 添加RECEIVER_NOT_EXPORTED标志
        }

        // 动态注册MyBroadcastReceiverThree广播
        three = new MyBroadcastReceiverThree();
        IntentFilter filter3 = new IntentFilter();
        filter3.setPriority(1600);
        filter3.addAction("Intercept_Stitch");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(three, filter3, Context.RECEIVER_NOT_EXPORTED); // 添加RECEIVER_NOT_EXPORTED标志
        }
    }


    private void init() {
        Button btn_send= (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      /*          Intent intent = new Intent();
                intent.setAction("Intercept_Stitch"); //定义广播的事件类型
                sendOrderedBroadcast(intent,null);     // 发送广播*/
                Intent intent = new Intent();
                intent.setAction("Intercept_Stitch"); // 定义广播的事件类型
                MyBroadcastReceiverThree receiver = new MyBroadcastReceiverThree();
                sendOrderedBroadcast(intent,null,receiver, null, 0, null, null); // 发送有序广播
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(one);
        unregisterReceiver(two);
        unregisterReceiver(three);
    }
}
