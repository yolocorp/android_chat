package com.example.yoann.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NamePickerActivity extends AppCompatActivity implements TextWatcher {

    EditText mNameEditText;
    EditText mEmailEditText;
    Button mSubmitButton;
    TextView errorName;
    TextView errorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_picker);

        mNameEditText = (EditText) findViewById(R.id.EditName);
        mEmailEditText = (EditText) findViewById(R.id.EditEmail);

        errorName = (TextView) findViewById(R.id.error_name);
        errorEmail = (TextView) findViewById(R.id.error_mail);

        mSubmitButton = (Button) findViewById(R.id.ButtonSubmit);

        mNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (mNameEditText.getText().toString().isEmpty()) {
                    showErrorName();
                    return false;
                } else {
                    hideErrorName();
                    return false;

                }
            }
        });

        mEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (mEmailEditText.getText().toString().isEmpty()) {
                    showErrorEmail();
                    return false;
                } else {
                    hideErrorEmail();
                    return false;

                }
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mNameEditText.getText().toString();
                String mail = mEmailEditText.getText().toString();
                if (mNameEditText.getText().toString().isEmpty() && mEmailEditText.getText().toString().isEmpty()) {
                    showErrorEmail();
                    showErrorName();
                } else if (mNameEditText.getText().toString().isEmpty()) {
                    showErrorName();
                    hideErrorEmail();
                } else if (mEmailEditText.getText().toString().isEmpty()) {
                    showErrorEmail();
                    hideErrorName();
                } else {
                    UserStorage.saveUserInfo(getBaseContext(), name, mail);
                    Intent intent = new Intent(getBaseContext(), AppActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void showErrorName() {
        errorName.setVisibility(View.VISIBLE);
    }

    private void showErrorEmail() {
        errorEmail.setVisibility(View.VISIBLE);
    }

    private void hideErrorName() {
        errorName.setVisibility(View.INVISIBLE);
    }

    private void hideErrorEmail() {
        errorEmail.setVisibility(View.INVISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mNameEditText.getText().length() < 0) {
            showErrorName();
        } else if (mEmailEditText.getText().length() < 0) {
            showErrorEmail();
        } else {
            hideErrorName();
            hideErrorEmail();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
                NamePickerActivity.this, R.style.Dialog));
        alertDialogBuilder.setTitle("Really Exit ?");
        alertDialogBuilder.setMessage("Are you sure you want to exit ? ");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                NamePickerActivity.super.onBackPressed();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
