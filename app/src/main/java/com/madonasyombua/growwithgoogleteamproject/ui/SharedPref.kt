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
package com.madonasyombua.growwithgoogleteamproject.ui

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val mySharedPref: SharedPreferences =
        context.getSharedPreferences("filename", Context.MODE_PRIVATE)

    fun setNightModeState(state: Boolean) {
        val editor = mySharedPref.edit()
        editor.putBoolean("enable_dark_mode", state)
        editor.apply()
    }

    fun loadNightModeState(): Boolean {
        return mySharedPref.getBoolean("enable_dark_mode", false)
    }

}