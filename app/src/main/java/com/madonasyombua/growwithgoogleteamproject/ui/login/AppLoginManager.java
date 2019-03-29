/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.ui.login;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.madonasyombua.growwithgoogleteamproject.data.models.User;

import java.util.Objects;

/**
 * Created by mahersoua on 23/02/2018.
 */

public class AppLoginManager {

    private static final String TAG = AppLoginManager.class.getName();
    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static FirebaseUser mCurrentUser;

    public static FirebaseUser registerUser(final Activity activity, User user, final String username){
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        mCurrentUser = firebaseAuth.getCurrentUser();
                        // This sets the username for the user
                        // and since Firebase User doesn't have setDisplay, we use UserProfileChangeRequest
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();

                        mCurrentUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                    }
                                });

                        ((LoginInterface)activity).onRegistrationSuccess();
                    } else {
                        ((LoginInterface)activity).onRegistrationFailed();
                        Toast.makeText(activity, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return mCurrentUser;
    }

    public static void signinUser(final  Activity activity, final User user){
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        mCurrentUser = firebaseAuth.getCurrentUser();
                        user.setStatus(true);
                        ((LoginInterface)activity).onSigninSuccess(user);
                    } else {
                        ((LoginInterface)activity).onSigninFailed();
                        Toast.makeText(activity, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void resetPassword(final Activity activity, String email){
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ((LoginInterface)activity).onResetPasswordSuccess();
                    } else {
                        ((LoginInterface)activity).onResetPasswordFailed();
                    }
                });
    }

    public interface LoginInterface {
        void onSigninSuccess (User user);
        void onRegistrationSuccess();
        void onSigninFailed();
        void onRegistrationFailed();
        void onResetPasswordSuccess();
        void onResetPasswordFailed();
    }
}
