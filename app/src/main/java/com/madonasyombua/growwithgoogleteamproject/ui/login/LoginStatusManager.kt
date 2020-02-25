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

import android.content.Context
import android.preference.PreferenceManager
import com.madonasyombua.growwithgoogleteamproject.R

/**
 * Created by mahersoua on 27/02/2018.
 */
object LoginStatusManager {
    @JvmStatic
    fun storeLoginStatus(context: Context, status: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putBoolean(context.getString(R.string.pref_login_status_key), status)
        editor.apply()
    }

    @JvmStatic
    fun getLoginStatus(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status_key),
                context.resources.getBoolean(R.bool.pref_login_status_default_key))
    }
}