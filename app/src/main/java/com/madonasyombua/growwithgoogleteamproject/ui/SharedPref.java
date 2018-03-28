package com.madonasyombua.growwithgoogleteamproject.ui;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kidus11 on 3/8/18.
 */

public class SharedPref {
    SharedPreferences mySharedPref ;
    public SharedPref(Context context) {
        mySharedPref = context.getSharedPreferences("filename",Context.MODE_PRIVATE);
    }
    // This will save the night state - True or False
    public void setNightModeState(Boolean state) {
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putBoolean("enable_dark_mode",state);
        editor.apply();
    }
    /**
     *
     * @return
     * This will load the Night State
     */
    public Boolean loadNightModeState (){
        Boolean state = mySharedPref.getBoolean("enable_dark_mode",false);
        return  state;
    }
}

