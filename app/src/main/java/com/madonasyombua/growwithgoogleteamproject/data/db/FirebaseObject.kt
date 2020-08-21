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
package com.madonasyombua.growwithgoogleteamproject.data.db

import com.google.firebase.database.Exclude

abstract class FirebaseObject() {
    @get:Exclude
    @set:Exclude
    @Exclude
    var key: String? = null

    @Exclude
    fun hasKey(): Boolean? {
        return key.let { key?.isNotEmpty() }
    }

    @get:Exclude
    abstract val path: String?

    @get:Exclude
    abstract val base: String?
    override fun equals(o: Any?): Boolean {
        return this === o || !(o == null || javaClass != o.javaClass) && (key
                == (o as FirebaseObject).key)
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}