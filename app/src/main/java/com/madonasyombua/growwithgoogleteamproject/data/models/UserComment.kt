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
package com.madonasyombua.growwithgoogleteamproject.data.models

import android.os.Parcel
import android.os.Parcelable

/**
 * @author madona 12/15/19
 *
 */
internal open class UserComment : Parcelable {
    private var cos: Int
    var user: User? = null
    var text: String?
    private var commented: String?
        private set
    var image: String?

    constructor(cos: Int, user: User?, text: String?, commented: String?, image: String?) {
        this.cos = cos
        this.user = user
        this.text = text
        this.commented = commented
        this.image = image
    }

    protected constructor(`in`: Parcel) {
        cos = `in`.readInt()
        text = `in`.readString()
        commented = `in`.readString()
        image = `in`.readString()
    }

    fun setCos(cid: Int) {
        cos = cid
    }

    fun getcos(): Int {
        return cos
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(cos)
        dest.writeString(text)
        dest.writeString(commented)
        dest.writeString(image)
    }



    companion object CREATOR : Parcelable.Creator<UserComment> {
        override fun createFromParcel(parcel: Parcel): UserComment {
            return UserComment(parcel)
        }

        override fun newArray(size: Int): Array<UserComment?> {
            return arrayOfNulls(size)
        }
    }
}