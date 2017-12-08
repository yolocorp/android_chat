package com.example.yoann.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NamePickerActivity extends AppCompatActivity {

    public static final String TAG = NamePickerActivity.class.getSimpleName();

    EditText mNameEditText;
    EditText mEmailEditText;
    Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_picker);

        mNameEditText = (EditText) findViewById(R.id.EditName);
        mEmailEditText = (EditText) findViewById(R.id.EditEmail);

        mSubmitButton = (Button) findViewById(R.id.ButtonSubmit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEditText.getText().toString();
                String mail = mEmailEditText.getText().toString();
                UserStorage.saveUserInfo(getBaseContext(),name, mail);
                Intent intent = new Intent(getBaseContext(), AppActivity.class);
                startActivity(intent);
            }
        });

    }
}
