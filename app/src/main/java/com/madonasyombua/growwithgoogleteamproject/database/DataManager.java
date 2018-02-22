package com.madonasyombua.growwithgoogleteamproject.database;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.madonasyombua.growwithgoogleteamproject.models.User;
import com.madonasyombua.growwithgoogleteamproject.ui.LoginActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahersoua on 21/02/2018.
 */

public class DataManager extends AppCompatActivity {

    private static final String TAG = "DataManger";
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static DataManager instance;

    public static DataManager getInstance() {
        if(instance == null){
            return instance = new DataManager();
        }
        return instance;
    }

    public static void saveUserInfo(User user){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        String id = ref.push().getKey();
        ref.setValue(user);
    }

    public static void registerUser(final Activity activity, User user){

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e(TAG , task.getException().getMessage());
                        }
                    }
                });
    }

    public static void signinUser(Activity activity, User user){
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {

                        }
                    }
                });
    }
}
