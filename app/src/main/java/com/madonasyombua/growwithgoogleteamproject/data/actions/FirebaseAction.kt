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
package com.madonasyombua.growwithgoogleteamproject.data.actions

import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference.CompletionListener
import com.madonasyombua.growwithgoogleteamproject.data.db.FirebaseObject
import com.madonasyombua.growwithgoogleteamproject.interfaces.Callback

object FirebaseAction {
    private const val ERROR_CODE_DATA_NULL = 0
    private const val ERROR_CODE_TOKEN_NULL = 1
    private const val ERROR_CODE_USER_NULL = 2
    private const val ERROR_DATA_NULL = "Error: DataSnapshot null."
    private const val ERROR_TOKEN_NULL = "Error: Firebase token null."
    private const val ERROR_USER_NULL = "Error: FirebaseAuth user null."

    /**
     * Getter for root reference.
     * @return reference to database root
     */
    private val base: DatabaseReference
        get() = FirebaseDatabase.getInstance().reference

    @JvmStatic
    fun write(o: FirebaseObject, cl: CompletionListener?) {
        if (o.hasKey()!!) {
            base.child(o.path).setValue(o, cl)
        } else {
            base.child(o.base).push().setValue(o, cl)
        }
    }

    operator fun <T : FirebaseObject?> get(
        path: String?,
        cls: Class<T>?,
        gc: FirebaseCallback<T?>
    ) {
        base.child(path).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    val o = dataSnapshot.getValue(cls)
                    gc.onComplete(o)
                } else {
                    gc.onError(ERROR_CODE_DATA_NULL, ERROR_DATA_NULL)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                gc.onError(databaseError.code, databaseError.message)
            }
        })
    }


    interface FirebaseCallback<T : FirebaseObject?> : Callback<T>
}