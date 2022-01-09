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
package com.madonasyombua.growwithgoogleteamproject.ui.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.madonasyombua.growwithgoogleteamproject.data.models.User

/**
 * Created by mahersoua on 23/02/2018.
 */
object AppLoginManager {
    private val TAG = AppLoginManager::class.java.name
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var mCurrentUser: FirebaseUser? = null

    @JvmStatic
    fun registerUser(activity: Activity, user: User, username: String?): FirebaseUser? {
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activity) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    mCurrentUser = firebaseAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    mCurrentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task1: Task<Void?> ->
                            if (task1.isSuccessful) {
                                Log.d(TAG, "User profile updated.")
                            }
                        }
                    (activity as LoginInterface).onRegistrationSuccess()
                } else {
                    (activity as LoginInterface).onRegistrationFailed()
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        return mCurrentUser
    }

    @JvmStatic
    fun signinUser(activity: Activity, user: User) {
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activity) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    mCurrentUser = firebaseAuth.currentUser
                    user.setStatus(true)
                    (activity as LoginInterface).onSigninSuccess(user)
                } else {
                    (activity as LoginInterface).onSigninFailed()
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    interface LoginInterface {
        fun onSigninSuccess(user: User?)
        fun onRegistrationSuccess()
        fun onSigninFailed()
        fun onRegistrationFailed()
        fun onResetPasswordSuccess()
        fun onResetPasswordFailed()
    }
}