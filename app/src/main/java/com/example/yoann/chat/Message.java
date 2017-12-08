package com.example.yoann.chat;

import android.content.Context;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Yoann on 27/11/2017.
 */

public class Message {

    public String content;
    public String userName;
    public String userEmail;

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

}

