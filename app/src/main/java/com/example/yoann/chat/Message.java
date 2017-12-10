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

    private String key;
    private String content;
    private String userName;
    private String userEmail = "unknown";

    private Long timestamp;

    public Message() {
    }

    public String toString() {
        return "{key:" + this.getKey() + ", content:" + this.getContent() + ", name:" + this.getUserName() + ", email:" + this.getUserEmail();
    }

    public Message(String content, String userName, String userEmail) {
        this.content = content;
        this.userName = (userName == null)? "Unknown" : userName;
        this.userEmail = (userEmail == null)? "unknown" : userEmail;

        this.timestamp = System.currentTimeMillis();
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        String dateString = formatter.format(new Date(timestamp));
        return dateString;
    }

    public String getKey() { return this.key; }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = (content == null)? "empty message" : content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = (userName == null)? "Unknown" : userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = (userEmail == null)? "unknown" : userEmail;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = (timestamp == null)? System.currentTimeMillis() : timestamp;
    }
}

