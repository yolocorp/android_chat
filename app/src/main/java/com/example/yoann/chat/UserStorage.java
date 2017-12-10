package com.example.yoann.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

class UserStorage {

    static void saveUserInfo(Context context, String name, String email) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("USER_NAME", name);
        editor.putString("USER_EMAIL", email);
        editor.apply();
    }

    static Map<String, String> getUserInfo(Context context){
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, String> userInfos = new HashMap<String, String>();
        userInfos.put("USER_NAME", sh.getString("USER_NAME", null));
        userInfos.put("USER_EMAIL", sh.getString("USER_EMAIL", null));

        return userInfos;
    }

}
