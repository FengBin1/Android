package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiverThree extends BroadcastReceiver {
// 冯斌
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BroadcastReceiverThree", "自定义的广播接收者Three,接收到了广播事件");
    }
}
