package com.madonasyombua.growwithgoogleteamproject.database;

import android.util.Log;

import com.google.android.gms.internal.qg;
import com.google.android.gms.internal.rx;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.madonasyombua.growwithgoogleteamproject.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahersoua on 21/02/2018.
 */

public class DataManager {

    private static final String TAG = "DataManger";

    public static void saveUserInfo(String name){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("userInfos");

        Log.d(TAG, ""+ref);
        String id = ref.push().getKey();
        Map<String, User> users = new HashMap<>();
        users.put("email", new User(name));

        Log.d(TAG, ""+ref.setValue(new User(name)));

    }
}
