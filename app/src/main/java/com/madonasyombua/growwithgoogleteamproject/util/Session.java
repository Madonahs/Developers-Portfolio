package com.madonasyombua.growwithgoogleteamproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by madona on 2/17/2018.
 */

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setuserId(int userId) {
        prefs.edit().putInt("userId", userId).commit();
    }

    public int getuserId() {

        return prefs.getInt("userId", 0);
    }

    public void logout() {
        prefs.edit().remove("userId").commit();
    }
}
