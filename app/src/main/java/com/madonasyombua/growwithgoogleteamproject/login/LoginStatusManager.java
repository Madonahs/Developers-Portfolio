package com.madonasyombua.growwithgoogleteamproject.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mahersoua on 27/02/2018.
 */

public class LoginStatusManager {
    public static void storeLoginStatus(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged", true);
        editor.apply();
    }

    public static boolean getLoginStatus(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return sharedPreferences.getBoolean("logged", false);
    }
}
