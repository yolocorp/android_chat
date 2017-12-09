package com.example.yoann.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;

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
    DatabaseReference list;

    LinearLayoutManager linearLayoutManager;

    static DatabaseReference Dbremove;

    Map<String,String> userInfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ediText = (EditText) findViewById(R.id.inputEditText);
        bouton = (ImageButton) findViewById(R.id.sendButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        cardView = (CardView) findViewById(R.id.card_view);
        userInfos = UserStorage.getUserInfo(getBaseContext());


        List<Message> dataList= new ArrayList<>();

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
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());            }
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
            UserStorage.saveUserInfo(getBaseContext(),null,null);
            Intent intent = new Intent (getBaseContext(), AppActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void connection(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("chat/messages");
        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        List<Message> items = new ArrayList<>();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
            items.add(postSnapshot.getValue(Message.class));
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

    public static void removeMessage(){
        Log.d("Tag2", "je suis dans le remove");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseR = database.getReference("chat/messages");
        mDatabaseR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("ID", dataSnapshot.child("chat/mesages").getKey());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

}
