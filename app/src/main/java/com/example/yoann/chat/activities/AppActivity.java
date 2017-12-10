package com.example.yoann.chat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yoann.chat.R;
import com.example.yoann.chat.models.UserStorage;

import java.util.Map;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Map<String, String> userInfos = UserStorage.getUserInfo(getBaseContext());
        if (userInfos.get("USER_NAME") != null && userInfos.get("USER_EMAIL") != null) {
            Intent intent = new Intent(getBaseContext(), ChatActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getBaseContext(), NamePickerActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
