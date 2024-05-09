package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wechat.MainActivity;
import com.example.wechat.R;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // 检查用户名和密码
                if (username.equals("1") && password.equals("1")) {
                    // 保存登录状态和时间
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putLong("loginTime", Calendar.getInstance().getTimeInMillis());
                    editor.apply();

                    // 跳转到 MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前活动，防止用户返回到登录页面
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 检查是否已经登录过，如果是，且时间不超过 5 分钟，则自动跳转到 MainActivity
        if (isLoggedIn()) {
            long loginTime = sharedPreferences.getLong("loginTime", 0);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if ((currentTime - loginTime) < (60 * 1000)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 结束当前活动，防止用户返回到登录页面
            }
        }
    }

    // 检查是否已经登录过
    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("loggedIn", false);
    }
}
