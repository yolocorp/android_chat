package com.example.yoann.chat.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yoann.chat.models.Message;
import com.example.yoann.chat.adapters.MessageAdapter;
import com.example.yoann.chat.R;
import com.example.yoann.chat.models.UserStorage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ValueEventListener {

    EditText ediText;
    ImageButton bouton;
    RecyclerView recyclerView;
    CardView cardView;

    MessageAdapter mMessageAdapter;
    DatabaseReference mDatabaseReference;
    DatabaseReference newDbRef;

    LinearLayoutManager linearLayoutManager;

    Map<String, String> userInfos;

    static Context context;

    static AlertDialog.Builder alertDialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ediText = (EditText) findViewById(R.id.inputEditText);
        bouton = (ImageButton) findViewById(R.id.sendButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        cardView = (CardView) findViewById(R.id.card_view);
        userInfos = UserStorage.getUserInfo(getBaseContext());
        context = getApplicationContext();
        alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(
                ChatActivity.this, R.style.Dialog));

        List<Message> dataList = new ArrayList<>();

        mMessageAdapter = new MessageAdapter(dataList, userInfos.get("USER_EMAIL"));

        connection();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mMessageAdapter);


        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                ediText.setText("");
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deconnexion, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_deconnexion) {
            UserStorage.saveUserInfo(getBaseContext(), null, null);
            Intent intent = new Intent(getBaseContext(), AppActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void connection() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("chat/messages");
        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Message> items = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            Message message = postSnapshot.getValue(Message.class);
            message.setKey(postSnapshot.getKey());
            items.add(message);
        }
        mMessageAdapter.setDatas(items);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void sendMessage() {
        newDbRef = mDatabaseReference.push();
        newDbRef.setValue(
                new Message(ediText.getText().toString(), userInfos.get("USER_NAME"), userInfos.get("USER_EMAIL")));
    }

    public static void removeMessage(String messageKey) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("chat/messages");
        databaseRef.child(messageKey).removeValue();
    }

    public static void showAlertDialog(final String messageKey) {

        alertDialogBuilder.setTitle("Delete Message");
        alertDialogBuilder.setMessage("Do you want to delete the message ");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                removeMessage(messageKey);

                Toast toast = Toast.makeText(context, "Deleted Message", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.setBackgroundResource(R.drawable.toast);
                toast.setView(view);
                toast.show();
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

    @Override
    public void onBackPressed() {
        alertDialogBuilder.setTitle("Really Exit ?");
        alertDialogBuilder.setMessage("Are you sure you want to exit ? ");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ChatActivity.super.onBackPressed();
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
