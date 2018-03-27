package com.madonasyombua.growwithgoogleteamproject.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.madonasyombua.growwithgoogleteamproject.R;

/**
 * Created by mahersoua on 27/02/2018.
 */

public class LoginStatusManager {
    public static void storeLoginStatus(Context context, boolean status){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status_key), status);
        editor.apply();
    }

    public static boolean getLoginStatus(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status_key),
                context.getResources().getBoolean(R.bool.pref_login_status_default_key));
    }
}
