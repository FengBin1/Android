package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
// 冯斌
public class MyBroadcastReceiverTwo extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BroadcastReceiverTwo", "自定义的广播接收者Two,接收到了广播事件");
        abortBroadcast(); //拦截有序广播
        Log.i("BroadcastReceiverTwo","我是广播接收者Two，广播被我拦截了");

    }
}
