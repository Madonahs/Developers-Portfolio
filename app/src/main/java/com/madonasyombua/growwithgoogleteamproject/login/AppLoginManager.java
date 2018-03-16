package com.madonasyombua.growwithgoogleteamproject.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.madonasyombua.growwithgoogleteamproject.models.User;

/**
 * Created by mahersoua on 23/02/2018.
 */

public class AppLoginManager {

    private static final String TAG = "DataManager";
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static FirebaseUser mCurrentUser;

    public static FirebaseUser registerUser(final Activity activity, User user){
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mCurrentUser = firebaseAuth.getCurrentUser();
                            ((LoginInterface)activity).onRegistrationSuccess();
                        } else {
                            ((LoginInterface)activity).onRegistrationFailed();
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return mCurrentUser;
    }

    public static FirebaseUser signinUser(final  Activity activity, User user){
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mCurrentUser = firebaseAuth.getCurrentUser();
                            ((LoginInterface)activity).onSigninSuccess();
                        } else {
                            ((LoginInterface)activity).onSigninFailed();
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return mCurrentUser;
    }

    public static void resetPassword(final Activity activity, String email){
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ((LoginInterface)activity).onResetPasswordSuccess();
                        } else {
                            ((LoginInterface)activity).onResetPasswordFailed();
                        }
                    }
                });
    }

    public interface LoginInterface {
        void onSigninSuccess ();
        void onRegistrationSuccess();
        void onSigninFailed();
        void onRegistrationFailed();
        void onResetPasswordSuccess();
        void onResetPasswordFailed();
    }
}
