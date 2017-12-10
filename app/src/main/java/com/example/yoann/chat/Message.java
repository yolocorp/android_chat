package com.example.yoann.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String key;
    private String content;
    private String userName = "unknown";
    private String userEmail = "unknown";

    private Long timestamp;

    public String toString() {
        return "{key:" + this.getKey() + ", content:" + this.getContent() + ", name:" + this.getUserName() + ", email:" + this.getUserEmail();
    }
    Message(){
    }

    Message(String content, String userName, String userEmail) {
        this.content = content;
        this.userName = (userName == null) ? "unknown" : userName;
        this.userEmail = (userEmail == null) ? "unknown" : userEmail;

        this.timestamp = System.currentTimeMillis();
    }

    String getDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        return formatter.format(new Date(timestamp));
    }

    String getKey() {
        return this.key;
    }

    void setKey(String key) {
        this.key = key;
    }

    String getUserEmail() {
        return this.userEmail;
    }

    String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = (content == null) ? "empty message" : content;
    }

    String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = (userName == null) ? "Unknown" : userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = (userEmail == null) ? "unknown" : userEmail;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = (timestamp == null) ? System.currentTimeMillis() : timestamp;
    }
}

