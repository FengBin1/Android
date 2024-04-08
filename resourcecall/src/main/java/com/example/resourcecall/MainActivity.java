package com.example.resourcecall;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // 计科六班 202305420615 冯斌

    TextView tvJava1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvJava1=(TextView)findViewById(R.id.tvJava);  //Java调用布局
        tvJava1.setText(getResources().getString(R.string.c));
        tvJava1.setTextSize(getResources().getDimension(R.dimen.chicuna));   //Java调用尺寸
        tvJava1.setTextColor(getResources().getColor(R.color.yansef));       //Java调用颜色
        tvJava1.setBackground(getResources().getDrawable(R.drawable.dog4));    //Java调用图片
    }

}