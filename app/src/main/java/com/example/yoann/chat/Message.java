package com.example.yoann.chat;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Yoann on 27/11/2017.
 */

public class Message {

    public String content;
    public String userName;
    public String userEmail = "unknown";

    static public List<String> Id = new ArrayList<>();

    public Long timestamp;

    public Message() {
    }

    public Message(String content, String userName, String userEmail) {
        this.content = content;
        this.userName = userName;
        this.userEmail = userEmail;
        this.timestamp = System.currentTimeMillis();
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        String dateString = formatter.format(new Date(timestamp));
        return dateString;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public static void setId(String key){
        Log.d("tag", key);
        Id.add(key);
    }

    public static String getId(int position){
        return Id.get(position);
    }

    public static void removeId(int position){
        Id.remove(position);
    }

}

